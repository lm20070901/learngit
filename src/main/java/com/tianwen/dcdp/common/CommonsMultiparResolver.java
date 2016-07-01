package com.tianwen.dcdp.common;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;
			 
public class CommonsMultiparResolver extends CommonsMultipartResolver {

	@Override  
	  public boolean isMultipart(javax.servlet.http.HttpServletRequest request) {  
	   String uri = request.getRequestURI();  
	   //过滤使用百度UEditor的URI  
	   if (uri.indexOf("ueditor/dispatch") > 0) {  
	    return false;  
	   }  
	   return super.isMultipart(request);  
	  }  
}
