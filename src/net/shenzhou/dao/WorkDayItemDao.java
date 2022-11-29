/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDayItem;

/**
 * 2017-5-25 09:24:01
 * @author wsr
 *
 */
public interface WorkDayItemDao extends BaseDao<WorkDayItem, Long> {


	/**
	 * 获取医生锁定的时间段列表
	 * 
	 * @param workday
	 *           工作日
	 * @return 若不存在则返回null
	 */
	public List<WorkDayItem> getLockTime(WorkDay workday);
	
	
	/**
	 * 根据开始时间获取时间段
	 * 
	 * @param startTime
	 *           开始时间
	 * @return 若不存在则返回null
	 */
	public WorkDayItem getByStartTime(String startTime , WorkDay workday);
	
	
	/**
	 * 根据患者查询时间段
	 * 
	 * @param startTime
	 *           开始时间
	 * @return 若不存在则返回null
	 */
	public List<WorkDayItem> getByMember(Member member);
	
	
	/**
	 * 根据工作日获取医生当天的机构排班情况
	 * 
	 * @param startTime
	 *           开始时间
	 * @return 若不存在则返回null
	 */
	public List<WorkDayItem> getDoctorMechanismTime(Doctor doctor, WorkDay workDay);
	
	
	/**
	 * 根据工作日获取医生当天的全部订单时间段
	 * 
	 * @param startTime
	 *           开始时间
	 * @return 若不存在则返回null
	 */
	public List<WorkDayItem> getDoctorOrderTime(Doctor doctor , WorkDay workDay);
	
	
	
	/**
	 * 根据工作日获取医生当天的全部锁定时间段
	 * 
	 * @param startTime
	 *           开始时间
	 * @return 若不存在则返回null
	 */
	public List<WorkDayItem> getDoctorLockTime(Doctor doctor , WorkDay workDay);
	
	
	/**
	 * 获取医生除机构时间外的所有时间段
	 * 
	 * @param startTime
	 *           开始时间
	 * @return 若不存在则返回null
	 */
	public List<WorkDayItem> getWorkDayItem(Doctor doctor , WorkDay workDay);
	
	
	
	/**
	 * 获取医生当前机构的
	 * 
	 * @param startTime
	 *           开始时间
	 * @return 若不存在则返回null
	 */
	public List<WorkDayItem> getMechanismWorkDayItemByMechanism(Doctor doctor , WorkDay workDay,Mechanism mechanism);
	
	
	
	/**
	 * 获取医生当前机构除机构时间外的时间段
	 * 
	 * @param startTime
	 *           开始时间
	 * @return 若不存在则返回null
	 */
	public List<WorkDayItem> getWorkDayItemByMechanism(Doctor doctor , WorkDay workDay,Mechanism mechanism);
	
	
	/**
	 * 获取机构下所有的时间段
	 * @param query_map
	 * @return
	 */
	public List<WorkDayItem> getWorkDayItems(Map<String,Object> query_map);
	
	
}