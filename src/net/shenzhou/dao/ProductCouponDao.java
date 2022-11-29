package net.shenzhou.dao;

import java.util.List;

import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductCoupon;

public interface ProductCouponDao extends BaseDao<ProductCoupon, Long>{

	
	/**
	 * 通过商品查询优惠券
	 * @param product
	 * @return
	 */
	List<ProductCoupon> findByProductId(Product product);
}
