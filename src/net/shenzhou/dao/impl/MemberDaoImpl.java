/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.MemberDao;
import net.shenzhou.entity.BeforehandLogin.UserType;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.PatientMechanism.HealthType;
import net.shenzhou.entity.Product;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Dao - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("memberDaoImpl")
public class MemberDaoImpl extends BaseDaoImpl<Member, Long> implements MemberDao {
	public boolean usernameExists(String username) {
		if (username == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.username) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		return count > 0;
	}
	
	public boolean mobileExists(String mobile){
		if (mobile == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.mobile) = lower(:mobile)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile", mobile).getSingleResult();
		return count > 0;
		
	}

	public boolean emailExists(String email) {
		if (email == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.email) = lower(:email)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("email", email).getSingleResult();
		return count > 0;
	}

	public Member findByUsername(String username) {
		if (username == null) {
			return null;
		}
		try {
			String jpql = "select members from Member members where lower(members.username) = lower(:username)";
			return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	public Member findBySafeKeyValue(String safeKeyValue){
		if (safeKeyValue == null) {
			return null;
		}
		try {
			String jpql = "select members from Member members where lower(members.safeKey.value) = lower(:safe_key_value)";
			return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT).setParameter("safe_key_value", safeKeyValue).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	
	@Override
	public Member findByMobile(String mobile) {
		if (mobile == null) {
			return null;
		}
		try {
			String jpql = "select members from Member members where lower(members.username) = lower(:mobile)";
			return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile", mobile).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	

	public List<Member> findListByEmail(String email) {
		if (email == null) {
			return Collections.<Member> emptyList();
		}
		String jpql = "select members from Member members where lower(members.email) = lower(:email)";
		return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT).setParameter("email", email).getResultList();
	}

	public List<Object[]> findPurchaseList(Date beginDate, Date endDate, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Member> member = criteriaQuery.from(Member.class);
		Join<Product, Order> orders = member.join("orders");
		criteriaQuery.multiselect(member.get("id"), member.get("username"), member.get("email"), member.get("point"), member.get("amount"), member.get("balance"), criteriaBuilder.sum(orders.<BigDecimal> get("amountPaid")));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(orders.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(orders.<Date> get("createDate"), endDate));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(orders.get("orderStatus"), OrderStatus.completed), criteriaBuilder.equal(orders.get("paymentStatus"), PaymentStatus.paid));
		criteriaQuery.where(restrictions);
		criteriaQuery.groupBy(member.get("id"), member.get("username"), member.get("email"), member.get("point"), member.get("amount"), member.get("balance"));
		criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(orders.<BigDecimal> get("amountPaid"))));
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (count != null && count >= 0) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<Member> getMemberByName(String name) {
		if (name == null) {
			return Collections.<Member> emptyList();
		}
		String jpql = "select members from Member members where lower(members.name) = lower(:name)";
		return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", name).getResultList();
	}

	@Override
	public Page<Member> getMembers(Pageable pageable, Mechanism mechanism) {
		if (mechanism == null) {
			return new Page<Member>(Collections.<Member> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Join<Member, Mechanism> memberMechanisms = root.join("memberMechanisms");
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(memberMechanisms.get("id"), mechanism.getId()));	
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<Member> getMemberByLikeName(String name) {
		if (name == null) {
			return Collections.<Member> emptyList();
		}
		String jpql = "select members from Member members where lower(members.name) like lower(:name)";
		List<Member> members = new ArrayList<Member>();
		try {
			members = entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", "%"+name+"%").getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return members;
	}

	@Override
	public Page<Member> getMemberLists(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Object type = query_map.get("type");
		if (mechanism == null) {
			return new Page<Member>(Collections.<Member> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		Join<Member, Mechanism> memberMechanisms = root.join("memberMechanisms");
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(memberMechanisms.get("id"), mechanism.getId()));	
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));	
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.isNull(root.get("parent")));//此处注释 是为了，也能搜索患者
		if (type!=null) {
			if (type.equals("member")) {
				if (nameOrmobile!=null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%"+nameOrmobile+"%"),criteriaBuilder.like(root.<String>get("mobile"), "%"+nameOrmobile+"%")));
				}
			}
			if(type.equals("patient")){
				if (nameOrmobile!=null) {
					List ids =new ArrayList();
					try {
						Session session =sessionFactory.openSession();
						String sql="SELECT parent FROM xx_member WHERE is_deleted = FALSE AND parent IS NOT NULL AND NAME LIKE '%"+nameOrmobile+"%' OR phone LIKE '%"+nameOrmobile+"%'";
						ids = session.createSQLQuery(sql).list();
						session.close();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}
					
					List<Long> id = new ArrayList<Long>();
					for (int i = 0; i < ids.size(); i++) {
						id.add(Long.valueOf(String.valueOf(ids.get(i))));
					}
					
					System.out.println("患者父级id:"+ids);	
					if (id.size()>0) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("id")).value(id));
					}else{
						return new Page<Member>(Collections.<Member> emptyList(), 0, pageable);
					}
					
				}
			}
		}
		try {
			criteriaQuery.where(restrictions);
			return super.findPage(criteriaQuery, pageable);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return super.findPage(criteriaQuery, pageable);
		
	}

	@Override
	public Page<Member> getPatientLists(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
				Mechanism mechanism = (Mechanism) query_map.get("mechanism");
				Pageable pageable = (Pageable) query_map.get("pageable");
				Object nameOrmobile = query_map.get("nameOrmobile");
				Object healthType = query_map.get("healthType");
				HealthType[] healthTypes =  (HealthType[]) query_map.get("healthTypes");
				Object isDeleted = query_map.get("isDeleted");
				
				
				if (mechanism == null) {
					return new Page<Member>(Collections.<Member> emptyList(), 0, pageable);
				}
				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
				Root<Member> root = criteriaQuery.from(Member.class);
				criteriaQuery.select(root);
				Predicate restrictions = criteriaBuilder.conjunction();
				
				Join<Member, Mechanism> memberMechanisms = root.join("patientMemberMechanisms");//此处两行代码取出该机构下的患者(暂时注释)
				restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(memberMechanisms.get("id"), mechanism.getId()));
				if (isDeleted!=null) {
					restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"),Boolean.valueOf(isDeleted.toString())));	
				}
				
				restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.isNotNull(root.get("parent")));
				
