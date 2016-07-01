package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.Words;

public interface IWordsService {

	/**
	 * 查询敏感词记录总数
	 * @return
	 */
	int getWordsTotalCount();

	/**
	 * 查询敏感词记录列表
	 * @param whereMap
	 * @return
	 */
	List<Words> getWordsList(Map<String, Object> whereMap);

	/**
	 * 根据ID，查询敏感词信息
	 * @param id
	 * @return
	 */
	Words getWordsById(Integer id);

	/**
	 * 修改敏感词信息
	 * @param words
	 */
	Integer modifyWords(Words words);

	/**
	 * 新增敏感词
	 * @param words
	 */
	Integer createWords(Words words);

	/**
	 * 删除敏感词记录
	 * @param ids
	 */
	void batchDeleteWords(List<Integer> ids);

}
