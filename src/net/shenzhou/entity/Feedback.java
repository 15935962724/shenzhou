/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 意见反馈
 * @date 2017-11-17 10:16:31
 * @author wsr
 * @version 1.0
 */
@Entity
@Table(name = "xx_feedback")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_feedback_sequence")
public class Feedback extends BaseEntity {

	private static final long serialVersionUID = -8461722184314688946L;

	/** 姓名 */
	private String name;

	/** 电话 */
	private String phone;
	
	/** ip */
	private String ip;
	
	/** 反馈内容 */
	private String content;

	/** 是否匿名 */
	private Boolean isAnonymous;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(nullable = false, updatable = false)
	public Boolean getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(Boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	


}