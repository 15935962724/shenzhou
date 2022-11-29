/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 经营模式
 * 2017-07-19 14:36:22
 * @author Administrator
 *
 */
@Entity
@Table(name = "xx_management")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_management_sequence")
public class Management extends BaseEntity {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4509841634860186773L;

	/**名称**/
	private String name;
	
	/**介绍**/
	private String introduce;

	private Set<Mechanism> mechanisms = new HashSet<Mechanism>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_management_mechanism")
	public Set<Mechanism> getMechanisms() {
		return mechanisms;
	}

	public void setMechanisms(Set<Mechanism> mechanisms) {
		this.mechanisms = mechanisms;
	}
	
	
}