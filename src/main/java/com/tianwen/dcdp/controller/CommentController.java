package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.pojo.Comment;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.service.ICommentService;


@Controller
@RequestMapping("/comment")
public class CommentController {
	
	private static final Logger logger = Logger.getLogger(CommentController.class);
	
	@Resource
    private ICommentService commentService;
	
	/**
	 * 评论列表
	 * @param page 当前页
	 * @param pageSize  页面大小
	 * @param contentId  内容ID 
	 * @param commentBody  评论内容
	 * @param replyUserName  评论制作者
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("society:comment:list")
	@RequestMapping("/commentList")
	public String commentList(@RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "pageSize",required = false) Integer pageSize,
            @RequestParam(value = "contentId",required = false) Integer contentId,
            @RequestParam(value = "commentBody",required = false) String commentBody,
            @RequestParam(value = "replyUserName",required = false) String replyUserName,
            @RequestParam(value = "startDate",required = false) String startDate,
            @RequestParam(value = "endDate",required = false) String endDate,
			HttpServletRequest request,Model model){
		
		page = page==null ?1:page;
		pageSize = pageSize==null?10:pageSize;
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("contentId", contentId);
		whereMap.put("commentBody", commentBody);
		whereMap.put("commentUserName", replyUserName);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		
		
		int totalCount = commentService.getTotalCount(whereMap);
		//获取分页列表
		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Comment> commentList = commentService.getCommentList(whereMap);
		//前台参数
		model.addAttribute("pager", pager);
		model.addAttribute("contentId",contentId);
		model.addAttribute("commentBody",commentBody);
		model.addAttribute("replyUserName",replyUserName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("commentList", commentList);
		
		return "comment/commentList";
	}	
	
	
	/**
	 * 删除评论 
	 * @param idList  要删除的ID数组
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("society:comment:delete")
	@RequestMapping("/deleteComments")
	@ResponseBody
	public Map<String,Object> deleteComments( @RequestParam("idList[]") List<Integer> idList,
			 HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		try{
			commentService.deleteCommentByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.DELETE, "删除评论 :<"+idList.toString()+">");
		return resultMap;
	}
	
	
	/**
	 * 隐藏评论
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("society:comment:hidden")
	@RequestMapping("/hiddenComments")
	@ResponseBody
	public Map<String,Object> hiddenComments(@RequestParam("idList[]") List<Integer> idList,
			 HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		try{
			commentService.hiddenCommentsByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.SHOW_OR_HIDE, "隐藏/显示评论 :<"+idList.toString()+">");
		return resultMap;
	}
		
}
