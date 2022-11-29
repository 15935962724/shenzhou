/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import net.shenzhou.Setting;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.Project.ServiceGroup;
import net.shenzhou.entity.Refunds.Status;
import net.shenzhou.entity.ServerProjectCategory.ServeType;
import net.shenzhou.util.SettingUtils;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 图书
 * @author Admin
 *
 */
@Entity
@Table(name = "xx_book")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_book_sequence")
public class Book extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1671089018157673367L;
	
	/** 编号 */
	private String sn;

	/** 名称 */
	private String name;
	
	/** 类型 */
	private String type;

	/** 作者 */
	private String author;
	
	/**单价*/
	private BigDecimal price;
	
	/**出版社*/
	private String press;
	
	/**状态*/
	private String status;
	
	/**数量*/
	private Integer number;
	
	/**
	 * 获取订单编号
	 * 
	 * @return 订单编号
	 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	

	
	
	
}
