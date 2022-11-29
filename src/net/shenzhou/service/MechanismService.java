/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.entity.Mechanism;

/**
 * 
 * 机构
 * @author wsr
 *
 */
public interface MechanismService extends BaseService<Mechanism, Long> {

	
	/**
	 * 根据机构名字筛选
	 * @param map
	 * @return
	 */
	List<Mechanism> findList(Map<String ,Object> map);
	
	/**
	 * 机构列表,根据评分，诊次，距离
	 * @param map
	 * @return
	 */
	Map<String ,Object> findList(String file);
	
	/**
	 * 机构列表,根据评分，诊次，距离(新版本兼容)
	 * @param map
	 * @return
	 */
	Map<String ,Object> findMechanismList(String file);
	
	/**
	 * 获取该机构下的所有医生的综合平均值
	 * @param mechanism
	 * @return
	 */
    Double findObject(Mechanism mechanism);
	
	
    /**
	 * 根据机构名称迷糊查询
	 * @param projectName
	 * @return
	 */
	List<Mechanism> searchByName(String mechanismName);
	
	
	/**
	 * 分页获取平台全部机构
	 * @param page
	 * @return
	 */
	List<Mechanism> findMechanismList(Integer page);

	
	/**
	 * 筛选医生
	 * @param file
	 * @return
	 */
	Map<String ,Object> mechanismFiltrate(String  file);
	
	/**
	 * 获取当前登录的机构
	 * @return
	 */
	Mechanism  getCurrent();
	
	/**
	 * web端机构列表
	 * @return
	 */
	List<Map<String,Object>> webMechanismList(String scoreSort,String second,String longitude,String latitude,String distance,Integer pageNumber,String flag);

	
	List<Mechanism> getMechanism();
	
	
	/**
	 * 机构列表,wo 
	 * @param map
	 * @return
	 */
	List<Mechanism> find(String file);
	
	/**
	 * 通过id获取机构
	 * @param mechanismid
	 * @return
	 */
	Mechanism getMechanismInfo(Long mechanismid);
	
	/**
	 * 机构列表,根据评分，诊次，距离(新版本我写的)
	 * @param map
	 * @return
	 */
	Map<String,Object> findMechanismLists(String file);
	
	
	/**
	 * 机构列表筛选(新版本我写的)
	 * @param map
	 * @return
	 */
	List<Mechanism> screenMechanismLists(String file);
}