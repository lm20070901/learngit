package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.pojo.ActivityEnroll;

public interface IActivityEnrollDao {
    int deleteByPrimaryKey(Integer enrollId);

    int insert(ActivityEnroll record);

    int insertSelective(ActivityEnroll record);

    ActivityEnroll selectByPrimaryKey(Integer enrollId);

    int updateByPrimaryKeySelective(ActivityEnroll record);

    int updateByPrimaryKey(ActivityEnroll record);
    /**
     * 分页查询 列表
     * @param map
     * @param pageBounds
     * @return
     */
    PageList<ActivityEnroll> selectPageList(Map<String,Object> map,PageBounds pageBounds);
    /**
     * 查询 列表所有数据
     * @param map
     * @return
     */
    List<ActivityEnroll> selectPageList(Map<String,Object> map);
    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<Integer> ids);
    
}