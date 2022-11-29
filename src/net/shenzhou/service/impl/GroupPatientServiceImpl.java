/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.dao.GroupPatientDao;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.GroupPatient;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientGroup;
import net.shenzhou.service.GroupPatientService;

import org.springframework.stereotype.Service;

/**
 * Service - 分组患者
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("groupPatientServiceImpl")
public class GroupPatientServiceImpl extends BaseServiceImpl<GroupPatient, Long> implements GroupPatientService {

	@Resource(name = "groupPatientDaoImpl")
	private GroupPatientDao groupPatientDao;

	@Resource(name = "groupPatientDaoImpl")
	public void setBaseDao(GroupPatientDao groupPatientDao) {
		super.setBaseDao(groupPatientDao);
	}

	@Override
	public List<Member> getGroupPatientByGroupAndDoctor(
			DoctorMechanismRelation doctorMechanismRelation,
			PatientGroup patientGroup) {
		List<GroupPatient> groupPatients = groupPatientDao.getGroupPatientByGroupAndDoctor(doctorMechanismRelation, patientGroup);
		
		List<Member> members = new ArrayList<Member>();
		for(GroupPatient groupPatient : groupPatients){
			members.add(groupPatient.getPatientMember());
		}
		
		return members;
	}

	@Override
	public GroupPatient getGroupPatientByPatientAndDoctor(
			DoctorMechanismRelation doctorMechanismRelation, Member member) {
		
		return groupPatientDao.getGroupPatientByPatientAndDoctor(doctorMechanismRelation,member);
	}

	@Override
	public GroupPatient getGroupPatient(
			DoctorMechanismRelation doctorMechanismRelation, Member member,
			PatientGroup patientGroup) {
		// TODO Auto-generated method stub
		return groupPatientDao.getGroupPatient(doctorMechanismRelation,member,patientGroup);
	}

	@Override
	public List<Member> getAddGroupPatientData(
			DoctorMechanismRelation doctorMechanismRelation) {
		return groupPatientDao.getAddGroupPatientData(doctorMechanismRelation);
	}
	
}