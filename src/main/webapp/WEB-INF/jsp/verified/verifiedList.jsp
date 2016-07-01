<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
<title>认证列表</title>
<style type="../text/css">
</style>
</head>

<body>

	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>认证管理<span class="c-gray en">&gt;</span> 认证列表 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
<div class="pd-20">
	<div class="text-l">
		<form action="<%=request.getContextPath()%>/verified/verifiedList" method="post">
			类型:
			<span class="select-box"  style="width:100px;"> 
				<select class="select" id="verifyType" name="verifyType">
						<option value="" selected >全部</option>
						<option value="0" <c:if test="${verifyType==0}">selected </c:if>>理财专家</option>
						<option value="1" <c:if test="${verifyType==1}">selected </c:if>>投资人</option>
						<option value="2" <c:if test="${verifyType==2}">selected </c:if>>创业者</option>
				</select>
			</span>
			状态:
			<span class="select-box"  style="width:100px;"> 
				<select class="select" id="status" name="status">
						<option value="" selected >全部</option>
						<option value="1" <c:if test="${status==1}">selected </c:if>>待审核</option>
						<option value="2" <c:if test="${status==2}">selected </c:if>>不通过</option>
						<option value="3" <c:if test="${status==3}">selected </c:if>>已审核</option>
				</select>
			</span>
		 	呢称：
			<input type="text" class="input-text" style="width:250px" placeholder="请输入昵称" id="nickName" name="nickName" value="${nickName}">
			账号：
			<input type="text" class="input-text" style="width:250px" placeholder="请输入账号" id="userName" name="userName" value="${userName}"> 
			
			<button type="submit" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
		</form>
	</div>
	
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			
			<shiro:hasPermission name="society:verified:view">
			<a href="javascript:;" onclick="toVerfiedDetail();" class="btn btn-primary radius">
			<i class="Hui-iconfont"></i>认证详情</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="society:verified:adudit">
			<a href="javascript:;" onclick="toVerfiedAudit();" class="btn  btn-primary  radius">
			<i class="Hui-iconfont"></i>审核</a>
			</shiro:hasPermission>
		</span>	
		<span class="r">共有数据：<strong>${page.totalRow}</strong> 条</span>
	</div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="10">认证列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="40">ID</th>
				<th width="130">呢称</th>
				<th width="90">账号</th>
				<th width="130">认证名称</th>
				<th width="130">认证类型</th>
				<th width="130">电话号码</th>
				<th width="150">身份证号</th>
				<th width="130">申请时间</th>
				<th width="50">状态</th>
				<th width="200">审核意见</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="verified" items="${verifiedList}">
			<tr class="text-c">
				<td><input type="checkbox" value="${verified.id}" name="selectBox"></td>
				<td>${verified.id}</td>
				<td>${verified.nickName}</td>
				<td>${verified.userName}</td>
				<td>${verified.realName}</td>
				<td>
					<c:choose>
						<c:when test="${verified.verifyType==0}">理财专家</c:when>
						<c:when test="${verified.verifyType==1}">投资人</c:when>
						<c:when test="${verified.verifyType==2}">创业者</c:when>
					</c:choose>
				</td>
				<td>${verified.phoneNum}</td>
				<td>${verified.idCard}</td>
				<td>
				      <c:if test="${! empty verified.applyTime}">
				      	<date:date value="${verified.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/> 
				      </c:if>
				</td>
				<td>
					<c:if test="${verified.status==1}">待审核</c:if>
					<c:if test="${verified.status==2}">被拒绝</c:if>
					<c:if test="${verified.status==3}">审核通过</c:if>
				</td>
				<td>${verified.auditRemark}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>
	<script type="text/javascript">
		//初始化
		TableUtils.init({
			deleteUrl : "<%=request.getContextPath()%>/verified/deleteUserByIds",
			updateUrl : "<%=request.getContextPath()%>/verified/changeUserLockedStatus",
			queryUrl  : "<%=request.getContextPath()%>/verified/verifiedList",
			page: {//分页
				totalPage  :  ${page.totalPage},
				cpage      :  ${page.cpage},
				pageSize   :  ${page.pageSize} 
			}
		});
		
			/*认证详情*/
			function toVerfiedDetail(){
				var url = "<%=request.getContextPath()%>/verified/toVerfiedDetail";
				var id = TableUtils.getSelectedRowsId();
				if(!id) return;
				
				if(id.indexOf(",")!=-1){
					layer.msg("只能选择一行记录!",{icon:1,time:2000});
					return;
				}
				url+='?id='+id;
				var index = layer.open({
					type: 2,
					title: '认证详情',
					content: url
				});
				
				layer.full(index);
				//var userId = id;
				//layer_show(title,url,w,h);
			}
			/**
			*
  			*  去认证审核
			**/
			function toVerfiedAudit(){
				var url = "<%=request.getContextPath()%>/verified/toVerfiedAdudit";
				var id = TableUtils.getSelectedRowsId();
				if(!id) return;
				
				if(id.indexOf(",")!=-1){
					layer.msg("只能选择一行记录!",{icon:1,time:2000});
					return;
				}
				url+='?id='+id;
				var index = layer.open({
					type: 2,
					title: '认证',
					content: url
				});
				
				layer.full(index);
			}
		</script>
</body>
</html>
