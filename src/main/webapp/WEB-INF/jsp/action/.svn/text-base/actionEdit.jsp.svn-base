<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags" prefix="date"%> 
<html>
<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
	
<title>编辑菜单</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/action/saveAction" method="post" class="form form-horizontal" id="form-action-edit" enctype="multipart/form-data" >
		<input type="hidden" name="actionId" value="${actionId}"/>
		<input type="hidden" name="pid" value="<c:if test='${empty pid}'>${action.pid}</c:if><c:if test='${!empty pid}'>${pid}</c:if>"/>
		<div class="row cl">
				<!--${admin.userId}  -->
				<label class="form-label col-2"><span class="c-red">*</span>菜单名称：</label>
				<div class="formControls col-5">
					<input value="${action.actionName}" name="actionName"  class="input-text" datatype="*2-30" nullmsg="菜单名称不能为空"  />
				</div>
				<div class="col-4"></div> 
		</div>
		<div class="row cl">
				<!--${admin.userId}  -->
				<label class="form-label col-2"><span class="c-red">*</span>访问路径：</label>
				<div class="formControls col-5">
					<input value="${action.actionUrl}" name="actionUrl"  class="input-text" datatype="*2-200" nullmsg="访问路径不能为空"  />
				</div>
				<div class="col-4"></div> 
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>备注：</label>
			<div class="formControls col-5">
				<textarea name="mark" cols="" rows="" class="textarea"  placeholder="30个字符以内" datatype="*1-30"   nullmsg="备注不能为空"  dragonfly="true" onKeyUp="textarealength(this,30)">${action.mark}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">1</em>/30</p>
			</div>
			<div class="col-4"> </div>
		</div>
		
		<div class="row cl">
			<div class="col-7 col-offset-5">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
				&nbsp;&nbsp;&nbsp;
				<input class="btn btn-warning-outline radius" type="button" value="取消" onclick="cancle();">
			</div> 
		</div>
	</form>
	<script type="text/javascript">
  		
	    var path = '<%=request.getContextPath()%>';
	   
	    
		$("#form-action-edit").Validform({
			tiptype : 2,
			ajaxPost:false,//不要采用默认的ajax
			callback : function(form) {

				TableUtils.saveUrl = path + "/action/saveAction";
				TableUtils.saveOpr("form-action-edit",function (returnData){
					//var actionId = 	$("input[name=actionId]").val();
					parent.callBack(returnData.data);
					window.setTimeout(function (){
						layer_close();
					},2000)
				});

				return false;
			}
		});
		//取消
		function cancle() {
			/* var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭   */
			layer_close();
		}
	</script>
	
			
	</div>

</body>
</html>