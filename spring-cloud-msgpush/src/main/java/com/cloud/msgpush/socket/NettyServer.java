package com.cloud.msgpush.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base             *
 *                                                            *
 *         File Name : NettyServer.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/30 16:38                       *
 *                                                            *
 *         Last Update : 2020/7/30 16:38                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   netty高性能                                                *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class NettyServer {

    private final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }


    public void start() throws Exception {

        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.group(group,boosGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(this.port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            logger.info("收到新连接");
                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            ch.pipeline().addLast(new HttpServerCodec());
                            //以块的方式来写的处理器
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
                            ch.pipeline().addLast(new WebsocketHandler());
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10));
                        }
                    });
            ChannelFuture cf = bootstrap.bind().sync(); // 服务器异步创建绑定
            logger.info(NettyServer.class + " 启动正在监听： " + cf.channel().localAddress());
            logger.info("启动完成=============");
            cf.channel().closeFuture().sync(); // 关闭服务器通道
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
            boosGroup.shutdownGracefully().sync();
        }


    }




}
