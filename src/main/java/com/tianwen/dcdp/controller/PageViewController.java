package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.PageView;
import com.tianwen.dcdp.service.IPageViewService;

@Controller
@RequestMapping("/pageView")
public class PageViewController {

	
	@Resource
	private IPageViewService pageViewService;

	
	/**
	 * 访问量统计
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param pageSize
	 * @param requst
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system:pageView:list")
	@RequestMapping("/toPageViewList")
	public String toPageViewList(
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			HttpServletRequest requst, Model model) {
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		
		int totalCount = pageViewService.countPageViewNum(whereMap);
		page = null == page ? 0 :page;
		pageSize = null == pageSize ? 10 : pageSize; 
		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		
		whereMap.put("sort", "REPORT_ID");
		whereMap.put("order", "DESC");
		
		List<PageView> pageViewList = pageViewService.getPageViewList(whereMap);
		model.addAllAttributes(whereMap);
		model.addAttribute("pageViewList", pageViewList);
		model.addAttribute("pager", pager);
		
		return "pageView/pageViewList";
	}
	
	
	/**
	 * 图表展示页面
	 * @param requst
	 * @param model
	 * @return
	 *//*
	@RequiresPermissions("system:pageView:chart")
	@RequestMapping("/toPageViewChart")
	public String toPageViewChart(
			HttpServletRequest requst, Model model){
		
		return "pageView/pageViewChart";
	}*/
	
	
	/**
	 * 查询表格数据 
	 * @param startDate
	 * @param endDate
	 * @param requst
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system:pageView:chart")
	@RequestMapping("/getChartData")
	@ResponseBody
	public Map<String,Object> getChartData(
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			HttpServletRequest requst, Model model){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		endDate = StringUtils.isEmpty(endDate)?General.getNow("yyyy-MM-dd"):endDate;
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		
		whereMap.put("sort", "DATE_STR");
		whereMap.put("order", "ASC");
		
		List<PageView> pageViewList = pageViewService.getPageViewList(whereMap);
		
		resultMap.put("pageViewList", pageViewList);
		resultMap.put("result", Constants.SUCCESS);
		resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		
		return resultMap;
	}

}
