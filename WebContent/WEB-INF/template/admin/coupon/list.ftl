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
	
	//启用or停用
	$(".stop").click(function(){
		var id = $(this).attr("data_id");
		$.ajax({ 
				url: "updateIsEnabled.jhtml",
				type: "POST",
				data: {
					"id":id
				},
				datatype:"text",
				cache: false,
				success: function(message){
				window.location.reload();
		    	},
		    	error:function(message){
		    	alert(message);
		    	}
		    });
	});


});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.coupon.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<a href="add.jhtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
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
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li>
							<a href="javascript:;"[#if page.searchProperty == "name"] class="current"[/#if] val="name">${message("Coupon.name")}</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					优惠券类型
				</th>
				<th>
					<a href="javascript:;" class="sort" name="name">${message("Coupon.name")}</a>
				</th>
				<th>
					有效期限
				</th>
				<th>
					<a href="javascript:;" class="sort" name="mechanism">发券机构</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="isEnabled">${message("Coupon.isEnabled")}</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as coupon]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${coupon.id}" />
					</td>
					<td>
						${message("Coupon.CouponType."+coupon.couponType)}
					</td>
					<td>
						${coupon.name}
					</td>
					<td>
					[#if coupon.couponType == "collarcoupon" ]
					自领券之日起,有效期为${coupon.effectiveDay}天
					[#else]
					<span title="${coupon.beginDate?string("yyyy-MM-dd HH:mm:ss")}">${coupon.beginDate}</span>
					- 
					<span title="${coupon.endDate?string("yyyy-MM-dd HH:mm:ss")}">${coupon.endDate}</span>
					[/#if]
					</td>
					<td>
						[#if coupon.mechanism??]
							<span >${coupon.mechanism.name}</span>
						[#else]
							<span >平台发放</span>
						[/#if]
						
					</td>
					<td>
						<span class="${coupon.isEnabled?string("true", "false")}Icon">&nbsp;</span>
					</td>
					<td>
					<!--
						<a href="build.jhtml?id=${coupon.id}">[${message("admin.coupon.build")}]</a>
						<a href="edit.jhtml?id=${coupon.id}">[${message("admin.common.edit")}]</a>
					-->
						<a href="view.jhtml?couponId=${coupon.id}">[领用明细]</a>
						<a href="javascript:;" data_id="${coupon.id}" class="stop">[${message(coupon.isEnabled?string("停用","启用"))}]</a>
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