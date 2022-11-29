/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import net.shenzhou.Setting;
import net.shenzhou.controller.app.PatientMemberController;
import net.shenzhou.entity.MemberAttribute.Type;
import net.shenzhou.interceptor.MemberInterceptor;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_member")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_member_sequence")
public class Member extends BaseEntity {

	private static final long serialVersionUID = 1533130686714725835L;

	/**康护状态**/
//    public enum	HealthType{
//    	/**在康护**/
//    	health,
//    	/**挂起**/
//    	hang,
//    	/**休疗程**/
//    	hughCourse,
//    	/**已康复**/
//    	alreadyRecovery
//    }
	
	/**障碍部位*/
	public enum Position{
		/**脑瘫*/
		brainParalysis,
		
		/**发育迟缓*/
		stunting,
		
		/**发育迟缓*/
		thickObstacle,
		
		/**其他*/
		other
		
		
	}
	
	/**日历*/
	public enum Calendar{
		
		/**阴历*/
		lunar,
		
		/**阳历*/
		cregorian
		
	}
	
	/**
	 * 性别
	 */
	public enum Gender {

		/** 男 */
		male,

		/** 女 */
		female
	}

	/** "身份信息"参数名称 */
	public static final String PRINCIPAL_ATTRIBUTE_NAME = MemberInterceptor.class.getName() + ".PRINCIPAL";

	/** "用户名"Cookie名称 */
	public static final String USERNAME_COOKIE_NAME = "username";

	/** 会员注册项值属性个数 */
	public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 10;

	/** 会员注册项值属性名称前缀 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

	/** 最大收藏商品数 */
	public static final Integer MAX_FAVORITE_COUNT = 10;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;
	
	/** 支付密码 */
	private String paymentPassword;

	/** E-mail */
	private String email;

	/** 积分 */
	private Long point;

	/** 消费金额 */
	private BigDecimal amount;

	/** 余额 */
	private BigDecimal balance;

	/** 是否启用 */
	private Boolean isEnabled;
	
	/** 是否默认患者*/
	private Boolean isDefault;

	/** 是否锁定 */
	private Boolean isLocked;

	/** 连续登录失败次数 */
	private Integer loginFailureCount;

	/** 锁定日期 */
	private Date lockedDate;

	/** 注册IP */
	private String registerIp;

	/** 最后登录IP */
	private String loginIp;

	/** 最后登录日期 */
	private Date loginDate;

	/** 姓名 */
	private String name;
	
	/**头像*/
	private String logo;

	/** 性别 */
	private Gender gender;

	/** 出生日期 */
	private Date birth;

	/** 日历 */
	private Calendar  calendar;
	
	/**民族**/
	private String nation;
	
	/**康护状态**/
//	private HealthType healthType;
	
	/** 现住详细地址 */
	private String address;

	/** 现住地区 */
	private Area nowArea;
	
	/** 邮编 */
	private String zipCode;

	/** 电话 */
	private String phone;

	/** 手机 */
	private String mobile;

	/** 医保卡号*/
	private String medicalInsuranceId;
	
	/** 经度 */
	private String longitude;
	
	/** 纬度 */
	private String latitude;
	
	/** 户籍地址 */
	private String householdAddress;
	
	/** QQ */
	private String cardQQ;
	
	/** 身份证号 */
	private String cardId;
	
	/** 微信号码*/
	private String cardWX;
	
	/** 备注 */
	private String remarks;
	
	/** 会员注册项值0 */
	private String attributeValue0;

	/** 会员注册项值1 */
	private String attributeValue1;

	/** 会员注册项值2 */
	private String attributeValue2;

	/** 会员注册项值3 */
	private String attributeValue3;

	/** 会员注册项值4 */
	private String attributeValue4;

	/** 会员注册项值5 */
	private String attributeValue5;

	/** 会员注册项值6 */
	private String attributeValue6;

	/** 会员注册项值7 */
	private String attributeValue7;

	/** 会员注册项值8 */
	private String attributeValue8;

	/** 会员注册项值9 */
	private String attributeValue9;

	/** 安全密匙 */
	private SafeKey safeKey;

	/** 籍贯 */
	private Area area;
	
	/** 籍贯地址 */
	private String areaAddress;

	/** 会员等级 */
	private MemberRank memberRank;

	/** 购物车 */
	private Cart cart;
	
