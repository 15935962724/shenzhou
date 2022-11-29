/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 账单
 * @date 2017-12-13 14:14:30
 * @author wsr
 * @version 1.0
 */
@Entity
@Table(name = "xx_versions")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_versions_sequence")
public class Versions extends BaseEntity {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2572962821451182618L;
	
	/**医生端版本号**/
	private String doctorVersions;

	/**用户端版本号**/
	private String memberVersions;

	public String getDoctorVersions() {
		return doctorVersions;
	}

	public void setDoctorVersions(String doctorVersions) {
		this.doctorVersions = doctorVersions;
	}

	public String getMemberVersions() {
		return memberVersions;
	}

	public void setMemberVersions(String memberVersions) {
		this.memberVersions = memberVersions;
	}
	
}