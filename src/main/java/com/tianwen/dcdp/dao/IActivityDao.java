package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.pojo.Activity;

public interface IActivityDao {
    int deleteByPrimaryKey(Integer contentId);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer contentId);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKeyWithBLOBs(Activity record);

    int updateByPrimaryKey(Activity record);
    /**
     * 分页查询 列表
     * @param map
     * @param pageBounds
     * @return
     */
    PageList<Activity> selectPageList(Map<String,Object> map,PageBounds pageBounds);
    /**
     * 审核发布，修改状态
     * @param ids
     * @return
     */
    int updateState(@Param("ids")List<Integer> ids);
    
    /**
     * 修改显示、隐藏状态
     * @param ids
     * @param value
     */
    void updateShowStatus(@Param("ids")List<Integer> ids, @Param("value")int value);
    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<Integer> ids);
}