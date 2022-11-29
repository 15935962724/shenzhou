/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.dao.BillDao;
import net.shenzhou.entity.Bill;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Bill.BillType;
import net.shenzhou.service.BillService;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("billServiceImpl")
public class BillServiceImpl extends BaseServiceImpl<Bill, Long> implements BillService {

	@Resource(name = "billDaoImpl")
	private BillDao billDao;

	@Resource(name = "billDaoImpl")
	public void setBaseDao(BillDao billDao) {
		super.setBaseDao(billDao);
	}

	@Override
	public List<Object[]> getBillDate(Doctor doctor) {
		return billDao.getBillDate(doctor);
	}
	
	
	
	/**
	 * 获取经过机构筛选后的账单日期
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@Override
	public List<Bill> filtrateBillDate(Doctor doctor, Mechanism mechanism,BillType billType) {
		return billDao.filtrateBillDate(doctor,mechanism,billType);
	}
	
	
	
	/**
	 * 获取账单日期
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@Override
	public List<Bill> getBillDatePack(Doctor doctor,BillType billType) {
		return billDao.getBillDatePack(doctor,billType);
	}
	
	
	
	/**
	 * 根据日期获取账单明细(详细)
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@Override
	public List<Bill> BillDetails(Doctor doctor, BillType billType, Date staterDate,Date endaDate) {
		return billDao.BillDetails(doctor,billType,staterDate,endaDate);
	}

	
	
	/**
	 * 根据日期,机构获取账单明细(详细)
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@Override
	public List<Bill> mechanismBillDetails(Doctor doctor, BillType billType,
			Date staterDate, Date endaDate, Mechanism mechanism) {
		return billDao.mechanismBillDetails(doctor,billType,staterDate,endaDate,mechanism);
	}


}