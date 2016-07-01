package com.tianwen.dcdp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.dao.IInviteCodeDao;
import com.tianwen.dcdp.dao.IVoteDao;
import com.tianwen.dcdp.dao.IVoteQuestionDao;
import com.tianwen.dcdp.pojo.InviteCode;
import com.tianwen.dcdp.pojo.Vote;
import com.tianwen.dcdp.pojo.VoteQuestion;
import com.tianwen.dcdp.service.IVoteService;

@Service("voteService")
public class VoteServiceImpl implements IVoteService {

	@Resource
	private IVoteDao voteDao;

	@Resource
	private IVoteQuestionDao voteQuestionDao;
	
	@Resource
	private IInviteCodeDao inviteDao;

	@Override
	public int countVoteListNum(Map<String, Object> whereMap) {

		return voteDao.countVoteListNum(whereMap);
	}

	@Override
	public List<Map<String, Object>> getVoteList(Map<String, Object> whereMap) {

		return voteDao.getVoteList(whereMap);
	}

	@Override
	@Transactional
	public void updateVoteShowStatus(List<Integer> idList) {

		voteDao.updateVoteStatus(idList);
	}

	@Override
	@Transactional
	public void deleteVoteByIds(List<Integer> idList) {

		voteDao.delteVoteOption(idList);// 删除问卷题目选项

		voteDao.delteVoteQuestion(idList);// 删除问卷题目
		voteDao.deleteVoteByIds(idList);// 删除问卷
		// 删除用户回复信息
		voteDao.delteVoteUser(idList);// 删除问卷调查用户回复

	}

	@Override
	public Map<String, Object> getVoteDetail(Integer voteId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 问卷信息
		Vote vote = voteDao.selectByPrimaryKey(voteId);
		resultMap.put("vote", vote);

		// 问题信息
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("voteId", voteId);
		List<Map<String, Object>> questionList = voteDao
				.getVoteQuestionsByMap(whereMap);
		resultMap.put("questionList", questionList);

		// 选项信息
		List<Map<String, Object>> optionList = voteQuestionDao
				.queryOptionsByMap(whereMap);
		resultMap.put("optionList", optionList);

		return resultMap;
	}

	@Override
	@Transactional
	public void publishVotes(List<Integer> idList) {

		voteDao.publishVotes(idList);
	}

	@Override
	@Transactional
	public void endVotes(List<Integer> idList) {

		voteDao.endVotes(idList);

	}

	@Override
	public Vote queryVoteById(Integer voteId) {

		return voteDao.selectByPrimaryKey(voteId);
	}

	@Override
	public List<Map<String, Object>> getVoteQuestionsByMap(
			Map<String, Object> whereMap) {

		return voteDao.getVoteQuestionsByMap(whereMap);
	}

	@Override
	public int countQuestionNumById(Integer voteId) {

		return voteDao.countQuestionNumById(voteId);
	}

	@Override
	@Transactional
	public Vote saveVote(Vote vote) {
		if (null == vote)
			return vote;

		if (null == vote.getVoteId()) {// 新增操作

			voteDao.insertSelective(vote);// 修改操作
		} else {
			vote.setVoteTime(null);// 更新操作 创建时间不更新
			vote.setVoteUid(null);// 创建者不更新
			voteDao.updateByPrimaryKeySelective(vote);
		}
		return vote;
	}

	@Override
	public VoteQuestion queryQuestionById(Integer questionId) {

		return voteQuestionDao.selectByPrimaryKey(questionId);
	}

	@Override
	public List<Map<String, Object>> queryQuestionOptionsById(Integer questionId) {

		return voteQuestionDao.queryQuestionOptionsById(questionId);
	}

	@Override
	@Transactional
	public VoteQuestion saveVoteQuestion(Map<String, Object> whereMap) {
		if (null == whereMap)
			return null;

		VoteQuestion question = new VoteQuestion();

		question.setTitle((String) whereMap.get("title"));
		question.setType((Byte) whereMap.get("type"));
		question.setVoteId((Integer) whereMap.get("voteId"));
		question.setQuestionId((Integer) whereMap.get("questionId"));
		String optionsStr = (String) whereMap.get("options");
		List<String> options = null;
		if (!StringUtils.isEmpty(optionsStr)) {
			options = strSplitToStrList(optionsStr);
		}

		if (null == question.getQuestionId()) {// 新增操作

			voteQuestionDao.insertSelective(question);
		} else {
			voteQuestionDao.updateByPrimaryKeySelective(question);// 修改

			voteQuestionDao.delteQuestionOption(question.getQuestionId());// 根据问题ID删除问题选项
		}
		if (question.getType() == ParamEnum.QuestionType.SINGLE_CHOICE
				.getValue()
				|| question.getType() == ParamEnum.QuestionType.MULTI_CHOICE
						.getValue()) {

			if (null != options && options.size() > 0) {
				voteQuestionDao.inserQuestionOptions(options,
						question.getQuestionId());
			}
		}
		return question;
	}

