package org.zsl.testmybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tianwen.dcdp.dao.IDistrictDao;
import com.tianwen.dcdp.dao.IPageViewDao;
import com.tianwen.dcdp.dao.IProjectDao;
import com.tianwen.dcdp.pojo.District;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类,"classpath:spring-mvc.xml"classpath:spring-mybatis.xml,
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})

public class TestMyBatis {
	
//	private ApplicationContext ac = null;
	@Resource
	private IPageViewDao pageViewDao;
	
	@Resource
	private IDistrictDao districtDao;
	
	@Resource
	private IProjectDao projectDao;
	
//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}

	@Test
	public void test1() {
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("nickName", "");
		whereMap.put("userName", "");
		PageBounds pageBounds = new PageBounds(1, 8);  
		List<Map<String,Object>> projectList = projectDao.getProjectList(whereMap, pageBounds);
		System.out.println("projectList==="+projectList.size());
		
		
		
	}
}
