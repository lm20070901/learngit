package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.ActivityEnroll;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IActivityEnrollService;

@Controller
@RequestMapping("/actEnroll")
public class ActivityEnrollController {
	
	@Resource
	private IActivityEnrollService activityEnrollService;

	/**
	 * 获取小组列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("activity:enroll:list")
	@RequestMapping("/enrollList")
	public String enrollList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "contentId", required = false) Integer contentId) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("title", title);
		whereMap.put("contentId", contentId);
		
		PageBounds pageBounds = new PageBounds(curPage,pageSize,Order.formString("ENROLL_ID"));
		PageList<ActivityEnroll> data = activityEnrollService.selectPageList(whereMap,pageBounds);
		Page pager = new Page(curPage, data.size(), pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		model.addAllAttributes(whereMap);
		return "activity/enrollList";
	}

	
	/**
	 * 批量删除
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("activity:enroll:delete")
	@RequestMapping("/deleteEnroll")
	@ResponseBody
	public Map<String, Object> deleteEnroll(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			activityEnrollService.batchDelete(idList);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.DELETE, "删除活动报名 : < " + idList.toString() + " >");
		return result.returnSuccess();
	}
	
	@RequiresPermissions("activity:enroll:export")
	@RequestMapping("/exportEnroll")
	@ResponseBody
	public Map<String, Object> exportEnroll(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "contentId", required = false) Integer contentId) {
		//设置返回头
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel; charset=utf-8");
		
		ResponseResult result = new ResponseResult();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("title", title);
		whereMap.put("contentId", contentId);
		
		try {
			response.setHeader("Content-disposition","attachment;filename="+new String( "活动报名信息".getBytes("GB2312"), "ISO8859-1" )+".xls");
			activityEnrollService.exportEnroll(whereMap,response.getOutputStream());
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.DELETE, "导出活动报名信息 : < title: " + title +" ,contentId："+contentId + " >");
		return result.returnSuccess();
	}
	
	
}
