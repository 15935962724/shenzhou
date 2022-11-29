/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.Query;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.DoctorDao;
import net.shenzhou.dao.MechanismDao;
import net.shenzhou.dao.ProjectDao;
import net.shenzhou.dao.ProjectItemDao;
import net.shenzhou.dao.ServerProjectCategoryDao;
import net.shenzhou.dao.WorkDayDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.User;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Project.Audit;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.WorkDay.WorkType;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ProjectItem;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.DoctorUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MechanismUtil;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author wsr
 *
 */
@Repository("doctorDaoImpl")
public class DoctorDaoImpl extends BaseDaoImpl<Doctor, Long> implements DoctorDao {

	@Resource(name = "mechanismDaoImpl")
	private MechanismDao mechanismDao;
	@Resource(name = "workDayDaoImpl")
	private WorkDayDao workDayDao;
	@Resource(name = "projectDaoImpl")
	private ProjectDao projectDao;
	@Resource(name = "projectItemDaoImpl")
	private ProjectItemDao projectItemDao;
	@Resource(name = "serverProjectCategoryDaoImpl")
	private ServerProjectCategoryDao serverProjectCategoryDao;
	@Resource(name = "doctorCategoryServiceImpl")
	private DoctorCategoryService doctorCategoryService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	
	public boolean usernameExists(String username) {
		if (username == null) {
			return false;
		}
		String jpql = "select count(*) from Doctor doctor where lower(doctor.username) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		return count > 0;
	}
	

	public boolean emailExists(String email) {
		if (email == null) {
			return false;
		}
		String jpql = "select count(*) from Doctor doctor where lower(doctor.email) = lower(:email)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("email", email).getSingleResult();
		return count > 0;
	}
	
	@Override
	public Doctor findByUsername(String username) {
		// TODO Auto-generated method stub
		if (username == null) {
			return null;
		}
		try {
			String jpql = "select doctors from Doctor doctors where lower(doctors.username) = lower(:username)";
			return entityManager.createQuery(jpql, Doctor.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public List<Doctor> findList(Map<String ,Object> map) {
		// TODO Auto-generated method stub
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
	/*	String doctorName = map.get("doctorName").toString();////医生技师名字
		if (doctorName != null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.like(root.<String> get("name"), "%" + doctorName + "%"));
		}
	    
		Gender gender =  (Gender) map.get("gender");//医师性别
		if (gender != null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("gender"),gender));
		}
	    
		String preDiagnosisDate=  map.get("preDiagnosisDate").toString();//预诊日期
		
		Map<String,Object> workDayMap = new HashMap<String, Object>();
		workDayMap.put("workDayDate", preDiagnosisDate);
		List<WorkDay> workDays = workDayDao.getWorkDays(workDayMap);//根据预诊日期筛选医师工作日
		if(workDays.size()>0){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("workDay")).value(workDays));//根据医师的工作日再去筛选医师
		}
		
	    String preDiagnosisStartTime =  map.get("preDiagnosisStartTime").toString();//预诊时间(开始时间)
	    String preDiagnosisEndTime =  map.get("preDiagnosisEndTime").toString();//预诊时间(结束时间)
	    if((preDiagnosisStartTime!=null&&!preDiagnosisStartTime.equals(""))&&(preDiagnosisEndTime!=null&&!preDiagnosisEndTime.equals(""))) {//预诊时间(开始时间)//预诊时间(结束时间)	
	    	
	    }
		
	    @SuppressWarnings("unchecked")
		List<Mechanism> mechanisms = (List<Mechanism>) map.get("mechanisms");
		if(mechanisms.size()>0){
			restrictions=criteriaBuilder.and(criteriaBuilder.in(root.get("mechanism")).value(mechanisms));//根据机构筛选医师
		}*/
		
		
	    
	    String scoreSort =  map.get("scoreSort").toString();//评分排序(医师字段)
	    if(scoreSort!=null&&!scoreSort.equals("")){
	    	if(scoreSort.equals("desc")){
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreSort")));
			}else if(scoreSort.equals("asc")){
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("scoreSort")));
			}
	    }
	    restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("status"),Status.allow));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
		
		
	}

	@Override
	public boolean mobileExists(String mobile) {
		if (mobile == null) {
			return false;
		}
		String jpql = "select count(*) from Doctor doctors where lower(doctors.mobile) = lower(:mobile)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile", mobile).getSingleResult();
		return count > 0;
	}

	@Override
	public Map<String, Object> findList(String file) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(file);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		Integer pageNumber = json.getInt("pageNumber");//页码
		
		Integer pageSize = Config.pageSize;//每页显示多少条
		
		String scoreSort = json.getString("scoreSort");//评分
	    if(scoreSort!=null&&!scoreSort.equals("")){
	    	if(scoreSort.equals("desc")){
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreSort")));
			}else if(scoreSort.equals("asc")){
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("scoreSort")));
			}
	    }
		
		String second = json.getString("second");//诊次
		
		if(second!=null&&!second.equals("")){
	    	if(second.equals("desc")){
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("second")));
			}else if(second.equals("asc")){
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("second")));
			}
	    }
		
		/** 经度 */
		double longitude = Double.valueOf((json.get("longitude").toString().equals("")?"0":json.get("longitude").toString()));
		
		/** 纬度 */
		double latitude =  Double.valueOf(json.get("latitude").toString().equals("")?"0":json.get("latitude").toString());
		 
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), Status.allow));
		criteriaQuery.where(restrictions);
		List<Doctor> doctors = super.findList(criteriaQuery, null, null, null, null);
		
		
		if(doctors.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无医生数据");
			map.put("data", "{}");
			return map;
		}
		
		//综合排序
		if(StringUtils.isEmpty(second)&&StringUtils.isEmpty(scoreSort)&&StringUtils.isEmpty(json.getString("longitude"))&&StringUtils.isEmpty(json.getString("latitude"))){
			
			 
			
		}
		
