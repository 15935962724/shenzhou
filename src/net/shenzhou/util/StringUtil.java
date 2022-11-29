package net.shenzhou.util;

public class StringUtil {

	public static boolean isNull(String text) {
		if (text!=null&&!"".equals(text)) {
			return true;
		}
		return false;
	}

}
