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
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 问答管理 <span class="c-gray en">&gt;</span>
	 评论管理
	 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="hotNewsForm" action="<%=request.getContextPath()%>/question/answerList" method="post">
	<div class="text-l">
		内容ID：<input type="text" class="input-text" style="width:80px" id="" name="id" value="${id}"> &nbsp;&nbsp;
		评论内容：<input type="text" class="input-text" style="width:150px" id="" name="content" value="${content}"> &nbsp;&nbsp;
		评论作者：<input type="text" class="input-text" style="width:150px" id="" name="author" value="${author}"> &nbsp;&nbsp;
	 	开始时间：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		结束时间：
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="deleteAnswer()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a> 
			<a href="javascript:;" onclick="modifyAnswerVisibility()" class="btn btn-warning radius">
				隐藏/显示
			</a> 
		</span> 
		<span class="r">共有数据：<strong>${fn:length(data)}</strong> 条</span> </div>
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
	</form>
</div>


	<script type="text/javascript">
	
	TableUtils.init({
		"deleteUrl" : "<%=request.getContextPath()%>/question/deleteAnswers",
		"updateUrl" : "<%=request.getContextPath()%>/question/modifyAnswerVisibility",
		"queryUrl"  : "<%=request.getContextPath()%>/question/answerList",
		"page": {//分页
			"totalPage"  :  ${page.totalPage},
			"cpage"      :  ${page.cpage},
			"pageSize"   :  ${page.pageSize} 
		}
	});
	
	//显示/隐藏
	function modifyAnswerVisibility() {
		TableUtils.updateMsg = "确定改变记录显示状态吗？";
		TableUtils.updateOpr(function() {
			$("#search").trigger("click");
		});
	}
	
	//批量删除
	function deleteAnswer() {
		TableUtils.deleteOpr(function (){
			$('#search').trigger('click');
		});
	}

</script>
</body>
</html>
