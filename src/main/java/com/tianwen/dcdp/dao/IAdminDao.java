package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Admin;

public interface IAdminDao {
	
	List<Admin> getByPage(Map<String,Object> whereMap);
	
	int getTotalCount(Map<String,Object> whereMap);
	
	void insert(Admin admin);
	
	void delete(String id);

	void update(Map<String,Object> whereMap);
}