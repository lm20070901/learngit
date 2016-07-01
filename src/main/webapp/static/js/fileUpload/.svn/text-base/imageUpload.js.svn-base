/**
 * 	图片上传插件  基于webupload  jQuery 
 *  @author jiangyx 2016.05.24  
 * 
 */


// 初始化Web Uploader
var imageUpload  = function (config){
	var path = getRootPath();
	
	var uploader = WebUploader.create({
		pick : {
			id : '#'+(config.filePicker||'filePicker'),
			label : '选择图片',
			multiple : true// 是否开起同时选择多个文件能力
		},
		method :'post',
		fileVal : 'images',//设置文件上传域的name。
		auto : config.auto||true,
		paste : document.body,
		accept : {
			title : 'Images',
			extensions : 'gif,jpg,jpeg,bmp,png',
			mimeTypes : 'image/*'
		},
		// swf文件路径  
		swf : path + '/static/lib/webuploader/0.1.5/Uploader.swf',
		disableGlobalDnd : false, //是否禁掉整个页面的拖拽功能
		chunked : false,
		server : path + '/file/uploadPic',
		fileNumLimit : config.fileNumLimit||9,
		fileSizeLimit : 50 * 1024 * 1024,//5M  
		fileSingleSizeLimit : 5 * 1024 * 1024,//1M 
		formData : {
			'folder' : config.folder||'default'
		}
	});
	
	var $list = $("#"+(config.fileList||'fileList'));
	
	var $btn ;
	
	if(config.btnstar){//上传按钮ID 
		$btn = $("#"+(config.btnstar||"btn-star"));
	}
	var state = "pending";
	
	//uploader;
	uploader.on( 'fileQueued', function( file ) {
		var $li = $(
			'<div id="' + file.id + '" class="item" style="float:left;margin:15px 10px 10px 0 ;">' +
				'<div class="pic-box" ><img></div>'+
				'<div class="info" >' + file.name + '</div>' +
				'<p class="state" >等待上传...</p>'+
			'</div>'
		),
		$img = $li.find('img');
		$list.append( $li );
	
		// 创建缩略图
		uploader.makeThumb( file, function( error, src ) {
			if ( error ) {
				$img.replaceWith('<span>不能预览</span>');
				return;
			}
	
			$img.attr( 'src', src );
		}, 100, 100 );
	});
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
		var $li = $( '#'+file.id ),
			$percent = $li.find('.progress-box .sr-only');
	
		// 避免重复创建
		if ( !$percent.length ) {
			$percent = $('<div class="progress-box"><span class="progress-bar radius"><span class="sr-only" style="width:0%"></span></span></div>').appendTo( $li ).find('.sr-only');
		}
		$li.find(".state").text("上传中");
		$percent.css( 'width', percentage * 100 + '%' );
	});
	
	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function( file,response  ) {
		if(response.result==1){
			$( '#'+file.id ).addClass('upload-state-success').find(".state").text("已上传");
			if(isExitsFunction(config.uploadSuccess)){
				config.uploadSuccess(response);//调用回调函数   
			}
		}else{
			$( '#'+file.id ).addClass('upload-state-error').find(".state").text("上传出错"+response.msg);
		}
	});
	
	// 文件上传失败，显示上传出错。
	uploader.on( 'uploadError', function( file,reason  ) {
		$( '#'+file.id ).addClass('upload-state-error').find(".state").text("上传出错");
	});
	
	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on( 'uploadComplete', function( file ) {
		$( '#'+file.id ).find('.progress-box').fadeOut();
	});
	
	
	uploader.on('all', function (type) {
	    if (type === 'startUpload') {
	        state = 'uploading';
	    } else if (type === 'stopUpload') {
	        state = 'paused';
	    } else if (type === 'uploadFinished') {
	        state = 'done';
	    }

	     if ($btn && $btn.length>0) {
	    	 if(state === 'uploading'){
	    		 $btn.text('暂停上传');
	    	 }else{
	    		 $btn.text('开始上传');
	    	 }
	    }
	}); 

	//如果存在上传按钮绑定点击事件 
	if($btn && $btn.length>0){
		$btn.on('click', function () {
		     if (state === 'uploading') {
		         uploader.stop();
		     } else {
		         uploader.upload();
		     }
		 }); 
	}
	
	return uploader;
	
}
//js获取项目根路径，
var getRootPath = function (){
    //获取当前网址
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}

//是否存在指定函数 
var  isExitsFunction = function(funcName){
    try {
        if (typeof(eval(funcName)) == "function") {
            return true;
        }
    } catch(e) {}
    return false;
}