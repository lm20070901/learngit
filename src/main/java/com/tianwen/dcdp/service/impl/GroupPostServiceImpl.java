package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IGroupPostDao;
import com.tianwen.dcdp.pojo.GroupPost;
import com.tianwen.dcdp.service.IGroupPostService;

@Service
public class GroupPostServiceImpl implements IGroupPostService{

	@Autowired
	private IGroupPostDao groupPostDao;

	@Override
	public List<GroupPost> selectList(Map<String, Object> whereMap) {
		return groupPostDao.selectList(whereMap);
	}

	@Override
	public int selectCountList(Map<String, Object> whereMap) {
		return groupPostDao.selectCountList(whereMap);
	}

	@Override
	public void batchDelete(List<Integer> ids) {
		groupPostDao.batchDelete(ids);
	}

	@Override
	public void updateState(@Param("ids") List<Integer> ids,
			@Param("value") int value) {
		groupPostDao.updateState(ids, value);
	}

	@Override
	public GroupPost selectById(Integer id) {
		return groupPostDao.selectByPrimaryKey(id);
	}

	

}
