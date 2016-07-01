package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.tianwen.dcdp.pojo.Action;




public interface IActionService {

	/**
	 * 获取权限列表
	 * @param whereMap
	 * @return
	 */
	List<Action> getActionListByMap(Map<String, Object> whereMap);
	
	/**
	 * 分配权限
	 * @param whereMap
	 */
	@Transactional
	void distributeAction(Map<String, Object> whereMap);
	
	/**
	 * 统计权限列表数量
	 * @param whereMap
	 * @return
	 */
	int countActionListNum(Map<String, Object> whereMap);
	
	/**
	 * 根据ID查询
	 * @param actionId
	 * @return
	 */
	Action getActionById(Integer actionId);
	
	/**
	 * 新增或者修改 action
	 * @param action
	 */
	@Transactional
	void saveAction(Action action);
	
	/**
	 * 批量删除action 
	 * @param idList
	 */
	@Transactional
	void deleteActionByIds(List<Integer> idList);
	
	/**
	 * 权限树 查询下级权限 
	 * @param whereMap
	 * @return
	 */
	List<Map<String, Object>> queryActionByLevle(Map<String, Object> whereMap);
	
	
}
