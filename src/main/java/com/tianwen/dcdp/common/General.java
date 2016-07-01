package com.tianwen.dcdp.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class General {
	private static String[] dect = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7",
			"8", "9" };

	/**
	 * 获取指定位数的随机字符串【A-Z 0-9】
	 * @param length
	 * @return
	 */
	public static String getRandomStr(int length) {
		StringBuffer sb = new StringBuffer(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(dect[Math.abs(random.nextInt()) % dect.length]);
		}
		return sb.toString();
	}

	/**
	 * 时间字符串转换为long
	 * @param timeStr
	 * @param format
	 * @return
	 */
	public static long timeStr2Long(String timeStr, String format) {
		if (format == null)
			format = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.parse(timeStr).getTime();
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * long时间转换为字符串
	 * @param timeLong
	 * @param format
	 * @return
	 */
	public static String timeLong2Str(Long timeLong, String format) {
		if (timeLong == null)
			return null;

		if (format == null)
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date(timeLong);
		return formatter.format(date);
	}

	/**
	 * 获取当前时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getNow() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
	}
	
	/**
	 * 依据格式获取当前时间
	 * @param format
	 * @return
	 */
	public static String getNow(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		return formatter.format(date);
	}
	/**
	 * 依据格式获取当前时间
	 * @param format
	 * @param isAddDay 加一天，还是加一月
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getNowAdd(String format,boolean isAddDay) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date=new Date();//取时间 
	     Calendar   calendar   =   new   GregorianCalendar(); 
	     calendar.setTime(date); 
	     if(isAddDay)//加一天
	     calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
	     else//加一月
	     {
			calendar.add(calendar.MONTH,1);
		}
	     date=calendar.getTime(); 
		return formatter.format(date);
	}
	/**
	 * 依据格式获取当前时间减一天或一月
	 * @param format
	 * @param isAddDay 减一天，还是减一月
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getNowMinus(String format,boolean isDay) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date=new Date();//取时间 
	     Calendar   calendar   =   new   GregorianCalendar(); 
	     calendar.setTime(date); 
	     if(isDay)//加一天
	     calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动 
	     else//加一月
	     {
			calendar.add(calendar.MONTH,-1);
		}
	     date=calendar.getTime(); 
		return formatter.format(date);
	}
	/**
	 * 判断字符串是否为空(包含null与"")
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str))
			return true;
		return false;
	}

	/**
	 * 判断字符串是否为非空(包含null与"")
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null || "".equals(str))
			return false;
		return true;
	}
	
	/**
	 * 判断对象是否为非空(包含null与"")
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		if (str == null || "".equals(str))
			return false;
		return true;
	}

	/**
	 * 字符串去空格方法
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (str == null)
			return "";
		return str.trim();
	}

	/**
	 * 替换字符串
	 * @param src 源字符串
	 * @param oldstr 要替换的字符串
	 * @param newstr 新字符串
	 * @return
	 */
	public static String replace(String src, String oldstr, String newstr) {
		StringBuffer dest = new StringBuffer();
		int beginIndex = 0;
		int endIndex = 0;
		while (true) {
			endIndex = src.indexOf(oldstr, beginIndex);
			if (endIndex >= 0) {
				dest.append(src.substring(beginIndex, endIndex));
				dest.append(newstr);
				beginIndex = endIndex + oldstr.length();
			} else {
				dest.append(src.substring(beginIndex));
				break;
			}
		}
		return dest.toString();
	}

	/**
	 * 防止SQL注入
	 * @param str
	 * @return
	 */
	public static String sqlStr(String str) {
		if (str != null) {
			str = replace(str, "'", "");
			str = replace(str, " ", "");
			str = replace(str, "\\", "&#92;");
		}
		return str;
	}

	/**
	 * 日期类型转换成yyyy-MM-ddTHH:mm:ss.SSSzzzzz+08.00格式字符串
	 * @param Date date
	 * @return String
	 */
	public static String xmlDateTime2xmlDateTimeStr(Date date) {
		if (date == null)
			return null;

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		XMLGregorianCalendar calendar;
		try {
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(
					(GregorianCalendar) ca);
		} catch (Exception ex) {
			return null;
		}
		return calendar.toString();
	}

	/**
	 * 获取当前时间戳
	 * @return 字符串形式的时间戳
	 */
	public static String getTimestamp() {
		return xmlDateTime2xmlDateTimeStr(new Date());
	}

	/**
	 * 将空字符串转换为""
	 * @param str
	 * @return
	 */
	public static String convertNullToEmpty(String str) {
		if (General.isEmpty(str))
			return "";
		return str;
	}

	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * URL编码方法
	 * @param url
	 * @param encode 字符集 默认为UTF-8
	 * @return
	 */
	public static String encodeURL(String url, String encode) {
		if (General.isEmpty(encode))
			encode = "utf8";
		try {
			return java.net.URLEncoder.encode(url, encode);
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

	/**
	 * URL解码方法
	 * @param url
	 * @param decode 字符集 默认为UTF-8
	 * @return
	 */
	public static String decodeURL(String url, String decode) {
		String u = url;
		if (General.isEmpty(decode))
			decode = "utf8";
		try {
			u = java.net.URLDecoder.decode(url, decode);
		} catch (Exception e) {
			return url;
		}
		return u;
	}

	/**
	 * 用指定分隔符分隔字符串
	 * @param str
	 * @param separator
	 * @return
	 */
	public static List<String> segmentationStr(String str, String separator) {
		List<String> result = new ArrayList<String>();
		if (General.isEmpty(str))
			return result;
		StringTokenizer token = new StringTokenizer(str, separator);
		while (token.hasMoreElements()) {
			result.add(token.nextToken());
		}
		return result;
	}

	/**
	 * 判断字符串是否为合法手机号 11位 13 14 15 18开头
	 * @param str
	 * @return boolean
	 */
	public static boolean isMobile(String str) {
		if (General.isEmpty(str))
			return false;
		return str.matches("^(13|14|15|18)\\d{9}$");
	}

	/**
	 * 判断是否为正确的邮件格式
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmail(String str) {
		if (General.isEmpty(str))
			return false;
		return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
	}

	/**
	 * HTML字符串编码
	 * @param str
	 * @return
	 */
	public static String HTMLEncode(String str) {
		if (str != null) {
			//需要放在前面替换，以防止替换掉其他已经替换过一次的，如 "\\", "&#92;"  lixinglei modify  at 2012-06-15
			str = replace(str, "&", "&amp;");
			str = replace(str, "'", "&#39;");
			str = replace(str, "\"", "&quot;");
			str = replace(str, "<", "&lt;");
			str = replace(str, ">", "&gt;");
			str = replace(str, "<<", "&raquo;");
			str = replace(str, ">>", "&laquo;");
			str = replace(str, "'", ""); //??
			str = replace(str, "\"", ""); //??
			str = replace(str, "\\r\\n", "\n"); //lixinglei modify  at 2012-06-15
			str = replace(str, "\\n", "<br>"); //lixinglei modify  at 2012-06-15
			str = replace(str, "\r\n", "\n");
			str = replace(str, "\n", "<br>");
			//需要放在\n之后替换，以防止替换掉\n的\ lixinglei modify  at 2012-06-15
			str = replace(str, "\\", "&#92;");
			str = replace(str, "  ", "　");
			str = replace(str, "&amp;amp;", "&amp;"); //??
			str = replace(str, "&amp;quot;", "&quot;"); //??
			str = replace(str, "&amp;lt;", "&lt;"); //??
			str = replace(str, "&amp;gt;", "&gt;"); //??
			str = replace(str, "&amp;nbsp;", "&nbsp;"); //??
		}
		return str;
	}

	/**
	 * 获得指定格式的时间
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String getDateByFormat(Date date, String formatStr) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sf = new SimpleDateFormat(formatStr);
		return sf.format(date);
	}

	/**
	 * 获取指定范围内不重复的随机数List
	 * @param start
	 * @param end
	 * @param size
	 * @return
	 */
	public static List<Integer> getRandomList(int start, int end, int size) {
		int maxCount = 10000;
		int curCount = 0;

		List<Integer> result = new ArrayList<Integer>();

		int curRandom = 0;
		Random random = null;
		while (result.size() < size) {
			//设置最大值防止死循环
			curCount++;
			if (curCount > maxCount)
				break;

			random = new Random(System.currentTimeMillis() + curCount);
			curRandom = Math.abs(random.nextInt()) % end + start;

			if (result.contains(curRandom))
				continue;
			result.add(curRandom);

		}
		return result;
	}

	/**
	 * 返回str的子串，起始位置为separator最后一次出现之后
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String substringAfterLast(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(separator)) {
			return "";
		}
		int pos = str.lastIndexOf(separator);

		if ((pos == -1) || (pos == str.length() - separator.length())) {
			return "";
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * 返回str的子串，结束位置为separator最后一次出现之前
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String substringBeforLast(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(separator)) {
			return "";
		}
		int pos = str.lastIndexOf(separator);

		if ((pos == -1) || (pos == str.length() - separator.length())) {
			return "";
		}

		return str.substring(0, pos);
	}

	/**
	 * 将list转换为字符串 1,2,3,4,5
	 * @param list
	 * @return
	 */
	public static String list2Str(List<Integer> list) {
		StringBuffer catalogIdsStr = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (catalogIdsStr.length() > 0)
					catalogIdsStr.append(",");
				catalogIdsStr.append(list.get(i));
			}
		}
		return catalogIdsStr.toString();
	}
	
	/**
	 * 格式化成标准路径
	 * @param str
	 * @return
	 */
	public static String formatPath(String str){
		if(General.isEmpty(str))
			return str;
		if(str.indexOf("\\/") != -1){
			str = General.replace(str, "\\/", File.separator);
		}
		if(str.indexOf("//") != -1)
			str = General.replace(str, "//", File.separator);
		if(str.indexOf("\\\\") != -1)
			str = General.replace(str, "\\\\", "\\");
		if(str.indexOf("/") != -1)
			str = General.replace(str, "/", File.separator);
		return str;
	}
	
	/**
	 * 格式化成标准的URL
	 * @param str
	 * @return
	 */
	public static String formatUrl(String str){
		if(General.isEmpty(str))
			return str;
		
		str = formatPath(str);
		return General.replace(str, File.separator, "/");
	}

	public static <T extends Object> boolean isNotEmpty(List<T> list) {
		if(list == null || list.size() <= 0){
			return false;
		}
		return true;
	}
	

	/**
	 * 获取用户IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
		String ip="";
    	ip = request.getHeader("x-forwarded-for");
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    		ip = request.getHeader("Proxy-Client-IP");
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    		ip = request.getHeader("WL-Proxy-Client-IP");
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    		ip = request.getRemoteAddr();
    	}
    	List<String> list = General.segmentationStr(ip, ",");
    	
    	if(list.size()>1){
    		return list.get(0);
    	}
    	return ip;
	}
	
	/**
	 * URL参数编码
	 * @param url
	 * @return
	 */
	public static String encodeURLParam(String url,String charset){
		if(url==null) return null;
		int rex = url.indexOf("=");
		if(rex == -1) return url;
		
		StringBuffer sb = new StringBuffer();
		List<String> list = General.segmentationStr(url, "=");
		for(int i=0;i<list.size();i++){
			String temp = list.get(i);
			if(i==0) sb.append(temp).append("=");
			else{
				if(temp.indexOf("&") ==0){
					sb.append(temp).append("=");
				}else if(temp.indexOf("&") == -1)
					sb.append(General.encodeURL(temp, charset));
				else{
					sb.append(General.encodeURL(temp.substring(0, temp.indexOf("&")), charset)).append(temp.substring(temp.indexOf("&")));
					sb.append("=");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将逗号分隔（id）的字符串转换为list
	 * @param str
	 * @return
	 */
	public static List<Integer> strSplitToList(String str) {
		List<Integer> result = new ArrayList<Integer>();
		String[] strArr = str.split(",");
		for(String st : strArr) {
			result.add(Integer.parseInt(st));
		}
		return result;
	}
}
