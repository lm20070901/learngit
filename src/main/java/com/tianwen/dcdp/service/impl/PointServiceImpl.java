package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IPointRuleDao;
import com.tianwen.dcdp.pojo.PointRule;
import com.tianwen.dcdp.service.IPointService;

@Service
public class PointServiceImpl implements IPointService {

	@Autowired
	private IPointRuleDao pointRuleDao;
	
	@Override
	public int getPointRuleTotalCount() {
		return pointRuleDao.selectTotalCount();
	}

	@Override
	public List<PointRule> getPointRuleList(Map<String, Object> whereMap) {
		return pointRuleDao.selectList(whereMap);
	}

	@Override
	public PointRule getPointRuleById(Integer ruleId) {
		return pointRuleDao.selectByPrimaryKey(ruleId);
	}

	@Override
	public Integer modifyPointRule(PointRule pointRule) {
		return pointRuleDao.updateByPrimaryKeySelective(pointRule);
	}

	@Override
	public Integer createPointRule(PointRule pointRule) {
		return pointRuleDao.insertSelective(pointRule);
	}

	@Override
	public void batchDeletePointRule(List<Integer> ids) {
		pointRuleDao.batchDelete(ids);
	}

}
