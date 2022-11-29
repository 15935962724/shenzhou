package net.shenzhou.service;

import java.util.List;

import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductCoupon;



public interface ProductCouponService extends BaseService<ProductCoupon, Long>{

	
	/**
	 * 通过商品查询优惠券
	 * @param product
	 * @return
	 */
	List<ProductCoupon> findByProductId(Product product);
}
