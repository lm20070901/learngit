package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.District;

public interface IDistrictService {

	/*List<Map<String,Object>> getProvienceListById(Integer provienceId);

	List<Map<String,Object>> getCityListByMap(Map<String,Object> queryMap);*/
	
	List<District> getProvienceListById(Map<String,Object> queryMap);

	List<District> getCityListByMap(Map<String,Object> queryMap);
	
	

}
