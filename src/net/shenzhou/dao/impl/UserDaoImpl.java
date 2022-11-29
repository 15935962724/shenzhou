/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.UserDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.User;
import net.shenzhou.entity.Member.Gender;

import org.springframework.stereotype.Repository;

/**
 * Dao - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

	public boolean usernameExists(String username) {
		if (username == null) {
			return false;
		}
		String jpql = "select count(*) from User user where lower(user.username) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		return count > 0;
	}
	

	public boolean emailExists(String email) {
		if (email == null) {
			return false;
		}
		String jpql = "select count(*) from User user where lower(user.email) = lower(:email)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("email", email).getSingleResult();
		return count > 0;
	}

	public User findByUsername(String username) {
		if (username == null) {
			return null;
		}
		try {
			String jpql = "select users from User users where lower(users.username) = lower(:username)";
			return entityManager.createQuery(jpql, User.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	@Override
	public Page<User> getMechanismUsers(Map<String, Object> query_map) {
		Pageable pageable = (Pageable) query_map.get("pageable");
		Mechanism mechanism =  (Mechanism) query_map.get("mechanism");
		Object nameOrphone = query_map.get("nameOrusername");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		if (mechanism!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		}
		
//		if (nameOrphone!=null) {
//			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%"+nameOrphone+"%"),criteriaBuilder.like(root.<String>get("username"), "%"+nameOrphone+"%")));
//		}
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	
	


}