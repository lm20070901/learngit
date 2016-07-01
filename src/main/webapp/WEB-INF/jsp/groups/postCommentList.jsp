<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<title>评论管理</title>
<style type="text/css">

</style>
</head>
<body>
<div class="pd-20">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="8">评论列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="150">内容</th>
				<th width="150">文章标题</th>
				<th width="100">状态</th>
				<th width="130">评论作者</th>
				<th width="100">作者账号</th>
				<th width="100">评论时间</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="8">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="comment" items="${data}" varStatus="state">
					<tr class="text-c">
						<td><input type="checkbox" value="${comment.commentId}" name="commentId" id="commentId"></td>
						<td>${state.index + 1}</td>
						<td>${comment.commentBody}</td>
						<td>${comment.title}</td>
						
						<td flag="${comment.showStatus}">
							<c:if test="${comment.showStatus == 0}">
								显示
							</c:if>
							<c:if test="${comment.showStatus == 1}">
								隐藏
							</c:if>
						</td>
						
						<td>
							${comment.nickName }
						</td>
						<td>
							${comment.userName }
						</td>
						
						<td><date:date value="${comment.dateline}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>

<script type="text/javascript">
$.jqPaginator('#pagination', {
    totalPages: ${pager.totalPage},
    visiblePages: 5,
    currentPage: ${pager.cpage},
    first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
    prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
    next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
    last: '<li class="last"><a href="javascript:void(0);">末页</a></li>',
    page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
    onPageChange: function (num) {
    	if(num!=${pager.cpage})
        	window.location.href="<%=request.getContextPath()%>/groupPost/postCommentList?page="+num+"&size=${pager.pageSize}";
    }
});


</script>
</body>
</html>
