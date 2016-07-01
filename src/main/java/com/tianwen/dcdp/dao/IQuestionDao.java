package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Question;

public interface IQuestionDao {
    int deleteByPrimaryKey(Integer questionId);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(Question record);



    /**
     * 查询问答记录总数
     * @param whereMap
     * @return
     */
	int selectTotalCount(Map<String, Object> whereMap);

	/**
	 * 查询记录列表
	 * @param whereMap
	 * @return
	 */
	List<Question> selectPageQuestion(Map<String, Object> whereMap);

	/**
	 * 修改问题显示与否状态
	 * @param ids
	 */
	void modifyVisibility(List<Integer> ids);

	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDelete(List<Integer> ids);

	/**
	 * 查询热门问答记录总数
	 * @param whereMap
	 * @return
	 */
	int selectHotQuestionTotalCount(Map<String, Object> whereMap);

	/**
	 * 按条件查询热门问题列表
	 * @param whereMap
	 * @return
	 */
	List<Question> selectHotQuestionList(Map<String, Object> whereMap);
	
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