package com.tianwen.dcdp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IUserDao;
import com.tianwen.dcdp.pojo.User;
import com.tianwen.dcdp.service.IUsersService;


@Service("usersService")
public class UsersServiceImpl implements IUsersService{
	
	@Resource
	private IUserDao userDao;
	
	
	@Override
	public int getTotalCount(Map<String, Object> whereMap) {
		
		return userDao.getTotalCount(whereMap);
	}
	
	@Override
	public List<User> getByPage(Map<String, Object> whereMap) {
		
		return userDao.getByPage(whereMap);
	}
	
	@Override
	public User getUserInfoById(Integer userId) {
		
		return userDao.getUserInfoById(userId);
	}
	
	@Override
	public void updateUserInfo(User user) {
		userDao.updateUser(user);
		
	}
	
	@Override
	public void deleteUserByIds(List<Integer> userIdLists) {
		userDao.deleteUserByIds(userIdLists);
		
	}
	
	@Override
	public void changeUserLockedStatus(List<Integer> userIdLists) {
		
		userDao.changeUserLockedStatus(userIdLists);
		
	}

	@Override
	public void resetPassword(List<Integer> userIdLists) {
		userDao.resetPassword(userIdLists);
	}
	
}
