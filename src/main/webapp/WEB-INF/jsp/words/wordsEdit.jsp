<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 

<title>编辑敏感词</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/words/wordsEidt" method="post"  class="form form-horizontal" id="form-words-edit">
		<input type="hidden" name="id" value="${data.id }">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>名词：</label>
			<div class="formControls col-4">
				<input type="text" class="input-text" id="word" value="${data.word }" datatype="*" name="word" nullmsg="名词不能为空">
			</div>
			<div class="col-2"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-3">备注：</label>
			<div class="formControls col-6">
				<textarea class="textarea"  id="remarks" name="remarks">${data.remarks }</textarea>
			</div>
			<div class="col-2"> </div>
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
	$("#form-words-edit").Validform({
		tiptype : 2,
		callback : function(form) {

			TableUtils.saveUrl = $(form).attr("action");
			TableUtils.saveOpr($(form).attr("id"), function() {
				setTimeout(function() {
					var index = parent.layer.getFrameIndex(window.name);
					$("#search",parent.document).trigger("click");
					parent.layer.close(index);
				}, 2000);
			});

			return false;
		}
		
	});
	
});

</script>
</body>
</html>