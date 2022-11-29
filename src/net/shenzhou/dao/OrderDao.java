/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.shenzhou.Filter;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
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
import net.shenzhou.entity.WorkDay;

/**
 * Dao - 订单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface OrderDao extends BaseDao<Order, Long> {

	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 订单，若不存在则返回null
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
	List<Order> downloadList(Map<String,Object> query_map);
	
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
	 * 查找所有过期订单
	 */
	List <Order> releaseTime();

	/**获取医生订单列表
	 * @param doctor
	 * @param paymentStatus
	 * @param orderStatus
	 * @param serveState
	 * @return
	 */
	Map<String ,Object> getDoctorOrderList(Doctor doctor,String paymentStatus,String orderStatus,String serveState,Integer pageNumber,Boolean isDelete);
	
	
	/**获取医生全部订单列表
	 * @param doctor
	 * @param paymentStatus
	 * @param orderStatus
	 * @param serveState
	 * @return
	 */
	List<Order> getDoctorOrderListByPatientType(Doctor doctor,PatientType patientType);
	
	
	

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
	 * @date 2017-06-17 17:19:05
	 * 获取监护人所有订单
	 * @author wsr
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
	 * 收费日报
	 * @param query_map
	 * @return
	 */
	Page<Order> charges(Map<String,Object> query_map);
	
	/**
	 * 计算患者就诊次数
	 * @param patient
	 * @return
	 */
	Long count(Member patientMember);
	
	
	/**
	 * 患者的预约信息安周 查询
	 * @param query_map
	 * @return
	 */
	List<Order> getWeekOrder(Map<String,Object>query_map);
	
	/**
	 * 查询月报(收费月报)
	 * @param query_map
	 * @return
	 */
	 List<Order> getMonthReport(Map<String,Object> query_map); 
	 
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
     List<Order> getLastDateCoruseHourCountMoney(Map<String, Object> query_map);
     
     /**
      * 机构端登录首页订单数据
      * @param query_map
      * @return
      */
     List<Order> getIndexOrder(Map<String,Object> query_map); 
	 
     
     
     /**
	  * 获取当前月份的用户账单列表
	  * @param query_map
	  * @return
	  */
	  MemberBill getDoctorBill(String day,Doctor doctor);
	  
	  
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
	 Integer getSumQuantity(Map<String,Object> query_map);
	 
	 
	 
	 /**
	  * 定时完成订单订单
	  * @return
	  */
	 List<Order> completeOrder();
	 
	 
	 /**
	  * 机构总消费额
	  * @param query_map
	  * @return
	  */
	 BigDecimal sumConsumption(Map<String, Object> query_map);
	 
	 
	 /**
	  * 根据机构和医生获取全部订单
	  * @return
	  */
	 List<Order> doctorMechanismOrder(Doctor doctor,Mechanism mechanism);
	 
	 
	 /**
	  * 获取患者某一天的所有订单
	  * @return
	  */
	 List<Order> patientOrder(Member patient,WorkDay workday);
	 
	 
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
	 Map<String,Object> member_search(String file);
	 
	 /**
	  * 患者康护记录
	  * @param query_map
	  * @return
	  */
	 Map<String ,Object> patientMemberRecord(Member patientMember,Integer pageNumber,Mechanism mechanism);
	 
	 
	 
	 /**
	  * 获取医生在默认机构当天的订单
	  * @param query_map
	  * @return
	  */
	 List<Order> getDoctorOrderPresentDay(Doctor doctor);
	 
	 
	 /**
	  * 获取医生在默认机构本月的订单
	  * @param query_map
	  * @return
	  */
	 List<Order> getDoctorOrderPresentMonth(Doctor doctor);
	 
	 /**
	  * 获取医生在默认机构近30天的订单
	  * @param query_map
	  * @return
	  */
	 List<Order> getDoctorOrderRecentlyMonth(Doctor doctor);
	 
	 
	 /**
	  * 根据传入日期获取医生当天订单
	  * @param query_map
	  * @return
	  */
	 Map<String,Object> getDoctorOrderByDate(Doctor doctor,String date);
	 
	 
	 /**
	  * 取出每个项目分类在当前日期前30天的订单金额
	  * @param query_map
	  * @return
	  */
	 BigDecimal getDoctorOrderByDate(Map<String,Object> query_map);
	 
	 
	 
	 /**
	  * 获取今日康复的订单
	  * @param query_map
	  * @return
	  */
	 List<Order> getTodayRecovery(Doctor doctor,Date date);
	 
	 
	 /**
	  * 获取医生项目分类在当前机构前30天的患者数
	  * @param query_map
	  * @return
	  */
	 List<Order> getThirtyTodayByServerProjectCategory(Map<String, Object> query_map);
	 
	 
	 
	 /**
      * 获取医生订单(确认订单列表)
      * @param file
      * @return
      */
	 List<Order> getConfirmOrder(Doctor doctor,Mechanism mechanism,WorkDay workDay);
	 
	 
	 /**
	  * 根据筛选条件获取某一天的订单信息
	  * @param query_map
	  * @return
	  */
	 Map<String,Object> getOrdersByDate(Map<String, Object> query_map);
	 /**
	  * 当日预约次数(下单人次患者去重)
	  * @param query_map
	  * @return
	  */
	 Integer getSameDayOrderCount(Map<String,Object> query_map);
	 
	 
	 /**
	  * 当日预约课节数量
	  * @param query_map
	  * @return
	  */
	 Double getSameDayQuantityCount(Map<String,Object> query_map);
	 
	 /**
	  * 当日服务人次
	  * @param query_map
	  * @return
	  */
	 Integer getSameDayServerCount(Map<String,Object> query_map);
	 
	 /**
	  *当日服务课节 
	  * @param query_map
	  * @return
	  */
	 Double getSameDayServerQuantity(Map<String,Object> query_map);
	 
	 
	 /**
	  * 我的绩效搜索
	  * @param query_map
	  * @return
	  */
	 Map<String,Object> doctorPerformance(Map<String, Object> query_map);
	 
	 
	 
	 /**
	  * 根据用户获取全部待服务订单
	  * @param query_map
	  * @return
	  */
	 List<Order> getMemberAwaitOrder(Member member);
	 
	 
	 
	 /**
	  * 根据用户获取全部待入档订单
	  * @param query_map
	  * @return
	  */
	 List<Order> getMemberRecordOrder(Member member);
	 
	 
	 /**
	  * 患者就诊记录
	  * @param query_map
	  * @return
	  */
	 Page<Order> getPatientOrders(Map<String,Object> query_map);
	 
	 
	 /**
	  * 今日预约次数
	  * @param query_map
	  * @return
	  */
	 Long toDayAboutCount(Map<String,Object> query_map);
	 
	 
	 /**
	  * 今日服务次数
	  * @param query_map
	  * @return
	  */
	 Double toDayServerCount(Map<String,Object> query_map);
	 
	 
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
	 List<Object[]> getWorkDayDate(Map<String,Object> query_map);
	 
	 
	 
	 
	 /**
	  * 获取患者订单(未取消的)
	  * @param query_map
	  * @return
	  */
	 List<Order> getMemberOrder(Member member);
	 
	 /**
	  * 获取用户的有效订单数量
	  * @param query_map
	  * @return
	  */
	 Integer getCountCompletedOrder(Map<String,Object> query_map);
	 
	 
	 
	 /**
	  * 获取患者在某机构订单(未取消的)
	  * @param query_map
	  * @return
	  */
	 List<Order> getMemberOrder(Member member,Mechanism mechanism);
	 
	 
	 /**
	  * 我的绩效搜索(最新)
	  * @param query_map
	  * @return
	  */
	 Map<String,Object> doctorPerformances(Map<String, Object> query_map);
	 

	 /**
	  * 获得患者待付款的订单
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

	 
	 /**
	  * 工作统计预约次数
	  * @param query_map
	  * @return
	  */
	 Long getAboutCount(Map<String,Object> query_map);
	 
	 /**
	  * 服务课节
	  * @param query_map
	  * @return
	  */
	 Double getServerCount(Map<String,Object> query_map);
	 
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

	 
}
