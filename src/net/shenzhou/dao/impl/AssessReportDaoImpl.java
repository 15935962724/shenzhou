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
import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.dao.AssessReportDao;
import net.shenzhou.dao.DoctorDao;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Bill;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Member;
import net.shenzhou.util.DateUtil;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
/**
 * Dao - 评估报告
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("assessReportDaoImpl")
public class AssessReportDaoImpl extends BaseDaoImpl<AssessReport, Long> implements AssessReportDao {

	@Resource(name = "doctorDaoImpl")
	private DoctorDao doctorDao;
	
	@Override
	public List findList(Map<String, Object> map) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AssessReport> criteriaQuery = criteriaBuilder.createQuery(AssessReport.class);
		Root<AssessReport> root = criteriaQuery.from(AssessReport.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		String startDate = map.get("startDate").toString();
		String endDate = map.get("endDate").toString();
		String doctors = map.get("doctors").toString();
        Member patientMember = (Member) map.get("patientMember");
		
        StringBuffer sbf=new StringBuffer();
		List assessReports =new ArrayList();
		
		
		
		
//		SELECT * FROM xx_assessreport WHERE member = 18 
//				 AND create_date BETWEEN '2017-06-01 00:00:00' AND '2017-10-31 23:59:59'
//				 AND id IN (SELECT recovery_assess_reports FROM xx_recovery_doctor_assess_report WHERE recovery_doctors IN (40,41))
//		
		sbf.append("SELECT * FROM xx_assessreport  where 1 =1 ");
		if (patientMember!=null) {
			sbf.append("AND member = "+patientMember.getId()+" ");
		}
		if (startDate!=null&&!startDate.equals("")&&endDate!=null&&!endDate.equals("")) {
			startDate = startDate+" 00:00:00";
			endDate = endDate + " 23:59:59";                 
			sbf.append(" AND create_date BETWEEN '"+startDate+"' AND '"+endDate+"' ");
		}
		if (doctors!=null&&!doctors.equals("")) {
			sbf.append(" AND id IN (SELECT recovery_assess_reports FROM xx_recovery_doctor_assess_report WHERE recovery_doctors IN ("+doctors+"))");
		}
		try {
			Session session =sessionFactory.openSession();
			String sql=sbf.toString();
			assessReports = session.createSQLQuery(sql).list();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
//		assessReports = entityManager.createQuery(sbf.toString(), AssessReport.class).setFlushMode(FlushModeType.COMMIT).getResultList();
        return assessReports;
        
        
        
        
//		if (startDate!=null&&!startDate.equals("")&&endDate!=null&&!endDate.equals("")) {
//			startDate = startDate+" 00:00:00";
//			endDate = endDate + " 23:59:59";                 
//			Date start = DateUtil.getStringtoDate(startDate, "yyyy-MM-dd HH:mm:ss");
//			Date end = DateUtil.getStringtoDate(endDate, "yyyy-MM-dd HH:mm:ss");
//			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date>get("createDate"),(Date)start,(Date)end));
//		}
//		
//		if (patientMember!=null) {
//			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),patientMember));
//		}
//		
//		if (doctors!=null&&!doctors.equals("")) {
//			String [] doctor = doctors.split(",");
//			List<Doctor> data_doctors = new ArrayList<Doctor>();
//			List<Long> doctorId = new ArrayList<Long>();
//			
//			for (String doctorid : doctor) {
//				data_doctors.add(doctorDao.find(Long.valueOf(doctorid)));
//			    doctorId.add(Long.valueOf(doctorid));
//			}
//			if(data_doctors.size()>0){
//				Root<AssessReport> assessReport = criteriaQuery.from(AssessReport.class);
//				Join<AssessReport, Doctor> recoveryDoctors = assessReport.join("recoveryDoctors");
//				
//				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(recoveryDoctors.get("id")).value(doctorId));//根据医师去筛选病例报告
//				
////				Join<Product, Cooperative> cooperativeJoin=root.join("cooperatives");
////				restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.equal(cooperativeJoin.get("id"), cooperativeId));
////				if(cooperativeProductCategoryId.length>0){
////					Join<Product, CooperativeProductCategory> categoryJoin=root.join("cooperativeProductCategories");
////					//多选
//////					restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.in(categoryJoin.get("id")).value(cooperativeProductCategoryIds));
////					//单选
////					restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.equal(categoryJoin.get("id"), cooperativeProductCategoryIds.get(0)));
////					
////				}
////				
////				restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.<Cooperative>get("cooperative"), cooperative), criteriaBuilder.equal(cooperativeJoin.<Long>get("parentId"), cooperative.getId())));
////				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(recoveryDoctors.get("recoveryDoctors")).value(data_doctors));//根据医师去筛选病例报告
//				
//				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("id")).value(recoveryDoctors.get("id")));//根据医师去筛选病例报告
//			}
//		}
//		
//		
//		
//		
//		criteriaQuery.where(restrictions);
//		return super.findList(criteriaQuery, null, null, null, null);
		
	}

	@Override
	public Long assessReportCount(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AssessReport> criteriaQuery = criteriaBuilder.createQuery(AssessReport.class);
		Root<AssessReport> root = criteriaQuery.from(AssessReport.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
		}
		criteriaQuery.where(restrictions);
		
		return super.count(criteriaQuery, null);
	}

	@Override
	public AssessReport getRecentlyAssessReport(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AssessReport> criteriaQuery = criteriaBuilder.createQuery(AssessReport.class);
		Root<AssessReport> root = criteriaQuery.from(AssessReport.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member == null) {
			return null;
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		criteriaQuery.where(restrictions);
		List<AssessReport> AssessReportList = super.findList(criteriaQuery, null, null, null, null);
		
		if(AssessReportList.size()>0){
			return AssessReportList.get(0);
		}
		return null;
	}

}