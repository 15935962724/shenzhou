/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
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

import net.shenzhou.entity.Deposit.Type;

/**
 * Entity - 充值日志
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_recharge_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_recharge_log_sequence")
public class RechargeLog extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8245546739201613813L;

	/** 操作员(姓名) */
	private String operator;

	/** 操作员(手机号) */
	private String mobile;
	
	/**充值金额**/
	private BigDecimal money;
	
	/** 备注 */
	private String remarks;
	
	/** 充值类型*/
	private Type type;
	
	/** 充值会员 */
	private Member member;
	
	/** 充值机构*/
	private Mechanism mechanism;
	
	

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
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
	
	

	
}