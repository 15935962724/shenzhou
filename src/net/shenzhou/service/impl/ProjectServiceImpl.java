/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Page;
import net.shenzhou.dao.DoctorDao;
import net.shenzhou.dao.ProjectDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Project.Audit;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.ProjectItem;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.ServerProjectCategory.ServeType;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.ProjectUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Service - 项目
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("projectServiceImpl")
public class ProjectServiceImpl extends BaseServiceImpl<Project, Long> implements ProjectService {

	@Resource(name = "projectDaoImpl")
	private ProjectDao projectDao;
	
	@Resource(name = "projectDaoImpl")
	public void setBaseDao(ProjectDao projectDao) {
		super.setBaseDao(projectDao);
	}

	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "doctorDaoImpl")
	private DoctorDao doctorDao;
	@Override
	public Map<String ,Object> findList(String file) {
		// TODO Auto-generated method stub
		return projectDao.findList(file);
	}

	@Override
	public Page<Project> getPageProjects(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return projectDao.getPageProjects(map);
	}

	@Override
	public List<Project> getProjectsByDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		return projectDao.getProjectsByDoctor(doctor);
	}

	@Override
	public List<Project> getProjectByDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		return projectDao.getProjectByDoctor(doctor);
	}

	@Override
	public List<Project> searchByName(String projectName) {
		// TODO Auto-generated method stub
		return projectDao.searchByName(projectName);
	}

	@Override
	public Page<Project> getDoctorProjects(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return projectDao.getDoctorProjects(query_map);
	}

	@Override
	public List<Project> getProjectCharge(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return projectDao.getProjectCharge(map);
	}

	public List<Project> getProjectByDoctorSucceed(Doctor doctor, Mechanism mechanism) {
		// TODO Auto-generated method stub
		return projectDao.getProjectByDoctorSucceed(doctor,mechanism);
	}

	@Override
	public List<Project> getProjectByDoctor(Doctor doctor, Mechanism mechanism) {
		// TODO Auto-generated method stub
		return projectDao.getProjectByDoctor(doctor,mechanism);
	}

	@Override
	public List<Object> getRepetitionProject() {
		// TODO Auto-generated method stub
		return projectDao.getRepetitionProject();
	}

	@Override
	public Map<String,Object> getPeojectList(Integer page) {
		// TODO Auto-generated method stub
		return projectDao.getPeojectList(page);
	}

	
	@Override
	public List<Project> getProjectByCondition(Doctor doctor, Integer time,
			ServerProjectCategory serverProjectCategory) {
		
		return projectDao.getProjectByCondition(doctor,time,serverProjectCategory);
	}
	
	@Override
	public Map<String,Object> getList(Integer page , String longitude, String latitude) {
		Map<String,Object> map_data = new HashMap<String, Object>();
		List<Object> object_list = new ArrayList<Object>();
		object_list =getRepetitionProject();
		List<Project> repetition_project_list = new ArrayList<Project>();
		for(int x=0;x<object_list.size();x++){
			Object obj = object_list.get(x);
			JSONArray object = JSONArray.fromObject(obj);
			System.out.print(object.get(0).toString()+"=======1====="+object.get(1).toString()+"======2======"+object.get(2).toString()+"========3===="+object.get(3).toString()+"=======4====="+object.get(4).toString()+"======5======"+object.get(5).toString()+"=====6======="+object.get(6).toString()+"======7======"+object.get(7).toString()+"======8======"+object.get(8).toString()+"=======9====="+object.get(9).toString()+"======10======"+object.get(10).toString()+"======11======"+object.get(11).toString()+"========12===="+object.get(12).toString()+"======13======"+object.get(13).toString()+"=====14======="+object.get(14).toString()+"=====15======="+object.get(15).toString()+"======16======"+object.get(16).toString()+"====17========"+object.get(17).toString()+"======18======"+object.get(18).toString()+"======19======"+object.get(19).toString()+"======20======"+object.get(20).toString()+"=====21======="+object.get(21).toString()+"=====22======="+object.get(22).toString());
			Project project = new Project();
			project.setId(Long.parseLong(object.get(0).toString()));
			project.setIntroduce(object.get(20).toString());
			project.setIntroduceImg(object.get(3).toString());
			project.setLogo((object.get(4).toString()));
			project.setName(object.get(5).toString());
			BigDecimal bd=new BigDecimal(object.get(6).toString()); 
			project.setPrice(bd);
			project.setSecond(Integer.valueOf(object.get(7).toString()));
			project.setTime(Integer.valueOf(object.get(8).toString()));
			project.setDoctor(doctorService.find(Long.valueOf(object.get(9).toString())));
			//项目里面服务方式去掉了,移到服务项(projectItem)里面去了
//			if(object.get(10).toString().equals("0")){
//				project.setMode(Mode.store);
//			}else if(object.get(10).toString().equals("1")){
//				project.setMode(Mode.home);
//			}else if(object.get(10).toString().equals("2")){
//				project.setMode(Mode.phone);
//			}else if(object.get(10).toString().equals("3")){
//				project.setMode(Mode.online);
//			}
			project.setShorthand(object.get(12).toString());
			project.setServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(14).toString())));
			if(object.get(14).toString().equals("0")){
				project.setAudit(Audit.pending);
			}else if(object.get(14).toString().equals("1")){
				project.setAudit(Audit.succeed);
			}else if(object.get(14).toString().equals("2")){
				project.setAudit(Audit.fail);
			}
			//项目里面的服务群体属性移到项目项(projectItem)里面去了
