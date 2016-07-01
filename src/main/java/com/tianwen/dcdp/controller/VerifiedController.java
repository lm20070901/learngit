package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.Verified;
import com.tianwen.dcdp.service.IVerifiedService;


@Controller
@RequestMapping("/verified")
public class VerifiedController {
	
	private static final Logger logger = Logger.getLogger(VerifiedController.class);
	
	@Resource
	private IVerifiedService  verifiedService;
	
	
	/**
	 * 认证列表
	 * @param page
	 * @param pageSize
	 * @param verifyType
	 * @param status
	 * @param nickName
	 * @param userName
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("society:verified:list")
	@RequestMapping("/verifiedList")
	public String verifiedList (
			@RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "pageSize",required = false) Integer pageSize,
            @RequestParam(value = "verifyType",required = false) Integer verifyType,
            @RequestParam(value = "status",required = false) Short status,
            @RequestParam(value = "nickName",required = false) String nickName,
            @RequestParam(value = "userName",required = false) String userName,
			HttpServletRequest request,Model model){
		page = page==null ?1:page;
		pageSize = pageSize==null?10:pageSize;
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("nickName", nickName);
		whereMap.put("userName", userName);
		whereMap.put("verifyType", verifyType);
		whereMap.put("status", status);
		//获取分页列表
		PageBounds pageBounds = new PageBounds(page, pageSize);
		PageList<Map<String,Object>> verifiedList = verifiedService.getByPage(whereMap,pageBounds);
		Page pager = new Page(page, verifiedList.getPaginator().getTotalCount(), pageSize);
		// 前台参数
		model.addAttribute("page", pager);
		model.addAttribute("nickName", nickName);
		model.addAttribute("userName", userName);
		model.addAttribute("status", status);
		model.addAttribute("verifyType", verifyType);
		model.addAttribute("verifiedList", verifiedList);
		
		return "verified/verifiedList";
	}
	
	/**
	 * 审核认证信息
	 * @param Verified
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("society:verified:save")
	@RequestMapping("/saveVerifiedInfo")
	@ResponseBody
	public Map<String,Object> saveVerifiedInfo(
			Verified verified,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (verified == null || verified.getId() == null) {
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		try{
			verifiedService.saveVerifiedInfo(verified);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		/*LogOperType type = null == verified.getId()?ParamEnum.LogOperType.CREATE:ParamEnum.LogOperType.UPDATE;
		LogUtils.log(type, type.getDesc()+"认证信息!");*/
		LogUtils.log(ParamEnum.LogOperType.AUDIT, "审核认证信息!");
		return resultMap;
	}
	
	/**
	 * 认证详情
	 * @param id
	 * @param reqeust
	 * @param respones
	 * @return
	 */
	@RequiresPermissions("society:verified:view")
	@RequestMapping("/toVerfiedDetail")
	public String toVerfiedDetail(
			@RequestParam(value = "id",required = true)Integer id,
			HttpServletRequest reqeust,Model model){
		
		Map<String, Object> verified = verifiedService.queryVerifiedDetail(id);
		model.addAttribute("verified", verified);
		
		return "verified/verfiedDetail";
	}
	
	
	/**
	 * 去认证审核
	 * @param id
	 * @param reqeust
	 * @param model
	 * @return
	 */
	@RequiresPermissions("society:verified:adudit")
	@RequestMapping("/toVerfiedAdudit")
	public String toVerfiedAdudit(
			@RequestParam(value = "id",required = true)Integer id,
			HttpServletRequest reqeust,Model model){
		
		Map<String, Object> verified = verifiedService.queryVerifiedDetail(id);
		model.addAttribute("verified", verified);
	
		
		return "verified/verfiedeAdudit";
	}
	
}
