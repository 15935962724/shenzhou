/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.MemberBill;

/**
 * Service - 预存款
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface DepositService extends BaseService<Deposit, Long> {

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
	 * 机构端查询预存款记录
	 * @param query_map
	 * @return
	 */
	List<Deposit> getMechanismDepositList(Map<String, Object> query_map);
	
	/**
	 * 机构端查询预存款分页
	 * @param query_map
	 * @return
	 */
	Page<Deposit> getMechanismDepositFindPage(Map<String, Object> query_map);

	/**
	 * 按月份获取用户账单列表
	 * @param query_map
	 * @return
	 */
	List<MemberBill> getMemberBillByMonth(String month ,Member member);
	
	
	/**
	 * 获取当前月份的用户账单列表
	 * @param query_map
	 * @return
	 */
	List<MemberBill> getMemberBill(Member member);
	
	/**
	 * 导出预存款统计
	 * @param query_map
	 * @return
	 */
	List<Deposit> downloadList(Map<String, Object> query_map);
	
	
	
	/**
	 * 按分类(今天,一周,最近一个月)获取用户账单列表
	 * @param query_map
	 * @return
	 */
	List<MemberBill> getMemberBillByType(String month ,Member member);
	
	
	/**
	 * 按时间段获取用户账单列表
	 * @param query_map
	 * @return
	 */
	List<MemberBill> getMemberBillByParagraph(String startTime,String endTime ,Member member);
	
	
	/**
	 * 获取用户账单列表
	 * @param query_map
	 * @return
	 */
	List<MemberBill> getMemberBillByDay(String mechanismid,String startDay ,String endDay ,String type,Member member);
	
	
	
	/**
	 * 获取用户账单列表,我写的
	 * @param query_map
	 * @return
	 */
	List<MemberBill> getMemberBillList(Member member);
	
	/**
	 * 按月份获取用户账单列表
	 * @param query_map
	 * @return
	 */
	List<MemberBill> getMemberBillByMonths(String startDay ,String endDay,Member member);
	
	
	/**
	 * 按月份获取用户账单列表(wo)
	 * @param query_map
	 * @return
	 */
	List<MemberBill> getMemberBillBy(Mechanism mechanismid,String type,String startDay ,String endDay,Member member);


	/**
	 * 获取用户全部账单列表,我写的
	 * @param query_map
	 * @return
	 */
	List<Deposit> getDepositAllList(Member member);
}