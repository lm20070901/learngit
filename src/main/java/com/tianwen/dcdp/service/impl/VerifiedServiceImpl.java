package com.tianwen.dcdp.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.dao.IVerifiedDao;
import com.tianwen.dcdp.pojo.Admin;
import com.tianwen.dcdp.pojo.Verified;
import com.tianwen.dcdp.service.IVerifiedService;

@Service("verifiedService")
public class VerifiedServiceImpl implements IVerifiedService{
	@Resource
	private IVerifiedDao verifiedDao;
	

	@Override
	public void saveVerifiedInfo(Verified verified) {
		Subject subject = SecurityUtils.getSubject();
		
		Admin admin = (Admin)subject.getPrincipal();
		verified.setAuditorId(admin.getUserId());
		
		verifiedDao.saveVerifiedInfo(verified);
	}

	@Override
	public PageList<Map<String, Object>> getByPage(Map<String, Object> whereMap,PageBounds pageBounds) {
		
		return verifiedDao.getByPage(whereMap,pageBounds);
	}

	@Override
	public Map<String, Object> queryVerifiedDetail(Integer id) {
		
		return verifiedDao.queryVerifiedDetail(id);
	}

	@Override
	public List<Verified> getVerifiedInfo(Integer userId) {
		
		return verifiedDao.getVerifiedInfo(userId);
	}

}