	/**障碍部位*/
	private Position position;
	
	/**上级*/
	private Member parent;
	
	/**与监护人关系*/
	private Relationship relationship;
	
	/**是否实名认证**/
	private Boolean isReal;
	
	/**设备token**/
	private String device_tokens;
	
	/**回访**/
	private List<Visit> visits = new ArrayList<Visit>();
	
	/**患者*/
	private Set<Member> children = new HashSet<Member>();
	/** 订单 */
	private Set<Order> orders = new HashSet<Order>();
	
	/** 患者订单 */
	private Set<Order> patientOrders = new HashSet<Order>();

	/** 预存款 */
	private Set<Deposit> deposits = new HashSet<Deposit>();

	/** 收款单 */
	private Set<Payment> payments = new HashSet<Payment>();

	/** 优惠码 */
	private Set<CouponCode> couponCodes = new HashSet<CouponCode>();

	/** 收货地址 */
	private Set<Receiver> receivers = new HashSet<Receiver>();

	/** 评论 */
	private Set<Review> reviews = new HashSet<Review>();

	/** 咨询 */
	private Set<Consultation> consultations = new HashSet<Consultation>();

	/** 收藏商品 */
	private Set<Product> favoriteProducts = new HashSet<Product>();

	/** 到货通知 */
	private Set<ProductNotify> productNotifies = new HashSet<ProductNotify>();

	/** 接收的消息 */
	private Set<Message> inMessages = new HashSet<Message>();

	/** 发送的消息 */
	private Set<Message> outMessages = new HashSet<Message>();
	
	/** 用户所在的机构*/
	private Set<Mechanism> memberMechanisms = new HashSet<Mechanism>();
	
	/** 积分兑换订单 */
	private Set<ProductOrder> productOrders = new HashSet<ProductOrder>();
	
	
//	/** 患者所在的机构 */
//	private Set<Mechanism> patientMemberMechanisms = new HashSet<Mechanism>();

	/**评价*/
	private List<Evaluate> evaluates = new ArrayList<Evaluate>();
	
	/**授权*/
	private List<GrantRight> grantRights = new ArrayList<GrantRight>();
	
	/**授权详情*/
	private List<DoctorAssessReport> doctorAssessReports = new ArrayList<DoctorAssessReport>();
	
	/**消息**/
	private List<Information> informations = new ArrayList<Information>();
	
	/**患者的评估报告**/
	private List<AssessReport> assessReports = new ArrayList<AssessReport>(); 
	
	/**预约时间**/
	private List<WorkDayItem> workDayItems = new ArrayList<WorkDayItem>();
	
	/**关注医生**/
	private List<Doctor> doctors = new ArrayList<Doctor>();
	
	/**医生患者多对多关系**/
	private List<Doctor> patientDoctor = new ArrayList<Doctor>();
	
	/**充值记录(机构)**/
	private List<RechargeLog> rechargeLogs = new ArrayList<RechargeLog>();
	
	/**充值记录(平台)**/
	private List<PlatformRechargeLog> platformRechargeLogs = new ArrayList<PlatformRechargeLog>();
	
	/**用户积分日志**/
	private List<MemberPointLog> memberPointLogs = new ArrayList<MemberPointLog>();
	
	/**邀请人积分日志**/
	private List<MemberPointLog> recommendMemberLogs = new ArrayList<MemberPointLog>();
	
	/**监护人回访信息**/
	private List<VisitMessage> memberVisitMessages = new ArrayList<VisitMessage>();
	
	/**患者回访信息**/
	private List<VisitMessage> patientVisitMessages = new ArrayList<VisitMessage>();
	
	/**患者康护计划**/
	private List<RecoveryPlan> recoveryPlans = new ArrayList<RecoveryPlan>();
	
	/**患者康护记录**/
	private List<RecoveryRecord> recoveryRecords = new ArrayList<RecoveryRecord>();
	
	/**患者分组**/
	private Set<PatientGroup> patientGroups = new HashSet<PatientGroup>();
	
	/**患者关系**/
	private List<GroupPatient> groupPatient = new ArrayList<GroupPatient>();
	
	/** 余额   wsr add 2018-3-19 14:42:39*/
	private Set<Balance> balances = new HashSet<Balance>();
	
