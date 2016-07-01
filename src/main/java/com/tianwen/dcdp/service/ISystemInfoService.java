package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Content;

public interface ISystemInfoService {

	int getTotalCount(Map<String, Object> whereMap);

	List<Map<String, Object>> getSystemInfoList(Map<String, Object> whereMap);

	void saveSystemInfo(Content whereMap);

	void deleteSystemInfoByIds(List<Integer> idList);
	
	
	
	

}
