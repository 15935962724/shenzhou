<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Haokanghu.Main.tile")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>

<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<style type="text/css">
	.specificationSelect {
		height: 100px;
		padding: 5px;
		overflow-y: scroll;
		border: 1px solid #cccccc;
	}
	
	.specificationSelect li {
		float: left;
		min-width: 150px;
		_width: 200px;
	}
</style>
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
	<div class="path">
		<a href="">${message("admin.path.index")}</a> &raquo;企业认证信息
	</div>
	<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.product.base")}" />
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>企业名称:
				</th>
				<td>
					<input type="text" name="title" class="text" maxlength="200" />
				</td>
			</tr>
			
			<tr>
				<th>
					<span class="requiredField">*</span>企业注册地:
				</th>
				<td>
					<span class="fieldSet">
						<input type="hidden" id="areaId" name="areaId" />
					</span>
				</td>
			</tr>		
			
			<tr>
				<th>
					详细地址:
				</th>
				<td>
					<input type="text" name="address" class="text" maxlength="100" title="" />
				</td>
			</tr>
			<tr>
				<th>
					证件图片:
				</th>
				<td>
				<input type="hidden" name="certificatesImg"  id = "certificatesImg"   value=""  class="text"  title="" />
				<img src="${base}/resources/mechanism/images/add_img.png" onerror="this.src='${base}/resources/mechanism/images/add_img.png'" id = "image" class="img-rounded" width="90px" height="90px"/>
        		<input type="file" id="browserButton" multiple="multiple" class="image-input"  onchange="uploadCertificatesImg();"  accept ="image/*" style="width: 6rem;height: 6rem; margin-left: -6.0rem;opacity: 0;"/>
				</td>
			</tr>
			
			<tr>
				<th>
					<span class="requiredField">*</span>机构法人代表:
				</th>
				<td>
					<input type="text" name="name" class="text" maxlength="100" title="" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>开户银行:
				</th>
				<td>
					<input type="text" name="brank" class="text" maxlength="100" title="" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>账户:
				</th>
				<td>
					<input type="text" name="account" class="text" maxlength="100" title="" />
				</td>
			</tr>
		</table>
		
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
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
			form.append("id","form");
			form.append("name","form");
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