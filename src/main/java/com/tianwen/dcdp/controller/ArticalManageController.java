package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Article;
import com.tianwen.dcdp.pojo.ArticleComment;
import com.tianwen.dcdp.pojo.NewsCategory;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IArticleService;

@RequestMapping("/article")
@Controller
public class ArticalManageController {
	
	@Autowired
	private IArticleService articleService;

	@RequestMapping("/articleList")
	public String articleList(Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value="parentId", required=false)Integer parentId,
			@RequestParam(value="categoryId", required=false)Integer categoryId,
			@RequestParam(value="contentType", required=false)Integer contentType,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "category", required = false) Integer category,
			@RequestParam(value = "origin", required = false) String origin,
			@RequestParam(value = "state", required = false) Integer state,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {
		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;
		//一级栏目
		List<NewsCategory> cates = articleService.getNewsCategoryWithCountByPid(0);

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("title", title);
		whereMap.put("category", category);
		whereMap.put("origin", origin);
		whereMap.put("state", state);
		whereMap.put("contentType", contentType);
		whereMap.put("categoryId", categoryId);
		whereMap.put("parentId", parentId);
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

		Page page = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", page.getStart());
		whereMap.put("size", page.getPageSize());
		List<Article> data = articleService.getPageList(whereMap);
		
		model.addAttribute("page", page);
		model.addAttribute("data", data);
		//一级目录列表
		model.addAttribute("cates", cates);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);

		model.addAllAttributes(whereMap);
		
		//文章类型（文章、多图、焦点图、视频）
		model.addAttribute("articleType", ParamEnum.ArticleType.getMap());
		//文章状态（发布、待审核、未通过）
		model.addAttribute("articleState", ParamEnum.ArticleState.getAllState());
		if(null != parentId) {
			//二级栏目
			List<NewsCategory> childCates = articleService.getNewsCategoryWithCountByPid(parentId);
			model.addAttribute("childCates", childCates);
		}
		
		return "article/articleList";
	}
	
	/**
	 * 加载二级栏目
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/loadChildCategory")
	@ResponseBody
	public String loadChildCategory(@RequestParam(value="parentId", required=false)Integer parentId) {
		List<NewsCategory> datas = articleService.getNewsCategoryWithCountByPid(parentId);
		StringBuilder sb = new StringBuilder();
		if(null != datas) {
			for(NewsCategory item : datas) {
				sb.append("<option value='");
				sb.append(item.getCategoryId());
				sb.append("'>");
				sb.append(item.getCategoryName());
				sb.append("</option>");
			}
		}
		return sb.toString();
	}
	
	//跳转到新增资讯(焦点图，多图，视频，文章)界面
	@RequestMapping("/toCreateArticlePage")
	public String toCreateArticlePage(Model model,
			Article article
			/*@RequestParam(value="contentType", required=false)Integer contentType*/) {
		
		List<NewsCategory> cates = articleService.getNewsCategoryWithCountByPid(0);
		model.addAttribute("cates", cates);
		model.addAttribute("types", ParamEnum.ArticleType.getMap());
		model.addAttribute("data", article);
		if(null != article && null != article.getParentId()) {
			List<NewsCategory> secCates = articleService.getNewsCategoryWithCountByPid(article.getParentId());
			model.addAttribute("secCates", secCates);
		}
		if(null != article) {
			Byte contentType = article.getContentType();
			if(contentType != null) {
				if(contentType == ParamEnum.ArticleType.MULTIPLE_PICTURE.getValue()) {
					return "article/articleAdd_mp";
				} else if(contentType == ParamEnum.ArticleType.FOCUS_PICTURE.getValue()) {
					return "article/articleAdd_fp";
				} else if(contentType == ParamEnum.ArticleType.VIDEO.getValue()) {
					return "article/articleAdd_vd";
				}
			}
		}
		return "article/articleAdd";
	}
	
	@RequestMapping("/addArticle")
	@ResponseBody
	public Map<String, Object> addArticle(Article article) {
		ResponseResult result = new ResponseResult();
		if(null == article.getPublishTime()) {
			article.setPublishTime(System.currentTimeMillis());
		}
		if(null == article.getUpdateTime()) {
			article.setUpdateTime(System.currentTimeMillis());
		}
		try {
			// 存储资讯类容
			articleService.addArticle(article);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}

		LogUtils.log(LogOperType.CREATE, "新增资讯 : < " + article.getContentId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 显示、隐藏文章
	 * @param ids
	 * @return
	 */
	@RequestMapping("/modifyArticleVisibility")
	@ResponseBody
	public Map<String, Object> modifyArticleVisibility(@RequestParam(value="idList[]", required=false)List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		try {
			articleService.modifyArticleVisibility(ids);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "隐藏/显示文章 : <" + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 审核发布文章
	 * @return
	 */
	@RequestMapping("/publishArticle")
	@ResponseBody
	public Map<String, Object> publishArticle(@RequestParam(value="idList[]", required=false)List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		
		try {
			articleService.updateArticleState(ids,
					ArticleState.PUBLISHED.getValue());
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}

		LogUtils.log(LogOperType.AUDIT, "审核发布文章 : <" + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 删除文章
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteArticles")
	@ResponseBody
	public Map<String, Object> deleteArticles(@RequestParam(value="idList[]", required=false)List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		try {
			articleService.batchDeleteArticles(ids);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除资文章: < " + ids + " >");
		return result.returnSuccess();
	}
	
	//查看特定文章的评论
	@RequestMapping("/articleCommentListByAid/{articleId}")
	public String articleCommentListByAid(Model model,
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
