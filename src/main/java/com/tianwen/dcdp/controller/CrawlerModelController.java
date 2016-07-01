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
import com.tianwen.dcdp.pojo.CrawlerModel;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.ICrawlerService;

@RequestMapping("/crawler")
@Controller
public class CrawlerModelController {

	@Autowired
	private ICrawlerService crawlerService;
	
	@RequestMapping("/main")
	public String toCrawlerPage() {
		return "crawler/main";
	}
	
	@RequestMapping("/getModels")
	@ResponseBody
	public Map<String, Object> getModels(Model model) {
		ResponseResult result = new ResponseResult();
		List<CrawlerModel> data = new ArrayList<CrawlerModel>(); 
		try {
			data = crawlerService.getModels();
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		result.setReturnObject("data", data);
		return result.returnSuccess();
	}
	
	@RequestMapping("/toAddModel")
	public String toAddModel() {
		return "crawler/addModel";
	}
	
	@RequestMapping("/addModel")
	@ResponseBody
	public Map<String, Object> addModel(CrawlerModel craw) {
		ResponseResult result = new ResponseResult();
		craw.setCreateDate(new Date());
		try {
			crawlerService.addModel(craw);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		result.setReturnObject("newId", craw.getId());
		return result.returnSuccess();
	}
	
	@RequestMapping("/deleteModel")
	@ResponseBody
	public Map<String, Object> deleteModel(@RequestParam(value="id", required=false)Long id) {
		ResponseResult result = new ResponseResult();
		if(id == null) {
			return result.returnNeedParams();
		}
		try {
			crawlerService.deleteModelById(id);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		return result.returnSuccess();
	}
	
	@RequestMapping("/toUpdateModel")
	public String toUpdateModel(@RequestParam(value="id", required=false)Long id,
			Model model) {
		CrawlerModel cra = null;
		try {
			cra = crawlerService.getModelById(id);
		} catch(Exception e) {
			
		}
		model.addAttribute("data", cra);
		return "crawler/updateModel";
	}
	
	@RequestMapping("/updateModel")
	@ResponseBody
	public Map<String, Object> updateModel(CrawlerModel cra) {
		ResponseResult result = new ResponseResult();
		try {
			crawlerService.updateModel(cra);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		return result.returnSuccess();
	}
}
