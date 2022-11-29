/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.PatientMechanism.HealthType;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 机构
 * @author wsr
 *
 */
@Entity
@Table(name = "xx_mechanism")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_mechanism_sequence")
public class Mechanism extends BaseEntity {

	private static final long serialVersionUID = 1533130686714725835L;
	
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


	public enum ServerStatus {

		/**运行中**/
		function,
		
		/**终止服务**/
		termination
		
	}
	
	/** 机构名称 */
	private String name;
	
	/** 机构logo图 */
	private String logo;
	
	/** 详细地址 */
	private String address;

	/** 经度 */
	private Double longitude;
	
	/** 纬度 */
	private Double latitude;
	
	/**评分*/
	private Double scoreSort;
	
	/**诊次*/
	private Integer second;
	/**距离 */
	private int distance;
	
	/**联系人*/
	private String contacts;
	
	/** 电话(座机) */
	private String phone;

	/** 手机 */
	private String mobile;

	/** 介绍 */
	private String introduce;
	
	/** 介绍 图片*/
	private String introduceImg;

	/** 地区 */
	private Area area;
	
	/**机构管理员*/
//	private User user;
//	
	/**机构证件(企业认证资料)**/
	private Certificates certificates;
	
	/**机构参数**/
	private MechanismSetup mechanismSetup ;
	
	/** 机构分类 */
	private MechanismCategory mechanismCategory;
	
	/** 机构等级 */
	private MechanismRank mechanismRank;
	
	/**工作时间**/
	private WorkDate workDate;
	
	/**使用周期开始时间**/
	private Date startDate;
	
	/**使用周期结束时间**/
	private Date endDate;
	
	/**服务状态**/
	private ServerStatus serverStatus;
	
	/**是否认证**/
	private Boolean isAuthentication;
	
	/**回访**/
	private List<Visit> visits = new ArrayList<Visit>();
	
	/**机构的评估报告**/
	private List<AssessReport> assessReports = new ArrayList<AssessReport>();
	
	/**工作时间**/
	private List<WorkDayItem> workDayItems = new ArrayList<WorkDayItem>();
	
	/**医生机构关系**/
	private List<DoctorMechanismRelation> doctorMechanismRelation = new ArrayList<DoctorMechanismRelation>();	
	
	/** 机构图片 */
	private List<MechanismImage> mechanismImages = new ArrayList<MechanismImage>();
	
	/**医师*/
	private List<Doctor> doctors = new ArrayList<Doctor>();
	
	/**项目**/
	private List<Project> projects = new ArrayList<Project>();
	
	/**账单**/
	private List<Bill> bills = new ArrayList<Bill>();
	
	/**会员**/
	private Set<Member> members = new HashSet<Member>();
	
//	/**患者**/
//	private Set<Member> patientMembers = new HashSet<Member>();
	
	/**经营模式**/
	private Set<Management> managements = new HashSet<Management>();
	
	
	/**订单**/
	private Set<Order> orders = new HashSet<Order>();
	
	/**项目分类*/
	private List<ServerProjectCategory> serverProjectCategorys = new ArrayList<ServerProjectCategory>();
	
	/**充值记录**/
	private List<RechargeLog> rechargeLogs = new ArrayList<RechargeLog>();
	
	/**用户积分日志**/
//	private List<MemberPointLog> memberPointLogs = new ArrayList<MemberPointLog>();
	
	/**医生积分日志**/
	private List<DoctorPointLog> doctorPointLogs = new ArrayList<DoctorPointLog>();
	
	/**机构的所有回访信息**/
	private List<VisitMessage> visitMessages = new ArrayList<VisitMessage>();
	
	/**机构的所有康护计划**/
	private List<RecoveryPlan> recoveryPlans = new ArrayList<RecoveryPlan>();
	
	/**机构的所有康护记录**/
	private List<RecoveryRecord> recoveryRecords = new ArrayList<RecoveryRecord>();
	
	/**机构管理员**/
	private Set<User> users = new HashSet<User>();
	
