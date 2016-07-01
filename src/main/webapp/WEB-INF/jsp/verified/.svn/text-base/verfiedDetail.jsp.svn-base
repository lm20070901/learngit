<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="../head.jsp"%>
	<%-- <link href="<%=request.getContextPath()%>/static/css/selectStyle.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/static/css/selectReset.css" rel="stylesheet" type="text/css"/>  --%>
	<script type="text/javascript"  src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script>
	<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/select/select.js"></script>  --%>
	<title>认证详情</title>
</head>

<body>
		<div class="pd-20">
	<form action="<%=request.getContextPath()%>/verified/toVerfiedDetail" method="post" class="form form-horizontal" id="form-verfied-detail">
			
			<div class="row cl">
				<input type="hidden" name="id" value="${verified.id}" name="id"/>
				<label class="form-label col-3">用户昵称：</label>
				<div class="formControls col-3">
					<input  value="${verified.nickName}" name="nickName"  readonly="readonly" class="input-text"/> 
				</div>
				<div class="col-4"></div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-3">用户账号：</label>
				<div class="formControls col-3">
					<input value="${verified.userName}" name="userName" readonly="readonly" class="input-text" />
				</div>
				<div class="col-4"></div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-3">认证名称：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="${verified.realName}" readonly="readonly"  name="realName">
				</div>
				<div class="col-4"> </div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-3">电话号码：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="${verified.phonenum}" readonly="readonly" name="phonenum" >
				</div>
				<div class="col-4"> </div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-3">身份证号：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="${verified.idcard}" readonly="readonly" name="idcard" >
				</div>
				<div class="col-4"> </div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-3">联系地址：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="${verified.address}"  readonly="readonly" name="address" >
				</div>
				<div class="col-4"> </div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-3">申请时间：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="<c:if test="${! empty verified.applyTime}">
				      	<date:date value="${verified.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/> 
				      </c:if>"  name="applyTime" readonly="readonly" >
				</div>
				<div class="col-4"> </div>
			</div>
			
			
			<div class="row cl">
				<label class="form-label col-3">认证介绍：</label>
				<div class="formControls col-5">
					<textarea name="authinfo" cols="" rows="" class="textarea"   readonly="readonly" >${verified.authinfo}</textarea>
					<p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
				</div>
				<div class="col-4"> </div>
			</div>
			<c:choose>
				<c:when test="${verified.verifyType==1}">
					<div class="row cl">
						<label class="form-label col-3">关注行业：</label>
						<div class="formControls col-5">
						   <div class="check-box">
							  <input type="text" class="input-text" value="${verified.concernIndustry}"  readonly="readonly" name="concernIndustry" >
  							</div>
						</div>
						<div class="col-4"> </div>
					</div>
					
					<div class="row cl">
						<label class="form-label col-3">年收入：</label>
						<div class="formControls col-5">
						     <span class="select-box">
						       <select class="select" size="1" name="annualIncome"   disabled >
							        <option value="" selected></option>
							        <option value="0"  <c:if test="${verified.annualIncome==0}">selected</c:if>>20万以下</option>
							        <option value="1"  <c:if test="${verified.annualIncome==1}">selected</c:if>>20-50万</option>
							        <option value="2"  <c:if test="${verified.annualIncome==2}">selected</c:if>>50-100万</option>
							        <option value="3"  <c:if test="${verified.annualIncome==3}">selected</c:if>>100万以上</option>
						       </select>
    						</span>
						</div>
						<div class="col-4"> </div>
					</div>
					
					<div class="row cl">
						<label class="form-label col-3">个人资产：</label>
						<div class="formControls col-5">
						     <input type="text" class="input-text" value="${verified.assets}"  readonly="readonly" name="assets" >
						</div>
						<div class="col-4"> </div>
					</div>
				</c:when>
				<c:when test="${verified.verifyType==2}">
					<div class="row cl">
						<label class="form-label col-3">项目名字：</label>
						<div class="formControls col-5">
						     <input type="text" class="input-text" value="${verified.projectName}"  readonly="readonly" name="projectName" >
						</div>
						<div class="col-4"> </div>
					</div>
					<div class="row cl">
						<label class="form-label col-3">所属行业：</label>
						<div class="formControls col-5">
						     <input type="text" class="input-text" value="${verified.industry}"  readonly="readonly" name="industry" >
						</div>
						<div class="col-4"> </div>
					</div>
					<div class="row cl">
						<label class="form-label col-3">所属地区：</label>
						<div class="formControls col-5">
						     <input type="text" class="input-text" value="${verified.province}"  readonly="readonly" name="province" >
						</div>
						<div class="col-4"> </div>
					</div>
					<div class="row cl">
						<label class="form-label col-3">所属阶段：</label>
						<div class="formControls col-5">
						     <input type="text" class="input-text" value="${verified.projectStage}"  readonly="readonly" name="projectStage" >
						</div>
						<div class="col-4"> </div>
					</div>
				</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
			
			<div class="row cl">
				<label class="form-label col-3">证件照片：</label>
				<div class="formControls col-5">
					<img src="${verified.idcardImg1}" class="img-responsive" alt="身份证正面照">
					<img src="${verified.idcardImg2}" class="img-responsive" alt="身份证背面照">
				</div>
				<div class="col-4"> </div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-3">审核状态：</label>
				<div class="formControls col-5">
					<span class="select-box" style="width:150px;">
					<select class="select" name="status" size="1" disabled=“disabled”>
							<option value="0" >请选择</option>
							<option value="1" <c:if test="${verified.status==1}">selected</c:if>>待审核</option>
							<option value="2" <c:if test="${verified.status==2}">selected</c:if>>被拒绝</option>
							<option value="3" <c:if test="${verified.status==3}">selected</c:if>>审核通过</option>
					</select> 
					</span>
				</div>
				<div class="col-4"> </div>
			</div>
		
			<div class="row cl">
					<label class="form-label col-3">审核意见：</label>
					<div class="formControls col-5">
						<textarea name="auditRemark" cols="" rows="" class="textarea"   readonly="readonly" >${verified.auditRemark}</textarea>
						<p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
					</div>
					<div class="col-4"> </div>
			</div>
		</div>
		
		
			
	</table>
	</form>
</div>

</body>
</html>
