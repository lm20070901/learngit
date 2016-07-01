package com.tianwen.dcdp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.pojo.CrawlerWebsite;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.ICrawlerService;

@RequestMapping("/crawler")
@Controller
public class CrawlerWebsiteController {

	@Autowired
	private ICrawlerService crawlerService;
	
	@RequestMapping("/getWebsitesByModelId")
	@ResponseBody
	public Map<String, Object> getWebsitesByModelId(@RequestParam(value="id", required=false)Long id,
			Model model) {
		ResponseResult result = new ResponseResult();
		if(id == null) {
			return result.returnNeedParams();
		}
		List<CrawlerWebsite> data = new ArrayList<CrawlerWebsite>();
		try {
			data = crawlerService.getWebsitesByModelId(id);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		result.setReturnObject("data", data);
		return result.returnSuccess();
	}
	
	@RequestMapping("/toAddWebsite")
	public String toAddWebsite(Model model,
			@RequestParam(value="mId", required=false)Long mId) {
		model.addAttribute("mId", mId);
		return "crawler/addWebsite";
	}
	
	@RequestMapping("/addWebsite")
	@ResponseBody
	public Map<String, Object> addWebsite(CrawlerWebsite website) {
		ResponseResult result = new ResponseResult();
		website.setCreateDate(new Date());
		try {
			crawlerService.addWebsite(website);
		}catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		result.setReturnObject("newId", website.getId());
		return result.returnSuccess();
	}
	
	@RequestMapping("/toUpdateWebsite")
	public String toUpdateWebsite(@RequestParam(value="id", required=false)Long id,
			Model model) {
		CrawlerWebsite website = crawlerService.getWebsiteById(id); 
		model.addAttribute("data", website);
		return "crawler/updateWebsite";
	}
	
	@RequestMapping("/updateWebsite")
	@ResponseBody
	public Map<String, Object> updateWebsite(CrawlerWebsite website) {
		ResponseResult result = new ResponseResult();
		try {
			crawlerService.updateWebsite(website);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		return result.returnSuccess();
	}
	
	@RequestMapping("/deleteWebsite")
	@ResponseBody
	public Map<String, Object> deleteWebsite(@RequestParam(value="id", required=false)Long id) {
		ResponseResult result = new ResponseResult();
		try {
			crawlerService.deleteWebsiteById(id);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		return result.returnSuccess();
	}
}
