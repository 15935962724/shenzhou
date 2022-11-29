/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;

import net.shenzhou.entity.MemberAttribute;

/**
 * Dao - 会员注册项
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface MemberAttributeDao extends BaseDao<MemberAttribute, Long> {

	/**
	 * 查找未使用的对象属性序号
	 * 
	 * @return 未使用的对象属性序号，若无可用序号则返回null
	 */
	Integer findUnusedPropertyIndex();

	/**
	 * 查找已启用会员注册项
	 * 
	 * @return 已启用会员注册项
	 */
	List<MemberAttribute> findList();

}