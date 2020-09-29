package com.cloud.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.bean.web.StageUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface StageUserDao extends BaseMapper<StageUser> {

    List<Map<String, Object>> findCateByUser(@Param("stageUser") StageUser stageUser);

    StageUser loginCheck(@Param("loginUser") StageUser stageUser);

}