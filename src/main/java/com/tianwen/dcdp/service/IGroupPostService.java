package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianwen.dcdp.pojo.Article;
import com.tianwen.dcdp.pojo.GroupPost;
import com.tianwen.dcdp.pojo.Groups;

public interface IGroupPostService {
	 /**
     * 根据条件列表分页查询
     * @param whereMap
     * @return
     */
    List<GroupPost> selectList(Map<String,Object> whereMap);
    /**
     * 列表个数
     * @param whereMap
     * @return
     */
    int selectCountList(Map<String,Object> whereMap);
    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<Integer> ids);
    /**
     * 修改显示、隐藏状态
     * @param ids
     * @param value
     */
    void updateState(@Param("ids")List<Integer> ids, @Param("value")int value);
    /**
     * 根据ID获取详情
     * @param id
     * @return
     */
    GroupPost selectById(Integer id);
}
