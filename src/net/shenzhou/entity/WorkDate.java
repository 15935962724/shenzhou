/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 机构上班时间
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_work_date")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_work_date_sequence")
public class WorkDate extends BaseEntity {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4170259236870746734L;

	/**开始时间**/
	private String startTime;
	
	/**工作结束时间*/
	private String endTime;
	
	/**机构**/
	private Mechanism mechanism;

	public WorkDate(){
		this.startTime = "09:00";
		this.endTime = "18:00";
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@OneToOne(mappedBy="workDate", fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}
	
	

}