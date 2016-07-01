package com.tianwen.dcdp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.ueditor.ActionEnter;

@Controller
@RequestMapping("/ueditor")
public class UeditorController {

	@RequestMapping("/dispatch")
    public void config(HttpServletRequest request,  HttpServletResponse response) {
           // response.setContentType("application/json");              
            String rootPath = request.getSession().getServletContext().getRealPath("/");
        	response.setHeader("Content-Type" , "text/html");
            try {
            	String a = request.getRequestURI();
                    String exec = new ActionEnter(request, rootPath).exec();
                    PrintWriter writer = response.getWriter();
                    writer.write(exec);
                    writer.flush();
                    writer.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
            
    }
}
