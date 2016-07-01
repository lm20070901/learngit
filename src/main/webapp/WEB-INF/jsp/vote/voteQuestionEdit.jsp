<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags" prefix="date"%> 
<html>
<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/table/common.js"></script> 
	
<title>新增题目</title>
</head>
<body>
<div class="pd-20">
	<form action="<%=request.getContextPath()%>/vote/saveVoteQuestion" method="post" class="form form-horizontal" id="form-votequestion-edit" enctype="multipart/form-data" >
		<input type="hidden" name="questionId" value="${questionId}"/>
		<input type="hidden" name="voteId"  value="${voteId}"/>
		
		<div class="row cl">
				<label class="form-label col-4"><span class="c-red">*</span>题目类型：</label>
				<div class="formControls col-4">
					<span class="select-box" style="width:150px;">
					<!-- <c:if test="${!empty questionId}">disabled</c:if> -->
					<select class="select" name="questionType" size="1" id="select_type"  >
							<option value="-1" >请选择</option>
							<option value="0" <c:if test="${question.type==0}">selected</c:if>>单选题</option>
							<option value="1" <c:if test="${question.type==1}">selected</c:if>>多选题</option>
							<option value="2" <c:if test="${question.type==2}">selected</c:if>>填空题</option>
					</select> 
					</span>
				</div>
				<div class="col-3" id="questionType_msg"></div> 
		</div>
		
		<div class="row cl">
				<label class="form-label col-4"><span class="c-red">*</span>题目标题：</label>
				<div class="formControls col-4">
					<input value="${question.title}" name="title"  class="input-text"  datatype="*2-100" nullmsg="题目标题不能为空" />
				</div>
				<div class="col-3"></div> 
		</div>
		<div class="row cl" <c:if test="${(empty question.questionId) || (question.type==2)}">style='display:none;'"</c:if> id='option_label'>
		            <label class="form-label col-4"><span class="c-red">*</span> 选项</label>
					<label class="form-label col-4">
						<a href="javascript:;" onclick="addOption();" class="btn btn-primary  radius">
							<i class="Hui-iconfont"></i> 增加选项
						</a>
					</label>
				<div class="col-3" id="options_msg"></div> 
		</div>
		<div id="options">
		<c:forEach var="option" items="${options}" varStatus="status">
		<div class="row cl">
				<label class="form-label col-4">${status.index+1}</label>
				<div class="formControls col-4" >
					 <input value="${option.optionBody}" name="options"  class="input-text" datatype="*1-20" nullmsg="选项内容不能为空" />
				</div>
				<label class="form-label col-1">
				<a title="移除" href="javascript:;" onclick="remove_option(this,${option.id})" class="ml-5" style="text-decoration:none">
		        <i class="Hui-iconfont">&#xe6e2;</i>
		        </a>
		        </label>
			<div class="col-3"></div> 
		</div>
		</c:forEach>
		</div> 
		<div class="row cl">
			<div class="col-7 col-offset-5">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div> 
		</div>
	</form>
</div>
<script type="text/javascript">


var path = '<%=request.getContextPath()%>';

$("#form-votequestion-edit").Validform({
	tiptype : 2,
	beforeCheck: function(curform){
		var type = $("#select_type").val(); 
		
		
		if(type<0){
			//
			//$("#questionType_msg").append('<span class="Validform_checktip Validform_wrong">题目类型不能为空</span>');
			layer.msg("请选择题目类型！",{icon:1,time:2000});
			return  false;
		}else{
			//$("#questionType_msg").empty();
			//$("#questionType_msg").append('<span class="Validform_checktip Validform_right">题目类型不能为空</span>');
		}
		//$("input[name='selectBox']")
		var optionsObj = $("input[name='options']");
		
		if(optionsObj.length<=0&&type!=2){
			//$("#options_msg").append('<span class="Validform_checktip Validform_wrong">请增加选项！</span>');
			layer.msg("请增加选项！",{icon:1,time:2000});
			return  false;
		}else{
			//$("#options_msg").empty();
			//$("#options_msg").append('<span class="Validform_checktip Validform_right"></span>');
		}
		for ( var i = 0; i < optionsObj.length; i++) {
			var val = $(optionsObj[i]).val();
			if (!val || $.trim(val )=='') {
				
				layer.msg("请输入选项"+(i+1)+"的内容！",{icon:1,time:2000});
				return  false;
			}
		}
		
	},
	callback : function(form) {
	
		TableUtils.saveUrl = path + "/vote/saveVoteQuestion";
		TableUtils.saveOpr("form-votequestion-edit");

		return false;
	}
});
//下拉框选中事件
$("#select_type").change(function(){
		
	    var type = $("#select_type").val(); 
	    if( type == 0 || type == 1){
	    	$("#option_label").show();
	    	$("#options").show();
	    	var index = $("#options").children("div").eq(-1).children("label").first().text();
	   
	    	if(!index||index.trim()==''){
	    		addOption();
	    	}
	    	
	    }else{
	    	$("#option_label").hide();
	    	$("#options").hide();
	    }
		
});   
function remove_option(obj,id){
		var optionDiv = $("#options").children("div");
		if(optionDiv.length<=0) {
			layer.msg("只有一个选项不允许删除！",{icon:1,time:2000});
			return;
		}
		if(!id || id=='')  {
			remove_row(obj);
			return;
		}
		//console.log("id="+id);
		id +="";
		var idArr = id.split(',');
		TableUtils.ajaxPost(path+"/vote/delteQuestionOptionsByIds",{'idList':idArr},function (){
			remove_row(obj);
			
		});
		
}

function remove_row(obj){
	var opr_obj =  $(obj).parent().parent();
	
	opr_obj.empty();
	opr_obj.remove();
	
	var optionDiv = $("#options").children("div");
	//var  index = $("#options").children("div").eq(-1).children("label").first().text();
	for(var i=0;i<optionDiv.length;i++){
		
		$(optionDiv[i]).children("label").first().text(i+1);
		
	}
}
//增加行
function addOption(){
	var index = $("#options").children("div").eq(-1).children("label").first().text();
	if(!index||index.trim()=='') {
		index = 1;
	}else{
		index = parseInt(index)+1;
	}
	if(index>9){
		layer.msg("选项不能超过9个！",{icon:1,time:2000});
		return;
	}
	//
	$("#options").append('<div class="row cl">'+
			'<label class="form-label col-4">'+index+'</label>'+
			'<div class="formControls col-4" >'+
			' <input value="" name="options"  class="input-text" datatype="*1-20" nullmsg="选项内容不能为空" />'+
			'</div><label class="form-label col-1">'+
			'<a title="移除" href="javascript:;" onclick="remove_option(this)" class="ml-5" style="text-decoration:none">'+
			   '<i class="Hui-iconfont">&#xe6e2;</i></a></label>'+
			'<div class="col-3"></div> </div>');
	
}


	
</script>
</body>
</html>