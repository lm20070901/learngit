package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Article;
import com.tianwen.dcdp.pojo.FocusPicture;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IArticleService;
import com.tianwen.dcdp.service.IFocusPictureService;

@RequestMapping("/focusPicture")
@Controller
public class FocusPictureController {

	@Autowired
	private IFocusPictureService focusService;
	
	@Autowired
	private IArticleService articleService;
	
	/**
	 * 获取焦点图列表
	 * @param model
	 * @return
	 */
	@RequiresPermissions("article:focusPic:list")
	@RequestMapping("/focusPictureList")
	public String focusPictureList(Model model) {
		List<FocusPicture> data = focusService.getFocusPictureList();
		model.addAttribute("data", data);
		return "focusPic/picList";
	}
	
	/**
	 * 批量删除焦点图
	 * @param ids 要删除的焦点图ID
	 * @return
	 */
	@RequiresPermissions("article:focusPic:delete")
	@RequestMapping("/deleteFocusPicture")
	@ResponseBody
	public Map<String, Object> deleteFocusPicture(@RequestParam(value="picId", required=false)String ids) {
		ResponseResult result = new ResponseResult();
		if(General.isEmpty(ids)) {
			return result.returnNeedParams();
		}
		List<Integer> picIdList = General.strSplitToList(ids);
		try {
			focusService.batchDeletePic(picIdList);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除焦点图 ： < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到焦点图新增界面
	 * @return
	 */
	@RequiresPermissions("article:focusPic:toAdd")
	@RequestMapping("/toCreateFocusPicPage")
	public String toCreateFocusPicPage() {
		return "focusPic/focusPicCreate";
	}
	
	/**
	 * 保存新增焦点图记录
	 * @param title 标题
	 * @param relatedLink 外链地址（关联文章链接）
	 * @param linkDir 图片相对地址
	 * @param articleId 外链资讯ID
	 * @param categoryType 外链资讯分类类型ID
	 * @return
	 */
	@RequiresPermissions("article:focusPic:add")
	@RequestMapping("/addFocusPicture")
	@ResponseBody
	public Map<String, Object> addFocusPicture(@RequestParam(value="title",required=false)String title,
			@RequestParam(value="relatedLink",required=false)String relatedLink,
			@RequestParam(value="linkDir",required=false)String linkDir,
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="categoryType",required=false)Byte categoryType) {
		ResponseResult result = new ResponseResult();
		if(General.isEmpty(title)) {
			return result.returnNeedParams();
		}
		
		FocusPicture pic = new FocusPicture();
		try {
			pic.setTitle(title);
			pic.setRelatedLink(relatedLink);
			pic.setLinkDir(linkDir);
			pic.setRelatedArticleId(articleId);
			pic.setContentType(categoryType);
			pic.setIsVisible((byte) ParamEnum.VisibleState.VISIBLE.getValue());
			
			focusService.createFocusPicture(pic);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.CREATE, "新增焦点图 ： < " + pic.getPicId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到焦点图信息编辑界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("article:focusPic:toEdit")
	@RequestMapping("/toEditFocusPicPage")
	public String toEditFocusPicPage(@RequestParam(value="id", required=false)Integer id, Model model) {
		FocusPicture pic = focusService.getFocusPicById(id);
		model.addAttribute("data", pic);
		return "focusPic/focusPicEdit";
	}
	
	/**
	 * 更新焦点图信息
	 * @param id
	 * @param title
	 * @param linkDir
	 * @param relatedLink
	 * @param articleId
	 * @param categoryId
	 * @param isVisible
	 * @return
	 */
	@RequiresPermissions("article:focusPic:edit")
	@RequestMapping("/editFocusPicture")
	@ResponseBody
	public Map<String, Object> editFocusPicture(@RequestParam(value="id", required=false)Integer id,
			@RequestParam(value="title", required=false)String title,
			@RequestParam(value="linkDir", required=false)String linkDir,
			@RequestParam(value="relatedLink", required=false)String relatedLink,
			@RequestParam(value="articleId", required=false)Integer articleId,
			@RequestParam(value="categoryId", required=false)Byte categoryId,
			@RequestParam(value="visible", required=false)Byte isVisible) {
		ResponseResult result = new ResponseResult();
		if(id == null || General.isEmpty(title) || General.isEmpty(linkDir)) {
			return result.returnNeedParams();
		}
		try {
			FocusPicture pic = new FocusPicture();
			pic.setPicId(id);
			pic.setTitle(title);
			pic.setLinkDir(linkDir);
			pic.setRelatedArticleId(articleId);
			pic.setContentType(categoryId);
			pic.setIsVisible(isVisible);
			focusService.editFocusPicture(pic);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.UPDATE, "修改焦点图信息 ： < " + id + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 选择关联文章
	 * @param request
	 * @param model
	 * @param curPage
	 * @param pageSize
	 * @param title
	 * @param category
	 * @return
	 */
	@RequestMapping("/selectLinkedArticle")
	//TODO 考虑只显示广告相关文章
	public String selectLinkedArticle(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "category", required = false) Integer category) {
		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("title", title);
		whereMap.put("category", category);
		
		// 获取记录总数
	    int totalCount = articleService.getTotalCount(whereMap);
		
	    Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Article> data = articleService.getPageList(whereMap);
		model.addAllAttributes(whereMap);
		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		
		return "focusPic/linkedArticleList";
	}
	
}
