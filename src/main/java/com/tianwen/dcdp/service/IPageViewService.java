package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.PageView;

public interface IPageViewService {
	
	

	/**
	 * 统计访问列表数量
	 * @param whereMap
	 * @return
	 */
	int countPageViewNum(Map<String, Object> whereMap);
	
	/**
	 * 访问统计列表
	 * @param whereMap
	 * @return
	 */
	List<PageView> getPageViewList(Map<String, Object> whereMap);

}
