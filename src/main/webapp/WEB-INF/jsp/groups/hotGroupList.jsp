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
<title>热门小组</title>
<style type="text/css">

</style>
</head>
<body>

<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 热门小组 <span class="c-gray en">&gt;</span> 小组列表	
	<a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="arForm" action="<%=request.getContextPath()%>/groups/HotGroups" method="post">
	<div class="text-l">
		标题：<input type="text" class="input-text" style="width:150px" placeholder="输入标题" id="groupName" name="groupName" value="${groupName}"> &nbsp;&nbsp;
		状态：
		<div style="display: inline-block;width: 80px">
			<span class="select-box">
				<select class="select" size="1" name="state">
			        <option value="" <c:if test="${empty state}">selected</c:if>>全部</option>
			        <c:forEach items="${groupsState }" var="st">
			        	<option value="${st.key }" <c:if test="${state == st.key}">selected</c:if>>${st.value }</option>
			        </c:forEach>
		      	</select>
	      	</span>
	     </div>
	      	 &nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
		<shiro:hasPermission name="groups:HotGroups:toEdit">
				<a href="javascript:;" onclick="edit_category('编辑排序','<%=request.getContextPath()%>/groups/toHotEditPage','800','500')" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe6df;</i> 编辑排序
				</a> 
		</shiro:hasPermission>
		<shiro:hasPermission name="groups:HotGroups:delete">
				<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
					<i class="Hui-iconfont">&#xe6e2;</i> 移除
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
				<th width="80">热度</th>
				<th width="180">名称</th>
				<th width="120">创建人</th>
				<th width="150">创建时间</th>
				<th width="80">成员数</th>
				<th width="80">帖子数</th>
				<th width="80">状态</th>
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
						<td><input type="checkbox" value="${content.groupId}" name="selectBox" id="groupId"></td>
						<td>${content.groupId}</td>
						<td>${content.orderNum}</td>
						<td>${content.groupName}</td>
						<td>${content.userName}</td>
						<c:if test="${not empty content.createTime}">
							<jsp:useBean id="createTime" class="java.util.Date"/> 
							<c:set target="${createTime}" property="time" value="${content.createTime}"/> 
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${createTime}" type="both"/> </td>
						</c:if>
						<c:if test="${empty content.createTime}">
							<td></td>
						</c:if>
						<td>${content.userCount}</td>
						<td>${content.articleCount}</td>
						<td flag="${content.state}"><input type="hidden" value="${content.state}" name="stateG" id="stateG">
							<c:forEach items="${groupsState }" var="st">
								<c:if test="${content.state == st.key}">
									<c:if test="${st.key == 1}">
										<span class="c-red">
									</c:if>
									${st.value }
									<c:if test="${st.key == 1}">
										</span>
									</c:if>
								</c:if>
							</c:forEach>
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
		queryUrl  : "<%=request.getContextPath()%>/groups/hotGroups",
		deleteUrl : "<%=request.getContextPath()%>/groups/delHotGroups",
		page: {//分页
			totalPage  :  ${pager.totalPage},
			cpage      :  ${pager.cpage},
			pageSize   :  ${pager.pageSize} 
		}
	});
	
	/*移除*/
	function deleteOpr(){
		TableUtils.deleteOpr(function (){
			 location.reload();
		});
	}
	
	/*进入编辑排序页面*/
	 function edit_category(title,url,w,h) {
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
			content:url + '?groupId=' + selectedBox.val()
		});
		layer.full(index);
	}
	
</script>
</body>
</html>
