<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>基本信息</title>
<!--
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/common.css" rel="stylesheet" type="text/css" />

<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
-->
<link href="${base}/resources/newmechanism/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	var $mechanismCategoryId = $("#mechanismCategoryId");//机构类型
	var $mechanismRankId = $("#mechanismRankId");//机构等级
	var $mechanismAreaId = $("#mechanismAreaId");//机构所在地区
	
	var $doctorBirthplaceId = $("#doctorBirthplaceId");//医师户籍地区
	var $doctorAreaId = $("#doctorAreaId");//医师现住地区
	
	
	// 机构所在地区选择
	$mechanismAreaId.lSelect({
		url: "area.jhtml"
	});
	
	// 医师户籍地区
	$doctorBirthplaceId.lSelect({
		url: "area.jhtml"
	});
	
	// 医师现住地区
	$doctorAreaId.lSelect({
		url: "area.jhtml"
	});
	
	// 机构类型
	$mechanismCategoryId.lSelect({
		url: "mechanismCategory.jhtml"
	});
	
	// 机构等级
	$mechanismRankId.lSelect({
		url: "mechanismRank.jhtml"
	});
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
		rules: {
			password: {
					required: true,
					pattern: /^[^\s&\"<>]+$/,
					minlength: 6
				},
			rePassword: {
				required: true,
				equalTo: "#password"
			},
			name:"required",
			mechanism_logo_img:"required",
			mechanismCategoryId:"required",
			mechanismRankId:"required",
			mechanismAreaId:"required",
			mechanismAddress:"required",
			introduce:"required",
			doctorName:"required",
		},	
		messages: {
			password: {
				pattern: "${message("shop.register.passwordIllegal")}"
			}
		},
		
		submitHandler: function(form) {
		
		 
		 var isAbout = $("#isAbout").prop("checked");
		 
		 if(isAbout){
			  if($("#doctorBirth").val()==""){
			 	$.message("warn", "请填写出生日期");
			  	return ;
			 }
			  if($("#doctorBirthplaceId").val()==""){
			  	$.message("warn", "户籍地址必选");
			 	return ;
			 }
			  if($("#doctorBirthplaceAddress").val()==""){
			    $.message("warn", "户籍详细地址必填");
			 	return ;
			 }
			  if($("#doctorAreaId").val()==""){
			  	$.message("warn", "现住地址必选");
			 	return ;
			 }
			  if($("#doctorAddress").val()==""){
			  	$.message("warn", "现住详细地址必填");
			 	return ;
			 }
			  if($("#doctorIntroduce").val()==""){
			  	$.message("warn", "个人简介必填");
			 	return ;
			 }
			
		 }
		
			form.submit();
		}
	});
	
	//患者可通过“好康护”用户端预约我的项目
	$("#isAbout").click(function(){
		$("#appo").toggle();
	});
	
});

</script>

