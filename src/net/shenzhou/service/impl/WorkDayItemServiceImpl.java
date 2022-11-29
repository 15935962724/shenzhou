/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.dao.WorkDayItemDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.service.WorkDayItemService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 预定时间操作
 * 2017-06-19 19:30:34
 * @author wsr
 *
 */
@Transactional
@Service("workDayItemServiceImpl")
public class WorkDayItemServiceImpl extends BaseServiceImpl<WorkDayItem, Long> implements WorkDayItemService {

	@Resource(name = "workDayItemDaoImpl")
	private WorkDayItemDao workDayItemDao;

	@Resource(name = "workDayItemDaoImpl")
	public void setBaseDao(WorkDayItemDao workDayItemDao) {
		super.setBaseDao(workDayItemDao);
	}

	@Override
	public List<WorkDayItem> getLockTime(WorkDay workday) {
		// TODO Auto-generated method stub
		return workDayItemDao.getLockTime(workday);
	}

	@Override
	public WorkDayItem getByStartTime(String startTime , WorkDay workday) {
		return workDayItemDao.getByStartTime(startTime,workday);
	}

	@Override
	public List<WorkDayItem> getByMember(Member member) {
		// TODO Auto-generated method stub
		return workDayItemDao.getByMember(member);
	}

	@Override
	public List<WorkDayItem> getDoctorMechanismTime(Doctor doctor ,WorkDay workDay) {
		// TODO Auto-generated method stub
		return workDayItemDao.getDoctorMechanismTime(doctor,workDay);
	}

	@Override
	public List<WorkDayItem> getDoctorOrderTime(Doctor doctor, WorkDay workDay) {
		// TODO Auto-generated method stub
		return workDayItemDao.getDoctorOrderTime(doctor,workDay);
	}

	@Override
	public List<WorkDayItem> getDoctorLockTime(Doctor doctor, WorkDay workDay) {
		// TODO Auto-generated method stub
		return workDayItemDao.getDoctorLockTime(doctor,workDay);
	}

	@Override
	public List<WorkDayItem> getWorkDayItem(Doctor doctor ,WorkDay workDay) {
		// TODO Auto-generated method stub
		return workDayItemDao.getWorkDayItem(doctor,workDay);
	}

	@Override
	public List<WorkDayItem> getMechanismWorkDayItemByMechanism(Doctor doctor,
			WorkDay workDay,Mechanism mechanism) {
		// TODO Auto-generated method stub
		return workDayItemDao.getMechanismWorkDayItemByMechanism(doctor,workDay,mechanism);
	}

	@Override
	public List<WorkDayItem> getWorkDayItemByMechanism(Doctor doctor,
			WorkDay workDay, Mechanism mechanism) {
		// TODO Auto-generated method stub
		return workDayItemDao.getWorkDayItemByMechanism(doctor,workDay,mechanism);
	}

	@Override
	public List<WorkDayItem> getWorkDayItems(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return workDayItemDao.getWorkDayItems(query_map);
	}

}
