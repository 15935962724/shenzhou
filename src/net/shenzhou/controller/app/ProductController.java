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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.json.JSONObject;
import net.shenzhou.Setting;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductCoupon;
import net.shenzhou.entity.ProductOrder.PaymentType;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.ProductCouponService;
import net.shenzhou.service.ProductService;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.SettingUtils;

/**
 * 积分兑换商品
 * 
 * @author wenlf
 *
 */
@Controller("appProductController")
@RequestMapping("/app/product")
public class ProductController extends BaseController {

	/** logger */
	private static final Logger logger = Logger.getLogger(ProductController.class.getName());

	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@Resource(name = "productCouponServiceImpl")
	private ProductCouponService productCouponService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	
	
	/**
	 * 商品列表
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/productList", method = RequestMethod.GET)
	public void productList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		
		try {
			file = StringEscapeUtils.unescapeHtml(file);
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
			
			
			map = productService.findList(file);
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
	 * 商品详情
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void view(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			BigDecimal pint = new BigDecimal(setting.getPoint());//平台设置的健康金
			BigDecimal money = setting.getMoney();//平台设置的钱
			
			
			if(pint.compareTo(BigDecimal.ZERO) == 0 && money.compareTo(BigDecimal.ZERO) == 0){
				map.put("status", "400");
				map.put("message", "系统信息错误请联系管理员");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			//判断自身健康金大于和等于商品的金额
			if(point.compareTo(product.getPrice())==1 || point.compareTo(product.getPrice())== 0){
				
				BigDecimal productPrice = product.getPrice();//商品价格
				BigDecimal totalPrice = money.divide(pint).multiply(productPrice);//用平台设置的钱除以平台设置的积分在乘以商品的价格得到多少钱
				/*BigDecimal surplus = point.subtract(productPrice);//用商品价格减去自己剩余的健康金
				BigDecimal balance = money.divide(pint).multiply(totalPrice);//除去健康金需要花的钱
*/				
				
				Map<String,Object> map_map = new HashMap<String, Object>();
				map_map.put("id", "healthGold");
				map_map.put("price", productPrice);
				list.add(map_map);
				map_map = new HashMap<String, Object>();
				map_map.put("id", "money");
				map_map.put("price", totalPrice);
				list.add(map_map);
				/*map_map = new HashMap<String, Object>();
				map_map.put("id", "healthyMoney");
				map_map.put("price", point+"+"+balance);
				list.add(map_map);*/
				
