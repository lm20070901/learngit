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
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/webuploader/0.1.5/webuploader.nolog.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/fileUpload/fileUpload.js"></script>  
	
<link href="<%=request.getContextPath()%>/static/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />
<title>添加资讯</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/article/addArticle" method="post" enctype="multipart/form-data" class="form form-horizontal" id="form-article-add">
		<div style="display: none"><input type="text" id="contentType" name="contentType" value="${contentType}"/></div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>标题：</label>
			<div class="formControls col-7">
				<input type="text" class="input-text" value="" placeholder="" id="title" datatype="*" name="title" nullmsg="标题不能为空">
			</div>
			<div class="col-3"> </div>
		</div>
		<div class="row cl">
			<%-- <c:if test="${contentType == 0 }"> --%>
			<label class="form-label col-2"><span class="c-red">*</span>分类：</label>
			<div class="formControls col-2">
				<span class="select-box">
			      <select class="select" size="1" name="categoryType">
			        <!-- <option value="" selected>请选择分类</option> -->
			        <c:forEach items="${cates}" var="item">
						<option value="${item.categoryId }" <c:if test="${item.categoryId == category }">selected</c:if>>${item.categoryName}</option>
					</c:forEach>
			      </select>
			    </span>
			</div>
			<div class="col-1"> </div>
			<%-- </c:if> --%>
			
			<label class="form-label col-2">来源：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" value="" placeholder="" id="source" name="source">
			</div>
			
			<div class="col-3"> </div>
		</div>
		
		<div class="row cl">
			<c:if test="${contentType != 3 }">
				<label class="form-label col-2">使用链接：</label>
				<div class="formControls col-7">
					<input type="text" autocomplete="off" class="input-text" datatype="url" ignore="ignore" errormsg="网址格式错误" id="url" name="url">
				</div>
			</c:if>
			<c:if test="${contentType == 3 }">
				<label class="form-label col-2">时长：</label>
				<div class="formControls col-2">
					<input type="text" class="input-text" placeholder="秒" datatype="n" ignore="ignore" errormsg="必须为数字" id="mediaTime" name="mediaTime">
				</div>
			</c:if>
			<div class="col-3"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>简介：</label>
			<div class="formControls col-7"> 
				<textarea name="introduce" cols="" rows="" class="textarea"  placeholder="说点什么...最少输入10个字符" datatype="*10-500" nullmsg="备注不能为空！" onKeyUp="textarealength(this,500)"></textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/500</p>
			</div>
			<div class="col-3"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">作者：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" value="" placeholder="" id="author" name="author">
			</div>
			<div class="col-1"> </div>
			
			<label class="form-label col-2">发布时间：</label>
			<div class="formControls col-2">
				<input type="text" readonly onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'publishDate\')||\'%y-%M-%d %H:%m:%s\'}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="publishDate" name="publishDate" class="input-text Wdate">
			</div>
			<div class="col-3"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-2">阅读量：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" value="" datatype="n" errormsg="必须为数字" ignore="ignore" id="readTimes" name="readTimes">
			</div>
			<div class="col-1"> </div>
			<label class="form-label col-2">排序：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" value="" placeholder="" datatype="n" errormsg="必须为数字" ignore="ignore" id="orderNum" name="orderNum">
			</div>
			<div class="col-3"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-2">关键词：</label>
			<div class="formControls col-7">
				<input type="text" class="input-text" value="" placeholder="" id="keywords" name="keywords">
			</div>
			<div class="col-3"> </div>
		</div>
		
		<div class="row cl">
			
			<label class="form-label col-2"><span class="c-red">*</span>发布终端：</label>
			<div class="formControls col-2">
				<span class="select-box">
			      <select class="select" size="1" name="devType">
			        <option value="0" selected>全部</option>
			        <option value="1">网站</option>
			        <option value="2">APP</option>
			      </select>
			    </span>
			</div>
			<div class="formControls skin-minimal col-4">
				<div class="check-box">
					<input type="checkbox" name="isComment" value="1">
					<label for="checkbox-5">允许评论</label>
				</div>
				<div class="check-box">
					<input type="checkbox" name="isTop" value="1">
					<label for="checkbox-6">是否置顶</label>
				</div>
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">缩略图:</label>
			<div class="formControls col-6"> <!-- <span class="btn-upload form-group">
				<input class="input-text upload-url" type="text" name="fileName" id="fileName" readonly style="width:200px">
				<a href="javascript:void();" class="btn btn-primary upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a> -->
				<!-- <input type="file" name="file_upload" id="file_upload" class="btn-primary radius">
				<input type="text" name="picUrl" style="display: none;"> -->
				<!-- </span>  -->
				
				<!-- <input type="file" name="file_upload" id="file_upload" class="btn-primary radius">
				<input type="text" name="picUrl" style="display: none;"> -->
				
				<div class="uploader-thum-container" style="display:inline;">
					<!-- <div id="fileList" class="uploader-list" "></div> -->
					<!-- <div id="filePicker" >选择图片</div> -->
					<div id="imageUpload"></div>
					<input type="hidden" name="picUrl" value="" id="picUrl"/>
				</div>
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<c:if test="${contentType != 3 }">
				<label class="form-label col-2">正文内容：</label>
				<div class="formControls col-10"> 
					<script id="content" name="content" type="text/plain" style="width:100%;height:400px;"></script> 
				</div>
			</c:if>
			<c:if test="${contentType == 3 }">
				<label class="form-label col-2">视频文件:</label>
				<div class="formControls col-6">
					<!-- <input type="file" name="media_upload" id="media_upload" class="btn-primary radius"> -->
					<div id="mediaUpload"></div>
					<input type="text" name="mediaUrl" style="display: none;" id="mediaUrl">
				</div>
			</c:if>
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
	var contentType = ${contentType};
	var path;
	if(contentType == 0) {
		path = "<%=request.getContextPath()%>/article/addFinanceArticle";
	} else if(contentType == 1) {
		path = "<%=request.getContextPath()%>/article/addResearchArticle";
	} else if(contentType == 2) {
		path = "<%=request.getContextPath()%>/article/addExpertArticle";
	} else if(contentType == 3) {
		path = "<%=request.getContextPath()%>/article/addVideoArticle";
	}
	$("#form-article-add").Validform({
		tiptype:2,
		callback:function(form){
			var index = layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
			
			$.ajax({
	            type:"post",
	            url:path,
	            dataType:'json',
	            data: $('#form-article-add').serialize(),
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

	var ue = UE.getEditor('content');
	//上传视频 
	 $("#mediaUpload").powerWebUpload({ 
		 innerOptions :{
			 formData : {folder : 'article'}, 
			 fileVal : 'files',
			 accept : {
		 			title : 'file',
		 			extensions : 'mp4,mp3,avi,mov',
		 			mimeTypes : 'video/*'
		 		}
		 },
 		hiddenInputId: "mediaUrl" }); 
	 //上传图片并显示缩略图 
	 $("#imageUpload").powerWebUpload({ 
		 innerOptions :{
			 formData : {folder : 'article'}, 
			 fileVal : 'files',
			 accept : {
		 			title : 'image',
		 			extensions : 'gif,jpg,jpeg,bmp,png',
		 			mimeTypes : 'image/*'
		 		}
		 },
 		hiddenInputId: "picUrl" }); 
	

</script>
</body>
</html>