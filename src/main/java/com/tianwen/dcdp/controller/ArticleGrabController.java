package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.DateUtils;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Admin;
import com.tianwen.dcdp.pojo.ArticleGrab;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IArticleGrabService;

@Controller
@RequestMapping("/articleGrab")
public class ArticleGrabController {

	@Autowired
	private IArticleGrabService articleGrabService;

	/**
	 * 获取资讯列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("article:grab:list")
	@RequestMapping("/grabList")
	public String grabList(
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "category", required = false) Integer category,
			@RequestParam(value = "origin", required = false) String origin,
			@RequestParam(value = "state", required = false) Integer state,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "contentType", required = false) Integer contentType) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("title", title);
		whereMap.put("category", category);
		whereMap.put("origin", origin);
		whereMap.put("state", state);
		whereMap.put("contentType", contentType);
		whereMap.put(
				"startDate",
				General.isEmpty(startDate) ? null : DateUtils
						.strDateToLong(startDate));
		whereMap.put(
				"endDate",
				General.isEmpty(endDate) ? null : DateUtils
						.strDateToLong(endDate));
		// 获取记录总数
		int totalCount = articleGrabService.getTotalCount(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<ArticleGrab> data = articleGrabService.getPageList(whereMap);
//		handleArticle(data);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);

		model.addAllAttributes(whereMap);

		model.addAttribute("articleState", ParamEnum.ArticleState.getAllState());
		return "article/articleGrabList";
	}

	/**
	 * 隐藏/显示资讯
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("article:grab:hide")
	@RequestMapping("/modifyVisibility")
	@ResponseBody
	public Map<String, Object> modifyVisibility(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList,
			@RequestParam(value = "value", required = false) Integer value) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			articleGrabService.updateShowStatus(idList, value);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "隐藏/显示抓取的资讯 : <" + idList + " >");
		return result.returnSuccess();
	}

	/**
	 * 跳转到资讯编辑界面
	 * @param request
	 * @param model
	 * @param articleId
	 * @param contentType
	 * @return
	 */
	@RequiresPermissions("article:grab:toEdit")
	@RequestMapping("/toEditPage")
	public String toEditPage(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "id", required = false) Integer articleId
			) {

		ArticleGrab article = articleGrabService.getById(articleId);

		model.addAttribute("article", article);
		if (article.getPublishTime() != null) {
			article.setStrPublishTime(DateUtils.longDateToStr(
					article.getPublishTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		return "article/articleGrabEdit";
	}

	/**
	 * 发布资讯抓取内容
	 * @return
	 */
	@RequiresPermissions("article:grab:publish")
	@RequestMapping("/publish")
	@ResponseBody
	public Map<String, Object> publish(
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
			@RequestParam(value = "contentType", required = false) Byte contentType,
			@RequestParam(value = "picUrl", required = false) String picUrl,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "mediaTime", required = false) Integer mediaTime,
			@RequestParam(value = "mediaUrl", required = false) String mediaUrl,
			@RequestParam(value = "flag", required = false) Integer flag,
			HttpServletRequest request) {
		ResponseResult result = new ResponseResult();

		ArticleGrab article = new ArticleGrab();
		article.setContentId(contentId);
		article.setTitle(title);
		article.setCategoryId(categoryType);
		article.setPublishTime(General.isEmpty(publishDate) ? System
				.currentTimeMillis() : DateUtils.strDateToLong(publishDate,
				"yyyy-MM-dd hh:mm:ss"));
//		article.setGrabTime(System.currentTimeMillis());
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
		article.setContentType(contentType);
		article.setPicUrl(picUrl);
		article.setMediaTime(mediaTime);
		article.setFileUrl(mediaUrl);
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
		if(General.isNotEmpty(admin)){
			article.setAuditorId(admin.getUserId());
		}
		try {
			// 更新资讯类容
			articleGrabService.publish(article,flag);
		} catch (Exception e) {
			System.out.println(e);
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}

		LogUtils.log(LogOperType.UPDATE, "修改资讯并发布 : < " + article.getContentId() + " >");
		return result.returnSuccess();
	}

	/**
	 * 删除资讯
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("article:grab:delete")
	@RequestMapping("/deleteGrab")
	@ResponseBody
	public Map<String, Object> deleteGrab(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			articleGrabService.batchDelete(idList);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除抓取的资讯 : < " + idList.toString() + " >");
		return result.returnSuccess();
	}

	/**
	 * 非专家观点，userName字段设为“”
	 * 
	 * @param articles
	 */
/*	private void handleArticle(List<ArticleGrab> articles) {
		if (articles != null && articles.size() > 0) {
			for (ArticleGrab ar : articles) {
				if (ar.getContentType() != ParamEnum.ArticleType.EXPERT_OPTION.getValue()) {
					ar.setUserName("");
				}
			}
		}
	}*/

}
