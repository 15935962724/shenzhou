/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.Project.Mode;

/**
 * 项目
 * @author wsr
 *
 */
public interface ProjectDao extends BaseDao<Project, Long> {

	/**
	 * 项目列表
	 * @param file
	 * @return
	 */
	Map<String ,Object> findList(String file);
	
	/**
	 * 机构端项目列表
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Page<Project> getPageProjects(Map<String,Object> map);
	
	/**
	 * 模糊查找项目
	 * @param name
	 * @return
	 */
	List<Project> findByProjects(String name);
	
	
	/**
	 * 根据医生获取服务
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectsByDoctor(Doctor doctor);
	
	/**
	 * 根据医生和机构获取服务(获取全部项目)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getAllProject(Doctor doctor,Mechanism mechanism);
	
	
	/**
	 * 根据项目类别获取项目
	 * @param serverProjectCategory
	 * @return
	 */
	List<Project> getProjects(ServerProjectCategory serverProjectCategory);
	
	
	/**
	 * 根据医生获取服务(只获取审核成功和未删除)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectByDoctor(Doctor doctor);
	
	
	
	/**
	 * 根据项目名称迷糊查询
	 * @param projectName
	 * @return
	 */
	List<Project> searchByName(String projectName);
	
	
	/**
	 * 机构端获取医生的项目
	 * @param map
	 * @return
	 */
	Page<Project> getDoctorProjects(Map<String,Object> query_map);
	
	/**
	 * 项目统计
	 * @param map
	 * @return
	 */
	List<Project> getProjectCharge( Map<String,Object> map);
	
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
	 * 筛选项目(筛选医生用的中间接口)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> projectFiltrate(String file);
	
	
	
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
	 * 导出项目
	 * @param query_map
	 * @return
	 */
	List<Project> getDownloadList(Map<String,Object> query_map);
	
	/**
	 * web端筛选项目(筛选医生用的中间接口)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> webProjectFiltrate(String price_min,String price_max,String mode,String doctorName,String serverProjectCategoryId);
	
	
	
	/**
	 * web分页排序查询去重数据
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String,Object> getWebPeojectListSort(Integer page,String flag,String sort);
	
	
	
	/**
	 * 判断项目是否重复(2018)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Project getRepetitionProject(Doctor doctor, ServerProjectCategory serverProjectCategory,Mechanism mechanism);
	
	
	
	
	/**
	 * 根据医生机构和项目分类获取服务(只获取审核成功和未删除)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectByServerProjectCategory(Doctor doctor, Mechanism mechanism,ServerProjectCategory serverProjectCategory);
	
	
	/**
	 * 项目列表,我写的
	 * @param file
	 * @return
	 */
	Map<String ,Object> findLists(String file);
	
	
	/**
	 * 项目列表(筛选),我写的
	 * @param file
	 * @return
	 */
	Map<String ,Object> screenLists(String file);
	
	/**
	 * 根据项目类别获取项目
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	List<Project> getProjectByServerProjectCategorys(ServerProjectCategory serverProjectCategory);
	
	
	/**
	 * 项目列表和筛选
	 * @param file
	 * @return
	 */
	Map<String ,Object> findByList(String file);
}
