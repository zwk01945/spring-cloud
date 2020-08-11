package com.cloud.msgpush.service;

import com.cloud.bean.msg.QueryParam;
import com.cloud.bean.msg.QueryParamBatch;
import com.aliyuncs.CommonResponse;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud                               *
 *                                                            *
 *         File Name : ApiService.java                        *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/10 15:26                       *
 *                                                            *
 *         Last Update : 2020/8/10 15:26                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   短信发送接口                                               *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public interface ApiService {

    /**
     * 发送短信验证码
     * 对于发送同一code的使用该方法
     * @param param 查询参数包括手机号
     */
    CommonResponse send(QueryParam param);
    /**
     * 分页查询已经发送的短信
     * @param param 查询参数包括手机号
     */
    CommonResponse querySendDetails(QueryParam param);
    /**
     * 批量发送短信
     * 在一次请求中，最多可以向100个手机号码分别发送短信。
     * 主要是用于发送不同签名和模板的数据
     * @param batch 批量导入查询参数包括手机号数组
     */
    CommonResponse batchSend(QueryParamBatch batch);

}