//			if(object.get(15).toString().equals("0")){
//				project.setServiceGroup(ServiceGroup.children);
//			}else if(object.get(15).toString().equals("1")){
//				project.setServiceGroup(ServiceGroup.adult);
//			}
			if(object.get(16).toString().equals("0")){
				project.setServeType(ServeType.examine);
			}else if(object.get(16).toString().equals("1")){
				project.setServeType(ServeType.assess);
			}
			System.out.println(object.get(19));
			project.setMechanism(mechanismService.find(Long.valueOf(object.get(18).toString())));
			project.setParentServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(22).toString())));
			repetition_project_list.add(project);
		}
		
		Map<String,Object> data_map = getPeojectList(page);
		List<Object> object_lists = (List<Object>) data_map.get("projectList");
		List<Project> project_list = new ArrayList<Project>();
		for(int x=0;x<object_lists.size();x++){
			Object obj = object_lists.get(x);
			JSONArray object = JSONArray.fromObject(obj);
			Project project = new Project();
			project.setId(Long.parseLong(object.get(0).toString()));
			project.setIntroduce(object.get(20).toString());
			project.setIntroduceImg(object.get(3).toString());
			project.setLogo((object.get(4).toString()));
			project.setName(object.get(5).toString());
			BigDecimal bd=new BigDecimal(object.get(6).toString()); 
			project.setPrice(bd);
			project.setSecond(Integer.valueOf(object.get(7).toString()));
			project.setTime(Integer.valueOf(object.get(8).toString()));
			project.setDoctor(doctorService.find(Long.valueOf(object.get(9).toString())));
			//项目里面服务方式去掉了,移到服务项(projectItem)里面去了
//			if(object.get(10).toString().equals("0")){
//				project.setMode(Mode.store);
//			}else if(object.get(10).toString().equals("1")){
//				project.setMode(Mode.home);
//			}else if(object.get(10).toString().equals("2")){
//				project.setMode(Mode.phone);
//			}else if(object.get(10).toString().equals("3")){
//				project.setMode(Mode.online);
//			}
			project.setShorthand(object.get(12).toString());
			project.setServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(14).toString())));
			if(object.get(14).toString().equals("0")){
				project.setAudit(Audit.pending);
			}else if(object.get(14).toString().equals("1")){
				project.setAudit(Audit.succeed);
			}else if(object.get(14).toString().equals("2")){
				project.setAudit(Audit.fail);
			}
			//项目里面的服务群体属性移到项目项(projectItem)里面去了
