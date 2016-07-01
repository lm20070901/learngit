<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE HTML>
<html style="height: 100%">
<head>
<%@ include file="../head.jsp"%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/static/js/table/common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/static/lib/ehart/echarts.min.js"></script>

<title>访问列表</title>
</head>

<body style="height: 100%; margin: 0">

	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>访问量统计
		<span class="c-gray en">&gt;</span> 访问列表 
		<a class="btn btn-refresh btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px"
			href="javascript:location.replace(location.href);" title="刷新"> 
			<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>
	<div class="pd-20">
		<div class="text-l">
			<form action="<%=request.getContextPath()%>/pageView/toPageViewList"
				method="post">
				开始时间： <input type="text"
					onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})"
					id="startDate" name="startDate" value="${startDate}"
					class="input-text Wdate" style="width:200px;">&nbsp;&nbsp;
				结束时间： <input type="text"
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})"
					id="endDate" name="endDate" value="${endDate}"
					class="input-text Wdate" style="width:200px;">&nbsp;&nbsp;

				<button type="submit" class="btn btn-success" id="" name="">
					<i class="Hui-iconfont">&#xe665;</i> 查询
				</button>
				<input type="button" class="btn btn-primary " id="" name=""   onclick="showTable();" value="表格展示" /> 
				<input type="button"  class="btn btn-primary " id="" name="" onclick="showChart();"  value="图表展示" />


			</form>

		</div>
		<div id="table">
			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="r">共有数据：<strong>${pager.totalRow}</strong> 条
				</span>
			</div>
			<table class="table table-border table-bordered table-bg table-sort"
				style="table-layout:fixed;">
				<thead>
					<tr>
						<th scope="col" colspan="6">访问列表</th>
					</tr>
					<tr class="text-c">
						<th width="25"><input type="checkbox" name="" value=""></th>
						<th width="150">日期</th>
						<th width="150">浏览次数(PV)</th>
						<th width="150">独立访客(UV)</th>
						<th width="150">IP</th>
						<th width="150">新注册</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="pageView" items="${pageViewList}">
						<tr class="text-c">
							<td><input type="checkbox" value="${pageView.reportId}"
								name="selectBox"></td>
							<td>${pageView.dateStr}</td>
							<td>${pageView.pv}</td>
							<td>${pageView.uv}</td>
							<td>${pageView.ip}</td>
							<td>${pageView.reg}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="pagination"></div>

		</div>

		<div id="chart" style="margin-top:20px;"></div>

	</div>

</body>

<script type="text/javascript">
	
		//初始化
		TableUtils.init({
			deleteUrl : "<%=request.getContextPath()%>/vote/deleteVoteByIds",
			updateUrl : "<%=request.getContextPath()%>/vote/updateShowStatus",
			queryUrl  : "<%=request.getContextPath()%>/vote/voteList",
			page: {//分页
				totalPage  :  ${pager.totalPage},
				cpage      :  ${pager.cpage},
				pageSize   :  ${pager.pageSize} 
			}
		});
		
		 var dateArr = new Array();
		  var pvArr = new Array();
		  var uvArr = new Array();
		  var ipArr = new Array();
		  $("#chart").height( $(window).innerHeight()-150);
		  $("#chart").width( $(window).innerWidth()-30);
		  //表格展示
		  function showTable(){
			  $("#chart").hide();
			  $("#table").show();
		  }
		  
		  //图形展示
		  function showChart(){
			  $("#table").hide();
			  
			  $("#chart").show();
			   var index = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
				 });
				 var startDate = $("#startDate").val();
				 var endDate = $("#endDate").val();
				 $.ajax({
			            type:'post',
			            url:"<%=request.getContextPath()%>/pageView/getChartData",
			            dataType:'json',
			            data: {'startDate':startDate,'endDate':endDate},
			            success:function(json){
			            	layer.close(index);
			            	if(json.result==1){
			            		for(var i=0;i<json.pageViewList.length;i++){
									 dateArr[i] = json.pageViewList[i].dateStr;
									 
									 pvArr[i] = json.pageViewList[i].pv; 
									 
									 uvArr[i] = json.pageViewList[i].uv; 
									 
									 ipArr[i] = json.pageViewList[i].ip; 
								 }
			            		initChart();
			            	}else{
			            		layer.msg(json.msg,{icon:1,time:2000});
			            	}
			            },
			            error:function(e){ 
			            	layer.close(index);
			            	layer.msg("系统或者网络出错",{icon:1,time:2000});
			            } 
			        });
			   
		  }
		  
		 
		  
		 function  initChart (){
			  
			  var dom = document.getElementById("chart");
			  var myChart = echarts.init(dom);
			  var app = {};
			  var option = null;
			 
			  option = {
			      title: {
			          text: '',
			          subtext: ''
			      },
			      tooltip: {
			          trigger: 'axis'
			      },
			      legend: {
			          data:['浏览次数(PV)','独立访客(UV)','IP']
			      },
			      toolbox: {
			          show: true,
			          feature: {
			             // magicType: {show: true, type: ['stack', 'tiled']},
			              saveAsImage: {show: true}
			          }
			      },
			      xAxis: {
			          type: 'category',
			          boundaryGap: false,
			          data: dateArr
			      },
			      yAxis: {
			          type: 'value'
			      },
			      series: [{
			          name: '浏览次数(PV)',
			          type: 'line',
			          smooth: true,
			          data: pvArr,
			          markPoint : {
			        	  data: [ {type: 'max', name: '最大值'},
			                      {type: 'min', name: '最小值'}]
			          }
			      },
			      {
			          name: '独立访客(UV)',
			          type: 'line',
			          smooth: true,
			          data: uvArr,
			          markPoint : {
			        	  data: [ {type: 'max', name: '最大值'},
			                      {type: 'min', name: '最小值'}]
				          }
			      },
			      {
			          name: 'IP',
			          type: 'line',
			          smooth: true,
			          data: ipArr,
			          markPoint : {
			        	  data: [ {type: 'max', name: '最大值'},
			                      {type: 'min', name: '最小值'}]
				          }
			      }]
			  };
			 
			  if (option && typeof option === "object") {
			      var startTime = +new Date();
			      myChart.setOption(option, true);
			      var endTime = +new Date();
			      var updateTime = endTime - startTime;
			     // console.log("Time used:", updateTime);
			  }
		  }
</script>

</html>
