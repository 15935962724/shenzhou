<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/iconfont.css" rel="stylesheet" type="text/css">
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script>

//隐藏框显示
function disp_hidden_d(d_id,d_width,d_height,p_id,id)
	{
		if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
			{
				var w=d_width / 2;
				var h=d_height / 2;
				$("."+d_id).css({"width":  d_width + "px","height": d_height + "px","left":"50%","top":($(document).scrollTop()+50) + "px","margin-left": "-" + w + "px","margin-bottom":"50px"});
				if (self.frameElement && self.frameElement.tagName == "IFRAME")
					{
						$("."+d_id).css({"top":$(parent.document).scrollTop()+50})
						if($(parent.document).height()<(d_height+$(parent.document).scrollTop()))
							$("#main_ifr",parent.document).height((parseInt(d_height)+$(parent.document).scrollTop()+150)+"px");
					}
				
			}else{
				ifr_height("main_ifr");
			}
		$("#"+p_id).val(id);
		$("#"+d_id).toggle();	
	}
	
		//上传(康护记录)预览
		function update_introduce_img(upfile,upimg,pid,flag)
		{
			$("#" + upfile + "_" + pid).click();
			$("#" + upfile + "_" + pid).on("change",function(){
			var	url=getObjectURL(this.files[0]);
		    console.log(url);
				if (url) 
					{
						if(upimg!="")
						{
						if(flag=="1")
							{
								$("#" + upimg).attr("src", url) ; 
							}
						if(flag=="2")
							{
								tmpstr="<div class=\"isImg\"><img src=\"" + url + "\" /><button id=\"" + upimg + "_b_" + pid + "\" class=\"removeBtn\" onclick=\"javascript:removeImg('"+ upimg +"','"+ upfile +"',"+ pid +")\">x</button></div>"
								$("." + upimg).append(tmpstr);
								pid=pid+1;
								if(pid == 3){
								$('#up_img_div').hide();
								}
								$("#" + upimg).append("<input id=\"" + upfile + "_" + pid + "\" name=\"" + upfile + "[" + pid + "].file" +"\" type=\"file\" style=\"display: none\"  />");
								$("#up_" + upimg).attr('onclick','update_introduce_img("' + upfile + '","' + upimg + '",' + pid +',"' + flag +'")');
								upimg="";
							}
						}
					}
			});
		}

		//删除(康护记录)预览图片
		function removeImg(upimg,upfile,pid)
		{
			var count=$("#" + upimg).children("input").size()-1;
			$("#" + upimg + "_b_" + pid).parent().remove();
			$("#" + upfile + "_" + pid).remove();
			$("#up_" + upimg).attr('onclick','update_introduce_img("' + upfile +'","' + upimg + '",'+ (count-1) +',"2")');
			if(pid<3){
			$('#up_img_div').show();
			}
			for(var i=(pid+1);i<=count;i++)
				{
					var tmp_pid=i-1;
					$("#" + upfile + "_" + i).attr({'name':upfile + '[' + tmp_pid+'].file','id':upfile + '_' + tmp_pid});
					$("#" + upimg + "_b_" + i).attr({'onclick':'removeImg("' + upimg + '","' + upfile +'",'+ tmp_pid + ')','id':upimg + '_b_' + tmp_pid});
				}
		}


		//上传(评估报告)预览
		function update_assess_img(upfile,upimg,pid,flag)
		{
			$("#" + upfile + "_" + pid).click();
			$("#" + upfile + "_" + pid).on("change",function(){
			var	url=getObjectURL(this.files[0]);
		    console.log(url);
				if (url) 
					{
						if(upimg!="")
						{
						if(flag=="1")
							{
								$("#" + upimg).attr("src", url) ; 
							}
						if(flag=="2")
							{
								tmpstr="<div class=\"isImg\"><img src=\"" + url + "\" /><button id=\"" + upimg + "_b_" + pid + "\" class=\"removeBtn\" onclick=\"javascript:removeAssessImg('"+ upimg +"','"+ upfile +"',"+ pid +")\">x</button></div>"
								$("." + upimg).append(tmpstr);
								pid=pid+1;
								if(pid == 3){
								$('#up_img_div_file').hide();
								}
								$("#" + upimg).append("<input id=\"" + upfile + "_" + pid + "\" name=\"" + upfile + "[" + pid + "].file" +"\" type=\"file\" style=\"display: none\"  />");
								$("#up_" + upimg).attr('onclick','update_assess_img("' + upfile + '","' + upimg + '",' + pid +',"' + flag +'")');
								upimg="";
							}
						}
					}
			});
		}

		//删除(评估报告)预览图片
		function removeAssessImg(upimg,upfile,pid)
		{
			var count=$("#" + upimg).children("input").size()-1;
			$("#" + upimg + "_b_" + pid).parent().remove();
			$("#" + upfile + "_" + pid).remove();
			$("#up_" + upimg).attr('onclick','update_assess_img("' + upfile +'","' + upimg + '",'+ (count-1) +',"2")');
			if(pid<3){
			$('#up_img_div_file').show();
			}
			
			for(var i=(pid+1);i<=count;i++)
				{
					var tmp_pid=i-1;
					$("#" + upfile + "_" + i).attr({'name':upfile + '[' + tmp_pid+"].file",'id':upfile + '_' + tmp_pid});
					$("#" + upimg + "_b_" + i).attr({'onclick':'removeAssessImg("' + upimg + '","' + upfile +'",'+ tmp_pid + ')','id':upimg + '_b_' + tmp_pid});
				}
		}


