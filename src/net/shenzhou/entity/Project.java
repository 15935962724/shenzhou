/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.shenzhou.entity.Member.Position;
import net.shenzhou.entity.ServerProjectCategory.ServeType;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
* @ClassName: Project 
* @Description: TODO(项目) 
* @author wsr
* @date 2017年5月22日 下午3:53:25
 */
@Entity
@Table(name = "xx_project")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_project_sequence")
public class Project extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2294720618856935550L;

	/** 点击数缓存名称 */
	public static final String HITS_CACHE_NAME = "productHits";

	/** 点击数缓存更新间隔时间 */
	public static final int HITS_CACHE_INTERVAL = 600000;

	/** 商品属性值属性个数 */
	public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;

	/** 商品属性值属性名称前缀 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

	/** 全称规格前缀 */
	public static final String FULL_NAME_SPECIFICATION_PREFIX = "[";

	/** 全称规格后缀 */
	public static final String FULL_NAME_SPECIFICATION_SUFFIX = "]";

	/** 全称规格分隔符 */
	public static final String FULL_NAME_SPECIFICATION_SEPARATOR = " ";
	
	/**
	 * 服务方式
	 * @author wsr
	 *
	 */
	public enum Mode{
		/**到店*/
		store,
		
		/**上门*/
		home,
		/**电话咨询**/
		phone,
		/**线上咨询**/
		online
		
	}
	
	/**
	 * 排序类型
	 */
	public enum OrderType {

		/** 置顶降序 */
		topDesc,

		/** 价格升序 */
		priceAsc,

		/** 价格降序 */
		priceDesc,

		/** 服务次数降序 */
		secondDesc,
		
		/** 服务次数降序 */
		secondAsc,

		/** 评分降序 */
		scoreDesc,
		
		/** 评分升序 */
		scoreAsc,

		/** 日期降序 */
		dateDesc
	}
	
	/**
	 * 服务群体
	 */
	public enum ServiceGroup {

		/** 儿童 */
		children,

		/** 成人 */
		adult
	}
	
	

	/**
	 * 审核状态
	 * @author wsr
	 *
	 */
	public enum Audit{
		
		/**待审核*/
		pending,
		
		/**审核通过*/
		succeed,
		
		/**审核未通过**/
		fail
	}
	
/*	*//**
	 * 服务类型
	 * @author fl
	 *
	 *//*
	public enum ServeType{
		
		*//**诊治*//*
		examine,
		
		*//**评估*//*
		assess
		
	}*/
	
	/** 名称 */
	private String name;
	
	/** 编号 */
//	private String sn;
	
	/**简写*/
	private String Shorthand;

	/**项目图片*/
	private String logo;
	
	/**大图**/
	private String bigImage;
	
	/**项目介绍(详情)*/
	private String introduce;
	
	/**项目介绍图片*/
	private String introduceImg;
	
	/**项目服务次数*/
	private Integer second;
	
	//**项目服务价格*//*
	private BigDecimal price;
	
	//**项目服务时长*//*
	private Integer time;
	
	/**医师*/
	private Doctor doctor;

	/**医生与机构关系表(主表)**/
	private DoctorMechanismRelation doctorMechanismRelation;
	
	/**障碍部位*/
	private Position position;
	
	/** 服务项目(分类) */          
	private ServerProjectCategory serverProjectCategory;
	
	/** 服务项目(父级---平台端) */          
	private ServerProjectCategory parentServerProjectCategory;
	
	/**服务方式*/
	private Mode mode;
	
	/**服务类型*/
	private ServeType serveType;
	
	/**备注**/
	private String remarks;
	
	/**订单项*/
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();
	
	/** 评价*/
	private Set<Evaluate> evaluates = new HashSet<Evaluate>();
	
	/** 项目项(价格)*/
	private Set<ProjectItem> projectItems = new HashSet<ProjectItem>();
	
	/**审核 **/
	private Audit audit;
	
	/**服务群体**/
	private ServiceGroup serviceGroup;
	
	/**机构**/
	private Mechanism mechanism;
	
	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@JsonProperty
//	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
//	@Pattern(regexp = "^[0-9a-zA-Z_-]+$")
//	@Length(max = 100)
////	@Column(nullable = false, unique = true, length = 100)
//	public String getSn() {
//		return sn;
//	}
//
//	public void setSn(String sn) {
//		this.sn = sn;
//	}

	@JsonProperty
	public String getShorthand() {
		return Shorthand;
	}

	public void setShorthand(String shorthand) {
		Shorthand = shorthand;
	}

	@JsonProperty
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@JsonProperty
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@Lob
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@JsonProperty
	public String getIntroduceImg() {
		return introduceImg;
	}

	public void setIntroduceImg(String introduceImg) {
		this.introduceImg = introduceImg;
	}

	@JsonProperty
	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	@JsonProperty
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@JsonProperty
	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
	
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public DoctorMechanismRelation getDoctorMechanismRelation() {
		return doctorMechanismRelation;
	}

	public void setDoctorMechanismRelation(
			DoctorMechanismRelation doctorMechanismRelation) {
		this.doctorMechanismRelation = doctorMechanismRelation;
	}

	@JsonProperty
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public ServerProjectCategory getServerProjectCategory() {
		return serverProjectCategory;
	}

	public void setServerProjectCategory(ServerProjectCategory serverProjectCategory) {
		this.serverProjectCategory = serverProjectCategory;
	}

//	@JsonProperty
//	public Mode getMode() {
//		return mode;
//	}
//
//	public void setMode(Mode mode) {
//		this.mode = mode;
//	}

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Evaluate> getEvaluates() {
		return evaluates;
	}

	public void setEvaluates(Set<Evaluate> evaluates) {
		this.evaluates = evaluates;
	}

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<ProjectItem> getProjectItems() {
		return projectItems;
	}

	public void setProjectItems(Set<ProjectItem> projectItems) {
		this.projectItems = projectItems;
	}

	@JsonProperty
	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

//	public ServiceGroup getServiceGroup() {
//		return serviceGroup;
//	}
//
//	public void setServiceGroup(ServiceGroup serviceGroup) {
//		this.serviceGroup = serviceGroup;
//	}

	public ServeType getServeType() {
		return serveType;
	}

	public void setServeType(ServeType serveType) {
		this.serveType = serveType;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	public String getBigImage() {
		return bigImage;
	}

	public void setBigImage(String bigImage) {
		this.bigImage = bigImage;
	}
	
	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public ServiceGroup getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(ServiceGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public ServerProjectCategory getParentServerProjectCategory() {
		return parentServerProjectCategory;
	}

	public void setParentServerProjectCategory(
			ServerProjectCategory parentServerProjectCategory) {
		this.parentServerProjectCategory = parentServerProjectCategory;
	}
	
}
