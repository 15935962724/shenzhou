package net.shenzhou.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.shenzhou.dao.ProductCouponDao;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductCoupon;


@Repository("productCouponDaoImpl")
public class ProductCouponDaoImpl extends BaseDaoImpl<ProductCoupon, Long> implements ProductCouponDao{

	@Override
	public List<ProductCoupon> findByProductId(Product product) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductCoupon> criteriaQuery = criteriaBuilder.createQuery(ProductCoupon.class);
		Root<ProductCoupon> root = criteriaQuery.from(ProductCoupon.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("product"),product));
		return super.findList(criteriaQuery, null, null, null, null);
	}

}
