package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Groups;

public interface IGroupsService {

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
	List<Groups> selectPageList(Map<String, Object> whereMap);
	
	void updateState(List<Integer> ids, int value) throws Exception;
	
	int updateForBid(List<Integer> ids,int value) throws Exception;
	
	void update(Groups group);
	
	void add(Groups group);
	
	Groups getById(Integer id);
	
	/**
	 * 根据ID，批量删除
	 * @param ids
	 */
	void batchDelete(List<Integer> ids);
	/**
	 * 小组成员查询
	 * @param groupId
	 * @return
	 */
	List<Map<String,Object>> selectUsers(Map<String, Object> whereMap);
	/**
     * 获取小组成员总记录数
     * @param whereMap
     * @return
     */
	int selectUsersCount(Map<String, Object> whereMap);
	/**
	 * 根据id和名称获取
	 * @param map
	 * @return
	 */
	int selectByName(Groups group);
	/**
     * 获取热门小组总记录数
     * @param whereMap
     * @return
     */
	int selectHotCount(Map<String, Object> whereMap);

	/**
	 * 热门小组分页获取列表
	 * @param whereMap
	 * @return
	 */
	List<Groups> selectHotList(Map<String, Object> whereMap);
	/**
	 * 批量删除热门小组
	 * @param articleIds
	 * @return
	 */
	void batchDelHot(List<Integer> ids);
	/**
	 * 根据小组ID得到该热门小组名称
	 * @param groupId
	 * @return
	 */
	Map<String,Object> selectHotById(Integer groupId);
	/**
	 *  热门小组编辑排序
	 * @param groupId
	 * @param orderNum
	 */
	void updateOrderNum(Integer groupId,Integer orderNum);
}
