<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

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
				pattern: /^1[3|4|5|6|7|8|9][0-9]{9}$/,
				
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
		 html += '					<table cellpadding="0" cellspacing="0" border="0" width="100%" align="center" class="m_t_10_1 z12999999">';
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
						html += '		<td height="18" align="left"><a id="ser_'+i+'" href="javascript:;" onclick="assign_str(\'' + dataObj[i].name + '|' + dataObj[i].username + '\',\'mobile\',\'name\',\'' + classname + '\');">　'+dataObj[i].name+'　'+dataObj[i].username+'</a></td>';
						html += '</tr>';
	                    
                    }
                }     
        }); 

		html += '					</table>';
		
		if(n == 0)
			{
				$("." + classname).html("");
				$("." + classname).css({"height": "0px","overflow-y": "none", "display": "none"});				
			}
		else
		{
			$("." + classname).html(html);
			var h = 12 + 18 * n;
			$("." + classname).css("height" , h + "px");
			if(n > 5)
				{
					$("." + classname).css("overflow-y" , "scroll");
				}
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
				$('.' + classname).css({"display":"block"});
				connect_str(classname,str);
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
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">账户充值</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<form id="inputForm" method="post" action="balanceSave.jhtml">
							<table cellpadding="0" cellspacing="0" border="0" width="670" class="z14444444">
								<tr>
									<td height="50" width="120" align="right">充值账户：<span class="z14ff0000">*</span></td>
									<td class="pr">
										<input type="text" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w420 m1020 plr10" name="mobile" id="mobile" placeholder="请输入充值用户手机号码" onKeyUp="this.value=this.value.replace(/\D/g,'');get_key('lay_flt_3','mobile');" onafterpaste="this.value=this.value.replace(/\D/g,'')">
										<div class="lay_flt_3 scroll_y_1">
										</div>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">用户姓名：<span class="z14ff0000">*</span></td>
									<td class="pr">
										<input type="text" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w420 m1020 plr10" name="name" id="name" placeholder="请输入充值用户姓名" onKeyUp="get_key('lay_flt_2','name');" />
										<div class="lay_flt_2 scroll_y_1">
										</div>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">充值金额：<span class="z14ff0000">*</span></td>
									<td class="plr20 ptb10">
										<table cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr>
												<td width="33%" height="25">
													<input type="radio" id="radio" name="modifyBalance" value="200"> 200元
												</td>
												<td width="33%" height="25">
													<input type="radio" id="radio" name="modifyBalance" value="500"> 500元
												</td>
												<td width="33%" height="25">
													<input type="radio" id="radio" name="modifyBalance" value="1000"> 1000元
												</td>
											</tr>
											<tr>
												<td width="33%" height="25">
													<input type="radio" id="radio" name="modifyBalance" value="3000"> 3000元
												</td>
												<td width="33%" height="25">
													<input type="radio" id="radio" name="modifyBalance" value="5000"> 5000元
												</td>
												<td width="33%" height="25">
													<input type="radio" id="radio" name="modifyBalance" value="10000"> 10000元
												</td>
											</tr>
											<tr>
												<td colspan="3" height="25">
													<input type="radio" id="radio" name="modifyBalance" value="0"> 其他<input type="text" class="w50 h15 banone bb1dd4d4d4 plr10" onblur="Multiple('money');" onpaste="return false;" onFocus="set_radio();" id="money">元 <span class="z14ff0000">* 正数充值 负数扣除</span>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">备注说明：<span class="z14ff0000">*</span></td>
									<td class="plr20 ptb10">
										<textarea name="memo" id="memo" class="z14323232 inputkd9d9d9bgf6f6f6 h40 w420 plr10 ptb10"></textarea>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right"></td>
									<td>
										<input type="submit" value="确认充值" class="button_3 ml20">
									</td>
								</tr>
							</table>
							</form>
						</td>
					</tr>
					
					<tr>
						<td colspan="2" height="30"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

</body>
</html>