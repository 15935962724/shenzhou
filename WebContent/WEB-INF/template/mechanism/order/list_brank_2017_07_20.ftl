[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.order.list")} - Powered By 好康护</title>
<meta name="author" content="好康护 Team" />
<meta name="copyright" content="好康护" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>

<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");
	var $print = $("select[name='print']");

	var $submitButton = $("#submitButton");


	[@flash_message /]
	
	// 订单筛选
	$filterSelect.mouseover(function() {
		var $this = $(this);
		var offset = $this.offset();
		var $menuWrap = $this.closest("div.menuWrap");
		var $popupMenu = $menuWrap.children("div.popupMenu");
		$popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
		$menuWrap.mouseleave(function() {
			$popupMenu.hide();
		});
	});
	
	// 筛选选项
	$filterOption.click(function() {
		var $this = $(this);
		var $dest = $("#" + $this.attr("name"));
		if ($this.hasClass("checked")) {
			$dest.val("");
		} else {
			$dest.val($this.attr("val"));
		}
		$listForm.submit();
		return false;
	});
	
	// 搜索
	$submitButton.click(function() {
		$listForm.submit();
		return false;
	});
	
	// 打印选择
	$print.change(function() {
		var $this = $(this);
		if ($this.val() != "") {
			window.open($this.val());
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.order.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<input type="hidden" id="orderStatus" name="orderStatus" value="${orderStatus}" />
		<input type="hidden" id="paymentStatus" name="paymentStatus" value="${paymentStatus}" />
		<input type="hidden" id="shippingStatus" name="shippingStatus" value="${shippingStatus}" />
		<input type="hidden" id="hasExpired" name="hasExpired" value="[#if hasExpired??]${hasExpired?string("true", "false")}[/#if]" />
		<table class="input tabContent">
			<tr>
				<th>
					订单编号:
				</th>
				<td>
					<input type="text" name="sn" class="text" value="${sn}" maxlength="20" />
				</td>
				<th>
					服务项目:
				</th>
				<td>
					<input type="text" name="projectName" class="text" value="${projectName}" maxlength="20" />
				</td>
				<th>
					服务医师:
				</th>
				<td>
					<input type="text" name="doctorName" class="text" value="${doctorName}" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>
					下单用户:
				</th>
				<td>
					<input type="text" name="memberName" class="text" value="${memberName}" maxlength="20" />
				</td>
				<th>
					患者姓名:
				</th>
				<td>
					<input type="text" name="patientName" class="text" value="${patientName}" maxlength="20" />
				</td>
				<th>
					联系电话:
				</th>
				<td>
					<input type="text" name="phone" class="text" value="${phone}" maxlength="20" />
				</td>
			</tr>
			<tr>
			<th>
				下单时间:
			</th>
		      <td>
		          <input type="text" id="startDate" name="startDate"  [#if startDate??] value="${startDate?string("yyyy-MM-dd hh:mm:ss")}" [/#if]   class="Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd 00:00:00', maxDate: '#F{$dp.$D(\'endDate\')}'});" />
			       - 
			       <input type="text" id="endDate" name="endDate" [#if endDate??] value="${endDate?string("yyyy-MM-dd hh:mm:ss")}" [/#if]   class="Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd 23:59:59', minDate: '#F{$dp.$D(\'startDate\')}'});" />
		       </td>
			</tr>
		</table>
		
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="filterSelect" class="button">
						${message("admin.order.filter")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="filterOption" class="check">
							<li>
								<a href="javascript:;" name="orderStatus" val="unconfirmed"[#if orderStatus == "unconfirmed"] class="checked"[/#if]>${message("Order.OrderStatus.unconfirmed")}</a>
							</li>
							<li>
								<a href="javascript:;" name="orderStatus" val="confirmed"[#if orderStatus == "confirmed"] class="checked"[/#if]>${message("Order.OrderStatus.confirmed")}</a>
							</li>
							<li>
								<a href="javascript:;" name="orderStatus" val="completed"[#if orderStatus == "completed"] class="checked"[/#if]>${message("Order.OrderStatus.completed")}</a>
							</li>
							<li>
								<a href="javascript:;" name="orderStatus" val="cancelled"[#if orderStatus == "cancelled"] class="checked"[/#if]>${message("Order.OrderStatus.cancelled")}</a>
							</li>
							<li class="separator">
								<a href="javascript:;" name="paymentStatus" val="unpaid"[#if paymentStatus == "unpaid"] class="checked"[/#if]>${message("Order.PaymentStatus.unpaid")}</a>
							</li>
							<li>
								<a href="javascript:;" name="paymentStatus" val="partialPayment"[#if paymentStatus == "partialPayment"] class="checked"[/#if]>${message("Order.PaymentStatus.partialPayment")}</a>
							</li>
							<li>
								<a href="javascript:;" name="paymentStatus" val="paid"[#if paymentStatus == "paid"] class="checked"[/#if]>${message("Order.PaymentStatus.paid")}</a>
							</li>
							<li>
								<a href="javascript:;" name="paymentStatus" val="partialRefunds"[#if paymentStatus == "partialRefunds"] class="checked"[/#if]>${message("Order.PaymentStatus.partialRefunds")}</a>
							</li>
							<li>
								<a href="javascript:;" name="paymentStatus" val="refunded"[#if paymentStatus == "refunded"] class="checked"[/#if]>${message("Order.PaymentStatus.refunded")}</a>
							</li>
							<li class="separator">
								<a href="javascript:;" name="shippingStatus" val="unshipped"[#if shippingStatus == "unshipped"] class="checked"[/#if]>${message("Order.ShippingStatus.unshipped")}</a>
							</li>
							<li>
								<a href="javascript:;" name="shippingStatus" val="partialShipment"[#if shippingStatus == "partialShipment"] class="checked"[/#if]>${message("Order.ShippingStatus.partialShipment")}</a>
							</li>
							<li>
								<a href="javascript:;" name="shippingStatus" val="shipped"[#if shippingStatus == "shipped"] class="checked"[/#if]>${message("Order.ShippingStatus.shipped")}</a>
							</li>
							<li>
								<a href="javascript:;" name="shippingStatus" val="partialReturns"[#if shippingStatus == "partialReturns"] class="checked"[/#if]>${message("Order.ShippingStatus.partialReturns")}</a>
							</li>
							<li>
								<a href="javascript:;" name="shippingStatus" val="returned"[#if shippingStatus == "returned"] class="checked"[/#if]>${message("Order.ShippingStatus.returned")}</a>
							</li>
							<li class="separator">
								<a href="javascript:;" name="hasExpired" val="true"[#if hasExpired?? && hasExpired] class="checked"[/#if]>${message("admin.order.hasExpired")}</a>
							</li>
							<li>
								<a href="javascript:;" name="hasExpired" val="false"[#if hasExpired?? && !hasExpired] class="checked"[/#if]>${message("admin.order.unexpired")}</a>
							</li>
						</ul>
					</div>
				</div>
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
			<div class="menuWrap">
					<a href="javascript:;" id="submitButton" class="iconButton">
					搜索
					</a>
			</div>
		</div>
		
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">序号</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">${message("Order.sn")}</a>
				</th>
				<th>
					服务项目
				</th>
				<th>
					服务医师
				</th>
				<th>
					订单金额
				</th>
				
				<th>
					下单用户
				</th>
				<th>
					患者姓名
				</th>
				<th>
					联系电话
				</th>
				<th>
					<a href="javascript:;" class="sort" name="orderStatus">${message("Order.orderStatus")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="paymentStatus">${message("Order.paymentStatus")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
				</th>
				[@shiro.hasPermission name = "admin:print"]
					<th>
						<span>${message("admin.order.print")}</span>
					</th>
				[/@shiro.hasPermission]
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as order]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${order.id}" />
					</td>
					<td>
						 ${order_index+1}  
					</td>
					<td>
						${order.sn}
					</td>
					<td>
						${order.project.name}
					</td>
					<td>
						${order.doctor.name}
					</td>
					<td>
						${currency(order.amount, true)}
					</td>
					<td>
						${order.consignee}
					</td>
					<td>
						${order.patientMember.name}
					</td>
					<td>
						${order.phone}
					</td>
					<td>
						${message("Order.OrderStatus." + order.orderStatus)}
						[#if order.expired]<span class="gray">(${message("admin.order.hasExpired")})</span>[/#if]
					</td>
					<td>
						${message("Order.PaymentStatus." + order.paymentStatus)}
					</td>
					<td>
						<span title="${order.createDate?string("yyyy-MM-dd HH:mm:ss")}">${order.createDate}</span>
					</td>
					[@shiro.hasPermission name = "admin:print"]
						<td>
							<select name="print">
								<option value="">${message("admin.common.choose")}</option>
								<option value="../print/order.jhtml?id=${order.id}">${message("admin.order.orderPrint")}</option>
								<option value="../print/product.jhtml?id=${order.id}">${message("admin.order.productPrint")}</option>
								<option value="../print/shipping.jhtml?id=${order.id}">${message("admin.order.shippingPrint")}</option>
								<option value="../print/delivery.jhtml?orderId=${order.id}">${message("admin.order.deliveryPrint")}</option>
							</select>
						</td>
					[/@shiro.hasPermission]
					<td>
						<a href="view.jhtml?id=${order.id}">[${message("admin.common.view")}]</a>
						[#if !order.expired && order.orderStatus == "unconfirmed"]
							<a href="edit.jhtml?id=${order.id}">[${message("admin.common.edit")}]</a>
						[#else]
							<span title="${message("admin.order.editNotAllowed")}">[${message("admin.common.edit")}]</span>
						[/#if]
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