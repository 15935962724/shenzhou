/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.shenzhou.entity.MemberBillDetails.BillType;

/**
 * Entity - 用户账单数据实体
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public class MemberBill {
	
	public enum TimeType {  
	       /*利用构造函数传参利用构造函数传参 
	       * 通过括号赋值,而且必须有带参构造器和属性和方法，否则编译出错  
	       * 赋值必须是都赋值或都不赋值，不能一部分赋值一部分不赋值  
	       * 如果不赋值则不能写构造器，赋值编译也出错 
	       * */    
	       TODAY ("今天"), WEEK ("最近7天"), MONTH ("最近30天");  
	   
	       // 定义私有变量  
	       private String clor ;  
	   
	       // 构造函数，枚举类型只能为私有  
	       private TimeType(String clor) {  
	           this.clor = clor;  
	       }  
	         
	       public String getClor(){  
	           return this.clor;  
	       }  
	         
	       public void setClor(String clor){  
	           this.clor=clor;  
	       }  
	         
	       @Override  
	       public String toString() {  
	           return this.clor;  
	       }  
	    }  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1727449869999714421L;

	/**日期**/
	private String billDay;
	
	/**日期**/
	private String billDays;
	
	/**总转出**/
	private String totalRecharge;
	
	/**总转入**/
	private String totalAddress;
	
	/**账单详情**/
	private List<MemberBillDetails> memberBillDetails = new ArrayList<MemberBillDetails>();
	
	@JsonProperty
	public String getBillDay() {
		return billDay;
	}

	public void setBillDay(String billDay) {
		this.billDay = billDay;
	}
	@JsonProperty
	public String getBillDays() {
		return billDays;
	}

	public void setBillDays(String billDays) {
		this.billDays = billDays;
	}
	
	@JsonProperty
	public String getTotalRecharge() {
		return totalRecharge;
	}

	public void setTotalRecharge(String totalRecharge) {
		this.totalRecharge = totalRecharge;
	}
	
	@JsonProperty
	public String getTotalAddress() {
		return totalAddress;
	}

	public void setTotalAddress(String totalAddress) {
		this.totalAddress = totalAddress;
	}
	
	@JsonProperty
	public List<MemberBillDetails> getMemberBillDetails() {
		return memberBillDetails;
	}

	public void setMemberBillDetails(List<MemberBillDetails> memberBillDetails) {
		this.memberBillDetails = memberBillDetails;
	}

	public static void main(String[] args) {
		
		if(TimeType.WEEK.getClor().equals("")){
			System.out.println(TimeType.WEEK.getClor());
		}
	}
	
	
	
}