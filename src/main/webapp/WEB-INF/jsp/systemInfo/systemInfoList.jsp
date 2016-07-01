<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
<title>系统消息列表</title>
<style type="text/css"></style>
	
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统消息<span class="c-gray en">&gt;</span> 系统消息列表 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="text-l">
	<form action="<%=request.getContextPath()%>/systemInfo/systemInfoList" method="post">
		内容：<input type="text" class="input-text" style="width:150px" placeholder="输入检索内容" id="" name="contentBody" value="${contentBody}"> &nbsp;&nbsp;
	 	开始时间：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		结束时间：
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<shiro:hasPermission name="society:systemInfo:toAdd">
			<a href="javascript:;" onclick="addOpr();" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe600;</i> 新建
			</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="society:systemInfo:delete">
			<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a>
			</shiro:hasPermission> 
		</span> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="9">动态列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="40">ID</th>
				<th width="200">消息内容</th>
				<th width="100">作者</th>
				<th width="100">账号</th>
				<th width="130">发布时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="systemInfo" items="${systemInfoList}">
			<tr class="text-c">
				<td><input type="checkbox" value="${systemInfo.contentId}" name="selectBox"></td>
				<td>${systemInfo.contentId}</td>
				<td>${systemInfo.contentBody}</td>
				<td>${systemInfo.roleName}</td>
				<td>${systemInfo.userName}</td>
				<td>
				<%-- <c:if test="${content.posttime!=null}">  --%>
					<date:date value="${systemInfo.postTime==null?0:systemInfo.postTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>

<script type="text/javascript">
			//初始化
			TableUtils.init({
				deleteUrl : "<%=request.getContextPath()%>/systemInfo/delelteSystemInfo",
				updateUrl : "<%=request.getContextPath()%>/systemInfo/saveSystemInfo",
				queryUrl  : "<%=request.getContextPath()%>/systemInfo/systemInfoList",
				page: {//分页
					totalPage   :  ${pager.totalPage},
					cpage       :  ${pager.cpage},
					pageSize    :  ${pager.pageSize} ,
					totalCounts : ${pager.totalRow}
				}
			}); 
			
			 
			/*删除*/
			function deleteOpr(){
				TableUtils.deleteOpr(function (){
					 location.reload();
				});
			}
			
			//查看操作
			function addOpr(){
				var addUrl = "<%=request.getContextPath()%>/systemInfo/toAddSystemInfo/";
				
				var index = layer.open({
					type: 2,
					title: '发布系统消息', 
					content: addUrl
				});
				layer.full(index);
			}
			
			
</script>
</body>
</html>
