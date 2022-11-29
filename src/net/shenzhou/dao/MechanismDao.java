/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.entity.Mechanism;

/**
 * 2017-05-24 16:21:54
 * 机构
 * @author wsr
 *
 */
public interface MechanismDao extends BaseDao<Mechanism, Long> {
	
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
	 * 机构列表,筛选(新版本兼容)
	 * @param map
	 * @return
	 */
	Map<String ,Object> screenMechanismLists(String file);
	
	/**
	 * 获取该机构下的所有医生的平均值
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
	 * @param projectName
	 * @return
	 */
	List<Mechanism> findMechanismList(Integer page);
	
	
	/**
	 * web端机构列表
	 * @return
	 */
	List<Map<String,Object>> webMechanismList(String scoreSort,String second,String longitude,String latitude,String distance,Integer pageNumber,String flag);

	/**
	 * 获取全部机构
	 * @return
	 */
	List<Mechanism> getMechanism();
	
	
	/**
	 * 机构列表,wo 
	 * @param map
	 * @return
	 */
	List<Mechanism> find(String file);
	
	
}