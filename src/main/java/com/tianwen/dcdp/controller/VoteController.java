package com.tianwen.dcdp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.ExportExcel;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.pojo.Admin;
import com.tianwen.dcdp.pojo.InviteCode;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.Vote;
import com.tianwen.dcdp.pojo.VoteQuestion;
import com.tianwen.dcdp.service.IVoteService;

@Controller
@RequestMapping("/vote")
public class VoteController {
		
	private static final Logger logger = Logger.getLogger(VoteController.class);
	
	@Resource
	private IVoteService voteService;
	
	@Resource
	private  ExportExcel<Map<String,Object>> export;
	
	
	/**
	 * 用户问卷/系统问卷 列表页面
	 * @param page
	 * @param pageSize
	 * @param voteTitle
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:list")
	@RequestMapping("/voteList")
	public String userVoteList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false ) Integer pageSize,
			@RequestParam(value="voteTitle",required=false)String voteTitle,
			@RequestParam(value="voteType",required=false)Integer  voteType,
			HttpServletRequest request,Model model ){
	
		Map<String, Object> whereMap = new HashMap<String, Object>();
		
		voteType = null == voteType?ParamEnum.VoteType.SYSTEM.getValue():voteType;//默认系统问卷调查
		
		page = null == page? 0:page;
		pageSize  = null == pageSize?10 :pageSize;
		whereMap.put("voteTitle", voteTitle);
		whereMap.put("voteType", voteType);
		//查询总记录数
		int totalCount = voteService.countVoteListNum(whereMap);
		
		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Map<String,Object>>  voteList = voteService.getVoteList(whereMap);
		model.addAllAttributes(whereMap);
		model.addAttribute("list", voteList);
		model.addAttribute("pager", pager);
		
		return "vote/voteList";
	}
	
	
	/**
	 * 进入编辑问卷调查页面 
	 * @param voteId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:toEdit")
	@RequestMapping("/toEditVote")
	public String toEditVote(
			@RequestParam(value = "voteId", required = false)Integer voteId,
			@RequestParam(value = "voteType", required = false)Integer voteType,
			HttpServletRequest request, Model model){
		
		if(null == voteType){
			model.addAttribute("msg", Constants.NULL_PARM_MSG);
			return "error";
		}
		
		if(null != voteId){
			Map<String,Object> whereMap = new HashMap<String,Object>();
			
			whereMap.put("voteId", voteId);
			
			Vote vote = voteService.queryVoteById(voteId);
			model.addAttribute("vote", vote);
		}
		model.addAttribute("voteType", voteType);		
		return "vote/voteEdit";
	}
	
	
	/**
	 * 问卷调查题目列表 
	 * @param voteId
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:questionList")
	@RequestMapping("/toVoteQuestionList")
	public String toVoteQuestionList(
			@RequestParam(value = "voteId", required = false) Integer voteId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			HttpServletRequest request, Model model) {

		
		page = null == page ? 0 : page;
		pageSize = null == pageSize ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		//为空的时候  设置参数为-1让查询结果为空 不让前台会报错
		whereMap.put("voteId", voteId == null ? -1 : voteId);

		int totalCount = voteService.countQuestionNumById(voteId == null ? -1 : voteId);
		Page pager = new Page(page, totalCount, pageSize);
		//取消分页 modify by jiangyx 2016.06.02  
	//	whereMap.put("start", pager.getStart());
	//	whereMap.put("size", pager.getPageSize());

		List<Map<String, Object>> voteQuestionList = voteService.getVoteQuestionsByMap(whereMap);

		model.addAttribute("voteId", voteId);
		model.addAttribute("pager", pager);
		model.addAttribute("list", voteQuestionList);

		return "vote/voteQuestionList";
	}
	
	
	
	
	
	/**
	 * 保存问卷调查
	 * @param vote
	 * @param request
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:save")
	@RequestMapping("/saveVote")
	@ResponseBody
	public Map<String,Object> saveVote(
			@RequestParam(value = "voteTitle" ,required=false)String voteTitle,
			@RequestParam(value = "voteInfo" ,required=false)String voteInfo,
			@RequestParam(value = "isInvite" ,required=false)Byte isInvite,
			@RequestParam(value = "resultVisible" ,required=false)Byte resultVisible,
			@RequestParam(value = "lastTime" ,required=false)String lastTime,
			@RequestParam(value = "voteType" ,required=false)Byte voteType,
			@RequestParam(value = "voteId" ,required=false)Integer voteId,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		Vote vote = new Vote();
		vote.setVoteTitle(voteTitle);
		vote.setVoteInfo(voteInfo);
		vote.setIsInvite(isInvite);
		vote.setResultVisible(resultVisible);
		vote.setVoteTime(new Date().getTime());
		vote.setVoteType(voteType);
		vote.setVoteId(voteId);
		
		Subject subject = SecurityUtils.getSubject();
		Admin user = (Admin)subject.getPrincipal();
		vote.setVoteUid(user.getUserId());
		//session.setAttribute("admin", admin);
		try {
			if(!StringUtils.isEmpty(lastTime)){
				Long last_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastTime).getTime();
				vote.setLastTime(last_time);
			}
		} catch (ParseException e) {
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", "日期格式转换错误!");
			return resultMap;
		}
			
		
		try{
			vote = voteService.saveVote(vote);
			resultMap.put("data", vote);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		
		return resultMap;
	}
	
	
	/**
	 * 问卷调查题目编辑页面
	 * @param voteId
	 * @param questionId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:toQuestionEdit")
	@RequestMapping("/toVoteQuestionEdit")
	public String toVoteQuestionEdit(
			@RequestParam(value = "voteId" ,required=false) Integer voteId,
			@RequestParam(value = "questionId" ,required=false) Integer questionId,
			HttpServletRequest request, Model model){
		
		if(null != questionId){
			VoteQuestion question = voteService.queryQuestionById(questionId);
			model.addAttribute("question", question);
			
			if( question.getType() == ParamEnum.QuestionType.SINGLE_CHOICE.getValue()
					|| question.getType() == ParamEnum.QuestionType.MULTI_CHOICE.getValue()	){
				//单选或者多选 查询选项
				List<Map<String,Object>> options = voteService.queryQuestionOptionsById(questionId);
				
				model.addAttribute("options", options);
			}
		}
		model.addAttribute("questionId", questionId);
		model.addAttribute("voteId", voteId);
		
		return "vote/voteQuestionEdit";
	}
	
	
	/**
	 * 保存编辑问题  
	 * @param type
	 * @param title
	 * @param questionId
	 * @param options
	 * @param request
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:saveQuestion")
	@RequestMapping("/saveVoteQuestion")
	@ResponseBody
	public Map<String,Object> saveVoteQuestion(
			@RequestParam(value = "voteId" ,required=false) Integer voteId,
			@RequestParam(value="questionType",required=false)Byte questionType,
			@RequestParam(value="title",required=false)String title,
			@RequestParam(value="questionId",required=false) Integer questionId,
			//@RequestParam(value = "options[]", required = false) List<String> options,
			@RequestParam(value = "options", required = false) String options,
			HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null == voteId || null == title || null == questionType  || questionType<0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("type", questionType);
			whereMap.put("title",title);
			whereMap.put("voteId",voteId);
			whereMap.put("questionId",questionId);
			whereMap.put("options", options);
			VoteQuestion question = voteService.saveVoteQuestion(whereMap);
			
			resultMap.put("data", question);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		
		return resultMap;
		
	}
	
	/**
	 * 查看问卷详情
	 * @param voteId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:view")
	@RequestMapping("/toVoteDetail")
	public String toVoteDetail(
			@RequestParam(value = "voteId" , required=false ) Integer voteId, //其实可以把 异常做统一处理  
			//Vote vote,
			HttpServletRequest request, Model model){
		if(null == voteId){
			model.addAttribute("msg", Constants.NULL_PARM_MSG);
			return "error";
		}
		Map<String,Object>  voteDetail = voteService.getVoteDetail(voteId);
		
		model.addAttribute("voteDetail", voteDetail);
		model.addAttribute("voteId", voteId);
		return "vote/voteDetail";
	}
	
	
	
	/**
	 * 修改显示状态
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:update")
	@RequestMapping("/updateShowStatus")
	@ResponseBody
	public Map<String,Object> updateShowStatus(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request, HttpServletResponse response){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			voteService.updateVoteShowStatus(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		return resultMap;
	}
	
	
	/**
	 * 批量删除问卷题目 
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:delete")
	@RequestMapping("/deleteVoteByIds")
	@ResponseBody
	public Map<String,Object> deleteVoteByIds(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			voteService.deleteVoteByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		return resultMap;
	}
	
	
	
	
	/**
	 * 发布问卷调查
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:publish")
	@RequestMapping("/publishVotes")
	@ResponseBody
	public Map<String,Object> publishVotes(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		

		try{
			voteService.publishVotes(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		
		return resultMap;
	}
	
	
	/**
	 * 结束问卷调查
	 * @param idList
	 * @param request
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:end")
	@RequestMapping("/endVotes")
	@ResponseBody
	public Map<String,Object> endVotes(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		
		try{
			voteService.endVotes(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		
		return resultMap;
	}
	
	
	
	/**
	 * 根据ID删除问题
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:deleteQuestion")
	@RequestMapping("/deleteVoteQuestionByIds")
	@ResponseBody
	public Map<String,Object> deleteVoteQuestionByIds(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			// HtmlUtil.writerJson(response, resultMap);
			 return resultMap;
		}
		
		try{
			voteService.deleteVoteQuestionByQuestionIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		// HtmlUtil.writerJson(response, resultMap);
		return resultMap;
	}
	
	
	
	/**
	 * 根据选项ID删除选项
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:deleteOptions")
	@RequestMapping("/delteQuestionOptionsByIds")
	@ResponseBody
	public Map<String,Object> delteQuestionOptionsByIds(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			voteService.deleteQuestionOptionsByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		return resultMap;
	}
	
	
	
	
	/**
	 * 保存问题顺序
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:saveQuestionOrder")
	@RequestMapping("/saveQuestionOrder")
	@ResponseBody
	public Map<String,Object> saveQuestionOrder(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			voteService.saveQuestionOrder(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		return resultMap;
	}
	
	
	/**
	 * 问答题列表
	 * @param voteId
	 * @param questionId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:anserList")
	@RequestMapping("/toVoteAnserList")
	public String toVoteAnserList(
			@RequestParam(value = "voteId" ,required=false) Integer voteId,
			@RequestParam(value = "questionId" ,required=false) Integer questionId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false ) Integer pageSize,
			HttpServletRequest request,Model model){
		if(null == questionId){
			model.addAttribute("msg", Constants.NULL_PARM_MSG);
			return "error";
		}
		page = null == page? 0:page;
		pageSize  = null == pageSize?10 :pageSize;
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("voteId", voteId);
		whereMap.put("questionId", questionId);
		
		int totalCount = voteService.countAnswerListNum(whereMap);
		
		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<Map<String,Object>> answerList = voteService.getAnswerList(whereMap);
		model.addAllAttributes(whereMap);
		model.addAttribute("answerList", answerList);
		model.addAttribute("pager", pager);
		return "vote/voteAnswerList";
	}

	
	/**
	 * 导出答卷
	 * @param voteId
	 * @param reqeust
	 * @param response
	 */
	@RequiresPermissions("questionnaire:vote:export")
	@RequestMapping(value = "/exportVote")
	public void exportVoteExcel(
			@RequestParam(value = "voteId") Integer voteId,
			HttpServletRequest reqeust,HttpServletResponse response) {
		//设置返回头
		response.setCharacterEncoding("UTF-8");
		
		response.setContentType("application/vnd.ms-excel; charset=utf-8");
		
		//response.setHeader("Content-disposition","attachment;filename=defalut.xls");
		
		Vote vote = voteService.queryVoteById(voteId);
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("voteId", voteId);
		whereMap.put("isInvite", vote.getIsInvite()==null?0:vote.getIsInvite());//默认不需要邀请
		
		List<Map<String,Object>> answerList = voteService.getVoteAnswerList(whereMap);
		
		
		List<Map<String,Object>>  questionList = voteService.getVoteQuestionsByMap(whereMap);
		
		String[] heads = new String[questionList.size()];
		
		for(int i = 0 ;i< questionList.size() ;i++ ){
			heads[i] = new StringBuilder("Q").append((i+1)).toString();
		}
		try {
			response.setHeader("Content-disposition","attachment;filename="+new String( vote.getVoteTitle().getBytes("GB2312"), "ISO8859-1" )+".xls");
			export.exportExcel(vote.getVoteTitle(),heads,answerList, response.getOutputStream());
		} catch (Exception e) {
			logger.error(e.getMessage());

		}

	}

	
	/**
	 * 进入生成邀请码页面
	 * @param voteId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:toAddInvite")
	@RequestMapping("/toAddInviteCode")
	public String toAddInviteCode(
			@RequestParam(value = "voteId",required = false) Integer voteId,
			HttpServletRequest request,Model model){
		if(null == voteId){//参数校验
			model.addAttribute("msg", Constants.NULL_PARM_MSG);
			return "error";
		}
		model.addAttribute("voteId", voteId);
		return "vote/inviteCodeAdd";
	}
	
	
	
	/**
	 * 生成邀请码
	 * @param voteId
	 * @param inviteNum
	 * @param timeLine
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:create")
	@RequestMapping("/createInviteCode")
	@ResponseBody
	public Map<String,Object> createInviteCode(
			@RequestParam(value = "voteId",required = false) Integer voteId,
			@RequestParam(value = "inviteNum",required = false) Integer inviteNum,
			@RequestParam(value = "timeLine",required = false) String timeLine,
			HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null == voteId || inviteNum == null || StringUtils.isEmpty(timeLine)){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		
		try{
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("voteId", voteId);
			whereMap.put("inviteNum", inviteNum);
			whereMap.put("timeLine", timeLine);
			
			voteService.createInviteCode(whereMap);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		return resultMap;
	}
	
	
	
	/**
	 * 管理邀请码页面
	 * @param voteId
	 * @param inviteCode
	 * @param isUsed
	 * @param userName
	 * @param beginTime
	 * @param endTime
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:toInviteCodeList")
	@RequestMapping("/toInviteCodeList")
	public String toInviteCodeList(
			@RequestParam(value = "voteId",required = false) Integer voteId,
			@RequestParam(value = "inviteCode",required = false) String inviteCode,
			@RequestParam(value = "isUsed",required = false) Integer isUsed,
			@RequestParam(value = "userName",required = false) String userName,
			@RequestParam(value = "startDate",required = false) String startDate,
			@RequestParam(value = "endDate",required = false) String endDate,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false ) Integer pageSize,
			HttpServletRequest request,Model model){
		//参数校验
		if(null == voteId){
			model.addAttribute("msg", Constants.NULL_PARM_MSG);
			return "error";
		}
		page = null == page? 0:page;
		pageSize  = null == pageSize?10 :pageSize;
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		
		whereMap.put("voteId", voteId);
		whereMap.put("inviteCode", inviteCode);
		whereMap.put("userName", userName);
		whereMap.put("isUsed", isUsed);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		
		int totalCount = voteService.countInviteCodeNum(whereMap);
		
		Page pager = new Page(page, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		
		List<InviteCode>  codeList = voteService.getInviteCodeList(whereMap);
		model.addAllAttributes(whereMap);
		model.addAttribute("codeList", codeList);
		model.addAttribute("pager", pager);
		
		return "vote/inviteCodeList";
	}
	
	
	
	/**
	 * 删除邀请码
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:deleteInviteCode")
	@RequestMapping("/deleteInviteCodeByIds")
	@ResponseBody
	public Map<String,Object> deleteInviteCodeByIds(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			voteService.deleteInviteCodeByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		return resultMap;
	}
	
	
	/**
	 * 编辑邀请码页面
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:toEditInvite")
	@RequestMapping("/toEditInviteCode")
	public String toEditInviteCode(
			@RequestParam(value = "id", required = false)Integer id,
			HttpServletRequest request, Model model){
		
		if(null != id){
		
			InviteCode inviteCode = voteService.queryInviteCodeById(id);
			model.addAttribute("inviteCode", inviteCode);
		}
		model.addAttribute("id", id);
		return "vote/inviteEdit";
	}
	
	
	/**
	 * 编辑邀请码
	 * @param id
	 * @param inviteCode
	 * @param timeLine
	 * @param isUsed
	 * @param userName
	 * @param voteId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("questionnaire:vote:saveInviteCode")
	@RequestMapping("/saveInviteCode")
	@ResponseBody
	public Map<String,Object> saveInviteCode(
			@RequestParam(value="id",required=false )  Integer id,
			@RequestParam(value="inviteCode",required=false )  String inviteCode,
			@RequestParam(value="timeLine",required=false )  String timeLine,
			@RequestParam(value="isUsed",required=false )  Integer isUsed,
			@RequestParam(value="userName",required=false )  String userName,
			@RequestParam(value="voteId",required=false )  Integer voteId ,
			HttpServletRequest request , Model model){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if(StringUtils.isEmpty(timeLine)){
			
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		InviteCode code = new InviteCode();
		code.setId(id);
		code.setInviteCode(inviteCode);
		code.setTimeLine(General.timeStr2Long(timeLine, "yyyy-MM-dd HH:mm:ss"));
		code.setUserName(userName);
		code.setVoteId(voteId);
		code.setIsUsed(isUsed==null?false:isUsed == 1);
		
		try{
			voteService.saveInviteCode(code);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		
		
		return resultMap;
	}
}
