<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>账单</title>
<link rel="stylesheet" href="${base}/resources/web/light7/css/light7.min.css">
<link rel="stylesheet" href="${base}/resources/web/light7/css/light7-swiper.min.css">
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/web/font/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/light7/js/light7.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/light7/js/light7-swiper.min.js" charset="utf-8"></script>
<script>
key = 1;	
	
var count = 10;
	
function DispTag(id)
	{
		for(var i=1;i<=count;i++)
			{
				if(id==i)
					{
						if(document.getElementById("cont_"+i).style.display == "block"){
								document.getElementById("cont_"+i).style.display = "none";
							}else{
								document.getElementById("cont_"+i).style.display = "block";
							}
					}else{
						document.getElementById("cont_"+i).style.display = "none";
					}
			}
	}

function getDateInfo(){

		//日期   
		var month = $("#ndate").val();
		var cardHTML =  '<div class="card-content">';
		$.ajax({
			url: "/shenzhou/web/member/memberBill.jhtml",
			type: "GET",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data: {page: '1', month: month},
			traditional: true,  
			async: false,
			success: function(data) {
			var dataObj = eval('('+data+')');
			if(dataObj.memberBillList==null||dataObj.memberBillList.length<=0){
				$.fn.tips({content:'无更多账单数据'});
				$(".infinite-scroll-preloader").css("display","none");
				return;
				}
				count=dataObj.sum;
				for(var i=0;i<dataObj.memberBillList.length;i++){	
				var str = dataObj.memberBillList[i].billDay.split("-");
				
				cardHTML += '				<div class="card-content-inner">';
				cardHTML += '					 <div class="myCenter_bill_header" onClick="DispTag('+i+')" style="width:100%;height:3.75rem;">';
				cardHTML += '						 <div class="myCenter_bill_headerDate">';
				cardHTML += '							<div class="myCenter_bill_circle" style="line-height: 0.5rem; text-align: center;"><span style="font-size:1.15rem;color:#FFFFFF;padding-top:0.5rem;line-height: 1.75rem;">' + str[2] + '</span><br/><span style="font-size:0.85rem;color:#FFFFFF;">' + str[1] + '月</span></div>';
				cardHTML += '						 </div>';
				cardHTML += '						 <div class="myCenter_bill_headerPay"><span style="font-size:1.25rem;color:#ca0000;text-align:left;">'+dataObj.memberBillList[i].totalRecharge+'</span></div>';
				cardHTML += '						 <div class="mycenter_bill_headerIncome"><span style="font-size:1.25rem;color:#23870b;text-align:left;">'+dataObj.memberBillList[i].totalAddress+'</span></div>';
				cardHTML += '						 <div class="mycenter_bill_headerDetails"><span style="font-size:1rem;color:#4c98f6;text-align:center;">明细</span></div>';
				cardHTML += '					 </div>';
					for(var x=0;x<dataObj.memberBillList[i].memberBillDetails.length;x++){
					cardHTML += '					 <div class="myCenter_bill_cell" style="display:none;" id="cont_'+i+'">';
						cardHTML += '						<div class="mycenter_bill_content_left">';
						cardHTML += '							<div class="mycenter_bill_timeLabel"><span style="font-size:1rem;color:#a5a5a5">'+dataObj.memberBillList[i].memberBillDetails[x].time+'</span><span class="myCenter_bill_cellLeftMoney">消费</span></div>';
						cardHTML += '							<div class="mycenter_bill_expertLabel"><span style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].projectName+'</span></div>';
						cardHTML += '							<div class="mycenter_bill_titleLabel"><span style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].mechanismName+'</span></div>';
						cardHTML += '						</div>';
						cardHTML += '						<div class="mycenter_bill_content_right">';
						cardHTML += '							<div class="mycenter_bill_rightName" style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].name+'</div>';
						cardHTML += '							<div class="mycenter_bill_rightMoney" style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].money+'</div>';
						cardHTML += '						</div>';
						cardHTML += '					 </div>';
						}
				
				cardHTML += '				</div>';
			}
			}
		});
		cardHTML += '</div>';

		$('.card .card-content').html(cardHTML);
		$(".card-content").css("min-height" , window.screen.height + "px");  
	// done
		$.pullToRefreshDone('.pull-to-refresh-content');									

		$(".infinite-scroll").scrollTop(0);
	}	