//			if(object.get(15).toString().equals("0")){
//				project.setServiceGroup(ServiceGroup.children);
//			}else if(object.get(15).toString().equals("1")){
//				project.setServiceGroup(ServiceGroup.adult);
//			}
			if(object.get(16).toString().equals("0")){
				project.setServeType(ServeType.examine);
			}else if(object.get(16).toString().equals("1")){
				project.setServeType(ServeType.assess);
			}
			System.out.println(object.get(19));
			project.setMechanism(mechanismService.find(Long.valueOf(object.get(18).toString())));
			project.setParentServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(22).toString())));
			project_list.add(project);
		}
		
		//project_list(去重分页好的数据)=============repetition_project_list(数据库里所有重复数据)
		List<Project> data_project = new ArrayList<Project>();
		for(Project project : project_list){
			List<Project> sort_list = new ArrayList<Project>();
			for(Project project_son : repetition_project_list){
				if(project.getParentServerProjectCategory().equals(project_son.getParentServerProjectCategory())&&project.getDoctor().equals(project_son.getDoctor())&&project.getTime().equals(project_son.getTime())){
					sort_list.add(project_son);
				}
			}
			if(sort_list.size()<=0){
				data_project.add(project);
				continue;
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("longitude", longitude);
			map.put("latitude", latitude);
			map.put("isDesc", false);
			map.put("projects", sort_list);
			List<Project> list = ProjectUtil.getOrderByDistance(map);
			data_project.add(list.get(0));
		}
		
		Integer count = getProjectCount();
		
		map_data.put("count", count);
		map_data.put("projectList", data_project);
		
		
		return map_data;
	}

	@Override
	public Project getProjectByCondition(Doctor doctor, Integer time,
			ServerProjectCategory serverProjectCategory, Mechanism mechanism,Mode mode) {
		// TODO Auto-generated method stub
		return projectDao.getProjectByCondition(doctor, time, serverProjectCategory,mechanism,mode);
	}

	@Override
	public Integer getProjectCount() {
		// TODO Auto-generated method stub
		return projectDao.getProjectCount();
	}

	@Override
	public Map<String, Object> projectFiltrate(String file) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		JSONObject json = JSONObject.fromObject(file);
		String sex = json.getString("gender");
		String doctorName = json.getString("doctorName");
		List<Project> project_list = projectDao.projectFiltrate(file);
		List<Project> projectList = new ArrayList<Project>();
		
		for(Project project : project_list){
			Boolean key = true;
			if(!StringUtils.isEmpty(sex)){
				if(!project.getDoctor().getGender().equals(Gender.valueOf(sex))){
					continue;
				}
			}
			if(project.getDoctor().getIsDeleted().equals(true)){
				continue;
			}
			if(!project.getDoctor().getStatus().equals(Status.allow)){
				continue;
			}
			if(!StringUtils.isEmpty(doctorName)){
				Doctor doctor = doctorDao.findByName(doctorName);
				if(doctor!=null){
					if(project.getDoctor()!=doctor){
						continue;
					}
				}
			}
			projectList.add(project);
		}
		
		Map<String,Object> data_map = new HashMap<String, Object>();
		
		Integer pageNumber = json.getInt("pageNumber");//页码
		Integer pageSize = Config.pageSize;
		
		String status = "200";
		String message = "第"+pageNumber+"页数据加载成功";
		
		if(projectList.size()==0){
			map.put("status", "500");
			map.put("message", "暂无项目数据");
			map.put("data", "{}");
			return map;
		}
		
		//总页数
		Integer pagecount = (projectList.size()+pageSize-1)/pageSize;
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		List<Project> project_lists = new ArrayList<Project>();
		if (projectList.size()>0){
			if(pageNumber>=pagecount){
				project_lists = projectList.subList((pagenumber-1)*pageSize, projectList.size());
			}else{
				project_lists = projectList.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
			}
			if (pageNumber>pagecount) {
				 status = "500";
				 message = "无更多数据";
			}
		}
		
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for(Project project : project_lists){
			Map<String,Object> project_map = new HashMap<String, Object>();
			
			Set<ProjectItem> projectItems = project.getProjectItems();
			BigDecimal max = new BigDecimal(0);
			BigDecimal min = new BigDecimal(0);
			for(ProjectItem projectItem : projectItems){
				min = projectItem.getPrice();
			}
			for(ProjectItem projectItem : projectItems){
				if(projectItem.getPrice().compareTo(max)==0||projectItem.getPrice().compareTo(max)==1){
					max = projectItem.getPrice();
				}
				if(projectItem.getPrice().compareTo(min)==0||projectItem.getPrice().compareTo(min)==-1){
					min = projectItem.getPrice();
				}
			}
			project_map.put("max", max);
			project_map.put("min", min);
			
			project_map.put("projectId", project.getId());
			project_map.put("projectName", project.getName());
			project_map.put("projectLogo", project.getLogo());
			project_map.put("mechanismName", project.getMechanism().getName());
			project_map.put("doctorName", project.getDoctor().getName());
			project_map.put("sex", project.getDoctor().getGender());
			project_map.put("doctorCategoryName", project.getDoctor().getDoctorCategory().getName());
			project_map.put("projectSecond", project.getSecond());
			project_map.put("projectScoreSort", project.getDoctor().getScoreSort());
			project_map.put("longitude", project.getMechanism().getLongitude());
			project_map.put("latitude", project.getMechanism().getLatitude());
			project_map.put("price", project.getPrice());
			project_map.put("time", project.getTime());
			data_list.add(project_map);
		}
		
		data_map.put("projects", data_list);
		
		map.put("status", status);
		map.put("message",message);
		map.put("data", JsonUtils.toJson(data_map));
		return map;
	}

	@Override
	public Map<String, Object> getList(Integer page, String longitude,
			String latitude, String scoreSort, String second, String distance,String price) {
		Map<String,Object> map_data = new HashMap<String, Object>();
		List<Object> object_list = new ArrayList<Object>();
		object_list =getRepetitionProject();
		List<Project> repetition_project_list = new ArrayList<Project>();
		for(int x=0;x<object_list.size();x++){
			Object obj = object_list.get(x);
			JSONArray object = JSONArray.fromObject(obj);
			System.out.print(object.get(0).toString()+"=======1====="+object.get(1).toString()+"======2======"+object.get(2).toString()+"========3===="+object.get(3).toString()+"=======4====="+object.get(4).toString()+"======5======"+object.get(5).toString()+"=====6======="+object.get(6).toString()+"======7======"+object.get(7).toString()+"======8======"+object.get(8).toString()+"=======9====="+object.get(9).toString()+"======10======"+object.get(10).toString()+"======11======"+object.get(11).toString()+"========12===="+object.get(12).toString()+"======13======"+object.get(13).toString()+"=====14======="+object.get(14).toString()+"=====15======="+object.get(15).toString()+"======16======"+object.get(16).toString()+"====17========"+object.get(17).toString()+"======18======"+object.get(18).toString()+"======19======"+object.get(19).toString()+"======20======"+object.get(20).toString()+"=====21======="+object.get(21).toString()+"=====22======="+object.get(22).toString());
			Project project = new Project();
			project.setId(Long.parseLong(object.get(0).toString()));
			project.setIntroduce(object.get(20).toString());
			project.setIntroduceImg(object.get(3).toString());
			project.setLogo((object.get(4).toString()));
			project.setName(object.get(5).toString());
			
			/****/
			Project project1 = projectDao.find(Long.parseLong(object.get(0).toString()));
			Set<ProjectItem> projectItems = project1.getProjectItems();
			BigDecimal max = new BigDecimal(0);
			BigDecimal min = new BigDecimal(0);
			Integer time = new Integer(0);
			for(ProjectItem projectItem : projectItems){
				min = projectItem.getPrice();
			}
			for(ProjectItem projectItem : projectItems){
				if(projectItem.getPrice().compareTo(max)==0||projectItem.getPrice().compareTo(max)==1){
					max = projectItem.getPrice();
				}
				if(projectItem.getPrice().compareTo(min)==0||projectItem.getPrice().compareTo(min)==-1){
					min = projectItem.getPrice();
					time = projectItem.getTime();
				}
			}
			project.setPrice(min);
			/****/
			
			project.setSecond(Integer.valueOf(object.get(7).toString()));
			project.setTime(time);
			project.setDoctor(doctorService.find(Long.valueOf(object.get(9).toString())));
			//项目里面服务方式去掉了,移到服务项(projectItem)里面去了
//			if(object.get(10).toString().equals("0")){
//				project.setMode(Mode.store);
//			}else if(object.get(10).toString().equals("1")){
//				project.setMode(Mode.home);
//			}else if(object.get(10).toString().equals("2")){
//				project.setMode(Mode.phone);
//			}else if(object.get(10).toString().equals("3")){
//				project.setMode(Mode.online);
//			}
			project.setShorthand(object.get(12).toString());
			
			project.setServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(14).toString())));
			if(object.get(14).toString().equals("0")){
				project.setAudit(Audit.pending);
			}else if(object.get(14).toString().equals("1")){
				project.setAudit(Audit.succeed);
			}else if(object.get(14).toString().equals("2")){
				project.setAudit(Audit.fail);
			}
			
			//项目里面的服务群体属性移到项目项(projectItem)里面去了
