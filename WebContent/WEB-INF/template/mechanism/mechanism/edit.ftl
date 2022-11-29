<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>基本信息</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>


<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	var $areaId = $("#areaId");
	var $name = $("#name");
	var $address = $("#address");
	var $phone = $("#phone");
	var $mechanismCategoryId = $("#mechanismCategoryId");
	var $mechanismRankId = $("#mechanismRankId");
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
	
	var mechanismIntroduceImgpath = "/upload/mechanism/introduceImg/";
	
	//$introduceImgBrowserButton.uploadImg(mechanismIntroduceImgpath);
	

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
			mechanismCategoryId: "required",
			mechanismRankId: "required",
			areaId: "required",
		    address: "required",
		    phone: {
				pattern: /^0\d{2,3}-?\d{7,8}$/,
			},
		},
		messages: {
			phone: {
				pattern: "电话号码有误"
			}
		},
		submitHandler: function(form) {
				form.submit();
		}
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
						tmpstr="<div class=\"isImg\"><img class=\"m1020 w80 h80\" src=\"" + url + "\" /><button id=\"" + upimg + "_b_" + pid + "\" class=\"removeBtn\" style=\"top:10px; right:-20px;\" onclick=\"javascript:removeImg('"+ upimg +"','"+ upfile +"',"+ pid +")\">x</button></div>"
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
	if($("#" + upfile + pid)){
		$("#" + upfile + pid).remove();
	}
	else{
		$("#" + upfile + "_" + pid).remove();
		$("#up_" + upimg).attr('onclick','update_introduce_img("' + upfile +'","' + upimg + '",'+ (count-1) +',"2")');
		for(var i=(pid+1);i<=count;i++)
			{
				var tmp_pid=i-1;
				$("#" + upfile + "_" + i).attr({'name':upfile + '_' + tmp_pid,'id':upfile + '_' + tmp_pid});
				$("#" + upimg + "_b_" + i).attr({'onclick':'removeImg("' + upimg + '","' + upfile +'",'+ tmp_pid + ')','id':upimg + '_b_' + tmp_pid});
			}
	}
	
	
}


function getObjectURL(file) {
	var url = null;
	if(window.createObjectURL != undefined) { // basic
		url = window.createObjectURL(file);
	} else if(window.URL != undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file);
	} else if(window.webkitURL != undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file);
	}
	//console.log(url);
	return url;
}

</script>


</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id = "page_nav">
		<tr>
			
		</tr>
	</table>
	
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">企业资料</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
							 <input type="hidden" name="id" value="${mechanism.id}" />
							<table cellpadding="0" cellspacing="0" border="0" width="600" class="z14444444">
								<tr>
									<td height="50" colspan="2"><span class="z14ff0000">*</span> 提示：您暂未提交机构信息/资料暂未通过审核，请耐心等待</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">企业名称：<span class="z14ff0000">*</span></td>
									<td>
										<input type="text" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w420 m1020" name="name" value="${mechanism.name}" readonly>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">企业LOGO：<span class="z14ff0000">*</span></td>
									<td>
										<img id="logo_img" src="${mechanism.logo}" onerror="this.src='${base}/resources/newmechanism/images/k.png'" class="m1020 h80 w80"  onclick="update_introduce_img('logo','logo_img',0,'1');" />
										<input id="logo_0" name="logo_img" type="file" style="display: none"  />
										<input type="hidden" name="logo" value="${mechanism.logo}" />
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">机构类型：<span class="z14ff0000">*</span></td>
									<td>
										  <span class="fieldSet">
												<input type="hidden" id="mechanismCategoryId" name="mechanismCategoryId" value="${(mechanism.mechanismCategory.id)!}" treePath="${(mechanism.mechanismCategory.treePath)!}"  />
										  </span>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">机构等级：<span class="z14ff0000">*</span></td>
									<td>
										  <span class="fieldSet">
												<input type="hidden" id="mechanismRankId" name="mechanismRankId" value="${(mechanism.mechanismRank.id)!}" treePath="${(mechanism.mechanismRank.treePath)!}" />
										  </span>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">企业地址：<span class="z14ff0000">*</span></td>
									<td>
										  <span class="fieldSet">
											<input type="hidden" id="areaId" name="areaId"  value="${(mechanism.area.id)!}" treePath="${(mechanism.area.treePath)!}"  />
										  </span>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">企业地址：<span class="z14ff0000">*</span></td>
									<td>
										  <input type="text" name="address" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020" value="${mechanism.address}">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">企业介绍：<span class="z14ff0000">*</span></td>
									<td>
										<textarea name="introduce" class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020">${mechanism.introduce}</textarea>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">机构图片：<span class="z14ff0000">*</span></td>
									<td>
										
										 <div class="img_div">
								            [#list mechanism.mechanismImages as mechanismImage]
									            <div class="isImg">
										            <img class="m1020 h80 w80" src="${mechanismImage.source}">
										            <button id="img_div_b_${mechanismImage_index}" style="top:10px;right:-20px;" class="removeBtn" onclick="javascript:removeImg('img_div','mechanismImages',${mechanismImage_index})">x</button>
									            </div>
								            [/#list]
											</div>
											<img id="up_img_div" class="m1020 h80 w80" src="${base}/resources/mechanism/images/j_i_1.png" alt="" onclick="update_introduce_img('mechanismImages','img_div',${mechanism.mechanismImages.size()},'2')" />
											<div id="img_div_file">
												[#list mechanism.mechanismImages as mechanismImage]
												<div id="mechanismImages${mechanismImage_index}">
													<input type="hidden" name="mechanismImages[${mechanismImage_index}].source" value="${mechanismImage.source}" />
													<input type="hidden" name="mechanismImages[${mechanismImage_index}].large" value="${mechanismImage.large}" />
													<input type="hidden" name="mechanismImages[${mechanismImage_index}].medium" value="${mechanismImage.medium}" />
													<input type="hidden" name="mechanismImages[${mechanismImage_index}].thumbnail" value="${mechanismImage.thumbnail}" />
											        <input id="mechanismImages_${mechanismImage_index}" name="mechanismImages[${mechanismImage_index}].file" type="file" style="display: none"  />
												</div>
												[/#list]
								            <input id="mechanismImages_${mechanism.mechanismImages.size()}" name="mechanismImages[${mechanism.mechanismImages.size()}].file" type="file" style="display: none"  />
											</div>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">联系电话：<span class="z14ff0000">*</span></td>
									<td>
										<input type="text" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020" name="phone" value="${mechanism.phone}">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right"></td>
									<td>
										<input type="submit" value="确认" class="button_3 ml20">
										&nbsp;　&nbsp;
										<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#inputForm')[0].reset();">
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