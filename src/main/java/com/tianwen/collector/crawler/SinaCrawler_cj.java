package com.tianwen.collector.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import com.tianwen.collector.pojo.CommonCrawler;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.dao.SpringContextHolder;
import com.tianwen.dcdp.pojo.ArticleGrab;
import com.tianwen.dcdp.pojo.CrawlerConfig;
import com.tianwen.dcdp.service.IArticleGrabService;

public class SinaCrawler_cj extends CommonCrawler<ArticleGrab> {
	
	public SinaCrawler_cj(CrawlerConfig config, String collectTime) {
		super(config, collectTime);
	}

	@Override
	public void visit(Page page, CrawlDatums crawlDatums) {
		String urlMatch = getField(page,"urlmatch");
		String urlseed = getField(page,"urlseed");
		System.out.println("---------------" + page.getUrl());
		ArticleGrab entity = null;
		if(urlMatch!=null&&!urlMatch.equals("")){
			if(page.getUrl().matches(urlMatch)){
				entity = this.createEntity(page);
			}
		}else{
			try {
				
				entity = this.createEntity(page);
			} catch(Exception e) {
				System.out.println("error------------------" + page.getUrl());
			}
		}
		if(urlseed!=null&&!urlseed.equals("")){
			if(page.getUrl().matches(urlseed)){
				batchAddSeed(page, crawlDatums);
			}
		}
		/*try {
			count(entity);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}*/
		if(entity!=null) 
			this.saveData(entity);
			
	}

	private ArticleGrab createEntity(Page page) {
		ArticleGrab article = new ArticleGrab();
		//标题
		String title = getField(page, "title");
		if(General.isEmpty(title)) {
			return null;
		}
		article.setTitle(title);
		//来源
		String source = getField(page, "source");
		article.setSource(source);
		//内容
		String content = getField(page, "content");
		article.setContent(content);
		
		article.setContentType((byte) 0);
		article.setGrabTime(System.currentTimeMillis());
		
		//TODO
		article.setUrl(page.getUrl());
		return article;
	}

	@Override
	public int saveData(ArticleGrab article) {
		IArticleGrabService service = SpringContextHolder.getBean("articleGrabServiceImpl");
		
		return service.addArticleGrab(article);
	}

}
