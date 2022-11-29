/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 工作日
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_workDay")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_workDay_sequence")
public class WorkDay extends BaseEntity {

	private static final long serialVersionUID = -1307743303786909390L;

	/**工作状态*/
	public enum WorkType{
		/**工作*/
		work,
		
		/**休息*/
		rest
		
	}
	
	/** 工作日 */
	private Date workDayDate;

	/**工作开始时间(上班/休息，时间)*/
	private String startTime;
	
	/**工作结束时间(下班/休息，时间)*/
	private String endTime;
	
	/**医师**/
    private Doctor doctor;
    
    /**工作状态*/
    private WorkType workType;
    
    /**是否排班*/
    private Boolean isArrange;
    
    /**工作日具体安排*/
    private List<WorkDayItem> workDayItems = new ArrayList<WorkDayItem>();
    
    @JsonProperty
    @Temporal(TemporalType.DATE)
	public Date getWorkDayDate() {
		return workDayDate;
	}

	public void setWorkDayDate(Date workDayDate) {
		this.workDayDate = workDayDate;
	}

	@JsonProperty
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@JsonProperty
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@JsonProperty
	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}

	
	@JsonProperty
	public Boolean getIsArrange() {
		return isArrange;
	}

	public void setIsArrange(Boolean isArrange) {
		this.isArrange = isArrange;
	}

	@OneToMany(mappedBy = "workDay", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<WorkDayItem> getWorkDayItems() {
		return workDayItems;
	}

	public void setWorkDayItems(List<WorkDayItem> workDayItems) {
		this.workDayItems = workDayItems;
	}


}