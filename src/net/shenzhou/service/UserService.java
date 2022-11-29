/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import net.shenzhou.Page;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.User;

/**
 * Service - 会员
 * 
 * @author SHARE Team
 * @version 1.0
 */
@WebService
public interface UserService extends BaseService<User, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 判断用户名是否禁用
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否禁用
	 */
	boolean usernameDisabled(String username);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 判断E-mail是否唯一
	 * 
	 * @param previousEmail
	 *            修改前E-mail(忽略大小写)
	 * @param currentEmail
	 *            当前E-mail(忽略大小写)
	 * @return E-mail是否唯一
	 */
	boolean emailUnique(String previousEmail, String currentEmail);
	
	/**
	 * 根据ID查找权限
	 * 
	 * @param id
	 *            ID
	 * @return 权限,若不存在则返回null
	 */
	List<String> findAuthorities(Long id);

	/**
	 * 判断管理员是否登录
	 * 
	 * @return 管理员是否登录
	 */
	boolean isAuthenticated();
	
	/**
	 * 根据用户名查找对象
	 * @param username
	 * @return
	 */
    User findByUsername(String username);
	
	
	/**
	 * 获取当前登录机构的管理员对象
	 * @return
	 */
	User getCurrent();


	/**
	 * 机构端查询管理员列表
	 * @param query_map
	 * @return
	 */
	Page<User> getMechanismUsers(Map<String, Object> query_map);
	
	
	
}