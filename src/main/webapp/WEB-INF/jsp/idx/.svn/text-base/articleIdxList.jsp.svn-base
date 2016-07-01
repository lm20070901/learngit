<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
<title>全文索引</title>
<style type="text/css">

</style>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span>
	全文索引
	 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="arForm" action="<%=request.getContextPath()%>/article/articleIdxSearch" method="post">
	<div class="text-l">
		类型：
		<div style="display: inline-block;width: 100px">
			<span class="select-box">
				<select class="select" size="1" name="contentType" id="contentType">
			        <option value="0" selected>财经资讯</option>
			        <option value="1" >研究报告</option>
			        <option value="2" >专家观点</option>
			        <option value="3" >讲座视频</option>
		      	</select>
	      	</span>
	     </div>
	      	    &nbsp;&nbsp; &nbsp;&nbsp; 
	           标题：<input type="text" class="input-text" style="width:150px" placeholder="输入标题" id="" name="title" value="${title}"> &nbsp;&nbsp;
	            内容：<input type="text" class="input-text" style="width:200px" placeholder="输入内容" id="" name="content" value="${content}">
	            &nbsp;&nbsp; &nbsp;&nbsp; 
	 	<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	 	<div style="display: inline-block;width: 80px;float: right;margin-right: 20px;">
			<button type="button" onclick="createIdx()" class="btn btn-success" id="idx" name=""><i class="Hui-iconfont">&#xe665;</i> 创建索引</button>
		</div>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="detail_article('查看详情','<%=request.getContextPath() %>/article/toDetailArticlePage','800','500')" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe603;</i> 查看详情
				</a> 
		</span> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="12">资讯列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"></th>
				<th width="40">ID</th>
				<th width="240">标题</th>
				<th width="60">来源</th>
				<th width="65">作者</th>
				<th width="100">状态</th>
				<th width="100">发布时间</th>
				<th width="100">更新时间</th>
				<th width="50">阅读量</th>
				<th width="50">评论数</th>
				<th width="50">是否可见</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data) eq 0}">
				<tr class="text-c">
					<td colspan="12">暂无相关记录</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(data) gt 0}">
				<c:forEach var="content" items="${data}" varStatus="state">
					<tr class="text-c">
						<td><input type="checkbox" value="${content.contentId}" name="selectBox" id="contentId"></td>
						<td>${content.contentId}</td>
						<td class="text-l">${content.title}</td>
						
						<td>${content.source}</td>
						
						<td>
							<c:if test="${empty content.userName}">
								管理员
							</c:if>
							${content.userName }
						</td>
						<td flag="${content.state }">
							<c:forEach items="${articleState }" var="st">
								<c:if test="${content.state == st.key}">
									<c:if test="${st.key == 1}">
										<span class="c-red">
									</c:if>
									${st.value }
									<c:if test="${st.key == 1}">
										</span>
									</c:if>
								</c:if>
							</c:forEach>
						</td>
						
						<c:if test="${not empty content.publishTime}">
							<jsp:useBean id="publishTime" class="java.util.Date"/> 
							<c:set target="${publishTime}" property="time" value="${content.publishTime}"/> 
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${publishTime}" type="both"/> </td>
						</c:if>
						<c:if test="${empty content.publishTime}">
							<td></td>
						</c:if>
						<c:if test="${not empty content.updateTime}">
							<jsp:useBean id="updateTime" class="java.util.Date"/> 
							<c:set target="${updateTime}" property="time" value="${content.updateTime}"/> 
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${updateTime}" type="both"/> </td>
						</c:if>
						<c:if test="${empty content.updateTime}">
							<td></td>
						</c:if>
						
						<td>${(empty content.readTimes || content.readTimes < 0) ? 0 : content.readTimes}</td>
						<td>${(empty content.commentTimes || content.commentTimes < 0) ? 0 : content.commentTimes}</td>
						<td>
							<c:if test="${content.showStatus == 0 }"><span class="c-red">是</span></c:if>
							<c:if test="${content.showStatus == 1 }">否</c:if>
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
	queryUrl  : "<%=request.getContextPath()%>/article/articleIdxSearch",
	page: {//分页
		totalPage  :  ${pager.totalPage},
		cpage      :  ${pager.cpage},
		pageSize   :  ${pager.pageSize} 
	}
});  


/*索引 查看详情*/
function detail_article(title,url,w,h) {
	var selectedBox = $("input[name='selectBox']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	if(selectedBox.length > 1) {
		layer.msg("一次只能查看一条记录！",{icon:1,time:2000});
		return;
	}
	var index = layer.open({
		type:2,
		title:title,
		area:[w + 'px', h + 'px'],
		maxmin:true,
		content:url + '?id=' + selectedBox.val()
	});
	layer.full(index);
}
/* 创建索引*/
function createIdx(){
	var contentType = $("#contentType").val();
	var text = $("#contentType").find("option:selected").text();
	layer.confirm("确认要为"+text+"建立索引吗？",function(index){
		$.ajax({
			type:"post",
			url:"<%=request.getContextPath()%>/article/articleCreateIdx",
			dataType:"json",
			data:{'contentType':contentType},
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

</script>
</body>
</html>
