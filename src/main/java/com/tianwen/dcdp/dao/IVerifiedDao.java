package com.tianwen.dcdp.dao;


import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.dao.base.BaseDao;
import com.tianwen.dcdp.pojo.Verified;

public interface IVerifiedDao extends BaseDao<Verified>{
	
	int deleteByPrimaryKey(Integer id);

    int insert(Verified record);

    int insertSelective(Verified record);

    Verified selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Verified record);

    int updateByPrimaryKey(Verified record);
    

	List<Verified> getVerifiedInfo(Integer userId);

	void saveVerifiedInfo(Verified verified);
	

	PageList<Map<String, Object>> getByPage(Map<String, Object> whereMap,PageBounds pageBounds);

	Map<String, Object> queryVerifiedDetail(Integer id);
	
	
}
