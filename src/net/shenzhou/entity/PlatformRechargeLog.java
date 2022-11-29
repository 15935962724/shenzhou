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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import net.shenzhou.entity.BaseEntity.Save;
import net.shenzhou.entity.Deposit.Type;

/**
 * Entity - 充值日志
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_platform_recharge_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_platform_recharge_log_sequence")
public class PlatformRechargeLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -78719495429325220L;

	/**
	 * 充值方式
	 */
	 
	public enum RechargeMode {
		
		/**
		 * 微信
		 */
		WeChat,
		
		/**
		 * 支付宝
		 */
		Alipay,
		
		/**
		 * 线下
		 */
		Under 
		
		
	}
	
	
	/** 充值方式 */
	private RechargeMode rechargeMode;
	
	/** 操作员(姓名) */
	private String operator;

	/** 操作员(手机号) */
	private String mobile;
	
	/** 商户订单号 */
	private String outTradeNo;
	
	/** 交易单号 */
	private String tradeNo;
	
	/**充值金额**/
	private BigDecimal money;
	
	/** 备注 */
	private String remarks;
	
	/** 充值会员 */
	private Member member;
	
	
	public RechargeMode getRechargeMode() {
		return rechargeMode;
	}

	public void setRechargeMode(RechargeMode rechargeMode) {
		this.rechargeMode = rechargeMode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
	@NotEmpty(groups = Save.class)
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	@NotEmpty(groups = Save.class)
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}


	
	

	
}