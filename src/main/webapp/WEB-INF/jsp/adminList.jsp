<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="head.jsp"%>
<title>管理员列表</title>
<style type="text/css">

</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <span class="c-gray en">&gt;</span> 管理员列表 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="text-l">
	<form action="<%=request.getContextPath()%>/system/adminList" method="post">
	 	登录日期：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">
		-
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">
		<input type="text" class="input-text" style="width:250px" placeholder="输入登录名" id="" name="userName" value="${userName}">
		<button type="submit" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="deleteAll()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="admin_add('添加管理员','<%=request.getContextPath()%>/system/toAddAdmin','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加管理员</a></span> <span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="9">管理员列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="40">ID</th>
				<th width="150">登录名</th>
				<th width="90">角色</th>
				<th width="150">最后登录时间</th>
				<th width="130">最后登录IP</th>
				<th width="100">是否启用</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="admin" items="${adminList}">
			<tr class="text-c">
				<td><input type="checkbox" value="${admin.userId}" name="selectBox"></td>
				<td>${admin.userId}</td>
				<td>${admin.userName}</td>
				<td>${admin.roleName}</td>
				<td>${admin.lastLoginTime}</td>
				<td>${admin.lastLoginIP}</td>
				<td>${admin.isUsed}</td>
				<td class="td-manage">
				<a title="编辑" href="javascript:;" onclick="admin_edit('管理员编辑','admin-add.html','1','800','500')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>&nbsp;
				<c:if test="${admin.isSystem==1}">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
				<c:if test="${admin.isSystem==0}">
				<a title="删除" href="javascript:;" onclick="admin_del(this,${admin.userId})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
				</c:if>
				
			</tr>
			</c:forEach>
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
        	window.location.href="<%=request.getContextPath()%>/system/adminList?page="+num+"&size=${pager.pageSize}";
    }
});
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*管理员-增加*/
function admin_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*管理员-删除*/
function deleteAll(obj,id){
	var selectBox = $("input[name='selectBox']");
	var selectTrs = new Array();
	var ids = "";
	var n = 0;
	for(var i=0;i<selectBox.length;i++){
		if (selectBox[i].checked) {
			ids += selectBox[i].value+",";
			selectTrs[n] = $(selectBox[i]).parents().parents();
			n++;
	    }
		
	}
	if(ids.length==0){
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}else{
		ids = ids.substring(0,ids.length-1);
	}
	
	layer.confirm('确认要删除吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		var index = layer.load(1, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
		$.ajax({
	            type:"get",
	            url:"<%=request.getContextPath()%>/system/deleteAdmin",
	            dataType:'json',
	            data: {userId:ids},
	            success:function(json){
	            	layer.close(index);
	            	//var json = eval('('+json+')');
	            	if(json.result==1){
	            		layer.msg(json.msg,{icon:1,time:2000});
	            		for(var i=0;i<selectTrs.length;i++){
	            			selectTrs.remove();
	            		}
	            	}else{
	            		layer.msg(json.msg,{icon:1,time:2000});
	            	}
	            	
	            },
	            error:function(e){ 
	            	layer.close(index);
	            	layer.msg("系统或者网络出错",{icon:1,time:2000});
	            } 
	        });
	});
}

/*管理员-删除所选*/
function admin_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		var index = layer.load(1, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
		$.ajax({
	            type:"get",
	            url:"<%=request.getContextPath()%>/system/deleteAdmin",
	            dataType:'json',
	            data: {userId:id},
	            success:function(json){
	            	layer.close(index);
	            	//var json = eval('('+json+')');
	            	if(json.result==1){
	            		layer.msg(json.msg,{icon:1,time:2000});
	            		$(obj).parents("tr").remove();
	            	}else{
	            		layer.msg(json.msg,{icon:1,time:2000});
	            	}
	            	
	            },
	            error:function(e){ 
	            	layer.close(index);
	            	layer.msg("系统或者网络出错",{icon:1,time:2000});
	            } 
	        });
	});
}

/*管理员-编辑*/
function admin_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*管理员-停用*/
function admin_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已禁用</span>');
		$(obj).remove();
		layer.msg('已停用!',{icon: 5,time:1000});
	});
}

/*管理员-启用*/
function admin_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		
		
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
		$(obj).remove();
		layer.msg('已启用!', {icon: 6,time:1000});
	});
}
</script>
</body>
</html>
