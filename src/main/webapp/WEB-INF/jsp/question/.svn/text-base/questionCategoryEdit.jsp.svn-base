<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/webuploader/0.1.5/webuploader.nolog.js"></script>  
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/fileUpload/fileUpload.js"></script> 
	
	<link href="<%=request.getContextPath()%>/static/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />

<title>编辑问题分类</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/question/editQuestionCategory" method="post"  class="form form-horizontal" id="form-category-edit">
		<input type="hidden" value="${data.categoryId }" name="categoryId">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>分类名：</label>
			<div class="formControls col-7">
				<input type="text" class="input-text" value="${data.categoryName }" placeholder="请输入分类名" id="categoryName" datatype="*" name="categoryName" nullmsg="分类名不能为空">
			</div>
			<div class="col-2"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">图标：</label>
			<div class="formControls col-7">
				<div class="uploader-thum-container" style="display:inline;">
					<div id="imageUpload"></div>
					<input type="hidden" name="icon" value="${data.icon }" id="icon"/>
				</div>
			</div>
			<div class="col-2"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">问题数量：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" value="${data.questionCount }" id="questionCount" datatype="n" name="questionCount" ignore="ignore" errormsg="只能为数字">
			</div>
			<div class="col-2"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">是否可见：</label>
			<div class="formControls col-2">
				<span class="select-box">
					<select id="isVisible" name="isVisible" class="select" size="1">
						<option value="0" <c:if test="${data.isVisible == 0 }">selected="selected"</c:if>>是</option>
						<option value="1" <c:if test="${data.isVisible == 1 }">selected="selected"</c:if>>否</option>
					</select>
				</span>
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
	$("#form-category-edit").Validform({
		tiptype : 2,
		callback : function(form) {

			TableUtils.saveUrl = $(form).attr("action");
			TableUtils.saveOpr("form-category-edit", function() {
				setTimeout(function() {
					var index = parent.layer.getFrameIndex(window.name);
					$("#search",parent.document).trigger("click");
					parent.layer.close(index);
				}, 2000);
			});

			return false;
		}
		
	});
	
	//图片上传类
	 $("#imageUpload").powerWebUpload({ 
		 innerOptions :{
			 formData : {folder : 'questionCategory'}, 
			 fileVal : 'files',
			 accept : {
		 			title : 'image',
		 			extensions : 'gif,jpg,jpeg,bmp,png',
		 			mimeTypes : 'image/*'
		 		}
		 },
		hiddenInputId: "icon" }); 
});

</script>
</body>
</html>