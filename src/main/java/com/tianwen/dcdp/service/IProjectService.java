package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.pojo.Project;

public interface IProjectService {

	PageList<Map<String, Object>> getProjectList(Map<String, Object> whereMap,PageBounds pageBounds);
	
	Project getProjectById(Integer projectId);
	
	@Transactional
	void saveProject(Project project);
	
	@Transactional
	void updateStatus(List<Integer> idList);
	
	@Transactional
	void deleteProjectByIds(List<Integer> idList);
	
	
	@Transactional
	void changeRecommendStatus(Integer isRecommend, List<Integer> idList);
	
	
	@Transactional
	void publishProject(List<Integer> idList);
	
	

	
		
}
