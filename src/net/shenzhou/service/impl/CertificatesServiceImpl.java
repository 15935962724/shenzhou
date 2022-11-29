/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.CertificatesDao;
import net.shenzhou.entity.Ad;
import net.shenzhou.entity.Certificates;
import net.shenzhou.service.CertificatesService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 机构证件
 * 2017-7-13 14:07:42
 * @author wsr
 *
 */
@Service("certificatesServiceImpl")
public class CertificatesServiceImpl extends BaseServiceImpl<Certificates, Long> implements CertificatesService {

	@Resource(name = "certificatesDaoImpl")
	public void setBaseDao(CertificatesDao certificatesDao) {
		super.setBaseDao(certificatesDao);
	}


}