package com.cloud.msgpush.socket;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : ChannelHandlerPool.java                *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/30 17:04                       *
 *                                                            *
 *         Last Update : 2020/7/30 17:04                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   通道组池，管理所有websocket连接                              *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class ChannelHandlerPool {

    public ChannelHandlerPool() {
    }

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
