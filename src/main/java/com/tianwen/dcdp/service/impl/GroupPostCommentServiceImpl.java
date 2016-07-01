package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IGroupPostCommentDao;
import com.tianwen.dcdp.pojo.GroupPostComment;
import com.tianwen.dcdp.service.IGroupPostCommentService;

@Service
public class GroupPostCommentServiceImpl implements IGroupPostCommentService{

	@Autowired
	private IGroupPostCommentDao groupPostCommentDao;

	@Override
	public List<GroupPostComment> selectList(Map<String, Object> whereMap) {
		return groupPostCommentDao.selectPageList(whereMap);
	}

	@Override
	public int selectCountList(Map<String, Object> whereMap) {
		return groupPostCommentDao.selectTotalCount(whereMap);
	}

	@Override
	public void batchDelete(List<Integer> ids) {
		groupPostCommentDao.batchDelete(ids);
	}

	@Override
	public void updateState(@Param("ids") List<Integer> ids,
			@Param("value") int value) {
		groupPostCommentDao.updateState(ids, value);
	}

	@Override
	public List<GroupPostComment> listByPostId(Map<String, Object> whereMap) {
		return groupPostCommentDao.listByPostId(whereMap);
	}

	@Override
	public int countByPostId(Map<String, Object> whereMap) {
		return groupPostCommentDao.countByPostId(whereMap);
	}

	@Override
	public GroupPostComment selectById(Integer id) {
		return groupPostCommentDao.selectByPrimaryKey(id);
	}

	

}
