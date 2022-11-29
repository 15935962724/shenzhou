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
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.WorkDay;

/**
 * 
 * 医师工作日Dao
 * 2017-6-5 09:08:20
 * @author wsr
 *
 */
public interface WorkDayService extends BaseService<WorkDay, Long> {

	/**
	 * 获取医生某天的工作日
	 * @throws IOException 
	 * @throws ParseException 
	 */
	WorkDay getWorkDayByDoctorAndData(Doctor doctor,Date data);
	
	/**
	 * 查找当前时间往后7天的工作日
	 * @throws IOException 
	 * @throws ParseException 
	 */
	List<WorkDay> getWorkDays(Doctor doctor,Date data);
	
	/**
	 * 以往排班
	 * @param query_map
	 * @return
	 */
	Page<WorkDay> getPageWorkDays(Map<String, Object> query_map);
	
	
	/**
	 * 查找医生指定日期的工作日
	 * @throws IOException 
	 * @throws ParseException 
	 */
	WorkDay getDoctorWorkDays(Doctor doctor,Date data);
	
}