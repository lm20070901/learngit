package com.tianwen.dcdp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.service.ILoginService;

@Controller
@RequestMapping("")
public class IndexController {
	@Resource
	private ILoginService loginService;
	
	@RequestMapping("/index")
	public String toIndex(HttpServletRequest request,Model model){
		return "index";
	}
	
	@RequestMapping("/welcome")
	public String welcome(HttpServletRequest request,Model model){
		return "welcome";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,Model model){	
		return "login";
	}
	
	/**
	 * 
	 * 用户登录
	 * 
	 */
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest request,Model model){
		//获取参数
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");//md5后的密码
		//参数校验
		if(General.isEmpty(userName)||
			General.isEmpty(password)){
			model.addAttribute("result", "-1");
			model.addAttribute("msg", "参数不能为空");
			return "login";
		}
		boolean result = loginService.loginIn(userName, password, "123");
		if(result)
			return "redirect:/index";
		else
			return "login";
		//检索用户
		/*Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("userName", userName);
		User user = userService.getUser(whereMap);
		if(user!=null&&password.equals(user.getPassword())){
			String token = General.getRandomStr(32);
			//存储登录信息
			
		
			//把用户信息放到内存，拦截器中验证是否登录
			
		}else{
			resultMap.put("result", "0");
			resultMap.put("msg", "用户名或者密码错误");
		}
		return "index";*/
	}
	
	/**
	 * 
	 * 用户退出
	 * 
	 */
	@RequestMapping("/toLogout")
	public String toLogout(HttpServletRequest request,Model model){
		if(loginService.loginOut())
			return "login";
		else
			return "redirect:/index";
	}
	
}
