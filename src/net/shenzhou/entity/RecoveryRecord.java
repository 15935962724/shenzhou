/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 康护记录
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_recoveryrecord")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_recoveryrecord_sequence")
public class RecoveryRecord extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 746946432518476251L;

	/**康护时间**/
	private String recoveryData;
	
	/**康护内容**/
	private String recoveryContent;

	/**效果总结**/
	private String effect;

	/**康护图片**/
	private List<RecoveryRecordImage> recoveryRecordImages = new ArrayList<RecoveryRecordImage>();
	
	/**康护计划**/
	private RecoveryPlan recoveryPlan;
	
	/**患者**/
	private Member patient;
	
	/**机构**/
	private Mechanism mechanism;
	
	/**订单**/
	private Order order;
	
	@JsonProperty
	public String getRecoveryData() {
		return recoveryData;
	}

	public void setRecoveryData(String recoveryData) {
		this.recoveryData = recoveryData;
	}

	@JsonProperty
	public String getRecoveryContent() {
		return recoveryContent;
	}

	public void setRecoveryContent(String recoveryContent) {
		this.recoveryContent = recoveryContent;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}
	
	@Valid
	@ElementCollection
	@CollectionTable(name = "xx_recovery_record_image")
	public List<RecoveryRecordImage> getRecoveryRecordImages() {
		return recoveryRecordImages;
	}

	public void setRecoveryRecordImages(
			List<RecoveryRecordImage> recoveryRecordImages) {
		this.recoveryRecordImages = recoveryRecordImages;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public RecoveryPlan getRecoveryPlan() {
		return recoveryPlan;
	}

	public void setRecoveryPlan(RecoveryPlan recoveryPlan) {
		this.recoveryPlan = recoveryPlan;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getPatient() {
		return patient;
	}

	public void setPatient(Member patient) {
		this.patient = patient;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}
	
	@OneToOne(mappedBy="recoveryRecord", fetch = FetchType.LAZY)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}

















