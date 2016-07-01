<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>

	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 

<title>
<c:choose>
		<c:when test="${voteType == 1}">
			系统问卷
		</c:when>
		<c:otherwise>
			用户问卷
		</c:otherwise>
</c:choose>
</title>
<style type="text/css">
/* div{
	background-color: #e9f0f6;
     border: 1px #949494 solid;
	 z-index: 1001; 
	 position: absolute;
	  height: 50px; 
	  width: 50px; 
	  margin-left: 70px; 
	  display: none;
} */
div.detail_info
{
    position: absolute;
    z-index: 1000;
    min-width: 150px;
    min-height: 40px;
    display: none;
    padding: 3px;
    overflow: visible;
    text-align: left;
}
</style>
</head>

<body>

	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>问卷管理<span class="c-gray en">&gt;</span> 问卷列表 <a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
<div class="pd-20">
	<div class="text-l">
		<form action="<%=request.getContextPath()%>/vote/voteList" method="post">
		 	问卷标题：
			<input type="text" class="input-text" style="width:250px" placeholder="请输入标题" id="voteTitle" name="voteTitle" value="${voteTitle}">
			
			<!-- 问卷类型 -->
			<input type="hidden" class="input-text" style="width:250px"  id="voteType" name="voteType" value="${voteType}"> 
			
			<button type="submit" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
		</form>
	</div>
	
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<c:if test="${voteType == 0}">
				
				<shiro:hasPermission name="questionnaire:vote:toEdit">
				<a href="javascript:;" onclick="addOpr();" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe600;</i> 新建
				</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="questionnaire:vote:toEdit">
				<a href="javascript:;" onclick="editOpr();" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe647;</i>编辑
				</a> 
				</shiro:hasPermission>
				<shiro:hasPermission name="questionnaire:vote:publish">
				<a href="javascript:;" onclick="publishOpr()" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe603;</i> 发布
				</a>
				</shiro:hasPermission>
				<!-- <a href="javascript:;" onclick="hiddenOrShowOpr();" class="btn btn-danger radius">
					<i class="Hui-iconfont"></i>  隐藏/显示
				</a> 
				<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
					<i class="Hui-iconfont">&#xe6e2;</i> 删除
				</a> 
				<a href="javascript:;" onclick="viewOpr();" class="btn btn-primary radius"  ">
					<i class="Hui-iconfont">&#xe695;</i> 查看详情
				</a> -->
				
				<shiro:hasPermission name="questionnaire:vote:export">
				<a href="javascript:;" onclick="exportOpr();" class="btn btn-primary radius" >
					<i class="Hui-iconfont">&#xe644;</i> 导出答卷
				</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="questionnaire:vote:toAddInvite">
				<a href="javascript:;" onclick="createOpr();" class="btn btn-primary radius" >
					<i class="Hui-iconfont">&#xe600;</i> 生成邀请码
				</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="questionnaire:vote:toInviteCodeList">
				<a href="javascript:;" onclick="manageOpr();" class="btn btn-primary radius" >
					<i class="Hui-iconfont">&#xe61d;</i> 管理邀请码
				</a>
				</shiro:hasPermission>
			</c:if>
				<shiro:hasPermission name="questionnaire:vote:view">
				<a href="javascript:;" onclick="viewOpr();" class="btn btn-primary radius"  >
					<i class="Hui-iconfont">&#xe695;</i> 查看详情
				</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="questionnaire:vote:update">
				<a href="javascript:;" onclick="hiddenOrShowOpr();" class="btn btn-warning radius">
					<i class="Hui-iconfont"></i> 隐藏/显示
				</a> 
				</shiro:hasPermission>
				<shiro:hasPermission name="questionnaire:vote:delete">
				<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
					<i class="Hui-iconfont">&#xe6e2;</i> 删除
				</a> 
				</shiro:hasPermission>
				<shiro:hasPermission name="questionnaire:vote:end">
				<a href="javascript:;" onclick="endOpr();" class="btn btn-danger  radius">
					<i class="Hui-iconfont"></i> 结束
				</a> 
				</shiro:hasPermission>
		</span> 
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span>
	</div>
	<table class="table table-border table-bordered table-bg table-sort" style="table-layout:fixed;">
		<thead>
			<tr>
				<th scope="col" colspan="12">问卷列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="20">ID</th>
				<th width="150">问卷标题</th>
				<th width="90">问卷类型</th>
				<th width="100">创建人</th>
				<th width="130">创建时间</th>
				<c:if test="${voteType == 0}">
						<th width="30">邀请人数</th>
				</c:if>
				<th width="30">答题人数</th>
				<th width="130">截止时间</th>
				<th width="30">发布状态</th>
				<th width="100">问卷介绍</th>
				<th width="30">显示状态</th> 
				<c:if test="${voteType == 1}">
						<th width="50"></th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vote" items="${list}">
			<tr class="text-c">
				<td><input type="checkbox" value="${vote.voteId}" name="selectBox"></td>
				<td>${vote.voteId}</td>
				<td class="text-overflow text-l">${vote.voteTitle}</td>
				<td>
				<c:if test="${vote.voteType==0}">
						 <c:if test="${vote.isInvite==1}">系统邀请问卷</c:if>
						 <c:if test="${vote.isInvite==0}">系统公开问卷</c:if> 
				</c:if>
				<c:if test="${vote.voteType==1}">用户问卷</c:if>
				</td>
				<td>${vote.userName}</td>
				<td>
					<date:date value="${vote.voteTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<c:if test="${vote.voteType==0}">
						<td>${vote.inviteNums}</td>
				</c:if>
				<td>${vote.voteTimes}</td>
				<td><date:date value="${vote.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:if test="${vote.state==2}">已结束</c:if>
					<c:if test="${vote.state==1}">已发布</c:if>
					<c:if test="${vote.state==0}">待发布</c:if>
				</td>
				<!--style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;"  -->
				<td class="text-overflow text-l" title="${vote.voteInfo}" >${vote.voteInfo}
				<%-- <div class="detail_info">
					
			    </div> --%>
				</td>
				<td><c:if test="${vote.showStatus==1}">隐藏</c:if>
					<c:if test="${vote.showStatus==0}">显示</c:if>
				</td>
				<c:if test="${vote.voteType==1}">
						<td></td>
				</c:if>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pagination"></div>
