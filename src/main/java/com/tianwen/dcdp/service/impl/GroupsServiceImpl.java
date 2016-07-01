package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.common.GroupsState;
import com.tianwen.dcdp.dao.IGroupsDao;
import com.tianwen.dcdp.pojo.Groups;
import com.tianwen.dcdp.service.IGroupsService;

@Service
public class GroupsServiceImpl implements IGroupsService{

	@Autowired
	private IGroupsDao groupsDao;

	@Override
	public int selectTotalCount(Map<String, Object> whereMap) {
		return groupsDao.selectTotalCount(whereMap);
	}

	@Override
	public List<Groups> selectPageList(Map<String, Object> whereMap) {
		return groupsDao.selectPageList(whereMap);
	}

	@Override
	public void updateState(List<Integer> ids, int value) throws Exception {
		for(Integer id :ids){
			Groups group = groupsDao.selectByPrimaryKey(id);
			if(group.getState().equals(GroupsState.NOT_PASSED)){  //若存在被拒绝状态
				throw new Exception("id为"+id+"的小组为被拒绝状态，不可审核");
			}
		}
		groupsDao.updateState(ids, value);
	}

	@Override
	public void update(Groups group) {
		groupsDao.updateByPrimaryKeySelective(group);
	}

	@Override
	public void add(Groups group) {
		groupsDao.insertSelective(group);
		
	}

	@Override
	public Groups getById(Integer id) {
		return groupsDao.selectByPrimaryKey(id);
	}

	@Override
	public void batchDelete(List<Integer> ids) {
		groupsDao.batchDelete(ids);
		
	}

	//先查出是否全为禁用or 解禁，然后再统一操作
	@Override
	public int updateForBid(List<Integer> ids,int value){
		return groupsDao.updateForbid(ids, value);
	}

	@Override
	public List<Map<String, Object>> selectUsers(Map<String, Object> map) {
		return groupsDao.selectUsers(map);
	}

	@Override
	public int selectUsersCount(Map<String, Object> map) {
		return groupsDao.selectUsersCount(map);
	}

	@Override
	public int selectByName(Groups group){
		return groupsDao.selectByName(group);
	}

	@Override
	public int selectHotCount(Map<String, Object> whereMap) {
		return groupsDao.selectHotCount(whereMap);
	}

	@Override
	public List<Groups> selectHotList(Map<String, Object> whereMap) {
		return groupsDao.selectHotList(whereMap);
	}

	@Override
	public void batchDelHot(List<Integer> ids) {
		groupsDao.batchDelHot(ids);
	}

	@Override
	public void updateOrderNum(Integer groupId, Integer orderNum) {
		groupsDao.updateOrderNum(groupId, orderNum);
	}

	@Override
	public Map<String,Object> selectHotById(Integer groupId) {
		return groupsDao.selectHotById(groupId);
	}

}
