/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.dao.DoctorAssessReportDao;
import net.shenzhou.dao.GoodsDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorAssessReport;
import net.shenzhou.entity.Member;
import net.shenzhou.service.DoctorAssessReportService;

import org.springframework.stereotype.Service;

/**
 * 授权详情
 * 2017-06-16 18:11:10
 * @author wsr
 *
 */
@Service("doctorAssessReportServiceImpl")
public class DoctorAssessReportServiceImpl extends BaseServiceImpl<DoctorAssessReport, Long> implements DoctorAssessReportService {

	@Resource(name = "doctorAssessReportDaoImpl")
	private DoctorAssessReportDao doctorAssessReportDao;
	
	@Resource(name = "doctorAssessReportDaoImpl")
	public void setBaseDao(DoctorAssessReportDao doctorAssessReportDao) {
		super.setBaseDao(doctorAssessReportDao);
	}
	
	@Override
	public List<DoctorAssessReport> findList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return doctorAssessReportDao.findList(map);
	}

	@Override
	public List<DoctorAssessReport> findByDoctorAndMember(Doctor doctor,
			Member member) {
		// TODO Auto-generated method stub
		return doctorAssessReportDao.findByDoctorAndMember(doctor,member);
	}
	
}
