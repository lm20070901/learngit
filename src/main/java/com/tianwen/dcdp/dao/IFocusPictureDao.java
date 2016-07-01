package com.tianwen.dcdp.dao;

import java.util.List;

import com.tianwen.dcdp.pojo.FocusPicture;

public interface IFocusPictureDao {
    int deleteByPrimaryKey(Integer picId);

    int insertSelective(FocusPicture record);

    FocusPicture selectByPrimaryKey(Integer picId);

    int updateByPrimaryKeySelective(FocusPicture record);

    /**
     * 获取所有焦点图列表
     * @return
     */
	List<FocusPicture> selectAll();

	/**
	 * 批量删除焦点图记录
	 * @param picIdList
	 */
	void batchDelete(List<Integer> picIdList);
	
	FocusPicture selectByPrimaryKeyWithArticleTitle(Integer picId);
}