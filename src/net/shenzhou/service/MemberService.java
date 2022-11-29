/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.BeforehandLogin.UserType;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;

/**
 * Service - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface MemberService extends BaseService<Member, Long> {

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
	 * @param mobile 手机号
	 *            
	 * @return 用户名是否存在
	 */
	boolean mobileExists(String mobile);

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
	 * 保存会员
	 * 
	 * @param member
	 *            会员
	 * @param operator
	 *            操作员
	 */
	void save(Member member, Admin operator);

	/**
	 * 更新会员
	 * 
	 * @param member
	 *            会员
	 * @param modifyPoint
	 *            修改积分
	 * @param modifyBalance
	 *            修改余额
	 * @param depositMemo
	 *            修改余额备注
	 * @param operator
	 *            操作员
	 */
	void update(Member member, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo, Admin operator);

	/**
	 * 根据用户名查找会员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByUsername(String username);
	
	
	/**
	 * 根据密钥查找会员
	 * @param mobile
	 * @return
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

	/**
	 * 判断会员是否登录
	 * 
	 * @return 会员是否登录
	 */
	boolean isAuthenticated();

	/**
	 * 获取当前登录会员
	 * 
	 * @return 当前登录会员，若不存在则返回null
	 */
	Member getCurrent();

	/**
	 * 获取当前登录用户名
	 * 
	 * @return 当前登录用户名，若不存在则返回null
	 */
	String getCurrentUsername();
	
	
	
	List<Member> getMemberByName(String name);
	
	/**
	 * 查询机构下的患者
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Page<Member> getMembers(Pageable pageable, Mechanism mechanism);
	
	/**
	 * 查找机构下的用户
	 * @param query_map
	 * @return
	 */
	Page<Member> getMemberLists(Map<String, Object> query_map);
	
	/**
	 * 机构端查找机构下的患者
	 * @param query_map
	 * @return
	 */
	Page<Member> getPatientLists(Map<String, Object> query_map);

	
	/**
	 * 导出患者信息(患者列表)
	 * @param query_map
	 * @return
	 */
	List<Member> downloadPatientList(Map<String, Object> query_map);
	/**
	 * 查找机构下的用户
	 * @param query_map
	 * @return
	 */
	List<Member> getMembersByNameOrMobile(Map<String, Object> query_map);
	
	
	/**
	 * 平台端查询用户
	 * @param query_map
	 * @return
	 */
	Page<Member> getMembers(Map<String, Object> query_map);
	
	
	/**
	 * 平台端查询患者
	 * @param query_map
	 * @return
	 */
	Page<Member> getPatients(Map<String, Object> query_map);
	
	/**
	 * 患者状态导出
	 * @param query_map
	 * @return
	 */
	List<Member> downloadPatientHealthType(Map<String, Object> query_map);
	
	/**
	 * 用户信息导出(用户列表)
	 * @param query_map
	 * @return
	 */
	List<Member> downloadMemberList(Map<String, Object> query_map);
	
	
	/**
	 * 判断身份证是否存在
	 * 
	 * @param cardId
	 *            身份证号
	 * @return 身份证是否存在
	 */
	boolean cardIdExists(String cardId);
	
	/**
	 * 查询该机构下所有用户的总额
	 * @param query_map
	 * @return
	 */
	BigDecimal sumBalance(Map<String, Object> query_map);
	

	
}