/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.ServerProjectCategory;

/**
 * Service - 项目
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface ProjectService extends BaseService<Project, Long> {

	Map<String ,Object> findList(String file);
	
	
	/**
	 * 机构端项目列表
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Page<Project> getPageProjects(Map<String,Object> map);
	

	/**
	 * 根据医生获取服务
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectsByDoctor(Doctor doctor);
	
	

	/**
	 * 根据医生获取服务(只获取审核成功和未删除)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectByDoctor(Doctor doctor);
	
	/**
	 * 根据医生和机构获取服务(获取全部项目)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getAllProject(Doctor doctor,Mechanism mechanism);
	
	/**
	 * 根据项目名称迷糊查询
	 * @param projectName
	 * @return
	 */
	List<Project> searchByName(String projectName);
	
	/**
	 * 机构端查找医生的项目
	 * @param query_map
	 * @return
	 */
	Page<Project> getDoctorProjects(Map<String,Object> query_map);
	
	
	/**
	 * 项目统计
	 * @param map
	 * @return
	 */
	List<Project> getProjectCharge(Map<String, Object> map);
	
	/**
	 * 根据医生和机构获取服务(只获取审核成功和未删除)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectByDoctorSucceed(Doctor doctor, Mechanism mechanism);
	
	
	/**
	 * 根据医生和机构获取服务(获取机构下的全部项目)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectByDoctor(Doctor doctor, Mechanism mechanism);
	
	
	
	/**
	 * 查询全部重复数据
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Object> getRepetitionProject();
	
	/**
	 * 分页查询去重数据
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String,Object> getPeojectList(Integer page);
	
	
	/**
	 * 获取项目列表(分页)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String,Object> getList(Integer page,String longitude, String latitude);
	
	
	/**
	 * 根据条件获取同一个服务在不同机构的产品
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectByCondition(Doctor doctor,Integer time, ServerProjectCategory serverProjectCategory);
	
	
	
	/**
	 * 根据条件获取同一个服务在不同机构的产品
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Project getProjectByCondition(Doctor doctor,Integer time, ServerProjectCategory serverProjectCategory,Mechanism mechanism,Mode mode);
	
	
	/**
	 * 获取去重后项目的数量
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Integer getProjectCount();
	
	
	/**
	 * 筛选医生
	 * @param file
	 * @return
	 */
	Map<String ,Object> projectFiltrate(String  file);
	
	
	
	/**
	 * 获取项目列表(分页)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String,Object> getList(Integer page,String longitude, String latitude,String scoreSort,String second,String distance,String price);
	
	
	/**
	 * 分页排序查询去重数据
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String,Object> getPeojectListSort(Integer page,String scoreSort , String second,String distance,String price);
	
	
	/**
	 * 根据项目类别获取价格排序项目
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectByServerProjectCategory(ServerProjectCategory serverProjectCategory);
	
	
	/**
	 * web端获取项目列表(分页)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String,Object> getWebProjectList(Integer page,String longitude, String latitude,String flag,String sort);
	
	
	/**
	 * 导出项目列表
	 * @param query_map
	 * @return
	 */
	List<Project> getDownloadList(Map<String, Object> query_map);
	
	
	
	/**
	 * 判断项目是否重复(2018)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Project getRepetitionProject(Doctor doctor, ServerProjectCategory serverProjectCategory,Mechanism mechanism);

	/**
	 * 项目列表,我写的
	 * @param file
	 * @return
	 */
	Map<String ,Object> findLists(String file);
	
	
	/**
	 * 获取项目列表筛选(我写的)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String,Object> screenLists(String file);


	Map<String, Object> getLists(Integer page, String longitude, String latitude, String scoreSort, String second,
			String distance, String price);
	
	
	/**
	 * 根据项目类别获取项目
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectByServerProjectCategorys(ServerProjectCategory serverProjectCategory);

	/**
	 * 服务项目列表和筛选
	 * @param file
	 * @return
	 */
	Map<String ,Object> findByList(String file);
}
