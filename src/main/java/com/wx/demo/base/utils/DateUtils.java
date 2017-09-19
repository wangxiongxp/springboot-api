package com.wx.demo.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangxiong on 2017/3/18.
 */
public class DateUtils {
	
	/**定义常量**/
    public static final String DATE_JFP_STR = "yyyyMM";
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";
	
    /**
     * 当前日期转换为指定格式字符串
     * @param format
     * @return
     */
	public static String getNowTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }
	
	/**
	 * 当前日期字符串HH:mm:ss
	 * @return
	 */
	public static String getTime() {
		Date date = new Date(System.currentTimeMillis()); 
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		return formatter.format(date);
	}
	
	/**
	 * 当前日期字符串yyyy-MM-dd 
	 * @return
	 */
	public static String getDate() {
		Date date = new Date(System.currentTimeMillis()); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	/**
	 * 当前日期字符串yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDateTime() {
		Date date = new Date(System.currentTimeMillis()); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}
	
	/**
	 * 将指定日期转换为指定格式的字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateFormat(Date date, String format) {
        if (date == null) {
            return "";
        }
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
	
	/**
     * 判断入口参数是否为Date，是，返回true；
     * @param vl
     * @return
     */
    public static boolean checkDate(String vl){
        try{
        	DateFormat format = DateFormat.getDateInstance();
        	format.parse(vl);
            return true;
        }catch(Exception x){
            return false;
        }
    }

    public static String getDateTimeStrByTimeMillis(long timeMillis) {
		Date date = new Date(timeMillis);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public static long getTimestampByDateTimeStr(String dateTimeStr) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = formatter.parse(dateTimeStr);
		return date.getTime();
	}

	public static void main(String[] args)
	{
	}
	
}
