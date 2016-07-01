package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.dao.base.BaseDao;
import com.tianwen.dcdp.pojo.Project;

public interface IProjectDao extends BaseDao<Project>{
	
    int deleteByPrimaryKey(Integer projectId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer projectId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
    
    /**
     * 工程列表
     * @param whereMap
     * @param pageBounds
     * @return
     */
	PageList<Map<String, Object>> getProjectList(Map<String, Object> whereMap,PageBounds pageBounds);
	
	/**
	 * 修改显示状态
	 * @param idList
	 */
	void updateStatus(List<Integer> idList);
	
	/**
	 * 批量删除
	 * @param idList
	 */
	void deleteProjectByIds(List<Integer> idList);
	
	/**
	 * 推荐/移除推荐状态
	 * @param whereMap
	 */
	void changeRecommendStatus(Map<String, Object> whereMap);
	
	/**
	 * 发布项目
	 * @param whereMap
	 */
	void publishProject(Map<String, Object> whereMap);
}