/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import net.shenzhou.entity.MechanismImage;

/**
 * 机构图片
 * 2017-07-20 09:20:03
 * @author Administrator
 *
 */
public interface MechanismImageService {

	/**
	 * 生成机构图片
	 * 
	 * @param mechanismImage
	 *            机构图片
	 */
	void build(MechanismImage mechanismImage);

}