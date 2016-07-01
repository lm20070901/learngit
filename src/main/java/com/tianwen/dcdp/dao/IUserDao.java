package com.tianwen.dcdp.dao;

import java.util.List;
import java.util.Map;

import com.tianwen.dcdp.pojo.User;

public interface IUserDao {

	int getTotalCount(Map<String, Object> whereMap);

	List<User> getByPage(Map<String, Object> whereMap);

	User getUserInfoById(Integer userId);

	void updateUser(User user);

	void deleteUserByIds(List<Integer> userIdLists);

	void changeUserLockedStatus(List<Integer> userIdLists);

	void resetPassword(List<Integer> userIdLists);
	
}
