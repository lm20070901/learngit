<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
<title>评论管理</title>
<style type="text/css">

</style>
</head>
<body>

<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 评论管理<span class="c-gray en">&gt;</span> 评论列表	
	<a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="arForm" action="<%=request.getContextPath()%>/groupPostComment/commentList" method="post">
	<div class="text-l">
		内容ID：<input type="text" class="input-text" style="width:150px" placeholder="输入内容ID" id="commentId" name="commentId" value="${commentId}"> 
		评论内容：<input type="text" class="input-text" style="width:150px" placeholder="输入评论内容" id="commentBody" name="commentBody" value="${commentBody}"> 
		 评论作者：<input type="text" class="input-text" style="width:150px" placeholder="输入评论作者" id="userName" name="userName" value="${userName}">
		 &nbsp;&nbsp;
	 	开始时间：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">
		结束时间：
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">
		&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
		
			<shiro:hasPermission name="gpComment:comment:hide">
				<a href="javascript:;" onclick="hideOpr()" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe603;</i> 隐藏/显示
				</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="gpComment:comment:delete">
				<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
					<i class="Hui-iconfont">&#xe6e2;</i> 删除
				</a> 
			</shiro:hasPermission>
		</span> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
		<%-- <div style="display: none"><input type="text" id="contentType" name="contentType" value="${contentType}"/></div> --%>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="25"></th>
				<th width="50">ID</th>
				<th width="200">评论内容</th>
				<th width="150">评论作者</th>
				<th width="100">状态</th>
				<th width="150">作者账号</th>
				<th width="150">评论时间</th>
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
						<td><input type="checkbox" value="${content.commentId}" name="selectBox" id="commentId"></td>
						<td>${content.commentId}</td>
						<td>${content.commentBody}</td>
						<td>${content.nickName}</td>
						<td flag="${content.showStatus }">
							 <c:if test="${content.showStatus == 1}">隐藏
							</c:if>
							<c:if test="${content.showStatus == 0}">显示
							</c:if> 
						</td>
						<td>${content.userName}</td>
						<c:if test="${not empty content.dateline}">
							<jsp:useBean id="dateline" class="java.util.Date"/> 
							<c:set target="${dateline}" property="time" value="${content.dateline}"/> 
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dateline}" type="both"/> </td>
						</c:if>
						<c:if test="${empty content.dateline}">
							<td></td>
						</c:if>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	</form>
	<div id="pagination"></div>
</div>

<script type="text/javascript">
	//初始化
	TableUtils.init({
		queryUrl  : "<%=request.getContextPath()%>/groupPostComment/commentList",
		deleteUrl : "<%=request.getContextPath()%>/groupPostComment/deleteComment",
		page: {//分页
			totalPage  :  ${pager.totalPage},
			cpage      :  ${pager.cpage}, 
			pageSize   :  ${pager.pageSize} 
		}
	});
	
	/*删除*/
	function deleteOpr(){
		TableUtils.deleteOpr(function (){
			 location.reload();
		});
	}
	
	/* 隐藏/显示 */
	function hideOpr(){
		var selectedBox = $("input[name='selectBox']:checked");
		if(selectedBox.length == 0) {
			layer.msg("请选择后操作",{icon:1,time:2000});
			return;
		}
		var state = $('[flag]',$(selectedBox[0]).parent().parent()).attr('flag');
		if(countRecord(selectedBox, state) != selectedBox.length){
			layer.msg("请选择同一状态的数据操作",{icon:1,time:2000});
			return;
		}
		var ids = TableUtils.getSelectedRowsId();
		if(!ids) return;
		var idArr = ids.split(',');
		var value = 0;
		if(state == 0){
			value = 1;
		}
		layer.confirm("确认要隐藏/显示吗？",function(index){
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath()%>/groupPostComment/hideComment",
				dataType:"json",
				data:{'idList':idArr,'value':value},
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


	
	
	 /*查看评论*/
	 function comment_category(title,url,w,h) {
		var selectedBox = $("input[name='groupId']:checked");
		if(selectedBox.length == 0) {
			layer.msg("请选择后操作",{icon:1,time:2000});
			return;
		}
		if(selectedBox.length > 1) {
			layer.msg("只能选择一行记录!",{icon:1,time:2000});
			return;
		}
		var index = layer.open({
			type:2,
			title:title,
			area:[w + 'px', h + 'px'],
			maxmin:true,
			content:url + '?groupId=' + selectedBox.val()
		});
		layer.full(index);
		
	}
	 
</script>
</body>
</html>
