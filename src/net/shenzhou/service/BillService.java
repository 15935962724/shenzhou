/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import net.shenzhou.entity.Bill;
import net.shenzhou.entity.Bill.BillType;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface BillService extends BaseService<Bill, Long> {

	List<Object[]> getBillDate(Doctor doctor);
	
	
	
	/**
	 * 获取账单日期
	 * @throws IOException 
	 * @throws ParseException 
	 */
	List<Bill> getBillDatePack(Doctor doctor,BillType billType);
	
	
	
	/**
	 * 获取经过机构筛选后的账单日期
	 * @throws IOException 
	 * @throws ParseException 
	 */
	List<Bill> filtrateBillDate(Doctor doctor , Mechanism mechanism,BillType billType);
	
	
	
	/**
	 * 根据日期获取账单明细(详细)
	 * @throws IOException 
	 * @throws ParseException 
	 */
	List<Bill> BillDetails(Doctor doctor,BillType billType , Date staterDate,Date endaDate);
	
	
	/**
	 * 根据日期,机构获取账单明细(详细)
	 * @throws IOException 
	 * @throws ParseException 
	 */
	List<Bill> mechanismBillDetails(Doctor doctor,BillType billType , Date staterDate,Date endaDate,Mechanism mechanism);
}