/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.DoctorDao;
import net.shenzhou.dao.WorkDayDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.entity.WorkDay.WorkType;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Repository;

/**
 * 
 * 2017-5-25 09:25:23
 * @author wsr
 * 
 *
 */
@Repository("workDayDaoImpl")
public class WorkDayDaoImpl extends BaseDaoImpl<WorkDay, Long> implements WorkDayDao {

	
	@Resource(name = "doctorDaoImpl")
	private DoctorDao doctorDao;
	
	@Override
	public List<WorkDay> getWorkDays(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Date workDayDate = DateUtil.getStringtoDate(map.get("workDayDate").toString(), "yyyy-MM-dd");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDay> criteriaQuery = criteriaBuilder.createQuery(WorkDay.class);
		Root<WorkDay> root = criteriaQuery.from(WorkDay.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		if(workDayDate != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Date>get("workDayDate"),workDayDate));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workType"),WorkType.work));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
		
	}

	@Override
	public WorkDay getWorkDayByDoctorAndData(Doctor doctor, Date data) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDay> criteriaQuery = criteriaBuilder.createQuery(WorkDay.class);
		Root<WorkDay> root = criteriaQuery.from(WorkDay.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		if(doctor!=null&&data!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Date>get("workDayDate"),data));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"),doctor));
		}
		criteriaQuery.where(restrictions);
		List<WorkDay> list = super.findList(criteriaQuery, null, null, null, null);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<WorkDay> getWorkDays(Doctor doctor, Date data) {
		//获取七天后的时间
//		int x = 100;
//		String end = DateUtil.getStatestime(x);
//		Date ends = DateUtil.getStringtoDate(end, "yyyy-MM-dd");
//		String endss = DateUtil.getDatetoString("yyyy-MM-dd", ends);
//		String endTime = endss+" 23:59:59";
		String start = DateUtil.getDatetoString("yyyy-MM-dd", data);
		String startTime = start+" 00:00:00";
//		
//		Date endaDate = DateUtil.getStringtoDate(endTime, "yyyy-MM-dd HH:mm:ss");
		Date staterDate = DateUtil.getStringtoDate(startTime, "yyyy-MM-dd HH:mm:ss");
//		
//		System.out.println("结束时间"+endTime+"================="+"开始时间"+startTime);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDay> criteriaQuery = criteriaBuilder.createQuery(WorkDay.class);
		Root<WorkDay> bill = criteriaQuery.from(WorkDay.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(bill);
		
		if (doctor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("doctor"),  doctor));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(bill.<Date>get("workDayDate"), staterDate));
		
		
//		if (staterDate != null&&endaDate!=null) {
//			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(bill.<Date>get("workDayDate"), staterDate, endaDate));
//		}
		
		
		
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(bill.get("workDayDate")));
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public Page<WorkDay> getPageWorkDays(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Date startDate = (Date) query_map.get("startDate");
		Date endDate = (Date) query_map.get("endDate");
		Object nameOrphone = query_map.get("nameOrphone");
		Pageable pageable =  (Pageable) query_map.get("pageable");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDay> criteriaQuery = criteriaBuilder.createQuery(WorkDay.class);
		Root<WorkDay> root = criteriaQuery.from(WorkDay.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isArrange"),  true));
		if (startDate != null&&endDate!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("workDayDate"), startDate, endDate));
		}
		Doctor doctor = null;
		if (nameOrphone!=null) {
			doctor = doctorDao.findByMobile(nameOrphone.toString())==null?doctorDao.findByName(nameOrphone.toString()):doctorDao.findByMobile(nameOrphone.toString());
		}else{
			return new Page<WorkDay>();
		}
		if (doctor!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"),  doctor));
		}else{
			return new Page<WorkDay>();
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("workDayDate")));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public WorkDay getDoctorWorkDays(Doctor doctor, Date data) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDay> criteriaQuery = criteriaBuilder.createQuery(WorkDay.class);
		Root<WorkDay> bill = criteriaQuery.from(WorkDay.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(bill);
		restrictions = criteriaBuilder.equal(bill.get("doctor"),  doctor);
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.<Date>get("workDayDate"),DateUtil.getStringtoDate(DateUtil.getDatetoString("yyyy-MM-dd", data), "yyyy-MM-dd")));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(bill.get("workDayDate")));
		List<WorkDay> workDays = super.findList(criteriaQuery, null, null, null, null);
		if(workDays.size()>0){
			return workDays.get(0);
		}
		return null;
	}

}