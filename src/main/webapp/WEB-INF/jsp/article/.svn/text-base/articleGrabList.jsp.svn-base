<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
<title>资讯抓取</title>
<style type="text/css">

</style>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 内容管理 <span class="c-gray en">&gt;</span>
		资讯抓取
	 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<form id="arForm" action="<%=request.getContextPath()%>/articleGrab/grabList" method="post">
	<div class="text-l">
		标题：<input type="text" class="input-text" style="width:150px" placeholder="输入标题" id="" name="title" value="${title}"> &nbsp;&nbsp;
			
			来源：<input type="text" class="input-text" style="width:100px" placeholder="输入来源" id="" name="origin" value="${origin}"> &nbsp;&nbsp;
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
		<shiro:hasPermission name="article:grab:toEdit"> 
			<a href="javascript:;" onclick="toEditPage('发布资讯','<%=request.getContextPath() %>/articleGrab/toEditPage','800','500')" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe603;</i> 发布
			</a> 
		</shiro:hasPermission>
		<shiro:hasPermission name="article:grab:hide"> 
			<a href="javascript:;" onclick="modifyVisibility()" class="btn btn-warning radius">
				 隐藏/显示
			</a> 
		</shiro:hasPermission>
		<shiro:hasPermission name="article:grab:delete"> 
			<a href="javascript:;" onclick="delete_grab()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 删除
			</a> 
		</shiro:hasPermission>
		</span> 
		
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span> </div>
		<div style="display: none"><input type="text" id="contentType" name="contentType" value="${contentType}"/></div>
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
				<th width="100">抓取时间</th>
				<th width="50">阅读量</th>
				<th width="50">是否显示</th>
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
						<td logo="${content.state}" >
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
						<c:if test="${not empty content.grabTime}">
							<jsp:useBean id="grabTime" class="java.util.Date"/> 
							<c:set target="${grabTime}" property="time" value="${content.grabTime}"/> 
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${grabTime}" type="both"/> </td>
						</c:if>
						<c:if test="${empty content.grabTime}">
							<td></td>
						</c:if>
						<td>${(empty content.readTimes || content.readTimes < 0) ? 0 : content.readTimes}</td>
						<td flag="${content.showStatus}">
							<c:if test="${content.showStatus == 0 }"><span class="c-red">显示</span></c:if>
							<c:if test="${content.showStatus == 1 }">隐藏</c:if>
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
	queryUrl  : "<%=request.getContextPath()%>/articleGrab/grabList",
	page: {//分页
		totalPage  :  ${pager.totalPage},
		cpage      :  ${pager.cpage},
		pageSize   :  ${pager.pageSize}
	}
});
var WEB_PATH = "<%=basePath%>";
/*隐藏资讯*/
function modifyVisibility() {
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
	layer.confirm("确认要隐藏/显示吗？",function(index){
		$.ajax({
			type:"post",
			url:"<%=request.getContextPath()%>/articleGrab/modifyVisibility",
			dataType:"json",
			data:{'idList':idArr,'value':value},
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

/*计算选择的记录中已指定状态(显示/隐藏)的记录数*/
function countRecord(selectedBox, state) {
	var count = 0;
	for(var i = 0; i < selectedBox.length; i++) {
		if($('[flag]',$(selectedBox[i]).parent().parent()).attr('flag') == state) {
			count++;
		}
	}
	return count;
}

/*删除资讯*/
 function delete_grab() {
	var selectedBox = $("input[name='selectBox']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	var ids = TableUtils.getSelectedRowsId();
	if(!ids) return;
	var idArr = ids.split(',');
	layer.confirm("确认要删除吗？",function(index){
		$.ajax({
			type:"post",
			url:WEB_PATH + "/articleGrab/deleteGrab",
			dataType:"json",
			data:{'idList':idArr},
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
 function toEditPage(title,url,w,h) {
	var selectedBox = $("input[name='selectBox']:checked");
	if(selectedBox.length == 0) {
		layer.msg("请选择后操作",{icon:1,time:2000});
		return;
	}
	var logo = $('[logo]',$(selectedBox[0]).parent().parent()).attr('logo');
	if(logo == 1) {
		layer.msg("选择的记录已发布！",{icon:1,time:2000});
		return;
	}
	if(selectedBox.length > 1) {
		layer.msg("一次只能发布一条记录！",{icon:1,time:2000});
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


</script>
</body>
</html>
