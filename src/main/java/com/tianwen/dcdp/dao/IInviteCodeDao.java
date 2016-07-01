package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.InviteCode;

public interface IInviteCodeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(InviteCode record);

    int insertSelective(InviteCode record);

    InviteCode selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(InviteCode record);
    /**
     * 批量生成邀请码
     * @param inviteList
     */
    void batchAddInviteCode(List<InviteCode> inviteList);

    /**
     * 统计邀请码数量
     * @param whereMap
     * @return
     */
	int countInviteCodeNum(Map<String, Object> whereMap);
	
	/**
	 * 获取邀请码列表
	 * @param whereMap
	 * @return
	 */
	List<InviteCode> getInviteCodeList(Map<String, Object> whereMap);
	
	
	/**
	 * 删除邀请码
	 * @param idList
	 */
	void deleteInviteCodeByIds(List<Integer> idList);
	
}