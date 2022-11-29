/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.dao.EvaluateDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Project;
import net.shenzhou.service.EvaluateService;

import org.springframework.stereotype.Service;

/**
 * 2017-06-05 17:44:04
 * 评论
 * @author wsr
 *
 */
@Service("evaluateServiceImpl")
public class EvaluateServiceImpl extends BaseServiceImpl<Evaluate, Long> implements EvaluateService {

	@Resource(name = "evaluateDaoImpl")
	private EvaluateDao evaluateDao;

	@Resource(name = "evaluateDaoImpl")
	public void setBaseDao(EvaluateDao evaluateDao) {
		super.setBaseDao(evaluateDao);
	}

	@Override
	public List<Evaluate> findList(Doctor doctor) {
		// TODO Auto-generated method stub
		return evaluateDao.findList(doctor);
	}

	@Override
	public Object findObject(Project project) {
		// TODO Auto-generated method stub
		return evaluateDao.findObject(project);
	}

	@Override
	public Page<Evaluate> getDoctorEvaluate(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return evaluateDao.getDoctorEvaluate(query_map);
	}

	@Override
	public Page<Evaluate> getPageEvaluate(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return evaluateDao.getPageEvaluate(query_map);
	}

	@Override
	public Page<Evaluate> getPageEvaluateCharge(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return evaluateDao.getPageEvaluateCharge(query_map);
	}

	@Override
	public List<Evaluate> getDownloadList(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return evaluateDao.getDownloadList(query_map);
	}

	@Override
	public List<Evaluate> getDoctorEvaluateList(Map<String, Object> query_map) {
		
		return evaluateDao.getDoctorEvaluateList(query_map);
	}


}