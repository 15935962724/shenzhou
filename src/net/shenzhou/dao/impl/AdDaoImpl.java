/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONObject;
import net.shenzhou.Filter;
import net.shenzhou.dao.AdDao;
import net.shenzhou.dao.AdPositionDao;
import net.shenzhou.dao.DoctorDao;
import net.shenzhou.dao.MechanismDao;
import net.shenzhou.dao.ServerProjectCategoryDao;
import net.shenzhou.entity.Ad;
import net.shenzhou.entity.Ad.Type;
import net.shenzhou.entity.AdPosition;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.Project.Audit;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Repository;

/**
 * Dao - 广告
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("adDaoImpl")
public class AdDaoImpl extends BaseDaoImpl<Ad, Long> implements AdDao {

	
	@Resource(name = "adPositionDaoImpl")
	private AdPositionDao adPositionDao;
	
	@Override
	public Map<String, Object> findList(String file) {
		// TODO Auto-generated method stub
		 Map<String, Object> map = new HashMap<String, Object>();
		 JSONObject json = JSONObject.fromObject(file);
//	     AdPosition adPosition = adPositionDao.find(json.getLong("1"));
	     AdPosition adPosition = adPositionDao.find(1l);
	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Ad> criteriaQuery = criteriaBuilder.createQuery(Ad.class);
		Root<Ad> root = criteriaQuery.from(Ad.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), Type.image));
		if (adPosition != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("adPosition"), adPosition));
		}
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
		criteriaQuery.where(restrictions);
		List<Ad> ads = new ArrayList<Ad>();
		ads = super.findList(criteriaQuery, null, null, null, null);
		Map<String, Object> map_data = new HashMap<String, Object>();
		map_data.put("ads", ads); 
		map.put("status", "200"); 
		map.put("data", JsonUtils.toJson(map_data));
		map.put("message", "数据加载成功");
		return map;
	}

}