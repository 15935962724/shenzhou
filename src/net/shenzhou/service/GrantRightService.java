/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.Map;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.GrantRight;
import net.shenzhou.entity.Member;

/**
 * 授权
 * 2017-6-14 09:54:09
 * @author wsr
 *
 */
public interface GrantRightService extends BaseService<GrantRight, Long> {

	/**
	 * 授权列表
	 * @param map
	 * @return
	 */
	Map<String ,Object > findList(Map<String,Object> map);
	
	
	
	/**
	 * 医生有无申请过查看该患者病历
	 * @param map
	 * @return
	 */
	boolean findByDoctor(Doctor doctor,Member member);
	
	
	
	/**
	 * 根据医生患者获取数据
	 * @param map
	 * @return
	 */
	GrantRight findByDoctorAndMember(Doctor doctor,Member member);
}