<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script>
$(function(){

	var $listForm = $("#listForm");
	
	var $sort = $("#sort");//排序
	
	var $query = $("#query");//查询

	[@flash_message /]

	$query.click(function()
				 	{
				 	 	 var starttime = $('#startTime').val();
				 		 var endtime = $('#endTime').val();
				 		 var nameOrMobile = $('#nameOrMobile').val();
				 	    
				 	    if(starttime=='' || endtime==''){  //如果开始时间或结束时间都为空结束时间和开始时间都设为今天
				 	     starttime = startTime(0)+" 00:00:00";
						 endtime = startTime(-1)+" 23:59:59";
						 $('#startTime').val( startTime(0));
						 $('#endTime').val( startTime(0));
						 $('#startDate').html(startTime(0));
						 $('#endDate').html(startTime(0));
				 	    }else{
				 	     $('#startTime').val(starttime);
						 $('#endTime').val(endtime);
						 $('#startDate').html(starttime);
						 $('#endDate').html(endtime);
				 	     starttime = starttime+" 00:00:00";
						 endtime = endtime+" 23:59:59";
				 	    }
				 		$('#nameOrMobile').val(nameOrMobile);
						workStatistics(starttime,endtime,nameOrMobile);//调用ajax方法
					}
				 );

	$(".fixedDate").click(function()
				 	{
						$(this).closest("tr").find("td").removeClass("bg279fff");
						$(this).closest("tr").find("td").removeClass("bgd5d5d5");
						$(this).closest("tr").find("td").addClass("bgd5d5d5");
						$(this).removeClass("bgd5d5d5");
						$(this).addClass("bg279fff");
						var data_day = $(this).attr('data_day');
						$('#startTime').val(startTime(data_day));
						 $('#endTime').val(startTime(-1));
						 $('#startDate').html(startTime(data_day));
						 $('#endDate').html(endtime);
						var starttime = startTime(data_day)+" 00:00:00";
						var endtime = startTime(-1)+" 23:59:59";
						var nameOrMobile = $('#nameOrMobile').val();
						workStatistics(starttime,endtime,nameOrMobile);//调用ajax方法
					}
				 );
	$(".selectDate").click(function()
				 	{
						$(this).closest("tr").find("td").removeClass("bg279fff");
						$(this).closest("tr").find("td").removeClass("bgd5d5d5");
						$(this).closest("tr").find("td").addClass("bgd5d5d5");
						$(this).removeClass("bgd5d5d5");
						$(this).addClass("bg279fff");
					}
				 );
	console.log($("#infoList").width());
	var infoLiNum = Math.floor(($("#infoList").width()-270)/265);
	var infoLiWidth = ($("#infoList").width()-270) / infoLiNum - 1;
	$("#infoList li").css({"width": infoLiWidth + "px"});
	
	
		$sort.change(function(){
				var selectValue = $(this).val(); 
				if(selectValue == 'quantityfalse'){
					$('#name').val('quantity');
					$('#fals').val('false');
				}
				if(selectValue == 'quantitytrue'){
					$('#name').val('quantity');
					$('#fals').val('true');
				}
				if(selectValue == 'pricefalse'){
					$('#name').val('price');
					$('#fals').val('false');
				}
				if(selectValue == 'pricetrue'){
					$('#name').val('price');
					$('#fals').val('true');
				}
				
				$listForm.submit();
	     }); 
	     
	        $('#startDate').html(startTime(-1));
		    $('#endDate').html(startTime(-1));
	     	var starttime = startTime(-1)+" 00:00:00";
			var endtime = startTime(-1)+" 23:59:59";
			var nameOrMobile = $('#nameOrMobile').val();
			workStatistics(starttime,endtime,nameOrMobile);//调用ajax方法
	     
})

		//使用ajax 查询机构内工作数据统计
		function workStatistics(startTime,endtime,nameOrMobile){
		
				$.ajax({
					url: "workStatistics.jhtml",
					type: "POST",
					data: {startTime:startTime,endTime :endtime,nameOrMobile:nameOrMobile},
					dataType: "json",
					cache: false,
					success: function(data) {
						$('#aboutCount').html(data.aboutCount);
						$('#serverCount').html(data.serverCount);
						$('#refundCount').html(data.refundCount);
						$('#refundPrice').html(data.refundPrice);
						$('#sumRecharge').html(data.sumRecharge);
						$('#consumptionPrice').html(data.consumptionPrice);
					}
				});
		
		}

		//获取开始时间
		function startTime(i){
		var dayTime=i*24*60*60*1000; //参数天数的时间戳
	    var nowTime=new Date().getTime(); //当天的时间戳
	    var mydate = new Date(nowTime+dayTime); //把两个时间戳转换成普通时间
	    var oDate = new Date(mydate),  
	    oYear = oDate.getFullYear(),  
	    oMonth = oDate.getMonth()+1,  
	    oDay = oDate.getDate(),  
	    oHour = oDate.getHours(),  
	    oMin = oDate.getMinutes(),  
	    oSen = oDate.getSeconds(),  
	    oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay);//最后拼接时间  
    	return oTime;  
		}


