package com.tianwen.dcdp.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.dao.IProjectDao;
import com.tianwen.dcdp.pojo.Admin;
import com.tianwen.dcdp.pojo.Project;
import com.tianwen.dcdp.service.IProjectService;


@Service("projectService")
public class ProjectServiceImpl implements IProjectService {
	
	@Resource
	IProjectDao projectDao;
	
	@Override
	public PageList<Map<String, Object>> getProjectList(Map<String, Object> whereMap, PageBounds pageBounds) {
		
		return projectDao.getProjectList(whereMap,pageBounds);
	}

	@Override
	public Project getProjectById(Integer projectId) {
		
		return projectId == null ? null :projectDao.selectByPrimaryKey(projectId);
	}

	@Override
	@Transactional
	public void saveProject(Project project) {
		if(null == project.getProjectId()){
			project.setIsAdmin(true);//默认是管理员创建 因为 用户发起的项目 不予许编辑
			project.setCreatTime(Calendar.getInstance().getTimeInMillis());
			Admin admin = (Admin)SecurityUtils.getSubject().getPrincipal();
			project.setCreatId(admin.getUserId());
			projectDao.insertSelective(project);
		}else{
			projectDao.updateByPrimaryKeySelective(project);
		}
		
	}

	@Override
	@Transactional
	public void updateStatus(List<Integer> idList) {
		
		projectDao.updateStatus(idList);
	}

	@Override
	@Transactional
	public void deleteProjectByIds(List<Integer> idList) {
		
		projectDao.deleteProjectByIds(idList);
	}

	@Override
	@Transactional
	public void changeRecommendStatus(Integer isRecommend, List<Integer> idList) {
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("isRecommend", isRecommend);
		whereMap.put("idList", idList);
		projectDao.changeRecommendStatus(whereMap);
	}

	@Override
	@Transactional
    public void publishProject(List<Integer> idList) {
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("projectStatus", ParamEnum.PublishStatus.PUBLISHED.getValue());
		whereMap.put("idList", idList);
		projectDao.publishProject(whereMap);
	}
	
}
