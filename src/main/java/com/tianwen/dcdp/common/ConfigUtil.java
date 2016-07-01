package com.tianwen.dcdp.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 系统配置文件读取类 用于读取系统配置
 * 
 * @author 乔广庆
 * @date 2012-01-10
 */
public class ConfigUtil {
	private static final Logger logger = Logger.getLogger(ConfigUtil.class);
	
	private static Properties properties = new Properties();
	
	public static final String parentDir = "upload.parent.location";
	
	public static final String childThumDir = "upload.child.thumbnail.location";
	
	public static final String childMediaDir = "upload.child.media.location";
	
	public static final String idxDir = "idx.parent.location";
	
	public static final String enrollDir = "enroll.parent.location";

	// 配置文件路径
	private static final String PATH = Thread.currentThread().getContextClassLoader().getResource("config.properties").getFile();
	
	//WebRoot路径
	private static final String WEBROOT = new File(Thread.currentThread().getContextClassLoader().getResource("config.properties").getFile()).getParentFile().getParentFile().getParentFile().getAbsolutePath();
	
	static {
		try {
			logger.info("初始化系统配置项目");
			properties.load(new FileInputStream(PATH));
			logger.info("初始化系统配置项目成功");
		} catch (Exception ex) {
			logger.error("读取系统配置文件 " + PATH + " 错误. Cause by:"
					+ ex.getMessage());
		}
	}

	/**
	 * 依据Key取得配置内容
	 * 
	 * @param ekeyy
	 * @return
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static Properties getProperties() {
		return properties;
	}
	
	/**
	 * 获取当前webRoot工程路径
	 * @return
	 */
	public static String getWebRootPath(){
		return WEBROOT;
	}
}
