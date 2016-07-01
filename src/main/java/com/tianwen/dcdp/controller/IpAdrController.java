package com.tianwen.dcdp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.tianwen.dcdp.pojo.IpAdr;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.ResponseResult;
import com.tianwen.dcdp.service.IIpAdrService;

@Controller
@RequestMapping("/ipAdr")
public class IpAdrController {

	
	@Resource
	private IIpAdrService ipAdrService;
	/**
	 * 获取IP列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ipAdr:adress:list")
	@RequestMapping("/selectPageList")
	public String selectPageList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false) Integer curPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "ipAddress", required = false) String ipAddress) {

		curPage = curPage == null ? 1 : curPage;
		pageSize = pageSize == null ? 10 : pageSize;

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("ipAddress", ipAddress);
		// 获取记录总数
		int totalCount = ipAdrService.selectTotalCount(whereMap);

		Page pager = new Page(curPage, totalCount, pageSize);
		whereMap.put("start", pager.getStart());
		whereMap.put("size", pager.getPageSize());
		List<IpAdr> data = ipAdrService.selectPageList(whereMap);

		model.addAttribute("pager", pager);
		model.addAttribute("data", data);
		model.addAllAttributes(whereMap);
		return "ipAdr/ipList";
	}
	
	/**
	 * 删除
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("ipAdr:adress:delete")
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Map<String, Object> batchDelete(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> ids) {
		ResponseResult result = new ResponseResult();
		if (ids == null || ids.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			ipAdrService.batchDelete(ids);
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.DELETE, "删除IP : < " + ids + " >");
		return result.returnSuccess();
	}
	
	
	
	/**
	 * 修改状态 禁用/解禁
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("ipAdr:adress:edit")
	@RequestMapping("/updateState")
	@ResponseBody
	public Map<String, Object> updateState(HttpServletRequest request,
			@RequestParam("idList[]") List<Integer> ids,
			@RequestParam("state") Integer state) {  
		ResponseResult result = new ResponseResult();
		if (ids ==null || ids.size() == 0) {
			return result.returnNeedParams();
		}
		try {
			ipAdrService.updateState(ids,state); //改变状态
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.UPDATE, "IP修改状态 : < " + ids + " >");
		return result.returnSuccess();
	}
	/**
	 * 新增
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("ipAdr:adress:add")
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request,
			@RequestParam("ipAddress") String ipAddress,
			@RequestParam("state") Byte state) {  //判断是隐藏还是删除
		ResponseResult result = new ResponseResult();
		if (!General.isNotEmpty(ipAddress)) {
			return result.returnNeedParams();
		}
		IpAdr ipAdr = new IpAdr();
		ipAdr.setIpAddress(ipAddress);
		String date = DateUtils.dateToString(new Date());
		Long createTime = DateUtils.strDateToLong(date);
		ipAdr.setCreateTime(createTime);
		if(General.isNotEmpty(state)){
			ipAdr.setState(state);
		}
		try {
			IpAdr adr = ipAdrService.selectByName(ipAddress);
			if(adr!= null){
				return result.returnError(Constants.FAILURE,
						Constants.IP_EXIST_MSG);
			}else{
				ipAdrService.add(ipAdr); 
			}
		} catch (Exception e) {
			return result.returnError(Constants.FAILURE,
					Constants.OPERATE_FAIL_MSG);
		}
		LogUtils.log(LogOperType.CREATE, "新增IP: < " + ipAdr.getId() + " >");
		return result.returnSuccess();
	}
	
	/**
	 * 跳转到新增界面
	 * @return
	 */
	@RequiresPermissions("ipAdr:adress:toAdd")
	@RequestMapping("/toaddPage")
	public String toaddPage() {
		return "ipAdr/ipAdrAdd";
	}
}
