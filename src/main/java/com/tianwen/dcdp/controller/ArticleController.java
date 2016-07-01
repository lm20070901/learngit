package com.tianwen.dcdp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.DateUtils;
import com.tianwen.dcdp.common.EdwManageConstants;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.GroupsState;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.ArticleType;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Article;
import com.tianwen.dcdp.pojo.ArticleComment;
import com.tianwen.dcdp.pojo.Groups;
import com.tianwen.dcdp.pojo.HotNews;
import com.tianwen.dcdp.pojo.NewsCategory;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.Question;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IArticleService;
import com.tianwen.dcdp.service.IGroupsService;
import com.tianwen.dcdp.service.IQestionService;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private IArticleService articleService;
	@Resource
	private EdwManageConstants edwFrontConstants;
	@Resource
	private IGroupsService groupsService;
	@Resource
	private IQestionService qestionService;
	/**
	 * 获取资讯评论列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("article:comment:list")
	@RequestMapping("/articleCommentList")
	public String articleCommentList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "author", required = false) String author,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "articleId", required = false) Integer articleId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("author", author);
		whereMap.put("content", content);
		whereMap.put("articleId", articleId);
		whereMap.put(
				"startDate",
				General.isEmpty(startDate) ? null : DateUtils.strDateToLong(
						startDate, "yyyy-MM-dd hh:mm:ss"));
		whereMap.put(
				"endDate",
				General.isEmpty(endDate) ? null : DateUtils.strDateToLong(
						endDate, "yyyy-MM-dd hh:mm:ss"));
		// 获取记录总数
		int totalCount = articleService.getArticleCommentTotalCount(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());

		List<ArticleComment> data = articleService.getCommentPageList(whereMap);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		model.addAllAttributes(whereMap);

		return "article/articleCommentList";
	}
	
	/**
	 * 删除资讯评论
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("article:comment:delete")
	@RequestMapping("/deleteArticleComment")
	@ResponseBody
	public Map<String, Object> deleteArticleComment(HttpServletRequest request,
			@RequestParam(value = "commentId", required = false) String ids) {
		ResponseResult result = new ResponseResult();
		List<Integer> commentIds = new ArrayList<Integer>();
		if (General.isNotEmpty(ids)) {
			String[] stArr = ids.split(",");
			for (String st : stArr) {
				commentIds.add(Integer.parseInt(st));
			}
		}

		if (ids == null || commentIds.size() == 0) {
			return result.returnNeedParams();
		}

		try {
			articleService.deleteArticleCommentsByIds(commentIds);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除资讯评论 : < " + ids + " >");
		return result.returnSuccess();
	}

	/**
	 * 隐藏资讯评论
	 * 
	 * @return
	 */
	@RequiresPermissions("article:comment:change")
	@RequestMapping("/modifyArticleCommentVisibility")
	@ResponseBody
	public Map<String, Object> modifyArticleCommentVisibility(HttpServletRequest request,
			@RequestParam(value = "commentId", required = false) String ids) {
		ResponseResult result = new ResponseResult();
		List<Integer> commentIds = new ArrayList<Integer>();
		if (General.isNotEmpty(ids)) {
			String[] stArr = ids.split(",");
			for (String st : stArr) {
				commentIds.add(Integer.parseInt(st));
			}
		}

		if (ids == null || commentIds.size() == 0) {
			return result.returnNeedParams();
		}

		try {
			articleService.modifyArticleCommentVisibility(commentIds);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "隐藏/显示资讯评论 : < " + ids + " >");
		return result.returnSuccess();
	}


	/**
	 * 跳转到栏目管理界面
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("article:category:list")
	@RequestMapping("/newsCategoryList")
	public String newsCategoryList(Model model) {
		
		List<NewsCategory> data = articleService.getNewsCategoryWithCountByPid(0);
		model.addAttribute("data", data);
		return "article/newsCatList";
	}
	
	//根据一级栏目查询子栏目
	@RequiresPermissions("article:category:list")
	@RequestMapping("/queryNewsCategorys")
	public String queryNewsCategorys(Model model,
			@RequestParam(value="tp", required=false)Integer parentId) {
		List<NewsCategory> data = articleService.getNewsCategoryWithCountByPid(parentId);
		model.addAttribute("data", data);
		return "article/newsCateFragment";
	}

	/**
	 * 跳转到栏目新增界面
	 * 
	 * @return
	 */
	@RequiresPermissions("article:category:toAdd")
	@RequestMapping("/toAddNewsCategoryPage")
	public String toAddNewsCategoryPage(Model model) {
		List<NewsCategory> data = articleService.getNewsCategoryWithCountByPid(0);
		model.addAttribute("data", data);
		return "article/newsCategoryAdd";
	}

	/**
	 * 新增资讯分类
	 * 
	 * @return
	 */
	@RequiresPermissions("article:category:add")
	@RequestMapping("addNewsCategory")
	@ResponseBody
	public Map<String, Object> addNewsCategory(NewsCategory category) {
		ResponseResult result = new ResponseResult();
		try {
			articleService.addNewsCategory(category);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.CREATE, "新增资讯分类 : < " + category.getCategoryId() + " >");
		return result.returnSuccess();
	}

	/**
	 * 跳转到资讯分类编辑界面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("article:category:toEdit")
	@RequestMapping("/toEditNewsCategoryPage")
	public String toEditNewsCategoryPage(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "categoryId", required = false) Integer categoryId) {
		NewsCategory category = articleService.getNewsCategoryById(categoryId);
		List<NewsCategory> topMenu = articleService.getNewsCategoryWithCountByPid(0);
		model.addAttribute("data", category);
		model.addAttribute("menu", topMenu);
		return "article/newsCategoryEdit";
	}

	/**
	 * 编辑资讯分类信息
	 * 
	 * @param categoryId
	 * @param categoryName
	 * @param isVisible
	 * @return
	 */
	@RequiresPermissions("article:category:edit")
	@RequestMapping("/editNewsCategory")
	@ResponseBody
	public Map<String, Object> editNewsCategory(NewsCategory category) {
		ResponseResult result = new ResponseResult();
		try {
			articleService.updateNewsCategory(category);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.UPDATE, "修改资讯分类信息 ： < " + category.getCategoryId() + " >");
		return result.returnSuccess();
	}

	/**
	 * 批量删除资讯分类
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("article:category:delete")
	@RequestMapping("/deleteNewsCategory")
	@ResponseBody
	public Map<String, Object> deleteNewsCategory(HttpServletRequest request,
			@RequestParam(value = "categoryId", required = false) String ids) {
		List<Integer> categoryIds = new ArrayList<Integer>();
		if (General.isNotEmpty(ids)) {
			String[] stArr = ids.split(",");
			for (String st : stArr) {
				categoryIds.add(Integer.parseInt(st));
			}
		}

		ResponseResult result = new ResponseResult();
		try {
			articleService.batchDeleteNewsCategory(categoryIds);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除资讯分类信息 ： < " + ids + " >");
		return result.returnSuccess();
	}

	/**
	 * 跳转到热门资讯列表页
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("article:hotArticle:list")
	@RequestMapping("/HotNewsList")
	public String HotNewsList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "origin", required = false) String origin,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("title", title);
		whereMap.put("origin", origin);
		whereMap.put(
				"startDate",
				General.isEmpty(startDate) ? null : DateUtils
						.strDateToLong(startDate));
		whereMap.put(
				"endDate",
				General.isEmpty(endDate) ? null : DateUtils
						.strDateToLong(endDate));

		List<Article> articles = articleService.getHotArticles(whereMap);

		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		model.addAllAttributes(whereMap);
		model.addAttribute("data", articles);
		return "article/hotNewsList";
	}

	/**
	 * 跳转到修改热门资讯排序界面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("article:hotArticle:toSort")
	@RequestMapping("/toModifyHotNewsOrder")
	public String toModifyHotNewsOrder(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "id", required = false) Integer articleId) {

		HotNews data = articleService.getHotNewsWithTitle(articleId);
		model.addAttribute("data", data);
		return "article/hotNewsEdit";
	}

	/**
	 * 修改热门资讯排序
	 * 
	 * @param articleId
	 * @param order
	 * @return
	 */
	@RequiresPermissions("article:hotArticle:sort")
	@RequestMapping("/modifyHotNewsOrder")
	@ResponseBody
	public Map<String, Object> modifyHotNewsOrder(
			@RequestParam(value = "contentId", required = false) Integer articleId,
			@RequestParam(value = "order", required = false) Integer order) {
		ResponseResult result = new ResponseResult();

		HotNews hotNews = new HotNews();
		hotNews.setContentId(articleId);
		hotNews.setOrderNum(order);

		try {
			articleService.modifyHotNews(hotNews);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.UPDATE, "修改热门资讯热度排序 ： < " + articleId + " >");
		return result.returnSuccess();
	}

	/**
	 * 删除热门咨询
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("article:hotArticle:delete")
	@RequestMapping("/deleteHotNews")
	@ResponseBody
	public Map<String, Object> deleteHotNews(HttpServletRequest request,
			@RequestParam(value = "contentId", required = false) String ids) {
		List<Integer> hotNewsIds = new ArrayList<Integer>();
		if (General.isNotEmpty(ids)) {
			String[] stArr = ids.split(",");
			for (String st : stArr) {
				hotNewsIds.add(Integer.parseInt(st));
			}
		}

		ResponseResult result = new ResponseResult();

		try {
			articleService.batchDeleteHotNews(hotNewsIds);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除热门资讯 ： < " + ids + " >");
		return result.returnSuccess();
	}
	
	
	/**
	 * 创建全文索引
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("article:idx:create")
	@RequestMapping("/articleCreateIdx")
	@ResponseBody
	public Map<String, Object> createIdx(HttpServletRequest request,
			@RequestParam(value = "contentType", required = false) Integer contentType) {
		ResponseResult result = new ResponseResult();
		if (contentType == null) {
			return result.returnNeedParams();
		}
		try {
			String path = edwFrontConstants.STATIC_IDX_PATH + contentType;
			articleService.indexUtil(contentType, path);
		} catch (Exception e) {
			e.printStackTrace();
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.DELETE, "创建全文索引,创建类型 : < " + contentType + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 获取资讯索引列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system:idx:list")
	@RequestMapping(value ="/articleIdxSearch" ,produces="application/json;charset=UTF-8")
	public String articleIdxSearch(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "contentType", required = false) Integer contentType) {
	   
		contentType = contentType == null ? 1 : contentType; //为空则默认为1
		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;
		Integer startIdx = (curPage - 1) * pageSize;
		Integer endIdx = curPage * pageSize ;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("content", content);
		whereMap.put("title", title);
		whereMap.put("contentType", contentType);
		String path = edwFrontConstants.STATIC_IDX_PATH + contentType;
		
		int totalCount  = 0;
		Map<String, Object> maps = new HashMap<String, Object>();
		if(title!=null && !title.trim().equals("") && content!=null && !content.trim().equals("") ){ //title content 都不为空
			maps = articleService.searchUtil(path, new String[]{"title","content"}, new String[]{title,content}, startIdx,endIdx,contentType);
		}else if(title!=null && !title.trim().equals("")){
			maps = articleService.searchUtil(path, new String[]{"title"}, new String[]{title}, startIdx,endIdx,contentType);
		}else if(content!=null && !content.trim().equals("") ){
			maps = articleService.searchUtil(path, new String[]{"content"}, new String[]{content}, startIdx,endIdx,contentType);
		}
		if(maps!=null && maps.get("size")!= null)
			totalCount = Integer.parseInt(maps.get("size").toString());
		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		model.addAttribute("pager", pager);
		model.addAttribute("data", maps.get("list"));
		model.addAllAttributes(whereMap);
		model.addAttribute("idxType", ParamEnum.IdxType.getAllState());
		if(contentType == 1)
			model.addAttribute("articleState", ParamEnum.ArticleState.getAllState());
		if(contentType == 4)
			model.addAttribute("groupsState", GroupsState.getAllState());
		return "idx/articleIdxList";
	}
	
	/**
	 * 跳转到索引查看详情界面
	 * @param request
	 * @param model
	 * @param articleId
	 * @param contentType
	 * @return
	 */
	@RequiresPermissions("article:idx:toDetail")
	@RequestMapping("/toDetailArticlePage")
	public String toDetailArticlePage(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "contentType", required = false) Integer contentType) {
		String page = "";
		if(contentType == 1){
			Article article = articleService.getArticleById(id);
			List<NewsCategory> cates = articleService.getNewsCategory();
			model.addAttribute("cates", cates);
			model.addAttribute("article", article);
			if (article.getPublishTime() != null) {
				article.setStrPublishTime(DateUtils.longDateToStr(
						article.getPublishTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			//TODO 待修改，占时注释掉 yinz
			//model.addAttribute("contentType", ArticleType.FINANCIAL_ARTICLE.getValue());
			page ="idx/articleIdxDetail";
		}else if(contentType == 2){ //动态没有详情页
			
		}else if(contentType == 3){ //问题
			Question data = qestionService.getQuestionById(id);
			model.addAttribute("data", data);
			return "idx/idxQuestionDetail";
		}else if(contentType == 4){
			Groups groups = groupsService.getById(id);
			if (groups.getCreateTime() != null) {
				groups.setStrCreateTime(DateUtils.longDateToStr(
						groups.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			model.addAttribute("group", groups);
			model.addAttribute("groupsState", GroupsState.getAllState());
			return "groups/groupsDetail";
		}
		return page;
	}
	
}
