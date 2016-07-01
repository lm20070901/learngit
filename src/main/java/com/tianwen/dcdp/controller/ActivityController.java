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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.DateUtils;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Activity;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IActivityService;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	
	@Resource
	private IActivityService activityService;

	/**
	 * 获取小组列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("activity:act:list")
	@RequestMapping("/actList")
	public String actList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "state", required = false) Integer state) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("title", title);
		whereMap.put("state", state);
		
		PageBounds pageBounds = new PageBounds(curPage,pageSize,Order.formString("CONTENT_ID"));
		PageList<Activity> data = activityService.selectPageList(whereMap,pageBounds);
		Page pager = new Page(curPage, data.size(), pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		model.addAttribute("actState", ParamEnum.ArticleState.getAllState());
		model.addAttribute("actShowStatus", ParamEnum.IsOrNot.getMap());
		model.addAllAttributes(whereMap);
		return "activity/actList";
	}
	
	/**
	 * 跳转到新增界面
	 * @return
	 */
	@RequiresPermissions("activity:act:toAdd")
	@RequestMapping("/toaddPage")
	public String toaddPage() {
		return "activity/actAdd";
	}
	
	/**
	 * 跳转到编辑界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("activity:act:toEdit")
	@RequestMapping("/toEditPage")
	public String toEditPage(@RequestParam(value="contentId", required=false)Integer contentId, Model model) {
		Activity data = activityService.selectByPrimaryKey(contentId);
		if (data.getPublishTime() != null) {
			data.setStrPublishTime(DateUtils.longDateToStr(
					data.getPublishTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		if (data.getEnrollDeadline() != null) {
			data.setStrEnrollDeadline(DateUtils.longDateToStr(
					data.getEnrollDeadline(), "yyyy-MM-dd HH:mm:ss"));
		}
		model.addAttribute("data", data);
		return "activity/actEdit";
	}
	

	/**
	 *   编辑活动
	 * @param contentId
	 * @param title
	 * @param publishTime
	 * @param url
	 * @param isEnroll
	 * @param enrollDeadline
	 * @param maxEnrollNum
	 * @param introduce
	 * @param picUrl
	 * @param content
	 * @param request
	 * @return
	 */
	@RequiresPermissions("activity:act:edit")
	@RequestMapping(value="/editAct")
	@ResponseBody
	public Map<String, Object> editAct(
			@RequestParam(value = "contentId", required = false) Integer contentId,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "publishTime", required = false) String publishTime,
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "isEnroll", required = false) Integer isEnroll,
			@RequestParam(value = "enrollDeadline", required = false) String enrollDeadline,
			@RequestParam(value = "maxEnrollNum", required = false) Integer maxEnrollNum,
			@RequestParam(value = "introduce", required = false) String introduce,
			@RequestParam(value = "picUrl", required = false) String picUrl,
			@RequestParam(value = "content", required = false) String content,
			HttpServletRequest request) {
		ResponseResult result = new ResponseResult();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (!General.isNotEmpty(contentId)) {
			resultMap.put("result", "0");
			resultMap.put("msg", "ID不能为空");
			return resultMap;
		}
		try {
			Activity activity = new Activity();
			activity.setContentId(contentId);
			activity.setTitle(title);
			activity.setUrl(url);
			activity.setPicUrl(picUrl);
			activity.setIsEnroll(isEnroll);
			activity.setMaxEnrollNum(maxEnrollNum);
			activity.setIntroduce(introduce);
			activity.setContent(content);
			activity.setPublishTime(General.isEmpty(publishTime) ? null 
					: DateUtils.strDateToLong(publishTime,
					"yyyy-MM-dd hh:mm:ss"));
			activity.setEnrollDeadline(General.isEmpty(enrollDeadline) ? null
					: DateUtils.strDateToLong(enrollDeadline,
					"yyyy-MM-dd hh:mm:ss"));
			activity.setUpdateTime(System.currentTimeMillis());
			activityService.updateByPrimaryKeySelective(activity);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.UPDATE, "编辑活动: < " + contentId + " >");
		return result.returnSuccess();
	}
	/**
	 * 创建活动
	 * @param contentId
	 * @param title
	 * @param publishTime
	 * @param url
	 * @param isEnroll
	 * @param enrollDeadline
	 * @param maxEnrollNum
	 * @param introduce
	 * @param picUrl
	 * @param content
	 * @param request
	 * @return
	 */
	@RequiresPermissions("activity:act:add")
	@RequestMapping(value="/addAct")
	@ResponseBody
	public Map<String, Object> addAct(
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "publishTime", required = false) String publishTime,
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "isEnroll", required = false) Integer isEnroll,
			@RequestParam(value = "enrollDeadline", required = false) String enrollDeadline,
			@RequestParam(value = "maxEnrollNum", required = false) Integer maxEnrollNum,
			@RequestParam(value = "introduce", required = false) String introduce,
			@RequestParam(value = "picUrl", required = false) String picUrl,
			@RequestParam(value = "content", required = false) String content,
			HttpServletRequest request) {
		ResponseResult result = new ResponseResult();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (!General.isNotEmpty(title)) {
			resultMap.put("result", "0");
			resultMap.put("msg", "名称不能为空");
			return resultMap;
		}
		Activity activity = new Activity();
		try {
			
			activity.setTitle(title);
			activity.setUrl(url);
			activity.setPicUrl(picUrl);
			activity.setIsEnroll(isEnroll);
			activity.setMaxEnrollNum(maxEnrollNum);
			activity.setIntroduce(introduce);
			activity.setContent(content);
			activity.setPublishTime(General.isEmpty(publishTime) ? null 
					: DateUtils.strDateToLong(publishTime,
					"yyyy-MM-dd hh:mm:ss"));
			activity.setEnrollDeadline(General.isEmpty(enrollDeadline) ? null
					: DateUtils.strDateToLong(enrollDeadline,
					"yyyy-MM-dd hh:mm:ss"));
			activity.setCreateTime(System.currentTimeMillis());
			activityService.insertSelective(activity);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.UPDATE, "创建活动: < " + activity.getContentId() + " >");
		return result.returnSuccess();
	}
	
	@RequiresPermissions("activity:act:publish")
	@RequestMapping("/publish")
	@ResponseBody
	public Map<String, Object> publish(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			activityService.updateState(idList);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.ENABLE_OD_DISABLE, "发布活动: < " + idList.toString() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 隐藏/显示
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("activity:act:hideOrShow")
	@RequestMapping("/hideOrShow")
	@ResponseBody
	public Map<String, Object> hideOrShow(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList,
			@RequestParam(value = "value", required = false) Integer value) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			activityService.updateShowStatus(idList, value);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "隐藏/显示活动 : <" + idList.toString() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("activity:act:delete")
	@RequestMapping("/deleteAct")
	@ResponseBody
	public Map<String, Object> deleteAct(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			activityService.batchDelete(idList);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.DELETE, "删除活动 : < " + idList.toString() + " >");
		return result.returnSuccess();
	}
	
}
