package com.tianwen.dcdp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianwen.dcdp.dao.IRoleDao;
import com.tianwen.dcdp.pojo.Role;
import com.tianwen.dcdp.service.IRoleService;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	@Resource
	private IRoleDao roleDao;
	
	public List<Role> getByPage(Map<String,Object> whereMap) {
		return roleDao.getByPage(whereMap);
	}

	public int getTotalCount(Map<String,Object> whereMap) {
		return roleDao.getTotalCount(whereMap);
	}

	public void insert(Role role){
		roleDao.insert(role);
		
	}

	public void delete(int userId){
		roleDao.delete(userId);
	}

	public void update(Map<String,Object> whereMap) {
		roleDao.update(whereMap);
	}

	@Override
	public void saveRole(Role role) {
		if(null  == role) return;
		
		if(0 == role.getRoleId()){
			this.insert(role);
		}else{
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("roleId", role.getRoleId());
			whereMap.put("roleName", role.getRoleName());
			whereMap.put("mark", role.getMark());
			roleDao.update(whereMap);
		}
		
	}

	@Override
	@Transactional
	public void deleteRoleByIds(List<Integer> idList) {
		
		roleDao.deleteRoleByIds(idList);
		
	}


	
}
