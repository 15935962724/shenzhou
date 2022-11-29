/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import net.shenzhou.BigDecimalNumericFieldBridge;

import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 项目分类
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_server_project_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_server_project_category_sequence")
public class ServerProjectCategory extends OrderEntity {

	private static final long serialVersionUID = 5095521437302782717L;

	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/project/list";

	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".jhtml";
	
	public enum ServeType{
		
		/**诊治*/
		examine,
		
		/**评估*/
		assess
		
	}
	
	/** logo图片 */
	private String logo;
	
	/** 名称 */
	private String name;

	/** 详情图 **/
	private String introduceImg;
	
	/** 页面描述 */
	private String seoDescription;

	/** 树路径 */
	private String treePath;

	/** 层级 */
	private Integer grade;
	
	/**类型*/
	private ServeType serveType;
	
	/**机构**/
	private Mechanism mechanism;

	/** 上级分类 */
	private ServerProjectCategory parent;

	/** 下级分类 */
	private Set<ServerProjectCategory> children = new HashSet<ServerProjectCategory>();

	/** 项目(项目机构的关系) */
	private List<Project> projects = new ArrayList<Project>();
	
	/** 项目(项目平台的关系) */
	private List<Project> platformProjects = new ArrayList<Project>();

	/** 训练内容 */
	private List<DrillContent> drillContent = new ArrayList<DrillContent>();

	/**优惠券**/
	private Set<Coupon> coupons = new HashSet<Coupon>();
	
	@JsonProperty
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

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

	public String getIntroduceImg() {
		return introduceImg;
	}

	public void setIntroduceImg(String introduceImg) {
		this.introduceImg = introduceImg;
	}

	/**
	 * 获取页面描述
	 * 
	 * @return 页面描述
	 */
	@JsonProperty
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
	
	
	public ServeType getServeType() {
		return serveType;
	}

	public void setServeType(ServeType serveType) {
		this.serveType = serveType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	/**
	 * 获取上级分类
	 * 
	 * @return 上级分类
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public ServerProjectCategory getParent() {
		return parent;
	}

	/**
	 * 设置上级分类
	 * 
	 * @param parent
	 *            上级分类
	 */
	public void setParent(ServerProjectCategory parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级分类
	 * 
	 * @return 下级分类
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("order asc")
	public Set<ServerProjectCategory> getChildren() {
		return children;
	}

	/**
	 * 设置下级分类
	 * 
	 * @param children
	 *            下级分类
	 */
	public void setChildren(Set<ServerProjectCategory> children) {
		this.children = children;
	}

	/**
	 * 获取项目
	 * @return
	 */
	@OneToMany(mappedBy = "serverProjectCategory", fetch = FetchType.LAZY)
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	@OneToMany(mappedBy = "serverProjectCategory", fetch = FetchType.LAZY)
	public List<DrillContent> getDrillContent() {
		return drillContent;
	}

	public void setDrillContent(List<DrillContent> drillContent) {
		this.drillContent = drillContent;
	}

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_server_project_category_coupon")
	public Set<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

	@OneToMany(mappedBy = "parentServerProjectCategory", fetch = FetchType.LAZY)
	public List<Project> getPlatformProjects() {
		return platformProjects;
	}

	public void setPlatformProjects(List<Project> platformProjects) {
		this.platformProjects = platformProjects;
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

	

	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		if (getId() != null) {
			return PATH_PREFIX + "/" + getId() + PATH_SUFFIX;
		}
		return null;
	}

	
}
