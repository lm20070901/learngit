package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IReportDao;
import com.tianwen.dcdp.pojo.Report;
import com.tianwen.dcdp.service.IReportService;

@Service
public class ReportServiceImpl implements IReportService{

	@Autowired
	private IReportDao reportDao;

	@Override
	public int selectTotalCount(Map<String, Object> whereMap) {
		return reportDao.selectTotalCount(whereMap);
	}

	@Override
	public List<Report> selectPageList(Map<String, Object> whereMap) {
		return reportDao.selectPageList(whereMap);
	}

	@Override
	public void batchDelete(List<Integer> ids) {
		reportDao.batchDelete(ids);
		
	}

	@Override
	public Report selectById(Integer id) {
		return reportDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateState(Integer isHandel,
			Integer id) {
		return reportDao.updateState(isHandel, id);
	}

}
