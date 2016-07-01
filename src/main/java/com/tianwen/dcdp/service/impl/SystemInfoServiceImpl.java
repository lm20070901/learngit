package com.tianwen.dcdp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.ISystemInfoDao;
import com.tianwen.dcdp.pojo.Admin;
import com.tianwen.dcdp.pojo.Content;
import com.tianwen.dcdp.service.ISystemInfoService;

@Service("systemInfoService")
public class SystemInfoServiceImpl implements ISystemInfoService{
	
	
	@Resource
	private ISystemInfoDao systemDao;

	@Override
	public int getTotalCount(Map<String, Object> whereMap) {
		
		return systemDao.getTotalCount(whereMap);
	}

	@Override
	public List<Map<String, Object>> getSystemInfoList(Map<String, Object> whereMap) {
		
		return systemDao.getSystemInfoList(whereMap);
	}

	@Override
	public void  saveSystemInfo(Content content) {
	
		if(null == content.getContentId() || 0 == content.getContentId() ){
			systemDao.addSystemInfo(content);
			Map<String,Object> whereMap = new HashMap<String,Object>();
			Admin admin = (Admin)SecurityUtils.getSubject().getPrincipal();
			whereMap.put("adminId", admin.getUserId());
			whereMap.put("contentId",content.getContentId());
			systemDao.insertAdminContent(whereMap);//往中间表中插入关联关系
		}else{
			systemDao.updateSystemInfo(content);
		}
		  
	}

	@Override
	public void deleteSystemInfoByIds(List<Integer> idList) {
		
		systemDao.deleteSystemInfoByIds(idList);
		systemDao.delteAdminContentByIds(idList);
	}
		
}
