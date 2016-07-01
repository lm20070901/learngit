package com.tianwen.dcdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IFocusPictureDao;
import com.tianwen.dcdp.pojo.FocusPicture;
import com.tianwen.dcdp.service.IFocusPictureService;

@Service
public class FocusPictureServiceImpl implements IFocusPictureService {

	@Autowired
	private IFocusPictureDao focusPictureDao;
	
	@Override
	public List<FocusPicture> getFocusPictureList() {
		return focusPictureDao.selectAll();
	}

	@Override
	public void batchDeletePic(List<Integer> picIdList) {
		focusPictureDao.batchDelete(picIdList);
	}

	@Override
	public void createFocusPicture(FocusPicture pic) {
		focusPictureDao.insertSelective(pic);
	}

	@Override
	public FocusPicture getFocusPicById(Integer id) {
		return focusPictureDao.selectByPrimaryKeyWithArticleTitle(id);
	}

	@Override
	public void editFocusPicture(FocusPicture pic) {
		focusPictureDao.updateByPrimaryKeySelective(pic);
	}

}
