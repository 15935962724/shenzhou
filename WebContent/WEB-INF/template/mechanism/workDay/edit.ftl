<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>

<script type="text/javascript">
var work_start_time = "${mechanism.workDate.startTime}" , work_end_time = "${mechanism.workDate.endTime}";
function fill_zero(n)
	{
		if(n<10)
			return "0" + n;
		else
			return n;
	}
function get_date()
	{
		var d = new Date();
		var da = d.getFullYear() + "/" + fill_zero((d.getMonth()+1)) + "/" + fill_zero(d.getDate());
		return da;
	}
function creat_html(num,classname)
	{
		var html = "";
		for (var i = 1; i <= num; i++)
			{
				if(i != num)
					{
						html += '	<div class="hour">';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '	</div>'
					}
				else
					{
						html += '<div class="hour"></div>';
					}
			}
		$("." + classname).html(html);		
	}
function creat_css(num,widt,start)
	{
		var start_num,start_str,sty="<style>";
		for(var i=1; i<=num; i++)
			{
				start_num = start + i - 1;
				if(start_num < 10)
					{
						start_str = "0" + start_num.toString() + ":00";
					}
				else
					{
						start_str = start_num.toString() + ":00";
					}

				$(".ruler .hour:nth-of-type(" + i + ")").css({"left" : ((i-1) * widt).toString() + "%"});
				sty += ".ruler .hour:nth-of-type(" + i + "):after {content: '" + start_str  + "';}";
			}	
		sty += "</style>";
		$("head").append(sty);
	}
function creat_ruler()
	{
		var start_t=work_start_time.split(":");
		var end_t=work_end_time.split(":");
		if(parseInt(end_t[1]) > 0)
			{
				end_t[0] = parseInt(end_t[0]) + 1;
			}
		var num = end_t[0] - parseInt(start_t[0]);
		var hour_r = 100 / num;
		creat_html(num+1,"ruler");
		$(".ruler .hour").css({"width" : hour_r + "%"});
		creat_css(num+1,hour_r,parseInt(start_t[0]));
	}

function disp_tr(key)
	{
		switch(key)
			{
					case "":
						$("#item").css("display","none");
						$("#fre").css("display","none");
						$("#s_time").css("display","none");
						$("#e_time").css("display","none");
						$("#p_t").css("display","none");
						$("#patient").css("display","none");
					    $("#temp").css({"width" : "0px" , "margin_left" : "0%" , "display" : "none"}); 
						break;
					case "reserve":
						$("#item").css("display","");
						$("#fre").css("display","");
						$("#s_time").css("display","");
						$("#e_time").css("display","none");
						$("#p_t").css("display","");
						$("#patient").css("display","");
						$("#temp").css({"width" : "0px" , "margin_left" : "0%" , "display" : "block"});

						break; 
					default :
						$("#item").css("display","none");
						$("#fre").css("display","none");
						$("#s_time").css("display","");
						$("#e_time").css("display","");
						$("#p_t").css("display","");
						$("#patient").css("display","none");
						$("#temp").css({"width" : "0px" , "margin_left" : "0%" , "display" : "block"});
			}
		
	}

function set_min_width()
	{
		var start_t=work_start_time.split(":");
		var end_t=work_end_time.split(":");
		if(parseInt(end_t[1]) > 0)
			{
				end_t[0] = parseInt(end_t[0]) + 1;
			}
		var num = 100 / (end_t[0] - parseInt(start_t[0])) / 12;
		return num;
	}
function set_width_item()
	{
		var item_num = $("#num").val();
		if(item_num <= 0)
			{
				alert("预约次数应大于0");
				$("#num").val("1");
				return false;
			}
		var item_time = $("#items").find("option:selected").attr("data_time") / 5;
		$("#temp").css("width",(item_time * item_num * set_min_width())+ "%" );
		
		add_time("${workDay.workDayDate?string("yyyy/MM/dd")} " + $("#start_time").val() + ":00" , item_num * $("#items").find("option:selected").attr("data_time"));
		
	}
function set_width_time(select_start_time , select_end_time , pid)
	{
		var start = new Date(get_date() + " " + select_start_time + ":00");
		var end = new Date(get_date() + " " + select_end_time + ":00");
		starttime_n = new Date(get_date() + " " + work_start_time + ":00");
		endtime_n = new Date(get_date() + " " + work_end_time + ":00");
		if((start > end) || (start < starttime_n) || (start >= endtime_n))
			{
				alert("开始/结束时间错误");
				return false;
			}
		
		//alert(end +"--1--" + start);
		var item_time = (end-start) / 1000 / 60 / 5;
		if(pid == "")
			$("#temp").css("width",(item_time * set_min_width())+ "%" );
		else
			$("#" + pid).css("width",(item_time * set_min_width())+ "%" );		
		
	}
