<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.setting.edit")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	var $inputForm = $("#inputForm");
	var $browserButton = $("input.browserButton");
	var $smtpFromMail = $("#smtpFromMail");
	var $smtpHost = $("#smtpHost");
	var $smtpPort = $("#smtpPort");
	var $smtpUsername = $("#smtpUsername");
	var $smtpPassword = $("#smtpPassword");
	var $toMailWrap = $("#toMailWrap");
	var $toMail = $("#toMail");
	var $mailTest = $("#mailTest");
	var $mailTestStatus = $("#mailTestStatus");
	
	[@flash_message /]
	
	$browserButton.browser();
	
	//首次加载页面判断是否一次发放还是二次发放
	var pointGrantTypeVal = '${setting.pointGrantType}'; 
		if(pointGrantTypeVal == "secondary"){
			$('.strong').show();
		}else{
			$('.strong').hide();
		}
	
	//积分奖励类型
	$(".pointGrantType").click(function(){
		var pointGrantTypeVal = $(this).val();
		if(pointGrantTypeVal == "secondary"){
			$('.strong').show();
		}else{
			$('.strong').hide();
		    $('#secondaryDoctorInvitationMemberPoint').val('0');
		    $('#secondaryMemberInvitationMemberPoint').val('0');
		}
	});
	
	// 邮件测试
	$mailTest.click(function() {
		var $this = $(this);
		if ($this.val() == "${message("admin.setting.mailTest")}") {
			$this.val("${message("admin.setting.sendMail")}");
			$toMailWrap.show();
		} else {
			function valid(element) {
				return $inputForm.validate().element(element);
			}
			$.ajax({
				url: "mail_test.jhtml",
				type: "POST",
				data: {smtpFromMail: $smtpFromMail.val(), smtpHost: $smtpHost.val(), smtpPort: $smtpPort.val(), smtpUsername: $smtpUsername.val(), smtpPassword: $smtpPassword.val(), toMail: $toMail.val()},
				dataType: "json",
				cache: false,
				beforeSend: function() {
					if (valid($smtpFromMail) & valid($smtpHost) & valid($smtpPort) & valid($smtpUsername) & valid($toMail)) {
						$mailTestStatus.html('<span class="loadingIcon">&nbsp;<\/span>${message("admin.setting.sendMailLoading")}');
						$this.prop("disabled", true);
					} else {
						return false;
					}
				},
				success: function(message) {
					$mailTestStatus.empty();
					$this.prop("disabled", false);
					$.message(message);
				}
			});
		}
	});
	
	$.validator.addMethod("compareLength", 
		function(value, element, param) {
			return this.optional(element) || $.trim(value) == "" || $.trim($(param).val()) == "" || parseFloat(value) >= parseFloat($(param).val());
		},
		"${message("admin.setting.compareLength")}"
	);
	
	// 表单验证
	$inputForm.validate({
		rules: {
			siteName: "required",
			siteUrl: "required",
			logo: "required",
			shareTitle: "required",
			shareDescribe: "required",
			shareImage: "required",
			email: "email",
			siteCloseMessage: "required",
			largeProductImageWidth: {
				required: true,
				integer: true,
				min: 1
			},
			largeProductImageHeight: {
				required: true,
				integer: true,
				min: 1
			},
			mediumProductImageWidth: {
				required: true,
				integer: true,
				min: 1
			},
			mediumProductImageHeight: {
				required: true,
				integer: true,
				min: 1
			},
			thumbnailProductImageWidth: {
				required: true,
				integer: true,
				min: 1
			},
			thumbnailProductImageHeight: {
				required: true,
				integer: true,
				min: 1
			},
			defaultLargeProductImage: "required",
			defaultMediumProductImage: "required",
			defaultThumbnailProductImage: "required",
			watermarkAlpha: {
				required: true,
				digits: true,
				max: 100
			},
			watermarkImageFile: {
				extension: "${setting.uploadImageExtension}"
			},
			
			usernameMinLength: {
				required: true,
				integer: true,
				min: 1,
				max: 117
			},
			usernameMaxLength: {
				required: true,
				integer: true,
				min: 1,
				max: 117,
				compareLength: "#usernameMinLength"
			},
			passwordMinLength: {
				required: true,
				integer: true,
				min: 1,
				max: 117
			},
			passwordMaxLength: {
				required: true,
				integer: true,
				min: 1,
				max: 117,
				compareLength: "#passwordMinLength"
			},
			registerPoint: {
				required: true,
				integer: true,
				min: 0
			},
			registerAgreement: "required",
			accountLockCount: {
				required: true,
				integer: true,
				min: 1
			},
			accountLockTime: {
				required: true,
				digits: true
			},
			safeKeyExpiryTime: {
				required: true,
				digits: true
			},
			uploadMaxSize: {
				required: true,
				digits: true
			},
			imageUploadPath: "required",
			flashUploadPath: "required",
			mediaUploadPath: "required",
			fileUploadPath: "required",
			smtpFromMail: {
				required: true,
				email: true
			},
			smtpHost: "required",
			smtpPort: {
				required: true,
				digits: true
			},
			smtpUsername: "required",
			toMail: {
				required: true,
				email: true
			},
			firstDoctorInvitationMemberPoint: {
				required: true,
				integer: true,
				min: 0
			},
			secondaryDoctorInvitationMemberPoint: {
				required: true,
				integer: true,
				min: 0
			},
			doctorInvitationMemberPoint: {
				required: true,
				integer: true,
				min: 0
			},
			doctorPointDay: {
				required: true,
				integer: true,
				min: 1
			},
			firstMemberInvitationMemberPoint: {
				required: true,
				integer: true,
				min: 0
			},
			memberPointDay: {
				required: true,
				integer: true,
				min: 1
			},
			secondaryMemberInvitationMemberPoint: {
				required: true,
				integer: true,
				min: 0
			},
			memberInvitationMemberPoint: {
				required: true,
				integer: true,
				min: 0
			},
			point: {
				required: true,
				integer: true,
				min: 0
			},
			money: {
				required: true,
				integer: true,
				min: 0
			},
			currencySign: "required",
			currencyUnit: "required",
			stockAlertCount: {
				required: true,
				digits: true
			},
			defaultPointScale: {
				required: true,
				min: 0,
				decimal: {
					integer: 3,
					fraction: 3
				}
			},
			taxRate: {
				required: true,
				min: 0,
				decimal: {
					integer: 3,
					fraction: 3
				}
			},
			cookiePath: "required"
		},
		submitHandler: function(form) {
			var discountTypeVal = $('input[type=radio][name=discountType]:checked').val();
			var satisfyMoneyVal = $('#satisfyMoney').val();
			var reduceMoneyVal = $('#reduceMoney').val();
			if(discountTypeVal == "firstorder"){
			    if(parseInt(satisfyMoneyVal)<=parseInt(reduceMoneyVal)){
			        $.message("warn","首单金额必须大于满减金额");
			        return false;
			    }
			}
			form.submit();
		}
		
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.setting.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.setting.base")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.setting.show")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.setting.registerSecurity")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.setting.mail")}" />
			</li>
			<li>
				<input type="button" value="机构设置" />
			</li>
			<li>
				<input type="button" value="积分设置" />
			</li>
			<li>
				<input type="button" value="支付设置" />
			</li>
			<li>
				<input type="button" value="${message("admin.setting.other")}" />
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.siteName")}:
				</th>
				<td>
					<input type="text" name="siteName" class="text" value="${setting.siteName}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.siteUrl")}:
				</th>
				<td>
					<input type="text" name="siteUrl" class="text" value="${setting.siteUrl}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.logo")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="logo" class="text" value="${setting.logo}" maxlength="200" />
						<input type="button" class="button browserButton" value="${message("admin.browser.select")}" />
						<a href="${setting.logo}" target="_blank">${message("admin.common.view")}</a>
					</span>
				</td>
			</tr>
			
			
			<tr>
				<th>
					<span class="requiredField">*</span>微信分享标题:
				</th>
				<td>
					<input type="text" name="shareTitle" class="text" value="${setting.shareTitle}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>微信分享描述:
				</th>
				<td>
					<input type="text" name="shareDescribe" class="text" value="${setting.shareDescribe}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>微信分享图标:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="shareImage" class="text" value="${setting.shareImage}" maxlength="200" />
						<input type="button" class="button browserButton" value="${message("admin.browser.select")}" />
						<a href="${setting.shareImage}" target="_blank">${message("admin.common.view")}</a>
					</span>
				</td>
			</tr>
			
			
			
			
			<tr>
				<th>
					${message("Setting.hotSearch")}:
				</th>
				<td>
					<input type="text" name="hotSearch" class="text" value="${setting.hotSearch}" maxlength="200" title="${message("admin.setting.hotSearchTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.address")}:
				</th>
				<td>
					<input type="text" name="address" class="text" value="${setting.address}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.phone")}:
				</th>
				<td>
					<input type="text" name="phone" class="text" value="${setting.phone}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					QQ:
				</th>
				<td>
					<input type="text" name="qq" class="text" value="${setting.qq}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.zipCode")}:
				</th>
				<td>
					<input type="text" name="zipCode" class="text" value="${setting.zipCode}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.email")}:
				</th>
				<td>
					<input type="text" name="email" class="text" value="${setting.email}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.certtext")}:
				</th>
				<td>
					<input type="text" name="certtext" class="text" value="${setting.certtext}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					版权:
				</th>
				<td>
					<input type="text" name="copyright" class="text" value="${setting.copyright}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.isSiteEnabled")}:
				</th>
				<td>
					<input type="checkbox" name="isSiteEnabled" value="true"[#if setting.isSiteEnabled] checked="checked"[/#if] />
					<input type="hidden" name="_isSiteEnabled" value="false" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.siteCloseMessage")}:
				</th>
				<td>
					<textarea name="siteCloseMessage" class="text">${setting.siteCloseMessage?html}</textarea>
				</td>
			</tr>
		</table>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>项目图片(大):
				</th>
				<td>
					<span class="fieldTitle">${message("admin.setting.width")}:</span>
					<input type="text" name="largeProductImageWidth" class="text" value="${setting.largeProductImageWidth}" maxlength="9" style="width: 50px;" title="${message("admin.setting.widthTitle")}" />
					<span class="fieldTitle">${message("admin.setting.height")}:</span>
					<input type="text" name="largeProductImageHeight" class="text" value="${setting.largeProductImageHeight}" maxlength="9" style="width: 50px;" title="${message("admin.setting.heightTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>项目图片(中):
				</th>
				<td>
					<span class="fieldTitle">${message("admin.setting.width")}:</span>
					<input type="text" name="mediumProductImageWidth" class="text" value="${setting.mediumProductImageWidth}" maxlength="9" style="width: 50px;" title="${message("admin.setting.widthTitle")}" />
					<span class="fieldTitle">${message("admin.setting.height")}:</span>
					<input type="text" name="mediumProductImageHeight" class="text" value="${setting.mediumProductImageHeight}" maxlength="9" style="width: 50px;" title="${message("admin.setting.heightTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>项目图片(小):
				</th>
				<td>
					<span class="fieldTitle">${message("admin.setting.width")}:</span>
					<input type="text" name="thumbnailProductImageWidth" class="text" value="${setting.thumbnailProductImageWidth}" maxlength="9" style="width: 50px;" title="${message("admin.setting.widthTitle")}" />
					<span class="fieldTitle">${message("admin.setting.height")}:</span>
					<input type="text" name="thumbnailProductImageHeight" class="text" value="${setting.thumbnailProductImageHeight}" maxlength="9" style="width: 50px;" title="${message("admin.setting.heightTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>默认项目图片(大):
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="defaultLargeProductImage" class="text" value="${setting.defaultLargeProductImage}" maxlength="200" />
						<input type="button" class="button browserButton" value="${message("admin.browser.select")}" />
						<a href="${setting.defaultLargeProductImage}" target="_blank">${message("admin.common.view")}</a>
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>默认项目图片(中):
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="defaultMediumProductImage" class="text" value="${setting.defaultMediumProductImage}" maxlength="200" />
						<input type="button" class="button browserButton" value="${message("admin.browser.select")}" />
						<a href="${setting.defaultMediumProductImage}" target="_blank">${message("admin.common.view")}</a>
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>默认项目图片(小):
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="defaultThumbnailProductImage" class="text" value="${setting.defaultThumbnailProductImage}" maxlength="200" />
						<input type="button" class="button browserButton" value="${message("admin.browser.select")}" />
						<a href="${setting.defaultThumbnailProductImage}" target="_blank">${message("admin.common.view")}</a>
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.watermarkAlpha")}:
				</th>
				<td>
					<input type="text" name="watermarkAlpha" class="text" value="${setting.watermarkAlpha}" maxlength="9" title="${message("admin.setting.watermarkAlphaTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.watermarkImage")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="file" name="watermarkImageFile" />
						<a href="${base}${setting.watermarkImage}" target="_blank">${message("admin.common.view")}</a>
					</span>
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.watermarkPosition")}:
				</th>
				<td>
					<select name="watermarkPosition">
						[#list watermarkPositions as watermarkPosition]
							<option value="${watermarkPosition}"[#if watermarkPosition == setting.watermarkPosition] selected="selected"[/#if]>${message("Setting.WatermarkPosition." + watermarkPosition)}</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.priceScale")}:
				</th>
				<td>
					<select name="priceScale">
						<option value="0"[#if setting.priceScale == 0] selected="selected"[/#if]>${message("admin.setting.priceScale0")}</option>
						<option value="1"[#if setting.priceScale == 1] selected="selected"[/#if]>${message("admin.setting.priceScale1")}</option>
						<option value="2"[#if setting.priceScale == 2] selected="selected"[/#if]>${message("admin.setting.priceScale2")}</option>
						<option value="3"[#if setting.priceScale == 3] selected="selected"[/#if]>${message("admin.setting.priceScale3")}</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.priceRoundType")}:
				</th>
				<td>
					<select name="priceRoundType">
						[#list roundTypes as roundType]
							<option value="${roundType}"[#if roundType == setting.priceRoundType] selected="selected"[/#if]>${message("Setting.RoundType." + roundType)}</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.isShowMarketPrice")}:
				</th>
				<td>
					<input type="checkbox" name="isShowMarketPrice" value="true"[#if setting.isShowMarketPrice] checked="checked"[/#if] />
					<input type="hidden" name="_isShowMarketPrice" value="false" />
				</td>
			</tr>
			
		</table>
		<table class="input tabContent">
			<tr>
				<th>
					${message("Setting.isRegisterEnabled")}:
				</th>
				<td>
					<input type="checkbox" name="isRegisterEnabled" value="true"[#if setting.isRegisterEnabled] checked="checked"[/#if] />
					<input type="hidden" name="_isRegisterEnabled" value="false" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.isDuplicateEmail")}:
				</th>
				<td>
					<input type="checkbox" name="isDuplicateEmail" value="true"[#if setting.isDuplicateEmail] checked="checked"[/#if] />
					<input type="hidden" name="_isDuplicateEmail" value="false" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.disabledUsername")}:
				</th>
				<td>
					<input type="text" name="disabledUsername" class="text" value="${setting.disabledUsername}" maxlength="200" title="${message("admin.setting.disabledUsernameTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.usernameMinLength")}:
				</th>
				<td>
					<input type="text" id="usernameMinLength" name="usernameMinLength" class="text" value="${setting.usernameMinLength}" maxlength="3" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.usernameMaxLength")}:
				</th>
				<td>
					<input type="text" name="usernameMaxLength" class="text" value="${setting.usernameMaxLength}" maxlength="3" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.passwordMinLength")}:
				</th>
				<td>
					<input type="text" id="passwordMinLength" name="passwordMinLength" class="text" value="${setting.passwordMinLength}" maxlength="3" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.passwordMaxLength")}:
				</th>
				<td>
					<input type="text" name="passwordMaxLength" class="text" value="${setting.passwordMaxLength}" maxlength="3" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.registerPoint")}:
				</th>
				<td>
					<input type="text" name="registerPoint" class="text" value="${setting.registerPoint}" maxlength="9" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.registerAgreement")}:
				</th>
				<td>
					<textarea name="registerAgreement" class="text">${setting.registerAgreement?html}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.isEmailLogin")}:
				</th>
				<td>
					<input type="checkbox" name="isEmailLogin" value="true"[#if setting.isEmailLogin] checked="checked"[/#if] />
					<input type="hidden" name="_isEmailLogin" value="false" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.captchaTypes")}:
				</th>
				<td>
					[#list captchaTypes as captchaType]
						<label>
							<input type="checkbox" name="captchaTypes" value="${captchaType}"[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains(captchaType)] checked="checked"[/#if] />${message("Setting.CaptchaType." + captchaType)}
						</label>
					[/#list]
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.accountLockTypes")}:
				</th>
				<td>
					[#list accountLockTypes as accountLockType]
						<label>
							<input type="checkbox" name="accountLockTypes" value="${accountLockType}"[#if setting.accountLockTypes?? && setting.accountLockTypes?seq_contains(accountLockType)] checked="checked"[/#if] />${message("Setting.AccountLockType." + accountLockType)}
						</label>
					[/#list]
				</td>
			</tr>
			<tr>
				<th>
					机构端注册权限组:
				</th>
				<td>
					<label>
						<input type="checkbox" name="authorities" value="authentication"[#if setting.authorities?? && setting.authorities?seq_contains("authentication")] checked="checked"[/#if] />企业认证
					</label>
					<label>
						<input type="checkbox" name="authorities" value="mechanism"[#if setting.authorities?? && setting.authorities?seq_contains("mechanism")] checked="checked"[/#if] />企业资料
					</label>
					<label>
						<input type="checkbox" name="authorities" value="mechanismrolelist"[#if setting.authorities?? && setting.authorities?seq_contains("mechanismrolelist")] checked="checked"[/#if] />角色设置
					</label>
					<label>
						<input type="checkbox" name="authorities" value="serviceTime"[#if setting.authorities?? && setting.authorities?seq_contains("serviceTime")] checked="checked"[/#if] />服务时间
					</label>
					<label>
						<input type="checkbox" name="authorities" value="serverProjectCotegorylist"[#if setting.authorities?? && setting.authorities?seq_contains("serverProjectCotegorylist")] checked="checked"[/#if] />服务项目
					</label>
					<label>
						<input type="checkbox" name="authorities" value="achievements"[#if setting.authorities?? && setting.authorities?seq_contains("achievements")] checked="checked"[/#if] />绩效设置
					</label>
					<label>
						<input type="checkbox" name="authorities" value="doctorlist"[#if setting.authorities?? && setting.authorities?seq_contains("doctorlist")] checked="checked"[/#if] />员工管理
					</label>
					<label>
						<input type="checkbox" name="authorities" value="doctorCategoryList"[#if setting.authorities?? && setting.authorities?seq_contains("doctorCategoryList")] checked="checked"[/#if] />职级设置
					</label>
					<label>
						<input type="checkbox" name="authorities" value="projectlist"[#if setting.authorities?? && setting.authorities?seq_contains("projectlist")] checked="checked"[/#if] />项目审核
					</label>
					<label>
						<input type="checkbox" name="authorities" value="workDaylist"[#if setting.authorities?? && setting.authorities?seq_contains("workDaylist")] checked="checked"[/#if] />排班管理
					</label>
					<label>
						<input type="checkbox" name="authorities" value="orderlist"[#if setting.authorities?? && setting.authorities?seq_contains("orderlist")] checked="checked"[/#if] />订单管理
					</label>
					<label>
						<input type="checkbox" name="authorities" value="memberlist"[#if setting.authorities?? && setting.authorities?seq_contains("memberlist")] checked="checked"[/#if] />用户信息
					</label>
					<label>
						<input type="checkbox" name="authorities" value="patientlist"[#if setting.authorities?? && setting.authorities?seq_contains("patientlist")] checked="checked"[/#if] />患者信息
					</label>
					<label>
						<input type="checkbox" name="authorities" value="couponlist"[#if setting.authorities?? && setting.authorities?seq_contains("couponlist")] checked="checked"[/#if] />患者信息
					</label>
					<label>
						<input type="checkbox" name="authorities" value="memberrecharge"[#if setting.authorities?? && setting.authorities?seq_contains("memberrecharge")] checked="checked"[/#if] />账户充值
					</label>
					<label>
						<input type="checkbox" name="authorities" value="refundslist"[#if setting.authorities?? && setting.authorities?seq_contains("refundslist")] checked="checked"[/#if] />退款管理
					</label>
					<label>
						<input type="checkbox" name="authorities" value="rechargeLoglist"[#if setting.authorities?? && setting.authorities?seq_contains("rechargeLoglist")] checked="checked"[/#if] />充值统计
					</label>
					<label>
						<input type="checkbox" name="authorities" value="depositlist"[#if setting.authorities?? && setting.authorities?seq_contains("depositlist")] checked="checked"[/#if] />余额变动
					</label>
					<label>
						<input type="checkbox" name="authorities" value="statisticscharge"[#if setting.authorities?? && setting.authorities?seq_contains("statisticscharge")] checked="checked"[/#if] />收费统计
					</label>
					<label>
						<input type="checkbox" name="authorities" value="statisticslist"[#if setting.authorities?? && setting.authorities?seq_contains("statisticslist")] checked="checked"[/#if] />收费月报
					</label>
					<label>
						<input type="checkbox" name="authorities" value="ordercharge"[#if setting.authorities?? && setting.authorities?seq_contains("ordercharge")] checked="checked"[/#if] />预约统计
					</label>
					<label>
						<input type="checkbox" name="authorities" value="projectcharge"[#if setting.authorities?? && setting.authorities?seq_contains("projectcharge")] checked="checked"[/#if] />项目统计
					</label>
					<label>
						<input type="checkbox" name="authorities" value="memberpatienthealthType"[#if setting.authorities?? && setting.authorities?seq_contains("memberpatienthealthType")] checked="checked"[/#if] />患者状态
					</label>
					<label>
						<input type="checkbox" name="authorities" value="evaluatelist"[#if setting.authorities?? && setting.authorities?seq_contains("evaluatelist")] checked="checked"[/#if] />评价统计
					</label>
					<label>
						<input type="checkbox" name="authorities" value="export"[#if setting.authorities?? && setting.authorities?seq_contains("export")] checked="checked"[/#if] />导出
					</label>
					<label>
						<input type="checkbox" name="authorities" value="lookphonenum"[#if setting.authorities?? && setting.authorities?seq_contains("lookphonenum")] checked="checked"[/#if] />查看手机号
					</label>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.accountLockCount")}:
				</th>
				<td>
					<input type="text" name="accountLockCount" class="text" value="${setting.accountLockCount}" maxlength="9" title="${message("admin.setting.accountLockCountTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.accountLockTime")}:
				</th>
				<td>
					<input type="text" name="accountLockTime" class="text" value="${setting.accountLockTime}" maxlength="9" title="${message("admin.setting.accountLockTimeTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.safeKeyExpiryTime")}:
				</th>
				<td>
					<input type="text" name="safeKeyExpiryTime" class="text" value="${setting.safeKeyExpiryTime}" maxlength="9" title="${message("admin.setting.safeKeyExpiryTimeTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.uploadMaxSize")}:
				</th>
				<td>
					<input type="text" name="uploadMaxSize" class="text" value="${setting.uploadMaxSize}" maxlength="9" title="${message("admin.setting.uploadMaxSizeTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.uploadImageExtension")}:
				</th>
				<td>
					<input type="text" name="uploadImageExtension" class="text" value="${setting.uploadImageExtension}" maxlength="200" title="${message("admin.setting.uploadImageExtensionTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.uploadFlashExtension")}:
				</th>
				<td>
					<input type="text" name="uploadFlashExtension" class="text" value="${setting.uploadFlashExtension}" maxlength="200" title="${message("admin.setting.uploadFlashExtensionTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.uploadMediaExtension")}:
				</th>
				<td>
					<input type="text" name="uploadMediaExtension" class="text" value="${setting.uploadMediaExtension}" maxlength="200" title="${message("admin.setting.uploadMediaExtensionTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.uploadFileExtension")}:
				</th>
				<td>
					<input type="text" name="uploadFileExtension" class="text" value="${setting.uploadFileExtension}" maxlength="200" title="${message("admin.setting.uploadFileExtensionTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.imageUploadPath")}:
				</th>
				<td>
					<input type="text" name="imageUploadPath" class="text" value="${setting.imageUploadPath}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.flashUploadPath")}:
				</th>
				<td>
					<input type="text" name="flashUploadPath" class="text" value="${setting.flashUploadPath}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.mediaUploadPath")}:
				</th>
				<td>
					<input type="text" name="mediaUploadPath" class="text" value="${setting.mediaUploadPath}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.fileUploadPath")}:
				</th>
				<td>
					<input type="text" name="fileUploadPath" class="text" value="${setting.fileUploadPath}" maxlength="200" />
				</td>
			</tr>
		</table>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.smtpFromMail")}:
				</th>
				<td>
					<input type="text" id="smtpFromMail" name="smtpFromMail" class="text" value="${setting.smtpFromMail}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.smtpHost")}:
				</th>
				<td>
					<input type="text" id="smtpHost" name="smtpHost" class="text" value="${setting.smtpHost}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.smtpPort")}:
				</th>
				<td>
					<input type="text" id="smtpPort" name="smtpPort" class="text" value="${setting.smtpPort}" maxlength="9" title="${message("admin.setting.smtpPorteTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.smtpUsername")}:
				</th>
				<td>
					<input type="text" id="smtpUsername" name="smtpUsername" class="text" value="${setting.smtpUsername}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.smtpPassword")}:
				</th>
				<td>
					<input type="password" id="smtpPassword" name="smtpPassword" class="text" maxlength="200" title="${message("admin.setting.smtpPasswordTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.setting.mailTest")}:
				</th>
				<td>
					<span class="fieldSet">
						<span id="toMailWrap" class="hidden">
							${message("admin.setting.toMail")}: <br />
							<input type="text" id="toMail" name="toMail" class="text ignore" maxlength="200" />
						</span>
						<input type="button" id="mailTest" class="button" value="${message("admin.setting.mailTest")}" />
						<span id="mailTestStatus">&nbsp;</span>
					</span>
				</td>
			</tr>
		</table>
		<table class="input tabContent">
			
			<tr>
				<th>
					<span class="requiredField">*</span>秘钥有效期:
				</th>
				<td>
					<input type="text" id="appSafeKeyExpiryTime" name="appSafeKeyExpiryTime" class="text" value="${setting.appSafeKeyExpiryTime}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>订单过期扣除费率:
				</th>
				<td>
					<input type="text" id="deductionRate" name="deductionRate" class="text" value="${setting.deductionRate}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>实名认证接口:
				</th>
				<td>
					<input type="text" id="cardUrl" name="cardUrl" class="text" value="${setting.cardUrl}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>实名认证Key:
				</th>
				<td>
					<input type="text" id="cardKey" name="cardKey" class="text" value="${setting.cardKey}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>微信wxAppId:
				</th>
				<td>
					<input type="text" id="wxAppId" name="wxAppId" class="text" value="${setting.wxAppId}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>微信wxSecret:
				</th>
				<td>
					<input type="text" id="wxSecret" name="wxSecret" class="text" value="${setting.wxSecret}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>微信wxMchId(商户号):
				</th>
				<td>
					<input type="text" id="wxMchId" name="wxMchId" class="text" value="${setting.wxMchId}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>高德获取经纬度接口:
				</th>
				<td>
					<input type="text" id="addressUrl" name="addressUrl" class="text" value="${setting.addressUrl}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>高德计算距离接口:
				</th>
				<td>
					<input type="text" id="distanceUrl" name="distanceUrl" class="text" value="${setting.distanceUrl}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>高德Key:
				</th>
				<td>
					<input type="text" id="addressKey" name="addressKey" class="text" value="${setting.addressKey}" maxlength="200" />
				</td>
			</tr>
		</table>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>积分获取说明:
				</th>
				<td>
					<textarea class="text" name = "poingExplain">${setting.poingExplain}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>积分奖励:
				</th>
				<td>
					<textarea class="text" disabled>被邀请者注册完成后，邀请者与被邀请者积分发放说明：
							　　一、被邀请者下载并注册完成后首次发放；
							　　二、被邀请者首次下单并完成康复项目后二次发放；
							　　三、被邀请者积分一次性发放。
					</textarea>
				</td>
			</tr>
			<tr>
				<th colspan="2" class="green" style="text-align: left;">
					积分奖励类型：
				</th>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>积分奖励类型:
				</th>
				<td>
				[#list pointGrantTypes as pointGrantType]
					<input type="radio" class="pointGrantType" name="pointGrantType" [#if setting.pointGrantType == pointGrantType] checked [/#if] value="${pointGrantType}"> ${message("Setting.PointGrantType."+pointGrantType)}
				[/#list]
				</td>
			</tr>
			<tr>
				<th colspan="2" class="green" style="text-align: left;">
					医师邀请患者：
				</th>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>邀请者:
				</th>
				<td>
					<span class="fieldTitle">首次:</span>
					<input type="text" name="firstDoctorInvitationMemberPoint" class="text" value="${setting.firstDoctorInvitationMemberPoint}" maxlength="9" style="width: 50px;" />
					<span class="fieldTitle">积分</span><strong class="strong"><span>，二次:</span>
					<input type="text" id="secondaryDoctorInvitationMemberPoint" name="secondaryDoctorInvitationMemberPoint" class="text" value="${setting.secondaryDoctorInvitationMemberPoint}" maxlength="9" style="width: 50px;" />
					<span class="fieldTitle">积分</span></strong>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>被邀请者:
				</th>
				<td>
					<input type="text" name="doctorInvitationMemberPoint" class="text" value="${setting.doctorInvitationMemberPoint}" maxlength="9" style="width: 225px;" />
					<span class="fieldTitle">积分</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>积分说明:
				</th>
				<td>
					<textarea class="text" name = "doctorInvitationMemberPointExplain">${setting.doctorInvitationMemberPointExplain}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>医生积分有效期:
				</th>
				<td>
					<input type="text" name="doctorPointDay" class="text" value="${setting.doctorPointDay}" maxlength="9" style="width: 225px;" />
					<span class="fieldTitle">天</span>
				</td>
			</tr>
			<tr>
				<th colspan="2" class="green" style="text-align: left;">
					患者邀请患者：
				</th>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>邀请者:
				</th>
				<td>
					<span class="fieldTitle">首次:</span>
					<input type="text" name="firstMemberInvitationMemberPoint" class="text" value="${setting.firstMemberInvitationMemberPoint}" maxlength="9" style="width: 50px;" />
					<span class="fieldTitle">积分</span><strong class="strong"><span>，二次:</span>
					<input type="text" id = "secondaryMemberInvitationMemberPoint" name="secondaryMemberInvitationMemberPoint" class="text" value="${setting.secondaryMemberInvitationMemberPoint}" maxlength="9" style="width: 50px;" />
					<span class="fieldTitle">积分</span></strong>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>被邀请者:
				</th>
				<td>
					<input type="text" name="memberInvitationMemberPoint" class="text" value="${setting.memberInvitationMemberPoint}" maxlength="9" style="width: 225px;" />
					<span class="fieldTitle">积分</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>积分说明:
				</th>
				<td>
					<textarea class="text" name = "memberInvitationMemberPointExplain">${setting.memberInvitationMemberPointExplain}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>用户积分有效期:
				</th>
				<td>
					<input type="text" name="memberPointDay" class="text" value="${setting.memberPointDay}" maxlength="9" style="width: 225px;" />
					<span class="fieldTitle">天</span>
				</td>
			</tr>
			<tr>
				<th colspan="2" class="green" style="text-align: left;">
					积分价值：
				</th>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>积分价值:
				</th>
				<td>
					<input type="text" name="point" class="text" value="${setting.point}" maxlength="9" style="width: 50px;"  />
					<span class="fieldTitle">积分，=</span>
					<input type="text" name="money" class="text" value="${setting.money}" maxlength="9" style="width: 50px;"  />
					<span class="fieldTitle">元人民币</span>
				</td>
			</tr>
			<tr>
				<th colspan="2" class="green" style="text-align: left;">
					其他优惠：
				</th>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>活动类型:
				</th>
				<td class="green" style="text-align: left;">
					<input type="radio" name="discountType" [#if setting.discountType == "fullcut"] checked [/#if] value="fullcut"> 被邀请者首单免单
					<input type="radio" name="discountType" [#if setting.discountType == "firstorder"] checked [/#if] value="firstorder"> 被邀请者首单满
					<input type="text" id = "satisfyMoney" name="satisfyMoney" class="text" value="${setting.satisfyMoney}" maxlength="9" style="width: 50px;" />
					<span class="fieldTitle">元，减</span>
					<input type="text" id = "reduceMoney" name="reduceMoney" class="text" value="${setting.reduceMoney}" maxlength="9" style="width: 50px;" />
					<span class="fieldTitle">元</span>
					<input type="radio" name="discountType" [#if setting.discountType == "nothing"] checked [/#if] value="nothing"> 无其他优惠
				</td>
			</tr>
			<tr>
				<th colspan="2" class="green" style="text-align: left;">
					活动页地址：
				</th>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>医师端:
				</th>
				<td>
					<input type="text" id="doctorUrl" name="doctorUrl" class="text" value="${setting.doctorUrl}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>患者端:
				</th>
				<td>
					<input type="text" id="memberUrl" name="memberUrl" class="text" value="${setting.memberUrl}" maxlength="200" />
				</td>
			</tr>
		</table>
		
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>支付宝APP_ID：:
				</th>
				<td>
					<input type="text" id="zfbAppId" name="zfbAppId" class="text" value="${setting.zfbAppId}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>支付宝APP_PRIVATE_KEY:
				</th>
				<td>
					<input type="text" id="zfbAppPrivateKey" name="zfbAppPrivateKey" class="text" value="${setting.zfbAppPrivateKey}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>支付宝ALIPAY_PUBLIC_KEY:
				</th>
				<td>
					<input type="text" id="zfbAlipaypublicKey" name="zfbAlipaypublicKey" class="text" value="${setting.zfbAlipaypublicKey}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>支付宝AES秘钥:
				</th>
				<td>
					<input type="text" id="zfbAlipaypublicKey" name="zfbAlipaypublicKey" class="text" value="${setting.zfbAlipaypublicKey}" maxlength="200" />
				</td>
			</tr>
		</table>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.currencySign")}: 
				</th>
				<td>
					<input type="text" name="currencySign" class="text" value="${setting.currencySign}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.currencyUnit")}: 
				</th>
				<td>
					<input type="text" name="currencyUnit" class="text" value="${setting.currencyUnit}" maxlength="200" />
				</td>
			</tr>
			
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.defaultPointScale")}: 
				</th>
				<td>
					<input type="text" name="defaultPointScale" class="text" value="${setting.defaultPointScale}" maxlength="7" title="${message("admin.setting.defaultPointScaleTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.isReviewEnabled")}:
				</th>
				<td>
					<input type="checkbox" name="isReviewEnabled" value="true"[#if setting.isReviewEnabled] checked="checked"[/#if] />
					<input type="hidden" name="_isReviewEnabled" value="false" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.isReviewCheck")}:
				</th>
				<td>
					<input type="checkbox" name="isReviewCheck" value="true"[#if setting.isReviewCheck] checked="checked"[/#if] />
					<input type="hidden" name="_isReviewCheck" value="false" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.reviewAuthority")}: 
				</th>
				<td>
					<select name="reviewAuthority">
						[#list reviewAuthorities as reviewAuthority]
							<option value="${reviewAuthority}"[#if reviewAuthority == setting.reviewAuthority] selected="selected"[/#if]>${message("Setting.ReviewAuthority." + reviewAuthority)}</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.isInvoiceEnabled")}:
				</th>
				<td>
					<input type="checkbox" name="isInvoiceEnabled" value="true"[#if setting.isInvoiceEnabled] checked="checked"[/#if] />
					<input type="hidden" name="_isInvoiceEnabled" value="false" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.isTaxPriceEnabled")}:
				</th>
				<td>
					<input type="checkbox" name="isTaxPriceEnabled" value="true" title="${message("admin.setting.taxRateTitle")}"[#if setting.isTaxPriceEnabled] checked="checked"[/#if] />
					<input type="hidden" name="_isTaxPriceEnabled" value="false" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.taxRate")}:
				</th>
				<td>
					<input type="text" name="taxRate" class="text" value="${setting.taxRate}" maxlength="7" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Setting.cookiePath")}: 
				</th>
				<td>
					<input type="text" name="cookiePath" class="text" value="${setting.cookiePath}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Setting.cookieDomain")}: 
				</th>
				<td>
					<input type="text" name="cookieDomain" class="text" value="${setting.cookieDomain}" maxlength="200" />
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
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='../common/index.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>