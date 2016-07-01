package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.dao.base.BaseDao;
import com.tianwen.dcdp.pojo.System;

public interface ISystemDao extends BaseDao<System>{
    int deleteByPrimaryKey(Integer id);

    int insert(System record);

    int insertSelective(System record);

    System selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(System record);

    int updateByPrimaryKey(System record);
    
    /**
     * 查询字典表
     * @param queryMap
     * @return
     */
	List<Map<String,Object>> getSystemList(Map<String, Object> queryMap);
}