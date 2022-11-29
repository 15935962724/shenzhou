/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import net.shenzhou.dao.VerificationDao;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Verification;

import org.springframework.stereotype.Repository;

/**
 * 验证码
 * 2017-05-25 13:58:31
 * @author wsr
 *
 */
@Repository("verificationDaoImpl")
public class VerificationDaoImpl extends BaseDaoImpl<Verification, Long> implements VerificationDao {

	public Verification findByMobile(String mobile) {
		if (mobile == null) {
			return null;
		}
		try {
			String jpql = "select verification from Verification verification where lower(verification.mobile) = lower(:mobile)";
			return entityManager.createQuery(jpql, Verification.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile", mobile).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean mobileExists(String mobile) {
		if (mobile == null) {
			return false;
		}
		String jpql = "select count(*) from Verification verification where lower(verification.mobile) = lower(:mobile)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile", mobile).getSingleResult();
		return count > 0;
	}

}