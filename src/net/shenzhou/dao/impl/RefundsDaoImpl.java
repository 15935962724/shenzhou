/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.RefundsDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Refunds;
import net.shenzhou.entity.Order.Evaluate;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.Order.ServeState;
import net.shenzhou.entity.Order.ShippingStatus;
import net.shenzhou.entity.Refunds.Status;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Repository;

/**
 * Dao - 退款单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("refundsDaoImpl")
public class RefundsDaoImpl extends BaseDaoImpl<Refunds, Long> implements RefundsDao {

	@Override
	public Page<Refunds> findPage(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Status status = (Status) query_map.get("status");
		Pageable pageable = (Pageable) query_map.get("pageable");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Refunds> criteriaQuery = criteriaBuilder.createQuery(Refunds.class);
		Root<Refunds> root = criteriaQuery.from(Refunds.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
//		if (mechanism!=null) {
//			List<Order> orders = new ArrayList<Order>();
//			for (Order order : mechanism.getOrders()) {
//				if (!order.getIsDeleted()) {
//					orders.add(order);
//				}
//			}
//			if (orders.size()>0) {
//				restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("order")).value(orders));
//			}else{
//				return new Page<Refunds>(new ArrayList<Refunds>(),0,pageable);
//			}
//		}
		
		if (mechanism!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		}else{
			return new Page<Refunds>(new ArrayList<Refunds>(),0,pageable);
		}
		
		if (status!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<Refunds> downloadList(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Status status = (Status) query_map.get("status");
		Pageable pageable = (Pageable) query_map.get("pageable");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Refunds> criteriaQuery = criteriaBuilder.createQuery(Refunds.class);
		Root<Refunds> root = criteriaQuery.from(Refunds.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		if (mechanism!=null) {
			List<Order> orders = new ArrayList<Order>();
			for (Order order : mechanism.getOrders()) {
				if (!order.getIsDeleted()) {
					orders.add(order);
				}
			}
			if (orders.size()>0) {
				restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("order")).value(orders));
			}else{
				return new ArrayList<Refunds>();
			}
		}
		if (status!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
	@Override
	public Map<String, Object> findMap(Map<String, Object> query_map) {
		Map<String, Object> data = new HashMap<String, Object>();
		
		Order order = (Order) query_map.get("order");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Refunds> criteriaQuery = criteriaBuilder.createQuery(Refunds.class);
		Root<Refunds> root = criteriaQuery.from(Refunds.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		if (order!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("order"), order));
		}
		criteriaQuery.where(restrictions);
		List<Refunds> refunds_list =  super.findList(criteriaQuery, null, null, null, null);
		List<Map<String,Object>> data_list =  new ArrayList<Map<String,Object>>();
		for (Refunds refunds : refunds_list) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("refundsStatus", refunds.getStatus());
			data_map.put("refundsCreateDate", refunds.getCreateDate());
			data_map.put("refundsAmount", refunds.getAmount());
			data_map.put("refundsMemo", refunds.getMemo());
			data_list.add(data_map);
		}
		
		Map<String, Object> refunds_map = new HashMap<String, Object>();
		Map<String, Object> order_map = new HashMap<String, Object>();
		order_map.put("projectName", order.getProject().getName());
		order_map.put("orderSn", order.getSn());
		//order_map.put("orderCountPrice", order.getWorkDayItem().getCountPrice());
		order_map.put("orderCountPrice", order.getPrice());
		refunds_map.put("data_list", data_list);
		refunds_map.put("order_map", order_map);
		data.put("status", "200");
		data.put("message", "数据加载成功");
		data.put("data",JsonUtils.toJson(refunds_map));
		return data;
	}



}
