/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.entity.BeforehandLogin;
import net.shenzhou.entity.BeforehandLogin.UserType;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface BeforehandLoginService extends BaseService<BeforehandLogin, Long> {

	/**
	 * 根据手机号查找邀请信息
	 * @param mobile
	 * @return
	 */
	BeforehandLogin findByMobile(String mobile);
	
	/**
	 * 根据手机号和被邀请类型查找邀请信息
	 * @param mobile
	 * @return
	 */
	BeforehandLogin findByMobile(String mobile,UserType UserTypes,UserType usersType);
	
	
	/**
	 * 查找患者未获取的积分
	 * @param mobile
	 * @return
	 */
	List<BeforehandLogin> findMemberUnAcquire(Map<String,Object> query_map);
	
	
	
	/**
	 * 查找医生未获取的积分
	 * @param mobile
	 * @return
	 */
	List<BeforehandLogin> findDoctorUnAcquire(Map<String,Object> query_map);
	
}