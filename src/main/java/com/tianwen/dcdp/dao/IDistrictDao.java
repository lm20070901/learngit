package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.District;

public interface IDistrictDao {

	List<District> getProvienceListById(Map<String,Object> queryMap);

	List<District> getCityListByMap(Map<String,Object> queryMap);
	
	
	
	

}
