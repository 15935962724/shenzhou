/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 地区
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_area")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_area_sequence")
public class Area extends OrderEntity {

	private static final long serialVersionUID = -2158109459123036967L;

	/** 树路径分隔符 */
	private static final String TREE_PATH_SEPARATOR = ",";

	/** 名称 */
	private String name;

	/** 全称 */
	private String fullName;

	/** 树路径 */
	private String treePath;

	/** 上级地区 */
	private Area parent;

	/** 下级地区 */
	private Set<Area> children = new HashSet<Area>();

	/** 会员 */
	private Set<Member> members = new HashSet<Member>();
	
	/** 会员籍贯 */
	private Set<Member> membersBirthplace = new HashSet<Member>();
	
	/** 医院机构 */
	private Set<Mechanism> mechanisms = new HashSet<Mechanism>();
	
	/** 医师 */
	private Set<Doctor> doctors = new HashSet<Doctor>();
	
	/** 医师籍贯 */
	private Set<Doctor> doctorsBirthplace = new HashSet<Doctor>();
	
	/** 专家 */
	private Set<Expert> experts = new HashSet<Expert>();
	
	/** 专家籍贯 */
	private Set<Expert> expertBirthplaces = new HashSet<Expert>();
	
	/** 机构证件 */
	private Set<Certificates> certificatess = new HashSet<Certificates>();

	/** 收货地址 */
	private Set<Receiver> receivers = new HashSet<Receiver>();

	/** 订单 */
	private Set<Order> orders = new HashSet<Order>();

	/** 发货点 */
	private Set<DeliveryCenter> deliveryCenters = new HashSet<DeliveryCenter>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 100)
	@Column(nullable = false, length = 100)
	@JsonProperty
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取全称
	 * 
	 * @return 全称
	 */
	@JsonProperty
	@Column(nullable = false, length = 500)
	public String getFullName() {
		return fullName;
	}

	/**
	 * 设置全称
	 * 
	 * @param fullName
	 *            全称
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	@Column(nullable = false, updatable = false)
	public String getTreePath() {
		return treePath;
	}

	/**
	 * 设置树路径
	 * 
	 * @param treePath
	 *            树路径
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	/**
	 * 获取上级地区
	 * 
	 * @return 上级地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getParent() {
		return parent;
	}

	/**
	 * 设置上级地区
	 * 
	 * @param parent
	 *            上级地区
	 */
	public void setParent(Area parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级地区
	 * 
	 * @return 下级地区
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<Area> getChildren() {
		return children;
	}

	/**
	 * 设置下级地区
	 * 
	 * @param children
	 *            下级地区
	 */
	public void setChildren(Set<Area> children) {
		this.children = children;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<Member> getMembers() {
		return members;
	}

	/**
	 * 设置会员
	 * 
	 * @param members
	 *            会员
	 */
	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	@OneToMany(mappedBy = "nowArea", fetch = FetchType.LAZY)
	public Set<Member> getMembersBirthplace() {
		return membersBirthplace;
	}

	public void setMembersBirthplace(Set<Member> membersBirthplace) {
		this.membersBirthplace = membersBirthplace;
	}

	/**
	 * 获取医生
	 * 
	 * @return 医生
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<Doctor> getDoctors() {
		return doctors;
	}

	/**
	 * 设置医生
	 * 
	 * @param members
	 *            医生
	 */
	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}
	
	@OneToMany(mappedBy = "birthplace", fetch = FetchType.LAZY)
	public Set<Doctor> getDoctorsBirthplace() {
		return doctorsBirthplace;
	}

	public void setDoctorsBirthplace(Set<Doctor> doctorsBirthplace) {
		this.doctorsBirthplace = doctorsBirthplace;
	}

	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<Expert> getExperts() {
		return experts;
	}

	public void setExperts(Set<Expert> experts) {
		this.experts = experts;
	}

	@OneToMany(mappedBy = "birthplace", fetch = FetchType.LAZY)
	public Set<Expert> getExpertBirthplaces() {
		return expertBirthplaces;
	}

	public void setExpertBirthplaces(Set<Expert> expertBirthplaces) {
		this.expertBirthplaces = expertBirthplaces;
	}

	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<Certificates> getCertificatess() {
		return certificatess;
	}

	public void setCertificatess(Set<Certificates> certificatess) {
		this.certificatess = certificatess;
	}

	/**
	 * 获取机构
	 * 
	 * @return 机构
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<Mechanism> getMechanisms() {
		return mechanisms;
	}

	public void setMechanisms(Set<Mechanism> mechanisms) {
		this.mechanisms = mechanisms;
	}

	/**
	 * 获取收货地址
	 * 
	 * @return 收货地址
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<Receiver> getReceivers() {
		return receivers;
	}

	/**
	 * 设置收货地址
	 * 
	 * @param receivers
	 *            收货地址
	 */
	public void setReceivers(Set<Receiver> receivers) {
		this.receivers = receivers;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * 设置订单
	 * 
	 * @param orders
	 *            订单
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * 获取发货点
	 * 
	 * @return 发货点
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<DeliveryCenter> getDeliveryCenters() {
		return deliveryCenters;
	}

	/**
	 * 设置发货点
	 * 
	 * @param deliveryCenters
	 *            发货点
	 */
	public void setDeliveryCenters(Set<DeliveryCenter> deliveryCenters) {
		this.deliveryCenters = deliveryCenters;
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		Area parent = getParent();
		if (parent != null) {
			setFullName(parent.getFullName() + getName());
			setTreePath(parent.getTreePath() + parent.getId() + TREE_PATH_SEPARATOR);
		} else {
			setFullName(getName());
			setTreePath(TREE_PATH_SEPARATOR);
		}
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		Area parent = getParent();
		if (parent != null) {
			setFullName(parent.getFullName() + getName());
		} else {
			setFullName(getName());
		}
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Member> members = getMembers();
		if (members != null) {
			for (Member member : members) {
				member.setArea(null);
			}
		}
		Set<Doctor> doctors = getDoctors();
		if (doctors != null) {
			for (Doctor doctor : doctors) {
				doctor.setArea(null);
			}
		}
		Set<Receiver> receivers = getReceivers();
		if (receivers != null) {
			for (Receiver receiver : receivers) {
				receiver.setArea(null);
			}
		}
		Set<Order> orders = getOrders();
		if (orders != null) {
			for (Order order : orders) {
				order.setArea(null);
			}
		}
		Set<DeliveryCenter> deliveryCenters = getDeliveryCenters();
		if (deliveryCenters != null) {
			for (DeliveryCenter deliveryCenter : deliveryCenters) {
				deliveryCenter.setArea(null);
			}
		}
	}

	/**
	 * 重写toString方法
	 * 
	 * @return 全称
	 */
	@Override
	public String toString() {
		return getFullName();
	}

}
