/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.dao.WorkDayDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.service.WorkDayService;

import org.springframework.stereotype.Service;

/**
 * @author wsr
 * 2017-6-5 09:32:20
 * @author 工作日
 *
 */
@Service("workDayServiceImpl")
public class WorkDayServiceImpl extends BaseServiceImpl<WorkDay, Long> implements WorkDayService {

	@Resource(name = "workDayDaoImpl")
	private WorkDayDao workDayDao;

	@Resource(name = "workDayDaoImpl")
	public void setBaseDao(WorkDayDao workDayDao) {
		super.setBaseDao(workDayDao);
	}

	@Override
	public WorkDay getWorkDayByDoctorAndData(Doctor doctor, Date data) {
		// TODO Auto-generated method stub
		return workDayDao.getWorkDayByDoctorAndData(doctor,data);
	}

	@Override
	public List<WorkDay> getWorkDays(Doctor doctor, Date data) {
		// TODO Auto-generated method stub
		return workDayDao.getWorkDays(doctor,data);
	}

	@Override
	public Page<WorkDay> getPageWorkDays(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return workDayDao.getPageWorkDays(query_map);
	}

	@Override
	public WorkDay getDoctorWorkDays(Doctor doctor, Date data) {
		// TODO Auto-generated method stub
		return workDayDao.getDoctorWorkDays(doctor,data);
	}

}