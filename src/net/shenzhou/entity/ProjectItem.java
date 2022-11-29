/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.Project.ServiceGroup;

/**
 * 
* @ClassName: ProjectItem 
* @Description: TODO(项目项) 
* @author wsr
* @date 2018-1-2 11:42:58
 */
@Entity
@Table(name = "xx_project_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_project_item_sequence")
public class ProjectItem extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -921227985228517625L;
	
	/**项目服务价格*/
	private BigDecimal price;
	
	/**项目服务时长*/
	private Integer time;

	/**服务方式*/
	private Mode mode;
	
	/**服务群体**/
	private ServiceGroup serviceGroup;

	/**服务项目**/
	private Project project;
	
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
	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@JsonProperty
	public ServiceGroup getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(ServiceGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
	
}