function set_margin_left(select_start_time,pid)
	{
		var judge = work_start_time.split(":") , start;
		if(parseInt(judge[1])>0)
			start = new Date(get_date() + " " + fill_zero(judge[0]) + ":00:00");
		else
			start = new Date(get_date() + " " + work_start_time + ":00");
		var end = new Date(get_date() + " " + select_start_time + ":00");
		endtime_n = new Date(get_date() + " " + work_end_time + ":00");
		if((start > end) || (start > endtime_n) || (start >= endtime_n))
			{
				alert("开始/结束时间错误");
				return false;
			}
		//alert(end +"--2--" + start);
		var item_time = (end-start) / 1000 / 60 / 5;
		if(pid=="")
			$("#temp").css("margin-left",(item_time * set_min_width())+ "%");
		else
			$("#" + pid).css("margin-left",(item_time * set_min_width())+ "%");
	}
function close_lock(workDayItemId)
	{
		if(window.confirm("您确认要完成解锁操作吗？"))
			{
				 $.ajax({
					url: "delete.jhtml",
					type: "POST",
					data: {
					 workDayItemId : workDayItemId
					},
					dataType: "json",
					cache: false,
					async:false,
					success: function(message) {
						$.message(message);
						location=location;
					}
				});

			}
	}

function change_start_time()
	{
		set_margin_left(fill_zero($dp.cal.getP('H')) + ":" + fill_zero($dp.cal.getP('m')),'');
		if($("#flag").val() == "reserve"){
			set_width_item(); 
		}else{
			set_width_time(fill_zero($dp.cal.getP('H')) + ":" + fill_zero($dp.cal.getP('m')),$("#end_time").val(),'');
		} 
		
	}
function change_end_time()
	{
		set_width_time($("#start_time").val(),fill_zero($dp.cal.getP('H')) + ":" + fill_zero($dp.cal.getP('m')),"");
	}

function add_time(start_time,min) 
	{
		var time = new Date(start_time.replace("-","/"));
		time.setMinutes(time.getMinutes() + min, time.getSeconds(), 0);
		var end_time = fill_zero(time.getHours()) + ":" + fill_zero(time.getMinutes());
		//var dataObj = verifyTime();
		//alert(dataObj);
		$("#end_time").attr("value" , end_time);
	}


	
	function submits(){
	    var workDayType = $("#flag").val();
	    var workDayId = $("#workDayId").val();
		var workDayType = $("#flag").val();
		var projectId = $("#items").val();
		var patientId = $("#patientId").val();
		var num = $("#num").val();
		var start_time = $("#start_time").val();
		var end_time = $("#end_time").val();
		
	   if(workDayType=="reserve"){
	     if(start_time==""){
	          alert("请选择开始/结束时间");
	          return false;
	      }
	       $.ajax({
					url: "verifyTime.jhtml",
					type: "POST",
					data: {
					 workDayId : workDayId,
					 workDayType : workDayType,
					 projectId : projectId,
					 patientId : patientId,
					 num : num,
					 start_time : start_time,
					 end_time : end_time
					},
					dataType: "json",
					cache: false,
					async:false,
					success: function(data) {
					 //alert(data);
					 console.log(data);
					 var dataObj = JSON.stringify(data);
					 console.log(dataObj);
					 if(data.status=="400"){
					 alert(data.message);
					 return false;
					 }else{
					 //alert("实际预约："+data.data.count+"次；实际预约时间为："+data.data.startTime+"至"+data.data.endTime+",总共"+data.data.endMinute+"分钟，实际总价钱为："+data.data.countPrice+"，实际优惠价为："+data.data.discountPrice);
					 var content = "实际预约："+data.data.count+"次；实际预约时间为："+data.data.startTime+"至"+data.data.endTime+",总共"+data.data.endMinute+"分钟，实际总价钱为："+data.data.countPrice+"，实际优惠价为："+data.data.discountPrice;
					  $("#num").val(data.data.count);
					  $("#end_time").val(data.data.endTime);
					  $("#temp").css("width",(data.data.endMinute/5  * set_min_width())+ "%" );
					  if(confirm('实际预约：'+data.data.count+'次；实际预约时间为：'+data.data.startTime+'至'+data.data.endTime+',总共'+data.data.endMinute+'分钟，实际总价钱为：'+data.data.countPrice+'，实际优惠价为：'+data.data.discountPrice)){
									console.log(1);
									$("#inputForm").submit();
									}
									else{
									console.log(2);
									return false;                
									}
					 }
					}
				});
	      
	   }else{
	      if(start_time==""||end_time==""){
	          alert("请选择开始/结束时间");
	          return false;
	      }
	     $.ajax({
					url: "verifyTime.jhtml",
					type: "POST",
					data: {
					 workDayId : workDayId,
					 workDayType : workDayType,
					 projectId : projectId,
					 patientId : patientId,
					 num : num,
					 start_time : start_time,
					 end_time : end_time
					},
					dataType: "json",
					cache: false,
					async:false,
					success: function(data) {
					 //alert(data);
					 console.log(data);
					 var dataObj = JSON.stringify(data);
					 console.log(dataObj);
					 //alert(data.status);
					 if(data.status=="400"){
					 alert(data.message);
					 return false;
					 }else{
					 //alert(data.message);
					 $("#inputForm").submit();
					  
					 }
					}
				});
	      
	   }
	   
	   
	}
	
	
	
