<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
<title>积分规则</title>
<style type="text/css">
</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>
		系统管理 <span class="c-gray en">&gt;</span> 积分规则 <a
			class="btn btn-refresh btn-success radius r mr-20"
			style="line-height:1.6em;margin-top:3px"
			href="javascript:location.replace(location.href);" title="刷新"><i
			class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="pd-20">
		<form id="pointRuleForm"
			action="<%=request.getContextPath()%>/point/pointRuleList"
			method="post">
			<div class="text-l">
				<div class="cl pd-5 bg-1 bk-gray mt-20">
					<span class="l"> 
						<a href="javascript:;"
							onclick="addPointRule()"
							class="btn btn-primary radius"> <i class="Hui-iconfont">&#xe600;</i>
								创建
						</a> 
						<a href="javascript:;"
							onclick="editPointRule('编辑积分规则','<%=request.getContextPath()%>/point/toPointRuleEdit','800','500')"
							class="btn btn-primary radius"> <i class="Hui-iconfont">&#xe6df;</i>
								编辑
						 </a>
						 <a href="javascript:;" onclick="deletePointRule()"
							class="btn btn-danger radius"> <i class="Hui-iconfont">&#xe6e2;</i>
								删除
						</a>
						</span> <span class="r">共有数据：<strong>${fn:length(data)}</strong> 条
					</span>
				</div>
				<table class="table table-border table-bordered table-bg table-sort">
					<thead>
						<tr>
							<th scope="col" colspan="6">积分规则列表</th>
						</tr>
						<tr class="text-c">
							<th width="25"></th>
							<th width="30">ID</th>
							<th width="150">动作名称</th>
							<th width="60">奖励周期</th>
							<th width="30">奖励次数</th>
							<th width="30">获得积分</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(data) eq 0}">
							<tr class="text-c">
								<td colspan="6">暂无相关记录</td>
							</tr>
						</c:if>
						<c:if test="${fn:length(data) gt 0}">
							<c:forEach var="rule" items="${data}" varStatus="state">
								<tr class="text-c">
									<td><input type="checkbox" value="${rule.ruleId}"
										name="selectBox" id="selectBox"></td>
									<td>${rule.ruleId}</td>
									<td>${rule.ruleName}</td>
									<td>
										<c:forEach items="${periods }" var="per">
											<c:if test="${per.key == rule.period }">
												${per.value }
											</c:if>
										</c:forEach>
									</td>
									<td>${(empty rule.times || rule.times < 0) ? 0 :  rule.times}</td>
									<td>${rule.point}</td>
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
			"deleteUrl" : "<%=request.getContextPath()%>/point/deletePointRule",
			"updateUrl" : "",
			"queryUrl"  : "<%=request.getContextPath()%>/point/pointRuleList",
			"page": {//分页
				"totalPage"  :  ${page.totalPage},
				"cpage"      :  ${page.cpage},
				"pageSize"   :  ${page.pageSize} 
			}
		});
		
		//新增积分规则
		function addPointRule() {
			var url = "<%=request.getContextPath()%>/point/toPointRuleAdd";
			var index = layer.open({
				type: 2,
				title:"新增积分规则",
				maxmin:true,
				area:['800px', '500px'],
				content:url
			});
			
			layer.full(index);
		}
		
		//编辑问题分类
		function editPointRule(title,url,w,h) {
			var id = TableUtils.getSelectedRowsId();
			if(!id) return;
			
			if(id.indexOf(",")!=-1){
				layer.msg("只能选择一行记录!",{icon:1,time:2000});
				return;
			}
			url+='?id='+id;
			var index = layer.open({
				type: 2,
				title: title,
				content: url
			});
			
			layer.full(index);
		}
		
		//批量删除
		function deletePointRule() {
			TableUtils.deleteOpr(function (){
				$('#search').trigger('click');
			});
		} 
	</script>
</body>
</html>
