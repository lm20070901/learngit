package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IWordsDao;
import com.tianwen.dcdp.pojo.Words;
import com.tianwen.dcdp.service.IWordsService;

@Service
public class WordsServiceImpl implements IWordsService {

	@Autowired
	private IWordsDao wordsDao;
	
	@Override
	public int getWordsTotalCount() {
		return wordsDao.selectTotalCount();
	}

	@Override
	public List<Words> getWordsList(Map<String, Object> whereMap) {
		return wordsDao.selectList(whereMap);
	}

	@Override
	public Words getWordsById(Integer id) {
		return wordsDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer modifyWords(Words words) {
		return wordsDao.updateByPrimaryKeySelective(words);
	}

	@Override
	public Integer createWords(Words words) {
		return wordsDao.insertSelective(words);
	}

	@Override
	public void batchDeleteWords(List<Integer> ids) {
		wordsDao.batchDelete(ids);
	}

}
