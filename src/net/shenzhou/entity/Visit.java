/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 回访
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_visit")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_visit_sequence")
public class Visit extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7915398912648976519L;


	/**
	 * 回访方式
	 */
	public enum VisitType {

		/**电话**/
		phone,

		/**上门**/
		home,
		
		/**微信**/
		weixin
	}
	
	/**用户*/
	private Member member;
	
	/**医生**/
	private Doctor doctor;
	
	/**机构**/
	private Mechanism mechanism;
	
	/**回访日期**/
	private Date visitDate;
	
	/**回访方式**/
	private VisitType visitType;
	
	/**回访内容**/
	private String content;
	
	/**回访结果**/
	private String result;

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

	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public VisitType getVisitType() {
		return visitType;
	}

	public void setVisitType(VisitType visitType) {
		this.visitType = visitType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}