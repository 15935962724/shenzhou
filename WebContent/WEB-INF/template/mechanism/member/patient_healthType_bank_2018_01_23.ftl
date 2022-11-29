<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>

<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>

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
               "${message("Member.HealthType."+healthType)}",
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
    	<div class="nav">管理导航</div>
        <div class="seat">
        	<div class="left_z">患者状态统计</div>
            <div class="export">
	            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
	            [#if valid('export')]<a href="javascript:;" id= "download"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>[/#if]
            </div>
        </div>
        <form id="listForm" action="patient_healthType.jhtml" method="get">
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="left" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="left" width="70" valign="top">状态选择：</td>
                <td align="left" valign="top">
                	<ul>
                	[#list healthTypes as healthType]
                		<li class="item_li" title="${message("Member.HealthType."+healthType)}"><input name="healthTypes" type="checkbox" value="${healthType}" onclick="unselectall('chkAll');" checked /> ${message("Member.HealthType."+healthType)} </li>
           			[/#list]
                	</ul>
                <input name="chkAll" id="chkAll" type="checkbox" onclick="CheckAll('id','chkAll');" checked /> 全选
                </td>
              </tr>
              <tr>
               	<td height="45" colspan="2" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="40" class="pos_re_1">
	                <input id="nameOrmobile" name="nameOrmobile" [#if nameOrmobile??] value="${nameOrmobile}" [/#if] type="text" placeholder="联系人(患者)姓名/电话" class="scr_k w_200_1 m_t_5_l_1" />
	                <input type="submit" name="button" id="button" value="搜索" class="scr_b m_t_5_l_1 b_4c98f6_1" />
                </td>
               	<td align="left">
               	</td>
                
              </tr>
            </table></td>
              </tr>
            </table></td>
            </tr>
          <tr>
            <td align="left" valign="middle" style="position: relative;">
            	<div id="chart_pic" style="margin: 20px auto;width: 450px;">
					<canvas id="mychart"></canvas>
				</div>
				<div id="chartjs-tooltip">
					<table></table>
				</div>
            </td>
          </tr>
          <tr>
            <td height="20" align="left" valign="middle"></td>
          </tr>
          <tr>
            <td height="20" align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list">
              <tr class="bg_f5f5f5_1">
                <td height="50" align="center">患者姓名</td>
                <td align="center">联系人姓名<font class="z_12_999999_1">&nbsp;</font></td>
                <td align="center">建档时间<font class="z_12_999999_1">&nbsp;</font></td>
                <td align="center">最后消费时间<font class="z_12_999999_1">&nbsp;</font></td>
                <td align="center">服务课时</td>
                <td align="center">消费金额<br>
                <font class="z_12_999999_1">(元)</font></td>
                <td align="center">患者状态</td>
              </tr>
              [#list page.content as patient]
              <tr class="fff">
                <td height="50" align="center">${patient.name}</td>
                <td align="center">
                	${patient.parent.name}<br />
                	<font class="z_12_999999_1">${patient.parent.mobile}</font>
                </td>
                <td align="center">
                	${firstOrderDate(patient.id)}
                </td>
                <td align="center">
                	${lastDate(patient.id)?string("yyyy-MM-dd")} <br />
					<font class="z_12_999999_1">${lastDate(patient.id)?string("HH:mm:ss")}</font>
                </td>
                <td align="center">${coruseHour(patient.id)}</td>
                <td align="center">${countMoney(patient.id)}</td>
                <td align="center">${message("Member.HealthType."+patient.healthType)}</td>
              </tr>
              [/#list]        
             </table>
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="b_f_4c98f6_1 pos_r_1">
		                       	[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
									[#include "/mechanism/include/pagination.ftl"]
							   	[/@pagination]                
							</td>
						</tr>
			 </table>
            </td>
          </tr>
        </table>
        </div> 
    	</form>
<div class="clear"></div>

</body>
</html>