	/**机构的角色**/
	private Set<MechanismRole> mechanismRoles = new HashSet<MechanismRole>();
	
	/**机构的服务时间**/
	private Set<MechanismServerTime> mechanismServerTimes = new HashSet<MechanismServerTime>();
	
	/**机构的医生职级**/
	private Set<DoctorCategory> doctorCategorys = new HashSet<DoctorCategory>();
	
	/**机构发放的优惠券**/
	private Set<Coupon> coupons = new HashSet<Coupon>();
	
	/**消息**/
	private List<Information> informations = new ArrayList<Information>();
	
	/**操作记录**/
	private Set<MechanismLog> mechanismLogs = new HashSet<MechanismLog>();
	
	/** add wsr 余额  2018-3-19 15:07:05*/
	private Set<Balance> blances = new HashSet<Balance>();
	
	/** add wsr 预存款  2018-3-19 15:07:09*/
	private Set<Deposit> deposits = new HashSet<Deposit>();
	
	/** add fl 收款单  2018-3-20*/
	private Set<Payment> payments = new HashSet<Payment>();
	
	/** add fl 收款单  2018-3-20*/
	private Set<Refunds> refunds = new HashSet<Refunds>();
	
	/**add wsr 用户积分日志 2018-3-22 14:37:40**/
	private List<MemberPointLog> memberPointLogs = new ArrayList<MemberPointLog>();
	
	/**add wsr 患者和机构的关系  2018-3-23 15:46:07**/
	private List<PatientMechanism> patientMechanisms = new ArrayList<PatientMechanism>();
	
	/**机构可使用的优惠券**/
	private Set<Coupon> mechanismCoupons = new HashSet<Coupon>();
	
	
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
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JsonProperty
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@JsonProperty
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
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

	@JsonProperty
	@Column(nullable=false,columnDefinition="Integer default 0")
	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	@JsonProperty
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@JsonProperty
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonProperty
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

	@OneToOne(fetch = FetchType.EAGER)	
	public WorkDate getWorkDate() {
		return workDate;
	}

