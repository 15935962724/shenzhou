<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>

<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>


<script type="text/javascript">

	var radom_color = function(){
		return '#'+('00000'+(Math.random()*0x1000000<<0).toString(16)).slice(-6);
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

		// Display, position, and set styles for font
		tooltipEl.style.opacity = 1;
		tooltipEl.style.left = position.left + tooltip.caretX + 'px';
		//tooltipEl.style.top = position.top + tooltip.caretY + 'px';
		tooltipEl.style.top = tooltip.caretY + 'px';
		tooltipEl.style.fontFamily = tooltip._fontFamily;
		tooltipEl.style.fontSize = tooltip.fontSize;
		tooltipEl.style.fontStyle = tooltip._fontStyle;
		tooltipEl.style.padding = tooltip.yPadding + 'px ' + tooltip.xPadding + 'px';
	};

	
	var chart_config = {
        type: 'pie',
        data: {
            datasets: [{
                data: [
                [#list bfb_list as bfb]
                  ${bfb},
                [/#list]
                
                  ],
                backgroundColor: [
				[#list healthTypes as healthType]
	               radom_color(),
	            [/#list]
                ],

            }],
            labels: [
            [#list healthTypes as healthType]
               "${message("PatientMechanism.HealthType."+healthType)}",
            [/#list]
            ]
        },
        options: {
            responsive: true,
			title: {
				display:true,
				text: "项目收费对比图",
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
			var ctx = document.getElementById("mychart").getContext("2d");
            window.myLine = new Chart(ctx , chart_config);  
            
            var $listForm = $("#listForm");
			var $download = $("#download");
			
			[@flash_message /]
			
			//导出
			$download.click(function() {
			$listForm.attr('action','downloadPatientHealthType.jhtml');
			$listForm.submit();
			$listForm.attr('action','patient_healthType.jhtml');		
			});
            
	  })
	
	
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
	}

	.chartjs-tooltip-key {
		display: inline-block;
		width: 10px;
		height: 10px;
		margin-right: 10px;
	}
	.item_li{width: 150px; height: 25px; overflow:hidden; margin-right: 20px;white-space: nowrap;} 
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
			<form id="listForm" action="patient_healthType.jhtml" method="get">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">患者状态统计</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							[#if valid('export')]<a href="javascript:;" id= "download">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2" class="bgf5fafe plr10 ptb10">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  <tr>
								<td align="left" width="70" valign="top">状态选择：</td>
								<td align="left" valign="top">
									<ul>
									[#list types as healthType]
										<li class="item_li" title="${message("PatientMechanism.HealthType."+healthType)}"><input name="healthTypes" type="checkbox" value="${healthType}" onclick="unselectall('chkAll');" [#if healthTypes?seq_contains(healthType)] checked="checked" [/#if] /> ${message("PatientMechanism.HealthType."+healthType)} </li>
									[/#list]
									</ul>
								<input name="chkAll" id="chkAll" type="checkbox" onclick="CheckAll('healthTypes','chkAll');" checked /> 全选
								</td>
							  </tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" colspan="2" align="right">
							<input type="text" id="nameOrmobile" name="nameOrmobile" [#if nameOrmobile??] value="${nameOrmobile}" [/#if] class="k_3 h30 bae1e1e1 tac w210" placeholder="请输入联系人(患者)姓名/电话">
							<input type="submit" value="查询" class="bae1e1e1 z16ffffff bg279fff h30 w65">
						</td>
					</tr>
					<tr>
						<td colspan="2" height="20"></td>
					</tr>
					<tr>
						<td colspan="2" class="ptb10 plr10 pr">
							<div id="chart_pic" style="margin: 20px auto;width: 450px;">
								<canvas id="mychart"></canvas>
							</div>
							<div id="chartjs-tooltip">
								<table></table>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div id="cont_table">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cont_list">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="120" height="50">患者姓名</td>
									<td class="btle3e3e3" align="center">联系人姓名</td>
									<td class="btle3e3e3" align="center">建档时间</td>
									<td class="btle3e3e3" align="center">最后消费时间</td>
									<td class="btle3e3e3" align="center">服务课时</td>							
									<td class="btle3e3e3" align="center">消费金额<span class="z12ffffff">(元)</span></td>
									<td class="btle3e3e3 bre3e3e3" align="center">患者状态</td>
								</tr>
								[#list page.content as patientMechanism]
								<tr [#if patientMechanism_index%2==0] class="bge7f4ff" [/#if]>
									<td class="btle3e3e3" align="center" height="50">${patientMechanism.patient.name}</td>
									<td class="btle3e3e3" align="center">
										${patientMechanism.patient.parent.name}
										<span class="z12999999">
											${patientMechanism.patient.parent.mobile}
										</span>
									</td>
									<td class="btle3e3e3" align="center">${firstOrderDate(patientMechanism.patient.id)}</td>
									<td class="btle3e3e3" align="center">
										${lastDate(patientMechanism.patient.id)?string("yyyy-MM-dd")}
										<span class="z12999999">
											${lastDate(patientMechanism.patient.id)?string("HH:mm:ss")}
										</span>
									</td>
									<td class="btle3e3e3" align="center">${coruseHour(patientMechanism.patient.id)}</td>
									<td class="btle3e3e3" align="center">${countMoney(patientMechanism.patient.id)}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">${message("PatientMechanism.HealthType."+patientMechanism.healthType)}</td>
								</tr>
								[/#list]     
							</table>
							</div>
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
					<tr>
						<td colspan="2"></td>
					</tr>
				</table>
				</form>
			</td>
		</tr>
	</table>
</div>
</body>
</html>