

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="../head.jsp"%>
<link href="<%=request.getContextPath()%>/static/lib/jquery-treetable/css/jquery.treetable.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/lib/jquery-treetable/css/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/jquery-treetable/jquery.treetable.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
 --%>
</head>
<body>
<div class="pd-20">
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l"> 
			<shiro:hasPermission name="system:action:toActionAdd">
				<a href="javascript:;" onclick="addOpr();" class="btn btn-primary radius"> 
					<i class="Hui-iconfont">&#xe600;</i>新建
				</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="system:action:toActionEdit">
				<a href="javascript:;" onclick="editOpr();" class="btn  btn-primary  radius"> 
					<i class="Hui-iconfont">&#xe647;</i>编辑
				</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="system:action:delete">
				<a href="javascript:;" onclick="deleteOpr();" class="btn btn-danger radius"> 
					<i class="Hui-iconfont">&#xe6e2;</i>删除
				</a>
			</shiro:hasPermission>
			</span>
		</div>
		<table id="treeTable"
			class="treetable table table-border table-bordered"
			style="margin-left:10px;">
			<thead>
				<tr style='height:30px;'>
					<th><input type="checkbox" name="" value="">&nbsp;&nbsp;&nbsp;菜单</th>
					<th>访问URL</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr  data-tt-id='0' data-tt-parent-id=''  class='text-l' style='height:0px;'>
					<td><input type='checkbox'  name='selectBox' id="index"  value='0'>&nbsp;&nbsp;&nbsp;系统菜单</td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>


<script type="text/javascript">
		
		
		var table = $("#treeTable");
		
		table.treetable({
			expandable : true,
			onNodeCollapse : function() {
				var node = this;
				// table.treetable("unloadBranch", node);
			},
			onNodeExpand : function() {
				var node = this;
				if(!node.children||node.children.length<=0){
					
					//$("input[value='"+node.id+"']").prev('span').empty();
					$("input[value='"+node.id+"']").prev('span').empty();
				}
				
			},
			onNodeInitialized :function (){
				/* var node = this;
				console.log(node.id);
				var html = queryData(node.id);
				//console.log(html);
				$("#treeTable").treetable("loadBranch",node, html); */
			}
		});
		
		
		//获取选中行
        function getSelectedIds(){
        	
        	var selectBox = $("input[type='checkbox']");
        	var ids = "";
        	for ( var i = 0; i < selectBox.length; i++) {
        		if (selectBox[i].checked) {
        			ids += selectBox[i].value + ",";
        		}
        	}
        	if (ids.length == 0) {
        		layer.msg("请选择后操作！", {icon : 1,time : 2000});
        		return;
        	} else {
        		ids = ids.substring(0, ids.length - 1);
        	return ids;
        	}
		}
        
        //新增
        function addOpr(){
        		var id = getSelectedIds();
        		if(!id) return;//未选择  默认创建一级菜单
    			
    			if(id.indexOf(",")!=-1){
    				layer.msg("只能选择一行记录!",{icon:1,time:2000});
    				return;
    			}
        		var url = "<%=request.getContextPath()%>/action/toActionAdd?pid="+id;
        		
        		layer_show('新增访问路径',url,'800','500');
        		
        		
        }
    		
        //编辑 
    	function editOpr(){
    			var id = getSelectedIds();
    			if(!id) return;
    			
    			if(id.indexOf(",")!=-1){
    				layer.msg("只能选择一行记录!",{icon:1,time:2000});
    				return;
    			}
    			//node.parentId
    			var url = "<%=request.getContextPath()%>/action/toActionEdit";
    			url+='?actionId='+id;
    			//var userId = id;
    			layer_show('编辑访问路径',url,'800','500');
    		
    	}
		//回调函数
		function callBack(data){
			table.treetable("unloadBranch", table.treetable("node",data.pid));
			loadChild($("input[value="+data.pid+"]"),data.pid);
			
		}
		
		
		//删除
		function deleteOpr(){
			var id = getSelectedIds();
			if(!id) return;
			
			
			var idArr = id.split(',');
			
			layer.confirm("确认要删除选中行吗？",function(index1){
				
				/* var index1 = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
				}); */
				$.ajax({
		            type:'post',
		            url : "<%=request.getContextPath()%>/action/deleteActionByIds",
					dataType : 'json',
					data : {'idList':idArr},
					success : function(json) {
						   
							layer.close(index1);
							
							if (json.result == 1) {
								$.each(idArr,function (i,n){
									var node = table.treetable("node",n);
									//var parentNode = table.treetable("node",node.parentId);
									table.treetable("unloadBranch",table.treetable("node",node.parentId));
									loadChild($("input[value="+node.parentId+"]"),node.parentId);
									
								}); 
							} else {
								layer.msg(json.msg, {
									icon : 1,
									time : 2000
								});
							}
						},
						error : function(e) {
							
							layer.msg("系统或者网络出错", {
								icon : 1,
								time : 2000
							});
						}
					});
			}); 
		}
		
		 function queryData(level){
			  var index = layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
			  });
			var rows ;
		 	$.ajax({
	            type:'post',
	            url:"<%=request.getContextPath()%>/role/queryActionsList",
					dataType : 'json',
					async : false,
					data : {pid : level,roleId: -1 },
					success : function(json) {
						layer.close(index);

						if (json.result == 1) {
							var actionList = json.actionList;
							rows = '';
							for ( var i = 0; i < actionList.length; i++) {
								var action = actionList[i];
								var checked = action.isUsed==0?'':'checked';
								
								var row = "<tr  data-tt-id='"+action.actionId
				      					+"' data-tt-parent-id='"/* */
				      					+action.pid+"'  class='text-l new' style='height:30px;'><td  onclick='loadChild(this,"
										+ action.actionId
										+ ")' > <input type='checkbox'  name='selectBox"+action.pid+"'  "
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
							layer.msg(json.msg, {
								icon : 1,
								time : 2000
							});
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
		
		//$("#treeTable tbody tr ").find('.indenter').not().append("<a href='#' >&nbsp;</a>");
		$("input[name='selectBox0']").prev('span').html("<a href='#' >&nbsp;</a>");
		
	}
	
	
	init();
	
	
	
	function loadChild(obj, id) {
		
		var html = queryData(id);
		table.treetable("loadBranch", table.treetable("node", id), html);
		table.treetable("expandNode", id);
		//加载完数据后就可以解除该事件
		$(obj).removeAttr('onclick');
		
		$("input[name='selectBox"+id+"']").prev('span').html("<a href='#' >&nbsp;</a>");
		
	};
	
	
</script>
</html>
