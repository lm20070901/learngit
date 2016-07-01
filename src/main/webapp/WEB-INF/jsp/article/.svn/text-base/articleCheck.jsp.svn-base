<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/ueditor/1.4.3/ueditor.config.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/ueditor/1.4.3/ueditor.all.min.js"> </script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/uploadify/jquery.uploadify.min.js"> </script> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/lib/uploadify/uploadify.css" />
<title>资讯审核</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/article/checkExpertArticle" method="post" class="form form-horizontal" id="form-article-check">
		<div style="display: none"><input type="text" id="contentId" name="contentId" value="${article.contentId}"/></div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>标题：</label>
			<div class="formControls col-7">
				<input type="text" class="input-text" value="${article.title }" readonly="readonly">
			</div>
			<div class="col-3"> </div>
		</div>
		<%-- <div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>简介：</label>
			<div class="formControls col-7"> 
				<textarea readonly="readonly" rows="" class="textarea" >${article.introduce }</textarea>
			</div>
			<div class="col-3"> </div>
		</div> --%>
		<div class="row cl">
			<label class="form-label col-2">作者：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" readonly="readonly" value="${article.author }">
			</div>
			<div class="col-1"> </div>
			
		</div>
		
		<div class="row cl">
			<label class="form-label col-2">正文内容：</label>
			<div class="formControls col-10"> 
				<script id="content" type="text/plain" style="width:100%;height:400px;">
					${article.content }
				</script> 
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-2">审核建议：</label>
			<div class="formControls col-10"> 
				<textarea class="textarea" name="advise" id="advise"></textarea>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">审核结果：</label>
			<div class="formControls col-6"> 
				  <div class="radio-box">
				    <input type="radio" id="radio-1" name="state" value="1" checked>
				    <label for="radio-1">通过</label>
				  </div>
				  <div class="radio-box">
				    <input type="radio" id="radio-4" name="state" value="4">
				    <label for="radio-4">不通过</label>
				  </div>
			</div>
		</div>
		<div class="row cl">
			<div class="col-9 col-offset-3">
				<input class="btn btn-primary radius" type="submit" onclick="return checkForm()" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
$(function(){
	var ue = UE.getEditor('content');
});

function checkForm() {
	
	if($("#radio-4:checked").val()) {
		if($("#advise").val().trim().length <= 0) {
			layer.msg('请输入审核不通过建议！',{time:2000});
			return false;
		}
	}
	
	var index = layer.load(1, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
	
	$.ajax({
		type : "post",
		url : "<%=request.getContextPath()%>/article/checkExpertArticle",
		dataType : "json",
		data : $("#form-article-check").serialize(),
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


</script>
</body>
</html>