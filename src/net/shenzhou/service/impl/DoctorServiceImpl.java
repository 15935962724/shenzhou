/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Page;
import net.shenzhou.Principal;
import net.shenzhou.Setting;
import net.shenzhou.dao.DoctorDao;
import net.shenzhou.dao.ProjectDao;
import net.shenzhou.dao.ProjectItemDao;
import net.shenzhou.dao.ServerProjectCategoryDao;
import net.shenzhou.dao.WorkDayDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.ProjectItem;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDay.WorkType;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MechanismUtil;
import net.shenzhou.util.SettingUtils;

/**
 * Service - 医生
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("doctorServiceImpl")
public class DoctorServiceImpl extends BaseServiceImpl<Doctor, Long> implements DoctorService {

	@Resource(name = "doctorDaoImpl")
	private DoctorDao doctorDao;

	@Resource(name = "doctorDaoImpl")
	public void setBaseDao(DoctorDao doctorDao) {
		super.setBaseDao(doctorDao);
	}
	
	@Resource(name = "projectDaoImpl")
	private ProjectDao projectDao;
	@Resource(name = "serverProjectCategoryDaoImpl")
	private ServerProjectCategoryDao serverProjectCategoryDao;
	@Resource(name = "workDayDaoImpl")
	private WorkDayDao workDayDao;
	@Resource(name = "projectItemDaoImpl")
	private ProjectItemDao projectItemDao;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	@Resource(name = "doctorCategoryServiceImpl")
	private DoctorCategoryService doctorCategoryService;
	
	@Override
	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		// TODO Auto-generated method stub
		return doctorDao.usernameExists(username);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean usernameDisabled(String username) {
		// TODO Auto-generated method stub
		Assert.hasText(username);
		Setting setting = SettingUtils.get();
		if (setting.getDisabledUsernames() != null) {
			for (String disabledUsername : setting.getDisabledUsernames()) {
				if (StringUtils.containsIgnoreCase(username, disabledUsername)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean emailExists(String email) {
		// TODO Auto-generated method stub
		return doctorDao.emailExists(email);
	}

	@Override
	public boolean emailUnique(String previousEmail, String currentEmail) {
		// TODO Auto-generated method stub
		if (StringUtils.equalsIgnoreCase(previousEmail, currentEmail)) {
			return true;
		} else {
			if (doctorDao.emailExists(currentEmail)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	
	
	@Override
	public Doctor findByUsername(String username) {
		// TODO Auto-generated method stub
		return doctorDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Doctor.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Doctor getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Doctor.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return doctorDao.find(principal.getId());
			}
		}
		return null;
	}
	
	
	@Override
	public List<Doctor> findList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return doctorDao.findList(map);
	}

	

	@Override
	public boolean mobileExists(String mobile) {
		// TODO Auto-generated method stub
		return doctorDao.mobileExists(mobile);
	}

	@Override
	public Map<String, Object> findList(String file) {
		// TODO Auto-generated method stub
		return doctorDao.findList(file);
	}
	
	@Override
	public Doctor findByMobile(String mobile) {
		// TODO Auto-generated method stub
		return doctorDao.findByMobile(mobile);
	}

	@Override
	public Doctor findBySafeKeyValue(String safeKeyValue) {
		// TODO Auto-generated method stub
		return doctorDao.findBySafeKeyValue(safeKeyValue);
	}

	@Override
	public List<Doctor> findByMechanism(Mechanism mechanism) {
		// TODO Auto-generated method stub
		return doctorDao.findByMechanism(mechanism);
	}

	@Override
	public Page<Doctor> findPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return doctorDao.findPage(query_map);
	}

	@Override
	public List<Doctor> searchByName(String doctorName) {
		// TODO Auto-generated method stub
		return doctorDao.searchByName(doctorName);
	}

	@Override
	public List<Doctor> getNameOrMobile(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return doctorDao.getNameOrMobile(query_map);
	}

	@Override
	public Map<String, Object> doctorFiltrate(String file) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		JSONObject json = JSONObject.fromObject(file);
		String longitude = json.getString("longitude");// 经度    	
    	String latitude = json.getString("latitude");//纬度 
		String gender = json.getString("gender");
		String dateTime = json.getString("dateTime");
		String date_min = json.getString("date_min");
		String date_max = json.getString("date_max");
		List<Project> project_list = projectDao.projectFiltrate(file);
		List<Doctor> doctor_list = new ArrayList<Doctor>();
		
		for(Project project : project_list){
			System.out.println(project.getId());
			Boolean key = false;
			if(!StringUtils.isEmpty(gender)){
				if(!project.getDoctor().getGender().equals(Gender.valueOf(gender))){
					continue;
				}
			}
			if(!StringUtils.isEmpty(dateTime)){
				System.out.println(project.getId());
				System.out.println(project.getDoctor().getId());
				WorkDay workDay = workDayDao.getWorkDayByDoctorAndData(project.getDoctor(), DateUtil.getStringtoDate(dateTime, "yyyy-MM-dd"));
				if(workDayDao.getWorkDayByDoctorAndData(project.getDoctor(), DateUtil.getStringtoDate(dateTime, "yyyy-MM-dd"))==null||!workDayDao.getWorkDayByDoctorAndData(project.getDoctor(), DateUtil.getStringtoDate(dateTime, "yyyy-MM-dd")).getWorkType().equals(WorkType.work)){
					continue;
				}
			}
			if(project.getDoctor().getIsDeleted().equals(true)){
				continue;
			}
			if(!project.getDoctor().getStatus().equals(Status.allow)){
				continue;
			}	
			if(!StringUtils.isEmpty(date_min)&&!StringUtils.isEmpty(date_max)){
				for(int x = 0;;x++){
					if(DateUtil.compare_date_days(DateUtil.dataAdd(DateUtil.getStringtoDate(date_min, "yyyy-MM-dd"), x), date_max)==-1){
						break;
					}
					if(workDayDao.getWorkDayByDoctorAndData(project.getDoctor(), DateUtil.getStringtoDate(DateUtil.dataAdd(DateUtil.getStringtoDate(date_min, "yyyy-MM-dd"), x), "yyyy-MM-dd"))==null||workDayDao.getWorkDayByDoctorAndData(project.getDoctor(), DateUtil.getStringtoDate(DateUtil.dataAdd(DateUtil.getStringtoDate(date_min, "yyyy-MM-dd"), x), "yyyy-MM-dd")).getWorkType().equals(WorkType.work)){
						key=true;
						break;
					}
				}
				if(key==false){
					continue;
				}
			}
			if(!doctor_list.contains(project.getDoctor())){
				doctor_list.add(project.getDoctor());
			}
		}
		
		Map<String,Object> data_map = new HashMap<String, Object>();
		
		Integer pageNumber = json.getInt("pageNumber");//页码
		Integer pageSize = Config.pageSize;
		
		String status = "200";
		String message = "第"+pageNumber+"页数据加载成功";
		
		if(doctor_list.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无医生数据");
			map.put("data", "{}");
			return map;
		}
		
		//总页数
		Integer pagecount = (doctor_list.size()+pageSize-1)/pageSize;
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;

		List<Doctor> doctor_lists = new ArrayList<Doctor>();
		
		
		if (doctor_list.size()>0){
			if(pageNumber>=pagecount){
				doctor_lists = doctor_list.subList((pagenumber-1)*pageSize, doctor_list.size());
			}else{
				doctor_lists = doctor_list.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
			}
			if (pageNumber>pagecount) {
				 status = "500";
				 message = "无更多数据";
			}
		}
		
		List<Map<String,Object>> doctor_data = new ArrayList<Map<String,Object>>();
		for(Doctor doctor : doctor_lists){
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
		
		map.put("status", status);
		map.put("message", message);
		map.put("data", JsonUtils.toJson(data_map));
		
		return map;
		
	}



	@Override
	public Map<String, Object> findDoctorList(String file) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(file);
		String scoreSort = json.getString("scoreSort");
		String second = json.getString("second");
		String distance = json.getString("distance");
		/** 经度 */
		double longitude = Double.valueOf((json.get("longitude").toString().equals("")?"0":json.get("longitude").toString()));
		/** 纬度 */
		double latitude =  Double.valueOf(json.get("latitude").toString().equals("")?"0":json.get("latitude").toString());
		
		Integer pageNumber = json.getInt("pageNumber");//页码
		Integer pageSize = Config.pageSize;//每页显示多少条
		
		//全部为空为综合排序
		if(StringUtils.isEmpty(scoreSort)&&StringUtils.isEmpty(second)&&StringUtils.isEmpty(distance)){
			/**获取评分,诊次,距离的最大值*/
			Integer second_max = doctorDao.getMaxSecond();
			System.out.println(second_max);
			List<Doctor> all_doctor = doctorDao.findByStatus();
			
			
			
			Map<Doctor,Double> doctorsSort = new HashMap<Doctor,Double>();
			Map<String ,Object> data_map = new HashMap<String, Object>();
			for(Doctor doctor : all_doctor){
				/*if(doctor.getIsDoctorStatus()&&doctor.getStatus().equals(Status.allow)){
					Double scoreSorts = doctor.getScoreSort()*10*0.5;
					Double seconds = (doctor.getSecond()/10)*0.5;
					Double grade = scoreSorts+seconds;
					doctorsSort.put(doctor, grade);
					System.out.println("排序前==========="+grade);
				}*/
				
				//add wsr 2018-3-23 18:39:35 start
				if(doctor.getIsDoctorStatus()&&doctor.getIsAbout()){
					Double scoreSorts = doctor.getScoreSort()*10*0.5;
					Double seconds = (doctor.getSecond()/10)*0.5;
					Double grade = scoreSorts+seconds;
					doctorsSort.put(doctor, grade);
					System.out.println("排序前==========="+grade);
				}
				//add wsr 2018-3-23 18:39:35 end
			}
			
			List<Map.Entry<Doctor, Double>> list_Data = new ArrayList<Map.Entry<Doctor, Double>>(doctorsSort.entrySet());  
			Collections.sort(list_Data, new Comparator<Map.Entry<Doctor, Double>>()  {
				public int compare(Entry<Doctor, Double> o1, Entry<Doctor, Double> o2) {
					if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
			            return 1;  
			           }else{  
			            return -1;  
			           } 
				}
				 
			 });
			
			List<Doctor> returnDoctor = new ArrayList<Doctor>();
			for(Map.Entry<Doctor, Double> map_data : list_Data){
				returnDoctor.add(map_data.getKey());
				System.out.println("排序后=============="+map_data.getValue());
			}
			
			if(returnDoctor.size()<=0){
				map.put("status", "500");
				map.put("message", "暂无医生数据");
				map.put("data", "{}");
				return map;
			}
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			//总页数
			Integer pagecount = (returnDoctor.size()+pageSize-1)/pageSize;
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			
			List<Doctor> doctor_list = new ArrayList<Doctor>();
			
			
			if (returnDoctor.size()>0){
				if(pageNumber>=pagecount){
					doctor_list = returnDoctor.subList((pagenumber-1)*pageSize, returnDoctor.size());
				}else{
					doctor_list = returnDoctor.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			
			List<Map<String,Object>> doctor_data = new ArrayList<Map<String,Object>>();
			for(Doctor doctor : doctor_list){
				
				
				System.out.println(doctor.getId());
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
				doctor_map.put("mechanismName", list_mechanism.size()<=0?"":list_mechanism.get(0).getName());
				doctor_map.put("longitude", list_mechanism.size()<=0?Config.longitude:list_mechanism.get(0).getLongitude());
				doctor_map.put("latitude", list_mechanism.size()<=0?Config.latitude:list_mechanism.get(0).getLatitude());
				doctor_data.add(doctor_map);
			}
			
			data_map.put("doctors", doctor_data);
			map.put("status", status);
			map.put("message", message);
			map.put("data", JsonUtils.toJson(data_map));
			
			return map;
			
		}
		
		return doctorDao.findDoctorList(file);
	}



	@Override
	public Map<String, Object> webDoctorList(String scoreSort, String second,
			String distance, String longitude, String latitude,
			String pageNumbers,String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 经度 */
		longitude = longitude.equals("0")?"0":Config.longitude;
		/** 纬度 */
		latitude =  latitude.equals("0")?"0":Config.latitude;
		
		Integer pageNumber = Integer.valueOf(pageNumbers);
		Integer pageSize = Config.pageSize;//每页显示多少条
		
		//全部为空为综合排序
		if(flag.equals("1")){
			/**获取评分,诊次,距离的最大值*/
			Integer second_max = doctorDao.getMaxSecond();
			System.out.println(second_max);
			List<Doctor> all_doctor = doctorDao.findByStatus();
			Map<Doctor,Double> doctorsSort = new HashMap<Doctor,Double>();
			Map<String ,Object> data_map = new HashMap<String, Object>();
			for(Doctor doctor : all_doctor){
				Double scoreSorts = doctor.getScoreSort()*10*0.5;
				Double seconds = (doctor.getSecond()/10)*0.5;
				Double grade = scoreSorts+seconds;
				doctorsSort.put(doctor, grade);
				System.out.println("排序前==========="+grade);
			}
			
			List<Map.Entry<Doctor, Double>> list_Data = new ArrayList<Map.Entry<Doctor, Double>>(doctorsSort.entrySet());  
			Collections.sort(list_Data, new Comparator<Map.Entry<Doctor, Double>>()  {
				public int compare(Entry<Doctor, Double> o1, Entry<Doctor, Double> o2) {
					if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
			            return 1;  
			           }else{  
			            return -1;  
			           } 
				}
				 
			 });
			
			List<Doctor> returnDoctor = new ArrayList<Doctor>();
			for(Map.Entry<Doctor, Double> map_data : list_Data){
				returnDoctor.add(map_data.getKey());
				System.out.println("排序后=============="+map_data.getValue());
			}
			
			if(returnDoctor.size()<=0){
				map.put("status", "500");
				map.put("message", "暂无医生数据");
				map.put("data", "{}");
				return map;
			}
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			//总页数
			Integer pagecount = (returnDoctor.size()+pageSize-1)/pageSize;
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			
			List<Doctor> doctor_list = new ArrayList<Doctor>();
			
			
			if (returnDoctor.size()>0){
				if(pageNumber>=pagecount){
					doctor_list = returnDoctor.subList((pagenumber-1)*pageSize, returnDoctor.size());
				}else{
					doctor_list = returnDoctor.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
					 return data_map;
				}
			}
			
			List<Map<String,Object>> doctor_data = new ArrayList<Map<String,Object>>();
			for(Doctor doctor : doctor_list){
				System.out.println(doctor.getId());
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
				doctor_map.put("mechanismName", list_mechanism.get(0)==null?"":list_mechanism.get(0).getName());
				doctor_map.put("longitude", list_mechanism.get(0)==null?Config.longitude:list_mechanism.get(0).getLongitude());
				doctor_map.put("latitude", list_mechanism.get(0)==null?Config.latitude:list_mechanism.get(0).getLatitude());
				doctor_data.add(doctor_map);
			}
			
			data_map.put("doctors", doctor_data);
			map.put("status", status);
			map.put("message", message);
			map.put("data", JsonUtils.toJson(data_map));
			
			return data_map;
			
		}
		
		return doctorDao.webDoctorList(scoreSort,second,distance,longitude,latitude,pageNumber.toString(),flag);
	}



	@Override
	public List<Map<String ,Object>> filtrateDoctor(String price_min,
			String price_max, String serverProjectCategoryId,
			String doctorName, String reserveDate, String startDate, String endDate,
			String service, String sex, Integer pageNumber,String longitude,String latitude) {
		
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		List<Project> project_list = projectDao.webProjectFiltrate(price_min,price_max,service,doctorName,serverProjectCategoryId);
		List<Doctor> doctor_list = new ArrayList<Doctor>();
		
		longitude = longitude==null?Config.longitude:longitude;
		latitude = latitude==null?Config.latitude:latitude;
		
		for(Project project : project_list){
			System.out.println(project.getId());
			Boolean key = false;
			if(!StringUtils.isEmpty(sex)){
				Gender gender = Gender.valueOf(sex);
				if(!project.getDoctor().getGender().equals(gender)){
					continue;
				}
			}
			if(!StringUtils.isEmpty(reserveDate)){
				System.out.println(project.getId());
				System.out.println(project.getDoctor().getId());
				WorkDay workDay = workDayDao.getWorkDayByDoctorAndData(project.getDoctor(), DateUtil.getStringtoDate(reserveDate, "yyyy-MM-dd"));
				if(workDayDao.getWorkDayByDoctorAndData(project.getDoctor(), DateUtil.getStringtoDate(reserveDate, "yyyy-MM-dd"))==null||!workDayDao.getWorkDayByDoctorAndData(project.getDoctor(), DateUtil.getStringtoDate(reserveDate, "yyyy-MM-dd")).getWorkType().equals(WorkType.work)){
					continue;
				}
			}
			if(project.getDoctor().getIsDeleted().equals(true)){
				continue;
			}
			if(!project.getDoctor().getStatus().equals(Status.allow)){
				continue;
			}	
			if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
				for(int x = 0;;x++){
					if(DateUtil.compare_date_days(DateUtil.dataAdd(DateUtil.getStringtoDate(startDate, "yyyy-MM-dd"), x), endDate)==-1){
						break;
					}
					if(workDayDao.getWorkDayByDoctorAndData(project.getDoctor(), DateUtil.getStringtoDate(DateUtil.dataAdd(DateUtil.getStringtoDate(startDate, "yyyy-MM-dd"), x), "yyyy-MM-dd"))==null||workDayDao.getWorkDayByDoctorAndData(project.getDoctor(), DateUtil.getStringtoDate(DateUtil.dataAdd(DateUtil.getStringtoDate(startDate, "yyyy-MM-dd"), x), "yyyy-MM-dd")).getWorkType().equals(WorkType.work)){
						key=true;
						break;
					}
				}
				if(key==false){
					continue;
				}
			}
			if(!doctor_list.contains(project.getDoctor())){
				doctor_list.add(project.getDoctor());
			}
		}
		
		Map<String,Object> data_map = new HashMap<String, Object>();
		
		Integer pageSize = Config.pageSize;
		
		
		if(doctor_list.size()<=0){
			return data_list;
		}
		
		//总页数
		Integer pagecount = (doctor_list.size()+pageSize-1)/pageSize;
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;

		List<Doctor> doctor_lists = new ArrayList<Doctor>();
		
		
		if (doctor_list.size()>0){
			if(pageNumber>=pagecount){
				doctor_lists = doctor_list.subList((pagenumber-1)*pageSize, doctor_list.size());
			}else{
				doctor_lists = doctor_list.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
			}
			if (pageNumber>pagecount) {
				return data_list;
			}
		}
		
		for(Doctor doctor : doctor_lists){
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
			doctor_map.put("mechanismName", list_mechanism.get(0)==null?"":list_mechanism.get(0).getName());
			doctor_map.put("longitude", list_mechanism.get(0)==null?"":list_mechanism.get(0).getLongitude());
			doctor_map.put("latitude", list_mechanism.get(0)==null?"":list_mechanism.get(0).getLatitude());
			data_list.add(doctor_map);
		}
		
		
		return data_list;
		
	}

	@Override
	public List<Project> findByProject(Doctor doctor) {
		// TODO Auto-generated method stub
		return projectDao.getProjectsByDoctor(doctor);
	}

	
	
	
	@Override
	public Map<String, Object> findDoctorLists(String file) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(file);
		String scoreSort = json.getString("scoreSort");
		String second = json.getString("second");
		String distance = json.getString("distance");
		/** 经度 */
		double longitude = Double.valueOf((json.get("longitude").toString().equals("")?"0":json.get("longitude").toString()));
		/** 纬度 */
		double latitude =  Double.valueOf(json.get("latitude").toString().equals("")?"0":json.get("latitude").toString());
		
		Integer pageNumber = json.getInt("pageNumber");//页码
		Integer pageSize = Config.pageSize;//每页显示多少条
		
		List<DoctorCategory> category_list = doctorCategoryService.find();
		
		
		//全部为空为综合排序
		if(StringUtils.isEmpty(scoreSort)&&StringUtils.isEmpty(second)&&StringUtils.isEmpty(distance)){
			/**获取评分,诊次,距离的最大值*/
			Integer second_max = doctorDao.getMaxSecond();
			System.out.println(second_max);
			List<Doctor> all_doctor = doctorDao.findByStatus();
			
			
			
			Map<Doctor,Double> doctorsSort = new HashMap<Doctor,Double>();
			Map<String ,Object> data_map = new HashMap<String, Object>();
			for(Doctor doctor : all_doctor){
				/*if(doctor.getIsDoctorStatus()&&doctor.getStatus().equals(Status.allow)){
					Double scoreSorts = doctor.getScoreSort()*10*0.5;
					Double seconds = (doctor.getSecond()/10)*0.5;
					Double grade = scoreSorts+seconds;
					doctorsSort.put(doctor, grade);
					System.out.println("排序前==========="+grade);
				}*/
				
				//add wsr 2018-3-23 18:39:35 start
				if(doctor.getIsDoctorStatus()&&doctor.getIsAbout()){
					Double scoreSorts = doctor.getScoreSort()*10*0.5;
					Double seconds = (doctor.getSecond()/10)*0.5;
					Double grade = scoreSorts+seconds;
					doctorsSort.put(doctor, grade);
					System.out.println("排序前==========="+grade);
				}
				//add wsr 2018-3-23 18:39:35 end
			}
			
			List<Map.Entry<Doctor, Double>> list_Data = new ArrayList<Map.Entry<Doctor, Double>>(doctorsSort.entrySet());  
			Collections.sort(list_Data, new Comparator<Map.Entry<Doctor, Double>>()  {
				public int compare(Entry<Doctor, Double> o1, Entry<Doctor, Double> o2) {
					if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
			            return 1;  
			           }else{  
			            return -1;  
			           } 
				}
				 
			 });
			
			List<Doctor> returnDoctor = new ArrayList<Doctor>();
			for(Map.Entry<Doctor, Double> map_data : list_Data){
				returnDoctor.add(map_data.getKey());
				System.out.println("排序后=============="+map_data.getValue());
			}
			
			if(returnDoctor.size()<=0){
				map.put("status", "500");
				map.put("message", "暂无医生数据");
				map.put("data", "{}");
				return map;
			}
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			//总页数
			Integer pagecount = (returnDoctor.size()+pageSize-1)/pageSize;
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			
			List<Doctor> doctor_list = new ArrayList<Doctor>();
			
			
			if (returnDoctor.size()>0){
				if(pageNumber>=pagecount){
					doctor_list = returnDoctor.subList((pagenumber-1)*pageSize, returnDoctor.size());
				}else{
					doctor_list = returnDoctor.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			List<Doctor> list_list = new ArrayList<Doctor>();
			for(Doctor d : doctor_list){
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
			List<Map<String,Object>> doctor_data = new ArrayList<Map<String,Object>>();
			for(Doctor doctor : list_list){
				
				
				List<Project> project_list = findByProject(doctor);
				List<ProjectItem> projectItems = null;
				List<BigDecimal> price = new ArrayList<BigDecimal>();
				for(Project project : project_list){
					
					projectItems = projectItemDao.getProject(project);
					if(projectItems == null){
						projectItems = new ArrayList<ProjectItem>();
					}
						for (int i = 0; i < projectItems.size(); i++) {//升序排序
							
							if(projectItems.size() == 1){
								price.add(projectItems.get(0).getPrice());
							}else{
								for (int j = i + 1; j < projectItems.size(); j++) {
									if (projectItems.get(i).getPrice().compareTo(projectItems.get(j).getPrice()) == 1 ) {
	
										ProjectItem temp = projectItems.get(i);
	
										projectItems.set(i, projectItems.get(j));
	
										projectItems.set(j, temp);
	
									}
	
								}
								price.add(projectItems.get(0).getPrice());
							}
							
						}
						
					}
					//冒泡排序
					for (int i = 0; i < price.size(); i++) {
						for (int j = i + 1; j < price.size(); j++) {
							if (price.get(i).compareTo(price.get(j)) == 1 ) {

								BigDecimal temp = price.get(i);

								price.set(i, price.get(j));

								price.set(j, temp);

							}
					}
					
				}
				
				
				System.out.println(doctor.getId());
				Map<String,Object> doctor_map = new HashMap<String, Object>();
				doctor_map.put("doctorId", doctor.getId());
				doctor_map.put("doctorName", doctor.getName());
				doctor_map.put("doctorCategory", doctor.getDoctorCategory().getName());
				doctor_map.put("sex", doctor.getGender());
				doctor_map.put("introduce", doctor.getIntroduce());
				doctor_map.put("doctorLogo", doctor.getLogo());
				doctor_map.put("scoreSort", doctor.getScoreSort());
				doctor_map.put("doctorSecond", doctor.getSecond());
				if(price.size() <= 0){
					doctor_map.put("price", 0);
				}else{
					doctor_map.put("price", price.get(0));
					System.out.println(price.get(0));
				}
				
				
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
				doctor_map.put("mechanismName", list_mechanism.size()<=0?"":list_mechanism.get(0).getName());
				doctor_map.put("longitude", list_mechanism.size()<=0?Config.longitude:list_mechanism.get(0).getLongitude());
				doctor_map.put("latitude", list_mechanism.size()<=0?Config.latitude:list_mechanism.get(0).getLatitude());
				doctor_data.add(doctor_map);
			}
			data_map.put("category_list", category_list);
			data_map.put("doctors", doctor_data);
			map.put("status", status);
			map.put("message", message);
			map.put("data", JsonUtils.toJson(data_map));
			
			return map;
			
		}
		
		return doctorDao.findDoctorList(file);
	}
	
	
	@Override
	public Map<String, Object> screenLists(String file) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(file);

		/** 经度 */
		double longitude = Double.valueOf((json.get("longitude").toString().equals("")?"0":json.get("longitude").toString()));
		/** 纬度 */
		double latitude =  Double.valueOf(json.get("latitude").toString().equals("")?"0":json.get("latitude").toString());
		
		Integer pageNumber = json.getInt("pageNumber");//页码
		Integer pageSize = Config.pageSize;//每页显示多少条
			/**获取评分,诊次,距离的最大值*/
			Integer second_max = doctorDao.getMaxSecond();
			System.out.println(second_max);
			List<Doctor> all_doctor = doctorDao.findBys(file);
			
			
			
			Map<Doctor,Double> doctorsSort = new HashMap<Doctor,Double>();
			Map<String ,Object> data_map = new HashMap<String, Object>();
			for(Doctor doctor : all_doctor){
				/*if(doctor.getIsDoctorStatus()&&doctor.getStatus().equals(Status.allow)){
					Double scoreSorts = doctor.getScoreSort()*10*0.5;
					Double seconds = (doctor.getSecond()/10)*0.5;
					Double grade = scoreSorts+seconds;
					doctorsSort.put(doctor, grade);
					System.out.println("排序前==========="+grade);
				}*/
				
				//add wsr 2018-3-23 18:39:35 start
				if(doctor.getIsDoctorStatus()&&doctor.getIsAbout()){
					Double scoreSorts = doctor.getScoreSort()*10*0.5;
					Double seconds = (doctor.getSecond()/10)*0.5;
					Double grade = scoreSorts+seconds;
					doctorsSort.put(doctor, grade);
					System.out.println("排序前==========="+grade);
				}
				//add wsr 2018-3-23 18:39:35 end
			}
			
			List<Map.Entry<Doctor, Double>> list_Data = new ArrayList<Map.Entry<Doctor, Double>>(doctorsSort.entrySet());  
			Collections.sort(list_Data, new Comparator<Map.Entry<Doctor, Double>>()  {
				public int compare(Entry<Doctor, Double> o1, Entry<Doctor, Double> o2) {
					if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
			            return 1;  
			           }else{  
			            return -1;  
			           } 
				}
				 
			 });
			
			List<Doctor> returnDoctor = new ArrayList<Doctor>();
			for(Map.Entry<Doctor, Double> map_data : list_Data){
				returnDoctor.add(map_data.getKey());
				System.out.println("排序后=============="+map_data.getValue());
			}
			
			if(returnDoctor.size()<=0){
				map.put("status", "500");
				map.put("message", "暂无医生数据");
				map.put("data", "{}");
				return map;
			}
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			//总页数
			Integer pagecount = (returnDoctor.size()+pageSize-1)/pageSize;
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			
			List<Doctor> doctor_list = new ArrayList<Doctor>();
			
			
			if (returnDoctor.size()>0){
				if(pageNumber>=pagecount){
					doctor_list = returnDoctor.subList((pagenumber-1)*pageSize, returnDoctor.size());
				}else{
					doctor_list = returnDoctor.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			List<Doctor> list = new ArrayList<Doctor>();
			String mo = json.get("mode").toString();
	        Mode mode = null;
	        if(mo != null && !mo.equals("")){
	        	mode = Mode.valueOf(mo);//服务方式(到店/上门)
	        }
			//------处理方式--------
			if(mode != null){
				int num = 0;
				for(Doctor doctor : doctor_list){
					for(Project pro : doctor.getProjects()){
						if(num < 1){
							if(mode == pro.getMode()){
								num++;
								list.add(doctor);
							}
						}
						
					}
					num=0;
				}
			}else{
				list = doctor_list;
			}
			List<Doctor> d_list = new ArrayList<Doctor>();
			//------处理医师职称------
			String doctorCategoryId = json.getString("doctorCategoryId");
			if(doctorCategoryId != null && !doctorCategoryId.equals("")){
				String categoryid[] = doctorCategoryId.split(",");
				for(String id : categoryid){
					for(Doctor doctor : list){
						Long tid = Long.parseLong(id);
						if(tid == doctor.getDoctorCategory().getId()){
							d_list.add(doctor);
						}
					}
				}
			}else{
				d_list = list;
			}
			List<Doctor> list_list = new ArrayList<Doctor>();
			for(Doctor d : d_list){
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
			
			
			List<Map<String,Object>> doctor_data = new ArrayList<Map<String,Object>>();
			for(Doctor doctor : list_list){
				
				
				List<Project> project_list = findByProject(doctor);
				List<ProjectItem> projectItems = null;
				List<BigDecimal> price = new ArrayList<BigDecimal>();
				for(Project project : project_list){
					
					projectItems = projectItemDao.getProject(project);
					if(projectItems == null){
						projectItems = new ArrayList<ProjectItem>();
					}
						for (int i = 0; i < projectItems.size(); i++) {
							
							if(projectItems.size() == 1){
								price.add(projectItems.get(0).getPrice());
							}else{
								for (int j = i + 1; j < projectItems.size(); j++) {
									if (projectItems.get(i).getPrice().compareTo(projectItems.get(j).getPrice()) == 1 ) {
	
										ProjectItem temp = projectItems.get(i);
	
										projectItems.set(i, projectItems.get(j));
	
										projectItems.set(j, temp);
	
									}
	
								}
								price.add(projectItems.get(0).getPrice());
							}
							
						}
						
					}
				
					for (int i = 0; i < price.size(); i++) {
						for (int j = i + 1; j < price.size(); j++) {
							if (price.get(i).compareTo(price.get(j)) == 1 ) {

								BigDecimal temp = price.get(i);

								price.set(i, price.get(j));

								price.set(j, temp);

							}
					}
					
				}
				
				
				System.out.println(doctor.getId());
				Map<String,Object> doctor_map = new HashMap<String, Object>();
				doctor_map.put("doctorId", doctor.getId());
				doctor_map.put("doctorName", doctor.getName());
				doctor_map.put("doctorCategory", doctor.getDoctorCategory().getName());
				doctor_map.put("sex", doctor.getGender());
				doctor_map.put("introduce", doctor.getIntroduce());
				doctor_map.put("doctorLogo", doctor.getLogo());
				doctor_map.put("scoreSort", doctor.getScoreSort());
				doctor_map.put("doctorSecond", doctor.getSecond());
				if(price.size() <= 0){
					doctor_map.put("price", 0);
				}else{
					doctor_map.put("price", price.get(0));
					System.out.println(price.get(0));
				}
				
				
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
				doctor_map.put("mechanismName", list_mechanism.size()<=0?"":list_mechanism.get(0).getName());
				doctor_map.put("longitude", list_mechanism.size()<=0?Config.longitude:list_mechanism.get(0).getLongitude());
				doctor_map.put("latitude", list_mechanism.size()<=0?Config.latitude:list_mechanism.get(0).getLatitude());
				doctor_data.add(doctor_map);
			}
			
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
						/*String pr = do_map.get("price").toString();
						BigDecimal price = new BigDecimal(pr);
						System.out.println(minPrice.compareTo(price) == 1);
						System.out.println(minPrice.compareTo(price) == 0);
						System.out.println(maxPrice.compareTo(price) == -1);
						System.out.println(maxPrice.compareTo(price) == 0);
						if(minPrice.compareTo(price) == 1 && minPrice.compareTo(price) == 0 && price.compareTo(maxPrice) == -1
								|| maxPrice.compareTo(price) == 0){
							price_screen.add(do_map);
						}*/
						
						Double pr = Double.parseDouble(do_map.get("price").toString());
						Double iPrice = Double.parseDouble(minPrice.toString());
						Double aPrice = Double.parseDouble(maxPrice.toString());
						 /* int a = bigdemical.compareTo(bigdemical2)
							a = -1,表示bigdemical小于bigdemical2；
							a = 0,表示bigdemical等于bigdemical2；
							a = 1,表示bigdemical大于bigdemical2；*/
						if(pr <= aPrice && pr >= iPrice || pr == iPrice || pr == aPrice){
							price_screen.add(do_map);
						}
						
						
					}
				}else{
					price_screen = doctor_data;
				}
				
				
				
			
			data_map.put("doctors", doctor_data);
			map.put("doctors", price_screen);
			map.put("status", status);
			map.put("message", message);
			map.put("data", JsonUtils.toJson(data_map));
			
			return map;
			
	}

/*	@Override
	public Map<String, Object> doctorFiltrate(String file) {
		return doctorDao.doctorFiltrate(file);
	}

*/
	
	
}
