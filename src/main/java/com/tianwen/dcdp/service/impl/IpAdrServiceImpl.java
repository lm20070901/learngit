package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IIpAdrDao;
import com.tianwen.dcdp.pojo.IpAdr;
import com.tianwen.dcdp.service.IIpAdrService;

@Service
public class IpAdrServiceImpl implements IIpAdrService{

	@Autowired
	private IIpAdrDao ipAdrDao;

	@Override
	public int selectTotalCount(Map<String, Object> whereMap) {
		return ipAdrDao.selectTotalCount(whereMap);
	}

	@Override
	public List<IpAdr> selectPageList(Map<String, Object> whereMap) {
		return ipAdrDao.selectPageList(whereMap);
	}

	@Override
	public void batchDelete(List<Integer> ids) {
		ipAdrDao.batchDelete(ids);
	}

	@Override
	public IpAdr selectById(Integer id) {
		return ipAdrDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateState(List<Integer> ids,Integer value) {
		return ipAdrDao.updateState(ids, value);
	}

	@Override
	public int add(IpAdr ipAdr) {
		return ipAdrDao.insertSelective(ipAdr);
	}

	@Override
	public IpAdr selectByName(String name) {
		return ipAdrDao.selectByName(name);
	}

}
