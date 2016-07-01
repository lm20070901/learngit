package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.pojo.PageView;

public interface IPageViewDao {
    int deleteByPrimaryKey(Integer reportId);

    int insert(PageView record);

    int insertSelective(PageView record);

    PageView selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(PageView record);

    int updateByPrimaryKey(PageView record);
    
    /**
     * 计算访问列表数量
     * @param whereMap
     * @return
     */
	int countPageViewNum(Map<String, Object> whereMap);
	
	/**
	 * 访问统计列表 
	 * @param whereMap
	 * @return
	 */
	List<PageView> getPageViewList(Map<String, Object> whereMap);
	/**
	 * <!-- 测试  分页插件 -->
	 * @param whereMap
	 * @param pageBounds
	 * @return
	 */
	PageList<PageView> getPageViewByPage(Map<String, Object> whereMap,PageBounds pageBounds);
	
	List<PageView> getPageViewByPage(Map<String, Object> whereMap);
}