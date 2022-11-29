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
					pielabels.push($("#cont_list").find("tr").eq(0).find("td").eq(i+1).html().replace("<br><span class=\"z12ffffff\">(元)</span>",""));
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
		var parent_width = $(".m020").width();
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
						<td class="z20616161 bb1dd4d4d4" height="50">项目统计</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							[#if valid('export')]<a href="javascript:;"  id="download">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2" class="bgf5fafe plr10 ptb10">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  <tr>
								<td align="left" width="70" valign="top">项目选择：</td>
								<td align="left" valign="top">
									<ul>
									[#list serverCategories as serverCategorie]
			                			<li class="item_li" title="${serverCategorie.name}"><input name="serverProjectCategoryIds" type="checkbox" value="${serverCategorie.id}" onclick="unselectall('chkAll');" [#if serverProjectCategories?seq_contains(serverCategorie) ] checked [/#if]  /> ${serverCategorie.name} </li>
				                	[/#list]
									</ul>
								<input name="chkAll" id="chkAll"  onclick="CheckAll('serverProjectCategoryIds','chkAll');"  type="checkbox" checked /> 全选
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
							<input type="text" placeholder="请选择月份" id = "createDate" name="createDate" [#if createDate??] value="${createDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-%d'});" class="k_3 h30 bae1e1e1 w100 tac">
							-
							<input type="text" placeholder="请选择月份" id = "endDate" name="endDate" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createDate\')}',  maxDate:'%y-%M-%d'});" class="k_3 h30 bae1e1e1 w100 tac">
							<input type="text" id="nameOrmoible" name="nameOrmoible" [#if nameOrmoible?? ] value="${nameOrmoible}" [/#if] class="k_3 h30 bae1e1e1 tac w210" placeholder="请输入医师姓名/电话">
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
									<td class="btle3e3e3" align="center" width="120" height="50">
										日期
									</td>
									 [#list serverCategories as serverCategorie]
									<td class="btle3e3e3" align="center">${serverCategorie.name}<br><span class="z12ffffff">(元)</span></td>
									[/#list]
									<td class="btle3e3e3 bre3e3e3" align="center">
										合计<br>
										<span class="z12ffffff">
											(元)
										</span>
									</td>
								</tr>
								[#assign count_price =  0]
								[#list page.content as data]
								<tr [#if data_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">
										${data?string("yyyy-MM-dd")}
									</td>
									[#assign count_data_price =  0]
									[#list serverCategories as serverCategorie]
										[#assign id =  serverCategorie.id]
		                 				[#assign count_today_price = projectAbout('${data?string("yyyy-MM-dd")}','${id}','${nameOrmoible}')]
									<td class="btle3e3e3" align="center">
										${count_today_price}
									</td>
									[#assign count_data_price =  count_today_price + count_data_price]
									[/#list]
									<td class="btle3e3e3 bre3e3e3" align="center">
										${count_data_price}
									</td>
									[#assign count_price =  count_data_price + count_price]
								</tr>
								 [/#list]
								<tr>
									<td class="btle3e3e3" align="center" height="50">
										合计
									</td>
									 [#list serverCategories as serverCategorie]
						                  <td class="btle3e3e3" align="center">
										
									      </td>
						             [/#list]
									<td class="btle3e3e3 bre3e3e3" align="center">
										${count_price}
									</td>
								</tr>
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
				</table>
				</form>
			</td>
		</tr>
	</table>
</div>
</body>
</html>