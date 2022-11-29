/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.DepositDao;
import net.shenzhou.dao.MemberDao;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Deposit.Type;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.MemberBill;
import net.shenzhou.entity.MemberBillDetails;
import net.shenzhou.entity.MemberBillDetails.BillType;
import net.shenzhou.util.DateUtil;

/**
 * Dao - 预存款
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("depositDaoImpl")
public class DepositDaoImpl extends BaseDaoImpl<Deposit, Long> implements DepositDao {

	
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	
	public Page<Deposit> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return new Page<Deposit>(Collections.<Deposit> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
		Root<Deposit> root = criteriaQuery.from(Deposit.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<Deposit> getMechanismDepositList(Map<String, Object> query_map) {
		
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Object createDate = query_map.get("createDate");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
		
		Root<Deposit> root = criteriaQuery.from(Deposit.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
	    if (mechanism!=null) {
	    	restrictions = criteriaBuilder.in(root.get("member")).value(mechanism.getMembers());
		}
		String data = DateUtil.getDatetoString("yyyy-MM-dd", createDate!=null?(Date)createDate:new Date());
		String startDate  = data+" 00:00:00";
		String endDate =  data+" 23:59:59";
		
		Date startDates = DateUtil.getStringtoDate(startDate, "yyyy-MM-dd HH:mm:ss");
		Date endDates = DateUtil.getStringtoDate(endDate, "yyyy-MM-dd HH:mm:ss");
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDates, endDates));

		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
		
		
	}

	@Override
	public Page<Deposit> getMechanismDepositFindPage(
			Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Object startDate = query_map.get("startDate");
		Object endDate = query_map.get("endDate");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Pageable pageable = (Pageable) query_map.get("pageable");
		Object type = query_map.get("type");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
		
		Root<Deposit> root = criteriaQuery.from(Deposit.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
	   
	    if (nameOrmobile!=null) {
	    	Map<String, Object> query_member_list_map = new HashMap<String, Object>();
	    	query_member_list_map.put("mechanism", mechanism);
	    	query_member_list_map.put("nameOrmobile", nameOrmobile);
	    	List<Member> members = memberDao.getMembersByNameOrMobile(query_member_list_map);
	    	if (members.size()>0) {
	    		restrictions = criteriaBuilder.in(root.get("member")).value(members);
			}
		}
//	    else{
//			restrictions = criteriaBuilder.in(root.get("member")).value(mechanism.getMembers());
//		}
//	    add wsr 2018-3-19 17:05:15
	    restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"), mechanism));
	    if (startDate != null&&endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"),(Date)startDate , (Date)endDate));
		}
	    if (type!=null) {
	    	restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("type"), Type.valueOf(type.toString())));
		}
	    
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public MemberBill getMemberBill(String day, Member member) {
		
		String startDate  = day+" 00:00:00";
		String endDate =  day+" 23:59:59";
		Date startDates = DateUtil.getStringtoDate(startDate, "yyyy-MM-dd HH:mm:ss");
		Date endDates = DateUtil.getStringtoDate(endDate, "yyyy-MM-dd HH:mm:ss");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
		Root<Deposit> root = criteriaQuery.from(Deposit.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDates, endDates));
		criteriaQuery.where(restrictions);
		List<Deposit> depositList = super.findList(criteriaQuery, null, null, null, null);
		
		if(depositList.size()<=0){
			return null;
		}
		
		MemberBill memberBill = new MemberBill();
		//充值
		BigDecimal income = new BigDecimal(0);
		//支出
		BigDecimal expend = new BigDecimal(0);
		for(Deposit deposit : depositList){
			
			
			
			
			
			
			
			MemberBillDetails memberBillDetails = new MemberBillDetails();
			
			//会员充值
			income = income.add(deposit.getCredit());
			expend = expend.add(deposit.getDebit());
			memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
			memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
			memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
			memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
			memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
			memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
			memberBill.getMemberBillDetails().add(memberBillDetails);
			/*memberBill.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
			memberBill.setBillTypes(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillTypes.consumption:BillTypes.recharge);
			memberBill.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
			memberBill.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
			memberBill.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
			memberBill.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));*/
		}
		
		memberBill.setTotalRecharge(expend.toString());
		memberBill.setTotalAddress(income.toString());
		memberBill.setBillDay(day);
		return memberBill;
		
	}

	@Override
	public List<Deposit> downloadList(Map<String, Object> query_map) {

		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Object startDate = query_map.get("startDate");
		Object endDate = query_map.get("endDate");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Pageable pageable = (Pageable) query_map.get("pageable");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
		
		Root<Deposit> root = criteriaQuery.from(Deposit.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
	   
	    if (nameOrmobile!=null) {
	    	Map<String, Object> query_member_list_map = new HashMap<String, Object>();
	    	query_member_list_map.put("mechanism", mechanism);
	    	query_member_list_map.put("nameOrmobile", nameOrmobile);
	    	List<Member> members = memberDao.getMembersByNameOrMobile(query_member_list_map);
	    	if (members.size()>0) {
	    		restrictions = criteriaBuilder.in(root.get("member")).value(members);
			}
	    	
		}
	    
	    if (startDate != null&&endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"),(Date)startDate , (Date)endDate));
		}else{
			String data = DateUtil.getDatetoString("yyyy-MM-dd",new Date());
			String start_Date  = data+" 00:00:00";
			String end_Date =  data+" 23:59:59";
			Date startDates = DateUtil.getStringtoDate(start_Date, "yyyy-MM-dd HH:mm:ss");
			Date endDates = DateUtil.getStringtoDate(end_Date, "yyyy-MM-dd HH:mm:ss");
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDates, endDates));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	
	}

	@Override
	public List<MemberBill> getMemberBillList(Member member) {
		
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
		Root<Deposit> root = criteriaQuery.from(Deposit.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
		//restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDates, endDates));
		criteriaQuery.where(restrictions);
		List<Deposit> depositList = super.findList(criteriaQuery, null, null, null, null);
		
		if(depositList.size()<=0){
			return null;
		}
		
		/*Date date = new Date();
		SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
		String day_day = format_day.format(date);
		String day_month = format_month.format(date);
		
		for(int x = 0;;x++){
			System.out.println(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x), "yyyy-MM")));
			System.out.println(day_month);
			if(!day_month.equals(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x), "yyyy-MM")))){
				break;
			}
			System.out.println(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x));
			System.out.println(day_month);
			
		}*/
		
		MemberBill memberBill = new MemberBill();
		List<MemberBill> memberBills = new ArrayList<MemberBill>();
		//充值
		BigDecimal income = new BigDecimal(0);
		//支出
		BigDecimal expend = new BigDecimal(0);
		for(Deposit deposit : depositList){
			
			MemberBillDetails memberBillDetails = new MemberBillDetails();
		
			//memberBill.setBillDay(DateUtil.getDatetoString("yyyy-MM", deposit.getCreateDate()));		
			//会员充值
			/*income = income.add(deposit.getCredit());
			expend = expend.add(deposit.getDebit());
			memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
			memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
			memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
			memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
			memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
			memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
			memberBill.getMemberBillDetails().add(memberBillDetails);*/
			
		}
		memberBill.setTotalRecharge(expend.toString());
		memberBill.setTotalAddress(income.toString());
		//SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM-dd");
		//String day_day = format_month.format(new Date());
		//memberBill.setBillDay(day_day);
		//memberBills.add(memberBill);
		
		return memberBills;
	}

	@Override
	public List<Deposit> getMemberBillScreen(String mechanismid, String stater_Day,String end_Day,String type, Member member) {
		//if(stater_Day.length()>8 ){//判断传过来的是月或日期的长度,大于8是日期,小于8是月
			/*String startDate  = stater_Day+" 00:00:00";
			String endDate =  end_Day+" 23:59:59";
			Date startDates = DateUtil.getStringtoDate(startDate, "yyyy-MM-dd HH:mm:ss");
			Date endDates = DateUtil.getStringtoDate(endDate, "yyyy-MM-dd HH:mm:ss");*/
			
			//获取当月用户账单
			Date date = new Date();
			SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
			String day_day = format_day.format(member.getCreateDate());
			String day_month = format_month.format(date);
			
		
		
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
			Root<Deposit> root = criteriaQuery.from(Deposit.class);
			criteriaQuery.select(root);
			Predicate restrictions = criteriaBuilder.conjunction();
			
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
			if(mechanismid != null && !mechanismid.equals("")){//判断为不为空
				Long mid = Long.parseLong(mechanismid);
				restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"),  mid));
			}
			if(stater_Day != null && !stater_Day.equals("") && end_Day != null && !end_Day.equals("")){
				//判断为不为空
				restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), DateUtil.getStringtoDate(stater_Day, "yyyy-MM-dd HH:mm:ss"), DateUtil.getStringtoDate(end_Day, "yyyy-MM-dd HH:mm:ss")));
			}
			if(stater_Day != null && !stater_Day.equals("") && end_Day == null && end_Day.equals("")){
				
			}
			
			criteriaQuery.where(restrictions);
			List<Deposit> depositList = super.findList(criteriaQuery, null, null, null, null);
			/*List<Deposit> list = new ArrayList<Deposit>();
			if(type != null && !type.equals("")){//判断为不为空
				for(Deposit dp : depositList){//处理收入还是支出
					Double credit = dp.getCredit().doubleValue();
					Double debit = dp.getDebit().doubleValue();
					if(type.equals("1")){//判断收入支出的值,1是收入,2是支出,null是全部
						if(dp.getCredit().doubleValue() != 0.0){
							list.add(dp);
						}
					}else{//
						if(dp.getDebit().doubleValue() != 0.0){
							list.add(dp);
						}
					}
				}
				if(list.size()<=0){
					return null;
				}
				
				MemberBill memberBill = new MemberBill();
				//充值
				BigDecimal income = new BigDecimal(0);
				//支出
				BigDecimal expend = new BigDecimal(0);
				for(Deposit deposit : list){
					
					MemberBillDetails memberBillDetails = new MemberBillDetails();
					
					//会员充值
					income = income.add(deposit.getCredit());
					expend = expend.add(deposit.getDebit());
					memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
					memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
					memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
					memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
					memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
					memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
					memberBill.getMemberBillDetails().add(memberBillDetails);
					
				}
				
				memberBill.setTotalRecharge(expend.toString());
				memberBill.setTotalAddress(income.toString());
				memberBill.setBillDay(end_Day);*/
				return depositList;
			/*}else{
				if(depositList.size()<=0){
					return null;
				}
				
				MemberBill memberBill = new MemberBill();
				//充值
				BigDecimal income = new BigDecimal(0);
				//支出
				BigDecimal expend = new BigDecimal(0);
				for(Deposit deposit : depositList){
					
					MemberBillDetails memberBillDetails = new MemberBillDetails();
					
					//会员充值
					income = income.add(deposit.getCredit());
					expend = expend.add(deposit.getDebit());
					memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
					memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
					memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
					memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
					memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
					memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
					memberBill.getMemberBillDetails().add(memberBillDetails);
					
				}
				
				memberBill.setTotalRecharge(expend.toString());
				memberBill.setTotalAddress(income.toString());
				memberBill.setBillDay(end_Day);
				return memberBill;
			}*/
				
			/*}else{//都为空,或按月查询
				Map<String, String> dateMap;
				String stater_Date = null;
				String enda_Date = null;
				Date startDates = null;
				Date endDates = null;
					try {
						if(stater_Day != null && !stater_Day.equals("") || end_Day != null && !end_Day.equals("")){
							dateMap = DateUtil.getDateStartEnd(stater_Day);
							
							String staterDate= dateMap.get("startDate");
					    	String endaDate =dateMap.get("endDate");
							
					    	stater_Date = DateUtil.getDatetoString("yyyy-MM-dd", DateUtil.getStringtoDate(staterDate, "yyyy-MM-dd"));
					    	enda_Date = DateUtil.getDatetoString("yyyy-MM-dd", DateUtil.getStringtoDate(endaDate, "yyyy-MM-dd"));
					    	
					    	String startDate  = stater_Date+" 00:00:00";
							String endDate =  enda_Date+" 23:59:59";
							startDates = DateUtil.getStringtoDate(startDate, "yyyy-MM-dd HH:mm:ss");
							endDates = DateUtil.getStringtoDate(endDate, "yyyy-MM-dd HH:mm:ss");
						}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
				Root<Deposit> root = criteriaQuery.from(Deposit.class);
				criteriaQuery.select(root);
				Predicate restrictions = criteriaBuilder.conjunction();
				
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
				if(mechanismid != null && !mechanismid.equals("")){//判断为不为空
					Long mid = Long.parseLong(mechanismid);
					restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"),  mid));
				}
				if(stater_Day != null && !stater_Day.equals("") || end_Day != null && !end_Day.equals("")){
					//判断为不为空
					restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDates, endDates));
				}
				
				criteriaQuery.where(restrictions);
				List<Deposit> depositList = super.findList(criteriaQuery, null, null, null, null);
				List<Deposit> list = new ArrayList<Deposit>();//处理收入支出的集合
				if(type != null && !type.equals("")){//判断是否为空
					for(Deposit dp : depositList){//处理收入还是支出
						Double credit = dp.getCredit().doubleValue();
						Double debit = dp.getDebit().doubleValue();
						if(type.equals("1")){//判断是收入还是支出或全部,null是全部,1是收入,2是支出
							if(dp.getCredit().doubleValue() != 0.0){
								list.add(dp);
							}
						}else{
							if(dp.getDebit().doubleValue() != 0.0){
								list.add(dp);
							}
						}
					}
					if(list.size()<=0){
						return null;
					}
					
					MemberBill memberBill = new MemberBill();
					//充值
					BigDecimal income = new BigDecimal(0);
					//支出
					BigDecimal expend = new BigDecimal(0);
					for(Deposit deposit : list){
						
						MemberBillDetails memberBillDetails = new MemberBillDetails();
						
						//会员充值
						income = income.add(deposit.getCredit());
						expend = expend.add(deposit.getDebit());
						memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
						memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
						memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
						memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
						memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
						memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
						memberBill.getMemberBillDetails().add(memberBillDetails);
						
					}
					
					memberBill.setTotalRecharge(expend.toString());
					memberBill.setTotalAddress(income.toString());
					memberBill.setBillDay(end_Day);
					return memberBill;
				}else{
					if(depositList.size()<=0){
						return null;
					}
					
					MemberBill memberBill = new MemberBill();
					//充值
					BigDecimal income = new BigDecimal(0);
					//支出
					BigDecimal expend = new BigDecimal(0);
					for(Deposit deposit : depositList){
						
						MemberBillDetails memberBillDetails = new MemberBillDetails();
						
						//会员充值
						income = income.add(deposit.getCredit());
						expend = expend.add(deposit.getDebit());
						memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
						memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
						memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
						memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
						memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
						memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
						memberBill.getMemberBillDetails().add(memberBillDetails);
						
					}
					
					memberBill.setTotalRecharge(expend.toString());
					memberBill.setTotalAddress(income.toString());
					memberBill.setBillDay(end_Day);
					return memberBill;
				}*/
		
			/*}*/
	}


	
	@Override
	public MemberBill getMemberBilly(String stater_Day,String end_Day, Member member) {
		
		
		Date startDates = DateUtil.getStringtoDate(stater_Day, "yyyy-MM-dd HH:mm:ss");
		Date endDates = DateUtil.getStringtoDate(end_Day, "yyyy-MM-dd HH:mm:ss");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
		Root<Deposit> root = criteriaQuery.from(Deposit.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDates, endDates));
		criteriaQuery.where(restrictions);
		List<Deposit> depositList = super.findList(criteriaQuery, null, null, null, null);
		
		/*if(depositList.size()<=0){
			return null;
		}*/
		
		MemberBill memberBill = new MemberBill();
		//充值
		BigDecimal income = new BigDecimal(0);
		//支出
		BigDecimal expend = new BigDecimal(0);
		for(Deposit deposit : depositList){
			
			MemberBillDetails memberBillDetails = new MemberBillDetails();
			
			//会员充值
			income = income.add(deposit.getCredit());
			expend = expend.add(deposit.getDebit());
			memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
			memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
			memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
			memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
			memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
			memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
			
			
			/*memberBill.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
			memberBill.setBillTypes(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillTypes.consumption:BillTypes.recharge);
			memberBill.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
			memberBill.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
			memberBill.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
			memberBill.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));*/
			memberBill.getMemberBillDetails().add(memberBillDetails);
			
		}
		
		memberBill.setTotalRecharge(expend.toString());
		memberBill.setTotalAddress(income.toString());
		//memberBill.setBillDay(day);
		return memberBill;
		
	}
	
	
	@Override
	public MemberBill getMemberBillDay(String startDay ,String endDay, Member member) {
		String startDate  = null;
		String endDate = null;
		if(endDay == null){
			startDate  = startDay+" 00:00:00";
			endDate =  startDay+" 23:59:59";
		}else{
			startDate  = startDay+" 00:00:00";
			endDate =  endDay+" 23:59:59";
		}
		
		Date startDates = DateUtil.getStringtoDate(startDate, "yyyy-MM-dd HH:mm:ss");
		Date endDates = DateUtil.getStringtoDate(endDate, "yyyy-MM-dd HH:mm:ss");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
		Root<Deposit> root = criteriaQuery.from(Deposit.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDates, endDates));
		criteriaQuery.where(restrictions);
		List<Deposit> depositList = super.findList(criteriaQuery, null, null, null, null);
		
		if(depositList.size()<=0){
			return null;
		}
		
		MemberBill memberBill = new MemberBill();
		//充值
		BigDecimal income = new BigDecimal(0);
		//支出
		BigDecimal expend = new BigDecimal(0);
		for(Deposit deposit : depositList){
			
					MemberBillDetails memberBillDetails = new MemberBillDetails();
					
					//会员充值
					income = income.add(deposit.getCredit());
					expend = expend.add(deposit.getDebit());
					memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
					memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
					memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
					memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
					memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
					memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
					memberBill.getMemberBillDetails().add(memberBillDetails);
				}
			
			memberBill.setTotalRecharge(expend.toString());
			memberBill.setTotalAddress(income.toString());
			memberBill.setBillDay("");
			return memberBill;
			}
	
	
	@Override
	public MemberBill getMemberBills(Mechanism mechanismid,String type,String day, Member member) {
		
		String startDate  = day+" 00:00:00";
		String endDate =  day+" 23:59:59";
		Date startDates = DateUtil.getStringtoDate(startDate, "yyyy-MM-dd HH:mm:ss");
		Date endDates = DateUtil.getStringtoDate(endDate, "yyyy-MM-dd HH:mm:ss");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
		Root<Deposit> root = criteriaQuery.from(Deposit.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(mechanismid != null && !mechanismid.equals("")){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"),  mechanismid));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDates, endDates));
		criteriaQuery.where(restrictions);
		List<Deposit> depositList = super.findList(criteriaQuery, null, null, null, null);
		List<MemberBill> member_bill = new ArrayList<MemberBill>();
		if(depositList.size()<=0){
			return null;
		}
		
		/*MemberBill memberBill = new MemberBill();
		//充值
		BigDecimal income = new BigDecimal(0);
		//支出
		BigDecimal expend = new BigDecimal(0);
		for(Deposit deposit : depositList){

			MemberBillDetails memberBillDetails = new MemberBillDetails();
			
			//会员充值
			income = income.add(deposit.getCredit());
			expend = expend.add(deposit.getDebit());
			memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
			memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
			memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
			memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
			memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
			memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
			memberBill.getMemberBillDetails().add(memberBillDetails);
			memberBill.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
			memberBill.setBillTypes(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillTypes.consumption:BillTypes.recharge);
			memberBill.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
			memberBill.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
			memberBill.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
			memberBill.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
		}
		
		memberBill.setTotalRecharge(expend.toString());
		memberBill.setTotalAddress(income.toString());
		memberBill.setBillDay(day);
		return memberBill;*/
		MemberBill memberBill = new MemberBill();
		DecimalFormat df = new DecimalFormat("######0.00"); 
		if(type == null || type.equals("")){//判断type是否为空:全部传null,收入传1,支出传2,退款传3
	
			//充值
			BigDecimal income = new BigDecimal(0);
			//支出
			BigDecimal expend = new BigDecimal(0);
			for(Deposit deposit : depositList){
				
				MemberBillDetails memberBillDetails = new MemberBillDetails();
				
				//会员充值
				income = income.add(deposit.getCredit());
				expend = expend.add(deposit.getDebit());
				memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
				memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
				memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
				memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
				memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
				memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
				memberBill.getMemberBillDetails().add(memberBillDetails);
					/*income = income.add(deposit.getCredit());
					expend = expend.add(deposit.getDebit());
					System.out.println(expend);*/
			}
			
			memberBill.setBillDay(DateUtil.getDatetoString("yyyy-MM-dd",DateUtil.getStringtoDate(startDate, "yyyy-MM-dd")));
			memberBill.setBillDays(DateUtil.getDatetoString("yyyy-MM-dd",DateUtil.getStringtoDate(endDate, "yyyy-MM-dd")));
			memberBill.setTotalRecharge(expend.toString());
			memberBill.setTotalAddress(income.toString());
			memberBill.setTotalAddress(df.format(income.doubleValue()));
			Double number = Math.abs(Double.parseDouble(df.format(expend)));
			memberBill.setTotalRecharge(String.valueOf(number));
			
			member_bill.add(memberBill);
		}else{//type不为空
			List<Deposit> lists = new ArrayList<Deposit>();
			for(Deposit dp : depositList){//处理收入还是支出
				int t = dp.getType().ordinal();
				
				if(type.equals("1")){//判断收入支出的值,1是收入,2是支出,null是全部
					if(t == 0 || t == 2 || t == 6){//判断存储的类型
						lists.add(dp);
					}
				}else if(type.equals("2")){//
					if(t == 1 || t == 4 || t == 8){
						lists.add(dp);
					}
				}else if(type.equals("3")){
					if(t == 5 || t == 9){
						lists.add(dp);
					}
				}else{
					lists.add(dp);
				}
			}
			if(lists.size()<=0){
				return null;
			}
			//充值
			BigDecimal income = new BigDecimal(0);
			//支出
			BigDecimal expend = new BigDecimal(0);
			for(Deposit deposit : lists){
				
				MemberBillDetails memberBillDetails = new MemberBillDetails();
				
				//会员充值
				/*income = income.add(deposit.getCredit());
				expend = expend.add(deposit.getDebit());*/
				memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
				memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
				memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
				memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
				memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
				memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
				memberBill.getMemberBillDetails().add(memberBillDetails);
					income = income.add(deposit.getCredit());
					expend = expend.add(deposit.getDebit());
			}
			memberBill.setBillDay(DateUtil.getDatetoString("yyyy-MM-dd",DateUtil.getStringtoDate(startDate, "yyyy-MM-dd")));
			memberBill.setBillDays(DateUtil.getDatetoString("yyyy-MM-dd",DateUtil.getStringtoDate(endDate, "yyyy-MM-dd")));
			memberBill.setTotalAddress(df.format(income.doubleValue()));
			Double number = Math.abs(expend.doubleValue());
			memberBill.setTotalRecharge(String.valueOf(number));
			member_bill.add(memberBill);
		}
		return memberBill;
		
	}

	@Override
	public List<Deposit> findList(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
		Root<Deposit> root = criteriaQuery.from(Deposit.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findList(criteriaQuery, null, null, null, null);
	}

		
}






