package net.shenzhou.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shenzhou.dao.GoodsDao;
import net.shenzhou.dao.ProductCouponDao;
import net.shenzhou.dao.ProductDao;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductCoupon;
import net.shenzhou.service.ProductCouponService;

@Service("productCouponServiceImpl")
public class ProductCouponServiceImpl extends BaseServiceImpl<ProductCoupon, Long> implements ProductCouponService{

	
	
	@Resource(name = "productCouponDaoImpl")
	private ProductCouponDao ProductCouponDao;
	
	@Resource(name = "productCouponDaoImpl")
	public void setBaseDao(ProductCouponDao productCouponDao) {
		super.setBaseDao(productCouponDao);
	}

	@Override
	public List<ProductCoupon> findByProductId(Product product) {
		return ProductCouponDao.findByProductId(product);
	}
}
