package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.dao.IActivityDao;
import com.tianwen.dcdp.pojo.Activity;
import com.tianwen.dcdp.service.IActivityService;
@Service
public class ActivityServiceImpl implements IActivityService {

	@Resource
	private IActivityDao activityDao;
	
	@Override
	public int deleteByPrimaryKey(Integer contentId) {
		return activityDao.deleteByPrimaryKey(contentId);
	}

	@Override
	public int insertSelective(Activity record) {
		return activityDao.insertSelective(record);
	}

	@Override
	public Activity selectByPrimaryKey(Integer contentId) {
		return activityDao.selectByPrimaryKey(contentId);
	}

	@Override
	public int updateByPrimaryKeySelective(Activity record) {
		return activityDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public PageList<Activity> selectPageList(Map<String, Object> map,
			PageBounds pageBounds) {
		return activityDao.selectPageList(map, pageBounds);
	}

	@Override
	public int updateState(@Param("ids") List<Integer> ids) {
		return activityDao.updateState(ids);
	}

	@Override
	public void updateShowStatus(@Param("ids") List<Integer> ids,
			@Param("value") int value) {
		activityDao.updateShowStatus(ids, value);
		
	}

	@Override
	public void batchDelete(List<Integer> ids) {
		activityDao.batchDelete(ids);
	}

}
