package com.tianwen.dcdp.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.common.ArticleIndexUtil;
import com.tianwen.dcdp.common.ArticleSearchUtil;
import com.tianwen.dcdp.dao.IArticleCommentDao;
import com.tianwen.dcdp.dao.IArticleDao;
import com.tianwen.dcdp.dao.IContentDao;
import com.tianwen.dcdp.dao.IGroupsDao;
import com.tianwen.dcdp.dao.IHotNewsDao;
import com.tianwen.dcdp.dao.INewsCategoryDao;
import com.tianwen.dcdp.dao.IQuestionDao;
import com.tianwen.dcdp.pojo.Article;
import com.tianwen.dcdp.pojo.ArticleComment;
import com.tianwen.dcdp.pojo.Content;
import com.tianwen.dcdp.pojo.Groups;
import com.tianwen.dcdp.pojo.HotNews;
import com.tianwen.dcdp.pojo.NewsCategory;
import com.tianwen.dcdp.pojo.Question;
import com.tianwen.dcdp.service.IArticleService;

@Service
public class ArticleServiceImpl implements IArticleService {

	@Autowired
	private IArticleDao articleMapper;
	
	@Autowired
	private INewsCategoryDao categoryMapper;
	
	@Autowired
	private IArticleCommentDao articleCommontMapper;
	
	@Autowired
	private IHotNewsDao hotNewsMapper;
	@Autowired
	private IGroupsDao groupsMapper;
	@Autowired
	private IContentDao contentMapper;
	@Autowired
	private IQuestionDao questionMapper;
	
	
	@Override
	public int getTotalCount(Map<String, Object> whereMap) {
		return articleMapper.selectTotalCount(whereMap);
	}

	@Override
	public List<Article> getPageList(Map<String, Object> whereMap) {
		return articleMapper.selectPageList(whereMap);
	}

	@Override
	public List<NewsCategory> getNewsCategory() {
		return categoryMapper.selectAll();
	}

	@Override
	public void updateArticleState(List<Integer> ids, int value) {
		
		articleMapper.updateState(ids, value);
	}
	

	@Override
	public void modifyArticleVisibility(List<Integer> articleIds) {
		articleMapper.updateVisibility(articleIds);
	}

	@Override
	public void addArticle(Article article) {
		articleMapper.insertSelective(article);
	}

	@Override
	public Article getArticleById(int articleId) {
		return articleMapper.selectByPrimaryKey(articleId);
	}

	@Override
	public void batchDeleteArticles(List<Integer> ids) {
		articleMapper.batchDeleteArticle(ids);
	}

	@Override
	public void editArticle(Article article) {
		articleMapper.updateByPrimaryKeySelective(article);
	}

	@Override
	public int getArticleCommentTotalCount(Map<String, Object> whereMap) {
		return articleCommontMapper.getTotalCount(whereMap);
	}

	@Override
	public List<ArticleComment> getCommentPageList(Map<String, Object> whereMap) {
		return articleCommontMapper.selectPageList(whereMap);
	}

	@Override
	public void deleteArticleCommentsByIds(List<Integer> ids) {
		
		articleCommontMapper.deleteByIds(ids);
	}

	@Override
	public void modifyArticleCommentVisibility(List<Integer> ids) {
		articleCommontMapper.modifyVisibility(ids);
	}

	@Override
	public int updateArticle(Article article) {
		return articleMapper.updateByPrimaryKeySelective(article);
	}

	@Override
	public List<NewsCategory> getNewsCategoryWithCountByPid(Integer pId) {
		
		return categoryMapper.selectAllWithCountByPid(pId);
	}

	@Override
	public Integer addNewsCategory(NewsCategory category) {
		return categoryMapper.insertSelective(category);
	}

	@Override
	public NewsCategory getNewsCategoryById(Integer categoryId) {
		return categoryMapper.selectByPrimaryKey(categoryId);
	}

	@Override
	public Integer updateNewsCategory(NewsCategory category) {
		return categoryMapper.updateByPrimaryKeySelective(category);
	}

	@Override
	public void batchDeleteNewsCategory(List<Integer> ids) {
		categoryMapper.batchDeleteByPrimaryKey(ids);
	}

	@Override
	public List<Article> getHotArticles(Map<String, Object> whereMap) {
		return articleMapper.getHotNewsList(whereMap);
	}

	@Override
	public Integer modifyHotNews(HotNews hotNews) {
		return hotNewsMapper.updateByPrimaryKeySelective(hotNews);
	}

	@Override
	public void batchDeleteHotNews(List<Integer> ids) {
		hotNewsMapper.batchDeleteHotNews(ids);
	}

	@Override
	public HotNews getHotNewsWithTitle(Integer articleId) {
		return hotNewsMapper.selectHotNewsWithTitleById(articleId);
	}

