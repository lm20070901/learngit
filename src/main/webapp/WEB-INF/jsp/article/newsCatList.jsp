<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<title>资讯分类</title>
<style type="text/css">

</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 内容管理 <span class="c-gray en">&gt;</span> 资讯分类 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="categoryForm" action="<%=request.getContextPath()%>/article/newsCategoryList" method="post">
	<div class="text-l">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="add_category('新增资讯分类','<%=request.getContextPath()%>/article/toAddNewsCategoryPage','800','500')" class="btn btn-primary radius">
			<i class="Hui-iconfont">&#xe600;</i> 创建
			</a> 
			<a href="javascript:;" onclick="edit_category('编辑资讯分类','<%=request.getContextPath()%>/article/toEditNewsCategoryPage','800','500')" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe6df;</i> 编辑
			</a> 
			<a href="javascript:;" onclick="delete_article()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a> 
		</span> 
		<span class="r">共有数据：<strong>${fn:length(data)}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="5">资讯列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="150">分类名</th>
				<th width="100">文章数量</th>
				<th width="130">是否可见</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="5">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="cate" items="${data}" varStatus="state">
					<tr class="text-c">
						<td><input type="checkbox" value="${cate.categoryId}" name="categoryId" id="categoryId"></td>
						<td>${cate.categoryId}</td>
						<td>${cate.categoryName}</td>
						<td>
							${(empty cate.count || cate.count < 0) ? 0 : cate.count}
						</td>
						<td>
							<c:if test="${cate.isVisible == 0}">否</c:if>
							<c:if test="${cate.isVisible == 1}"><span class="c-red">是</span></c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<input type="submit" id="search" style="display: none;">
		
		</div>
	</form>
</div>

<script type="text/javascript">
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*资讯-增加*/
function add_category(title,url,w,h){
	var index = layer.open({
		type: 2,
		title:title,
		maxmin:true,
		area:[w + 'px', h + 'px'],
		content:url
	});
	
	//layer.full(index);
}


var WEB_PATH = "<%=basePath%>";

/*删除资讯*/
 function delete_article() {
	var selectedBox = $("input[name='categoryId']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	layer.confirm("确认要删除吗？",function(index){
		$.ajax({
			type:"post",
			url:WEB_PATH + "/article/deleteNewsCategory",
			dataType:"json",
			data:$("#categoryForm").serialize(),
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

/*编辑资讯分类*/
 function edit_category(title,url,w,h) {
	var selectedBox = $("input[name='categoryId']:checked");
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
		content:url + '?categoryId=' + selectedBox.val()
	});
	//layer.full(index);
	
}
</script>
</body>
</html>
