package com.tianwen.dcdp.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	/**
	 * 日期比较
	 * @param one
	 * @param two
	 * @return
	 */
	public static boolean compare(Date one,Date two){
		if(null == one || null == two){
		     return false;	
		}
		return one.getTime()-two.getTime()>0;
	}
	
	public static Long strDateToLong(String date) {
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d.getTime();
	}
	
	public static String longDateToStr(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date(date);
		return format.format(d);
	}
	
	public static Long strDateToLong(String date, String pattern) {
		SimpleDateFormat format =  new SimpleDateFormat(General.isEmpty(pattern)? "yyyy-MM-dd" : pattern);
		Date d = null;
		try {
			d = General.isEmpty(date)?new Date():format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d.getTime();
	}
	
	public static String longDateToStr(long date, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(General.isEmpty(pattern) ? "yyyy-MM-dd" : pattern);
		Date d = new Date(date);
		return format.format(d);
	}
	
	public static String dateToString(Date date){
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return dateStr;
	}
	
}
