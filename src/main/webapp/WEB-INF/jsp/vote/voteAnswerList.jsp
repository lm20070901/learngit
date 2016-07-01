<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>

	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 

<title>回答列表</title>
<style type="../text/css">
</style>
</head>

<body>
<div class="pd-20">
	<div class="text-l">
		<form action="<%=request.getContextPath()%>/vote/toVoteAnserList" method="post">
		 	
			<input type="hidden" class="input-text" id="questionId" name="questionId" value="${questionId}"> 
		</form>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span>
	</div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="5">填空题回答列表</th>
			</tr>
			<tr>
				<th class="text-c" width="40">ID</th>
				<th width="500">回答内容</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="answer" items="${answerList}">
			<tr>
				<td class="text-c">${answer.id}</td>
				<td>${answer.optionIds}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>
	<script type="text/javascript">
		//初始化
		TableUtils.init({
			// queryUrl  : "<%=request.getContextPath()%>/vote/toVoteAnserList?questionId=${questionId}",
			queryUrl  : "<%=request.getContextPath()%>/vote/toVoteAnserList",
			page: {//分页
				totalPage  :  ${pager.totalPage},
				cpage      :  ${pager.cpage},
				pageSize   :  ${pager.pageSize} 
			}
		});
		
	</script>
</body>
</html>
