<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script>
$(function(){

	var $listForm = $('#listForm');
	var $isUsed = $('#isUsed');
	
	$isUsed.bind("change",function(){
		$listForm.submit();
	});
	
	
});
</script>
</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
			<form id = "listForm" action = "view.jhtml" method="get">
			<input type ="hidden"  name ="couponId" value ="${coupon.id}" >
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">优惠卷管理</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="list.jhtml">返回</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2" height="20" class="bgf5fafe plr10 ptb10">
							活动名称：${coupon.name}<br>
							发放时间：${coupon.createDate?string("yyyy-MM-dd HH:mm:ss")}<br>
							有效区间：[#if coupon.couponType == "collarcoupon" ]自领券之日起,有效期为${coupon.effectiveDay}天[#else]${coupon.beginDate?string("yyyy-MM-dd")} - ${coupon.endDate?string("yyyy-MM-dd")}[/#if]<br>
							共发放优惠卷：${coupon.couponCodes.size()}个，已使用${usedCount}个
						</td>
					</tr>
					<tr>
						<td colspan="2" height="60" align="right">
							<select id ="isUsed" name="isUsed" class="z14323232 h30 w100">
								<option value="">全部</option>
								<option value="false">未使用</option>
								<option value="true">已使用</option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="180" height="50">领用账号</td>
									<td class="btle3e3e3" align="center" width="70">状态</td>
									<td class="btle3e3e3" align="center">购买服务</td>
									<td class="btle3e3e3" align="center" width="100">使用时间</td>
									<td class="btle3e3e3" align="center" width="100">服务总价</td>
									<td class="btle3e3e3" align="center" width="100">订单金额</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="100">优惠金额</td>
								</tr>
								[#list page.content as couponCode]
								<tr [#if couponCode_index%2==0] class="bge7f4ff" [/#if] >
									<td class="btle3e3e3 plr10" align="left " height="50">
										${couponCode.member.name}
										<span class="z12999999">
										${couponCode.member.mobile}<br>
										[#if couponCode.isUsed]
											${couponCode.order.patientMember.name} ${message("Member.Gender."+couponCode.order.patientMember.gender)} ${age(couponCode.order.patientMember.birth)}周岁
										[#else]
										-
										[/#if]
										</span>
									</td>
									<td class="btle3e3e3 plr10">${couponCode.isUsed?string("已", "未")}使用</td>
									<td class="btle3e3e3 plr10" align="left">
										[#if couponCode.isUsed]
											[#list couponCode.order.orderItems as orderItem]
													${orderItem.name}	
													[#break]												
											[/#list]
										<span class="z12999999">
											[${couponCode.order.sn}]<br>
											${couponCode.order.doctor.name} ${couponCode.order.doctor.mobile}
										</span>
										[#else]
										-
										[/#if]
										
									</td>
									<td class="btle3e3e3 plr10" align="center">
										[#if couponCode.usedDate??]
											${couponCode.usedDate?string("yyyy-MM-dd")}<br>
										<span class="z12999999">
											${couponCode.usedDate?string("HH:mm:ss")}
										</span>
										[#else]
										-
										[/#if]
									</td>
									<td class="btle3e3e3" align="center">[#if couponCode.order??] ${couponCode.order.getPrice()} [#else]0.00[/#if]</td>
									<td class="btle3e3e3" align="center">[#if couponCode.order??] ${couponCode.order.getAmount()} [#else]0.00[/#if]</td>
									<td class="btle3e3e3 bre3e3e3" align="center">[#if couponCode.order??] ${couponCode.order.couponDiscount} [#else]0.00[/#if]</td>
								</tr>
								[/#list]
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="bte3e3e3" height="10"></td>
					</tr>
					<tr>
						[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
							[#include "/mechanism/include/pagination.ftl"]
						[/@pagination]
					</tr>
					<tr>
						<td colspan="2"></td>
					</tr>
				</table>
				</form>
			</td>
		</tr>
	</table>
</div>
</body>
</html>