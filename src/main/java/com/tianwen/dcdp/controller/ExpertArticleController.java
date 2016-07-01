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
 * @description: 专家观点 
 * @author yinz
 * 2016-6-13
 */
@RequestMapping("/article")
@Controller
public class ExpertArticleController {


	@Autowired
	private IArticleService articleService;
	
	//专家观点列表
	@RequiresPermissions("article:expert:list")
	@RequestMapping("/expertArticleList")
	public String expertArticleList(
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
		//whereMap.put("contentType", ArticleType.EXPERT_OPTION.getValue());
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
		return "article_e/articleList";
	}
	
	/**
	 * 删除资讯
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("article:expert:delete")
	@RequestMapping("/deleteExpertArticle")
	@ResponseBody
	public Map<String, Object> deleteExpertArticle(HttpServletRequest request,
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
		
		LogUtils.log(LogOperType.DELETE, "删除专家观点 : < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 显示/隐藏专家观点
	 * @return
	 */
	@RequiresPermissions("article:expert:change")
	@RequestMapping("/modifyExpertArticleVisibility")
	@ResponseBody
	public Map<String, Object> modifyExpertArticleVisibility(HttpServletRequest request,
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
		
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "隐藏/显示专家观点 : <" + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 专家观点评论列表
	 * @return
	 */
	@RequiresPermissions("article:expert:commentList")
	@RequestMapping("/expertArticleCommentListByAid/{articleId}")
	public String expertArticleCommentListByAid(
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
	
	/**
	 * 跳转到资讯审核界面
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("article:expert:toAudit")
	@RequestMapping("/toCheckExpertArticlePage")
	public String toCheckExpertArticlePage(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = false) Integer articleId) {

		Article article = articleService.getArticleById(articleId);
		model.addAttribute("article", article);

		return "article/articleCheck";
	}
	
	/**
	 * 资讯审核发布
	 * @return
	 */
	@RequiresPermissions("article:expert:audit")
	@RequestMapping("/checkExpertArticle")
	@ResponseBody
	public Map<String, Object> checkExpertArticle(
			HttpServletRequest request,
			@RequestParam(value = "state", required = false) Boolean isPassed,
			@RequestParam(value = "advise", required = false) String advise,
			@RequestParam(value = "contentId", required = false) Integer contentId) {

		ResponseResult result = new ResponseResult();
		if (!isPassed) {
			if (General.isEmpty(advise)) {
				return result.returnNeedParams();
			}
		}

		Article article = new Article();
		article.setState((byte) (isPassed ? ArticleState.PUBLISHED.getValue() : ArticleState.NOT_PASSED.getValue()));
		article.setSuggestion(advise);
		article.setContentId(contentId);

		try {
			articleService.updateArticle(article);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}

		LogUtils.log(LogOperType.AUDIT, "专家观点审核发布 : < " + contentId + " >");
		return result.returnSuccess();
	}

}