//			if(object.get(15).toString().equals("0")){
//				project.setServiceGroup(ServiceGroup.children);
//			}else if(object.get(15).toString().equals("1")){
//				project.setServiceGroup(ServiceGroup.adult);
//			}
			if(object.get(16).toString().equals("0")){
				project.setServeType(ServeType.examine);
			}else if(object.get(16).toString().equals("1")){
				project.setServeType(ServeType.assess);
			}
			System.out.println(object.get(19));
			project.setMechanism(mechanismService.find(Long.valueOf(object.get(18).toString())));
			project.setParentServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(22).toString())));
			repetition_project_list.add(project);
		}
		
		Map<String,Object> data_map = getPeojectListSort(page,scoreSort,second,distance,price);
		List<Object> object_lists = (List<Object>) data_map.get("projectList");
		List<Project> project_list = new ArrayList<Project>();
		for(int x=0;x<object_lists.size();x++){
			Object obj = object_lists.get(x);
			JSONArray object = JSONArray.fromObject(obj);
			Project project = new Project();
			project.setId(Long.parseLong(object.get(0).toString()));
			project.setIntroduce(object.get(20).toString());
			project.setIntroduceImg(object.get(3).toString());
			project.setLogo((object.get(4).toString()));
			project.setName(object.get(5).toString());
			/****/
			Project project1 = projectDao.find(Long.parseLong(object.get(0).toString()));
			Set<ProjectItem> projectItems = project1.getProjectItems();
			BigDecimal max = new BigDecimal(0);
			BigDecimal min = new BigDecimal(0);
			Integer time = new Integer(0);
			for(ProjectItem projectItem : projectItems){
				min = projectItem.getPrice();
			}
			for(ProjectItem projectItem : projectItems){
				if(projectItem.getPrice().compareTo(max)==0||projectItem.getPrice().compareTo(max)==1){
					max = projectItem.getPrice();
				}
				if(projectItem.getPrice().compareTo(min)==0||projectItem.getPrice().compareTo(min)==-1){
					min = projectItem.getPrice();
					time = projectItem.getTime();
				}
			}
			project.setPrice(min);
			
			/****/
			project.setSecond(Integer.valueOf(object.get(7).toString()));
			project.setTime(time);
			project.setDoctor(doctorService.find(Long.valueOf(object.get(9).toString())));
			//项目里面服务方式去掉了,移到服务项(projectItem)里面去了
//			if(object.get(10).toString().equals("0")){
//				project.setMode(Mode.store);
//			}else if(object.get(10).toString().equals("1")){
//				project.setMode(Mode.home);
//			}else if(object.get(10).toString().equals("2")){
//				project.setMode(Mode.phone);
//			}else if(object.get(10).toString().equals("3")){
//				project.setMode(Mode.online);
//			}
			project.setShorthand(object.get(12).toString());
			project.setServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(14).toString())));
			if(object.get(14).toString().equals("0")){
				project.setAudit(Audit.pending);
			}else if(object.get(14).toString().equals("1")){
				project.setAudit(Audit.succeed);
			}else if(object.get(14).toString().equals("2")){
				project.setAudit(Audit.fail);
			}
			//项目里面的服务群体属性移到项目项(projectItem)里面去了
