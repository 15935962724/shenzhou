package net.shenzhou.service;

import net.shenzhou.entity.DoctorCategoryImage;

public interface DoctorCategoryImageService {

	/**
	 * 生成医生资质证书图片
	 * 
	 * @param doctorImage
	 *            医生资质证书图片
	 */
	void build(DoctorCategoryImage doctorCategoryImage);
	
}
