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
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>用户管理<span class="c-gray en">&gt;</span> 用户列表 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
<div class="pd-20">
	<div class="text-l">
		<form action="<%=request.getContextPath()%>/user/getUserListByMap" method="post">
		 	呢称：
			<input type="text" class="input-text" style="width:250px" placeholder="请输入昵称" id="nickName" name="nickName" value="${nickName}">
			账号：
			<input type="text" class="input-text" style="width:250px" placeholder="请输入账号" id="userName" name="userName" value="${userName}"> 
			是否认证:
			<!-- <span class="select-box inline" >  -->
			<span class="select-box"  style="width:150px;"> 
				<select class="select" id="userAuth" name="userAuth">
						<option value="" selected>全部</option>
						<option value="1" <c:if test="${userAuth==1}">selected </c:if>>是</option>
						<option value="0" <c:if test="${userAuth==0}">selected </c:if>>否</option>
				</select>
			</span>
				<button type="submit" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
		</form>
	</div>
	
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<shiro:hasPermission name="society:user:toEdit">
				<a href="javascript:;" onclick="user_edit('用户编辑','<%=request.getContextPath()%>/user/toUserEdit','800','500');" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe647;</i>编辑</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="society:user:change">
					<a href="javascript:;" onclick="user_locked()" class="btn btn-warning radius"><i class="Hui-iconfont"></i>锁定/解锁</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="society:user:reset">
				<a href="javascript:;" onclick="password_reset()" class="btn  btn-warning  radius"><i class="Hui-iconfont"></i>密码重置</a>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="society:user:delete">
			<a href="javascript:;" onclick="delete_user()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除</a>  
			</shiro:hasPermission>
			</span>
			
		<span class="r">共有数据：<strong>${page.totalRow}</strong> 条</span>
	</div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="10">用户列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="40">ID</th>
				<th width="130">呢称</th>
				<th width="90">账号</th>
				<th width="50">是否认证</th>
				<th width="130">注册时间</th>
				<th width="100">注册IP</th>
				<th width="130">最后登录时间</th>
				<th width="50">登录次数</th>
				<th width="50">状态</th>
				<!-- <th width="100">操作</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${userList}">
			<tr class="text-c">
				<td><input type="checkbox" value="${user.userId}" name="selectBox"></td>
				<td>${user.userId}</td>
				<td>${user.nickName}</td>
				<td>${user.userName}</td>
				<td><c:if test="${user.userAuth==1}">是</c:if>
					<c:if test="${user.userAuth==0}">否</c:if></td>
				<td>
				<%-- 	<c:if test="${user.signUpDate!=0}"> --%>
						<date:date value="${user.signUpDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					<%-- </c:if> --%>
				</td>
				<td>${user.regIp}</td>
				<td>
				<%-- 	<c:if test="${user.lastLogin!=0}"> --%>
						<date:date value="${user.lastLogin}" pattern="yyyy-MM-dd HH:mm:ss"/>
					<%-- </c:if> --%>
				</td>
				<td>${user.loginTimes}</td>
				<td><c:if test="${user.userLock==1}">锁定</c:if>
					<c:if test="${user.userLock==0}">正常</c:if>
				</td>
				<%-- <td class="td-manage">
				<a title="编辑" href="javascript:;" onclick="user_edit('用户编辑','<%=request.getContextPath()%>/user/toUserEdit',${user.userId},'800','500')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>&nbsp;
			
				<a title="删除" href="javascript:;" onclick="user_del(this,${user.userId})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
				</td>
				 --%>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>
	<script type="text/javascript">
		//初始化
		TableUtils.init({
			deleteUrl : "<%=request.getContextPath()%>/user/deleteUserByIds",
			updateUrl : "<%=request.getContextPath()%>/user/changeUserLockedStatus",
			queryUrl  : "<%=request.getContextPath()%>/user/getUserListByMap",
			page: {//分页
				totalPage  :  ${page.totalPage},
				cpage      :  ${page.cpage},
				pageSize   :  ${page.pageSize} 
			}
		});
		
			/*用户-编辑*/
			function user_edit(title,url,w,h){
				var id = TableUtils.getSelectedRowsId();
				if(!id) return;
				
				if(id.indexOf(",")!=-1){
					layer.msg("只能选择一行记录!",{icon:1,time:2000});
					return;
				}
				url+='?userId='+id;
				var index = layer.open({
					type: 2,
					title: title,
					content: url
				});
				
				layer.full(index);
				//var userId = id;
				//layer_show(title,url,w,h);
			}
			/* 锁定/解锁用户 */
			function user_locked(){
				
				TableUtils.updateUrl = "<%=request.getContextPath()%>/user/changeUserLockedStatus";
				TableUtils.updateMsg = "确认要改变用户锁定状态吗？";
				TableUtils.updateOpr(function (){
					var selectTrs = TableUtils.selectTrs;
					
					for ( var i = 0; i < selectTrs.length; i++) {
						var text = $(selectTrs[i]).children('td:nth-child(10)').text();
						if('正常' == text.trim()){
							text = '锁定';
						}else if('锁定' == text.trim()){
							text = '正常';
						}
						$(selectTrs[i]).children('td:nth-child(10)').text(text);//改变某个列的值 
					}
					
				});
				
			}
			
			
			//密码重置
			function password_reset(){
				TableUtils.updateUrl = "<%=request.getContextPath()%>/user/resetPassword";
				TableUtils.updateMsg = "确认要重置密码吗？";
				TableUtils.updateOpr(function (){ });
			}
			
			 /* 删除用户 */
			function delete_user(){
				TableUtils.deleteOpr(function (){
					 location.reload();
				});
			} 
		</script>
</body>
</html>
