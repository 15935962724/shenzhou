/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import net.shenzhou.entity.RecoveryRecordImage;

/**
 * 康护记录图片
 * 2018-1-5 17:34:54
 * @author wsr
 *
 */
public interface RecoveryRecordImageService {

	/**
	 * 生成康护记录图片
	 * 
	 * @param recoveryRecordImage
	 *            康护记录图片
	 */
	void build(RecoveryRecordImage recoveryRecordImage);

}