				/*product_map.put("point",productPrice);
				product_map.put("price",totalPrice);
				product_map.put("pointPrice",point+"+"+balance);
				product_map.put("flag",false);//false不显示健康金加钱的按钮
				product_map.put("isAsh",false);//是否变灰:false不变灰
*/			}
			////判断自身健康金小于商品的金额
			if(point.compareTo(product.getPrice())==-1){
				
				BigDecimal productPrice = product.getPrice();//商品价格
				BigDecimal totalPrice = money.divide(pint).multiply(productPrice);//用平台设置的钱除以平台设置的积分在乘以商品的价格得到多少钱
				BigDecimal surplus = productPrice.subtract(point);//用商品价格减去自己剩余的健康金
				BigDecimal balance = money.divide(pint).multiply(surplus);//除去健康金需要花的钱
				
				
				//List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				Map<String,Object> map_map = new HashMap<String, Object>();
				/*map_map.put("id", "healthGold");
				map_map.put("price", productPrice);
				list.add(map_map);*/
				map_map = new HashMap<String, Object>();
				map_map.put("id", "money");
				map_map.put("price", totalPrice);
				list.add(map_map);
				map_map = new HashMap<String, Object>();
				map_map.put("id", "healthyMoney");
				map_map.put("price", point+"+"+balance);
				list.add(map_map);
				
				
				
				/*product_map.put("point",productPrice);
				product_map.put("price",totalPrice);
				product_map.put("pointPrice",point+"+"+balance);
				product_map.put("flag",true);//trie显示健康金加钱的按钮
				product_map.put("isAsh",true);//是否变灰:true变灰
*/			}
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
	 * 图文介绍
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/introduction", method = RequestMethod.GET)
	public String introduction(String file, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		file = StringEscapeUtils.unescapeHtml(file);
		JSONObject json = JSONObject.fromObject(file);
		Long productId = json.getLong("productId");
		Product product = productService.find(productId);

		model.addAttribute("introduction", product.getIntroduction());
		return "/app/product/product_introduction";
	}

	
	/**
	 * 商品规格
	 * @param file
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/coupon", method = RequestMethod.GET)
	public String coupon(String file, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		file = StringEscapeUtils.unescapeHtml(file);
		JSONObject json = JSONObject.fromObject(file);
		List<ProductCoupon> pc_list = new ArrayList<ProductCoupon>();
		Long productId = json.getLong("productId");
		Product product = productService.find(productId);
		List<Map<String, Object>> map_List = new ArrayList<Map<String, Object>>();
		pc_list = productCouponService.findByProductId(product);
		for (int i = 0; i < pc_list.size(); i++) {
			Map<String, Object> map_map = new HashMap<String, Object>();
			if (map_List.size() > 0) {
				if (pc_list.get(i).getCoupon().getId() == map_List.get(i - 1).get("id")) {
					Integer num = (Integer) map_List.get(i - 1).get("number");
					map_List.get(i - 1).put("number", num + 1);
				} else {
					map_map.put("id", pc_list.get(i).getCoupon().getId());
					map_map.put("name", pc_list.get(i).getCoupon().getName());
					map_map.put("beginDate", pc_list.get(i).getCoupon().getBeginDate());
					map_map.put("endDate", pc_list.get(i).getCoupon().getEndDate());
					System.out.println(pc_list.get(i).getCoupon().getCouponType());
					map_map.put("couponType", pc_list.get(i).getCoupon().getCouponType());
					map_map.put("number", 1);
					map_map.put("mechanism", pc_list.get(i).getCoupon().getMechanisms());
					System.out.println(pc_list.get(i).getCoupon().getMechanisms());
					map_map.put("price", pc_list.get(i).getPrice());
					map_List.add(map_map);
				}

			} else {
				map_map.put("id", pc_list.get(i).getCoupon().getId());
				map_map.put("name", pc_list.get(i).getCoupon().getName());
				map_map.put("beginDate", pc_list.get(i).getCoupon().getBeginDate());
				map_map.put("endDate", pc_list.get(i).getCoupon().getEndDate());
				System.out.println(pc_list.get(i).getCoupon().getCouponType());
				map_map.put("couponType", pc_list.get(i).getCoupon().getCouponType());
				map_map.put("number", 1);
				map_map.put("mechanism", pc_list.get(i).getCoupon().getMechanisms());
				System.out.println(pc_list.get(i).getCoupon().getMechanisms());
				map_map.put("price", pc_list.get(i).getPrice());
				map_List.add(map_map);
			}
		}
		model.addAttribute("coupon", map_List);
		
		return "/app/product/product_coupon";
	}
	
	
	
	/**
	 * 保障说明
	 * @param file
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/guarantee", method = RequestMethod.GET)
	public String guarantee(String file, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		file = StringEscapeUtils.unescapeHtml(file);
		JSONObject json = JSONObject.fromObject(file);
		Long productId = json.getLong("productId");
		Product product = productService.find(productId);

		model.addAttribute("introduction", product.getIntroduction());
		return "/app/product/product_guarantee";
	}
	
	
	/**
	 * 校验数量
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/isNumber", method = RequestMethod.GET)
	public void isNumber(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			file = StringEscapeUtils.unescapeHtml(file);
			JSONObject json = JSONObject.fromObject(file);
			Long productId = json.getLong("productId");
			String safeKeyValue = json.getString("safeKeyValue");
			Integer stock = json.getInt("stock");
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
			
			
			Product product  = productService.find(productId);//获取商品详情
			if(stock > product.getStock()){
				map.put("status", "400");
				map.put("message", "你选的数量超过了库存数,请重新选择");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
		
			
			BigDecimal count = product.getPrice().multiply(new BigDecimal(stock));// 得到总花费健康金
			Setting setting = SettingUtils.get();
			BigDecimal pint = new BigDecimal(setting.getPoint());// 平台设置的健康金
			BigDecimal money = setting.getMoney();// 平台设置的钱
		
			/*BigDecimal pint = new BigDecimal(1);// 平台设置的健康金
			BigDecimal money = new BigDecimal(1);// 平台设置的钱
*/			
			if (paymentType.equals("healthGold")) {//健康金
				if(count.compareTo(new BigDecimal(member.getCountPoint())) == 1){
					map.put("status", "200");
					map.put("message", "加载成功");
					Map<String,Object> map_status = new HashMap<String, Object>();
					map_status.put("status", "500");
					map_status.put("message", "你的健康金不足");
					map.put("data", map_status);
					printWriter.write(JsonUtils.toJson(map)) ;
					return;
				}
			}
			BigDecimal totalPrice = money.divide(pint).multiply(product.getPrice());//用平台设置的钱除以平台设置的积分在乘以商品的价格得到多少钱
			if (paymentType.equals("money")) {
				if (totalPrice.compareTo(member.getBalance()) == 1) {// 如果自身没有健康金,全部用钱支付
					map.put("status", "200");
					map.put("message", "加载成功");
					Map<String,Object> map_status = new HashMap<String, Object>();
					map_status.put("status", "500");
					map_status.put("message", "账户余额不足,请先充值");
					map.put("data", map_status);
					printWriter.write(JsonUtils.toJson(map)) ;
					return;
				}
			}
			if (paymentType.equals("healthyMoney")) {
					BigDecimal surplus = product.getPrice().subtract(new BigDecimal(member.getCountPoint()));//用商品价格减去自己剩余的健康金
					BigDecimal balance = money.divide(pint).multiply(surplus);//除去健康金需要花的钱
					if(balance.compareTo(member.getBalance()) == 1){
						map.put("status", "200");
						map.put("message", "加载成功");
						Map<String,Object> map_status = new HashMap<String, Object>();
						map_status.put("status", "500");
						map_status.put("message", "账户余额不足,请先充值");
						map.put("data", map_status);
						printWriter.write(JsonUtils.toJson(map)) ;
						return;
					}
					
			}
			
			
			
			
			map.put("status", "200");
			map.put("data", "{}");
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
}
