package com.tianwen.dcdp.service;

import java.util.List;

import com.tianwen.dcdp.pojo.CrawlerConfig;
import com.tianwen.dcdp.pojo.CrawlerModel;
import com.tianwen.dcdp.pojo.CrawlerWebsite;

public interface ICrawlerService {

	List<CrawlerModel> getModels();

	Integer addModel(CrawlerModel craw);

	Integer deleteModelById(Long id);

	CrawlerModel getModelById(Long id);

	Integer updateModel(CrawlerModel cra);

	List<CrawlerWebsite> getWebsitesByModelId(Long id);

	Integer addWebsite(CrawlerWebsite website);

	CrawlerWebsite getWebsiteById(Long id);

	Integer updateWebsite(CrawlerWebsite website);

	Integer deleteWebsiteById(Long id);

	Integer updateConfig(CrawlerConfig config);

	Integer addConfig(CrawlerConfig config);

	List<CrawlerConfig> getConfigBySiteId(Long id);

	void exeCrawler(Long wId);

}