$(function(){
	creat_ruler();
	set_margin_left(work_start_time , "work");
	set_width_time(work_start_time , work_end_time , "work");
	
	var doctorId = ${doctor.id}
	//日期下拉框绑定事件
	$('#workDay').change(function(){ 
	
	var workDayDate=$(this).children('option:selected').val();//这就是selected的值 
	//alert(workDayDate); 
	//alert(doctorId); 
	
				$.ajax({
					url: "verificationScheduling.jhtml",
					type: "POST",
					data: {
					 doctorId : doctorId,
					 workDayDate : workDayDate
					},
					dataType: "json",
					cache: false,
					async:false,
					success: function(data) {
					
					 if(data.status=="400"){
					  	 if(confirm('该医生'+workDayDate+'未排班,确定要给该医生排班么?')){
								 $.ajax({
									url: "scheduling.jhtml",
									type: "POST",
									data: {
									 doctorId : doctorId,
									 workDayDate : workDayDate
									},
									dataType: "json",
									cache: false,
									async:false,
									success: function(data1) {
									//alert(data1);
									  if(data.status=="200"){
									  	$.message(data.message);
									  	location.href = "edit.jhtml?doctorId="+doctorId+"&workDayId="+data1.data;
									  }else{
									    $.message(data.message);
									  }
									}
								});
						}
					  }else{
					    location.href = "edit.jhtml?doctorId="+doctorId+"&workDayId="+data.data;
					  }
					}
				});
	
	
	
	}) 
	
	
	
})
</script>
<style>
.det {
	position: relative;
	width: 95%;
	margin: 0px auto;
	height: 60px;
	background: #eee;
	border-bottom: none;
	z-index: 1;
}
.work{
	position: absolute;
	background: #cee8bc;
	height: 60px;
	z-index: 2;
}
.lock{
	position: absolute;
	background: #7195bc;
	height: 60px;
	z-index: 4;
	color: #fff;
	line-height: 59px;
	text-align: center;
}
.appo{
	position: absolute;
	background: #f6b032;
	height: 60px;
	z-index: 5;
	color: #fff;
	line-height: 59px;
	text-align: center;
	border-left: 1px solid #90b57f;
	border-right: 1px solid #90b57f;
}
.rest{
	position: absolute;
	background: #ccc;
	height: 60px;
	z-index: 6;
	color: #fff;
	line-height: 59px;
	text-align: center;
}
.mechanism{
	position: absolute;
	background: #90b57f;
	height: 60px;
	z-index: 3;
	color: #fff;
	line-height: 59px;
	text-align: center;
}
.temp{
	position: absolute;
	border: 1px solid #f00;
	height: 58px;
	display: none;
	z-index: 10;
}
.ruler {
	position: relative;
	width: 95%;
	margin: 0px auto;
	height: 14px;
	border-top: 1px solid #555;
}
.ruler .hour{
	position: absolute;
	border-left: 1px solid #555;
	height: 14px;
}	
.ruler .min {
	position: absolute;
	border-left: 1px solid #555;
	height: 14px;
}
.ruler .hour:after {
	position: absolute;
	bottom: -15px;
	left:-16px;
	font: 11px/1 sans-serif;
}
.ruler .min {
	height: 5px;
}
.ruler .min:nth-of-type(6) {
	height: 10px;
}
.ruler .min:nth-of-type(1) {
	left: 8.33%;
}
.ruler .min:nth-of-type(2) {
	left: 16.66%;
}
.ruler .min:nth-of-type(3) {
	left: 24.99%;
}
.ruler .min:nth-of-type(4) {
	left: 33.32%;
}
.ruler .min:nth-of-type(5) {
	left: 41.65%;
}
.ruler .min:nth-of-type(6) {
	left: 49.98%;
}
.ruler .min:nth-of-type(7) {
	left: 58.31%;
}
.ruler .min:nth-of-type(8) {
	left: 66.64%;
}
.ruler .min:nth-of-type(9) {
	left: 74.97%;
}
.ruler .min:nth-of-type(10) {
	left: 83.3%;
}
.ruler .min:nth-of-type(11) {
	left: 91.63%;
}
.ruler .min:nth-of-type(12) {
	left: 100%;
}
.dis{display:none;}
</style>
</head>
<body>
    	<div class="nav">管理导航：
	    	<a href="javascript:;">用户管理首页</a>　
	    	<a href="javascript:;">患者信息</a>　
	    	<a href="javascript:;">预约信息</a>　
	    	<a href="javascript:;">回访信息</a>　
	    	<a href="javascript:;">账户信息</a>　
	    	<a href="javascript:;">医师信息</a>
    	</div>
        <div class="seat">
        	<div class="left_z">用户信息</div>
            <div class="export">
				<a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
        		<a href="javascript:;"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>
				<a href="javascript:history.back(-1);"><img src="${base}/resources/mechanism/images/back.png" border="0" class="top_img_b_1" /> 返回</a>
          </div>
        
        </div>
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td width="280" valign="top">
            	<div class="peo_2">
            		<table cellpadding="0" cellspacing="0" border="0">
            			<tr>
            				<td><img src="${base}/resources/mechanism/images/r.png" alt="" title="" class="head_1" /></td>
            			</tr>
            			<tr>
            				<td height="50" class="b_a_dcdcdc_1"><span class="sp_m_1">${doctor.name}</span><span class="z_12_999999_1">　${doctor.doctorCategory.name}　${message("Member.Gender."+doctor.gender)}</span></td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">联系电话：${doctor.mobile}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">岗位级别：${doctor.doctorCategory.name}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">出生日期：${doctor.birth?string("yyyy-MM-dd")}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">用户积分：1000分</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">用户评分：
            					<table width="200" cellpadding="0" cellspacing="0" border="0" class="pif_1">
            						<tr>
            							<td align="center">
											<span>综合评分</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.scoreSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.scoreSort}分</span>
										</td>
            						</tr>
            						<tr>
            							<td align="center">
											<span>技能评价</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.skillSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.skillSort}分</span>
										</td>
            						</tr>
            						<tr>
            							<td align="center">
											<span>服务能力</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.serverSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.serverSort}分</span>
										</td>
            						</tr>
            						<tr>
            							<td align="center">
											<span>沟通能力</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.communicateSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.communicateSort}分</span>
										</td>
            						</tr>
            					</table>
            				</td>
            			</tr>
            			<tr>
            				<td>
            				</td>
            			</tr>
            		</table>
            	</div>
            </td>
            <td valign="top"><table width="99%" align="right" border="0" cellspacing="0" cellpadding="0">
              <tbody>
                <tr>
                  <td height="40">
					<div class="peo_tag_1">
						<ul>
							<li style="width:17%;"><div class="border_radius_l_1"><a href="javascript:;">基本信息</a></div><div class="div_x_1"></div></li>
							<li style="width:16%;"><div><a href="javascript:;">服务项目</a></div><div class="div_x_1"></div></li>
							<li style="width:17%;"><div class="sel_div_1"><a href="javascript:;">排班信息</a></div><div class="div_x_1"></div></li>							
							<li style="width:16%;"><div><a href="javascript:;">预约项目</a></div><div class="div_x_1"></div></li>
							<li style="width:17%;"><div><a href="javascript:;">回访信息</a></div><div class="div_x_1"></div></li>
							<li style="width:17%;"><div class="border_radius_r_1"><a href="javascript:;">评价信息</a></div><div class="div_x_1"></div></li>
						</ul>												
					</div>

               
               	  </td>
                </tr>
                <tr>
                  <td><table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_14_707070_1" >
                    <tr class="fff">
                      <td height="45" class="b_l_r_b_dcdcdc_1"><table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <td height="40" align="right" class="z_14_4c98f6_1 pos_r_1">
                              <select name="select" id="workDay" class="cate_o">
	                              [#list dateDays as dateDay]
	                              	<option value="${dateDay.dateDay?string("yyyyMMdd")}">${dateDay.week} ${dateDay.dateDay?string("yyyy-MM-dd")}</option>
	                              [/#list]
                              </select>
								
                            </td>
                          </tr>
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_666666_1">
                              <tr>
                                <td align="center" valign="top" height="5"><hr class="b_b_dcdcdc_2" /></td>
                              </tr>
                              <tr>
                                <td align="left" valign="top" class="p_10_20_1">
									<div class="det">
										<div id="temp" class="temp"></div>
										<div id = "work" class="work" style="width:100%;margin-left: 0%;"></div>
										[#list workDay.workDayItems as workDayItem]
											[#if workDayItem.mechanism.equals(mechanism)]
											   [#if workDayItem.workDayType == "reserve"]
											   		<div class="appo" style="width:${width(mechanism.workDate.startTime,mechanism.workDate.endTime,workDayItem.startTime,workDayItem.endTime)};margin-left:${marginLeft(mechanism.workDate.startTime,mechanism.workDate.endTime,workDayItem.startTime,workDayItem.endTime)};cursor: pointer;">${workDayItem.order.patientMember.name}</div>
											   [/#if]
											   [#if workDayItem.workDayType == "locking"]
											   <div class="lock"  onClick="close_lock(${workDayItem.id});" style="width:${width(mechanism.workDate.startTime,mechanism.workDate.endTime,workDayItem.startTime,workDayItem.endTime)};margin-left:${marginLeft(mechanism.workDate.startTime,mechanism.workDate.endTime,workDayItem.startTime,workDayItem.endTime)};cursor: pointer;">解锁</div>
											   [/#if]
											   [#if workDayItem.workDayType == "rest"]
											   <div class="rest"  onClick="close_lock(${workDayItem.id});" style="width:${width(mechanism.workDate.startTime,mechanism.workDate.endTime,workDayItem.startTime,workDayItem.endTime)};margin-left:${marginLeft(mechanism.workDate.startTime,mechanism.workDate.endTime,workDayItem.startTime,workDayItem.endTime)};cursor: pointer;">休息</div>
											   [/#if]
											   [#if workDayItem.workDayType == "mechanism"]
											   <div class="mechanism" style="width:${width(mechanism.workDate.startTime,mechanism.workDate.endTime,workDayItem.startTime,workDayItem.endTime)};margin-left:${marginLeft(mechanism.workDate.startTime,mechanism.workDate.endTime,workDayItem.startTime,workDayItem.endTime)};cursor: pointer;">${mechanism.name}</div>
											   [/#if]
											[/#if]
										[/#list]
									</div>
									<div class="ruler"></div>
                                </td>
                              </tr>
                              <tr>
                              	<td>
                              		<table cellpadding="0" cellspacing="0" border="0" width="60%" align="left" style="margin-left: 80px;">
                              			<tr>
                              				<td height="30" colspan="2"></td>
                              			</tr>
                              			<tr>
                              				<td height="20" width="20"><div style="width: 15px; height: 15px; border-radius: 15px; background:#eee;display: block;"></div></td>
											<td>休息或未排班，患者不可预约</td>
										</tr>
                              			<tr>
                              				<td height="20" width="20"><div style="width: 15px; height: 15px; border-radius: 15px; background:#cee8bc;display: block;"></div></td>
											<td>该机构工作时间</td>
										</tr>
                              			<tr>
                              				<td height="20" width="20"><div style="width: 15px; height: 15px; border-radius: 15px; background:#7195bc;display: block;"></div></td>
											<td>锁定状态，患者不可预约，点击对应时间轴可取消锁定</td>
										</tr>
                              			<tr>
                              				<td height="20" width="20"><div style="width: 15px; height: 15px; border-radius: 15px; background:#f6b032;display: block;"></div></td>
											<td>已被预约</td>
										</tr>
										<tr>
                              				<td height="20" width="20"><div style="width: 15px; height: 15px; border-radius: 15px; background:#90b57f;display: block;"></div></td>
											<td>该医生的上班时间,可预约</td>
										</tr>
                              		</table>
                              	</td>
                              </tr>
                              <tr>
                                <td align="center" valign="top" height="5"><hr class="b_b_dcdcdc_3" /></td>
                              </tr>
                              <tr>
                              	<td>
                              		<table cellpadding="0" cellspacing="0" border="0" width="100%">
                              			<tr>
                              				<td valign="top" width="50%">
                              					<form id = "inputForm" action="save.jhtml" method="post">
                              					<input type="hidden" id="workDayId" name = "workDayId" value = "${workDay.id}">
                              					<input type="hidden" id="doctorId" name = "doctorId" value = "${doctor.id}">
                              					<table cellpadding="0" cellspacing="0" border="0" width="250" align="center">
                              						<tr>
                              							<td colspan="2" class="z_14_4c98f6_1">状态设置</td>
                              						</tr>
                              						<tr>
                              							<td height="35" width="100" align="right">状态选择：</td>
                              							<td>
															<select id="flag" class="cate_o" name="workDayType" onChange="disp_tr($(this).val());">
																<option value="">请选择</option>
																[#list workDayTypes as workDayType]
																<option value="${workDayType}">${message("WorkDayItem.WorkDayType."+workDayType)}</option>
																[/#list]
															</select>
                             							</td>
                              						</tr>
                              						<tr id="item" style="display: none;">
                              							<td height="35" width="100" align="right">服务项目：</td>
                              							<td width="150">
															<select id="items" class="cate_o" name="projectId" onChange="set_width_item();set_margin_left($('#start_time').val(),'');">
																<option value="">请选择</option>
																[#list doctor.projects as project]
																	[#if project.mechanism.equals(mechanism)]
																	<option data_time="${project.time}" value="${project.id}">${project.name}</option>
																	[/#if]
																[/#list]
															</select>
                             							</td>
                              						</tr>
                              						<tr id="patient" style="display: none;">
                              							<td height="35" width="100" align="right">患者姓名：</td>
                              							<td width="150">
															<select id="patientId" name="patientId" class="cate_o" >
																<option value="">请选择</option>
																[#list mechanism.patientMember as patient]
																	<option value="${patient.id}">${patient.name}</option>
																[/#list]
															</select>
                             							</td>
                              						</tr>
                               						<tr id="fre" style="display: none;">
                              							<td height="35" width="100" align="right">预约次数：</td>
                              							<td width="150"><input type="text" value="1" id="num" name = "num"  class="inp_3 h_28_1 w_40_1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onBlur="set_width_item();set_margin_left($('#start_time').val(),'');"> 次</td>
                              						</tr>
                             						<tr id="s_time" style="display: none;">
                              							<td height="35" width="100" align="right">开始时间：</td>
                              							<td width="150">
                              								<input type="text" id="start_time" name = "start_time" value="" onclick="WdatePicker({dateFmt: 'HH:mm', qsEnabled:false, Hchanged:change_start_time , mchanged:change_start_time});" onChange="set_margin_left($('#start_time').val(),'');if($('#flag').val() == 'reserve') set_width_item(); else set_width_time($('#start_time').val(),$('#end_time').val(),'');">
                              							</td>
                              						</tr>
                             						<tr id="e_time" style="display: none;">
                              							<td height="35" width="100" align="right">结束时间：</td>
                              							<td width="150">
															<input type="text" id="end_time" name = "end_time" value="" onfocus="WdatePicker({dateFmt: 'HH:mm', Hchanged:change_end_time , mchanged:change_end_time});" onChange="change_end_time($('#start_time').val(),$('#end_time').val());" >
                              							</td>
                              						</tr>
                             						
                              						<tr>
                              							<td align="right" height="15" colspan="2"></td>
                              						</tr>
                              						<tr id="p_t" style="display: none;">
                              							<td width="100" align="right">&nbsp;</td>
                              							<td width="150" align="">
															<input type="button" name="button7" id="button7" value=" 提 交 " onClick="submits();" class="button_1 b_4188d1_1" />　
                             								<input type="reset" name="button7" id="button7" value=" 重 置 " class="button_1 b_ececec_1 z_12_707070_1" />
														</td>
                              						</tr>
                              					</table>
                              					</form>
                              				</td>
                              				<td valign="top" width="50%"></td>
                              			</tr>
                              		</table>
                              	</td>
                              </tr>
                            </table>
                              </td>
                          </tr>
                          <tr>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td>&nbsp;</td>
                          </tr>
                        </tbody>
                      </table></td>
                    </tr>
                  </table></td>
                </tr>
              </tbody>
            </table></td>
            </tr>
        </table>
        </div> 

<div class="clear"></div>
</body>
</html>