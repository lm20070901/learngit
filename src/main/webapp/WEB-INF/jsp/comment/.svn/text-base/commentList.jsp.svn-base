<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
<title>评论列表</title>
<style type="text/css"></style>
	
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 评论管理 <span class="c-gray en">&gt;</span> 评论列表 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="text-l">
	<form action="<%=request.getContextPath()%>/comment/commentList" method="post">
		内容ID：<input type="text" class="input-text" style="width:150px" placeholder="输入检索内容" id="contentId" name="contentId" value="${contentId}"> &nbsp;&nbsp;
		评论内容：<input type="text" class="input-text" style="width:80px" placeholder="输入检索内容" id="commentBody" name="commentBody" value="${commentBody}"> &nbsp;&nbsp;
		评论作者：<input type="text" class="input-text" style="width:100px" placeholder="输入检索内容" id="replyUserName" name="replyUserName" value="${replyUserName}"> &nbsp;&nbsp;
	 	开始时间：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		结束时间：
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<shiro:hasPermission name="society:comment:delete">
			<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="society:comment:hidden">
			<a href="javascript:;" onclick="hiddenOpr();" class="btn btn-warning radius">
				<i class="Hui-iconfont"></i> 隐藏/显示
			</a> 
			</shiro:hasPermission>
		</span> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="9">动态列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="40">ID</th>
				<th width="200">评论内容</th>
				<th width="50">评论作者</th>
				<th width="30">状态</th>
				<th width="100">作者账号</th>
				<th width="130">评论时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="comment" items="${commentList}">
			<tr class="text-c">
				<td><input type="checkbox" value="${comment.commentId}" name="selectBox"></td>
				<td>${comment.commentId}</td>
				<td>${comment.commentBody}</td>
				<td>${comment.replyUserName}</td>
				<td>
					<c:if test="${comment.showStatus==0}">显示</c:if>
					<c:if test="${comment.showStatus==1}">隐藏</c:if>
				</td>
				<td>${comment.userName}</td>
				<td>
				<%-- <c:if test="${content.posttime!=null}">  --%>
					<date:date value="${content.dateline==null?0:content.dateline}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>

<script type="text/javascript">
			
			
			//初始化
			TableUtils.init({
				deleteUrl : "<%=request.getContextPath()%>/comment/deleteComments",
				updateUrl : "<%=request.getContextPath()%>/comment/hiddenComments",
				queryUrl  : "<%=request.getContextPath()%>/comment/commentList",
				page: {
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
			
			/*隐藏操作*/
			function hiddenOpr(){
				TableUtils.updateOpr(function (){
					var selectTrs = TableUtils.selectTrs;
					TableUtils.updateColumnsValue(selectTrs,5,'隐藏');
				});
			}
			
			
			
</script>
</body>
</html>
