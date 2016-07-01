package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.PointRule;

public interface IPointService {

	/**
	 * 查询记录总数
	 * @return
	 */
	int getPointRuleTotalCount();

	/**
	 * 查询积分规则记录列表
	 * @param whereMap
	 * @return
	 */
	List<PointRule> getPointRuleList(Map<String, Object> whereMap);

	/**
	 * 根据Id,查询积分规则详情
	 * @param ruleId
	 * @return
	 */
	PointRule getPointRuleById(Integer ruleId);

	/**
	 * 修改积分规则信息
	 * @param pointRule
	 */
	Integer modifyPointRule(PointRule pointRule);

	/**
	 * 新增积分规则
	 * @param pointRule
	 */
	Integer createPointRule(PointRule pointRule);

	/**
	 * 批量删除积分规则
	 * @param ids
	 */
	void batchDeletePointRule(List<Integer> ids);

}
