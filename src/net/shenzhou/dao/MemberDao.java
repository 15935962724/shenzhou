/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.BeforehandLogin.UserType;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;

/**
 * Dao - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface MemberDao extends BaseDao<Member, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);
	
	/**
	 * 判断手机号是否存在
	 * 
	 * @param mobile
	 *            
	 * @return 手机号是否存在
	 */
	boolean mobileExists(String mobile);
	
	

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
	Member findByUsername(String username);
	
	/**
	 * 根据秘钥查找会员
	 * 
	 * @param 秘钥
	 * @return 会员，若不存在则返回null
	 */
	Member findBySafeKeyValue(String safeKeyValue);
	
	/**
	 * 根据手机号查找会员
	 * @param mobile
	 * @return
	 */
	Member findByMobile(String mobile);
	

	/**
	 * 根据E-mail查找会员
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	List<Member> findListByEmail(String email);

	/**
	 * 查找会员消费信息
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param count
	 *            数量
	 * @return 会员消费信息
	 */
	List<Object[]> findPurchaseList(Date beginDate, Date endDate, Integer count);

	
	List<Member> getMemberByName(String name);
	
	/**
	 * 查询机构下的患者
	 * @param mechanism
	 * @return
	 */
	Page<Member> getMembers(Pageable pageable ,Mechanism mechanism);
	
	
	/**
	 * 根据姓名模糊查询
	 * @param name
	 * @return
	 */
	List<Member> getMemberByLikeName(String name);
	
	/**
	 * 查找机构下的用户
	 * @param query_map
	 * @return
	 */
	Page<Member> getMemberLists(Map<String,Object>query_map);
	
	
	/**
	 * 查找机构下的患者
	 * @param query_map
	 * @return
	 */
	Page<Member> getPatientLists(Map<String,Object>query_map);
	
	/**
	 * 导出患者信息(列表)
	 * @param query_map
	 * @return
	 */
	List<Member> downloadPatientList(Map<String,Object>query_map);
	
	
	
	/**
	 * 根据姓名或手机号模糊查询本机构下的用户(充值模糊查询)
	 * @param query_map
	 * @return
	 */
	List<Member> getMembersByNameOrMobile(Map<String,Object> query_map);
	
	
	/**
	 * 根据姓名或手机号模糊查询所有用户或患者
	 * @param nameOrmobile
	 * @return
	 */
	List<Member> getMembersByNameOrMobile(String nameOrmobile);
	
	
	/**
	 * 平台端查询用户
	 * @param query_map
	 * @return
	 */
	Page<Member> getMembers(Map<String,Object> query_map);
	
	/**
	 * 平台端查询患者
	 * @param query_map
	 * @return
	 */
	Page<Member> getPatients(Map<String,Object> query_map);
	
	/**
	 * 导出患者状态
	 * @param query_map
	 * @return
	 */
	List<Member> downloadPatientHealthType(Map<String,Object> query_map);
	
	
	/**
	 * 导出用户信息(用户列表)
	 * @param query_map
	 * @return
	 */
	List<Member> downloadMemberList(Map<String,Object> query_map);
	
	
	
	/**
	 * 判断身份证是否存在
	 * 
	 * @param cardId
	 *            身份证号
	 * @return 身份证是否存在
	 */
	boolean cardIdExists(String cardId);
	
	
	/**
	 * 该机构下所有用户的总金额
	 * @param query_map
	 * @return
	 */
	BigDecimal sumBalance(Map<String, Object> query_map);
	
	

	
}