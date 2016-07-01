package com.tianwen.dcdp.dao;

import java.util.List;

import com.tianwen.dcdp.pojo.CrawlerModel;

public interface ICrawlerModelDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CrawlerModel record);

    CrawlerModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CrawlerModel record);

	List<CrawlerModel> selectAll();
}