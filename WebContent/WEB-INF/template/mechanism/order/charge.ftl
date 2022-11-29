[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript">
	var randomScalingFactor_255 = function(){ return Math.round(Math.random()*255)};
	var radom_color = function(){
		return '#'+('00000'+(Math.random()*0x1000000<<0).toString(16)).slice(-6);
	}
	var chart_config_1 = {
		type : "line",
		data : {
			labels : [
				[#list dates as date]
					"${date?string('MM月dd日')}",
				[/#list]
			],
		datasets : [{
			fill: true,
			label: "已服务",
			borderColor: "rgba(3, 131, 255, 1)",
			backgroundColor: "rgba(3, 131, 255, 0.5)",
			pointBorderWidth: 1,
			lineTension: 0,	             //设置是否为直线，曲线为0.x
			data: [
			 [#list data_list as data]
				${data.count_accomplish},
			 [/#list]
			],
		}]
		},
		options: {
			responsive: true,
			title: {
				display: true,
				text: "预约状态统计",
				fontSize: 20,
				fontColor: "#4c98f6"
			},
			legend: {
				labels: {
					boxWidth: 12,
				}
			},			
		}
		
	}
	

	
	Chart.defaults.global.tooltips.custom = function(tooltip) {
		// Tooltip Element
		var tooltipEl = document.getElementById('chartjs-tooltip');

		// Hide if no tooltip
		if (tooltip.opacity === 0) {
			tooltipEl.style.opacity = 0;
			return;
		}

		// Set caret Position
		tooltipEl.classList.remove('above', 'below', 'no-transform');
		if (tooltip.yAlign) {
			tooltipEl.classList.add(tooltip.yAlign);
		} else {
			tooltipEl.classList.add('no-transform');
		}

		function getBody(bodyItem) {
			return bodyItem.lines;
		}

		// Set Text
		if (tooltip.body) {
			var titleLines = tooltip.title || [];
			var bodyLines = tooltip.body.map(getBody);

			var innerHtml = '<thead>';

			titleLines.forEach(function(title) {
				innerHtml += '<tr><th>' + title + '</th></tr>';
			});
			innerHtml += '</thead><tbody>';

			bodyLines.forEach(function(body, i) {
				var colors = tooltip.labelColors[i];
				var style = 'background:' + colors.backgroundColor;
				style += '; border-color:' + colors.borderColor;
				style += '; border-width: 2px'; 
				var span = '<span class="chartjs-tooltip-key" style="' + style + '"></span>';
				innerHtml += '<tr><td>' + span + body + '%</td></tr>';
			});
			innerHtml += '</tbody>';

			var tableRoot = tooltipEl.querySelector('table');
			tableRoot.innerHTML = innerHtml;
		}

		var position = this._chart.canvas.getBoundingClientRect();

		tooltipEl.style.opacity = 1;
		tooltipEl.style.left = position.left + tooltip.caretX + 'px';
		tooltipEl.style.top = tooltip.caretY + 'px';
		tooltipEl.style.fontFamily = tooltip._fontFamily;
		tooltipEl.style.fontSize = tooltip.fontSize;
		tooltipEl.style.fontStyle = tooltip._fontStyle;
		tooltipEl.style.padding = tooltip.yPadding + 'px ' + tooltip.xPadding + 'px';
	};
	var chart_config_2 = {
        type: 'pie',
        data: {
            datasets: [{
                data: [${accomplishB},${cancelledB},],
                backgroundColor: [
					radom_color(),radom_color(),
                ],

            }],
            labels: [
                "已服务",
                "已取消",
            ]
        },
        options: {
            responsive: true,
			title: {
				display:true,
				text: "预约服务对比图",
				fontSize: 20,
				fontColor: "#4c98f6"
			},
			legend: {
				position: 'right',
				labels: {
					boxWidth: 12,
				}
			},
			tooltips:{
				enabled: false,
			}

        }
    }
	
	$(function(){
			var chart_width = $('.m020').width()-100;
			$("#chart_pic_1").css("width",chart_width + "px");
			var ctx_1 = document.getElementById("mychart_1").getContext("2d");
            window.myLine = new Chart(ctx_1 , chart_config_1);  
			var ctx_2 = document.getElementById("mychart_2").getContext("2d");
            window.pie = new Chart(ctx_2 , chart_config_2);  
	  })
function Toggle_chart( num , count )
	{
		if ( num == 2 )
			{
				$("#chartjs-tooltip").css("display" , "block");
			}
		else
			{
				$("#chartjs-tooltip").css("display" , "none");
			}
		for(i = 1; i <= count; i++ )
			{
				if (i == num)
					{
						$("#t_" + i).attr("class" , "z14279fff");
						$("#chart_pic_" + i).css("display" , "block");						
					}
				else
					{
						$("#t_" + i).attr("class" , "z14717171");
						$("#chart_pic_" + i).css("display" , "none");
					}				
			}
	}
	
</script>
<style>
	#chartjs-tooltip {
		opacity: 1;
		position: absolute;
		background: rgba(0, 0, 0, .7);
		color: white;
		border-radius: 3px;
		-webkit-transition: all .1s ease;
		transition: all .1s ease;
		pointer-events: none;
		-webkit-transform: translate(-50%, 0);
		transform: translate(-50%, 0);
		display: none;
	}

	.chartjs-tooltip-key {
		display: inline-block;
		width: 10px;
		height: 10px;
		margin-right: 10px;
	}
</style>
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
			<form id="listForm" action="charge.jhtml" method="get">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">预约统计</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<!--<a href="javascript:;">导出</a>-->
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" align="left">
							<input type="text" placeholder="请选择月份" id = "createDate" name="createDate" [#if createDate??] value="${createDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-%d'});" class="k_3 h30 bae1e1e1 w100 tac">
							-
							<input type="text" placeholder="请选择月份" id = "endDate" name="endDate" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createDate\')}',  maxDate:'%y-%M-%d'});" class="k_3 h30 bae1e1e1 w100 tac">
							<input type="text" id="nameOrmoible" name="nameOrmoible" value="${nameOrmoible}" class="k_3 h30 bae1e1e1 tac w210" placeholder="请输入医师姓名/电话">
							<input type="submit" value="查询" class="bae1e1e1 z16ffffff bg279fff h30 w65">
						</td>
						<td align="right">
							<font class="z14279fff" id="t_1">
								<a href="javascript:;" onClick="Toggle_chart(1,2);">折线图</a>
							</font> 
							<font class="z14717171" id="t_2">
								<a href="javascript:;" onClick="Toggle_chart(2,2);">饼状图</a>
							</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="20"></td>
					</tr>
					<tr>
						<td colspan="2" class="ptb10 plr10 pr">
							<div id="chart_pic_1" style="margin: 20px auto;">
								<canvas id="mychart_1"></canvas>
							</div>
							<div id="chart_pic_2" style="margin: 20px auto; display: none;width: 450px;">
								<canvas id="mychart_2"></canvas>
							</div>
							<div id="chartjs-tooltip">
								<table></table>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="25%" height="50">
										日期
									</td>
									<td class="btle3e3e3" align="center" width="25%">
										已服务(次)<br>
										<span class="z12ffffff">
											${countAccomplish}
										</span>
									</td>
									<td class="btle3e3e3" align="center" width="25%">
										已取消(次)<br>
										<span class="z12ffffff">
											${countCancelled}
										</span>
									</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="25%">
										总计(次)<br>
										<span class="z12ffffff">
											${countAccomplish+countCancelled}
										</span>
									</td>
								</tr>
								[#list page.content as data]
								<tr [#if data_index%2==0]class="bge7f4ff"[/#if] >
									<td class="btle3e3e3" align="center" height="50">
										${data.date?string("yyyy-MM-dd")}
									</td>
									<td class="btle3e3e3" align="center">
										${data.count_accomplish}
									</td>
									<td class="btle3e3e3" align="center">
										${data.count_cancelled}
									</td>
									[#assign count = data.count_cancelled+data.count_accomplish]
									<td class="btle3e3e3 bre3e3e3" align="center">
										${count}
									</td>
								</tr>
								[/#list]
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