/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.dao.DoctorMechanismRelationDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.entity.User;
import net.shenzhou.service.DoctorMechanismRelationService;

import org.springframework.stereotype.Service;

/**
 * Service - 医生机构关系表
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("doctorMechanismRelationServiceImpl")
public class DoctorMechanismRelationServiceImpl extends BaseServiceImpl<DoctorMechanismRelation, Long> implements DoctorMechanismRelationService {

	@Resource(name = "doctorMechanismRelationDaoImpl")
	private DoctorMechanismRelationDao doctorMechanismRelationDao;

	@Resource(name = "doctorMechanismRelationDaoImpl")
	public void setBaseDao(DoctorMechanismRelationDao doctorMechanismRelationDao) {
		super.setBaseDao(doctorMechanismRelationDao);
	}

	@Override
	public List<String> findAuthorities(Long id) {
		// TODO Auto-generated method stub
		List<String> authorities = new ArrayList<String>();
		 DoctorMechanismRelation doctorMechanismRelation = doctorMechanismRelationDao.find(id);
		if (doctorMechanismRelation != null) {
			for (MechanismRole mechanismRole : doctorMechanismRelation.getMechanismroles()) {
				authorities.addAll(mechanismRole.getAuthorities());
			}
		}
		return authorities;
	}

	
	@Override
	public Page<DoctorMechanismRelation> getPageMechanismDoctors(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return doctorMechanismRelationDao.getPageMechanismDoctors(query_map);
	}

	@Override
	public List<DoctorMechanismRelation> downloadList(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return doctorMechanismRelationDao.downloadList(query_map);
	}

	@Override
	public List<DoctorMechanismRelation> getDoctorMechanism(Doctor doctor) {
		// TODO Auto-generated method stub
		return doctorMechanismRelationDao.getDoctorMechanism(doctor);
	}

	@Override
	public DoctorMechanismRelation getByDoctorMechanism(Doctor doctor, Mechanism mechanism) {
		// TODO Auto-generated method stub
		return doctorMechanismRelationDao.getByDoctorMechanism(doctor, mechanism);
	}




}
