/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 机构等级
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_mechanism_rank")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_mechanism_rank_sequence")
public class MechanismRank extends OrderEntity {
	


	private static final long serialVersionUID = 5095521437302782717L;

	
	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/mechanism/list";

	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".jhtml";
	
	/** 名称 */
	private String name;

	/** 页面标题 */
	private String seoTitle;

	/** 页面关键词 */
	private String seoKeywords;

	/** 页面描述 */
	private String seoDescription;

	/** 树路径 */
	private String treePath;

	/** 层级 */
	private Integer grade;

	/** 上级分类 */
	private MechanismRank parent;

	/** 下级分类 */
	private Set<MechanismRank> children = new HashSet<MechanismRank>();

	/** 机构 */
	private Set<Mechanism> mechanisms = new HashSet<Mechanism>();

	
	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@JsonProperty
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
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
	 * 获取页面标题
	 * 
	 * @return 页面标题
	 */
	@Length(max = 200)
	public String getSeoTitle() {
		return seoTitle;
	}

	/**
	 * 设置页面标题
	 * 
	 * @param seoTitle
	 *            页面标题
	 */
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
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
	public MechanismRank getParent() {
		return parent;
	}

	/**
	 * 设置上级分类
	 * 
	 * @param parent
	 *            上级分类
	 */
	public void setParent(MechanismRank parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级分类
	 * 
	 * @return 下级分类
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("order asc")
	public Set<MechanismRank> getChildren() {
		return children;
	}

	/**
	 * 设置下级分类
	 * 
	 * @param children
	 *            下级分类
	 */
	public void setChildren(Set<MechanismRank> children) {
		this.children = children;
	}

	/**
	 * 获取机构
	 * 
	 * @return 机构
	 */
	@OneToMany(mappedBy = "mechanismRank", fetch = FetchType.LAZY)
	public Set<Mechanism> getMechanisms() {
		return mechanisms;
	}

	public void setMechanisms(Set<Mechanism> mechanisms) {
		this.mechanisms = mechanisms;
	}
	

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
//		Set<Promotion> promotions = getPromotions();
//		if (promotions != null) {
//			for (Promotion promotion : promotions) {
//				promotion.getProductCategories().remove(this);
//			}
//		}
	}


	
}