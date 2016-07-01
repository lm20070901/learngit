package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.ICommentDao;
import com.tianwen.dcdp.pojo.Comment;
import com.tianwen.dcdp.service.ICommentService;


@Service("commentService")
public class CommentServiceImpl implements ICommentService{
	
	@Resource
	private  ICommentDao commentDao;
	
	@Override
	public List<Comment> getCommentList(Map<String, Object> whereMap) {
		
		return commentDao.getCommentList(whereMap);
	}

	@Override
	public int getTotalCount(Map<String, Object> whereMap) {
		
		return commentDao.getTotalCount(whereMap);
	}

	@Override
	public void deleteCommentByIds(List<Integer> idList) {
		commentDao.deleteCommentByIds(idList);
		
	}

	@Override
	public void hiddenCommentsByIds(List<Integer> idList) {
		commentDao.hiddenCommentsByIds(idList);
		
	}

	@Override
	public Comment selectById(Integer id) {
		return commentDao.selectByPrimaryKey(id);
	}
	
}
