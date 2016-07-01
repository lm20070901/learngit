package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.dao.IAdminLogDao;
import com.tianwen.dcdp.pojo.AdminLog;
import com.tianwen.dcdp.service.IAdminLogService;

@Service
public class AdminLogServiceImpl implements IAdminLogService {

	@Autowired
	private IAdminLogDao adminLogDao;
	
	@Override
	public int getLogTotalCount(Map<String, Object> whereMap) {
		return adminLogDao.selectTotalCount(whereMap);
	}

	@Override
	public List<AdminLog> getAdminLogList(Map<String, Object> whereMap) {
		return adminLogDao.selectLogList(whereMap);
	}

	@Override
	public int addAdminLog(LogOperType type, Integer userId, String logInfo) {
		AdminLog log = new AdminLog();
		log.setLogInfo(General.isEmpty(logInfo) ? "" : logInfo);
		log.setLogTime(System.currentTimeMillis());
		log.setOperationType((byte) type.getValue());
		log.setUserId(userId);
		return adminLogDao.insertSelective(log);
	}

}
