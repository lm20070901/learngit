<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags" prefix="date"%> 
<html>
<head> 
	<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
	<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/jquery.sortable.js"></script> -->
	 <script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/TableDnD-master/js/jquery.tablednd.0.9.rc1.js"></script>
	 <%-- <link href="<%=request.getContextPath()%>/static/lib/TableDnD-master/tablednd.css" rel="stylesheet" type="text/css" /> --%>
	<style type="text/css">
		table tbody tr.myDragClass td {
			font-size: 15px;
			/* padding:8px;
			background-color: #D4E6FC; */
		    /* color: red;  */
	    	background-color:  #D4E6FC;
    		/* -webkit-box-shadow: 0 12px 14px -12px #111 inset, 0 -2px 2px -1px #333 inset; */
		}
		
	</style>
	
<title>创建问卷调查</title>
</head>
<body>
<div class="pd-20">
		<!-- 父页面先保存才能进行操作 -->
	
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<input type="hidden" name="voteId" value="${voteId}" id="voteId"/>
	<c:if test="${!empty voteId}">
		<span class="l">
			
				<a href="javascript:;" onclick="addOpr();" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe600;</i> 新建
				</a>
				<a href="javascript:;" onclick="editOpr();" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe647;</i>编辑
				</a> 
				<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius">
					<i class="Hui-iconfont">&#xe6e2;</i> 删除
				</a> 
		</span> 
	 </c:if>
		
		<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条</span>
	</div>
	<table class="table table-border table-bordered table-bg table-sort table-hover" id="table-1">
		<thead>
			<tr class="nodrop nodrag">
				<th scope="col" colspan="5">已经创建题目列表<th>
			</tr>
			<tr class="text-c nodrop nodrag">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="50">ID</th>
				<th width="300" class="l">问题标题</th>
				<th width="100">问题类型</th>
				<!-- <th width="40">排序号</th>-->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vote" items="${list}"  varStatus="status">
			<tr class="text-c" class="gbsortable active" id="row${status.index+1}"><!-- 必须给tr元素添加id属性  不然onDrop事件不响应-->
				<td><input type="checkbox" value="${vote.questionId}" name="selectBox"/></td>
				<td>${status.index+1}</td>
				<td>${vote.title}</td>
				<td>
					<c:if test="${vote.type==0}">单选题</c:if>
					<c:if test="${vote.type==1}">多选题</c:if>
					<c:if test="${vote.type==2}">填空题</c:if>
				</td>
				<!-- <td>${status.index+1}</td>-->
			</tr> 
			</c:forEach>
		</tbody>
	</table>
	<div id="pagination"></div>
	
	
	<!-- <ul id="sortable">
  <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 1</li>
  <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 2</li>
  <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 3</li>
  <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 4</li>
  <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 5</li>
  <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 6</li>
  <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 7</li>
</ul>-->
 
</div>
<script type="text/javascript">

//$( "#sortable" ).sortable();

$(function (){
	$('#table-1').tableDnD({
		onDragClass:'myDragClass' ,
	    onDrop: function(table, row) {
	    	saveOrderNum();
	    }
	}); 
	
	 $("#table-1 tr").hover(function() {
         $(this.cells[0]).addClass('showDragHandle');
   }, function() {
         $(this.cells[0]).removeClass('showDragHandle');
   });
	
});

//保存排序
function saveOrderNum(){
	var selectBox = $("#table-1 input[name='selectBox']");
    if(selectBox.length<=0) {
    	layer.msg("还没有创建问题!",{icon:1,time:2000});
    	return;
    }
	var idArr = new Array();
	for ( var i = 0; i < selectBox.length; i++) {
		idArr[i]=selectBox[i].value ;
	}
	var index = parent.window.layer.load(1, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景
	});
	$.ajax({
        type:'post',
        url:"<%=request.getContextPath()%>/vote/saveQuestionOrder",
        dataType:'json',
        data: {'idList':idArr},
        success:function(json){
        	parent.window.layer.close(index);
        	parent.window.layer.msg(json.msg,{icon:1,time:2000});
        },
        error:function(e){ 
        	layer.close(TableUtils.index);
        	parent.window.layer.msg("系统或者网络出错",{icon:1,time:2000});
        } 
    });  
}
//取消分页
<%-- TableUtils.init({
	deleteUrl : "<%=request.getContextPath()%>/vote/deleteVoteQuestionByIds",
	queryUrl  : "<%=request.getContextPath()%>/vote/toVoteQuestionList?voteId=${voteId}",
	page: {
		totalPage  :  ${pager.totalPage},
		cpage      :  ${pager.cpage},
		pageSize   :  ${pager.pageSize} 
	}
}); --%>

/*新增*/
function addOpr(){
	parent.window.layer_show("创建问卷题目","<%=request.getContextPath()%>/vote/toVoteQuestionEdit?voteId=${voteId}","800","500");
	//layer.full(index);
}

/*修改*/
function editOpr(){
	var selectTrs  = TableUtils.getSelectedRows();
	
	if (!selectTrs)  return;
	
	if(selectTrs.length != 1){
		layer.msg("只能选择一行记录!",{icon:1,time:2000});
		return;
	}
	
	parent.window.layer_show("修改问卷题目","<%=request.getContextPath()%>/vote/toVoteQuestionEdit?voteId=${voteId}&questionId="+TableUtils.ids,"800","500");

}

/*删除*/
function deleteOpr(){
	TableUtils.deleteUrl  = "<%=request.getContextPath()%>/vote/deleteVoteQuestionByIds";
	var ids = TableUtils.getSelectedRowsId();
	if(!ids) return;
	var idArr = ids.split(',');
	TableUtils.index = parent.window.layer.load(1, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景
	});
	parent.window.layer.confirm(TableUtils.deleteMsg,function(){
		
		if(!TableUtils.deleteUrl || TableUtils.deleteUrl==''){
			parent.window.layer.msg("请先设置操作路径！",{icon:1,time:2000});
	    	return;
	    }
		
		TableUtils.ajaxPost(TableUtils.deleteUrl,{'idList':idArr},function (){
			window.location.reload();
		});
	},
	function() {
		parent.window.layer.close(TableUtils.index);
	});
	
}

//重写改方法  使得弹窗口在页面中间 
TableUtils.getSelectedDatas = function (){
	
	var selectBox = $("input[name='selectBox']");
	var selectTrs = new Array();
	var ids = "";
	var n = 0;
	for ( var i = 0; i < selectBox.length; i++) {
		if (selectBox[i].checked) {
			ids += selectBox[i].value + ",";
			selectTrs[n] = $(selectBox[i]).parent().parent();
			n++;
		}
	}
	if (ids.length == 0) {
	
		TableUtils.selectTrs = undefined ;
		TableUtils.ids = undefined ;
		parent.window.layer.msg("请选择后操作", {icon : 1,time : 2000});
		return;
	} else {
		ids = ids.substring(0, ids.length - 1);
	}
	
	TableUtils.selectTrs = selectTrs;
	TableUtils.ids = ids;
}
		
</script>
</body>
</html>