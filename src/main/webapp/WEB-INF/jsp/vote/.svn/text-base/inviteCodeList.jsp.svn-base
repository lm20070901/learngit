<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/static/js/table/common.js"></script>

<title>邀请码管理</title>
<style type="../text/css">
</style>
</head>

<body>

	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>问卷管理<span
			class="c-gray en">&gt;</span> 邀请码管理 <a
			class="btn btn-refresh btn-success radius r mr-20"
			style="line-height:1.6em;margin-top:3px"
			href="javascript:location.replace(location.href);" title="刷新"><i
			class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="pd-20">
		<div class="text-l">
			<form action="<%=request.getContextPath()%>/vote/toInviteCodeList"  method="post">
			   <input type="hidden" name="voteId" value="${voteId}"/>
				 邀请码：
				<input type="text" class="input-text" style="width:130px" placeholder="请输入邀请码" id="inviteCode" name="inviteCode" value="${inviteCode}"/> 
			　　
			　　　使用者账号：
				<input type="text" class="input-text" style="width:130px" placeholder="请输入账号" id="userName" name="userName" value="${userName}"/> 
			　　　是否使用:
				<span class="select-box" style="width:80px;"> 
				<select class="select" id="isUsed" name="isUsed">
						<option value="" selected>全部</option>
						<option value="1" <c:if test="${isUsed==1}">selected </c:if>>是</option>
						<option value="0" <c:if test="${isUsed==0}">selected </c:if>>否</option>
				</select>
				</span>
				开始时间：
				<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
				结束时间：
				<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		
				<button type="submit" class="btn btn-success" id="" name="">
					<i class="Hui-iconfont">&#xe665;</i> 查询
				</button>
			</form>
		</div>

		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l">
			<!--  <a href="javascript:;" onclick="addOpr();" class="btn btn-primary radius"> 
				<i class="Hui-iconfont">&#xe600;</i>新建
			 </a>  -->
			<a href="javascript:;" onclick="editOpr();" class="btn btn-primary radius"> 
				<i class="Hui-iconfont">&#xe647;</i>编辑
			</a> 
			<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius"> 
				<i class="Hui-iconfont">&#xe6e2;</i>删除
			</a>

			</span> <span class="r">共有数据：<strong>${pager.totalRow}</strong> 条
			</span>
		</div>
		<table class="table table-border table-bordered table-bg table-sort"
			style="table-layout:fixed;">
			<thead>
				<tr>
					<th scope="col" colspan="6">邀请码列表</th>
				</tr>
				<tr class="text-c">
					<th width="5%"><input type="checkbox" name="" value=""></th>
					<th width="10%">ID</th>
					<th width="25%">邀请码</th>
					<th width="20%">有效期</th>
					<th width="15%">是否被使用</th>
					<th width="25%">使用者帐号</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="code" items="${codeList}">
					<tr class="text-c">
						<td>
						<input type="checkbox" value="${code.id}" name="selectBox"></td>
						<td>${code.id}</td>
						<td >${code.inviteCode}</td>
						<td>
						<date:date value="${code.timeLine}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							 <c:if test="${code.isUsed}">是</c:if> 
							<c:if test="${!code.isUsed}">否</c:if> 
								
						</td>
						<td>${code.userName}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="pagination"></div>
	</div>
	<script type="text/javascript">
		//初始化
		TableUtils.init({
			deleteUrl : "<%=request.getContextPath()%>/vote/deleteInviteCodeByIds",
			
			queryUrl  : "<%=request.getContextPath()%>/vote/toInviteCodeList",
			page: {//分页
				totalPage  :  ${pager.totalPage},
				cpage      :  ${pager.cpage},
				pageSize   :  ${pager.pageSize} 
			}
		});
		
			/*新增问卷调查*/
			<%-- function addOpr(){
				var index = layer.open({
					type: 2,
					title: "创建问卷",
					content: "<%=request.getContextPath()%>/vote/toEditVote?voteType=${voteType}"
				});
				
				layer.full(index);
			} --%>
			
			/**编辑问卷调查*/
			function editOpr(){
				var selectTrs  = TableUtils.getSelectedRows();
				
				if (!selectTrs)  return;
				
				if(selectTrs.length != 1){
					layer.msg("只能选择一行记录!",{icon:1,time:2000});
					return;
				}
				
				var text = $(selectTrs[0]).children('td').eq(-2).text();
				if('否' != text.trim()){
						layer.msg("邀请码已被使用!",{icon:1,time:2000});
						return ;
				}
				
				layer_show("编辑邀请码","<%=request.getContextPath()%>/vote/toEditInviteCode?id="+TableUtils.ids,"800","500");
				
			}
			
			
			 /* 删除问卷*/
			function deleteOpr(){
				TableUtils.deleteOpr(function (){
					 location.reload();
				});
			} 
			 
			 
			
			 
			
		</script>
</body>
</html>
