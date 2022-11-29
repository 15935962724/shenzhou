/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.CertificatesDao;
import net.shenzhou.entity.Certificates;

import org.springframework.stereotype.Repository;

/**
 * 机构证件
 * 2017-7-13 14:04:41
 * @author 2017-7-13 14:04:46
 */
@Repository("certificatesDaoImpl")
public class CertificatesDaoImpl extends BaseDaoImpl<Certificates, Long> implements CertificatesDao {

}