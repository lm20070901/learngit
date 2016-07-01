

/*
 * 自定义JS  列表的公共方法  该JS文件需要用到jQuery ,layer ,jqPaginator框架 
 * @auth jiangyx
 * time :  2016.05.12
 */
var TableUtils = {
	    init:function (options){
	    	TableUtils.deleteUrl = options.deleteUrl||'';
	    	TableUtils.updateUrl = options.updateUrl||'';
	    	TableUtils.queryUrl  = options.queryUrl ||'';
	    	var pager  =  options.page;
	    	
	    	
	    	try{
	    		$.jqPaginator('#pagination', {
				    totalPages: pager.totalPage ,
				    visiblePages: 5,
				    totalCounts : pager.totalCounts,
				    pageSize : pager.pageSize ,
				    currentPage: pager.cpage,
				    first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
				    prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
				    next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
				    last: '<li class="last"><a href="javascript:void(0);">末页</a></li>',
				    page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
				    onPageChange: function (num) {
				    	//var data = $('form').serialize();
				    	if(num!=pager.cpage){
				    		   if($("form:first").attr("action")==''){
				    			   $("form:first").attr("action",options.queryUrl);
				    		   }
				    		   if($("#page").length<=0){
				    			   $("form:first").append("<input name='page' value='1' id='page' type='hidden'/>" +
				    			   		"<input name='pageSize' value='10' id='pageSize' type='hidden'/>");
				    		   }
				    		   $("#page").val(num);
				    		   $("#pageSize").val(pager.pageSize);
				               $("form:first").submit();
				    	   //  window.location.href= options.queryUrl+"?page="+num+"&size="+pager.pageSize+"&"+data;
				    		
				    	}
				    	
				        	
				    }
		    	});
	    		
	    	}catch(e){
	    		//console.log(e);
	    		//当记录数目为0  会报错 
	    		$("#pagination").append('<li jp-data="1" jp-role="first" class="first disabled"><a href="javascript:void(0);">首页</a></li>'+
	    		'<li jp-data="1" jp-role="page" class="disabled"><a href="javascript:void(0);">1</a></li>'+
	    		'<li jp-data="2" jp-role="last" class="last disabled"><a href="javascript:void(0);">末页</a></li>');
	    	}
	    	
	    	
	    },
		index : '',
		queryUrl : '',
		deleteUrl:'',
		updateUrl :'',
		saveUrl :'',
		updateMsg :'确认要执行更新操作吗？',
		deleteMsg :'确认要删除选中行吗？',
		ajaxPost : function (postUrl,jsonData,callBack){//ajax post 请求
			$.ajax({
	            type:'post',
	            url:postUrl,
	            dataType:'json',
	            data: jsonData,
	            success:function(json){
	            	layer.close(TableUtils.index);
	            	if(json.result==1){
	            		layer.msg(json.msg,{icon:1,time:2000});
	            		callBack(json);
	            	}else{
	            		layer.msg(json.msg,{icon:1,time:2000});
	            	}
	            },
	            error:function(e){ 
	            	layer.close(TableUtils.index);
	            	layer.msg("系统或者网络出错",{icon:1,time:2000});
	            } 
	        });
		},
		deleteOpr : function (callBack){//删除操作
			var ids = TableUtils.getSelectedRowsId();
			if(!ids) return;
			var idArr = ids.split(',');
			TableUtils.index = layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
			layer.confirm(TableUtils.deleteMsg,function(){
				
				if(!TableUtils.deleteUrl || TableUtils.deleteUrl==''){
			    	layer.msg("请先设置操作路径！",{icon:1,time:2000});
			    	return;
			    }
				
				TableUtils.ajaxPost(TableUtils.deleteUrl,{'idList':idArr},function (){
					
					if(TableUtils.isExitsFunction(callBack)){
						callBack();
					}else{
						TableUtils.removeSelectedRows();
					}
				});
			},
			function() {
				layer.close(TableUtils.index);
			});
		},
		updateOpr : function (callBack){//更新状态
			var ids = TableUtils.getSelectedRowsId();
			if(!ids) return;
			var idArr = ids.split(',');
			TableUtils.index = layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
			layer.confirm(TableUtils.updateMsg,function (){				
				if(!TableUtils.updateUrl||TableUtils.updateUrl==''){
			    	layer.msg("请先设置操作路径！",{icon:1,time:2000});
			    	return;
			    }
				
				TableUtils.ajaxPost(TableUtils.updateUrl,{'idList':idArr},function (){
					
					if(TableUtils.isExitsFunction(callBack)){
						callBack();
					}else{
						location.reload();
					}
				});	
			},
			function() {//取消操作的时候需要把全局遮罩关闭
				layer.close(TableUtils.index);
			});
		},
		saveOpr : function (id,callBack){//保存操作
					
					TableUtils.index = layer.load(1, {shade : [ 0.1, '#fff' ] });
					
					TableUtils.ajaxPost(TableUtils.saveUrl, $('#' + id).serialize(),
							function(json) {
			
								if (TableUtils.isExitsFunction(callBack)) {
									callBack(json);
								} else {
									setTimeout(function() {
												var index = parent.layer.getFrameIndex(window.name);
												parent.location.reload();
												parent.layer.close(TableUtils.index);
											}, 2000);
								}
							});
		}
		
};



/**
 * 指定更新某些行某列的值 rowsObj 行对象 col 列下标 text 更新的文本
 */
TableUtils.updateColumnsValue = function (rowsObj,col,text){
	for ( var i = 0; i < rowsObj.length; i++) {
		$(rowsObj[i]).children('td:nth-child('+col+')').html(text);//改变某个列的值 
	}
	
}

//是否存在指定函数 
TableUtils.isExitsFunction = function(funcName){
    try {
        if (typeof(eval(funcName)) == "function") {
            return true;
        }
    } catch(e) {}
    return false;
}
/**
 * 选中行ID 
 * 
 **/
TableUtils.getSelectedRowsId = function () {
    
    TableUtils.getSelectedDatas();
   
	return TableUtils.ids;
}

/**
 * 获取选中行对象
 * 
 */
TableUtils.getSelectedRows = function (){
	  
	    TableUtils.getSelectedDatas();
	    
		return TableUtils.selectTrs;
}


/**
 * 删除选中行
 */
TableUtils.removeSelectedRows = function (){
	
    TableUtils.getSelectedDatas();
	
	if (!TableUtils.selectTrs)
		return;
	var selectTrs = TableUtils.selectTrs;
	for ( var i = 0; i < selectTrs.length; i++) {
		$(selectTrs[i]).remove();
	}
	
}

/**
 * 初始化值
 */
TableUtils.getSelectedDatas = function (){
	
	var selectBox = $("input[name='selectBox']");
	var selectTrs = new Array();
	var ids = "";
	var n = 0;
	for ( var i = 0; i < selectBox.length; i++) {
		if (selectBox[i].checked) {
			ids += selectBox[i].value + ",";
			selectTrs[n] = $(selectBox[i]).parent().parent();
			n++;
		}
	}
	if (ids.length == 0) {
		/**add by jiangyx  未选中 必须重新将选择行初始为空 不然 用户取消选择后 还有选中行 */
		TableUtils.selectTrs = undefined ;
		TableUtils.ids = undefined ;
		layer.msg("请选择后操作", {icon : 1,time : 2000});
		return;
	} else {
		ids = ids.substring(0, ids.length - 1);
	}
	
	TableUtils.selectTrs = selectTrs;
	TableUtils.ids = ids;
}



