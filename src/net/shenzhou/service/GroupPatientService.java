/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;

import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.GroupPatient;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientGroup;

/**
 * Service - 分组患者
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface GroupPatientService extends BaseService<GroupPatient, Long> {
	
	/**
	 * 根据医生机构和分组查找患者
	 * 
	 * @return 
	 */
	List<Member> getGroupPatientByGroupAndDoctor(DoctorMechanismRelation doctorMechanismRelation,PatientGroup patientGroup);
	
	
	
	/**
	 * 根据医生机构和患者查找分组
	 * 
	 * @return 
	 */
	GroupPatient getGroupPatientByPatientAndDoctor(DoctorMechanismRelation doctorMechanismRelation,Member member);
	
	
	/**
	 * 所有条件确定一条患者数据
	 * 
	 * @return 
	 */
	GroupPatient getGroupPatient(DoctorMechanismRelation doctorMechanismRelation,Member member,PatientGroup patientGroup);
	
	
	/**
	 * 获取医生在该机构所有已分组的患者
	 * 
	 * @return 
	 */
	List<Member> getAddGroupPatientData(DoctorMechanismRelation doctorMechanismRelation);
}