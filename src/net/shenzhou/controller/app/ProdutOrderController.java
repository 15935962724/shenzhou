package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.json.JSONObject;
import net.shenzhou.Setting;
import net.shenzhou.dao.ProductOrderLogDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.MemberPointLog;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductCoupon;
import net.shenzhou.entity.ProductOrder;
import net.shenzhou.entity.ProductOrderLog;
import net.shenzhou.entity.ProductOrder.OrderStatus;
import net.shenzhou.entity.ProductOrder.PaymentStatus;
import net.shenzhou.entity.ProductOrder.PaymentType;
import net.shenzhou.entity.ProductOrder.ShippingStatus;
import net.shenzhou.entity.ProductOrderLog.Type;
import net.shenzhou.service.MemberPointLogService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.ProductOrderService;
import net.shenzhou.service.ProductService;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.SettingUtils;

@Controller("appProductOrderController")
@RequestMapping("/app/productOrder")
public class ProdutOrderController extends BaseController{

	
	/** logger */
	private static final Logger logger = Logger.getLogger(ProdutOrderController.class.getName());
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productOrderServiceImpl")
	private ProductOrderService productOrderService;
	@Resource(name = "memberPointLogServiceImpl")
	private MemberPointLogService memberPointLogService;
	@Resource(name = "productOrderLogDaoImpl")
	private ProductOrderLogDao productOrderLogDao;
	
	
	
	/** 
	 * ????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public void submit(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			
			String safeKeyValue = json.getString("safeKeyValue");
			String productId = json.getString("productId");//??????id
			Integer stock = json.getInt("stock");//????????????
			String paymentType = json.getString("paymentType");//????????????
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			/*if(member.getPaymentPassword()==null||member.getPaymentPassword().equals("")){
				map.put("status", "600");
				map.put("message", "?????????????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(!member.getIsReal()){
				map.put("status", "666");
				map.put("message", "?????????????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}*/
			Product product = productService.find(Long.parseLong(productId));
			if(product.getStock() < stock){
				map.put("status", "404");
				map.put("message", "???????????????????????????,???????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			Map<String,Object> map_order = new HashMap<String, Object>();
			map_order.put("product", product);
			map_order.put("member", member);
			map_order.put("stock", stock);
			map_order.put("paymentType", paymentType);
			ProductOrder pOrder = productOrderService.create(map_order);//????????????
			
			//??????????????????
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("productName", product.getName());
			data_map.put("stock", stock);
			data_map.put("price", product.getPrice());
			data_map.put("price", pOrder.getPaymentType());
			data_map.put("spendPaid", pOrder.getSpendPaid());//???
			data_map.put("spendPoint", pOrder.getSpendPoint());//?????????
			data_map.put("pointPaid",pOrder.getSpendPoint()+"+"+ pOrder.getSpendPaid());//???????????????
			data_map.put("id", pOrder.getId());
			data_map.put("balance", member.getBalance());//????????????
			data_map.put("point",member.getCountPoint());//???????????????
			data_map.put("orderSn", pOrder.getSn());//????????????
			
			map.put("status", "200");
			map.put("message", "??????????????????");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
		}catch (Exception e){
			// TODO: handle exception
				System.out.println(e.getMessage());
				map.put("status", "400");
				map.put("message", e.getMessage());
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
		}
	}
	
	/** 
	 * ??????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public void payment(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        try {
			
			JSONObject json = JSONObject.fromObject(file);

			String safeKeyValue = json.getString("safeKeyValue");//??????
			String sn = json.getString("sn");//????????????
			String payType = json.getString("paymentType");//????????????
			String productId = json.getString("productId");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString());
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString());
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if (!member.getIsReal()) {
				map.put("status", "666");
				map.put("message", "?????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			String paymentPassword = json.getString("paymentPassword");
			if (member.getPaymentPassword()==null) {
				map.put("status", "400");
				map.put("message", "?????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if((!member.getPaymentPassword().equals(DigestUtils.md5Hex(paymentPassword)))){
				map.put("status", "400");
				map.put("message", "????????????????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			ProductOrder order = productOrderService.findBySn(sn);
			//????????????
			ProductOrderLog orderLog = new ProductOrderLog();
			orderLog.setType(Type.confirm);
			orderLog.setOperator(member.getName());
			orderLog.setContent("");
			orderLog.setIsDeleted(false);
			orderLog.setProductOrder(order);
			productOrderLogDao.merge(orderLog);
			
			
			
			
			Product product = productService.find(Long.parseLong(productId));
			/**************????????????????????????????????????????????????,?????????????????????******fl 2018/03/20*******/
			/*if(payType.equals("mechanism")){
				Mechanism mechanism = order.getMember().getMechanism();
				Balance balance = member.getBalance(mechanism);
				if(balance==null){
					map.put("status", "500");
					map.put("message", "??????????????????,?????????"+mechanism.getName()+"??????");
					map.put("data", "{}");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}
			}else */
			//* ????????? healthGold,/** ??? */money,/**??????????????? */healthyMoney

