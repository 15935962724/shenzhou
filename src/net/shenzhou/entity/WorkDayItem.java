/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.mail.handlers.message_rfc822;

/**
 * Entity - 工作日项
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_work_day_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_work_day_item_sequence")
public class WorkDayItem extends BaseEntity {

	private static final long serialVersionUID = -7519486823153844426L;

	/**工作时间工作类型**/
	public enum WorkDayType{
		/**
		 * 休息
		 * */
		rest,
		
		/**
		 * 锁定
		 * */
		locking,
		
		/**
		 * 预定(已有订单)
		 **/
		reserve,
		
		/**
		 * 设置的机构上班时间
		 **/
		mechanism
		
	}
	
	/**工作开始时间(上班/休息，时间)*/
	private String startTime;
	
	/**工作结束时间(下班/休息，时间)*/
	private String endTime;
	
	/**内容*/
    private String content;	
    
   /* *//**总价*//*
    private BigDecimal countPrice;
    
    *//**项目单价*//*
    private BigDecimal price;*/
//    /**总价*/
//    private BigDecimal countPrice;
//    
//    /**项目单价*/
//    private BigDecimal price;
    
    /**工作日*/
    private WorkDay workDay;

    /**工作日状态*/
    private WorkDayType workDayType;
    
    /**订单*/
    private Order order;
    
    /**患者联系人**/
    private Member member;
    
    /**机构(机构时间关联)**/
    private Mechanism mechanism;
    
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

	@JsonProperty
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	/*public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCountPrice() {
		return countPrice;
	}

	public void setCountPrice(BigDecimal countPrice) {
		this.countPrice = countPrice;
	}*/
//	public BigDecimal getPrice() {
//		return price;
//	}
//
//	public void setPrice(BigDecimal price) {
//		this.price = price;
//	}
//
//	public BigDecimal getCountPrice() {
//		return countPrice;
//	}
//
//	public void setCountPrice(BigDecimal countPrice) {
//		this.countPrice = countPrice;
//	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public WorkDay getWorkDay() {
		return workDay;
	}

	public void setWorkDay(WorkDay workDay) {
		this.workDay = workDay;
	}

	@JsonProperty
	public WorkDayType getWorkDayType() {
		return workDayType;
	}

	public void setWorkDayType(WorkDayType workDayType) {
		this.workDayType = workDayType;
	}

	@OneToOne(mappedBy = "workDayItem", fetch = FetchType.LAZY)
//	@JoinColumn(name = "orders")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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
	
//	@PreRemove
//	public void preRemove() {
////		if (this.getOrder() != null) {
////			this.getOrder().setWorkDayItem(null);
////		}
////		if (this.getOrder() != null) {
////			this.setOrder(null);
////		}
//	}

	
}
