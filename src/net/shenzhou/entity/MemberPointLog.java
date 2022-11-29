/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.shenzhou.Setting;
import net.shenzhou.util.SettingUtils;

/**
 * Entity - 用户积分日志
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_member_point_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_member_point_log_sequence")
public class MemberPointLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3173562508594388973L;

	/**
	 * 类型
	 */
	public enum Type {

		/**邀请获取**/
		invitation,
		
		/**签到获取**/
		sign,
		
		/**积分消费**/
		consumption
		
	};

	/** 类型 */
	private Type type;

	/** 收入积分(有效积分) */
	private Long credit;
	
	/**失效积分**/
	private Long invalid;

	/** 支出积分 */
	private Long debit;

	/** 当前积分 */
	private Long point;

	/** 备注 */
	private String memo;

	/** 会员 */
	private Member member;
	
	/** 邀请人 */
	private Member recommendMember;
	
	/** 邀请人 */
	private Doctor recommendDoctor;

	/** 订单 */
	private Order order;
	
	/** 积分订单 */
	private ProductOrder productOrder;

	/** 所属机构 */
	private Mechanism mechanism;

	/** 到期(过期时间)时间 */
	private Date expire;
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Long getCredit() {
		return credit;
	}

	public void setCredit(Long credit) {
		this.credit = credit;
	}

	public Long getInvalid() {
		return invalid;
	}

	public void setInvalid(Long invalid) {
		this.invalid = invalid;
	}

	public Long getDebit() {
		return debit;
	}

	public void setDebit(Long debit) {
		this.debit = debit;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	public Member getRecommendMember() {
		return recommendMember;
	}

	public void setRecommendMember(Member recommendMember) {
		this.recommendMember = recommendMember;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	public Doctor getRecommendDoctor() {
		return recommendDoctor;
	}

	public void setRecommendDoctor(Doctor recommendDoctor) {
		this.recommendDoctor = recommendDoctor;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders",updatable = false)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	
	
	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productOrders",updatable = false)
	public ProductOrder getProductOrder() {
		return productOrder;
	}

	public void setProductOrder(ProductOrder productOrder) {
		this.productOrder = productOrder;
	}

	/**
	 * 是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean isExpired() {
		
		return getExpire() != null && new Date().after(getExpire());
		
//		Date date = new Date();
//		Setting setting = SettingUtils.get();
//		Integer day = (int) (getCreateDate().getTime()-date.getTime())/ (24 * 60 * 60 * 1000);
//		return day<setting.getMemberPointDay();
	}
	
}

