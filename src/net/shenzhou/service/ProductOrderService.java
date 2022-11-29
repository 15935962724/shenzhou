package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.Filter;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.ProductOrder;
import net.shenzhou.entity.ProductOrder.OrderStatus;
import net.shenzhou.entity.ProductOrder.PaymentStatus;
import net.shenzhou.entity.ProductOrder.ShippingStatus;


public interface ProductOrderService  extends BaseService<ProductOrder, Long>{

	/**
	 * 创建订单
	 * @param map
	 * @return
	 */
	ProductOrder create(Map<String,Object> map);
	
	/**
	 * 根据订单号获得订单
	 * @param sn
	 * @return
	 */
	ProductOrder findBySn(String sn);
	
	
	/**
	 * 订单列表
	 * @param map
	 * @return
	 */
	Map<String, Object> findList(Map<String,Object> map);
	
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
