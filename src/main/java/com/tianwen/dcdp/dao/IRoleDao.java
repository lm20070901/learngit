package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Role;

public interface IRoleDao {
	/**
	 * 统计角色列表总数
	 * @param whereMap
	 * @return
	 */
	List<Role> getByPage(Map<String,Object> whereMap);
	
	/**
	 * 角色列表
	 * @param whereMap
	 * @return
	 */
	int getTotalCount(Map<String,Object> whereMap);
	
	void insert(Role role);
	
	void delete(int id);

	void update(Map<String,Object> whereMap);
	
	/**
	 * 批量删除
	 * @param idList
	 */
	void deleteRoleByIds(List<Integer> idList);
	
	

}