<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
<title>动态列表</title>
<style type="text/css"></style>
	
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 动态管理 <span class="c-gray en">&gt;</span> 动态列表 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="text-l">
	<form action="<%=request.getContextPath()%>/content/contentList" method="post">
		内容：<input type="text" class="input-text" style="width:150px" placeholder="输入检索内容" id="" name="content" value="${content}"> &nbsp;&nbsp;
		用户ID：<input type="text" class="input-text" style="width:80px" placeholder="输入检索内容" id="" name="userId" value="${userId}"> &nbsp;&nbsp;
		用户昵称：<input type="text" class="input-text" style="width:100px" placeholder="输入检索内容" id="" name="nickName" value="${nickName}"> &nbsp;&nbsp;
	 	开始时间：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		结束时间：
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<shiro:hasPermission name="society:content:delete">
			<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="society:content:hidden">
			<a href="javascript:;" onclick="hiddenOpr();" class="btn btn-warning radius">
				<i class="Hui-iconfont"></i> 隐藏/显示
			</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="society:comment:list">
			<a href="javascript:;" onclick="viewOpr();" class="btn btn-primary radius">
				<i class="Hui-iconfont"></i> 查看评论
			</a>
			</shiro:hasPermission>
		</span> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="9">动态列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="40">ID</th>
				<th width="100">内容</th>
				<th width="50">来源</th>
				<th width="30">状态</th>
				<th width="100">发布人昵称</th>
				<th width="100">发布人账号</th>
				<th width="130">发布时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="content" items="${data}">
			<tr class="text-c">
				<td><input type="checkbox" value="${content.contentId}" name="selectBox"></td>
				<td>${content.contentId}</td>
				<td class="text-overflow text-l" title="${content.contentBody}" >${content.contentBody}</td>
				<td>${content.type}</td>
				<td>
					<c:if test="${content.showStatus==0}">显示</c:if>
					<c:if test="${content.showStatus==1}">隐藏</c:if>
				</td>
				<td>${content.nickName}</td>
				<td>${content.userName}</td>
				<td>
				<%-- <c:if test="${content.postTime!=null}">  --%>
					<date:date value="${content.postTime==null?0:content.postTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>

<script type="text/javascript">
			
			//初始化
			TableUtils.init({
				deleteUrl : "<%=request.getContextPath()%>/content/deleteContent",
				updateUrl : "<%=request.getContextPath()%>/content/hiddenContents",
				queryUrl  : "<%=request.getContextPath()%>/content/contentList",
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
			
			/*隐藏操作*/
 			/* 要选取jQuery每一个父元素下的某个子元素使用:nth-child(index)做选择器修饰，index从1开始，表示选取以前面选择器过滤出来的每一个父元素下的第几个子元素 */
			function hiddenOpr(){
				var selectTrs  = TableUtils.getSelectedRows();
				if (!selectTrs)  return;
				
				TableUtils.updateOpr(function (){
					
					for ( var i = 0; i < selectTrs.length; i++) {
						var text = $(selectTrs[i]).children('td').eq(4).text();
						if('隐藏' == text.trim()){
							text = "显示";
						}else{
							text = "隐藏";
						}
						$(selectTrs[i]).children('td').eq(4).text(text);//改变某个列的值 
					}
					
				});
			}
			//查看操作
			function viewOpr(){
				var viewUrl = "<%=request.getContextPath()%>/comment/commentList/";
				var id = TableUtils.getSelectedRowsId();
				if(!id) return;
				
				if(id.indexOf(",")!=-1){
					layer.msg("只能选择一行记录!",{icon:1,time:2000});
					return;
				}
				viewUrl+='?contentId='+id;
				var index = layer.open({
					type: 2,
					title: '查看评论',
					content: viewUrl
				});
				layer.full(index);
			}
			
			
</script>
</body>
</html>
