package com.tianwen.dcdp.service;


public interface ILoginService {
	public boolean loginIn(String username,String password,String Code);
	public boolean loginOut();
}
