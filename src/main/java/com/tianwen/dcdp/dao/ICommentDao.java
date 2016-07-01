package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Comment;

public interface ICommentDao {

	List<Comment> getCommentList(Map<String, Object> whereMap);

	int getTotalCount(Map<String, Object> whereMap);

	void deleteCommentByIds(List<Integer> idList);

	void hiddenCommentsByIds(List<Integer> idList);
	
	
	Comment selectByPrimaryKey(Integer commentId);
	
	
	
	
}
