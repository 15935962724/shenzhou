/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 工作目标 提成占比
 * @date 2018-1-20 09:54:39
 * @author wsr
 *
 */
@Embeddable
public class WorkTarget implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2237405574434897753L;

	/** 日工作目标  **/
	private BigDecimal dayWorkTarget;

	/** 提成占比 */
	private BigDecimal percentage;
	
	public WorkTarget() {
		
		this.dayWorkTarget = new BigDecimal(0);
		
		this.percentage = new BigDecimal(0);
	}
	
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name = "day_work_target" ,nullable = false, precision = 21, scale = 1)
	public BigDecimal getDayWorkTarget() {
		return dayWorkTarget;
	}

	public void setDayWorkTarget(BigDecimal dayWorkTarget) {
		this.dayWorkTarget = dayWorkTarget;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name = "percentage" ,nullable = false, precision = 21, scale = 1)
	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}


}