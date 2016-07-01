package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.pojo.AdminLog;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.service.IAdminLogService;

@RequestMapping("/adminLog")
@Controller
public class AdminLogController {
	
	@Autowired
	private IAdminLogService adminLogService;

	@RequestMapping("/logList")
	public String logList(Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "logInfo", required = false) String logInfo,
			@RequestParam(value = "operType", required = false) Integer operType) {
		
		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;
		
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("logInfo", logInfo);
		whereMap.put("operType", operType);
		
		//查询记录总数
		int totalCount = adminLogService.getLogTotalCount(whereMap);
		
		Page page = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", page.getStart());
		whereMap.put("size", page.getPageSize());
		
		List<AdminLog> data = adminLogService.getAdminLogList(whereMap);
		
		model.addAttribute("data", data);
		model.addAttribute("page", page);
		model.addAllAttributes(whereMap);
		//返回日志操作类型
		model.addAttribute("type", ParamEnum.LogOperType.getMap());
		
		return "log/logList";
	}
}
