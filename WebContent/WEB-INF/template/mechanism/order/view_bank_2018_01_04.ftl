<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>

<script type="text/javascript">
$().ready(function() {

var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
	
		rules: {
			diseaseName: "required",
			nowExplain: "required",
			assessResult: "required",
			proposal: "required",
			shortTarget: "required",
			longTarget: "required",
			startTime: "required",
			endTime: "required",
			summary: "required"
			
		},
		
		submitHandler: function(form) {
		var i = $('#project option').size();
		console.log($('#project option').text());
		console.log(i);
		if(i<1){
		$.message("warn", "至少添加一个康护项目");
		return false;
		}
		
		var tmp_str="";
		for(var j = 0; j < i; j++){
				tmp_str = tmp_str  + $('#project option:eq('+j+')').val() + "&";
		}
		console.log(tmp_str);
		$('#drillContent').val(tmp_str);
		
		 		form.submit();
		}
	});






});
</script>	


<script type="text/javascript" >
$(function(){



	//添加服务项目
	$("#remove").click(function(){
		if(!$("#project_s option").is(":selected"))
			{			
				alert("请选择康复项目");
				return false;
			}
		if(isNaN($("#n").val()) || $("#n").val()<1)
			{
				alert("服务次数应为大于0的数字");
				return false;
			}
		var idx = $("#project_s option:selected").val() + "," + $("#n").val();
		var txt = $("#n").val() + "次 " + $("#project_s option:selected").text()
		$("#project").append("<option value='" + idx + "'>" + txt + "</option>")
		$('#project_s option:selected').remove();
	});
	
	//取消服务项目
	$("#project").dblclick(function(){
		var idx = $("#project option:selected").val().split(",");
		var txt = $("#project option:selected").text().split("次 ");
		$("#project_s").append("<option value='" + idx[0] + "'>" + txt[1] + "</option>")
		$('#project option:selected').remove();
	})
	
	
	
})
  
  //上传预览
function upload_assessReport_img(upfile,upimg,pid,flag)
{
	$("#" + upfile + "_" + pid).click();
	$("#" + upfile + "_" + pid).on("change",function(){
		url=getObjectURL(this.files[0]);

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
						$("#" + upimg +"_file").append("<input id=\"" + upfile + "_" + pid + "\" name=\"" + upfile + "[" + pid + "].file" +"\" type=\"file\" style=\"display: none\"  />");
						$("#up_" + upimg).attr('onclick','upload_assessReport_img("' + upfile + '","' + upimg + '",' + pid +',"' + flag +'")');
						upimg="";
					}
				}
			}
	});
}

//删除预览图片
function removeImg(upimg,upfile,pid)
{
	var count=$("#" + upimg +"_file").children("input").size()-1;
	$("#" + upimg + "_b_" + pid).parent().remove();
	
		$("#" + upfile + "_" + pid).remove();
		$("#up_" + upimg).attr('onclick','upload_assessReport_img("' + upfile +'","' + upimg + '",'+ (count-1) +',"2")');
		for(var i=(pid+1);i<=count;i++)
			{
				var tmp_pid=i-1;
				$("#" + upfile + "_" + i).attr({'name':upfile + '_' + tmp_pid,'id':upfile + '_' + tmp_pid});
				$("#" + upimg + "_b_" + i).attr({'onclick':'removeImg("' + upimg + '","' + upfile +'",'+ tmp_pid + ')','id':upimg + '_b_' + tmp_pid});
			}
	
	
	
}
  
</script>

