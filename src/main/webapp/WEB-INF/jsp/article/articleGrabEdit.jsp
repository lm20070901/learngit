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
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/uploadify/jquery.uploadify.min.js"> </script> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/lib/uploadify/uploadify.css" /> --%>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/webuploader/0.1.5/webuploader.nolog.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/fileUpload/fileUpload.js"></script>  
	
<link href="<%=request.getContextPath()%>/static/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />
<title>发布</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/articleGrab/publish" method="post" class="form form-horizontal" id="form-article-edit">
		<div style="display: none"><input type="text" id="contentId" name="contentId" value="${article.contentId}"/></div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>标题：</label>
			<div class="formControls col-7">
				<input type="text" class="input-text" id="title" name="title" value="${article.title }" datatype="*" nullmsg="标题不能为空">
			</div>
			<div class="col-3"> </div>
		</div>
		<div class="row cl">
		
			<%-- <label class="form-label col-2"><span class="c-red">*</span>分类：</label>
			<div class="formControls col-2">
				<span class="select-box">
			      <select class="select" size="1" name="categoryType">
			       <!--  <option value="" selected>请选择分类</option> -->
			        <c:forEach items="${cates}" var="item">
						<option value="${item.categoryId }" <c:if test="${item.categoryId == article.categoryId }">selected</c:if>>${item.categoryName}</option>
					</c:forEach>
			      </select>
			    </span>
			</div> 
			<div class="col-1"> </div>
			--%>
			<label class="form-label col-2">来源：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" placeholder="" value="${article.source }" id="source" name="source">
			</div>
			<div class="col-3"> </div>
		</div>
		<div class="row cl">
			<c:if test="${contentType != 3 }">
				<label class="form-label col-2">使用链接：</label>
				<div class="formControls col-7">
					<input type="text" autocomplete="off" class="input-text" datatype="url" ignore="ignore" id="url" value="${article.url }" name="url">
				</div>
			</c:if>
			<c:if test="${contentType == 3 }">
				<label class="form-label col-2">时长：</label>
				<div class="formControls col-2">
					<input type="text" class="input-text" placeholder="秒" datatype="n" ignore="ignore" errormsg="必须为数字" id="mediaTime" value="${article.mediaTime }" name="mediaTime">
				</div>
			</c:if>
			
			<div class="col-3"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>简介：</label>
			<div class="formControls col-7"> 
				<textarea name="introduce" cols="" rows="" class="textarea" placeholder="说点什么...最少输入10个字符" datatype="*10-500" nullmsg="备注不能为空！" onKeyUp="textarealength(this,500)">${article.introduce }</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/500</p>
			</div>
			<div class="col-3"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">作者：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" placeholder="" value="${article.author }" id="author" name="author">
			</div>
			<div class="col-1"> </div>
			
			<label class="form-label col-2">发布时间：</label>
			<div class="formControls col-2">
				<input type="text" readonly value="${article.strPublishTime }" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'publishDate\')||\'%y-%M-%d %H:%m:%s\'}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="publishDate" name="publishDate" class="input-text Wdate">
			</div>
			<div class="col-3"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-2">阅读量：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" placeholder="" value="${article.readTimes }" datatype="n" ignore="ignore" id="readTimes" name="readTimes">
			</div>
			<div class="col-1"> </div>
			
			<label class="form-label col-2">排序：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" placeholder="" value="${article.orderNum }" datatype="n" ignore="ignore" id="orderNum" name="orderNum">
			</div>
			<div class="col-3"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-2">关键词：</label>
			<div class="formControls col-7">
				<input type="text" class="input-text" placeholder="" value="${article.keywords }" id="keywords" name="keywords">
			</div>
			<div class="col-3"> </div>
		</div>
		
		<div class="row cl">
			
			<label class="form-label col-2"><span class="c-red">*</span>发布终端：</label>
			<div class="formControls col-2">
				<span class="select-box">
			      <select class="select" size="1" name="devType">
			        <option value="" selected>全部</option>
			        <option value="0">网站</option>
			        <option value="1">APP</option>
			      </select>
			    </span>
			</div>
			<div class="formControls skin-minimal col-4">
				<div class="check-box">
					<input type="checkbox" name="isComment" value="1" <c:if test="${article.isComment == 1 }">checked</c:if>>
					<label for="checkbox-5">允许评论</label>
				</div>
				<div class="check-box">
					<input type="checkbox" name="isTop" value="1" <c:if test="${article.isTop == 1 }">checked</c:if>>
					<label for="checkbox-6">是否置顶</label>
				</div>
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">缩略图:</label>
			<div class="formControls col-6"> 
				<div class="uploader-thum-container" style="display:inline;">
					<div id="imageUpload"></div>
					<input type="hidden" name="picUrl" value="${article.picUrl}" id="picUrl"/>
				</div>
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<c:if test="${contentType != 3 }">
				<label class="form-label col-2">正文内容：</label>
				<div class="formControls col-10"> 
					<script id="content" name="content" type="text/plain" style="width:100%;height:400px;">
					${article.content }
				</script> 
				</div>
			</c:if>
			<c:if test="${contentType == 3 }">
				<label class="form-label col-2">视频文件:</label>
				<div class="formControls col-6">
					<div id="mediaUpload"></div>
					<input type="text" name="mediaUrl" style="display: none;" id="mediaUrl"  value="${article.fileUrl}">
				</div>
			</c:if>
		</div>
		<div class="row cl">
			<div class="col-9 col-offset-3">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;发布&nbsp;&nbsp;" onclick="publish_grab(1)">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;不通过&nbsp;&nbsp;" onclick="publish_grab(3)">
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">

//发布
function publish_grab(flag){
	var msg = "不通过发布";
	if(flag == 1){
		msg = "发布";
	}
	layer.confirm("确认要"+msg+"吗？",function(index){
		$.ajax({
	        type:"post",
	        url:"<%=request.getContextPath()%>/articleGrab/publish?flag=" + flag ,
	        dataType:'json',
	        data: $('#form-article-edit').serialize(),
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
 	 });
}

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