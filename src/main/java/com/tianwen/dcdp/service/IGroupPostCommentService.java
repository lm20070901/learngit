package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianwen.dcdp.pojo.GroupPostComment;

public interface IGroupPostCommentService {
	 /**
     * 根据条件列表分页查询
     * @param whereMap
     * @return
     */
    List<GroupPostComment> selectList(Map<String,Object> whereMap);
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
     * 根据文章ID分页查询
     * @param whereMap
     * @return
     */
    List<GroupPostComment> listByPostId(Map<String,Object> whereMap);
    /**
     * 列表个数
     * @param whereMap
     * @return
     */
    int countByPostId(Map<String,Object> whereMap);
    /**
     * 根据ID获取详情
     * @param id
     * @return
     */
    GroupPostComment selectById(Integer id);
}
