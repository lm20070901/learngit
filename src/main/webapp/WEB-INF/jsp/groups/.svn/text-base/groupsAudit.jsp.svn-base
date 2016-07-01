<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 

<title>小组审核</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/groups/editGroups" method="post"  class="form form-horizontal" id="form-category-edit">
		<div class="row cl">
			<fieldset>
				<legend>小组审核</legend>
				<input type="text" name="groupId" value="${data.groupId }" style="display: none;">
			<div class="row cl">
				<label class="form-label col-2">状态：</label>
				<div style="width: 150px" class="formControls col-7">
					<span class="select-box">
						<select class="select" size="1" name="state" id="state">
					        <option value="" <c:if test="${empty state}">selected</c:if>>全部</option>
					        <c:forEach items="${groupsState }" var="st">
					        	<option value="${st.key }" <c:if test="${data.state == st.key}">selected</c:if>>${st.value }</option>
					        </c:forEach>
				      	</select>
			      	</span>
			     </div>
			     <div class="col-1"> </div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">审核建议：</label>
				<div class="formControls col-7"> 
					<textarea name="suggestion" id="suggestion" cols="" rows="" class="textarea" placeholder="审核建议..." onKeyUp="textarealength(this,500)">${data.suggestion }</textarea>
					<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
				</div>
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
		beforeSubmit:function(form){
			var state = $("#state").val();
			var suggestion = $("#suggestion").val();
			if(state == 3){
				if(suggestion == null ||  typeof suggestion == "undefined" || suggestion == ''){
					layer.msg("请填写审核建议",{icon:1,time:2000});
					return false;
				}
			}
		},
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