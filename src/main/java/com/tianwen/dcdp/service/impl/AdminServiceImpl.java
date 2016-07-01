package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IAdminDao;
import com.tianwen.dcdp.pojo.Admin;
import com.tianwen.dcdp.service.IAdminService;

@Service("adminService")
public class AdminServiceImpl implements IAdminService {
	@Resource
	private IAdminDao adminDao;
	
	public List<Admin> getByPage(Map<String,Object> whereMap) {
		return adminDao.getByPage(whereMap);
	}

	public int getTotalCount(Map<String,Object> whereMap) {
		return adminDao.getTotalCount(whereMap);
	}

	public void insert(Admin admin){
		adminDao.insert(admin);
	}

	public void delete(String userId){
		adminDao.delete(userId);
	}

	public void update(Map<String,Object> whereMap) {
		adminDao.update(whereMap);
	}
}