	/** 患者与机构关系  wsr add 2018-3-23 15:48:48 **/
	private List<PatientMechanism> patientMechanisms = new ArrayList<PatientMechanism>();
	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	@JsonProperty
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Column(nullable = false, unique = true, length = 100)
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取密码
	 * 
	 * @return 密码
	 */
	
	@Pattern(regexp = "^[^\\s&\"<>]+$")
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

	
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[^\\s&\"<>]+$")
	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

	/**
	 * 获取E-mail
	 * 
	 * @return E-mail
	 */
	
	@Email
	@Length(max = 200)
	public String getEmail() {
		return email;
	}

	/**
	 * 设置E-mail
	 * 
	 * @param email
	 *            E-mail
	 */
	public void setEmail(String email) {
		this.email = email;
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

	/**
	 * 获取消费金额
	 * 
	 * @return 消费金额
	 */
	@Column(nullable = false, precision = 27, scale = 6)
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置消费金额
	 * 
	 * @param amount
	 *            消费金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取余额
	 * 
	 * @return 余额
	 */
	@NotNull(groups = Save.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 27, scale = 6)
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * 设置余额
	 * 
	 * @param balance
	 *            余额
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * 获取是否启用
	 * 
	 * @return 是否启用
	 */
	@NotNull
	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * 设置是否启用
	 * 
	 * @param isEnabled
	 *            是否启用
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@JsonProperty
	@NotNull
	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * 获取是否锁定
	 * 
	 * @return 是否锁定
	 */
	@Column(nullable = false)
	public Boolean getIsLocked() {
		return isLocked;
	}

	/**
	 * 设置是否锁定
	 * 
	 * @param isLocked
	 *            是否锁定
	 */
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * 获取连续登录失败次数
	 * 
	 * @return 连续登录失败次数
	 */
	@Column(nullable = false)
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	/**
	 * 设置连续登录失败次数
	 * 
	 * @param loginFailureCount
	 *            连续登录失败次数
	 */
	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	/**
	 * 获取锁定日期
	 * 
	 * @return 锁定日期
	 */
	public Date getLockedDate() {
		return lockedDate;
	}

	/**
	 * 设置锁定日期
	 * 
	 * @param lockedDate
	 *            锁定日期
	 */
	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	/**
	 * 获取注册IP
	 * 
	 * @return 注册IP
	 */
	@Column(nullable = false, updatable = false)
	public String getRegisterIp() {
		return registerIp;
	}

	/**
	 * 设置注册IP
	 * 
	 * @param registerIp
	 *            注册IP
	 */
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	/**
	 * 获取最后登录IP
	 * 
	 * @return 最后登录IP
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * 设置最后登录IP
	 * 
	 * @param loginIp
	 *            最后登录IP
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * 获取最后登录日期
	 * 
	 * @return 最后登录日期
	 */
	public Date getLoginDate() {
		return loginDate;
	}

	/**
	 * 设置最后登录日期
	 * 
	 * @param loginDate
	 *            最后登录日期
	 */
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	/**
	 * 获取姓名
	 * 
	 * @return 姓名
	 */
	@JsonProperty
	@Length(max = 200)
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 * 
	 * @param name
	 *            姓名
	 */
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
	 * 获取性别
	 * 
	 * @return 性别
	 */
	@JsonProperty
	public Gender getGender() {
		return gender;
	}

	/**
	 * 设置性别
	 * 
	 * @param gender
	 *            性别
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * 获取出生日期
	 * 
	 * @return 出生日期
	 */
	@JsonProperty
	public Date getBirth() {
		return birth;
	}

	/**
	 * 设置出生日期
	 * 
	 * @param birth
	 *            出生日期
	 */
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	
	/**
	 * 获取日历
	 * 
	 * @return 出生日历
	 */
	@JsonProperty
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * 设置日历
	 * 
	 * @param calendar
	 *            日历
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
//	public HealthType getHealthType() {
//		return healthType;
//	}
//
//	public void setHealthType(HealthType healthType) {
//		this.healthType = healthType;
//	}

	

	/**
	 * 获取地址
	 * 
	 * @return 地址
	 */
	@Length(max = 200)
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 * 
	 * @param address
	 *            地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Area getNowArea() {
		return nowArea;
	}

	public void setNowArea(Area nowArea) {
		this.nowArea = nowArea;
	}

	/**
	 * 获取邮编
	 * 
	 * @return 邮编
	 */
	@Length(max = 200)
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 * 
	 * @param zipCode
	 *            邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取电话
	 * 
	 * @return 电话
	 */
	@Length(max = 200)
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置电话
	 * 
	 * @param phone
	 *            电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取手机
	 * 
	 * @return 手机
	 */
	@JsonProperty
	@Length(max = 200)
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 * 
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMedicalInsuranceId() {
		return medicalInsuranceId;
	}

	public void setMedicalInsuranceId(String medicalInsuranceId) {
		this.medicalInsuranceId = medicalInsuranceId;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getHouseholdAddress() {
		return householdAddress;
	}

	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}

	public String getCardQQ() {
		return cardQQ;
	}

	public void setCardQQ(String cardQQ) {
		this.cardQQ = cardQQ;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardWX() {
		return cardWX;
	}

	public void setCardWX(String cardWX) {
		this.cardWX = cardWX;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 获取会员注册项值0
	 * 
	 * @return 会员注册项值0
	 */
	@Length(max = 200)
	public String getAttributeValue0() {
		return attributeValue0;
	}

	/**
	 * 设置会员注册项值0
	 * 
	 * @param attributeValue0
	 *            会员注册项值0
	 */
	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	/**
	 * 获取会员注册项值1
	 * 
	 * @return 会员注册项值1
	 */
	@Length(max = 200)
	public String getAttributeValue1() {
		return attributeValue1;
	}

	/**
	 * 设置会员注册项值1
	 * 
	 * @param attributeValue1
	 *            会员注册项值1
	 */
	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	/**
	 * 获取会员注册项值2
	 * 
	 * @return 会员注册项值2
	 */
	@Length(max = 200)
	public String getAttributeValue2() {
		return attributeValue2;
	}

	/**
	 * 设置会员注册项值2
	 * 
	 * @param attributeValue2
	 *            会员注册项值2
	 */
	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	/**
	 * 获取会员注册项值3
	 * 
	 * @return 会员注册项值3
	 */
	@Length(max = 200)
	public String getAttributeValue3() {
		return attributeValue3;
	}

	/**
	 * 设置会员注册项值3
	 * 
	 * @param attributeValue3
	 *            会员注册项值3
	 */
	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	/**
	 * 获取会员注册项值4
	 * 
	 * @return 会员注册项值4
	 */
	@Length(max = 200)
	public String getAttributeValue4() {
		return attributeValue4;
	}

	/**
	 * 设置会员注册项值4
	 * 
	 * @param attributeValue4
	 *            会员注册项值4
	 */
	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	/**
	 * 获取会员注册项值5
	 * 
	 * @return 会员注册项值5
	 */
	@Length(max = 200)
	public String getAttributeValue5() {
		return attributeValue5;
	}

	/**
	 * 设置会员注册项值5
	 * 
	 * @param attributeValue5
	 *            会员注册项值5
	 */
	public void setAttributeValue5(String attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}

	/**
	 * 获取会员注册项值6
	 * 
	 * @return 会员注册项值6
	 */
	@Length(max = 200)
	public String getAttributeValue6() {
		return attributeValue6;
	}

	/**
	 * 设置会员注册项值6
	 * 
	 * @param attributeValue6
	 *            会员注册项值6
	 */
	public void setAttributeValue6(String attributeValue6) {
		this.attributeValue6 = attributeValue6;
	}

	/**
	 * 获取会员注册项值7
	 * 
	 * @return 会员注册项值7
	 */
	@Length(max = 200)
	public String getAttributeValue7() {
		return attributeValue7;
	}

	/**
	 * 设置会员注册项值7
	 * 
	 * @param attributeValue7
	 *            会员注册项值7
	 */
	public void setAttributeValue7(String attributeValue7) {
		this.attributeValue7 = attributeValue7;
	}

	/**
	 * 获取会员注册项值8
	 * 
	 * @return 会员注册项值8
	 */
	@Length(max = 200)
	public String getAttributeValue8() {
		return attributeValue8;
	}

	/**
	 * 设置会员注册项值8
	 * 
	 * @param attributeValue8
	 *            会员注册项值8
	 */
	public void setAttributeValue8(String attributeValue8) {
		this.attributeValue8 = attributeValue8;
	}

	/**
	 * 获取会员注册项值9
	 * 
	 * @return 会员注册项值9
	 */
	@Length(max = 200)
	public String getAttributeValue9() {
		return attributeValue9;
	}

	/**
	 * 设置会员注册项值9
	 * 
	 * @param attributeValue9
	 *            会员注册项值9
	 */
	public void setAttributeValue9(String attributeValue9) {
		this.attributeValue9 = attributeValue9;
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

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
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
	
	public String getAreaAddress() {
		return areaAddress;
	}

	public void setAreaAddress(String areaAddress) {
		this.areaAddress = areaAddress;
	}

	/**
	 * 获取会员等级
	 * 
	 * @return 会员等级
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public MemberRank getMemberRank() {
		return memberRank;
	}

	/**
	 * 设置会员等级
	 * 
	 * @param memberRank
	 *            会员等级
	 */
	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

	/**
	 * 获取购物车
	 * 
	 * @return 购物车
	 */
	@OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Cart getCart() {
		return cart;
	}

	/**
	 * 设置购物车
	 * 
	 * @param cart
	 *            购物车
	 */
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getParent() {
		return parent;
	}

	public void setParent(Member parent) {
		this.parent = parent;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Relationship getRelationship() {
		return relationship;
	}

	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
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

	
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Member> getChildren() {
		return children;
	}

	public void setChildren(Set<Member> children) {
		this.children = children;
	}

	
	
	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * 设置订单
	 * 
	 * @param orders
	 *            订单
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	
	@OneToMany(mappedBy = "patientMember", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Order> getPatientOrders() {
		return patientOrders;
	}

	public void setPatientOrders(Set<Order> patientOrders) {
		this.patientOrders = patientOrders;
	}

	/**
	 * 获取预存款
	 * 
	 * @return 预存款
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Deposit> getDeposits() {
		return deposits;
	}

	/**
	 * 设置预存款
	 * 
	 * @param deposits
	 *            预存款
	 */
	public void setDeposits(Set<Deposit> deposits) {
		this.deposits = deposits;
	}

	/**
	 * 获取收款单
	 * 
	 * @return 收款单
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Payment> getPayments() {
		return payments;
	}

	/**
	 * 设置收款单
	 * 
	 * @param payments
	 *            收款单
	 */
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * 获取优惠码
	 * 
	 * @return 优惠码
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<CouponCode> getCouponCodes() {
		return couponCodes;
	}

	/**
	 * 设置优惠码
	 * 
	 * @param couponCodes
	 *            优惠码
	 */
	public void setCouponCodes(Set<CouponCode> couponCodes) {
		this.couponCodes = couponCodes;
	}

	/**
	 * 获取收货地址
	 * 
	 * @return 收货地址
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("isDefault desc, createDate desc")
	public Set<Receiver> getReceivers() {
		return receivers;
	}

	/**
	 * 设置收货地址
	 * 
	 * @param receivers
	 *            收货地址
	 */
	public void setReceivers(Set<Receiver> receivers) {
		this.receivers = receivers;
	}

	/**
	 * 获取评论
	 * 
	 * @return 评论
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	public Set<Review> getReviews() {
		return reviews;
	}

	/**
	 * 设置评论
	 * 
	 * @param reviews
	 *            评论
	 */
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * 获取咨询
	 * 
	 * @return 咨询
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	public Set<Consultation> getConsultations() {
		return consultations;
	}

	/**
	 * 设置咨询
	 * 
	 * @param consultations
	 *            咨询
	 */
	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

	/**
	 * 获取收藏商品
	 * 
	 * @return 收藏商品
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_member_favorite_product")
	@OrderBy("createDate desc")
	public Set<Product> getFavoriteProducts() {
		return favoriteProducts;
	}

	/**
	 * 设置收藏商品
	 * 
	 * @param favoriteProducts
	 *            收藏商品
	 */
	public void setFavoriteProducts(Set<Product> favoriteProducts) {
		this.favoriteProducts = favoriteProducts;
	}

	/**
	 * 获取到货通知
	 * 
	 * @return 到货通知
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<ProductNotify> getProductNotifies() {
		return productNotifies;
	}

	/**
	 * 设置到货通知
	 * 
	 * @param productNotifies
	 *            到货通知
	 */
	public void setProductNotifies(Set<ProductNotify> productNotifies) {
		this.productNotifies = productNotifies;
	}

	/**
	 * 获取接收的消息
	 * 
	 * @return 接收的消息
	 */
	@OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Message> getInMessages() {
		return inMessages;
	}

	/**
	 * 设置接收的消息
	 * 
	 * @param inMessages
	 *            接收的消息
	 */
	public void setInMessages(Set<Message> inMessages) {
		this.inMessages = inMessages;
	}

	/**
	 * 获取发送的消息
	 * 
	 * @return 发送的消息
	 */
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Message> getOutMessages() {
		return outMessages;
	}

	/**
	 * 设置发送的消息
	 * 
	 * @param outMessages
	 *            发送的消息
	 */
	public void setOutMessages(Set<Message> outMessages) {
		this.outMessages = outMessages;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_member_mechanism")
	public Set<Mechanism> getMemberMechanisms() {
		return memberMechanisms;
	}

	public void setMemberMechanisms(Set<Mechanism> memberMechanisms) {
		this.memberMechanisms = memberMechanisms;
	}

//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "xx_patient_member_mechanism")
//	public Set<Mechanism> getPatientMemberMechanisms() {
//		return patientMemberMechanisms;
//	}
//
//	public void setPatientMemberMechanisms(Set<Mechanism> patientMemberMechanisms) {
//		this.patientMemberMechanisms = patientMemberMechanisms;
//	}

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Evaluate> getEvaluates() {
		return evaluates;
	}

	public void setEvaluates(List<Evaluate> evaluates) {
		this.evaluates = evaluates;
	}
	
	@OneToMany(mappedBy = "patientMember", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<GrantRight> getGrantRights() {
		return grantRights;
	}

	public void setGrantRights(List<GrantRight> grantRights) {
		this.grantRights = grantRights;
	}

	@OneToMany(mappedBy = "patientMember", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<DoctorAssessReport> getDoctorAssessReports() {
		return doctorAssessReports;
	}

	public void setDoctorAssessReports(List<DoctorAssessReport> doctorAssessReports) {
		this.doctorAssessReports = doctorAssessReports;
	}

	@ManyToMany(mappedBy = "member", fetch = FetchType.LAZY)
	public List<Information> getInformations() {
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}
	
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<AssessReport> getAssessReports() {
		return assessReports;
	}

	public void setAssessReports(List<AssessReport> assessReports) {
		this.assessReports = assessReports;
	}
	
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<WorkDayItem> getWorkDayItems() {
		return workDayItems;
	}

	public void setWorkDayItems(List<WorkDayItem> workDayItems) {
		this.workDayItems = workDayItems;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_member_doctor")
	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_patient_doctor")
	public List<Doctor> getPatientDoctor() {
		return patientDoctor;
	}

	public void setPatientDoctor(List<Doctor> patientDoctor) {
		this.patientDoctor = patientDoctor;
	}
	
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RechargeLog> getRechargeLogs() {
		return rechargeLogs;
	}

	public void setRechargeLogs(List<RechargeLog> rechargeLogs) {
		this.rechargeLogs = rechargeLogs;
	}
	
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<PlatformRechargeLog> getPlatformRechargeLogs() {
		return platformRechargeLogs;
	}

	public void setPlatformRechargeLogs(
			List<PlatformRechargeLog> platformRechargeLogs) {
		this.platformRechargeLogs = platformRechargeLogs;
	}

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("expire asc")
	public List<MemberPointLog> getMemberPointLogs() {
		return memberPointLogs;
	}

	public void setMemberPointLogs(List<MemberPointLog> memberPointLogs) {
		this.memberPointLogs = memberPointLogs;
	}

	@OneToMany(mappedBy = "recommendMember", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<MemberPointLog> getRecommendMemberLogs() {
		return recommendMemberLogs;
	}

	public void setRecommendMemberLogs(List<MemberPointLog> recommendMemberLogs) {
		this.recommendMemberLogs = recommendMemberLogs;
	}

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<VisitMessage> getMemberVisitMessages() {
		return memberVisitMessages;
	}

	public void setMemberVisitMessages(List<VisitMessage> memberVisitMessages) {
		this.memberVisitMessages = memberVisitMessages;
	}

	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<VisitMessage> getPatientVisitMessages() {
		return patientVisitMessages;
	}

	public void setPatientVisitMessages(List<VisitMessage> patientVisitMessages) {
		this.patientVisitMessages = patientVisitMessages;
	}

	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RecoveryPlan> getRecoveryPlans() {
		return recoveryPlans;
	}

	public void setRecoveryPlans(List<RecoveryPlan> recoveryPlans) {
		this.recoveryPlans = recoveryPlans;
	}

	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RecoveryRecord> getRecoveryRecords() {
		return recoveryRecords;
	}

	public void setRecoveryRecords(List<RecoveryRecord> recoveryRecords) {
		this.recoveryRecords = recoveryRecords;
	}
	
	@OneToMany(mappedBy = "patientMember", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)		
	public List<GroupPatient> getGroupPatient() {
		return groupPatient;
	}

	public void setGroupPatient(List<GroupPatient> groupPatient) {
		this.groupPatient = groupPatient;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_member_patientgroup")
	public Set<PatientGroup> getPatientGroups() {
		return patientGroups;
	}

	public void setPatientGroups(Set<PatientGroup> patientGroups) {
		this.patientGroups = patientGroups;
	}

	
	
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<ProductOrder> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(Set<ProductOrder> productOrders) {
		this.productOrders = productOrders;
	}

	/**
	 * 获取余额
	 * 
	 * @return 余额
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Balance> getBalances() {
		return balances;
	}

	/**
	 * 设置余额
	 * 
	 * @return 余额
	 */
	public void setBalances(Set<Balance> balances) {
		this.balances = balances;
	}
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<PatientMechanism> getPatientMechanisms() {
		return patientMechanisms;
	}

	public void setPatientMechanisms(List<PatientMechanism> patientMechanisms) {
		this.patientMechanisms = patientMechanisms;
	}

	/**
	 * 获取会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 * @return 会员注册项值
	 */
	@Transient
	public Object getAttributeValue(MemberAttribute memberAttribute) {
		if (memberAttribute != null) {
			if (memberAttribute.getType() == Type.name) {
				return getName();
			} else if (memberAttribute.getType() == Type.gender) {
				return getGender();
			} else if (memberAttribute.getType() == Type.birth) {
				return getBirth();
			} else if (memberAttribute.getType() == Type.area) {
				return getArea();
			} else if (memberAttribute.getType() == Type.address) {
				return getAddress();
			} else if (memberAttribute.getType() == Type.zipCode) {
				return getZipCode();
			} else if (memberAttribute.getType() == Type.phone) {
				return getPhone();
			} else if (memberAttribute.getType() == Type.mobile) {
				return getMobile();
			} else if (memberAttribute.getType() == Type.checkbox) {
				if (memberAttribute.getPropertyIndex() != null) {
					try {
						String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
						String propertyValue = (String) PropertyUtils.getProperty(this, propertyName);
						if (propertyValue != null) {
							return JsonUtils.toObject(propertyValue, List.class);
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			} else {
				if (memberAttribute.getPropertyIndex() != null) {
					try {
						String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
						return (String) PropertyUtils.getProperty(this, propertyName);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 设置会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 * @param attributeValue
	 *            会员注册项值
	 */
	@Transient
	public void setAttributeValue(MemberAttribute memberAttribute, Object attributeValue) {
		if (memberAttribute != null) {
			if (attributeValue instanceof String && StringUtils.isEmpty((String) attributeValue)) {
				attributeValue = null;
			}
			if (memberAttribute.getType() == Type.name && (attributeValue instanceof String || attributeValue == null)) {
				setName((String) attributeValue);
			} else if (memberAttribute.getType() == Type.gender && (attributeValue instanceof Gender || attributeValue == null)) {
				setGender((Gender) attributeValue);
			} else if (memberAttribute.getType() == Type.birth && (attributeValue instanceof Date || attributeValue == null)) {
				setBirth((Date) attributeValue);
			} else if (memberAttribute.getType() == Type.area && (attributeValue instanceof Area || attributeValue == null)) {
				setArea((Area) attributeValue);
			} else if (memberAttribute.getType() == Type.address && (attributeValue instanceof String || attributeValue == null)) {
				setAddress((String) attributeValue);
			} else if (memberAttribute.getType() == Type.zipCode && (attributeValue instanceof String || attributeValue == null)) {
				setZipCode((String) attributeValue);
			} else if (memberAttribute.getType() == Type.phone && (attributeValue instanceof String || attributeValue == null)) {
				setPhone((String) attributeValue);
			} else if (memberAttribute.getType() == Type.mobile && (attributeValue instanceof String || attributeValue == null)) {
				setMobile((String) attributeValue);
			} else if (memberAttribute.getType() == Type.checkbox && (attributeValue instanceof List || attributeValue == null)) {
				if (memberAttribute.getPropertyIndex() != null) {
					if (attributeValue == null || (memberAttribute.getOptions() != null && memberAttribute.getOptions().containsAll((List<?>) attributeValue))) {
						try {
							String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
							PropertyUtils.setProperty(this, propertyName, JsonUtils.toJson(attributeValue));
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				if (memberAttribute.getPropertyIndex() != null) {
					try {
						String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
						PropertyUtils.setProperty(this, propertyName, attributeValue);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	

	/**
	 * 移除所有会员注册项值
	 */
	@Transient
	public void removeAttributeValue() {
		setName(null);
		setGender(null);
		setBirth(null);
		setArea(null);
		setAddress(null);
		setZipCode(null);
		setPhone(null);
		setMobile(null);
		for (int i = 0; i < ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			try {
				PropertyUtils.setProperty(this, propertyName, null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	

    public GrantRight getGrantRight(Doctor doctor){
    	for (GrantRight grantRight : this.getGrantRights()) {
			if (grantRight.getDoctor().equals(doctor)) {
				return grantRight;
			}
		}
    	return null;
    }
	
    /**获取用户默认收货地址**/
    public Receiver defaultReceiver(){
    	for (Receiver receiver : this.getReceivers()) {
			if (receiver.getIsDefault()) {
				return receiver;
			}
		}
    	return null;
    }
	
	/**
	* @author wsr by 2018-3-19 14:58:24
	* @Title: getBalance 
	* @Description: TODO(获取当前用户所在机构的余额实体) 
	* @param mechanism
	* @return 设定文件 
	* @return Balance    返回类型 
	* @throws
	 */
	public Balance getBalance (Mechanism mechanism){
		for (Balance balance : this.getBalances()) {
			if(balance.getMechanism()==mechanism){
				return balance;
			}
		}
		return null;
	}
    
	
	/**
	* @author wsr by 2018-3-20 10:27:01
	* @Title: getBalance 
	* @Description: TODO(获取当前用户所在机构的余额) 
	* @param mechanism
	* @return 设定文件 
	* @return Balance    返回类型 
	* @throws
	 */
	public BigDecimal getBalances (Mechanism mechanism){
		for (Balance balance : this.getBalances()) {
			if(balance.getMechanism()==mechanism){
				return balance.getBalance();
			}
		}
		return new BigDecimal("0.00");
	}
	
	
	//获取用户总余额
	@Transient
	public BigDecimal getCountBalance (){
		BigDecimal countBalance = new BigDecimal(0);
		for (Balance balance : this.getBalances()) {
			countBalance = countBalance.add(balance.getBalance());
		}
		return countBalance;
	}
	
	//获取该患者所在的机构
	@Transient
	public PatientMechanism getPatientMechanism (Mechanism mechanism){
		for (PatientMechanism patientMechanism : this.getPatientMechanisms()) {
			if (patientMechanism.getMechanism()==mechanism) {
				return patientMechanism;
			}
		}
		return null;
	}
	
	//获取用户的有效积分
	@Transient
	public Long getCountPoint(){
		Long count = 0l;
//		Date date = new Date();
//		Setting setting = SettingUtils.get();
//		for (MemberPointLog memberPointLog : this.memberPointLogs) {
//			if (!memberPointLog.getType().equals(net.shenzhou.entity.MemberPointLog.Type.consumption)) {
//				Integer day = (int) ((memberPointLog.getCreateDate().getTime()-date.getTime())/ (24 * 60 * 60 * 1000));
//				if (day<setting.getMemberPointDay()) {
//					count = count+memberPointLog.getCredit();
//				}
//			}
//		}
		for (MemberPointLog memberPointLog : this.memberPointLogs) {
			if (!memberPointLog.isExpired()) {
					count = count+memberPointLog.getCredit();
			}
		}
		return count;
	}
	
	
	//获取用户的有效积分
		@Transient
		public Set<Coupon> getMemberCoupons(){
			Set<Coupon> coupons = new HashSet<Coupon>();
			for(CouponCode couponCode : this.getCouponCodes()){
				coupons.add(couponCode.getCoupon());
			}
			
			return coupons;
		}
	
}

    
    
