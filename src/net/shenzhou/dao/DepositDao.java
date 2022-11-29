/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.MemberBill;

/**
 * Dao - 预存款
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface DepositDao extends BaseDao<Deposit, Long> {

	/**
	 * 查找预存款分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 预存款分页
	 */
	Page<Deposit> findPage(Member member, Pageable pageable);
	
	/**
	 * 机构端查看预存款
	 * @param query_map
	 * @return
	 */
	List<Deposit> getMechanismDepositList(Map<String,Object> query_map);
	
	/**
	 * 机构端查找预存款分页
	 * @param query_map
	 * @return
	 */
	Page<Deposit> getMechanismDepositFindPage(Map<String,Object> query_map);
	
	
	/**
	 * 根据日期查找当天的账单列表数据
	 * @param query_map
	 * @return
	 */
	MemberBill getMemberBill(String day , Member member);
	
	/**
	 * 导出预存款统计
	 * @param query_map
	 * @return
	 */
	List<Deposit> downloadList(Map<String,Object> query_map);
	
	
	/**
	 * 查找账单列表数据,我写的
	 * @param query_map
	 * @return
	 */
	List<MemberBill> getMemberBillList(Member member);
	
	
	List<Deposit> getMemberBillScreen(String mechanismid,String stater_Day,String end_Day ,String type, Member member);

	MemberBill getMemberBilly(String stater_Day,String end_Day, Member member);
	
	
	/**
	 * 根据日期和月份查找当天的账单列表数据
	 * @param query_map
	 * @return
	 */
	MemberBill getMemberBillDay(String stater_Day,String end_Day , Member member);
	
	
	/**
	 * 根据日期查找当天的账单列表数据
	 * @param query_map
	 * @return
	 */
	MemberBill getMemberBills(Mechanism mechanismid,String type,String day , Member member);

	/**
	 * 获取全部账单
	 * @param query_map
	 * @return
	 */
	List<Deposit> findList(Member member);
}