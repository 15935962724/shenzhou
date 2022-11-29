<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>好康护 - 让幸福简单起来</title>
<link rel="stylesheet" href="${base}/resources/web/light7/dist/css/light7.min.css">
<link rel="stylesheet" href="${base}/resources/web/light7/dist/css/light7-swiper.min.css">
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/web/font/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/light7/dist/js/light7.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/light7/dist/js/light7-swiper.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/web/tips/jquery.tips.js"></script>
<style>
	.pull-to-refresh-layer{margin-top: 6.21rem;}	
	.bar{padding-right: 0px;padding-left: 0px;border-bottom:none;}
	.page{background: #f0f1f0;}
	.card{border-radius: 0; box-shadow: #ffffff 0px 0rem 0rem;margin: 0px;}
	.card-content-inner{border-bottom: 2px solid #f9f9f9}
</style>
<script>

	//id 编号、num 总数、til id值、info 列表、s_style 选择后的样式、 o_style 初始样式
	var flag = 1,key = 1,sort='asc';	
	
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
			$.fn.tips({content:'无更多服务数据'});
			$(".infinite-scroll-preloader").css("display","none");
			return;
		}
				var cardHTML =  '<div class="card-content">';
				for(var i=0;i<dataObj.length;i++){		
					cardHTML += '				<div class="card-content-inner">';
					cardHTML += '					<a href="${base}/web/project/projectDetails.jhtml?projectId='+dataObj[i].projectId+'">';
					cardHTML += '					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto">';
					cardHTML += '						<tr>';
					cardHTML += '							<td valign="top" class="w6"><img src="'+dataObj[i].projectLogo+'" class="imgw120"></td>';
					cardHTML += '							<td valign="top">';
					cardHTML += '								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1 tlf">';
					cardHTML += '									<tr>';
					cardHTML += '										<td class="z_128_323232_1 tdwb">';
					cardHTML += '											'+dataObj[i].projectName+'';
					cardHTML += '										</td>';
					cardHTML += '									</tr>';
					cardHTML += '									<tr>';
					cardHTML += '										<td>';
					cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tlf">';
					cardHTML += '												<tr>';
					cardHTML += '													<td class="tdwb">'+dataObj[i].mechanismName+'</td>';
					cardHTML += '													<td class="w3" align="right">评分：</td>';
					cardHTML += '													<td class="w3" align="right"><span class="zdf0000">'+dataObj[i].scoreSort+'</span>分</td>';
					cardHTML += '												</tr>';
					cardHTML += '												<tr>';
					cardHTML += '													<td>'+dataObj[i].doctorName+' 女 <span class="tag1">主任医师</span></td>';
					cardHTML += '													<td class="w3" align="right">距离：</td>';
					cardHTML += '													<td class="w3" align="right">9999km</td>';
					cardHTML += '												</tr>';
					cardHTML += '											</table>';
					cardHTML += '										</td>';
					cardHTML += '									</tr>';
					cardHTML += '									<tr>';
					cardHTML += '										<td>';
					cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
					cardHTML += '												<tr>';
					cardHTML += '													<td width="50%">诊次：'+dataObj[i].second+'次</td>';
					cardHTML += '													<td width="50%" align="right"><span class="z_106_df0000_1">￥'+dataObj[i].price+'</span>/'+dataObj[i].time+'分钟</td>';
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
									if(sort == "desc")
									   	{
											$("#icon_5").html("<i class=\"iconfont zs08\">&#xe602;</i>");
											sort = "asc";
										}
									else
									   	{
											$("#icon_5").html("<i class=\"iconfont zs08\">&#xe603;</i>");
											sort = "desc";
										}
								}
							else
								{
											$("#icon_5").html("<i class=\"iconfont zs08\">&#xe62b;</i>");
											sort = "desc";
								}							
							
							
							if(i==6)
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
									//flag="1：综合；2：评分；3、诊次；4、距离；5、价格; 6 筛选"
									$.ajax({
										url: "/shenzhou/web/project/projectList.jhtml",
										type: "GET",
										contentType:"application/x-www-form-urlencoded; charset=UTF-8",
										data: {longitude: '0',latitude: '0',pageNumber: '1',flag: flag,sort: sort},
										traditional: true,  
										async: false,
										success: function(data) {
										var dataObj = eval('('+data+')');
										if(dataObj==null||dataObj.length<=0){
											$.fn.tips({content:'无更多服务数据'});
											$(".infinite-scroll-preloader").css("display","none");
											return;
										}
												for(var i=0;i<dataObj.length;i++){		
												cardHTML += '				<div class="card-content-inner">';
												cardHTML += '					<a href="${base}/web/project/projectDetails.jhtml?projectId='+dataObj[i].projectId+'">';
												cardHTML += '					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto">';
												cardHTML += '						<tr>';
												cardHTML += '							<td valign="top" class="w6"><img src="'+dataObj[i].projectLogo+'" class="imgw120"></td>';
												cardHTML += '							<td valign="top">';
												cardHTML += '								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1 tlf">';
												cardHTML += '									<tr>';
												cardHTML += '										<td class="z_128_323232_1 tdwb">';
												cardHTML += '											'+dataObj[i].projectName+'';
												cardHTML += '										</td>';
												cardHTML += '									</tr>';
												cardHTML += '									<tr>';
												cardHTML += '										<td>';
												cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tlf">';
												cardHTML += '												<tr>';
												cardHTML += '													<td class="tdwb">'+dataObj[i].mechanismName+'</td>';
												cardHTML += '													<td class="w3" align="right">评分：</td>';
												cardHTML += '													<td class="w3" align="right"><span class="zdf0000">'+dataObj[i].scoreSort+'</span>分</td>';
												cardHTML += '												</tr>';
												cardHTML += '												<tr>';
												cardHTML += '													<td>'+dataObj[i].doctorName+' 女 <span class="tag1">主任医师</span></td>';
												cardHTML += '													<td class="w3" align="right">距离：</td>';
												cardHTML += '													<td class="w3" align="right">9999km</td>';
												cardHTML += '												</tr>';
												cardHTML += '											</table>';
												cardHTML += '										</td>';
												cardHTML += '									</tr>';
												cardHTML += '									<tr>';
												cardHTML += '										<td>';
												cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
												cardHTML += '												<tr>';
												cardHTML += '													<td width="50%">诊次：'+dataObj[i].second+'次</td>';
												cardHTML += '													<td width="50%" align="right"><span class="z_106_df0000_1">￥'+dataObj[i].price+'</span>/'+dataObj[i].time+'分钟</td>';
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
				url: "/shenzhou/web/project/projectList.jhtml",
				type: "GET",
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				data: {longitude: '0',latitude: '0',pageNumber: '1',flag: flag,sort: sort},
				traditional: true,  
				async: false,
				success: function(data) {
				var dataObj = eval('('+data+')');
				if(dataObj==null||dataObj.length<=0){
					$.fn.tips({content:'无更多服务数据'});
					$(".infinite-scroll-preloader").css("display","none");
					return;
				}
				for(var i=0;i<dataObj.length;i++){				
					cardHTML += '				<div class="card-content-inner">';
					cardHTML += '					<a href="${base}/web/project/projectDetails.jhtml?projectId='+dataObj[i].projectId+'">';
					cardHTML += '					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto">';
					cardHTML += '						<tr>';
					cardHTML += '							<td valign="top" class="w6"><img src="'+dataObj[i].projectLogo+'" class="imgw120"></td>';
					cardHTML += '							<td valign="top">';
					cardHTML += '								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1 tlf">';
					cardHTML += '									<tr>';
					cardHTML += '										<td class="z_128_323232_1 tdwb">';
					cardHTML += '											'+dataObj[i].projectName+'';
					cardHTML += '										</td>';
					cardHTML += '									</tr>';
					cardHTML += '									<tr>';
					cardHTML += '										<td>';
					cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tlf">';
					cardHTML += '												<tr>';
					cardHTML += '													<td class="tdwb">'+dataObj[i].mechanismName+'</td>';
					cardHTML += '													<td class="w3" align="right">评分：</td>';
					cardHTML += '													<td class="w3" align="right"><span class="zdf0000">'+dataObj[i].scoreSort+'</span>分</td>';
					cardHTML += '												</tr>';
					cardHTML += '												<tr>';
					cardHTML += '													<td>'+dataObj[i].doctorName+' 女 <span class="tag1">主任医师</span></td>';
					cardHTML += '													<td class="w3" align="right">距离：</td>';
					cardHTML += '													<td class="w3" align="right">9999km</td>';
					cardHTML += '												</tr>';
					cardHTML += '											</table>';
					cardHTML += '										</td>';
					cardHTML += '									</tr>';
					cardHTML += '									<tr>';
					cardHTML += '										<td>';
					cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
					cardHTML += '												<tr>';
					cardHTML += '													<td width="50%">诊次：'+dataObj[i].second+'次</td>';
					cardHTML += '													<td width="50%" align="right"><span class="z_106_df0000_1">￥'+dataObj[i].price+'</span>/'+dataObj[i].time+'分钟</td>';
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
			var cardHTML = '';
			//flag="1：综合；2：评分；3、诊次；4、距离；5、筛选"
			key += 1;
			console.log(key);
			$.ajax({
				url: "/shenzhou/web/project/projectList.jhtml",
				type: "GET",
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				data: {longitude: '0',latitude: '0',pageNumber: key,flag: flag,sort: sort},
				traditional: true,  
				async: false,
				success: function(data) {
					var dataObj = eval('('+data+')');
					if(dataObj==null||dataObj.length<=0){
					$.fn.tips({content:'无更多服务数据'});
					$(".infinite-scroll-preloader").css("display","none");
					return;
				}
					for(var i=0;i<dataObj.length;i++){					
						cardHTML += '				<div class="card-content-inner">';
						cardHTML += '					<a href="${base}/web/project/projectDetails.jhtml?projectId='+dataObj[i].projectId+'">';
						cardHTML += '					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto">';
						cardHTML += '						<tr>';
						cardHTML += '							<td valign="top" class="w6"><img src="'+dataObj[i].projectLogo+'" class="imgw120"></td>';
						cardHTML += '							<td valign="top">';
						cardHTML += '								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1 tlf">';
						cardHTML += '									<tr>';
						cardHTML += '										<td class="z_128_323232_1 tdwb">';
						cardHTML += '											'+dataObj[i].projectName+'';
						cardHTML += '										</td>';
						cardHTML += '									</tr>';
						cardHTML += '									<tr>';
						cardHTML += '										<td>';
						cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tlf">';
						cardHTML += '												<tr>';
						cardHTML += '													<td class="tdwb">'+dataObj[i].mechanismName+'</td>';
						cardHTML += '													<td class="w3" align="right">评分：</td>';
						cardHTML += '													<td class="w3" align="right"><span class="zdf0000">'+dataObj[i].scoreSort+'</span>分</td>';
						cardHTML += '												</tr>';
						cardHTML += '												<tr>';
						cardHTML += '													<td>'+dataObj[i].doctorName+' 女 <span class="tag1">主任医师</span></td>';
						cardHTML += '													<td class="w3" align="right">距离：</td>';
						cardHTML += '													<td class="w3" align="right">9999km</td>';
						cardHTML += '												</tr>';
						cardHTML += '											</table>';
						cardHTML += '										</td>';
						cardHTML += '									</tr>';
						cardHTML += '									<tr>';
						cardHTML += '										<td>';
						cardHTML += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
						cardHTML += '												<tr>';
						cardHTML += '													<td width="50%">诊次：'+dataObj[i].second+'次</td>';
						cardHTML += '													<td width="50%" align="right"><span class="z_106_df0000_1">￥'+dataObj[i].price+'</span>/'+dataObj[i].time+'分钟</td>';
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
				<td align="center" class="wz w2"><a href="javascript:;" onclick="javascript:history.back(-1);"><img src="images/j_2.png" class="imgw0667 hauto"></a></td>
				<td align="center">
					<input type="text" placeholder="搜索项目" class="sip">
				</td>
				<td style="width: 2rem" align="center"><img src="images/x_1.png" style="width:1.53rem"></td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" border="0" width="98%" align="center" class="m0auto">
			<tr>
				<td align="center" class="btbl70adf8_1"><a href="javascript:;" class="bg4c98f6 z_106_ffffff_1 dpibk h208 w100 lh208">服务项目</a></td>
				<td align="center" class="btbl70adf8_1"><a href="javascript:;" class="z_106_909090_1 dpibk h208 w100 lh208">服务机构</a></td>
				<td align="center" class="btbl70adf8_1 br70adf8_1"><a href="javascript:;" class="z_106_909090_1 dpibk h208 w100 lh208">诊疗医师</a></td>
			</tr>
		</table>  	
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td class="bgf9f9f9">
					<table cellpadding="0" cellspacing="0" border="0" width="98%" class="m0auto">
						<tr>
							<td width="25%" align="center" class="h316 z_106_4c98f6_1" id="til_1"><a href="javascript:;" onClick="get_info(1,6,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">综合排序</a></td>
							<td width="14%" align="center" class="z_106_bdbcbc_1" id="til_2"><a href="javascript:;" onClick="get_info(2,6,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">评分</a></td>
							<td width="14%" align="center" class="z_106_bdbcbc_1" id="til_3"><a href="javascript:;" onClick="get_info(3,6,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">诊次</a></td>
							<td width="14%" align="center" class="z_106_bdbcbc_1" id="til_4"><a href="javascript:;" onClick="get_info(4,6,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">距离</a></td>
							<td width="19%" align="center" class="z_106_bdbcbc_1" id="til_5"><a href="javascript:;" onClick="get_info(5,6,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">价格<span id="icon_5"><i class="iconfont zs08">&#xe62b;</i></span></a></td>
							<td width="14%" align="center" class="z_106_bdbcbc_1" id="til_6"><a href="javascript:;" onClick="get_info(6,6,'til','','z_106_4c98f6_1','z_106_bdbcbc_1');">筛选</a></td>
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
								<input type="text" class="h229 w8 lh229 z_106_323232_1 inp_1">
								<span class="z_106_4c98f6_1">—</span>
								<input type="text" class="h229 w8 lh229 z_106_323232_1 inp_1">
							</td>
						</tr>
						<tr>
							<td class="h316">服务项目</td>
							<td>
								<select class="h229 w1764 lh229 z_106_323232_1 inp_1">
									<option>请选择</option>
									<option>物理训练</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="h316">医生技师</td>
							<td><input type="text" class="h229 w1764 lh229 z_106_323232_1 inp_1"></td>
						</tr>
						<tr>
							<td class="h316">预诊日期</td>
							<td><input type="text" class="h229 w1764 lh229 z_106_323232_1 inp_1" id="ReserveDate"></td>
						</tr>
						<tr>
							<td class="h316">预诊日期</td>
							<td>
								<input type="text" class="h229 w8 lh229 z_106_323232_1 inp_1" id="StartDate">
								<span class="z_106_4c98f6_1">—</span>
								<input type="text" class="h229 w8 lh229 z_106_323232_1 inp_1" id="EndDate">
							</td>
						</tr>
						<tr>
							<td class="h316">服务方式</td>
							<td>
								<input type="radio" id="service" name="service" value="到店" checked> 到店
								<input type="radio" id="service" name="service" value="上门"> 上门
							</td>
						</tr>
						<tr>
							<td colspan="2" class="h316">
								<input type="button" value="重置" onClick="reset();" class="h229 w8 lh229 z_106_323232_1 inp_2" style="width: 100%">
							</td>
						</tr>
						<tr>
							<td colspan="2" class="h316">
								<input type="submit" value="确定" class="h229 w8 lh229 z_106_323232_1 inp_3" style="width: 100%">
							</td>
						</tr>
					</table>
					</form>
				</div>
			</div>
		</div>
	</div>	
</div>




</body>
</html>
