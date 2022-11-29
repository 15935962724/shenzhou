/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 2017年6月23日17:24:15
 * 机构证件
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_certificates")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_certificates_sequence")
public class Certificates extends BaseEntity {

	
	private static final long serialVersionUID = -6421860239387447716L;

	/** 企业名称 */
	private String title;

	/** 地区 */
	private Area area;

	/** 详细地址 */
	private String address;

	/** 证件图片*/
	private String certificatesImg;

	
	/** 机构法人代表 */
	private String name;

	/** 开户银行 */
	private String brank;
	
	/** 账号 */
	private String account;
	
	/** 机构 */
	private Mechanism mechanism;
	
	/** 机构证件图片 */
	private List<CertificatesImage> certificatesImages = new ArrayList<CertificatesImage>();
	
	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCertificatesImg() {
		return certificatesImg;
	}

	public void setCertificatesImg(String certificatesImg) {
		this.certificatesImg = certificatesImg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrank() {
		return brank;
	}

	public void setBrank(String brank) {
		this.brank = brank;
	}

	
	@OneToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Valid
	@ElementCollection
	@CollectionTable(name = "xx_certificates_certificates_image")
	public List<CertificatesImage> getCertificatesImages() {
		return certificatesImages;
	}

	public void setCertificatesImages(List<CertificatesImage> certificatesImages) {
		this.certificatesImages = certificatesImages;
	}

	
	
	
}