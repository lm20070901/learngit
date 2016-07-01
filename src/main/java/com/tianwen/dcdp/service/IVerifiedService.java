package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.pojo.Verified;

public interface IVerifiedService {

	void saveVerifiedInfo(Verified verified);
	
	PageList<Map<String, Object>> getByPage(Map<String, Object> whereMap,PageBounds pageBounds);

	Map<String, Object> queryVerifiedDetail(Integer id);
	
	List<Verified> getVerifiedInfo(Integer userId);
	
	
	
	
	
}