<script>
//上传预览
function update_introduce_img(upfile,upimg,pid,flag)
{
	$("#" + upfile + "_" + pid).click();
	$("#" + upfile + "_" + pid).on("change",function(){
		var url=getObjectURL(this.files[0]);

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
						tmpstr="<div class=\"isImg\"><img class = \"h80 w80\" src=\"" + url + "\" /><button id=\"" + upimg + "_b_" + pid + "\" class=\"removeBtn\" onclick=\"javascript:removeImg('"+ upimg +"','"+ upfile +"',"+ pid +")\">x</button></div>"
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

<table cellpadding="0" cellspacing="0" width="1200" border="0" align="center" class="m0auto">
	<tr>
		<td width="150" height="90" align="left">
			<img src="${base}/resources/mechanism/images/reglogo.png">
		</td>
		<td width="12" align="left">
			<span class="spanline"></span>
		</td>
		<td class="z16636363">
			机构账户注册
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="bgf9f9f9">
	<tr>
		<td class="ptb50">
			<table cellpadding="0" cellspacing="0" border="0" width="1200" align="center" class="m0auto z14b9b9b9">
				<tr>
					<td width="33%" height="90" class="pr">
						<hr class="pa bb4e96f4 w100 h1 mt64 z1 wzt0l0">
						<table cellpadding="0" cellspacing="0" border="0" width="80" class="z2 pa wzt0l5040">
							<tr>
								<td height="40" class="z144e96f4" align="center">用户名设置</td>
							</tr>
							<tr>
								<td align="center">
									<span class="z20ffffff spanround bg4e96f4">01</span>
								</td>
							</tr>
						</table>
					</td>
					<td width="33%" class="pr">
						<hr class="pa bb4e96f4 w100 h1 mt64 z1 wzt0l0">
						<table cellpadding="0" cellspacing="0" border="0" class="z2 pa wzt0l5040">
							<tr>
								<td height="40" class="z144e96f4" align="center">账号设置</td>
							</tr>
							<tr>
								<td align="center">
									<span class="z20ffffff spanround bg4e96f4">02</span>
								</td>
							</tr>
						</table>
					</td>
					<td width="33%" class="pr">
						<hr class="pa bbcccccc w100 h1 mt64 z1 wzt0l0">
						<table cellpadding="0" cellspacing="0" border="0" class="z2 pa wzt0l5040">
							<tr>
								<td height="40" align="center">注册成功</td>
							</tr>
							<tr>
								<td align="center">
									<span class="z20ffffff spanround bgdddddd">03</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="50" colspan="3"></td>
				</tr>
				<tr>
					<td colspan="3">
					
						<table cellpadding="0" cellspacing="0" border="0" width="800" align="center" class="m0auto k_1">
							<tr>
								<td class="pa55">
								<form id = "inputForm" action = "save.jhtml" method="post" enctype="multipart/form-data">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr>
											<td class="z165d5d5d" height="40" colspan="2">
												设置登录密码
											</td>
										</tr>
										<tr>
											<td class="bae5e5e5" colspan="2">
												<input type="password" placeholder="登录密码" id= "password" name="password" class="banone ma20 w400 z14666666">
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="bae5e5e5" colspan="2">
												<input type="password" placeholder="密码确认" id = "rePassword" class="banone ma20 w400 z14666666">
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="z165d5d5d" height="40" colspan="2">
												企业信息
											</td>
										</tr>
										<tr>
											<td class="bae5e5e5" colspan="2">
												<input type="text" placeholder="企业名称（必填）" name = "name" class="banone ma20 w400 z14666666">
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="pa20 z14757575">机构LOGO（必传）</td>
											<td>
												<img id="mechanism_logo_img"src='${base}/resources/mechanism/images/j_i_1.png'  alt="" width="80" height="80" class="u_img_1" onclick="update_introduce_img('mechanism_logo','mechanism_logo_img',0,'1');" />
												<input id="mechanism_logo_0" name="mechanism_logo_img" type="file" style="display: none"  />
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="btble5e5e5 pa20 z14757575" width="150">
												机构类型（必选）
											</td>
											<td class="btbre5e5e5">
												<span class="fieldSet">
														<input type="hidden" id="mechanismCategoryId" name="mechanismCategoryId"  />
												</span>
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="btble5e5e5 pa20 z14757575" width="150">
												机构等级（必选）
											</td>
											<td class="btbre5e5e5">
												 <span class="fieldSet">
													<input type="hidden" id="mechanismRankId" name="mechanismRankId" />
											  	 </span>
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="btble5e5e5 pa20 z14757575" width="150">
												所在地区（必选）
											</td>
											<td class="btbre5e5e5">
												 <span class="fieldSet">
													<input type="hidden" id="mechanismAreaId" name="mechanismAreaId"  />
												</span>
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="bae5e5e5" colspan="2">
												<input type="text" placeholder="详细地址（必填）" name = "address" class="banone ma20 w400 z14666666">
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="pa20 z14757575">机构图片（必传）</td>
											<td>
												<div class="img_div">
												</div>
												<img id="up_img_div" src="${base}/resources/mechanism/images/j_i_1.png" alt="" width="80" height="80" class="u_img_1" onclick="update_introduce_img('mechanismImages','img_div',0,'2')" />
												<div id="img_div_file">
												<input id="mechanismImages_0" name="mechanismImages[0].file" type="file" style="display: none"  />
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="bae5e5e5" colspan="2">
												<textarea placeholder="企业简介（必填）" name = "introduce" class="banone ma20 w630 h80 z14666666"></textarea>
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="z165d5d5d" height="40" colspan="2">
												联系人信息
											</td>
										</tr>
										<tr>
											<td class="btble5e5e5 pa20 z14757575" width="150">
												手机号（必填）
											</td>
											<td class="btbre5e5e5">
												<input type="text" value="${username}" name = "username" class="banone ma20 w400 z14666666">
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="bae5e5e5" colspan="2">
												<input type="text" placeholder="您的姓名（必填）" name="doctorName" class="banone ma20 w400 z14666666">
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2"></td>
										</tr>
										<tr>
											<td class="btble5e5e5 pa20 z14757575" width="150">
												您的性别（必选）
											</td>
											<td class="btbre5e5e5">
											[#list genders as gender]
											<input type="radio" id="sex" name="doctorGender" [#if gender_index==0] checked [/#if] value="${gender}"> ${message("Member.Gender."+gender)} 
												&nbsp;　&nbsp;
											[/#list]
											</td>
										</tr>
										<tr>
											<td colspan="2" height="30" class="z14666666">
												<input type="checkbox" id="isAbout"> 患者可通过“好康护”用户端预约我的项目
											</td>
										</tr>
										<tr>
											<td colspan="2" id="appo" class="dpn">
												<table cellpadding="0" cellspacing="0" border="0" width="100%">
													<tr>
														<td height="20" colspan="2"></td>
													</tr>
													<tr>
														<td class="bae5e5e5" colspan="2">
															<input type="text" placeholder="出生日期（必填）" id = "doctorBirth"  name="doctorBirth" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-%d'});" class="banone ma20 w400 z14666666">
														</td>
													</tr>
													<tr>
														<td height="20" colspan="2"></td>
													</tr>
													<tr>
														<td class="btble5e5e5 pa20 z14757575" width="150">
															户籍地址（必选）
														</td>
														<td class="btbre5e5e5">
															<span class="fieldSet">
																<input type="hidden" id="doctorBirthplaceId" id = "doctorBirthplaceId" name="doctorBirthplaceId"  />
															</span>
														</td>
													</tr>
													<tr>
														<td height="20" colspan="2"></td>
													</tr>
													<tr>
														<td class="bae5e5e5" colspan="2">
															<input type="text" placeholder="详细地址..." id = "doctorBirthplaceAddress" name = "doctorBirthplaceAddress" class="banone ma20 w400 z14666666">
														</td>
													</tr>
													<tr>
														<td height="20" colspan="2"></td>
													</tr>
													<tr>
														<td class="btble5e5e5 pa20 z14757575" width="150">
															现住地址（必选）
														</td>
														<td class="btbre5e5e5">
															<span class="fieldSet">
																<input type="hidden" id="doctorAreaId" name="doctorAreaId"  />
															</span>
														</td>
													</tr>
													<tr>
														<td height="20" colspan="2"></td>
													</tr>
													<tr>
														<td class="bae5e5e5" colspan="2">
															<input type="text" placeholder="详细地址..." id = "doctorAddress" name="doctorAddress" class="banone ma20 w400 z14666666">
														</td>
													</tr>
													<tr>
														<td height="20" colspan="2"></td>
													</tr>
													<tr>
														<td class="bae5e5e5" colspan="2">
															<textarea placeholder="个人简介（必填）" id = "doctorIntroduce" name = "doctorIntroduce" class="banone ma20 w630 h80 z14666666"></textarea>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="60" colspan="2">
												
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<input type="submit" value="保存" class="cp bg4e96f4 z14ffffff w690 h40 br5">
											</td>
										</tr>
									</table>
									</form>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="50" colspan="3"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="0" width="1200" border="0" align="center" class="m0auto">
	<tr>
		<td height="70" align="center" class="z14b4b4b4">
			©2017 好康护
		</td>
	</tr>
</table>

</body>
</html>