package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.AdminLog;

public interface IAdminLogDao {
    int deleteByPrimaryKey(Integer logId);

    int insertSelective(AdminLog record);

    AdminLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(AdminLog record);

    /**
     * 查询记录总数
     * @param whereMap
     * @return
     */
	int selectTotalCount(Map<String, Object> whereMap);

	/**
	 * 查询记录列表
	 * @param whereMap
	 * @return
	 */
	List<AdminLog> selectLogList(Map<String, Object> whereMap);
}