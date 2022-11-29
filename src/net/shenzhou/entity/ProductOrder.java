package net.shenzhou.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "xx_product_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_product_order_sequence")
public class ProductOrder extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9108452571841930261L;

	/** 订单名称分隔符 */
	private static final String NAME_SEPARATOR = " ";

	/**
	 * 
	 * @author 评价
	 *
	 */
	public enum Evaluate{
		
		/** 未评价 */
		not,
		
		/** 已评价 */
		already
		
	}
	
	/**
	 * 订单状态
	 */
	public enum OrderStatus {

		/** 未确认 */
		unconfirmed,

		/** 已确认 */
		confirmed,

		/** 已完成 */
		completed,

		/** 已取消 */
		cancelled,
		
		/** 待入档*/
		record
	}

	/**
	 * 支付状态
	 */
	public enum PaymentStatus {

		/** 未支付 */
		unpaid,

		/** 部分支付 */
		partialPayment,

		/** 已支付 */
		paid,

		/** 部分退款 */
		partialRefunds,

		/** 已退款 */
		refunded
	}

	
	/**
	 * 配送状态
	 */
	public enum ShippingStatus {

		/** 未发货 */
		unshipped,

		/** 部分发货 */
		partialShipment,

		/** 已发货 */
		shipped,

		/** 部分退货 */
		partialReturns,

		/** 已退货 */
		returned
	}



	
	/**
	 * 
	 * @author 支付类型
	 *
	 */
	public enum PaymentType{
		
		/** 健康金 */
		healthGold,
		
		/** 钱 */
		money,
		/**健康金加钱 */
		healthyMoney,
		
	}
	
	/** 订单编号 */
	private String sn;

	/** 评价状态 */
	private Evaluate evaluate;
	
	/** 评论 */
	private net.shenzhou.entity.Evaluate evaluateOrder;
	
	/** 订单状态 */
	private OrderStatus orderStatus;

	/** 支付状态 */
	private PaymentStatus paymentStatus;
	
	/** 支付类型 */
	private PaymentType paymentType;

	/** 配送状态 */
	private ShippingStatus shippingStatus;

	/** 支付手续费 */
	private BigDecimal fee;

	/** 运费 */
	private BigDecimal freight;
	
	
	/** 花费健康金*/
	private BigDecimal spendPoint;

	/** 花费金额*/
	private BigDecimal spendPaid;
	
	/** 兑换数量 */
	private Integer number;
	
	/** 收货人(下单用户) */
	private String consignee;
	
	/** 地区名称 */
	private String areaName;

	/** 地址 */
	private String address;

	/** 邮编 */
	private String zipCode;

	/** 电话 */
	private String phone;
	
	/** 收货时间 */
	private Date collectGoodsDate;
	
	/** 商品 */
	private Product product;
	/** 用户 */
	private Member member;
	
	/**用户积分日志**/
	private List<MemberPointLog> memberPointLogs = new ArrayList<MemberPointLog>();
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Evaluate getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(Evaluate evaluate) {
		this.evaluate = evaluate;
	}

	public net.shenzhou.entity.Evaluate getEvaluateOrder() {
		return evaluateOrder;
	}

	public void setEvaluateOrder(net.shenzhou.entity.Evaluate evaluateOrder) {
		this.evaluateOrder = evaluateOrder;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getSpendPoint() {
		return spendPoint;
	}

	public void setSpendPoint(BigDecimal spendPoint) {
		this.spendPoint = spendPoint;
	}

	public BigDecimal getSpendPaid() {
		return spendPaid;
	}

	public void setSpendPaid(BigDecimal spendPaid) {
		this.spendPaid = spendPaid;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getCollectGoodsDate() {
		return collectGoodsDate;
	}

	public void setCollectGoodsDate(Date collectGoodsDate) {
		this.collectGoodsDate = collectGoodsDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	@OneToMany(mappedBy = "productOrder", fetch = FetchType.LAZY)
	public List<MemberPointLog> getMemberPointLogs() {
		return memberPointLogs;
	}

	public void setMemberPointLogs(List<MemberPointLog> memberPointLogs) {
		this.memberPointLogs = memberPointLogs;
	}

	
	
	
	
}
