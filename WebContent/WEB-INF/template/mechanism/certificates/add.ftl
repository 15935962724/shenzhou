<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Haokanghu.Main.title")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
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
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript">
$().ready(function() {


	var $inputForm = $("#inputForm");
	
	var $title = $("#title");
	var $areaId = $("#areaId");
	var $address = $("#address");
	var $name = $("#name");
	var $brank = $("#brank");
	var $account = $("#account");
	
	
	
	// 地区选择
	$areaId.lSelect({
		url: "${base}/mechanism/common/area.jhtml"
	});
	
	
	[@flash_message /]
	
	
	

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
			title: "required",
			areaId: "required",
			address: "required",
			name: "required",
		    brank: "required",
		    account: "required",
		},
		
		submitHandler: function(form) {
					form.submit();
		}
	});
	
});
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
						<td class="z20616161 bb1dd4d4d4" height="50">企业认证</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data" >
							<table cellpadding="0" cellspacing="0" border="0" width="600" class="z14444444">
								<tr>
									<td height="50" colspan="2"><span class="z14ff0000">*</span> 提示：您暂未提交机构信息/资料暂未通过审核，请耐心等待</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">企业名称：<span class="z14ff0000">*</span></td>
									<td>
										<input type="text" name="title" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">企业注册地：<span class="z14ff0000">*</span></td>
									<td>
										<span class="fieldSet">
											<input type="hidden" id="areaId" name="areaId"  value="${(mechanism.area.id)!}" treePath="${(mechanism.area.treePath)!}"  />
										  </span>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">详细地址：<span class="z14ff0000">*</span></td>
									<td>
										<input type="text" name="address" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">证件图片：<span class="z14ff0000">*</span></td>
									<td class="pr">
										<input type="hidden" name="certificatesImg"  id = "certificatesImg"   value=""  class="text"  title="" />
										<img src="${base}/resources/mechanism/images/j_i_1.png" id = "image" class="m1020 h80 w80">
										<input type="file" id="browserButton" multiple="multiple" onchange="uploadCertificatesImg();"  accept ="image/*" class="pa m1020 w80 h80" style="left: 0px;top: 0px; opacity: 0;"/>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">法定代表人：<span class="z14ff0000">*</span></td>
									<td>
										<input type="text" name="name" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">开户银行：<span class="z14ff0000">*</span></td>
									<td>
										<input type="text" name="brank" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">账户：<span class="z14ff0000">*</span></td>
									<td>
										<input type="text" name="account" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020">
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
<script type="text/javascript">

function uploadCertificatesImg(){

	    var token = getCookie("token");
		var fileObj = document.getElementById("browserButton");
		var files = fileObj.files;
		var form = new FormData();
			form.append("fileName",files[0]);
			form.append("token",token);
			form.append("encType","multipart/form-data");
			
			var input_hidden = document.createElement("input");
			input_hidden.type="hidden";
			input_hidden.value=token;
			input_hidden.name="token";
			form.append("input_hidden",input_hidden);
			var xhr = new XMLHttpRequest();
			xhr.open("post","uploadCertificatesImg.jhtml",false);
			//监听请求状态  200 成功
			xhr.onreadystatechange = function(){
			   if(xhr.readyState == 4 && xhr.status == 200){
			      var result = xhr.responseText.trim();
			       $("#image").attr("src",result);
			      $("#certificatesImg").val(result);
			   }else{
			    $("#image").attr("src",result);
			      $("#certificatesImg").val(result);
			   }
			}
			xhr.send(form);
				
		}


</script>


</html>