package com.tianwen.dcdp.common;

import org.apache.shiro.SecurityUtils;

import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.dao.SpringContextHolder;
import com.tianwen.dcdp.pojo.Admin;
import com.tianwen.dcdp.service.IAdminLogService;

/**
 * 记录操作日志
 * @author yinz
 *
 */
public class LogUtils {

	/**
	 * 记录操作日志
	 * @param type  操作类型
	 * @param logInfo 日志信息
	 */
	public static void log(LogOperType type, String logInfo) {
		try {
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
			if(admin == null) {
				return;
			}
			IAdminLogService logService = SpringContextHolder.getBean("adminLogServiceImpl");
			logService.addAdminLog(type, admin.getUserId(), logInfo);
		} catch(Exception e) {
			
		}
	}
}