//			if(object.get(15).toString().equals("0")){
//				project.setServiceGroup(ServiceGroup.children);
//			}else if(object.get(15).toString().equals("1")){
//				project.setServiceGroup(ServiceGroup.adult);
//			}
			if(object.get(16).toString().equals("0")){
				project.setServeType(ServeType.examine);
			}else if(object.get(16).toString().equals("1")){
				project.setServeType(ServeType.assess);
			}
			System.out.println(object.get(19));
			project.setMechanism(mechanismService.find(Long.valueOf(object.get(18).toString())));
			project.setParentServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(22).toString())));
			project_list.add(project);
		}
		
		//project_list(去重的数据)=============repetition_project_list(数据库里所有重复数据)
		List<Project> data_project = new ArrayList<Project>();
		for(Project project : project_list){
			List<Project> sort_list = new ArrayList<Project>();
			for(Project project_son : repetition_project_list){
				if(project.getParentServerProjectCategory().equals(project_son.getParentServerProjectCategory())&&project.getDoctor().equals(project_son.getDoctor())&&project.getTime().equals(project_son.getTime())){
					sort_list.add(project_son);
				}
			}
			if(sort_list.size()<=0){
				data_project.add(project);
				continue;
			}
			/*Map<String,Object> map = new HashMap<String, Object>();
			map.put("longitude", longitude);
			map.put("latitude", latitude);
			map.put("isDesc", false);
			map.put("projects", sort_list);
			List<Project> list = ProjectUtil.getOrderByDistance(map);
			data_project.add(list.get(0));*/
		}
		
		List<Project> projects_list = new ArrayList<Project>();
		projects_list = data_project;
		if(!StringUtils.isEmpty(distance)){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("longitude", longitude);
			map.put("latitude", latitude);
			if(distance.equals(true)){
				map.put("isDesc", true);
			}else{
				map.put("isDesc", false);
			}
			map.put("projects", data_project);
			projects_list = ProjectUtil.getOrderByDistance(map);
		}
		
		Integer count = getProjectCount();
		
		map_data.put("count", count);
		map_data.put("projectList", projects_list);
		
		
		return map_data;
	}

	@Override
	public Map<String, Object> getPeojectListSort(Integer page,String scoreSort , String second,String distance,String price) {
		// TODO Auto-generated method stub
		return projectDao.getPeojectListSort(page,scoreSort,second,distance,price);
	}

	@Override
	public List<Project> getProjectByServerProjectCategory(
			ServerProjectCategory serverProjectCategory) {
		// TODO Auto-generated method stub
		return  projectDao.getProjectByServerProjectCategory(serverProjectCategory);
	}

	@Override
	public Map<String, Object> getWebProjectList(Integer page,
			String longitude, String latitude, String flag, String sort) {
		Map<String,Object> map_data = new HashMap<String, Object>();
		List<Object> object_list = new ArrayList<Object>();
		object_list =getRepetitionProject();
		List<Project> repetition_project_list = new ArrayList<Project>();
		for(int x=0;x<object_list.size();x++){
			Object obj = object_list.get(x);
			JSONArray object = JSONArray.fromObject(obj);
			System.out.print(object.get(0).toString()+"=======1====="+object.get(1).toString()+"======2======"+object.get(2).toString()+"========3===="+object.get(3).toString()+"=======4====="+object.get(4).toString()+"======5======"+object.get(5).toString()+"=====6======="+object.get(6).toString()+"======7======"+object.get(7).toString()+"======8======"+object.get(8).toString()+"=======9====="+object.get(9).toString()+"======10======"+object.get(10).toString()+"======11======"+object.get(11).toString()+"========12===="+object.get(12).toString()+"======13======"+object.get(13).toString()+"=====14======="+object.get(14).toString()+"=====15======="+object.get(15).toString()+"======16======"+object.get(16).toString()+"====17========"+object.get(17).toString()+"======18======"+object.get(18).toString()+"======19======"+object.get(19).toString()+"======20======"+object.get(20).toString()+"=====21======="+object.get(21).toString()+"=====22======="+object.get(22).toString());
			Project project = new Project();
			project.setId(Long.parseLong(object.get(0).toString()));
			project.setIntroduce(object.get(20).toString());
			project.setIntroduceImg(object.get(3).toString());
			project.setLogo((object.get(4).toString()));
			project.setName(object.get(5).toString());
			BigDecimal bd=new BigDecimal(object.get(6).toString()); 
			project.setPrice(bd);
			project.setSecond(Integer.valueOf(object.get(7).toString()));
			project.setTime(Integer.valueOf(object.get(8).toString()));
			project.setDoctor(doctorService.find(Long.valueOf(object.get(9).toString())));
			//项目里面服务方式去掉了,移到服务项(projectItem)里面去了
//			if(object.get(10).toString().equals("0")){
//				project.setMode(Mode.store);
//			}else if(object.get(10).toString().equals("1")){
//				project.setMode(Mode.home);
//			}else if(object.get(10).toString().equals("2")){
//				project.setMode(Mode.phone);
//			}else if(object.get(10).toString().equals("3")){
//				project.setMode(Mode.online);
//			}
			project.setShorthand(object.get(12).toString());
			project.setServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(14).toString())));
			if(object.get(14).toString().equals("0")){
				project.setAudit(Audit.pending);
			}else if(object.get(14).toString().equals("1")){
				project.setAudit(Audit.succeed);
			}else if(object.get(14).toString().equals("2")){
				project.setAudit(Audit.fail);
			}
			//项目里面的服务群体属性移到项目项(projectItem)里面去了
