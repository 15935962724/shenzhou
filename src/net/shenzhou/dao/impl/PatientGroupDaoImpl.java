/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.PatientGroupDao;
import net.shenzhou.entity.PatientGroup;

import org.springframework.stereotype.Repository;
/**
 * Dao - 患者分类
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("patientGroupDaoImpl")
public class PatientGroupDaoImpl extends BaseDaoImpl<PatientGroup, Long> implements PatientGroupDao {
}