package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianwen.dcdp.pojo.Report;

public interface IReportDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);
    /**
     * 获取总记录数
     * @param whereMap
     * @return
     */
	int selectTotalCount(Map<String, Object> whereMap);

	/**
	 * 分页获取列表
	 * @param whereMap
	 * @return
	 */
	List<Report> selectPageList(Map<String, Object> whereMap);
	/**
	 * 批量删除
	 * @param articleIds
	 * @return
	 */
	void batchDelete(List<Integer> ids);
	/**
	 * 修改是否处理的状态
	 * @param isHandel
	 * @param id
	 * @return
	 */
	int  updateState(@Param(value="isHandel")Integer isHandel,@Param(value="id")Integer id);
}