package com.tianwen.dcdp.controller;

import java.util.ArrayList;
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
import com.tianwen.dcdp.common.GroupsState;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Admin;
import com.tianwen.dcdp.pojo.Groups;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IGroupsService;

@Controller
@RequestMapping("/groups")
public class GroupsController {

	@Autowired
	private IGroupsService groupsService;
	
	
	/**
	 * 获取小组列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("groups:group:list")
	@RequestMapping("/groupsList")
	public String groupsList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "groupName", required = false) String groupName,
			@RequestParam(value = "state", required = false) Integer state,
			@RequestParam(value = "module", required = false) Integer module) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("groupName", groupName);
		whereMap.put("state", state);
		whereMap.put("module", module);
		// 获取记录总数
		int totalCount = groupsService.selectTotalCount(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Groups> data = groupsService.selectPageList(whereMap);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);

		model.addAllAttributes(whereMap);
		model.addAttribute("groupsState", GroupsState.getAllState());
		return "groups/groupsList";
	}
	
	/**
	 * 小组禁用/解禁
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("groups:group:forbid")
	@RequestMapping("/forbid")
	@ResponseBody
	public Map<String, Object> forbid(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList,
			@RequestParam(value = "value", required = false) Integer value) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			groupsService.updateForBid(idList,value);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.ENABLE_OD_DISABLE, "禁用/解禁小组 : < " + idList.toString() + " >");
		return result.returnSuccess();
	}
	
	/**
	 *小组编辑
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("groups:group:edit")
	@RequestMapping("/editGroups")
	@ResponseBody
	public Map<String, Object> editGroups(HttpServletRequest request,
			@RequestParam(value = "groupId", required = false) Integer groupId,
			@RequestParam(value = "groupName", required = false) String groupName,
			@RequestParam(value = "state", required = false) Byte state,
			@RequestParam(value = "suggestion", required = false) String suggestion
			) {
		ResponseResult result = new ResponseResult();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (!General.isNotEmpty(groupId)) {
			resultMap.put("result", "0");
			resultMap.put("msg", "ID不能为空");
			return resultMap;
		}
		Groups group = new Groups();
		group.setGroupId(groupId);
		if(General.isNotEmpty(groupName)){
			group.setGroupName(groupName);
		}
		int count = groupsService.selectByName(group);
		if(count>0){
			resultMap.put("result", "0");
			resultMap.put("msg", "名称不能重复");
			return resultMap;
		}
		if(General.isNotEmpty(state)){
			group.setState(state);
		}
		if(General.isNotEmpty(suggestion)){
			group.setSuggestion(suggestion);
		}
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
		if(General.isNotEmpty(admin)){
			group.setAuditorId(admin.getUserId());
		}
		try {
			groupsService.update(group);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.UPDATE, "编辑小组 : < " + groupId + " >");
		return result.returnSuccess();
	}
	
	/**
	 *  小组审核
	 * 
	 * @param request
	 * @return
	 *//*
	@RequiresPermissions("groups:group:audit")
	@RequestMapping("/auditGroups")
	@ResponseBody
	public Map<String, Object> auditGroups(HttpServletRequest request,
			@RequestParam(value = "groupId", required = false) Integer groupId,
			@RequestParam(value = "groupName", required = false) String groupName,
			@RequestParam(value = "state", required = false) Byte state,
			@RequestParam(value = "suggestion", required = false) String suggestion
			) {
		ResponseResult result = new ResponseResult();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (!General.isNotEmpty(groupId)) {
			resultMap.put("result", "0");
			resultMap.put("msg", "ID不能为空");
			return resultMap;
		}
		Groups group = new Groups();
		group.setGroupId(groupId);
		if(General.isNotEmpty(groupName)){
			group.setGroupName(groupName);
		}
		int count = groupsService.selectByName(group);
		if(count>0){
			resultMap.put("result", "0");
			resultMap.put("msg", "名称不能重复");
			return resultMap;
		}
		if(General.isNotEmpty(state)){
			group.setState(state);
		}
		if(General.isNotEmpty(suggestion)){
			group.setSuggestion(suggestion);
		}
		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("admin");
		System.out.println("admin: "+admin.getUserId());
		try {
			groupsService.update(group);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.UPDATE, "审核小组 : < " + groupId + " >");
		return result.returnSuccess();
	}*/
	/**
	 * 跳转到新增界面
	 * @return
	 */
	@RequiresPermissions("groups:group:toAdd")
	@RequestMapping("/toaddPage")
	public String toaddPage() {
		return "groups/groupsAdd";
	}
	
