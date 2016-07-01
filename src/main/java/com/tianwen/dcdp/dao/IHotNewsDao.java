package com.tianwen.dcdp.dao;

import java.util.List;

import com.tianwen.dcdp.pojo.HotNews;

public interface IHotNewsDao {

    int insertSelective(HotNews record);

	Integer updateByPrimaryKeySelective(HotNews hotNews);

	/**
	 * 根据ID批量删除
	 * @param newsIds
	 */
	void batchDeleteHotNews(List<Integer> newsIds);

	/**
	 * 根据ID查询热门资讯信息，并携带资讯title
	 * @param parseInt
	 * @return
	 */
	HotNews selectHotNewsWithTitleById(Integer contentId);
}