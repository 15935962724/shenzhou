<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/common.css" rel="stylesheet" type="text/css" />
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
	
		$.validator.addClassRules({
		modifyBalance: {
			required: true,
			extension: "${setting.priceScale}"
		}
		
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			mobile: {
				required: true,
				pattern: /^1[3|4|5|7|8][0-9]{9}$/,
				
			},
			name: "required",
			messages: {
				mobile: {
					pattern: "电话号码有误"
				}
			},
		
		},
		
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
function connect_str(classname,key)
	{
		//拼接ajax，key为传递搜索关键词，手机、姓名
		var	n = 0;  //填写查询记录条数
		var html="";
		 html += '					<table cellpadding="0" cellspacing="0" border="0" width="90%" align="center" class="m_t_10_1 z_12_999999_1">';
		 $.ajax( {     
                "type": "POST",  
                "url": 'members.jhtml',  
                "contentType": "application/x-www-form-urlencoded; charset=utf-8",   
                "traditional": true,  
                "async": false,
                "data": {  
                    nameOrMobile:key  
                },  
                success: function(data) {   
                	
                    //将数据转换成对象  
                    var dataObj = eval('('+data+')');  
                    n = dataObj.length;
                    console.log(dataObj);
                    for(var i = 0; i<dataObj.length; i++){
	                    html += '<tr>';
						html += '		<td height="18" align="left"><a id="ser_1" href="javascript:;" onclick="assign_str(\'' + dataObj[i].name + '|' + dataObj[i].mobile + '\',\'mobile\',\'name\',\'' + classname + '\');">　'+dataObj[i].name+'　'+dataObj[i].mobile+'</a></td>';
						html += '</tr>';
	                    
                    }
                }     
        }); 

		html += '					</table>';
		$("." + classname).html(html);
		
		var h = 12 + 18 * n;
		$("." + classname).css("height" , h + "px");
		if(n > 5)
			{
				$("." + classname).css("overflow-y" , "scroll");
			}
	}
function assign_str(str,mobile,name,classname)
	{
		var att = str.split("|");
		$("#" + name).val(att[0]);
		$("#" + mobile).val(att[1]);
		$('.' + classname).css({"display":"none" , "height":"12px" , "overflow-y":"hidden"});
		$("." + classname).empty();
	}
function str_length(str)
	{
		var realLength = 0, len = str.length, charCode = -1;
		for (var i = 0; i < len; i++) 
			{
				charCode = str.charCodeAt(i);
				if (charCode >= 0 && charCode <= 128) 
					realLength += 1;
				else
					realLength += 2;
			}
		return realLength;
	}
	
function get_key(classname,id)
	{
		var str = $("#" + id).val();
		if(((str_length(str) >= 4) && (id == "name")) || ((str_length(str) >= 8) && (id == "mobile")))
			{
				connect_str(classname,str);
				$('.' + classname).css({"display":"block"});
			}
	}

function Multiple(id)
	{
		
		
		if($("#" + id).val() % 10)
			{
				alert("只能充值10元或10的倍数");
				$("#" + id).val("");
				$("#" + id)[0].focus();
				return false;
			}
	}
$(function(){
	$("body").click(function(){
		var ori = document.activeElement.id;
		if(ori.substr(0,4) != "ser_")
			{
				$(".lay_flt_2").empty();
				$(".lay_flt_2").css({"display":"none" , "height":"12px" , "overflow-y":"hidden"});
				$(".lay_flt_3").empty();
				$(".lay_flt_3").css({"display":"none" , "height":"12px" , "overflow-y":"hidden"});
			}
	});
});
</script>
<style>
	.lay_flt_2 a{display: inline-block;height: 18px;width: 450px;}
	.lay_flt_3 a{display: inline-block;height: 18px;width: 450px;}
</style>

</head>
<body>

    	<div class="nav">管理导航：<a href="javascript:;">管理首页</a></div>
        <div class="seat">
        	<div class="left_z">账户充值</div>
            <div class="export"><font class="z_14_ff0000_1">*</font> 为必填项
        </div>
        
        </div>
        <div class="detail">
        <form id="inputForm" method="post" action="balanceSave.jhtml">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_323232_1">
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td width="150" height="40" align="right" valign="top" class="p_t_5_1">充值账号<span class="z_14_ff0000_1">*</span></td>
            <td height="40" valign="top" class="p_l_r_20_1 pos_r_1"><input type="text" placeholder="请输入对方手机号" name="mobile" id="mobile" class="inp_1 w_450_1" onKeyUp="this.value=this.value.replace(/\D/g,'');get_key('lay_flt_3','mobile');" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
				<div class="lay_flt_3">
				</div>
            </td>
          </tr>
          <tr>
            <td width="150" height="40" align="right" valign="top" class="p_t_5_1">收款人姓名<span class="z_14_ff0000_1">*</span></td>
            <td height="40" valign="top" class="p_l_r_20_1 pos_r_1"><input type="text" placeholder="请输入对方姓名" name="name" id="name" class="inp_1 w_450_1" onKeyUp="get_key('lay_flt_2','name');" />
				<div class="lay_flt_2">
				</div>
            </td>
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
                  	<input type="text" name="money" id="money" class="inp_4 w_40_1" onKeyUp="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'')" onBlur="Multiple('money');" >元
                  </td>
                </tr>
              </tbody>
            </table></td>
          </tr>
          <tr>
            <td height="40" align="right" valign="top">备注<span class="z_14_ff0000_1">&nbsp;</span></td>
            <td height="40" valign="top" class="p_l_r_20_1 p_b_10_1"><textarea name="memo" id="memo" rows="5" class="inp_2 h_80_1 w_450_1"></textarea></td>
          </tr>
          <tr>
            <td height="10" align="right" valign="top">&nbsp;</td>
            <td height="10" valign="top">&nbsp;</td>
          </tr>
          <tr>
            <td height="40" align="right" valign="top">&nbsp;</td>
            <td height="40" valign="top" class="p_l_r_20_1"><input type="submit" name="button7" id="button7" value=" 充 值 " class="button_1 b_4188d1_1" />　<input type="reset" name="button7" id="button7" value=" 重 置 " class="button_1 b_ececec_1 z_12_707070_1" />
            </td>
          </tr>
        </table>
        </form>
        </div>      
<div class="clear"></div>

</body>
</html>