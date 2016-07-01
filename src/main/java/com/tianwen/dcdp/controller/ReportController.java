package com.tianwen.dcdp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Answer;
import com.tianwen.dcdp.pojo.Article;
import com.tianwen.dcdp.pojo.ArticleComment;
import com.tianwen.dcdp.pojo.Comment;
import com.tianwen.dcdp.pojo.Content;
import com.tianwen.dcdp.pojo.GroupPost;
import com.tianwen.dcdp.pojo.GroupPostComment;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.Question;
import com.tianwen.dcdp.pojo.Report;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.pojo.User;
import com.tianwen.dcdp.service.IArticleService;
import com.tianwen.dcdp.service.ICommentService;
import com.tianwen.dcdp.service.IContentService;
import com.tianwen.dcdp.service.IGroupPostCommentService;
import com.tianwen.dcdp.service.IGroupPostService;
import com.tianwen.dcdp.service.IQestionService;
import com.tianwen.dcdp.service.IReportService;
import com.tianwen.dcdp.service.IUsersService;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private IReportService reportService;
	@Autowired
	private IArticleService articleService;
	@Autowired
	private IQestionService questionService;
	@Autowired
	private IGroupPostService groupPostService;
	@Autowired
	private IGroupPostCommentService groupPostCommentService;
	@Autowired
	private IContentService contentService;
	@Resource
    private ICommentService commentService;
	@Resource
	private IUsersService usersService;
	/**
	 * 获取举报列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("report:report:list")
	@RequestMapping("/selectPageList")
	public String selectPageList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "reportBody", required = false) String reportBody,
			@RequestParam(value = "reportType", required = false) Integer reportType,
			@RequestParam(value = "isHandel", required = false) Integer isHandel) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("reportBody", reportBody);
		whereMap.put("reportType", reportType);
		whereMap.put("isHandel", isHandel);
		// 获取记录总数
		int totalCount = reportService.selectTotalCount(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Report> data = reportService.selectPageList(whereMap);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		model.addAllAttributes(whereMap);
		return "report/reportList";
	}
	
	/**
	 * 删除
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("report:report:delete")
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Map<String, Object> batchDelete(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		if (ids == null || ids.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			reportService.batchDelete(ids);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.DELETE, "删除举报事项 : < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到详情界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("report:report:toDetail")
	@RequestMapping("/toDetailPage")
	public String toDetailPage(@RequestParam(value="id", required=false)Integer id, Model model) {
		Report report = reportService.selectById(id);
		if(General.isNotEmpty(report)){
			Integer contentId = report.getContentId();
			Byte contentType = report.getContentCategory();
			if(contentType == 1){ //资讯
				Article article = articleService.getArticleById(contentId);
				model.addAttribute("data", article);
				report.setStrcontentCategory("资讯");
			}else if(contentType == 2){ //资讯评论
				ArticleComment articleComment = articleService.getCommentById(contentId);
				model.addAttribute("data", articleComment);
				report.setStrcontentCategory("资讯评论");
			}else if(contentType == 3){ //问答
				Question question = questionService.getQuestionById(contentId);
				model.addAttribute("data", question);
				report.setStrcontentCategory("问答");
			}else if(contentType == 4){ //问答评论
				Answer answer = questionService.getAnswerById(contentId);
				model.addAttribute("data", answer);
				report.setStrcontentCategory("问答评论");
			}else if(contentType == 5){ //小组帖子
				GroupPost post = groupPostService.selectById(contentId);
				model.addAttribute("data", post);
				report.setStrcontentCategory("小组帖子");
			}else if(contentType == 6){ //小组帖子评论
				GroupPostComment postComment = groupPostCommentService.selectById(contentId);
				model.addAttribute("data", postComment);
				report.setStrcontentCategory("小组帖子评论");
			}else if(contentType == 7){ //动态
				Content content = contentService.selectById(contentId);
				model.addAttribute("data", content);
				report.setStrcontentCategory("动态");
			}else if(contentType == 8){ //动态评论
				Comment comment = commentService.selectById(contentId);
				model.addAttribute("data", comment);
				report.setStrcontentCategory("动态评论");
			}else if(contentType == 9){ //用户
				User user = usersService.getUserInfoById(contentId);
				model.addAttribute("data", user);
				report.setStrcontentCategory("用户");
			}
		}
		model.addAttribute("report", report);
		return "report/reportDetail";
	}
	
	/**
	 * 修改是否处理的状态,删除1，或者隐藏0
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("report:report:edit")
	@RequestMapping("/updateState")
	@ResponseBody
	public Map<String, Object> updateState(HttpServletRequest request,
			@RequestParam("id") Integer id,
			@RequestParam("contentId") Integer contentId, //内容id
			@RequestParam("contentCategory") Integer contentCategory, //举报内容类型
			@RequestParam("flag") Integer flag) {  //判断是隐藏还是删除
		ResponseResult result = new ResponseResult();
		if (!General.isNotEmpty(contentId)) {
			return result.returnNeedParams();
		}
		try {
			if(flag ==1){
				delContent(contentId,contentCategory);  //删除举报内容
			}else{
				hideContent(contentId,contentCategory);//隐藏举报内容
			}
			reportService.updateState(1, id); //改变处理状态
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.UPDATE, "举报管理：修改是否处理的状态 （flag=1(删除)，flag=0(隐藏)）: < " + id + ", flag = "+flag+" >");
		return result.returnSuccess();
	}
	/**
	 * 隐藏举报内容 ,对于用户而言则是锁定
	 * @param contentId   内容id
	 * @param type  1-9种不同类型
	 * @return
	 */
	public void hideContent(Integer contentId,Integer contentType){
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(contentId);
		if(contentType == 1){ //资讯
			articleService.modifyArticleVisibility(ids);
		}else if(contentType == 2){ //资讯评论
			articleService.modifyArticleCommentVisibility(ids);
		}else if(contentType == 3){ //问答
			questionService.modifyQuestionVisibility(ids);
		}else if(contentType == 4){ //问答评论
			questionService.modifyAnswerVisibility(ids);
		}else if(contentType == 5){ //小组帖子
			groupPostService.updateState(ids, 1);
		}else if(contentType == 6){ //小组帖子评论
			groupPostCommentService.updateState(ids, 1);
		}else if(contentType == 7){ //动态
			contentService.hiddenContents(ids);
		}else if(contentType == 8){ //动态评论
			commentService.hiddenCommentsByIds(ids);
		}else if(contentType == 9){ //用户  
			usersService.changeUserLockedStatus(ids);
		}
	}
	
	/**
	 * 删除举报内容 
	 * @param contentId   内容id
	 * @param type  1-9种不同类型
	 * @return
	 */
	public void delContent(Integer contentId,Integer contentType){
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(contentId);
		if(contentType == 1){ //资讯
			articleService.batchDeleteArticles(ids);
		}else if(contentType == 2){ //资讯评论
			articleService.deleteArticleCommentsByIds(ids);
		}else if(contentType == 3){ //问答
			questionService.batchDeleteQuestion(ids);
		}else if(contentType == 4){ //问答评论
			questionService.batchDeleteAnswer(ids);
		}else if(contentType == 5){ //小组帖子
			groupPostService.batchDelete(ids);
		}else if(contentType == 6){ //小组帖子评论
			groupPostCommentService.batchDelete(ids);
		}else if(contentType == 7){ //动态
			contentService.deleteContentByIds(ids);
		}else if(contentType == 8){ //动态评论
			commentService.deleteCommentByIds(ids);
		}else if(contentType == 9){ //用户  
			usersService.deleteUserByIds(ids);
		}
	}
	
	
}
