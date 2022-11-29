/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.MechanismDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.Project.Audit;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MechanismUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author wsr
 *
 */
@Repository("mechanismDaoImpl")
public class MechanismDaoImpl extends BaseDaoImpl<Mechanism, Long> implements MechanismDao {

	@Override
	public List<Mechanism> findList(Map<String, Object> map) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mechanism> criteriaQuery = criteriaBuilder.createQuery(Mechanism.class);
		Root<Mechanism> root = criteriaQuery.from(Mechanism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		String name = map.get("name").toString();
		if (name != null) {
			restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("name"), "%"+name+"%"));
//			restrictions = criteriaBuilder.like(root.<String> get("name"), "%" + name + "%");
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
		
	}

	@Override
	public Map<String ,Object> findList(String file) {
		Map<String ,Object> map = new HashMap<String, Object>();
		List<Mechanism> mechanisms = new ArrayList<Mechanism>();
		
		JSONObject json = JSONObject.fromObject(file);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mechanism> criteriaQuery = criteriaBuilder.createQuery(Mechanism.class);
		Root<Mechanism> root = criteriaQuery.from(Mechanism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();

		/**评分*/
		String scoreSort = json.get("scoreSort").toString();
		
		/**诊次*/
		String second = json.get("second").toString();
		
		/** 经度 */
		 double longitude = Double.valueOf((json.get("longitude").toString().equals("")?Config.longitude:json.get("longitude").toString()));
		
		/** 纬度 */
		 double latitude =  Double.valueOf(json.get("latitude").toString().equals("")?Config.latitude:json.get("latitude").toString());
		
		 /** 页码 */
		 int pageNumber = json.getInt("pageNumber");
		 
		 /** 每页显示条数 */
		 int pagesize = Config.pageSize;
		 
		if (scoreSort.equals("desc")) {
	        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreSort")));  
		}else if(scoreSort.equals("asc")){
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("scoreSort")));
			
		}
		if (second.equals("desc")) {
	        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("second")));  
		}else if(second.equals("asc")){
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("second")));
			
		}
		
		criteriaQuery.where(restrictions);
		mechanisms =  super.findList(criteriaQuery, null, null, null, null);
		
		if(mechanisms.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无机构数据");
			map.put("data", "{}");
			return map;
		}
		