</script>
<style>
	#flag_1{padding: 0px;left: 130px;top: 0px; position: absolute; display: none;}
</style>
</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
		</tr>
	</table>
	
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2" style="margin-bottom: 20px;">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">患者明细</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<!--<a href="javascript:;">导出</a>-->
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					
					[#include "/mechanism/include/patient_view.ftl"]
					
				</table>
			</td>
		</tr>
	</table>
	<form id = "listForm" action = "patient_order.jhtml" method = "get">
	<input type = "hidden" name = "patientMemberId" value = "${patientMember.id}"/>
	[#list page.content as order]
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mtb20">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" >
					<tr>
						<td class="bb1dd4d4d4 pr" height="50" colspan="2"><span class="z162890f4">[#if order.workDayItem??] ${order.workDayItem.workDay.workDayDate?string("yyyy-MM-dd")}[/#if]</span>
						<div class="ribbon_k"><div class="ribbon_z ribbon5ad857">${message("Order.OrderStatus."+order.orderStatus)}</div></div>
						</td>
					</tr>
					<tr>
						<td height="10" colspan="2"></td>
					</tr>
					<tr>
						<td class="iconfont z16646464" height="30">
							&#xe601; 
							[#if order.workDayItem??] ${order.workDayItem.startTime}-${order.workDayItem.endTime} [#else] 00:00 - 00:00[/#if]
							[#if order.serveType=="assess"]
							<span class="pgbutton bgfa7600">评 估</span>
							[#else] 
							<span style="display: inline-block; background: #eeca00;color: #ffffff;font-size: 12px; width: 50px; height: 15px; border-radius: 10px; text-align: center;line-height: 15px;">${message("Project.ServeType."+order.serveType)}</span>
							[/#if] 
							 
						</td>
						<td rowspan="3" width="260" valign="bottom" align="right">
							[#if order.orderStatus == "confirmed" || order.orderStatus == "completed" ||  order.orderStatus == "record" ]
								[#if order.serveType=="assess"]
									[#if !order.assessReport]
									<input type="button" value="添加评估结果" class="bgfafafa bae1e1e1 z12575757 plr20 ptb10 mlr20" onclick="disp_hidden_d('assess',600,610,'assessReportOrderId',${order.id})">
									[/#if]
								[#else] 
									<input type="button" value="添加诊治结果" class="bgfafafa bae1e1e1 z12575757 plr20 ptb10 mlr20" onClick="disp_hidden_d('record',600,450,'orderId',${order.id})">
								[/#if] 
							[/#if]
						</td>
					</tr>
					<tr>
						<td height="30" class="z18444444">
							${order.orderItems[0].name}
						</td>
					</tr>
					<tr>
						<td height="30" class="z16646464">
							${order.doctor.name} ${order.doctor.mobile}
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	[/#list]
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="mtb20">
		<tr>
			[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
				[#include "/mechanism/include/pagination.ftl"]
			[/@pagination] 
		</tr>
	</table>
	</form>
</div>

   					
<div id="assess">
	<div class="assess">
  		<form id="myform" action="${base}/mechanism/assessReport/assessReportSave.jhtml" method="post" enctype="multipart/form-data">
   		<input type="hidden" name="patientMemberId" value="${patientMember.id}" />
		<input type="hidden" id="assessReportOrderId" name="orderId" />
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					添加评估报告
  				</td>
   			</tr>
			<tr>
				<td height="50" class="bbe3e3e3 z14323232" align="right">评估时间</td>
				<td align="right" class="bbe3e3e3 pr65"><input type="text" name="createDate" id="createDate" value="${.now?string("yyyy-MM-dd")}" class="baffffff tar z14444444 w100"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});"/></td><!--点击打开日历组件，可选择日期，默认为康复日期-->
   			</tr>
  			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					病患名称
   				</td>
   				<td>
   					<input type="text" name="diseaseName" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020">
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					现况说明
   				</td>
   				<td>
   					<textarea name="nowExplain" class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					评估结果
   				</td>
   				<td>
   					<textarea name="assessResult" class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					康复建议
   				</td>
   				<td>
   					<textarea name="proposal" class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					图片上传
   				</td>
   				<td class="plr20">
	   				<div class="img_div_file">
					</div>
   					<img id="up_img_div_file" width="80" height="80" class="u_img_1" src="${base}/resources/newmechanism/images/k.png" onclick="update_assess_img('assessReportImages','img_div_file',0,'2')" >
	   				<div id="img_div_file">
						<input id="assessReportImages_0" name="assessReportImages[0].file" type="file" style="display: none"  />
					</div>
   				</td>
   			</tr>
   			<tr>
   				<td align="center" colspan="2" height="60">
   					<input type="submit" value="确认" class="button_3">
   					&nbsp;　&nbsp;
					<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#myform')[0].reset();disp_hidden_d('assess','','','p_id');">
  					<input type="hidden" id="p_id" name="p_id" value="">
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>
<div id="record">
	<div class="record">
  		<form id="recordform" action="${base}/mechanism/recoveryRecord/saveRecoveryRecord.jhtml" method="post" enctype="multipart/form-data">
   		<input type="hidden" name="patientMemberId" value="${patientMember.id}" />
		<input type="hidden" id = "orderId" name="orderId"  />
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					添加康复记录
  				</td>
   			</tr>
   			<tr>
				<td height="50" class="bbe3e3e3 z14323232" align="right">康复时间</td>
				<td align="right" class="bbe3e3e3 pr65"><input type="text" name="recoveryData" id="recoveryData" value="${.now?string("yyyy-MM-dd")}" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});" class="baffffff tar z14444444 w100" readonly></td><!--点击打开日历组件，可选择日期，默认为康复日期-->
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					康复内容
   				</td>
   				<td>
   					<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020" name="recoveryContent"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					疗效总结
   				</td>
   				<td>
   					<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020" name="effect"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					图片上传
   				</td>
   				<td class="plr20">
					<div class="img_div"></div>
					<img id="up_img_div" src="${base}/resources/mechanism/images/j_i_1.png" alt="" width="80" height="80" class="u_img_1" onclick="update_introduce_img('recoveryRecordImages','img_div',0,'2')">
					<div id="img_div">
						<input id="recoveryRecordImages_0" name="recoveryRecordImages[0].file" type="file" style="display: none"/>
					</div>
   					
   				</td>
   			</tr>
   			<tr>
   				<td align="center" colspan="2" height="60">
   					<input type="submit" value="确认" class="button_3">
   					&nbsp;　&nbsp;
					<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#recordform')[0].reset();disp_hidden_d('record','','','r_id');">
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>


</body>
</html>