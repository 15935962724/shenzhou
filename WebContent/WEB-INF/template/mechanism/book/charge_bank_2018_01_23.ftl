[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
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
			borderColor: "rgba(54, 162, 235, 0.5)",
			backgroundColor: "rgba(1, 162, 235, 0.5)",
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
				text: "服务项目统计",
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
				text: "服务项目对比图",
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
			var chart_width = $('.detail').width()-100;
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
						$("#t_" + i).attr("class" , "z_14_4c98f6_1");
						$("#chart_pic_" + i).css("display" , "block");						
					}
				else
					{
						$("#t_" + i).attr("class" , "z_14_999999_1");
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

    	<div class="nav">管理导航</div>
        <div class="seat">
        	<div class="left_z">预约统计</div>
            <div class="export">
            <!--
            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
            <a href="javascript:;"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>
          	-->
          	</div>
        </div>
        <form id="listForm" action="charge.jhtml" method="get">
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td height="40" align="left" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               	<td height="40" align="left">统计日期：
               	  <input type="text" class="inp_1 w_70_1" name="createDate" [#if createDate??] value="${createDate?string("yyyy-MM")}" [/#if]  placeholder="请选择日期" onfocus="WdatePicker({dateFmt: 'yyyy-MM',maxDate:'%y-%M'});"> 
               	  <input id="nameOrmoible" name="nameOrmoible" type="text" placeholder="医师姓名/电话" class="scr_k" style="position: static;" /><input type="submit" name="button" id="button" value="搜索" class="button_4" /></td>
               	<td align="right">
               	  <font class="z_14_4c98f6_1" id="t_1"><a href="javascript:;" onClick="Toggle_chart(1,2);">折线图</a></font> <font class="z_14_999999_1" id="t_2"><a href="javascript:;" onClick="Toggle_chart(2,2);">饼状图</a></font>
               	</td>
              </tr>
            </table></td>
            </tr>
          <tr>
            <td align="left" valign="middle" class="pos_re_1">
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
            <td height="20" align="left" valign="middle"></td>
          </tr>
          <tr>
            <td height="20" align="left" valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list">
              <tr class="bg_f5f5f5_1">
                <td width="25%" height="50" align="center">日期</td>
                <td width="25%" align="center" class="p_l_r_5">已服务<font class="z_12_999999_1">(次)</font></td>
                <td width="25%" align="center">已取消<font class="z_12_999999_1">(次)</font></td>
                <!--<td width="20%" align="center">失约<font class="z_12_999999_1">(次)</font></td>-->
                <td width="25%" align="center">总计<font class="z_12_999999_1">(次)</font></td>
              </tr>
              [#assign count_accomplish = 0] 
              [#assign count_cancelled = 0] 
              [#assign count_second = 0]
              [#list data_list as data]
              <tr [#if data_index%2==0]class="fff"[/#if] >
                <td height="35" align="center">${data.date?string("yyyy-MM-dd")}</td>
                <td align="center">${data.count_accomplish}</td>
                <td align="center">${data.count_cancelled}</td>
                <!--<td align="center">暂无</td>-->
                [#assign count = data.count_cancelled+data.count_accomplish] 
                <td align="center">${count}</td>
              </tr>
               [#assign count_accomplish =  data.count_accomplish + count_accomplish]
               [#assign count_cancelled =  data.count_cancelled + count_cancelled]
               [#assign count_second =  count + count_second]
              [/#list]
              <tr>
                <td height="35" align="center"> 合计</td>
                <td align="center">${count_accomplish} </td>
                <td align="center">${count_cancelled}</td>
                <td align="center">${count_second}</td>
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