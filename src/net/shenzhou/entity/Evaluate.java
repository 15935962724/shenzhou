/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 评论
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_evaluate")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_evaluate_sequence")
public class Evaluate extends BaseEntity {

	private static final long serialVersionUID = 8795901519290584100L;

	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/review/content";

	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".jhtml";

//	/**
//	 * 类型
//	 */
//	public enum Type {
//
//		/** 好评 */
//		positive,
//
//		/** 中评 */
//		moderate,
//
//		/** 差评 */
//		negative
//	}

	/**综合评分*/
	private Integer scoreSort;
	
	/**服务评分*/
	private Integer serverSort;
	
	/**技能评分*/
	private Integer skillSort;
	
	/**沟通评分*/
	private Integer communicateSort;

	/** 内容 */
	private String content;

	/** 是否显示 */
	private Boolean isShow;

	
	/** IP */
	private String ip;

	/** 会员 */
	private Member member;

	/** 项目 */
	private Project project;
	
	/** 订单 */
	private Order order;

	/**
	 * 获取评分
	 * 
	 * @return 评分
	 */
	
	@NotNull
	@Min(0)
	@Max(10)
	@Column(nullable = false, updatable = false)
	public Integer getScoreSort() {
		return scoreSort;
	}

	public void setScoreSort(Integer scoreSort) {
		this.scoreSort = scoreSort;
	}

	@NotNull
	@Min(0)
	@Max(10)
	@Column(nullable = false, updatable = false)
	public Integer getServerSort() {
		return serverSort;
	}

	public void setServerSort(Integer serverSort) {
		this.serverSort = serverSort;
	}

	@NotNull
	@Min(0)
	@Max(10)
	@Column(nullable = false, updatable = false)
	public Integer getSkillSort() {
		return skillSort;
	}

	public void setSkillSort(Integer skillSort) {
		this.skillSort = skillSort;
	}

	@NotNull
	@Min(0)
	@Max(10)
	@Column(nullable = false, updatable = false)
	public Integer getCommunicateSort() {
		return communicateSort;
	}

	public void setCommunicateSort(Integer communicateSort) {
		this.communicateSort = communicateSort;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	@JsonProperty
	public String getContent() {
		return content;
	}

	

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取是否显示
	 * 
	 * @return 是否显示
	 */
	@JsonProperty
	@Column(nullable = false)
	public Boolean getIsShow() {
		return isShow;
	}

	/**
	 * 设置是否显示
	 * 
	 * @param isShow
	 *            是否显示
	 */
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	/**
	 * 获取IP
	 * 
	 * @return IP
	 */
	@Column(nullable = false, updatable = false)
	public String getIp() {
		return ip;
	}

	/**
	 * 设置IP
	 * 
	 * @param ip
	 *            IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
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
	 * 医师
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@OneToOne(mappedBy = "evaluateOrder", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 获取访该医师问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		if (getProject() != null && getProject().getId() != null) {
			return PATH_PREFIX + "/" + getProject().getId() + PATH_SUFFIX;
		}
		return null;
	}

}