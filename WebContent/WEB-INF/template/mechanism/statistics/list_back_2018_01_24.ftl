<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>基本信息</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>

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
			borderColor: "rgba(54, 162, 235, 0.5)",
			backgroundColor: "rgba(1, 162, 235, 0.5)",
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
			var chart_width = $('.detail').width()-100;
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
    	<div class="nav">管理导航</div>
        <div class="seat">
        	<div class="left_z">收费月报</div>
            <div class="export">
	            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
	            [#if valid('export')]<a href="javascript:;" id = "download"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>[/#if]
            </div>
        </div>
        <form id="listForm" action="list.jhtml" method="get">
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td height="40" align="left" valign="middle">
	            <table width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	               	<td align="left" width="300">预约日期：<input type="text" name="createDate" class="inp_1 w_100_1" [#if createDate??] value="${createDate?string("yyyy-MM")}" [/#if] value="" onfocus="WdatePicker({dateFmt: 'yyyy-MM',maxDate:'%y-%M'});" placeholder="请选择日期"></td>
	                <td height="40" class="pos_re_1">
		                <input id="nameOrmobile" name="nameOrmobile" type="text" placeholder="医师姓名/电话" class="scr_k w_200_1 m_t_5_l_1" />
		                <input type="submit" name="button" id="button" value="搜索" class="scr_b m_t_5_l_1 b_4c98f6_1" />
	                </td>
	              </tr>
	            </table>
            </td>
          </tr>
          <tr>
            <td align="left" valign="middle">
            	<div id="chart_pic" style="margin: 20px auto;">
					<canvas id="mychart"></canvas>
            	</div>
            </td>
          </tr>
          <tr>
            <td height="20" align="left" valign="middle"></td>
          </tr>
          <tr>
            <td height="20" align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list">
              <tr class="bg_f5f5f5_1">
                <td width="120" height="50" align="center">日期</td>
                <td class="p_l_r_5" align="center">课节数<br><font class="z_12_999999_1">(节)</font></td>
                <td align="center">应收金额<br><font class="z_12_999999_1">(元)</font></td>
                <td align="center">减免金额<br><font class="z_12_999999_1">(元)</font></td>
                <td align="center">实收金额<br><font class="z_12_999999_1">(元)</font></td>
                <td align="center">患者数量<br><font class="z_12_999999_1">(人)</font></td>
                <td align="center">下单人数量<br><font class="z_12_999999_1">(人)</font></td>
                <td align="center">客单价<br><font class="z_12_999999_1">(元/人)</font></td>
              </tr>
              [#assign count_course = 0] 
              [#assign count_amount_payable = 0] 
              [#assign count_couponDiscount = 0] 
              [#assign count_price = 0] 
              [#assign count_prtient = 0]
              [#assign count_order = 0]
              [#assign count_ave_order_price = 0]
              [#list data_list as data]
              <tr [#if data_index%2==0]class="fff"[/#if]>
	                <td height="35" align="center">${data.date?string("yyyy-MM-dd")}</td>
	                <td align="center">${data.count_course}</td>
	                <td align="center">${currency(data.count_amount_payable, true)}</td>
	                <td align="center">${currency(data.count_couponDiscount, true)}</td>
	                <td align="center">${currency(data.count_price, true)}</td>
	                <td align="center">${data.count_prtient}</td>
	                <td align="center">${data.count_order}</td>
	                <td align="center">[#if data.count_order==0]0[#else]${currency(data.count_price/data.count_order,true)}[/#if]</td>
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
                <td height="35" align="center"> 合计</td>
                <td align="center">${count_course}</td>
                <td align="center">${currency(count_amount_payable,true)}</td>
                <td align="center">${currency(count_couponDiscount,true)}</td>
                <td align="center">${currency(count_price,true)}</td>
                <td align="center">${count_prtient}</td>
                <td align="center">${count_order}</td>
                <td align="center">${currency(count_ave_order_price/data_list.size(),true)}</td>
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