				if (nameOrmobile!=null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%"+nameOrmobile+"%"),criteriaBuilder.like(root.<String>get("mobile"), "%"+nameOrmobile+"%")));
				}
				if (healthType!=null) {
					restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("healthType"), HealthType.valueOf(String.valueOf(healthType))));	
				}
//				if (healthTypes!=null) {//这个条件主要针对患者状态 统计 那里使用
//					for (HealthType healthType2 : healthTypes) {
//						restrictions = criteriaBuilder.or(restrictions,criteriaBuilder.equal(root.get("healthType"), healthType2));	
//					}
//				}
				
				if (healthTypes!=null) {//这个条件主要针对患者状态 统计 那里使用
					
						restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("healthType")).value( Arrays.asList(healthTypes)));	
					
				}
				
				criteriaQuery.where(restrictions);
				return super.findPage(criteriaQuery, pageable);
	}

	
	
	@Override
	public List<Member> downloadPatientList(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Object healthType = query_map.get("healthType");
		HealthType[] healthTypes =  (HealthType[]) query_map.get("healthTypes");
		
		if (mechanism == null) {
			return new ArrayList<Member>();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
//		Join<Member, Mechanism> memberMechanisms = root.join("patientMemberMechanisms");//此处两行代码取出该机构下的患者(暂时注释)
//		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(memberMechanisms.get("id"), mechanism.getId()));	
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));	
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.isNotNull(root.get("parent")));
		
		if (nameOrmobile!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%"+nameOrmobile+"%"),criteriaBuilder.like(root.<String>get("mobile"), "%"+nameOrmobile+"%")));
		}
		if (healthType!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("healthType"), HealthType.valueOf(String.valueOf(healthType))));	
		}
