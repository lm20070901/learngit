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
<title>IP管理</title>
<style type="text/css">

</style>
</head>
<body>

<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>IP管理 <span class="c-gray en">&gt;</span> IP列表	
	<a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="arForm" action="<%=request.getContextPath()%>/ipAdr/selectPageList" method="post">
	<div class="text-l">
		IP地址：<input type="text" class="input-text" style="width:150px" placeholder="输入IP地址" id="ipAddress" name="ipAddress" value="${ipAddress}"> 
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
		    <shiro:hasPermission name="ipAdr:adress:toAdd">
				<a href="javascript:;" onclick="add_category('新增','<%=request.getContextPath()%>/ipAdr/toaddPage','800','500')" class="btn btn-primary radius" >
					<i class="Hui-iconfont">&#xe603;</i> 新增
				</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="ipAdr:adress:edit">
				<a href="javascript:;" onclick="forbidOpr()" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe6e0;</i> 禁用/解禁
				</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="ipAdr:adress:delete">
				<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
					<i class="Hui-iconfont">&#xe6e2;</i> 删除
				</a> 
			</shiro:hasPermission>
		</span> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="25"></th>
				<th width="80">ID</th>
				<th width="200">IP地址</th>
				<th width="130">状态</th>
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
						<td>${content.ipAddress}</td>
						<td  flag="${content.state }">
							<c:if test="${content.state == 1}">禁用
							</c:if>
							<c:if test="${content.state == 0}">启用
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
		queryUrl  : "<%=request.getContextPath()%>/ipAdr/selectPageList",
		deleteUrl : "<%=request.getContextPath()%>/ipAdr/batchDelete",
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
				url:"<%=request.getContextPath()%>/ipAdr/updateState",
				dataType:"json",
				data:{'idList':idArr,'state':value},
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
	
	function add_category(title,url,w,h){
		var index = layer.open({
			type:2,
			title:title,
			area:[w + 'px', h + 'px'],
			maxmin:true,
			content:url
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
