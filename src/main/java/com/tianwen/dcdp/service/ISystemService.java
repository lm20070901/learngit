package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;
import com.tianwen.dcdp.pojo.System;

public interface ISystemService {

	List<Map<String,Object>> getSystemList(Map<String, Object> queryMap);
	
	
		
}
