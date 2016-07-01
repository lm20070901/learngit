<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
<title>用户列表</title>
<style type="../text/css">
</style>
</head>

<body>

	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>角色管理<span class="c-gray en">&gt;</span> 角色列表 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
<div class="pd-20">
	<div class="text-l">
		<form action="<%=request.getContextPath()%>/role/toRoleList" method="post">
		 	ID：
			<input type="text" class="input-text" style="width:250px" placeholder="请输入ID" id="roleId" name="roleId" value="${roleId}">
			角色名：
			<input type="text" class="input-text" style="width:250px" placeholder="请输入角色名" id="roleName" name="roleName" value="${roleName}"> 
			<button type="submit" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
		</form>
	</div>
	
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
		<shiro:hasPermission name="system:role:toAdd">
			<a href="javascript:;" onclick="role_add();" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe600;</i> 新建
			</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:role:delete">
			<a href="javascript:;" onclick="delete_role();" class="btn btn-danger radius">
					<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a>  
		</shiro:hasPermission>
		<shiro:hasPermission name="system:role:toActionList">
			<a href="javascript:;" onclick="distrbute_action();" class="btn  btn-warning  radius">
					<i class="Hui-iconfont"></i>分配权限
			</a>
		</shiro:hasPermission>
			</span>
			
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span>
	</div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="10">用户列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="40">ID</th>
				<th width="130">角色名</th>
				<th width="50">备注</th>
				<!-- <th width="100">操作</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="role" items="${roleList}">
			<tr class="text-c">
				<td><input type="checkbox" value="${role.roleId}" name="selectBox"></td>
				<td>${role.roleId}</td>
				<td>${role.roleName}</td>
				<td>${role.mark}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>
	<script type="text/javascript">
		//初始化
		TableUtils.init({
			deleteUrl : "<%=request.getContextPath()%>/role/deleteRoleByIds",
			<%-- updateUrl : "<%=request.getContextPath()%>/role/changeUserLockedStatus", --%>
			queryUrl  : "<%=request.getContextPath()%>/role/toRoleList",
			page: {//分页
				totalPage  :  ${pager.totalPage},
				cpage      :  ${pager.cpage},
				pageSize   :  ${pager.pageSize} 
			}
		});
		
			/*角色新增*/
			function role_add(){
				var url = "<%=request.getContextPath()%>/role/toRoleAdd";
				
				layer_show('新建角色',url,'800','500');
			}
			
			
			
			 /* 删除角色 */
			function delete_role(){
				TableUtils.deleteOpr(function (){
					 location.reload();
				});
			} 
			 
			 /*分配权限*/
			function distrbute_action(){
				var url = "<%=request.getContextPath()%>/role/toActionList";
				var id = TableUtils.getSelectedRowsId();
				if(!id) return;
				
				if(id.indexOf(",")!=-1){
					layer.msg("只能选择一行记录!",{icon:1,time:2000});
					return;
				}
				url+='?roleId='+id;
				var index = layer.open({
					type: 2,
					title: '分配权限',
					content: url
				});
				
				layer.full(index);
			}
		</script>
</body>
</html>