//		if (healthTypes!=null) {//这个条件主要针对患者状态 统计 那里使用
//			for (HealthType healthType2 : healthTypes) {
//				restrictions = criteriaBuilder.or(restrictions,criteriaBuilder.equal(root.get("healthType"), healthType2));	
//			}
//		}
		
		if (healthTypes!=null) {//这个条件主要针对患者状态 统计 那里使用
				restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("healthType")).value( Arrays.asList(healthTypes)));	
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	
	
	@Override
	public List<Member> getMembersByNameOrMobile(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Object nameOrmobile = query_map.get("nameOrmobile");
		if (mechanism == null) {
			return new ArrayList<Member>();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
//		Join<Member, Mechanism> memberMechanisms = root.join("memberMechanisms");
//		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(memberMechanisms.get("id"), mechanism.getId()));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.isNull(root.get("parent")));	
		if (nameOrmobile!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%"+nameOrmobile+"%"),criteriaBuilder.like(root.<String>get("username"), "%"+nameOrmobile+"%"),criteriaBuilder.like(root.<String>get("mobile"), "%"+nameOrmobile+"%")));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
		
	}

	public List<Member> getMembersByNameOrMobile(String nameOrmobile) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (nameOrmobile!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%"+nameOrmobile+"%"),criteriaBuilder.like(root.<String>get("mobile"), "%"+nameOrmobile+"%")));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
		
	}

	@Override
	public Page<Member> getMembers(Map<String, Object> query_map) {
		Pageable pageable = (Pageable) query_map.get("pageable");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.isNull(root.get("parent")));
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<Member> getPatients(Map<String, Object> query_map) {
		Pageable pageable = (Pageable) query_map.get("pageable");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.isNotNull(root.get("parent")));
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<Member> downloadPatientHealthType(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Object healthType = query_map.get("healthType");
		HealthType[] healthTypes =  (HealthType[]) query_map.get("healthTypes");
		
		if (mechanism == null) {
			return new ArrayList<Member>();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
//		Join<Member, Mechanism> memberMechanisms = root.join("patientMemberMechanisms");//此处两行代码取出该机构下的患者(暂时注释)
//		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(memberMechanisms.get("id"), mechanism.getId()));	
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));	
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.isNotNull(root.get("parent")));
		
		if (nameOrmobile!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%"+nameOrmobile+"%"),criteriaBuilder.like(root.<String>get("mobile"), "%"+nameOrmobile+"%")));
		}
//		if (healthType!=null) {
//			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("healthType"), HealthType.valueOf(String.valueOf(healthType))));	
//		}
//		if (healthTypes!=null) {//这个条件主要针对患者状态 统计 那里使用
//			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("healthType")).value( Arrays.asList(healthTypes)));	
//		}
		
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<Member> downloadMemberList(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Object type = query_map.get("type");
		if (mechanism == null) {
			return new ArrayList<Member>();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		Join<Member, Mechanism> memberMechanisms = root.join("memberMechanisms");
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(memberMechanisms.get("id"), mechanism.getId()));	
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));	
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.isNull(root.get("parent")));//此处注释 是为了，也能搜索患者
		if (type!=null) {
			if (type.equals("member")) {
				if (nameOrmobile!=null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%"+nameOrmobile+"%"),criteriaBuilder.like(root.<String>get("mobile"), "%"+nameOrmobile+"%")));
				}
			}
			if(type.equals("patient")){
				if (nameOrmobile!=null) {
					List ids =new ArrayList();
					try {
						Session session =sessionFactory.openSession();
						String sql="SELECT parent FROM xx_member WHERE is_deleted = FALSE AND parent IS NOT NULL AND NAME LIKE '%"+nameOrmobile+"%' OR phone LIKE '%"+nameOrmobile+"%'";
						ids = session.createSQLQuery(sql).list();
						session.close();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}
					
					List<Long> id = new ArrayList<Long>();
					for (int i = 0; i < ids.size(); i++) {
						id.add(Long.valueOf(String.valueOf(ids.get(i))));
					}
					
					System.out.println("患者父级id:"+ids);	
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("id")).value(id));
				}
			}
		}
		
			criteriaQuery.where(restrictions);
			return super.findList(criteriaQuery, null, null, null, null);
		
	}

	@Override
	public boolean cardIdExists(String cardId) {
		if (cardId == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.cardId) = lower(:cardId)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("cardId", cardId).getSingleResult();
		return count > 0;
	}

	/**
	 * 该机构下所有的用户的总余额
	 */
	@Override
	public BigDecimal sumBalance(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal> get("balance")));
		Predicate restrictions = criteriaBuilder.conjunction();
		
		Join<Member, Mechanism> memberMechanisms = root.join("memberMechanisms");
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(memberMechanisms.get("id"), mechanism.getId()));	
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	}

	}
