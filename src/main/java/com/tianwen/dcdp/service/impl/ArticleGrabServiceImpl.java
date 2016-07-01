package com.tianwen.dcdp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IArticleGrabDao;
import com.tianwen.dcdp.pojo.ArticleGrab;
import com.tianwen.dcdp.service.IArticleGrabService;

@Service
public class ArticleGrabServiceImpl implements IArticleGrabService {

	@Autowired
	private IArticleGrabDao articleGrabDao;

	@Override
	public int getTotalCount(Map<String, Object> whereMap) {
		return articleGrabDao.selectTotalCount(whereMap);
	}

	@Override
	public List<ArticleGrab> getPageList(Map<String, Object> whereMap) {
		return articleGrabDao.selectPageList(whereMap);
	}

	@Override
	public void updateState(List<Integer> ids, int value) {
		articleGrabDao.updateState(ids, value);
	}

	@Override
	public ArticleGrab getById(int contentId) {
		return articleGrabDao.selectByPrimaryKey(contentId);
	}

	@Override
	public void batchDelete(List<Integer> ids) {
		articleGrabDao.batchDelete(ids);
	}

	@Override
	@Transactional
	public void publish(ArticleGrab articleGrab,Integer flag) {
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(articleGrab.getContentId());
		articleGrabDao.updateByPrimaryKeySelective(articleGrab);
		articleGrabDao.updateState(idList, flag); //flag = 1 发布，flag =3 未通过
	}

	@Override
	public void updateShowStatus(List<Integer> ids, int value) {
		articleGrabDao.updateShowStatus(ids, value);
	}

	@Override
	public Integer addArticleGrab(ArticleGrab article) {
		return articleGrabDao.insertSelective(article);
	}

}
