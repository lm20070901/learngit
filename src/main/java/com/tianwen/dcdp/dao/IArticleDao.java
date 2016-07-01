package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianwen.dcdp.pojo.Article;

public interface IArticleDao {
    int deleteByPrimaryKey(Integer contentId);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer contentId);

    int updateByPrimaryKeySelective(Article record);

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
	List<Article> selectPageList(Map<String, Object> whereMap);

	/**
	 * 批量更新资讯状态
	 * @param idList
	 * @param value
	 */
	void updateState(@Param("ids")List<Integer> idList, @Param("value")int value);

	/**
	 * 批量删除资讯
	 * @param articleIds
	 * @return
	 */
	void batchDeleteArticle(List<Integer> articleIds);

	/**
	 * 获取热门资讯列表
	 * @return
	 */
	List<Article> getHotNewsList(Map<String, Object> whereMap);

	/**
	 * 显示/隐藏资讯
	 * @param articleIds
	 */
	void updateVisibility(List<Integer> articleIds);
	/**
	 * 根据类型查找所有资讯
	 * @param contentType
	 * @return
	 */
	List<Article> selectAll();
	/**
	 * 根据ids获取资讯list
	 * @param articleIds
	 * @return
	 */
	List<Article> selectArticleByIds(List<Integer> articleIds);
}