//			if(object.get(15).toString().equals("0")){
//				project.setServiceGroup(ServiceGroup.children);
//			}else if(object.get(15).toString().equals("1")){
//				project.setServiceGroup(ServiceGroup.adult);
//			}
			if(object.get(16).toString().equals("0")){
				project.setServeType(ServeType.examine);
			}else if(object.get(16).toString().equals("1")){
				project.setServeType(ServeType.assess);
			}
			System.out.println(object.get(19));
			project.setMechanism(mechanismService.find(Long.valueOf(object.get(18).toString())));
			project.setParentServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(22).toString())));
			repetition_project_list.add(project);
		}
		
		Map<String,Object> data_map = projectDao.getWebPeojectListSort(page,flag,sort);
		List<Object> object_lists = (List<Object>) data_map.get("projectList");
		List<Project> project_list = new ArrayList<Project>();
		for(int x=0;x<object_lists.size();x++){
			Object obj = object_lists.get(x);
			JSONArray object = JSONArray.fromObject(obj);
			Project project = new Project();
			project.setId(Long.parseLong(object.get(0).toString()));
			project.setIntroduce(object.get(20).toString());
			project.setIntroduceImg(object.get(3).toString());
			project.setLogo((object.get(4).toString()));
			project.setName(object.get(5).toString());
			BigDecimal bd=new BigDecimal(object.get(6).toString()); 
			project.setPrice(bd);
			project.setSecond(Integer.valueOf(object.get(7).toString()));
			project.setTime(Integer.valueOf(object.get(8).toString()));
			project.setDoctor(doctorService.find(Long.valueOf(object.get(9).toString())));
			//项目里面服务方式去掉了,移到服务项(projectItem)里面去了
//			if(object.get(10).toString().equals("0")){
//				project.setMode(Mode.store);
//			}else if(object.get(10).toString().equals("1")){
//				project.setMode(Mode.home);
//			}else if(object.get(10).toString().equals("2")){
//				project.setMode(Mode.phone);
//			}else if(object.get(10).toString().equals("3")){
//				project.setMode(Mode.online);
//			}
			project.setShorthand(object.get(12).toString());
			project.setServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(14).toString())));
			if(object.get(14).toString().equals("0")){
				project.setAudit(Audit.pending);
			}else if(object.get(14).toString().equals("1")){
				project.setAudit(Audit.succeed);
			}else if(object.get(14).toString().equals("2")){
				project.setAudit(Audit.fail);
			}
			//项目里面的服务群体属性移到项目项(projectItem)里面去了
