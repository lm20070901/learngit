<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
<title>小组成员</title>
</head>
<body>
<div class="pd-20">
		<div class="row cl">
		<fieldset>
			<legend>小组成员</legend>
   <div class="pd-20">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
		<div style="display: none"><input type="text" id="groupId" name="groupId" value="${groupId}"/></div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="50">ID</th>
				<th width="150">昵称</th>
				<th width="130">加入时间</th>
				<th width="200">发表帖子数</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="11">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="content" items="${data}" varStatus="state">
					<tr class="text-c">
						<td>${content.userId}</td>
						<td>${content.nickName}</td>
						<c:if test="${not empty content.joinTime}">
							<jsp:useBean id="joinTime" class="java.util.Date"/> 
							<c:set target="${joinTime}" property="time" value="${content.joinTime}"/> 
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${joinTime}" type="both"/> </td>
						</c:if>
						<c:if test="${empty content.joinTime}">
							<td></td>
						</c:if>
						<td>${content.postNum}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	  </table>
	 <div id="pagination"></div>
    </div>
	</fieldset>
	</div>
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
        	window.location.href="<%=request.getContextPath()%>/groups/selectUsers?page="+num+"&size=${pager.pageSize}&groupId=${groupId}";
    }
});
</script>
</body>
</html>