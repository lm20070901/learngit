<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<title>视频讲座</title>
<style type="text/css">

</style>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 内容管理 <span class="c-gray en">&gt;</span>
	 讲座视频
	 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="arForm" action="<%=request.getContextPath()%>/article/videoArticleList" method="post">
	<div class="text-l">
		标题：<input type="text" class="input-text" style="width:150px" placeholder="输入标题" id="" name="title" value="${title}"> &nbsp;&nbsp;
		
		状态：
		<div style="display: inline-block;width: 80px">
			<span class="select-box">
				<select class="select" size="1" name="state">
			        <option value="" <c:if test="${empty state}">selected</c:if>>全部</option>
			        <c:forEach items="${articleState }" var="st">
			        	<option value="${st.key }" <c:if test="${state == st.key}">selected</c:if>>${st.value }</option>
			        </c:forEach>
		      	</select>
	      	</span>
	     </div>
	      	 &nbsp;&nbsp;
	 	开始时间：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${startDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		结束时间：
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate"  name="endDate" value="${endDate}" class="input-text Wdate" style="width:120px;">&nbsp;&nbsp;
		<button type="submit" class="btn btn-success" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="add_article('新增资讯','<%=request.getContextPath()%>/article/toCreateVideoArticlePage','800','500')" class="btn btn-primary radius">
			<i class="Hui-iconfont">&#xe600;</i> 创建
			</a> 
			<a href="javascript:;" onclick="edit_article('编辑资讯','<%=request.getContextPath()%>/article/toEditVideoArticlePage','800','500')" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe6df;</i> 编辑
			</a> 
			<a href="javascript:;" onclick="publishArticle()" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe603;</i> 审核发布
			</a> 
			<a href="javascript:;" onclick="modifyVisibility()" class="btn btn-warning radius">
				 隐藏/显示
			</a> 
			<a href="javascript:;" onclick="delete_article()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a> 
			<a href="javascript:;" onclick="retriveComment(800, 500)" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe695;</i> 查看评论
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
				<th width="100">时长</th>
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
						<td><input type="checkbox" value="${content.contentId}" name="contentId" id="contentId"></td>
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
						
						<td>${content.mediaTime }</td>
						
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
$.jqPaginator('#pagination', {
    totalPages: ${pager.totalPage},
    visiblePages: 5,
    currentPage: ${pager.cpage},
    first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
    prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
    next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
    last: '<li class="last"><a href="javascript:void(0);">末页</a></li>',
    page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
    onPageChange: function (num) {
    	if(num!=${pager.cpage})
        	window.location.href="<%=request.getContextPath()%>/article/videoArticleList?page="+num+"&size=${pager.pageSize}&" +  + $("#arForm").serialize();
    }
});
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*资讯-增加*/
function add_article(title,url,w,h){
	var index = layer.open({
		type: 2,
		title:title,
		maxmin:true,
		area:[w + 'px', h + 'px'],
		content:url
	});
	
	layer.full(index);
}


var WEB_PATH = "<%=basePath%>";
/*隐藏资讯*/
function modifyVisibility() {
	var selectedBox = $("input[name='contentId']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	
	layer.confirm("确认隐藏/显示选择记录?",function() {
		$.ajax({
			type:"post",
            url:WEB_PATH + "/article/modifyVideoArticleVisibility",
            dataType:'json',
            data: $("#arForm").serialize(),
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
            error:function(e) {
            	layer.msg("系统或者网络出错",{icon:1,time:2000});
            }
		});
	});
	return false;
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

/*审核发布（非专家观点）资讯 */
function publishArticle() {
	var selectedBox = $("input[name='contentId']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	
	if(countRecord(selectedBox, 1) == selectedBox.length) {
		layer.msg("选择的记录已通过审核并已发布！",{icon:1,time:2000});
		return;
	}
	
	layer.confirm("是否审核发布?",function() {
		$.ajax({
			type:"post",
			url:WEB_PATH + "/article/publishVideoArticle",
			dataType:"json",
			data:$("#arForm").serialize(),
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
	return false;
}

/*删除资讯*/
 function delete_article() {
	var selectedBox = $("input[name='contentId']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	layer.confirm("确认要删除吗？",function(index){
		$.ajax({
			type:"post",
			url:WEB_PATH + "/article/deleteVideoArticle",
			dataType:"json",
			data:$("#arForm").serialize(),
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
 function edit_article(title,url,w,h) {
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
	layer.full(index);
	
}

/*查看资讯评论*/
function retriveComment(w, h) {
	var selectedBox = $("input[name='contentId']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	if(selectedBox.length > 1) {
		layer.msg("一次只能查看一条记录的评论！",{icon:1,time:2000});
		return;
	}
	var url = "<%=request.getContextPath() %>/article/videoArticleCommentListByAid/" + selectedBox.val();
	var index = layer.open({
		type:2,
		area:[w + 'px', h + 'px'],
		maxmin:true,
		content:url
	});
	layer.full(index);
}

</script>
</body>
</html>
