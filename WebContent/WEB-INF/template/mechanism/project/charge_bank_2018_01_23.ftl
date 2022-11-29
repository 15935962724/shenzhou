[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
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
	
	
	$(function(){
			
			
			var tab_tr_num = $("#cont_list").find("tr").length;
			var tab_td_num = $("#cont_list").find("tr").eq(0).find("td").length;

			var count = new Array(),total = 0;

			for(var i = 1; i < (tab_tr_num-1); i++ )
				{
					for(var j = 1; j < (tab_td_num-1); j++)
						{
							if(i == 1)
								{
									count[j-1] = $("#cont_list").find("tr").eq(i).find("td").eq(j).html();
								}
							else
								{
									count[j-1] =parseFloat(count[j-1]) + parseFloat($("#cont_list").find("tr").eq(i).find("td").eq(j).html());
								}
							total += parseFloat($("#cont_list").find("tr").eq(i).find("td").eq(j).html());
						}
				}
		
			var piedata = [], pieColor = [], pielabels = [];
		
			for(var i = 0; i < count.length; i++)
				{
					$("#cont_list").find("tr").eq(tab_tr_num-1).find("td").eq(i+1).html(count[i]);
					piedata.push(((count[i] / total) * 100).toFixed(2));
					pieColor.push(radom_color());
					pielabels.push($("#cont_list").find("tr").eq(0).find("td").eq(i+1).html().replace("<br><font class=\"z_12_999999_1\">(元)</font>",""));
				}
		
		
		
			var ctx = document.getElementById("mychart").getContext("2d");
            window.myPie = new Chart(ctx , {
				type: 'pie',
				data: {
					datasets: [{
						data: piedata,
						backgroundColor: pieColor,

					}],
					labels: pielabels
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
			});  	
		var parent_width = $(".detail").width();
		var tab_width = 120 + (tab_td_num - 1) * 100;
	
		$("#cont_table").css("width" , parent_width + "px");
		if(tab_width <= parent_width)
			{
				$("#cont_list").css("width" , parent_width + "px");
			}
		else
			{
				$("#cont_list").css("width" , tab_width + "px");		
			}
			
			
				var $listForm = $("#listForm");
				var $download = $("#download");
				
				[@flash_message /]
				
				//导出
				$download.click(function() {
				$listForm.attr('action','downloadCharge.jhtml');
				$listForm.submit();
				$listForm.attr('action','charge.jhtml');		
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
        	<div class="left_z">服务项目收费统计</div>
            <div class="export">
            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
            <a href="javascript:;" id="download"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>
            </div>
        </div>
        <form id="listForm" action="charge.jhtml" method="get">
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="left" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="left" width="70" valign="top">项目选择：</td>
                <td align="left" valign="top">
                	<ul>
	                	[#list serverCategories as serverCategorie]
                			<li class="item_li" title="${serverCategorie.name}"><input name="serverProjectCategoryIds" type="checkbox" value="${serverCategorie.id}" onclick="unselectall('chkAll');" checked /> ${serverCategorie.name} </li>
	                	[/#list]
                	</ul>
                <input name="chkAll" id="chkAll" type="checkbox" onclick="CheckAll('id','chkAll');" checked /> 全选
                </td>
              </tr>
              <tr>
               	<td height="45" colspan="2" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               	<td align="left" width="300">预约日期：
               	<input type="text" class="inp_1 w_70_1" name="createDate" [#if createDate??] value="${createDate?string("yyyy-MM")}" [/#if]  placeholder="请选择日期" onfocus="WdatePicker({dateFmt: 'yyyy-MM',maxDate:'%y-%M'});"> 
               	</td>
                <td height="40" class="pos_re_1"><input id="nameOrmoible" name="nameOrmoible" [#if nameOrmoible?? ] value="${nameOrmoible}" [/#if] type="text" placeholder="医师姓名/电话" class="scr_k w_200_1 m_t_5_l_1" /><input type="submit" name="button" id="button" value="搜索" class="scr_b m_t_5_l_1 b_4c98f6_1" /></td>
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
            <td height="20" align="left" valign="top">
			<div id="cont_table" style="overflow-x: auto;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list">
              <tr class="bg_f5f5f5_1">
                <td width="120" height="50" align="center">日期</td>
                [#list serverCategories as serverCategorie]
                 <td align="center">${serverCategorie.name}<br><font class="z_12_999999_1">(元)</font></td>
                [/#list]
                <td align="center">合计<br>
                <font class="z_12_999999_1">(元)</font></td>
              </tr>
              [#assign count_price =  0]
              [#list dates as data]
	              <tr [#if data_index%2==0]class="fff"[/#if]>
	                <td height="35" align="center">${data?string("yyyy-MM-dd")}</td>
	                 [#assign count_data_price =  0]
	                 [#list serverCategories as serverCategorie]
	                 [#assign id =  serverCategorie.id]
	                 [#assign count_today_price = projectAbout('${data?string("yyyy-MM-dd")}','${id}','${nameOrmoible}')]
	                 <td align="center">${count_today_price}</td>
	                 [#assign count_data_price =  count_today_price + count_data_price]
	                 [/#list]
	                <td align="center">${count_data_price}</td>
	                [#assign count_price =  count_data_price + count_price]
	              </tr>
              [/#list]
              <tr>
                <td height="35" align="center"> 合计</td>
                [#list serverCategories as serverCategorie]
	                  <td align="center"></td>
	            [/#list]
                <td align="center">${count_price}</td>
              </tr>            
             </table>
             </div>
            </td>
          </tr>
        </table>
        </div> 
    	</form>
<div class="clear"></div>
</body>
</html>