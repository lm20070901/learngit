package com.tianwen.dcdp.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.District;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.User;
import com.tianwen.dcdp.pojo.Verified;
import com.tianwen.dcdp.service.IDistrictService;
import com.tianwen.dcdp.service.IUsersService;
import com.tianwen.dcdp.service.IVerifiedService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private IUsersService usersService;
	@Resource
	private IVerifiedService  verifiedService;
	
	@Resource
	private IDistrictService  districtService;
	
	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @param nickName
	 * @param userName
	 * @param userAuth
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("society:user:list")
	@RequestMapping("/getUserListByMap")
	public String getUserListByMap(
			@RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "pageSize",required = false) Integer pageSize,
            @RequestParam(value = "nickName",required = false) String nickName,
            @RequestParam(value = "userName",required = false) String userName,
            @RequestParam(value = "userAuth",required = false) Integer userAuth,
			HttpServletRequest request,Model model){
		page = page==null ?1:page;
		pageSize = pageSize==null?10:pageSize;
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("nickName", nickName);
		whereMap.put("userName", userName);
		whereMap.put("userAuth", userAuth);
		
		int totalCount = usersService.getTotalCount(whereMap);
		//获取分页列表
		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<User> userList = usersService.getByPage(whereMap);
		//前台参数
		model.addAttribute("page", pager);
		model.addAttribute("nickName",nickName);
		model.addAttribute("userName",userName);
		model.addAttribute("userAuth",userAuth);
		model.addAttribute("userList", userList);	
		return "/users/userList";
	}
	
	
	
	/**
	 * 编辑用户编辑页面
	 * @param userId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("society:user:toEdit")
	@RequestMapping("/toUserEdit")
	public  String toUserEdit(
			@RequestParam(value = "userId") Integer userId,
			HttpServletRequest request,Model model){
		
		User user = usersService.getUserInfoById(userId);
		
		model.addAttribute("user", user);
		List<Verified> verifiedlist = verifiedService.getVerifiedInfo(userId);
		Map<String,Object> queryMap = new HashMap<String,Object>();
		//查询全部的省
		List<District> provicenList = districtService.getProvienceListById(queryMap);
		
		queryMap.put("provienceId", user.getProvinceId());
		List<District> cityList = districtService.getCityListByMap(queryMap);
		
		model.addAttribute("provicenList", provicenList);
		model.addAttribute("cityList", cityList);
		
		model.addAttribute("verifiedlist", verifiedlist);
		return "users/userEdit";
	}
	
	/**
	 * 编辑用户
	 * @param user
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("society:user:edit")
	@RequestMapping("/editUser")
	@ResponseBody
	public Map<String,Object> editUser(User user,HttpServletRequest request,Model model){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (user == null || user.getUserId() == 0) {
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		// 执行数据库操作
		try {
			usersService.updateUserInfo(user);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg",  Constants.SYSTEM_ERROR_MSG);
		}
		LogOperType type = user.getUserId()==0?ParamEnum.LogOperType.CREATE: ParamEnum.LogOperType.UPDATE;
		LogUtils.log(type, type.getDesc()+"用户");
		return resultMap;
	}
	
	/**
	 * 根据用户ID批量删除用户
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("society:user:delete")
	@RequestMapping("/deleteUserByIds")
	@ResponseBody
	public Map<String,Object> deleteUserByIds(
			@RequestParam("idList[]") List<Integer> idList,
			 HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		try{
			usersService.deleteUserByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.DELETE, "删除用户：<"+idList.toString()+">");
		return resultMap;
	}
	
	
	/**
	 * 锁定/解锁用户
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("society:user:change")
	@RequestMapping("/changeUserLockedStatus")
	@ResponseBody
	public Map<String,Object> changeUserLockedStatus(
			@RequestParam("idList[]") List<Integer> idList,
			 HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			usersService.changeUserLockedStatus(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.LOCK_OR_UNLOCK, "锁定/解锁用户：<"+idList.toString()+">");
		return resultMap;
	}
	
	
	/**
	 * 密码重置
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("society:user:reset")
	@RequestMapping("/resetPassword")
	@ResponseBody
	public Map<String,Object> resetPassword(
			 @RequestParam("idList[]") List<Integer> idList,
			 HttpServletRequest request, HttpServletResponse response){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		
		try{
			usersService.resetPassword(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.RESET, "密码重置：<"+idList.toString()+">");
		return resultMap;
	}
}
