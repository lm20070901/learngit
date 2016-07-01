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
import com.tianwen.dcdp.pojo.Action;
import com.tianwen.dcdp.service.IActionService;

@Controller
@RequestMapping("/action")
public class ActionController {

	private static Logger logger = Logger.getLogger(ActionController.class);

	@Resource
	private IActionService actionService;

	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @param actionId
	 * @param actionName
	 * @param actionUrl
	 * @param pid
	 * @param mark
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system:action:list")
	@RequestMapping("/actionList")
	public String toActionList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "actionId", required = false) Integer actionId,
			@RequestParam(value = "actionName", required = false) String actionName,
			@RequestParam(value = "actionUrl", required = false) Integer actionUrl,
			@RequestParam(value = "pid", required = false) Integer pid,
			@RequestParam(value = "mark", required = false) String mark,
			HttpServletRequest request, Model model) {
		// NOTE 只做跳转 要做成树的形式了 
	/*	Map<String, Object> whereMap = new HashMap<String, Object>();
		page = null == page ? 0 : page;
		pageSize = null == pageSize ? 10 : pageSize;
		whereMap.put("actionId", actionId);
		whereMap.put("actionName", actionName);
		whereMap.put("actionUrl", actionUrl);
		whereMap.put("pid", pid);
		whereMap.put("mark", mark);

		int totalCount = actionService.countActionListNum(whereMap);

		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());

		List<Action> actionList = actionService.getActionListByMap(whereMap);
		Collections.reverse(actionList);//反转顺序 倒序排列
		model.addAllAttributes(whereMap);
		model.addAttribute("actionList", actionList);
		model.addAttribute("pager", pager);
*/
		return "action/actionList";
	}

	
	/**
	 * 跳转进入新增页面
	 * @param pid
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system:action:toActionAdd")
	@RequestMapping("/toActionAdd")
	public String toActionAdd(
			@RequestParam(value = "pid", required = false) Integer pid,
			HttpServletRequest request, Model model) {
		
		if(null == pid){
			model.addAttribute("msg", Constants.NULL_PARM_MSG);
			return "error";
		}
		
		model.addAttribute("pid", pid);
		
		return "action/actionEdit";
	}

	
	
	/**
	 * 进入编辑页面
	 * @param actionId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system:action:toActionEdit")
	@RequestMapping("/toActionEdit")
	public String toActionEdit(
			@RequestParam(value="actionId",required=false)Integer actionId,
			HttpServletRequest request ,Model model){
		
		if(null == actionId){
				model.addAttribute("msg", Constants.NULL_PARM_MSG);
				return "error";
		}
		
		Action action  = actionService.getActionById(actionId);
		
		model.addAttribute("action", action);
		model.addAttribute("actionId", actionId);
		
		return "action/actionEdit";
	}

	
	
	/**
	 * 保存action
	 * @param action
	 * @return
	 */
	@RequiresPermissions("system:action:saveAction")
	@RequestMapping("/saveAction")
	@ResponseBody
	public Map<String, Object> saveAction(Action action) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (StringUtils.isEmpty(action.getActionName())) {
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		//不传父ID 默认是一级菜单
	    action.setPid(null == action.getPid()?0:action.getPid());
	    
		try {
			actionService.saveAction(action);
			resultMap.put("data", action);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogOperType type = action.getActionId()==null?ParamEnum.LogOperType.CREATE:ParamEnum.LogOperType.UPDATE;
		LogUtils.log(type, type.getDesc()+"访问路径。");
		return resultMap;
	}
	
	
	
	/**
	 * 批量删除 访问路径 
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("system:action:delete")
	@RequestMapping("/deleteActionByIds")
	@ResponseBody
	public Map<String,Object> deleteActionByIds(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			actionService.deleteActionByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.DELETE, "删除访问路径 ：<"+idList.toString()+">");

		return resultMap;
	}
}
