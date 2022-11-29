/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Entity - 用户账单数据实体
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public class MemberBillDetails {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1727449869999714421L;

	public enum BillType {

		/** 充值 **/
		recharge,
		
		/** 消费  **/
		consumption,
		
		/** 退款  **/
		income
	}
	
	
	/**机构名称**/
	private String mechanismName;
	
	/**项目名**/
	private String projectName;
	
	/**类型(充值,消费)**/
	private BillType billType;
	
	/**时间**/
	private String time;
	
	/**金钱**/
	private String money;
	
	/**姓名(医生端存患者姓名,患者端存医生姓名)**/
	private String name;
	
	@JsonProperty
	public String getMechanismName() {
		return mechanismName;
	}

	public void setMechanismName(String mechanismName) {
		this.mechanismName = mechanismName;
	}
	
	@JsonProperty
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@JsonProperty
	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}
	
	@JsonProperty
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@JsonProperty
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}