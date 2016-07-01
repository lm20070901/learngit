package com.tianwen.dcdp.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.common.DateUtils;
import com.tianwen.dcdp.common.EdwManageConstants;
import com.tianwen.dcdp.common.ExportExcel;
import com.tianwen.dcdp.dao.IActivityEnrollDao;
import com.tianwen.dcdp.pojo.ActivityEnroll;
import com.tianwen.dcdp.service.IActivityEnrollService;

@Service
public class ActivityEnrollServiceImpl implements IActivityEnrollService {
	@Resource
	private EdwManageConstants edwFrontConstants;
	@Resource
	private IActivityEnrollDao activityEnrollDao;

	@Override
	public int deleteByPrimaryKey(Integer contentId) {
		return activityEnrollDao.deleteByPrimaryKey(contentId);
	}

	@Override
	public ActivityEnroll selectByPrimaryKey(Integer contentId) {
		return activityEnrollDao.selectByPrimaryKey(contentId);
	}

	@Override
	public int updateByPrimaryKeySelective(ActivityEnroll record) {
		return activityEnrollDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public PageList<ActivityEnroll> selectPageList(Map<String, Object> map,
			PageBounds pageBounds) {
		return activityEnrollDao.selectPageList(map, pageBounds);
	}

	@Override
	public void batchDelete(List<Integer> ids) {
		activityEnrollDao.batchDelete(ids);
	}

	@Override
	public void exportEnroll(Map<String, Object> map,OutputStream out) {
		ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
		String[] headers = { "ID", "活动名称", "姓名", "性别", "手机号", "常用邮箱", "公司",
				"职位", "报名日期" };
		List<Map<String, Object>>  dataset = new ArrayList<Map<String, Object>>();  
		Map<String, Object> objs = new LinkedHashMap<String, Object>();  
		List<ActivityEnroll> list = activityEnrollDao.selectPageList(map);
		
		for(ActivityEnroll ae: list){
				objs.put("enrollId", ae.getEnrollId());
				objs.put("title", ae.getTitle());
				objs.put("realName", ae.getRealName());
				if(ae.getSex() == 0){
					 objs.put("sex",  "男");  
			    }else{
			    	 objs.put("sex",  "女");  
			    }
				objs.put("phoneNum", ae.getPhoneNum());
				objs.put("email", ae.getEmail());
				objs.put("company", ae.getCompany());
				objs.put("position", ae.getPosition());
				objs.put("enrollTime", DateUtils.longDateToStr(
			    		ae.getEnrollTime(), "yyyy-MM-dd HH:mm:ss") );
			    dataset.add(objs);  
		}
		try {
			ex.exportExcel("活动报名表",headers,dataset, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