//			if(object.get(15).toString().equals("0")){
//				project.setServiceGroup(ServiceGroup.children);
//			}else if(object.get(15).toString().equals("1")){
//				project.setServiceGroup(ServiceGroup.adult);
//			}
			if(object.get(16).toString().equals("0")){
				project.setServeType(ServeType.examine);
			}else if(object.get(16).toString().equals("1")){
				project.setServeType(ServeType.assess);
			}
			System.out.println(object.get(19));
			project.setMechanism(mechanismService.find(Long.valueOf(object.get(18).toString())));
			project.setParentServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(22).toString())));
			project_list.add(project);
		}
		
		//project_list(去重的数据)=============repetition_project_list(数据库里所有重复数据)
		List<Project> data_project = new ArrayList<Project>();
		for(Project project : project_list){
			List<Project> sort_list = new ArrayList<Project>();
			for(Project project_son : repetition_project_list){
				if(project.getParentServerProjectCategory().equals(project_son.getParentServerProjectCategory())&&project.getDoctor().equals(project_son.getDoctor())&&project.getTime().equals(project_son.getTime())){
					sort_list.add(project_son);
				}
			}
			if(sort_list.size()<=0){
				data_project.add(project);
				continue;
			}
			/*Map<String,Object> map = new HashMap<String, Object>();
			map.put("longitude", longitude);
			map.put("latitude", latitude);
			map.put("isDesc", false);
			map.put("projects", sort_list);
			List<Project> list = ProjectUtil.getOrderByDistance(map);
			data_project.add(list.get(0));*/
		}
		
		List<Project> projects_list = new ArrayList<Project>();
		projects_list = data_project;
		if(flag.equals("4")){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("longitude", longitude);
			map.put("latitude", latitude);
			map.put("isDesc", true);
			map.put("projects", data_project);
			projects_list = ProjectUtil.getOrderByDistance(map);
		}
		
		Integer count = getProjectCount();
		
		map_data.put("count", count);
		map_data.put("projectList", projects_list);
		
		return map_data;
	}
	
	
	public List<Project> getDownloadList(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return projectDao.getDownloadList(query_map);
	}

	@Override
	public Project getRepetitionProject(Doctor doctor,
			ServerProjectCategory serverProjectCategory, Mechanism mechanism) {
		// TODO Auto-generated method stub
		return projectDao.getRepetitionProject(doctor,serverProjectCategory,mechanism);
	}

	@Override
	public List<Project> getAllProject(Doctor doctor, Mechanism mechanism) {
		// TODO Auto-generated method stub
		return projectDao.getAllProject(doctor,mechanism);
	}

	@Override
	public Map<String, Object> findLists(String file) {
		Map<String,Object> projectList_map =  projectDao.findLists(file);
		
		

		List<Project> projects_list = new ArrayList<Project>();
		projects_list = (List<Project>) projectList_map.get("list");
		 JSONObject json = JSONObject.fromObject(file);
		String distance = json.getString("distanceSort");
		String longitude = json.getString("longitude");
		String latitude = json.getString("latitude");
		if(!StringUtils.isEmpty(distance)){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("longitude", longitude);
			map.put("latitude", latitude);
			if(distance.equals(true)){
				map.put("isDesc", true);
			}else{
				map.put("isDesc", false);
			}
			map.put("projects", projects_list);
			projects_list = ProjectUtil.getOrderByDistance(map);
		}
		
		projectList_map.put("projects_list", projects_list);
		return projectList_map;
	}

	@Override
	public Map<String, Object> getLists(Integer page, String longitude,
			String latitude, String scoreSort, String second, String distance,String price) {
		Map<String,Object> map_data = new HashMap<String, Object>();
		
		//List<Project> projectList = projectDao.findLists(file);
		
		List<Object> object_list = new ArrayList<Object>();
		object_list =getRepetitionProject();
		List<Project> repetition_project_list = new ArrayList<Project>();
		for(int x=0;x<object_list.size();x++){
			Object obj = object_list.get(x);
			JSONArray object = JSONArray.fromObject(obj);
			System.out.print(object.get(0).toString()+"=======1====="+object.get(1).toString()+"======2======"+object.get(2).toString()+"========3===="+object.get(3).toString()+"=======4====="+object.get(4).toString()+"======5======"+object.get(5).toString()+"=====6======="+object.get(6).toString()+"======7======"+object.get(7).toString()+"======8======"+object.get(8).toString()+"=======9====="+object.get(9).toString()+"======10======"+object.get(10).toString()+"======11======"+object.get(11).toString()+"========12===="+object.get(12).toString()+"======13======"+object.get(13).toString()+"=====14======="+object.get(14).toString()+"=====15======="+object.get(15).toString()+"======16======"+object.get(16).toString()+"====17========"+object.get(17).toString()+"======18======"+object.get(18).toString()+"======19======"+object.get(19).toString()+"======20======"+object.get(20).toString()+"=====21======="+object.get(21).toString()+"=====22======="+object.get(22).toString());
			Project project = new Project();
			project.setId(Long.parseLong(object.get(0).toString()));
			project.setIntroduce(object.get(20).toString());
			project.setIntroduceImg(object.get(3).toString());
			project.setLogo((object.get(4).toString()));
			project.setName(object.get(5).toString());
			
			/****/
			Project project1 = projectDao.find(Long.parseLong(object.get(0).toString()));
			Set<ProjectItem> projectItems = project1.getProjectItems();
			BigDecimal max = new BigDecimal(0);
			BigDecimal min = new BigDecimal(0);
			Integer time = new Integer(0);
			for(ProjectItem projectItem : projectItems){
				min = projectItem.getPrice();
			}
			for(ProjectItem projectItem : projectItems){
				if(projectItem.getPrice().compareTo(max)==0||projectItem.getPrice().compareTo(max)==1){
					max = projectItem.getPrice();
				}
				if(projectItem.getPrice().compareTo(min)==0||projectItem.getPrice().compareTo(min)==-1){
					min = projectItem.getPrice();
					time = projectItem.getTime();
				}
			}
			project.setPrice(min);
			/****/
			
			project.setSecond(Integer.valueOf(object.get(7).toString()));
			project.setTime(time);
			project.setDoctor(doctorService.find(Long.valueOf(object.get(9).toString())));
			//项目里面服务方式去掉了,移到服务项(projectItem)里面去了
//			if(object.get(10).toString().equals("0")){
//				project.setMode(Mode.store);
//			}else if(object.get(10).toString().equals("1")){
//				project.setMode(Mode.home);
//			}else if(object.get(10).toString().equals("2")){
//				project.setMode(Mode.phone);
//			}else if(object.get(10).toString().equals("3")){
//				project.setMode(Mode.online);
//			}
			project.setShorthand(object.get(12).toString());
			
			project.setServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(14).toString())));
			if(object.get(14).toString().equals("0")){
				project.setAudit(Audit.pending);
			}else if(object.get(14).toString().equals("1")){
				project.setAudit(Audit.succeed);
			}else if(object.get(14).toString().equals("2")){
				project.setAudit(Audit.fail);
			}
			
			//项目里面的服务群体属性移到项目项(projectItem)里面去了
