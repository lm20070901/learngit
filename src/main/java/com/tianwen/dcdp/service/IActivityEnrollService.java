package com.tianwen.dcdp.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.pojo.ActivityEnroll;

public interface IActivityEnrollService {
    int deleteByPrimaryKey(Integer contentId);

    ActivityEnroll selectByPrimaryKey(Integer contentId);

    int updateByPrimaryKeySelective(ActivityEnroll record);

    /**
     * 分页查询 列表
     * @param map
     * @param pageBounds
     * @return
     */
    PageList<ActivityEnroll> selectPageList(Map<String,Object> map,PageBounds pageBounds);
    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<Integer> ids);
    
    void exportEnroll(Map<String, Object> whereMap,OutputStream out);
}