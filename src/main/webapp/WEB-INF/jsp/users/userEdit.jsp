<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<%-- <link href="<%=request.getContextPath()%>/static/css/selectStyle.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/static/css/selectReset.css" rel="stylesheet" type="text/css"/>  --%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/static/lib/Validform/5.3.2/Validform.min.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/select/select.js"></script>  --%>
<title>编辑用户</title>
</head>

<body>

	<div id="editUser" class="HuiTab">
		<div class="tabBar cl">
			<span>基本信息</span><span>理财专家认证信息</span><span>投资人认证信息</span><span>创业者认证信息</span>
		</div>
		<div class="tabCon">
			<form action="<%=request.getContextPath()%>/user/editUser"
				method="post" class="form form-horizontal" id="form-user-edit">

				<div class="row cl">
					<label class="form-label col-2 " style="height:50px;">头像：</label>
					<div class="formControls col-3">
						<img alt="" src="${user.userHead}">
					</div>
					<label class="form-label col-2"> <!-- 用户密码: -->
					</label>
					<div class="formControls col-2">
						<!-- 	<input type="hidden" value="" name="password" class="input-text" id="password_hide"/>
							<input  id="password_show" value="" name="password_show" class="input-text" datatype="*0-8" nullmsg=""  autocomplete="off" errormsg="请输入2-7个字符"/>
		 		-->
					</div>

					<div class="col-1"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-2">用户账号：</label>
					<div class="formControls col-3">
						<input value="${user.userName}" name="userName"
							readonly="readonly" class="input-text" />
					</div>



					<label class="form-label col-2">用户ID：</label>
					<div class="formControls col-3">
						<input value="${user.userId}" name="userId" readonly="readonly"
							class="input-text" />
					</div>
					<div class="col-1"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-2">用户昵称：</label>
					<div class="formControls col-2">
						<input value="${user.nickName}" name="nickName" autocomplete="off"
							class="input-text" datatype="*2-18" nullmsg="昵称不能为空"
							errormsg="请输入2-7个字符" />
					</div>
					<div class="col-1"></div>
					<label class="form-label col-2">性别：</label>
					<div class="formControls  col-2 radio-box">
						<input name="userGender" type="radio" value="1"
							<c:if test="${user.userGender==1}">checked</c:if> id="man" /><label
							for="man">男</label> &nbsp;&nbsp;&nbsp; <input name="userGender"
							type="radio" <c:if test="${user.userGender==0}">checked</c:if>
							value="0" id="woman" /><label for="woman">女</label>
					</div>

					<div class="col-4"></div>
				</div>

				<div class="row cl">
					<label class="form-label col-2">所在地区：</label>
					<div class="formControls col-3">
						<%-- <span class="select-box  inline" style="width:80px;margin-right:15px;">
								<select id="sel-9-1" class="select" url="<%=request.getContextPath()%>/district/getProvienceList" prompt="请选择省"  dataRoot="data" 
							  		 childId="sel-9-2" childDataPath="<%=request.getContextPath()%>/district/getCityList?provienceId=" ></select>
					  	</span>&nbsp;&nbsp;&nbsp;
					  	<!-- <span>&nbps;&nbps;&nbps;</span> -->
					  	<span class="select-box  inline" style="width:80px;">
								<select id="sel-9-2" class="select"  prompt="请选择市 " data='{"data":[]}' dataRoot="data" ></select>
					  	</span> --%>
						<span class="select-box  inline" style="width:100px;"> <select
							class="select" name="provinceId" size="1" id="province">
								<option value="0">请选择</option>
								<c:forEach var="provience" items="${provicenList}">
									<option value="${provience.id}"
										<c:if test="${user.provinceId==provience.id}">selected</c:if>>${provience.name}</option>
								</c:forEach>
						</select>
						</span> <span class="select-box  inline" style="width:100px;"> <select
							class="select" name="cityId" size="1" id="city">
								<option value="0">请选择</option>
								<c:forEach var="city" items="${cityList}">
									<option value="${city.id}"
										<c:if test="${user.cityId==city.id}">selected</c:if>>${city.name}</option>
								</c:forEach>
						</select>
						</span>
					</div>
					<label class="form-label col-2">学历：</label>
					<div class="formControls col-3">
						<span class="select-box" style="width:150px;"> <select
							class="select" name="educationId" size="1">
								<option value="0">请选择</option>
								<option value="7"
									<c:if test="${user.educationId==7}">selected</c:if>>博士</option>
								<option value="6"
									<c:if test="${user.educationId==6}">selected</c:if>>研究生</option>
								<option value="5"
									<c:if test="${user.educationId==5}">selected</c:if>>大学</option>
								<option value="4"
									<c:if test="${user.educationId==4}">selected</c:if>>大专</option>
								<option value="3"
									<c:if test="${user.educationId==3}">selected</c:if>>高中</option>
								<option value="2"
									<c:if test="${user.educationId==2}">selected</c:if>>初中</option>
								<option value="1"
									<c:if test="${user.educationId==1}">selected</c:if>>小学</option>
						</select>
						</span>
					</div>

					<div class="col-4"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-2">用户邮箱：</label>
					<div class="formControls col-2">
						<input value="${user.authEmail}" name="authEmail"
							autocomplete="off" class="input-text" datatype="e"
							errormsg="请输入正确邮箱格式！" />
						<!-- datatype="e" 邮箱格式-->
					</div>
					<div class="col-2"></div>
					<label class="form-label col-1">注册时间：</label>
					<div class="formControls col-3">
						<input type="hidden" id="signUpDate" readonly="readonly"
							name="signUpDate" value="${user.signUpDate}"
							class="input-text Wdate"> <input type="text"
							readonly="readonly" name="signUpDate_show"
							value="<date:date value='${user.signUpDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
							class="input-text Wdate" style="width:200px;"> <span></span>
					</div>
				</div>

				<div class="row cl">
					<label class="form-label col-2">注册IP：</label>
					<div class="formControls col-3">
						<input value="${user.regIp}" name="regIp" autocomplete="off"
							class="input-text" readonly="readonly" />
					</div>
					<label class="form-label col-2">文章数量：</label>
					<div class="formControls col-3">
						<input value="${user.msgNum}" name="msgNum" autocomplete="off"
							class="input-text" readonly="readonly" />
					</div>
					<div class="col-4"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-2">关注数量：</label>
					<div class="formControls col-3">
						<input value="${user.followNum}" name="followNum"
							autocomplete="off" class="input-text" readonly="readonly" />
					</div>
					<label class="form-label col-2">粉丝数量：</label>
					<div class="formControls col-3">
						<input value="${user.followmeNum}" name="followmeNum"
							autocomplete="off" class="input-text" readonly="readonly" />
					</div>
					<div class="col-4"></div>
				</div>
			</form>
			<div class="row cl">
				<div class="col-offset-5">
					<input class="btn btn-primary radius" type="button" value="保存"
						onclick="save('0');"> &nbsp;&nbsp;&nbsp;<input
						class="btn btn-warning-outline radius" type="button" value="取消"
						onclick="cancle();">
				</div>
			</div>
		</div>
		<c:forEach items="${verifiedlist}" var="verified">
			<c:if test="${verified.verifyType==0 }">
				<div class="tabCon">
					<form  action="<%=request.getContextPath()%>/verified/saveVerifiedInfo"  method="post" class="form form-horizontal"  id="form-verified1-edit">
						<hr align=center width=auto color=#987cb9 size=1>
						<!-- <h4>认证信息</h4> -->
						<input type="hidden" name="id" value="${verified.id}" name="id"/>
						<input value="${user.userId}" name="userId" class="input-text"  type="hidden" />
						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>认证名称：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text"
									value="${verified.realname}" placeholder="" id="realname"
									name="realname" datatype="*2-9" nullmsg="用户名不能为空">
							</div>
							<div class="col-4"></div>
						</div>
						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>电话号码：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text"
									value="${verified.phonenum}" placeholder="" id="phonenum"
									name="phonenum" datatype="*2-16" nullmsg="电话号码不能为空">
							</div>
							<div class="col-4"></div>
						</div>
						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>身份证号：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text" value="${verified.idcard}"
									placeholder="" id="idcard" name="idcard" datatype="*2-18"
									nullmsg="身份证号不能为空">
							</div>
							<div class="col-4"></div>
						</div>
						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>QQ：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text" value="${verified.qq}"
									placeholder="" id="qq" name="qq" datatype="*4-15"
									nullmsg="QQ不能为空">
							</div>
							<div class="col-4"></div>
						</div>
						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>联系地址：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text"
									value="${verified.address}" placeholder="" id="address"
									name="address" datatype="*2-16" nullmsg="联系地址不能为空">
							</div>
							<div class="col-4"></div>
						</div>

						<div class="row cl">
							<label class="form-label col-2">证件照片：</label>
							<div class="formControls col-5">
								<img src="${verified.idcardImg1}" class="img-responsive"
									alt="身份证正面照"> <img src="${verified.idcardImg2}"
									class="img-responsive" alt="身份证背面照">
							</div>
							<div class="col-4"></div>
						</div>

						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>认证介绍：</label>
							<div class="formControls col-5">
								<textarea name="authinfo" cols="" rows="" class="textarea"
									datatype="*1-101" placeholder="100个字符以内" dragonfly="true"
									onKeyUp="textarealength(this,100)">${verified.authinfo}</textarea>
								<p class="textarea-numberbar">
									<em class="textarea-length">0</em>/100
								</p>
							</div>
							<div class="col-4"></div>
						</div>

					</form>

					<div class="row cl">
						<div class="col-offset-5">
							<input class="btn btn-primary radius" type="button" value="保存"
								onclick="save('1');"> &nbsp;&nbsp;&nbsp;<input
								class="btn btn-warning-outline radius" type="button" value="取消"
								onclick="cancle();">
						</div>
					</div>
				</div>

			</c:if>
			<c:if test="${verified.verifyType==1 }">

				<div class="tabCon">
					<form
						action="<%=request.getContextPath()%>/verified/saveVerifiedInfo"
						method="post" class="form form-horizontal"
						id="form-verified2-edit">
						<hr align=center width=auto color=#987cb9 size=1>
						<!-- <h4>认证信息</h4> -->
						<input type="hidden" name="id" value="${verified.id}" name="id"/>
						<input value="${user.userId}" name="userId" class="input-text"
							type="hidden" />
						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>认证名称：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text"
									value="${verified.realname}" placeholder="" id="realname"
									name="realname" datatype="*2-9" nullmsg="用户名不能为空">
							</div>
							<div class="col-4"></div>
						</div>
						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>电话号码：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text"
									value="${verified.phonenum}" placeholder="" id="phonenum"
									name="phonenum" datatype="*2-16" nullmsg="电话号码不能为空">
							</div>
							<div class="col-4"></div>
						</div>
						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>身份证号：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text" value="${verified.idcard}"
									placeholder="" id="idcard" name="idcard" datatype="*2-18"
									nullmsg="身份证号不能为空">
							</div>
							<div class="col-4"></div>
						</div>
						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>QQ：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text" value="${verified.qq}"
									placeholder="" id="qq" name="qq" datatype="*4-15"
									nullmsg="QQ不能为空">
							</div>
							<div class="col-4"></div>
						</div>
						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>联系地址：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text"
									value="${verified.address}" placeholder="" id="address"
									name="address" datatype="*2-16" nullmsg="联系地址不能为空">
							</div>
							<div class="col-4"></div>
						</div>

						<div class="row cl">
							<label class="form-label col-2">证件照片：</label>
							<div class="formControls col-5">
								<img src="${verified.idcardImg1}" class="img-responsive"
									alt="身份证正面照"> <img src="${verified.idcardImg2}"
									class="img-responsive" alt="身份证背面照">
							</div>
							<div class="col-4"></div>
						</div>


						<div class="row cl">
							<label class="form-label col-3">关注行业：</label>
							<div class="formControls col-5">
								<div class="check-box">
									<input type="text" class="input-text"
										value="${verified.concernIndustry}" readonly="readonly"
										name="concernIndustry">
								</div>
							</div>
							<div class="col-4"></div>
						</div>

						<div class="row cl">
							<label class="form-label col-3">年收入：</label>
							<div class="formControls col-5">
								<span class="select-box"> <select class="select" size="1"
									name="annualIncome" disabled>
										<option value="" selected></option>
										<option value="0"
											<c:if test="${verified.annualIncome==0}">selected</c:if>>20万以下</option>
										<option value="1"
											<c:if test="${verified.annualIncome==1}">selected</c:if>>20-50万</option>
										<option value="2"
											<c:if test="${verified.annualIncome==2}">selected</c:if>>50-100万</option>
										<option value="3"
											<c:if test="${verified.annualIncome==3}">selected</c:if>>100万以上</option>
								</select>
								</span>
							</div>
							<div class="col-4"></div>
						</div>

						<div class="row cl">
							<label class="form-label col-3">个人资产：</label>
							<div class="formControls col-5">
								<input type="text" class="input-text" value="${verified.assets}"
									readonly="readonly" name="assets">
							</div>
							<div class="col-4"></div>
						</div>

						<div class="row cl">
							<label class="form-label col-2"><span class="c-red">*</span>认证介绍：</label>
							<div class="formControls col-5">
								<textarea name="authinfo" cols="" rows="" class="textarea"
									datatype="*1-101" placeholder="100个字符以内" dragonfly="true"
									onKeyUp="textarealength(this,100)">${verified.authinfo}</textarea>
								<p class="textarea-numberbar">
									<em class="textarea-length">0</em>/100
								</p>
							</div>
							<div class="col-4"></div>
						</div>
					</form>

					<div class="row cl">
						<div class="col-offset-5">
							<input class="btn btn-primary radius" type="button" value="保存"
								onclick="save('2');"> &nbsp;&nbsp;&nbsp;<input
								class="btn btn-warning-outline radius" type="button" value="取消"
								onclick="cancle();">
						</div>
					</div>
				</div>
	</div>




	</c:if>

	<c:if test="${verified.verifyType==2 }">

		<div class="tabCon">
			<form
				action="<%=request.getContextPath()%>/verified/saveVerifiedInfo"
				method="post" class="form form-horizontal" id="form-verified3-edit">
				<hr align=center width=auto color=#987cb9 size=1>
				
				<input type="hidden" name="id" value="${verified.id}" name="id"/>
				<input value="${user.userId}" name="userId" class="input-text"
					type="hidden" />
				<div class="row cl">
					<label class="form-label col-2"><span class="c-red">*</span>认证名称：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${verified.realname}"
							placeholder="" id="realname" name="realname" datatype="*2-9"
							nullmsg="用户名不能为空">
					</div>
					<div class="col-4"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-2"><span class="c-red">*</span>电话号码：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${verified.phonenum}"
							placeholder="" id="phonenum" name="phonenum" datatype="*2-16"
							nullmsg="电话号码不能为空">
					</div>
					<div class="col-4"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-2"><span class="c-red">*</span>身份证号：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${verified.idcard}"
							placeholder="" id="idcard" name="idcard" datatype="*2-18"
							nullmsg="身份证号不能为空">
					</div>
					<div class="col-4"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-2"><span class="c-red">*</span>QQ：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${verified.qq}"
							placeholder="" id="qq" name="qq" datatype="*4-15"
							nullmsg="QQ不能为空">
					</div>
					<div class="col-4"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-2"><span class="c-red">*</span>联系地址：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${verified.address}"
							placeholder="" id="address" name="address" datatype="*2-16"
							nullmsg="联系地址不能为空">
					</div>
					<div class="col-4"></div>
				</div>

				<div class="row cl">
					<label class="form-label col-2">证件照片：</label>
					<div class="formControls col-5">
						<img src="${verified.idcardImg1}" class="img-responsive"
							alt="身份证正面照"> <img src="${verified.idcardImg2}"
							class="img-responsive" alt="身份证背面照">
					</div>
					<div class="col-4"></div>
				</div>



				<div class="row cl">
					<label class="form-label col-3">项目名字：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text"
							value="${verified.projectName}" readonly="readonly"
							name="projectName">
					</div>
					<div class="col-4"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-3">所属行业：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${verified.industry}"
							readonly="readonly" name="industry">
					</div>
					<div class="col-4"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-3">所属地区：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${verified.province}"
							readonly="readonly" name="province">
					</div>
					<div class="col-4"></div>
				</div>
				<div class="row cl">
					<label class="form-label col-3">所属阶段：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text"
							value="${verified.projectStage}" readonly="readonly"
							name="projectStage">
					</div>
					<div class="col-4"></div>
				</div>

				<div class="row cl">
					<label class="form-label col-2"><span class="c-red">*</span>认证介绍：</label>
					<div class="formControls col-5">
						<textarea name="authinfo" cols="" rows="" class="textarea"
							datatype="*1-101" placeholder="100个字符以内" dragonfly="true"
							onKeyUp="textarealength(this,100)">${verified.authinfo}</textarea>
						<p class="textarea-numberbar">
							<em class="textarea-length">0</em>/100
						</p>
					</div>
					<div class="col-4"></div>
				</div>

			</form>
			<div class="row cl">
				<div class="col-offset-5">
					<input class="btn btn-primary radius" type="button" value="保存"
						onclick="save('3');"> &nbsp;&nbsp;&nbsp;<input
						class="btn btn-warning-outline radius" type="button" value="取消"
						onclick="cancle();">
				</div>
			</div>
		</div>

		</div>





	</c:if>
	</c:forEach>

	</div>
	<!-- <div class="pd-20">
	
		<div style="height:20px;"></div> -->


	</div>


	<script type="text/javascript">
	
	//保存
	function save(type) {
		if(type=="0"){
			user_form.submitForm(false);
		}
		if(type=="1"){
			verified1_form.submitForm(false);
		}
		if(type=="2"){
			verified2_form.submitForm(false);
		}
		if(type=="3"){
			verified3_form.submitForm(false);
		}
	}
	//取消
	function cancle() {
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭  
	}
	
		//联动省市
		$("#province").change(function() {
							var province = $("#province").val();
							if (!province || province == 0)
								layer.msg("请先选择省 ", {icon : 1,time : 2000}, function() {
										
								});
							$("#city").empty();

							$.ajax({
								type : "post",
								url : "<%=request.getContextPath()%>/district/getCityList",
            					dataType:'json',
             					data: {'provienceId':province},
				             success:function(json) {
				            	//var json = eval('('+json+')');
				            	if(json.result==1){
				             		 var data = json.data;
				             		 $("#city").append("<option value=\"0\"  >请选择</option>");
				             		 for(var i=0;i<data.length;i++){
				 		       		    var obj = data[i];
				 		       				$("#city").append("<option value='"+obj.id+"'>"+obj.name+"</option>");
				        				}  
				            	}else{
				            		layer.msg(json.msg,{icon:1,time:2000});
				             	}				
				            	
				             },
				             error:function(e){ 
				            	
				             	layer.msg("加载出错",{icon:1,time:2000});
				             } 
         }); 
		 
 	}); 
	
	/*  用户基本信息*/
	user_form = $("#form-user-edit").Validform({
		tiptype:2,
		callback:function(data){
			validate_reuslt = true;
			return false;
		},
		ajaxPost:false,
		beforeSubmit:function(curform){
			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
			//这里明确return false的话表单将不会提交;
			var index = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
				$.ajax({
		            type:"post",
		            url:"<%=request.getContextPath()%>/user/editUser",
		            dataType:'json',
		            data: $('#form-user-edit').serialize(),
		            success:function(json) {
		            	layer.close(index);
		            	layer.msg(json.msg, {
								icon : 1,
								time : 2000
							});

					},
					error : function(e) {
							layer.close(index);
							layer.msg("系统或者网络出错", {	icon : 1,time : 2000});
						}
					});
				return false;
			}

			});

	
	   
			//校验用户认证信息
		verified1_form = $("#form-verified1-edit").Validform({
				tiptype : 2,//提示方式
				ajaxPost : false,
				callback : function(data) {//提交表单的回调函数
					//validate_reuslt = true;
					saveVerfiedInfo("form-verified1-edit");
					return false;
				}

			});
			
		verified2_form = $("#form-verified2-edit").Validform({
			tiptype : 2,//提示方式
			ajaxPost : false,
			callback : function(data) {//提交表单的回调函数
				//validate_reuslt = true;
				saveVerfiedInfo("form-verified2-edit");
				return false;
			}

		});
		
		verified3_form = $("#form-verified3-edit").Validform({
			tiptype : 2,//提示方式
			ajaxPost : false,
			callback : function(data) {//提交表单的回调函数
				//validate_reuslt = true;
				saveVerfiedInfo("form-verified2-edit");
				return false;
			}

		});
		

		function saveVerfiedInfo(id){
			var index = layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
			$.ajax({
	          type:"post",
	          url:"<%=request.getContextPath()%>/verified/saveVerifiedInfo",
						dataType : 'json',
						data : $('#' + id + '').serialize(),
						success : function(json1) {
							layer.close(index);
						layer.msg(json1.msg, {
									icon : 1,
									time : 2000
								});

						},
						error : function(e) {
							layer.close(index);
							layer.msg("系统或者网络出错", {
								icon : 1,
								time : 2000
							});

						}
					});
		}

		$.Huitab("#editUser .tabBar span", "#editUser .tabCon", "current",
				"click", "0");
	</script>
</body>
</html>
