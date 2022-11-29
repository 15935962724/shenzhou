/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @date 2017-11-30 11:18:02
 * 患者和监护人系
 * @author wsr
 */
@Entity
@Table(name = "xx_relationship")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_relationship_sequence")
public class Relationship extends OrderEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6997181920406169070L;

	
	/** 关系标题 */
	private String title;

	/** 备注 */
	private String remarks;

	/** 患者 */
	private Set<Member> patients = new HashSet<Member>();
	
	
	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@OneToMany(mappedBy = "relationship", fetch = FetchType.LAZY)
	public Set<Member> getPatients() {
		return patients;
	}
	
	public void setPatients(Set<Member> patients) {
		this.patients = patients;
	}




	

}