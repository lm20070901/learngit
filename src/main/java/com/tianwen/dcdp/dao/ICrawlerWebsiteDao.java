package com.tianwen.dcdp.dao;

import java.util.List;

import com.tianwen.dcdp.pojo.CrawlerWebsite;

public interface ICrawlerWebsiteDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CrawlerWebsite record);

    CrawlerWebsite selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CrawlerWebsite record);

	List<CrawlerWebsite> selectByModelId(Long id);
}