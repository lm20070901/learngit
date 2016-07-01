package com.tianwen.dcdp.dao;

import java.util.List;

import com.tianwen.dcdp.pojo.HotQuestion;

public interface IHotQuestionDao {

    int insertSelective(HotQuestion record);

    /**
     * 批量删除热门问题记录
     * @param ids
     */
	void batchDelete(List<Integer> ids);

	/**
	 * 根据ID，查询热门问题记录
	 * @param id
	 * @return
	 */
	HotQuestion selectHotQuestionWithTitleById(Integer id);

	/**
	 * 修改热门问题信息
	 * @param question
	 * @return
	 */
	Integer updateSelective(HotQuestion question);
}