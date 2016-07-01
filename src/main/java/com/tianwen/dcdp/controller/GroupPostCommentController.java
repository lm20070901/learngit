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
import com.tianwen.dcdp.pojo.GroupPostComment;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IGroupPostCommentService;

@Controller
@RequestMapping("/groupPostComment")
public class GroupPostCommentController {

	@Autowired
	private IGroupPostCommentService groupPostCommentService;
	
	
	/**
	 * 获取小组帖子评论列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("gpComment:comment:list")
	@RequestMapping("/commentList")
	public String postList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "commentId", required = false) Integer commentId,
			@RequestParam(value = "commentBody", required = false) String commentBody,
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("commentId", commentId);
		whereMap.put("commentBody", commentBody);
		whereMap.put("userName", userName);
		whereMap.put(
				"startDate",
				General.isEmpty(startDate) ? null : DateUtils
						.strDateToLong(startDate));
		whereMap.put(
				"endDate",
				General.isEmpty(endDate) ? null : DateUtils
						.strDateToLong(endDate));
		// 获取记录总数
		int totalCount = groupPostCommentService.selectCountList(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<GroupPostComment> data = groupPostCommentService.selectList(whereMap);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		model.addAllAttributes(whereMap);
		return "groups/commentList";
	}
	
	/**
	 * 帖子评论隐藏/显示
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("gpComment:comment:hide")
	@RequestMapping("/hideComment")
	@ResponseBody
	public Map<String, Object> hideComment(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList,
			@RequestParam(value = "value", required = false) Integer value) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			groupPostCommentService.updateState(idList, value);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "隐藏/显示小组评论 : <" + idList.toString() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到查看评论界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("gpComment:comment:toView")
	@RequestMapping("/toCommentPage")
	public String toCommentPage(@RequestParam(value="postId", required=false)Integer postId, Model model) {
//		Groups groups = groupsService.getById(groupId);
//		model.addAttribute("data", groups);
		return "groups/postComment";
	}
	
	/**
	 * 帖子评论删除
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("gpComment:comment:delete")
	@RequestMapping("/deleteComment")
	@ResponseBody
	public Map<String, Object> deleteComment(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> idList) {
		ResponseResult result = new ResponseResult();
		if (idList == null || idList.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			groupPostCommentService.batchDelete(idList);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.DELETE, "删除小组评论 : < " + idList.toString() + " >");
		return result.returnSuccess();
	}
}