</script>
</head>
<body>
 <div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">机构内工作数据统计</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<form>
								<table cellpadding="0" cellspacing="0" border="0" align="right">
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td class="bg279fff plr20 pa5 z16ffffff bre3e3e3 cp fixedDate" data_day = '-1'>昨日</td>
													<td class="bgd5d5d5 plr20 pa5 z16ffffff bre3e3e3 cp fixedDate" data_day = '-8'>七日内</td>
													<td class="bgd5d5d5 plr20 pa5 z16ffffff cp fixedDate" data_day = '-31'>三十日内</td>
												</tr>
											</table>
										</td>
										<td width="10"></td>
										<td class="z16999999">
											<input type="text" placeholder="开始时间" id="startTime" name = "startTime" class="k_3 h30 bae1e1e1 w100 tac" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endTime\')}'});">
											 - 
											<input type="text" placeholder="结束时间" id="endTime" name = "endTime" class="k_3 h30 bae1e1e1 w100 tac" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'});"> 
											<input type="text" placeholder="用户姓名/电话" id = "nameOrMobile" class="k_3 h30 bae1e1e1 tac w150">
											<input type="button" value="查询" id = "query" class="bae1e1e1 z16ffffff bg279fff h30 w65">
										</td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="bb1dd4d4d4">
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z18279fff" colspan="2" align="left" height="70">
							统计周期：<span id = "startDate"></span>至<span id = "endDate"></span>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="btle3e3e3 bre3e3e3 bbe3e3e3 z16646464">
								<tr>
									<td width="165" align="center" height="50" class="bre3e3e3">预约次数</td>
									<td class="bre3e3e3 plr20"><a href="JavaScript:;" id = "aboutCount"></a>次</td>
									<td width="165" align="center" height="50" class="bre3e3e3">服务课节</td>
									<td class="plr20"><a href="JavaScript:;" id = "serverCount"></a>节</td>
								</tr>
								<tr>
									<td width="165" align="center" height="50" class="bre3e3e3 bge7f4ff">退款课节</td>
									<td class="bre3e3e3 bge7f4ff plr20"><a href="JavaScript:;" id = "refundCount"></a>节</td>
									<td width="165" align="center" height="50" class="bre3e3e3 bge7f4ff">退款金额</td>
									<td class="bge7f4ff plr20"><a href="JavaScript:;" id = "refundPrice"></a>元</td>
								</tr>
								<tr>
									<td width="165" align="center" height="50" class="bre3e3e3">充值金额</td>
									<td class="bre3e3e3 plr20"><a href="JavaScript:;" id = "sumRecharge"></a>元</td>
									<td width="165" align="center" height="50" class="bre3e3e3">消费金额</td>
									<td class="plr20"><a href="JavaScript:;" id = "consumptionPrice"></a>元</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<form id="listForm" action="list.jhtml" method="get">   
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mtb30">
	<input type = "hidden" id = "name" name = "name" value = "${name}" />
	<input type = "hidden" id = "fals" name = "fals" value = "${fals}" />
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z16444444" height="50">
							<table cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td>
											<input type="text" placeholder="${.now?string("yyyy-MM-dd")}" [#if date??] value="${date?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-%d'});" name="date" class="k_3 h30 bae1e1e1 tac z16999999 w125">
											<input type="submit" value="查询" class="bae1e1e1 z16ffffff bg279fff h30 w65">
									</td>
								</tr>
							</table>
						</td>
						<td align="right">
							排序：
							<select id = "sort" class="h34 bae1e1e1">
								<option value="0">默认排序</option>
								<option [#if name == 'quantity' && !fals ]selected[/#if] value="quantityfalse">课节升序</option>
								<option [#if name == 'quantity' && fals ]selected[/#if] value="quantitytrue">课节降序</option>
								<option [#if name == 'price' && !fals ]selected[/#if] value="pricefalse">收费升序</option>
								<option [#if name == 'price' && fals ]selected[/#if] value="pricetrue">收费降序</option>
							</select>
						</td>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="bae1e1e1 mt20" id="infoList">
				[#list doctor_list as doctor]
					<tr>
						<td width="270" class="bg279fff" valign="top">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
								<tr>
									<td colspan="2" align="center" class="z16ffffff bbe3e3e3 bte3e3e3" height="50">医生姓名：${doctor.doctorName}</td>
								</tr>
								<tr>
									<td colspan="2" height="7"></td>
								</tr>
								<tr>
									<td class="z14ffffff pl15 h20" width="50%">课节：${doctor.quantity}节</td>
									<td class="z14ffffff pr15 h20" width="50%">收费：${doctor.price}元</td>
								</tr>
								<tr>
									<td class="z14ffffff pl15 h26" width="50%">已退课节：${doctor.cancelledQuantity}节</td>
									<td class="z14ffffff pr15 h26 bb1dd4d4d4" width="50%">已退收费：${doctor.cancelledPrice}元</td>
								</tr>
								<tr>
									<td></td>
									<td class="z14ffffff pr15 h26">合计：${doctor.price - doctor.cancelledPrice}元</td>
								</tr>
								<tr>
									<td colspan="2" height="7"></td>
								</tr>
							</table>
						</td>
						<td valign="top">
							<ul>
							[#list doctor.workDayItem_list as workDayItem]
								<li class="bre3e3e3 bbe3e3e3">
									<table cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
										<tr>
											<td align="center" class="bge7f4ff" height="50" colspan="2">
												${workDayItem.startTime}-${workDayItem.endTime}
											</td>
										</tr>
										<tr>
											<td align="center" valign="middle" width="180" height="88" class="z18444444">
												<table cellpadding="0" cellspacing="0" border="0" width="170" align="right">
													<tr>
														<td nowrap><div class="w170 m0auto toeoh">${workDayItem.projectName}</div></td>
													</tr>
													<tr>
														<td align="left">${workDayItem.patientName}</td>
													</tr>
												</table>
											</td>
											<td class="z14999999" width="85">
												<table cellpadding="0" cellspacing="0" border="0" width="75" align="left">
													<tr>
														<td align="right">${workDayItem.time}分钟</td>
													</tr>
													<tr>
														<td align="right">${workDayItem.price}元</td>
													</tr>
													<tr>
														<td align="right" class="z14ff0000">退${workDayItem.refundsPrice}元</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</li>
							[/#list]	
							</ul>
						</td>
					</tr>
					[/#list]
				</table>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						 [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
								[#include "/mechanism/include/pagination.ftl"]
						 [/@pagination]
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>   
</div>
 
</body>
</html>