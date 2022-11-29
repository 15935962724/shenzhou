/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import net.shenzhou.interceptor.UserInterceptor;

/**
 * Entity - 医生机构关系表
 * fl_2017/9/11
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_doctor_mechanism_relation")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_doctor_mechanism_relation_sequence")
public class DoctorMechanismRelation extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2220735486145749715L;

	/**
	 * 审核状态
	 *
	 */
	public enum Audit{
		
		/**待审核*/
		pending,
		
		/**审核通过*/
		succeed,
		
		/**审核未通过**/
		fail,
		
		/**取消申请**/
		cancel
		
	}
	
	/**医生**/
	private Doctor doctor;
	
	/**机构**/
	private Mechanism mechanism;
	
	/**审核状态**/
	private Audit audit;
	
	/** 是否启用  true*/
	private Boolean isEnabled;
	
	/**是否启用说明**/
	private String isEnabledExplain;
	
	/** 是否内置   false*/
	private Boolean isSystem;
	
	/**审核说明**/
	private String statusExplain;
	
	/**医生当前选择的机构(默认机构)**/
	private Boolean defaultMechanism;
	
	/**是否开通预约**/
	private Boolean isAbout;
	
	/**提成占比**/
	private WorkTarget workTarget;
	
	/**职级**/
	private DoctorCategory doctorCategory;
	
	/**该医生所在该机构下的角色**/
	private Set<MechanismRole> mechanismroles = new HashSet<MechanismRole>();
	
	/**该医生所在机构下的项目**/
	private Set<Project> projects = new HashSet<Project>();
	
	/**医生在当前机构的患者分类**/
	private List<PatientGroup> patientGroups = new ArrayList<PatientGroup>();
	
	/**患者和医生机构关系**/
	private List<GroupPatient> groupPatients = new ArrayList<GroupPatient>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getIsEnabledExplain() {
		return isEnabledExplain;
	}

	public void setIsEnabledExplain(String isEnabledExplain) {
		this.isEnabledExplain = isEnabledExplain;
	}

	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	public String getStatusExplain() {
		return statusExplain;
	}

	public void setStatusExplain(String statusExplain) {
		this.statusExplain = statusExplain;
	}

	public Boolean getDefaultMechanism() {
		return defaultMechanism;
	}

	public void setDefaultMechanism(Boolean defaultMechanism) {
		this.defaultMechanism = defaultMechanism;
	}

	public Boolean getIsAbout() {
		return isAbout;
	}

	public void setIsAbout(Boolean isAbout) {
		this.isAbout = isAbout;
	}

	@Embedded
	public WorkTarget getWorkTarget() {
		return workTarget;
	}

	public void setWorkTarget(WorkTarget workTarget) {
		this.workTarget = workTarget;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	public DoctorCategory getDoctorCategory() {
		return doctorCategory;
	}

	public void setDoctorCategory(DoctorCategory doctorCategory) {
		this.doctorCategory = doctorCategory;
	}

	@OneToMany(mappedBy = "doctorMechanismRelation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<PatientGroup> getPatientGroups() {
		return patientGroups;
	}

	public void setPatientGroups(List<PatientGroup> patientGroups) {
		this.patientGroups = patientGroups;
	}
	

	@OneToMany(mappedBy = "doctorMechanismRelation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)	
	public List<GroupPatient> getGroupPatients() {
		return groupPatients;
	}

	public void setGroupPatients(List<GroupPatient> groupPatients) {
		this.groupPatients = groupPatients;
	}

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_doctor_mechanism_role")
	public Set<MechanismRole> getMechanismroles() {
		return mechanismroles;
	}

	public void setMechanismroles(Set<MechanismRole> mechanismroles) {
		this.mechanismroles = mechanismroles;
	}

	@OneToMany(mappedBy = "doctorMechanismRelation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	
	@Transient
	public String getMechanismRoleName(){
		for (MechanismRole mechanismRole : this.getMechanismroles()) {
			return mechanismRole.getName();
		}
		return "暂无角色";
	}
	
	
	
	
}
