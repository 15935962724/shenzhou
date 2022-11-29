/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;

import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Verification;

/**
 * 验证码
 * 2017-05-25 13:52:55
 * @author wsr
 *
 */
public interface VerificationService extends BaseService<Verification, Long> {

	
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