$(function () {		
		$(document).on('refresh', '.pull-to-refresh-content',function(e) {
		setTimeout(function() {
			var cardHTML =  '<div class="card-content">';
			$.ajax({
			url: "/shenzhou/web/member/memberBill.jhtml",
			type: "GET",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data: {page: '1', month: month},
			traditional: true,  
			async: false,
			success: function(data) {
			var dataObj = eval('('+data+')');
			if(dataObj.memberBillList==null||dataObj.memberBillList.length<=0){
				$.fn.tips({content:'无更多账单数据'});
				$(".infinite-scroll-preloader").css("display","none");
				return;
				}
				count=dataObj.sum;
				for(var i=0;i<dataObj.memberBillList.length;i++){	
				var str = dataObj.memberBillList[i].billDay.split("-");
				
				cardHTML += '				<div class="card-content-inner">';
				cardHTML += '					 <div class="myCenter_bill_header" onClick="DispTag('+i+')" style="width:100%;height:3.75rem;">';
				cardHTML += '						 <div class="myCenter_bill_headerDate">';
				cardHTML += '							<div class="myCenter_bill_circle" style="line-height: 0.5rem; text-align: center;"><span style="font-size:1.15rem;color:#FFFFFF;padding-top:0.5rem;line-height: 1.75rem;">' + str[2] + '</span><br/><span style="font-size:0.85rem;color:#FFFFFF;">' + str[1] + '月</span></div>';
				cardHTML += '						 </div>';
				cardHTML += '						 <div class="myCenter_bill_headerPay"><span style="font-size:1.25rem;color:#ca0000;text-align:left;">'+dataObj.memberBillList[i].totalRecharge+'</span></div>';
				cardHTML += '						 <div class="mycenter_bill_headerIncome"><span style="font-size:1.25rem;color:#23870b;text-align:left;">'+dataObj.memberBillList[i].totalAddress+'</span></div>';
				cardHTML += '						 <div class="mycenter_bill_headerDetails"><span style="font-size:1rem;color:#4c98f6;text-align:center;">明细</span></div>';
				cardHTML += '					 </div>';
				cardHTML += '					 <div class="myCenter_bill_cell" style="display:none;" id="cont_'+i+'">';
					for(var x=0;x<dataObj.memberBillList[i].memberBillDetails.length;x++){
						cardHTML += '						<div class="mycenter_bill_content_left">';
						cardHTML += '							<div class="mycenter_bill_timeLabel"><span style="font-size:1rem;color:#a5a5a5">'+dataObj.memberBillList[i].memberBillDetails[x].time+'</span><span class="myCenter_bill_cellLeftMoney">消费</span></div>';
						cardHTML += '							<div class="mycenter_bill_expertLabel"><span style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].projectName+'</span></div>';
						cardHTML += '							<div class="mycenter_bill_titleLabel"><span style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].mechanismName+'</span></div>';
						cardHTML += '						</div>';
						cardHTML += '						<div class="mycenter_bill_content_right">';
						cardHTML += '							<div class="mycenter_bill_rightName" style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].name+'</div>';
						cardHTML += '							<div class="mycenter_bill_rightMoney" style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].money+'</div>';
						cardHTML += '						</div>';
						}
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
			var html = '';
			$.ajax({
			url: "/shenzhou/web/member/memberBill.jhtml",
			type: "GET",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data: {page: '1', month: month},
			traditional: true,  
			async: false,
			success: function(data) {
			var dataObj = eval('('+data+')');
			if(dataObj.memberBillList==null||dataObj.memberBillList.length<=0){
				$.fn.tips({content:'无更多账单数据'});
				$(".infinite-scroll-preloader").css("display","none");
				return;
				}
				count=dataObj.sum;
				for(var i=0;i<dataObj.memberBillList.length;i++){	
				var str = dataObj.memberBillList[i].billDay.split("-");
				
				cardHTML += '				<div class="card-content-inner">';
				cardHTML += '					 <div class="myCenter_bill_header" onClick="DispTag('+i+')" style="width:100%;height:3.75rem;">';
				cardHTML += '						 <div class="myCenter_bill_headerDate">';
				cardHTML += '							<div class="myCenter_bill_circle" style="line-height: 0.5rem; text-align: center;"><span style="font-size:1.15rem;color:#FFFFFF;padding-top:0.5rem;line-height: 1.75rem;">' + str[2] + '</span><br/><span style="font-size:0.85rem;color:#FFFFFF;">' + str[1] + '月</span></div>';
				cardHTML += '						 </div>';
				cardHTML += '						 <div class="myCenter_bill_headerPay"><span style="font-size:1.25rem;color:#ca0000;text-align:left;">'+dataObj.memberBillList[i].totalRecharge+'</span></div>';
				cardHTML += '						 <div class="mycenter_bill_headerIncome"><span style="font-size:1.25rem;color:#23870b;text-align:left;">'+dataObj.memberBillList[i].totalAddress+'</span></div>';
				cardHTML += '						 <div class="mycenter_bill_headerDetails"><span style="font-size:1rem;color:#4c98f6;text-align:center;">明细</span></div>';
				cardHTML += '					 </div>';
				cardHTML += '					 <div class="myCenter_bill_cell" style="display:none;" id="cont_'+i+'">';
					for(var x=0;x<dataObj.memberBillList[i].memberBillDetails.length;x++){
						cardHTML += '						<div class="mycenter_bill_content_left">';
						cardHTML += '							<div class="mycenter_bill_timeLabel"><span style="font-size:1rem;color:#a5a5a5">'+dataObj.memberBillList[i].memberBillDetails[x].time+'</span><span class="myCenter_bill_cellLeftMoney">消费</span></div>';
						cardHTML += '							<div class="mycenter_bill_expertLabel"><span style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].projectName+'</span></div>';
						cardHTML += '							<div class="mycenter_bill_titleLabel"><span style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].mechanismName+'</span></div>';
						cardHTML += '						</div>';
						cardHTML += '						<div class="mycenter_bill_content_right">';
						cardHTML += '							<div class="mycenter_bill_rightName" style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].name+'</div>';
						cardHTML += '							<div class="mycenter_bill_rightMoney" style="font-size:1rem;color:#646464">'+dataObj.memberBillList[i].memberBillDetails[x].money+'</div>';
						cardHTML += '						</div>';
						}
						cardHTML += '					 </div>';
				
				cardHTML += '				</div>';
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

<script>
</script>  

</head>

<body style="background:#f0f1f0">

<!-- page 容器 -->
<div class="page" id="page-infinite-scroll">
	<!-- 标题栏 -->
	<header class="bar bar-nav bgffffff" id="header" style="height: 6.36rem;">
		<table cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
			<tr>
				<td align="center" class="wz w2"><a href="javascript:;" onclick="javascript:history.back(-1);"><img src="${base}/resources/web/images/j_2.png" class="imgw0667 hauto"></a></td>
				<td align="center" style="padding-left: 5rem;">
					账单
				</td>
				<td style="width: 7rem" align="center">
				<select class="mycenter_bill_toDayTime" id="ndate" onchange="getDateInfo(); ">
				[#list stringList as dateString]
     	 	    	<option value="${dateString}">${dateString}</option>
     	 	    [/#list]
     	 	    </select>
				</td>
			</tr>
		</table>

		<table cellpadding="0" cellspacing="0" border="0" width="100%" class="bgf9f9f9">
			<tr>
				<td align="center" class="h316">
		 <div class="myCenter_bill_left" style="font-size:1rem;color:#666666;"><span style="padding-left:0.833rem;">当月支出:</span><span style="font-size:1rem;color:#4c98f6;">100元</span></div>
     	 <div class="mycenter_bill_right" style="font-size:1rem;color:#666666;"><span style="padding-left:0.833rem;">当月收入:</span><span style="font-size:1rem;color:#4c98f6;">200元</span></div>
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
		</div>
	</div>	
</div>
     
</body>
</html>