	@Override
	@Transactional
	public void deleteVoteQuestionByQuestionIds(List<Integer> idList) {
		voteQuestionDao.delteVoteOptionByQuestionIds(idList);// 删除选项

		voteQuestionDao.deleteVoteQuestionByQuestionIds(idList);// 删除问题
	}

	@Override
	@Transactional
	public void deleteQuestionOptionsByIds(List<Integer> idList) {
		voteQuestionDao.deleteQuestionOptionsByIds(idList);
	}

	private List<String> strSplitToStrList(String str) {
		List<String> result = new ArrayList<String>();
		String[] strArr = str.split(",");
		for (String st : strArr) {
			result.add(st);
		}
		return result;
	}

	@Override
	@Transactional
	public void saveQuestionOrder(List<Integer> idList) {

		voteQuestionDao.saveQuestionOrder(idList);
	}

	@Override
	public List<Map<String, Object>> getAnswerList(Map<String, Object> whereMap) {

		return voteQuestionDao.getAnswerList(whereMap);
	}

	@Override
	public int countAnswerListNum(Map<String, Object> whereMap) {

		return voteQuestionDao.countAnswerListNum(whereMap);
	}

	@Override
	public List<Map<String, Object>> getVoteAnswerList(
			Map<String, Object> whereMap) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> answerList = voteQuestionDao.getVoteAnswerList(whereMap);
		// 是否需要邀请
		boolean isInvite = String.valueOf(ParamEnum.IsOrNot.YES.getValue())
				.equals(String.valueOf(whereMap.get("isInvite")));

		Map<String, Object> answerMap = null;
		Map<String, Object> answer  = null;
		String userId = "";
		for(int i = 0 ;i<answerList.size();i++){
			answer = answerList.get(i);
			if (!isInvite && !userId.equals(String.valueOf(answer.get("userId")))) {
				userId = String.valueOf(answer.get("userId"));// 不需要邀请码
				if (null != answerMap){
					resultList.add(answerMap);
				}
				answerMap = new LinkedHashMap<String, Object>();
				// answerMap.put("userId", userId);

			} else if (isInvite && !userId.equals(String.valueOf(answer.get("invitCode")))) {
				userId = String.valueOf(answer.get("invitCode"));// 需要邀请码
				if (null != answerMap){
					resultList.add(answerMap);
				}
				//NOTE:  这里没有使用HashMap  是因为 它没有顺序    造成导出的时候 问题的答案顺序不对
				answerMap = new LinkedHashMap<String, Object>();
				// answerMap.put("invitCode", userId);
			}
			// answerMap.put("questionId", answer.get("questionId"));// 问题ID
			answerMap.put(String.valueOf(answer.get("questionId")),answer.get("optionIds"));// 答题内容
			// questionId.equals(answer.get("questionId"));
		}
		
		resultList.add(answerMap);//将最后一个MAP放入
		
		return resultList;
	}

	@Override
	public void createInviteCode(Map<String, Object> whereMap) {
		int num = Integer.parseInt(String.valueOf(whereMap.get("inviteNum")));
		
		List<InviteCode> inviteList = new ArrayList<InviteCode>(num);
		Long timeLine = General.timeStr2Long((String)whereMap.get("timeLine"),"yyyy-MM-dd HH:mm:ss");
		Integer voteId = Integer.parseInt(String.valueOf(whereMap.get("voteId")));
		InviteCode invite = null;
		
		for(int i = 0; i< num ; i++){
			invite = new InviteCode();
			invite.setIsUsed(false);
			invite.setTimeLine(timeLine);
			invite.setVoteId(voteId);
			invite.setInviteCode(General.getRandomStr(6));//生成指定位数的邀请码
			inviteList.add(invite);
		}
		
		inviteDao.batchAddInviteCode(inviteList);
	}

	@Override
	public int countInviteCodeNum(Map<String, Object> whereMap) {
		
		return inviteDao.countInviteCodeNum(whereMap);
	}

	@Override
	public List<InviteCode> getInviteCodeList(Map<String, Object> whereMap) {
		return inviteDao.getInviteCodeList(whereMap);
	}

	@Override
	@Transactional
	public void deleteInviteCodeByIds(List<Integer> idList) {
		
		inviteDao.deleteInviteCodeByIds(idList);
	}

	@Override
	public InviteCode queryInviteCodeById(Integer id) {
		
		return inviteDao.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void saveInviteCode(InviteCode inviteCode) {
		if(null == inviteCode.getId()){
			inviteDao.insertSelective(inviteCode);
		}else{
			inviteDao.updateByPrimaryKeySelective(inviteCode);
		}
		
	}

	
	
	
}
