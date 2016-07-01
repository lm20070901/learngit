package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IAnswerDao;
import com.tianwen.dcdp.dao.IHotQuestionDao;
import com.tianwen.dcdp.dao.IQuestionCategoryDao;
import com.tianwen.dcdp.dao.IQuestionDao;
import com.tianwen.dcdp.pojo.Answer;
import com.tianwen.dcdp.pojo.HotQuestion;
import com.tianwen.dcdp.pojo.Question;
import com.tianwen.dcdp.pojo.QuestionCategory;
import com.tianwen.dcdp.service.IQestionService;

@Service
public class QuestionServiceImpl implements IQestionService{

	@Autowired
	private IQuestionCategoryDao categoryDao;
	
	@Autowired
	private IQuestionDao questionDao;
	
	@Autowired
	private IHotQuestionDao hotQuestionDao;
	
	@Autowired
	private IAnswerDao answerDao;
	
	@Override
	public int getQuestionCatogoryTotalCount(Integer modelId) {
		return categoryDao.selectTotalCount(modelId);
	}

	@Override
	public List<QuestionCategory> getAllQuestionCategory(Map<String, Object> whereMap) {
		return categoryDao.selectPageCategory(whereMap);
	}

	@Override
	public void batchDeleteQuestionCategory(List<Integer> ids) {
		categoryDao.batchDeleteById(ids);
	}

	@Override
	public Integer createQuestionCategory(QuestionCategory category) {
		return categoryDao.insertSelective(category);
	}

	@Override
	public QuestionCategory getCategoryById(Integer categoryId) {
		return categoryDao.selectByPrimaryKey(categoryId);
	}

	@Override
	public Integer updateQuestionCategoryById(QuestionCategory category) {
		return categoryDao.updateByPrimaryKeySelective(category);
	}

	@Override
	public int getQuestionTotalCount(Map<String, Object> whereMap) {
		return questionDao.selectTotalCount(whereMap);
	}

	@Override
	public List<Question> getPageQuestion(Map<String, Object> whereMap) {
		return questionDao.selectPageQuestion(whereMap);
	}

	@Override
	public void modifyQuestionVisibility(List<Integer> ids) {
		questionDao.modifyVisibility(ids);
	}

	@Override
	public void batchDeleteQuestion(List<Integer> ids) {
		questionDao.batchDelete(ids);
	}


	@Override
	public int getHotQuestionTotalCount(Map<String, Object> whereMap) {
		return questionDao.selectHotQuestionTotalCount(whereMap);
	}

	@Override
	public List<Question> getHotQuestionList(Map<String, Object> whereMap) {
		return questionDao.selectHotQuestionList(whereMap);
	}

	@Override
	public void batchDeleteHotQuestion(List<Integer> ids) {
		hotQuestionDao.batchDelete(ids);
	}

	@Override
	public HotQuestion getHotQuestionById(Integer id) {
		return hotQuestionDao.selectHotQuestionWithTitleById(id);
	}

	@Override
	public Integer modifyHotQuestionOrder(HotQuestion question) {
		return hotQuestionDao.updateSelective(question);
	}

	@Override
	public int selectAnswerTotalCount(Map<String, Object> whereMap) {
		return answerDao.selectTotalCount(whereMap);
	}

	@Override
	public List<Answer> getAnswerList(Map<String, Object> whereMap) {
		return answerDao.selectAnswerList(whereMap);
	}

	@Override
	public void modifyAnswerVisibility(List<Integer> ids) {
		answerDao.updateVisibility(ids);
	}

	@Override
	public void batchDeleteAnswer(List<Integer> ids) {
		answerDao.batchDelete(ids);
	}

	@Override
	public Answer getAnswerById(Integer answerId) {
		return answerDao.selectByPrimaryKey(answerId);
	}

	@Override
	public Question getQuestionById(Integer id) {
		return questionDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Question> selectAll() {
		return questionDao.selectAll();
	}

	@Override
	public List<Question> selectByIds(List<Integer> ids) {
		return questionDao.selectByIds(ids);
	}

}
