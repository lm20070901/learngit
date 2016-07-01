<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
<title>评论管理</title>
<style type="text/css">

</style>
</head>
<body>
<div class="pd-20">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">共有数据：<strong>${page.totalRow}</strong> 条</span> 
	</div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="7">问题列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="260">评论内容</th>
				<th width="60">评论作者</th>
				<th width="40">状态</th>
				<th width="100">作者账号</th>
				<th width="100">评论时间</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="7">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="answer" items="${data}" varStatus="state">
					<tr class="text-c">
						<td><input type="checkbox" value="${answer.answerId}" name="selectBox" id="selectBox"></td>
						<td>${answer.answerId}</td>
						<td class="text-l">${answer.content}</td>
						<td>${answer.nickName}</td>
						<td>
							<c:if test="${answer.showStatus == 0 }"><span class="c-red">显示</span></c:if>
							<c:if test="${answer.showStatus == 1 }">隐藏</c:if>
						</td>
						<td>${answer.userName}</td>
						<td><date:date value="${answer.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>


	<script type="text/javascript">
	
	TableUtils.init({
		"deleteUrl" : "",
		"updateUrl" : "",
		"queryUrl"  : "<%=request.getContextPath()%>/question/answerListByQid/${questionId}",
		"page": {//分页
			"totalPage"  :  ${page.totalPage},
			"cpage"      :  ${page.cpage},
			"pageSize"   :  ${page.pageSize} 
		}
	});
	

</script>
</body>
</html>
