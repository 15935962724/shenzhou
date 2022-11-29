package net.shenzhou.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.Setting;
import net.shenzhou.dao.ProductOrderDao;
import net.shenzhou.dao.ProductOrderLogDao;
import net.shenzhou.dao.SnDao;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductOrder;
import net.shenzhou.entity.ProductOrder.Evaluate;
import net.shenzhou.entity.ProductOrder.OrderStatus;
import net.shenzhou.entity.ProductOrder.PaymentType;
import net.shenzhou.entity.ProductOrder.ShippingStatus;
import net.shenzhou.entity.ProductOrderLog;
import net.shenzhou.entity.ProductOrderLog.Type;
import net.shenzhou.entity.Sn;
import net.shenzhou.service.ProductOrderService;
import net.shenzhou.util.SettingUtils;

@Service("productOrderServiceImpl")
public class ProductOrderServiceImpl extends BaseServiceImpl<ProductOrder, Long>implements ProductOrderService {

	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "productOrderServiceImpl")
	private ProductOrderService productOrderService;
	@Resource(name = "productOrderDaoImpl")
	private ProductOrderDao productOrderDao;
	@Resource(name = "productOrderLogDaoImpl")
	private ProductOrderLogDao productOrderLogDao;

	@Resource(name = "productOrderDaoImpl")
	public void setBaseDao(ProductOrderDao productOrderDao) {
		super.setBaseDao(productOrderDao);
	}

	@Override
	public ProductOrder create(Map<String, Object> map) {
		Member member = (Member) map.get("member");
		Product product = (Product) map.get("product");
		Integer stock = (Integer) map.get("stock");
		String paymentType = (String) map.get("paymentType");
		ProductOrder productOrder = new ProductOrder();
		productOrder.setProduct(product);
		productOrder.setMember(member);
		productOrder.setSn(snDao.generate(Sn.Type.productOrder));
		productOrder.setShippingStatus(ShippingStatus.unshipped);
		productOrder.setOrderStatus(OrderStatus.unconfirmed);
		productOrder.setEvaluate(Evaluate.not);
		productOrder.setFee(new BigDecimal(0));
		productOrder.setNumber(stock);
		productOrder.setConsignee(member.getName());

		productOrder.setOrderStatus(OrderStatus.confirmed);// 已确认
		BigDecimal count = product.getPrice().multiply(new BigDecimal(stock));// 得到总花费健康金
		Long point = count.longValue();
		Setting setting = SettingUtils.get();
		BigDecimal pint = new BigDecimal(setting.getPoint());// 平台设置的健康金
		BigDecimal money = setting.getMoney();// 平台设置的钱
		if (paymentType.equals("healthGold")) {
			productOrder.setSpendPoint(count);// 保存花费健康金
			productOrder.setSpendPaid(new BigDecimal(0));// 保存花费的钱
			productOrder.setPaymentType(PaymentType.healthGold);
		}
		if (paymentType.equals("money")) {
			if (member.getCountPoint() == 0) {// 如果自身没有健康金,全部用钱支付
				BigDecimal balance = money.divide(pint).multiply(count);// 除去健康金需要花的钱
				productOrder.setSpendPaid(balance);// 保存花费的钱
				productOrder.setPaymentType(PaymentType.money);
				productOrder.setSpendPoint(new BigDecimal(0));// 保存花费健康金
			}
		}
		if (paymentType.equals("healthyMoney")) {
			if (member.getCountPoint() < point) {// 判断自身的健康金是否小于总共需要花费多少健康金
				Long p = point - member.getCountPoint();// 用总的健康金减去自身的得到剩余的
				productOrder.setSpendPoint(new BigDecimal(member.getCountPoint()));// 保存花费健康金
				BigDecimal balance = money.divide(pint).multiply(new BigDecimal(p));// 除去健康金需要花的钱
				productOrder.setSpendPaid(balance);// 保存花费的钱
				productOrder.setPaymentType(PaymentType.healthyMoney);
			}
		}
		productOrderService.save(productOrder);
		
		ProductOrderLog orderLog = new ProductOrderLog();
		orderLog.setType(Type.create);
		orderLog.setOperator(member.getName());
		orderLog.setContent("");
		orderLog.setIsDeleted(false);
		orderLog.setProductOrder(productOrder);
		productOrderLogDao.merge(orderLog);
		return productOrder;
	}

	@Override
	public ProductOrder findBySn(String sn) {
		return productOrderDao.findBySn(sn);
	}

	@Override
	public Map<String, Object> findList(Map<String, Object> map) {
		return productOrderDao.findList(map);
	}

	@Override
	public Page<ProductOrder> findPage(OrderStatus orderStatus,
			net.shenzhou.entity.ProductOrder.PaymentStatus paymentStatus, ShippingStatus shippingStatus,
			Boolean hasExpired, Pageable pageable) {
		return productOrderDao.findPage(orderStatus, paymentStatus, shippingStatus, hasExpired, pageable);
	}

	


	
	
}
