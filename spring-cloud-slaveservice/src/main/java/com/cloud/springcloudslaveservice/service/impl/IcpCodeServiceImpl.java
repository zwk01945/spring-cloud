package com.cloud.springcloudslaveservice.service.impl;

import com.cloud.aop.annotation.CacheRedis;
import com.cloud.bean.ohters.IcpCode;
import com.cloud.springcloudslaveservice.mapper.IcpCodeMapper;
import com.cloud.springcloudslaveservice.service.IcpCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * ClassName: IcpCodeServiceImpl
 * Description:
 * date: 2020/7/21 16:22
 *
 * @author zyl
 */
@Service("icpCodeService")
public class IcpCodeServiceImpl implements IcpCodeService {

    @Autowired
    IcpCodeMapper icpCodeMapper;

    @Override
    @CacheRedis(value = {"icp_code","icp_code1"})
    public List<IcpCode> selectAll() {
        return icpCodeMapper.selectList(null);
    }

    @Override
    public long insert(IcpCode icpCode) {
        return icpCodeMapper.insert(icpCode);
    }

}
