/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.shenzhou.Filter;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.BeforehandLogin;
import net.shenzhou.entity.Cart;
import net.shenzhou.entity.CouponCode;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.MemberBill;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PatientType;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.Order.ServeState;
import net.shenzhou.entity.Order.ShippingStatus;
import net.shenzhou.entity.Payment;
import net.shenzhou.entity.PaymentMethod;
import net.shenzhou.entity.Receiver;
import net.shenzhou.entity.Refunds;
import net.shenzhou.entity.Returns;
import net.shenzhou.entity.Shipping;
import net.shenzhou.entity.ShippingMethod;
import net.shenzhou.entity.WorkDay;

/**
 * Service - 订单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface OrderService extends BaseService<Order, Long> {

	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 若不存在则返回null
	 */
	Order findBySn(String sn);

	/**
	 * 查找订单
	 * 
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 订单
	 */
	List<Order> findList(Member member, Integer count, List<Filter> filters, List<net.shenzhou.Order> orders);

	/**
	 * 查找订单分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Order> findPage(Member member, Pageable pageable);

	/**
	 * 查找订单分页
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param paymentStatus
	 *            支付状态
	 * @param shippingStatus
	 *            配送状态
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Order> findPage(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus,ServeState serveState, Boolean hasExpired, Pageable pageable);

	/**
	 * 查找订单分页信息
	 * @param query_map
	 * @return
	 */
	Page<Order> findPage(Map<String,Object> query_map);
	
	/**
	 * 导出订单管理
	 * @param query_map
	 * @return
	 */
	List<Order> downloadList(Map<String, Object> query_map);
	
	
	/**
	 * 查询订单数量
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param paymentStatus
	 *            支付状态
	 * @param shippingStatus
	 *            配送状态
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long count(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired);

	/**
	 * 查询等待支付订单数量
	 * 
	 * @param member
	 *            会员
	 * @return 等待支付订单数量
	 */
	Long waitingPaymentCount(Member member);

	/**
	 * 查询等待发货订单数量
	 * 
	 * @param member
	 *            会员
	 * @return 等待发货订单数量
	 */
	Long waitingShippingCount(Member member);

	/**
	 * 获取销售额
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 销售额
	 */
	BigDecimal getSalesAmount(Date beginDate, Date endDate);

	/**
	 * 获取销售量
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 销售量
	 */
	Integer getSalesVolume(Date beginDate, Date endDate);

	/**
	 * 释放过期订单库存
	 */
	void releaseStock();

	/**
	 * 生成订单
	 * 
	 * @param cart
	 *            购物车
	 * @param receiver
	 *            收货地址
	 * @param paymentMethod
	 *            支付方式
	 * @param shippingMethod
	 *            配送方式
	 * @param couponCode
	 *            优惠码
	 * @param isInvoice
	 *            是否开据发票
	 * @param invoiceTitle
	 *            发票抬头
	 * @param useBalance
	 *            是否使用余额
	 * @param memo
	 *            附言
	 * @return 订单
	 */
	Order build(Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo);

	/**
	 * 创建订单
	 * 
	 * @param cart
	 *            购物车
	 * @param receiver
	 *            收货地址
	 * @param paymentMethod
	 *            支付方式
	 * @param shippingMethod
	 *            配送方式
	 * @param couponCode
	 *            优惠码
	 * @param isInvoice
	 *            是否开据发票
	 * @param invoiceTitle
	 *            发票抬头
	 * @param useBalance
	 *            是否使用余额
	 * @param memo
	 *            附言
	 * @param operator
	 *            操作员
	 * @return 订单
	 */
	Order create(Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo, Admin operator);

	/**
	 * 更新订单
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void update(Order order, Admin operator);

	/**
	 * 订单确认
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void confirm(Order order, Admin operator);

	/**
	 * 订单完成
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void complete(Order order, Admin operator);

	/**
	 * 订单取消
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void cancel(Order order, Admin operator);

	/**
	 * 订单支付
	 * 
	 * @param order
	 *            订单
	 * @param payment
	 *            收款单
	 * @param operator
	 *            操作员
	 */
	void payment(Order order, Payment payment, Admin operator);

	/**
	 * 订单退款
	 * 
	 * @param order
	 *            订单
	 * @param refunds
	 *            退款单
	 * @param operator
	 *            操作员
	 */
	void refunds(Order order, Refunds refunds, Admin operator);

	/**
	 * 订单发货
	 * 
	 * @param order
	 *            订单
	 * @param shipping
	 *            发货单
	 * @param operator
	 *            操作员
	 */
	void shipping(Order order, Shipping shipping, Admin operator);

	/**
	 * 订单退货
	 * 
	 * @param order
	 *            订单
	 * @param returns
	 *            退货单
	 * @param operator
	 *            操作员
	 */
	void returns(Order order, Returns returns, Admin operator);
	
	/**
	 * 创建订单
	 * @param map
	 * @return
	 */
	 Order persist (Map<String,Object> map);
	
	 
	 /**
	  * 支付
	  * @param map
	  * @return
	  */
	 Map<String,Object>  payment(Order order ,PaymentMethod paymentMethod ,String payType/*,WorkDay workDay ,String startTime,String endTime*/);
	 
	

	
	/**根据订单状态获取医生订单列表
	 * @param doctor
	 * @param paymentStatus
	 * @param orderStatus
	 * @param serveState
	 * @return
	 */
	 Map<String ,Object> getDoctorOrderList(Doctor doctor,String paymentStatus,String orderStatus,String serveState,Integer pageNumber,Boolean isDelete);
	
	
	
	/**根据订单类型(患者,机构)获取医生全部订单列表
	 * @param doctor
	 * @param paymentStatus
	 * @param orderStatus
	 * @param serveState
	 * @return
	 */
	List<Order> getDoctorOrderListByPatientType(Doctor doctor ,PatientType patientType);
	
	
	
	
	/**根据患者(联系人,患者)获取医生全部订单列表
	 * @param doctor
	 * @param paymentStatus
	 * @param orderStatus
	 * @param serveState
	 * @return
	 */
	List<Order> getDoctorOrderListBypatientMember(Doctor doctor ,String filtrateMold,List<Member> members,PatientType patientType);
	
	
	
	
	/**获取患者最近的评估订单
	 * @param doctor
	 * @param paymentStatus
	 * @param orderStatus
	 * @param serveState
	 * @return
	 */
	Order getPatientMemberRecentlyOrder(Member patientMember);
	
	
	/**
	 * 用户端订单列表(获取监护人的所有订单)
	 * @param data_map
	 * @return
	 */
	Map<String,Object> findList(Map<String,Object> data_map);
	
	
	/**获取患者最近的诊治订单
	 * @param doctor
	 * @param paymentStatus
	 * @param orderStatus
	 * @param serveState
	 * @return
	 */
	Order getPatientMemberRecentlyRecoveryOrder(Member patientMember);
	
	
	/**获取患者最近的订单
	 * @param doctor
	 * @param paymentStatus
	 * @param orderStatus
	 * @param serveState
	 * @return
	 */
	Order getPatientMemberRecentlysOrder(Member patientMember);
	
	
	
	/**获取患者康护订单数量
	 * @param patientMember
	 * @return
	 */
	Long getPatientMemberRecentlyRecoveryOrderCount(Member patientMember);
	
	
	
	

	/**获取患者最早的订单
	 * @param doctor
	 * @param paymentStatus
	 * @param orderStatus
	 * @param serveState
	 * @return
	 */
	Order getPatientMemberOldOrder(Member patientMember);
	
	
	
	
	/**获取医生今日订单数量
	 * @param patientMember
	 * @return
	 */
	Long getDoctorTodayOrder(Doctor doctor,Date date);
	
	
	/**
	 * 创建订单
	 * @param map
	 * @return
	 */
	 Order persistOrder (Map<String,Object> map);
	 
	 /**
	  * 过期订单
	  * @return
	  */
	 List<Order> releaseTime();
	 
	 
	 /**
	  * 收费日报
	  * @param query_map
	  * @return
	  */
	 Page<Order> charges(Map<String,Object> query_map);
	 
	 
	 /**
	  * 患者就诊次数
	  * @param patientMember
	  * @return
	  */
	 Long count(Member patientMember);
	 
	 
	 /**
	  * 查询患者的预约信息
	  * @param query_map
	  * @return
	  */
	 List<Order> getWeekOrder(Map<String, Object> query_map);
	 
	 /**
	  * 月报表统计(收费月报)
	  * @param query_map
	  * @return
	  */
	 List<Order> getMonthReport(Map<String, Object> query_map);
	 
	 
	 /**
	  * 预约统计
	  * @param query_map
	  * @return
	  */
	 List<Order> getMonthAbout(Map<String,Object> query_map); 
	 
     /**
      * 患者状态获取 最后消费时间  服务课时  消费金额
      * @param query_map
      * @return
      */
	 List<Order> getLastDateCoruseHourCountMoney(
				Map<String, Object> query_map);
	 
	 
	 /**
      * 验证时间接口
      * @param 
      * @return
      */
	 Map<String,Object> getData(Map<String,Object> map);
	 
	 
	 /**
      * 机构端登录首页订单数据
      * @param query_map
      * @return
      */
	 List<Order> getIndexOrder(Map<String, Object> query_map);
	 
	 
	 
	 /**
	  * 获取当前月份的用户账单列表
	  * @param query_map
	  * @return
	  */
	  List<MemberBill> getDoctorBill(Doctor doctor);
	 
	  
	  /**
	  * 获取当前月份的用户账单列表
	  * @param query_map
	  * @return
	  */
	  List<MemberBill> getDoctorBillByMonth(String month,Doctor doctor);
	  
	  
	  
	  /**
      * 根据患者名称和日期筛选订单
      * @param file
      * @return
      */
	 List<Order> orderFiltrate(String file,Doctor doctor);
	 
	 /**
	  * 康复总课时
	  * @param query_map
	  * @return
	  */
	 Integer getSumQuantity(Map<String, Object> query_map);
	 
	 
	 
	 /**
	  * 定时完成订单订单
	  * @return
	  */
	 List<Order> completeOrder();
	 
	 /**
	  * 查询机构的总消费
	  * @param query_map
	  * @return
	  */
	 BigDecimal sumConsumption(Map<String, Object> query_map);
	 
	 
	 /**
	  * 验证用户服务时间是否重复
	  * @param query_map
	  * @return
	  */
	 Boolean memberTimeRepetition(String startTime_patient,String endTime_patient,WorkDay workday,Member patient);
	 
	 
	 
	 /**
	  * 根据机构和医生获取全部订单
	  * @return
	  */
	 List<Order> doctorMechanismOrder(Doctor doctor,Mechanism mechanism);
	 
	 
	 /**
	  * 导出收费统计
	  * @param query_map
	  * @return
	  */
	 List<Order> downloadCharge(Map<String, Object> query_map);
	 
	 /**
	  * 用户端搜索订单
	  * @param file
	  * @return
	  */
	 Map<String, Object> member_search(String file);
	 
	 /**
	  * 患者康护记录
	  * @param query_map
	  * @return
	  */
	 Map<String ,Object> patientMemberRecord(Member patientMember,Integer pageNumber,Mechanism mechanism);
	 
	 
	 /**
	  *	资金收益
	  * @param query_map
	  * @return
	  */
	 Map<String ,Object> capitalEarnings(Doctor doctor);
	 
	 
	 /**
	  *	业务数据
	  * @param query_map
	  * @return
	  */
	 Map<String ,Object> businessStatistics(Doctor doctor);
	 
	 
	 /**
	  *	工作量数据
	  * @param query_map
	  * @return
	  */
	 Map<String ,Object> workloadStatistics(Doctor doctor);
	 
	 
	 /**
      * 获取医生订单(确认订单列表)
      * @param file
      * @return
      */
	 List<Order> getConfirmOrder(Doctor doctor,Mechanism mechanism,WorkDay workDay);
	 
	 
	 
	 /**
	  * 资金收益明细
	  * @param query_map
	  * @return
	  */
	 Map<String,Object> capitalEarningsDetail(Map<String, Object> query_map);
	 /**
	  * 以往排班里的当日预约人次
	  * @param query_map
	  * @return
	  */
	 Integer getSameDayOrderCount(Map<String, Object> query_map);
	 
	 /**
	  * 以往排班里的当日预约课节
	  * @param query_map
	  * @return
	  */
	 Double getSameDayQuantityCount(Map<String, Object> query_map);
	 
	 /**
	  * 以往排班里的当日服务人次
	  * @param query_map
	  * @return
	  */
	 Integer getSameDayServerCount(Map<String, Object> query_map);
	 
	 
	 /**
	  * 以往排班里的当日服务课节
	  * @param query_map
	  * @return
	  */
	 Double getSameDayServerQuantity(Map<String, Object> query_map);
	 
	 
	 /**
	  * 业务统计明细
	  * @param query_map
	  * @return
	  */
	 Map<String,Object> businessStatisticsDetails(Map<String, Object> query_map);
	 
	 
	 /**
	  * 工作量统计明细
	  * @param query_map
	  * @return
	  */
	 Map<String,Object> workloadStatisticsDetails(Map<String, Object> query_map);
	 
	 
	 /**
	  * 工作报表列表页数据
	  * @param query_map
	  * @return
	  */
	 Map<String,Object> workStatementList(Map<String, Object> query_map);
	 
	 
	 /**
	  * 我的绩效搜索
	  * @param query_map
	  * @return
	  */
	 Map<String,Object> doctorPerformance(Map<String, Object> query_map);
	 
	 
	 
	 /**
      * 医生验证时间接口(2018，老的)
      * @param 
      * @return
      */
	 Map<String,Object> getDoctorData(Map<String,Object> map);
	 
	 
	 
	 /**
      * 医生验证时间接口(2018，新的)
      * @param 
      * @return
      */
	 Map<String,Object> getDoctorDatas(Map<String,Object> map);
	 
	 /**
	 * 创建医生订单
	 * @param map
	 * @return
	 */
	 Order persistDoctorOrder (Map<String,Object> map);
	 
	 
	 /**
	  * 患者康护记录
	  * @param query_map
	  * @return
	  */
	 Page<Order> getPatientOrders(Map<String,Object> query_map);
	 
	 
	 
	 /**
	 * 创建患者订单(老版本)
	 * @param map
	 * @return
	 */
	 Order persistMemberOrder (Map<String,Object> map);
	 
	 
	 
	 /**
		 * 创建患者订单
		 * @param map
		 * @return
		 */
	Order persistMemberOrders (Map<String,Object> map);
	 
	 /**
	 * 待服务订单过了服务时间自动变成待入档订单(患者端)
	 * @param map
	 * @return
	 */
	 void memberAwaitToRecord (Member member);
	 
	 
	 
	 /**
	 * 待入档订单过了时间自动变成已完成订单(患者端)
	 * @param map
	 * @return
	 */
	 void memberRecordToCompleted (Member member,BeforehandLogin beforehandLogin);
	 
	 /**
	  * 今日预约次数
	  * @param query_map
	  * @return
	  */
	 Long toDayAboutCount(Map<String, Object> query_map);
	 
	 
	 /**
	  * 今日服务次数
	  * @param query_map
	  * @return
	  */
	 Double toDayServerCount(Map<String, Object> query_map);
	 
	 
	 /**
	  * 未来待服务次数
	  * @param query_map
	  * @return
	  */
	 Long futureServerCount(Map<String,Object> query_map);
	 
	 /**
	  * 获取订单的服务日期
	  * @param query_map
	  * @return
	  */
	 List<Object[]> getWorkDayDate(Map<String, Object> query_map); 	
	 
	 
	 
	 /**
	  * 获取患者订单(未取消的)
	  * @param query_map
	  * @return
	  */
	 List<Order> getMemberOrder(Member member);
	 
	 
	 
	 
	 /**
	  * 我的绩效搜索(最新)
	  * @param query_map
	  * @return
	  */
	 Map<String,Object> doctorPerformances(Map<String, Object> query_map);
	 
	 /**
	  * 工作统计预约次数
	  * @param query_map
	  * @return
	  */
	 Long getAboutCount(Map<String,Object> query_map);
	 
	 /**
	  * 工作统计服务课节
	  * @param query_map
	  * @return
	  */
	 Double getServerCount(Map<String, Object> query_map);
	 

	 /**
	  * 退款课节
	  * @param query_map
	  * @return
	  */
	 Double getRefundedCount(Map<String,Object> query_map);
	 
	 
	 /**
	  * 退款金额
	  * @param query_map
	  * @return
	  */
	 BigDecimal getRefundedPrice(Map<String,Object> query_map);
	 
	 
	 /**
	  * 消费金额
	  * @param query_map
	  * @return
	  */
	 BigDecimal getConsumptionPrice(Map<String,Object> query_map);
	 /**
	  * 获得患者待付款的订单数量
	  * @param member
	  * @return
	  */
	 List<Order> getMemberOrderByNumber(Member member);
	 
	 
	 /**
	  * 获得患者待康复的订单数
	  * @param member
	  * @return
	  */
	 List<Order> getMemberOrderByRecovery(Member member);
	 
	 

	 /**
	  * 获得患者待归档的订单数
	  * @param member
	  * @return
	  */
	 List<Order> getMemberOrderByFile(Member member);
}




