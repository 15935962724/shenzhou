/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

/**
 * 上门费用
 * @date 2018-1-20 16:25:18
 * @author wsr
 *
 */
@Embeddable
public class DoorCost implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 863530862964473000L;


	/** 路程在 ${kilometre}公里内收费 price 元  */
	private Integer kilometre;

	/** 路程在 kilometre公里内收费 ${price} 元  */
	private BigDecimal price;
	
	/** 每超过${increaseKilometre}公里增加元  */
	private Integer increaseKilometre;
	
	/** 路程在 increaseKilometre 公里内收费 ${increaseprice} 元  */
	private BigDecimal increasePrice;

	
	@Min(0)
	@Field(store = Store.YES, index = Index.NO)
	@Column(nullable = false)
	public Integer getKilometre() {
		return kilometre;
	}

	public void setKilometre(Integer kilometre) {
		this.kilometre = kilometre;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name = "price" ,nullable = false, precision = 21, scale = 1)
	public BigDecimal getPrice() {
		return price;
	}

	@Min(0)
	@Field(store = Store.YES, index = Index.NO)
	@Column(nullable = false)
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Min(0)
	@Field(store = Store.YES, index = Index.NO)
	@Column(nullable = false)
	public Integer getIncreaseKilometre() {
		return increaseKilometre;
	}

	public void setIncreaseKilometre(Integer increaseKilometre) {
		this.increaseKilometre = increaseKilometre;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name = "increase_price" ,nullable = false, precision = 21, scale = 1)
	public BigDecimal getIncreasePrice() {
		return increasePrice;
	}

	public void setIncreasePrice(BigDecimal increasePrice) {
		this.increasePrice = increasePrice;
	}

	
	
	

}