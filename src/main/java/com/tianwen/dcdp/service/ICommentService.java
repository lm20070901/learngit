package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Comment;

public interface ICommentService {

	List<Comment> getCommentList(Map<String, Object> whereMap);

	int getTotalCount(Map<String, Object> whereMap);

	void deleteCommentByIds(List<Integer> idList);

	
	void hiddenCommentsByIds(List<Integer> idList);
	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 */
	Comment selectById(Integer id);
	
	

}
