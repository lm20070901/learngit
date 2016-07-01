package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IPageViewDao;
import com.tianwen.dcdp.pojo.PageView;
import com.tianwen.dcdp.service.IPageViewService;


@Service("pageViewService")
public class PageViewServiceImpl implements IPageViewService {
	
	@Resource
	private IPageViewDao pageViewDao;
	

	@Override
	public int countPageViewNum(Map<String, Object> whereMap) {
		
		return pageViewDao.countPageViewNum(whereMap);
	}

	@Override
	public List<PageView> getPageViewList(Map<String, Object> whereMap) {
		
		return pageViewDao.getPageViewList(whereMap);
	}

}
