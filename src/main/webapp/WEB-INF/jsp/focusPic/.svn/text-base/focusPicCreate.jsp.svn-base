<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/webuploader/0.1.5/webuploader.nolog.js"></script>  
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/fileUpload/imageUpload.js"></script>  
	
	<link href="<%=request.getContextPath()%>/static/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />

<title>新增焦点图</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/focusPicture/addFocusPicture" method="post"  class="form form-horizontal" id="form-focusPic-add">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>标题：</label>
			<div class="formControls col-8">
				<input type="text" class="input-text" value="" placeholder="请输入标题" id="title" datatype="*" name="title" nullmsg="分类名不能为空">
			</div>
			<div class="col-2"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">外链地址：</label>
			<div class="formControls col-8">
				<input type="text" class="input-text" placeholder="请输入外链地址" id="relatedLink" name="relatedLink" dataType="url" errormsg="地址格式错误">
			</div>
			<div class="col-2"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">关联文章：</label>
			<div class="formControls col-8">
				<input type="text" name="articleId" id="articleId" style="display: none;">
				<input type="text" name="categoryId" id="categoryId" style="display: none;">
				<input type="text" class="input-text upload-url radius" readonly="readonly" placeholder="请选择关联文章" id="articleTitle">
				<input type="button" class="btn btn-primary radius" onclick="select_article('选择文章','<%=request.getContextPath()%>/focusPicture/selectLinkedArticle','800','500')" value="选择文章">
			</div>
			<div class="col-2"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-2">焦点图：</label>
			<div class="formControls col-8">
				<div id="uploader" class="wu-example">
					<!--用来存放文件信息-->
				    <div id="thelist" class="uploader-list"></div>
				    <div class="btns">
					<input type="text" class="input-text upload-url radius" id="linkDir" name="linkDir">
			        <div id="picker">选择文件</div>
			    </div>
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
	$("#form-focusPic-add").Validform({
		tiptype:2,
		callback:function(form){
			var index = layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
			
			$.ajax({
	            type:"post",
	            url:$(form).attr("action"),
	            dataType:'json',
	            data: $(form).serialize(),
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
	
	//图片上传类
	new imageUpload({useType:"focusPicture",
		filePicker:"picker",
		fileList:"thelist",
		uploadSuccess :function (json){
		/* var urls = $("#linkDir").val();
		
		urls = (urls && urls!='')?(urls+'|'+json.data.savePath):json.data.savePath;
		$("#linkDir").val(urls); */
		$("#linkDir").val(json.data.savePath);
	}});
});

/*选择关联文章*/
function select_article(title,url,w,h) {
	var index = layer.open({
		type: 2,
		title:title,
		maxmin:true,
		area:[w + 'px', h + 'px'],
		content:url
	});
	
	layer.full(index);
}
</script>
</body>
</html>