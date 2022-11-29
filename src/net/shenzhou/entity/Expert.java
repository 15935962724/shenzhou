/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.shenzhou.entity.BaseEntity.Save;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.util.MobileUtil;

import org.hibernate.annotations.OrderBy;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Similarity;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.NotEmpty;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKSimilarity;

/**
 * 医生
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_expert")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_expert_sequence")
public class Expert extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7557469637671405429L;

	/** 姓名 */
	private String name;
	
	/**姓名全拼**/
	private String pinyin;
	
	/** 头像 */
	private String logo;

	/** 性别  1.男 2.女*/
	private Gender gender;

	/** 出生日期 */
	private Date birth;
	
	/**民族**/
	private String nation;

	/** 现居住地区 */
	private Area area;
	
	/** 现居住地址 */
	private String address;
	
	/** 籍贯 */
	private Area birthplace;
	
	/** 籍贯详细地址 */
	private String birthplaceAddress;

	/** 电话 */
	private String phone;

	/** 手机 */
	private String mobile;

    /**从业年限*/
	private Integer year;
	
	/** 排序 */
	private Integer order;
	
	/**擅长*/
	private String advantage;
	
	/**个人介绍(简介)*/
	private String introduce;
	
	/**身份证号*/
	private String entityCode;
	
	/**综合评分*/
	private Double scoreSort;
	
	/**服务评分*/
	private Double serverSort;
	
	/**技能评分*/
	private Double skillSort;
	
	/**沟通评分*/
	private Double communicateSort;
	
	/**专家分类**/
	private DoctorCategory doctorCategory;
	
	/**专家等级**/
	private DoctorRank doctorRank;
	
	
	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@JsonProperty
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public DoctorCategory getDoctorCategory() {
		return doctorCategory;
	}

	public void setDoctorCategory(DoctorCategory doctorCategory) {
		this.doctorCategory = doctorCategory;
	}

	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public DoctorRank getDoctorRank() {
		return doctorRank;
	}

	public void setDoctorRank(DoctorRank doctorRank) {
		this.doctorRank = doctorRank;
	}

	@JsonProperty
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	@JsonProperty
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@JsonProperty
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(Area birthplace) {
		this.birthplace = birthplace;
	}

	public String getBirthplaceAddress() {
		return birthplaceAddress;
	}

	public void setBirthplaceAddress(String birthplaceAddress) {
		this.birthplaceAddress = birthplaceAddress;
	}

	@JsonProperty
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
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

	@JsonProperty
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@JsonProperty
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	@Min(0)
	@Column(name = "orders")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@JsonProperty
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@Lob
	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
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
	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	@JsonProperty
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	@Column(nullable = false, precision = 12, scale = 1,columnDefinition="double(10,1) default '0.0'")
	public Double getScoreSort() {
		return scoreSort;
	}

	public void setScoreSort(Double scoreSort) {
		this.scoreSort = scoreSort;
	}
	

	@JsonProperty
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	@Column(nullable = false, precision = 12, scale = 1,columnDefinition="double(10,1) default '0.0'")
	public Double getServerSort() {
		return serverSort;
	}

	public void setServerSort(Double serverSort) {
		this.serverSort = serverSort;
	}

	@JsonProperty
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	@Column(nullable = false, precision = 12, scale = 1,columnDefinition="double(10,1) default '0.0'")
	public Double getSkillSort() {
		return skillSort;
	}

	public void setSkillSort(Double skillSort) {
		this.skillSort = skillSort;
	}

	@JsonProperty
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	@Column(nullable = false, precision = 12, scale = 1,columnDefinition="double(10,1) default '0.0'")
	public Double getCommunicateSort() {
		return communicateSort;
	}

	public void setCommunicateSort(Double communicateSort) {
		this.communicateSort = communicateSort;
	}
	
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
}
