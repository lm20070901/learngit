package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.pojo.District;
import com.tianwen.dcdp.service.IDistrictService;

@Controller
@RequestMapping("/district")
public class DistrictController {
	
	@Resource
	private IDistrictService  districtService;
	
	
	
	/**
	 * 获取省份
	 * @param provienceId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getProvienceList")
	@ResponseBody
	public Map<String,Object> getProvienceList( 
			@RequestParam( value="provienceId",required = false)Integer provienceId,
			HttpServletRequest request,Model model){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("provienceId", provienceId);
		List<District> districtList = districtService.getProvienceListById(queryMap);
		
		if(null == districtList||districtList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NO_DATAS);
		}else{
			resultMap.put("data", districtList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}
		return resultMap;
	}
	
	/**
	 * 获取市  获取省下面的市 
	 * @param cityId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getCityList")
	@ResponseBody
	public Map<String,Object> getCityList(
			@RequestParam( value="cityId",required = false)Integer cityId,
			@RequestParam( value="provienceId",required = false)Integer provienceId,
			HttpServletRequest request,Model model){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("cityId", cityId);
		queryMap.put("provienceId", provienceId);
		List<District> districtList = districtService.getCityListByMap(queryMap);
		
		if(null == districtList||districtList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NO_DATAS);
		}else{
			resultMap.put("data", districtList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);			
		}
		return resultMap;
	}
}
