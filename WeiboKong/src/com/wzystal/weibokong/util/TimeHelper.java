package com.wzystal.weibokong.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 格式 String s = "Thu Aug 16 09:46:53 +0800 2012";
 */
public class TimeHelper {
	private static final String[] mE = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep",
			"Oct", "Nov", "Dec" };
	private static final String[] mC = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };
	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

	/**
	 * 
	 * @param s
	 *            "Thu Aug 16 09:46:53 +0800 2012"
	 * @return 1年前 or 08-15 10:50 or 几小时前 or 几分钟前 or 几秒前， otherwise ""
	 */
	public static String parseTime(String s) {
		String[] split = s.split(" ");
		String month = monthUtil(split[1]);
		calendar.set(Calendar.YEAR, Integer.valueOf(split[5]));
		calendar.set(Calendar.MONTH, Integer.valueOf(month));
		calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(split[2]));
		String[] hourSplit = split[3].split(":");
		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hourSplit[0]));
		calendar.set(Calendar.MINUTE, Integer.valueOf(hourSplit[1]));
		calendar.set(Calendar.SECOND, Integer.valueOf(hourSplit[2]));
		Calendar currentCalendar = Calendar.getInstance();
		if (calendar.get(calendar.YEAR) < currentCalendar.get(currentCalendar.YEAR))
			return currentCalendar.get(currentCalendar.YEAR) - calendar.get(calendar.YEAR) + "年前";
		if (currentCalendar.get(currentCalendar.MONTH) - calendar.get(calendar.MONTH) > 0) 
			return sdf.format(calendar.getTimeInMillis());
		if (currentCalendar.get(currentCalendar.DAY_OF_MONTH) - calendar.get(calendar.DAY_OF_MONTH) > 0)
			return sdf.format(calendar.getTimeInMillis());
		if (currentCalendar.get(currentCalendar.HOUR_OF_DAY) - calendar.get(calendar.HOUR_OF_DAY) > 0)
			return currentCalendar.get(currentCalendar.HOUR_OF_DAY) - calendar.get(calendar.HOUR_OF_DAY) + "小时前";
		if (currentCalendar.get(currentCalendar.MINUTE) - calendar.get(calendar.MINUTE) > 0)
			return currentCalendar.get(currentCalendar.MINUTE) - calendar.get(calendar.MINUTE) + "分钟前";
		if (currentCalendar.get(currentCalendar.SECOND) - calendar.get(calendar.SECOND) > 0)
			return currentCalendar.get(currentCalendar.SECOND) - calendar.get(calendar.SECOND) + "秒前";
		return "";
	}

	private static String monthUtil(String m) {
		for (int i = 0; i < mE.length; i++) {
			if (mE[i].equalsIgnoreCase(m))
				return mC[i];
		}
		return "";//这个若返回""会报错，没处理
	}
}