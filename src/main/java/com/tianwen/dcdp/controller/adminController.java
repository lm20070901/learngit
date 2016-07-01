package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.MD5;
import com.tianwen.dcdp.pojo.Admin;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.Role;
import com.tianwen.dcdp.service.IAdminService;
import com.tianwen.dcdp.service.IRoleService;

@Controller
@RequestMapping("/system")
public class adminController {
	//controller默认是单例，不要在controller中定义成员变量
	@Resource
	private IAdminService adminService;
	@Resource
	private IRoleService roleService;
	
	/**
	 * 
	 * 管理员列表 
	 * 
	 */
	@RequiresPermissions("system:admin:list")
	@RequestMapping("/adminList")
	public String adminList(HttpServletRequest request,Model model){
		//获取参数
		String pageStr = request.getParameter("page");
		String pageSizeStr = request.getParameter("pageSize");
		String userName = request.getParameter("userName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		int page = 1;
		int pageSize = 10;
		if(General.isNotEmpty(pageStr))
			page = Integer.parseInt(pageStr);
		if(General.isNotEmpty(pageSizeStr))
			pageSize = Integer.parseInt(pageSizeStr);
		
		//获取总数
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("userName", userName);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		int totalCount = adminService.getTotalCount(whereMap);
		//获取分页列表
		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Admin> adminList = adminService.getByPage(whereMap);
		//前台参数
		model.addAttribute("pager", pager);
		model.addAttribute("userName", userName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("adminList", adminList);
		return "adminList";
	}
	
	/**
	 * 
	 * 添加管理员页
	 * 
	 */
	@RequiresPermissions("system:admin:toAdd")
	@RequestMapping("/toAddAdmin")
	public String toAddAdmin(HttpServletRequest request,Model model){
		Map<String,Object> whereMap = new HashMap<String,Object>();
		List<Role> roleList = roleService.getByPage(whereMap);
		//去掉超级管理员
		if(roleList!=null){
			for (Role role : roleList) {
				if(role.getRoleId()==1){
					roleList.remove(role);
					break;
				}
			}
		}
		
		model.addAttribute("roleList", roleList);
		return "adminAdd";
	}
	
	/**
	 * 
	 * 添加管理员
	 * 
	 */
	@RequiresPermissions("system:admin:add")
	@RequestMapping("/addAdmin")
	@ResponseBody
	public Map<String,Object> addAdmin(HttpServletRequest request,Model model){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//获取参数
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String roleId = request.getParameter("roleId");
		String mark = request.getParameter("mark");
		//参数校验
		if(General.isEmpty(userName)){
			resultMap.put("result", "0");
			resultMap.put("msg", "用户名不能为空");
			return resultMap;
		}
		if(General.isEmpty(password)){
			resultMap.put("result", "0");
			resultMap.put("msg", "密码不能为空");
			return resultMap;
		}
		if(General.isEmpty(roleId)){
			resultMap.put("result", "0");
			resultMap.put("msg", "角色不能为空");
			return resultMap;
		}else if("1".equals(roleId)){
			resultMap.put("result", "0");
			resultMap.put("msg", "角色选择错误");
			return resultMap;
		}
		
		
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("userName", userName);
		whereMap.put("start", 0);
		whereMap.put("size", 1);
		List<Admin> admins = adminService.getByPage(whereMap);
		if(admins!=null&&admins.size()>0){
			resultMap.put("result", "0");
			resultMap.put("msg", "该用户已经存在");
			return resultMap;
		}
		
		Admin newadmin = new Admin();
		newadmin.setRoleName(userName);
		MD5 md5 = new MD5();
		newadmin.setPassword(md5.getMD5(password));
		newadmin.setRoleId(Integer.parseInt(roleId));
		newadmin.setUserName(userName);
		newadmin.setMark(mark);
		//执行数据库操作
		try {
			adminService.insert(newadmin);
			resultMap.put("result", "1");
			resultMap.put("msg", "创建成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("msg", "创建失败");
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * 删除管理员
	 * 
	 */
	@RequiresPermissions("system:admin:delete")
	@RequestMapping("/deleteAdmin")
	@ResponseBody
	public Map<String,Object> deleteAdmin(HttpServletRequest request,Model model){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//获取参数
		String userId = request.getParameter("userId");
		//参数校验
		if(General.isEmpty(userId)){
			resultMap.put("result", "0");
			resultMap.put("msg", "用户ID不能为空");
			return resultMap;
		}
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("userId", userId);
		List<Admin> admins = adminService.getByPage(whereMap);
		if(admins!=null&&admins.size()>0){
			//系统用户不能删除
			if(admins.get(0).getIsSystem()==1){
				resultMap.put("result", "0");
				resultMap.put("msg", "该用户不能被删除");
				return resultMap;
			}
		}else{
			resultMap.put("result", "0");
			resultMap.put("msg", "该用户不存在");
			return resultMap;
		}
		//执行数据库操作
		try {
			adminService.delete(userId);
			resultMap.put("result", "1");
			resultMap.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("msg", "删除失败");
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * 开启禁用管理员
	 * 
	 */
	@RequiresPermissions("system:admin:update")
	@RequestMapping("/updateAdminState")
	@ResponseBody
	public Map<String,Object> updateAdminState(HttpServletRequest request,Model model){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//获取参数
		String userIdStr = request.getParameter("userId");
		//参数校验
		if(General.isEmpty(userIdStr)){
			resultMap.put("result", "0");
			resultMap.put("msg", "用户ID不能为空");
			return resultMap;
		}
		int userId = Integer.parseInt(userIdStr);
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("userId", userId);
		whereMap.put("start", 0);
		whereMap.put("size", 1);
		List<Admin> admins = adminService.getByPage(whereMap);
		if(admins!=null&&admins.size()>0){
			//系统用户不能操作
			if(admins.get(0).getIsSystem()==1){
				resultMap.put("result", "0");
				resultMap.put("msg", "该用户不能被修改");
				return resultMap;
			}
		}else{
			resultMap.put("result", "0");
			resultMap.put("msg", "该用户不存在");
			return resultMap;
		}
		//执行数据库操作
		try {
			Map<String,Object> whereMap1 = new HashMap<String,Object>();
			whereMap1.put("userId", userId);
			if(admins.get(0).getIsUsed()==1)
				whereMap1.put("isUsed", 0);
			else
				whereMap1.put("isUsed", 1);
			adminService.update(whereMap1);
			resultMap.put("result", "1");
			resultMap.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("msg", "更新失败");
		}
		
		return resultMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
