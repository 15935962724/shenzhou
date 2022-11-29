/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import net.shenzhou.entity.AssessReportImage;

/**
 * 评估报告图片
 * 2017-8-11 15:33:07
 * @author wsr
 *
 */
public interface AssessReportImageService {

	/**
	 * 生成评估报告图片
	 * @param assessReportImage
	 */
	void build(AssessReportImage assessReportImage);

}