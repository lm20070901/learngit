package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.IpAdr;

public interface IIpAdrService {

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
	List<IpAdr> selectPageList(Map<String, Object> whereMap);
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
	IpAdr selectById(Integer id);
	/**
	 * 修改是否处理的状态
	 * @param isHandel
	 * @param id
	 * @return
	 */
	int  updateState(List<Integer> ids,Integer value);
	/**
	 * 新增
	 * @param ipAdr
	 * @return
	 */
	int add(IpAdr ipAdr);
	/**
	 * 根据Ip地址得到对象
	 * @param id
	 * @return
	 */
	IpAdr selectByName(String name);
}
