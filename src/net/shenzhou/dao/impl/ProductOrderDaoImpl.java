package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.shenzhou.Config;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.ProductOrderDao;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.ProductOrder;
import net.shenzhou.entity.ProductOrder.OrderStatus;
import net.shenzhou.entity.ProductOrder.PaymentStatus;
import net.shenzhou.entity.ProductOrder.PaymentType;
import net.shenzhou.entity.ProductOrder.ShippingStatus;
import net.shenzhou.util.JsonUtils;


@Repository("productOrderDaoImpl")
public class ProductOrderDaoImpl extends BaseDaoImpl<ProductOrder, Long> implements ProductOrderDao{

	@Override
	public ProductOrder findBySn(String sn) {
			if (sn == null) {
				return null;
			}
			String jpql = "select orders from ProductOrder orders where lower(orders.sn) = lower(:sn)";
			try {
				return entityManager.createQuery(jpql, ProductOrder.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
	}

	@Override
	public Map<String, Object> findList(Map<String, Object> query_map) {
		Map<String,Object> map = new HashMap<String, Object>();
		Member member = (Member) query_map.get("member");
		Integer pageNumber = Integer.valueOf(query_map.get("pageNumber").toString());//页码
		Pageable pageable = new Pageable();
		pageable.setPageNumber(pageNumber);
		pageable.setPageSize(Config.pageSize);
		
		Object paymentStatus = query_map.get("paymentStatus");
		Object orderStatus = query_map.get("orderStatus");
		Object shippingStatus = query_map.get("shippingStatus");
		
		

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductOrder> criteriaQuery = criteriaBuilder.createQuery(ProductOrder.class);
		Root<ProductOrder> root = criteriaQuery.from(ProductOrder.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));		
				if(member != null && !member.equals("")){
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));		
				}
		
				//待付款状态unpaid
				if(paymentStatus != null && !paymentStatus.equals("")){
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("paymentStatus"), PaymentStatus.unpaid));
				}
				
				
				if(shippingStatus != null && !shippingStatus.equals("")){
					//待发货unshipped
					if(shippingStatus.equals("unshipped")){
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shippingStatus"), ShippingStatus.unshipped));
					}
					//待收货shipped
					if(shippingStatus.equals("shipped")){
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shippingStatus"), ShippingStatus.shipped));
					}
				}
				
				
				if(orderStatus != null && !orderStatus.equals("")){
					//已完成completed
					if(orderStatus.equals("completed")){
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.completed));
					}
					//取消cancelled
					if(orderStatus.equals("cancelled")){
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.cancelled));
					}
				}
				
		
		
		criteriaQuery.where(restrictions);
		Page<ProductOrder> page =  super.findPage(criteriaQuery, pageable);
		List<ProductOrder> list = page.getContent();
		Integer count = page.getTotalPages();
		String status = "200";
		List<Map<String,Object>> order_list = new ArrayList<Map<String,Object>>();
		if (pageNumber>(count)) {
			status = "500";
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("order_list", order_list);
			map.put("status", status);
			map.put("message",list.size()==0?"暂无订单数据":"没有更多数据");
			map.put("data", JsonUtils.toJson(data));
			return map;
		}
		

		for (ProductOrder order : list) {
			Map<String,Object> order_map = new HashMap<String, Object>();
			order_map.put("orderId", order.getId());//订单id
			order_map.put("orderSn", order.getSn());//订单编号
			
			order_map.put("orderNumber", order.getNumber());//商品数量
			
			order_map.put("productid", order.getProduct().getId());//商品编号
			order_map.put("productName", order.getProduct().getName());//商品名称
			order_map.put("productTime", order.getProduct().getCreateDate());//商品发布时间
			order_map.put("productPrice", order.getProduct().getPrice());//商品价格
			if(order.getPaymentType() == PaymentType.healthGold){
				order_map.put("spendPaid", order.getSpendPoint());//健康金
				order_map.put("pointPrice", 0);//健康金加钱
				order_map.put("price", 0);//钱
			}
			
			if(order.getPaymentType() == PaymentType.healthyMoney){
				order_map.put("spendPaid", 0);//健康金
				order_map.put("pointPrice", order.getSpendPoint()+"+"+order.getSpendPaid());//健康金加钱
				order_map.put("price", 0);//钱
			}
			if(order.getPaymentType() == PaymentType.money){
				order_map.put("spendPaid", 0);//健康金
				order_map.put("pointPrice", 0);//健康金加钱
				order_map.put("price", order.getSpendPaid());//钱
			}
			
			order_map.put("orderStatust", order.getOrderStatus());//订单状态
			order_map.put("orderPaymentStatus", order.getPaymentStatus());//支付状态
			order_map.put("orderShippingstatus", order.getShippingStatus());//配送状态
			order_map.put("specifications", "");//规格
			order_map.put("safeguards", "");//说明
			
			order_list.add(order_map);
		
		}
		
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("order_list", order_list);
		map.put("status", status);
		map.put("message", "数据加载成功");
		map.put("data", JsonUtils.toJson(data));
		
		
		return map;
	}

	@Override
	public Page<ProductOrder> findPage(OrderStatus orderStatus, PaymentStatus paymentStatus,
			ShippingStatus shippingStatus, Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductOrder> criteriaQuery = criteriaBuilder.createQuery(ProductOrder.class);
		Root<ProductOrder> root = criteriaQuery.from(ProductOrder.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("paymentStatus"), PaymentStatus.paid));
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (paymentStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus));
		}
		if (shippingStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shippingStatus"), shippingStatus));
		}
		
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThan(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}



}
