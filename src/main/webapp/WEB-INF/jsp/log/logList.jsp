<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
<title>日志管理</title>
<style type="text/css">

</style>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span>
	 日志管理
	 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="hotNewsForm" action="<%=request.getContextPath()%>/adminLog/logList" method="post">
	<div class="text-l">
		日志类容：<input type="text" class="input-text" style="width:150px" placeholder="输入检索内容" name="logInfo" value="${logInfo}"> &nbsp;&nbsp;
		操作类型 ：
		<div style="display: inline-block;width: 120px">
				<span class="select-box">
					<select class="select" size="1" name="operType">
						<option value="">全部</option>
						<c:forEach items="${type}" var="item">
							<option value="${item.key }" <c:if test="${item.key == operType }">selected</c:if>>${item.value}</option>
						</c:forEach>
					</select>
				</span>
			</div> &nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">共有数据：<strong>${fn:length(data)}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="6">日志列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="250">内容</th>
				<th width="60">操作人</th>
				<th width="80">操作类型</th>
				<th width="80">操作时间</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="6">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="log" items="${data}" varStatus="state">
					<tr class="text-c">
						<td><input type="checkbox" value="${log.logId}" name="selectBox" id="selectBox"></td>
						<td>${log.logId}</td>
						<td class="text-l">${log.logInfo}</td>
						<td>${log.nickName}</td>
						<td>
							<c:forEach items="${type }" var="item">
								<c:if test="${item.key == log.operationType}">
									${item.value }
								</c:if>
							</c:forEach>
						</td>
						<td><date:date value="${log.logTime }" pattern="yyyy-MM-dd HH:mm:ss"></date:date></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<div id="pagination"></div>
	</form>
</div>


	<script type="text/javascript">
	
	TableUtils.init({
		"deleteUrl" : "",
		"updateUrl" : "",
		"queryUrl"  : "<%=request.getContextPath()%>/adminLog/logList",
		"page": {//分页
			"totalPage"  :  ${page.totalPage},
			"cpage"      :  ${page.cpage},
			"pageSize"   :  ${page.pageSize} 
		}
	});
	
</script>
</body>
</html>
