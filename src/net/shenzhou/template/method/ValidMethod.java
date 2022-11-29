/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.template.method;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.User;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.UserService;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 验证权限
 * @date 2017-11-2 16:23:51
 * @author wsr
 *
 */
@Component("validMethod")
public class ValidMethod implements TemplateMethodModel {

	@Resource(name = "userServiceImpl")
    private UserService userService;
	@Resource(name = "doctorServiceImpl")
    private DoctorService doctorService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
    private DoctorMechanismRelationService doctorMechanismRelationService;
	
	
	
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null) {
			return valid(arguments.get(0).toString());
		}
		return null;
	}

	/**
	 * 返回是否 有权限
	 * @param authoritie
	 * @return
	 */
	private Boolean valid(String authoritie) {
		if (authoritie != null) {
//			User user = userService.getCurrent();
			Doctor doctor = doctorService.getCurrent();
			DoctorMechanismRelation doctorMechanismRelation =  doctor.getDefaultDoctorMechanismRelation();
			if (doctorMechanismRelation==null) {
				return false;
			}
//			List<String> authorities = userService.findAuthorities(user.getId());
			List<String> authorities = doctorMechanismRelationService.findAuthorities(doctorMechanismRelation.getId());
		    return authorities.contains(authoritie);
		} else {
			return false;
		}
	}
	
	/**
	 * 返回是否 有权限
	 * @param authoritie
	 * @return
	 */
	/*private Boolean valid(String authoritie) {
		if (authoritie != null) {
			User user = userService.getCurrent();
			List<String> authorities = userService.findAuthorities(user.getId());
		    return authorities.contains(authoritie);
		} else {
			return false;
		}
	}*/
	
	

}