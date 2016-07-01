package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.ArticleComment;

public interface IArticleCommentDao {
    int deleteByPrimaryKey(Integer commentId);

    int insertSelective(ArticleComment record);

    ArticleComment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(ArticleComment record);

    /**
     * 获取评论记录总数
     * @param whereMap
     * @return
     */
	int getTotalCount(Map<String, Object> whereMap);

	/**
	 * 分页获取资讯评论
	 * @param whereMap
	 * @return
	 */
	List<ArticleComment> selectPageList(Map<String, Object> whereMap);

	/**
	 * 批量删除资讯评论
	 * @param commIds
	 */
	void deleteByIds(List<Integer> commIds);

	/**
	 * 隐藏/显示资讯评论
	 * @param commIds
	 */
	void modifyVisibility(List<Integer> commIds);
}