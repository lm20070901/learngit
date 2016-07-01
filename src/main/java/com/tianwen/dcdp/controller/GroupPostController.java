package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.DateUtils;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.GroupPost;
import com.tianwen.dcdp.pojo.GroupPostComment;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IGroupPostCommentService;
import com.tianwen.dcdp.service.IGroupPostService;

@Controller
@RequestMapping("/groupPost")
public class GroupPostController {

	@Autowired
	private IGroupPostService groupPostService;
	@Autowired
	private IGroupPostCommentService groupPostCommentService;
	
	/**
	 * 获取小组帖子列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("groupPost:post:list")
	@RequestMapping("/postList")
	public String postList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "groupId", required = false) Integer groupId,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("groupId", groupId);
		whereMap.put("title", title);
		whereMap.put(
				"startDate",
				General.isEmpty(startDate) ? null : DateUtils
						.strDateToLong(startDate));
		whereMap.put(
				"endDate",
				General.isEmpty(endDate) ? null : DateUtils
						.strDateToLong(endDate));
		// 获取记录总数
		int totalCount = groupPostService.selectCountList(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<GroupPost> data = groupPostService.selectList(whereMap);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		model.addAllAttributes(whereMap);
		return "groups/postList";
	}
	
	/**
	 * 小组帖子隐藏/显示
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("groupPost:post:hide")
	@RequestMapping("/hidePost")
	@ResponseBody
	public Map<String, Object> hidePost(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList,
			@RequestParam(value = "value", required = false) Integer value) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			groupPostService.updateState(idList, value);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "隐藏/显示小组帖子 : <" + idList.toString() + " >");
		return result.returnSuccess();
	}
	
/*	*//**
	 * 跳转到查看评论界面
	 * @param id
	 * @param model
	 * @return
	 *//*
	@RequiresPermissions("groupPost:post:toComment")
	@RequestMapping("/toCommentPage")
	public String toCommentPage(@RequestParam(value="postId", required=false)Integer postId, Model model) {
//		Groups groups = groupsService.getById(groupId);
//		model.addAttribute("data", groups);
		return "groups/postComment";
	}*/
	
	/**
	 * 小组帖子删除
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("groupPost:post:delete")
	@RequestMapping("/deletePost")
	@ResponseBody
	public Map<String, Object> deletePost(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			groupPostService.batchDelete(idList);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.DELETE, "删除小组帖子 : < " + idList.toString() + " >");
		return result.returnSuccess();
	}
	/**
	 * 获取某一指定帖子的评论列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("groupPost:comment:list")
	@RequestMapping("/postCommentList")
	public String getOneCommentList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "postId", required = false) Integer postId) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("contentId", postId);
		// 获取记录总数
		int totalCount = groupPostCommentService.countByPostId(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());

		List<GroupPostComment> data = groupPostCommentService.listByPostId(whereMap);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		model.addAllAttributes(whereMap);

		return "groups/postCommentList";
	}
}
