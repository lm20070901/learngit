package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Answer;
import com.tianwen.dcdp.pojo.HotQuestion;
import com.tianwen.dcdp.pojo.Question;
import com.tianwen.dcdp.pojo.QuestionCategory;

public interface IQestionService {

	//查询问题分类记录总数
	int getQuestionCatogoryTotalCount(Integer modelId);

	/**
	 * 分页查询
	 * @param whereMap
	 * @return
	 */
	List<QuestionCategory> getAllQuestionCategory(Map<String, Object> whereMap);

	/**
	 * 根据ID，批量删除问题分类
	 * @param ids
	 */
	void batchDeleteQuestionCategory(List<Integer> ids);

	/**
	 * 新增问题分类
	 * @param category
	 */
	Integer createQuestionCategory(QuestionCategory category);

	/**
	 * 根据ID，查询问题分类记录
	 * @param categoryId
	 * @return
	 */
	QuestionCategory getCategoryById(Integer categoryId);

	/**
	 * 更新问题分类信息
	 * @param category
	 * @return
	 */
	Integer updateQuestionCategoryById(QuestionCategory category);

	/**
	 * 按条件查询问题记录数
	 * @param whereMap
	 * @return
	 */
	int getQuestionTotalCount(Map<String, Object> whereMap);

	/**
	 * 按条件分页查询问题记录数
	 * @param whereMap
	 * @return
	 */
	List<Question> getPageQuestion(Map<String, Object> whereMap);

	/**
	 * 修改问题显示与否
	 * @param questionId
	 */
	void modifyQuestionVisibility(List<Integer> ids);

	/**
	 * 批量删除问题记录
	 * @param ids
	 */
	void batchDeleteQuestion(List<Integer> ids);


	/**
	 * 按条件查询热门问题总数
	 * @param whereMap
	 * @return
	 */
	int getHotQuestionTotalCount(Map<String, Object> whereMap);

	/**
	 * 按条件查询热门问题列表
	 * @param whereMap
	 * @return
	 */
	List<Question> getHotQuestionList(Map<String, Object> whereMap);

	/**
	 * 批量删除热门问题
	 * @param ids
	 */
	void batchDeleteHotQuestion(List<Integer> ids);

	/**
	 * 根据ID，查询热门问题详细信息
	 * @param id
	 * @return
	 */
	HotQuestion getHotQuestionById(Integer id);

	/**
	 * 修改热门问题排序
	 * @param question
	 */
	Integer modifyHotQuestionOrder(HotQuestion question);

	/**
	 * 按条件查询answer记录总数
	 * @param whereMap
	 * @return
	 */
	int selectAnswerTotalCount(Map<String, Object> whereMap);

	/**
	 * 按条件查询answer记录列表
	 * @param whereMap
	 * @return
	 */
	List<Answer> getAnswerList(Map<String, Object> whereMap);

	/**
	 * 修改问题回复（评论）显示状态
	 * @param ids
	 */
	void modifyAnswerVisibility(List<Integer> ids);

	/**
	 * 批量删除问题回复（评论）
	 * @param ids
	 */
	void batchDeleteAnswer(List<Integer> ids);
	/**
	 * 根据id获取回答详情
	 * @param id
	 * @return
	 */
	Answer getAnswerById(Integer id);
	/**
	 * 根据id获取问题详情
	 * @param id
	 * @return
	 */
	Question getQuestionById(Integer id);
	
	/**
	 * 查找所有
	 * @return
	 */
	List<Question> selectAll();
	/**
	 * 根据ids获取list
	 * @param articleIds
	 * @return
	 */
	List<Question> selectByIds(List<Integer> ids);

}
