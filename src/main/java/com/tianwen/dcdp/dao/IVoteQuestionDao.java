package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianwen.dcdp.pojo.VoteQuestion;

public interface IVoteQuestionDao {
    int deleteByPrimaryKey(Integer questionId);

    int insert(VoteQuestion record);

    int insertSelective(VoteQuestion record);

    VoteQuestion selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(VoteQuestion record);

    int updateByPrimaryKey(VoteQuestion record);
    
    /**
     * 根据问题ID查询选项
     * @param questionId
     * @return
     */
	List<Map<String, Object>> queryQuestionOptionsById(Integer questionId);
	
	/**
	 * 根据问题ID删除选项
	 * @param questionId
	 */
	void delteQuestionOption(Integer questionId);
	
	/**
	 * 插入问题选项 将选项和问题关联
	 * @param options
	 * @param questionId
	 */
	void inserQuestionOptions(@Param("options")List<String> options,@Param("questionId") Integer questionId);
	
	/**
	 * 根据问题ID 删除问题
	 * @param idList
	 */
	void deleteVoteQuestionByQuestionIds(List<Integer> idList);
	
	/**
	 * 根据问题ID 删除问题选项
	 * @param idList
	 */
	void delteVoteOptionByQuestionIds(List<Integer> idList);
	/**
	 * 根据ID批量删除选项
	 * @param idList
	 */
	void deleteQuestionOptionsByIds(List<Integer> idList);
	
	/**
	 * 保存问题排序
	 * @param idList
	 */
	void saveQuestionOrder(List<Integer> idList);
	
	/**
	 * 根据VOTEID查询选项
	 * @param whereMap
	 * @return
	 */
	List<Map<String, Object>> queryOptionsByMap(Map<String, Object> whereMap);
	
	/**
	 * 查询问答题列表
	 * @param whereMap
	 * @return
	 */
	List<Map<String, Object>> getAnswerList(Map<String, Object> whereMap);
	
	/**
	 * 统计用户回答该问答题的回答数量
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
}