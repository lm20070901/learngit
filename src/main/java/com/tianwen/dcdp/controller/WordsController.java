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
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.pojo.Words;
import com.tianwen.dcdp.service.IWordsService;

@RequestMapping("/words")
@Controller
public class WordsController {

	@Autowired
	private IWordsService wordsService;
	
	/**
	 * 获取敏感词列表
	 * @param model
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@RequiresPermissions("system:words:list")
	@RequestMapping("/wordsList")
	private String wordsList(Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;
		
		//查询记录总数
		int totalCount = wordsService.getWordsTotalCount();
		
		Page page = new Page(curPage, totalCount, pageSize);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("start", page.getStart());
		whereMap.put("size", page.getPageSize());
		
		List<Words> data = wordsService.getWordsList(whereMap);
		model.addAttribute("page", page);
		model.addAttribute("data", data);
		
		return "words/wordsList";
	}
	
	/**
	 * 跳转到敏感词编辑界面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:words:toEdit")
	@RequestMapping("/toWordsEidt")
	public String toWordsEidt(Model model,
			@RequestParam(value="id", required=false)Integer id) {
		Words data = wordsService.getWordsById(id);
		model.addAttribute("data", data);
		return "words/wordsEdit";
	}
	
	/**
	 * 编辑敏感词信息
	 * @param id
	 * @param word
	 * @param type
	 * @return
	 */
	@RequiresPermissions("system:words:edit")
	@RequestMapping("/wordsEidt")
	@ResponseBody
	public Map<String, Object> wordsEidt(Words words) {
		ResponseResult result = new ResponseResult();
		try {
			wordsService.modifyWords(words);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.UPDATE, "修改敏感词信息 ： < " + words.getId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到新增敏感词界面
	 * @return
	 */
	@RequiresPermissions("system:words:toAdd")
	@RequestMapping("/toWordsAdd")
	public String toWordsAdd() {
		
		return "words/wordsAdd";
	}
	
	/**
	 * 新增敏感词记录
	 * @param word
	 * @param type
	 * @return
	 */
	@RequiresPermissions("system:words:add")
	@RequestMapping("/createWords")
	@ResponseBody
	public Map<String, Object> createWords(Words words) {
		ResponseResult result = new ResponseResult();
		try {
			wordsService.createWords(words);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.CREATE, "新增敏感词 ： < " + words.getId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 删除敏感词记录
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("system:words:delete")
	@RequestMapping("/deleteWords")
	@ResponseBody
	public Map<String, Object> deleteWords(@RequestParam(value="idList[]", required=false)List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		if(ids == null || ids.size() <= 0) {
			return result.returnNeedParams();
		}
		try {
			wordsService.batchDeleteWords(ids);
		} catch(Exception e) {
			return result.returnError(Constants.FAILURE, Constants.OPERATE_FAIL_MSG);
		}
		
		LogUtils.log(LogOperType.DELETE, "删除敏感词 ： < " + ids + " >");
		return result.returnSuccess();
	}
}
