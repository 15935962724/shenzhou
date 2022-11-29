/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import net.shenzhou.entity.DoctorMechanismRelation.Audit;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 机构操作记录
 * @date 2018-3-5 16:50:31
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_mechanism_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_mechanism_log_sequence")
public class MechanismLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5954158117590390965L;

	/** 动作*/
	private String action;
	
	/** 备注 */
	private String remark;

	/** 操作员 */
	private Admin operator;
	
	/** 机构**/
	private Mechanism mechanism;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}
	
	
	
}
