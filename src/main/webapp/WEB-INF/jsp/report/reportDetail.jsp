<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 

<title>举报详情</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/groups/editHotGroups" method="post"  class="form form-horizontal" id="form-news-edit">
		<div class="row cl">
		<fieldset>
			<legend>举报详情</legend>
			<div class="row cl">
				<input type="text" value="${report.contentId}" style="display: none;" name="contentId" id="contentId">
				<input type="text" value="${report.id}" style="display: none;" name="id" id="id">
				<input type="text" value="${report.isHandel}" style="display: none;" name="isHandel" id="isHandel">
				<input type="text" value="${report.contentCategory}" style="display: none;" name="contentCategory" id="contentCategory">
				<c:if test="${empty data}">
					<input type="text" value="0" style="display: none;" name="data" id="data">
				</c:if>
			</div>
			<div class="row cl">
				<label class="form-label col-2">举报内容类型：</label>
				<div class="formControls col-3">
					<input type="text" class="input-text" value="${report.strcontentCategory }" readonly="readonly">
				</div>
				<label class="form-label col-2">举报原因：</label>
				<div class="formControls col-3">
					<c:if test="${report.reportType == 1}">
						<input type="text" class="input-text" value="内容非法" readonly="readonly">
					</c:if>
					<c:if test="${report.reportType == 2}">
						<input type="text" class="input-text" value="色情" readonly="readonly">
					</c:if>
				</div>
			</div>
			<c:if test="${report.contentCategory != 9 }">
			<div class="row cl">
				<label class="form-label col-2">详细内容：</label>
				<div class="formControls col-7"> 
				<!-- <textarea name="commentBody" cols="" rows="" class="textarea" readonly="readonly" > -->
				<c:if test="${report.contentCategory == 1 || report.contentCategory == 3 || report.contentCategory == 4 || report.contentCategory == 5}">
					${data.content }
				</c:if>
				<c:if test="${report.contentCategory == 2 || report.contentCategory == 6 || report.contentCategory == 8}">
					${data.commentBody }
				</c:if>
				<c:if test="${report.contentCategory == 7 }">
					${data.contentBody }
				</c:if>
				<!-- 
				</textarea> -->
				</div>
				<div class="col-3"> </div>
			</div>
			</c:if>
			<c:if test="${report.contentCategory == 9 }">
				<div class="row cl">
				<label class="form-label col-2">用户账号：</label>
				<div class="formControls col-3">
					<input value="${data.userName}" name="userName" readonly="readonly" class="input-text" />
				</div>
 			 <label class="form-label col-2">用户ID：</label>
				<div class="formControls col-3">
					<input value="${data.userId}" name="userId" readonly="readonly" class="input-text" />
				</div>
				<div class="col-1"></div> 
			</div>
			<div class="row cl">
				<label class="form-label col-2">用户昵称：</label>
				<div class="formControls col-2">
					<input  value="${data.nickName}" name="nickName"  readonly="readonly"  autocomplete="off" class="input-text" /> 
				</div>
				<div class="col-1"> </div>
				<label class="form-label col-2">性别：</label>
				<div class="formControls  col-2 radio-box">
					<input name="userGender" type="radio" value="1" <c:if test="${data.userGender==1}">checked</c:if> id="man" /><label for="man">男</label>   &nbsp;&nbsp;&nbsp;
					<input name="userGender" type="radio" <c:if test="${data.userGender==0}">checked</c:if> value="0" id="woman" /><label for="woman">女</label>
				</div>
				
				<div class="col-4"> </div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">用户邮箱：</label>
				<div class="formControls col-2">
					<input  value="${data.authEmail}" name="authEmail" readonly="readonly"  class="input-text" /> 
				</div>
				<div class="col-2"> </div>
				<label class="form-label col-1">注册时间：</label>
				<div class="formControls col-3">
				   <input type="text"  readonly="readonly" name="signUpDate_show" value="<date:date value='${data.signUpDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" class="input-text Wdate" style="width:200px;">
				   <span></span>
				</div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-2">注册IP：</label>
				<div class="formControls col-3">
					<input  value="${user.regIp}" name="regIp" autocomplete="off" class="input-text"   readonly="readonly"/> 
				</div>
				<label class="form-label col-2">文章数量：</label>
				<div class="formControls col-3">
				    <input  value="${user.msgNum}" name="msgNum" autocomplete="off" class="input-text"   readonly="readonly"/> 
				</div>
			<div class="col-4"> </div>
			</div>
			</c:if>
		</fieldset>
		</div>
		<div class="row cl">
			<div class="col-9 col-offset-3">
				<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;删除&nbsp;&nbsp;" name="btn_del" onclick="updateState(1)">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<c:if test="${report.contentCategory != 9 }">
					<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;隐藏&nbsp;&nbsp;" name="btn_hide" onclick="updateState(0)">
				</c:if>
				<c:if test="${report.contentCategory == 9 }">
					<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;锁定&nbsp;&nbsp;" name="btn_hide" onclick="updateState(0)">
				</c:if>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
window.onload = function() { 
	var data = $("#data").val();
	if(data == 0){
		layer.msg("该举报内容已删除！",{icon:1,time:2000});
		return;
	}
};

function updateState(type){
	var isHandel = $("#isHandel").val();
	if(isHandel == 1){
		layer.msg("该举报内容已处理！",{icon:1,time:2000});
		return;
	}
	var msg = "删除";
	var id = $("#id").val();
	var contentId = $("#contentId").val();
	var contentCategory = $("#contentCategory").val();
	if(type==0){
		msg = "隐藏";
		if(contentCategory == 9){
			msg = "锁定";
		}
	}
	layer.confirm("确认要"+msg+"该内容吗？",function(index){
		$.ajax({
			type:"post",
			url:"<%=request.getContextPath()%>/report/updateState",
			dataType:"json",
			data:{"id":id,
				"contentId":contentId,
				"contentCategory":contentCategory,
				"flag":type},
			success:function(data) {
				layer.close(index);
				layer.msg(data.msg,{icon:1,time:2000});
            	if(data.result == 1) {
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
$(function(){
	$("#form-news-edit").Validform({
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
});


</script>
</body>
</html>