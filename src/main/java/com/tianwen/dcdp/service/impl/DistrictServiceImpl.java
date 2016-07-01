package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IDistrictDao;
import com.tianwen.dcdp.pojo.District;
import com.tianwen.dcdp.service.IDistrictService;

@Service("districtService")
public class DistrictServiceImpl implements IDistrictService{
	
	@Resource
	private IDistrictDao districtDao;
	

	@Override
	public List<District> getProvienceListById(Map<String,Object> queryMap) {
		
		return districtDao.getProvienceListById(queryMap);
	}


	@Override
	public List<District> getCityListByMap(Map<String,Object> queryMap) {
		
		return districtDao.getCityListByMap(queryMap);
	}

}
