package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.tianwen.dcdp.pojo.Role;

public interface IRoleService {
	/**
	 * 角色列表
	 * @param whereMap
	 * @return
	 */
	public List<Role> getByPage(Map<String,Object> whereMap);
	
	/**
	 * 角色列表总数
	 * @param whereMap
	 * @return
	 */
	public int getTotalCount(Map<String,Object> whereMap);
	
	
	public void insert(Role role);
	
	public void delete(int userId);
	
	public void update(Map<String,Object> whereMap);
	
	/**
	 * 保存角色
	 * @param role
	 */
	public void saveRole(Role role);
	
	/**
	 * 批量删除角色
	 * @param idList
	 */
	@Transactional
	public void deleteRoleByIds(List<Integer> idList);
	
	
}
