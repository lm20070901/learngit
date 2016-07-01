package com.tianwen.dcdp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.ICrawlerService;

@RequestMapping("/crawler")
@Controller
public class CrawlerExeController {

	@Autowired
	private ICrawlerService crawlerService;
	
	@RequestMapping("/exeCrawler")
	@ResponseBody
	public Map<String, Object> exeCrawler(@RequestParam(value="id", required=false)Long wId) {
		ResponseResult result = new ResponseResult();
		if(wId == null) {
			return result.returnNeedParams();
		}
		try {
			crawlerService.exeCrawler(wId);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		return result.returnSuccess();
	}
}
