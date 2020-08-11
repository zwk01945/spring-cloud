package com.cloud.msgpush.socket;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatchers;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : WebsocketHandler.java                  *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/30 17:02                       *
 *                                                            *
 *         Last Update : 2020/7/30 17:02                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   socket建立通信的核心                                        *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class WebsocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final static Logger logger = LoggerFactory.getLogger(WebsocketHandler.class);

    private final static List<ChannelInfo> channelInfo = new CopyOnWriteArrayList<ChannelInfo>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //添加到channelGroup通道组
        ChannelHandlerPool.channelGroup.add(ctx.channel());
        logger.info(ctx.channel().id() + "与服务端建立连接，通道开启！");
        logger.info("剩余连接个数为:{}",ChannelHandlerPool.channelGroup.size());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (channelInfo.size() > 0) {
           channelInfo.forEach(value -> {
               if (value.getChannelId() == ctx.channel().id()) {
                   logger.info("移除集合中连接,key为:{},channel为:{}",value.getKey(),value.getChannelId());
                   channelInfo.remove(value);
                   logger.info("channelInfo集合个数为:{}",JSON.toJSONString(channelInfo.size()));
               }
           });
        }
        ChannelHandlerPool.channelGroup.remove(ctx.channel());
        logger.info(ctx.channel().id() + "与服务端断开连接，通道关闭！");
        logger.info("剩余连接个数为:{}",ChannelHandlerPool.channelGroup.size());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //首次连接是FullHttpRequest，处理参数
            final ChannelId id = ctx.channel().id();
            if (msg instanceof FullHttpRequest) {
                logger.info("首次连接,当前连接点ChannelId为:{}",id);
                FullHttpRequest request = (FullHttpRequest) msg;
                String uri = request.uri();
                Map<String,String> paramMap=getUrlParams(uri);
                logger.info("接收到的参数是："+ JSON.toJSONString(paramMap));
                if (paramMap.size() >0)
                    channelInfo.add(new ChannelInfo(paramMap.get("name"),id));
                //如果url包含参数，需要处理
                if(uri.contains("?")){
                    String newUri=uri.substring(0,uri.indexOf("?"));
                    logger.info("截取后的Uri为:{}",newUri);
                    request.setUri(newUri);
                }
            }else if(msg instanceof TextWebSocketFrame){
                //正常的TEXT消息类型
                TextWebSocketFrame frame=(TextWebSocketFrame)msg;
                logger.info("服务器收到客户端数据：" +frame.text());
                sendAllMessage(frame.text());
            }
        logger.info("channelInfo集合个数为:{}",JSON.toJSONString(channelInfo.size()));
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }



    /**
     * 广播发送
     * @param message
     */
    public static boolean sendAllMessage(String message) throws InterruptedException {
        //收到信息后，群发给所有channel
        final ChannelGroup channelGroup = ChannelHandlerPool.channelGroup;
        ChannelGroupFuture channelFutures = channelGroup.writeAndFlush(new TextWebSocketFrame(message));
        Thread.sleep(200);
        return channelFutures.isDone();
    }

    /**
     * 单独推送
     * @param name
     * @param message
     */
    public synchronized static boolean sendMessage(String name,String message) throws Exception{
        final ChannelGroup channelGroup = ChannelHandlerPool.channelGroup;
        AtomicReference<ChannelGroupFuture> channelFutures = new AtomicReference<ChannelGroupFuture>();
        List<ChannelInfo> collect = channelInfo.stream().filter(value -> value.getKey().equals(name)).collect(Collectors.toList());
        if (collect.size() == 0) throw new RuntimeException("请先建立连接.....");
            try {
                collect.forEach(x -> {
                    Channel channel = channelGroup.find(x.getChannelId());
                    if (channel == null) throw new RuntimeException("请先建立连接.....");
                    channelFutures.set(channelGroup.writeAndFlush(new TextWebSocketFrame(message), ChannelMatchers.is(channel)));
                    logger.info("服务器主动推送消息给: {},推送消息为:{}",name,message);
                });
            }catch (Exception e) {
                throw new RuntimeException("请先建立连接.....");
            }
        return channelFutures.get().isDone();
    }

    private static Map<String,String> getUrlParams(String url){
        Map<String,String> map = new HashMap<>();
        url = url.replace("?",";");
        if (!url.contains(";")){
            return map;
        }
        if (url.split(";").length > 0){
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr){
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key,value);
            }
            return  map;

        }else{
            return map;
        }
    }
}
