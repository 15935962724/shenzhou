/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 医生分类
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_doctor_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_doctor_category_sequence")
public class DoctorCategory extends OrderEntity {
	


	private static final long serialVersionUID = 5095521437302782717L;

	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".jhtml";

	/**收费类型**/
    public enum	ChargeType{
    	
    	/**免费**/
    	free,
    	
    	/**按公里收费**/
    	kmCharge,
    	
    }
	
	/** 名称 */
	private String name;

	/** 职称(此字段暂时放弃) */
	private String title;

	/** 页面关键词 */
	private String seoKeywords;

	/** 页面描述 */
	private String seoDescription;

	/** 树路径 */
	private String treePath;

	/**服务类型**/
	private ChargeType chargeType;
	
	/**收费标准**/
	private DoorCost doorCost;

	/**所属机构**/
	private Mechanism mechanism;
	
	/** 层级 */
	private Integer grade;

	/** 上级分类 */
	private DoctorCategory parent;

	/** 下级分类 */
	private Set<DoctorCategory> children = new HashSet<DoctorCategory>();

	/** 医师 */
	private Set<Doctor> doctors = new HashSet<Doctor>();

	/** 专家 */
	private Set<Expert> expers = new HashSet<Expert>();
	
	/**医生职称关系**/
	private List<DoctorCategoryRelation> doctorCategoryRelations = new ArrayList<DoctorCategoryRelation>();
	
	/**医生主表**/
	private Set<DoctorMechanismRelation> doctorMechanismRelations = new HashSet<DoctorMechanismRelation>();
	
	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
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
	 * 获取职称
	 * 
	 * @return 职称
	 */
	@Length(max = 200)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置职称
	 * 
	 * @param seoTitle
	 *            职称
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取页面关键词
	 * 
	 * @return 页面关键词
	 */
	@Length(max = 200)
	public String getSeoKeywords() {
		return seoKeywords;
	}

	/**
	 * 设置页面关键词
	 * 
	 * @param seoKeywords
	 *            页面关键词
	 */
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	/**
	 * 获取页面描述
	 * 
	 * @return 页面描述
	 */
	@Length(max = 200)
	public String getSeoDescription() {
		return seoDescription;
	}

	/**
	 * 设置页面描述
	 * 
	 * @param seoDescription
	 *            页面描述
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	@Column(nullable = false)
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

	public ChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}

	@Embedded
	public DoorCost getDoorCost() {
		return doorCost;
	}

	public void setDoorCost(DoorCost doorCost) {
		this.doorCost = doorCost;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	/**
	 * 获取层级
	 * 
	 * @return 层级
	 */
	@Column(nullable = false)
	public Integer getGrade() {
		return grade;
	}

	/**
	 * 设置层级
	 * 
	 * @param grade
	 *            层级
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * 获取上级分类
	 * 
	 * @return 上级分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public DoctorCategory getParent() {
		return parent;
	}

	/**
	 * 设置上级分类
	 * 
	 * @param parent
	 *            上级分类
	 */
	public void setParent(DoctorCategory parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级分类
	 * 
	 * @return 下级分类
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("order asc")
	public Set<DoctorCategory> getChildren() {
		return children;
	}

	/**
	 * 设置下级分类
	 * 
	 * @param children
	 *            下级分类
	 */
	public void setChildren(Set<DoctorCategory> children) {
		this.children = children;
	}

	@OneToMany(mappedBy = "doctorCategory", fetch = FetchType.LAZY)
	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}
	
	@OneToMany(mappedBy = "doctorCategory", fetch = FetchType.LAZY)
	public Set<Expert> getExpers() {
		return expers;
	}

	public void setExpers(Set<Expert> expers) {
		this.expers = expers;
	}

	@OneToMany(mappedBy = "doctorCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<DoctorCategoryRelation> getDoctorCategoryRelations() {
		return doctorCategoryRelations;
	}

	public void setDoctorCategoryRelations(
			List<DoctorCategoryRelation> doctorCategoryRelations) {
		this.doctorCategoryRelations = doctorCategoryRelations;
	}
	
	
	@OneToMany(mappedBy = "doctorCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<DoctorMechanismRelation> getDoctorMechanismRelations() {
		return doctorMechanismRelations;
	}

	public void setDoctorMechanismRelations(
			Set<DoctorMechanismRelation> doctorMechanismRelations) {
		this.doctorMechanismRelations = doctorMechanismRelations;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	@Transient
	public List<Long> getTreePaths() {
		List<Long> treePaths = new ArrayList<Long>();
		String[] ids = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		if (ids != null) {
			for (String id : ids) {
				treePaths.add(Long.valueOf(id));
			}
		}
		return treePaths;
	}

	
//	/**
//	 * 删除前处理
//	 */
//	@PreRemove
//	public void preRemove() {
//		Set<Promotion> promotions = getPromotions();
//		if (promotions != null) {
//			for (Promotion promotion : promotions) {
//				promotion.getProductCategories().remove(this);
//			}
//		}
//	}
	
	
	
}