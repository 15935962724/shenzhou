/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.User;

/**
 * Service - 医生
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface DoctorService extends BaseService<Doctor, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 判断用户名是否禁用
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否禁用
	 */
	boolean usernameDisabled(String username);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 判断E-mail是否唯一
	 * 
	 * @param previousEmail
	 *            修改前E-mail(忽略大小写)
	 * @param currentEmail
	 *            当前E-mail(忽略大小写)
	 * @return E-mail是否唯一
	 */
	boolean emailUnique(String previousEmail, String currentEmail);
	
	
	
	/**
	 * 根据用户名查找医生
	 * @param username
	 * @return
	 */
	Doctor findByUsername(String username);
	

	/**
	 * 判断机构管理员是否登录
	 * 
	 * @return 机构管理员是否登录
	 */
	boolean isAuthenticated();
	
	/**
	 * 机构端登录的医生
	 * @return
	 */
	Doctor getCurrent();
	
	/**
	 * 查找医师
	 * 
	 * @return 医师，若不存在则返回null
	 */
	List<Doctor> findList(Map<String ,Object> map);
	
	/**
	 * 判断手机号是否存在
	 * 
	 * @param mobile 手机号
	 *            
	 * @return 用户名是否存在
	 */
	boolean mobileExists(String mobile);
	
	
	/**
	 * 医师列表
	 * @param file
	 * @return
	 */
	Map<String ,Object> findList(String  file);
	
	
	/**
	 * 医师列表(新)
	 * @param file
	 * @return
	 */
	Map<String ,Object> findDoctorList(String  file);

	/**
	 * 根据手机号查找医生
	 * @param mobile
	 * @return
	 */
	Doctor findByMobile(String mobile);

	/**
	 * 根据密钥查找医生
	 * @param mobile
	 * @return
	 */
	Doctor findBySafeKeyValue(String safeKeyValue);
	

	/**
	 * 根据机构获取医生
	 * @param mobile
	 * @return
	 */
	List<Doctor> findByMechanism(Mechanism mechanism);
	
	
	/**
	 * 机构端获取医生列表
	 * @param query_map
	 * @return
	 */
	Page<Doctor> findPage(Map<String, Object> query_map);
	
	
	/**
	 * 根据医生名称迷糊查询
	 * @param projectName
	 * @return
	 */
	List<Doctor> searchByName(String doctorName);
	
	
	/**
	 * 根据医生姓名或电话查找该机构下的医生
	 * @param query_map
	 * @return
	 */
	List<Doctor> getNameOrMobile(Map<String, Object> query_map) ;
	
	
	/**
	 * 筛选医生
	 * @param file
	 * @return
	 */
	Map<String ,Object> doctorFiltrate(String  file);
	
	
	/**
	 * web端医师列表
	 * @param file
	 * @return
	 */
	Map<String ,Object> webDoctorList(String scoreSort,String second,String distance,String longitude,String latitude,String pageNumbers,String flag);
	
	
	/**
	 * web端筛选医生
	 * @param file
	 * @return
	 */
	List<Map<String ,Object>> filtrateDoctor(String price_min,String price_max,String serverProjectCategoryId,String doctorName,String reserveDate,String startDate,String endDate,String service,String sex,Integer pageNumber,String longitude,String latitude);

	
	
	/**
	 * 根据医生获取项目
	 * @param mobile
	 * @return
	 */
	List<Project> findByProject(Doctor doctor);
	
	/**
	 * 医师列表(最新,我写的)
	 * @param file
	 * @return
	 */
	Map<String ,Object> findDoctorLists(String  file);
	
	
	/**
	 * 医师列表筛选(我写的)
	 * @param pageable
	 * @param mechanism
	 * @return
	 */
	Map<String,Object> screenLists(String file);
}
