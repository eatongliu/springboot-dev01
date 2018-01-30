package com.apabi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String SOLR_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static String DATE_ONLY_FORMAT = "yyyy-MM-dd";
	public static String CASSANDRA_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String formatDate(Date date) {
		if (date == null) return null;
		return formatDate(date, null);
	}
	

	public static String formatDate(Date date, String format) {
		if (format == null) {
			format = DEFAULT_DATE_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String formatCurrentDate() {
		return currentDate(DEFAULT_DATE_FORMAT);
	}
	public static String currentDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}

	public static int PeriodRelation(Date oldStartDate, Date oldEndDate,
			Date newStartDate, Date newEndDate) {
		if (oldEndDate.after(newStartDate) && oldStartDate.before(newEndDate)) {
			return 1;// 1.old new部分重合
		} else if (oldStartDate.before(newEndDate)
				&& oldEndDate.after(newStartDate)) {
			return 2;// 2.new old部分重合
		} else if (oldEndDate.equals(newStartDate)) {
			return 3;// 3.old连接new
		} else if (oldStartDate.equals(newEndDate)) {
			return 4;// 4.new连接old
		} else if (oldEndDate.before(newStartDate)
				|| oldStartDate.after(newEndDate)) {
			return 5;// 5.不重合
		} else if (oldEndDate.before(newEndDate)
				&& oldStartDate.after(newStartDate)) {
			return 6;// 6.new包含old
		} else {
			return 7;// 7.old包含new
		}
	}

	public static String twoDateRelation(Date startDate, Date endDate) {
		Date now = new Date();
		// 开始时间大于结束时间
		if (startDate.after(endDate)) {
			return "error1";
		}
		// 开始时间小于当前时间
		if (startDate.before(now)) {
			return "error2";
		}
		return "right";
	}
	
	public static Date getDateFromString(String time, String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(time);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getDateFromString(String time){
		return getDateFromString(time,DEFAULT_DATE_FORMAT);
	}
	
	//比较两个日期是否为同一天
	public static boolean isSameDay(Date date1, Date date2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate1 = sdf.format(date1.getTime());
		String strDate2 = sdf.format(date2.getTime());
		if(strDate1.equals(strDate2)){
			return true;
		}
		return false;
	}
	
	//判断是否为今天
	public static boolean isToday(Date date){
		return isSameDay(date, new Date());
	}
	
	/**
	 * 获取指定天数前的日期
	 */
	public static Date getDateBeforeDays(Date date, int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);
		return calendar.getTime();
	}
	
	/**
	 * 获取指定天数后的日期
	 */
	public static Date getDateAfterDays(Date date, int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
		return calendar.getTime();
	}
	
	/**
	 * 获取指定分钟数之前的时间  
	 */
	public static Date getDateBeforeMinutes(Date date, int minutes){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - minutes);
		return calendar.getTime();
	}
	
	/**
	 * 计算两个日期之间相差的天数
	 */
	public static int minusTwoDate(Date date1, Date date2){
		long millseconds = Math.abs(date1.getTime() - date2.getTime());
		int days = (int) (millseconds/(24*60*60*1000));
		return days;
	}
	
	/**
	 * 计算当天最小时间，即00:00:00.000
	 */
	public static Date getDayMinTime(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		//将时分秒毫秒设置为0
		calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 计算当天最大时间，即23:59:59.999
	 */
	public static Date getDayMaxTime(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		//将时分秒毫秒设为23:59:59.999
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 计算第二天最小时间，即00:00:00.000
	 */
	public static Date getNextDayMinTime(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);	//计算下一天：天数加1
		//将时分秒毫秒设置为0
		calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 计算前一天的最大时间，即23:59:59.000
	 */
	public static Date getLastDayMaxTime(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);	//计算前一天：天数减1
		//将时分秒毫秒设为23:59:59.999
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	/**
	 * 根据给定的时间找到该时间所在星期的第一天
	 */
	public static Date getFirstDayOfWeek(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		//计算day属于一周的第几天
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;	//按中国习惯，以星期一为每星期第一天
		int days = dayOfWeek - 1;	//计算day和该星期的第一天之间的天数差
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);
		//将时分秒毫秒设置为0
		calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 根据给定的时间找到该时间所在星期的最后一天
	 */
	public static Date getLastDayOfWeek(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		//计算day属于一周的第几天
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;	//按中国习惯，以星期日为每星期最后一天
		int days = 7 - dayOfWeek;	//计算day和最后一天的时间差
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
		//将时分秒毫秒设为23:59:59.999
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 根据给定的时间找到该时间所在月的第一天
	 */
	public static Date getFirstDayOfMonth(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		//计算day属于一月的第几天
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);	
		int days = dayOfMonth - 1;	//计算day和该月的第一天之间的天数差
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);
		//将时分秒毫秒设置为0
		calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 根据给定的时间找到该时间所在月的最后一天
	 */
	public static Date getLastDayOfMonth(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		//计算day属于一月的第几天
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);	
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfMonth;
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
		//将时分秒毫秒设为23:59:59.999
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 根据给定的时间找到该时间所在季度的第一天
	 */
	public static Date getFirstDayOfQuarter(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int month = 0;
		if(currentMonth >= 1 && currentMonth <= 3){//第一季度
			month = Calendar.JANUARY;
		}else if(currentMonth >= 4 && currentMonth <= 6){//第二季度
			month = Calendar.APRIL;
		}else if(currentMonth >= 7 && currentMonth <= 9){//第三季度
			month = Calendar.JULY;
		}else{//第四季度
			month = Calendar.OCTOBER;
		}
		//设置为本季度第一天的00:00:00.000
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 根据给定的时间找到该时间所在季度的最后一天
	 */
	public static Date getLastDayOfQuarter(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int month = 0;
		if(currentMonth >= 1 && currentMonth <= 3){//第一季度
			month = Calendar.MARCH;
		}else if(currentMonth >= 4 && currentMonth <= 6){//第二季度
			month = Calendar.JUNE;
		}else if(currentMonth >= 7 && currentMonth <= 9){//第三季度
			month = Calendar.SEPTEMBER;
		}else{//第四季度
			month = Calendar.DECEMBER;
		}
		//设置为本季度最后一天的23:59:59.999
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 根据给定的时间找到该时间所在年的第一天
	 */
	public static Date getFirstDayOfYear(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		//设置为1月1日的00:00:00.000
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 根据给定的时间找到该时间所在年的最后一天
	 */
	public static Date getLastDayOfYear(Date day){
		String dayStr = formatDate(day, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		//设置为12月31日的00:00:00.000
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	/**
	 * 计算date所属的季度
	 */
	public static int getQuarter(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		if(currentMonth >= 1 && currentMonth <= 3){//第一季度
			return 1;
		}else if(currentMonth >= 4 && currentMonth <= 6){//第二季度
			return 2;
		}else if(currentMonth >= 7 && currentMonth <= 9){//第三季度
			return 3;
		}else{//第四季度
			return 4;
		}
	}
	
	//根据年份获取该年份最小的时间
	public static Date getFirstDateOfYear(int year){
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.JANUARY, c.getActualMinimum(Calendar.DAY_OF_MONTH), c.getActualMinimum(Calendar.HOUR_OF_DAY), c.getActualMinimum(Calendar.MINUTE), c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	//根据年份获取该年份最大的时间
	public static Date getLastDateOfYear(int year){
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.DECEMBER, c.getActualMaximum(Calendar.DAY_OF_MONTH), c.getActualMaximum(Calendar.HOUR_OF_DAY), c.getActualMaximum(Calendar.MINUTE), c.getActualMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	//根据年份和第几个季度，计算该年份该季度的最小时间
	public static Date getFirstDateOfQuarter(int year, int quarter){
		Calendar c = Calendar.getInstance();
		int month = Calendar.JANUARY;
		if(month == 2){
			month = Calendar.APRIL;
		}else if(month == 3){
			month = Calendar.JULY;
		}else if(month == 4){
			month = Calendar.OCTOBER;
		}
		c.set(year, month, c.getActualMinimum(Calendar.DAY_OF_MONTH), c.getActualMinimum(Calendar.HOUR_OF_DAY), c.getActualMinimum(Calendar.MINUTE), c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	//根据年份和第几个季度，计算该年份该季度的最大时间
	public static Date getLastDateOfQuarter(int year, int quarter){
		Calendar c = Calendar.getInstance();
		int month = Calendar.MARCH;
		if(month == 2){
			month = Calendar.JUNE;
		}else if(month == 3){
			month = Calendar.SEPTEMBER;
		}else if(month == 4){
			month = Calendar.DECEMBER;
		}
		c.set(year, month, c.getActualMaximum(Calendar.DAY_OF_MONTH), c.getActualMaximum(Calendar.HOUR_OF_DAY), c.getActualMaximum(Calendar.MINUTE), c.getActualMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	//根据年份和月份，计算该月最小的时间
	public static Date getFirstDateOfMonth(int year, int month){
		Calendar c = Calendar.getInstance();
		c.set(year, month, c.getActualMinimum(Calendar.DAY_OF_MONTH), c.getActualMinimum(Calendar.HOUR_OF_DAY), c.getActualMinimum(Calendar.MINUTE), c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	//根据年份和月份，计算该月最大的时间
	public static Date getLastDateOfMonth(int year, int month){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(year, month, c.get(Calendar.DAY_OF_MONTH), c.getActualMaximum(Calendar.HOUR_OF_DAY), c.getActualMaximum(Calendar.MINUTE), c.getActualMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	//根据年，月，日，计算该日期的最小时间
	public static Date getDateMin(int year, int month, int dayOfMonth){
		Calendar c = Calendar.getInstance();
		c.set(year, month, dayOfMonth, c.getActualMinimum(Calendar.HOUR_OF_DAY), c.getActualMinimum(Calendar.MINUTE), c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	//根据年，月，日，计算该日期的最大时间
	public static Date getDateMax(int year, int month, int dayOfMonth){
		Calendar c = Calendar.getInstance();
		c.set(year, month, dayOfMonth, c.getActualMaximum(Calendar.HOUR_OF_DAY), c.getActualMaximum(Calendar.MINUTE), c.getActualMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	//根据给定时间计算上月最小时间
	public static Date getMinTimeOfLastMonth(Date date){
		String dayStr = formatDate(date, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(tmpDate);
		c1.set(Calendar.YEAR, c1.get(Calendar.YEAR));
		c1.set(Calendar.MONTH, c1.get(Calendar.MONTH) - 1);
		c1.set(Calendar.DAY_OF_MONTH, c1.getActualMinimum(Calendar.DAY_OF_MONTH));
		c1.set(Calendar.HOUR, c1.getActualMinimum(Calendar.HOUR_OF_DAY));
		c1.set(Calendar.MINUTE, c1.getActualMinimum(Calendar.MINUTE));
		c1.set(Calendar.SECOND, c1.getActualMinimum(Calendar.SECOND));
		c1.set(Calendar.MILLISECOND, c1.getActualMinimum(Calendar.MILLISECOND));
		return c1.getTime();
	}
	
	//根据给定时间计算上月最大时间
	public static Date getMaxTimeOfLastMonth(Date date){
		String dayStr = formatDate(date, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(tmpDate);
		c1.set(Calendar.YEAR, c1.get(Calendar.YEAR));
		c1.set(Calendar.MONTH, c1.get(Calendar.MONTH) - 1);
		c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH));
		c1.set(Calendar.HOUR, c1.getActualMaximum(Calendar.HOUR_OF_DAY));
		c1.set(Calendar.MINUTE, c1.getActualMaximum(Calendar.MINUTE));
		c1.set(Calendar.SECOND, c1.getActualMaximum(Calendar.SECOND));
		c1.set(Calendar.MILLISECOND, c1.getActualMaximum(Calendar.MILLISECOND));
		return c1.getTime();
	}
	
	//根据给定的时间计算上个季度的最小时间
	public static Date getMinTimeOfLastQuarter(Date date){
		String dayStr = formatDate(date, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(tmpDate);
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		if(Calendar.JANUARY <= month && month <= Calendar.MARCH){
			c1.set(Calendar.YEAR, year - 1);
			c1.set(Calendar.MONTH, Calendar.OCTOBER);
		}else if(Calendar.APRIL <= month && month <= Calendar.JUNE){
			c1.set(Calendar.MONTH, Calendar.JANUARY);
		}else if(Calendar.JULY <= month && month <= Calendar.SEPTEMBER){
			c1.set(Calendar.MONTH, Calendar.APRIL);
		}else{
			c1.set(Calendar.MONTH, Calendar.JULY);
		}
		c1.set(Calendar.DAY_OF_MONTH, c1.getActualMinimum(Calendar.DAY_OF_MONTH));
		c1.set(Calendar.HOUR, c1.getActualMinimum(Calendar.HOUR_OF_DAY));
		c1.set(Calendar.MINUTE, c1.getActualMinimum(Calendar.MINUTE));
		c1.set(Calendar.SECOND, c1.getActualMinimum(Calendar.SECOND));
		c1.set(Calendar.MILLISECOND, c1.getActualMinimum(Calendar.MILLISECOND));
		return c1.getTime();
	}
	
	//根据给定的时间计算上个季度的最大时间
	public static Date getMaxTimeOfLastQuarter(Date date){
		String dayStr = formatDate(date, DATE_ONLY_FORMAT);
		Date tmpDate = getDateFromString(dayStr, DATE_ONLY_FORMAT);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(tmpDate);
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		if(Calendar.JANUARY <= month && month <= Calendar.MARCH){
			c1.set(Calendar.YEAR, year - 1);
			c1.set(Calendar.MONTH, Calendar.DECEMBER);
		}else if(Calendar.APRIL <= month && month <= Calendar.JUNE){
			c1.set(Calendar.MONTH, Calendar.MARCH);
		}else if(Calendar.JULY <= month && month <= Calendar.SEPTEMBER){
			c1.set(Calendar.MONTH, Calendar.JUNE);
		}else{
			c1.set(Calendar.MONTH, Calendar.SEPTEMBER);
		}
		c1.set(Calendar.HOUR, c1.getActualMaximum(Calendar.HOUR_OF_DAY));
		c1.set(Calendar.MINUTE, c1.getActualMaximum(Calendar.MINUTE));
		c1.set(Calendar.SECOND, c1.getActualMaximum(Calendar.SECOND));
		c1.set(Calendar.MILLISECOND, c1.getActualMaximum(Calendar.MILLISECOND));
		return c1.getTime();
	}
	
	public static Date getDateAfterYears(Date date, int years){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + years);
		return calendar.getTime();
	}
	
	public static Date strToDate(String dateStr) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat();
		return sdf.parse(dateStr);
	}
	
	public static void main(String[] args) {
//		System.out.println(isToday(getDateFromString("2014/07/03", "yyyy/MM/dd")));
//		Date date = getDateBeforeMinutes(new Date(), 10);
//		System.out.println(formatDate(date));
		
//		Date date1 = getDateFromString("2014-11-28", "yyyy-MM-dd");
//		Date date2 = getDateFromString("2014-12-02", "yyyy-MM-dd");
//		System.out.println(minusTwoDate(date1, date2));
//		Date d1 = getDateFromString("1970-01-02", "yyyy-MM-dd");
//		Date d2 = getDateFromString("9999-12-31 23:59:59.999", "yyyy-MM-dd HH:mm:ss.SSS");
//		System.out.println(d2.getTime()/d1.getTime());
//		System.out.println(d2.getTime());
//		System.out.println(Long.MAX_VALUE/d2.getTime());
//		Date dt = getDateFromString("2015-02-28 14:22:29.316", "yyyy-MM-dd HH:mm:ss.SSS");
//		dt = new Date();
//		System.out.println(dt);
//		System.out.println(getFirstDayOfWeek(dt));
//		System.out.println(getLastDayOfWeek(dt));
//		System.out.println(getFirstDayOfMonth(dt));
//		System.out.println(getLastDayOfMonth(dt));
//		System.out.println(getFirstDayOfQuarter(dt));
//		System.out.println(getLastDayOfQuarter(dt));
//		System.out.println(getNextDayMinTime(dt));
//		String dateStr = "2015-01-31 15:01:01.567";
//		Date dt = getDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
//		Calendar c = Calendar.getInstance();
//		c.setTime(dt);
//		Calendar c1 = Calendar.getInstance();
//		c1.set(Calendar.YEAR, c.get(Calendar.YEAR));
//		c1.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
//		c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH));
//		System.out.println(c1.getTime());
		
		Date d2 = getDateFromString("2017-07-28 18:11:11", "yyyy-MM-dd HH:mm:ss");
		//Date d = new Date();
		System.out.println(d2);
	}
}
