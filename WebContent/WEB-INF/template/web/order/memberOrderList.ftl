<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>订单</title>
<link rel="stylesheet" href="${base}/resources/web/light7/css/light7.min.css">
<link rel="stylesheet" href="${base}/resources/web/light7/css/light7-swiper.min.css">
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/web/font/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/light7/js/light7.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/light7/js/light7-swiper.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/tips/jquery.tips.js"></script>
<script>

//id 编号、num 总数、til id值、info 列表、s_style 选择后的样式、 o_style 初始样式
var flag = 1,key = 1;	
	
			
	//id 编号、num 总数、til id值、info 列表、s_style 选择后的样式、 o_style 初始样式
	function get_info(id,num,til,info,s_style,o_style)
		{
			flag = id;
			key = 1;
			for(var i=1; i<=num; i++)
				{
					$("#" + til + "_" + i).removeClass(s_style);
					$("#" + til + "_" + i).removeClass(o_style);
					if(id==i)
						{
							$("#" + til + "_" + i).addClass(s_style);
							
							var cardHTML =  '<div class="card-content">';
							//flag="1：全部；2：待付款；3、待康复；4、待评价；5、已完成"
							
							$.ajax({
								url: "/shenzhou/web/order/memberOrderList.jhtml",
								type: "GET",
								contentType:"application/x-www-form-urlencoded; charset=UTF-8",
								data: {pageNumber: '1',flag: flag},
								traditional: true,  
								async: false,
								success: function(data) {
								var dataObj = eval('('+data+')');
								var obj = eval('('+dataObj.data+')');
								if(obj==null||obj.order_list.length<=0){
									$.fn.tips({content:'无更多订单数据'});
									$(".infinite-scroll-preloader").css("display","none");
									return;
								}
									for(var i=0;i<obj.order_list.length;i++){		
										cardHTML += '				<div class="card-content-inner">';
										cardHTML += '					 <div class="select_list">';
										cardHTML += '						  <div class="select_list_top">';
										cardHTML += '							  <div class="select_list_topExpert"><span style="margin-left:0.833rem; font-size:1rem;color:#323232;">'+obj.order_list[i].mechanismName+'</span></div>';
										cardHTML += '							  <div class="select_list_topStatus"><span style="padding-left:1rem; font-size:1rem;color:#4c98f6;">[#if '+obj.order_list[i].orderServeState+'==await]待服务[/#if][#if '+obj.order_list[i].accomplish+'==male]已服务[/#if]</span></div>';
										cardHTML += '						  </div>';
										cardHTML += '						  <div class="select_list_moddle">';
										cardHTML += '							  <div class="select_list_moddle_left" style="position: absolute;"><img src="'+obj.order_list[i].projectLogo+'" class="select_list_moddle_leftImg"></div>';
										cardHTML += '							  <div class="select_list_moddle_center" style="position: absolute;">';
										cardHTML += '								  <div style="margin-top:0.6rem;"><span style="font-size:1.05rem;color:#323232;">'+obj.order_list[i].projectName+'</span></div>';
										cardHTML += '								  <div class="select_list_moddle_leftName" style="margin-top:0.2rem;">'+obj.order_list[i].doctorName+'&nbsp<span class="select_list_moddle_leftName">[#if '+obj.order_list[i].doctorGender+'=="male"]男[/#if][#if '+obj.order_list[i].doctorGender=="female"+']女[/#if]&nbsp</span><span class="tag1">'+obj.order_list[i].doctorCategoryName+'</span></div>';
										cardHTML += '								  <div class="select_list_moddle_leftIntruduce">项目描述:'+obj.order_list[i].doctorIntroduce+'</div>';
										cardHTML += '								  <div class="select_list_moddle_leftName">患者姓名：'+obj.order_list[i].patientMemberName+'</div>';
										cardHTML += '								  <div class="select_list_moddle_leftName">预约时间：'+obj.order_list[i].workDayItemDate+'</div>';
										cardHTML += '							  </div>';
										cardHTML += '							  <div class="select_list_moddle_right" style="position: absolute;">';
										cardHTML += '								  <div class="select_list_moddle_rightMoney">';
										cardHTML += '									  <span style="font-size:1.04rem;color:#db0101;">¥'+obj.order_list[i].projectPrice+'</span>';
										cardHTML += '									  <span style="float:right;font-size:0.833rem;color:#666666;margin-top: 0.3rem;">/'+obj.order_list[i].projectTime+'分钟</span>';
										cardHTML += '								  </div>';
										cardHTML += '								  <div class="select_list_moddle_rightTotalMoney">';
										cardHTML += '									  <span style="font-size:0.833rem;color:#666666;">合计:¥</span>';
										cardHTML += '									  <span style="font-size:1rem;color:#db0101;">'+obj.order_list[i].orderAmount+'</span>';
										cardHTML += '									  <span style="float: right;font-size:0.833rem;color:#666666;margin-top: 0.3rem;">元</span>';
										cardHTML += '								  </div>';
										cardHTML += '							  </div>';
										cardHTML += '						  </div>';
										cardHTML += '						  <div class="select_list_footer">';
										cardHTML += '							  <div class="select_list_footer_cancel"><input type ="button" style="border: 1px solid #797979;border-radius:2px;background:#ffffff; height:1.58rem;width:4.166rem; font-size:0.833rem;color:#646464" value="取消预约"></div>';
										cardHTML += '						  </div>';
										cardHTML += '					 </div>';
										cardHTML += '				</div>';
											}	
								}
							});
							
							cardHTML += '</div>';

							$('.card .card-content').html(cardHTML);
							$(".card-content").css("min-height" , window.screen.height + "px");  
						// done
							$.pullToRefreshDone('.pull-to-refresh-content');									
							
							
							
						}
					else
						{
							$("#" + til + "_" + i).addClass(o_style);
						}
				}
			$(".infinite-scroll").scrollTop(0);
		}
	
