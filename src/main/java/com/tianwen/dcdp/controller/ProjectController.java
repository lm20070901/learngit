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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.General;
import com.tianwen.dcdp.common.LogUtils;
import com.tianwen.dcdp.common.ParamEnum;
import com.tianwen.dcdp.common.ParamEnum.LogOperType;
import com.tianwen.dcdp.pojo.District;
import com.tianwen.dcdp.pojo.Page;
import com.tianwen.dcdp.pojo.Project;
import com.tianwen.dcdp.service.IDistrictService;
import com.tianwen.dcdp.service.IProjectService;
import com.tianwen.dcdp.service.ISystemService;


@Controller
@RequestMapping("/project")
public class ProjectController {
		
	
	private static final Logger logger = Logger.getLogger(ProjectController.class);
	
	@Resource
	private IProjectService projectService;
	
	@Resource
	private IDistrictService  districtService;
	
	
	@Resource
	private ISystemService systemService;
	
	/**
	 * 项目发布列表
	 * @param projectName
	 * @param areaId
	 * @param industry
	 * @param projectStatus
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("project:publish:list")
	@RequestMapping("/getProjectList")
	public String getProjectList(
			@RequestParam(value="projectName",required=false)String projectName,
			@RequestParam(value="areaId",required=false)Integer areaId,
			@RequestParam(value="industry",required=false)Integer industry,
			@RequestParam(value="projectStatus",required=false)Integer projectStatus,
			@RequestParam(value="isAdmin",required=false ) Boolean  isAdmin,
			@RequestParam(value="isRecommend",required=false ) Boolean isRecommend,
			@RequestParam(value="page",required=false)Integer page,
			@RequestParam(value="pageSize",required=false)Integer pageSize,
			HttpServletRequest request,Model model){
		page = null == page? 1:page;
		pageSize = null == pageSize?10:pageSize;
		//NOTE  它用的是子查询  这里用了别名后 列明变化了 所以需要改变排序字段 PROJECT_ID  成  projectId
		PageBounds pageBounds = new PageBounds(page,pageSize,Order.formString(" projectId.desc "));
		isAdmin = isAdmin == null ? true:isAdmin;
		isRecommend = isRecommend == null ? false : isRecommend;
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("projectName", projectName);
		whereMap.put("areaId", areaId);
		whereMap.put("industry", industry);
		whereMap.put("projectStatus", projectStatus);
		whereMap.put("isAdmin", isAdmin);
		whereMap.put("isRecommend", isRecommend);
		
		PageList<Map<String,Object>> projectList = projectService.getProjectList(whereMap,pageBounds);
		Page pager = new Page(page, projectList.getPaginator().getTotalCount(), pageSize);
		List<District> districtList = districtService.getProvienceListById(null);
		
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("groupName", "行业");
		List<Map<String,Object>> industryList =   systemService.getSystemList(queryMap);
		
		model.addAllAttributes(whereMap);
		model.addAttribute("projectList", projectList);
		model.addAttribute("districtList", districtList);
		model.addAttribute("industryList", industryList);
		model.addAttribute("pager", pager);
		return "project/projectList";
	}
	
	
	/**
	 * 去编辑页面
	 * @param projectId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("project:publish:toEdit")
	@RequestMapping("/toEditProject")
	public String toEditProject(
			@RequestParam(value="projectId",required=false)Integer projectId,Model model){
		
		Project project = projectService.getProjectById(projectId);
		
		model.addAttribute("projectId", projectId);
		model.addAttribute("project", project);
		
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("groupName", "行业");
		List<Map<String,Object>> industryList =   systemService.getSystemList(queryMap);
		model.addAttribute("industryList", industryList);
		
		List<District> districtList = districtService.getProvienceListById(null);
		model.addAttribute("districtList", districtList);
		
		queryMap.put("groupName", "项目阶段");
		List<Map<String,Object>> projectStageList =   systemService.getSystemList(queryMap);
		model.addAttribute("projectStageList", projectStageList);
		
		return "project/projectEdit";
	}
	
	
	
	/**
	 * 保存工程项目信息
	 * @param project
	 * @return
	 */
	@RequiresPermissions("project:publish:save")
	@RequestMapping("/saveProject")
	@ResponseBody
	public Map<String,Object> saveProject(Project project){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isEmpty(project.getProjectName())) {
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		try {
			
			projectService.saveProject(project);
			resultMap.put("project", project);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogOperType type = project.getProjectId()==null?ParamEnum.LogOperType.CREATE:ParamEnum.LogOperType.UPDATE;
		LogUtils.log(type, type.getDesc()+"项目!");
		return resultMap;
	}
	
	
	/**
	 * 修改显示状态
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("project:publish:changeShow")
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
			projectService.updateStatus(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		
		LogUtils.log(ParamEnum.LogOperType.SHOW_OR_HIDE, "隐藏/显示项目,idList:"+General.list2Str(idList));
		
		return resultMap;
	}
	
	
	
	/**
	 * 推荐或者移除推荐
	 * @param isRecommend
	 * @param idList
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequiresPermissions("project:publish:recommendStatus")
	@RequestMapping("/changeRecommendStatus")
	@ResponseBody
	public Map<String,Object> changeRecommendStatus(
			@RequestParam(value = "isRecommend", required = false) Integer  isRecommend,
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if( null==idList||idList.size()<=0 || isRecommend == null){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		try{
			projectService.changeRecommendStatus(isRecommend,idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.OTHERS, (isRecommend!=0?"推荐":"移除")+"项目,idList:"+General.list2Str(idList));
		
		return resultMap;
	}
	
	
	/**
	 * 发布项目
	 * @param idList
	 * @return
	 */
	@RequiresPermissions("project:publish:publish")
	@RequestMapping("/publishProject")
	@ResponseBody
	public Map<String,Object> publishProject(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if( null==idList||idList.size()<=0 ){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		try{
			projectService.publishProject(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.OTHERS, "发布项目,idList:"+General.list2Str(idList));
		
		return resultMap;
	}
	
	
	/**
	 * 批量删除项目
	 * @param idList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("project:publish:delete")
	@RequestMapping("/deleteProjectByIds")
	@ResponseBody
	public Map<String,Object> deleteProjectByIds(
			@RequestParam(value = "idList[]", required = false) List<Integer> idList,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==idList||idList.size()<=0){
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.NULL_PARM_MSG);
			return resultMap;
		}
		
		try{
			projectService.deleteProjectByIds(idList);
			resultMap.put("result", Constants.SUCCESS);
			resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
		}catch (Exception e){
			logger.error(e.getMessage());
			resultMap.put("result", Constants.FAILURE);
			resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
		}
		LogUtils.log(ParamEnum.LogOperType.DELETE, "删除项目,idList:"+General.list2Str(idList));
		return resultMap;
	}
	
	
	
	/**
	 * 项目审核
	 * @param projectId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("project:publish:toAudit")
	@RequestMapping("/toAuditProject")
	public String toAuditProject(
		@RequestParam(value="projectId",required=false)Integer projectId,Model model){
			
			if(null == projectId){
				model.addAttribute("msg", Constants.NULL_PARM_MSG);
				return "error";
			}
		
			Project project = projectService.getProjectById(projectId);
			
			model.addAttribute("projectId", projectId);
			model.addAttribute("project", project);
			
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("groupName", "行业");
			List<Map<String,Object>> industryList =   systemService.getSystemList(queryMap);
			model.addAttribute("industryList", industryList);
			
			List<District> districtList = districtService.getProvienceListById(null);
			model.addAttribute("districtList", districtList);
			
			queryMap.put("groupName", "项目阶段");
			List<Map<String,Object>> projectStageList =   systemService.getSystemList(queryMap);
			model.addAttribute("projectStageList", projectStageList);
			
			return "project/projectAudit";
	}
	
}
