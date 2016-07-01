package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.PointRule;

public interface IPointRuleDao {
    int deleteByPrimaryKey(Integer ruleId);

    int insertSelective(PointRule record);

    PointRule selectByPrimaryKey(Integer ruleId);

    int updateByPrimaryKeySelective(PointRule record);

    /**
     * 查询记录总数
     * @return
     */
	int selectTotalCount();

	/**
	 * 查询记录列表
	 * @return
	 */
	List<PointRule> selectList(Map<String, Object> whereMap);

	/**
	 * 批量删除记录
	 * @param ids
	 */
	void batchDelete(List<Integer> ids);

}