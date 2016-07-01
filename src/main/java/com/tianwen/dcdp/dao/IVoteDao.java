package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Vote;

public interface IVoteDao {
	
	
	int deleteByPrimaryKey(Integer voteId);

	int insert(Vote record);

	int insertSelective(Vote record);

	Vote selectByPrimaryKey(Integer voteId);

	int updateByPrimaryKeySelective(Vote record);
	
	
	int updateByPrimaryKeyWithBLOBs(Vote record);
	
	
	int updateByPrimaryKey(Vote record);
	/**
	 * 统计问卷调查列表数量
	 * @param whereMap
	 * @return
	 */
	int countVoteListNum(Map<String,Object> whereMap);
	/**
	 * 获取问卷调查列表
	 * @param whereMap
	 * @return
	 */
	List<Map<String, Object>> getVoteList(Map<String, Object> whereMap);
	
	/**
	 *根据问卷ID  批量删除问卷调查
	 * @param idList
	 */
	void deleteVoteByIds(List<Integer> idList);
	/**
	 * 批量隐藏动态
	 * @param idList
	 */
	void updateVoteStatus(List<Integer> idList);
	

	/**
	 * 发布动态
	 * @param idList
	 */
	void publishVotes(List<Integer> idList);
	
	/**
	 * 根据问卷ID 删除问卷选项
	 * @param idList
	 */
	void delteVoteOption(List<Integer> idList);
	
	/**
	 * 结束问卷
	 * @param idList
	 */
	void endVotes(List<Integer> idList);
	
	
	/**
	 * 根据问卷调查ID查询调查问题列表
	 * @return
	 */
	List<Map<String, Object>> getVoteQuestionsByMap(Map<String, Object> whereMap);
	
	/**
	 * 统计问卷调查 中问题数量
	 * @param voteId
	 * @return
	 */
	int countQuestionNumById(Integer voteId);
	/**
	 * 根据问卷ID 删除问卷题目
	 * @param idList
	 */
	void delteVoteQuestion(List<Integer> idList);
	
	
	/**
	 * 根据问卷ID  删除问卷调查用户回复
	 * @param idList
	 */
	void delteVoteUser(List<Integer> idList);
	
	
	
}