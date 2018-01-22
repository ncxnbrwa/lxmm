package com.nuocf.yshuobang.utils;

import android.content.Context;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HTimeUtils {
	public final static String LONGEST_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String LONG_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String SHORT_FORMAT = "yyyy-MM-dd";
	public final static String TIME_FORMAT = "HH:mm:ss";
	public final static String TIME_SHORT_FORMAT = "HH:mm";
	private static SimpleDateFormat formatter = new SimpleDateFormat();

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static Date getNowDateLongest() {
		return getNowDate(LONGEST_FORMAT);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		return getNowDate(LONG_FORMAT);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		return getNowDate(SHORT_FORMAT);
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static Date getNowTimeShort() {
		return getNowDate(TIME_FORMAT);
	}

	/**
	 * 获取现在时间
	 * 
	 * @param timeFormat
	 *            返回时间格式
	 */
	public static Date getNowDate(String timeFormat) {
		Date currentTime = new Date();
		Date currentTime_2 = null;
		synchronized (formatter) {
			formatter.applyPattern(timeFormat);
			String dateString = formatter.format(currentTime);
			ParsePosition pos = new ParsePosition(0);
			currentTime_2 = formatter.parse(dateString, pos);
		}
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static String getStringDateLongest() {
		return getStringDate(LONGEST_FORMAT);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		return getStringDate(LONG_FORMAT);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		return getStringDate(SHORT_FORMAT);
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		return getStringDate(TIME_FORMAT);
	}

	/**
	 * 获取现在时间
	 * 
	 * @param 返回字符串格式
	 */
	public static String getStringDate(String timeFormat) {
		java.util.Date currentTime = new java.util.Date();
		String dateString = null;
		synchronized (formatter) {
			formatter.applyPattern(timeFormat);
			dateString = formatter.format(currentTime);
		}
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToLongDateLongest(String strDate) {
		return strToDate(strDate, LONGEST_FORMAT);
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToLongDate(String strDate) {
		return strToDate(strDate, LONG_FORMAT);
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToShortDate(String strDate) {
		return strToDate(strDate, SHORT_FORMAT);
	}

	/**
	 * 将时间格式字符串转换为时间 HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToTimeDate(String strDate) {
		return strToDate(strDate, TIME_FORMAT);
	}

	/**
	 * 获取时间 小时:分 HH:mm
	 * 
	 * @return
	 */
	public static Date strTimeMoreShort(String strDate) {
		return strToDate(strDate, TIME_SHORT_FORMAT);
	}

	/**
	 * 按指定的时间格式字符串转换为时间
	 * 
	 * @param strDate
	 * @param timeFormat
	 * @return
	 */
	public static Date strToDate(String strDate, String timeFormat) {
		Date strtodate = null;
		synchronized (formatter) {
			formatter.applyPattern(timeFormat);
			ParsePosition pos = new ParsePosition(0);
			strtodate = formatter.parse(strDate, pos);
		}
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToLongestStr(Date dateDate) {
		return dateToStr(dateDate, LONGEST_FORMAT);
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToLongStr(Date dateDate) {
		return dateToStr(dateDate, LONG_FORMAT);
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static String dateToShortStr(Date dateDate) {
		return dateToStr(dateDate, SHORT_FORMAT);
	}

	/**
	 * 将时间格式字符串转换为时间 HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static String dateToTimeStr(Date dateDate) {
		return dateToStr(dateDate, TIME_FORMAT);
	}

	/**
	 * 按指定的时间格式时间转换为字符串
	 * 
	 * @param dateDate
	 * @param timeFormat
	 * @return
	 */
	public static String dateToStr(Date dateDate, String timeFormat) {
		String dateString = null;
		synchronized (formatter) {
			formatter.applyPattern(timeFormat);
			dateString = formatter.format(dateDate);
		}
		return dateString;
	}

	public static String LongToStr(long m, String timeFormat) {
		String dateString = null;
		synchronized (formatter) {
			formatter.applyPattern(timeFormat);
			dateString = formatter.format(new Date(m));
		}
		return dateString;
	}

	public final static int DAYTIME = 1000 * 60 * 60 * 24;

	/**
	 * 获得今天是星期几
	 * 
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate(Context context) {
		Date dt = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		if (context.getResources().getConfiguration().locale
				.equals(Locale.CHINA)
				|| context.getResources().getConfiguration().locale
						.equals(Locale.TRADITIONAL_CHINESE)) {
			String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
					"星期六" };
			return weekDays[w];
		} else {
			String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday",
					"Thursday", "Friday", "Saturday" };
			return weekDays[w];
		}

	}

	public static String getMonth(Context context) {
		int d[] = HTimeUtils.getDate();
		String string = null;
		if (context.getResources().getConfiguration().locale
				.equals(Locale.CHINA)
				|| context.getResources().getConfiguration().locale
						.equals(Locale.TRADITIONAL_CHINESE)) {
			string = String.valueOf(d[1]) + "月";
		}

		else {

			String[] month = { "January", "February", "March", "April", "May",
					"June", "July", "August", "September", "October",
					"November", "December" };
			string = String.valueOf(month[d[1] - 1]);
		}

		return string;

	}

	/**
	 * 获取年月
	 * 
	 * @return
	 */
	public static int[] getDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		return new int[] { year, month, day };
	}

	/**
	 * 将长时间类型转换成yyyy-MM-dd HH:mm:ss时间类型
	 * 
	 * @param time
	 * @return
	 */
	public static String longToTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date dt = new Date(time);
		String sDateTime = sdf.format(dt); // 得到精确到秒的表示：2006-08-31 21:08:00
		return sDateTime;
	}

	/**
	 * 获取格林威治时间
	 */
	public static String getGelinData() {
		Date data = new Date();
		return data.toGMTString();

	}

	/**
	 * 获取unix时间戳
	 * 
	 * @return long
	 */
	public static long getUnixTimestamp() {
		long epoch = System.currentTimeMillis() / 1000;
		return epoch;
	}

	/**
	 * unix时间戳转换为HH:MM时间格式
	 * 
	 * @return long
	 */
	public static String unixTime2Date(long timestamp) {
		String date = new java.text.SimpleDateFormat("HH:mm")
				.format(new java.util.Date(timestamp * 1000));
		return date;
	}

	public static String unixTime3Date(long timestamp) {
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd")
				.format(new java.util.Date(timestamp * 1000));
		return date;
	}
	
	public static String MonthDay(long timestamp) {

		long currentTime = System.currentTimeMillis() / 1000;

		String[] otherday = date(timestamp);

		String[] today = date(currentTime);

		if (otherday.length < 5 || today.length < 5)
			return "日期错误";

		int today_otherday = Integer.parseInt(today[2])
				- Integer.parseInt(otherday[2]);
		boolean atSameMonth = today[1].equals(otherday[1]);

		try {
			// 判断是否是同一天
			if (atSameMonth && today_otherday == 0)
				return "今天"
						+ detailTime(Integer.parseInt(otherday[3]), otherday[4]);
			// 判断是否是昨天
			else if (atSameMonth
					&& (today_otherday == 1 || today_otherday == -27
							|| today_otherday == -28 || today_otherday == -29 || today_otherday == -30))
				return "昨天"
						+ detailTime(Integer.parseInt(otherday[3]), otherday[4]);
			else
				return otherday[1]
						+ "月"
						+ otherday[2]
						+ "日"
						+ detailTime(Integer.parseInt(otherday[3]), otherday[4]);

		} finally {
			otherday = null;
			today =null;
		}
	}

	public static String detailTime(int hour, String min) {

		if (hour >= 18)
			return "晚上 " + hour + ":" + min;
		else if (hour >= 12)
			return "下午 " + hour + ":" + min;
		else if (hour >= 6)
			return "上午 " + hour + ":" + min;
		else if (hour >= 0)
			return "凌晨 " + hour + ":" + min;
		else
			return "未知时辰";
	}

	public static String detailDate(long lastTime, long timestamp) {

		// 判断是否是在同一分钟内用lastTime/60==timestamp/60，不能用(lastTime-timestamp)/60==0
		boolean isAtSameMin = lastTime / 60 == timestamp / 60;
		if (lastTime != 0 && isAtSameMin) {
			return "";
		}
		return MonthDay(timestamp);
	}

	public static String[] date(long timestamp) {
		String date = new SimpleDateFormat("yyyy-MM-dd-HH-mm")
				.format(new Date(timestamp * 1000));
		return date.split("-");
	}
	
	public static boolean atSameDay(long otherdate,long today){
		
		String otherd = new SimpleDateFormat("yyyy-MM-dd").format(new Date(otherdate));
		String tod = new SimpleDateFormat("yyyy-MM-dd").format(new Date(today));
		
		return otherd.equals(tod);
	}
	
	public static boolean after24Hour(long lastTime){
		long currentTime = System.currentTimeMillis();
		if(currentTime-lastTime>86400000)
			return true;
		return false;
		
	}
	public static boolean after2Hour(long lastTime){
		long currentTime = System.currentTimeMillis();
		if(currentTime-lastTime>7200000)
			return true;
		return false;
	}
	
	public static String getWeekDayString(long timestamp){

		String weekString = null;
	
		final String dayNames[] = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		
		Calendar calendar = Calendar.getInstance();
	
		Date date = new Date(timestamp);
	
		calendar.setTime(date); 
		
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	
		weekString = dayNames[dayOfWeek - 1];

		return weekString;
	}
	
	public static long getJson2Date(String date){
		
		String time = (date.replace("/Date(", "")).replace(")\\", "");
		long timestamp = 0;
		if(time.contains("+")){
			try{
				timestamp = Long.parseLong(time.substring(0, time.indexOf("+")));
			}catch(Exception e){
			}
		}
		return timestamp;
	}
	
}