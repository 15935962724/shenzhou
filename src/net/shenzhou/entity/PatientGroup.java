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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 患者分类
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_patient_group")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_patient_group_sequence")
public class PatientGroup extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7741881568517753227L;

	/**分组名称**/
	private String groupName;
	
	/**治疗人姓名**/
	private String memberName;
	
	/**分组人数**/
	private Integer sum;

	/**医生机构**/
	private DoctorMechanismRelation doctorMechanismRelation;
	
	/**分组内患者**/
	private Set<Member> patientMembers = new HashSet<Member>();

	/**患者和医生患者分组关系**/
	private List<GroupPatient> groupPatients = new ArrayList<GroupPatient>();
	
	@JsonProperty
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@JsonProperty
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@JsonProperty
	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}
	
	@ManyToMany(mappedBy = "patientGroups", fetch = FetchType.LAZY)
	public Set<Member> getPatientMembers() {
		return patientMembers;
	}

	public void setPatientMembers(Set<Member> patientMembers) {
		this.patientMembers = patientMembers;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public DoctorMechanismRelation getDoctorMechanismRelation() {
		return doctorMechanismRelation;
	}

	public void setDoctorMechanismRelation(
			DoctorMechanismRelation doctorMechanismRelation) {
		this.doctorMechanismRelation = doctorMechanismRelation;
	}

	@OneToMany(mappedBy = "patientGroup", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<GroupPatient> getGroupPatients() {
		return groupPatients;
	}

	public void setGroupPatients(List<GroupPatient> groupPatients) {
		this.groupPatients = groupPatients;
	}

	
	
}