package com.cloud.msgpush.service.impl;

import com.cloud.bean.msg.QueryParam;
import com.cloud.bean.msg.QueryParamBatch;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.cloud.msgpush.bean.SmsEnv;
import com.cloud.msgpush.service.ApiService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : ApiServiceImpl.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/11 9:22                       *
 *                                                            *
 *         Last Update : 2020/8/11 9:22                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   阿里云短信实现                                             *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Service("apiService")
public class ApiServiceImpl implements ApiService, InitializingBean {

    private SmsEnv smsEnv;

    private CommonResponse response = null;
    private IAcsClient client = null;
    @Autowired
    public void setSmsEnv(SmsEnv smsEnv) {
        this.smsEnv = smsEnv;
    }

    @Override
    public void afterPropertiesSet() {
        response = new CommonResponse();
        DefaultProfile profile = DefaultProfile.getProfile(smsEnv.getRegionName(), smsEnv.getAccessKeyId(), smsEnv.getAccessKeySecret());
        client = new DefaultAcsClient(profile);
    }


    @Override
    public CommonResponse send(QueryParam param) {
        try {
            String signName = new String(smsEnv.getSignName().getBytes(StandardCharsets.ISO_8859_1.name()), StandardCharsets.UTF_8.name());
            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");
            request.putQueryParameter("RegionId", smsEnv.getRegionName());
            request.putQueryParameter("PhoneNumbers", param.getPhoneNumber());
            request.putQueryParameter("SignName",signName);
            request.putQueryParameter("TemplateCode", smsEnv.getTemplateCode());
            request.putQueryParameter("TemplateParam", JSON.toJSONString(param.getTemplateParam()));
            request.putQueryParameter("SmsUpExtendCode", "90999");
            response = client.getCommonResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public CommonResponse querySendDetails(QueryParam param) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("QuerySendDetails");
        request.putQueryParameter("RegionId", smsEnv.getRegionName());
        request.putQueryParameter("PhoneNumber", param.getPhoneNumber());
        request.putQueryParameter("SendDate", param.getSendDate());
        request.putQueryParameter("PageSize", param.getPageSize());
        request.putQueryParameter("CurrentPage", param.getCurrentPage());
        try {
            response = client.getCommonResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public CommonResponse batchSend(QueryParamBatch batch) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendBatchSms");
        request.putQueryParameter("RegionId", smsEnv.getRegionName());
        request.putQueryParameter("PhoneNumberJson", JSON.toJSONString(batch.getPhoneNumber()));
        request.putQueryParameter("SignNameJson", JSON.toJSONString(batch.getSignName()));
        request.putQueryParameter("TemplateCode",smsEnv.getTemplateCode());
        request.putQueryParameter("TemplateParamJson", JSON.toJSONString(batch.getTemplateParam()));
        request.putQueryParameter("SmsUpExtendCodeJson", JSON.toJSONString(new String[]{"90999","90998"}));
        try {
            response = client.getCommonResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
