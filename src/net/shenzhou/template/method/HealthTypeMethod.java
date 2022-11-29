/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.template.method;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientMechanism.HealthType;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 获取年龄
 * 2017-8-2 11:29:42
 * @author wsr
 *
 */
@Component("healthTypeMethod")
public class HealthTypeMethod implements TemplateMethodModel {
	
	@Resource(name = "memberServiceImpl")
    private MemberService memberService;
	@Resource(name = "doctorServiceImpl")
    private DoctorService doctorService;
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null) {
			return getHealthType(arguments.get(0));
		}
		return null;
	}

	/**
	 * return 缩略字符
	 */
	public HealthType getHealthType(Object pateintId) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patient = memberService.find(Long.valueOf(String.valueOf(pateintId)));	 
		return patient.getPatientMechanism(mechanism).getHealthType();
		
	}

}