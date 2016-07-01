package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Content;

public interface IContentService {

	//获取动态总记录数
	int getContentTotalCount(Map<String, Object> whereMap);

	List<Content> getByPage(Map<String, Object> whereMap);

	void deleteContentByIds(List<Integer> idList);

	void hiddenContents(List<Integer> idList);
	/**
	 * 根据ID获取详情
	 * @param id
	 * @return
	 */
	Content selectById(Integer id);
	/**
	 * 查找所有
	 * @return
	 */
	List<Content> selectAll();
	/**
	 * 根据ids获取list
	 * @param articleIds
	 * @return
	 */
	List<Content> selectByIds(List<Integer> ids);
}
 