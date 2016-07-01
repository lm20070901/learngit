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
 * @description: 视频讲座
 * @author yinz
 * 2016-6-13
 */
@Controller
@RequestMapping("/article")
public class VideoArticleController {

	@Autowired
	private IArticleService articleService;
	
	//视频讲座列表
	@RequiresPermissions("article:video:list")
	@RequestMapping("/videoArticleList")
	public String videoArticleList(
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
		//whereMap.put("contentType", ArticleType.VIDEO_LECTURES.getValue());
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
		return "article_v/articleList";
	}
	
	/**
	 * 跳转到新增讲座视频界面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("article:video:toAdd")
	@RequestMapping("/toCreateVideoArticlePage")
	public String toCreateVideoArticlePage(HttpServletRequest request, Model model) {
		List<NewsCategory> cates = articleService.getNewsCategory();
		model.addAttribute("cates", cates);
		//model.addAttribute("contentType", ArticleType.VIDEO_LECTURES.getValue());
		return "article/articleAdd";
	}
	
	/**
	 * 新增视频讲座
	 * @return
	 */
	@RequiresPermissions("article:video:add")
	@RequestMapping("/addVideoArticle")
	@ResponseBody
	public Map<String, Object> addVideoArticle(
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
		//article.setContentType((byte) ArticleType.VIDEO_LECTURES.getValue());
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

		LogUtils.log(LogOperType.CREATE, "新增视频讲座 : < " + article.getContentId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到讲座视频编辑界面
	 * @param request
	 * @param model
	 * @param articleId
	 * @param contentType
	 * @return
	 */
	@RequiresPermissions("article:video:toEdit")
	@RequestMapping("/toEditVideoArticlePage")
	public String toEditVideoArticlePage(
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
		//model.addAttribute("contentType", ArticleType.VIDEO_LECTURES.getValue());
		
		return "article/articleEdit";
	}
	
	/**
	 * 编辑视频讲座
	 * @return
	 */
	@RequiresPermissions("article:video:edit")
	@RequestMapping("/editVideoArticle")
	@ResponseBody
	public Map<String, Object> editVideoArticle(
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

		LogUtils.log(LogOperType.UPDATE, "修改视频讲座 : < " + article.getContentId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 删除资讯
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("article:video:delete")
	@RequestMapping("/deleteVideoArticle")
	@ResponseBody
	public Map<String, Object> deleteVideoArticle(HttpServletRequest request,
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
		
		LogUtils.log(LogOperType.DELETE, "删除视频讲座 : < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 审核发布视频讲座
	 * @return
	 */
	@RequiresPermissions("article:video:audit")
	@RequestMapping("/publishVideoArticle")
	@ResponseBody
	public Map<String, Object> publishVideoArticle(HttpServletRequest request,
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

		LogUtils.log(LogOperType.AUDIT, "审核发布视频讲座 : <" + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 显示/隐藏视频讲座
	 * @return
	 */
	@RequiresPermissions("article:video:change")
	@RequestMapping("/modifyVideoArticleVisibility")
	@ResponseBody
	public Map<String, Object> modifyVideoArticleVisibility(HttpServletRequest request,
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
		
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "隐藏/显示视频讲座 : <" + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 视频讲座评论列表
	 * @return
	 */
	@RequiresPermissions("article:video:commentList")
	@RequestMapping("/videoArticleCommentListByAid/{articleId}")
	public String videoArticleCommentListByAid(
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