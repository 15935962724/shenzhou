<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.deposit.list")} - Powered By HaoKangHu</title>
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
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("用户积分")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		[#if member??]
			<input type="hidden" name="memberId" value="${member.id}" />
		[/#if]
		<div class="bar">
			<div class="buttonWrap">
				[#if member??]
					<a href="../member/view.jhtml?id=${member.id}" class="button">${message("admin.common.back")}</a>
				[/#if]
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
			<!--
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li>
							<a href="javascript:;"[#if page.searchProperty == "operator"] class="current"[/#if] val="operator">${message("Deposit.operator")}</a>
						</li>
					</ul>
				</div>
			</div>
			-->
		</div>
		<table id="listTable" class="list">
			<tr>
				<th>
					<a href="javascript:;" class="sort" name="type">类型</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="credit">积分收入</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="debit">积分支出</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="point">当前积分</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="member">会员</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="order">订单</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="memo">备注</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">创建日期</a>
				</th>
			</tr>
			[#list page.content as point]
				<tr>
					<td>
						${message("MemberPointLog.Type." + point.type)}
					</td>
					<td>
						${currency(point.credit)}
					</td>
					<td>
						${currency(point.debit)}
					</td>
					<td>
						${currency(point.point)}
					</td>
					<td>
						[#if point.member??]
							<a href="../member/view.jhtml?id=${point.member.id}">${point.member.username}</a>
						[#else]
							-
						[/#if]
					</td>
					<td>
						[#if point.order??]
							<a href="../order/view.jhtml?id=${point.order.id}">${point.order.sn}</a>
						[#else]
							-
						[/#if]
					</td>
					<td>
						[#if point.memo??]
							<span title="${deposit.memo}">${abbreviate(point.memo, 50, "...")}</span>
						[/#if]
					</td>
					<td>
						<span title="${point.createDate?string("yyyy-MM-dd HH:mm:ss")}">${point.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
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