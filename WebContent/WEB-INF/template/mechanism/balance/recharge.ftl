<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
[#include "/mechanism/include/meta.ftl" /] 
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	

	
	
	// 表单验证
	$inputForm.validate({
		submitHandler: function(form) {
			var modifyBalance = $('input:radio[name="modifyBalance"]:checked').val();
			
			if(modifyBalance==null){
			  alert("请选择充值金额");
			  return false;
			}
			
			if(modifyBalance==0){
				var money = $('#money').val();
					if(money==""){
						alert("请输入充值金额");
						return false;
					}else{
					   $('input:radio[name="modifyBalance"]:checked').val(money);
					    modifyBalance = $('input:radio[name="modifyBalance"]:checked').val();
					}
			}
			form.submit();
		}
	});
	
});
</script>




<script type="text/javascript">

function Multiple(id)
	{
		$("input[name='modifyBalance']:eq(7)").attr("checked","checked");
		var str = $("#money").val();
		var pattern = /^(-)?[1-9][0-9]*$/;
		if(pattern.test(str) == false)
			{
				alert("充值金额只能为正数或负数");
				return false;
			}
		if($("#" + id).val() % 10)
			{
				alert("只能充值10元或10的倍数");
				return false;
			}
	}

</script>
</head>
<body>
    	<div class="nav">管理导航：
	    	<a href="javascript:;">用户管理首页</a>　
	    	<a href="javascript:;">患者信息</a>　
	    	<a href="javascript:;">预约信息</a>　
	    	<a href="javascript:;">回访信息</a>　
	    	<a href="javascript:;">账户信息</a>　
	    	<a href="javascript:;">医师信息</a>
    	</div>
        <div class="seat">
        	<div class="left_z">用户信息</div>
            <div class="export">
	            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
	            <a href="javascript:;"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>
	            <a href="javascript:window.back();"><img src="${base}/resources/mechanism/images/back.png" border="0" class="top_img_b_1" /> 返回</a>
            </div>
        </div>
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td width="280" valign="top">
            	<div class="peo_2">
            		<table cellpadding="0" cellspacing="0" border="0">
            			<tr>
            				<td><img onerror="this.src='${base}/resources/mechanism/images/r.png'" src="${member.logo}" alt="${member.name}" title="${member.name}" class="head_1" /></td>
            			</tr>
            			<tr>
            				<td height="50" class="b_a_dcdcdc_1"><span class="sp_m_1">${member.name}</span><span class="z_12_999999_1">　${message("Member.Gender."+member.gender)}　${member.nation}</span></td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">联系电话：${member.mobile}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">名下患者：
	            				[#list member.children as patient]
	                				  [#if !patient.isDeleted]
											${patient.name}、
									  [/#if]
		                		[/#list]
            				</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">出生日期：${member.birth?string("yyyy-MM-dd")}</td>
            			</tr>
            		</table>
            	</div>
            </td>
            <td valign="top"><table width="99%" align="right" border="0" cellspacing="0" cellpadding="0">
	            <tbody>
	                <tr>
	                  <td height="40">
						<div class="peo_tag_1">
							<ul>
								<li style="width:17%;"><div class="border_radius_l_1"><a href="${base}/mechanism/member/member_view.jhtml?id=${member.id}">基本信息</a></div><div class="div_x_1"></div></li>
								<li style="width:17%;"><div><a href="${base}/mechanism/member/member_patient.jhtml?memberId=${member.id}">患者信息</a></div><div class="div_x_1"></div></li>
								<li style="width:17%;"><div><a href="${base}/mechanism/member/member_reserve.jhtml?memberId=${member.id}">预约信息</a></div><div class="div_x_1"></div></li>
								<li style="width:17%;"><div><a href="${base}/mechanism/visitMessage/member_visitMessage_list.jhtml?memberId=${member.id}&type=member">回访信息</a></div><div class="div_x_1"></div></li>
								<li style="width:16%;"><div class="sel_div_1"><a href="${base}/mechanism/balance/recharge.jhtml?memberId=${member.id}">账户充值</a></div><div class="div_x_1"></div></li>
								<li style="width:16%;"><div class="border_radius_r_1"><a href="${base}/mechanism/member/member_doctor.jhtml?memberId=${member.id}">医师信息</a></div><div class="div_x_1"></div></li>
							</ul>												
						</div>
	               	  </td>
	                </tr>
	                <tr>
	                  <td class="b_l_r_b_dcdcdc_1" height="500" valign="top">
	                  <form id="inputForm" method="post" action="save.jhtml">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_323232_1">
						  <input type="hidden" name = "memberId" value="${member.id}"/>
						  <tr>
							<td colspan="2">&nbsp;</td>
						  </tr>
						  <tr>
							<td width="150" height="40" align="right" valign="top" class="p_t_5_1">充值账号<span class="z_14_ff0000_1"> </span></td>
							<td height="40" valign="top" class="p_l_r_20_1"><input type="text" name="mobile" id="mobile" class="inp_1 w_360_1" value="${member.mobile}" disabled /></td>
						  </tr>
						  <tr>
							<td width="150" height="40" align="right" valign="top" class="p_t_5_1">收款人姓名<span class="z_14_ff0000_1"> </span></td>
							<td height="40" valign="top" class="p_l_r_20_1"><input type="text" name="name" id="name" class="inp_1 w_360_1" value="${member.name}" disabled /></td>
						  </tr>
						  <tr>
							<td width="150" height="40" align="right" valign="top" class="p_t_5_1">充值金额<span class="z_14_ff0000_1">*</span></td>
				            <td height="40" valign="top" class="p_l_r_20_1"><table width="450" border="0" cellspacing="0" cellpadding="0">
				              <tbody>
				                <tr>
				                  <td width="33%" height="25">
				                  	<input type="radio" name="modifyBalance" id="radio" value="200" />200元
				                  </td>
				                  <td width="33%">
				                  	<input type="radio" name="modifyBalance" id="radio" value="500" />500元
				                  </td>
				                  <td width="33%">
				                  	<input type="radio" name="modifyBalance" id="radio" value="1000" />1000元
				                  </td>
				                </tr>
				                <tr>
				                  <td height="25">
				                  	<input type="radio" name="modifyBalance" id="radio" value="3000"  />3000元
				                  </td>
				                  <td>
				                  	<input type="radio" name="modifyBalance" id="radio" value="5000"  />5000元
				                  </td>
				                  <td>
				                  	<input type="radio" name="modifyBalance" id="radio" value="10000"  />10000元
				                  </td>
				                </tr>
				                <tr>
				                  <td height="25">
				                  	<input type="radio" name="modifyBalance" id="radio" value="20000"  />20000元
				                  </td>
				                  <td colspan="2">
				                  	<input type="radio" name="modifyBalance" id="radio" value="0" /> 
				                  	其他
				                  	<input type="text" name="money" id="money" class="inp_4 w_40_1" onfocus="" onBlur="Multiple('money');" onpaste="return false;" >元<font color="#ff0000">*正数为充值 负数为扣除</font>
				                  </td>
				                </tr>
				              </tbody>
				            </table></td>
						  </tr>
						  <tr>
							<td height="40" align="right" valign="top">备注<span class="z_14_ff0000_1">&nbsp;</span></td>
							<td height="40" valign="top" class="p_l_r_20_1 p_b_10_1"><textarea name="memo" id="memo" rows="5" class="inp_2 h_80_1 w_360_1"></textarea></td>
						  </tr>
						  <tr>
							<td height="10" align="right" valign="top">&nbsp;</td>
							<td height="10" valign="top">&nbsp;</td>
						  </tr>
						  <tr>
							<td height="40" align="right" valign="top">&nbsp;</td>
							<td height="40" valign="top" class="p_l_r_20_1">
								<input type="submit" name="button7" id="button7" value=" 充 值 " class="button_1 b_4188d1_1" />　
								<input type="reset" name="button7" id="button7" value=" 重 置 " class="button_1 b_ececec_1 z_12_707070_1" />
							</td>
						  </tr>
						</table>
	                  </form>
	                  </td>
	                </tr>
	              </tbody>
	            </table>
            </td>
            </tr>
        </table>
        </div> 

<div class="clear"></div>

</body>
</html>