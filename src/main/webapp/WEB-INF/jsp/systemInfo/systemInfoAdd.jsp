<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/webuploader/0.1.5/webuploader.nolog.js"></script>  
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/fileUpload/fileUpload.js"></script>  
	
	<link href="<%=request.getContextPath()%>/static/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />
<title>发布系统消息</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/systemInfo/saveSystemInfo" method="post" class="form form-horizontal" id="form-systemInfo-add" enctype="multipart/form-data" >
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>消息内容：</label>
			<div class="formControls col-5">
				<textarea name="contentBody" cols="" rows="" class="textarea"  placeholder="140个字符以内" dragonfly="true" onKeyUp="textarealength(this,140)"></textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/140</p>
			</div>
			<div class="col-4"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-3">消息图片：</label>
			<div class="formControls col-5">
				<div class="uploader-thum-container" style="display:inline;">
					<!-- <div id="fileList" class="uploader-list"></div>
					<div id="filePicker" >选择图片</div> -->
					<div id="infoImage"></div>
					<input type="hidden" name="imageUrls" value="" id="imageUrls"/>
					<!--  <button id="btn-star" class="btn btn-default btn-uploadstar radius ml-10">开始上传</button> -->
				</div>
			</div>
		</div>   
		<div class="row cl">
			<div class="col-9 col-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>

	</div>
<script type="text/javascript">
$(function(){
	    var path = '<%=request.getContextPath()%>';
	    
		$("#form-systemInfo-add").Validform({
			tiptype : 2,
			callback : function(form) {

				TableUtils.saveUrl = path + "/systemInfo/saveSystemInfo";
				TableUtils.saveOpr("form-systemInfo-add");

				return false;
			}
		});
		//图片上传类
		 $("#infoImage").powerWebUpload({ 
			 innerOptions :{
				 formData : {folder : 'systemInfo'}, 
				 fileVal : 'files',
				 accept : {
			 			title : 'image',
			 			extensions : 'gif,jpg,jpeg,bmp,png',
			 			mimeTypes : 'image/*'
			 		}
			 },
	 		hiddenInputId: "imageUrls" }); 
	});
</script>
</body>
</html>