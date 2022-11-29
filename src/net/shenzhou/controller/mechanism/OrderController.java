/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.ExcelView;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.DeliveryCorp;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Order.Evaluate;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.Order.ServeState;
import net.shenzhou.entity.Order.ShippingStatus;
import net.shenzhou.entity.OrderItem;
import net.shenzhou.entity.PatientMechanism.HealthType;
import net.shenzhou.entity.Payment;
import net.shenzhou.entity.Payment.Status;
import net.shenzhou.entity.Payment.Type;
import net.shenzhou.entity.PaymentMethod;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.Refunds;
import net.shenzhou.entity.Returns;
import net.shenzhou.entity.ReturnsItem;
import net.shenzhou.entity.ServerProjectCategory.ServeType;
import net.shenzhou.entity.Shipping;
import net.shenzhou.entity.ShippingItem;
import net.shenzhou.entity.ShippingMethod;
import net.shenzhou.entity.Sn;
import net.shenzhou.entity.VisitMessage.VisitType;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DeliveryCorpService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderItemService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.PaymentMethodService;
import net.shenzhou.service.ProductService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.ShippingMethodService;
import net.shenzhou.service.SnService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 机构订单
 * 2017-7-10 15:46:41
 * @author wsr
 *
 */
@Controller("mechanismOrderController")
@RequestMapping("/mechanism/order")
public class OrderController extends BaseController {

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "orderItemServiceImpl")
	private OrderItemService orderItemService;
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	
	
	/**
	 * 检查锁定
	 */
	@RequestMapping(value = "/check_lock", method = RequestMethod.POST)
	public @ResponseBody
	Message checkLock(Long id) {
		Order order = orderService.find(id);
		if (order == null) {
			return Message.warn("admin.common.invalid");
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			if (order.getOperator() != null) {
				return Message.warn("admin.order.adminLocked", order.getOperator().getUsername());
			} else {
				return Message.warn("admin.order.memberLocked");
			}
		} else {
			order.setLockExpire(DateUtils.addSeconds(new Date(), 20));
			order.setOperator(admin);
			orderService.update(order);
			return SUCCESS_MESSAGE;
		}
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism  mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		model.addAttribute("methods", Payment.Method.values());
		model.addAttribute("refundsMethods", Refunds.Method.values());
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		model.addAttribute("order", orderService.find(id));
		model.addAttribute("serverProjectCategorys", serverProjectCategoryService.getServerProjectCategory(mechanism));
		model.addAttribute("doctors", mechanism.getDoctors());
		model.addAttribute("visitTypes", VisitType.values());
		return "/mechanism/order/view";
	}

	/**
	 * 确认
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String confirm(Long id, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(id);
		Admin admin = adminService.getCurrent();
		if (order != null && !order.isExpired() && order.getOrderStatus() == OrderStatus.unconfirmed && !order.isLocked(admin)) {
			orderService.confirm(order, admin);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} else {
			addFlashMessage(redirectAttributes, Message.warn("admin.common.invalid"));
		}
		return "redirect:view.jhtml?id=" + id;
	}

	/**
	 * 完成
	 */
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public String complete(Long id, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(id);
		Admin admin = adminService.getCurrent();
		if (order != null && !order.isExpired() && order.getOrderStatus() == OrderStatus.confirmed && !order.isLocked(admin)) {
			orderService.complete(order, admin);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} else {
			addFlashMessage(redirectAttributes, Message.warn("admin.common.invalid"));
		}
		return "redirect:view.jhtml?id=" + id;
	}

	/**
	 * 取消
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancel(Long id, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(id);
		Admin admin = adminService.getCurrent();
		if (order != null && !order.isExpired() && order.getOrderStatus() == OrderStatus.unconfirmed && !order.isLocked(admin)) {
			orderService.cancel(order, admin);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} else {
			addFlashMessage(redirectAttributes, Message.warn("admin.common.invalid"));
		}
		return "redirect:view.jhtml?id=" + id;
	}

	/**
	 * 支付
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String payment(Long orderId, Long paymentMethodId, Payment payment, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		payment.setOrder(order);
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		payment.setPaymentMethod(paymentMethod != null ? paymentMethod.getName() : null);
		if (!isValid(payment)) {
			return ERROR_VIEW;
		}
		if (order.isExpired() || order.getOrderStatus() != OrderStatus.confirmed) {
			return ERROR_VIEW;
		}
		if (order.getPaymentStatus() != PaymentStatus.unpaid && order.getPaymentStatus() != PaymentStatus.partialPayment) {
			return ERROR_VIEW;
		}
		if (payment.getAmount().compareTo(new BigDecimal(0)) <= 0 || payment.getAmount().compareTo(order.getAmountPayable()) > 0) {
			return ERROR_VIEW;
		}
		Member member = order.getMember();
		if (payment.getMethod() == Payment.Method.deposit && payment.getAmount().compareTo(member.getBalance()) > 0) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			return ERROR_VIEW;
		}
		payment.setSn(snService.generate(Sn.Type.payment));
		payment.setType(Type.payment);
		payment.setStatus(Status.success);
		payment.setFee(new BigDecimal(0));
		payment.setOperator(admin.getUsername());
		payment.setPaymentDate(new Date());
		payment.setPaymentPluginId(null);
		payment.setExpire(null);
		payment.setDeposit(null);
		payment.setMember(null);
		orderService.payment(order, payment, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 * 退款
	 */
	@RequestMapping(value = "/refunds", method = RequestMethod.POST)
	public String refunds(Long orderId, Long paymentMethodId, Refunds refunds, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		refunds.setOrder(order);
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		refunds.setPaymentMethod(paymentMethod != null ? paymentMethod.getName() : null);
		if (!isValid(refunds)) {
			return ERROR_VIEW;
		}
		if (order.isExpired() || order.getOrderStatus() != OrderStatus.confirmed) {
			return ERROR_VIEW;
		}
		if (order.getPaymentStatus() != PaymentStatus.paid && order.getPaymentStatus() != PaymentStatus.partialPayment && order.getPaymentStatus() != PaymentStatus.partialRefunds) {
			return ERROR_VIEW;
		}
		if (refunds.getAmount().compareTo(new BigDecimal(0)) <= 0 || refunds.getAmount().compareTo(order.getAmountPaid()) > 0) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			return ERROR_VIEW;
		}
		refunds.setSn(snService.generate(Sn.Type.refunds));
		refunds.setOperator(admin.getUsername());
		orderService.refunds(order, refunds, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 * 发货
	 */
	@RequestMapping(value = "/shipping", method = RequestMethod.POST)
	public String shipping(Long orderId, Long shippingMethodId, Long deliveryCorpId, Long areaId, Shipping shipping, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		if (order == null) {
			return ERROR_VIEW;
		}
		for (Iterator<ShippingItem> iterator = shipping.getShippingItems().iterator(); iterator.hasNext();) {
			ShippingItem shippingItem = iterator.next();
			if (shippingItem == null || StringUtils.isEmpty(shippingItem.getSn()) || shippingItem.getQuantity() == null || shippingItem.getQuantity() <= 0) {
				iterator.remove();
				continue;
			}
			OrderItem orderItem = order.getOrderItem(shippingItem.getSn());
			if (orderItem == null || shippingItem.getQuantity() > orderItem.getQuantity() - orderItem.getShippedQuantity()) {
				return ERROR_VIEW;
			}
			if (orderItem.getProduct() != null && orderItem.getProduct().getStock() != null && shippingItem.getQuantity() > orderItem.getProduct().getStock()) {
				return ERROR_VIEW;
			}
			shippingItem.setName(orderItem.getFullName());
			shippingItem.setShipping(shipping);
		}
		shipping.setOrder(order);
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		shipping.setShippingMethod(shippingMethod != null ? shippingMethod.getName() : null);
		DeliveryCorp deliveryCorp = deliveryCorpService.find(deliveryCorpId);
		shipping.setDeliveryCorp(deliveryCorp != null ? deliveryCorp.getName() : null);
		shipping.setDeliveryCorpUrl(deliveryCorp != null ? deliveryCorp.getUrl() : null);
		shipping.setDeliveryCorpCode(deliveryCorp != null ? deliveryCorp.getCode() : null);
		Area area = areaService.find(areaId);
		shipping.setArea(area != null ? area.getFullName() : null);
		if (!isValid(shipping)) {
			return ERROR_VIEW;
		}
		if (order.isExpired() || order.getOrderStatus() != OrderStatus.confirmed) {
			return ERROR_VIEW;
		}
		if (order.getShippingStatus() != ShippingStatus.unshipped && order.getShippingStatus() != ShippingStatus.partialShipment) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			return ERROR_VIEW;
		}
		shipping.setSn(snService.generate(Sn.Type.shipping));
		shipping.setOperator(admin.getUsername());
		orderService.shipping(order, shipping, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 * 退货
	 */
	@RequestMapping(value = "/returns", method = RequestMethod.POST)
	public String returns(Long orderId, Long shippingMethodId, Long deliveryCorpId, Long areaId, Returns returns, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		if (order == null) {
			return ERROR_VIEW;
		}
		for (Iterator<ReturnsItem> iterator = returns.getReturnsItems().iterator(); iterator.hasNext();) {
			ReturnsItem returnsItem = iterator.next();
			if (returnsItem == null || StringUtils.isEmpty(returnsItem.getSn()) || returnsItem.getQuantity() == null || returnsItem.getQuantity() <= 0) {
				iterator.remove();
				continue;
			}
			OrderItem orderItem = order.getOrderItem(returnsItem.getSn());
			if (orderItem == null || returnsItem.getQuantity() > orderItem.getShippedQuantity() - orderItem.getReturnQuantity()) {
				return ERROR_VIEW;
			}
			returnsItem.setName(orderItem.getFullName());
			returnsItem.setReturns(returns);
		}
		returns.setOrder(order);
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		returns.setShippingMethod(shippingMethod != null ? shippingMethod.getName() : null);
		DeliveryCorp deliveryCorp = deliveryCorpService.find(deliveryCorpId);
		returns.setDeliveryCorp(deliveryCorp != null ? deliveryCorp.getName() : null);
		Area area = areaService.find(areaId);
		returns.setArea(area != null ? area.getFullName() : null);
		if (!isValid(returns)) {
			return ERROR_VIEW;
		}
		if (order.isExpired() || order.getOrderStatus() != OrderStatus.confirmed) {
			return ERROR_VIEW;
		}
		if (order.getShippingStatus() != ShippingStatus.shipped && order.getShippingStatus() != ShippingStatus.partialShipment && order.getShippingStatus() != ShippingStatus.partialReturns) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			return ERROR_VIEW;
		}
		returns.setSn(snService.generate(Sn.Type.returns));
		returns.setOperator(admin.getUsername());
		orderService.returns(order, returns, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		model.addAttribute("order", orderService.find(id));
		return "/mechanism/order/edit";
	}

	/**
	 * 订单项添加
	 */
	@RequestMapping(value = "/order_item_add", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> orderItemAdd(String productSn) {
		Map<String, Object> data = new HashMap<String, Object>();
		Product product = productService.findBySn(productSn);
		if (product == null) {
			data.put("message", Message.warn("admin.order.productNotExist"));
			return data;
		}
		if (!product.getIsMarketable()) {
			data.put("message", Message.warn("admin.order.productNotMarketable"));
			return data;
		}
		if (product.getIsOutOfStock()) {
			data.put("message", Message.warn("admin.order.productOutOfStock"));
			return data;
		}
		data.put("sn", product.getSn());
		data.put("fullName", product.getFullName());
		data.put("price", product.getPrice());
		data.put("weight", product.getWeight());
		data.put("isGift", product.getIsGift());
		data.put("message", SUCCESS_MESSAGE);
		return data;
	}

	/**
	 * 计算
	 */
	@RequestMapping(value = "/calculate", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> calculate(Order order, Long areaId, Long paymentMethodId, Long shippingMethodId) {
		Map<String, Object> data = new HashMap<String, Object>();
		for (Iterator<OrderItem> iterator = order.getOrderItems().iterator(); iterator.hasNext();) {
			OrderItem orderItem = iterator.next();
			if (orderItem == null || StringUtils.isEmpty(orderItem.getSn())) {
				iterator.remove();
			}
		}
		order.setArea(areaService.find(areaId));
		order.setPaymentMethod(paymentMethodService.find(paymentMethodId));
		order.setShippingMethod(shippingMethodService.find(shippingMethodId));
		if (!isValid(order)) {
			data.put("message", Message.warn("admin.common.invalid"));
			return data;
		}
		Order pOrder = orderService.find(order.getId());
		if (pOrder == null) {
			data.put("message", Message.error("admin.common.invalid"));
			return data;
		}
		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem.getId() != null) {
				OrderItem pOrderItem = orderItemService.find(orderItem.getId());
				if (pOrderItem == null || !pOrder.equals(pOrderItem.getOrder())) {
					data.put("message", Message.error("admin.common.invalid"));
					return data;
				}
				Product product = pOrderItem.getProduct();
				if (product != null && product.getStock() != null) {
					if (pOrder.getIsAllocatedStock()) {
						if (orderItem.getQuantity() > product.getAvailableStock() + pOrderItem.getQuantity()) {
							data.put("message", Message.warn("admin.order.lowStock"));
							return data;
						}
					} else {
						if (orderItem.getQuantity() > product.getAvailableStock()) {
							data.put("message", Message.warn("admin.order.lowStock"));
							return data;
						}
					}
				}
			} else {
				Product product = productService.findBySn(orderItem.getSn());
				if (product == null) {
					data.put("message", Message.error("admin.common.invalid"));
					return data;
				}
				if (product.getStock() != null && orderItem.getQuantity() > product.getAvailableStock()) {
					data.put("message", Message.warn("admin.order.lowStock"));
					return data;
				}
			}
		}
		Map<String, Object> orderItems = new HashMap<String, Object>();
		for (OrderItem orderItem : order.getOrderItems()) {
			orderItems.put(orderItem.getSn(), orderItem);
		}
		order.setFee(pOrder.getFee());
		order.setPromotionDiscount(pOrder.getPromotionDiscount());
		order.setCouponDiscount(pOrder.getCouponDiscount());
		order.setAmountPaid(pOrder.getAmountPaid());
		data.put("weight", order.getWeight());
		data.put("price", order.getPrice());
		data.put("quantity", order.getQuantity());
		data.put("amount", order.getAmount());
		data.put("orderItems", orderItems);
		data.put("message", SUCCESS_MESSAGE);
		return data;
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Order order, Long areaId, Long paymentMethodId, Long shippingMethodId, RedirectAttributes redirectAttributes) {
		for (Iterator<OrderItem> iterator = order.getOrderItems().iterator(); iterator.hasNext();) {
			OrderItem orderItem = iterator.next();
			if (orderItem == null || StringUtils.isEmpty(orderItem.getSn())) {
				iterator.remove();
			}
		}
		order.setArea(areaService.find(areaId));
		order.setPaymentMethod(paymentMethodService.find(paymentMethodId));
		order.setShippingMethod(shippingMethodService.find(shippingMethodId));
		if (!isValid(order)) {
			return ERROR_VIEW;
		}
		Order pOrder = orderService.find(order.getId());
		if (pOrder == null) {
			return ERROR_VIEW;
		}
		if (pOrder.isExpired() || pOrder.getOrderStatus() != OrderStatus.unconfirmed) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (pOrder.isLocked(admin)) {
			return ERROR_VIEW;
		}
		if (!order.getIsInvoice()) {
			order.setInvoiceTitle(null);
			order.setTax(new BigDecimal(0));
		}
		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem.getId() != null) {
				OrderItem pOrderItem = orderItemService.find(orderItem.getId());
				if (pOrderItem == null || !pOrder.equals(pOrderItem.getOrder())) {
					return ERROR_VIEW;
				}
				Product product = pOrderItem.getProduct();
				if (product != null && product.getStock() != null) {
					if (pOrder.getIsAllocatedStock()) {
						if (orderItem.getQuantity() > product.getAvailableStock() + pOrderItem.getQuantity()) {
							return ERROR_VIEW;
						}
					} else {
						if (orderItem.getQuantity() > product.getAvailableStock()) {
							return ERROR_VIEW;
						}
					}
				}
				BeanUtils.copyProperties(pOrderItem, orderItem, new String[] { "price", "quantity" });
				if (pOrderItem.getIsGift()) {
					orderItem.setPrice(new BigDecimal(0));
				}
			} else {
				Product product = productService.findBySn(orderItem.getSn());
				if (product == null) {
					return ERROR_VIEW;
				}
				if (product.getStock() != null && orderItem.getQuantity() > product.getAvailableStock()) {
					return ERROR_VIEW;
				}
				orderItem.setName(product.getName());
				orderItem.setFullName(product.getFullName());
				if (product.getIsGift()) {
					orderItem.setPrice(new BigDecimal(0));
				}
				orderItem.setWeight(product.getWeight());
				orderItem.setThumbnail(product.getThumbnail());
				orderItem.setIsGift(product.getIsGift());
				orderItem.setShippedQuantity(0);
				orderItem.setReturnQuantity(0);
				orderItem.setProduct(product);
				orderItem.setOrder(pOrder);
			}
		}
		order.setSn(pOrder.getSn());
		order.setOrderStatus(pOrder.getOrderStatus());
		order.setPaymentStatus(pOrder.getPaymentStatus());
		order.setShippingStatus(pOrder.getShippingStatus());
		order.setFee(pOrder.getFee());
		order.setPromotionDiscount(pOrder.getPromotionDiscount());
		order.setCouponDiscount(pOrder.getCouponDiscount());
		order.setAmountPaid(pOrder.getAmountPaid());
		order.setPromotion(pOrder.getPromotion());
		order.setExpire(pOrder.getExpire());
		order.setLockExpire(null);
		order.setIsAllocatedStock(pOrder.getIsAllocatedStock());
		order.setOperator(null);
		order.setMember(pOrder.getMember());
		order.setCouponCode(pOrder.getCouponCode());
		order.setCoupons(pOrder.getCoupons());
		order.setOrderLogs(pOrder.getOrderLogs());
		order.setDeposits(pOrder.getDeposits());
		order.setPayments(pOrder.getPayments());
		order.setRefunds(pOrder.getRefunds());
		order.setShippings(pOrder.getShippings());
		order.setReturns(pOrder.getReturns());

		orderService.update(order, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String sn ,String projectName,String doctorName,String memberName,String patientName,String phone,Date startDate,Date endDate,
			ServeState serveState,Evaluate evaluate,
			OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired,
			ServeType serveType,Boolean isAbnormal,
			Pageable pageable, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("sn", sn);
		query_map.put("projectName", projectName);
		query_map.put("doctorName", doctorName);
		query_map.put("memberName", memberName);
		query_map.put("patientName", patientName);
		query_map.put("phone", phone);
		query_map.put("serveType", serveType);
		query_map.put("isAbnormal", isAbnormal);
		
		Calendar calendar = Calendar.getInstance();
		if (startDate!=null) {
			calendar.setTime(startDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startDate = calendar.getTime();
		}
		if (endDate!=null) {
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endDate = calendar.getTime();
		}
		query_map.put("startDate", startDate);
		query_map.put("endDate", endDate);
		
		
		query_map.put("serveState", serveState);
		query_map.put("evaluate", evaluate);
		query_map.put("orderStatus", orderStatus);
		query_map.put("paymentStatus", paymentStatus);
		query_map.put("shippingStatus", shippingStatus);
		query_map.put("hasExpired", hasExpired);
		query_map.put("pageable", pageable);
		
		model.addAttribute("sn", sn);
		model.addAttribute("projectName", projectName);
		model.addAttribute("doctorName", doctorName);
		model.addAttribute("memberName", memberName);
		model.addAttribute("patientName", patientName);
		model.addAttribute("phone", phone);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("serveState", serveState);
		model.addAttribute("evaluate", evaluate);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("paymentStatus", paymentStatus);
		model.addAttribute("shippingStatus", shippingStatus);
		model.addAttribute("hasExpired", hasExpired);
		model.addAttribute("isAbnormal", isAbnormal);
		model.addAttribute("serveType", serveType);
		model.addAttribute("serveTypes", ServeType.values());
		model.addAttribute("page", orderService.findPage(query_map));
		model.addAttribute("doctorMechanismRelations", mechanism.getDoctorMechanismRelations(Audit.succeed));
		model.addAttribute("visitTypes", VisitType.values());
		return "/mechanism/order/list";
	}

	
	/**
	 * 导出订单管理
	 * @param sn
	 * @param projectName
	 * @param doctorName
	 * @param memberName
	 * @param patientName
	 * @param phone
	 * @param startDate
	 * @param endDate
	 * @param serveState
	 * @param evaluate
	 * @param orderStatus
	 * @param paymentStatus
	 * @param shippingStatus
	 * @param hasExpired
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downloadList", method = RequestMethod.GET)
	public ModelAndView downloadList(String sn ,String projectName,String doctorName,String memberName,String patientName,String phone,Date startDate,Date endDate,
			ServeState serveState,Evaluate evaluate,
			OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired,
			Pageable pageable, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("sn", sn);
		query_map.put("projectName", projectName);
		query_map.put("doctorName", doctorName);
		query_map.put("memberName", memberName);
		query_map.put("patientName", patientName);
		query_map.put("phone", phone);
		Calendar calendar = Calendar.getInstance();
		if (startDate!=null) {
			calendar.setTime(startDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startDate = calendar.getTime();
		}
		if (endDate!=null) {
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endDate = calendar.getTime();
		}
		query_map.put("startDate", startDate);
		query_map.put("endDate", endDate);
		query_map.put("serveState", serveState);
		query_map.put("evaluate", evaluate);
		query_map.put("orderStatus", orderStatus);
		query_map.put("paymentStatus", paymentStatus);
		query_map.put("shippingStatus", shippingStatus);
		query_map.put("hasExpired", hasExpired);
		query_map.put("pageable", pageable);
		
		List<Order> orders = orderService.downloadList(query_map);
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for (Order order : orders) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("orderPatientMemberName", order.getPatientMember().getName());
			data_map.put("orderConsignee", order.getConsignee());
			data_map.put("orderPhone", order.getPhone());
			data_map.put("orderProjectName",order.getOrderItems().get(0).getName());
			data_map.put("orderDoctorName",order.getDoctor().getName());
			data_map.put("orderDoctorMobile", order.getDoctor().getMobile());
			String orderWorkDayItemTime = "-";
			if (order.getWorkDayItem()!=null) {
				orderWorkDayItemTime = order.getWorkDayItem().getWorkDay().getWorkDayDate() +" "+  order.getWorkDayItem().getStartTime() + "-" +order.getWorkDayItem().getEndTime();
			}
			data_map.put("orderWorkDayItemTime",orderWorkDayItemTime);
			String expire = order.isExpired()?"(已过期)":"";
			data_map.put("orderStatus", message("Order.OrderStatus." + order.getOrderStatus())+expire);
			data_map.put("paymentStatus", message("Order.PaymentStatus." + order.getPaymentStatus()));
			data_map.put("orderSn", order.getSn());
			data_map.put("orderMemo", order.getMemo());
			data_list.add(data_map);
		}
		
		String filename = "订单管理" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"患者姓名","下单用户","联系电话","服务项目","服务医师","医师电话","预约时间","订单状态","支付状态","订单编号","备注"};//title
		String[] contents = new String []{"orderPatientMemberName","orderConsignee","orderPhone","orderProjectName","orderDoctorName","orderDoctorMobile","orderWorkDayItemTime","orderStatus","paymentStatus","orderSn","orderMemo"};//content
		
		String[] memos = new String [3];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				System.out.println(id);
				Order order = orderService.find(id);
				order.setMechanismIsDeleted(true);
				orderService.update(order);
			}
			
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 预约统计
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/charge", method = RequestMethod.GET)
	public String charge( ModelMap model, String nameOrmoible,Date createDate,Date endDate,Pageable pageable ) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		List<Date> dates = null;
		if (createDate==null||endDate==null) {
			 dates = DateUtil.getAllTheDateOftheMonth(new Date());
		}else{
			dates =DateUtil.findDates(createDate,endDate);
		}
		
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		String accomplishB = "0.00";
		String cancelledB = "0.00";
		int count = 0;//总次数
		int countAccomplish =0;//已服务(总次数)
		int countCancelled = 0;//已取消(总次数)
		for (Date date : dates) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			int count_accomplish=0;//已服务(次数)
			int count_cancelled = 0;//已取消(次数)
			query_map.put("date", date);
			query_map.put("nameOrmoible", nameOrmoible);
			List<Order> orders = orderService.getMonthAbout(query_map);
			for (Order order : orders) {
			
				if (order.getServeState().equals(ServeState.accomplish)) {
					count_accomplish++;
				}
				if (order.getOrderStatus().equals(OrderStatus.cancelled)) {
					count_cancelled++;
				}
			}
			data_map.put("date", date);
			data_map.put("count_accomplish", count_accomplish);
			data_map.put("count_cancelled", count_cancelled);
			data_list.add(data_map);
			countAccomplish = (countAccomplish + count_accomplish);
			countCancelled = (countCancelled + count_cancelled);
			count=(count_accomplish+count_cancelled+count);
		}
		NumberFormat numberFormat = NumberFormat.getInstance();

		// 设置精确到小数点后2位

		numberFormat.setMaximumFractionDigits(2);
		
		if (count>0) {
			accomplishB = numberFormat.format((float) countAccomplish / (float) count * 100);
		    cancelledB = numberFormat.format((float) countCancelled / (float) count * 100);
		}
		System.out.println("总共"+data_list.size()+"条记录");
		Integer last = pageable.getPageSize()*pageable.getPageNumber()>data_list.size()?data_list.size():pageable.getPageSize()*pageable.getPageNumber();
		
		Page<Map<String,Object>> page = new Page<Map<String,Object>>( data_list.subList((pageable.getPageNumber()-1)*pageable.getPageSize(), last), data_list.size(), pageable);
		
		model.addAttribute("dates", dates);
//		model.addAttribute("data_list", data_list);
		model.addAttribute("page", page);
		model.addAttribute("createDate", createDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("nameOrmoible", nameOrmoible);
		
		model.addAttribute("countAccomplish", countAccomplish);
		model.addAttribute("countCancelled", countCancelled);
		model.addAttribute("accomplishB", accomplishB);
		model.addAttribute("cancelledB", cancelledB);
		return "/mechanism/order/charge";
	}
	
	
	/**
	 * 就诊详情
	 * @param patientMemberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/patient_order", method = RequestMethod.GET)
	public String patient_order(Pageable pageable ,Long patientMemberId, ModelMap model) {

		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patientMember = memberService.find(patientMemberId);

		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("patientMember", patientMember);
		query_map.put("pageable", pageable);
		
		Page<Order> page = orderService.getPatientOrders(query_map);
		
		model.addAttribute("page", page);
		model.addAttribute("patientMember", patientMember);
		model.addAttribute("colour", "2");
		model.addAttribute("healthTypes", HealthType.values());
		model.addAttribute("patientMechanism", patientMember.getPatientMechanism(mechanism));
		return "/mechanism/order/patient_order";
	}
	
	
	
	
	
}