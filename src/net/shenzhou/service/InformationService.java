/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.Map;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Information.ClassifyType;
import net.shenzhou.entity.Member;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface InformationService extends BaseService<Information, Long> {
	

	/**
	 * 根据医生获取消息
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String ,Object> getInformationByDoctor(Doctor doctor ,Integer pageNumber);
	
	
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
	Map<String ,Object> getInformationByMember(Member member ,Integer pageNumber);
	
	
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
	 * 获取医生三个分类最新的消息(新)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String ,Object> getDoctorRecentlyInformation(Doctor doctor);
	
	
	
	/**
	 * 医生有无未读消息(新)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Boolean newIsDoctorUnread(Doctor doctor);
	
	/**
	 * 患者端消息列表(新)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String ,Object> getNewInformationByMember(Member member ,Integer pageNumber,ClassifyType classifyType);
	
	
	/**
	 * 获取患者三个分类最新的消息(新)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String ,Object> getMemberRecentlyInformation(Member member);
	
	
	/**
	 * 患者有无未读消息(新)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Boolean newIsMemberUnread(Member member);
	
	
	
	/**
	 * 医生一键阅读消息(新)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	void newDoctorRead(Doctor doctor);
	
	
	/**
	 * 医生一键系统消息(新)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	void doctorReadSystem(Doctor doctor);
	
	
	/**
	 * 医生一键业务消息(新)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	void doctorReadBusiness(Doctor doctor);
}