			if(payType.equals("healthGold")){
				Long point = member.getCountPoint();
				if(point == null || point == 0){
					map.put("status", "400");
					map.put("message", "?????????????????????,?????????????????????????????????");
					map.put("data", "{}");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}else{
					BigDecimal pint = new BigDecimal(point);
					if(pint.compareTo(order.getSpendPoint()) == -1){//?????????????????????????????????????????????
						map.put("status", "400");
						map.put("message", "?????????????????????,?????????????????????????????????");
						map.put("data", "{}");
						printWriter.write(JsonUtils.toString(map)) ;
						return;
					}
				}
			}
			if(payType.equals("money")){
				BigDecimal balance = member.getBalance();
				if(balance==null){
					map.put("status", "500");
					map.put("message", "??????????????????,?????????");
					map.put("data", "{}");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}else{
					if(balance.compareTo(order.getSpendPaid()) == -1){
						map.put("status", "500");
						map.put("message", "??????????????????,?????????");
						map.put("data", "{}");
						printWriter.write(JsonUtils.toString(map)) ;
						return;
					}
				}
			}
			Setting setting = SettingUtils.get();
			BigDecimal pint = new BigDecimal(setting.getPoint());//????????????????????????
			BigDecimal money = setting.getMoney();//??????????????????
			List<MemberPointLog> list = member.getMemberPointLogs();
			/*BigDecimal pint = new BigDecimal(1);//????????????????????????
			BigDecimal money = new BigDecimal(1);//??????????????????
*/			
			if(payType.equals("healthyMoney")){//???????????????
				if(list != null){
					BigDecimal num = product.getPrice().multiply(new BigDecimal(order.getNumber()));
					BigDecimal point = new BigDecimal(list.get(list.size()-1).getPoint());//??????????????????
					BigDecimal surplus = num.subtract(point);//?????????????????????????????????????????????
					BigDecimal balance = money.divide(pint).multiply(surplus);//??????????????????????????????
					member.setBalance(member.getBalance().subtract(balance));
					memberService.update(member);//?????????
					
					//????????????
					/*MemberPointLog pointLog = new MemberPointLog();
					pointLog.setId(list.get(list.size()-1).getId());
					pointLog.setPoint(0L);
					memberPointLogService.merge(pointLog);*/
					
					MemberPointLog pointLog = memberPointLogService.find(list.get(list.size()-1).getId());
					//pointLog.setId(list.get(list.size()-1).getId());
					pointLog.setCredit(0L);
					pointLog.setDebit(point.longValue());
					pointLog.setPoint(0L);
					pointLog.setType(net.shenzhou.entity.MemberPointLog.Type.consumption);
					pointLog.setMember(member);
					pointLog.setMemo("??????????????????");
					pointLog.setProductOrder(order);
					memberPointLogService.save(pointLog);
				}
			}
			if(payType.equals("healthGold")) {//????????????
				
				if(list != null){
					BigDecimal point = new BigDecimal(list.get(list.size()-1).getPoint());
					BigDecimal points = point.subtract(order.getSpendPoint());
					MemberPointLog pointLog = memberPointLogService.find(list.get(list.size()-1).getId());
					//pointLog.setId(list.get(list.size()-1).getId());
					pointLog.setPoint(points.longValue());
					pointLog.setCredit(points.longValue());
					pointLog.setDebit(order.getSpendPoint().longValue());
					pointLog.setType(net.shenzhou.entity.MemberPointLog.Type.consumption);
					pointLog.setMember(member);
					pointLog.setMemo("??????????????????");
					pointLog.setProductOrder(order);
					memberPointLogService.save(pointLog);
				}
				
			}
			
			
			if (payType.equals("money")) {//????????????
				member.setBalance(member.getBalance().subtract(order.getSpendPaid()));
				memberService.update(member);
			}
			order.setPaymentStatus(PaymentStatus.paid);
			order.setOrderStatus(OrderStatus.completed);
			order.setShippingStatus(ShippingStatus.shipped);
			
