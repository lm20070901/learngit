package com.tianwen.dcdp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.pojo.CrawlerConfig;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.ICrawlerService;

@RequestMapping("/crawler")
@Controller
public class CrawlerConfigController {

	@Autowired
	private ICrawlerService crawlerService;
	
	@RequestMapping("/getConfigBySiteId")
	@ResponseBody
	public Map<String, Object> getConfigBySiteId(@RequestParam(value="id", required=false)Long id,
			Model model) {
		ResponseResult result = new ResponseResult();
		if(id == null) {
			return result.returnNeedParams();
		}
		List<CrawlerConfig> datas = new ArrayList<CrawlerConfig>();
		try {
			datas = crawlerService.getConfigBySiteId(id);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		CrawlerConfig data = null;
		if(datas.size() > 0) {
			data = datas.get(0);
			data.setFieldConfig(replaceHtml(data.getFieldConfig()));
			/*data.setPagingRules(replaceHtml(data.getPagingRules()));
			data.setFilterRegular(replaceHtml(data.getFilterRegular()));
			data.setHeadOptions(replaceHtml(data.getHeadOptions()));
			data.setWebsiteAdress(replaceHtml(data.getWebsiteAdress()));*/
		}
		result.setReturnObject("data", data);
		return result.returnSuccess();
	}
	
	public String replaceHtml(String str) {
		str = str.replace("&amp;", "&").replace("&gt;", ">").replace("&lt;", "<").replace("&quot;", "\"")
				.replace("&amp;", "&");
		return str;
	}
	
	@RequestMapping("/setCrawlerConfig")
	@ResponseBody
	public Map<String, Object> setCrawlerConfig(CrawlerConfig config) {
		ResponseResult result = new ResponseResult();
		try {
			if(config.getId() != null) {
				crawlerService.updateConfig(config);
			} else {
				crawlerService.addConfig(config);
			}
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG + ">>" + e.getMessage());
		}
		
		result.setReturnObject("newId", config.getId());
		return result.returnSuccess();
	}
}
