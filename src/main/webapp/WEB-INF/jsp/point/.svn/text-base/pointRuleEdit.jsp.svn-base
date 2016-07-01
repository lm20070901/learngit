<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 
	
<title>编辑积分规则</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/point/editPointRule" method="post"  class="form form-horizontal" id="form-pointRule-edit">
		<input type="hidden" value="${data.ruleId }" name="ruleId">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>动作名称：</label>
			<div class="formControls col-4">
				<input type="text" class="input-text" value="${data.ruleName }" id="ruleName" datatype="*" name="ruleName" nullmsg="动作名称不能为空">
			</div>
			<div class="col-2"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">奖励周期：</label>
			<div class="formControls col-2">
				<span class="select-box">
					<select id="period" name="period" class="select" size="1">
						<c:forEach items="${periods }" var="item">
							<option value="${item.key }" <c:if test="${data.period == item.key }">selected="selected"</c:if>>${item.value }</option>
						</c:forEach>
					</select>
				</span>
			</div>
			<div class="col-2"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>奖励次数：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" value="${data.times }" id="times" datatype="n" name="times" errormsg="只能为数字" nullmsg="不能为空">
			</div>
			<div class="col-2"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>获得积分：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" value="${data.point }" id="point" datatype="n" name="point" errormsg="只能为数字" nullmsg="不能为空">
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
	$("#form-pointRule-edit").Validform({
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