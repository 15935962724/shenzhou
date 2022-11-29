/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou;

/**
 * 公共参数
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** shenzhou.xml文件路径 */
	public static final String shenzhou_XML_PATH = "/shenzhou.xml";

	/** shenzhou.properties文件路径 */
	public static final String shenzhou_PROPERTIES_PATH = "/shenzhou.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}