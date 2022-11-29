/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.User;

/**
 * Dao - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface UserDao extends BaseDao<User, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);
	
	
	

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 根据用户名查找会员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	User findByUsername(String username);
	
	/**
	 * 机构端查询管理员
	 * @param query_map
	 * @return
	 */
	Page<User> getMechanismUsers (Map<String,Object> query_map);

	
}