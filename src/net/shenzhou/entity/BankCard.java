/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_bankcard")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_bankcard_sequence")
public class BankCard extends BaseEntity {

	/**
	 * 银行卡类型
	 */
	public enum CardType {

		/**储蓄卡**/
		barkCard,

		/**信用卡**/
		blueCard
	}

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7021661482734924595L;
	
	/**银行卡号**/
	private String cardNo;
	
	/**持卡人姓名**/
	private String customerName;
	
	/**关联电话**/
	private String mobile;
	
	/**银行卡类型**/
	private CardType cardType;
	
	/**所属银行**/
	private String bank;
	
	/**医生**/
	private Doctor doctor;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	
	
}