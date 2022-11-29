/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.dao.BillDao;
import net.shenzhou.entity.Bill;
import net.shenzhou.entity.Bill.BillType;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;

import org.springframework.stereotype.Repository;
/**
 * Dao - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("billDaoImpl")
public class BillDaoImpl extends BaseDaoImpl<Bill, Long> implements BillDao {

	@Override
	public List<Object[]> getBillDate(Doctor doctor) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Bill> bill = criteriaQuery.from(Bill.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.multiselect(bill.get("createDate"));
		if (doctor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("doctor"),  doctor));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.groupBy(bill.get("createDate"));
		
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		
		return query.getResultList();
		
	

	}


	@Override
	public List<Bill> filtrateBillDate(Doctor doctor, Mechanism mechanism,BillType billType) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Bill> criteriaQuery = criteriaBuilder.createQuery(Bill.class);
		Root<Bill> bill = criteriaQuery.from(Bill.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (doctor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("doctor"),  doctor));
		}
		if(mechanism!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("mechanism"),  mechanism));
		}
		if (billType != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("billType"),  billType));
		}
		criteriaQuery.select(bill);
		criteriaQuery.where(restrictions);
		criteriaQuery.groupBy(bill.get("createDate"));
		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	
	@Override
	public List<Bill> getBillDatePack(Doctor doctor,BillType billType) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Bill> criteriaQuery = criteriaBuilder.createQuery(Bill.class);
		Root<Bill> bill = criteriaQuery.from(Bill.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (doctor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("doctor"),  doctor));
		}
		if (billType != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("billType"),  billType));
		}
		criteriaQuery.select(bill);
		criteriaQuery.where(restrictions);
		criteriaQuery.groupBy(bill.get("createDate"));
		
		return super.findList(criteriaQuery, null, null, null, null);
	}


	@Override
	public List<Bill> BillDetails(Doctor doctor, BillType billType, Date staterDate,Date endaDate) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Bill> criteriaQuery = criteriaBuilder.createQuery(Bill.class);
			Root<Bill> bill = criteriaQuery.from(Bill.class);
			Predicate restrictions = criteriaBuilder.conjunction();
			if (doctor != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("doctor"),  doctor));
			}
			if (billType != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("billType"),  billType));
			}
			if (staterDate != null&&endaDate!=null) {
				restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(bill.<Date>get("createDate"), staterDate, endaDate));
			}
			criteriaQuery.select(bill);
			criteriaQuery.where(restrictions);
			
			return super.findList(criteriaQuery, null, null, null, null);
	}


	@Override
	public List<Bill> mechanismBillDetails(Doctor doctor, BillType billType,
			Date staterDate, Date endaDate, Mechanism mechanism) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Bill> criteriaQuery = criteriaBuilder.createQuery(Bill.class);
		Root<Bill> bill = criteriaQuery.from(Bill.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (doctor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("doctor"),  doctor));
		}
		if (billType != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("billType"),  billType));
		}
		if(mechanism!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("mechanism"),  mechanism));
		}
		if (staterDate != null&&endaDate!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(bill.<Date>get("createDate"), staterDate, endaDate));
		}
		criteriaQuery.select(bill);
		criteriaQuery.where(restrictions);
		
		return super.findList(criteriaQuery, null, null, null, null);
	}
}