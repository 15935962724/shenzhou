/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import net.shenzhou.entity.DoctorImage;

/**
 * @date 2017-11-20 16:26:54
 * 医生上传资质证书图片
 * @author Administrator
 *
 */
public interface DoctorImageService {

	/**
	 * 生成医生资质证书图片
	 * 
	 * @param doctorImage
	 *            医生资质证书图片
	 */
	void build(DoctorImage doctorImage);

}