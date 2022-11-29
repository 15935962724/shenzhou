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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import net.shenzhou.entity.GrantRight.WhetherAllow;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 医师和评估报告   授权
 * 2017-6-13 18:22:00
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_doctor_assess_report")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_doctor_assess_report_sequence")
public class DoctorAssessReport extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -401307273829224748L;

	/**医师**/
	private Doctor doctor;
	
	/**评估报告(病例)**/
	private AssessReport assessReport;

	/**是否授权**/
	private WhetherAllow whetherAllow;
	
	/** 患者*/
	private Member patientMember;
	
	/**备注**/
    private	String remarks;


	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false,updatable = false)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false,updatable = false)
	public AssessReport getAssessReport() {
		return assessReport;
	}

	public void setAssessReport(AssessReport assessReport) {
		this.assessReport = assessReport;
	}

	@JsonProperty
	@JoinColumn(nullable = false)
	public WhetherAllow getWhetherAllow() {
		return whetherAllow;
	}

	public void setWhetherAllow(WhetherAllow whetherAllow) {
		this.whetherAllow = whetherAllow;
	}

	
    @JsonProperty
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false,updatable = false)
	public Member getPatientMember() {
		return patientMember;
	}

	public void setPatientMember(Member patientMember) {
		this.patientMember = patientMember;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}

















