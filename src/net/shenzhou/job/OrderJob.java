/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.job;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.entity.CouponCode;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.ShippingStatus;
import net.shenzhou.entity.OrderLog;
import net.shenzhou.service.CouponCodeService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderLogService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.WorkDayItemService;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job - 订单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Component("orderJob")
@Lazy(false)
public class OrderJob {

	@Resource(name = "orderServiceImpl")
	private OrderService orderService;

	@Resource(name = "workDayItemServiceImpl")
	private WorkDayItemService workDayItemService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "orderLogServiceImpl")
	private OrderLogService orderLogService;
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	
	/**
	 * 释放过期订单库存
	 */
	@Scheduled(cron = "${job.order_release_stock.cron}")
	public void releaseStock() {
		
		List<Order> orders = orderService.releaseTime();
		if (orders.size()>0) {
			for (Order order : orders) {
				order.setOrderStatus(OrderStatus.cancelled);
				order.setShippingStatus(ShippingStatus.returned);
				order.setIsDeleted(true);
				Long workDayItemId = order.getWorkDayItem().getId();
				order.setWorkDayItem(null);
				CouponCode couponCode = order.getCouponCode();
				if(couponCode!=null){
					couponCode.setIsUsed(false);
					couponCode.setOrder(null);
					couponCodeService.update(couponCode);
					order.setCouponCode(null);
					System.out.println("优惠券返已退回");
				}
				orderService.update(order);
				System.out.println(order.getSn()+"订单已过期,把预定时间释放出来");
				workDayItemService.delete(workDayItemId);
				
				OrderLog orderLog = new OrderLog();
				orderLog.setType(OrderLog.Type.cancel);
				orderLog.setOperator("系统管理员");
				orderLog.setContent("订单超时未支付，系统自动取消");
				orderLog.setIsDeleted(false);
				orderLog.setOrder(order);
				orderLogService.save(orderLog);
				
			}
		}
		
	}

	
	/**
	 * 指定时间点完成订单
	 *//*
	@Scheduled(cron = "${job.order_complete_order.cron}")
	public void completeOrder() {
		
		List<Order> orders = orderService.completeOrder();
		if (orders.size()>0) {
			for (Order order : orders) {
				System.out.println(order.getId());
				WorkDayItem workdayitem = order.getWorkDayItem();
				System.out.println(workdayitem.getId());
				WorkDay workDay = workdayitem.getWorkDay();
				System.out.println(workdayitem.getWorkDay().getId());
				Date ss = order.getWorkDayItem().getWorkDay().getWorkDayDate();
				String date = order.getWorkDayItem().getWorkDay().getWorkDayDate()+" "+ order.getWorkDayItem().getEndTime();
				System.out.println(date);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				String timing = DateUtil.getMinute(DateUtil.getStringtoDate("yyyy-MM-dd HH:mm", date), 4320);
				
				if(DateUtil.compare_date_day(timing, df.format(new Date()))==1){

					order.setOrderStatus(OrderStatus.completed);
					order.setServeState(ServeState.accomplish);
					orderService.update(order);
					
					Member patientMember = order.getPatientMember();
					
					Project project = order.getProject();
					project.setSecond((project.getSecond()+1));
					projectService.update(project);
					
					Doctor doctor = order.getDoctor();
					doctor.setSecond((doctor.getSecond()+1));
					doctor.getMembers().add(patientMember.getParent());
					List<Member> member_list = doctor.getParents();

					if(!member_list.contains(patientMember)){
						doctor.getParents().add(patientMember);
					}
					
					doctorService.update(doctor);
					
					Mechanism mechanism = project.getMechanism();
					mechanism.setSecond((mechanism.getSecond()+1));
					mechanismService.update(mechanism);
					
					Member member = patientMember.getParent();
					List<Doctor> doctorList = member.getDoctors();
					if(!doctorList.contains(doctor)){
						member.getDoctors().add(doctor);
						memberService.update(member);
					}
					
					if(!patientMember.getPatientDoctor().contains(doctor)){
						patientMember.getPatientDoctor().add(doctor);
						memberService.update(patientMember);
					}
					
				}
				
			}
		}
		
	}
	*/
	
}