	public void setWorkDate(WorkDate workDate) {
		this.workDate = workDate;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ServerStatus getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(ServerStatus serverStatus) {
		this.serverStatus = serverStatus;
	}

	public Boolean getIsAuthentication() {
		return isAuthentication;
	}

	public void setIsAuthentication(Boolean isAuthentication) {
		this.isAuthentication = isAuthentication;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	@Valid
	@ElementCollection
	@CollectionTable(name = "xx_mechanism_mechanism_image")
	public List<MechanismImage> getMechanismImages() {
		return mechanismImages;
	}

	public void setMechanismImages(List<MechanismImage> mechanismImages) {
		this.mechanismImages = mechanismImages;
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

	public void setArea(Area area) {
		this.area = area;
	}

//	@OneToOne(fetch = FetchType.LAZY)
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	@OneToOne(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Certificates getCertificates() {
		return certificates;
	}

	public void setCertificates(Certificates certificates) {
		this.certificates = certificates;
	}

	@OneToOne(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public MechanismSetup getMechanismSetup() {
		return mechanismSetup;
	}

	public void setMechanismSetup(MechanismSetup mechanismSetup) {
		this.mechanismSetup = mechanismSetup;
	}
	
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public MechanismCategory getMechanismCategory() {
		return mechanismCategory;
	}

	public void setMechanismCategory(MechanismCategory mechanismCategory) {
		this.mechanismCategory = mechanismCategory;
	}

	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public MechanismRank getMechanismRank() {
		return mechanismRank;
	}

	public void setMechanismRank(MechanismRank mechanismRank) {
		this.mechanismRank = mechanismRank;
	}

	
	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<AssessReport> getAssessReports() {
		return assessReports;
	}

	public void setAssessReports(List<AssessReport> assessReports) {
		this.assessReports = assessReports;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Bill> getBills() {
		return bills;
	}
	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	/**
	 * 机构会员
	 * @return
	 */
	@ManyToMany(mappedBy = "memberMechanisms", fetch = FetchType.LAZY)
	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}
	
	
	/**
	 * 机构患者
	 * @return
	 */
//	@ManyToMany(mappedBy = "patientMemberMechanisms", fetch = FetchType.LAZY)
//	public Set<Member> getPatientMembers() {
//		return patientMembers;
//	}
//
//	public void setPatientMembers(Set<Member> patientMembers) {
//		this.patientMembers = patientMembers;
//	}
	
	
	@ManyToMany(mappedBy = "mechanisms", fetch = FetchType.LAZY)
	public Set<Management> getManagements() {
		return managements;
	}



	public void setManagements(Set<Management> managements) {
		this.managements = managements;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY)
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<ServerProjectCategory> getServerProjectCategorys() {
		return serverProjectCategorys;
	}

	public void setServerProjectCategorys(
			List<ServerProjectCategory> serverProjectCategorys) {
		this.serverProjectCategorys = serverProjectCategorys;
	}
	
	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RechargeLog> getRechargeLogs() {
		return rechargeLogs;
	}

	public void setRechargeLogs(List<RechargeLog> rechargeLogs) {
		this.rechargeLogs = rechargeLogs;
	}

	
	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<DoctorPointLog> getDoctorPointLogs() {
		return doctorPointLogs;
	}

	public void setDoctorPointLogs(List<DoctorPointLog> doctorPointLogs) {
		this.doctorPointLogs = doctorPointLogs;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<VisitMessage> getVisitMessages() {
		return visitMessages;
	}

	public void setVisitMessages(List<VisitMessage> visitMessages) {
		this.visitMessages = visitMessages;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RecoveryPlan> getRecoveryPlans() {
		return recoveryPlans;
	}

	public void setRecoveryPlans(List<RecoveryPlan> recoveryPlans) {
		this.recoveryPlans = recoveryPlans;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RecoveryRecord> getRecoveryRecords() {
		return recoveryRecords;
	}

	public void setRecoveryRecords(List<RecoveryRecord> recoveryRecords) {
		this.recoveryRecords = recoveryRecords;
	}
	
	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<DoctorMechanismRelation> getDoctorMechanismRelation() {
		return doctorMechanismRelation;
	}

	public void setDoctorMechanismRelation(
			List<DoctorMechanismRelation> doctorMechanismRelation) {
		this.doctorMechanismRelation = doctorMechanismRelation;
	}
	
	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<WorkDayItem> getWorkDayItems() {
		return workDayItems;
	}

	public void setWorkDayItems(List<WorkDayItem> workDayItems) {
		this.workDayItems = workDayItems;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	
	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<MechanismRole> getMechanismRoles() {
		return mechanismRoles;
	}

	public void setMechanismRoles(Set<MechanismRole> mechanismRoles) {
		this.mechanismRoles = mechanismRoles;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<MechanismServerTime> getMechanismServerTimes() {
		return mechanismServerTimes;
	}

	public void setMechanismServerTimes(
			Set<MechanismServerTime> mechanismServerTimes) {
		this.mechanismServerTimes = mechanismServerTimes;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<DoctorCategory> getDoctorCategorys() {
		return doctorCategorys;
	}

	public void setDoctorCategorys(Set<DoctorCategory> doctorCategorys) {
		this.doctorCategorys = doctorCategorys;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY)
	public List<Information> getInformations() {
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}
	
	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY)
	public Set<MechanismLog> getMechanismLogs() {
		return mechanismLogs;
	}

	public void setMechanismLogs(Set<MechanismLog> mechanismLogs) {
		this.mechanismLogs = mechanismLogs;
	}

	/**
	 * 获取余额  
	 * @return blances blances  
	 */
	@OneToMany(mappedBy="mechanism",fetch=FetchType.LAZY)
	public Set<Balance> getBlances() {
		return blances;
	}

	/**
	 * 获取预存款  
	 * @return deposits deposits  
	 */
	@OneToMany(mappedBy="mechanism",fetch=FetchType.LAZY)
	public Set<Deposit> getDeposits() {
		return deposits;
	}

	/**
	 * 设置余额  
	 * @return blances blances  
	 */
	public void setBlances(Set<Balance> blances) {
		this.blances = blances;
	}

	/**
	 * 设置预存款   
	 * @param deposits deposits  
	 */
	public void setDeposits(Set<Deposit> deposits) {
		this.deposits = deposits;
	}
	
	@OneToMany(mappedBy="mechanism",fetch=FetchType.LAZY)
	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	@OneToMany(mappedBy="mechanism",fetch=FetchType.LAZY)
	public Set<Refunds> getRefunds() {
		return refunds;
	}

	public void setRefunds(Set<Refunds> refunds) {
		this.refunds = refunds;
	}

	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<MemberPointLog> getMemberPointLogs() {
		return memberPointLogs;
	}

	public void setMemberPointLogs(List<MemberPointLog> memberPointLogs) {
		this.memberPointLogs = memberPointLogs;
	}
	
	@OneToMany(mappedBy = "mechanism", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<PatientMechanism> getPatientMechanisms() {
		return patientMechanisms;
	}

	public void setPatientMechanisms(List<PatientMechanism> patientMechanisms) {
		this.patientMechanisms = patientMechanisms;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_mechanism_coupon")
	public Set<Coupon> getMechanismCoupons() {
		return mechanismCoupons;
	}

	public void setMechanismCoupons(Set<Coupon> mechanismCoupons) {
		this.mechanismCoupons = mechanismCoupons;
	}

	/**
	 * 是否已到期
	 * 
	 * @return 是否已到期
	 */
	@Transient
	public boolean isExpired() {
		return getEndDate() != null && new Date().after(getEndDate());
	}
	
	/**
	 * 获取源图片
	 * 
	 * @return 源图片
	 */
	@JsonProperty
	@Transient
	public String getSourceImg() {
		if (getMechanismImages() != null && !getMechanismImages().isEmpty()) {
			return getMechanismImages().get(0).getSource();
		}
		return null;
	}
	
	@Transient
	public List<DoctorMechanismRelation> getDoctorMechanismRelations(Audit audit){
		List<DoctorMechanismRelation> doctorMechanismRelationList = new ArrayList<DoctorMechanismRelation>();
		for(DoctorMechanismRelation doctorMechanismRelation : this.getDoctorMechanismRelation()){
			if(doctorMechanismRelation.getAudit().equals(audit)&&!doctorMechanismRelation.getIsSystem()){
				doctorMechanismRelationList.add(doctorMechanismRelation);
			}
		}
		return doctorMechanismRelationList;
	}
	
	
	@Transient
	public String getSystemDoctorName(){
		for(DoctorMechanismRelation doctorMechanismRelation : this.getDoctorMechanismRelation()){
			if(doctorMechanismRelation.getIsSystem()){
				return doctorMechanismRelation.getDoctor().getName();
			}
		}
		return null;
	}
	
	@Transient
	public Doctor getSystemDoctor(){
		for(DoctorMechanismRelation doctorMechanismRelation : this.getDoctorMechanismRelation()){
			if(doctorMechanismRelation.getIsSystem()){
				return doctorMechanismRelation.getDoctor();
			}
		}
		return null;
	}
	
	
	@Transient
	public Set<Member> getPatients(){
		Set<Member> patients = new HashSet<Member>() ;
		for (PatientMechanism patientMechanism : this.getPatientMechanisms()) {
			patients.add(patientMechanism.getPatient());
		}
		return patients;
	}
	
	@Transient
	public Set<PatientMechanism> getPatientMechanisms(HealthType healthType){
		Set<PatientMechanism> patientMechanisms = new HashSet<PatientMechanism>() ;
		for (PatientMechanism patientMechanism : this.getPatientMechanisms()) {
			if (patientMechanism.getHealthType().equals(healthType)) {
				patientMechanisms.add(patientMechanism);
			}
		}
		return patientMechanisms;
	}

	public int getDistance() {
		return distance;
	}
	@Transient
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	
	
}
