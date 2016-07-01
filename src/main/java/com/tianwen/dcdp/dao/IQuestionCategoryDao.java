package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.QuestionCategory;

public interface IQuestionCategoryDao {
    int deleteByPrimaryKey(Integer categoryId);

    int insertSelective(QuestionCategory record);

    QuestionCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(QuestionCategory record);

    /**
     * 查询总记录数
     * @return
     */
	int selectTotalCount(Integer modelId);

	/**
	 * 分页查询问题分类列表
	 * @param whereMap
	 * @return
	 */
	List<QuestionCategory> selectPageCategory(Map<String, Object> whereMap);

	/**
	 * 批量删除问题分类
	 * @param ids
	 */
	void batchDeleteById(List<Integer> ids);

}