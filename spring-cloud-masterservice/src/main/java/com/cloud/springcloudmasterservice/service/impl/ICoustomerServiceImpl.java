package com.cloud.springcloudmasterservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.bean.ohters.CoustomerZt;
import com.cloud.springcloudmasterservice.mapper.CoustomerZtMapper;
import com.cloud.springcloudmasterservice.service.ICoustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


/**
 * ClassName: ICoustomerServiceImpl
 * Description:
 * date: 2020/7/20 14:54
 *
 * @author zwk
 */
@Service("iCoustomerService")
public class ICoustomerServiceImpl implements ICoustomerService {

    @Autowired
    CoustomerZtMapper coustomerZtMapper;


    @Override
    public List<CoustomerZt> selectAll() {
        QueryWrapper<CoustomerZt> query = Wrappers.query();
        query.le("ID", 20);
        return coustomerZtMapper.selectList(query);
    }

    @Override
    public CoustomerZt selectOne(Map<String, Object> params) {
        QueryWrapper<CoustomerZt> query = Wrappers.query();
        query.allEq(params);
        return coustomerZtMapper.selectOne(query);
    }

    @Override
    public List<CoustomerZt> selectJoin() {
        return coustomerZtMapper.selectJoin();
    }

    @Override
    public IPage<CoustomerZt> selectByPage(Integer start, Integer size) {
        IPage<CoustomerZt> page = new Page<>(start,size);
        QueryWrapper<CoustomerZt> query = Wrappers.query();
        query.le("ID", 20);
        return coustomerZtMapper.selectPage(page,query);
    }

    @Override
    public long insert(CoustomerZt coustomerZt) {
        return coustomerZtMapper.insert(coustomerZt);
    }
}
