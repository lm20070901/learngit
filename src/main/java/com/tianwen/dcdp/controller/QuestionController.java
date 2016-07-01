package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.DateUtils;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Answer;
import com.tianwen.dcdp.pojo.HotQuestion;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.Question;
import com.tianwen.dcdp.pojo.QuestionCategory;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IQestionService;

@Controller
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private IQestionService questionService;
	
	/**
	 * 查询问题分类列表
	 * @param curPage 当前页
	 * @param pageSize 每页记录数
	 * @param model
	 * @return
	 */
	@RequiresPermissions("qa:category:list")
	@RequestMapping("/categoryList")
	public String categoryList(
			@RequestParam(value="modelId", required=false)Integer modelId,
			@RequestParam(value="curPage", required=false)Integer curPage,
			@RequestParam(value="pageSize", required=false)Integer pageSize,
			Model model) {
		curPage = (curPage == null || curPage <= 0 ) ? 1 : curPage;
		pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
		modelId = (modelId == null || ParamEnum.QuestionModule.getEnum(modelId) == null) ? ParamEnum.QuestionModule.FINANCIAL.getValue() : modelId;
		//查询总记录数
		int totalCount = questionService.getQuestionCatogoryTotalCount(modelId);
		
		Page page = new Page(curPage, totalCount, pageSize);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("modelId", modelId);
		whereMap.put("start", page.getStart());
		whereMap.put("size", page.getPageSize());
		List<QuestionCategory> data = null;
		try {
			data = questionService.getAllQuestionCategory(whereMap);
		} catch(Exception e) {
			
		}
				
		model.addAttribute("data", data);
		model.addAttribute("page", page);
		model.addAllAttributes(whereMap);
		model.addAttribute("module", ParamEnum.QuestionModule.getMap());
		return "question/categoryList";
	}
	
	
	/**
	 * 批量删除问题分类列表
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("qa:category:delete")
	@RequestMapping("/deleteCategoryByIds")
	@ResponseBody
	public Map<String, Object> deleteCategoryByIds(@RequestParam(value="idList[]", required=false)List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		if(ids == null || ids.size() <= 0) {
			return result.returnNeedParams();
		}
		try {
			questionService.batchDeleteQuestionCategory(ids);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除问题分类 ： < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到问题分类新增界面
	 * @return
	 */
	@RequiresPermissions("qa:category:toAdd")
	@RequestMapping("/toAddQuestionCategoryPage")
	public String toAddQuestionCategoryPage(Model model) {
		model.addAttribute("module", ParamEnum.QuestionModule.getMap());
		return "question/questionCategoryAdd";
	}
	
	/**
	 * 新增问题分类
	 * @param categoryName
	 * @param questionCount
	 * @param icon
	 * @param isVisible
	 * @return
	 */
	@RequiresPermissions("qa:category:add")
	@RequestMapping("/addQuestionCategory")
	@ResponseBody
	public Map<String, Object> addQuestionCategory(QuestionCategory category) {
		ResponseResult result = new ResponseResult();
		try {
			questionService.createQuestionCategory(category);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.CREATE, "新增问题分类 ： < " + category.getCategoryId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到问题分类编辑界面
	 * @param categoryId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("qa:category:toEdit")
	@RequestMapping("/toQuestionCategoryEdit")
	public String toQuestionCategoryEdit(@RequestParam(value="categoryId", required=false)Integer categoryId,
			Model model) {
		QuestionCategory category = questionService.getCategoryById(categoryId);
		model.addAttribute("data", category);
		model.addAttribute("module", ParamEnum.QuestionModule.getMap());
		return "question/questionCategoryEdit";
	}
	
	/**
	 * 更新问题分类信息
	 * @param categoryId
	 * @param categoryName
	 * @param questionCount
	 * @param icon
	 * @param isVisible
	 * @return
	 */
	@RequiresPermissions("qa:category:edit")
	@RequestMapping("/editQuestionCategory")
	@ResponseBody
	public Map<String, Object> editQuestionCategory(QuestionCategory category) {
		ResponseResult result = new ResponseResult();
		try {
			questionService.updateQuestionCategoryById(category);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.UPDATE, "修改问题分类信息 ： < " + category.getCategoryId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 按条件查询问题列表
	 * @param model
	 * @param curPage
	 * @param pageSize
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequiresPermissions("qa:question:list")
	@RequestMapping("/questionList")
	public String questionList(Model model,
			@RequestParam(value="modelId", required=false)Integer modelId,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value="title", required=false)String title,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {
		
		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;
		modelId = (modelId == null || ParamEnum.QuestionModule.getEnum(modelId) == null) ? ParamEnum.QuestionModule.FINANCIAL.getValue() : modelId;
		
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("title", title);
		whereMap.put("modelId", modelId);
		whereMap.put("startDate", General.isEmpty(startDate) ? null : DateUtils.strDateToLong(startDate));
		whereMap.put("endDate", General.isEmpty(endDate) ? null : DateUtils.strDateToLong(endDate));
		
		//获取总记录数
		int totalCount = questionService.getQuestionTotalCount(whereMap);
		
		Page page = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", page.getStart());
		whereMap.put("size", page.getPageSize());
		List<Question> data = questionService.getPageQuestion(whereMap);
		
		model.addAttribute("page", page);
		model.addAttribute("data", data);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		model.addAllAttributes(whereMap);
		model.addAttribute("module", ParamEnum.QuestionModule.getMap());
		return "question/questionList";
	}
	
	/**
	 * 修改问题显示状态
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("qa:question:change")
	@RequestMapping("/modifyVisibility")
	@ResponseBody
	public Map<String, Object> modifyVisibility(@RequestParam(value="idList[]", required=false)List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		if(ids == null) {
			return result.returnNeedParams();
		}
		try {
			questionService.modifyQuestionVisibility(ids);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "显示/隐藏问题 ： < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 批量删除问题
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("qa:question:delete")
	@RequestMapping("/deleteQuestions")
	@ResponseBody
	public Map<String, Object> deleteQuestions(@RequestParam(value="idList[]", required=false)List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		if(ids == null) {
			return result.returnNeedParams();
		}
		try {
			questionService.batchDeleteQuestion(ids);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除问题 ： < " + ids + " >");
		return result.returnSuccess();
		
	}
	
	/**
	 * 热门问题列表
	 * @param model
	 * @param curPage
	 * @param pageSize
	 * @param title
	 * @param categoryId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequiresPermissions("qa:hotQuestion:list")
	@RequestMapping("/hotQuestionList")
	public String hotQuestionList(Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value="title", required=false)String title,
			@RequestParam(value="category", required=false)Integer categoryId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;
		
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("title", title);
		whereMap.put("category", categoryId);
		whereMap.put(
				"startDate",
				General.isEmpty(startDate) ? null : DateUtils
						.strDateToLong(startDate));
		whereMap.put(
				"endDate",
				General.isEmpty(endDate) ? null : DateUtils
						.strDateToLong(endDate));
		
		//查询问题分类列表
		List<QuestionCategory> categoryList = questionService.getAllQuestionCategory(new HashMap<String, Object>());
		
		//查询记录总数
		int totalCount = questionService.getHotQuestionTotalCount(whereMap);
		
		Page page = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", page.getStart());
		whereMap.put("size", page.getPageSize());
		
		//查询热门问题列表
		List<Question> hotQuestionList = questionService.getHotQuestionList(whereMap);
		model.addAttribute("data", hotQuestionList);
		model.addAttribute("page", page);
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		model.addAllAttributes(whereMap);
		model.addAttribute("cateList", categoryList);
		
		return "question/hotQuestionList";
	}
	
	/**
	 * 批量删除热门问题
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("qa:hotQuestion:delete")
	@RequestMapping("/deleteHotQuestions")
	@ResponseBody
	public Map<String, Object> deleteHotQuestions(@RequestParam(value="idList[]", required=false)List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		if(ids == null || ids.size() <= 0) {
			return result.returnNeedParams();
		}
		
		try {
			questionService.batchDeleteHotQuestion(ids);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除热门问题 ： < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到热门问题排序界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("qa:hotQuestion:toSort")
	@RequestMapping("/toModifyHotQuestionOrder")
	public String toModifyHotQuestionOrder(@RequestParam(value="id", required=false)Integer id,
			Model model) {
		HotQuestion question = questionService.getHotQuestionById(id);
		model.addAttribute("data", question);
		return "question/hotQuestionEdit";
	}
	
	/**
	 * 修改热门问题排序
	 * @param questionId
	 * @param orderNum
	 * @return
	 */
	@RequiresPermissions("qa:hotQuestion:sort")
	@RequestMapping("/modifyHotQuestionOrder")
	@ResponseBody
	public Map<String, Object> modifyHotQuestionOrder(@RequestParam(value="id", required=false)Integer questionId,
			@RequestParam(value="orderNum", required=false)Integer orderNum) {
		ResponseResult result = new ResponseResult();
		if(questionId == null || orderNum == null) {
			return result.returnNeedParams();
		}
		try {
			HotQuestion question = new HotQuestion();
			question.setQuestionId(questionId);
			question.setOrderNum(orderNum);
			questionService.modifyHotQuestionOrder(question);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.UPDATE, "修改热门问题排序 ： < " + questionId + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 问题回答（评论）列表
	 * @return
	 */
	@RequiresPermissions("qa:answer:list")
	@RequestMapping("/answerList")
	public  String answerList(Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value="id", required=false)Integer questionId,
			@RequestParam(value="content", required=false)String content,
			@RequestParam(value="author", required=false)String author,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {
		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("questionId", questionId);
		whereMap.put("content", content);
		whereMap.put("author", author);
		whereMap.put(
				"startDate",
				General.isEmpty(startDate) ? null : DateUtils
						.strDateToLong(startDate));
		whereMap.put(
				"endDate",
				General.isEmpty(endDate) ? null : DateUtils
						.strDateToLong(endDate));
		//获取记录总数
		int totalCount = questionService.selectAnswerTotalCount(whereMap);
		
		Page page = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", page.getStart());
		whereMap.put("size", page.getPageSize());
		
		//按条件查询记录
		List<Answer> data = questionService.getAnswerList(whereMap);
		
		whereMap.put("startDate", startDate);
		whereMap.put("endDate", endDate);
		
		model.addAttribute("data", data);
		model.addAttribute("page", page);
		model.addAllAttributes(whereMap);
		
		return "question/answerList";
	}
	
	/**
	 * 修改问题回复（评论）显示状态
	 * @return
	 */
	@RequiresPermissions("qa:answer:change")
	@RequestMapping("/modifyAnswerVisibility")
	@ResponseBody
	public Map<String, Object> modifyAnswerVisibility(@RequestParam(value="idList[]",required=false)List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		if(ids == null || ids.size() <= 0) {
			return result.returnNeedParams();
		}
		try {
			questionService.modifyAnswerVisibility(ids);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.SHOW_OR_HIDE, "显示/隐藏问答评论 ： < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 删除问题回复（评论）
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("qa:answer:delete")
	@RequestMapping("/deleteAnswers")
	@ResponseBody
	public Map<String, Object> deleteAnswers(@RequestParam(value="idList[]", required=false)List<Integer> ids){
		ResponseResult result = new ResponseResult();
		if(ids == null || ids.size() <= 0) {
			return result.returnNeedParams();
		}
		
		try {
			questionService.batchDeleteAnswer(ids);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除问答评论 ： < " + ids + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 查询指定问题的评论
	 * @param model
	 * @param curPage
	 * @param pageSize
	 * @param questionId
	 * @return
	 */
	@RequiresPermissions("qa:question:answerList")
	@RequestMapping("/answerListByQid/{id}")
	public String answerListByQid(Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@PathVariable(value="id")Integer questionId) {
		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("questionId", questionId);
		//获取记录总数
		int totalCount = questionService.selectAnswerTotalCount(whereMap);
		
		Page page = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", page.getStart());
		whereMap.put("size", page.getPageSize());
		
		//按条件查询记录
		List<Answer> data = questionService.getAnswerList(whereMap);
		
		
		model.addAttribute("data", data);
		model.addAttribute("page", page);
		model.addAllAttributes(whereMap);
		model.addAttribute("questionId", questionId);
		
		return "question/answerListByQid";
	}
}
