/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 患者在机构的状态
 * @date 2018-3-23 15:38:14
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_patient_mechanism")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_patient_mechanism_sequence")
public class PatientMechanism extends BaseEntity {


	private static final long serialVersionUID = 1155531743023107759L;


	/**康护状态**/
    public enum	HealthType{
    	
    	/**在康护**/
    	health,
    	
    	/**挂起**/
    	hang,
    	
    	/**休疗程**/
    	hughCourse,
    	
    	/**已康复**/
    	alreadyRecovery
    	
    }

	/**患者**/
	private Member patient;
	
	/**机构**/
	private Mechanism mechanism;
	
	/**状态**/
	private HealthType healthType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( nullable = false, updatable = false)
	public Member getPatient() {
		return patient;
	}

	public void setPatient(Member patient) {
		this.patient = patient;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( nullable = false, updatable = false)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	public HealthType getHealthType() {
		return healthType;
	}

	public void setHealthType(HealthType healthType) {
		this.healthType = healthType;
	}
	
	
	
}

    
    
