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
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_organization")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_organization_sequence")
public class Organization extends BaseEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4534993336970631841L;

	/**发起人姓名**/
	private String customerName;
	
	/**联系电话**/
	private String phone;
	
	/**机构名称**/
	private String organization;
	
	/**简介**/
	private String introduce;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	
}