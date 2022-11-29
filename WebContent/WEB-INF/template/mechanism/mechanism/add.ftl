<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>基本信息</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
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
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	var $areaId = $("#areaId");
	var $name = $("#name");
	var $logo_img = $("#logo_img");
	var $address = $("#address");
	var $mechanismCategoryId = $("#mechanismCategoryId");
	var $mechanismRankId = $("#mechanismRankId");
	var $phone = $("#phone");
	var $startTime = $("#startTime");
	var $endTime = $("#endTime");
	var $up_img_div = $("#up_img_div");
	var $upfile_1 = $("#upfile_1");
	
	
	
	
	
	
	//var $introduceImgBrowserButton = $("#introduceImgBrowserButton");
	
	// 地区选择
	$areaId.lSelect({
		url: "${base}/mechanism/common/area.jhtml"
	});
	
	// 机构类型
	$mechanismCategoryId.lSelect({
		url: "${base}/mechanism/common/mechanismCategory.jhtml"
	});
	
	// 机构等级
	$mechanismRankId.lSelect({
		url: "${base}/mechanism/common/mechanismRank.jhtml"
	});
	
	[@flash_message /]
	var mechanismImageIndex = 0;
	
	$("#up_img_div").click(function() {
	
		$upfile_1.click();
		
		var fals = true;
		$upfile_1.on("change",function(){
				if(fals){
					var url=getObjectURL(this.files[0]);
					[@compress single_line = true]
						var html = '<div class="isImg"><img width="80" height="80" class="u_img_1" src="' + url + '" /><input type="file" name="mechanismImages[' + mechanismImageIndex + '].file" class="productImageFile" \/><button class="removeBtn" onclick="javascript:removeImg(this)">x</button></div>';
					[/@compress]
					    $("#img_div").append(html);
						mechanismImageIndex ++;
						fals = false;
				}
			});
	});
	
		
	
	$.validator.addClassRules({
		logo: {
			required: true,
			extension: "${setting.uploadImageExtension}"
		},
		introduceImg: {
			required: true,
			extension: "${setting.uploadImageExtension}"
		},
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			logo_img: "required",
			mechanismCategoryId: "required",
			mechanismRankId: "required",
			areaId: "required",
		    address: "required",
		    phone: {
				pattern: /^0\d{2,3}-?\d{7,8}$/,
				
			},
			startTime: "required",
			endTime: "required",
		},
		messages: {
			phone: {
				pattern: "电话号码有误"
			}
		},
		
		//submitHandler: function(form) {
		
		//if ($("input[name='managementIds']:checked").length == 0) {
		//	$.message("warn", "${message("至少选择一种经营模式")}");
		//	return false;
		//}
		
			//form.submit();
		//}
	});
	
});

