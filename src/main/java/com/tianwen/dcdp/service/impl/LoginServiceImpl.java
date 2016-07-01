package com.tianwen.dcdp.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianwen.dcdp.dao.IAdminDao;
import com.tianwen.dcdp.service.ILoginService;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	@Resource
	private IAdminDao adminDao;
	@Autowired
	private  HttpServletRequest request;

	public boolean loginIn(String username, String password, String Code) {
		//subject理解成权限对象。类似user
		Subject subject = SecurityUtils.getSubject();
		Session session=subject.getSession();
		//验证验证码是否正确
		/*if(!session.getAttribute("ccode").equals(Code)){
			request.setAttribute("msg", "验证码错误");
			return false;
		}*/
		//创建用户名和密码的令牌
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		//记录该令牌，如果不记录则类似购物车功能不能使用。
		token.setRememberMe(true);
		System.out.println(username);
		try {
			subject.login(token);
		} catch (UnknownAccountException ex) {	
			request.setAttribute("msg", "用户不存在");
		} catch (IncorrectCredentialsException ex) {
			request.setAttribute("msg", "密码错误");
		}catch (AuthenticationException e) {
			request.setAttribute("msg", "服务器忙...");
			e.printStackTrace();
		}
		//验证是否成功登录的方法
		if (subject.isAuthenticated()) {	
			Object admin = subject.getPrincipal();
			
			session.setAttribute("admin", admin);
			return true;
		}
		return false;
	}

	public boolean loginOut() {
		Subject subject = SecurityUtils.getSubject();	
		subject.logout();
		return true;
	}
	
	
}
