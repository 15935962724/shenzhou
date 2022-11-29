/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import net.shenzhou.entity.RecoveryPlan.CheckState;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 评估报告(病例)
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_assessreport")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_assessreport_sequence")
public class AssessReport extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5838701273362844084L;

	/**病患名称**/
	private String diseaseName;
	
	/**现况说明**/
	private String nowExplain;

	/**评估结果**/
	private String assessResult;
	
	/**康复建议**/
	private String proposal;

	/**订单**/
	private Order order;
	
	/**评估医师**/
	private Doctor doctor;
	
	/**审核医师**/
	private Doctor auditDoctor;
	
	/**康护技师(提交计划)**/
	private Doctor redoctor;
	
	/**患者**/
	private Member member;
	
	/**审核状态*/
	private CheckState checkState;
	
	/**机构**/
	private Mechanism mechanism;
	
	/**健康计划**/
	private List<RecoveryPlan> recoveryPlans = new ArrayList<RecoveryPlan>();
	
	/**康护技师**/
	private List<Doctor> recoveryDoctors = new ArrayList<Doctor>();
	
	/**授权(某个评估报告是否授权某个医生)**/
	private List<DoctorAssessReport> doctorAssessReport = new ArrayList<DoctorAssessReport>();
	
	/**评估图片**/
	private List<AssessReportImage> assessReportImages = new ArrayList<AssessReportImage>();
	
	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public String getNowExplain() {
		return nowExplain;
	}

	public void setNowExplain(String nowExplain) {
		this.nowExplain = nowExplain;
	}

	public String getAssessResult() {
		return assessResult;
	}

	public void setAssessResult(String assessResult) {
		this.assessResult = assessResult;
	}

	public String getProposal() {
		return proposal;
	}

	public void setProposal(String proposal) {
		this.proposal = proposal;
	}
	
	@OneToOne(mappedBy="assessReport", fetch = FetchType.LAZY)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getAuditDoctor() {
		return auditDoctor;
	}

	public void setAuditDoctor(Doctor auditDoctor) {
		this.auditDoctor = auditDoctor;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	@OneToMany(mappedBy = "assessReport", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RecoveryPlan> getRecoveryPlans() {
		return recoveryPlans;
	}

	public void setRecoveryPlans(List<RecoveryPlan> recoveryPlans) {
		this.recoveryPlans = recoveryPlans;
	}

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_recovery_doctor_assess_report")
	public List<Doctor> getRecoveryDoctors() {
		return recoveryDoctors;
	}

	public void setRecoveryDoctors(List<Doctor> recoveryDoctors) {
		this.recoveryDoctors = recoveryDoctors;
	}

	@OneToMany(mappedBy = "assessReport", fetch = FetchType.LAZY)
	public List<DoctorAssessReport> getDoctorAssessReport() {
		return doctorAssessReport;
	}

	public void setDoctorAssessReport(List<DoctorAssessReport> doctorAssessReport) {
		this.doctorAssessReport = doctorAssessReport;
	}

	@Valid
	@JsonProperty
	@ElementCollection
	@CollectionTable(name = "xx_assess_report_image")
	public List<AssessReportImage> getAssessReportImages() {
		return assessReportImages;
	}

	public void setAssessReportImages(List<AssessReportImage> assessReportImages) {
		this.assessReportImages = assessReportImages;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public CheckState getCheckState() {
		return checkState;
	}

	public void setCheckState(CheckState checkState) {
		this.checkState = checkState;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getRedoctor() {
		return redoctor;
	}

	public void setRedoctor(Doctor redoctor) {
		this.redoctor = redoctor;
	}
	
	
	
}

















