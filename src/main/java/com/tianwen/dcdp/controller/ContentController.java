package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.pojo.Content;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.service.IContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private IContentService contentService;
	
	@RequiresPermissions("society:content:list")
	@RequestMapping("/contentList")
	public String contentList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false ) Integer pageSize,
			@RequestParam(value = "content", required = false ) String content,
			@RequestParam(value = "userId", required = false ) Integer userId,
			@RequestParam(value = "nickName", required = false ) String nickName,
			@RequestParam(value = "startDate", required = false ) String startDate,
			@RequestParam(value = "endDate", required = false ) String endDate,
			HttpServletRequest request, Model model) {
		
		page = null == page? 0:page;
		pageSize  = null == pageSize?10 :pageSize;
		
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("content", content);
		whereMap.put("userId", userId);
		whereMap.put("nickName", nickName);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		//查询总记录数
		int totalCount = contentService.getContentTotalCount(whereMap);
		
		//获取分页列表
		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Content> contents = contentService.getByPage(whereMap);
		
		//前台参数
		model.addAttribute("pager", pager);
		model.addAttribute("content", content);
		model.addAttribute("userId", userId);
		model.addAttribute("nickName", nickName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("data", contents);
		return "content/contentList";
	}
	
	/**
	 * 批量删除动态
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("society:content:delete")
	@RequestMapping("/deleteContent")
	@ResponseBody
	public Map<String,Object> deleteContent( @RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			contentService.deleteContentByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			e.getMessage();
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.DELETE, "删除动态：<"+idList.toString()+">");
		return resultMap;
	}
	
	
	/**
	 * 批量隐藏动态
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("society:content:hidden")
	@RequestMapping("/hiddenContents")
	@ResponseBody
	public Map<String,Object> hiddenContents( @RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			contentService.hiddenContents(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			e.getMessage();
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.SHOW_OR_HIDE, "隐藏/显示动态：<"+idList.toString()+">");
		return resultMap;
	}
	
	
	
	
}
