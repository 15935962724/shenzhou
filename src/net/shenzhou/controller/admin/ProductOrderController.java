package net.shenzhou.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.shenzhou.Pageable;
import net.shenzhou.entity.Payment;
import net.shenzhou.entity.Refunds;
import net.shenzhou.entity.ProductOrder.OrderStatus;
import net.shenzhou.entity.ProductOrder.PaymentStatus;
import net.shenzhou.entity.ProductOrder.ShippingStatus;
import net.shenzhou.service.ProductOrderService;

@Controller("adminProductOrderController")
@RequestMapping("/admin/productOrder")
public class ProductOrderController extends BaseController{
	
	@Resource(name = "productOrderServiceImpl")
	private ProductOrderService productOrderService;
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired, Pageable pageable, ModelMap model) {
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("paymentStatus", paymentStatus);
		model.addAttribute("shippingStatus", shippingStatus);
		model.addAttribute("hasExpired", hasExpired);
		model.addAttribute("page", productOrderService.findPage(orderStatus, paymentStatus, shippingStatus, hasExpired, pageable));
		return "/admin/productOrder/list";
	}
	
	
	
	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		
		model.addAttribute("order", productOrderService.find(id));
		return "/admin/productOrder/view";
	}


}
