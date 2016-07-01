<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<title>热门资讯</title>
<style type="text/css">

</style>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 内容管理 <span class="c-gray en">&gt;</span>
	 热门资讯
	 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="hotNewsForm" action="<%=request.getContextPath()%>/article/HotNewsList" method="post">
	<div class="text-l">
		标题：<input type="text" class="input-text" style="width:150px" placeholder="输入标题" id="" name="title" value="${title}"> &nbsp;&nbsp;
		
		来源：<input type="text" class="input-text" style="width:100px" placeholder="输入来源" id="" name="origin" value="${origin}"> &nbsp;&nbsp;
	 	
	 	开始时间：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		结束时间：
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="edit_news_order('编辑排序','<%=request.getContextPath()%>/article/toModifyHotNewsOrder','800','500')" class="btn btn-primary radius">
				编辑排序
			</a> 
			<a href="javascript:;" onclick="delete_hotNews()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a> 
		</span> 
		<span class="r">共有数据：<strong>${fn:length(data)}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="11">热门资讯列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="40">热度排序</th>
				<th width="260">标题</th>
				<th width="80">栏目</th>
				<th width="80">来源</th>
				<th width="70">作者</th>
				<th width="100">发布时间</th>
				<th width="100">更新时间</th>
				<th width="50">阅读量</th>
				<th width="50">评论数</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="11">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="article" items="${data}" varStatus="state">
					<tr class="text-c">
						<td><input type="checkbox" value="${article.contentId}" name="contentId" id="contentId"></td>
						<td>${article.contentId}</td>
						<td>${article.order }</td>
						<td class="text-l">${article.title}</td>
						<td>
							<c:choose>
								<c:when test="${article.contentType == 0 }">财经资讯</c:when>
							 	<c:when test="${article.contentType == 1 }">研究报告</c:when>
							 	<c:when test="${article.contentType == 2 }">专家观点</c:when>
							 	<c:when test="${article.contentType == 3 }">讲座视频</c:when>
							</c:choose>
						</td>
						<td>${article.source}</td>
						
						<td>
							<c:if test="${empty article.userName}">
								管理员
							</c:if>
							${article.userName }
						</td>
						
						
						<td><date:date value="${article.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><date:date value="${article.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${(empty article.readTimes || article.readTimes < 0) ? 0 : article.readTimes}</td>
						<td>${(empty article.commentTimes || article.commentTimes < 0) ? 0 : article.commentTimes}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	</form>
</div>


<script type="text/javascript">


var WEB_PATH = "<%=basePath%>";

/*删除热门资讯*/
 function delete_hotNews() {
	var selectedBox = $("input[name='contentId']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	layer.confirm("确认要删除吗？",function(index){
		$.ajax({
			type:"post",
			url:WEB_PATH + "/article/deleteHotNews",
			dataType:"json",
			data:$("#hotNewsForm").serialize(),
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

/*编辑资讯*/
 function edit_news_order(title,url,w,h) {
	var selectedBox = $("input[name='contentId']:checked");
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
		content:url + '?id=' + selectedBox.val()
	});
	//layer.full(index);
	
}
</script>
</body>
</html>
