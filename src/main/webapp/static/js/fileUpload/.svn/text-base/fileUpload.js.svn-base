
/**
 * 文件上传插件   add by jiangyx 2016.05.24  基于webupload jquery  
 */
(function ($, window) {
　　　　
　　　　function S4() {
　　　　　　return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
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
	//获取路径 	
	var applicationPath = getRootPath();
		
	//初始化 webupload 	
    function initWebUpload(item, options) {
    
        if (!WebUploader.Uploader.support()) {
            var error = "上传控件不支持您的浏览器！请尝试升级flash版本或者使用Chrome引擎的浏览器。<a target='_blank' href='http://se.360.cn'>下载页面</a>";
            if (window.console) {
                window.console.log(error);
            }
            $(item).text(error);
            return;
        }
     
        var defaults = {
            hiddenInputId: "uploadHiddenInputId", // input hidden id
            onAllComplete: function (event) { }, // 当所有file都上传后执行的回调函数
            onComplete: function (event) { },// 每上传一个file的回调函数
            innerOptions: {},
            fileVal : 'files',
            formData :  {folder : 'default'},
            fileNumLimit: 2,
            paste : document.body,
            fileSizeLimit: 500 * 1024 * 1024,//500M 
            fileSingleSizeLimit:  50* 1024 * 1024,//50M 
            PostbackHold: false
        };
 
        var opts = $.extend({}, defaults, options);
        
        var hdFileData = $("#" + opts.hiddenInputId);
        
        var target = $(item);//容器
        var pickerid = "";
        if (typeof guidGenerator36 != 'undefined')//给一个唯一ID
            pickerid = guidGenerator36();
        else
            pickerid = (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
 
        var uploaderStrdiv = '<div class="webuploader">' +
            '<div id="thelist" class="uploader-list"></div>' +
            '<div class="btns">' +
            '<div id="' + pickerid + '">选择文件</div>' +
            //'<a id="ctlBtn" class="btn btn-default">开始上传</a>' +
            '</div>' +
        '</div>';
        target.append(uploaderStrdiv);
        
        var $list = target.find('#thelist'),
             $btn = target.find('#ctlBtn'),//这个留着，以便随时切换是否要手动上传
             state = 'pending',
             uploader;
 
        var jsonData = {
            fileList: []
        };
        
        var webuploaderoptions = $.extend({
            // swf文件路径
            swf: applicationPath + '/static/lib/webuploader/0.1.5/Uploader.swf',
            // 文件接收服务端。
            server: applicationPath + '/file/uploadFile',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#' + pickerid,
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            fileNumLimit: opts.fileNumLimit,
            fileSizeLimit: opts.fileSizeLimit,
            fileSingleSizeLimit: opts.fileSingleSizeLimit,
            fileVal : opts.fileVal,
            formData :  {folder : opts.formData.folder},
            method :'post',
            auto : true
        },
        opts.innerOptions);
        
        var uploader = WebUploader.create(webuploaderoptions);
 
　　　　　　//回发时还原hiddenfiled的保持数据
　　　　　　var fileDataStr = hdFileData.val();

　　　　　　if (fileDataStr && opts.PostbackHold) {
　　　　　　　　   jsonData = JSON.parse(fileDataStr);
	　　　　　　$.each(jsonData.fileList, function (index, fileData) {
		　　　　　　var newid = S4();
		　　　　　　fileData.queueId = newid;
		　　　　　　$list.append('<div id="' + newid + '" class="item">' +
		　　　　　　　　'<div class="info">' + fileData.name + '</div>' +
		　　　　　　　　'<div class="state">已上传</div>' +
		　　　　　　　　'<div class="del"></div>' +
		　　　　　　　　'</div>');
	　　　　　	});
　　　　　　	hdFileData.val(JSON.stringify(jsonData));
　　　　　　}




        uploader.on('fileQueued', function (file) {//队列事件
        	
        	if(!file.type || file.type.indexOf("image") ==-1){
        		 $list.append('<div id="' + file.id + '" class="item">' +
        	                '<div class="info">' + file.name + '</div>' +
        	                '<div class="state">等待上传...</div>' +
        	                '<div class="del">删除</div>' +
        	            '</div>');
        	}else{
        		var $li = $('<div id="' + file.id + '" class="item" style="float:left;margin:15px 10px 10px 0 ;">' +
        					'<div class="pic-box" ><img></div>'+
        					'<div class="info" >' + file.name + '</div>' +
        					'<div class="state" >等待上传...</div>'+
        					'<div class="del">删除</div>' +
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
        	}
           
        });
        uploader.on('uploadProgress', function (file, percentage) {//进度条事件
            var $li = target.find('#' + file.id),
                $percent = $li.find('.progress .bar');
 
            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<span class="progress">' +
                    '<span  class="percentage"><span class="text"></span>' +
                  '<span class="bar" role="progressbar" style="width: 0%">' +
                  '</span></span>' +
                '</span>').appendTo($li).find('.bar');
            }
 
            $li.find('div.state').text('上传中');
            $li.find(".text").text(Math.round(percentage * 100) + '%');
            $percent.css('width', percentage * 100 + '%');
        });
        
        uploader.on('uploadSuccess', function (file, response) {//上传成功事件
            target.find('#' + file.id).find('div.state').text('已上传');
            var fileEvent = {
                queueId: file.id,
                name: file.name,
                size: file.size,
                type: file.type,
                filePath: response.data.savePath
            };
            jsonData.fileList.push(fileEvent)
            opts.onComplete(fileEvent);
 
        });
 
        uploader.on('uploadError', function (file) {
            target.find('#' + file.id).find('div.state').text('上传出错');
        });
 
        uploader.on('uploadComplete', function (file) {//全部完成事件
            target.find('#' + file.id).find('.progress').fadeOut();
            var fp = $("#" + opts.hiddenInputId);
           // console.log("jsonData="+JSON.stringify(jsonData));
            var url ='';
            for(var i = 0;i<jsonData.fileList.length ; i++){
            	url += jsonData.fileList[i].filePath+"|";
            }
            fp.val(url.substring(0,url.length-1));
            //console.log( " fp.val()="+fp.val());
            opts.onAllComplete(jsonData.fileList);
        });
 
        uploader.on('fileQueued', function (file) {
            uploader.upload();
        });
 
        uploader.on('filesQueued', function (file) {
            uploader.upload();
        });
 
        uploader.on('all', function (type) {
            if (type === 'startUpload') {
                state = 'uploading';
            } else if (type === 'stopUpload') {
                state = 'paused';
            } else if (type === 'uploadFinished') {
                state = 'done';
            }
 
            if (state === 'uploading') {
                $btn.text('暂停上传');
            } else {
                $btn.text('开始上传');
            }
        });
        
        $btn.on('click', function () {
            if (state === 'uploading') {
                uploader.stop();
            } else {
                uploader.upload();
            }
        });
        //删除文件
        $list.on("click", ".del", function () {
            var $ele = $(this);
            var id = $ele.parent().attr("id");
            var deletefile = {};
            $.each(jsonData.fileList, function (index, item) {
                if (item && item.queueId === id) {
　　　　　　　　　　　　　　   uploader.removeFile(uploader.getFile(id));//不要遗漏
                    deletefile = jsonData.fileList.splice(index, 1)[0];
                    $("#" + opts.hiddenInputId).val(JSON.stringify(jsonData));
//                    console.log($("#" + opts.hiddenInputId).val());
//                    console.log(deletefile.filePath);
                    $.post(applicationPath + "/file/deleteFile", {  'filePath': deletefile.filePath }, function (returnData) {
                    	if(returnData.result == 1){
                    		$ele.parent().remove();
                    	}else{
                    		$ele.parent().find('div.state').text('上传出错'+returnData.msg);
                    		
                    	}
                    	 return;	
                    },'json');
                    return;
                }
            });
        });
 
    }
    
  
   /* $.extend({
        includePath: '',
        include: function(file){
        	var files = typeof file == "string" ? [file] : file;

            for (var i = 0; i < files.length; i++) {
                var name = files[i].replace(/^s|s$/g, "");
                var att = name.split('.');
                var ext = att[att.length - 1].toLowerCase();
                var isCSS = ext == "css";
                var tag = isCSS ? "link" : "script";
                var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
                var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
                if ($(tag + "[" + link + "]").length == 0) 
                    document.write("<" + tag + attr + link + "></" + tag + ">");
            }
        }
    });*/

    //$.fn是指jquery的命名空间，加上fn上的方法及属性，会对jquery实例每一个有效。
    $.fn.powerWebUpload = function (options) {
        	var ele = this;
        	initWebUpload(ele, options);
    }
})(jQuery, window);