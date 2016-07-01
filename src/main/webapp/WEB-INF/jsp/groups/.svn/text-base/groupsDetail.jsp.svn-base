<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%> 
<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript"  src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script>
	<title>小组详情</title>
</head>

<body>
<form class="form form-horizontal" id="form-user-edit">
  <div class="pd-20">
			<div class="row cl">
				<label class="form-label col-2 " style="height:50px;">头像：</label>
				<div class="formControls col-3">
					<img alt="" src="${group.iconPath}">
				</div>
				<label class="form-label col-2"></label>
				<div class="formControls col-2">
 			</div>
				<div class="col-1"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">名称：</label>
				<div class="formControls col-3">
					<input value="${group.groupName}" name="groupName" readonly="readonly" class="input-text" />
				</div>
				<div class="col-1"></div> 
			</div>
			<div class="row cl">
 			 <label class="form-label col-2">小组介绍：</label>
				<div class="formControls col-7"> 
					<textarea name="groupIntroduce" cols="" rows="" class="textarea"  readonly="readonly">${group.groupIntroduce }</textarea>
				</div>
				<div class="col-1"></div> 
			</div>
			<div class="row cl">
				<label class="form-label col-2">创建人：</label>
				<div class="formControls col-2">
					<input  value="${group.userName}" name="userName"  autocomplete="off" class="input-text"  readonly="readonly"/> 
				</div>
				<div class="col-1"> </div>
				<label class="form-label col-1">创建时间：</label>
				<div class="formControls col-2">
				<input value="${group.strCreateTime }" class="input-text"  readonly="readonly"  />
			</div>
				<div class="col-1"> </div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">成员数：</label>
				<div class="formControls col-2">
					<input  value="${group.userCount}" name="userCount"  autocomplete="off" class="input-text"  readonly="readonly"/> 
				</div>
				<label class="form-label col-2">帖子数：</label>
				<div class="formControls col-2">
					<input  value="${group.articleCount}" name="articleCount"  autocomplete="off" class="input-text"  readonly="readonly"/> 
				</div>
				<div class="col-1"> </div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">状态：</label>
				<div style="width: 200px" class="formControls col-7">
					<span class="select-box">
						<select class="select" size="1" name="state" id="state" >
					        <option value="" <c:if test="${empty state}">selected</c:if>>全部</option>
					        <c:forEach items="${groupsState }" var="st">
					        	<option value="${st.key }" <c:if test="${group.state == st.key}">selected</c:if>>${st.value }</option>
					        </c:forEach>
				      	</select>
			      	</span>
			     </div>
				<label class="form-label col-2">是否禁用：</label>
				<div class="formControls col-2">
					<input  value="${group.forbid}" name="forbid"  autocomplete="off" class="input-text" readonly="readonly"/> 
				</div>
				<div class="col-1"> </div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">审核建议：</label>
				<div class="formControls col-7"> 
					<textarea name="suggestion" id="suggestion" readonly="readonly" cols="" rows="" class="textarea">${group.suggestion }</textarea>
				</div>
				<div class="col-4"> </div>
			</div>
</div></form>
<script type="text/javascript">
	
</script>
</body>
</html>
