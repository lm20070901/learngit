<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="../head.jsp"%>
<link href="<%=request.getContextPath()%>/static/lib/jquery-treetable/css/jquery.treetable.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/lib/jquery-treetable/css/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/static/lib/jquery-treetable/jquery.treetable.js"></script>

</head>
<body>

	<table id="treeTable" class="treetable table-border table-bordered"
		style="margin-left:10px;">
		<thead>
			<tr style='height:30px;'>
				<th>菜单&nbsp;&nbsp;&nbsp;<input type="checkbox" name="" value=""></th>

				<th>访问URL</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>


		</tbody>
	</table>

	<div class="pd-20">
		<div class="row cl">
			<div class="col-offset-3">
				<input class="btn btn-primary radius" type="button"
					value="&nbsp;&nbsp;保存&nbsp;&nbsp;" onclick="save();">
				&nbsp;&nbsp;&nbsp;<input class="btn btn-warning-outline radius"
					type="button" value="返回" onclick="cancle();">
			</div>
		</div>
	</div>
</body>


<script type="text/javascript">
			//保存
			function save(){
				    var selectBox =  $("input[type='checkbox']");
				    
					var ids = "";
					
					for ( var i = 0; i < selectBox.length; i++) {
						if (selectBox[i].checked&&selectBox[i].value!="") {
							ids += selectBox[i].value + ",";	
						}
					} 
					
					if(!ids||ids=="") {
						layer.msg("请选择操作权限!",{icon:1,time:2000});
						return;
					}
					
					ids = ids.substring(0,ids.length-1);
					
					var idArr = ids.split(',');
					
					var index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
					
					$.ajax({
			            type:'post',
			            url:"<%=request.getContextPath()%>/role/distributeAction",
			            dataType:'json',
			            async : false,
			            data: {idList:idArr,roleId: ${roleId}},
			            success:function(json){
			            	layer.close(index);
			            	if(json.result==1){
			            		layer.msg(json.msg,{icon:1,time:2000});
			            		
			            	}else{
			            		layer.msg(json.msg,{icon:1,time:2000});
			            	}
			            },
			            error:function(e){ 
			            	layer.close(index);
			            	layer.msg("系统或者网络出错",{icon:1,time:2000});
			            } 
			        });
			}
			//取消
			function cancle(){
				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.layer.close(index); //再执行关闭  
			}
		 var index = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
		 }); 
		 
		var table = $("#treeTable");
		
		var quer_num = 0;
		table.treetable({
			expandable : true,
			onNodeCollapse : function() {
				var node = this;
				// table.treetable("unloadBranch", node);
			},
			onNodeExpand : function() {
				
			},
			onNodeInitialized :function (){
				var node = this;
				if($("tr[data-tt-id='"+node.id+"']").attr("haveChild")=="1"){
					var html = queryData(node.id);
					table.treetable("loadBranch", node, html);
				}
				//console.log(quer_num);
				//table.treetable("collapseNode", node.id);
				$("input[name='selectBox"+node.id+"']").change(changeSelected);//绑定选中事件
			},
			onInitialized : function (){
				layer.close(index);
				//console.log(quer_num);
				//table.treetable("collapseAll");
			}
			
		});
		
		function queryData(level){
			quer_num++;
			var rows ;
		 	$.ajax({
	            type:'post',
	            url:"<%=request.getContextPath()%>/role/queryActionsList",
				dataType : 'json',
				async : false,
				data : {pid : level,roleId: ${roleId} },
				success : function(json) {
						if (json.result == 1) {
							var actionList = json.actionList;
							rows = '';
							for ( var i = 0; i < actionList.length; i++) {
								var action = actionList[i];
								var checked = action.isUsed==0?'':'checked';
								
								var row = "<tr  data-tt-id='"+action.actionId
				      					+"' data-tt-parent-id='"/* */
				      					+action.pid+"'  class='text-l new' style='height:30px;' haveChild='"
				      					+action.haveChild+"'><td><input type='checkbox' name='selectBox"+action.pid+"'  "
										+checked+" value='"
										+ action.actionId+"'>&nbsp;&nbsp;&nbsp;"
										+ action.actionName
										+ "</td><td>"
										+ action.actionUrl
										+ "</td><td>"
										+ action.mark + "</td></tr>";
								rows += row;
							}
						} else {
							layer.msg(json.msg, {icon : 1,time : 2000 });
						}
					},
				error : function(e) {
						layer.close(index);
						layer.msg("系统或者网络出错", {
							icon : 1,
							time : 2000
						});
					}
				});
		return rows;
	}

	function init() {
		var html = queryData(0);
		table.treetable("loadBranch", table.treetable("node", 0), html);
		$("input[name='selectBox0']").change(changeSelected);//绑定选中事件
	}
	
	init();
	
	/* 选中事件 */
	function changeSelected(){
		var id = $(this).val();
		//父节点 的选择状态将改变子节点状态
	    $("input[name='selectBox"+id+"']").prop("checked",$(this).prop("checked"));
	   
	   <%--  var selectBox =  $("input[name='selectBox"+id+"']");
		var ids = "";
		for ( var i = 0; i < selectBox.length; i++) {
				ids += selectBox[i].value + ",";
		} 
		ids +=id;
		//console.log(ids);
		if(!ids) return;
		var idArr = ids.split(',');
		
		var index = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
		
		$.ajax({
            type:'post',
            url:"<%=request.getContextPath()%>/role/distributeAction",
            dataType:'json',
            async : false,
            data: {idList:idArr,roleId: ${roleId}},
            success:function(json){
            	layer.close(index);
            	if(json.result==1){
            		layer.msg(json.msg,{icon:1,time:2000});
            		
            	}else{
            		layer.msg(json.msg,{icon:1,time:2000});
            	}
            },
            error:function(e){ 
            	layer.close(index);
            	layer.msg("系统或者网络出错",{icon:1,time:2000});
            } 
        }); --%>
	}
</script>
</html>
