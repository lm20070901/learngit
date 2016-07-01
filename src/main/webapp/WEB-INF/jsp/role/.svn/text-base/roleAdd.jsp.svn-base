<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript"  src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
	<title>新建角色 </title>
</head>

<body>
		<div class="pd-20">
	<form action="<%=request.getContextPath()%>/role/saveRole" method="post" class="form form-horizontal" id="form-role-add">
			<div class="row cl">
			
				<label class="form-label col-4">角色名：</label>
				<div class="formControls col-3">
					<input value="" name="roleName"  class="input-text" />
				</div>
				<div class="col-4"></div> 
			</div>
			<div class="row cl">
				<label class="form-label col-4">备注：</label>
				<div class="formControls col-5">
					<textarea name="mark" cols="" rows="" class="textarea"  placeholder="25个字符以内" dragonfly="true" onKeyUp="textarealength(this,25)"></textarea>
					<p class="textarea-numberbar"><em class="textarea-length">0</em>/25</p>
				</div>
				<div class="col-4"> </div>
			</div>
			<div class="row cl">
			<div class="col-offset-5">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
				&nbsp;&nbsp;&nbsp;<input class="btn btn-warning-outline radius" type="button" value="取消" onclick="cancle();">
			</div>
		</div>
		</form>
</div>


<script type="text/javascript">
		
	
	  $("#form-role-add").Validform({
		tiptype:2,//提示方式
		ajaxPost:false,
		callback:function(data){//提交表单的回调函数
			TableUtils.saveUrl =  "<%=request.getContextPath()%>/role/saveRole";
			TableUtils.saveOpr("form-role-add");

			return false;
		}
		
	});
	
	  function cancle() {
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭  
	}
	
</script>
</body>
</html>
