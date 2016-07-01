package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Action;

public interface IActionDao {
    int deleteByPrimaryKey(Integer actionId);

    int insert(Action record);

    int insertSelective(Action record);

    Action selectByPrimaryKey(Integer actionId);

    int updateByPrimaryKeySelective(Action record);

    int updateByPrimaryKey(Action record);
    
    int selectNextOrderNum(Integer pid);
    
    /**
     * 获取ACTION 列表
     * @param whereMap
     * @return
     */
	List<Action> getActionListByMap(Map<String, Object> whereMap);
	
	/**
	 * 角色和ACTION的关联
	 * @param whereMap
	 */
	void saveRoleAndAction(Map<String, Object> whereMap);
	
	/**
	 * 统计权限列表数量
	 * @param whereMap
	 * @return
	 */
	int countActionListNum(Map<String, Object> whereMap);
	
	/**
	 * 批量删除action
	 * @param idList
	 */
	void delteActionByIds(List<Integer> idList);
	
	/**
	 * 删除角色action关系
	 * @param whereMap
	 */
	void delteActionAndRoleByActionIds(Map<String, Object> whereMap);
	
	/**
	 * 预制角色和权限关系
	 * @param actionId
	 */
	void insertRoleAction(Integer actionId);
	
	/**
	 * 查询下级权限 
	 * @param whereMap
	 * @return
	 */
	List<Map<String, Object>> queryActionByLevle(Map<String, Object> whereMap);
}