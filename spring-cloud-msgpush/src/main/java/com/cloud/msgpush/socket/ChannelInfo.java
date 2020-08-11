package com.cloud.msgpush.socket;

import io.netty.channel.ChannelId;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base             *
 *                                                            *
 *         File Name : ChannelInfo.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/31 13:56                       *
 *                                                            *
 *         Last Update : 2020/7/31 13:56                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *                                                            *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class ChannelInfo {

    private String key;
    private ChannelId channelId;

    public ChannelInfo() {
    }

    public ChannelInfo(String key, ChannelId channelId) {
        this.key = key;
        this.channelId = channelId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ChannelId getChannelId() {
        return channelId;
    }

    public void setChannelId(ChannelId channelId) {
        this.channelId = channelId;
    }
}
