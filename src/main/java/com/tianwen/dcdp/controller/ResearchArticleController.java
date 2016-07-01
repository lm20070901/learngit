package com.tianwen.dcdp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.DateUtils;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.ArticleState;
import com.tianwen.dcdp.common.ParamEnum.ArticleType;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Article;
import com.tianwen.dcdp.pojo.ArticleComment;
import com.tianwen.dcdp.pojo.NewsCategory;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IArticleService;

/**
 * 
 * @description: 研究报告 
 * @author yinz
 * 2016-6-13
 */
@RequestMapping("/article")
@Controller
public class ResearchArticleController {


	@Autowired
	private IArticleService articleService;
	
	//研究报告列表
	@RequiresPermissions("article:research:list")
	@RequestMapping("/researchArticleList")
	public String researchArticleList(
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "category", required = false) Integer category,
			@RequestParam(value = "origin", required = false) String origin,
			@RequestParam(value = "state", required = false) Integer state,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		List<NewsCategory> cates = articleService.getNewsCategory();

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("title", title);
		whereMap.put("category", category);
		whereMap.put("origin", origin);
		whereMap.put("state", state);
		//whereMap.put("contentType", ArticleType.RESEARCH_REPORT.getValue());
		whereMap.put(
				"startDate",
				General.isEmpty(startDate) ? null : DateUtils
						.strDateToLong(startDate));
		whereMap.put(
				"endDate",
				General.isEmpty(endDate) ? null : DateUtils
						.strDateToLong(endDate));
		// 获取记录总数
		int totalCount = articleService.getTotalCount(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Article> data = articleService.getPageList(whereMap);
		
		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		model.addAttribute("cates", cates);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);

		model.addAllAttributes(whereMap);

