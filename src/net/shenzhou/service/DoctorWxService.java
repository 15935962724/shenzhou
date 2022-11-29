/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import net.shenzhou.entity.DoctorWx;

/**
 * Service - 医生的微信信息
 * @date 2017-12-12 14:38:44
 * @author wsr
 * @version 1.0
 */
public interface DoctorWxService extends BaseService<DoctorWx, Long> {
	
	
	DoctorWx findByOpenid(String openid);
	
	
}
