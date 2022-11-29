/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.Map;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Information.ClassifyType;

/**
 * Dao - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface InformationDao extends BaseDao<Information, Long> {
	
	/**
	 * 根据医生获取消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String ,Object> getInformationByDoctor(Doctor doctor , Integer pageNumber);
	
	
	/**
	 * 医生有无未读消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Boolean isDoctorUnread(Doctor doctor);
	
	
	
	/**
	 * 根据用户获取消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String ,Object> getInformationByMember(Member member , Integer pageNumber);
	
	
	
	/**
	 * 用户有无未读消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Boolean isMemberUnread(Member member);
	
	
	/**
	 * 用户一键阅读消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	void memberRead(Member member);
	
	
	/**
	 * 医生一键阅读消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	void doctorRead(Doctor doctor);
	
	
	/**
	 * 根据医生获取消息(新)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String ,Object> getNewInformationByDoctor(Doctor doctor ,Integer pageNumber,ClassifyType classifyType);
	
	/**
	 * 获取医生最近的系统消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Information getDoctorRecentlySystemInformation(Doctor doctor);
	
	/**
	 * 获取医生最近的业务消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Information getDoctorRecentlyBusinessInformation(Doctor doctor);
	
	
	/**
	 * 获取医生最近的活动消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Information getDoctorRecentlyActivityInformation(Doctor doctor);
	
	
	/**
	 * 获取医生系统消息未读状态
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Boolean getDoctorSystemInformationRead(Doctor doctor);
	
	/**
	 * 获取医生业务消息未读状态
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Boolean getDoctorBusinessInformationRead(Doctor doctor);
	
	
	/**
	 * 获取医生活动消息未读状态
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Boolean getDoctorActivityInformationRead(Doctor doctor);
	
	
	/**
	 * 患者端消息列表(新)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String ,Object> getNewInformationByMember(Member member ,Integer pageNumber,ClassifyType classifyType);
	
	
	
	
	/**
	 * 获取患者最近的系统消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Information getMemberRecentlySystemInformation(Member member);
	
	/**
	 * 获取患者最近的业务消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Information getMemberRecentlyBusinessInformation(Member member);
	
	
	/**
	 * 获取患者最近的活动消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Information getMemberRecentlyActivityInformation(Member member);
	
	
	/**
	 * 获取患者系统消息未读状态
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Boolean getMemberSystemInformationRead(Member member);
	
	/**
	 * 获取患者业务消息未读状态
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Boolean getMemberBusinessInformationRead(Member member);
	
	
	/**
	 * 获取患者活动消息未读状态
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Boolean getMemberActivityInformationRead(Member member);
	
	
	/**
	 * 医生一键阅读系统和活动消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	void doctorReadSystemActivity(Doctor doctor);
	
	
	/**
	 * 医生一键阅读当前机构业务消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	void doctorReadBusiness(Doctor doctor);
}