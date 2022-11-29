/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 机构角色
 * @author wsr
 * @date 2017-10-31 09:42:47
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_mechanism_role")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_mechanism_role_sequence")
public class MechanismRole extends BaseEntity {


	 /** */
	private static final long serialVersionUID = -752114901247548240L;

	/** 名称 */
	private String name;

	/** 是否内置 */
	private Boolean isSystem;

	/** 描述 */
	private String description;

	/** 机构 */
	private Mechanism mechanism;
	
	/** 权限 */
	private List<String> authorities = new ArrayList<String>();

	/** 机构管理员 */
	private Set<User> users = new HashSet<User>();
	
	/** 机构管理员(医生所在的机构管理员) */
	private Set<DoctorMechanismRelation> doctorMechanismRelations = new HashSet<DoctorMechanismRelation>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取是否内置
	 * 
	 * @return 是否内置
	 */
	@Column(nullable = false, updatable = false)
	public Boolean getIsSystem() {
		return isSystem;
	}

	/**
	 * 设置是否内置
	 * 
	 * @param isSystem
	 *            是否内置
	 */
	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	/**
	 * 获取描述
	 * 
	 * @return 描述
	 */
	@Length(max = 200)
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * 
	 * @param description
	 *            描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	/**
	 * 获取权限
	 * 
	 * @return 权限
	 */
	@ElementCollection
	@CollectionTable(name = "xx_mechanism_role_authority")
	public List<String> getAuthorities() {
		return authorities;
	}

	/**
	 * 设置权限
	 * 
	 * @param authorities
	 *            权限
	 */
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	

	/**
	 * 获取管理员
	 * 
	 * @return 管理员
	 */
	@ManyToMany(mappedBy = "mechanismroles", fetch = FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * 设置管理员
	 * 
	 * @param users
	 *            管理员
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	
	@ManyToMany(mappedBy = "mechanismroles", fetch = FetchType.LAZY)
	public Set<DoctorMechanismRelation> getDoctorMechanismRelations() {
		return doctorMechanismRelations;
	}

	public void setDoctorMechanismRelations(
			Set<DoctorMechanismRelation> doctorMechanismRelations) {
		this.doctorMechanismRelations = doctorMechanismRelations;
	}

	
	

}