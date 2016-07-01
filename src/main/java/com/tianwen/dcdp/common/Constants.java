package com.tianwen.dcdp.common;

/**
 * 接口返回代码和消息库
 * @author Jiangyouxing
 * @date 2016-04-28
 */
public interface Constants {
	
	//编码部分
	/*
	 * 操作成功
	 */
	String SUCCESS = "1";
	
	/**
	 * 一般错误
	 */
	String FAILURE = "0";
	
	/**
	 * 系统错误
	 */
	String SYSTEM_ERROR = "1000";
	/**
	 * 参数为空
	 */
	 String NULL_PARM = "1001";
	
	 String LOCKED_USER = "1002";//用户被锁定
	 
	 String NO_EXIST_USER = "1003";//用户不存在
	 
	 String UNAUTHORIZED = "1004";//未授权
	 
	 
	//字符串常量部分
	 String OPERATE_SUCCESS_MSG = "操作成功！";
	
	 String OPERATE_FAIL_MSG = "操作失败！";
	
	 String SYSTEM_ERROR_MSG = "系统错误！";
	
	 String NULL_PARM_MSG = "参数不能为空!";
	
	 String  NAMEORPASSWORD_ERROR_MSG = "用户名或者密码错误";
	
	 String LOCKED_USER_MSG = "用户被锁定";
	 
	 String NO_EXIST_USER_MSG = "用户不存在";
	 
	 String UNAUTHORIZED_MSG = "未授权";
	 
	 String LENGTH_OUT_12_MSG = "昵称长度不能超过12个字符串";
	 
	 String MAIL_ERROR_FORMAT_MSG = "邮箱格式不正确";
	 
	 String NAME_EXIST_MSG	 = "用户名已经存在";
	 
	 String NO_DATAS = "无数据";
	 
	 String GROUPS_STATE_ERROR = "状态为被拒绝，不可直接审核";
	 
	 String IP_EXIST_MSG	 = "IP地址已经存在";
}
