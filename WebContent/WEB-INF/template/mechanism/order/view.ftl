<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript">


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
</head>
<body>

<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mtb30">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">预约查询</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="javascript:;">导出</a>
							&nbsp;&nbsp;
							<a href="javascript:;">返回</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr class="bg279fff z14ffffff">
						<td colspan="2" class="btle3e3e3 plr20" align="left" height="50">订单编号：${order.sn}</td>
					</tr>
					<tr>
						<td colspan="2" class="btle3e3e3 bbe3e3e3 bre3e3e3 pa20">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td align="center" valign="middle" class="z36279fff" width="150">
										${message("Order.PaymentStatus."+order.paymentStatus)}
									</td>
									<td width="1" bgcolor="#e3e3e3"></td>
									<td>
										<table cellpadding="0" cellspacing="0" border="0" width="100%" align="right">
											<tr>
												<td width="20%" class="pr">
													<div class="w40 h40 br40 z14ffffff pa5 bg279fff tac ba279fff m0auto zindex10 ">提交订单</div>
													<hr class="hr2dashed279fff pa zindex1">
												</td>
												<td width="20%" class="pr">
													<div class="pa w40 h40 br40 pa5 [#if order.paymentStatus == "unpaid"] z14279fff bge6f4ff [#else] z14ffffff bg279fff [/#if] baafd4f0 tac m0auto zindex10 fkwz">付款完成</div>
													<hr class="[#if order.paymentStatus == "unpaid"] hr2dashedd3d3d3[#else] hr2dashed279fff[/#if] pa zindex1">
												</td>
												<td width="20%" class="pr">
													<div class="pa w40 h40 br40 z14279fff pa5 bge6f4ff baafd4f0 tac m0auto zindex10 fkwz">诊治评估</div>
													<hr class="hr2dashedd3d3d3 pa zindex1">
												</td>
												<td width="20%" class="pr">
													<div class="pa w40 h40 br40 z14279fff pa5 bge6f4ff baafd4f0 tac m0auto zindex10 fkwz">评价打分</div>
													<hr class="hr2dashedd3d3d3 pa zindex1">
												</td>
												<td width="20%" class="pr">
													<div style="line-height: 40px;" class="pa w40 h40 br40 z14279fff pa5 bge6f4ff baafd4f0 tac m0auto zindex10 fkwz">完成</div>
												</td>
											</tr>
											<tr>
												<td align="center" height="50">
													<span class="z14279fff">${order.createDate?string("yyyy-MM-dd")}</span><br>
													<span class="z126fbfff">${order.createDate?string("HH:mm:ss")}</span>
												</td>
												<td align="center">
													<span class="z14279fff">[#if order.paymentStatus != "unpaid"]${order.paidDate?string("yyyy-MM-dd")}[/#if]</span><br>
													<span class="z126fbfff">[#if order.paymentStatus != "unpaid"]${order.paidDate?string("HH:mm:ss")}[/#if]</span>
												</td>
												<td align="center">
													<span class="z14279fff"></span><br>
													<span class="z126fbfff"></span>
												</td>
												<td align="center">
													<span class="z14279fff"></span><br>
													<span class="z126fbfff"></span>
												</td>
												<td align="center">
													<span class="z14279fff"></span><br>
													<span class="z126fbfff"></span>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mtb30">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z16279fff" height="50">订单信息</td>
					</tr>
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z14444444">
								<tr>
									<td rowspan="4" width="155" class="btle3e3e3" align="center" valign="middle"><img src="${base}/resources/newmechanism/images/temp.png" class="img145175"></td>
									<td width="90" align="center" class="btle3e3e3">下单用户</td>
									<td class="btle3e3e3 bre3e3e3 plr10 z14717171">${order.member.name}</td>
								</tr>
								<tr>
									<td align="center" class="btle3e3e3 bgf2f9ff">患者姓名</td>
									<td class="btle3e3e3 bre3e3e3 bgf2f9ff plr10 z14717171">${order.patientMember.name} ${age(order.patientMember.birth)}周岁 ${message("Member.Gender."+order.patientMember.gender)}</td>
								</tr>
								<tr>
									<td align="center" class="btle3e3e3">服务时间</td>
									<td class="btle3e3e3 bre3e3e3 plr10 z14717171">[#if order.workDayItem??] ${order.workDayItem.workDay.workDayDate?string("yyyy-MM-dd")} ${order.workDayItem.startTime}-${order.workDayItem.endTime}[#else] - [/#if]</td>
								</tr>
								<tr>
									<td align="center" class="btle3e3e3 bgf2f9ff">服务方式</td>
									<td class="btle3e3e3 bre3e3e3 bgf2f9ff plr10 z14717171">${message("Project.Mode."+order.mode)}</td>
								</tr>
								<tr>
									<td align="center" height="45" class="btle3e3e3">服务地址</td>
									<td colspan="2" class="btle3e3e3 bre3e3e3 plr10 z14717171">${order.mechanism.area.fullName}${order.mechanism.address}</td>
								</tr>
								<tr>
									<td align="center" height="45" class="btle3e3e3 bbe3e3e3 bgf2f9ff">操　　作</td>
									<td colspan="2" class="btle3e3e3 bre3e3e3 bbe3e3e3 plr10 z14717171 bgf2f9ff">
									    [#if order.orderStatus == "record"]
									    [/#if]
										    [#if order.serveType == "examine"]
										    	<input type="button" value="诊治" class="bae1e1e1 w40 z12ffffff bg279fff" onClick="disp_hidden_d('record',600,450,'r_id',1)">
										    [#else]
										    	<input type="button" value="评估" class="bae1e1e1 w40 z12ffffff bgff8001" onClick="disp_hidden_d('assess',600,610,'p_id',1)">
										    [/#if]
										<!--测试<input type="button" value="评估" class="bae1e1e1 w40 z12ffffff bgff8001" onClick="disp_hidden_d('assess',600,610,'p_id',1)">-->
										<input type="button" value="回访" class="bae1e1e1 w40 z12ffffff bg32d3ea" onClick="disp_hidden_d('visit',600,450,'v_id',1);">
									</td>
								</tr>
							</table>
                        </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mtb30">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z16279fff" height="50">付款信息</td>
					</tr>
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z14444444">
								<tr>
									<td width="155" class="btle3e3e3" height="45" align="center">付款方式</td>
									<td class="plr10 btle3e3e3 bre3e3e3 z14717171">${order.paymentMethodName}</td>
								</tr>
								<tr>
									<td width="155" class="btle3e3e3 bgf2f9ff" height="45" align="center">付款时间</td>
									<td class="plr10 btle3e3e3 bre3e3e3 z14717171 bgf2f9ff">[#if order.paidDate??]${order.paidDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]</td>
								</tr>
								<tr>
									<td width="155" class="btle3e3e3" height="45" align="center">应付总额</td>
									<td class="plr10 btle3e3e3 bre3e3e3 z14717171">${currency(order.price, true)}</td>
								</tr>
								<tr>
									<td width="155" class="btle3e3e3 bgf2f9ff" height="45" align="center">实付金额</td>
									<td class="plr10 btle3e3e3 bre3e3e3 z14717171 bgf2f9ff">${currency(order.amountPaid, true)}</td>
								</tr>
								<tr>
									<td width="155" class="btle3e3e3" height="45" align="center">单节费用</td>
									<td class="plr10 btle3e3e3 bre3e3e3 z14717171">[#list order.orderItems as orderItem]${currency(orderItem.price, true)}[/#list]</td>
								</tr>
								<tr>
									<td width="155" class="btle3e3e3 bgf2f9ff" height="45" align="center">课节数量</td>
									<td class="plr10 btle3e3e3 bre3e3e3 z14717171 bgf2f9ff">${order.quantity}</td>
								</tr>
								<tr>
									<td width="155" class="btle3e3e3" height="45" align="center">上门费用</td>
									<td class="plr10 btle3e3e3 bre3e3e3 z14717171">${currency(order.freight, true)}</td>
								</tr>
								<tr>
									<td width="155" class="btle3e3e3 bbe3e3e3 bgf2f9ff" height="45" align="center">优惠</td>
									<td class="plr10 btle3e3e3 bre3e3e3 bbe3e3e3 z14717171 bgf2f9ff">${currency(order.couponDiscount, true)} </td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mtb30">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z16279fff" height="50">评价打分</td>
					</tr>
					<tr>
						<td class="bgfafafa pa20">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z14444444">
								<tr>
									<td width="80" align="center" valign="top" class="ptb10">评价内容</td>
									<td class="z14717171 ptb10" valign="top">[#if order.evaluateOrder] ${order.evaluateOrder.content} [#else]暂无评论 [/#if]</td>
								</tr>
								<tr>
									<td height="45" align="center"  valign="top">项目评分</td>
									<td class="z14717171">
										<table width="612" border="0" cellspacing="0" cellpadding="0" class="pif">
										  <tr>
											<td width="204" height="25" colspan="3" align="left"><span>综合得分</span><span class="pf_span"><div class="pf_bg" style="width:[#if order.evaluateOrder] ${order.evaluateOrder.scoreSort*10}[#else]0[/#if]%;"></div><img src="${base}/resources/newmechanism/images/x_1.png" border="0" class="pa" /></span><span>[#if order.evaluateOrder] ${order.evaluateOrder.scoreSort} [#else]0.0 [/#if]分</span></td>
										  </tr>
										  <tr>
											<td width="204" height="25"><span>技能评价</span><span class="pf_span"><div class="pf_bg" style="width:[#if order.evaluateOrder]${order.evaluateOrder.skillSort*10}[#else]0[/#if]%;"></div><img src="${base}/resources/newmechanism/images/x_1.png" border="0" class="pa" /></span><span>[#if order.evaluateOrder] ${order.evaluateOrder.skillSort} [#else]0.0[/#if]分</span></td>
											<td width="204"><span>服务能力</span><span class="pf_span"><div class="pf_bg" style="width:[#if order.evaluateOrder] ${order.evaluateOrder.serverSort*10}[#else]0[/#if]%;"></div><img src="${base}/resources/newmechanism/images/x_1.png" border="0" class="pa" /></span><span>[#if order.evaluateOrder] ${order.evaluateOrder.serverSort} [#else]0.0[/#if]分</span></td>
											<td width="204"><span>沟通水平</span><span class="pf_span"><div class="pf_bg" style="width:[#if order.evaluateOrder] ${order.evaluateOrder.communicateSort*10}[#else]0[/#if]%;"></div><img src="${base}/resources/newmechanism/images/x_1.png" border="0" class="pa" /></span><span>[#if order.evaluateOrder] ${order.evaluateOrder.communicateSort} [#else]0.0[/#if]分</span></td>
										  </tr>
										</table>									
                    				</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mtb30">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z16279fff" height="50">${order.mechanism.name}</td>
					</tr>
					<tr>
						<td class="bgfafafa pa10">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z14444444">
								<tr>
									<td width="180" align="center" valign="top" class="ptb10">
										<img onerror="this.src='${base}/resources/newmechanism/images/temp1.png'" src="${order.orderItems[0].thumbnail}" class="img160160">
									</td>
									<td class="z14717171 ptb10" valign="middle">
										<table cellpadding="0" cellspacing="0" border="0" width="98%" align="center">
											<tr>
												<td class="z16646464">${order.orderItems[0].fullName}</td>
											</tr>
											<tr>
												<td height="40">服务医师：${order.doctor.name} ${order.doctor.doctorCategory.name} ${message("Member.Gender." + order.doctor.gender)}</td>
											</tr>
											<tr>
												<td height="40">${order.project.introduce}</td>
											</tr>
										</table>
									</td>
									<td width="120" align="right" valign="bottom" class="z14717171">
										￥<span class="z20de0000">${order.orderItems[0].price}</span>元/${order.orderItems[0].time}分钟
										<br>
										<span class="z12939393">×${order.orderItems[0].quantity}&nbsp;</span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height="5"></td>
					</tr>
					<tr>
						<td align="right" class="z14717171 plr10" height="25">
							小计：${currency(order.price, true)}元
						</td>
					</tr>
					<tr>
						<td align="right" class="z14717171 plr10" height="25">
							上门费：${currency(order.freight, true)}元
						</td>
					</tr>
					<tr>
						<td align="right" class="z14717171 plr10" height="25">
							优惠：${currency(order.couponDiscount, true)}元
						</td>
					</tr>
					<tr>
						<td align="right" class="z14717171 plr10" height="25">
							总支付金额：${currency(order.amountPaid, true)}元
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mtb30">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z16279fff" height="50">操作日志</td>
					</tr>
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="200" height="50">操作类型</td>
									<td class="btle3e3e3" align="center">操作说明</td>
									<td class="btle3e3e3" align="center" width="200">操作人</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="160">操作时间</td>
								</tr>
								[#list order.orderLogs as orderLog]
								<tr [#if orderLog_index%2==0] class="bge7f4ff" [/#if]>
									<td class="btle3e3e3" align="center" height="50">${message("OrderLog.Type."+orderLog.type)}</td>
									<td class="btle3e3e3 plr10">${orderLog.content}</td>
									<td class="btle3e3e3 plr10">${orderLog.operator}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">${orderLog.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
								</tr>
								[/#list]
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
			
</div>
   					
<div id="assess">
	<div class="assess">
  		<form id="myform" action="${base}/mechanism/assessReport/save.jhtml" method="post" enctype="multipart/form-data">
   		<input type="hidden" name="patientMemberId" value="${order.patientMember.id}" />
		<input type="hidden" name="orderId" value="${order.id}" />
		<input type="hidden" name="doctorId" value="${order.doctor.id}" />
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					添加评估报告
  				</td>
   			</tr>
			<tr>
				<td height="50" class="bbe3e3e3 z14323232" align="right">评估时间</td>
				<td align="right" class="bbe3e3e3 pr65">
				<input type="text" name="createDate" id="createDate" value="${.now?string("yyyy-MM-dd")}" class="baffffff tar z14444444 w100"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});"/>
				</td><!--点击打开日历组件，可选择日期，默认为康复日期-->
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
  		<form id="inputForm" action="${base}/mechanism/recoveryRecord/save.jhtml" method="post" enctype="multipart/form-data">
   		<input type="hidden" name="patientMemberId" value="${order.patientMember.id}" />
		<input type="hidden" id = "orderId" name="orderId" value="${order.id}" />
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					添加康复记录
  				</td>
   			</tr>
   			<tr>
				<td height="50" class="bbe3e3e3 z14323232" align="right">康复时间</td>
				<td align="right" class="bbe3e3e3 pr65">
				<input type="text" name="recoveryData" id="recoveryData" value="${.now?string("yyyy-MM-dd")}" class="baffffff tar z14444444 w100"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});"/>
				</td><!--点击打开日历组件，可选择日期，默认为康复日期-->
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
	   				<div class="img_div">
					</div>
   					<img id="up_img_div" width="80" height="80" class="u_img_1" src="${base}/resources/newmechanism/images/k.png" onclick="update_introduce_img('recoveryRecordImages','img_div',0,'2')" >
	   				<div id="img_div">
						<input id="recoveryRecordImages_0" name="recoveryRecordImages[0].file" type="file" style="display: none"  />
					</div>
   				</td>
   			</tr>
   			<tr>
   				<td align="center" colspan="2" height="60">
   					<input type="submit" value="确认" class="button_3">
   					&nbsp;　&nbsp;
					<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#inputForm')[0].reset();disp_hidden_d('record','','','r_id');">
  					<input type="hidden" id="r_id" name="r_id" value="">
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>
<div id="visit">
	<div class="visit">
  		<form id="visitform"  action="${base}/mechanism/visitMessage/save.jhtml" method="post">
  		<input type="hidden"  name="visitDoctorId" value="${order.doctor.id}" />
  		<input type="hidden"  name="patientMemberId" value="${order.patientMember.id}" />
  		<input type="hidden"  name="type" value="patientMember" />
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4">
   					添加回访信息
  				</td>
   			</tr>
   			<tr>
   				<td>
   					<table cellpadding="0" cellspacing="0" border="0" width="90%" align="center" class="z14323232 m0auto">
   						<tr>
   							<td height="50" class="bbe3e3e3">回访日期</td>
   							<td align="right" class="bbe3e3e3"><input type="text" name="visitDate" id="visitDate" value="${.now?string("yyyy-MM-dd")}" class="baffffff tar z14444444 w100"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});"/></td><!--点击打开日历组件，可选择日期-->
   						</tr>
   						<tr>
   							<td height="50" class="bbe3e3e3">回访员工</td>
   							<td align="right" class="bbe3e3e3">
   								<select name="visitDoctorId" class="h30 w100 bg279fff z14ffffff baffffff"><!--默认为只有登录人自己，根据权限可以显示所有的人-->
   									[#list doctors as doctor]
   										<option value = "${doctor.id}">${doctor.name}</option>
   									[/#list]
   								</select>
   							</td>
   						</tr>
   						<tr>
   							<td height="50" class="bbe3e3e3">回访方式</td>
   							<td align="right" class="bbe3e3e3">
   								<select name="visitType" class="h30 w100 bg279fff z14ffffff baffffff">
   									[#list visitTypes as visitType]
   										<option value = "${visitType}">${message("VisitMessage.VisitType."+visitType)}</option>
   									[/#list]
   								</select>
   							</td>
   						</tr>
   						<tr>
   							<td class="bbe3e3e3">回访内容</td>
   							<td align="right" class="bbe3e3e3">
   								<textarea name="message" class="z14323232 inputkd9d9d9bgf6f6f6 w450 h80 mtb10"></textarea>
   							</td>
   						</tr>
   						<tr>
   							<td class="bbe3e3e3">回访结果</td>
   							<td align="right" class="bbe3e3e3">
   								<textarea name="resultMessage" class="z14323232 inputkd9d9d9bgf6f6f6 w450 h80 mtb10"></textarea></td>
   						</tr>
						<tr>
							<td align="center" colspan="2" height="60">
								<input type="submit" value="确认" class="button_3">
								&nbsp;　&nbsp;
								<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#visitform')[0].reset();disp_hidden_d('visit','','','v_id');">
								<input type="hidden" id="v_id" name="v_id" value="">
							</td>
						</tr>
   					</table>
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>

</body>
</html>