</head>
<body>

    	<div class="nav">管理导航：<a href="javascript:;">管理首页</a>　<a href="javascript:;">新增医师</a>　<a href="javascript:;">排班管理</a></div>
        <div class="seat">
        	<div class="left_z">订单管理</div>
            <div class="export"><a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a><a href="javascript:;"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a><a href="javascript:history.back(-1);"><img src="${base}/resources/mechanism/images/back.png" border="0" class="top_img_b_1" /> <font color="#FF0000">返回</font></a>
            </div>
        </div>
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="250" align="center" valign="middle" class="b_r_dcdcdc_1 b_b_dcdcdc_1">订单号：${order.sn}<br />
                  <span class="z_20_4c98f6_1">${message("Order.PaymentStatus." + order.paymentStatus)}<font class="z_14_666666_1">[#if order.expired](已取消)[/#if]</font></span></td>
                <td class="b_b_dcdcdc_1"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="20%" height="26" align="center" class="zt_bg_1 pos_re_1"><div class="zt_bg_l_1"></div><img src="${base}/resources/mechanism/images/q_l.jpg" alt="" width="26" height="26" class="zt_bg_l_img_1" /></td>
                    <td width="20%" align="center" class="zt_bg_1"><img [#if order.paymentStatus == "paid"] src="${base}/resources/mechanism/images/q_l.jpg" [#else] src="${base}/resources/mechanism/images/q_h.jpg" [/#if]  alt="" width="26" height="26" /></td>
                    <td width="20%" align="center" class="zt_bg_1"><img src="${base}/resources/mechanism/images/q_h.jpg" alt="" width="26" height="26" /></td>
                    <td width="20%" align="center" class="zt_bg_1"><img src="${base}/resources/mechanism/images/q_h.jpg" alt="" width="26" height="26" /></td>
                    <td width="20%" align="center" class="zt_bg_1 pos_re_1"><div class="zt_bg_r_1"></div><img src="${base}/resources/mechanism/images/q_h.jpg" alt="" width="26" height="26" class="zt_bg_l_img_1" /></td>
                  </tr>
                  <tr>
                    <td height="70" align="center" valign="top"><span class="z_14_666666_1">提交订单</span><br />
                      <span class="z_12_999999_1">${order.createDate?string("yyyy.MM.dd")}<br />
                      ${order.createDate?string("HH:mm:ss")}</span>
                      
                      </td>
                    <td align="center" valign="top"><span class="z_14_666666_1">付款完成</span><br />
                    [#if order.paymentStatus == "paid"]
                      <span class="z_12_999999_1">${order.paidDate?string("yyyy.MM.dd")}<br />
                      ${order.paidDate?string("HH:mm:ss")}</span>
                    [/#if]
                    </td>
                    <td align="center" valign="top"><span class="z_14_666666_1">诊治评估</span><br /></td>
                    <td align="center" valign="top"><span class="z_14_666666_1">评价打分</span><br /></td>
                    <td align="center" valign="top"><span class="z_14_666666_1">完成</span><br /></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_666666_1">
              <tr>
                <td width="50%" valign="top" class="b_r_dcdcdc_1 b_b_dcdcdc_1"><table width="98%" border="0" cellspacing="0" cellpadding="0" class="m_t_f_30_1">
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="160" align="left" valign="top"><img  src="${order.member.logo}"  alt="" width="140" height="140" class="img_radius_70_1" /></td>
                        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>下单用户：${order.consignee} <span class="z_12_999999_1">${order.phone}</span></td>
                          </tr>
                          <tr>
                            <td>患者姓名：${order.patientMember.name} <span class="z_12_999999_1">2周岁   ${message("Member.Gender." + order.patientMember.gender)}</span></td>
                          </tr>
                          <tr>
                            <td>服务时间：[#if order.workDayItem??] ${order.workDayItem.workDay.workDayDate?string("yyyy年MM月dd日")} ${order.workDayItem.startTime}-${order.workDayItem.endTime}[/#if]</td>
                          </tr>
                          <tr>
                            <td>服务类型：到店康护</td>
                          </tr>
                          <tr>
                            <td>服务地址：${order.mechanism.area.fullName}${order.mechanism.address}</td>
                          </tr>
                          <tr>
                            <td><!--症状简述：--></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td height="40" valign="bottom"><input type="button" name="button2" id="button2" value="申请查阅病例" class="button_1 b_ee6a71_1" />
                      <input type="button" name="button3" id="button3" value="以往病例" class="button_1 b_40d0a7_1" />
                      <input type="button" name="button4" id="button4" value="添加评估报告"  onClick="disp_hidden_d('report',590,500,'1');" class="button_1 b_4fc1e9_1" />
                      <input type="button" name="button5" id="button5" value="评估" class="button_1 b_d1d141_1" />
                      <input type="button" name="button7" id="button7" value="诊治" class="button_1 b_4188d1_1" /></td>
                  </tr>
                </table></td>
                <td width="50%" valign="middle" class="b_b_dcdcdc_1"><table width="98%" border="0" align="right" cellpadding="0" cellspacing="0">
                  <tr>
                    <td height="50" valign="top" class="z_20_323232_1">付款信息</td>
                  </tr>
                  <tr>
                    <td>付款方式：余额支付</td>
                  </tr>
                  <tr>
                    <td>付款时间：
	                    [#if order.paymentStatus == "paid"]
	                     ${order.paidDate?string("yyyy-MM-dd HH:mm:ss")}
	                    [#else]
	                    	未支付
	                    [/#if]
                    </td>
                  </tr>
                  <tr>
                    <td>订单总额：${currency(order.amount, true)}</td>
                  </tr>
                  <tr>
                    <td>优 惠 卷：${currency(order.couponDiscount, true)}</td>
                  </tr>
                  <tr>
                    <td>应付金额：${currency(order.amountPayable, true)}</td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td class="b_l_r_b_dcdcdc_1 p_25_1"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="30" valign="top"><span class="z_14_4c98f6_1">评价打分&gt;</span></td>
              </tr>
              <tr>
                <td class="z_14_666666_1"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="70" valign="top" class="p_t_10_1">评价内容：</td>
                    <td valign="top" class="p_t_10_1">[#if order.evaluateOrder] ${order.evaluateOrder.content} [#else]暂无评论 [/#if]</td>
                  </tr>
                  <tr>
                    <td width="70" valign="top" class="p_t_10_1">项目评分：</td>
                    <td valign="top" class="p_t_10_1"><table width="600" border="0" cellspacing="0" cellpadding="0" class="pif">
                      <tr>
                        <td height="25" colspan="3"><span>综合得分</span><span class="pf_span"><div class="pf_bg" style="width:[#if order.evaluateOrder] ${order.evaluateOrder.scoreSort*10}[#else]0[/#if]%;"></div><img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" /></span><span>[#if order.evaluateOrder] ${order.evaluateOrder.scoreSort} [#else]0.0 [/#if]分</span></td>
                        </tr>
                      <tr>
                        <td width="200" height="25"><span>技能评价</span><span class="pf_span"><div class="pf_bg" style="width:[#if order.evaluateOrder]${order.evaluateOrder.skillSort*10}[#else]0[/#if]%;"></div><img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" /></span><span>[#if order.evaluateOrder] ${order.evaluateOrder.skillSort} [#else]0.0[/#if]分</span></td>
                        <td width="200"><span>服务能力</span><span class="pf_span"><div class="pf_bg" style="width:[#if order.evaluateOrder] ${order.evaluateOrder.serverSort*10}[#else]0[/#if]%;"></div><img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" /></span><span>[#if order.evaluateOrder] ${order.evaluateOrder.serverSort} [#else]0.0[/#if]分</span></td>
                        <td width="200"><span>沟通水平</span><span class="pf_span"><div class="pf_bg" style="width:[#if order.evaluateOrder] ${order.evaluateOrder.communicateSort*10}[#else]0[/#if]%;"></div><img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" /></span><span>[#if order.evaluateOrder] ${order.evaluateOrder.communicateSort} [#else]0.0[/#if]分</span></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td class="b_l_r_b_dcdcdc_1 p_25_1"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="180" align="left" valign="top"><img src="${order.project.logo}" onerror="this.src='${base}/upload/project/logo/project_default_logo.png'" alt="" width="160" height="160" class="img_radius_10_1" /></td>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><span class="z_16_666666_1">${order.project.name}</span><br />
服务医师：${order.doctor.name} <span class="z_12_999999_1">${order.doctor.doctorCategory.name} ${message("Member.Gender." + order.doctor.gender)}</span><br />
项目描述：${order.project.introduce} </td>
                  </tr>
                  <tr>
                    <td height="150" align="right" valign="bottom" class="l_170_1"><span class="z_14_ff0000_1">￥${order.project.price}元</span><span class="z_12_999999_1">/${order.project.time}分钟</span><br />
                      小计：￥${currency(order.price, true)}元<br />
                      优惠卷：￥${currency(order.couponDiscount, true)}元<br />
                      总支付金额：￥${currency(order.amount, true)}元</td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td colspan="4" height="70" class="z_20_4c98f6_1">操作日志</td>
					</tr>
					<tr>
						<td colspan="4"><hr class="b_b_dcdcdc_3"></td>
					</tr>
					<tr>
						<td width="200" height="45" class="b_b_dcdcdc_3 p_l_r_10_1">操作类型</td>
						<td class="b_b_dcdcdc_3 p_l_r_10_1">操作说明</td>
						<td width="120" class="b_b_dcdcdc_3" align="center">操作人</td>
						<td width="120" class="b_b_dcdcdc_3" align="center">操作时间</td>
					</tr>
					[#list order.orderLogs as orderLog]
					<tr>
						<td width="200" height="45" class="b_b_dcdcdc_3 p_l_r_10_1">${message("OrderLog.Type."+orderLog.type)}</td>
						<td class="b_b_dcdcdc_3 p_l_r_10_1">${orderLog.content}</td>
						<td width="120" class="b_b_dcdcdc_3" align="center">${orderLog.operator}</td>
						<td width="120" class="b_b_dcdcdc_3" align="center">${orderLog.createDate?string("yyyy-MM-dd")}<font class="z_12_999999_1"><br>${orderLog.createDate?string("HH:mm:ss")}</font></td>
					</tr>
					[/#list]
				</table>  
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
        </div>      
 
<div class="clear"></div>
	
<div id="report" >
	<div class="report scroll_y_1">
    	<div class="d_l_1">
    	<form id="inputForm" action="${base}/mechanism/assessReport/save.jhtml" method="post" enctype="multipart/form-data">
			<input type="hidden" name="patientMemberId" value="${order.patientMember.id}" />
			<input type="hidden" id = "orderId" name="orderId" value="${order.id}" />
			<input type="hidden" id = "drillContent" name="drill" value="" />
			<table cellpadding="0" cellspacing="0" border="0" width="95%" align="left">
				<tr>
					<td class="b_b_dcdcdc_1">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="10"></td>
								<td width="100" height="35" class="pos_re_1">
									<div id="b_t_1" class="k_3 b_4c98f6_1"><a href="javascript:;" onClick="Toggle_tag('b',1,2)">诊评报告</a></div>
								</td>
								<td width="10"></td>
								<td width="100" class="pos_re_1">
									<div id="b_t_2" class="k_3 b_dcdcdc_1"><a href="javascript:;" onClick="Toggle_tag('b',2,2)">康复计划</a></div>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td valign="top">
						<table id="b_i_1" cellpadding="0" cellspacing="0" border="0" width="100%" class="z_14_666666_1" style="display: block;">
							
							<tr>
								<td height="10" colspan="2"></td>
							</tr>
							<tr>
								<td width="70" height="35">评估医师：</td>
								<td>
									<select id="doctor" name = "doctorId" class="inp_1 w_180_1">
									[#list order.mechanism.doctors as doctor]
										<option value="${doctor.id}">${doctor.name}</option>
									[/#list]
									</select>
								</td>
							</tr>
							<tr>
								<td width="70" height="35">病患名称：</td>
								<td><input type="text" name="diseaseName"  id="diseaseName" class="inp_1 w_315_1"></td>
							</tr>
							<tr>
								<td height="35" valign="top">病情描述:</td>
								<td><textarea  name="nowExplain" id="nowExplain" rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td height="3" colspan="2"></td>
							</tr>
							<tr>
								<td height="35" valign="top">诊评结果:</td>
								<td><textarea name="assessResult" id="assessResult"  rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td height="3" colspan="2"></td>
							</tr>
							<tr>
								<td height="35" valign="top">康复建议:</td>
								<td><textarea  name="proposal" id="proposal"  rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td height="3" colspan="2"></td>
							</tr>
							<tr>
								<td height="35" valign="top">图片上传：</td>
								<td>
									<div class="img_div"></div>
									<img id="up_img_div" src="${base}/resources/mechanism/images/j_i_1.png" alt="" width="80" height="80" class="u_img_1" onClick="upload_assessReport_img('assessReportImages','img_div',0,'2');" />
									<div id="img_div_file">
										<input id="assessReportImages_0"  name="assessReportImages[0].file" type="file" style="display: none"  />
									</div>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<input type="hidden" id="p_id" value="">	
            					</td>
							</tr>
						</table>
						<table id="b_i_2" cellpadding="0" cellspacing="0" border="0" width="100%" class="z_14_666666_1" style="display: none;">
							<tr>
								<td height="10" colspan="2"></td>
							</tr>
							<tr>
								<td width="70" height="35">康复医师：</td>
								<td>
									<select id="doctor"  name = "redoctorId" class="inp_1 w_180_1">
										[#list order.mechanism.doctors as doctor]
											<option value="${doctor.id}">${doctor.name}</option>
										[/#list]
									</select>
								</td>
							</tr>
							<tr>
								<td width="70" height="35">审核医师：</td>
								<td>
									<select id="doctor" name = "auditDoctorId" class="inp_1 w_180_1">
										[#list order.mechanism.doctors as doctor]
											<option value="${doctor.id}">${doctor.name}</option>
										[/#list]
									</select>
								</td>
							</tr>
							<tr>
								<td height="35" valign="top">短期目标：</td>
								<td><textarea name="shortTarget" id="shortTarget"  rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td height="3" colspan="2"></td>
							</tr>
							
							<tr>
								<td height="35" valign="top">长期目标：</td>
								<td><textarea  name="longTarget" id="longTarget"  rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td width="70" height="35">康复周期：</td>
								<td>
									<input type="text"  id="startTime" name="startTime"  class="inp_1 " onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endTime\')}'});" >-<input type="text"  id="endTime" name="endTime"  class="inp_1 " onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startTime\')}'});" >
								</td>
							</tr>
							<tr>
								<td height="35" valign="top">康复项目：</td>
								<td valign="top">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr>
											<td width="110" valign="top"><select name="project" size="6" class="w_100_1 h_110_1 b_a_dcdcdc_2" id="project"></select><br><font color="#FF0000">* 双击取消</font></td>
											<td width="30">&lt;--</td>
											<td width="110" valign="top">
											<select name="project_s" size="6" class="w_100_1 h_110_1 b_a_dcdcdc_2" id="project_s">
													[#list serverProjectCategorys as serverProjectCategory]
														<option value="${serverProjectCategory.id}">
															${serverProjectCategory.name}
														</option>
													[/#list]
											</select>
											</td>
											<td align="right">
											次数：
											<input name="n" style="width:30px" type="text" id="n" value="1" size="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
											次
										  <input type="button" name="remove" id="remove" class="button_1 b_d17441_1" value="添加">
										  <br>
										  
											</td>
										</tr>
									</table>
								  </td>
							</tr>
							<tr>
								<td height="35" valign="top">康复总结：</td>
								<td><textarea  name="summary" id="summary"  rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td></td>
								<td>
									<input type="hidden" id="p_id_2" value="">
            					</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table id="b_i_3" cellpadding="0" cellspacing="0" border="0" width="100%" style="text-align: center;" class="z_14_666666_1">
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<input type="submit" class="button_1 b_4fc1e9_1" value="提交" />
						<input type="button" class="button_1" value="取消" onclick="disp_hidden_d('report');" />
					</td>
				</tr>
				<tr>
								<td colspan="2" height="20"></td>
				</tr>
			</table>
			</form>
        </div>
        <div class="d_cl_1"><a href="javascript:;" onclick="disp_hidden_d('report');"><img src="${base}/resources/mechanism/images/x.png" width="15" border="0" /></a></div>
	</div>
</div>
	
	
</body>
</html>