/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.Filter;
import net.shenzhou.Order;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.ConsultationDao;
import net.shenzhou.entity.Consultation;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Product;
import net.shenzhou.service.ConsultationService;
import net.shenzhou.service.StaticService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 咨询
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("consultationServiceImpl")
public class ConsultationServiceImpl extends BaseServiceImpl<Consultation, Long> implements ConsultationService {

	@Resource(name = "consultationDaoImpl")
	private ConsultationDao consultationDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	@Resource(name = "consultationDaoImpl")
	public void setBaseDao(ConsultationDao consultationDao) {
		super.setBaseDao(consultationDao);
	}

	@Transactional(readOnly = true)
	public List<Consultation> findList(Member member, Product product, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		return consultationDao.findList(member, product, isShow, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable("consultation")
	public List<Consultation> findList(Member member, Product product, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders, String cacheRegion) {
		return consultationDao.findList(member, product, isShow, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<Consultation> findPage(Member member, Product product, Boolean isShow, Pageable pageable) {
		return consultationDao.findPage(member, product, isShow, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member member, Product product, Boolean isShow) {
		return consultationDao.count(member, product, isShow);
	}

	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void reply(Consultation consultation, Consultation replyConsultation) {
		if (consultation == null || replyConsultation == null) {
			return;
		}
		consultation.setIsShow(true);
		consultationDao.merge(consultation);

		replyConsultation.setIsShow(true);
		replyConsultation.setProduct(consultation.getProduct());
		replyConsultation.setForConsultation(consultation);
		consultationDao.persist(replyConsultation);

		Product product = consultation.getProduct();
		if (product != null) {
			consultationDao.flush();
			staticService.build(product);
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void save(Consultation consultation) {
		super.save(consultation);
		Product product = consultation.getProduct();
		if (product != null) {
			consultationDao.flush();
			staticService.build(product);
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public Consultation update(Consultation consultation) {
		Consultation pConsultation = super.update(consultation);
		Product product = pConsultation.getProduct();
		if (product != null) {
			consultationDao.flush();
			staticService.build(product);
		}
		return pConsultation;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public Consultation update(Consultation consultation, String... ignoreProperties) {
		return super.update(consultation, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Consultation consultation) {
		if (consultation != null) {
			super.delete(consultation);
			Product product = consultation.getProduct();
			if (product != null) {
				consultationDao.flush();
				staticService.build(product);
			}
		}
	}

}