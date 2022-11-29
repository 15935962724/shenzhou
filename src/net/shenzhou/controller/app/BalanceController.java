package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Member;
import net.shenzhou.service.BalanceService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.MemberService;
import net.shenzhou.util.JsonUtils;
/**
 * 会员所有机构余额
 * @author wenlf
 *
 */
@Controller("appBalanceController")
@RequestMapping("/app/balance")
public class BalanceController {

	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "balanceServiceImpl")
	private BalanceService BalanceService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	
	/**
	 * 会员所有机构余额列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			
			String safeKeyValue = json.getString("safeKeyValue");
			
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
			    printWriter.write(JsonUtils.toString(map)) ;
			    return;
			}
			
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
			    printWriter.write(JsonUtils.toString(map)) ;
			    return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
			    printWriter.write(JsonUtils.toString(map)) ;
			    return;
			}
			
		     String status = "200";
			List<Balance> balance = BalanceService.getBalanceList(member);//获取机构账单
			
			
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
			BigDecimal notToMention = new BigDecimal(0);//不可提现
			BigDecimal canBepPesented = new BigDecimal(0);//可提现
			DecimalFormat df = new DecimalFormat("######0.00"); 
			for(Balance ba : balance){//处理机构钱包账单,机构下的总额
				BigDecimal incomes = new BigDecimal(ba.getBalance().toString());   
				notToMention = notToMention.add(incomes);
				Map<String,Object> balance_map = new HashMap<String, Object>();
				balance_map.put("mechanismNmae", ba.getMechanism().getName());
				balance_map.put("balance", Math.abs(Double.parseDouble(ba.getBalance().toString())));
				data_list.add(balance_map);
			}
			//获取可提现金额		
			canBepPesented = member.getBalance();
			Map<String,Object> balance_map = new HashMap<String, Object>();
			balance_map.put("mechanismNmae", "(好康护)平台系统");
			balance_map.put("balance", Math.abs(Double.parseDouble(member.getBalance().toString())));
			data_list.add(balance_map);
			//处理只保留小数两位
			Double pesented = Math.abs(Double.parseDouble(canBepPesented.toString()));
			Double mention = Math.abs(Double.parseDouble(notToMention.toString()));
			data_map.put("canBepPesented", df.format(pesented));
			data_map.put("notToMention", df.format(mention));
			data_map.put("countBalance", df.format(pesented+mention));
			data_map.put("balance", data_list);
			map.put("status", status);
			map.put("message","数据加载成功");
			map.put("data", JsonUtils.toJson(data_map));
	        printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	
	
}
