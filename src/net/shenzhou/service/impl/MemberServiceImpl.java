/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.Principal;
import net.shenzhou.Setting;
import net.shenzhou.dao.DepositDao;
import net.shenzhou.dao.MemberDao;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.BeforehandLogin.UserType;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.service.MemberService;
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
@Service("memberServiceImpl")
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {

	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "depositDaoImpl")
	private DepositDao depositDao;

	@Resource(name = "memberDaoImpl")
	public void setBaseDao(MemberDao memberDao) {
		super.setBaseDao(memberDao);
	}

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return memberDao.usernameExists(username);
	}
	
	@Transactional(readOnly = true)
	public boolean mobileExists(String mobile) {
		return memberDao.mobileExists(mobile);
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
		return memberDao.emailExists(email);
	}

	@Transactional(readOnly = true)
	public boolean emailUnique(String previousEmail, String currentEmail) {
		if (StringUtils.equalsIgnoreCase(previousEmail, currentEmail)) {
			return true;
		} else {
			if (memberDao.emailExists(currentEmail)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public void save(Member member, Admin operator) {
		Assert.notNull(member);
		memberDao.persist(member);
		if (member.getBalance().compareTo(new BigDecimal(0)) > 0) {
			Deposit deposit = new Deposit();
			deposit.setType(operator != null ? Deposit.Type.adminRecharge : Deposit.Type.memberRecharge);
			deposit.setCredit(member.getBalance());
			deposit.setDebit(new BigDecimal(0));
			deposit.setBalance(member.getBalance());
			deposit.setOperator(operator != null ? operator.getUsername() : null);
			deposit.setMember(member);
			depositDao.persist(deposit);
		}
	}

	public void update(Member member, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo, Admin operator) {
		Assert.notNull(member);

		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);

		if (modifyPoint != null && modifyPoint != 0 && member.getPoint() + modifyPoint >= 0) {
			member.setPoint(member.getPoint() + modifyPoint);
		}

		if (modifyBalance != null && modifyBalance.compareTo(new BigDecimal(0)) != 0 && member.getBalance().add(modifyBalance).compareTo(new BigDecimal(0)) >= 0) {
			member.setBalance(member.getBalance().add(modifyBalance));
			Deposit deposit = new Deposit();
			if (modifyBalance.compareTo(new BigDecimal(0)) > 0) {
				deposit.setType(operator != null ? Deposit.Type.adminRecharge : Deposit.Type.memberRecharge);
				deposit.setCredit(modifyBalance);
				deposit.setDebit(new BigDecimal(0));
			} else {
				deposit.setType(operator != null ? Deposit.Type.adminChargeback : Deposit.Type.memberPayment);
				deposit.setCredit(new BigDecimal(0));
				deposit.setDebit(modifyBalance);
			}
			deposit.setBalance(member.getBalance());
			deposit.setOperator(operator != null ? operator.getUsername() : null);
			deposit.setMemo(depositMemo);
			deposit.setMember(member);
			depositDao.persist(deposit);
		}
		memberDao.merge(member);
	}

	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		return memberDao.findByUsername(username);
	}

	@Override
	public Member findBySafeKeyValue(String safeKeyValue) {
		// TODO Auto-generated method stub
		return memberDao.findBySafeKeyValue(safeKeyValue);
	}

	@Override
	public Member findByMobile(String mobile) {
		// TODO Auto-generated method stub
		return memberDao.findByMobile(mobile);
	}
	
	
	@Transactional(readOnly = true)
	public List<Member> findListByEmail(String email) {
		return memberDao.findListByEmail(email);
	}

	@Transactional(readOnly = true)
	public List<Object[]> findPurchaseList(Date beginDate, Date endDate, Integer count) {
		return memberDao.findPurchaseList(beginDate, endDate, count);
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public Member getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return memberDao.find(principal.getId());
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	@Override
	public List<Member> getMemberByName(String name) {
		// TODO Auto-generated method stub
		return memberDao.getMemberByName(name);
	}

	@Override
	public Page<Member> getMembers(Pageable pageable, Mechanism mechanism) {
		// TODO Auto-generated method stub
		return memberDao.getMembers(pageable, mechanism);
	}

	@Override
	public Page<Member> getMemberLists(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return memberDao.getMemberLists(query_map);
	}

	@Override
	public Page<Member> getPatientLists(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return memberDao.getPatientLists(query_map);
	}

	@Override
	public List<Member> downloadPatientList(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return memberDao.downloadPatientList(query_map);
	}
	
	
	@Override
	public List<Member> getMembersByNameOrMobile(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return memberDao.getMembersByNameOrMobile(query_map);
	}

	@Override
	public Page<Member> getMembers(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return memberDao.getMembers(query_map);
	}

	@Override
	public Page<Member> getPatients(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return memberDao.getPatients(query_map);
	}

	@Override
	public List<Member> downloadPatientHealthType(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return memberDao.downloadPatientHealthType(query_map);
	}

	@Override
	public List<Member> downloadMemberList(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return memberDao.downloadMemberList(query_map);
	}

	@Transactional(readOnly = true)
	public boolean cardIdExists(String cardId) {
		
		return memberDao.cardIdExists(cardId);
	}

	@Override
	public BigDecimal sumBalance(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return memberDao.sumBalance(query_map);
	}

	
}