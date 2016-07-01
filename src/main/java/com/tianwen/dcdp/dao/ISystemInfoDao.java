package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Content;

public interface ISystemInfoDao {

	int getTotalCount(Map<String, Object> whereMap);

	List<Map<String, Object>> getSystemInfoList(Map<String, Object> whereMap);

	void addSystemInfo(Content content);

	void deleteSystemInfoByIds(List<Integer> idList);

	void updateSystemInfo(Content content);

	void insertAdminContent(Map<String, Object> whereMap);

	void delteAdminContentByIds(List<Integer> idList);
	
	
	
	
}
