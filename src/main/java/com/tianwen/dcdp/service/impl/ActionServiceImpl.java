package com.tianwen.dcdp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianwen.dcdp.dao.IActionDao;
import com.tianwen.dcdp.pojo.Action;
import com.tianwen.dcdp.service.IActionService;


@Service("actionService")
public class ActionServiceImpl implements IActionService {

	@Resource
	private IActionDao actionDao;
	
	@Override
	public List<Action> getActionListByMap(Map<String, Object> whereMap) {
		
		return actionDao.getActionListByMap(whereMap);
	}

	@Override
	@Transactional
	public void distributeAction(Map<String, Object> whereMap) {
		
		//Integer roleId = Integer.valueOf(String.valueOf(whereMap.get("roleId")));
		Map<String, Object> deleteMap = new HashMap<String,Object>();
		deleteMap.put("roleId", whereMap.get("roleId"));
		//先删除原先的 关系 
		actionDao.delteActionAndRoleByActionIds(deleteMap);
		
		actionDao.saveRoleAndAction(whereMap);
	}

	@Override
	public int countActionListNum(Map<String, Object> whereMap) {
	
		return actionDao.countActionListNum(whereMap);
	}

	@Override
	public Action getActionById(Integer actionId) {
		
		return actionDao.selectByPrimaryKey(actionId);
	}

	@Override
	@Transactional
	public void saveAction(Action action) {
		if(null == action.getActionId()){
			int  orderNum = actionDao.selectNextOrderNum(action.getPid()); 
			action.setOrderNum(orderNum);
			actionDao.insert(action);
			//NOTE: 预先生成  需要做到同步 麻烦 每次生成一个角色或者action都需要同步一次记录  
			//actionDao.insertRoleAction(action.getActionId());
		}else{
			actionDao.updateByPrimaryKeySelective(action);
		}
		
	}

	@Override
	@Transactional
	public void deleteActionByIds(List<Integer> idList) {
		// 1. 删除关系角色 
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("idList", idList);
		actionDao.delteActionAndRoleByActionIds(whereMap);
		// 2.删除访问路径
		actionDao.delteActionByIds(idList);
		
	}

	@Override
	public List<Map<String, Object>> queryActionByLevle(
			Map<String, Object> whereMap) {
		
		return actionDao.queryActionByLevle(whereMap);
	}
	
}
