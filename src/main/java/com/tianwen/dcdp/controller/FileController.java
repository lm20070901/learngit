package com.tianwen.dcdp.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tianwen.dcdp.common.Constants;
import com.tianwen.dcdp.common.DateUtils;
import com.tianwen.dcdp.common.EdwManageConstants;


/**
 * 文件上传公共类 
 * @author jiangyx 2016.05.20 
 *
 */
@Controller
@RequestMapping("/file")
public class FileController {
		
		private static final Logger logger = Logger.getLogger(FileController.class);
		
		@Resource
		private EdwManageConstants edwFrontConstants;
		
		
		/**
		 * 图片上传的公共接口
		 * 
		 * @param folder
		 *            文件存储路径 根据用途分 比如 上传的文件是属于动态模块 folder = content
		 * @param files
		 *            上传的文件 可以是多个
		 * @param request
		 * @return
		 */
		@RequestMapping("/uploadPic")
		@ResponseBody
		public Map<String, Object> uploadPic(
				@RequestParam(value = "folder", required = false) String folder,
				@RequestParam(value = "images", required = false) CommonsMultipartFile[] images,
				HttpServletRequest request) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 参数判断
			if (StringUtils.isEmpty(folder) || null == images || images.length <= 0) {
				resultMap.put("result", Constants.FAILURE);
				resultMap.put("msg", Constants.NULL_PARM_MSG);
				return resultMap;
			}
	
			try {
				String curDir = new SimpleDateFormat("yyyyMMdd").format(new Date());
				// 上传文件存储目录（物理路径）
				String subDir = StringUtils.isEmpty(folder) ? "default" : folder;
	
				String storeDir = edwFrontConstants.STATIC_FILE_PATH + subDir + "/"+ curDir;
	
				StringBuilder sb = new StringBuilder();
	
				for (CommonsMultipartFile image : images) {
	
					if (null == image || image.isEmpty())
						continue;
	
					String origFileName = image.getOriginalFilename();
					// 上传文件起别名
					String newFileName = DateUtils.longDateToStr(System.currentTimeMillis(), "yyyyMMddHHmmss")+ "_" + origFileName;
					File targetFile = new File(storeDir, newFileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					// 文件上传
					image.transferTo(targetFile);
	
					// 文件保存路径
					sb.append(folder);
					sb.append("/");
					sb.append(curDir);
					sb.append("/");
					sb.append(newFileName);
					sb.append("|");
				}
				Map<String, Object> pathMap = new HashMap<String, Object>();
				pathMap.put("commonPath", edwFrontConstants.HOST_NAME);    // 访问路径 返回给前端
				pathMap.put("savePath", sb.substring(0, sb.length() - 1)); // 存储路径 保存的到数据库中的字段
				
				resultMap.put("result", Constants.SUCCESS);
				resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
				resultMap.put("data", pathMap);
			} catch (Exception e) {
				logger.error(e.getMessage());
				resultMap.put("result", Constants.FAILURE);
				resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
				return resultMap;
	
			}
	
			return resultMap;
		}
		
		
		
		/**
		 *    上传文件  
		 * @param folder  上传文件存储路径 
		 * @param uploadFile  媒体文件
		 * @param request 
		 * @return
		 */
		@RequestMapping("/uploadFile")
		@ResponseBody
		public Map<String, Object> uploadFile(
				@RequestParam(value = "folder",required = false) String folder,
				@RequestParam(value = "files", required = false) CommonsMultipartFile[]  files,
				HttpServletRequest request) {
			Map<String,Object> resultMap = new HashMap<String,Object>();
			
			//参数判断
			if(StringUtils.isEmpty(folder) || null == files || files.length <= 0){
				resultMap.put("result", Constants.FAILURE);
				resultMap.put("msg", Constants.NULL_PARM_MSG);
				return resultMap;
			}
			try {
				String curDir = DateUtils.longDateToStr(
						System.currentTimeMillis(), "yyyyMMdd");
				// 上传视频文件存储目录（物理路径）
				//String subDir = edwFrontConstants.CHILDMEDIADIR;
				String storeDir =  edwFrontConstants.STATIC_FILE_PATH  + folder +"/"+ curDir;
				StringBuilder sb = new StringBuilder();
				for(CommonsMultipartFile file :files){
							
					if (file == null || file.isEmpty()) 
						continue;
						// 上传文件起别名
						String origFileName = file.getOriginalFilename();
						
						String newFileName = DateUtils.longDateToStr(System.currentTimeMillis(), "yyyyMMddHHmmss")+ "_" +origFileName;

						File targetFile = new File(storeDir, newFileName);
						
						if (!targetFile.exists()) {
							targetFile.mkdirs();
						}
						file.transferTo(targetFile);

						// 缩略图网络访问路径
						sb.append(folder);
						sb.append("/");
						sb.append(curDir);
						sb.append("/");
						sb.append(newFileName);
						sb.append("|");
					
				}
				Map<String, Object> pathMap = new HashMap<String, Object>();
				pathMap.put("commonPath", edwFrontConstants.HOST_NAME);    // 访问路径 返回给前端
				pathMap.put("savePath", sb.substring(0, sb.length() - 1));// 存储路径 保存的到数据库中的字段
				
				resultMap.put("result", Constants.SUCCESS);
				resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
				resultMap.put("data", pathMap);
			} catch (Exception e) {
				logger.error(e.getMessage());
				resultMap.put("result", Constants.FAILURE);
				resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
				return resultMap;
			}

			return resultMap;
		}
		
		
		
		/**
		 * 删除上传文件  
		 * @param filePath
		 * @param request
		 * @return
		 */
		@RequestMapping("/deleteFile")
		@ResponseBody
		public Map<String,Object> deleteFile(
				@RequestParam(value = "filePath",required = false) String filePath,
				HttpServletRequest request){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			//参数判断
			if(StringUtils.isEmpty(filePath) ){
				resultMap.put("result", Constants.FAILURE);
				resultMap.put("msg", Constants.NULL_PARM_MSG);
				return resultMap;
			}
			String  matches = "[A-Za-z]:\\\\[^:?\"><*]*"; 
			if(filePath.matches(matches)){
				resultMap.put("result", Constants.FAILURE);
				resultMap.put("msg", "文件路径有误！");
				return resultMap;
			}
			try{
				File file = new File(edwFrontConstants.STATIC_FILE_PATH+filePath);
				if( file.exists() ){
					if(file.isFile() ){
						 file.delete();
						 resultMap.put("result", Constants.SUCCESS);
					     resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
					}else{
						resultMap.put("result", Constants.FAILURE);
						resultMap.put("msg", "目标路径不是文件！");
					}
				}else{
					resultMap.put("result", Constants.FAILURE);
					resultMap.put("msg", "目标路径文件不存在！");
				}
			}catch (Exception e){
				logger.error(e.getMessage());
				resultMap.put("result", Constants.FAILURE);
				resultMap.put("msg", Constants.SYSTEM_ERROR_MSG);
			}
			
			return resultMap;
		}
}
