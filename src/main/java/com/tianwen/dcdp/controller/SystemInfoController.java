package com.tianwen.dcdp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.EdwManageConstants;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Content;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.service.ISystemInfoService;



/***
 * 系统消息管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/systemInfo")
public class SystemInfoController {
	
	
	private static final Logger logger = Logger.getLogger(SystemInfoController.class);
	
	@Resource
	private  ISystemInfoService  systemInfoService;
	
	@Resource
	private EdwManageConstants edwFrontConstants;
		
	/**
	 * 系统消息管理  系统消息看成是动态
	 * @param contentBody
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("society:systemInfo:list")
	@RequestMapping("/systemInfoList")
	public String systemInfoList(
			 @RequestParam(value = "page",required = false) Integer page,
             @RequestParam(value = "pageSize",required = false) Integer pageSize,
			 @RequestParam(value = "contentBody",required = false) String contentBody,
			 @RequestParam(value = "startDate",required = false) String startDate,
	         @RequestParam(value = "endDate",required = false) String endDate,
			HttpServletRequest request, Model model){
		
		page = page==null ?1:page;
		pageSize = pageSize==null?10:pageSize;
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("contentBody", contentBody);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		
		int totalCount = systemInfoService.getTotalCount(whereMap);
		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Map<String,Object>> systemInfoList = systemInfoService.getSystemInfoList(whereMap);
		//前台参数
		model.addAttribute("pager", pager);
		model.addAttribute("contentBody",contentBody);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("systemInfoList", systemInfoList);
		
		return "systemInfo/systemInfoList";
	
	}
	
	/**
	 * 去新增系统消息页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("society:systemInfo:toAdd")
	@RequestMapping("/toAddSystemInfo")
	public  String toAddSystemInfo(
			HttpServletRequest request, Model model){
		
		return "systemInfo/systemInfoAdd";
	}
	
	
	/**
	 * 保存系统消息
	 * @param contentId
	 * @param contentBody
	 * @param images
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("society:systemInfo:save")
	@RequestMapping("/saveSystemInfo")
	@ResponseBody
	public Map<String, Object> saveSystemInfo(
			@RequestParam(value = "contentId", required = false) Integer contentId,
			@RequestParam(value = "contentBody", required = false) String contentBody,
			@RequestParam(value = "imageUrls", required = false) String imageUrls ,
			HttpServletRequest request, Model model) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//参数判断
		if(StringUtils.isEmpty(contentBody) ){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		Content content = new Content();
		content.setContentBody(contentBody);
		content.setContentId(contentId);
		content.setImageUrls(imageUrls);
		content.setPostTime( new Date().getTime());//发布时间
		
		try{
			systemInfoService.saveSystemInfo(content);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch(Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogOperType type = null == contentId?ParamEnum.LogOperType.CREATE:ParamEnum.LogOperType.UPDATE;
		LogUtils.log(type, type.getDesc()+"系统消息!");
		return resultMap;
	}
	
	
	/**
	 * 删除系统消息
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("society:systemInfo:delete")
	@RequestMapping("/delelteSystemInfo")
	@ResponseBody
	public Map<String,Object> delelteSystemInfo(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			systemInfoService.deleteSystemInfoByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.DELETE, "删除系统消息:<"+idList.toString()+">");
		return resultMap;
	}
}
