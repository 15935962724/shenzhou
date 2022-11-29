/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 回访信息
 * @date 2017-8-16 16:53:33
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_visit_message")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_visit_message_sequence")
public class VisitMessage extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3882048186915336377L;

	/**回访方式**/
    public enum	VisitType{
    	
    	/**电话回访**/
    	phone,
    	
    	/**上门回访**/
    	door,
    	
    	/**微信回访**/
    	weChat
    }

	/**回访时间**/
	private Date visitDate;
	
	/**回访人**/
	private Doctor doctor;
	
	/**被访患者**/
	private Member patient;
	
	/**被访患者监护人**/
	private Member member;
	
	/**机构**/
	private Mechanism mechanism;
	
	/**回访方式**/
	private VisitType visitType;
	
	/**回访内容**/
	private String message;
	
	/**回访结果**/
	private String resultMessage;
	

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Member getPatient() {
		return patient;
	}

	public void setPatient(Member patient) {
		this.patient = patient;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}
	
	public VisitType getVisitType() {
		return visitType;
	}

	public void setVisitType(VisitType visitType) {
		this.visitType = visitType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	
	

}