	/**
	 * 跳转到编辑界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("groups:group:toEdit")
	@RequestMapping("/toEditPage")
	public String toEditPage(@RequestParam(value="groupId", required=false)Integer groupId, Model model) {
		Groups groups = groupsService.getById(groupId);
		model.addAttribute("data", groups);
		return "groups/groupsEdit";
	}
	
	/**
	 * 跳转到审核界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("groups:group:toAudit")
	@RequestMapping("/toAuditPage")
	public String toAuditPage(@RequestParam(value="groupId", required=false)Integer groupId, Model model) {
		Groups groups = groupsService.getById(groupId);
		model.addAttribute("data", groups);
		model.addAttribute("groupsState", GroupsState.getAllState());
		return "groups/groupsAudit";
	}
	
	/**
	 * 跳转到详情界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("groups:group:toDetail")
	@RequestMapping("/detail")
	public String toDetailPage(@RequestParam(value="groupId", required=false)Integer groupId, Model model) {
		Groups groups = groupsService.getById(groupId);
		if (groups.getCreateTime() != null) {
			groups.setStrCreateTime(DateUtils.longDateToStr(
					groups.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		model.addAttribute("group", groups);
		model.addAttribute("groupsState", GroupsState.getAllState());
		return "groups/groupsDetail";
	}
	/**
	 * 删除小组
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("groups:group:delete")
	@RequestMapping("/deleteGroups")
	@ResponseBody
	public Map<String, Object> deleteGroups(HttpServletRequest request,
			@RequestParam(value = "groupId", required = false) String ids) {
		ResponseResult result = new ResponseResult();
		List<Integer> groupsIds = new ArrayList<Integer>();
		if (General.isNotEmpty(ids)) {
			String[] stArr = ids.split(",");
			for (String st : stArr) {
				groupsIds.add(Integer.parseInt(st));
			}
		}

		try {
			groupsService.batchDelete(groupsIds);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.DELETE, "删除小组 : < " + ids + " >");
		return result.returnSuccess();
	}
	/**
	 * 新增
	 * 
	 * @param request
	 * @return
	 *//*
	@RequiresPermissions("groups:group:add")
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request,
			@RequestBody Groups group) {
		ResponseResult result = new ResponseResult();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (!General.isNotEmpty(group.getGroupName())) {
			resultMap.put("result", "0");
			resultMap.put("msg", "ID不能为空");
			return resultMap;
		}
		try {
			groupsService.add(group);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.CREATE, "新增小组 : < " + group.getGroupId() + " >");
		return result.returnSuccess();
	}
	*/
	/**
	 * 获取小组成员列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("groups:group:getUsers")
	@RequestMapping("/selectUsers")
	public String selectUsers(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "groupId", required = false) String groupId) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("groupId", groupId);
		// 获取记录总数
		int totalCount = groupsService.selectUsersCount(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Map<String,Object>> data = groupsService.selectUsers(whereMap);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		model.addAttribute("groupId", groupId);
		return "groups/groupsUser";
	}
	
	/**
	 * 获取热门小组列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("groups:HotGroups:list")
	@RequestMapping("/HotGroups")
	public String HotGroups(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "groupName", required = false) String groupName,
			@RequestParam(value = "state", required = false) Integer state) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("groupName", groupName);
		whereMap.put("state", state);
		// 获取记录总数
		int totalCount = groupsService.selectHotCount(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Groups> data = groupsService.selectHotList(whereMap);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);

		model.addAllAttributes(whereMap);
		model.addAttribute("groupsState", GroupsState.getAllState());
		return "groups/hotGroupList";
	}
	/**
	 * 删除热门小组
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("groups:HotGroups:delete")
	@RequestMapping("/delHotGroups")
	@ResponseBody
	public Map<String, Object> delHotGroups(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> ids){
		ResponseResult result = new ResponseResult();
		try {
			groupsService.batchDelHot(ids);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.DELETE, "删除热门小组 : < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到热门小组编辑排序界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("groups:HotGroups:toEdit")
	@RequestMapping("/toHotEditPage")
	public String toHotEditPage(@RequestParam(value="groupId", required=false)Integer groupId, Model model) {
		Map<String,Object> map = groupsService.selectHotById(groupId);
		model.addAttribute("data", map);
		return "groups/hotGroupsEdit";
	}
	/**
	 * 热门小组编辑 排序
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("groups:HotGroups:edit")
	@RequestMapping("/editHotGroups")
	@ResponseBody
	public Map<String, Object> editHotGroups(HttpServletRequest request,
			@RequestParam(value = "groupId", required = false) Integer groupId,
			@RequestParam(value = "orderNum", required = false) Integer orderNum
			) {
		ResponseResult result = new ResponseResult();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (!General.isNotEmpty(groupId)) {
			resultMap.put("result", "0");
			resultMap.put("msg", "ID不能为空");
			return resultMap;
		}
		if(!General.isNotEmpty(orderNum)){
			orderNum = 0;
		}
		try {
			groupsService.updateOrderNum(groupId, orderNum);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.UPDATE, "热门小组编辑排序 : < " + groupId + " >");
		return result.returnSuccess();
	}
}
