<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
<title>热门问题</title>
<style type="text/css">

</style>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 问答管理 <span class="c-gray en">&gt;</span>
	 热门问题
	 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="hotNewsForm" action="<%=request.getContextPath()%>/question/hotQuestionList" method="post">
	<div class="text-l">
		标题：<input type="text" class="input-text" style="width:150px" placeholder="输入标题" id="" name="title" value="${title}"> &nbsp;&nbsp;
		分类 ：
		<div style="display: inline-block;width: 80px">
				<span class="select-box">
					<select class="select" size="1" name="category">
						<option value="">全部</option>
						<c:forEach items="${cateList}" var="item">
							<option value="${item.categoryId }" <c:if test="${item.categoryId == category }">selected</c:if>>${item.categoryName}</option>
						</c:forEach>
					</select>
				</span>
			</div> &nbsp;&nbsp;
	 	开始时间：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		结束时间：
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="modifyHotQuestionOrder('编辑排序','<%=request.getContextPath()%>/question/toModifyHotQuestionOrder','800','500')" class="btn btn-primary radius">
				编辑排序
			</a> 
			<a href="javascript:;" onclick="deleteHotQuestion()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a> 
		</span> 
		<span class="r">共有数据：<strong>${fn:length(data)}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="9">热门资讯列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="40">热度排序</th>
				<th width="260">标题</th>
				<th width="60">分类</th>
				<th width="70">作者</th>
				<th width="100">发布时间</th>
				<th width="60">阅读量</th>
				<th width="60">回答</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="9">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="question" items="${data}" varStatus="state">
					<tr class="text-c">
						<td><input type="checkbox" value="${question.questionId}" name="selectBox" id="selectBox"></td>
						<td>${question.questionId}</td>
						<td>${question.orderNum}</td>
						<td class="text-l">${question.title}</td>
						<td>
							<c:forEach items="${cateList }" var="item">
								<c:if test="${item.categoryId == question.categoryId}">
									${item.categoryName }
								</c:if>
							</c:forEach>
						</td>
						<td>${question.nickName}</td>
						<td><date:date value="${question.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${(empty question.readTimes || question.readTimes < 0) ? 0 : question.readTimes}</td>
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
		"deleteUrl" : "<%=request.getContextPath()%>/question/deleteHotQuestions",
		"updateUrl" : "",
		"queryUrl"  : "<%=request.getContextPath()%>/question/hotQuestionList",
		"page": {//分页
			"totalPage"  :  ${page.totalPage},
			"cpage"      :  ${page.cpage},
			"pageSize"   :  ${page.pageSize} 
		}
	});
	
	//显示/隐藏
	function modifyHotQuestionOrder(title,url,w,h) {
		var selectedBox = $("input[name='selectBox']:checked");
		if(selectedBox.length == 0) {
			layer.msg("请选择后操作",{icon:1,time:2000});
			return;
		}
		if(selectedBox.length > 1) {
			layer.msg("一次只能编辑一条记录！",{icon:1,time:2000});
			return;
		}
		var index = layer.open({
			type:2,
			title:title,
			area:[w + 'px', h + 'px'],
			maxmin:true,
			content:url + '?id=' + selectedBox.val()
		});
		//layer.full(index);
	}
	
	//批量删除
	function deleteHotQuestion() {
		TableUtils.deleteOpr(function (){
			$('#search').trigger('click');
		});
	}

</script>
</body>
</html>
