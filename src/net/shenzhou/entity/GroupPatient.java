/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 分组患者
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_group_patient")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_group_patient_sequence")
public class GroupPatient extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8703969159208429024L;

	/**患者**/
	private Member patientMember ;
	
	/**医生机构**/
	private DoctorMechanismRelation doctorMechanismRelation ;
	
	/**分组**/
	private PatientGroup patientGroup ;

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getPatientMember() {
		return patientMember;
	}

	public void setPatientMember(Member patientMember) {
		this.patientMember = patientMember;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public DoctorMechanismRelation getDoctorMechanismRelation() {
		return doctorMechanismRelation;
	}

	public void setDoctorMechanismRelation(
			DoctorMechanismRelation doctorMechanismRelation) {
		this.doctorMechanismRelation = doctorMechanismRelation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public PatientGroup getPatientGroup() {
		return patientGroup;
	}

	public void setPatientGroup(PatientGroup patientGroup) {
		this.patientGroup = patientGroup;
	}
}