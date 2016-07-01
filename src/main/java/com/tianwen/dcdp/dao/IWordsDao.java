package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Words;

public interface IWordsDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Words record);

    Words selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Words record);

    /**
     * 查询记录总数
     * @return
     */
	int selectTotalCount();

	/**
	 * 查询记录列表
	 * @param whereMap
	 * @return
	 */
	List<Words> selectList(Map<String, Object> whereMap);

	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDelete(List<Integer> ids);
}