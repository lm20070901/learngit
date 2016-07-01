package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianwen.dcdp.pojo.Report;

public interface IReportService {

	/**
     * 获取总记录数
     * @param whereMap
     * @return
     */
	int selectTotalCount(Map<String, Object> whereMap);

	/**
	 * 分页获取小组列表
	 * @param whereMap
	 * @return
	 */
	List<Report> selectPageList(Map<String, Object> whereMap);
	/**
	 * 根据ID，批量删除
	 * @param ids
	 */
	void batchDelete(List<Integer> ids);
	/**
	 * 根据主键得到对象
	 * @param id
	 * @return
	 */
	Report selectById(Integer id);
	/**
	 * 修改是否处理的状态
	 * @param isHandel
	 * @param id
	 * @return
	 */
	int  updateState(@Param(value="isHandel")Integer isHandel,@Param(value="id")Integer id);
}
