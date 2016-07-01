<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<title>焦点图管理</title>
<style type="text/css">

</style>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 内容管理 <span class="c-gray en">&gt;</span>
	 焦点图管理
	 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="focusPicForm" action="<%=request.getContextPath()%>/focusPicture/focusPictureList" method="post">
	<input type="submit" style="display: none" id="search"/>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="add_focusPic('新增焦点图','<%=request.getContextPath()%>/focusPicture/toCreateFocusPicPage','800','500')" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe600;</i> 新建
			</a> 
			<a href="javascript:;" onclick="edit_focusPic('编辑焦点图','<%=request.getContextPath()%>/focusPicture/toEditFocusPicPage','800','500')" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe6df;</i> 编辑
			</a> 
			<a href="javascript:;" onclick="delete_focusPic()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a> 
		</span> 
		<span class="r">共有数据：<strong>${fn:length(data)}</strong> 条</span> 
	</div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="5">焦点图列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="150">标题</th>
				<th width="90">链接地址</th>
				<th width="100">是否可见</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="5">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="pic" items="${data}" varStatus="state">
					<tr class="text-c">
						<td><input type="checkbox" value="${pic.picId}" name="picId" id="picId"></td>
						<td>${pic.picId}</td>
						<td class="text-l">${pic.title}</td>
						<td class="text-l">${pic.linkDir}</td>
						<td>
							<c:if test="${pic.isVisible == 0 }"><span class="c-red">可见</span></c:if>	
							<c:if test="${pic.isVisible == 1 }">不可见</c:if>						
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	</form>
</div>


<script type="text/javascript">
/*新增焦点图*/
function add_focusPic(title,url,w,h){
	var index = layer.open({
		type: 2,
		title:title,
		maxmin:true,
		area:[w + 'px', h + 'px'],
		content:url
	});
	
	layer.full(index);
}

var WEB_PATH = "<%=basePath%>";


/*删除焦点图*/
 function delete_focusPic() {
	var selectedBox = $("input[name='picId']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	layer.confirm("确认要删除吗？",function(index){
		$.ajax({
			type:"post",
			url:WEB_PATH + "/focusPicture/deleteFocusPicture",
			dataType:"json",
			data:$("#focusPicForm").serialize(),
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

/*编辑焦点图*/
 function edit_focusPic(title,url,w,h) {
	var selectedBox = $("input[name='picId']:checked");
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
		content:url + '?id=' + selectedBox.val()
	});
	layer.full(index);
	
}
</script>
</body>
</html>
