<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../head.jsp"%>
<title>查看详情</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/lib/icheck/jquery.icheck.min.js"></script>
<link rel="<%=request.getContextPath()%>/static/lib/icheck/icheck.css" type="text/css"/>

<style type="text/css">
	
</style>
</head>

<body>

	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 
		<span class="c-gray en">&gt;</span>问卷管理
		<span class="c-gray en">&gt;</span> 问卷详情
		<a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新">
		<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav> 
	<div class="pd-20">
		<div class="text-l">
			<input type="hidden" name="voteId" value="${voteId}" id="voteId" />
			<h3>${voteDetail.vote.voteTitle}</h3>
			<!-- 问卷介绍 resize:none-->
			<div>${voteDetail.vote.voteInfo}</div>
			<!-- <textarea class="textarea radius" readonly="readonly" onfocus=""></textarea> -->
			
		</div>
		<c:forEach items="${voteDetail.questionList}" var="question"
			varStatus="status">

			<table class="table  table-hover" style="width:auto;">
				<thead>
					<tr>
						<th scope="col" colspan="5" >Q${status.index+1}:${question.title}</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${question.type==0 || question.type==1}">
							<c:forEach items="${voteDetail.optionList}" var="option"
								varStatus="status">
								<c:if test="${option.questionId==question.questionId}">
									<tr>
										<td width="5" class="text-r"><c:choose>
												<c:when test="${question.type==0}">
													<input type="radio" value="${option.id}" name="selectBox"  disabled="disabled">
												</c:when>
												<c:when test="${question.type==1}">
													<input type="checkbox" value="${option.id}" disabled="disabled"
														name="selectBox">
												</c:when>
											</c:choose></td>

										<td width="20%">${option.optionBody}</td>
										<td>
										<div > <!-- style="background: #dadada;" -->
										<span  class="progress" data-percent="width:${option.percentage}%">
											<span class="progress-bar">
												<span class="sr-only" style="width:${option.percentage}%"></span> 
											</span>
										</span>${option.percentage}%</div></td>
										<%-- <div class="check-box">
			  								<input type="checkbox" name="selectBox" value="${option.id}">
  											<label for="checkbox-1">${option.optionBody}</label>
 										</div> --%>
									</tr>
								</c:if>


							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="text-c">
								<td colspan="5"><a href="javascript:;" onclick="viewOpr(${question.questionId});"
									class="btn btn-primary radius""> <i class="Hui-iconfont">&#xe695;</i>
										查看结果
								</a></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</c:forEach>
	</div>

</body>

<script type="text/javascript">
	function viewOpr(questionId){
		layer_show("回答内容列表","<%=request.getContextPath()%>/vote/toVoteAnserList?questionId="+questionId,"800","500");
	}
</script>
</html>