		model.addAttribute("articleState", ParamEnum.ArticleState.getAllState());
		return "article_r/articleList";
	}
	
	/**
	 * 跳转到新增研究报告界面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("article:research:toAdd")
	@RequestMapping("/toCreateResearchArticlePage")
	public String toCreateResearchArticlePage(HttpServletRequest request, Model model) {
		List<NewsCategory> cates = articleService.getNewsCategory();
		model.addAttribute("cates", cates);
		//model.addAttribute("contentType", ArticleType.RESEARCH_REPORT.getValue());
		return "article/articleAdd";
	}
	
	/**
	 * 新增研究报告
	 * @return
	 */
	@RequiresPermissions("article:research:add")
	@RequestMapping("/addResearchArticle")
	@ResponseBody
	public Map<String, Object> addResearchArticle(
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "categoryType", required = false) Integer categoryType,
			@RequestParam(value = "publishDate", required = false) String publishDate,
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "introduce", required = false) String introduce,
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "author", required = false) String author,
			@RequestParam(value = "readTimes", required = false) Integer readTimes,
			@RequestParam(value = "orderNum", required = false) Integer orderNum,
			@RequestParam(value = "keywords", required = false) String keywords,
			@RequestParam(value = "devType", required = false) Byte devType,
			@RequestParam(value = "isComment", required = false) Byte isComment,
			@RequestParam(value = "isTop", required = false) Byte isTop,
			@RequestParam(value = "picUrl", required = false) String picUrl,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "mediaTime", required = false) Integer mediaTime,
			@RequestParam(value = "mediaUrl", required = false) String mediaUrl,
			HttpServletRequest request) {
		ResponseResult result = new ResponseResult();

		Article article = new Article();
		article.setTitle(title);
		article.setCategoryId(categoryType);
		article.setPublishTime(General.isEmpty(publishDate) ? System
				.currentTimeMillis() : DateUtils.strDateToLong(publishDate,
				"yyyy-MM-dd hh:mm:ss"));
		article.setUpdateTime(General.isEmpty(publishDate) ? System
				.currentTimeMillis() : DateUtils.strDateToLong(publishDate,
				"yyyy-MM-dd hh:mm:ss"));
		article.setUrl(url);
		article.setSource(source);
		article.setIntroduce(introduce);
		article.setAuthor(author);
		article.setReadTimes(readTimes);
		article.setOrderNum(orderNum);
		article.setKeywords(keywords);
		article.setIsComment(isComment);
		article.setIsTop(isTop);
		article.setContent(content);
		article.setDevType(devType);
		//article.setContentType((byte) ArticleType.RESEARCH_REPORT.getValue());
		article.setPicUrl(picUrl);
		article.setMediaTime(mediaTime);
		article.setFileUrl(mediaUrl);
		try {
			// 存储资讯类容
			articleService.addArticle(article);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}

		LogUtils.log(LogOperType.CREATE, "新增研究报告 : < " + article.getContentId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到研究报告编辑界面
	 * @param request
	 * @param model
	 * @param articleId
	 * @param contentType
	 * @return
	 */
	@RequiresPermissions("article:research:toEdit")
	@RequestMapping("/toEditResearchArticlePage")
	public String toEditResearchArticlePage(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "id", required = false) Integer articleId) {

		Article article = articleService.getArticleById(articleId);
		List<NewsCategory> cates = articleService.getNewsCategory();
		model.addAttribute("cates", cates);

		model.addAttribute("article", article);
		if (article.getPublishTime() != null) {
			article.setStrPublishTime(DateUtils.longDateToStr(
					article.getPublishTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		//model.addAttribute("contentType", ArticleType.RESEARCH_REPORT.getValue());
		
		return "article/articleEdit";
	}
	
	/**
	 * 编辑研究报告
	 * @return
	 */
	@RequiresPermissions("article:research:edit")
	@RequestMapping("/editResearchArticle")
	@ResponseBody
	public Map<String, Object> editResearchArticle(
			@RequestParam(value = "contentId", required = false) Integer contentId,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "categoryType", required = false) Integer categoryType,
			@RequestParam(value = "publishDate", required = false) String publishDate,
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "introduce", required = false) String introduce,
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "author", required = false) String author,
			@RequestParam(value = "readTimes", required = false) Integer readTimes,
			@RequestParam(value = "orderNum", required = false) Integer orderNum,
			@RequestParam(value = "keywords", required = false) String keywords,
			@RequestParam(value = "devType", required = false) Byte devType,
			@RequestParam(value = "isComment", required = false) Byte isComment,
			@RequestParam(value = "isTop", required = false) Byte isTop,
			@RequestParam(value = "picUrl", required = false) String picUrl,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "mediaTime", required = false) Integer mediaTime,
			@RequestParam(value = "mediaUrl", required = false) String mediaUrl,
			HttpServletRequest request) {
		ResponseResult result = new ResponseResult();

		Article article = new Article();
		article.setContentId(contentId);
		article.setTitle(title);
		article.setCategoryId(categoryType);
		article.setPublishTime(General.isEmpty(publishDate) ? System
				.currentTimeMillis() : DateUtils.strDateToLong(publishDate,
				"yyyy-MM-dd hh:mm:ss"));
		article.setUpdateTime(System.currentTimeMillis());
		article.setUrl(url);
		article.setSource(source);
		article.setIntroduce(introduce);
		article.setAuthor(author);
		article.setReadTimes(readTimes);
		article.setOrderNum(orderNum);
		article.setKeywords(keywords);
		article.setIsComment(isComment);
		article.setIsTop(isTop);
		article.setContent(content);
		article.setDevType(devType);
		article.setPicUrl(picUrl);
		article.setMediaTime(mediaTime);
		article.setFileUrl(mediaUrl);
		try {
			// 更新资讯类容
			articleService.editArticle(article);
		} catch (Exception e) {
			System.out.println(e);
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}

		LogUtils.log(LogOperType.UPDATE, "修改研究报告: < " + article.getContentId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 删除资讯
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("article:research:delete")
	@RequestMapping("/deleteResearchArticle")
	@ResponseBody
	public Map<String, Object> deleteResearchArticle(HttpServletRequest request,
			@RequestParam(value = "contentId", required = false) String ids) {
		ResponseResult result = new ResponseResult();
		List<Integer> articleIds = new ArrayList<Integer>();
		if (General.isNotEmpty(ids)) {
			String[] stArr = ids.split(",");
			for (String st : stArr) {
				articleIds.add(Integer.parseInt(st));
			}
		}

		try {
			articleService.batchDeleteArticles(articleIds);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除研究报告 : < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 审核发布研究报告
	 * @return
	 */
	@RequiresPermissions("article:research:audit")
	@RequestMapping("/publishResearchArticle")
	@ResponseBody
	public Map<String, Object> publishResearchArticle(HttpServletRequest request,
			@RequestParam(value = "contentId", required = false) String ids) {
		ResponseResult result = new ResponseResult();
		List<Integer> articleIds = new ArrayList<Integer>();
		if (General.isNotEmpty(ids)) {
			String[] stArr = ids.split(",");
			for (String st : stArr) {
				articleIds.add(Integer.parseInt(st));
			}
		}
		try {
			articleService.updateArticleState(articleIds,
					ArticleState.PUBLISHED.getValue());
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}

		LogUtils.log(LogOperType.AUDIT, "审核发布研究报告 : <" + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 显示/隐藏研究报告
	 * @return
	 */
	@RequiresPermissions("article:research:change")
	@RequestMapping("/modifyResearchArticleVisibility")
	@ResponseBody
	public Map<String, Object> modifyResearchArticleVisibility(HttpServletRequest request,
			@RequestParam(value = "contentId", required = false) String ids) {
		ResponseResult result = new ResponseResult();
		List<Integer> articleIds = new ArrayList<Integer>();
		if (General.isNotEmpty(ids)) {
			String[] stArr = ids.split(",");
			for (String st : stArr) {
				articleIds.add(Integer.parseInt(st));
			}
		}

		try {
			articleService.modifyArticleVisibility(articleIds);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "隐藏/显示研究报告 : <" + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 研究报告评论列表
	 * @return
	 */
	@RequiresPermissions("article:research:commentList")
	@RequestMapping("/researchArticleCommentListByAid/{articleId}")
	public String researchArticleCommentListByAid(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@PathVariable(value="articleId") Integer articleId) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("articleId", articleId);
		// 获取记录总数
		int totalCount = articleService.getArticleCommentTotalCount(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());

		List<ArticleComment> data = articleService.getCommentPageList(whereMap);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		model.addAllAttributes(whereMap);
		model.addAttribute("articleId", articleId);

		return "article/articleCommentListByAid";
	}

}
