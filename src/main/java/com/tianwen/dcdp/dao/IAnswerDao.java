package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Answer;

public interface IAnswerDao {
    int deleteByPrimaryKey(Integer answerId);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer answerId);

    int updateByPrimaryKeySelective(Answer record);

    /**
     * 按条件查询记录总数
     * @param whereMap
     * @return
     */
	int selectTotalCount(Map<String, Object> whereMap);

	/**
	 * 按条件查询记录列表
	 * @param whereMap
	 * @return
	 */
	List<Answer> selectAnswerList(Map<String, Object> whereMap);

	/**
	 * 修改记录显示状态
	 * @param ids
	 */
	void updateVisibility(List<Integer> ids);

	/**
	 * 根据ID，批量删除
	 * @param ids
	 */
	void batchDelete(List<Integer> ids);

}