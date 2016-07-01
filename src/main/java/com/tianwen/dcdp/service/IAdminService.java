package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Admin;

public interface IAdminService {
	public List<Admin> getByPage(Map<String,Object> whereMap);
	
	public int getTotalCount(Map<String,Object> whereMap);
	
	public void insert(Admin admin);
	
	public void delete(String userId);
	
	public void update(Map<String,Object> whereMap);
}
