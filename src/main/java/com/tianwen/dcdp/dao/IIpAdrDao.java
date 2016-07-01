package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianwen.dcdp.pojo.IpAdr;

public interface IIpAdrDao {
    int deleteByPrimaryKey(Integer id);

    int insert(IpAdr record);

    int insertSelective(IpAdr record);

    IpAdr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IpAdr record);

    int updateByPrimaryKey(IpAdr record);
    
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
	List<IpAdr> selectPageList(Map<String, Object> whereMap);
	/**
	 * 批量删除
	 * @param articleIds
	 * @return
	 */
	void batchDelete(List<Integer> ids);
	/**
     * 修改状态
     * @param ids
     * @param value
     */
    int updateState(@Param("ids")List<Integer> ids, @Param("value")int value);
    /**
     * 根据ip地址获取对象
     * @param ipAddress
     * @return
     */
    IpAdr selectByName(String ipAddress);
}