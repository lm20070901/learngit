
package com.tianwen.dcdp.service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

import com.tianwen.dcdp.common.MD5;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.pojo.Action;
import com.tianwen.dcdp.pojo.Admin;

/**
 * 系统安全认证实现类
 * 
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {
	@Resource
	private IAdminService adminService;
	
	@Resource
	private IActionService actionService;
	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		//token中储存着输入的用户名和密码
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		//获得用户名与密码
		String username = upToken.getUsername();
		String password = String.valueOf(upToken.getPassword());
		//TODO 与数据库中用户名和密码进行比对。比对成功则返回info，比对失败则抛出对应信息的异常AuthenticationException
		//SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password .toCharArray(),getName());
		//return info;
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("userName", username);
		List<Admin> admins = adminService.getByPage(whereMap);
		if(admins!=null&&admins.size()>0){
			Admin admin = admins.get(0);
			//用户名、密码正确
			MD5 md5 = new MD5();
			if(admin.getPassword().equals(md5.getMD5(password))){
				SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin, password.toCharArray(),getName());
				return info;
			}else{
				throw new IncorrectCredentialsException();
			}
			
		}else{
			//用户不存在
			throw new UnknownAccountException();
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
//		Principal principal = (Principal) getAvailablePrincipal(principals);
//		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		info.addStringPermission("system:admin:list");
		
		Subject subject = SecurityUtils.getSubject();
		Admin admin =(Admin) subject.getPrincipal();
		
		if (admin == null) return null;
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		// 添加基于Permission的权限信息
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("roleId", admin.getRoleId());
		//whereMap.put("isUsed", ParamEnum.IsOrNot.YES.getValue());
		List<Action> actionList = actionService.getActionListByMap(whereMap);
		
		for(Action action : actionList){
			String urlStr = action.getActionUrl();
			if(!StringUtils.isEmpty(urlStr)){
				String[] urls = urlStr.split("\\|");
				for(String url : urls){
					info.addStringPermission(url);
				}
			}
		}
		
//		info.addStringPermission("user");
//		List<Menu> list = UserUtils.getMenuList(admin.getRoleId());
//		
//		
//		for (Menu menu : list) {
//			menu.getActionCommand();
//			if (StringUtils.isNotBlank(menu.getPermission())) {
//				String[] permissions = StringUtils.split(menu.getPermission(),",");
//				for (String permission : permissions) {
//					info.addStringPermission(permission);
//				}
//			}
//		}
		return info;
	}


}