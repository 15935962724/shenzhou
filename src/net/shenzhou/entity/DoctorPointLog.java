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
 * Entity - 医生积分日志
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_doctor_point_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_doctor_point_log_sequence")
public class DoctorPointLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3173562508594388973L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 增加 */
		add,

		/** 减少 */
		decrease

	};
	
	/**
	 * 来源
	 */
	public enum Genre {

		/** 用户邀请用户获取积分 */
		memberToMember,

		/** 医生邀请用户获取积分 */
		doctorToMember,

		/** 医生邀请医生获取积分 */
		doctorToDoctor,
		
	};

	/** 类型 */
	private Type type;

	/** 积分变化值 */
	private Long pointChange;

	/** 机构 */
	private Mechanism mechanism;

	/** 医生 */
	private Doctor doctor;

	/** 备注 */
	private String comment;

	/**积分变化原因**/
	private Genre genre;
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Long getPointChange() {
		return pointChange;
	}

	public void setPointChange(Long pointChange) {
		this.pointChange = pointChange;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

}

















