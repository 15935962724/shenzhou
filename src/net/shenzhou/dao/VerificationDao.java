/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Verification;

/**
 * 验证码dao
 * 2017-5-25 13:46:39
 * @author wsr
 *
 */
public interface VerificationDao extends BaseDao<Verification, Long> {
	
	/**
	 * 判断手机号是否存在
	 * @param mobile
	 * @return
	 */
	boolean mobileExists(String mobile);

	/**
	 * 根据手机号查找验证码
	 * @param mobile
	 * @return
	 */
	Verification findByMobile(String mobile);
	
}