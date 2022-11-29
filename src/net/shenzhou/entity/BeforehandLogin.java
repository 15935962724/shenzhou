/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 邀请记录表
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_beforehand_login")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_beforehand_login_sequence")
public class BeforehandLogin extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7297904064211878299L;

	/**
	 * 邀请人类型
	 */
	public enum UserType {

		/**医生**/
		doctor,

		/**用户**/
		member
	}
	
	/**
	 * 邀请类型
	 */
	public enum InviteType {

		/**活动**/
		activity,

		/**注册**/
		login,
		
		/**下单**/
		order
	}

	/**邀请人类型**/
	private UserType userType;
	
	/**被邀请人类型**/
	private UserType usersType;
	
	/**邀请人手机号**/
	private String phone;
	
	/**被邀请人手机号**/
	private String mobile;
	
	/**被邀请人是否注册**/
	private boolean notarizeLogin;

	/**被邀请人是否下单**/
	private boolean purchase;
	
	/**本次积分**/
	private Long point;
	
	/**本次积分是否完成**/
	private boolean isAccomplish;
	
	/**邀请类型**/
	private InviteType inviteType;
	
	/**注册生效日期**/
	private Date registerDate;
	
	/**下单日期**/
	private Date orderDate;
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public UserType getUsersType() {
		return usersType;
	}

	public void setUsersType(UserType usersType) {
		this.usersType = usersType;
	}

	public InviteType getInviteType() {
		return inviteType;
	}

	public void setInviteType(InviteType inviteType) {
		this.inviteType = inviteType;
	}

	public boolean isNotarizeLogin() {
		return notarizeLogin;
	}

	public void setNotarizeLogin(boolean notarizeLogin) {
		this.notarizeLogin = notarizeLogin;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public boolean isAccomplish() {
		return isAccomplish;
	}

	public void setAccomplish(boolean isAccomplish) {
		this.isAccomplish = isAccomplish;
	}

	public boolean isPurchase() {
		return purchase;
	}

	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
}