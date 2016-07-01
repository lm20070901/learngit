<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
<title>问题分类</title>
<style type="text/css">
</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>
		问答管理 <span class="c-gray en">&gt;</span> 问题分类 <a
			class="btn btn-refresh btn-success radius r mr-20"
			style="line-height:1.6em;margin-top:3px"
			href="javascript:location.replace(location.href);" title="刷新"><i
			class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="pd-20">
		<form id="categoryForm"
			action="<%=request.getContextPath()%>/question/categoryList"
			method="post">
			<div class="text-l">
				<div class="cl pd-5 bg-1 bk-gray mt-20">
					<span class="l"> 
					<a href="javascript:;"
						onclick="addQuestionCategory()"
						class="btn btn-primary radius"> <i class="Hui-iconfont">&#xe600;</i>
							创建
					</a> 
					<a href="javascript:;"
						onclick="editQuestionCategory('编辑问题分类','<%=request.getContextPath()%>/question/toQuestionCategoryEdit','800','500')"
						class="btn btn-primary radius"> <i class="Hui-iconfont">&#xe6df;</i>
							编辑
					</a> 
					<a href="javascript:;" onclick="deleteQuestionCategory()"
						class="btn btn-danger radius"> <i class="Hui-iconfont">&#xe6e2;</i>
							删除
					</a>
					</span> <span class="r">共有数据：<strong>${fn:length(data)}</strong> 条
					</span>
				</div>
				<table class="table table-border table-bordered table-bg table-sort">
					<thead>
						<tr>
							<th scope="col" colspan="6">问题分类列表</th>
						</tr>
						<tr class="text-c">
							<th width="25"></th>
							<th width="40">ID</th>
							<th width="80">分类名</th>
							<th width="50">问题数量</th>
							<th width="260">图标</th>
							<th width="30">是否可见</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(data) eq 0}">
							<tr class="text-c">
								<td colspan="6">暂无相关记录</td>
							</tr>
						</c:if>
						<c:if test="${fn:length(data) gt 0}">
							<c:forEach var="cate" items="${data}" varStatus="state">
								<tr class="text-c">
									<td><input type="checkbox" value="${cate.categoryId}"
										name="selectBox" id="selectBox"></td>
									<td>${cate.categoryId}</td>
									<td>${cate.categoryName}</td>
									<td>
										${(empty cate.questionCount || cate.questionCount < 0) ? 0 : cate.questionCount}
									</td>
									<td class="text-l">${cate.icon }</td>
									<td><c:if test="${cate.isVisible == 1}">否</c:if> <c:if
											test="${cate.isVisible == 0}"><span class="c-red">是</span></c:if></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<input type="submit" id="search" style="display: none;">
				<div id="pagination"></div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		TableUtils.init({
			"deleteUrl" : "<%=request.getContextPath()%>/question/deleteCategoryByIds",
			"updateUrl" : "",
			"queryUrl"  : "<%=request.getContextPath()%>/question/categoryList",
			"page": {//分页
				"totalPage"  :  ${page.totalPage},
				"cpage"      :  ${page.cpage},
				"pageSize"   :  ${page.pageSize} 
			}
		});
		
		//新增问题分类
		function addQuestionCategory() {
			var url = "<%=request.getContextPath()%>/question/toAddQuestionCategoryPage";
			var index = layer.open({
				type: 2,
				title:"新增问题分类",
				maxmin:true,
				area:['800px', '500px'],
				content:url
			});
			
			layer.full(index);
		}
		
		//编辑问题分类
		function editQuestionCategory(title,url,w,h) {
			var id = TableUtils.getSelectedRowsId();
			if(!id) return;
			
			if(id.indexOf(",")!=-1){
				layer.msg("只能选择一行记录!",{icon:1,time:2000});
				return;
			}
			url+='?categoryId='+id;
			var index = layer.open({
				type: 2,
				title: title,
				content: url
			});
			
			layer.full(index);
		}
		
		//批量删除
		function deleteQuestionCategory() {
			TableUtils.deleteOpr(function (){
				$('#search').trigger('click');
			});
		}
	</script>
</body>
</html>
