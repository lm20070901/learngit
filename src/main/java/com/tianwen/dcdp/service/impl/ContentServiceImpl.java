package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IContentDao;
import com.tianwen.dcdp.pojo.Content;
import com.tianwen.dcdp.service.IContentService;

@Service
public class ContentServiceImpl implements IContentService {

	@Autowired
	private IContentDao contentMapper;
	
	@Override
	public int getContentTotalCount(Map<String, Object> whereMap) {
		
		return contentMapper.selectTotalCount(whereMap);
	}

	@Override
	public List<Content> getByPage(Map<String, Object> whereMap) {
		
		return contentMapper.selectByPage(whereMap);
	}

	@Override
	public void deleteContentByIds(List<Integer> idList) {
		contentMapper.deleteContentByIds(idList);
		
	}

	@Override
	public void hiddenContents(List<Integer> idList) {
		contentMapper.updateContentsStatus(idList);
	}

	@Override
	public Content selectById(Integer id) {
		return contentMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Content> selectAll() {
		return contentMapper.selectAll();
	}

	@Override
	public List<Content> selectByIds(List<Integer> ids) {
		return contentMapper.selectByIds(ids);
	}

}
