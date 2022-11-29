/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.DepositDao;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Deposit.Type;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.MemberBill;
import net.shenzhou.entity.MemberBill.TimeType;
import net.shenzhou.entity.MemberBillDetails;
import net.shenzhou.entity.MemberBillDetails.BillType;
import net.shenzhou.service.DepositService;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 预存款
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("depositServiceImpl")
public class DepositServiceImpl extends BaseServiceImpl<Deposit, Long> implements DepositService {

	@Resource(name = "depositDaoImpl")
	private DepositDao depositDao;

	@Resource(name = "depositDaoImpl")
	public void setBaseDao(DepositDao depositDao) {
		super.setBaseDao(depositDao);
	}

	@Transactional(readOnly = true)
	public Page<Deposit> findPage(Member member, Pageable pageable) {
		return depositDao.findPage(member, pageable);
	}

	@Override
	public List<Deposit> getMechanismDepositList(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return depositDao.getMechanismDepositList(query_map);
	}

	@Override
	public Page<Deposit> getMechanismDepositFindPage(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return depositDao.getMechanismDepositFindPage(query_map);
	}

	@Override
	public List<MemberBill> getMemberBillByMonth(String month, Member member) {
		
		SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
		Map<String, String> dateMap;
		try {
			dateMap = DateUtil.getDateStartEnd(month);
			String staterDate= dateMap.get("startDate");
	    	String endaDate =dateMap.get("endDate");
			
	    	String stater_Date = DateUtil.getDatetoString("yyyy-MM", DateUtil.getStringtoDate(staterDate, "yyyy-MM-dd"));
	    	String enda_Date = DateUtil.getDatetoString("yyyy-MM-dd", DateUtil.getStringtoDate(endaDate, "yyyy-MM-dd"));
			List<MemberBill> member_bill = new ArrayList<MemberBill>();
			
			for(int x = 0;;x++){
				/*System.out.println(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x), "yyyy-MM")));
				System.out.println(day_month);*/
				if(!stater_Date.equals(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(enda_Date, "yyyy-MM-dd"), x), "yyyy-MM")))){
					break;
				}
				MemberBill memberBill = depositDao.getMemberBill(DateUtil.getLastDate(DateUtil.getStringtoDate(enda_Date, "yyyy-MM-dd"), x), member);
				if(memberBill!=null){
					member_bill.add(memberBill);
				}
			}
			
			System.out.print(member_bill);
			
			return member_bill;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MemberBill> getMemberBill(Member member) {
		//获取当月用户账单
		Date date = new Date();
		SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
		String day_day = format_day.format(date);
		String day_month = format_month.format(date);
		
		List<MemberBill> member_bill = new ArrayList<MemberBill>();
		
		for(int x = 0;;x++){
			/*System.out.println(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x), "yyyy-MM")));
			System.out.println(day_month);*/
			if(!day_month.equals(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x), "yyyy-MM")))){
				break;
			}
			System.out.println(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x));
			System.out.println(day_month);
			
			MemberBill memberBill = depositDao.getMemberBill(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x), member);
			if(memberBill!=null){
				member_bill.add(memberBill);
			}
		}
		
		System.out.print(member_bill);
		
		return member_bill;
	}

	@Override
	public List<Deposit> downloadList(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return depositDao.downloadList(query_map);
	}

	@Override
	public List<MemberBill> getMemberBillByType(String month, Member member) {
		
		List<MemberBill> memberBillList = new ArrayList<MemberBill>();
		SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd");
		/**今天的账单*/
		if(TimeType.TODAY.getClor().equals(month)){
			MemberBill memberBill = depositDao.getMemberBill(format_day.format(new Date()), member);
			if(null!=memberBill){
				memberBillList.add(memberBill);
			}
		}
		/**最近7天的账单*/
		else if(TimeType.WEEK.getClor().equals(month)){
			String weekTime = format_day.format(new Date());
			for(int x=0;x<=7;x++){
				MemberBill memberBill = depositDao.getMemberBill(DateUtil.getLastDate(DateUtil.getStringtoDate(weekTime, "yyyy-MM-dd"), x), member);
				if(null!=memberBill){
					memberBillList.add(memberBill);
				}
			}
		}
		/**最近一个月的账单*/
		else if(TimeType.MONTH.getClor().equals(month)){
			String weekTime = format_day.format(new Date());
			for(int x=0;x<=30;x++){
				MemberBill memberBill = depositDao.getMemberBill(DateUtil.getLastDate(DateUtil.getStringtoDate(weekTime, "yyyy-MM-dd"), x), member);
				if(null!=memberBill){
					memberBillList.add(memberBill);
				}
			}
		}
		
		return memberBillList;
	}

	@Override
	public List<MemberBill> getMemberBillByParagraph(String startTime,
			String endTime, Member member) {
		//获取当月用户账单
		Date date = new Date();
		SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
		String day_day = format_day.format(date);
		String day_month = format_month.format(date);
		
		List<MemberBill> member_bill = new ArrayList<MemberBill>();
		
		for(int x = 0;;x++){
			if(DateUtil.compare_date_days(DateUtil.getLastDate(DateUtil.getStringtoDate(endTime, "yyyy-MM-dd"), x), startTime)==1){
				break;
			}
			System.out.println(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x));
			System.out.println(day_month);
			
			MemberBill memberBill = depositDao.getMemberBill(DateUtil.getLastDate(DateUtil.getStringtoDate(endTime, "yyyy-MM-dd"), x), member);
			if(memberBill!=null){
				member_bill.add(memberBill);
			}
		}
		
		System.out.print(member_bill);
		
		return member_bill;
	}

	@Override
	public List<MemberBill> getMemberBillByDay(String mechanismid,String startDay, String endDay,String type, Member member) {
		//获取当月用户账单
				Date date = new Date();
				SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
				String day_day = format_day.format(member.getCreateDate());
				String day_month = format_month.format(date);
		try {
				List<MemberBill> member_bill = new ArrayList<MemberBill>();
				List<Deposit> memberBill_list = new ArrayList<Deposit>();	
				List<String> list = null;
					try {
						list = DateUtil.getMonthBetween(DateUtil.getDatetoString("yyyy-MM",member.getCreateDate()),day_month);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Collections.reverse(list);
					memberBill_list = depositDao.getMemberBillScreen(mechanismid,startDay,endDay,type, member);
					DecimalFormat df = new DecimalFormat("######0.00"); 
					if(startDay != null || endDay != null){//判断月是否为空
							if(memberBill_list.size()<=0){
								return member_bill;
							}
							if(type == null || type.equals("")){//判断type是否为空:全部传null,收入传1,支出传2,退款传3
								MemberBill memberBill = new MemberBill();
								//充值
								BigDecimal income = new BigDecimal(0);
								//支出
								BigDecimal expend = new BigDecimal(0);
								for(Deposit deposit : memberBill_list){
									
									MemberBillDetails memberBillDetails = new MemberBillDetails();
									
									//会员
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
								memberBill.setBillDay(startDay);
								memberBill.setBillDays(endDay);
								/*memberBill.setTotalRecharge(expend.toString());
								memberBill.setTotalAddress(income.toString());*/
								memberBill.setTotalAddress(df.format(income.doubleValue()));
								Double number = Math.abs(Double.parseDouble(df.format(expend)));
								memberBill.setTotalRecharge(String.valueOf(number));
								
								member_bill.add(memberBill);
							}else{//type不为空
								List<Deposit> lists = new ArrayList<Deposit>();
								for(Deposit dp : memberBill_list){//处理收入还是支出
									int t = dp.getType().ordinal();
									System.out.println(t);
									
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
								MemberBill memberBill = new MemberBill();
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
									//memberBill.getMemberBillDetails().add(memberBillDetails);
										income = income.add(deposit.getCredit());
										expend = expend.add(deposit.getDebit());
								}
								memberBill.setBillDay(startDay);
								memberBill.setBillDays(endDay);
								memberBill.setTotalAddress(df.format(income.doubleValue()));
								Double number = Math.abs(expend.doubleValue());
								memberBill.setTotalRecharge(String.valueOf(number));
								member_bill.add(memberBill);
							}
							
						
							
							
					}else{//日期为空
						for(int i=0;i<list.size();i++){
							List<Deposit> lists = new ArrayList<Deposit>();
							if(type == null || type.equals("") ){//判断type为不为空
								for(Deposit dp : memberBill_list){//处理收入还是支出
									int t = dp.getType().ordinal();
									System.out.println(t);
									
									if(type.equals("1")){//判断收入支出的值,1是收入,2是支出,null是全部
										if(t == 0 || t == 2 || t == 6){
											lists.add(dp);
										}
										/*if(dp.getCredit().doubleValue() != 0.0){
											lists.add(dp);
										}*/
									}else if(type.equals("2")){//
										if(t == 1 || t == 4 || t == 8){
											lists.add(dp);
										}
										/*if(dp.getDebit().doubleValue() != 0.0){
											lists.add(dp);
										}*/
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
								
								MemberBill memberBill = new MemberBill();
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
									//memberBill.getMemberBillDetails().add(memberBillDetails);
									String mo = format_month.format(deposit.getCreateDate());
									if(list.get(i).equals(mo)){
										income = income.add(deposit.getCredit());
										System.out.println(deposit.getDebit());
										expend = expend.add(deposit.getDebit());
										System.out.println(expend);
									}
								}
								
								memberBill.setTotalAddress(df.format(income.doubleValue()));
								Double number = Math.abs(expend.doubleValue());
								memberBill.setTotalRecharge(String.valueOf(number));
								//memberBill.setBillDay(end_Day);
							
							
							
							if(day_month.equals(list.get(i))){
								memberBill.setBillDay("本月");
								
							}else{
								memberBill.setBillDay(list.get(i));
							}
							member_bill.add(memberBill);
						}else{//type不为空
							if(memberBill_list.size()<=0){
								return member_bill;
							}
							
							MemberBill memberBill = new MemberBill();
							//充值
							BigDecimal income = new BigDecimal(0);
							//支出
							BigDecimal expend = new BigDecimal(0);
							for(Deposit deposit : memberBill_list){
								
								MemberBillDetails memberBillDetails = new MemberBillDetails();
								
								//会员充值
								
								memberBillDetails.setName(deposit.getOrder()==null?"系统":deposit.getOrder().getDoctor().getName());
								memberBillDetails.setBillType(deposit.getCredit().compareTo(new BigDecimal(0))==0?BillType.consumption:BillType.recharge);
								memberBillDetails.setProjectName(deposit.getOrder()==null?deposit.getCredit().compareTo(new BigDecimal(0))==0?"消费":"充值":deposit.getOrder().getProject().getName());
								memberBillDetails.setMechanismName(deposit.getMechanism()==null?"":deposit.getMechanism().getName());
								memberBillDetails.setMoney(deposit.getCredit().compareTo(new BigDecimal(0))==0?deposit.getDebit().toString():deposit.getCredit().toString());
								memberBillDetails.setTime(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()));
								memberBill.getMemberBillDetails().add(memberBillDetails);
								
								String mo = format_month.format(deposit.getCreateDate());
								if(list.get(i).equals(mo)){
									income = income.add(deposit.getCredit());
									expend = expend.add(deposit.getDebit());
								}
							
							}
							
							memberBill.setTotalAddress(df.format(income.doubleValue()));
							
							Double number = Math.abs(expend.doubleValue());
							memberBill.setTotalRecharge(String.valueOf(number));
							//memberBill.setBillDay(end_Day);
							if(startDay == null || startDay.equals("") && endDay == null || endDay.equals("")){//返回月值
								if(day_month.equals(list.get(i))){
									memberBill.setBillDay("本月");
									
								}else{
									memberBill.setBillDay(list.get(i));
								}
							}
							
							member_bill.add(memberBill);
						}
							
					}
				
				System.out.print(member_bill);
					}
				return member_bill;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
	}


	
	@Override
	public List<MemberBill> getMemberBillList(Member member) {
		//获取当月用户账单
		Date date = new Date();
		SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
		String day_day = format_day.format(member.getCreateDate());
		String day_month = format_month.format(date);
		List<MemberBill> member_bill = new ArrayList<MemberBill>();
		List<String> list = null;
		try {
			list = DateUtil.getMonthBetween(DateUtil.getDatetoString("yyyy-MM",member.getCreateDate()),day_month);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.reverse(list);
		MemberBill memberBill = new MemberBill();
		for(int i=0;i<list.size();i++){
			//memberBill = depositDao.getMemberBilly(list.get(i),member);
			if(day_month.equals(list.get(i))){
				memberBill.setBillDay("本月");
			}else{
				memberBill.setBillDay(list.get(i));
			}
			member_bill.add(memberBill);
		}
		return member_bill;
	}
	
	
	
	@Override
	public List<MemberBill> getMemberBillByMonths(String startDay,String endDay, Member member) {
		if(endDay == null || endDay.equals("")){
			SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
			Map<String, String> dateMap;
			try {
				dateMap = DateUtil.getDateStartEnd(startDay);
				String staterDate= dateMap.get("startDate");
		    	String endaDate =dateMap.get("endDate");
				
		    	String stater_Date = DateUtil.getDatetoString("yyyy-MM", DateUtil.getStringtoDate(staterDate, "yyyy-MM-dd"));
		    	String enda_Date = DateUtil.getDatetoString("yyyy-MM-dd", DateUtil.getStringtoDate(endaDate, "yyyy-MM-dd"));
				List<MemberBill> member_bill = new ArrayList<MemberBill>();
				
				for(int x = 0;;x++){
					/*System.out.println(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x), "yyyy-MM")));
					System.out.println(day_month);*/
					if(!stater_Date.equals(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(enda_Date, "yyyy-MM-dd"), x), "yyyy-MM")))){
						break;
					}
					MemberBill memberBill = depositDao.getMemberBill(DateUtil.getLastDate(DateUtil.getStringtoDate(enda_Date, "yyyy-MM-dd"), x), member);
					if(memberBill!=null){
						member_bill.add(memberBill);
					}
				}
				
				System.out.print(member_bill);
				
				return member_bill;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}else{
			//SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
			Map<String, String> dateMap;
			try {
				/*dateMap = DateUtil.getDateStartEnd(month);
				String staterDate= dateMap.get("startDate");
		    	String endaDate =dateMap.get("endDate");*/
				
		    	String stater_Date = startDay+" 00:00:00";
		    	String enda_Date = endDay +" 23:59:59";
				
		    	List<MemberBill> member_bill = new ArrayList<MemberBill>();
				
				
					MemberBill memberBill = depositDao.getMemberBilly(stater_Date,enda_Date, member);
					if(memberBill!=null){
						member_bill.add(memberBill);
					}
				
				
				System.out.print(member_bill);
				
				return member_bill;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		
	}

	@Override
	public List<MemberBill> getMemberBillBy(Mechanism mechanismid,String type, String startDay, String endDay, Member member) {

		SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
		Map<String, String> dateMap;
		try {
			String stater_Date = null;
			String enda_Date = null;
			List<MemberBill> member_bill = new ArrayList<MemberBill>();
			if(endDay == null || endDay.equals("")){
				dateMap = DateUtil.getDateStartEnd(startDay);
				String staterDate= dateMap.get("startDate");
		    	String endaDate =dateMap.get("endDate");
				
		    	stater_Date = DateUtil.getDatetoString("yyyy-MM", DateUtil.getStringtoDate(staterDate, "yyyy-MM-dd"));
		    	enda_Date = DateUtil.getDatetoString("yyyy-MM-dd", DateUtil.getStringtoDate(endaDate, "yyyy-MM-dd"));
			
		    	for(int x = 0;;x++){
					//System.out.println(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x), "yyyy-MM")));
					//System.out.println(day_month);
					if(!stater_Date.equals(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(enda_Date, "yyyy-MM-dd"), x), "yyyy-MM")))){
						break;
					}
					MemberBill memberBill = depositDao.getMemberBills(mechanismid,type,DateUtil.getLastDate(DateUtil.getStringtoDate(enda_Date, "yyyy-MM-dd"), x), member);
					if(memberBill!=null){
						member_bill.add(memberBill);
					}
				}
			}else{
				stater_Date = DateUtil.getDatetoString("yyyy-MM", DateUtil.getStringtoDate(startDay, "yyyy-MM-dd"));
				enda_Date = DateUtil.getDatetoString("yyyy-MM", DateUtil.getStringtoDate(endDay, "yyyy-MM-dd"));
				List<Date> list = null;
				for(int x = 0;;x++){
					if(stater_Date.equals(enda_Date)){
						if(!stater_Date.equals(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(endDay, "yyyy-MM-dd"), x), "yyyy-MM")))){
							break;
						}
					}else{
						if(stater_Date.equals(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(endDay, "yyyy-MM-dd"), x), "yyyy-MM")))){
							break;
						}
					}
					
					list = DateUtil.getBetweenDates(DateUtil.getStringtoDate(startDay, "yyyy-MM-dd"),DateUtil.getStringtoDate(endDay, "yyyy-MM-dd"));
					
				}
				for(Date d : list){
					MemberBill memberBill = depositDao.getMemberBills(mechanismid,type,DateUtil.getDatetoString("yyyy-MM-dd", d), member);
					
					if(memberBill!=null){
						member_bill.add(memberBill);
					}
					}
			}
			
			
	    	
			
			
			
			System.out.print(member_bill);
			
			return member_bill;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Deposit> getDepositAllList(Member member) {
		// TODO Auto-generated method stub
		return depositDao.findList(member);
	}
	
	
	/*@Override
	public List<MemberBill> getMemberBillBy(String type,String startDay ,String endDay, Member member) {
		
		SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
		Map<String, String> dateMap;
		try {
			String stater_Date = null;
			String enda_Date = null;
			List<MemberBill> member_bill = new ArrayList<MemberBill>();
			if(endDay == null || endDay.equals("")){
				dateMap = DateUtil.getDateStartEnd(startDay);
				String staterDate= dateMap.get("startDate");
		    	String endaDate =dateMap.get("endDate");
				
		    	stater_Date = DateUtil.getDatetoString("yyyy-MM", DateUtil.getStringtoDate(staterDate, "yyyy-MM-dd"));
		    	enda_Date = DateUtil.getDatetoString("yyyy-MM-dd", DateUtil.getStringtoDate(endaDate, "yyyy-MM-dd"));
			
		    	for(int x = 0;;x++){
					System.out.println(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(day_day, "yyyy-MM-dd"), x), "yyyy-MM")));
					System.out.println(day_month);
					if(!stater_Date.equals(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(enda_Date, "yyyy-MM-dd"), x), "yyyy-MM")))){
						break;
					}
					MemberBill memberBill = depositDao.getMemberBills(type,DateUtil.getLastDate(DateUtil.getStringtoDate(enda_Date, "yyyy-MM-dd"), x), member);
					if(memberBill!=null){
						member_bill.add(memberBill);
					}
				}
			}else{
				stater_Date = DateUtil.getDatetoString("yyyy-MM", DateUtil.getStringtoDate(startDay, "yyyy-MM-dd"));
				enda_Date = DateUtil.getDatetoString("yyyy-MM", DateUtil.getStringtoDate(endDay, "yyyy-MM-dd"));;
				for(int x = 0;;x++){
					if(stater_Date.equals(enda_Date)){
						if(!stater_Date.equals(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(endDay, "yyyy-MM-dd"), x), "yyyy-MM")))){
							break;
						}
					}else{
						if(stater_Date.equals(format_month.format(DateUtil.getStringtoDate(DateUtil.getLastDate(DateUtil.getStringtoDate(endDay, "yyyy-MM-dd"), x), "yyyy-MM")))){
							break;
						}
					}
					
					List<Date> list = DateUtil.getBetweenDates(DateUtil.getStringtoDate(startDay, "yyyy-MM-dd"),DateUtil.getStringtoDate(endDay, "yyyy-MM-dd"));
					for(Date d : list){
					MemberBill memberBill = depositDao.getMemberBills(type,DateUtil.getDatetoString("yyyy-MM-dd", d), member);
					
					if(memberBill!=null){
						member_bill.add(memberBill);
					}
					}
				}
			}
			
			
	    	
			
			
			
			System.out.print(member_bill);
			
			return member_bill;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	
}














