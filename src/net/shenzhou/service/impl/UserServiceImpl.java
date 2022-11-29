/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.Page;
import net.shenzhou.Principal;
import net.shenzhou.Setting;
import net.shenzhou.dao.UserDao;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.entity.User;
import net.shenzhou.service.UserService;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Service - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	@Resource(name = "userDaoImpl")
	private UserDao userDao;
	

	@Resource(name = "userDaoImpl")
	public void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
	}

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return userDao.usernameExists(username);
	}

	@Transactional(readOnly = true)
	public boolean usernameDisabled(String username) {
		Assert.hasText(username);
		Setting setting = SettingUtils.get();
		if (setting.getDisabledUsernames() != null) {
			for (String disabledUsername : setting.getDisabledUsernames()) {
				if (StringUtils.containsIgnoreCase(username, disabledUsername)) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return userDao.emailExists(email);
	}

	@Transactional(readOnly = true)
	public boolean emailUnique(String previousEmail, String currentEmail) {
		if (StringUtils.equalsIgnoreCase(previousEmail, currentEmail)) {
			return true;
		} else {
			if (userDao.emailExists(currentEmail)) {
				return false;
			} else {
				return true;
			}
		}
	}


	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userDao.findByUsername(username);
	}

	
	@Transactional(readOnly = true)
	public User getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return userDao.find(principal.getId());
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	@Override
	public Page<User> getMechanismUsers(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return userDao.getMechanismUsers(query_map);
	}

	@Override
	public List<String> findAuthorities(Long id) {
		// TODO Auto-generated method stub
		List<String> authorities = new ArrayList<String>();
		User user = userDao.find(id);
		if (user != null) {
			for (MechanismRole mechanismRole : user.getMechanismroles()) {
				authorities.addAll(mechanismRole.getAuthorities());
			}
		}
		return authorities;
	}

	


	
}