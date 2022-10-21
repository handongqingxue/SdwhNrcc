package com.sdwhNrcc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	//private static DateFormat timeDF=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	private static DateFormat timeDF=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String convertLongToString(long longDate) {
		Date date = new Date(longDate);
		return timeDF.format(date);
	}
	
	public static Long convertStringToLong(String fmtDate) throws ParseException {
		Date date = timeDF.parse(fmtDate);
		return date.getTime();
	}
	
	/**
	 * 天数操作 根据传入日期字符串和天数获取结果
	 * @param dateStr 日期字符串 yyyy-MM-dd
	 * @param days 操作天数
	 * @return days后的日期Date对象
	 */
    public static Date dayOper(String dateStr,int days) {

        int year = new Integer(dateStr.split("-")[0]);
        int month = new Integer(dateStr.split("-")[1]);
        int day = new Integer(dateStr.split("-")[2]);
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear + days);
        return calendar.getTime();
    }

	/**
	 * 格式化时间的显示
	 * @param date	如果date为null那么就是当前时间
	 * @param format 日期格式 默认yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDate(Date date,String format){
		String str = "";
		SimpleDateFormat sdf = null;
		if(format == null){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			sdf = new SimpleDateFormat(format);
		}
		if(date==null){
			str = sdf.format(new Date());
		}else{
			str = sdf.format(date);
		}
		return str;
	}
	
	public static void main(String[] args) {
		//DateUtil.convertLongToString(Long.parseLong("1634626280668"));
		try {
			DateUtil.convertStringToLong("2021-10-20 08:36:36:429");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
