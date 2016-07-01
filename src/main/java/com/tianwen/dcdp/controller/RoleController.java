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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.Role;
import com.tianwen.dcdp.service.IActionService;
import com.tianwen.dcdp.service.IRoleService;


@Controller
@RequestMapping("/role")
public class RoleController {
	
	private static final Logger logger = Logger.getLogger(RoleController.class);
	
	@Resource
	private IRoleService roleService;
	
	
	@Resource
	private IActionService actionService;
	
	
	
	/**
	 * 角色列表
	 * @param page
	 * @param pageSize
	 * @param roleId
	 * @param roleName
	 * @param rquest
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system:role:list")
	@RequestMapping("/toRoleList")
	public String toRoleList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false ) Integer pageSize,
			@RequestParam(value="roleId",required=false)Integer roleId,
			@RequestParam(value="roleName",required=false)String roleName,
			@RequestParam(value="mark",required=false)String mark,
			HttpServletRequest rquest ,Model model ){
		Map<String, Object> whereMap = new HashMap<String, Object>();
		page = null == page? 0:page;
		pageSize  = null == pageSize?10 :pageSize;
		whereMap.put("roleName", roleName);
		whereMap.put("roleId", roleId);
		whereMap.put("mark", mark);
		//查询总记录数
		int totalCount = roleService.getTotalCount(whereMap);
		
		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		
		List<Role> roleList = roleService.getByPage(whereMap);
		model.addAllAttributes(whereMap);
		model.addAttribute("roleList", roleList);
		model.addAttribute("pager", pager);
		
		return "role/roleList";
	}
	
	/**
	 * 新增角色
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system:role:toAdd")
	@RequestMapping("/toRoleAdd")
	public String toRoleAdd(HttpServletRequest request ,Model model){
		
		return "role/roleAdd";
	}
	
	
	/**
	 * 保存角色
	 * @param role
	 * @param request
	 * @return
	 */
	@RequiresPermissions("system:role:save")
	@RequestMapping("/saveRole")
	@ResponseBody
	public Map<String, Object> saveRole(Role role, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (StringUtils.isEmpty(role.getRoleName())) {
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			roleService.saveRole(role);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogOperType type = role.getRoleId()==0?ParamEnum.LogOperType.CREATE: ParamEnum.LogOperType.UPDATE;
		LogUtils.log(type, type.getDesc()+"角色");
		return resultMap;
	}
	
	
	
	/**
	 * 批量删除角色
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("system:role:delete")
	@RequestMapping("/deleteRoleByIds")
	@ResponseBody
	public Map<String,Object> deleteRoleByIds(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			roleService.deleteRoleByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.DELETE, "删除角色：<"+idList.toString()+">");
		return resultMap;
	}
	
	
	
	/**
	 * 去分配权限
	 * @param pid
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system:role:toActionList")
	@RequestMapping("/toActionList")
	public String toActionList(
			@RequestParam(value = "roleId", required = false) Integer roleId,
			@RequestParam(value = "pid", required = false) Integer pid,
			HttpServletRequest request,Model model ){
		if(null == roleId){
			model.addAttribute("msg", Constants.NULL_PARM_MSG);
			return "error";
		}
//		pid  = pid==null?0:pid;
//		Map<String,Object> whereMap = new HashMap<String,Object>();
////		//不设置参数 获取全部
////        whereMap.put("roleId", roleId);
//		whereMap.put("pid", pid);
////       whereMap.put("isUsed", ParamEnum.IsOrNot.YES);
//		List<Action> actionList = actionService.getActionListByMap(whereMap);//查询权限列表
//		model.addAttribute("actionList", actionList);
		model.addAttribute("roleId", roleId);
		return "role/roleAction";
	}
	
	
	/**
	 * 权限树表  根据等级查询权限树
	 * @param pid
	 * @param request
	 * @return
	 */
	@RequiresPermissions("system:role:queryActionsList")
	@RequestMapping("/queryActionsList")
	@ResponseBody
	public Map<String,Object> queryActionsList(
			@RequestParam(value = "pid", required = false) Integer pid,
			@RequestParam(value = "roleId", required = false) Integer roleId,
			HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if(null == roleId){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		pid  = pid==null?0:pid;
		Map<String,Object> whereMap = new HashMap<String,Object>();
		
      	whereMap.put("pid", pid);
      	whereMap.put("roleId", roleId);
		List<Map<String,Object>> actionList = actionService.queryActionByLevle(whereMap);//查询权限列表
		
		resultMap.put("result", Constants.SUCCESS);
		resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		resultMap.put("actionList", actionList);
		
		return resultMap;
	}
	
	
	
	/**
	 * 分配权限
	 * @param roleId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system:role:distribute")
	@RequestMapping("/distributeAction")
	@ResponseBody
	public Map<String,Object> distributeAction(
			@RequestParam(value = "roleId", required = false) Integer roleId,
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request,Model model){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if( StringUtils.isEmpty(roleId) || null == idList || idList.size() <= 0){
			
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("roleId", roleId);
			whereMap.put("idList", idList);
			
			actionService.distributeAction(whereMap);
			
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.DISTRIBUTE_ACTION, "删除角色：roleId="+roleId+",idList=<"+idList.toString()+">");

		return resultMap;
	}
	
}
