<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
<title>问题管理</title>
<style type="text/css">

</style>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 问答管理 <span class="c-gray en">&gt;</span>
	 问题管理
	 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="hotNewsForm" action="<%=request.getContextPath()%>/question/questionList" method="post">
	<div class="text-l">
		标题：<input type="text" class="input-text" style="width:150px" placeholder="输入标题" id="" name="title" value="${title}"> &nbsp;&nbsp;
		
	 	开始时间：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		结束时间：
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="delete_question()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a> 
			<a href="javascript:;" onclick="modify_question_state()" class="btn btn-warning radius">
				隐藏/显示
			</a> 
			<a href="javascript:;" onclick="show_comment(800, 500)" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe695;</i> 查看评论
			</a> 
		</span> 
		<span class="r">共有数据：<strong>${fn:length(data)}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="8">问题列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="280">标题</th>
				<th width="70">作者</th>
				<th width="30">状态</th>
				<th width="100">发布时间</th>
				<th width="50">阅读量</th>
				<th width="50">回答</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="8">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="question" items="${data}" varStatus="state">
					<tr class="text-c">
						<td><input type="checkbox" value="${question.questionId}" name="selectBox" id="selectBox"></td>
						<td>${question.questionId}</td>
						<td class="text-l">${question.title}</td>
						<td>${question.nickName}</td>
						<td>
							<c:if test="${question.showStatus == 0 }"><span class="c-red">显示</span></c:if>
							<c:if test="${question.showStatus == 1 }">隐藏</c:if>
						</td>
						<td><date:date value="${question.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${( empty question.readTimes || question.readTimes < 0) ? 0 : question.readTimes}</td>
						<td>${(empty question.answerTimes || question.answerTimes < 0) ? 0 : question.answerTimes}</td>
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
		"deleteUrl" : "<%=request.getContextPath()%>/question/deleteQuestions",
		"updateUrl" : "<%=request.getContextPath()%>/question/modifyVisibility",
		"queryUrl"  : "<%=request.getContextPath()%>/question/questionList",
		"page": {//分页
			"totalPage"  :  ${page.totalPage},
			"cpage"      :  ${page.cpage},
			"pageSize"   :  ${page.pageSize} 
		}
	});
	
	//显示/隐藏
	function modify_question_state() {
		TableUtils.updateMsg = "确定改变记录显示状态吗？";
		TableUtils.updateOpr(function() {
			$("#search").trigger("click");
		});
	}
	
	//批量删除
	function delete_question() {
		TableUtils.deleteOpr(function (){
			$('#search').trigger('click');
		});
	}
	
	//查看评论
	function show_comment(w, h) {
		var selectedBox = $("input[name='selectBox']:checked");
		if(selectedBox.length == 0) {
			layer.msg("请选择后操作",{icon:1,time:2000});
			return;
		}
		if(selectedBox.length > 1) {
			layer.msg("一次只能查看一条记录的评论！",{icon:1,time:2000});
			return;
		}
		var url = "<%=request.getContextPath()%>/question/answerListByQid/" + selectedBox.val();
		var index = layer.open({
			type:2,
			area:[w + 'px', h + 'px'],
			maxmin:true,
			content:url
		});
		layer.full(index);
	}

</script>
</body>
</html>
