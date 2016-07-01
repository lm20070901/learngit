package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.ArticleGrab;

public interface IArticleGrabService {

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
	List<ArticleGrab> getPageList(Map<String, Object> whereMap);

	/**
	 * 显示/隐藏
	 * @param value
	 */
	void updateShowStatus(List<Integer> ids, int value);
	
	
	void updateState(List<Integer> ids, int value);
	/**
	 * 根据资讯ID获取资讯详情
	 * @param parseInt
	 * @return
	 */
	ArticleGrab getById(int articleId);

	/**
	 * 根据资讯ID，批量删除资讯
	 * @param ids
	 */
	void batchDelete(List<Integer> ids);

	/**
	 * 编辑资讯信息，且发布
	 * @param article
	 */
	void publish(ArticleGrab article,Integer flag);

	Integer addArticleGrab(ArticleGrab article);
}