$(function () {		

		$(document).on('refresh', '.pull-to-refresh-content',function(e) {
		setTimeout(function() {
			var cardHTML =  '<div class="card-content">';
			$.ajax({
								url: "/shenzhou/web/order/memberOrderList.jhtml",
								type: "GET",
								contentType:"application/x-www-form-urlencoded; charset=UTF-8",
								data: {pageNumber: '1',flag: flag},
								traditional: true,  
								async: false,
								success: function(data) {
								var dataObj = eval('('+data+')');
								var obj = eval('('+dataObj.data+')');
								if(obj==null||obj.order_list.length<=0){
									$.fn.tips({content:'无更多订单数据'});
									$(".infinite-scroll-preloader").css("display","none");
									return;
								}
									for(var i=0;i<obj.order_list.length;i++){		
										cardHTML += '				<div class="card-content-inner">';
										cardHTML += '					 <div class="select_list">';
										cardHTML += '						  <div class="select_list_top">';
										cardHTML += '							  <div class="select_list_topExpert"><span style="margin-left:0.833rem; font-size:1rem;color:#323232;">'+obj.order_list[i].mechanismName+'</span></div>';
										cardHTML += '							  <div class="select_list_topStatus"><span style="padding-left:1rem; font-size:1rem;color:#4c98f6;">[#if '+obj.order_list[i].orderServeState+'==await]待服务[/#if][#if '+obj.order_list[i].accomplish+'==male]已服务[/#if]</span></div>';
										cardHTML += '						  </div>';
										cardHTML += '						  <div class="select_list_moddle">';
										cardHTML += '							  <div class="select_list_moddle_left" style="position: absolute;"><img src="'+obj.order_list[i].projectLogo+'" class="select_list_moddle_leftImg"></div>';
										cardHTML += '							  <div class="select_list_moddle_center" style="position: absolute;">';
										cardHTML += '								  <div style="margin-top:0.6rem;"><span style="font-size:1.05rem;color:#323232;">'+obj.order_list[i].projectName+'</span></div>';
										cardHTML += '								  <div class="select_list_moddle_leftName" style="margin-top:0.2rem;">'+obj.order_list[i].doctorName+'&nbsp<span class="select_list_moddle_leftName">[#if '+obj.order_list[i].doctorGender+'=="male"]男[/#if][#if '+obj.order_list[i].doctorGender=="female"+']女[/#if]&nbsp</span><span class="tag1">'+obj.order_list[i].doctorCategoryName+'</span></div>';
										cardHTML += '								  <div class="select_list_moddle_leftIntruduce">项目描述:'+obj.order_list[i].doctorIntroduce+'</div>';
										cardHTML += '								  <div class="select_list_moddle_leftName">患者姓名：'+obj.order_list[i].patientMemberName+'</div>';
										cardHTML += '								  <div class="select_list_moddle_leftName">预约时间：'+obj.order_list[i].workDayItemDate+'</div>';
										cardHTML += '							  </div>';
										cardHTML += '							  <div class="select_list_moddle_right" style="position: absolute;">';
										cardHTML += '								  <div class="select_list_moddle_rightMoney">';
										cardHTML += '									  <span style="font-size:1.04rem;color:#db0101;">¥'+obj.order_list[i].projectPrice+'</span>';
										cardHTML += '									  <span style="float:right;font-size:0.833rem;color:#666666;margin-top: 0.3rem;">/'+obj.order_list[i].projectTime+'分钟</span>';
										cardHTML += '								  </div>';
										cardHTML += '								  <div class="select_list_moddle_rightTotalMoney">';
										cardHTML += '									  <span style="font-size:0.833rem;color:#666666;">合计:¥</span>';
										cardHTML += '									  <span style="font-size:1rem;color:#db0101;">'+obj.order_list[i].orderAmount+'</span>';
										cardHTML += '									  <span style="float: right;font-size:0.833rem;color:#666666;margin-top: 0.3rem;">元</span>';
										cardHTML += '								  </div>';
										cardHTML += '							  </div>';
										cardHTML += '						  </div>';
										cardHTML += '						  <div class="select_list_footer">';
										cardHTML += '							  <div class="select_list_footer_cancel"><input type ="button" style="border: 1px solid #797979;border-radius:2px;background:#ffffff; height:1.58rem;width:4.166rem; font-size:0.833rem;color:#646464" value="取消预约"></div>';
										cardHTML += '						  </div>';
										cardHTML += '					 </div>';
										cardHTML += '				</div>';
											}	
								}
							});
							
			cardHTML += '</div>';

			$(e.target).find('.card .card-content').replaceWith(cardHTML);
			$(".card-content").css("min-height" , window.screen.height + "px");  
		// done
			$.pullToRefreshDone('.pull-to-refresh-content');
		}, 2000);
	});
	
	
	
	$(document).on("pageInit", "#page-infinite-scroll", function(e, id, page) {
		function addItems(number, lastIndex) {
			key += 1;
			var cardHTML = '';
			$.ajax({
								url: "/shenzhou/web/order/memberOrderList.jhtml",
								type: "GET",
								contentType:"application/x-www-form-urlencoded; charset=UTF-8",
								data: {pageNumber: key,flag: flag},
								traditional: true,  
								async: false,
								success: function(data) {
								var dataObj = eval('('+data+')');
								var obj = eval('('+dataObj.data+')');
								if(obj==null||obj.order_list.length<=0){
									$.fn.tips({content:'无更多订单数据'});
									$(".infinite-scroll-preloader").css("display","none");
									return;
								}
									for(var i=0;i<obj.order_list.length;i++){		
										cardHTML += '				<div class="card-content-inner">';
										cardHTML += '					 <div class="select_list">';
										cardHTML += '						  <div class="select_list_top">';
										cardHTML += '							  <div class="select_list_topExpert"><span style="margin-left:0.833rem; font-size:1rem;color:#323232;">'+obj.order_list[i].mechanismName+'</span></div>';
										cardHTML += '							  <div class="select_list_topStatus"><span style="padding-left:1rem; font-size:1rem;color:#4c98f6;">[#if '+obj.order_list[i].orderServeState+'==await]待服务[/#if][#if '+obj.order_list[i].accomplish+'==male]已服务[/#if]</span></div>';
										cardHTML += '						  </div>';
										cardHTML += '						  <div class="select_list_moddle">';
										cardHTML += '							  <div class="select_list_moddle_left" style="position: absolute;"><img src="'+obj.order_list[i].projectLogo+'" class="select_list_moddle_leftImg"></div>';
										cardHTML += '							  <div class="select_list_moddle_center" style="position: absolute;">';
										cardHTML += '								  <div style="margin-top:0.6rem;"><span style="font-size:1.05rem;color:#323232;">'+obj.order_list[i].projectName+'</span></div>';
										cardHTML += '								  <div class="select_list_moddle_leftName" style="margin-top:0.2rem;">'+obj.order_list[i].doctorName+'&nbsp<span class="select_list_moddle_leftName">[#if '+obj.order_list[i].doctorGender+'=="male"]男[/#if][#if '+obj.order_list[i].doctorGender=="female"+']女[/#if]&nbsp</span><span class="tag1">'+obj.order_list[i].doctorCategoryName+'</span></div>';
										cardHTML += '								  <div class="select_list_moddle_leftIntruduce">项目描述:'+obj.order_list[i].doctorIntroduce+'</div>';
										cardHTML += '								  <div class="select_list_moddle_leftName">患者姓名：'+obj.order_list[i].patientMemberName+'</div>';
										cardHTML += '								  <div class="select_list_moddle_leftName">预约时间：'+obj.order_list[i].workDayItemDate+'</div>';
										cardHTML += '							  </div>';
										cardHTML += '							  <div class="select_list_moddle_right" style="position: absolute;">';
										cardHTML += '								  <div class="select_list_moddle_rightMoney">';
										cardHTML += '									  <span style="font-size:1.04rem;color:#db0101;">¥'+obj.order_list[i].projectPrice+'</span>';
										cardHTML += '									  <span style="float:right;font-size:0.833rem;color:#666666;margin-top: 0.3rem;">/'+obj.order_list[i].projectTime+'分钟</span>';
										cardHTML += '								  </div>';
										cardHTML += '								  <div class="select_list_moddle_rightTotalMoney">';
										cardHTML += '									  <span style="font-size:0.833rem;color:#666666;">合计:¥</span>';
										cardHTML += '									  <span style="font-size:1rem;color:#db0101;">'+obj.order_list[i].orderAmount+'</span>';
										cardHTML += '									  <span style="float: right;font-size:0.833rem;color:#666666;margin-top: 0.3rem;">元</span>';
										cardHTML += '								  </div>';
										cardHTML += '							  </div>';
										cardHTML += '						  </div>';
										cardHTML += '						  <div class="select_list_footer">';
										cardHTML += '							  <div class="select_list_footer_cancel"><input type ="button" style="border: 1px solid #797979;border-radius:2px;background:#ffffff; height:1.58rem;width:4.166rem; font-size:0.833rem;color:#646464" value="取消预约"></div>';
										cardHTML += '						  </div>';
										cardHTML += '					 </div>';
										cardHTML += '				</div>';
											}	
								}
							});
							
			$('.card .card-content').append(cardHTML);
		}
		var loading = false;
		$(page).on('infinite', function() {
			if (loading) return;
			loading = true;
			setTimeout(function() {
				loading = false;
				addItems();
			}, 1000);
		});
	});	
	get_info(1,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');
	$.init();
	
	$(".card-content").css("min-height" , window.screen.height + "px");  
	
});
	
</script>
<style>
	.pull-to-refresh-layer{margin-top:3.97rem;}	
	.bar{padding-right: 0px;padding-left: 0px;border-bottom:none;}
	.page{background: #f0f1f0;}
	.card{border-radius: 0; box-shadow: #ffffff 0px 0rem 0rem;margin: 0px;}
	.card-content-inner{border-bottom: 2px solid #f9f9f9;padding: 0rem;} 
	</style>
</head>

<body style="background:#f0f1f0">

<!-- page 容器 -->
<div class="page" id="page-infinite-scroll">
	<!-- 标题栏 -->
	<header class="bar bar-nav bgffffff" id="header" style="height: 6.36rem;">
		<table cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
			<tr>
				<td align="center" class="h316">
					订单
				</td>
			</tr>
		</table>

		<table cellpadding="0" cellspacing="0" border="0" width="100%" class="bgf9f9f9">
			<tr>
				<td width="20%" align="center" class="h316 z_106_4c98f6_1" id="til_1"><a href="javascript:;" onClick="get_info(1,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">全部</a></td>
				<td width="20%" align="center" class="z_106_bdbcbc_1" id="til_2"><a href="javascript:;" onClick="get_info(2,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">待付款</a></td>
				<td width="20%" align="center" class="z_106_bdbcbc_1" id="til_3"><a href="javascript:;" onClick="get_info(3,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">待康复</a></td>
				<td width="20%" align="center" class="z_106_bdbcbc_1" id="til_4"><a href="javascript:;" onClick="get_info(4,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">待评价</a></td>
				<td width="20%" align="center" class="z_106_bdbcbc_1" id="til_5"><a href="javascript:;" onClick="get_info(5,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">已完成</a></td>
			</tr>
		</table>

	</header>
	<!-- 这里是页面内容区 -->
	<div class="content pull-to-refresh-content infinite-scroll" data-ptr-distance="55" data-distance="100">
		<div class="pull-to-refresh-layer">
			<div class="preloader"></div>
			<div class="pull-to-refresh-arrow"></div>
		</div>
		<div class="card">
			<div class="card-content">
			</div>
			<div class="infinite-scroll-preloader">
				<div class="preloader"></div>
			</div>
		</div>
	</div>	
</div>


</body>
</html>
