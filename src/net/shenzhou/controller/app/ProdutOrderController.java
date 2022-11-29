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
	 * 确认购买
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
			String productId = json.getString("productId");//商品id
			Integer stock = json.getInt("stock");//兑换数量
			String paymentType = json.getString("paymentType");//兑换类型
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			/*if(member.getPaymentPassword()==null||member.getPaymentPassword().equals("")){
				map.put("status", "600");
				map.put("message", "请设置支付密码");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(!member.getIsReal()){
				map.put("status", "666");
				map.put("message", "请进行实名认证");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}*/
			Product product = productService.find(Long.parseLong(productId));
			if(product.getStock() < stock){
				map.put("status", "404");
				map.put("message", "库存数量没有这么多,请重新选择");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			Map<String,Object> map_order = new HashMap<String, Object>();
			map_order.put("product", product);
			map_order.put("member", member);
			map_order.put("stock", stock);
			map_order.put("paymentType", paymentType);
			ProductOrder pOrder = productOrderService.create(map_order);//生成订单
			
			//回显订单数据
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("productName", product.getName());
			data_map.put("stock", stock);
			data_map.put("price", product.getPrice());
			data_map.put("price", pOrder.getPaymentType());
			data_map.put("spendPaid", pOrder.getSpendPaid());//钱
			data_map.put("spendPoint", pOrder.getSpendPoint());//健康金
			data_map.put("pointPaid",pOrder.getSpendPoint()+"+"+ pOrder.getSpendPaid());//健康金加钱
			data_map.put("id", pOrder.getId());
			data_map.put("balance", member.getBalance());//自身的钱
			data_map.put("point",member.getCountPoint());//自身健康金
			data_map.put("orderSn", pOrder.getSn());//订单编号
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
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
	 * 支付
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

			String safeKeyValue = json.getString("safeKeyValue");//秘钥
			String sn = json.getString("sn");//订单编号
			String payType = json.getString("paymentType");//支付方式
			String productId = json.getString("productId");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString());
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString());
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if (!member.getIsReal()) {
				map.put("status", "666");
				map.put("message", "请进行实名认证");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			String paymentPassword = json.getString("paymentPassword");
			if (member.getPaymentPassword()==null) {
				map.put("status", "400");
				map.put("message", "请设置支付密码");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if((!member.getPaymentPassword().equals(DigestUtils.md5Hex(paymentPassword)))){
				map.put("status", "400");
				map.put("message", "支付密码输入有误,请重新输入");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			ProductOrder order = productOrderService.findBySn(sn);
			//保存日志
			ProductOrderLog orderLog = new ProductOrderLog();
			orderLog.setType(Type.confirm);
			orderLog.setOperator(member.getName());
			orderLog.setContent("");
			orderLog.setIsDeleted(false);
			orderLog.setProductOrder(order);
			productOrderLogDao.merge(orderLog);
			
			
			
			
			Product product = productService.find(Long.parseLong(productId));
			/**************这里判断用户在当前机构有没有账户,没有则提示充值******fl 2018/03/20*******/
			/*if(payType.equals("mechanism")){
				Mechanism mechanism = order.getMember().getMechanism();
				Balance balance = member.getBalance(mechanism);
				if(balance==null){
					map.put("status", "500");
					map.put("message", "当前余额不足,请前往"+mechanism.getName()+"充值");
					map.put("data", "{}");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}
			}else */
			//* 健康金 healthGold,/** 钱 */money,/**健康金加钱 */healthyMoney

			if(payType.equals("healthGold")){
				Long point = member.getCountPoint();
				if(point == null || point == 0){
					map.put("status", "400");
					map.put("message", "当前健康金不足,请先去获取更多的健康金");
					map.put("data", "{}");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}else{
					BigDecimal pint = new BigDecimal(point);
					if(pint.compareTo(order.getSpendPoint()) == -1){//判断自身的健康金是否小于应付的
						map.put("status", "400");
						map.put("message", "当前健康金不足,请先去获取更多的健康金");
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
					map.put("message", "当前余额不足,请充值");
					map.put("data", "{}");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}else{
					if(balance.compareTo(order.getSpendPaid()) == -1){
						map.put("status", "500");
						map.put("message", "当前余额不足,请充值");
						map.put("data", "{}");
						printWriter.write(JsonUtils.toString(map)) ;
						return;
					}
				}
			}
			Setting setting = SettingUtils.get();
			BigDecimal pint = new BigDecimal(setting.getPoint());//平台设置的健康金
			BigDecimal money = setting.getMoney();//平台设置的钱
			List<MemberPointLog> list = member.getMemberPointLogs();
			/*BigDecimal pint = new BigDecimal(1);//平台设置的健康金
			BigDecimal money = new BigDecimal(1);//平台设置的钱
*/			
			if(payType.equals("healthyMoney")){//健康金加钱
				if(list != null){
					BigDecimal num = product.getPrice().multiply(new BigDecimal(order.getNumber()));
					BigDecimal point = new BigDecimal(list.get(list.size()-1).getPoint());//自身的健康金
					BigDecimal surplus = num.subtract(point);//用商品价格减去自己剩余的健康金
					BigDecimal balance = money.divide(pint).multiply(surplus);//除去健康金需要花的钱
					member.setBalance(member.getBalance().subtract(balance));
					memberService.update(member);//修改钱
					
					//修改积分
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
					pointLog.setMemo("积分兑换消费");
					pointLog.setProductOrder(order);
					memberPointLogService.save(pointLog);
				}
			}
			if(payType.equals("healthGold")) {//用健康金
				
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
					pointLog.setMemo("积分兑换消费");
					pointLog.setProductOrder(order);
					memberPointLogService.save(pointLog);
				}
				
			}
			
			
			if (payType.equals("money")) {//用钱兑换
				member.setBalance(member.getBalance().subtract(order.getSpendPaid()));
				memberService.update(member);
			}
			order.setPaymentStatus(PaymentStatus.paid);
			order.setOrderStatus(OrderStatus.completed);
			order.setShippingStatus(ShippingStatus.shipped);
			
			productOrderService.update(order);
			
			//保存日志
			ProductOrderLog orderLogs = new ProductOrderLog();
			orderLogs.setType(Type.payment);
			orderLogs.setOperator(member.getName());
			orderLogs.setContent("");
			orderLogs.setIsDeleted(false);
			orderLogs.setProductOrder(order);
			productOrderLogDao.merge(orderLogs);
			
			
			map.put("status", "200");
			map.put("message", "支付成功");
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
	 * 订单列表
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
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            Member member = memberService.findBySafeKeyValue(safeKeyValue); 
            if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			Boolean isDelete = false;
			if(json.containsKey("isDelete")){
				isDelete = json.getBoolean("isDelete");
			}
			
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			String paymentStatus = json.getString("paymentStatus");//待付款
			String orderStatus =  json.getString("orderStatus");//已完成
			String shippingStatus = json.getString("shippingStatus");//待发货
			Integer pageNumber = json.getInt("pageNumber");//页码
			
			
			data_map.put("paymentStatus", paymentStatus);//待付款
			data_map.put("orderStatus", orderStatus);//已完成
			data_map.put("member",member);//用户
			data_map.put("pageNumber",pageNumber);//页码
			data_map.put("shippingStatus", shippingStatus);
			
			/*//在这里处理待服务到带入档操作
			orderService.memberAwaitToRecord(member);
			//在这里处理带入档到已完成操作
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
					map.put("message", "还没登录请先登录");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
				
				Member member = memberService.findBySafeKeyValue(safeKeyValue);
				if(member == null){
					map.put("status", "300");
					map.put("message", "秘钥失效,请重新登录");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
				if(member.getSafeKey().hasExpired()){
					map.put("status", "300");
					map.put("message", "秘钥失效,请重新登录");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
			
			
			Product product  = productService.find(productId);//获取商品详情
			BigDecimal point = new BigDecimal(member.getCountPoint());//自身的健康金
			Setting setting = SettingUtils.get();
			/*BigDecimal pint = new BigDecimal(setting.getPoint());//平台设置的健康金
			BigDecimal money = setting.getMoney();//平台设置的钱
*/			
			BigDecimal pint = new BigDecimal(1);//平台设置的健康金
			BigDecimal money = new BigDecimal(1);//平台设置的钱
			if(pint.compareTo(BigDecimal.ZERO) == 0 && money.compareTo(BigDecimal.ZERO) == 0){
				map.put("status", "400");
				map.put("message", "系统信息错误请联系管理员");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			//判断自身健康金大于和等于商品的金额
			if(point.compareTo(product.getPrice())==1 && point.compareTo(product.getPrice())== 0){
				
				BigDecimal productPrice = product.getPrice();//商品价格
				BigDecimal totalPrice = money.divide(pint).multiply(productPrice);//用平台设置的钱除以平台设置的积分在乘以商品的价格得到多少钱
				BigDecimal surplus = productPrice.subtract(point);//用商品价格减去自己剩余的健康金
				BigDecimal balance = money.divide(pint).multiply(surplus);//除去健康金需要花的钱
				
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
				
				product_map.put("point",member.getCountPoint());//健康金
				product_map.put("balance",member.getBalance());//钱
				product_map.put("pointPrice",point+"+"+balance);//小计
				
			}
			////判断自身健康金小于商品的金额
			if(point.compareTo(product.getPrice())==-1){
				
				BigDecimal productPrice = product.getPrice();//商品价格
				BigDecimal totalPrice = money.divide(pint).multiply(productPrice);//用平台设置的钱除以平台设置的积分在乘以商品的价格得到多少钱
				BigDecimal surplus = productPrice.subtract(point);//用商品价格减去自己剩余的健康金
				BigDecimal balance = money.divide(pint).multiply(surplus);//除去健康金需要花的钱
				
				
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
				
				
				
				product_map.put("point",member.getCountPoint());//健康金
				product_map.put("balance",member.getBalance());//钱
				product_map.put("pointPrice",point+"+"+balance);//小计
				
			}
			product_map.put("product", product);
			product_map.put("list", list);
			
			map.put("status", "200");
			map.put("data", JsonUtils.toJson(product_map));
			map.put("message", "数据加载成功");
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
	 * 订单详情
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
			map_list.put("message", "数据加载成功");
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
