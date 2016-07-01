package com.tianwen.dcdp.service;

import java.util.List;

import com.tianwen.dcdp.pojo.FocusPicture;

public interface IFocusPictureService {

	/**
	 * 获取焦点图列表
	 * @return
	 */
	List<FocusPicture> getFocusPictureList();

	/**
	 * 批量删除焦点图记录
	 * @param picIdList
	 */
	void batchDeletePic(List<Integer> picIdList);

	/**
	 * 新增焦点图
	 * @param pic
	 */
	void createFocusPicture(FocusPicture pic);

	/**
	 * 根据焦点图ID，获取焦点图信息
	 * @param id 焦点图ID
	 * @return
	 */
	FocusPicture getFocusPicById(Integer id);

	/**
	 * 更新焦点图信息
	 * @param pic
	 */
	void editFocusPicture(FocusPicture pic);

}
