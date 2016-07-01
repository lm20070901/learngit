<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<title>广告</title>
<style type="text/css">

</style>
</head>
<body>
<div class="pd-20">
	<form id="arForm" action="<%=request.getContextPath()%>/focusPicture/selectLinkedArticle" method="post">
	<div class="text-l">
		标题：<input type="text" class="input-text" style="width:150px" placeholder="输入标题" id="" name="title" value="${title}"> &nbsp;&nbsp;
		
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
		<button type="button" class="btn btn-primary radius" id="selectArticle" onclick="selectLinkedArticle()">确认选择</button>
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
		<div style="display: none"><input type="text" id="category" name="category" value="${category}"/></div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="2">广告列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="150">标题</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="3">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="content" items="${data}" varStatus="state">
					<tr class="text-c">
						<td><input type="radio" value="${content.contentId}" name="contentId"></td>
						<td>${content.contentId}</td>
						<td flag="title">${content.title}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	</form>
	<div id="pagination"></div>
</div>


<script type="text/javascript">

function selectLinkedArticle() {
	var selectedBox = $("input[name='contentId']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择需要关联的文章",{icon:1,time:2000});
		return;
	}
	
	parent.$('#articleId').val(selectedBox.val());
	parent.$('#articleTitle').val($('td:eq(2)',$(selectedBox.parent().parent())).text());
	
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

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
        	window.location.href="<%=request.getContextPath()%>/focusPicture/selectLinkedArticle?page="+num+"&size=${pager.pageSize}&category=${category}";
    }
});


</script>
</body>
</html>
