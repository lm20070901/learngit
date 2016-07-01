package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Content;

public interface IContentDao {
    int deleteByPrimaryKey(Integer contentId);

    int insert(Content record);

    int insertSelective(Content record);

    Content selectByPrimaryKey(Integer contentId);

    int updateByPrimaryKeySelective(Content record);

    int updateByPrimaryKeyWithBLOBs(Content record);

    int updateByPrimaryKey(Content record);

    /**
     * 查询记录总数
     * @param whereMap
     * @return
     */
	int selectTotalCount(Map<String, Object> whereMap);

	/**
	 * 分页获取动态内容
	 * @param whereMap
	 * @return
	 */
	List<Content> selectByPage(Map<String, Object> whereMap);
	/**
	 * 批量删除
	 * @param idList
	 */
	void deleteContentByIds(List<Integer> idList);
	/**
	 * 批量隐藏动态
	 * @param idList
	 */
	void updateContentsStatus(List<Integer> idList);
	/**
	 * 查找所有
	 * @return
	 */
	List<Content> selectAll();
	/**
	 * 根据ids获取list
	 * @param articleIds
	 * @return
	 */
	List<Content> selectByIds(List<Integer> ids);
}