//		if(!StringUtils.isEmpty(json.getString("distance"))){
//			Boolean key = Boolean.parseBoolean(json.getString("distance"));  
			if(longitude != 0 && latitude!=0){
				Map<String ,Object> mechanism_map = new HashMap<String, Object>();
				mechanism_map.put("longitude", longitude);
				mechanism_map.put("latitude", latitude);
				mechanism_map.put("mechanisms", mechanisms);
				//mechanism_map.put("isDesc", key);//false 升序  true 降序
				mechanism_map.put("isDesc", false);//false 升序  true 降序
				mechanisms = MechanismUtil.getOrderByDistance(mechanism_map);
//			}
		}
		
		String status = "200";
		String message = "第"+pageNumber+"数据加载";
		Map<String ,Object> data_map = new HashMap<String, Object>();
		
		//总页数
		Integer pagecount = (mechanisms.size()+pagesize-1)/pagesize;
		
		if (pageNumber>pagecount) {
			message = "无更多数据";
			status = "500";
		}
		
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		
		if(pageNumber>=pagecount){
			data_map.put("mechanisms",mechanisms.subList((pagenumber-1)*pagesize, mechanisms.size()));
		}else{
			data_map.put("mechanisms", mechanisms.subList((pagenumber-1)*pagesize, pagesize*pagenumber));
		}
		map.put("status", status);
		map.put("message", message);
		map.put("data", JsonUtils.toJson(data_map));
		
		return map;
		
		
		
		
	}

	@Override
	public Double findObject(Mechanism mechanism) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		criteriaQuery.multiselect(criteriaBuilder.avg(root.<Double> get("scoreSort")).alias("scoreSort"));

		Predicate restrictions = criteriaBuilder.conjunction();
		if (mechanism != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Date> get("mechanism"), mechanism));
		}
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	
	}

	@Override
	public List<Mechanism> searchByName(String mechanismName) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mechanism> criteriaQuery = criteriaBuilder.createQuery(Mechanism.class);
		Root<Mechanism> root = criteriaQuery.from(Mechanism.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.like(root.<String>get("name"),"%" + mechanismName + "%"));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<Mechanism> findMechanismList(Integer page) {
		Pageable pageable = new Pageable();
		pageable.setPageNumber(page);
		pageable.setPageSize(Config.pageSize);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mechanism> criteriaQuery = criteriaBuilder.createQuery(Mechanism.class);
		Root<Mechanism> root = criteriaQuery.from(Mechanism.class);
		criteriaQuery.select(root);
		return super.findPage(criteriaQuery, pageable).getContent();
	}

	@Override
	public Map<String, Object> findMechanismList(String file) {
		Map<String ,Object> map = new HashMap<String, Object>();
		List<Mechanism> mechanisms = new ArrayList<Mechanism>();
		
		JSONObject json = JSONObject.fromObject(file);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mechanism> criteriaQuery = criteriaBuilder.createQuery(Mechanism.class);
		Root<Mechanism> root = criteriaQuery.from(Mechanism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();

		/**评分*/
		String scoreSort = json.get("scoreSort").toString();
		
		/**诊次*/
		String second = json.get("second").toString();
		
		/** 经度 */
		 double longitude = Double.valueOf((json.get("longitude").toString().equals("")?Config.longitude:json.get("longitude").toString()));
		
		/** 纬度 */
		 double latitude =  Double.valueOf(json.get("latitude").toString().equals("")?Config.latitude:json.get("latitude").toString());
		
		 /** 页码 */
		 Integer pageNumber = json.getInt("pageNumber");//页码
	     Integer pageSize = Config.pageSize;
		 
		 /** 每页显示条数 */
		 int pagesize = Config.pageSize;
		 
		if (scoreSort.equals("desc")) {
	        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreSort")));  
		}else if(scoreSort.equals("asc")){
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("scoreSort")));
			
		}
		if (second.equals("desc")) {
	        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("second")));  
		}else if(second.equals("asc")){
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("second")));
			
		}
		
		criteriaQuery.where(restrictions);
		mechanisms =  super.findList(criteriaQuery, null, null, null, null);
		
		if(mechanisms.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无机构数据");
			map.put("data", "{}");
			return map;
		}
		
		if(!StringUtils.isEmpty(json.getString("distance"))){
			Boolean key = Boolean.parseBoolean(json.getString("distance"));  
			if(longitude != 0 && latitude!=0){
				Map<String ,Object> mechanism_map = new HashMap<String, Object>();
				mechanism_map.put("longitude", longitude);
				mechanism_map.put("latitude", latitude);
				mechanism_map.put("mechanisms", mechanisms);
				mechanism_map.put("isDesc", key);//false 升序  true 降序
				mechanisms = MechanismUtil.getOrderByDistance(mechanism_map);
			}
		}
		
		String status = "200";
		String message = "第"+pageNumber+"数据加载";
		Map<String ,Object> data_map = new HashMap<String, Object>();
		
		
		
		//页数
		/*Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		
		if(pageNumber>=pagecount){
			data_map.put("mechanisms",mechanisms.subList((pagenumber-1)*pagesize, mechanisms.size()));
			//map.put("mechanismsList", mechanisms);
		}else{
			data_map.put("mechanisms", mechanisms.subList((pagenumber-1)*pagesize, pagesize*pagenumber));
			//map.put("mechanismsList", mechanisms);
		}
		map.put("mechanismsList", mechanisms);
		map.put("status", status);
		map.put("message", message);
		map.put("data", JsonUtils.toJson(data_map));*/
		
		
		/*String status = "200";
		String message = "第"+pageNumber+"页数据加载成功";
		
		if(projects.size()==0){
			map.put("status", "500");
			map.put("message", "暂无项目数据");
			map.put("data", "{}");
			return map;
		}*/
		
		
		//总页数
		Integer pagecount = (mechanisms.size()+pageSize-1)/pageSize;
				
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		
		if (pageNumber>pagecount) {
			message = "无更多数据";
			status = "500";
		}
		
		if(pageNumber>=pagecount){
			data_map.put("mechanisms",mechanisms.subList((pagenumber-1)*pageSize, mechanisms.size()));
		}else{
			data_map.put("mechanisms", mechanisms.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
		}
		
		map.put("mechanismsList", mechanisms);
		map.put("status", status);
		map.put("message", message);
		map.put("data", JsonUtils.toJson(data_map));
		return map;
	}
	
	
	
	@Override
	public Map<String, Object> screenMechanismLists(String file) {
		Map<String ,Object> map = new HashMap<String, Object>();
		List<Mechanism> mechanisms = new ArrayList<Mechanism>();
		Map<String ,Object> data_map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(file);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mechanism> criteriaQuery = criteriaBuilder.createQuery(Mechanism.class);
		Root<Mechanism> root = criteriaQuery.from(Mechanism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		 /** 页码 */
		 int pageNumber = json.getInt("pageNumber");
		 
		 /** 每页显示条数 */
		 int pagesize = 5;
		criteriaQuery.where(restrictions);
		mechanisms =  super.findList(criteriaQuery, null, null, null, null);
		
		if(mechanisms.size()<=0	){
			map.put("status", "500");
			map.put("message", "暂无机构数据");
			map.put("data", "{}");
			return map;
		}
		
		/*if(!StringUtils.isEmpty(json.getString("distance"))){
			Boolean key = Boolean.parseBoolean(json.getString("distance"));  
			if(longitude != 0 && latitude!=0){
				Map<String ,Object> mechanism_map = new HashMap<String, Object>();
				mechanism_map.put("longitude", longitude);
				mechanism_map.put("latitude", latitude);
				mechanism_map.put("mechanisms", mechanisms);
				mechanism_map.put("isDesc", key);//false 升序  true 降序
				mechanisms = MechanismUtil.getOrderByDistance(mechanism_map);
			}
		}*/
		
	/*	String status = "200";
		String message = "第"+pageNumber+"数据加载";
		
		
		//总页数
		Integer pagecount = (mechanisms.size()+pagesize-1)/pagesize;
		
		if (pageNumber>pagecount) {
			message = "无更多数据";
			status = "500";
		}
		
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		
		if(pageNumber>=pagecount){
			data_map.put("mechanisms",mechanisms.subList((pagenumber-1)*pagesize, mechanisms.size()));
			//map.put("mechanismsList", mechanisms);
		}else{
			data_map.put("mechanisms", mechanisms.subList((pagenumber-1)*pagesize, pagesize*pagenumber));
			//
		}*/
		
		data_map.put("mechanismsList", mechanisms);
		
		return data_map;
		
		
		
		
	}
	
	

	@Override
	public List<Map<String, Object>> webMechanismList(String scoreSort,
			String second, String longitude, String latitude, String distance,
			Integer pageNumber,String flag) {
		
		//flag="1：综合；2：评分；3、诊次；4、距离；5、筛选"
		
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		Map<String ,Object> map = new HashMap<String, Object>();
		List<Mechanism> mechanisms = new ArrayList<Mechanism>();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mechanism> criteriaQuery = criteriaBuilder.createQuery(Mechanism.class);
		Root<Mechanism> root = criteriaQuery.from(Mechanism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();

		/** 经度 */
		 longitude = longitude.equals("")?Config.longitude:longitude;
		
		/** 纬度 */
		 latitude =  latitude.equals("")?Config.latitude:latitude;
		
		 /** 每页显示条数 */
		 int pagesize = Config.pageSize;
		 
		if (flag.equals("2")) {
	        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreSort")));  
		}
		if (flag.equals("3")) {
	        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("second")));  
		}
		
		criteriaQuery.where(restrictions);
		mechanisms =  super.findList(criteriaQuery, null, null, null, null);
		
		if(mechanisms.size()<=0){
			return data_list;
		}
		
		if(flag.equals("4")){
			if(!StringUtils.isEmpty(longitude)&&!StringUtils.isEmpty(latitude)){
				Map<String ,Object> mechanism_map = new HashMap<String, Object>();
				mechanism_map.put("longitude", longitude);
				mechanism_map.put("latitude", latitude);
				mechanism_map.put("mechanisms", mechanisms);
				mechanism_map.put("isDesc", true);
				mechanisms = MechanismUtil.getOrderByDistance(mechanism_map);
			}
		}
		
		//总页数
		Integer pagecount = (mechanisms.size()+pagesize-1)/pagesize;
		
		if (pageNumber>pagecount) {
			return data_list;
		}
		
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		List<Mechanism> mechanismList = new ArrayList<Mechanism>();
		if(pageNumber>=pagecount){
			mechanismList=mechanisms.subList((pagenumber-1)*pagesize, mechanisms.size());
		}else{
			mechanismList=mechanisms.subList((pagenumber-1)*pagesize, pagesize*pagenumber);
		}
		
		for(Mechanism mechanism : mechanismList){
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("mechanismId", mechanism.getId());
			data_map.put("mechanismName", mechanism.getName());
			data_map.put("mechanismAddress", mechanism.getAddress());
			data_map.put("mechainismPhone", mechanism.getPhone());
			data_map.put("mechanismScoreSort", mechanism.getScoreSort());
			data_map.put("mechanismSecond", mechanism.getSecond());
			data_map.put("mechanismLogo", mechanism.getLogo());
			data_map.put("mechanismLongitude", mechanism.getLongitude());
			data_map.put("mechanismLatitude", mechanism.getLatitude());
			data_map.put("mechanismIntroduce", mechanism.getIntroduce());
			
			data_list.add(data_map);
		}
		
		return data_list;
		
		
		
	}

	@Override
	public List<Mechanism> getMechanism() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mechanism> criteriaQuery = criteriaBuilder.createQuery(Mechanism.class);
		Root<Mechanism> root = criteriaQuery.from(Mechanism.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<Mechanism> find(String file) {
		Map<String ,Object> map = new HashMap<String, Object>();
		List<Mechanism> mechanisms = new ArrayList<Mechanism>();
		
		JSONObject json = JSONObject.fromObject(file);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mechanism> criteriaQuery = criteriaBuilder.createQuery(Mechanism.class);
		Root<Mechanism> root = criteriaQuery.from(Mechanism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();

		/**评分*/
		String scoreSort = json.get("scoreSort").toString();
		
		/**诊次*/
		String second = json.get("second").toString();
		
		/** 经度 */
		 double longitude = Double.valueOf((json.get("longitude").toString().equals("")?Config.longitude:json.get("longitude").toString()));
		
		/** 纬度 */
		 double latitude =  Double.valueOf(json.get("latitude").toString().equals("")?Config.latitude:json.get("latitude").toString());
		
		 /** 页码 */
		 int pageNumber = json.getInt("pageNumber");
		 
		 /** 每页显示条数 */
		 int pagesize = Config.pageSize;
		 
		if (scoreSort.equals("desc")) {
	        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreSort")));  
		}else if(scoreSort.equals("asc")){
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("scoreSort")));
			
		}
		if (second.equals("desc")) {
	        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("second")));  
		}else if(second.equals("asc")){
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("second")));
			
		}
		
		criteriaQuery.where(restrictions);
		mechanisms =  super.findList(criteriaQuery, null, null, null, null);
		
		if(mechanisms.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无机构数据");
			map.put("data", "{}");
			return mechanisms;
		}
		
