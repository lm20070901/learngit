package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianwen.dcdp.pojo.Groups;

public interface IGroupsDao {
    int deleteByPrimaryKey(Integer groupId);

    int insert(Groups record);

    int insertSelective(Groups record);

    Groups selectByPrimaryKey(Integer groupId);

    int updateByPrimaryKeySelective(Groups record);

    int updateByPrimaryKey(Groups record);
    
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
	List<Groups> selectPageList(Map<String, Object> whereMap);
	/**
	 * 批量审核
	 * @param idList
	 * @param value
	 */
	void updateState(@Param("ids")List<Integer> ids, @Param("value")int value);
	/**
	 * 批量删除
	 * @param articleIds
	 * @return
	 */
	void batchDelete(List<Integer> ids);
	/**
	 * 批量删除热门小组
	 * @param articleIds
	 * @return
	 */
	void batchDelHot(List<Integer> ids);
	/**
	 * 禁用/解禁
	 * @param ids
	 * @param value
	 */
	int updateForbid(@Param("ids")List<Integer> ids,@Param("value")int value);
	/**
	 * 查询是否启用
	 * @param ids
	 * @return
	 */
	List<Integer> selectForbid(List<Integer> ids); 
	/**
	 * 小组成员查询
	 * @param groupId
	 * @return
	 */
	List<Map<String,Object>> selectUsers(Map<String, Object> map);
	/**
     * 获取总记录数
     * @param whereMap
     * @return
     */
	int selectUsersCount(Map<String, Object> map);
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
	void updateOrderNum(@Param("groupId")Integer groupId,@Param("orderNum")Integer orderNum);
	
	/**
	 * 查找所有
	 * @return
	 */
	List<Groups> selectAll();
	/**
	 * 根据ids获取list
	 * @param articleIds
	 * @return
	 */
	List<Groups> selectByIds(List<Integer> ids);
	
}