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
<title>举报管理</title>
<style type="text/css">

</style>
</head>
<body>

<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 举报管理 <span class="c-gray en">&gt;</span> 举报列表	
	<a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="arForm" action="<%=request.getContextPath()%>/report/selectPageList" method="post">
	<div class="text-l">
		举报内容：<input type="text" class="input-text" style="width:150px" placeholder="输入举报内容" id="reportBody" name="reportBody" value="${reportBody}"> &nbsp;&nbsp;
		举报原因：
		<div style="display: inline-block;width: 80px">
			<span class="select-box">
				<select class="select" size="1" name="reportType">
			        <option value="" <c:if test="${empty reportType}">selected</c:if>>全部</option>
			        <option value="1" <c:if test="${reportType == 1}">selected</c:if>>内容非法</option>
			        <option value="2" <c:if test="${reportType == 2}">selected</c:if>>色情</option>
		      	</select>
	      	</span>
	     </div>
	      	 &nbsp;&nbsp;
	      	是否处理：
		<div style="display: inline-block;width: 80px">
			<span class="select-box">
				<select class="select" size="1" name="isHandel">
			        <option value="" <c:if test="${empty isHandel}">selected</c:if>>全部</option>
			        <option value="1" <c:if test="${isHandel == 1}">selected</c:if>>是</option>
			        <option value="0" <c:if test="${isHandel == 0}">selected</c:if>>否</option>
		      	</select>
	      	</span>
	     </div>
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
		    <shiro:hasPermission name="report:report:toDetail">
				<a href="javascript:;" onclick="detail_category('小组详情','<%=request.getContextPath()%>/report/toDetailPage','800','500')" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe695;</i> 查看详情
				</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="report:report:delete">
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
				<th width="80">ID</th>
				<th width="200">举报内容</th>
				<th width="130">举报原因</th>
				<th width="150">举报账号</th>
				<th width="100">举报时间</th>
				<th width="80">是否处理</th>
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
						<td><input type="checkbox" value="${content.id}" name="selectBox" id="id"></td>
						<td>${content.id}</td>
						<td>${content.reportBody}</td>
						<td>
							<c:if test="${content.reportType == 1}">内容非法
							</c:if>
							<c:if test="${content.reportType == 2}">色情
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
						<td>
							<c:if test="${content.isHandel == 1}">是
							</c:if>
							<c:if test="${content.isHandel == 0}">否
							</c:if>
						</td>
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
		queryUrl  : "<%=request.getContextPath()%>/report/selectPageList",
		deleteUrl : "<%=request.getContextPath()%>/report/batchDelete",
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
	
	/*进入编辑页面*/
	 function detail_category(title,url,w,h) {
		var selectedBox = $("input[name='selectBox']:checked");
		if(selectedBox.length == 0) {
			layer.msg("请选择后操作",{icon:1,time:2000});
			return;
		}
		if(selectedBox.length > 1) {
			layer.msg("一次只能查看一条记录！",{icon:1,time:2000});
			return;
		}
		var index = layer.open({
			type:2,
			title:title,
			area:[w + 'px', h + 'px'],
			maxmin:true,
			content:url + '?id=' + selectedBox.val()
		});
		layer.full(index);
	}
	
	
</script>
</body>
</html>
