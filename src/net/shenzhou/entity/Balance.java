/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

/**
 * 
* @ClassName: Balance 
* @Description: TODO(余额) 
* @author wsr  
* @date 2018-3-19 14:36:54
 */
@Entity
@Table(name = "xx_balance")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_balance_sequence")
public class Balance extends BaseEntity {

	private static final long serialVersionUID = -8323452873046981882L;

	

	/** 当前余额 */
	private BigDecimal balance;
	
	

	/** 会员 */
	private Member member;

	
	
	/** 所属机构 */
	private Mechanism mechanism;

	

	/**
	 * 获取当前余额
	 * 
	 * @return 当前余额
	 */
	@Field(store = Store.YES, index = Index.NO)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 2)
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * 设置当前余额
	 * 
	 * @param balance
	 *            当前余额
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**  
	 * 获取所属合作社  
	 * @return cooperative  
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}


}