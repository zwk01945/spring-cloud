package com.cloud.springcloudslaveservice.service;

import com.cloud.bean.ohters.IcpCode;

import java.util.List;

/**
 * ClassName: IcpCodeService
 * Description:
 * date: 2020/7/21 16:21
 *
 * @author zyl
 */
public interface IcpCodeService {
    List<IcpCode> selectAll();
    long insert(IcpCode icpCode);
}
