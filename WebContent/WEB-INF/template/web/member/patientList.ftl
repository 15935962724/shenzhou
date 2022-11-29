<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>就诊人管理</title>
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/web/tips/jquery.tips.js"></script>
<script type="text/javascript" src="${base}/resources/web/js/zepto.js"></script>
</head>

<script type="text/javascript">
function isDefault(patientMemberId) {  

	$.ajax({
	url: "memberIsDefault.jhtml",
	type: "GET",
	data: {patientMemberId: patientMemberId},
	dataType: "json",
	cache: false,
	async: false,
	success: function() {
			alert('设置成功');
		}
	});
}
</script>

<body style="background:#f0f1f0">

    <div id="header" class="pf h329" style="border-bottom:#f0f1f0 1px solid;">
     	<div id="back" align="left"><a href="${base}/web/member/toMyself.jhtml"><img alt="" src="${base}/resources/web/images/backBtnImg.png" style="width:0.59rem; height:auto" class="backImg"></a></div>
     	<div id="title" style="align-content: center" align="center" class="titleLabel">就诊人管理</div>
     	<div style="text-align: right;color:#4c98f6" class="ArchivesLabel"><a href="toCreationPatient.jhtml">建档</a></div>
    </div>
     <div id="list" class="manageList" style="background: #FFFFFF">
[#list patientMember_List as member]
   		<div id="list" class="manageList" style="background: #FFFFFF; margin-top:0.2rem;">
    	<div class="listTop" style="border-bottom:#f0f1f0 1px solid;">
    		<img src="${member.logo}" class="list_top_Img" style="border-radius: 50%;margin-top:0.75rem;">
    		<div class="top_name_label">${member.name}</div>
    	</div>
    	<div class="listFooter">
    		<div class="footerleft">
    			<div class="checkBoxLeft"><a href="memberIsDefault.jhtml?patientMemberId=${member.id}"><input type="checkbox"  [#if member.isDefault] checked="checked" [/#if]>设置为默认就诊者</a></div>
    		</div>
    		<div class="footerRight">
    			<ul>
					<a href="deletePatient.jhtml?patientId=${member.id}" style="color:#323232;"><li>删除</li></a>
    				<a href="toEditPatientData.jhtml?patientId=${member.id}" style="color:#323232;"><li>资料修改</li></a>
    				<a href="${base}/web/assessReport/toAssessReport.jhtml?patientMemberId=${member.id}" style="color:#4c98f6;"><li>病历档案</li></a>
    			</ul>
    		</div>
    	</div>
    </div>
[/#list]
    
</body>
</html>