</div>
	<script type="text/javascript">
	
	 /*  $(".text-overflow").hover( 
	    function(){ 
	        // $(this).next("div").show(); 
	    	 $(this).children(".detail_info").show(); 
        }, 
        function(){ 
          $(this).children(".detail_info").hide(); 
      });  */
		//初始化
		TableUtils.init({
			deleteUrl : "<%=request.getContextPath()%>/vote/deleteVoteByIds",
			updateUrl : "<%=request.getContextPath()%>/vote/updateShowStatus",
			queryUrl  : "<%=request.getContextPath()%>/vote/voteList",
			page: {//分页
				totalPage  :  ${pager.totalPage},
				cpage      :  ${pager.cpage},
				pageSize   :  ${pager.pageSize} 
			}
		});
		
			/*新增问卷调查*/
			function addOpr(){
				var index = layer.open({
					type: 2,
					title: "创建问卷",
					content: "<%=request.getContextPath()%>/vote/toEditVote?voteType=${voteType}"
				});
				
				layer.full(index);
			}
			
			/**编辑问卷调查*/
			function editOpr(){
				var selectTrs  = TableUtils.getSelectedRows();
				
				if (!selectTrs)  return;
				
				if(selectTrs.length != 1){
					layer.msg("只能选择一行记录!",{icon:1,time:2000});
					return;
				}
				
				var text = $(selectTrs[0]).children('td').eq(-3).text();
				if('待发布' != text.trim()){
						layer.msg("只能编辑待发布问卷!",{icon:1,time:2000});
						return ;
				}
				
				layer.full(layer.open({
					type: 2,
					title: "编辑问卷",
					content: "<%=request.getContextPath()%>/vote/toEditVote?voteType=${voteType}&voteId="+TableUtils.ids 
				}));
				
			}
			/* 显示/隐藏操作 */
			function hiddenOrShowOpr(){
				
				TableUtils.updateUrl = "<%=request.getContextPath()%>/vote/updateShowStatus";
				TableUtils.updateMsg = "确认要隐藏/显示吗？";
				TableUtils.updateOpr(function (){
					var selectTrs = TableUtils.selectTrs;
					for ( var i = 0; i < selectTrs.length; i++) {
						var text = $(selectTrs[i]).children('td').eq(-1).text();
						if('隐藏' == text.trim()){
							text = '显示';
						}else if('显示' == text.trim()){
							text = '隐藏';
						}
						$(selectTrs[i]).children('td').eq(-1).text(text);//改变某个列的值 
					}
					
				});
				
			}
			
			
			//问卷发布
			function publishOpr(){
				var selectTrs  = TableUtils.getSelectedRows();
				if (!selectTrs)  return;
				for ( var i = 0; i < selectTrs.length; i++) {
					var text = $(selectTrs[i]).children('td').eq(-3).text();
					if('待发布' != text.trim()){
						layer.msg("只能发布待发布问卷!",{icon:1,time:2000});
						return ;
					}
				}
				TableUtils.updateUrl = "<%=request.getContextPath()%>/vote/publishVotes";
				TableUtils.updateMsg = "确认要发布吗？";
				TableUtils.updateOpr(function (){
					
					for ( var i = 0; i < selectTrs.length; i++) {
						var text = $(selectTrs[i]).children('td').eq(-3).text();
						
						$(selectTrs[i]).children('td').eq(-3).text("已发布");//改变某个列的值 
					}
					
				});
			}
			
			 /* 删除问卷*/
			function deleteOpr(){
				TableUtils.deleteOpr(function (){
					 location.reload();
				});
			} 
			 
			 
			 /**结束问卷调查*/
			function endOpr(){
				var selectTrs  = TableUtils.getSelectedRows();
				if (!selectTrs)  return;
				for ( var i = 0; i < selectTrs.length; i++) {
					var text = $(selectTrs[i]).children('td').eq(-3).text();
					if('已发布' != text.trim()){
						layer.msg("只能结束已发布问卷!",{icon:1,time:2000});
						return ;
					}
				}
				TableUtils.updateUrl = "<%=request.getContextPath()%>/vote/endVotes";
				TableUtils.updateMsg = "确认要结束问卷调查吗？";
				TableUtils.updateOpr(function (){
					
					for ( var i = 0; i < selectTrs.length; i++) {
						var text = $(selectTrs[i]).children('td').eq(-3).text();
						
						$(selectTrs[i]).children('td').eq(-3).text("已结束");//改变某个列的值 
					}
					
				});
			}
			 
			 /*查看详情*/
			 function viewOpr(){
				    var selectTrs  = TableUtils.getSelectedRows();
					
					if (!selectTrs)  return;
					
					if(selectTrs.length != 1){
						layer.msg("只能选择一行记录!",{icon:1,time:2000});
						return;
					}
					var voteId = TableUtils.ids;
					layer.full(layer.open({
						type: 2,
						title: "编辑问卷",
						content: "<%=request.getContextPath()%>/vote/toVoteDetail?voteId="+voteId 
					}));
			 }
			 
			 /*导出答卷*/
			 function exportOpr(){
				    var selectTrs  = TableUtils.getSelectedRows();
					
					if (!selectTrs)  return;
					
					if(selectTrs.length != 1){
						layer.msg("只能选择一行记录!",{icon:1,time:2000});
						return;
					}
					var text = $(selectTrs[0]).children('td').eq(-3).text();
					if('已发布' != text.trim()){
							layer.msg("只能导出已发布问卷答案!",{icon:1,time:2000});
							return ;
					}
					var voteId = TableUtils.ids;
				    window.location.href =  "<%=request.getContextPath()%>/vote/exportVote?voteId="+voteId 
			 }
			 
			 /*生成校验码*/
			 function createOpr(){
				   var selectTrs  = TableUtils.getSelectedRows();
					
					if (!selectTrs)  return;
					
					if(selectTrs.length != 1){
						layer.msg("只能选择一行记录!",{icon:1,time:2000});
						return;
					}
					var text = $(selectTrs[0]).children('td').eq(-3).text();
					if('已结束' == text.trim()){
							layer.msg("问卷调查已经结束!",{icon:1,time:2000});
							return ;
					}
					var voteId = TableUtils.ids;
					layer_show("生成邀请码","<%=request.getContextPath()%>/vote/toAddInviteCode?voteId="+TableUtils.ids,"800","500");
				  
			 }
			 
			 
			 /*管理邀请码*/
			 function manageOpr(){
				 var selectTrs  = TableUtils.getSelectedRows();
					
					if (!selectTrs)  return;
					
					if(selectTrs.length != 1){
						layer.msg("只能选择一行记录!",{icon:1,time:2000});
						return;
					}
					
					layer.full(layer.open({
						type: 2,
						title: "管理邀请码",
						content: "<%=request.getContextPath()%>/vote/toInviteCodeList?voteId="+TableUtils.ids 
					}));
			 }
		</script>
</body>
</html>