//		if(!StringUtils.isEmpty(json.getString("distance"))){
//			Boolean key = Boolean.parseBoolean(json.getString("distance"));  
			if( longitude != 0 && latitude!= 0){
				Map<String ,Object> doctor_map = new HashMap<String, Object>();
				doctor_map.put("longitude", longitude);
				doctor_map.put("latitude", latitude);
				//doctor_map.put("isDesc", key);//false 升序  true 降序
				doctor_map.put("isDesc", false);//false 升序  true 降序
				doctor_map.put("doctors", doctors);
				doctors = DoctorUtil.getOrderByDistance(doctor_map);
//			}
			
		}
		
		String status = "200";
		String message = "第"+pageNumber+"页数据加载成功";
		Map<String ,Object> data_map = new HashMap<String, Object>();
		
		//总页数
		Integer pagecount = (doctors.size()+pageSize-1)/pageSize;
				
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		
		if (doctors.size()>0){
			if(pageNumber>=pagecount){
				data_map.put("doctors",doctors.subList((pagenumber-1)*pageSize, doctors.size()));
			}else{
				data_map.put("doctors", doctors.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
			}
			if (pageNumber>pagecount) {
				 status = "500";
				 message = "无更多数据";
			}
		}
		map.put("status", status);
		map.put("message", message);
		map.put("data", JsonUtils.toJson(data_map));
		
		return map;
		
	}

	@Override
	public Doctor findByMobile(String mobile) {
		if (mobile == null) {
			return null;
		}
		try {
			String jpql = "select doctors from Doctor doctors where lower(doctors.mobile) = lower(:mobile)";
			return entityManager.createQuery(jpql, Doctor.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile", mobile).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Doctor findBySafeKeyValue(String safeKeyValue) {
		if (safeKeyValue == null) {
			return null;
		}
		try {
			String jpql = "select doctors from Doctor doctors where lower(doctors.safeKey.value) = lower(:safe_key_value)";
			return entityManager.createQuery(jpql, Doctor.class).setFlushMode(FlushModeType.COMMIT).setParameter("safe_key_value", safeKeyValue).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Doctor> findByMechanism(Mechanism mechanism) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> bill = criteriaQuery.from(Doctor.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(mechanism!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("mechanism"),  mechanism));
		}
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("isDeleted"),  false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("status"),  Status.allow));
		
		criteriaQuery.select(bill);
		criteriaQuery.where(restrictions);
		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<Doctor> findByDoctors(String name) {
		if (name == null) {
			return Collections.<Doctor> emptyList();
		}
		String jpql = "select doctors from Doctor doctors where lower(doctors.name) like lower(:name)";
		return entityManager.createQuery(jpql, Doctor.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", "%"+name+"%").getResultList();
	}

	@Override
	public Doctor findByName(String name) {
		if (name == null) {
			return null;
		}
		try {
			String jpql = "select doctors from Doctor doctors where lower(doctors.name) = lower(:name)";
			return entityManager.createQuery(jpql, Doctor.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Page<Doctor> findPage(Map<String, Object> query_map) {
		
		
		Pageable pageable = (Pageable) query_map.get("pageable");
		Mechanism mechanism =  (Mechanism) query_map.get("mechanism");
		Object nameOrphone = query_map.get("nameOrphone");
		Object doctorCategory = query_map.get("doctorCategory");
		Object gender = query_map.get("gender");
		Object serverProjectCategory = query_map.get("serverProjectCategory");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		//根据医生姓名或手机号搜索
		if (nameOrphone != null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.or(criteriaBuilder.like(root.<String> get("name"), "%" + nameOrphone + "%"),criteriaBuilder.like(root.<String> get("mobile"), "%" + nameOrphone + "%")));
		}
		
		//医生类别
		if (doctorCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctorCategory"),(DoctorCategory)doctorCategory));
		}
		//医生性别
		if (gender != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("gender"),Gender.valueOf(String.valueOf(gender))));
		}
		//服务类别
		if (serverProjectCategory!=null) {
			List<Project> projects = projectDao.getProjects((ServerProjectCategory)serverProjectCategory);
		    if (projects.size()>0) {
		    	restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("projects")).value(projects));
			}
		}
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
		
	}

	@Override
	public List<Doctor> findByStatus() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), Status.allow));
		criteriaQuery.where(restrictions);
		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<Doctor> searchByName(String doctorName) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"),  Status.allow));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.like(root.<String>get("name"),"%" + doctorName + "%"));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<Doctor> getNameOrMobile(Map<String, Object> query_map) {
		Mechanism mechanism =  (Mechanism) query_map.get("mechanism");
		Object nameOrmoible = query_map.get("nameOrmobile");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		if (mechanism!=null) {
			restrictions = criteriaBuilder.and(criteriaBuilder.equal(root.get("mechanism"), mechanism));
		}
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.or(criteriaBuilder.like(root.<String> get("name"), "%" + nameOrmoible + "%"),criteriaBuilder.like(root.<String> get("mobile"), "%" + nameOrmoible + "%")));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<Doctor> getDoctors(Map<String, Object> query_map) {
		
		Object nameOrphone = query_map.get("nameOrphone");
		Object doctorCategory = query_map.get("doctorCategory");
		Object gender = query_map.get("gender");
//		Object serverProjectCategory = query_map.get("serverProjectCategory");
		ServerProjectCategory serverProjectCategory =(ServerProjectCategory)query_map.get("serverProjectCategory");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		//根据医生姓名或手机号搜索
		if (nameOrphone != null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.or(criteriaBuilder.like(root.<String> get("name"), "%" + nameOrphone + "%"),criteriaBuilder.like(root.<String> get("mobile"), "%" + nameOrphone + "%")));
		}
		
		//医生类别
		if (doctorCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctorCategory"),(DoctorCategory)doctorCategory));
		}
		//医生性别
		if (gender != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("gender"),Gender.valueOf(String.valueOf(gender))));
		}
		//服务类别
		if (serverProjectCategory!=null) {
			List<Project> projects = projectDao.getProjects(serverProjectCategory);
		    if (projects.size()>0) {
		    	restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("projects")).value(Arrays.asList(projects)));
			}
		}
		
		criteriaQuery.where(restrictions);
		try {
			return super.findList(criteriaQuery, null, null, null, null);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public Map<String, Object> findDoctorList(String file) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(file);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		Integer pageNumber = json.getInt("pageNumber");//页码
		
		Integer pageSize = Config.pageSize;//每页显示多少条
		
		String scoreSort = json.getString("scoreSort");//评分
		List<DoctorCategory> category_list = doctorCategoryService.find();
	    if(scoreSort!=null&&!scoreSort.equals("")){
	    	if(scoreSort.equals("desc")){
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreSort")));
			}else if(scoreSort.equals("asc")){
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("scoreSort")));
			}
	    }
		
		String second = json.getString("second");//诊次
		
		if(second!=null&&!second.equals("")){
	    	if(second.equals("desc")){
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("second")));
			}else if(second.equals("asc")){
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("second")));
			}
	    }
		
		/** 经度 */
		double longitude = Double.valueOf((json.get("longitude").toString().equals("")?"0":json.get("longitude").toString()));
		
		/** 纬度 */
		double latitude =  Double.valueOf(json.get("latitude").toString().equals("")?"0":json.get("latitude").toString());
		 
		criteriaQuery.where(restrictions);
		List<Doctor> allDoctor = super.findList(criteriaQuery, null, null, null, null);
		
		if(allDoctor.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无医生数据");
			map.put("data", "{}");
			return map;
		}
		
		
		List<Doctor> list_list = new ArrayList<Doctor>();
		for(Doctor d : allDoctor){
			List<DoctorMechanismRelation> relation = doctorMechanismRelationService.getDoctorMechanism(d);
			if(relation != null && relation.size() > 0){
				for(DoctorMechanismRelation dmr : relation){
					if(dmr.getIsAbout() == true){
						list_list.add(d);
						break;
					}
				}
			}	
			
		}
		
		
		
		List<Doctor> doctors = new ArrayList<Doctor>();
		for(Doctor doctor : list_list){
			if(doctor.getIsDoctorStatus()&&doctor.getStatus().equals(Status.allow)){
				doctors.add(doctor);
			}
		}
		
		if(!StringUtils.isEmpty(json.getString("distance"))){
			Boolean key = Boolean.parseBoolean(json.getString("distance"));  
			if( longitude != 0 && latitude!= 0){
				Map<String ,Object> doctor_map = new HashMap<String, Object>();
				doctor_map.put("longitude", longitude);
				doctor_map.put("latitude", latitude);
				doctor_map.put("isDesc", key);//false 升序  true 降序
				doctor_map.put("doctors", doctors);
				doctors = DoctorUtil.getOrderByDistance(doctor_map);
			}
			
		}
		
		String status = "200";
		String message = "第"+pageNumber+"页数据加载成功";
		Map<String ,Object> data_map = new HashMap<String, Object>();
		
		//总页数
		Integer pagecount = (doctors.size()+pageSize-1)/pageSize;
				
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		
		List<Doctor> doctor_list = new ArrayList<Doctor>();
		
		
		if (doctors.size()>0){
			if(pageNumber>=pagecount){
				doctor_list = doctors.subList((pagenumber-1)*pageSize, doctors.size());
			}else{
				doctor_list = doctors.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
			}
			if (pageNumber>pagecount) {
				 status = "500";
				 message = "无更多数据";
			}
		}
		
		List<Map<String,Object>> doctor_data = new ArrayList<Map<String,Object>>();
		for(Doctor doctor : doctor_list){
			Map<String,Object> doctor_map = new HashMap<String, Object>();
			doctor_map.put("doctorId", doctor.getId());
			doctor_map.put("doctorName", doctor.getName());
			doctor_map.put("doctorCategory", doctor.getDoctorCategory().getName());
			doctor_map.put("sex", doctor.getGender());
			doctor_map.put("introduce", doctor.getIntroduce());
			doctor_map.put("doctorLogo", doctor.getLogo());
			doctor_map.put("scoreSort", doctor.getScoreSort());
			doctor_map.put("doctorSecond", doctor.getSecond());
			//获取离这个用户最近的医生所在机构
			List<Mechanism> mechanisms = new ArrayList<Mechanism>();
			for(DoctorMechanismRelation dmr : doctor.getDoctorMechanismRelations()){
				Mechanism mechanism = dmr.getMechanism();
				System.out.println(mechanism.getId());
				System.out.println(mechanism.getName());
				if (dmr.getAudit().equals(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed)) {
					mechanisms.add(dmr.getMechanism());
				}
			}
			Map<String,Object> mechanisms_map = new HashMap<String, Object>();
			mechanisms_map.put("longitude", longitude);
			mechanisms_map.put("latitude", latitude);
			mechanisms_map.put("mechanisms", mechanisms);
			mechanisms_map.put("isDesc", false);
			List<Mechanism> list_mechanism = MechanismUtil.getOrderByDistance(mechanisms_map);
			doctor_map.put("mechanismName", list_mechanism.get(0).getName());
			doctor_map.put("longitude", list_mechanism.get(0).getLongitude());
			doctor_map.put("latitude", list_mechanism.get(0).getLatitude());
			doctor_data.add(doctor_map);
		}
		data_map.put("category_list", category_list);
		data_map.put("doctors", doctor_data);
		
		map.put("status", status);
		map.put("message", message);
		map.put("data", JsonUtils.toJson(data_map));
		
		return map;
		
	}

	@Override
	public Integer getMaxSecond() {
		String jpql = "SELECT MAX(second) FROM Doctor";
		Integer count = entityManager.createQuery(jpql, Integer.class).setFlushMode(FlushModeType.COMMIT).getSingleResult();
		return count;
	}

	@Override
	public Map<String, Object> doctorFiltrate(String file) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(file);
		String price_min = json.getString("price_min");
		String price_max = json.getString("price_max");
		String mode = json.getString("mode");
		String doctorName = json.getString("doctorName");
		String longitude = json.getString("longitude");// 经度    	
    	String latitude = json.getString("latitude");//纬度 
		String gender = json.getString("gender");
		String dateTime = json.getString("dateTime");
		String date_min = json.getString("date_min");
		String date_max = json.getString("date_max");
		Integer pageNumber = json.getInt("pageNumber");//页码
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> doctors = criteriaQuery.from(Doctor.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(doctors);
		/*if(!StringUtils.isEmpty(json.getString("serverProjectCategoryId"))){
			ServerProjectCategory serverProjectCategory = serverProjectCategoryDao.find(json.getLong("serverProjectCategoryId"));
			if(serverProjectCategory!=null){
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(project.get("parentServerProjectCategory"),  serverProjectCategory));
			}
		}*/
		//yisheng
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(doctors.get("isDeleted"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(doctors.get("status"), Status.allow));
		if(!StringUtils.isEmpty(gender)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(doctors.get("gender"), Gender.valueOf(gender)));
		}
		if(!StringUtils.isEmpty(doctorName)){
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.like(doctors.<String>get("name"),"%" + doctorName + "%"));
		}
		
		Join<Project ,Doctor > project = doctors.join("projects");
		Join<WorkDay, Doctor> workDay = doctors.join("workDays");
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(project.get("isDeleted"),  false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(project.get("audit"),  Audit.succeed));
		if(!StringUtils.isEmpty(price_min)&&!StringUtils.isEmpty(price_max)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.between(project.<BigDecimal>get("price"), new BigDecimal(price_min), new BigDecimal(price_max)));
		}
		if(!StringUtils.isEmpty(mode)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(project.get("mode"),  Mode.valueOf(mode)));
		}
		
		//日期条件
		if(!StringUtils.isEmpty(dateTime)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(workDay.get("workType"), WorkType.work));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(workDay.get("workDayDate"),DateUtil.getStringtoDate(dateTime, "yyyy-MM-dd")));
		}
		
		if(!StringUtils.isEmpty(date_min)&&!StringUtils.isEmpty(date_max)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(workDay.get("workType"), WorkType.work));
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(workDay.<Date>get("createDate"), DateUtil.getStringtoDate(date_min, "yyyy-MM-dd"), DateUtil.getStringtoDate(date_max, "yyyy-MM-dd")));
		}
		
		criteriaQuery.where(restrictions);
		Pageable pageable = new Pageable();
		pageable.setPageSize(Config.pageSize);
		pageable.setPageNumber(pageNumber);
		List<Doctor> doctor_list = super.findPage(criteriaQuery, pageable).getContent();
		
		if(doctor_list.size()<=0){
			map.put("status", "500");
			map.put("message", "无更多医生数据");
			map.put("data", "{}");
			return map;
		}
		
		List<Map<String,Object>> doctor_data = new ArrayList<Map<String,Object>>();
		for(Doctor doctor : doctor_list){
			Map<String,Object> doctor_map = new HashMap<String, Object>();
			doctor_map.put("doctorId", doctor.getId());
			doctor_map.put("doctorName", doctor.getName());
			doctor_map.put("doctorCategory", doctor.getDoctorCategory().getName());
			doctor_map.put("sex", doctor.getGender());
			doctor_map.put("introduce", doctor.getIntroduce());
			doctor_map.put("doctorLogo", doctor.getLogo());
			doctor_map.put("scoreSort", doctor.getScoreSort());
			doctor_map.put("doctorSecond", doctor.getSecond());
			//获取离这个用户最近的医生所在机构
			List<Mechanism> mechanisms = new ArrayList<Mechanism>();
			for(DoctorMechanismRelation dmr : doctor.getDoctorMechanismRelations(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed)){
				mechanisms.add(dmr.getMechanism());
			}
			Map<String,Object> mechanisms_map = new HashMap<String, Object>();
			mechanisms_map.put("longitude", longitude);
			mechanisms_map.put("latitude", latitude);
			mechanisms_map.put("mechanisms", mechanisms);
			mechanisms_map.put("isDesc", false);
			List<Mechanism> list_mechanism = MechanismUtil.getOrderByDistance(mechanisms_map);
			doctor_map.put("mechanismName", list_mechanism.get(0).getName());
			doctor_map.put("longitude", list_mechanism.get(0).getLongitude());
			doctor_map.put("latitude", list_mechanism.get(0).getLatitude());
			doctor_data.add(doctor_map);
		}
		
		data_map.put("doctors", doctor_data);
		
		map.put("status", "200");
		map.put("message", "加载成功");
		map.put("data", JsonUtils.toJson(data_map));
		
		return map;
	}

	@Override
	public Map<String, Object> webDoctorList(String scoreSort, String second,
			String distance, String longitude, String latitude,
			String pageNumbers,String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String ,Object> data_map = new HashMap<String, Object>();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		Integer pageNumber = Integer.valueOf(pageNumbers);
		Integer pageSize = Config.pageSize;//每页显示多少条
		
	    if(flag.equals("2")){
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreSort")));
	    }
		
		
		if(flag.equals("3")){
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("second")));
	    }
		
		/** 经度 */
		longitude = longitude.equals("0")?"0":Config.longitude;
		
		/** 纬度 */
		latitude =  latitude.toString().equals("0")?"0":Config.latitude;
		 
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), Status.allow));
		criteriaQuery.where(restrictions);
		List<Doctor> doctors = super.findList(criteriaQuery, null, null, null, null);
		
		if(doctors.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无医生数据");
			map.put("data", "{}");
			return data_map;
		}
		if(flag.equals("4")){
			Boolean key = Boolean.parseBoolean(distance);  
			if( Double.parseDouble(longitude) != 0 && Double.parseDouble(latitude)!= 0){
				Map<String ,Object> doctor_map = new HashMap<String, Object>();
				doctor_map.put("longitude", longitude);
				doctor_map.put("latitude", latitude);
				doctor_map.put("isDesc", key);//false 升序  true 降序
				doctor_map.put("doctors", doctors);
				doctors = DoctorUtil.getOrderByDistance(doctor_map);
			}
			
		}
		
		String status = "200";
		String message = "第"+pageNumber+"页数据加载成功";
		
		//总页数
		Integer pagecount = (doctors.size()+pageSize-1)/pageSize;
				
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		
		List<Doctor> doctor_list = new ArrayList<Doctor>();
		
		
		if (doctors.size()>0){
			if(pageNumber>=pagecount){
				doctor_list = doctors.subList((pagenumber-1)*pageSize, doctors.size());
			}else{
				doctor_list = doctors.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
			}
			if (pageNumber>pagecount) {
				 status = "500";
				 message = "无更多数据";
				 return data_map;
			}
		}
		
		List<Map<String,Object>> doctor_data = new ArrayList<Map<String,Object>>();
		for(Doctor doctor : doctor_list){
			Map<String,Object> doctor_map = new HashMap<String, Object>();
			doctor_map.put("doctorId", doctor.getId());
			doctor_map.put("doctorName", doctor.getName());
			doctor_map.put("doctorCategory", doctor.getDoctorCategory().getName());
			doctor_map.put("sex", doctor.getGender());
			doctor_map.put("introduce", doctor.getIntroduce());
			doctor_map.put("doctorLogo", doctor.getLogo());
			doctor_map.put("scoreSort", doctor.getScoreSort());
			doctor_map.put("doctorSecond", doctor.getSecond());
			//获取离这个用户最近的医生所在机构
			List<Mechanism> mechanisms = new ArrayList<Mechanism>();
			for(DoctorMechanismRelation dmr : doctor.getDoctorMechanismRelations()){
				Mechanism mechanism = dmr.getMechanism();
				System.out.println(mechanism.getId());
				System.out.println(mechanism.getName());
				if (dmr.getAudit().equals(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed)) {
					mechanisms.add(dmr.getMechanism());
				}
			}
			Map<String,Object> mechanisms_map = new HashMap<String, Object>();
			mechanisms_map.put("longitude", longitude);
			mechanisms_map.put("latitude", latitude);
			mechanisms_map.put("mechanisms", mechanisms);
			mechanisms_map.put("isDesc", false);
			List<Mechanism> list_mechanism = MechanismUtil.getOrderByDistance(mechanisms_map);
			doctor_map.put("mechanismName", list_mechanism.get(0).getName());
			doctor_map.put("longitude", list_mechanism.get(0).getLongitude());
			doctor_map.put("latitude", list_mechanism.get(0).getLatitude());
			doctor_data.add(doctor_map);
		}
		
		data_map.put("doctors", doctor_data);
		
		map.put("status", status);
		map.put("message", message);
		map.put("data", JsonUtils.toJson(data_map));
		
		return data_map;
		
	}

	@Override
	public Map<String, Object> filtrateDoctor(String price_min,
			String price_max, String serverProjectCategoryId,
			String doctorName, Date reserveDate, Date startDate, Date endDate,
			String service, String sex, Integer pageNumber) {
		
		return null;
	}
	
	@Override
	public Map<String, Object> screenLists(String file) {
		Map<String ,Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(file) || StringUtils.isEmpty(file)) {
			map.put("status", "400");
			map.put("message", "参数有误");
			map.put("data", new Object());
			return map;
		}
	    JSONObject json = JSONObject.fromObject(file);
	    /** 经度 */
		double longitude = Double.valueOf((json.get("longitude").toString().equals("")?"0":json.get("longitude").toString()));
		/** 纬度 */
		double latitude =  Double.valueOf(json.get("latitude").toString().equals("")?"0":json.get("latitude").toString());
	    String miPrice = json.getString("minPrice");
	    BigDecimal minPrice = null;
	    if(miPrice != null && !miPrice.equals("")){
	    	minPrice = BigDecimal.valueOf(Double.valueOf(miPrice));//最低价格(筛选)
	    }
	    String maPrice = json.getString("maxPrice");
	    BigDecimal maxPrice = null;
	    if(maPrice != null && !maPrice.equals("")){
	    	maxPrice = BigDecimal.valueOf(Double.valueOf(maPrice)); ;//最高价格(筛选)
	    }

	    
	    String  serverProjectCategoryId = json.getString("serverProjectCategoryId");//服务分类ID
	   	
        String mo = json.get("mode").toString();
        Mode mode = null;
        if(mo != null && !mo.equals("")){
        	mode = Mode.valueOf(mo);//服务方式(到店/上门)
        }
        String gender = json.get("gender").toString();
        
     
        Integer pageNumber = json.getInt("pageNumber");//页码
        Integer pageSize = Config.pageSize;
        
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), Status.allow));
		//List<ServerProjectCategory> list = new ArrayList<ServerProjectCategory>();
		if(gender != null && !gender.equals("")){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("gender"), Gender.valueOf(gender)));
		}
		Map<String ,Object> doctorMap = new HashMap<String ,Object>();
		criteriaQuery.where(restrictions);
		List<Doctor> doctors = super.findList(criteriaQuery, null, null, null, null);
		List<Doctor> doctors_list = new ArrayList<Doctor>();
		List<Doctor> doctor_list = new ArrayList<Doctor>();
	
		
		//------处理服务项目搜索的--------
		/*
				if(serverProjectCategoryId != null && !serverProjectCategoryId.equals("")){
					String typeid[] = serverProjectCategoryId.split(",");
					for(String id : typeid){
						for(Doctor doctor : doctors){
							Long tid = Long.parseLong(id);
							ServerProjectCategory spc = serverProjectCategoryDao.find(tid);
							List<Project> project = projectDao.getProjectByServerProjectCategorys(spc);
							for(Project p : project){
								
							}
						}
					}
					doctors = doctor_list;
				}*/
		
				//------处理方式--------
				if(mode != null){
					for(Doctor doctor : doctors){
						for(Project pro : doctor.getProjects()){
							if(mode == pro.getMode()){
								doctors_list.add(doctor);	
							}
						}
						
					}
				}
				//------处理医师职称------
				String doctorCategoryId = json.getString("doctorCategoryId");
				if(doctorCategoryId != null && !doctorCategoryId.equals("")){
					String categoryid[] = doctorCategoryId.split(",");
					for(String id : categoryid){
						for(Doctor doctor : doctors_list){
							Long tid = Long.parseLong(id);
							if(tid == doctor.getDoctorCategory().getId()){
								doctors_list.add(doctor);
							}
						}
					}
				}
		
		
		
		List<Map<String,Object>> doctor_data = new ArrayList<Map<String,Object>>();
		for(Doctor dt : doctors){//处理数据
			List<Project> project_list = projectDao.getProjectsByDoctor(dt);//根据医师获取项目
			List<ProjectItem> projectItems = null;
			List<BigDecimal> price = new ArrayList<BigDecimal>();
			for(Project project : project_list){//处理项目存储钱
				projectItems = projectItemDao.getProject(project);//通过项目获取价格
				if(projectItems == null){
					projectItems = new ArrayList<ProjectItem>();
				}
					for (int i = 0; i < projectItems.size(); i++) {
						if(projectItems.size() == 1){//处理项目价格的数据,临时存放在price里
							price.add(projectItems.get(0).getPrice());
						}else{
							for (int j = i + 1; j < projectItems.size(); j++) {//升序排序
								if (projectItems.get(i).getPrice().compareTo(projectItems.get(j).getPrice()) == 1 ) {
									ProjectItem temp = projectItems.get(i);
									projectItems.set(i, projectItems.get(j));
									projectItems.set(j, temp);
								}
							}
							price.add(projectItems.get(0).getPrice());//获取第一条数据,存放到临时集合
						}
					}
				}
				for (int i = 0; i < price.size(); i++) {//升序排序
					for (int j = i + 1; j < price.size(); j++) {
						if (price.get(i).compareTo(price.get(j)) == 1) {
							BigDecimal temp = price.get(i);
							price.set(i, price.get(j));
							price.set(j, temp);
						}
					}
				}
				
				Map<String,Object> doctor_map = new HashMap<String, Object>();
				doctor_map.put("doctorId", dt.getId());
				doctor_map.put("doctorName", dt.getName());
				doctor_map.put("doctorCategory", dt.getDoctorCategory().getName());
				doctor_map.put("sex", dt.getGender());
				doctor_map.put("introduce", dt.getIntroduce());
				doctor_map.put("doctorLogo", dt.getLogo());
				doctor_map.put("scoreSort", dt.getScoreSort());
				doctor_map.put("doctorSecond", dt.getSecond());
				if(price.size() <= 0){
					doctor_map.put("price", 0);
				}else{
					doctor_map.put("price", price.get(0));
					System.out.println(price.get(0));
				}
				
				
				//获取离这个用户最近的医生所在机构
				List<Mechanism> mechanisms = new ArrayList<Mechanism>();
				for(DoctorMechanismRelation dmr : dt.getDoctorMechanismRelations()){
					Mechanism mechanism = dmr.getMechanism();
					System.out.println(mechanism.getId());
					System.out.println(mechanism.getName());
					if (dmr.getAudit().equals(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed)) {
						mechanisms.add(dmr.getMechanism());
					}
				}
				Map<String,Object> mechanisms_map = new HashMap<String, Object>();
				mechanisms_map.put("longitude", longitude);
				mechanisms_map.put("latitude", latitude);
				mechanisms_map.put("mechanisms", mechanisms);
				mechanisms_map.put("isDesc", false);
				List<Mechanism> list_mechanism = MechanismUtil.getOrderByDistance(mechanisms_map);
				doctor_map.put("mechanismName", list_mechanism.size()<=0?"":list_mechanism.get(0).getName());
				doctor_map.put("longitude", list_mechanism.size()<=0?Config.longitude:list_mechanism.get(0).getLongitude());
				doctor_map.put("latitude", list_mechanism.size()<=0?Config.latitude:list_mechanism.get(0).getLatitude());
				doctor_data.add(doctor_map);
		}
		//---------------处理价格搜索的 -------
		List<Map<String,Object>> price_screen = new ArrayList<Map<String,Object>>();
		if(miPrice != null && !miPrice.equals("") && maPrice == null && maPrice.equals("")){
			for(Map<String,Object> do_map: doctor_data){
				/*int a = bigdemical.compareTo(bigdemical2)
				a = -1,表示bigdemical小于bigdemical2；
				a = 0,表示bigdemical等于bigdemical2；
				a = 1,表示bigdemical大于bigdemical2；*/
				String pr = do_map.get("price").toString();
				BigDecimal price = new BigDecimal(pr);
				if(minPrice.compareTo(price) == 1 && minPrice.compareTo(price) == 0 && maxPrice.compareTo(price) == -1){
					price_screen.add(do_map);
				}
			}
		}else if(minPrice != null && !minPrice.equals("") && maxPrice != null && !maxPrice.equals("")){
			for(Map<String,Object> do_map: doctor_data){
				/*int a = bigdemical.compareTo(bigdemical2)
				a = -1,表示bigdemical小于bigdemical2；
				a = 0,表示bigdemical等于bigdemical2；
				a = 1,表示bigdemical大于bigdemical2；*/
				String pr = do_map.get("price").toString();
				BigDecimal price = new BigDecimal(pr);
				if(minPrice.compareTo(price) == 1 && minPrice.compareTo(price) == 0 && maxPrice.compareTo(price) == -1){
					price_screen.add(do_map);
				}
			}
		}else{
			price_screen = doctor_data;
		}
		
		
		
		Map<String ,Object> data_map = new HashMap<String, Object>();
		String status = "200";
		String message = "第"+pageNumber+"页数据加载成功";
		
		if(doctor_data.size()==0){
			map.put("status", "500");
			map.put("message", "暂无项目数据");
			map.put("data", "{}");
			return map;
		}
		
		
		//总页数
		Integer pagecount = (doctor_data.size()+pageSize-1)/pageSize;
				
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		
		if (pageNumber>pagecount) {
			message = "无更多数据";
			status = "500";
		}
		
		
		if(pageNumber>=pagecount){
			data_map.put("doctors",doctor_data.subList((pagenumber-1)*pageSize, doctor_data.size()));
		}else{
			data_map.put("doctors", doctor_data.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
		}
		
		map.put("list", doctor_data);
		map.put("status", status);
		map.put("message", message);
		map.put("data", JsonUtils.toJson(data_map));
		return map;
	
	}
	
	
	@Override
	public List<Doctor> findBys(String file) {
		 JSONObject json = JSONObject.fromObject(file);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> root = criteriaQuery.from(Doctor.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		String gender = json.getString("gender");
		if(gender != null && !gender.equals("")){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("gender"), Gender.valueOf(gender)));
		}
		/*List<DoctorCategory> list = new ArrayList<DoctorCategory>();
		String doctorCategoryId = json.getString("doctorCategoryId");
		if(doctorCategoryId != null && !doctorCategoryId.equals("")){
			String categoryid[] = doctorCategoryId.split(",");
			for(String id : categoryid){
				DoctorCategory dc = doctorCategoryService.find(Long.parseLong(id));
				if(dc != null){
					list.add(dc);
				}
			}
		}
		if(list != null && list.size() > 0){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.in<>,  list));
		}*/
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), Status.allow));
		criteriaQuery.where(restrictions);
		
		return super.findList(criteriaQuery, null, null, null, null);
		
	}
}