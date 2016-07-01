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
	<form action="<%=request.getContextPath()%>/vote/saveVote" method="post" class="form form-horizontal" id="form-vote-edit" enctype="multipart/form-data" >
		<input type="hidden" name="voteId" value="${vote.voteId}"/>
		<input type="hidden" name="voteType"  value="${voteType}"/>
		<div class="row cl">
				<!--${admin.userId}  -->
				<label class="form-label col-2"><span class="c-red">*</span>问卷标题：</label>
				<div class="formControls col-5">
					<input value="${vote.voteTitle}" name="voteTitle"  class="input-text" datatype="*2-30" nullmsg="标题不能为空"  />
				</div>
				<div class="col-4"></div> 
		</div>
		
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>问卷介绍：</label>
			<div class="formControls col-5">
				<textarea name="voteInfo" cols="" rows="" class="textarea"  placeholder="140个字符以内" datatype="*5-140"   nullmsg="问卷介绍不能为空"  dragonfly="true" onKeyUp="textarealength(this,140)">${vote.voteInfo}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">5</em>/140</p>
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
				
				<label class="form-label col-2"><span class="c-red">*</span>是否需要邀请：</label>
				<div class="formControls  col-5 radio-box">
					<input name="isInvite" type="radio" value="1" <c:if test="${vote.isInvite==1}">checked</c:if> id="isInvite" /><label for="isInvite">是</label>   &nbsp;&nbsp;&nbsp;
					<input name="isInvite" type="radio" <c:if test="${empty vote||empty vote.isInvite ||  vote.isInvite==0 }">checked</c:if> value="0" id="noInvite" /><label for="noInvite">否</label>
				</div>
				
				<div class="col-4"> </div>
		</div>
		<div class="row cl">
				
				<label class="form-label col-2"><span class="c-red">*</span>是否公开结果：</label>
				<div class="formControls  col-5 radio-box">
					<input name="resultVisible" type="radio" value="1" <c:if test="${vote.resultVisible==1}">checked</c:if> id="yes" /><label for="yes">是</label>   &nbsp;&nbsp;&nbsp;
					<input name="resultVisible" type="radio" <c:if test="${empty vote||empty vote.isInvite || vote.resultVisible==0}">checked</c:if> value="0" id="no" /><label for="no">否</label>
				</div>
				
				<div class="col-4"> </div>
		</div>
		
		<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>截止时间：</label>
				<div class="formControls col-5">
				 <c:if test="${!empty vote.lastTime}"> 
				 <input type="text" onfocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="lastTime"  name="lastTime" value="<date:date value='${vote.lastTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" class="input-text Wdate" style="width:200px;">
				 </c:if>    
				 <c:if test="${empty vote.lastTime}"> 
				 	<input type="text" onfocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="lastTime"  name="lastTime" value="" class="input-text Wdate"  style="width:200px;" >
				 </c:if> 
				  
				</div>
	    </div>
		<div class="row cl">
			<div class="col-9 col-offset-5">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
				&nbsp;&nbsp;&nbsp;
				<input class="btn btn-warning-outline radius" type="button" value="取消" onclick="cancle();">
			</div> 
		</div>
	</form>
	<script type="text/javascript">
  		//console.log("测试");
	    var path = '<%=request.getContextPath()%>';
	    
		$("#form-vote-edit").Validform({
			tiptype : 2,
			ajaxPost:false,//不要采用默认的ajax
			callback : function(form) {

				TableUtils.saveUrl = path + "/vote/saveVote";
				TableUtils.saveOpr("form-vote-edit",function (returnData){
					window.location.href= path + "/vote/toEditVote?voteType=${voteType}&voteId="+returnData.data.voteId;
				});

				return false;
			}
		});
		
		//取消
		function cancle() {
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭  
		}
	</script>
	<!-- <div style="margin: 0px;height: 580px;width:960px;">-->
				<iframe
				src="<%=request.getContextPath()%>/vote/toVoteQuestionList?voteId=${vote.voteId}"
				width="48%" height="100%" frameborder="0" scrolling="no"
				style="float:left">
	<!--</div> -->
			<!-- 先保存才能进行操作 -->
			<%-- <%@ include file="voteQuestionList.jsp"%>  --%>

			
	</div>

</body>
</html>