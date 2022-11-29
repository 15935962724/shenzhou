<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.coupon.list")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.coupon.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="view.jhtml" method="get">
	<input type = "hidden" name = "couponId" value = "${coupon.id}" />
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="pageSizeSelect" class="button">
						${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="pageSizeOption">
							<li>
								<a href="javascript:;"[#if page.pageSize == 10] class="current"[/#if] val="10">10</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 20] class="current"[/#if] val="20">20</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 50] class="current"[/#if] val="50">50</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 100] class="current"[/#if] val="100">100</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th>
					领用账号
				</th>
				<th>
					使用状态
				</th>
				<th>
					购买服务
				</th>
				<th>
					使用时间
				</th>
				<th>
					服务总价
				</th>
				<th>
					订单金额
				</th>
				<th>
					优惠金额
				</th>
			</tr>
			[#list page.content as couponCode]
				<tr>
					<td>
						${couponCode.member.name}
						<span class="z12999999">
						${couponCode.member.mobile}<br>
						[#if couponCode.isUsed]
							${couponCode.order.patientMember.name} ${message("Member.Gender."+couponCode.order.patientMember.gender)} ${age(couponCode.order.patientMember.birth)}周岁
						[/#if]
						</span>
					</td>
					<td>
						${couponCode.isUsed?string("已", "未")}使用
					</td>
					<td>
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
					<td>
						[#if couponCode.usedDate??]
							${couponCode.usedDate?string("yyyy-MM-dd")}<br>
						<span class="z12999999">
							${couponCode.usedDate?string("HH:mm:ss")}
						</span>
						[#else]
						-
						[/#if]
					</td>
					<td>
						[#if couponCode.order??] ${couponCode.order.getPrice()} [#else]0.00[/#if]
					</td>
					<td>
						[#if couponCode.order??] ${couponCode.order.getAmount()} [#else]0.00[/#if]
					</td>
					<td>
						[#if couponCode.order??] ${couponCode.order.couponDiscount} [#else]0.00[/#if]
					</td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>