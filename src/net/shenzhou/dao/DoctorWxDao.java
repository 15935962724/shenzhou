/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorWx;

/**
 * 2017-12-12 14:31:52
 * 医师微信信息
 * @author wsr
 *
 */
public interface DoctorWxDao extends BaseDao<DoctorWx, Long> {

	/**
	 * openid查找医生的微信信息
	 * @param openid
	 * @return
	 */
	DoctorWx findByOpenid(String openid);

}