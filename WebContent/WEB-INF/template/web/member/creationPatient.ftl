<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>个人资料</title>
<link href="${base}/resources/web/css/iosSelect.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/web/font/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/tips/jquery.tips.js"></script>
<script type="text/javascript" src="${base}/resources/web/js/zepto.js"></script>
<script type="text/javascript" src="${base}/resources/web/js/iscroll.js"></script>
<script type="text/javascript" src="${base}/resources/web/js/iosSelect.js"></script>
<script type="text/javascript" src="${base}/resources/web/js/js.js"></script>
<style>
	.layer{padding: 0px;}	
</style>
<script>
	
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

function preview_img(upfile,upimg)
{
	$("#" + upfile).click();
	$("#" + upfile).on("change",function(){
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
	
function setSelect(id, selectName, flag)
	{
		var html = "";
		var f=flag.split("|");
		//根据id值ajax获取数据到html中
		if(f[0] == "city")
			{
				$.ajax({
						url: "/shenzhou/web/member/getArea.jhtml",
						type: "POST",
						contentType:"application/x-www-form-urlencoded; charset=UTF-8",
						data: {areaId: id},
						traditional: true,  
						async: false,
						success: function(data) {
						var dataObj = eval('('+data+')');
									html += ' 					<select id="' + f[1] + '_city" class="w5 h15 z_106_909090_1" onChange="setSelect($(\'#' + f[1] + '_city\').val(),\'' + f[1] + 'districtinfo\',\'district|' + f[1] + '\');">';
									html += '    	 	     		<option>城市</option>';
										for(var i=0;i<dataObj.length;i++){
											html += '    	 	     		<option value="'+dataObj[i].id+'">'+dataObj[i].areaName+'</option>';
										}
									html += '    	 	     	</select>';
							}
						});
			}
		else
			{
			
			$.ajax({
						url: "/shenzhou/web/member/getArea.jhtml",
						type: "POST",
						contentType:"application/x-www-form-urlencoded; charset=UTF-8",
						data: {areaId: id},
						traditional: true,  
						async: false,
						success: function(data) {
						var dataObj = eval('('+data+')');
									html += '    	 	     	<select id="' + f[1] + '_district" class="w5 h15 z_106_909090_1" onchange="$(\'#' + f[1] + 'AreaId\').val($(this).val());">';
									html += '    	 	     		<option>区/县</option>';
										for(var i=0;i<dataObj.length;i++){
											html += '    	 	     		<option value="'+dataObj[i].id+'">'+dataObj[i].areaName+'</option>';
										}
									html += '    	 	     	</select>';
							}
						});
			}
		$("#" + f[1] + "AreaId").val(id);
		$("#" + selectName).html(html);
	}

$(function(){

	var selectDateDom = $('#birthDay');
    var showDateDom = $('#birthDay');
    // 初始化时间
    var now = new Date();
    var nowYear = now.getFullYear();
    var nowMonth = now.getMonth() + 1;
    var nowDate = now.getDate();
    showDateDom.attr('data-year', nowYear);
    showDateDom.attr('data-month', nowMonth);
    showDateDom.attr('data-date', nowDate);
    // 数据初始化
    function formatYear (nowYear) {
        var arr = [];
        for (var i = nowYear - 100; i <= nowYear; i++) {
            arr.push({
                id: i + '',
                value: i + '年'
            });
        }
        return arr;
    }
    function formatMonth () {
        var arr = [];
        for (var i = 1; i <= 12; i++) {
            arr.push({
                id: i + '',
                value: i + '月'
            });
        }
        return arr;
    }
    function formatDate (count) {
        var arr = [];
        for (var i = 1; i <= count; i++) {
            arr.push({
                id: i + '',
                value: i + '日'
            });
        }
        return arr;
    }
    var yearData = function(callback) {
        callback(formatYear(nowYear))
    }
    var monthData = function (year, callback) {
        callback(formatMonth());
    };
    var dateData = function (year, month, callback) {
        if (/^(1|3|5|7|8|10|12)$/.test(month)) {
            callback(formatDate(31));
        }
        else if (/^(4|6|9|11)$/.test(month)) {
            callback(formatDate(30));
        }
        else if (/^2$/.test(month)) {
            if (year % 4 === 0 && year % 100 !==0 || year % 400 === 0) {
                callback(formatDate(29));
            }
            else {
                callback(formatDate(28));
            }
        }
        else {
            throw new Error('month is illegal');
        }
    };
    selectDateDom.bind('click', function () {
        var oneLevelId = showDateDom.attr('data-year');
        var twoLevelId = showDateDom.attr('data-month');
        var threeLevelId = showDateDom.attr('data-date');

        var iosSelect = new IosSelect(3, 
            [yearData, monthData, dateData],
            {
                title: '出生日期选择',
                itemHeight: 35,
                relation: [1, 1, 0, 0],
                itemShowCount: 9,
                oneLevelId: oneLevelId,
                twoLevelId: twoLevelId,
                threeLevelId: threeLevelId,

                callback: function (selectOneObj, selectTwoObj, selectThreeObj) {
                    showDateDom.attr('data-year', selectOneObj.id);
                    showDateDom.attr('data-month', selectTwoObj.id);
                    showDateDom.attr('data-date', selectThreeObj.id);
					var YearMonthDay = selectOneObj.value.replace("年","-") + selectTwoObj.value.replace("月","-") + selectThreeObj.value.replace("日","");
                    showDateDom.val(YearMonthDay);
                }
        });
    });
})
	




</script>


<script type="text/javascript">
//提交验证
function confirm() {  
	
	var realName = document.getElementById("realName").value;
	var sex = document.getElementById("sex").value; 
	var nation = document.getElementById("nation").value; 
	var birthDay = document.getElementById("birthDay").value; 
	var calendar = document.getElementById("calendar").value; 
	var householdAddress = document.getElementById("householdAddress").value; 
	var address = document.getElementById("address").value; 
	var nowAreaId = document.getElementById("nowAreaId").value; 
	var regAreaId = document.getElementById("regAreaId").value; 			
	var relationship = document.getElementById("relationship").value; 
	
	if(realName==''||realName==null||realName==undefined){
		$.fn.tips({content:'姓名不能为空'});
		return false;
	}
	if($(':radio[name=sex]:checked').length == 0){
		$.fn.tips({content:'请选择性别'});
		return false;
	}
	if(nation==''||nation==null||nation==undefined){
		$.fn.tips({content:'请填写民族'});
		return false;
	}
	if(birthDay==''||birthDay==null||birthDay==undefined){
		$.fn.tips({content:'请选择生日信息'});
		return false;
	}
	if(calendar==''||calendar==null||calendar==undefined){
		$.fn.tips({content:'请选择阴历阳历'});
		return false;
	}
	if(regAreaId==''||regAreaId==null||regAreaId==undefined){
		$.fn.tips({content:'请选择户籍地'});
		return false;
	}
	if(householdAddress==''||householdAddress==null||householdAddress==undefined){
		$.fn.tips({content:'户籍地址不能为空'});
		return false;
	}
	if(nowAreaId==''||nowAreaId==null||nowAreaId==undefined){
		$.fn.tips({content:'请选择现居地'});
		return false;
	}
	
	if(address==''||address==null||address==undefined){
		$.fn.tips({content:'现居地地址不能为空'});
		return false;
	}
	if(relationship==''||relationship==null||relationship==undefined){
		$.fn.tips({content:'与患者的关系不能为空'});
		return false;
	}
	document.getElementById("inputForm").submit(); 

}  
</script>


</head>

<body style="background:#fff">
<!-- page 容器 -->
<div class="page" id="page-infinite-scroll" >
<form id="inputForm" action="/shenzhou/web/member/creationPatient.jhtml" method="POST" enctype="multipart/form-data">
<input type="hidden" name="nowAreaId" id="nowAreaId" value="">
<input type="hidden" name="regAreaId" id="regAreaId" value="">

     <div id="header" class="pf h329" style="border-bottom:#f0f1f0 1px solid;">
     	<div id="back" align="left" style="width: 2rem;float:left;margin-left:0.5rem;"><a href="javascript:;" onclick="javascript:history.back(-1);"><img alt="" src="${base}/resources/web/images/backBtnImg.png" style="width:0.59rem; height:auto" class="backImg"></a></div>
     	<div id="title" style="align-content:center;margin-top: 1rem" align="center" class="titleLabel">个人资料</div>
     	<div style="text-align: right;color:#4c98f6" class="ArchivesLabel"><a onclick="confirm()">保存</a></div>
     </div> 
     
     <div class="myCenter_information">
     	 <div class="myCenter_information_style" style="border-bottom:#f0f1f0 1px solid;">
     	 	 <div class="myCenter_information_leftName">头像</div>
     	 	 <div style="top:0px; margin-left: 15rem;" class="myCenter_information_topImg"><img src="${base}/resources/web/images/h.png"  id="up_img" style="border-radius: 1rem;width: 2rem; height: 2rem;" onclick="preview_img('upfile','up_img','1');" ><input id="upfile" name="upfile" type="file" style="display: none"/></div>
     	 </div>
     	 
     	 <div class="myCenter_information_style" style="border-bottom:#f0f1f0 1px solid;">
     	 	 <div class="myCenter_information_leftName">真实姓名</div>
     	 	 <div class="myCenter_information_rightInput">
     	 	     <input type="text" name="realName" id="realName" size="15" placeholder="请输入真实姓名" class="myCenter_information_input" style="font-size:1rem;">
     	 	 </div>
     	 </div>
     	 
     	 <div class="myCenter_information_style" style="border-bottom:#f0f1f0 1px solid;">
     	 	 <div class="myCenter_information_leftName">性别</div>
     	 	 <div class="myCenter_information_sex">
     	 	    <input type="radio" id="sex" name="sex" value="male" class="myCenter_information_sexStyle"> 男
     	 	    <input type="radio" id="sex" name="sex" value="female" class="myCenter_information_sexNv myCenter_information_sexStyle"> 女
     	 	 </div>
     	 </div>
     	 
     	 <div class="myCenter_information_style" style="border-bottom:#f0f1f0 1px solid;">
     	 	 <div class="myCenter_information_leftName">民族</div>
     	 	 <div class="myCenter_information_rightInput">
     	 	      <input type="text" name="nation" id="nation" size="15" placeholder="请输入民族" class="myCenter_information_input" style="font-size:1rem;">
     	 	 </div>
     	 </div>
     	 
     	 <div class="myCenter_information_style" style="border-bottom:#f0f1f0 1px solid;">
     	 	 <div class="myCenter_information_leftName">出生日期</div>
     	 	 <div class="myCenter_information_birthStyle">
     	 	     <input type="text" name="birthDay" id="birthDay" placeholder="请选择出生日期" class="myCenter_information_birth_input" style="font-size:1rem;">
     	 	 </div>
     	 	 <div class="myCenter_information_calendar">
     	 	    <!--input type="button" id="calendar" style="font-size:1rem;" value="阳历" onClick="calendarClink()" class="myCenter_information_calendarInput"-->
     	 	    <select id="calendar" name="calendar" class="myCenter_information_calendarInput myCenter_caleder_select" style="line-height: 1.3rem;">
     	 	    	<option value="cregorian">阳历</option>
     	 	    	<option value="lunar">阴历</option>
     	 	    </select>
     	 	 </div>
     	 </div>
     	 
     	 <div class="myCenter_information_style" style="border-bottom:#f0f1f0 1px solid;">
     	 	 <div class="myCenter_information_leftName">户籍地址</div>
     	 	 <div class="myCenter_information_selectClass myCenter_information_input">
    	 	     <select id="reg_province" name="reg_province" class="w5 h15 z_106_909090_1" onChange="setSelect($('#reg_province').val(),'regcityinfo','city|reg');">
    	 	     	<option>省份</option>
    	 	     	[#list top_area as province]
    	 	     		<option value="${province.id}">${province.name}</option>
    	 	     	[/#list]
    	 	     </select>
    	 	     <span id="regcityinfo">
    	 	     	<select id="reg_city" name="reg_city" class="w5 h15 z_106_909090_1">
    	 	     		<option>城市</option>
    	 	     	</select>
    	 	     </span>
    	 	     <span id="regdistrictinfo">
    	 	     	<select id="reg_district" name="reg_district" class="w5 h15 z_106_909090_1">
    	 	     		<option>区/县</option>
    	 	     	</select>
    	 	     </span>
     	 	     <!--input type="text" id="nativeAddress" placeholder="这个是要写的三级联动" class="myCenter_information_input" style="font-size:1rem;"-->
				<!--input type="hidden" name="RegAdd_contact_province_code" data-id="0001" id="RegAdd_contact_province_code" value="" RegAdd_data-province-name="">
				<input type="hidden" name="RegAdd_contact_city_code" id="RegAdd_contact_city_code" value="" RegAdd_data-city-name=""><span RegAdd_data-city-code="" RegAdd_data-province-code="" RegAdd_data-district-code="" id="RegAdd_show_contact" class="myCenter_information_input" style="display: block;font-size: 1rem;">请选择户籍所在区域</span--> 

     	 	 </div>
     	 </div>
     	 
     	 <div class="myCenter_information_inputStyle" style="border-bottom:#f0f1f0 1px solid;">
     	 	  <div class="myCenter_information_rightInputs">
     	 	       <textarea name="householdAddress" id="householdAddress" placeholder="请输入户籍详细地址" style="width:100%;height:4rem;font-size:1rem;color:#323232;" class="testarea_style"></textarea>
     	 	  </div>
     	 </div>
     	 
     	  <div class="myCenter_information_style" style="border-bottom:#f0f1f0 1px solid;">
     	 	 <div class="myCenter_information_leftName">现住地址</div>
     	 	 <div class="myCenter_information_selectClass myCenter_information_input">
    	 	     <select id="now_province" name="now_province" class="w5 h15 z_106_909090_1" onChange="setSelect($('#now_province').val(),'nowcityinfo','city|now');">
    	 	     	<option>省份</option>
    	 	     	[#list top_area as province]
    	 	     		<option value="${province.id}">${province.name}</option>
    	 	     	[/#list]
    	 	     </select>
    	 	     <span id="nowcityinfo">
    	 	     	<select id="now_city" name="now_city" class="w5 h15 z_106_909090_1">
    	 	     		<option>城市</option>
    	 	     	</select>
    	 	     </span>
    	 	     <span id="nowdistrictinfo">
    	 	     	<select id="now_district" name="now_district" class="w5 h15 z_106_909090_1">
    	 	     		<option>区/县</option>
    	 	     	</select>
    	 	     </span>
     	 	    <!--input type="text" id="nowAddress" placeholder="这个是要写的三级联动" class="myCenter_information_input" style="font-size:1rem;"-->
				<!--input type="hidden" name="NowAdd_contact_province_code" data-id="0002" id="NowAdd_contact_province_code" value="" NowAdd_data-province-name="">
				<input type="hidden" name="NowAdd_contact_city_code" id="NowAdd_contact_city_code" value="" NowAdd_data-city-name=""><span NowAdd_data-city-code="" NowAdd_data-province-code="" NowAdd_data-district-code="" id="NowAdd_show_contact" class="myCenter_information_input" style="display: block;font-size: 1rem;">请选择现居住地所在区域</span-->      	     
     	 	 </div>
     	 
     	 <div class="myCenter_information_inputStyle" style="border-bottom:#f0f1f0 1px solid;">
     	 	  <div class="myCenter_information_rightInputs">
     	 	     <textarea name="address" id="address" placeholder="请输入现住详细地址" style="width:100%;height:4rem;font-size:1rem;color:#323232;" class="testarea_style"></textarea>
     	 	  </div>
     	 </div>
     	 
     	 <div class="myCenter_information_style" style="border-bottom:#f0f1f0 1px solid;">
     	 	 <div class="myCenter_information_leftName">与您的关系</div>
     	 	 <div class="myCenter_information_rightInput"> 
     	 	     <input type="text" name="relationship" id="relationship" size="15" placeholder="请输入关系(必填)" class="myCenter_information_input" style="font-size:1rem;">
     	 	 </div>
     	 </div>
     	 
     	 <div class="myCenter_information_style" style="border-bottom:#f0f1f0 1px solid;">
     	 	 <div class="myCenter_information_leftName">联系电话</div>
     	 	 <div class="myCenter_information_rightInput">
     	 	     <input type="text" name="memberIphone" id="memberIphone" size="15" placeholder="请输入联系电话" class="myCenter_information_input" style="font-size:1rem;">
     	 	 </div>
     	 </div>
     	 
     	 <div class="myCenter_information_style" style="border-bottom:#f0f1f0 1px solid;">
     	 	 <div class="myCenter_information_leftName">医保卡号</div>
     	 	 <div class="myCenter_information_rightInput">
     	 	     <input type="text" name="healthCard" id="healthCard" size="15" placeholder="请输入医保卡号" class="myCenter_information_input" style="font-size:1rem;">
     	 	 </div>
     	 </div>
     	 
     </div>
   </div>
</form>   
</div>

</body>
</html>
