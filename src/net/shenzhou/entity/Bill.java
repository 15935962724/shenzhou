/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_bill")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_bill_sequence")
public class Bill extends BaseEntity {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7017956143416517723L;

	/**
	 * 账单类型
	 */
	public enum BillType {

		/**个人**/
		personage,

		/**机构**/
		organization
	}

	/**项目名称**/
	private String BillName;
	
	/**治疗人姓名**/
	private String memberName;
	
	/**治疗人年龄**/
	private Integer age;
	
	/**账单类型**/
	private BillType billType;
	
	/**服务地址**/
	private String address;
	
	/**金额**/
	private BigDecimal money;
	
	/**医生**/
	private Doctor doctor;
	
	/**机构**/
	private Mechanism mechanism;
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public String getBillName() {
		return BillName;
	}

	public void setBillName(String billName) {
		BillName = billName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	

}