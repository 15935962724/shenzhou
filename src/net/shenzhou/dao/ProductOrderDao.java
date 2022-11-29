package net.shenzhou.dao;

import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.ProductOrder;
import net.shenzhou.entity.ProductOrder.OrderStatus;
import net.shenzhou.entity.ProductOrder.PaymentStatus;
import net.shenzhou.entity.ProductOrder.ShippingStatus;

public interface ProductOrderDao extends BaseDao<ProductOrder, Long>{

	
	ProductOrder findBySn(String sn);
	
	Map<String, Object> findList(Map<String,Object> query_map);
	
	
	/**
	 * 查找订单分页
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param paymentStatus
	 *            支付状态
	 * @param shippingStatus
	 *            配送状态
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<ProductOrder> findPage(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired, Pageable pageable);
}
