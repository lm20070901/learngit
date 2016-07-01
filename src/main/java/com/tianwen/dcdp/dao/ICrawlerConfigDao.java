package com.tianwen.dcdp.dao;

import java.util.List;

import com.tianwen.dcdp.pojo.CrawlerConfig;

public interface ICrawlerConfigDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CrawlerConfig record);

    CrawlerConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CrawlerConfig record);

	List<CrawlerConfig> selectBySiteId(Long id);

	List<CrawlerConfig> selectBysiteIdWithModelInfo(Long wId);
}