/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import net.shenzhou.entity.DoctorMechanismRelation.Audit;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 机构
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_mechanism_setup")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_mechanism_setup_sequence")
public class MechanismSetup extends BaseEntity {

	
	private static final long serialVersionUID = 6151146995995756469L;
	
	
	/**收费标准(收费类型)**/
    public enum	ChargeType{
    	
    	/**课节**/
    	course,
    	
    	/**分钟**/
    	minute
    }
	

	/**接单类型**/
    public enum	OrderType{
    	
    	/**自动**/
    	automatic,
    	
    	/**手动**/
    	manual
    }
    
    /**绩效类型**/
    public enum	AchievementsType{
    	
    	/**项目收入固定提成比例**/
    	fixedProportion,
    	
    	/**工作目标完成阶梯分配**/
    	ladderProportion
    }
    
    
	/**开放预约最大天数*/
	private Integer maxday;
	
	/**参数说明(开放预约最大天数)*/
	private String maxdayRemark;
	
	/**接单类型**/
	private OrderType orderType; 
	
	/**参数说明(接单类型)*/
	private String orderTypeRemark;
	
	/**收费标准(收费类型)**/
	private ChargeType chargeType; 
	
	/**参数说明(收费标准 or 收费类型)*/
	private String chargeTypeRemark;
	
	

	/**机构占有比例**/
	private BigDecimal mechanismProportion;
	
	/**医师占有比例**/
	private BigDecimal doctorProportion;
	
	/**绩效占有类型**/
	private AchievementsType achievementsType;

	/**阶梯降低金额(实际完成工作收入与月度目标每降低  200 元，员工占比降低 0.5%，直至占比降至0%截至。)**/
	private BigDecimal reduceMoney;
	
	/**阶梯降低百分比(实际完成工作收入与月度目标每降低  200 元，员工占比降低 0.5%，直至占比降至0%截至。)**/
	private BigDecimal reduceProportion;
	
	/** 机构 */
	private Mechanism mechanism;
	
	private Double isMinute;
	
	public Integer getMaxday() {
		return maxday;
	}

	public void setMaxday(Integer maxday) {
		this.maxday = maxday;
	}

	public String getMaxdayRemark() {
		return maxdayRemark;
	}

	public void setMaxdayRemark(String maxdayRemark) {
		this.maxdayRemark = maxdayRemark;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeRemark() {
		return orderTypeRemark;
	}

	public void setOrderTypeRemark(String orderTypeRemark) {
		this.orderTypeRemark = orderTypeRemark;
	}
	
	public ChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}
	
	public String getChargeTypeRemark() {
		return chargeTypeRemark;
	}

	public void setChargeTypeRemark(String chargeTypeRemark) {
		this.chargeTypeRemark = chargeTypeRemark;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 1)
	public BigDecimal getMechanismProportion() {
		return mechanismProportion;
	}

	public void setMechanismProportion(BigDecimal mechanismProportion) {
		this.mechanismProportion = mechanismProportion;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 1)
	public BigDecimal getDoctorProportion() {
		return doctorProportion;
	}

	public void setDoctorProportion(BigDecimal doctorProportion) {
		this.doctorProportion = doctorProportion;
	}

	public AchievementsType getAchievementsType() {
		return achievementsType;
	}

	public void setAchievementsType(AchievementsType achievementsType) {
		this.achievementsType = achievementsType;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 2)
	public BigDecimal getReduceMoney() {
		return reduceMoney;
	}

	public void setReduceMoney(BigDecimal reduceMoney) {
		this.reduceMoney = reduceMoney;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 1)
	public BigDecimal getReduceProportion() {
		return reduceProportion;
	}

	public void setReduceProportion(BigDecimal reduceProportion) {
		this.reduceProportion = reduceProportion;
	}

	@OneToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	public Double getIsMinute() {
		return isMinute;
	}

	public void setIsMinute(Double isMinute) {
		this.isMinute = isMinute;
	}
	
	
	
}
