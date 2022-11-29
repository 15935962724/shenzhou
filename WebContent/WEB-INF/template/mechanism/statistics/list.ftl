<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>基本信息</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>

<script type="text/javascript">
	var chart_config = {
		type : "line",
		data : {
			labels : [
				[#list dates as date]
					"${date?string('MM月dd日')}",
				[/#list]
			 ],
		datasets : [{
			fill: true,
			label: "日实际收费金额(元)",
			borderColor: "rgba(3, 131, 255, 1)",
			backgroundColor: "rgba(3, 131, 255, 0.5)",
			pointBorderWidth: 1,
			lineTension: 0,	             //设置是否为直线，曲线为0.x
			data: [
			 [#list data_list as data]
				${data.count_price},
			 [/#list]
			],
			
		}]
		},
		options: {
			responsive: true,
			title: {
				display: true,
				text: "收费统计报表",
				fontSize: 20,
				fontColor: "#4c98f6"
			}
		}
		
	}
	$(function(){
			var chart_width = $('.m020').width()-100;
			$("#chart_pic").css("width",chart_width + "px");
			var ctx = document.getElementById("mychart").getContext("2d");
            window.myLine = new Chart(ctx , chart_config);  
            
			    var $listForm = $("#listForm");
				var $download = $("#download");
				
				[@flash_message /]
				
				//导出
				$download.click(function() {
					$listForm.attr('action','downloadList.jhtml');
					$listForm.submit();
					$listForm.attr('action','list.jhtml');		
				});
            
	  })
	

	
	
	
	
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
			<form id="listForm" action="list.jhtml" method="get">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">收费月报</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							[#if valid('export')]<a href="javascript:;" id = "download">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" colspan="2" align="right">
							<input type="text" placeholder="请选择月份" id = "createDate" name="createDate" [#if createDate??] value="${createDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-%d'});" class="k_3 h30 bae1e1e1 w100 tac">
							-
							<input type="text" placeholder="请选择月份" id = "endDate" name="endDate" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createDate\')}',  maxDate:'%y-%M-%d'});" class="k_3 h30 bae1e1e1 w100 tac">
							<input type="text" id="nameOrmobile" name="nameOrmobile" class="k_3 h30 bae1e1e1 tac w210" placeholder="请输入医师姓名/电话">
							<input type="submit" value="查询" class="bae1e1e1 z16ffffff bg279fff h30 w65">
						</td>
					</tr>
					<tr>
						<td colspan="2" height="20"></td>
					</tr>
					<tr>
						<td colspan="2" class="ptb10 plr10">
							<div id="chart_pic" class="m0auto">
								<canvas id="mychart"></canvas>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="120" height="50">
										日期
									</td>
									<td class="btle3e3e3" align="center">
										课节数<br>
										<span class="z12ffffff">
											(节)
										</span>
									</td>
									<td class="btle3e3e3" align="center">
										应收金额<br>
										<span class="z12ffffff">
											(元)
										</span>
									</td>
									<td class="btle3e3e3" align="center">
										减免金额<br>
										<span class="z12ffffff">
											(元)
										</span>
									</td>
									<td class="btle3e3e3" align="center">
										实收金额<br>
										<span class="z12ffffff">
											(元)
										</span>
									</td>
									<td class="btle3e3e3" align="center">
										患者数量<br>
										<span class="z12ffffff">
											(人)
										</span>
									</td>
									<td class="btle3e3e3" align="center">
										下单人数量<br>
										<span class="z12ffffff">
											(人)
										</span>
									</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										客单价<br>
										<span class="z12ffffff">
											(元/人)
										</span>
									</td>
								</tr>
								  [#assign count_course = 0] 
								  [#assign count_amount_payable = 0] 
								  [#assign count_couponDiscount = 0] 
								  [#assign count_price = 0] 
								  [#assign count_prtient = 0]
								  [#assign count_order = 0]
								  [#assign count_ave_order_price = 0]
								[#list page.content as data]
								<tr [#if data_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">
										${data.date?string("yyyy-MM-dd")}
									</td>
									<td class="btle3e3e3" align="center">
										${data.count_course}
									</td>
									<td class="btle3e3e3" align="center">
										${currency(data.count_amount_payable, true)}
									</td>
									<td class="btle3e3e3" align="center">
										${currency(data.count_couponDiscount, true)}
									</td>
									<td class="btle3e3e3" align="center">
										${currency(data.count_price, true)}
									</td>
									<td class="btle3e3e3" align="center">${data.count_prtient}</td>
									<td class="btle3e3e3" align="center">${data.count_order}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										[#if data.count_order==0]0[#else]${currency(data.count_price/data.count_order,true)}[/#if]
									</td>
								</tr>
								  [#assign count_course =  data.count_course + count_course]
						          [#assign count_amount_payable =  data.count_amount_payable + count_amount_payable] 
						          [#assign count_couponDiscount =  data.count_couponDiscount + count_couponDiscount] 
						          [#assign count_price =  data.count_price + count_price] 
						          [#assign count_prtient =  data.count_prtient + count_prtient] 
						          [#assign count_order =  data.count_order + count_order]
						          [#if data.count_order!=0][#assign count_ave_order_price =  (data.count_price/data.count_order) + count_ave_order_price][/#if]
								[/#list]
								<tr>
									<td class="btle3e3e3" align="center" height="50">
										合计
									</td>
									<td class="btle3e3e3" align="center">
										${count_course}
									</td>
									<td class="btle3e3e3" align="center">
										${currency(count_amount_payable,true)}
									</td>
									<td class="btle3e3e3" align="center">
										${currency(count_couponDiscount,true)}
									</td>
									<td class="btle3e3e3" align="center">
										${currency(count_price,true)}
									</td>
									<td class="btle3e3e3" align="center">${count_prtient}</td>
									<td class="btle3e3e3" align="center">${count_order}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										${currency(count_ave_order_price/page.getTotal(),true)}
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="bte3e3e3" height="10"></td>
					</tr>
					<tr>
						[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
							[#include "/mechanism/include/pagination.ftl"]
					  	[/@pagination]
					</tr>
				</table>
				</form>
			</td>
		</tr>
	</table>
</div>
</body>
</html>