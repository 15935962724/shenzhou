<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>我的医生</title>
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/js/js.js"></script>
</head>

<body style="background:#f0f1f0">

     <div id="header" class="pf h329" style="border-bottom:#f0f1f0 1px solid;">
     	<div id="back" align="left"><a href="js/"><img alt="" src="images/backBtnImg.png" style="width:0.59rem; height:auto" class="backImg"></a></div>
     	<div id="title" style="align-content:center" align="center" class="titleLabel">我的医生</div>
     </div> 
      [#list doctors as doctor]     
     <div class="myDoctorList" [#if doctor_index==0] style=" margin-top:3.29rem;"[/#if]>
     	 <div class="myDoctorList_left" style="line-height:5rem;position: absolute;"><img src="${doctor.logo}" class="myDoctorList_topImg" style="border-radius:50%;"></div>
     	 
     	 <div class="myDoctorList_moddle" style="position: absolute;">
     	 	 <div class="myDoctor_name" style="margin-top:0.2rem;">${doctor.name}&nbsp<span style="font-size:0.833rem;color:#646464;">[#if doctor.gender=="male"]男[/#if][#if doctor.gender=="female"]女[/#if]&nbsp</span><span class="tag1">首席康复师</span></div>
     	     <div class="myDoctor_expert myDoctor_expert_style"></div>
     	 	 <div class="myDoctor_introduce myDoctor_expert_style">${doctor.introduce}</div>
     	 </div>
     	 
     	 <div class="myDoctorList_right" style="position: absolute;">
     	 	 <div class="myDoctorList_score myDoctorList_score_style" style="margin-top:1.35rem;">评分:<span style="color:#e00101;">${doctor.scoreSort}</span>分</div>
     	 	 <div class="myDoctorList_scend myDoctorList_score_style">诊次:${doctor.second}次</div>
     	 </div>
     </div>  
      [/#list]
     
</body>
</html>
