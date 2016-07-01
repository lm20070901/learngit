package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianwen.dcdp.pojo.ArticleGrab;

public interface IArticleGrabDao {
    int deleteByPrimaryKey(Integer contentId);

    int insert(ArticleGrab record);

    int insertSelective(ArticleGrab record);

    ArticleGrab selectByPrimaryKey(Integer contentId);

    int updateByPrimaryKeySelective(ArticleGrab record);

    int updateByPrimaryKeyWithBLOBs(ArticleGrab record);

    int updateByPrimaryKey(ArticleGrab record);
    /**
     * 获取总记录数
     * @param whereMap
     * @return
     */
	int selectTotalCount(Map<String, Object> whereMap);

	/**
	 * 分页获取资讯列表
	 * @param whereMap
	 * @return
	 */
	List<ArticleGrab> selectPageList(Map<String, Object> whereMap);

	/**
	 * 显示/隐藏
	 * @param idList
	 * @param value
	 */
	void updateShowStatus(@Param("ids")List<Integer> idList, @Param("value")int value);
	
	/**
	 * 是否发布状态
	 * @param idList
	 * @param value
	 */
	void updateState(@Param("ids")List<Integer> idList, @Param("value")int value);

	/**
	 * 批量删除资讯
	 * @param articleIds
	 * @return
	 */
	void batchDelete(List<Integer> articleIds);
}