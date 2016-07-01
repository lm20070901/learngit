package com.tianwen.dcdp.service;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.User;

public interface IUsersService {

	int getTotalCount(Map<String, Object> whereMap);
	
	List<User> getByPage(Map<String, Object> whereMap);

	User  getUserInfoById(Integer userId) ;

	void updateUserInfo(User user);

	void deleteUserByIds(List<Integer> userIdLists);

	void changeUserLockedStatus(List<Integer> userIdLists);

	void resetPassword(List<Integer> userIdLists);
	


}
