package com.cloud.web.service;

import com.cloud.aop.annotation.CacheRedis;
import org.springframework.stereotype.Service;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : AserviceImpl.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/11 17:46                       *
 *                                                            *
 *         Last Update : 2020/8/11 17:46                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Service("aservice")
public class AserviceImpl implements Aservice {
    @Override
    @CacheRedis("ICP_CODE1")
    public String get() {
        return "hello";
    }
}
