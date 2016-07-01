<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 

<title>小组编辑</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/groups/editGroups" method="post"  class="form form-horizontal" id="form-category-edit">
		<div class="row cl">
			<fieldset>
				<legend>小组编辑</legend>
				<input type="text" name="groupId" value="${data.groupId }" style="display: none;">
				<label class="form-label col-2"><span class="c-red">*</span>名称：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="${data.groupName }" 
				placeholder="请输入名称" id="groupName" datatype="*" name="groupName" nullmsg="名称不能为空">
				</div> 
				
			</fieldset>
		</div>
		<div class="row cl">
			<div class="col-9 col-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;" name="btn">
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
$(function(){
	$("#form-category-edit").Validform({
		tiptype:2,
		callback:function(form){
			var index = layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
			
			$.ajax({
	            type:"post",
	            url:$("#form-category-edit").attr("action"),
	            dataType:'json',
	            data: $('#form-category-edit').serialize(),
	            success:function(data) {
	            	layer.close(index);
	            	layer.msg(data.msg,{icon:1,time:2000});
	            	
	            	if(data.result==1){
	            		setTimeout(function(){
	            			var index = parent.layer.getFrameIndex(window.name);
	            			$('#search',parent.document).trigger('click');
			    			parent.layer.close(index);
	            		},2000);
	            	}
	            	
	            },
	            error:function(e){ 
	            	layer.close(index);
	            	layer.msg("系统或者网络出错",{icon:1,time:2000});
	            } 
	        });
			
			return false; 
		}
	});
});


</script>
</body>
</html>