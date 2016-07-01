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
	<form action="<%=request.getContextPath()%>/project/saveProject" method="post" class="form form-horizontal" id="form-project-edit" enctype="multipart/form-data" >
		<input type="hidden" name="projectId"  value="${projectId}"/>
		<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>项目名称：</label>
				<div class="formControls col-5">
					<input value="${project.projectName}" name="projectName"  class="input-text" datatype="*2-30" nullmsg="项目名称不能为空"  />
				</div>
				<div class="col-4"></div> 
		</div>
		
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>所属行业：</label>
			<div class="formControls col-5">
				<span class="select-box" > 
				<select class="select" id="industry" name="industry">
						<option value="" selected>全部</option>
						<c:forEach items="${industryList}" var="item">
							<option value="${item.code}" <c:if test="${project.industry==item.code}">selected </c:if>>${item.name}</option>
						</c:forEach>
				</select>
			</span>
			</div>
			<div class="col-4"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>所属地区：</label>
			<div class="formControls col-5">
				<span class="select-box"  > 
				<select class="select" id="areaId" name="areaId">
						<option value="" selected>全部</option>
						<c:forEach items="${districtList}" var="item">
							<option value="${item.id}" <c:if test="${project.industry==item.id}">selected </c:if>>${item.name}</option>
						</c:forEach>
				</select>
			</span>
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>所属阶段：</label>
			<div class="formControls col-5">
				<span class="select-box" > 
				<select class="select" id="projectStage" name="projectStage">
						<option value="" selected>全部</option>
						<c:forEach items="${projectStageList}" var="item">
							<option value="${item.code}" <c:if test="${project.industry==item.code}">selected </c:if>>${item.name}</option>
						</c:forEach>
				</select>
			</span>
			</div>
			<div class="col-4"> </div>
		</div>
		
		<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>项目估值：</label>
				<div class="formControls col-5">
					<input value="${project.projectValuation}" name="projectValuation"  class="input-text" datatype="double" nullmsg="项目估值不能为空"  />
				</div>
				<div class="col-4">万&nbsp;&nbsp;&nbsp;</div>
		</div>
		
		<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>项目负责人：</label>
				<div class="formControls col-5">
					<input value="${project.projectHead}" name="projectHead"  class="input-text" datatype="*2-30" nullmsg="项目负责人不能为空"  />
				</div>
				<div class="col-4"></div> 
		</div>
		
		<div class="row cl">
				<!--${admin.userId}  -->
				<label class="form-label col-2"><span class="c-red">*</span>联系方式：</label>
				<div class="formControls col-5">
					<input value="${project.phoneNum}" name="phoneNum"  class="input-text" datatype="m|e|s6-16" nullmsg="联系方式不能为空"  />
				</div>
				<div class="col-4"></div> 
		</div>
		
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>项目介绍 ：</label>
			<div class="formControls col-5">
				<textarea name="projcetIntroduce" cols="" rows="" class="textarea"  placeholder="140个字符以内" datatype="*1-140"   nullmsg="项目介绍 不能为空"  dragonfly="true" onKeyUp="textarealength(this,140)">${project.projcetIntroduce}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">1</em>/140</p>
			</div>
			<div class="col-4"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>团队介绍：</label>
			<div class="formControls col-5">
				<textarea name="teamIntroduce" cols="" rows="" class="textarea"  placeholder="140个字符以内" datatype="*1-140"   nullmsg="团队介绍不能为空"  dragonfly="true" onKeyUp="textarealength(this,140)">${project.teamIntroduce}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">1</em>/140</p>
			</div>
			<div class="col-4"> </div>
		</div>
		
		<div class="row cl">
			<div class="col-7 col-offset-5">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
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
	    
		$("#form-project-edit").Validform({
			tiptype : 2,
			ajaxPost:false,//不要采用默认的ajax
			datatype:{
			   "double":/^\d{1,10}.\d{0,3}$/
			},
			callback : function(form) {

				TableUtils.saveUrl = path + "/project/saveProject";
				TableUtils.saveOpr("form-project-edit",function (returnData){
					window.setTimeout(function (){
						parent.location.reload();
						layer_close();
					},2000)
				});

				return false;
			}
		});
		
		//取消
		function cancle() {
			layer_close();
		}
</script>
</html>