			productOrderService.update(order);
			
			//????????????
			ProductOrderLog orderLogs = new ProductOrderLog();
			orderLogs.setType(Type.payment);
			orderLogs.setOperator(member.getName());
			orderLogs.setContent("");
			orderLogs.setIsDeleted(false);
			orderLogs.setProductOrder(order);
			productOrderLogDao.merge(orderLogs);
			
			
			map.put("status", "200");
			map.put("message", "????????????");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return ;
        }catch(Exception e){
        	// TODO: handle exception
        	e.printStackTrace();
        	System.out.println(e.getMessage());
        	map.put("status", "400");
        	map.put("message", e.getMessage());
        	map.put("data", new Object());
        	printWriter.write(JSONObject.fromObject(map).toString()) ;
        	return;
        }
	}
	
	
	
	/**
	 * ????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/orderList", method = RequestMethod.GET)
	public void orderList(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            Member member = memberService.findBySafeKeyValue(safeKeyValue); 
            if(member == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			Boolean isDelete = false;
			if(json.containsKey("isDelete")){
				isDelete = json.getBoolean("isDelete");
			}
			
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			String paymentStatus = json.getString("paymentStatus");//?????????
			String orderStatus =  json.getString("orderStatus");//?????????
			String shippingStatus = json.getString("shippingStatus");//?????????
			Integer pageNumber = json.getInt("pageNumber");//??????
			
			
			data_map.put("paymentStatus", paymentStatus);//?????????
			data_map.put("orderStatus", orderStatus);//?????????
			data_map.put("member",member);//??????
			data_map.put("pageNumber",pageNumber);//??????
			data_map.put("shippingStatus", shippingStatus);
			
			/*//??????????????????????????????????????????
			orderService.memberAwaitToRecord(member);
			//??????????????????????????????????????????
			orderService.memberRecordToCompleted(member,beforehandLogin);*/
			
			map = productOrderService.findList(data_map);
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
	
	
	@RequestMapping(value = "/isOrder", method = RequestMethod.GET)
	public void isOrder(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		Map<String, Object> product_map = new HashMap<String, Object>();
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			file = StringEscapeUtils.unescapeHtml(file);
			JSONObject json = JSONObject.fromObject(file);
			Long productId = json.getLong("productId");
			 String safeKeyValue = json.getString("safeKeyValue");
				if(StringUtils.isEmpty(safeKeyValue)){
					map.put("status", "300");
					map.put("message", "????????????????????????");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
				
				Member member = memberService.findBySafeKeyValue(safeKeyValue);
				if(member == null){
					map.put("status", "300");
					map.put("message", "????????????,???????????????");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
				if(member.getSafeKey().hasExpired()){
					map.put("status", "300");
					map.put("message", "????????????,???????????????");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
			
			
			Product product  = productService.find(productId);//??????????????????
			BigDecimal point = new BigDecimal(member.getCountPoint());//??????????????????
			Setting setting = SettingUtils.get();
			/*BigDecimal pint = new BigDecimal(setting.getPoint());//????????????????????????
			BigDecimal money = setting.getMoney();//??????????????????
*/			
			BigDecimal pint = new BigDecimal(1);//????????????????????????
			BigDecimal money = new BigDecimal(1);//??????????????????
			if(pint.compareTo(BigDecimal.ZERO) == 0 && money.compareTo(BigDecimal.ZERO) == 0){
				map.put("status", "400");
				map.put("message", "????????????????????????????????????");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			//???????????????????????????????????????????????????
			if(point.compareTo(product.getPrice())==1 && point.compareTo(product.getPrice())== 0){
				
				BigDecimal productPrice = product.getPrice();//????????????
				BigDecimal totalPrice = money.divide(pint).multiply(productPrice);//???????????????????????????????????????????????????????????????????????????????????????
				BigDecimal surplus = productPrice.subtract(point);//?????????????????????????????????????????????
				BigDecimal balance = money.divide(pint).multiply(surplus);//??????????????????????????????
				
				/*
				Map<String,Object> map_map = new HashMap<String, Object>();
				map_map.put("id", "healthGold");
				map_map.put("price", productPrice);
				list.add(map_map);
				map_map.put("id", "money");
				map_map.put("price", totalPrice);
				list.add(map_map);
				map_map.put("id", "healthyMoney");
				map_map.put("price", point+"+"+balance);
				list.add(map_map);*/
				
				/*product_map.put("point",productPrice);
				product_map.put("price",totalPrice);
				product_map.put("pointPrice",point+"+"+balance);*/
				
				product_map.put("point",member.getCountPoint());//?????????
				product_map.put("balance",member.getBalance());//???
				product_map.put("pointPrice",point+"+"+balance);//??????
				
			}
			////??????????????????????????????????????????
			if(point.compareTo(product.getPrice())==-1){
				
				BigDecimal productPrice = product.getPrice();//????????????
				BigDecimal totalPrice = money.divide(pint).multiply(productPrice);//???????????????????????????????????????????????????????????????????????????????????????
				BigDecimal surplus = productPrice.subtract(point);//?????????????????????????????????????????????
				BigDecimal balance = money.divide(pint).multiply(surplus);//??????????????????????????????
				
				
				//List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				/*Map<String,Object> map_map = new HashMap<String, Object>();
				map_map.put("id", "healthGold");
				map_map.put("price", productPrice);
				list.add(map_map);
				map_map = new HashMap<String, Object>();
				map_map.put("id", "money");
				map_map.put("price", totalPrice);
				list.add(map_map);
				map_map = new HashMap<String, Object>();
				map_map.put("id", "healthyMoney");
				map_map.put("price", point+"+"+balance);
				list.add(map_map);*/
				
				
				
				product_map.put("point",member.getCountPoint());//?????????
				product_map.put("balance",member.getBalance());//???
				product_map.put("pointPrice",point+"+"+balance);//??????
				
			}
			product_map.put("product", product);
			product_map.put("list", list);
			
			map.put("status", "200");
			map.put("data", JsonUtils.toJson(product_map));
			map.put("message", "??????????????????");
			printWriter.write(JsonUtils.toString(map));
			return;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString());
			return;
		}
	}
	
	
	
	/**
	 * ????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/orderView", method = RequestMethod.GET)
	public void orderView(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
			
			
			
			
			
			
			ProductOrder productOrder = productOrderService.find(json.getLong("productOrderId"));
			
			List<Map<String, Object>> list_map = new ArrayList<Map<String,Object>>();
			map.put("orderSn", productOrder.getSn());
			map.put("productNuber", productOrder.getNumber());
			map.put("productName", productOrder.getProduct().getName());
			map.put("productImage", productOrder.getProduct().getImage());
			map.put("paymentType", productOrder.getPaymentType());
			if(productOrder.getPaymentType().equals(PaymentType.healthGold)){
				map.put("point", productOrder.getSpendPoint());
			}
			if(productOrder.getPaymentType().equals(PaymentType.money)){
				map.put("point", productOrder.getSpendPaid());
			}
			if(productOrder.getPaymentType().equals(PaymentType.healthyMoney)){
				map.put("point", productOrder.getSpendPoint()+"+"+productOrder.getSpendPaid());
			}
			map.put("OrderDate", productOrder.getCreateDate());
			List<Map<String, Object>> list_coupon = new ArrayList<Map<String,Object>>();
			List<ProductCoupon> coupon = productOrder.getProduct().getProductCoupons();
			for(ProductCoupon pc : coupon){
				Map<String,Object> map_coupon = new HashMap<String, Object>();
				map_coupon.put("price", pc.getPrice());
				map_coupon.put("couponName", pc.getCoupon().getName());
				map_coupon.put("couponType", pc.getCoupon().getCouponType());
				map_coupon.put("statusDate", pc.getCoupon().getBeginDate());
				map_coupon.put("endDate", pc.getCoupon().getEndDate());
				map_coupon.put("quantity", pc.getQuantity());
				
				List<Map<String,Object>> mechanism_list = new ArrayList<Map<String,Object>>();
				for (Mechanism mechanism : pc.getCoupon().getMechanisms()) {
					Map<String,Object> mechanism_map = new HashMap<String, Object>();
					mechanism_map.put("name", mechanism.getName());
					mechanism_list.add(mechanism_map);
				}
				map_coupon.put("mechanism", mechanism_list);
				list_coupon.add(map_coupon);
			}
			map.put("coupon", list_coupon);
			//list_map.add(map);
			Map<String,Object> map_list = new HashMap<String, Object>();
			map_list.put("product", list_map);
			map_list.put("status", "200");
			map_list.put("data", JsonUtils.toJson(map));
			map_list.put("message", "??????????????????");
			printWriter.write(JsonUtils.toString(map_list)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
}
