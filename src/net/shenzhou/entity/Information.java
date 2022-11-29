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
 * Entity - 医生端消息
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_information_table")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_information_table_sequence")
public class Information extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2224841299395475879L;

	/**
	 * 
	 */

	public enum InformationType {

		/** 系统通知(实名认证,修改密码手机号,加入平台等) */
		system,
		
		/** 计划审核授权通知 */
		audit,
		
		/**康护技师授权通知**/
		recovery,
		
		/** 订单通知 */
		order ,
		
		/** 服务审核通知 */
		project ,
		
		/** 医生审核通知(机构审核医生) */
		doctor 
		
	}
	
	public enum ClassifyType {

		/** 系统消息 */
		system,
		
		/** 业务消息*/
		business,
		
		/** 活动消息*/
		activity
		
	}
	
	
	public enum StateType {

		/** 已读 */
		read,
		
		/** 未读*/
		unread,
	}
	
	public enum DisposeState {

		/** 已处理 */
		dispose,
		
		/** 未处理*/
		unDispose,
	}
	
	/**用户类型**/
	public enum UserType {

		/** 医生消息 */
		doctor,
		
		/** 用户消息*/
		member,
	}
	
	
	/**内容**/
	private String message;
	
	/**标题**/
	private String headline;
	
	/**携带数据Id**/
	private Long informationId;
	
	/**消息类型**/
	private InformationType informationType;
	
	/**消息分类**/
	private ClassifyType classifyType;
	
	/**已读状态**/
	private StateType state;
	
	/**医生**/
	private Doctor doctor;
	
	/**用户**/
	private Member member;
	
	/**机构**/
	private Mechanism mechanism;
	
	/**处理状态**/
	private DisposeState disposeState;
	
	/**用户类型**/
	private UserType userType;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getInformationId() {
		return informationId;
	}

	public void setInformationId(Long informationId) {
		this.informationId = informationId;
	}

	public InformationType getInformationType() {
		return informationType;
	}

	public void setInformationType(InformationType informationType) {
		this.informationType = informationType;
	}

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public DisposeState getDisposeState() {
		return disposeState;
	}

	public void setDisposeState(DisposeState disposeState) {
		this.disposeState = disposeState;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public ClassifyType getClassifyType() {
		return classifyType;
	}

	public void setClassifyType(ClassifyType classifyType) {
		this.classifyType = classifyType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}
	
}


