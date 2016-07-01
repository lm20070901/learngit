package com.tianwen.dcdp.service.impl;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.collector.pojo.CommonCrawler;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.dao.ICrawlerConfigDao;
import com.tianwen.dcdp.dao.ICrawlerModelDao;
import com.tianwen.dcdp.dao.ICrawlerWebsiteDao;
import com.tianwen.dcdp.pojo.CrawlerConfig;
import com.tianwen.dcdp.pojo.CrawlerModel;
import com.tianwen.dcdp.pojo.CrawlerWebsite;
import com.tianwen.dcdp.service.ICrawlerService;

@Service
public class CrawlerServiceImpl implements ICrawlerService {

	@Autowired
	private ICrawlerModelDao modelDao;
	
	@Autowired
	private ICrawlerWebsiteDao siteDao;
	
	@Autowired
	private ICrawlerConfigDao configDao;
	
	@Override
	public List<CrawlerModel> getModels() {
		return modelDao.selectAll();
	}

	@Override
	public Integer addModel(CrawlerModel craw) {
		return modelDao.insertSelective(craw);
	}

	@Override
	public Integer deleteModelById(Long id) {
		return modelDao.deleteByPrimaryKey(id);
	}

	@Override
	public CrawlerModel getModelById(Long id) {
		return modelDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer updateModel(CrawlerModel cra) {
		return modelDao.updateByPrimaryKeySelective(cra);
	}

	@Override
	public List<CrawlerWebsite> getWebsitesByModelId(Long id) {
		return siteDao.selectByModelId(id);
	}

	@Override
	public Integer addWebsite(CrawlerWebsite website) {
		return siteDao.insertSelective(website);
	}

	@Override
	public CrawlerWebsite getWebsiteById(Long id) {
		return siteDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer updateWebsite(CrawlerWebsite website) {
		return siteDao.updateByPrimaryKeySelective(website);
	}

	@Override
	public Integer deleteWebsiteById(Long id) {
		return siteDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer updateConfig(CrawlerConfig config) {
		return configDao.updateByPrimaryKeySelective(config);
	}

	@Override
	public Integer addConfig(CrawlerConfig config) {
		return configDao.insertSelective(config);
	}

	@Override
	public List<CrawlerConfig> getConfigBySiteId(Long id) {
		return configDao.selectBySiteId(id);
	}

	//执行制度爬取动作
	@Override
	public void exeCrawler(Long wId) {
		//根据网站ID，查询相关配置信息
		List<CrawlerConfig> confs = configDao.selectBysiteIdWithModelInfo(wId);
		if(General.isNotEmpty(confs)) {
			CrawlerConfig conf = confs.get(0);
			try {
				String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
				CommonCrawler clawler = this.executeCrawler(conf,nowTime);
			} catch(Exception e) {
				
			}
		}
	}

	/**
	 * 执行相关爬虫
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public CommonCrawler executeCrawler(CrawlerConfig config,String dateStr) throws Exception {
		Set errorInfo = null;
		Class<?> clazz = null;  
		 try{  
			 clazz = Class.forName(config.getModelClassPath());
		 } catch (Exception e) {  
			 // TODO: handle exception  
		 } 
		 Constructor<?> cons[] = clazz.getConstructors();
		 CommonCrawler crawler = null;
		 try {
			 crawler = (CommonCrawler) cons[0].newInstance(config,dateStr);
			 crawler.doCollect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return crawler;
	}
}
