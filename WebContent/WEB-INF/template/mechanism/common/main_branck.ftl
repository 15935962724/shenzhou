<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "index"]
	<title>[#if seo.title??][@seo.title?interpret /][#else]${message("shop.index.title")}[/#if][#if systemShowPowered] - Powered By HaoKangHu[/#if]</title>
	<meta name="author" content="HaoKangHu Team" />
	<meta name="copyright" content="HaoKangHu" />
	[#if seo.keywords??]
		<meta name="keywords" content="[@seo.keywords?interpret /]" />
	[/#if]
	[#if seo.description??]
		<meta name="description" content="[@seo.description?interpret /]" />
	[/#if]
[/@seo]
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<link href="${base}/resources/shop/slider/slider.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="${base}/resources/shop/slider/slider.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $slider = $("#slider");
	var $newArticleTab = $("#newArticle .tab");
	var $promotionProductTab = $("#promotionProduct .tab");
	var $promotionProductInfo = $("#promotionProduct .info");
	var $hotProductTab = $("#hotProduct .tab");
	var $newProductTab = $("#newProduct .tab");
	var $hotProductImage = $("#hotProduct img");
	var $newProductImage = $("#newProduct img");
	
	$slider.nivoSlider({
		effect: "random",
		animSpeed: 1000,
		pauseTime: 6000,
		controlNav: true,
		keyboardNav: false,
		captionOpacity: 0.4
	});
	
	$newArticleTab.tabs("#newArticle .tabContent", {
		tabs: "li",
		event: "mouseover",
		initialIndex: 1
	});
	
	$promotionProductTab.tabs("#promotionProduct .tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	$hotProductTab.tabs("#hotProduct .tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	$newProductTab.tabs("#newProduct .tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	function promotionInfo() {
		$promotionProductInfo.each(function() {
			var $this = $(this);
			var beginDate = $this.attr("beginTimeStamp") != null ? new Date(parseFloat($this.attr("beginTimeStamp"))) : null;
			var endDate = $this.attr("endTimeStamp") != null ? new Date(parseFloat($this.attr("endTimeStamp"))) : null;
			if (beginDate == null || beginDate <= new Date()) {
				if (endDate != null && endDate >= new Date()) {
					var time = (endDate - new Date()) / 1000;
					$this.html("${message("shop.index.remain")}:<em>" + Math.floor(time / (24 * 3600)) + "<\/em> ${message("shop.index.day")} <em>" + Math.floor((time % (24 * 3600)) / 3600) + "<\/em> ${message("shop.index.hour")} <em>" + Math.floor((time % 3600) / 60) + "<\/em> ${message("shop.index.minute")}");
				} else if (endDate != null && endDate < new Date()) {
					$this.html("${message("shop.index.ended")}");
				} else {
					$this.html("${message("shop.index.going")}");
				}
			}
		});
	}
	
	promotionInfo();
	setInterval(promotionInfo, 60 * 1000);
	
	$hotProductImage.lazyload({
		threshold: 100,
		effect: "fadeIn",
		skip_invisible: false
	});
	
	$newProductImage.lazyload({
		threshold: 100,
		effect: "fadeIn",
		skip_invisible: false
	});

});
</script>
</head>
<body>
	<script type="text/javascript">
		if (self != top) {
			top.location = self.location;
		};
	</script>
	[#include "/mechanism/include/header.ftl" /]
	<table class="main">
		<tr>
			<th class="logo">
				<a href="main.jhtml">
					<img src="${base}/resources/admin/images/header_logo.gif" alt="HaoKangHu" />
				</a>
			</th>
			<th>
				<div id="nav" class="nav">
					<ul>
						
								<li>
									<a href="#product">${message("admin.main.productNav")}</a>
								</li>
						
						
								<li>
									<a href="#order">${message("admin.main.orderNav")}</a>
								</li>
															
						
								<li>
									<a href="#member">${message("admin.main.memberNav")}</a>
								</li>
							
						
								<li>
									<a href="#content">${message("admin.main.contentNav")}</a>
								</li>
							
						
								<li>
									<a href="#marketing">${message("admin.main.marketingNav")}</a>
								</li>
							
						
								<li>
									<a href="#statistics">${message("admin.main.statisticsNav")}</a>
								</li>
							
						
								<li>
									<a href="#system">${message("admin.main.systemNav")}</a>
								</li>
							
						<li>
							<a href="${base}/" target="_blank">${message("admin.main.home")}</a>
						</li>
					</ul>
				</div>
				<div class="link">
					<a href="http://www.shenzhou.net" target="_blank">${message("admin.main.official")}</a>|
					<a href="http://bbs.shenzhou.net" target="_blank">${message("admin.main.bbs")}</a>|
					<a href="http://www.shenzhou.net/about.html" target="_blank">${message("admin.main.about")}</a>
				</div>
				<div class="link">
					<strong id = "username"></strong>
					${message("admin.main.hello")}!
					<a href="../profile/edit.jhtml" target="iframe">[${message("admin.main.profile")}]</a>
					<a href="../logout.jsp" target="_top">[${message("admin.main.logout")}]</a>
				</div>
			</th>
		</tr>
		<tr>
			<td id="menu" class="menu">
				<dl id="product" class="default">
					<dt>${message("admin.main.productGroup")}</dt>
					
						<dd>
							<a href="../product/list.jhtml" target="iframe">${message("admin.main.product")}</a>
						</dd>
					
						<dd>
							<a href="../product_category/list.jhtml" target="iframe">${message("admin.main.productCategory")}</a>
						</dd>
					
						<dd>
							<a href="../parameter_group/list.jhtml" target="iframe">${message("admin.main.parameterGroup")}</a>
						</dd>
					
						<dd>
							<a href="../attribute/list.jhtml" target="iframe">${message("admin.main.attribute")}</a>
						</dd>
					
						<dd>
							<a href="../specification/list.jhtml" target="iframe">${message("admin.main.specification")}</a>
						</dd>
					
						<dd>
							<a href="../brand/list.jhtml" target="iframe">${message("admin.main.brand")}</a>
						</dd>
					
						<dd>
							<a href="../product_notify/list.jhtml" target="iframe">${message("admin.main.productNotify")}</a>
						</dd>
					
				</dl>
				<dl id="order">
					<dt>${message("admin.main.orderGroup")}</dt>
					
						<dd>
							<a href="../order/list.jhtml" target="iframe">${message("admin.main.order")}</a>
						</dd>
					
						<dd>
							<a href="../payment/list.jhtml" target="iframe">${message("admin.main.payment")}</a>
						</dd>
					
						<dd>
							<a href="../refunds/list.jhtml" target="iframe">${message("admin.main.refunds")}</a>
						</dd>
					
						<dd>
							<a href="../shipping/list.jhtml" target="iframe">${message("admin.main.shipping")}</a>
						</dd>
					
						<dd>
							<a href="../returns/list.jhtml" target="iframe">${message("admin.main.returns")}</a>
						</dd>
					
						<dd>
							<a href="../delivery_center/list.jhtml" target="iframe">${message("admin.main.deliveryCenter")}</a>
						</dd>
					
						<dd>
							<a href="../delivery_template/list.jhtml" target="iframe">${message("admin.main.deliveryTemplate")}</a>
						</dd>
					
				</dl>
				<dl id="member">
					<dt>${message("admin.main.memberGroup")}</dt>
					
						<dd>
							<a href="../member/list.jhtml" target="iframe">${message("admin.main.member")}</a>
						</dd>
					
						<dd>
							<a href="../member_rank/list.jhtml" target="iframe">${message("admin.main.memberRank")}</a>
						</dd>
					
						<dd>
							<a href="../member_attribute/list.jhtml" target="iframe">${message("admin.main.memberAttribute")}</a>
						</dd>
					
						<dd>
							<a href="../review/list.jhtml" target="iframe">${message("admin.main.review")}</a>
						</dd>
					
						<dd>
							<a href="../consultation/list.jhtml" target="iframe">${message("admin.main.consultation")}</a>
						</dd>
					
				</dl>
				<dl id="content">
					<dt>${message("admin.main.contentGroup")}</dt>
					
						<dd>
							<a href="../navigation/list.jhtml" target="iframe">${message("admin.main.navigation")}</a>
						</dd>
					
						<dd>
							<a href="../article/list.jhtml" target="iframe">${message("admin.main.article")}</a>
						</dd>
					
						<dd>
							<a href="../article_category/list.jhtml" target="iframe">${message("admin.main.articleCategory")}</a>
						</dd>
					
						<dd>
							<a href="../tag/list.jhtml" target="iframe">${message("admin.main.tag")}</a>
						</dd>
					
						<dd>
							<a href="../friend_link/list.jhtml" target="iframe">${message("admin.main.friendLink")}</a>
						</dd>
					
						<dd>
							<a href="../ad_position/list.jhtml" target="iframe">${message("admin.main.adPosition")}</a>
						</dd>
					
						<dd>
							<a href="../ad/list.jhtml" target="iframe">${message("admin.main.ad")}</a>
						</dd>
					
						<dd>
							<a href="../template/list.jhtml" target="iframe">${message("admin.main.template")}</a>
						</dd>
					
						<dd>
							<a href="../cache/clear.jhtml" target="iframe">${message("admin.main.cache")}</a>
						</dd>
					
						<dd>
							<a href="../static/build.jhtml" target="iframe">${message("admin.main.static")}</a>
						</dd>
					
						<dd>
							<a href="../index/build.jhtml" target="iframe">${message("admin.main.index")}</a>
						</dd>
					
				</dl>
				<dl id="marketing">
					<dt>${message("admin.main.marketingGroup")}</dt>
					
						<dd>
							<a href="../promotion/list.jhtml" target="iframe">${message("admin.main.promotion")}</a>
						</dd>
					
						<dd>
							<a href="../coupon/list.jhtml" target="iframe">${message("admin.main.coupon")}</a>
						</dd>
					
						<dd>
							<a href="../seo/list.jhtml" target="iframe">${message("admin.main.seo")}</a>
						</dd>
					
						<dd>
							<a href="../sitemap/build.jhtml" target="iframe">${message("admin.main.sitemap")}</a>
						</dd>
					
				</dl>
				<dl id="statistics">
					<dt>${message("admin.main.statisticsGroup")}</dt>
					
						<dd>
							<a href="../statistics/view.jhtml" target="iframe">${message("admin.main.statistics")}</a>
						</dd>
					
						<dd>
							<a href="../statistics/setting.jhtml" target="iframe">${message("admin.main.statisticsSetting")}</a>
						</dd>
					
						<dd>
							<a href="../sales/view.jhtml" target="iframe">${message("admin.main.sales")}</a>
						</dd>
					
						<dd>
							<a href="../sales_ranking/list.jhtml" target="iframe">${message("admin.main.salesRanking")}</a>
						</dd>
					
						<dd>
							<a href="../purchase_ranking/list.jhtml" target="iframe">${message("admin.main.purchaseRanking")}</a>
						</dd>
					
						<dd>
							<a href="../deposit/list.jhtml" target="iframe">${message("admin.main.deposit")}</a>
						</dd>
					
				</dl>
				<dl id="system">
					<dt>${message("admin.main.systemGroup")}</dt>
					
						<dd>
							<a href="../setting/edit.jhtml" target="iframe">${message("admin.main.setting")}</a>
						</dd>
					
						<dd>
							<a href="../area/list.jhtml" target="iframe">${message("admin.main.area")}</a>
						</dd>
					
						<dd>
							<a href="../payment_method/list.jhtml" target="iframe">${message("admin.main.paymentMethod")}</a>
						</dd>
					
						<dd>
							<a href="../shipping_method/list.jhtml" target="iframe">${message("admin.main.shippingMethod")}</a>
						</dd>
					
						<dd>
							<a href="../delivery_corp/list.jhtml" target="iframe">${message("admin.main.deliveryCorp")}</a>
						</dd>
					
						<dd>
							<a href="../payment_plugin/list.jhtml" target="iframe">${message("admin.main.paymentPlugin")}</a>
						</dd>
					
						<dd>
							<a href="../storage_plugin/list.jhtml" target="iframe">${message("admin.main.storagePlugin")}</a>
						</dd>
					
						<dd>
							<a href="../admin/list.jhtml" target="iframe">${message("admin.main.admin")}</a>
						</dd>
					
						<dd>
							<a href="../role/list.jhtml" target="iframe">${message("admin.main.role")}</a>
						</dd>
					
						<dd>
							<a href="../message/send.jhtml" target="iframe">${message("admin.main.send")}</a>
						</dd>
					
						<dd>
							<a href="../message/list.jhtml" target="iframe">${message("admin.main.message")}</a>
						</dd>
					
						<dd>
							<a href="../message/draft.jhtml" target="iframe">${message("admin.main.draft")}</a>
						</dd>
					
						<dd>
							<a href="../log/list.jhtml" target="iframe">${message("admin.main.log")}</a>
						</dd>
				</dl>
			</td>
			<td>
				<iframe id="iframe" name="iframe" src="index.jhtml" frameborder="0"></iframe>
			</td>
		</tr>
	</table>
	
</body>
</html>