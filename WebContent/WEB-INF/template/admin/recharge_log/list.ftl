<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("机构充值记录")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");


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
		var $dest = $("#mechanismId");
		if ($this.hasClass("checked")) {
			$dest.val("");
		} else {
			$dest.val($this.attr("val"));
		}
		$listForm.submit();
		return false;
	});

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("机构充值列表")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<input type="hidden" id = "mechanismId" name="mechanismId" [#if mechanism??] value="${mechanism.id}" [/#if] />
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="filterSelect" class="button">
						${message("机构筛选")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="filterOption" class="check">
							[#list mechanisms as mech]
								<li>
									<a href="javascript:;" val="${mech.id}" [#if mechanism??][#if mech == mechanism] class="checked"[/#if][/#if]>${mech.name}</a>
								</li>
							[/#list]
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
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="nameOrmobile" name="nameOrmobile" value="" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th>
					<a href="javascript:;" class="sort" name="type">用户</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="credit">充值金额</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="payment">备注</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="debit">充值时间</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="balance">充值机构</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="operator">充值类型</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="member">操作员</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="order">操作员手机号</a>
				</th>
			</tr>
			[#list page.content as rechargeLog]
				<tr>
					<td>
						${rechargeLog.member.name}
					</td>
					<td>
						${currency(rechargeLog.money)}
					</td>
					<td>
						${rechargeLog.remarks}
					</td>
					<td>
						${rechargeLog.createDate?string("yyyy-MM-dd HH:mm:ss")}
					</td>
					<td>
						${rechargeLog.mechanism.name}
					</td>
					<td>
						${message("Deposit.Type."+rechargeLog.type)}
					</td>
					<td>
						${rechargeLog.operator}
					</td>
					<td>
						${rechargeLog.mobile}
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