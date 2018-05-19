package com.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * @author XingyuLi
 *
 */
public class DateUtils
{
	protected static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
		"yyyy年MM月dd日", "yyyyMMddhhmmssSSS","yyyyMMdd","yyyy年MM月", "yyyyMMdd"};
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		try {
			if (pattern != null && pattern.length > 0) {
				formatDate = DateFormatUtils.format(date, pattern[0].toString());
			} else {
				formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
			}
		} catch (Exception e) {
			logger.error("转化日期格式错误:"+e.getMessage());
	    	e.printStackTrace();
		}
		return formatDate;
	}
	
	/**
	 * 格式化日期类型
	 * @throws ParseException 
	 */
	public static Date formatDate(String date) throws ParseException
	{
		return format.parse(date);
	}
	
    /**
     * 获取指定时间的那天23:59:59的时间
     * @param date
     * @return
     */
    public static Date getMaximumDay(Date date) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.HOUR_OF_DAY, 23);
    	c.set(Calendar.MINUTE, 59);
    	c.set(Calendar.SECOND, 59);
    	//c.set(Calendar.MILLISECOND, 999);
    	return c.getTime();
    }
    
	/**
     * 获取指定时间的那天00:00:00的时间
     * @param date
     * @return
     */
    public static Date dayBegin(Date date)
    {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	return c.getTime();
    }
}
