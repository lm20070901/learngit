<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>

	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 

<title>项目发布</title>
</head>

<body>

	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>项目管理<span class="c-gray en">&gt;</span>项目发布 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
<div class="pd-20">
	<div class="text-l">
		<form action="<%=request.getContextPath()%>/project/getProjectList" method="post">
			<input name="isAdmin" value="${isAdmin}" type="hidden"/>
			<input name="isRecommend" value="${isRecommend}" type="hidden"/>
		 	项目名称 ：
			<input type="text" class="input-text" style="width:150px" placeholder="请输入项目名称" id="projectName" name="projectName" value="${projectName}" readonly="readonly">
			所属地区 : 
			<span class="select-box"  style="width:100px;"> 
				<select class="select" id="areaId" name="areaId">
						<option value="" selected>全部</option>
						<c:forEach items="${districtList}" var="area">
							<option value="${area.id}" <c:if test="${areaId==area.id}">selected </c:if>>${area.name}</option>
						</c:forEach>
				</select>
			</span>
			所属行业 : 
			<span class="select-box"  style="width:100px;"> 
				<select class="select" id="industry" name="industry">
						<option value="" selected>全部</option>
						<c:forEach items="${industryList}" var="item">
							<option value="${item.code}" <c:if test="${industry==item.code}">selected </c:if>>${item.name}</option>
						</c:forEach>
				</select>
			</span>
			<c:if test="${isAdmin}"> 发布状态 : </c:if>
			<c:if test="${!isAdmin}">采纳状态 : </c:if>
			<span class="select-box"  style="width:100px;"> 
						<select class="select" id="projectStatus" name="projectStatus">
						<option value="" selected>全部</option>
				<c:choose>
					<c:when test="${isAdmin}">
								<option value="0" <c:if test="${projectStatus==0}">selected </c:if>>待发布</option>
								<option value="1" <c:if test="${projectStatus==1}">selected </c:if>>已发布</option>
					</c:when>
					<c:otherwise>
						<option value="0" <c:if test="${projectStatus==0}">selected </c:if>>待采纳</option>
						<option value="1" <c:if test="${projectStatus==1}">selected </c:if>>已采纳</option>
						<option value="2" <c:if test="${projectStatus==2}">selected </c:if>>未采纳</option>
					</c:otherwise>
				</c:choose>
					</select>
			</span>
			<button type="submit" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
		</form>
	</div>
	
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<c:if test="${isAdmin and !isRecommend}">
				
				<shiro:hasPermission name="project:publish:toEdit">
				<a href="javascript:;" onclick="addOpr();" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe600;</i> 新建
				</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="project:publish:toEdit">
				<a href="javascript:;" onclick="editOpr();" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe647;</i>编辑
				</a> 
				</shiro:hasPermission>
				<shiro:hasPermission name="project:publish:publish">
				<a href="javascript:;" onclick="publishOpr()" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe603;</i> 发布
				</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="project:publish:recommendStatus">
				<a href="javascript:;" onclick="recommendOpr();" class="btn btn-primary  radius">
					<i class="Hui-iconfont"></i>  推荐
				</a> 
				</shiro:hasPermission>
				<shiro:hasPermission name="project:publish:changeShow">
				<a href="javascript:;" onclick="hiddenOrShowOpr();" class="btn btn-warning radius">
					<i class="Hui-iconfont"></i> 隐藏/显示
				</a> 
				</shiro:hasPermission>
				<shiro:hasPermission name="project:publish:delete">
				<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
					<i class="Hui-iconfont">&#xe6e2;</i> 删除
				</a> 
				</shiro:hasPermission>
			</c:if>
			<c:if test="${!isAdmin and !isRecommend }">
				<shiro:hasPermission name="project:publish:toAudit">
				<a href="javascript:;" onclick="auditOpr();" class="btn btn-primary  radius">
					<i class="Hui-iconfont"></i> 审核
				</a> 
				</shiro:hasPermission>
				<shiro:hasPermission name="project:publish:delete">
				<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
					<i class="Hui-iconfont">&#xe6e2;</i> 删除
				</a> 
				</shiro:hasPermission>
			</c:if>
			<c:if test="${isAdmin and isRecommend }">
				<shiro:hasPermission name="project:publish:recommendStatus">
				<a href="javascript:;" onclick="removeOpr();" class="btn btn-warning radius">
					<i class="Hui-iconfont"></i> 移除
				</a> 
				</shiro:hasPermission>
			</c:if>
		</span> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span>
	</div>
	<table class="table table-border table-bordered table-bg table-sort" style="table-layout:fixed;">
		<thead>
			<tr>
				<th scope="col" colspan="9">项目列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="20">ID</th>
				<th width="150">项目名称</th>
				<th width="90">所属行业</th>
				<th width="100">所属地区</th>
				<th width="130">所属阶段</th>
				<th width="130">项目估值(万)</th>
				<c:if test="${!isRecommend}">
				<th width="30"><c:if test="${isAdmin}">发布状态</c:if><c:if test="${!isAdmin}">采纳状态</c:if></th>
				</c:if>
				<th width="30">显示状态</th>
				<th width="130">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="project" items="${projectList}">
			<tr class="text-c">
				<td><input type="checkbox" value="${project.projectId}" name="selectBox"></td>
				<td>${project.projectId}</td>
				<td >${project.projectName}</td>
				<td>${project.industryName}</td>
				<td>${project.areaName}</td>
				<td>${project.projectStageName}</td>
				<td>
					${project.projectValuation}
				</td>
				<c:if test="${!isRecommend}">
				<td>
					<c:if test="${isAdmin}">
						<c:if test="${project.projectStatus==0}">待发布</c:if>
						<c:if test="${project.projectStatus==1}">已发布</c:if>
					</c:if>
					<c:if test="${!isAdmin}">
						<c:if test="${project.projectStatus==0}">待采纳</c:if>
						<c:if test="${project.projectStatus==1}">已采纳</c:if>
						<c:if test="${project.projectStatus==2}">未采纳</c:if>
					</c:if>
				</td>
				<td>
					<c:if test="${!project.showStatus}">显示</c:if> 
					<c:if test="${project.showStatus}">隐藏</c:if>
				</td>
				</c:if>
				<td><date:date value="${project.creatTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>
	<script type="text/javascript">
	
		//初始化
		TableUtils.init({
			deleteUrl : "<%=request.getContextPath()%>/project/deleteProjectByIds",
			updateUrl : "<%=request.getContextPath()%>/project/updateShowStatus",
			queryUrl  : "<%=request.getContextPath()%>/project/getProjectList",
			page: {//分页
				totalPage  :  ${pager.totalPage},
				cpage      :  ${pager.cpage},
				pageSize   :  ${pager.pageSize} 
			}
		});
		
			/*新增*/
			function addOpr(){
				var index = layer.open({
					type: 2,
					title: "新增项目",
					content: "<%=request.getContextPath()%>/project/toEditProject"
				});
				
				layer.full(index);
			}
			
			/**编辑*/
			function editOpr(){
				var selectTrs  = TableUtils.getSelectedRows();
				
				if (!selectTrs)  return;
				
				if(selectTrs.length != 1){
					layer.msg("只能选择一行记录!",{icon:1,time:2000});
					return;
				}
				
				var text = $(selectTrs[0]).children('td').eq(-3).text();
				if('待发布' != text.trim()){
						layer.msg("只能编辑待发布项目!",{icon:1,time:2000});
						return ;
				}
				
				layer.full(layer.open({
					type: 2,
					title: "编辑项目",
					content: "<%=request.getContextPath()%>/project/toEditProject?projectId="+TableUtils.ids 
				}));
				
			}
			/* 显示/隐藏操作 */
			function hiddenOrShowOpr(){
				
				TableUtils.updateUrl = "<%=request.getContextPath()%>/project/updateShowStatus";
				TableUtils.updateMsg = "确认要隐藏/显示吗？";
				TableUtils.updateOpr(function (){
					var selectTrs = TableUtils.selectTrs;
					for ( var i = 0; i < selectTrs.length; i++) {
						var text = $(selectTrs[i]).children('td').eq(-2).text();
						if('隐藏' == text.trim()){
							text = '显示';
						}else if('显示' == text.trim()){
							text = '隐藏';
						}
						$(selectTrs[i]).children('td').eq(-2).text(text);//改变某个列的值 
					}
					
				});
				
			}
			
			
			//发布
			function publishOpr(){
				var selectTrs  = TableUtils.getSelectedRows();
				if (!selectTrs)  return;
				for ( var i = 0; i < selectTrs.length; i++) {
					var text = $(selectTrs[i]).children('td').eq(-3).text();
					if('待发布' != text.trim()){
						layer.msg("只能发布待发布项目!",{icon:1,time:2000});
						return ;
					}
				}
				TableUtils.updateUrl = "<%=request.getContextPath()%>/project/publishProject";
				TableUtils.updateMsg = "确认要发布吗？";
				TableUtils.updateOpr(function (){
					
					for ( var i = 0; i < selectTrs.length; i++) {
						var text = $(selectTrs[i]).children('td').eq(-3).text();
						
						$(selectTrs[i]).children('td').eq(-3).text("已发布");//改变某个列的值 
					}
					
				});
			}
			
			 /* 删除*/
			function deleteOpr(){
				TableUtils.deleteOpr(function (){
					 location.reload();
				});
			} 
			 
			 
			 /*推荐*/
			 function recommendOpr(){
					var ids = TableUtils.getSelectedRowsId();
					if(!ids) return;
					var idArr = ids.split(',');
					TableUtils.index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
					
					TableUtils.ajaxPost("<%=request.getContextPath()%>/project/changeRecommendStatus",
							{'idList':idArr,'isRecommend':1},function (returnData){location.reload();});
					
					
					
			 }
			 //移除
			 function  removeOpr(){
				 var ids = TableUtils.getSelectedRowsId();
					if(!ids) return;
					var idArr = ids.split(',');
					TableUtils.index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
					
					TableUtils.ajaxPost("<%=request.getContextPath()%>/project/changeRecommendStatus",
							{'idList':idArr,'isRecommend':0},function (returnData){location.reload();});
			 }
			 
			 //审核
			 function auditOpr(){
				 var selectTrs  = TableUtils.getSelectedRows();
					
					if (!selectTrs)  return;
					
					if(selectTrs.length != 1){
						layer.msg("只能选择一行记录!",{icon:1,time:2000});
						return;
					}
					
					var text = $(selectTrs[0]).children('td').eq(-3).text();
					
					if('待采纳' != text.trim()){
							layer.msg("只能审核待采纳项目!",{icon:1,time:2000});
							return ;
					}
					
					layer.full(layer.open({
						type: 2,
						title: "审核项目",
						content: "<%=request.getContextPath()%>/project/toAuditProject?projectId="+TableUtils.ids 
					}));
			 }
		</script>
</body>
</html>
