package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.pojo.AdminLog;

public interface IAdminLogService {

	/**
	 * 查询adminLog记录总数
	 * @param whereMap
	 * @return
	 */
	int getLogTotalCount(Map<String, Object> whereMap);

	/**
	 * 查询adminLog记录列表
	 * @param whereMap
	 * @return
	 */
	List<AdminLog> getAdminLogList(Map<String, Object> whereMap);
	
	/**
	 * 新增日志
	 * @param type 操作类型
	 * @param userId  用户ID
	 * @param logInfo 日志信息
	 * @return
	 */
	int addAdminLog(ParamEnum.LogOperType type, Integer userId, String logInfo);

}
