/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.shenzhou.entity.DoctorMechanismRelation.Audit;

/**
 * Entity - 医生医生职称关系表
 * fl_2017/9/11
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_doctor_category_relation")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_doctor_category_relation_sequence")
public class DoctorCategoryRelation extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -610713115330767861L;

	/**审核状态**/
	private Audit audit;
	
	/**医生**/
	private Doctor doctor;
	
	/**医生职称**/
	private DoctorCategory doctorCategory;
	
	/**审核说明**/
	private String statusExplain;
	
	/**医生当前选择的默认展示**/
	private Boolean defaultDoctorCategory;
	
	/**医生职称资质图**/
	private List<DoctorCategoryImage> doctorCategoryImages = new ArrayList<DoctorCategoryImage>();

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public DoctorCategory getDoctorCategory() {
		return doctorCategory;
	}

	public void setDoctorCategory(DoctorCategory doctorCategory) {
		this.doctorCategory = doctorCategory;
	}

	public String getStatusExplain() {
		return statusExplain;
	}

	public void setStatusExplain(String statusExplain) {
		this.statusExplain = statusExplain;
	}

	public Boolean getDefaultDoctorCategory() {
		return defaultDoctorCategory;
	}

	public void setDefaultDoctorCategory(Boolean defaultDoctorCategory) {
		this.defaultDoctorCategory = defaultDoctorCategory;
	}

	@Valid
	@JsonProperty
	@ElementCollection
	@CollectionTable(name = "xx_doctor_category_image")
	public List<DoctorCategoryImage> getDoctorCategoryImages() {
		return doctorCategoryImages;
	}

	public void setDoctorCategoryImages(
			List<DoctorCategoryImage> doctorCategoryImages) {
		this.doctorCategoryImages = doctorCategoryImages;
	}
}