//		if(!StringUtils.isEmpty(json.getString("distance"))){
//			Boolean key = Boolean.parseBoolean(json.getString("distance"));  
			if(longitude != 0 && latitude!=0){
				Map<String ,Object> mechanism_map = new HashMap<String, Object>();
				mechanism_map.put("longitude", longitude);
				mechanism_map.put("latitude", latitude);
				mechanism_map.put("mechanisms", mechanisms);
				//mechanism_map.put("isDesc", key);//false 升序  true 降序
				mechanism_map.put("isDesc", false);//false 升序  true 降序
				mechanisms = MechanismUtil.getOrderByDistance(mechanism_map);
//			}
		}
		
		String status = "200";
		String message = "第"+pageNumber+"数据加载";
		Map<String ,Object> data_map = new HashMap<String, Object>();
		
		//总页数
		Integer pagecount = (mechanisms.size()+pagesize-1)/pagesize;
		
		if (pageNumber>pagecount) {
			message = "无更多数据";
			status = "500";
		}
		
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		
		if(pageNumber>=pagecount){
			data_map.put("mechanisms",mechanisms.subList((pagenumber-1)*pagesize, mechanisms.size()));
		}else{
			data_map.put("mechanisms", mechanisms.subList((pagenumber-1)*pagesize, pagesize*pagenumber));
		}
		map.put("status", status);
		map.put("message", message);
		map.put("data", JsonUtils.toJson(data_map));
		return mechanisms;
	}


}