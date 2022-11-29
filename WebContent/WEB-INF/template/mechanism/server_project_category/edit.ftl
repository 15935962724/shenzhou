<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

<script type="text/javascript">

$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
    
         
     $('#parentId').click(function(){
     	var parentId = $('#parentId').val();
		     if(parentId!=""){
		       console.log(parentId);
		        $.ajax({
		             type: "POST",
		             url: "query.jhtml",
		             data: {
		             parentId:parentId
		             },
		             dataType: "json",
		             success: function(data){
		             var last=data.data; 
		             var dataObj=eval("("+last+")");
				             console.log(dataObj);
				             console.log(dataObj.name);
				             $("#name").val(dataObj.name);      
				             $("#seoDescription").val(dataObj.seoDescription);  
				             $("#logoImg").attr("src",dataObj.logo); 
				             //$("#logo").val(dataObj.logo);  
				             $("#introduceImg").attr("src",dataObj.introduceImg); 
				             
				             
				             
		               }
		         });
		     }
    });
    

    

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			parentId: "required",
			
			
		},
		
		submitHandler: function(form) {
			form.submit();
		}
		
		
	});
	
	
});
</script>
<script type="text/javascript">


	//上传预览
	function upload_logo_img(upfile,upimg)
	{
		$("#" + upfile ).click();
		$("#" + upfile ).on("change",function(){
			url=getObjectURL(this.files[0]);
			if (url) 
				{
					if(upimg!="")
					{
						$("#" + upimg).attr("src", url) ; 
					}
				}
		});
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
						<td class="z20616161 bb1dd4d4d4" height="50">服务项目</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="list.jhtml">返回</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" value="${serverProjectCategory.id}" />
							<table cellpadding="0" cellspacing="0" border="0" width="600" class="z14444444">
								<tr>
									<td height="50" width="120" align="right">项目类别：<span class="z14ff0000">*</span></td>
									<td colspan="2">
										<select id = "parentId" name="parentId" class="h30 w150 bg279fff z14ffffff baffffff m1020">
										[#list serverProjectCategorys as category]
											<option [#if serverProjectCategory.parent==category] selected="true"[/#if] value = "${category.id}">${category.name}</option>
										[/#list]
										</select>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">项目名称：<span class="z14ff0000">*</span></td>
									<td colspan="2">
										<input type="text"  id="name" name="name" value="${serverProjectCategory.name}" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w370 m1020">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">详情描述：<span class="z14ff0000">*</span></td>
									<td colspan="2">
										<textarea id="seoDescription" name = "seoDescription" class="z14323232 inputkd9d9d9bgf6f6f6 w370 h80 m1020">${serverProjectCategory.seoDescription}</textarea>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">Logo图片：<span class="z14ff0000">*</span></td>
									<td align="left" width="120">
										<img id = "logo_img" onerror = "this.src='${base}/resources/mechanism/images/j_i_1.png'" src="${serverProjectCategory.logo}" onclick="upload_logo_img('logo_file','logo_img');"  class="m1020 w80 h80">
										<input id="logo_file" name="logo_file" type="file" style="display: none"  /> 
									</td>
									<td align="left" valign="bottom" class="ptb10 z12939393">
										长宽比为750：280
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">列表页图片：<span class="z14ff0000">*</span></td>
									<td align="left">
										<img id = "introduceImg" onerror = "this.src='${base}/resources/mechanism/images/j_i_1.png'" src="${serverProjectCategory.introduceImg}" onclick="upload_logo_img('introduceImg_file','introduceImg',);" class="m1020 w80 h80"> 
										<input id="introduceImg_file" name="introduceImg_file" type="file" style="display: none"  /> 
									</td>
									<td align="left" valign="bottom" class="ptb10 z12939393">
										长宽比为140：140
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right"></td>
									<td colspan="2">
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