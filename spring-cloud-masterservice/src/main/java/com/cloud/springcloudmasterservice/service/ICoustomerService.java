package com.cloud.springcloudmasterservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.bean.ResultObject;
import com.cloud.bean.ohters.CoustomerZt;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ICoustomerService
 * Description:
 * date: 2020/7/20 14:49
 *
 * @author zwk
 */
public interface ICoustomerService {

    List<CoustomerZt> selectAll();

    CoustomerZt selectOne(Map<String,Object> params);

    List<CoustomerZt> selectJoin();

    IPage<CoustomerZt> selectByPage(Integer start, Integer size);

    ResultObject insert(CoustomerZt coustomerZt);
}