//上传预览
function update_introduce_img(upfile,upimg,pid,flag)
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
						$("#up_" + upimg).attr('onclick','update_introduce_img("' + upfile + '","' + upimg + '",' + pid +',"' + flag +'")');
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
	$("#up_" + upimg).attr('onclick','update_introduce_img("' + upfile +'","' + upimg + '",'+ (count-1) +',"2")');
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

    	<div class="nav">管理导航：<a href="javascript:;">管理首页</a>　<a href="企业信息.html">企业信息</a>　<a href="企业认证.html">企业认证</a>　<a href="服务时间.html">服务时间</a>　<a href="服务项目.html">服务项目</a>　<a href="我的账号.html">我的账号</a>　<a href="评价信息.html">评价信息</a></div>
        <div class="seat">
        	<div class="left_z">企业信息</div>
            <div class="export"><font class="z_14_ff0000_1">*</font> 为必填项
            </div>
        
        </div>
        <div class="detail">
        <form  id="inputForm" action="${base}/mechanism/mechanism/save.jhtml" method="post" enctype="multipart/form-data">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_323232_1">
	          <tr>
	            <td colspan="2">&nbsp;</td>
	          </tr>
	          <tr>
	            <td width="150" height="40" align="right" valign="top" class="p_t_5_1">企业名称<span class="z_14_ff0000_1">*</span></td>
	            <td height="40" valign="top" class="p_l_r_20_1"><input type="text" name="name" id="textfield" class="inp_1 w_450_1" /></td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top">企业logo<span class="z_14_ff0000_1">*</span></td>
	            <td height="40" valign="top" class="p_l_r_20_1 p_b_10_1">
	            <img id="logo_img"src='${base}/resources/mechanism/images/j_i_1.png'  alt="" width="80" height="80" class="u_img_1" onclick="update_introduce_img('logo','logo_img',0,'1');" /><input id="logo_0" name="logo_img" type="file" style="display: none"  /></td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top" class="p_t_5_1">机构类型<span class="z_14_ff0000_1">*</span></td>
	            <td height="40" valign="top" class="p_l_r_20_1">
		            <span class="fieldSet">
							<input type="hidden" id="mechanismCategoryId" name="mechanismCategoryId"  />
					</span>
	            </td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top">机构级别<span class="z_14_ff0000_1">*</span></td>
	            <td height="40" valign="top" class="p_l_r_20_1">
		             <span class="fieldSet">
						<input type="hidden" id="mechanismRankId" name="mechanismRankId" />
				  	 </span>
	            </td>
	          </tr>
		      <tr>
	            <td width="150" height="40" align="right" valign="top" class="p_t_5_1">工作时间<span class="z_14_ff0000_1">*</span></td>
	            <td height="40" valign="top" class="p_l_r_20_1">
	            <input type="text" name="startTime" id="startTime" class="inp_1 " maxlength="200" value="" onfocus="WdatePicker({dateFmt: 'HH:mm',minDate:'08:00:00', maxDate: '#F{$dp.$D(\'endTime\')}'});" />
	            -
	            <input type="text" name="endTime" id="endTime" class="inp_1 " maxlength="200"  value="" onfocus="WdatePicker({dateFmt: 'HH:mm', minDate: '#F{$dp.$D(\'startTime\')}'});"  />
	            </td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top">经营模式<span class="z_14_ff0000_1">*</span></td>
	            <td height="40" valign="top" class="p_l_r_20_1">
		            <div id="managementSelect" >
			            [#list managements as management]
			            <input name="managementIds" type="checkbox" id="pattern" value="${management.id}" /> ${management.name}
			            [/#list]
			        </div>
	            </td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top">机构地址<span class="z_14_ff0000_1">*</span></td>
	            <td height="40" valign="top" class="p_l_r_20_1">
		            <span class="fieldSet">
						<input type="hidden" id="areaId" name="areaId"  />
					</span>
	            <br />
	            <input name="address" type="text" class="inp_1 w_450_1 m_t_5_1 m_b_10_1" id="address" value="" /></td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top">机构图片<span class="z_14_ff0000_1">*</span></td>
	            <td height="40" valign="top" class="p_l_r_20_1 p_b_10_1">
	            <div class="img_div">
				</div>
				<img id="up_img_div" src="${base}/resources/mechanism/images/j_i_1.png" alt="" width="80" height="80" class="u_img_1" onclick="update_introduce_img('mechanismImages','img_div',0,'2')" />
				<div id="img_div_file">
				<input id="mechanismImages_0" name="mechanismImages[0].file" type="file" style="display: none"  />
				</div>
	            
	            </td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top">机构简介</td>
	            <td height="40" valign="top" class="p_l_r_20_1 p_b_10_1"><textarea name="introduce" id="introduce" rows="5" class="inp_2 h_80_1 w_450_1"></textarea></td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top">联系电话<span class="z_14_ff0000_1">*</span></td>
	            <td height="40" valign="top" class="p_l_r_20_1 z_12_999999_1"><input name="global_code" type="text" class="inp_3 h_28_1 w_20_1" id="global_code" value="86" onfocus="keyfocus('86','global_code');" onblur="keyfocus('86','global_code');" />
	              —
	              <input name="phone" type="text" class="inp_3 h_28_1 w_120_1" id="tel" value="电话号码" onfocus="keyfocus('电话号码','tel');" onblur="keyfocus('电话号码','tel');" /></td>
	          </tr>
	          <tr>
	            <td height="10" align="right" valign="top">&nbsp;</td>
	            <td height="10" valign="top">&nbsp;</td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top">&nbsp;</td>
	            <td height="40" valign="top" class="p_l_r_20_1">
	              <input type="submit" name="button7" id="button7" value=" 提 交 " class="button_1 b_4188d1_1" />　<input type="reset" name="button7" id="button7" value=" 重 置 " class="button_1 b_ececec_1 z_12_707070_1" />
	            </td>
	          </tr>
	        </table>
        </form>
        </div>      
     <div class="clear"></div>

</body>
</html>