	@Override
	public ArticleComment getCommentById(Integer commentId) {
		return articleCommontMapper.selectByPrimaryKey(commentId);
	}

	/**
	 * 创建索引
	 */
	public void indexUtil(int contentType,String indexDir){
		ArticleIndexUtil.deleteDir(new File(indexDir));//先删除该文件夹下所有文件
		Map<String,Object> map = new HashMap<String, Object>();
		if(contentType == 1){
			List<Article> list = articleMapper.selectAll();
			for(Article article : list){
				map.put("contentId", article.getContentId()); //contentId为必传
				map.put("content", article.getContent());
				map.put("title", article.getTitle());
				ArticleIndexUtil.createArticleIndex(indexDir, map);
			}
		}else if(contentType == 2){ //动态
			List<Content> list = contentMapper.selectAll();
			for(Content article : list){
				map.put("contentId", article.getContentId()); //contentId为必传
				map.put("content", article.getContentBody());
				ArticleIndexUtil.createArticleIndex(indexDir, map);
			}
		}else if(contentType == 3){ //问题
			List<Question> list = questionMapper.selectAll();
			for(Question article : list){
				map.put("contentId", article.getQuestionId()); //contentId为必传
				map.put("content", article.getContent());
				map.put("title", article.getTitle());
				ArticleIndexUtil.createArticleIndex(indexDir, map);
			}
		}else if(contentType == 4){ //小组列表
			List<Groups> list = groupsMapper.selectAll();
			for(Groups obj : list){
				map.put("contentId", obj.getGroupId()); //contentId为必传
				map.put("title", obj.getGroupName());
				ArticleIndexUtil.createArticleIndex(indexDir, map);
			}
		}
		
	}
	/**
	 * 
	 */
	public Map<String,Object> searchUtil(String indexDir,String[] property,
			String[] queryKey,int startIndex,int endIndex,Integer contentType){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> maps = new HashMap<String,Object>();
		String[] key = getKeyByType(contentType);  
		int arcs =  ArticleSearchUtil.searchByKey(indexDir, property, queryKey, startIndex, endIndex,list,key);
		List<Integer> ids = new ArrayList<Integer>();
		for(Map<String,Object> ar :list){
			ids.add(Integer.parseInt(ar.get("contentId").toString()));
		}
		getListByType(contentType, ids, list, maps);
		maps.put("size", arcs);
		return maps;
	}

	/**
	 * doc中查询出的结果list字段标识  //contentId为必传,标红字段放第一位
	 * @param contentType 类型
	 * @return
	 */
	public String[] getKeyByType(Integer contentType){
		String[] key = null;
		if(contentType  == 1){
			key = new String[]{"title","contentId","content"}; 
		}else if(contentType == 2){ //动态
			key = new String[]{"content","contentId"};
		}else if(contentType == 3){ //问题
			key = new String[]{"title","contentId"};
		}else if(contentType == 4){ //小组列表
			key = new String[]{"title","contentId"};
		}
		return key;
	}
	/**
	 *  将得到的list重新组合，取出ids重新查询出结果
	 * @param contentType  类型
	 * @param ids  对象id集合
	 * @param list  索引得出的集合 
	 * @param maps  根据索引得到的ids重新查询的结果  标红
	 */
	public void getListByType(Integer contentType,List<Integer> ids,List<Map<String, Object>> list,Map<String,Object> maps){
		if(contentType == 1){ // 资讯
			List<Article> data = null ;
			if(ids!=null && ids.size()>0){
				data = articleMapper.selectArticleByIds(ids);
				for(int i=0;i<data.size();i++){
					Article ar = data.get(i);
					ar.setTitle(list.get(i).get("title").toString());
					data.set(i, ar);
				}
			}
			maps.put("list", data);
		}else if(contentType == 2){ //动态
			List<Content> data = null ;
			if(ids!=null && ids.size()>0){
				data = contentMapper.selectByIds(ids);
				for(int i=0;i<data.size();i++){
					Content ar = data.get(i);
					ar.setContentBody(list.get(i).get("content").toString());
					data.set(i, ar);
				}
			}
			maps.put("list", data);
		}else if(contentType == 3){ //问题
			List<Question> data = null ;
			if(ids!=null && ids.size()>0){
				data = questionMapper.selectByIds(ids);
				for(int i=0;i<data.size();i++){
					Question ar = data.get(i);
					ar.setTitle(list.get(i).get("title").toString());
					data.set(i, ar);
				}
			}
			maps.put("list", data);
		}else if(contentType == 4){ //小组列表
			List<Groups> data = null ;
			if(ids!=null && ids.size()>0){
				data = groupsMapper.selectByIds(ids); 
				for(int i=0;i<data.size();i++){
					Groups ar = data.get(i);
					ar.setGroupName(list.get(i).get("title").toString());
					data.set(i, ar);
				}
			}
			maps.put("list", data);
		}
	}

}
