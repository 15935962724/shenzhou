/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 康复计划
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_recoveryplan")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_recoveryplan_sequence")
public class RecoveryPlan extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7773808561450892066L;
	
	/**审核状态**/
	public enum CheckState {

		/** 待审核 */
		waitCheck,
		
		/** 审核成功 */
		succeed,

		/** 审核失败 */
		defeated 
	}
	
	/**工作开始时间*/
	private String startTime;
	
	/**工作结束时间*/
	private String endTime;
	
	/**短期目标**/
	private String shortTarget;
	
	/**长期目标**/
	private String longTarget;
	
	/**康复项目(该字段暂时废弃 2017-9-27 10:27:14 wsr)**/
	private String recoveryProject;

	/**疗效总结**/
	private String summary;
	
	/**审核状态*/
	private CheckState checkState;
	
	/**评估报告**/
	private AssessReport assessReport;
	
	/**康护技师**/
	private Doctor recoveryDoctor;
	
	/**患者**/
	private Member patient;
	
	/**机构**/
	private Mechanism mechanism;
	
	/**训练内容**/
	private List<DrillContent> drillContent = new ArrayList<DrillContent>();
	
	/**康护记录**/
	private List<RecoveryRecord> recoveryRecordList = new ArrayList<RecoveryRecord>();
	
	@JsonProperty
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@JsonProperty
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@JsonProperty
	public String getShortTarget() {
		return shortTarget;
	}

	public void setShortTarget(String shortTarget) {
		this.shortTarget = shortTarget;
	}

	@JsonProperty
	public String getLongTarget() {
		return longTarget;
	}

	public void setLongTarget(String longTarget) {
		this.longTarget = longTarget;
	}
	
	@JsonProperty
	public String getRecoveryProject() {
		return recoveryProject;
	}

	public void setRecoveryProject(String recoveryProject) {
		this.recoveryProject = recoveryProject;
	}

	@JsonProperty
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@JsonProperty
	@Enumerated
	public CheckState getCheckState() {
		return checkState;
	}

	public void setCheckState(CheckState checkState) {
		this.checkState = checkState;
	}
	
	@OneToMany(mappedBy = "recoveryplan", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<DrillContent> getDrillContent() {
		return drillContent;
	}

	public void setDrillContent(List<DrillContent> drillContent) {
		this.drillContent = drillContent;
	}
	
	@JsonProperty
	@OneToMany(mappedBy = "recoveryPlan", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RecoveryRecord> getRecoveryRecordList() {
		return recoveryRecordList;
	}

	public void setRecoveryRecordList(List<RecoveryRecord> recoveryRecordList) {
		this.recoveryRecordList = recoveryRecordList;
	}
	
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public AssessReport getAssessReport() {
		return assessReport;
	}

	public void setAssessReport(AssessReport assessReport) {
		this.assessReport = assessReport;
	}
	
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getRecoveryDoctor() {
		return recoveryDoctor;
	}

	public void setRecoveryDoctor(Doctor recoveryDoctor) {
		this.recoveryDoctor = recoveryDoctor;
	}

	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Member getPatient() {
		return patient;
	}

	public void setPatient(Member patient) {
		this.patient = patient;
	}

	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	
}