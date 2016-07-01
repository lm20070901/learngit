<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 
<title>添加管理员</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/system/addAdmin" method="post" class="form form-horizontal" id="form-admin-add">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>管理员：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" value="" placeholder="" id="user-name" name="userName" datatype="*2-16" nullmsg="用户名不能为空">
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>初始密码：</label>
			<div class="formControls col-5">
				<input type="password" placeholder="密码" autocomplete="off" value="" class="input-text" name="password" datatype="*6-20" nullmsg="密码不能为空">
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>确认密码：</label>
			<div class="formControls col-5">
				<input type="password" placeholder="确认新密码" autocomplete="off" class="input-text Validform_error" errormsg="您两次输入的新密码不一致！" datatype="*" nullmsg="请再输入一次新密码！" recheck="password" id="newpassword2" name="newpassword2">
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">角色：</label>
			<div class="formControls col-5"> <span class="select-box" style="width:150px;">
				<select class="select" name="roleId" size="1">
					<c:forEach var="role" items="${roleList}">
					<option value="${role.roleId}">${role.roleName}</option>
					</c:forEach>
				</select>
				</span> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">备注：</label>
			<div class="formControls col-5">
				<textarea name="mark" cols="" rows="" class="textarea"  placeholder="100个字符以内" dragonfly="true" onKeyUp="textarealength(this,100)"></textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
			</div>
			<div class="col-4"> </div>
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
	$("#form-admin-add").Validform({
		tiptype:2,
		callback:function(form){
			var index = layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
			$.ajax({
	            type:"post",
	            url:"<%=request.getContextPath()%>/system/addAdmin",
	            dataType:'json',
	            data: $('#form-admin-add').serialize(),
	            success:function(json) {
	            	layer.close(index);
	            	//var json = eval('('+json+')');
	            	if(json.result==1){
	            		layer.msg(json.msg,{icon:1,time:2000});
	            		setTimeout(function(){
	            			var index = parent.layer.getFrameIndex(window.name);
	            			parent.location.reload();
			    			parent.layer.close(index);
	            		},2000);
	            	}else{
	            		layer.msg(json.msg,{icon:1,time:2000});
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