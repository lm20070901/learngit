<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags" prefix="date"%> 
<html>
<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
	
<title>创建问卷调查</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/vote/createInviteCode" method="post" class="form form-horizontal" id="form-invitecode-create" enctype="multipart/form-data" >
		<input type="hidden" name="voteId" value="${voteId}"/>
		<div class="row cl">
				<!--${admin.userId}  -->
				<label class="form-label col-2"><span class="c-red">*</span>生成个数：</label>
				<div class="formControls col-3">
					<input value="" name="inviteNum" placeholder="100个以内" class="input-text" datatype="n1-2" nullmsg="填写个数"  />
				</div>
				<div class="col-4"></div> 
		</div>
		<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>截止时间：</label>
				<div class="formControls col-3">
				 <input type="text" onfocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="timeLine"  name="timeLine" value="" class="input-text Wdate" style="width:200px;">
				</div>
				<div class="col-3" id="dataMsg" style="margin-left:15px;"></div> 
	    </div>
		<div class="row cl">
			<div class="col-7 col-offset-5">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;确认&nbsp;&nbsp;">
				&nbsp;&nbsp;&nbsp;
				<input class="btn btn-warning-outline radius" type="button" value="取消" onclick="cancle();">
			</div> 
		</div>
	</form>
	
	</div>

</body>
<script type="text/javascript">
  		//console.log("测试");
	    var path = '<%=request.getContextPath()%>';
	    
		$("#form-invitecode-create").Validform({
			tiptype : 2,
			ajaxPost:false,//不要采用默认的ajax
			callback : function(form) {
				var timeLine = $("#timeLine").val();
				$("#dataMsg").empty();
				if(!timeLine || timeLine == ''){
					
					$("#dataMsg").append('<span class="Validform_checktip Validform_wrong">请输入截止时间!</span>');
					return false;
				}else{
					$("#dataMsg").append('<span class="Validform_checktip Validform_right"></span>');
				}
				
				TableUtils.saveUrl = "<%=request.getContextPath()%>/vote/createInviteCode";
				TableUtils.saveOpr("form-invitecode-create");

				return false;
			}
		});
		
		//取消
		function cancle() {
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭  
		}
</script>
</html>