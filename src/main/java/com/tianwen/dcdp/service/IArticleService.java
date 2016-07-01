package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Article;
import com.tianwen.dcdp.pojo.ArticleComment;
import com.tianwen.dcdp.pojo.HotNews;
import com.tianwen.dcdp.pojo.NewsCategory;

public interface IArticleService {

	/**
	 * 获取资讯记录总数
	 * @param whereMap
	 * @return
	 */
	int getTotalCount(Map<String, Object> whereMap);

	/**
	 * 分页获取资讯列表
	 * @param whereMap
	 * @return
	 */
	List<Article> getPageList(Map<String, Object> whereMap);

	List<NewsCategory> getNewsCategory();

	/**
	 * 修改资讯状态
	 * @param value
	 */
	void updateArticleState(List<Integer> ids, int value);
	
	/**
	 * 显示/隐藏资讯
	 * @param articleIds
	 */
	void modifyArticleVisibility(List<Integer> articleIds);

	/**
	 * 新增资讯
	 * @param article
	 */
	void addArticle(Article article);

	/**
	 * 根据资讯ID获取资讯详情
	 * @param parseInt
	 * @return
	 */
	Article getArticleById(int articleId);

	/**
	 * 根据资讯ID，批量删除资讯
	 * @param ids
	 */
	void batchDeleteArticles(List<Integer> ids);

	/**
	 * 编辑资讯信息
	 * @param article
	 */
	void editArticle(Article article);

	
	/**
	 * 获取资讯评论记录总数
	 * @param whereMap
	 * @return
	 */
	int getArticleCommentTotalCount(Map<String, Object> whereMap);

	/**
	 * 分页获取资讯评论
	 * @param whereMap
	 * @return
	 */
	List<ArticleComment> getCommentPageList(Map<String, Object> whereMap);

	/**
	 * 批量删除资讯评论
	 * @param ids
	 */
	void deleteArticleCommentsByIds(List<Integer> ids);

	/**
	 * 修改资讯评论显示状态
	 * @param ids
	 */
	void modifyArticleCommentVisibility(List<Integer> ids);
	
	/**
	 * 修改资讯信息
	 * @param article
	 * @return
	 */
	int updateArticle(Article article);

	/**
	 * 根据一级目录获取资讯分类，携带计数字段
	 * pId : 一级目录ID
	 * @return
	 */
	List<NewsCategory> getNewsCategoryWithCountByPid(Integer pId);

	/**
	 * 新增资讯分类
	 * @param category
	 */
	Integer addNewsCategory(NewsCategory category);

	/**
	 * 根据分类ID获取分类详情
	 * @param categoryId
	 * @return
	 */
	NewsCategory getNewsCategoryById(Integer categoryId);

	/**
	 * 修改资讯分类信息
	 * @param category
	 */
	Integer updateNewsCategory(NewsCategory category);

	/**
	 * 批量删除资讯列表
	 * @param ids
	 */
	void batchDeleteNewsCategory(List<Integer> ids);

	/**
	 * 获取热门资讯列表
	 * @return
	 */
	List<Article> getHotArticles(Map<String, Object> whereMap);

	/**
	 * 修改热门资讯排序
	 * @param hotNews
	 */
	Integer modifyHotNews(HotNews hotNews);

	/**
	 * 批量删除热门资讯
	 * @param ids
	 */
	void batchDeleteHotNews(List<Integer> ids);

	/**
	 * 根据Id查询热门资讯信息
	 * @param articleId
	 * @return
	 */
	HotNews getHotNewsWithTitle(Integer articleId);
	/**
	 */
	ArticleComment getCommentById(Integer id);
	
	/**
	 * 创建检索doc
	 * @param contentType
	 * @return
	 */
	void indexUtil(int contentType,String indexDir);
	/**
	 * 全文检索
	 * @param indexDir
	 * @param property
	 * @param queryKey
	 * @param startIndex
	 * @param endIndex
	 * @param list   引用类型，返回list值
	 * @return
	 */
	Map<String,Object> searchUtil(String indexDir,String[] property,
				String[] queryKey,int startIndex,int endIndex,Integer contentType);
}
