package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.tianwen.dcdp.pojo.InviteCode;
import com.tianwen.dcdp.pojo.Vote;
import com.tianwen.dcdp.pojo.VoteQuestion;

public interface IVoteService {
	
	/**
	 * 统计问卷列表数量
	 * @param whereMap
	 * @return
	 */
	int countVoteListNum(Map<String, Object> whereMap);
	
	/**
	 * 获取问卷列表
	 * @param whereMap
	 * @return
	 */
	List<Map<String, Object>> getVoteList(Map<String, Object> whereMap);
	
	/**
	 * 批量更新问卷显示状态
	 * @param idList
	 */
	@Transactional
	void updateVoteShowStatus(List<Integer> idList);
	/**
	 * 批量删除问卷
	 * @param idList
	 */
	@Transactional
	void deleteVoteByIds(List<Integer> idList);
	/**
	 * 获取问卷详情
	 * @param voteId
	 * @return
	 */
	Map<String, Object> getVoteDetail(Integer voteId);
	
	/**
	 * 发布问卷
	 * @param idList
	 */
	@Transactional
	void publishVotes(List<Integer> idList);
	
	/**
	 * 结束问卷
	 * @param idList
	 */
	@Transactional
	void endVotes(List<Integer> idList);
	
	/**
	 * 根据ID 查询问卷调查 
	 * @param voteId
	 * @return
	 */
	Vote queryVoteById(Integer voteId);
	
	
	/**
	 * 根据问卷调查ID查询调查问题列表
	 * @return
	 */
	List<Map<String,Object>> getVoteQuestionsByMap(Map<String,Object> whereMap);
	
	/**
	 * 统计某问卷调查 问题数量
	 * @param voteId
	 * @return
	 */
	int countQuestionNumById(Integer voteId);
	
	/**
	 * 修改或者保存问卷调查
	 * @param vote
	 * @return
	 */
	@Transactional
	Vote saveVote(Vote vote);
	
	/**
	 * 根据问题ID 查询 问题
	 * @param questionId
	 * @return
	 */
	VoteQuestion queryQuestionById(Integer questionId);
	
	/**
	 * 根据问题ID查询选择题选项
	 * @param questionId
	 * @return
	 */
	List<Map<String, Object>> queryQuestionOptionsById(Integer questionId);
	
	/**
	 * 保存问卷调查题目
	 * @param question
	 * @return
	 */
	@Transactional
	VoteQuestion saveVoteQuestion(Map<String,Object> whereMap);
	/**
	 * 批量删除问卷题目
	 * @param idList
	 */
	@Transactional
	void deleteVoteQuestionByQuestionIds(List<Integer> idList);
	
	/**
	 * 批量删除问题选项
	 * @param idList
	 */
	@Transactional
	void deleteQuestionOptionsByIds(List<Integer> idList);
	
	/**
	 * 保存问题排序
	 * @param idList
	 */
	@Transactional
	void saveQuestionOrder(List<Integer> idList);
	
	
	/**
	 * 查询用户回答的填空题列表
	 * @param whereMap
	 * @return
	 */
	List<Map<String, Object>> getAnswerList(Map<String, Object> whereMap);
	
	/**
	 * 统计问答题 用户回答数量
	 * @param whereMap
	 * @return
	 */
	int countAnswerListNum(Map<String, Object> whereMap);
	
	/**
	 * 答卷列表
	 * @param whereMap
	 * @return
	 */
	List<Map<String, Object>> getVoteAnswerList(Map<String, Object> whereMap);
	
	/**
	 * 生成邀请码
	 * @param whereMap
	 */
	void createInviteCode(Map<String, Object> whereMap);
	
	/**
	 * 统计邀请码数量
	 * @param whereMap
	 * @return
	 */
	int countInviteCodeNum(Map<String, Object> whereMap);
	
	/**
	 * 邀请码列表
	 * 
	 * @param whereMap
	 * @return
	 */
	List<InviteCode> getInviteCodeList(Map<String, Object> whereMap);
	
	/**
	 * 删除邀请码
	 * @param idList
	 */
	@Transactional
	void deleteInviteCodeByIds(List<Integer> idList);
	
	/**
	 * 查询邀请码
	 * @param id
	 * @return
	 */
	InviteCode queryInviteCodeById(Integer id);
	
	/**
	 * 编辑保存邀请码
	 * @param inviteCode
	 */
	@Transactional
	void saveInviteCode(InviteCode inviteCode);
	
}
