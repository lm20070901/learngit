package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.ISystemDao;
import com.tianwen.dcdp.service.ISystemService;

@Service("systemService")
public class SystemServiceImpl implements ISystemService {
	
	@Resource
	private ISystemDao  systemDao;

	@Override
	public List<Map<String,Object>>getSystemList(Map<String, Object> queryMap) {
		
		return systemDao.getSystemList(queryMap);
	}
	
	
	
	
	
}
