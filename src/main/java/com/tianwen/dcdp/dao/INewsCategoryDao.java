package com.tianwen.dcdp.dao;

import java.util.List;

import com.tianwen.dcdp.pojo.NewsCategory;

public interface INewsCategoryDao {
    int deleteByPrimaryKey(Integer categoryId);

    int insertSelective(NewsCategory record);

    NewsCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(NewsCategory record);

    /**
     * 获取所有资讯分类
     * @return
     */
	List<NewsCategory> selectAll();

	/**
	 * 获取所有资讯分类，携带计数
	 * @return
	 */
	List<NewsCategory> selectAllWithCountByPid(Integer pId);

	/**
	 * 批量删除资讯分类
	 * @param cateIds
	 */
	void batchDeleteByPrimaryKey(List<Integer> cateIds);
}