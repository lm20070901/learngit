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
<title>小组管理</title>
<style type="text/css">

</style>
</head>
<body>

<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 小组管理 <span class="c-gray en">&gt;</span> 小组列表	
	<a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="arForm" action="<%=request.getContextPath()%>/groups/groupsList" method="post">
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
			<shiro:hasPermission name="groups:group:toEdit"> 
				<a href="javascript:;" onclick="edit_category('小组编辑','<%=request.getContextPath()%>/groups/toEditPage','500','300')" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe6df;</i> 编辑
				</a> 
			</shiro:hasPermission>
				<shiro:hasPermission name="groups:group:toDetail"> 
				<a href="javascript:;" onclick="detail_category('小组详情','<%=request.getContextPath()%>/groups/detail','800','500')" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe695;</i> 查看详情
				</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="groups:group:toEdit"> 
				<a href="javascript:;" onclick="forbidOpr()" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe6e0;</i> 禁用/解禁
				</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="groups:group:toAudit">
				<a href="javascript:;" onclick="toAuditPage('小组审核','<%=request.getContextPath()%>/groups/toAuditPage','800','500')" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe603;</i> 审核
				</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="groups:group:getUsers">
				<a href="javascript:;" onclick="user_category('小组成员','<%=request.getContextPath()%>/groups/selectUsers','800','500')" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe695;</i> 小组成员
				</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="groups:group:delete">
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
				<th width="150">名称</th>
				<th width="130">创建人</th>
				<th width="200">创建时间</th>
				<th width="50">成员数</th>
				<th width="50">帖子数</th>
				<th width="80">状态</th>
				<th width="80">是否禁用</th>
				<th with="100">审核建议</th>
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
						<td><input type="hidden" value="${content.state}" name="stateG" id="stateG">
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
						<td flag="${content.forbid }">
							<c:if test="${content.forbid == 1}">启用
							</c:if>
							<c:if test="${content.forbid == 0}">禁用
							</c:if>
						</td>
						<td style="text-align:left;">${content.suggestion}</td>
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
		queryUrl  : "<%=request.getContextPath()%>/groups/groupsList",
		page: {//分页
			totalPage  :  ${pager.totalPage},
			cpage      :  ${pager.cpage},
			pageSize   :  ${pager.pageSize} 
		}
	});
	
	/*删除*/
	function deleteOpr(){
		var selectedBox = $("input[name='selectBox']:checked");
		if(selectedBox.length == 0) {
			layer.msg("请选择后操作",{icon:1,time:2000});
			return;
		}
		layer.confirm("确认要删除吗？",function(index){
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath()%>/groups/deleteGroups",
				dataType:"json",
				data:$("#arForm").serialize(),
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
	/*禁用/解禁*/
	function forbidOpr(){//禁用/启用
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
		layer.confirm("确认要禁用/解禁吗？",function(index){
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath()%>/groups/forbid",
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
	
	
	/*审核*/
	 function toAuditPage(title,url,w,h) {
		var selectedBox = $("input[name='selectBox']:checked");
		if(selectedBox.length == 0) {
			layer.msg("请选择后操作",{icon:1,time:2000});
			return;
		}
		if(selectedBox.length > 1) {
			layer.msg("一次只能审核一条记录！",{icon:1,time:2000});
			return;
		}
		var st = 0;
		var state = selectedBox.parents("tr").find("input[name='stateG']").val();
		if(state == '1'){
			st = 1;
		}
		if(st == 1){
			layer.msg("该记录已审核！",{icon:1,time:2000});
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
	/*进入编辑页面*/
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
		//layer.full(index);
		
	}
	
	 /*进入小组成员*/
	 function user_category(title,url,w,h) {
		var selectedBox = $("input[name='selectBox']:checked");
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
	 
	 /*进入详情页面*/
	 function detail_category(title,url,w,h) {
		var selectedBox = $("input[name='selectBox']:checked");
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


	
</script>
</body>
</html>
