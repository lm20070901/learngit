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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 内容管理 <span class="c-gray en">&gt;</span> 评论管理 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="text-l">
	<form id="arCommentForm" action="<%=request.getContextPath()%>/article/articleCommentList" method="post">
		资讯ID：<input type="text" class="input-text" style="width:80px" placeholder="输入ID" id="" name="articleId" value="${articleId}"> &nbsp;&nbsp;
		评论内容：<input type="text" class="input-text" style="width:160px" placeholder="输入评论内容" id="" name="content" value="${content}"> &nbsp;&nbsp;
		评论作者：<input type="text" class="input-text" style="width:100px" placeholder="输入评论作者" id="" name="author" value="${author}"> &nbsp;&nbsp;
	 	开始时间：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		结束时间：
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="modifyArCommentVisibility()" class="btn btn-warning radius">
				隐藏/显示
			</a> 
			<a href="javascript:;" onclick="deleteArticleComment()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a> 
		</span> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
		<div style="display: none"><input type="text" id="contentType" name="contentType" value="${contentType}"/></div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="8">评论列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="210">内容</th>
				<th width="210">文章标题</th>
				<th width="50">状态</th>
				<th width="60">评论作者</th>
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
						<td class="text-l">${comment.commentBody}</td>
						<td class="text-l">${comment.articleTitle}</td>
						
						<td flag="${comment.showStatus}">
							<c:if test="${comment.showStatus == 0}">
								<span class="c-red">
									显示
								</span>
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
        	window.location.href="<%=request.getContextPath()%>/article/articleCommentList?page="+num+"&size=${pager.pageSize}&contentType=${contentType}&" + $("#arCommentForm").serialize();
    }
});

var WEB_PATH = "<%=basePath%>";

/*计算选择的记录中已指定状态的记录数*/
function countRecord(selectedBox, state) {
	var count = 0;
	for(var i = 0; i < selectedBox.length; i++) {
		if($('[flag]',$(selectedBox[i]).parent().parent()).attr('flag') == state) {
			count++;
		}
	}
	return count;
}

/*隐藏资讯评论*/
function modifyArCommentVisibility() {
	var selectedBox = $("input[name='commentId']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	
	layer.confirm("确认修改记录显示状态吗?",function() {
		$.ajax({
			type:"post",
            url:WEB_PATH + "/article/modifyArticleCommentVisibility",
            dataType:'json',
            data: $("#arCommentForm").serialize(),
            success:function(data) {
            	layer.msg(data.msg,{icon:1,time:2000});
            	if(data.result == 1) {
            		setTimeout(function(){
            			var index = parent.layer.getFrameIndex(window.name);
            			$('#search').trigger('click');
		    			parent.layer.close(index);
            		},2000);
            	} 
            },
            error:function(e) {
            	layer.msg("系统或者网络出错",{icon:1,time:2000});
            }
		});
	});
	return false;
}


/*删除资讯*/
 function deleteArticleComment() {
	var selectedBox = $("input[name='commentId']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	layer.confirm("确认要删除吗？",function(index){
		$.ajax({
			type:"post",
			url:WEB_PATH + "/article/deleteArticleComment",
			dataType:"json",
			data:$("#arCommentForm").serialize(),
			success:function(data) {
				layer.msg(data.msg,{icon:1,time:2000});
            	if(data.result == 1) {
            		setTimeout(function(){
            			var index = parent.layer.getFrameIndex(window.name);
            			$('#search').trigger('click');
		    			parent.layer.close(index);
            		},2000);
            	} 
			},
			error:function(e){
				layer.msg("系统或者网络出错",{icon:1,time:2000});
			}
		});
	});
}

</script>
</body>
</html>
