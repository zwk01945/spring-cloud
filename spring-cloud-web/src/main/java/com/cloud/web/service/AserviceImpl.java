package com.cloud.web.service;

import com.cloud.aop.annotation.CacheRedis;
import com.cloud.bean.ResultObject;
import com.cloud.web.feign.MasterService;
import com.cloud.web.feign.SlaveService;
//import io.seata.core.context.RootContext;
//import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    MasterService masterService;

    SlaveService slaveService;

    @Autowired
    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }
    @Autowired
    public void setSlaveService(SlaveService slaveService) {
        this.slaveService = slaveService;
    }


    @Override
    @CacheRedis("ICP_CODE1")
    public String get() {
        return "hello";
    }



    @Override
//    @GlobalTransactional
    public void insert() {
//        System.out.println("==================="+ RootContext.getXID());
        ResultObject master = masterService.insert();
        ResultObject slave = slaveService.insert();

        if (master.getCode() != 200 || slave.getCode() != 200) {
            throw new RuntimeException("子服务失败,事务回滚");
        }


    }
}
