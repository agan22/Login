package com.szy.news.util;

public class StringUtil {
	/**
	 * 从字符串转换成整形
	 * @param str 待转换字符串 
	 * @return int
	 */
	public static int String2Int(String str)
	{
		try
		{
			int value = Integer.valueOf(str);
			return value;
		} catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
}
