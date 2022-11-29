/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.shenzhou.entity.BankCard.CardType;

/**
 * Entity - 提现记录
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_withdrawdeposit")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_withdrawdeposit_sequence")
public class WithdrawDeposit extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2531899216222821273L;
	
	
	public enum WithdrawState {

		/** 处理中 */
		operation,
		
		/** 提现成功 */
		succeed,

		/** 提现失败 */
		defeated 
	}
	
	/**医生**/
	private Doctor doctor;

	/**提现日期**/
	private Date date;
	
	/**提现金额**/
	private BigDecimal money;
	
	/**银行卡类型**/
	private CardType cardType;
	
	/**银行卡号**/
	private String cardNo;
	
	/**预计到账时间**/
	private Date predictDate;
	
	/**提现状态**/
	private WithdrawState withdrawState;
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getPredictDate() {
		return predictDate;
	}

	public void setPredictDate(Date predictDate) {
		this.predictDate = predictDate;
	}

	public WithdrawState getWithdrawState() {
		return withdrawState;
	}

	public void setWithdrawState(WithdrawState withdrawState) {
		this.withdrawState = withdrawState;
	}
	
}

















