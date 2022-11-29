/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;

/**
 * 2017-05-24 15:25:54
 * 医师
 * @author wsr
 *
 */
public interface DoctorDao extends BaseDao<Doctor, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);
	
	
	

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	
	/**
	 * 根据用户名查找医生
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Doctor findByUsername(String username);

	/**
	 * 查找医师
	 * 
	 * @return 医师，若不存在则返回null
	 */
	List<Doctor> findList(Map<String ,Object> map);

	/**
	 * 判断手机号是否存在
	 * 
	 * @param mobile
	 *            
	 * @return 手机号是否存在
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
	 * 根据姓名模糊查询医生
	 * @param name
	 * @return
	 */
	List<Doctor> findByDoctors(String name);
	
	/**
	 * 根据用户名查找医生
	 * @param name
	 * @return
	 */
	Doctor findByName(String name);
	
	
	/**
	 * 机构端医生列表
	 * @param query_map
	 * @return
	 */
	Page<Doctor> findPage(Map<String,Object> query_map);
	
	
	
	/**
	 * 获取审核通过的医生
	 * @param name
	 * @return
	 */
	List<Doctor> findByStatus();
	
	
	
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
	List<Doctor> getNameOrMobile(Map<String,Object> query_map);
	
	/**
	 * 根据姓名或电话  级别  性别  服务项目  查询医生
	 * @param query_map
	 * @return
	 */
	List<Doctor> getDoctors(Map<String, Object> query_map);
	
	
	/**
	 * 获取医生最高诊次
	 * @param query_map
	 * @return
	 */
	Integer getMaxSecond();
	
	

	/**
	 * 筛选医生
	 * @param query_map
	 * @return
	 */
	Map<String,Object> doctorFiltrate(String file);
	
	
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
	Map<String ,Object> filtrateDoctor(String price_min,String price_max,String serverProjectCategoryId,String doctorName,Date reserveDate,Date startDate,Date endDate,String service,String sex,Integer pageNumber);
	
	
	/**
	 * 医师列表(患者筛选),我写的
	 * @param file
	 * @return
	 */
	Map<String ,Object> screenLists(String file);
	
	/**
	 * 获取医生
	 * @param name
	 * @return
	 */
	List<Doctor> findBys(String file);
}