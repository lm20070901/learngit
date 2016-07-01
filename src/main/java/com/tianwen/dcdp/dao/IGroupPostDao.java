package com.tianwen.dcdp.dao;

import com.tianwen.dcdp.pojo.GroupPost;
import java.util.*;

import org.apache.ibatis.annotations.Param;

public interface IGroupPostDao {
    int deleteByPrimaryKey(Integer postId);

    int insert(GroupPost record);

    int insertSelective(GroupPost record);

    GroupPost selectByPrimaryKey(Integer postId);

    int updateByPrimaryKeySelective(GroupPost record);

    int updateByPrimaryKeyWithBLOBs(GroupPost record);

    int updateByPrimaryKey(GroupPost record);
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
    
}