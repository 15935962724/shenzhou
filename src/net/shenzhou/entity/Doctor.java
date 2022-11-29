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

import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.interceptor.DoctorInterceptor;
import net.shenzhou.interceptor.UserInterceptor;

import org.hibernate.annotations.OrderBy;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.NotEmpty;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 医生
 * @author wsr
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "xx_doctor",uniqueConstraints = {@UniqueConstraint(columnNames="mobile")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_doctor_sequence")
public class Doctor extends BaseEntity {

	
	/** "身份信息"参数名称 */
	public static final String PRINCIPAL_ATTRIBUTE_NAME = DoctorInterceptor.class.getName() + ".PRINCIPAL";

	/** "用户名"Cookie名称 */
	public static final String USERNAME_COOKIE_NAME = "username";

	/** 会员注册项值属性名称前缀 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";
	
	public enum Status {

		/**无操作**/
		noOperation,
		
		/**允许**/
		allow,
		
		/**拒绝**/
		refuse
		
	}
	
	/** 用户名 */
	private String username;
	
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
	
	/**参加工作日期*/
	private Date workDate;
	
	/**民族**/
	private String nation;
	
	/** 密码 */
	private String password;
	
	/** 现居住地址 */
	private String address;

	/** 现居住地区 */
	private Area area;
	
	/** 籍贯 */
	private Area birthplace;
	
	/** 籍贯详细地址 */
	private String birthplaceAddress;

	/** 手机 */
	private String mobile;

    /**从业年限*/
	private Integer year;
	
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
	
	/**诊次*/
	private Integer second; 
	
	/**机构*/         
	private Mechanism mechanism;
	
	/** 安全密匙 */
	private SafeKey safeKey;
	
	/**个人账户**/
	private BigDecimal personageAccount;
	
	/**机构账户**/
	private BigDecimal organizationAccount;
	
	/**支付密码**/
	private String paymentPassWord;
	
	/**医师分类**/
	private DoctorCategory doctorCategory;
	
	/** 积分 */
	private Long point;
	
	/**审核状态**/
	private Status status;
	
	/**审核说明**/
	private String statusExplain;
	
	/**上班时间**/
	private String startTime;
	
	/**下班时间**/
	private String endTime;
	
	/**是否实名认证**/
	private Boolean isReal;
	
	/**设备token**/
	private String device_tokens;
	
	/**医生的微信信息**/
	private DoctorWx doctorWx;

	/** 是否锁定 */
	private Boolean isLocked;

	/** 连续登录失败次数 */
	private Integer loginFailureCount;

	/** 锁定日期 */
	private Date lockedDate;

	/** 最后登录IP */
	private String loginIp;

	/** 最后登录日期 */
	private Date loginDate;
	
	/**身份证(正面)**/
	private String idCardFrontImg;
	
	/**身份证(反面)**/
	private String idCardReverseImg;
	
	/**回访**/
	private List<Visit> visits = new ArrayList<Visit>();
	
	/**评估报告**/
	private List<AssessReport> assessReport = new ArrayList<AssessReport>();
	
	/**评估报告**/
	private List<AssessReport> reassessReports = new ArrayList<AssessReport>();
	
	/**医生机构关系**/
	private List<DoctorMechanismRelation> doctorMechanismRelations = new ArrayList<DoctorMechanismRelation>();
	
	/**绑定的银行卡**/
	private List<BankCard> bankCards = new ArrayList<BankCard>();
	
	/**提现记录明细**/
	private List<WithdrawDeposit> withdrawDeposits = new ArrayList<WithdrawDeposit>();
	
	/**账单**/
	private List<Bill> bills = new ArrayList<Bill>();
	
	/**项目*/
	private List<Project> projects = new ArrayList<Project>();
	
	/** 资格证书 */
	private List<DoctorImage> doctorImages = new ArrayList<DoctorImage>();
	
	/**评价*/
//	private List<Evaluate> evaluates = new ArrayList<Evaluate>();
	
    /**工作日*/
	private List<WorkDay> workDays = new ArrayList<WorkDay>();
	
	/**患者的评估报告是否授权某个医生**/
	private List<AssessReport> assessReports = new ArrayList<AssessReport>();
	
	/**评估报告授权某个医生**/
	private List<DoctorAssessReport> doctorAssessReport = new ArrayList<DoctorAssessReport>();
	
	/**康护计划**/
	private List<RecoveryPlan> recoveryPlans = new ArrayList<RecoveryPlan>();
	
	/**技师评估报告**/
	private List<AssessReport> recoveryAssessReports = new ArrayList<AssessReport>();
	
	/**授权*/
	private List<GrantRight> grantRights = new ArrayList<GrantRight>();
	
	/**消息**/
	private List<Information> informations = new ArrayList<Information>();
	
	/**订单**/
	private Set<Order> orders = new HashSet<Order>();
	
	/**粉丝(用户)**/
	private List<Member> members = new ArrayList<Member>();
	
	/**患者**/
	private List<Member> parents = new ArrayList<Member>();
	
	/**医生积分日志**/
	private List<DoctorPointLog> doctorPointLogs = new ArrayList<DoctorPointLog>();
	
	/**医生回访信息**/
	private List<VisitMessage> visitMessages = new ArrayList<VisitMessage>();
	
	/**医生职称关系**/
	private List<DoctorCategoryRelation> doctorCategoryRelations = new ArrayList<DoctorCategoryRelation>();
	
	/**用户积分日志**/
	private List<MemberPointLog> memberPointLogs = new ArrayList<MemberPointLog>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	/**
	 * 获取密码
	 * 
	 * @return 密码
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[^\\s&\"<>]+$")
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}
	
	/**
	 * 设置密码
	 * 
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPaymentPassWord() {
		return paymentPassWord;
	}

	public void setPaymentPassWord(String paymentPassWord) {
		this.paymentPassWord = paymentPassWord;
	}

	
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public DoctorCategory getDoctorCategory() {
		return doctorCategory;
	}

	public void setDoctorCategory(DoctorCategory doctorCategory) {
		this.doctorCategory = doctorCategory;
	}
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<AssessReport> getAssessReport() {
		return assessReport;
	}

	public void setAssessReport(List<AssessReport> assessReport) {
		this.assessReport = assessReport;
	}
	
	/**
	 * 获取安全密匙
	 * 
	 * @return 安全密匙
	 */
	@Embedded
	public SafeKey getSafeKey() {
		return safeKey;
	}

	/**
	 * 设置安全密匙
	 * 
	 * @param safeKey
	 *            安全密匙
	 */
	public void setSafeKey(SafeKey safeKey) {
		this.safeKey = safeKey;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getStatusExplain() {
		return statusExplain;
	}

	public void setStatusExplain(String statusExplain) {
		this.statusExplain = statusExplain;
	}

	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<BankCard> getBankCards() {
		return bankCards;
	}

	public void setBankCards(List<BankCard> bankCards) {
		this.bankCards = bankCards;
	}
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<WithdrawDeposit> getWithdrawDeposits() {
		return withdrawDeposits;
	}

	public void setWithdrawDeposits(List<WithdrawDeposit> withdrawDeposits) {
		this.withdrawDeposits = withdrawDeposits;
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
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@Lob
	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	@JsonProperty
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
	@NotNull
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	@Column(nullable = false, precision = 12, scale = 1,columnDefinition="double(10,1) default '0.0'")
	public Double getScoreSort() {
		return scoreSort;
	}

	public void setScoreSort(Double scoreSort) {
		this.scoreSort = scoreSort;
	}
	
	public BigDecimal getPersonageAccount() {
		return personageAccount;
	}

	public void setPersonageAccount(BigDecimal personageAccount) {
		this.personageAccount = personageAccount;
	}

	public BigDecimal getOrganizationAccount() {
		return organizationAccount;
	}

	public void setOrganizationAccount(BigDecimal organizationAccount) {
		this.organizationAccount = organizationAccount;
	}

	@JsonProperty
	@NotNull
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
	@NotNull
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
	@NotNull
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	@Column(nullable = false, precision = 12, scale = 1,columnDefinition="double(10,1) default '0.0'")
	public Double getCommunicateSort() {
		return communicateSort;
	}

	public void setCommunicateSort(Double communicateSort) {
		this.communicateSort = communicateSort;
	}

	@JsonProperty
	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Mechanism getMechanism() {
		return mechanism;
	}

	public void setMechanism(Mechanism mechanism) {
		this.mechanism = mechanism;
	}

	@JsonProperty
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	
	/**
	 * 获取积分
	 * 
	 * @return 积分
	 */
	@NotNull(groups = Save.class)
	@Min(0)
	@Column(nullable = false)
	public Long getPoint() {
		return point;
	}

	/**
	 * 设置积分
	 * 
	 * @param point
	 *            积分
	 */
	public void setPoint(Long point) {
		this.point = point;
	}

	public Boolean getIsReal() {
		return isReal;
	}

	public void setIsReal(Boolean isReal) {
		this.isReal = isReal;
	}

	public String getDevice_tokens() {
		return device_tokens;
	}

	public void setDevice_tokens(String device_tokens) {
		this.device_tokens = device_tokens;
	}

	@OneToOne(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public DoctorWx getDoctorWx() {
		return doctorWx;
	}

	public void setDoctorWx(DoctorWx doctorWx) {
		this.doctorWx = doctorWx;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getIdCardFrontImg() {
		return idCardFrontImg;
	}

	public void setIdCardFrontImg(String idCardFrontImg) {
		this.idCardFrontImg = idCardFrontImg;
	}

	public String getIdCardReverseImg() {
		return idCardReverseImg;
	}

	public void setIdCardReverseImg(String idCardReverseImg) {
		this.idCardReverseImg = idCardReverseImg;
	}

	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Project> getProjects() {
		return projects;
	}
	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Valid
	@ElementCollection
	@CollectionTable(name = "xx_doctor_doctor_image")
	public List<DoctorImage> getDoctorImages() {
		return doctorImages;
	}

	public void setDoctorImages(List<DoctorImage> doctorImages) {
		this.doctorImages = doctorImages;
	}

//	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//	public List<Evaluate> getEvaluates() {
//		return evaluates;
//	}
//
//	public void setEvaluates(List<Evaluate> evaluates) {
//		this.evaluates = evaluates;
//	}

	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<WorkDay> getWorkDays() {
		return workDays;
	}

	public void setWorkDays(List<WorkDay> workDays) {
		this.workDays = workDays;
	}
	
	@OneToMany(mappedBy = "auditDoctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<AssessReport> getAssessReports() {
		return assessReports;
	}

	public void setAssessReports(List<AssessReport> assessReports) {
		this.assessReports = assessReports;
	}
	
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<DoctorAssessReport> getDoctorAssessReport() {
		return doctorAssessReport;
	}

	public void setDoctorAssessReport(List<DoctorAssessReport> doctorAssessReport) {
		this.doctorAssessReport = doctorAssessReport;
	}

	@OneToMany(mappedBy = "recoveryDoctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RecoveryPlan> getRecoveryPlans() {
		return recoveryPlans;
	}

	public void setRecoveryPlans(List<RecoveryPlan> recoveryPlans) {
		this.recoveryPlans = recoveryPlans;
	}
	@ManyToMany(mappedBy = "recoveryDoctors", fetch = FetchType.LAZY)
	public List<AssessReport> getRecoveryAssessReports() {
		return recoveryAssessReports;
	}

	public void setRecoveryAssessReports(List<AssessReport> recoveryAssessReports) {
		this.recoveryAssessReports = recoveryAssessReports;
	}

	@ManyToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
	public List<GrantRight> getGrantRights() {
		return grantRights;
	}

	public void setGrantRights(List<GrantRight> grantRights) {
		this.grantRights = grantRights;
	}
	
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@OrderBy(clause = "createDate desc")  
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Information> getInformations() {
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}
	
	@OneToMany(mappedBy = "redoctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<AssessReport> getReassessReports() {
		return reassessReports;
	}

	public void setReassessReports(List<AssessReport> reassessReports) {
		this.reassessReports = reassessReports;
	}
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	@ManyToMany(mappedBy = "doctors", fetch = FetchType.LAZY)
	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	@ManyToMany(mappedBy = "patientDoctor", fetch = FetchType.LAZY)
	public List<Member> getParents() {
		return parents;
	}

	public void setParents(List<Member> parents) {
		this.parents = parents;
	}

	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<DoctorPointLog> getDoctorPointLogs() {
		return doctorPointLogs;
	}

	public void setDoctorPointLogs(List<DoctorPointLog> doctorPointLogs) {
		this.doctorPointLogs = doctorPointLogs;
	}

	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<VisitMessage> getVisitMessages() {
		return visitMessages;
	}

	public void setVisitMessages(List<VisitMessage> visitMessages) {
		this.visitMessages = visitMessages;
	}
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<DoctorMechanismRelation> getDoctorMechanismRelations() {
		return doctorMechanismRelations;
	}

	public void setDoctorMechanismRelations(
			List<DoctorMechanismRelation> doctorMechanismRelations) {
		this.doctorMechanismRelations = doctorMechanismRelations;
	}
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<DoctorCategoryRelation> getDoctorCategoryRelations() {
		return doctorCategoryRelations;
	}

	public void setDoctorCategoryRelations(
			List<DoctorCategoryRelation> doctorCategoryRelations) {
		this.doctorCategoryRelations = doctorCategoryRelations;
	}

	@OneToMany(mappedBy = "recommendDoctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<MemberPointLog> getMemberPointLogs() {
		return memberPointLogs;
	}

	public void setMemberPointLogs(List<MemberPointLog> memberPointLogs) {
		this.memberPointLogs = memberPointLogs;
	}

	@Transient
	public DoctorAssessReport getDoctorAssessReports(AssessReport assessReport){
		for (DoctorAssessReport doctorAssessReport : this.getDoctorAssessReport()){
			if (doctorAssessReport.getAssessReport().equals(assessReport)) {
				return doctorAssessReport;
			}
		}
		return null;
	}

	@Transient
	public DoctorMechanismRelation getDoctorMechanismRelation(Mechanism mechanism){
		for(DoctorMechanismRelation doctorMechanismRelation : this.getDoctorMechanismRelations()){
			if (doctorMechanismRelation.getMechanism().equals(mechanism)) {
				return doctorMechanismRelation;
			}
		}
		return null;
	}
	
	@Transient
	public List<DoctorMechanismRelation> getDoctorMechanismRelations(Audit audit){
		List<DoctorMechanismRelation> doctorMechanismRelationList = new ArrayList<DoctorMechanismRelation>();
		for(DoctorMechanismRelation doctorMechanismRelation : this.getDoctorMechanismRelations()){
			if(doctorMechanismRelation.getAudit().equals(audit)){
				doctorMechanismRelationList.add(doctorMechanismRelation);
			}
		}
		return doctorMechanismRelationList;
	}
	
	//获取医生当选择的机构(默认机构)
	@Transient
	public DoctorMechanismRelation getDefaultDoctorMechanismRelation(){
		for(DoctorMechanismRelation doctorMechanismRelation : this.getDoctorMechanismRelations()){
			if(doctorMechanismRelation.getDefaultMechanism()){
				return doctorMechanismRelation;
			}
		}
		return null;
	}
	
	//获取医生是否在一个机构审核通过
	@Transient
	public Boolean getIsDoctorStatus(){
		for(DoctorMechanismRelation doctorMechanismRelation : this.getDoctorMechanismRelations()){
			if(doctorMechanismRelation.getAudit().equals(Audit.succeed)){
				return true;
			}
		}
		return false;
	}
	
	//获取医生是否开通预约功能
	@Transient
	public Boolean getIsAbout(){
		for(DoctorMechanismRelation doctorMechanismRelation : this.getDoctorMechanismRelations()){
			if(doctorMechanismRelation.getIsAbout()){
				return true;
			}
		}
		return false;
	}
	
}
