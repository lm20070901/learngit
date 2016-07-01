package com.tianwen.dcdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.PointRule;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IPointService;

@Controller
@RequestMapping("/point")
public class PointRuleController {
	
	@Autowired
	private IPointService pointService;
	
	/**
	 * 查询积分规则列表
	 * @param model
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@RequiresPermissions("system:pointRule:list")
	@RequestMapping("/pointRuleList")
	public String pointRuleList(Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;
		
		//查询记录总数
		int totalCount = pointService.getPointRuleTotalCount();
		
		Page page = new Page(curPage, totalCount, pageSize);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("start", page.getStart());
		whereMap.put("size", page.getPageSize());
		List<PointRule> data = pointService.getPointRuleList(whereMap);
		
		model.addAttribute("page", page);
		model.addAttribute("data", data);
		model.addAttribute("periods", ParamEnum.Period.getMap());
		
		return "point/pointRuleList";
	}
	
	/**
	 * 跳转到积分规则编辑界面
	 * @param model
	 * @param ruleId
	 * @return
	 */
	@RequiresPermissions("system:pointRule:toEdit")
	@RequestMapping("/toPointRuleEdit")
	public String toPointRuleEdit(Model model,
			@RequestParam(value="id",required=false)Integer ruleId) {
		PointRule data = pointService.getPointRuleById(ruleId);
		model.addAttribute("data", data);
		model.addAttribute("periods", ParamEnum.Period.getMap());
		
		return "point/pointRuleEdit";
	}
	
	/**
	 * 修改积分规则信息
	 * @param pointRule
	 * @return
	 */
	@RequiresPermissions("system:pointRule:edit")
	@RequestMapping("/editPointRule")
	@ResponseBody
	public Map<String, Object> editPointRule(PointRule pointRule) {
		ResponseResult result = new ResponseResult();
		try {
			pointService.modifyPointRule(pointRule);
		} catch(Exception e) {
			result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.UPDATE, "修改积分规则 ： < " + pointRule.getRuleId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到积分规则新增界面
	 * @return
	 */
	@RequiresPermissions("system:pointRule:toAdd")
	@RequestMapping("/toPointRuleAdd")
	public String toPointRuleAdd(Model model) {
		model.addAttribute("periods", ParamEnum.Period.getMap());
		return "point/pointRuleAdd";
	}
	
	/**
	 * 新增积分规则
	 * @param pointRule
	 * @return
	 */
	@RequiresPermissions("system:pointRule:add")
	@RequestMapping("/addPointRule")
	@ResponseBody
	public Map<String, Object> addPointRule(PointRule pointRule) {
		ResponseResult result = new ResponseResult();
		try {
			pointService.createPointRule(pointRule);
		} catch(Exception e) {
			result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.CREATE, "新增积分规则 ： < " + pointRule.getRuleId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 批量删除积分规则
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("system:pointRule:delete")
	@RequestMapping("/deletePointRule")
	@ResponseBody
	public Map<String, Object> deletePointRule(@RequestParam(value="idList[]", required=false)List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		if(ids == null || ids.size() <= 0) {
			return result.returnNeedParams();
		}
		try {
			pointService.batchDeletePointRule(ids);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除积分规则 ： < " + ids + " >");
		return result.returnSuccess();
	}
}