//			if(object.get(15).toString().equals("0")){
//				project.setServiceGroup(ServiceGroup.children);
//			}else if(object.get(15).toString().equals("1")){
//				project.setServiceGroup(ServiceGroup.adult);
//			}
			if(object.get(16).toString().equals("0")){
				project.setServeType(ServeType.examine);
			}else if(object.get(16).toString().equals("1")){
				project.setServeType(ServeType.assess);
			}
			System.out.println(object.get(19));
			project.setMechanism(mechanismService.find(Long.valueOf(object.get(18).toString())));
			project.setParentServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(22).toString())));
			repetition_project_list.add(project);
		}
		
		Map<String,Object> data_map = getPeojectListSort(page,scoreSort,second,distance,price);
		List<Object> object_lists = (List<Object>) data_map.get("projectList");
		List<Project> project_list = new ArrayList<Project>();
		for(int x=0;x<object_lists.size();x++){
			Object obj = object_lists.get(x);
			JSONArray object = JSONArray.fromObject(obj);
			Project project = new Project();
			project.setId(Long.parseLong(object.get(0).toString()));
			project.setIntroduce(object.get(20).toString());
			project.setIntroduceImg(object.get(3).toString());
			project.setLogo((object.get(4).toString()));
			project.setName(object.get(5).toString());
			/****/
			Project project1 = projectDao.find(Long.parseLong(object.get(0).toString()));
			Set<ProjectItem> projectItems = project1.getProjectItems();
			BigDecimal max = new BigDecimal(0);
			BigDecimal min = new BigDecimal(0);
			Integer time = new Integer(0);
			for(ProjectItem projectItem : projectItems){
				min = projectItem.getPrice();
			}
			for(ProjectItem projectItem : projectItems){
				if(projectItem.getPrice().compareTo(max)==0||projectItem.getPrice().compareTo(max)==1){
					max = projectItem.getPrice();
				}
				if(projectItem.getPrice().compareTo(min)==0||projectItem.getPrice().compareTo(min)==-1){
					min = projectItem.getPrice();
					time = projectItem.getTime();
				}
			}
			project.setPrice(min);
			
			/****/
			project.setSecond(Integer.valueOf(object.get(7).toString()));
			project.setTime(time);
			project.setDoctor(doctorService.find(Long.valueOf(object.get(9).toString())));
			//项目里面服务方式去掉了,移到服务项(projectItem)里面去了
//			if(object.get(10).toString().equals("0")){
//				project.setMode(Mode.store);
//			}else if(object.get(10).toString().equals("1")){
//				project.setMode(Mode.home);
//			}else if(object.get(10).toString().equals("2")){
//				project.setMode(Mode.phone);
//			}else if(object.get(10).toString().equals("3")){
//				project.setMode(Mode.online);
//			}
			project.setShorthand(object.get(12).toString());
			project.setServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(14).toString())));
			if(object.get(14).toString().equals("0")){
				project.setAudit(Audit.pending);
			}else if(object.get(14).toString().equals("1")){
				project.setAudit(Audit.succeed);
			}else if(object.get(14).toString().equals("2")){
				project.setAudit(Audit.fail);
			}
			//项目里面的服务群体属性移到项目项(projectItem)里面去了
//			if(object.get(15).toString().equals("0")){
//				project.setServiceGroup(ServiceGroup.children);
//			}else if(object.get(15).toString().equals("1")){
//				project.setServiceGroup(ServiceGroup.adult);
//			}
			if(object.get(16).toString().equals("0")){
				project.setServeType(ServeType.examine);
			}else if(object.get(16).toString().equals("1")){
				project.setServeType(ServeType.assess);
			}
			System.out.println(object.get(19));
			project.setMechanism(mechanismService.find(Long.valueOf(object.get(18).toString())));
			project.setParentServerProjectCategory(serverProjectCategoryService.find(Long.valueOf(object.get(22).toString())));
			project_list.add(project);
		}
		

		
		List<Project> projects_list = new ArrayList<Project>();
		projects_list = project_list;
		if(!StringUtils.isEmpty(distance)){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("longitude", longitude);
			map.put("latitude", latitude);
			if(distance.equals(true)){
				map.put("isDesc", true);
			}else{
				map.put("isDesc", false);
			}
			map.put("projects", projects_list);
			projects_list = ProjectUtil.getOrderByDistance(map);
		}
		
		Integer count = getProjectCount();
		
		map_data.put("count", count);
		map_data.put("projectList", projects_list);
		
		
		return map_data;
	}
	
	
	
	@Override
	public Map<String, Object> screenLists(String file) {
		Map<String,Object> projectList_map =  projectDao.screenLists(file);
		List<Project> projects_list = new ArrayList<Project>();
		projects_list = (List<Project>) projectList_map.get("list");
		projectList_map.put("projects_list", projects_list);
		return projectList_map;
	}
	
	@Override
	public List<Project> getProjectByServerProjectCategorys(
			ServerProjectCategory serverProjectCategory) {
		// TODO Auto-generated method stub
		return  projectDao.getProjectByServerProjectCategorys(serverProjectCategory);
	}
	
	
	@Override
	public Map<String ,Object> findByList(String file) {
		// TODO Auto-generated method stub
		Map<String ,Object> map = new HashMap<String, Object>();
		Map<String ,Object> map1 = new HashMap<String, Object>();
		try {
			map = projectDao.findByList(file);
			List<Project> list = (List<Project>) map.get("list");
			map1.put("projects", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map1;
	}
}

