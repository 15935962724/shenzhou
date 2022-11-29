<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>好康护 - 让幸福简单起来</title>
<link rel="stylesheet" href="${base}/resources/web/light7/css/light7.min.css">
<link rel="stylesheet" href="${base}/resources/web/light7/css/light7-swiper.min.css">
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/web/font/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/light7/js/light7.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/light7/js/light7-swiper.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/web/tips/jquery.tips.js"></script>
<script>

	//id 编号、num 总数、til id值、info 列表、s_style 选择后的样式、 o_style 初始样式
	var flag = 1,key = 1;	
	
	function filtrateDoctor(){
		
		
		var price_min = $("#price_min").val();
		var price_max = $("#price_max").val();
		var serverProjectCategoryId = $("#serverProjectCategoryId").val();
		var doctorName = $("#doctorName").val();
		var ReserveDate = $("#ReserveDate").val();
		var StartDate = $("#StartDate").val();
		var EndDate = $("#EndDate").val();
		var service = $("#service").val();
		var sex = $("#sex").val();
		
		$.ajax({
		url: "/shenzhou/web/doctor/filtrateDoctor.jhtml",
		type: "GET",
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		data: {price_min: price_min, price_max: price_max, serverProjectCategoryId: serverProjectCategoryId, doctorName: doctorName,ReserveDate: ReserveDate,pageNumber: key,StartDate: StartDate,EndDate: EndDate,service :service,sex :sex},
		traditional: true,  
		async: false,
		success: function(data) {
		$("#layer").css("display","none");
		alert(data);
		var dataObj = eval('('+data+')');
		alert(dataObj);
		if(dataObj==null||dataObj.length<=0){
			$.fn.tips({content:'无更多医生数据'});
			$(".infinite-scroll-preloader").css("display","none");
			return;
		}
				var cardHTML =  '<div class="card-content">';
				for(var i=0;i<dataObj.length;i++){		
					cardHTML += '				<div class="card-content-inner">';
					cardHTML += '					<a href="${base}/web/doctor/doctorDetails.jhtml?doctorId='+dataObj[i].doctorId+'">';
					cardHTML += '					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto">';
					cardHTML += '						<tr>';
					cardHTML += '							<td valign="top" class="w6 pr"><img src="'+dataObj[i].doctorLogo+'" class="imgw120 br5"><img src="images/sm.png" class="imgw19"></td>';
					cardHTML += '							<td valign="top">';
					cardHTML += '								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1 tlf">';
					cardHTML += '									<tr>';
					cardHTML += '										<td class="z_1_323232_1 tdwb">';
					cardHTML += '											'+dataObj[i].doctorName+' <span class="z_0782_666666_1">'+(dataObj[i].sex=="male"?"男":"女")+'</span> <span class="tag1">'+dataObj[i].doctorCategory+'</span>';
					cardHTML += '										</td>';
					cardHTML += '									</tr>';
					cardHTML += '									<tr>';
					cardHTML += '										<td>';
					cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
					cardHTML += '												<tr>';
					cardHTML += '													<td valign="top">';
					cardHTML += '														<table cellpadding="0" cellspacing="0" border="0" width="95%" class="tla">';
					cardHTML += '															<tr>';
					cardHTML += '																<td class="lh085">'+dataObj[i].mechanismName+'</td>';
					cardHTML += '															</tr>';
					cardHTML += '															<tr>';
					cardHTML += '																<td class="lh085">'+dataObj[i].introduce+'</td>';
					cardHTML += '															</tr>';
					cardHTML += '														</table>';
					cardHTML += '													</td>';
					cardHTML += '													<td class="w6" align="right">';
					cardHTML += '														<table cellpadding="0" cellspacing="0" border="0" width="100%" class="lh085 tla">';
					cardHTML += '															<tr>';
					cardHTML += '																<td class="w3">评分：</td>';
					cardHTML += '																<td class="w3"><span class="zdf0000">'+dataObj[i].scoreSort+'</span>分</td>';
					cardHTML += '															</tr>';
					cardHTML += '															<tr>';
					cardHTML += '																<td>诊次：</td>';
					cardHTML += '																<td>'+dataObj[i].doctorSecond+'次</td>';
					cardHTML += '															</tr>';
					cardHTML += '															<tr>';
					cardHTML += '																<td>距离：</td>';
					cardHTML += '																<td>0km</td>';
					cardHTML += '															</tr>';
					cardHTML += '														</table>';
					cardHTML += '													</td>';
					cardHTML += '												</tr>';
					cardHTML += '											</table>';
					cardHTML += '										</td>';
					cardHTML += '									</tr>';
					cardHTML += '								</table>';
					cardHTML += '							</td>';
					cardHTML += '						</tr>';
					cardHTML += '					</table>';
					cardHTML += '					</a>';
					cardHTML += '				</div>';

					}
									cardHTML += '</div>';

									$('.card .card-content').replaceWith(cardHTML);
									$(".card-content").css("min-height" , window.screen.height + "px");  
								// done
								//	$.pullToRefreshDone('.pull-to-refresh-content');					
					
			}
		});
		
}
	
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
							$(".infinite-scroll-preloader").css("display","block");
							$("#" + til + "_" + i).addClass(s_style);
							if(i==5)
								{
									//筛选
									$("#layer").css({"display":"block"});
									
									
								//	cardHtml=
								//	$('.card .card-content').replaceWith(cardHTML);
								//	$(".card-content").css("min-height" , window.screen.height + "px");  
								// done
								//	$.pullToRefreshDone('.pull-to-refresh-content');
								}
							else
								{
									if($("#layer").css("display")=="block")
										{
											$("#myFilter")[0].reset();
										}
									$("#layer").css({"display":"none"});
									var cardHTML =  '<div class="card-content">';
									//flag="1：综合；2：评分；3、诊次；4、距离；5、筛选"
									$.ajax({
										url: "/shenzhou/web/doctor/doctorList.jhtml",
										type: "GET",
										contentType:"application/x-www-form-urlencoded; charset=UTF-8",
										data: {scoreSort: '0', second: '0', distance: '0', longitude: '0',latitude: '0',pageNumber: '1',flag: flag},
										traditional: true,  
										async: false,
										success: function(data) {
										var dataObj = eval('('+data+')');
										if(dataObj==null||dataObj.length<=0){
											$.fn.tips({content:'无更多医生数据'});
											$(".infinite-scroll-preloader").css("display","none");
											return;
										}
												for(var i=0;i<dataObj.length;i++){		
												cardHTML += '				<div class="card-content-inner">';
												cardHTML += '					<a href="${base}/web/doctor/doctorDetails.jhtml?doctorId='+dataObj[i].doctorId+'">';
												cardHTML += '					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto">';
												cardHTML += '						<tr>';
												cardHTML += '							<td valign="top" class="w6 pr"><img src="'+dataObj[i].doctorLogo+'" class="imgw120 br5"><img src="images/sm.png" class="imgw19"></td>';
												cardHTML += '							<td valign="top">';
												cardHTML += '								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1 tlf">';
												cardHTML += '									<tr>';
												cardHTML += '										<td class="z_1_323232_1 tdwb">';
												cardHTML += '											'+dataObj[i].doctorName+' <span class="z_0782_666666_1">'+(dataObj[i].sex=="male"?"男":"女")+'</span> <span class="tag1">'+dataObj[i].doctorCategory+'</span>';
												cardHTML += '										</td>';
												cardHTML += '									</tr>';
												cardHTML += '									<tr>';
												cardHTML += '										<td>';
												cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
												cardHTML += '												<tr>';
												cardHTML += '													<td valign="top">';
												cardHTML += '														<table cellpadding="0" cellspacing="0" border="0" width="95%" class="tla">';
												cardHTML += '															<tr>';
												cardHTML += '																<td class="lh085">'+dataObj[i].mechanismName+'</td>';
												cardHTML += '															</tr>';
												cardHTML += '															<tr>';
												cardHTML += '																<td class="lh085">'+dataObj[i].introduce+'</td>';
												cardHTML += '															</tr>';
												cardHTML += '														</table>';
												cardHTML += '													</td>';
												cardHTML += '													<td class="w6" align="right">';
												cardHTML += '														<table cellpadding="0" cellspacing="0" border="0" width="100%" class="lh085 tla">';
												cardHTML += '															<tr>';
												cardHTML += '																<td class="w3">评分：</td>';
												cardHTML += '																<td class="w3"><span class="zdf0000">'+dataObj[i].scoreSort+'</span>分</td>';
												cardHTML += '															</tr>';
												cardHTML += '															<tr>';
												cardHTML += '																<td>诊次：</td>';
												cardHTML += '																<td>'+dataObj[i].doctorSecond+'次</td>';
												cardHTML += '															</tr>';
												cardHTML += '															<tr>';
												cardHTML += '																<td>距离：</td>';
												cardHTML += '																<td>0km</td>';
												cardHTML += '															</tr>';
												cardHTML += '														</table>';
												cardHTML += '													</td>';
												cardHTML += '												</tr>';
												cardHTML += '											</table>';
												cardHTML += '										</td>';
												cardHTML += '									</tr>';
												cardHTML += '								</table>';
												cardHTML += '							</td>';
												cardHTML += '						</tr>';
												cardHTML += '					</table>';
												cardHTML += '					</a>';
												cardHTML += '				</div>';

									}
											}
										});
									cardHTML += '</div>';

									$('.card .card-content').replaceWith(cardHTML);
									$(".card-content").css("min-height" , window.screen.height + "px");  
								// done
								//	$.pullToRefreshDone('.pull-to-refresh-content');									
								}
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
		
		
		//////上拉加载
		
			//flag="1：综合；2：评分；3、诊次；4、距离；5、筛选"
			var cardHTML =  '<div class="card-content">';
			
			$.ajax({
				url: "/shenzhou/web/doctor/doctorList.jhtml",
				type: "GET",
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				data: {scoreSort: '0', second: '0', distance: '0', longitude: '0',latitude: '0',pageNumber: '1',flag: flag},
				traditional: true,  
				async: false,
				success: function(data) {
				var dataObj = eval('('+data+')');
				if(dataObj==null||dataObj.length<=0){
					$.fn.tips({content:'无更多医生数据'});
					$(".infinite-scroll-preloader").css("display","none");
					return;
				}
				for(var i=0;i<dataObj.length;i++){				
					cardHTML += '				<div class="card-content-inner">';
					cardHTML += '					<a href="${base}/web/doctor/doctorDetails.jhtml?doctorId='+dataObj[i].doctorId+'">';
					cardHTML += '					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto">';
					cardHTML += '						<tr>';
					cardHTML += '							<td valign="top" class="w6 pr"><img src="'+dataObj[i].doctorLogo+'" class="imgw120 br5"><img src="images/sm.png" class="imgw19"></td>';
					cardHTML += '							<td valign="top">';
					cardHTML += '								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1 tlf">';
					cardHTML += '									<tr>';
					cardHTML += '										<td class="z_1_323232_1 tdwb">';
					cardHTML += '											'+dataObj[i].doctorName+' <span class="z_0782_666666_1">'+(dataObj[i].sex=="male"?"男":"女")+'</span> <span class="tag1">'+dataObj[i].doctorCategory+'</span>';
					cardHTML += '										</td>';
					cardHTML += '									</tr>';
					cardHTML += '									<tr>';
					cardHTML += '										<td>';
					cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
					cardHTML += '												<tr>';
					cardHTML += '													<td valign="top">';
					cardHTML += '														<table cellpadding="0" cellspacing="0" border="0" width="95%" class="tla">';
					cardHTML += '															<tr>';
					cardHTML += '																<td class="lh085">'+dataObj[i].mechanismName+'</td>';
					cardHTML += '															</tr>';
					cardHTML += '															<tr>';
					cardHTML += '																<td class="lh085">'+dataObj[i].introduce+'</td>';
					cardHTML += '															</tr>';
					cardHTML += '														</table>';
					cardHTML += '													</td>';
					cardHTML += '													<td class="w6" align="right">';
					cardHTML += '														<table cellpadding="0" cellspacing="0" border="0" width="100%" class="lh085 tla">';
					cardHTML += '															<tr>';
					cardHTML += '																<td class="w3">评分：</td>';
					cardHTML += '																<td class="w3"><span class="zdf0000">'+dataObj[i].scoreSort+'</span>分</td>';
					cardHTML += '															</tr>';
					cardHTML += '															<tr>';
					cardHTML += '																<td>诊次：</td>';
					cardHTML += '																<td>'+dataObj[i].doctorSecond+'次</td>';
					cardHTML += '															</tr>';
					cardHTML += '															<tr>';
					cardHTML += '																<td>距离：</td>';
					cardHTML += '																<td>0km</td>';
					cardHTML += '															</tr>';
					cardHTML += '														</table>';
					cardHTML += '													</td>';
					cardHTML += '												</tr>';
					cardHTML += '											</table>';
					cardHTML += '										</td>';
					cardHTML += '									</tr>';
					cardHTML += '								</table>';
					cardHTML += '							</td>';
					cardHTML += '						</tr>';
					cardHTML += '					</table>';
					cardHTML += '					</a>';
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
	
	/////下一页
	
	$(document).on("pageInit", "#page-infinite-scroll", function(e, id, page) {
		function addItems(number, lastIndex) {
			var html = '';
			//flag="1：综合；2：评分；3、诊次；4、距离；5、筛选"
			key += 1;
			console.log(key);
			$.ajax({
				url: "/shenzhou/web/doctor/doctorList.jhtml",
				type: "GET",
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				data: {scoreSort: '0', second: '0', distance: '0', longitude: '0',latitude: '0',pageNumber: key,flag: flag},
				traditional: true,  
				async: false,
				success: function(data) {
					var dataObj = eval('('+data+')');
					if(dataObj==null||dataObj.length<=0){
					$.fn.tips({content:'无更多医生数据'});
					$(".infinite-scroll-preloader").css("display","none");
					return;
				}
					for(var i=0;i<dataObj.length;i++){					
						html += '				<div class="card-content-inner">';
						html += '					<a href="${base}/web/doctor/doctorDetails.jhtml?doctorId='+dataObj[i].doctorId+'">';
						html += '					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto">';
						html += '						<tr>';
						html += '							<td valign="top" class="w6 pr"><img src="'+dataObj[i].doctorLogo+'" class="imgw120 br5"><img src="images/sm.png" class="imgw19"></td>';
						html += '							<td valign="top">';
						html += '								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1 tlf">';
						html += '									<tr>';
						html += '										<td class="z_1_323232_1 tdwb">';
						html += '											'+dataObj[i].doctorName+' <span class="z_0782_666666_1">'+(dataObj[i].sex=="male"?"男":"女")+'</span> <span class="tag1">'+dataObj[i].doctorCategory+'</span>';
						html += '										</td>';
						html += '									</tr>';
						html += '									<tr>';
						html += '										<td>';
						html += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
						html += '												<tr>';
						html += '													<td valign="top">';
						html += '														<table cellpadding="0" cellspacing="0" border="0" width="95%" class="tla">';
						html += '															<tr>';
						html += '																<td class="lh085">'+dataObj[i].mechanismName+'</td>';
						html += '															</tr>';
						html += '															<tr>';
						html += '																<td class="lh085">'+dataObj[i].introduce+'</td>';
						html += '															</tr>';
						html += '														</table>';
						html += '													</td>';
						html += '													<td class="w6" align="right">';
						html += '														<table cellpadding="0" cellspacing="0" border="0" width="100%" class="lh085 tla">';
						html += '															<tr>';
						html += '																<td class="w3">评分：</td>';
						html += '																<td class="w3"><span class="zdf0000">'+dataObj[i].scoreSort+'</span>分</td>';
						html += '															</tr>';
						html += '															<tr>';
						html += '																<td>诊次：</td>';
						html += '																<td>'+dataObj[i].doctorSecond+'次</td>';
						html += '															</tr>';
						html += '															<tr>';
						html += '																<td>距离：</td>';
						html += '																<td>0km</td>';
						html += '															</tr>';
						html += '														</table>';
						html += '													</td>';
						html += '												</tr>';
						html += '											</table>';
						html += '										</td>';
						html += '									</tr>';
						html += '								</table>';
						html += '							</td>';
						html += '						</tr>';
						html += '					</table>';
						html += '					</a>';
						html += '				</div>';
			}		
			}
				});
			$('.card .card-content').append(html);
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
	
	$("#ReserveDate").calendar({
		monthNames		:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
		dayNamesShort	:['周日','周一','周二','周三','周四','周五','周六'],
		minDate 		: getNowFormatDate(),
		
	});
	$("#StartDate").calendar({
		monthNames		:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
		dayNamesShort	:['周日','周一','周二','周三','周四','周五','周六'],
		minDate 		: getNowFormatDate(),
		
	});
	$("#EndDate").calendar({
		monthNames		:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
		dayNamesShort	:['周日','周一','周二','周三','周四','周五','周六'],
		minDate 		:getNowFormatDate(),
		
	});
	
	//这里载入页面首次加载数据
	get_info(1,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');
	$.init();
	
	$(".card-content").css("min-height" , window.screen.height + "px");  
	
});
	

</script>


</head>

<body>
<!-- page 容器 -->
<div class="page" id="page-infinite-scroll">
	<!-- 标题栏 -->
	<header class="bar bar-nav h86 bgffffff" id="header">
		<table cellpadding="0" cellspacing="0" border="0" width="98%" align="center">
			<tr>
				<td align="center" class="wz w2"><a href="javascript:;" onclick="javascript:history.back(-1);"><img src="${base}/resources/web/images/j_2.png" class="imgw0667 hauto"></a></td>
				<td align="center">
					<input type="text" placeholder="搜索项目" class="sip">
				</td>
				<td style="width: 2rem" align="center"><img src="${base}/resources/web/images/x_1.png" style="width:1.53rem"></td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" border="0" width="98%" align="center" class="m0auto">
			<tr>
				<td align="center" class="btbl70adf8_1"><a href="${base}/web/project/toProjectList.jhtml" class="z_106_909090_1 dpibk h208 w100 lh208">服务项目</a></td>
				<td align="center" class="btbl70adf8_1"><a href="${base}/web/mechanism/toMechanismList.jhtml" class="z_106_909090_1 dpibk h208 w100 lh208">服务机构</a></td>
				<td align="center" class="btbl70adf8_1 br70adf8_1"><a href="javascript:;" class="bg4c98f6 z_106_ffffff_1 dpibk h208 w100 lh208">诊疗医师</a></td>
			</tr>
		</table>  	
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td class="bgf9f9f9">
					<table cellpadding="0" cellspacing="0" border="0" width="98%" class="m0auto">
						<tr>
							<td width="28%" align="center" class="h316 z_106_4c98f6_1" id="til_1"><a href="javascript:;" onClick="get_info(1,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">综合排序</a></td>
							<td width="18%" align="center" class="z_106_bdbcbc_1" id="til_2"><a href="javascript:;" onClick="get_info(2,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">评分</a></td>
							<td width="18%" align="center" class="z_106_bdbcbc_1" id="til_3"><a href="javascript:;" onClick="get_info(3,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">诊次</a></td>
							<td width="18%" align="center" class="z_106_bdbcbc_1" id="til_4"><a href="javascript:;" onClick="get_info(4,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">距离</a></td>
							<td width="18%" align="center" class="z_106_bdbcbc_1" id="til_5"><a href="javascript:;" onClick="get_info(5,5,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">筛选</a></td>
						</tr>
					</table>
				</td>
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
			<div id="layer">
				<div class="layer" style="overflow-y:hidden">
					<form id="myFilter">
					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_106_323232_1">
						<tr>
							<td class="w5 h316">价格区间</td>
							<td>
								<input type="text" id="price_min" name="price_min" class="h229 w8 lh229 z_106_323232_1 inp_1">
								<span class="z_106_4c98f6_1">—</span>
								<input type="text" id="price_max" name="price_max" class="h229 w8 lh229 z_106_323232_1 inp_1">
							</td>
						</tr>
						<tr>
							<td class="h316">服务项目</td>
							<td>
								<select id="serverProjectCategoryId" name="serverProjectCategoryId" class="h229 w1764 lh229 z_106_323232_1 inp_1">
									<option value="">请选择</option>
									[#list serverProjectCategoryList as serverProjectCategory]
									<option value="${serverProjectCategory.id}">${serverProjectCategory.name}</option>
									[/#list]
								</select>
							</td>
						</tr>
						<tr>
							<td class="h316">医生技师</td>
							<td><input type="text" id="doctorName" name="doctorName" class="h229 w1764 lh229 z_106_323232_1 inp_1"></td>
						</tr>
						<tr>
							<td class="h316">预诊日期</td>
							<td><input type="text" class="h229 w1764 lh229 z_106_323232_1 inp_1" id="ReserveDate" name="ReserveDate"></td>
						</tr>
						<tr>
							<td class="h316">预诊日期</td>
							<td>
								<input type="text" class="h229 w8 lh229 z_106_323232_1 inp_1" id="StartDate" name="StartDate">
								<span class="z_106_4c98f6_1">—</span>
								<input type="text" class="h229 w8 lh229 z_106_323232_1 inp_1" id="EndDate" name="EndDate">
							</td>
						</tr>
						<tr>
							<td class="h316">服务方式</td>
							<td>
								<input type="radio" id="service" name="service" value="store" checked> 到店
								<input type="radio" id="service" name="service" value="home"> 上门
							</td>
						</tr>
						<tr>
							<td class="h316">医师性别</td>
							<td>
								<input type="radio" id="sex" name="sex" value="male" checked> 男
								<input type="radio" id="sex" name="sex" value="female"> 女
							</td>
						</tr>
						<tr>
							<td colspan="2" class="h316">
								<input type="button" value="重置" onClick="reset();" class="h229 w8 lh229 z_106_323232_1 inp_2" style="width: 100%">
							</td>
						</tr>
						<tr>
							<td colspan="2" class="h316">
								<input type="button" value="确定" onclick="filtrateDoctor()" class="h229 w8 lh229 z_106_323232_1 inp_3" style="width: 100%">
							</td>
						</tr>
					</table>
					</form>
				</div>
			</div>
		</div>
	</div>	
</div>
<style>
	.pull-to-refresh-layer{margin-top: 6.21rem;}	
	.bar{padding-right: 0px;padding-left: 0px;border-bottom:none;}
	.page{background: #f0f1f0;}
	.card{border-radius: 0; box-shadow: #ffffff 0px 0rem 0rem;margin: 0px;}
	.card-content-inner{border-bottom: 2px solid #f9f9f9}
</style>
</body>

</html>
