/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shenzhou.dao.AssessReportDao;
import net.shenzhou.dao.DoctorAssessReportDao;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Bill;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorAssessReport;
import net.shenzhou.entity.Member;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Repository;

import com.google.gson.JsonObject;

/**
 * 授权详情
 * 2017-6-16 09:51:44
 * @author wsr
 *
 */
@Repository("doctorAssessReportDaoImpl")
public class DoctorAssessReportDaoImpl extends BaseDaoImpl<DoctorAssessReport, Long> implements DoctorAssessReportDao {

	@Resource(name="assessReportDaoImpl")
	AssessReportDao assessReportDao;
	
	@Override
	public List<DoctorAssessReport> findList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Member patientMember = (Member) map.get("patientMember");
		Doctor doctor = (Doctor) map.get("doctor");
		
//		String startDate = map.get("startDate").toString();
//		String endDate = map.get("endDate").toString();
//		String doctors = map.get("doctors").toString();
		List<AssessReport> assessReports = new ArrayList<AssessReport>();
		List list = new ArrayList();
		if (map.containsKey("startDate")||map.containsKey("endDate")||map.containsKey("doctors")) {
			list = assessReportDao.findList(map);
		}
		System.out.println(list.size());
		
		for (Object object : list) {
			System.out.println(JSONArray.fromObject(object).get(0));
			AssessReport assessReport = assessReportDao.find(Long.valueOf(JSONArray.fromObject(object).get(0).toString()));
			assessReports.add(assessReport);
		}
		
		System.out.println(assessReports.size());
		for (AssessReport assessReport : assessReports) {
			System.out.println(assessReport.getId()+":"+assessReport.getDiseaseName());
		}
		
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoctorAssessReport> criteriaQuery = criteriaBuilder.createQuery(DoctorAssessReport.class);
		Root<DoctorAssessReport> root = criteriaQuery.from(DoctorAssessReport.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		if (patientMember!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patientMember"), patientMember));
		} 
		
		if (doctor!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"), doctor));
		} 
		if (assessReports.size()>0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("assessReport")).value(assessReports));
			
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<DoctorAssessReport> findByDoctorAndMember(Doctor doctor,
			Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoctorAssessReport> criteriaQuery = criteriaBuilder.createQuery(DoctorAssessReport.class);
		Root<DoctorAssessReport> root = criteriaQuery.from(DoctorAssessReport.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (doctor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"),  doctor));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patientMember"),  member));
		}
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	
	
	
}