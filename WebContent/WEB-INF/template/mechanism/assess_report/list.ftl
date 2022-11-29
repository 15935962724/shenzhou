<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/iconfont.css" rel="stylesheet" type="text/css">
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script>
	//隐藏框显示
	function disp_hidden_d(d_id,d_width,d_height,p_id,id)
		{
			if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
				{
					var w=d_width / 2;
					var h=d_height / 2;
					$("."+d_id).css({"width":  d_width + "px","height": d_height + "px","left":"50%","top":($(document).scrollTop()+50) + "px","margin-left": "-" + w + "px","margin-bottom":"50px"});
					if (self.frameElement && self.frameElement.tagName == "IFRAME")
						{
							$("."+d_id).css({"top":$(parent.document).scrollTop()+50})
							if($(parent.document).height()<(d_height+$(parent.document).scrollTop()))
								$("#main_ifr",parent.document).height((parseInt(d_height)+$(parent.document).scrollTop()+150)+"px");
						}

				}else{
					ifr_height("main_ifr");
				}
			$("#"+p_id).val(id);
			$("#"+d_id).toggle();	
		}

	function switch_tag(id,select_tag,flag)
		{
			if(flag=="over")
				{
					if (id != select_tag)
						{
							$("#tag_" + id).removeClass("z12575757");
							$("#tag_" + id).removeClass("bgffffff");
							$("#tag_" + id).addClass("z12ffffff"); 
							$("#tag_" + id).addClass("bg279fff"); 
						}
				}
			else
				{
					if(id != select_tag)
						{
							$("#tag_" + id).addClass("z12575757");
							$("#tag_" + id).addClass("bgffffff");
							$("#tag_" + id).removeClass("z12ffffff"); 
							$("#tag_" + id).removeClass("bg279fff"); 
						}
				}
		}
	$(function(){
	
	
	
	
	
	
	
		$("#flag_1 :radio").click(function(){
			$("#rehstate").html($(this).val());
			$('#flag_1').css('display','none');
		})
		
		
		//诊评报告展开
		$(".asse").click(function(){
			if($(this).closest("tr").next("tr").length>0)
				{
					$(this).closest("tr").next("tr").remove();
				}
			else
				{
					//现况说明
					var nowExplain = $(this).attr('data_nowExplain');
				
					//评估结果
					var assessResult = $(this).attr('data_assessResult');
					
					//康复建议
					var proposal = $(this).attr('data_proposal');
					
					//起始时间
					var start_date = $(this).attr('data_start_date');
					
					//结束时间
					var end_date = $(this).attr('data_end_date');
					
					//患者id
					var patientMemberId = ${patientMember.id};
					
					//当前
					var $this = $(this);
				

					
						 $.ajax({
			             type: "POST",
			             url: "recoveryPlanList.jhtml",
			             data: {
				             patientMemberId:patientMemberId,
				             createDate:start_date,
				             endDate:end_date
				             },
			             dataType: "json",
			             success: function(data){
					             if(data.status=="200"){
											var html='';
											html +='		<tr>';
											html +='			<td>';
											html +='				<table cellpadding="0" cellspacing="0" border="0" width="100%">';
											html +='					<tr>';
											html +='						<td class="bgeef7ff pa20">';
											html +='							<table cellpadding="0" cellspacing="0" border="0" width="100%">';
											html +='								<tr>';
											html +='									<td class="iconfont z16ff811b lh40">';
											html +='										&#xe605;<span class="z16444444 lh40">现状说明</span>';
											html +='									</td>';
											html +='								</tr>';
											html +='								<tr>';
											html +='									<td class="z14717171 pl15">';
											html +=										nowExplain;
											html +='									</td>';
											html +='								</tr>';
											html +='								<tr>';
											html +='									<td height="30" valign="bottom">';
											html +='										<hr class="banone bb1dd4d4d4">';
											html +='									</td>';
											html +='								</tr>';
											html +='								<tr>';
											html +='									<td class="iconfont z16ff811b lh40">';
											html +='										&#xe605;<span class="z16444444 lh40">诊评结果</span>';
											html +='									</td>';
											html +='								</tr>';
											html +='								<tr>';
											html +='									<td class="z14717171 pl15">';
											html +=										assessResult
											html +='									</td>';
											html +='								</tr>';
											html +='								<tr>';
											html +='									<td height="30" valign="bottom">';
											html +='										<hr class="banone bb1dd4d4d4">';
											html +='									</td>';
											html +='								</tr>';
											html +='								<tr>';
											html +='									<td class="iconfont z16ff811b lh40">';
											html +='										&#xe605;<span class="z16444444 lh40">康护建议</span>';
											html +='									</td>';
											html +='								</tr>';
											html +='								<tr>';
											html +='									<td class="z14717171 pl15">';
											html +=										proposal
											html +='									</td>';
											html +='								</tr>';
											html +='								<tr>';
											html +='									<td class="iconfont"></td>';
											html +='								</tr>';
											html +='							</table>';
											html +='						</td>';
											html +='					</tr>';
											html +='					<tr>';
											html +='						<td>';
											html +='<!--此处为添加康复计划-->';
											html +='							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="mb20">';
											html +='								<tr>';
											html +='									<td class="pr">';
											html +='										<a href="javascript:;"  class="dpib k_2 pa20 br5" onClick="disp_hidden_d(\'assess\',600,550,\'p_id\','+$this.attr("data_id")+')">';
											html +='											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
											html +='												<tr>';
											html +='													<td class="z20444444"><span class="iconfont z20279fff">&#xe62c;</span> 康复计划</td>';
											html +='													<td align="right" class="z14279fff">添加计划</td>';
											html +='												</tr>';
											html +='											</table>';
											html +='										</a>';
											html +='									</td>';
											html +='								</tr>';
											html +='							</table>';
											html +='<!--康复计划循环开始-->';
					             //console.log(data.data);
					              //console.log(data.data.assessReportlist);
					              var dataObj = data.data.assessReportlist;
					              console.log(dataObj.length);
						             for(var i = 0; i<dataObj.length; i++){
						             
						             		html +='							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="mb20"><!--最后一个康复计划无mb20样式-->';
											html +='								<tr>';
											html +='									<td class="pr">';
											html +='										<a href="javascript:;" data_id="'+dataObj[i].recoveryPlanId+'" data_start_date="'+dataObj[i].recoveryPlanStartTime+'" data_end_date = "'+dataObj[i].recoveryPlanEndTime+'" class="dpib k_2 pa20 br5 plan">';
											html +='											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
											html +='												<tr>';
											html +='													<td class="z20444444"><span class="iconfont z20279fff">&#xe62c;</span> 康复计划</td>';
											html +='													<td align="right" class="z14279fff">'+dataObj[i].recoveryPlanStartTime+'至'+dataObj[i].recoveryPlanEndTime+'</td>';
											html +='												</tr>';
											html +='											</table>';
											html +='										</a>';
											html +='									</td>';
											html +='								</tr>';
											html +='							</table>';
						             
						             }
											html +='<!--康复计划循环结束-->';
											
											html +='						</td>';
											html +='					</tr>';
											html +='				</table>';
											html +='			</td>';
											html +='		</tr>';
											$(".asse").closest("tr").next("tr").remove();
											$this.closest("tbody").append(html);	
											ifr_height("main_ifr");				             
					             }
			               	  }
			        	 });

				}
		})
			//康复计划展开
		$("table").on("click",".plan",function(){
			
			if($(this).closest("tr").next("tr").length>0)
				{
					$(this).find("span").html('&#xe62c;');
					$(this).closest("tr").next("tr").remove();
				}
			else
				{
				
				var $this = $(this);
				var id = $this.attr("data_id");
					
				console.log($this.html());	

				var start_date = $this.attr("data_start_date");
				
				var end_date = $this.attr("data_end_date");
				
				var file = '{"patientMemberId":${patientMember.id},"recoveryPlanId":"'+id+'","createDate":"'+start_date+'","endDate":"'+end_date+'"}';
				
				$.ajax({
				
			             type: "POST",
			             url: "${base}/mechanism/recoveryPlan/view.jhtml",
			             data: {
				             file:file
				             },
			             dataType: "json",
			             success: function(data){
						             if(data.status=="200"){
						             	console.log($this.html());	
						             	console.log($this.closest("table").html());
						             	var html='',iconf='';
						                var dataObj = data.data;
						                
						            	html +='					<tr>';
										html +='						<td>';
										html +='							<table cellpadding="0" cellspacing="0" border="0" width="100%">';
										html +='								<tr>';
										html +='									<td class="bgffffff pa20">';
										html +='										<table cellpadding="0" cellspacing="0" border="0" width="100%">';
										html +='											<tr>';
										html +='												<td class="iconfont z16ff811b lh40">';
										html +='													&#xe605;<span class="z16444444 lh40">计划内容</span>';
										html +='												</td>';
										html +='											</tr>';
										html +='											<tr>';
										html +='												<td class="z14717171 pl15">';
										html +='													<table cellpadding="0" cellspacing="0" border="0" width="100%">';
										html +='														<tr>';
										html +='															<td width="80" height="30">康护医师：</td>';
										html +='															<td>'+dataObj.recoveryDoctorName+'</td>';
										html +='														</tr>';
										html +='														<tr>';
										html +='															<td height="30">计划周期：</td>';
										html +='															<td>'+dataObj.recoveryPlanStartTime+'至'+dataObj.recoveryPlanEndTime+'</td>';
										html +='														</tr>';
										html +='														<tr>';
										html +='															<td height="30" valign="top" class="pt5">训练内容：</td>';
										html +='															<td valign="top" class="pt5">';
										
										for(var i = 0; i<dataObj.recoveryPlanDrillContents.length;i++){
											html += dataObj.recoveryPlanDrillContents[i].serverProjectCategoryName + dataObj.recoveryPlanDrillContents[i].time +'节<br>';
										}
										
										html +='															</td>';
										html +='														</tr>';
										html +='														<tr>';
										html +='															<td width="70" height="30" valign="top" class="pt5">短期目标：</td>';
										html +='															<td class="pt5">'+dataObj.recoveryPlanShortTarget+'</td>';
										html +='														</tr>';
										html +='														<tr>';
										html +='															<td width="70" height="30" valign="top" class="pt10">长期目标：</td>';
										html +='															<td valign="top" class="pt10">'+dataObj.recoveryPlanLongTarget+'</td>';
										html +='														</tr>';
										html +='													</table>';
										html +='												</td>';
										html +='											</tr>';
										html +='											<tr>';
										html +='												<td height="40">';
										html +='													<hr class="banone bb1dd4d4d4">';
										html +='												</td>';
										html +='											</tr>';
										html +='											<tr>';
										html +='												<td class="iconfont z16ff811b lh40">';
										html +='													&#xe605;<span class="z16444444 lh40">康复记录</span>';
										html +='												</td>';
										html +='											</tr>';
										html +='											<tr>';
										html +='												<td class="z14717171 pl15">';
										html +='													<table cellpadding="0" cellspacing="0" border="0" width="100%">';
											
										for(var i = 0; i < dataObj.recoveryRecords.length;i++){
											html +='														<tr>';
											html +='															<td class="bgeef7ff">';
											html +='																<table cellpadding="0" cellspacing="0" border="0" class="mtb10" width="100%">';
											html +='																	<tr>';
											html +='																		<td align="center" valign="middle" width="120" rowspan="2" class="z14717171 brcdd6df">';
											html +=																		dataObj.recoveryRecords[i].recoveryDate;
											html +='																		</td>';
											html +='																		<td width="90" align="center" class="z126fbfff lh170 pt5" valign="top">康复内容</td>';
											html +='																		<td class="lh170 pt5" valign="top">'+dataObj.recoveryRecords[i].recoveryContent+'</td>';
											html +='																	</tr>';
											html +='																	<tr>';
											html +='																		<td align="center" class="z126fbfff pt5" valign="top">疗效总结</td>';
											html +='																		<td class="lh170 pt5" valign="top">'+dataObj.recoveryRecords[i].effect+'</td>';
											html +='																	</tr>';
											html +='																</table>';
											html +='															</td>';
											html +='														</tr>';
											html +='														<tr>';
											html +='															<td height="10"></td>';
											html +='														</tr>';
										}
										
										html +='													</table>';
										html +='												</td>';
										html +='											</tr>';
										html +='										</table>';
										html +='									</td>';
										html +='								</tr>';
										html +='								<tr>';
										html +='									<td>';
										html +='										<table cellpadding="0" cellspacing="0" border="0" width="100%">';					
										html +='											<tr>';
										html +='												<td class="pr">';
										
										if(!dataObj.recoveryPlanSummary){
												html +='													<a href="javascript:;" id="addSummary"  data_summary = ""   class="dpib k_2 pa20 br5" onClick="disp_hidden_d(\'visit\',600,220,\'recoveryPlanId\','+dataObj.recoveryPlanId+')">';
												html +='														<table cellpadding="0" cellspacing="0" border="0" width="100%">';
												html +='															<tr>';
												html +='																<td class="z20444444"><span class="iconfont z20279fff">&#xe62c;</span> 疗效总结</td>';
												html +='																<td align="right" class="z14279fff">添加总结</td>';
												html +='															</tr>';
												html +='														</table>';
												html +='													</a>';
												html +='<!--上面table为添加总结，下面table为已添加好总结-->';
										}else{
												html +='													<a href="javascript:;" data_summary = "'+dataObj.recoveryPlanSummary+'"  class="dpib k_2 pa20 br5 summary">';
												html +='														<table cellpadding="0" cellspacing="0" border="0" width="100%">';
												html +='															<tr>';
												html +='																<td class="z20444444"><span class="iconfont z20279fff">&#xe62c;</span> 疗效总结</td>';
												html +='																<td align="right" class="z14279fff">展示详情</td>';
												html +='															</tr>';
												html +='														</table>';
												html +='													</a>';
										}
										
										html +='												</td>';
										html +='											</tr>';
										html +='										</table>';
										html +='									</td>';
										html +='								</tr>';
										html +='							</table>';					
										html +='						</td>';
										html +='					</tr>';
										iconf = "&#xe62c;";//收起箭头
										$(".plan").find("span").html(iconf);
										$(".plan").closest("tr").next("tr").remove();
										iconf = "&#xe631;";//打开箭头
										$this.find("span").html(iconf);
										console.log($this.find("span").html());
										
										$this.closest("tbody").append(html);
										//console.log($this.closest("tbody").next().html());
										ifr_height("main_ifr");
						             
						             }else{
						                 $.message("warn","数据加载错误");
						                 return false;
						             }
						             
						         }    
					    });
							
				}
		})
			//疗效总结展开
		$("table").on("click",".summary",function(){
			var html='',iconf='';
			if($(this).closest("tr").next("tr").length>0)
				{
					iconf = "&#xe62c;";
					$(this).find("span").html(iconf);
					$(this).closest("tr").next("tr").remove();
				}
			else
				{
					
				
					html +='								<tr>';
					html +='									<td class="bgffffff pa20">';
					html +='										<table cellpadding="0" cellspacing="0" border="0" width="100%">';
					html +='											<tr>';
					html +='												<td class="bgeef7ff pa20 lh170">';
					html += $(this).attr("data_summary") ;
					html +='												</td>';
					html +='											</tr>';
					html +='										</table>';
					html +='									</td>';
					html +='								</tr>';
					
					iconf = "&#xe62c;";//收起箭头
					$(".summary").find("span").html(iconf);
					$(".summary").closest("tr").next("tr").remove();
					iconf = "&#xe631;";//打开箭头
					$(this).find("span").html(iconf);
					$(this).closest("tbody").append(html);
					ifr_height("main_ifr");
				}
		})
		
	//添加服务项目
	$("#remove").click(function(){
		if(!$("#project_s option").is(":selected"))
			{			
				$.message("warn","${message("请选择服务项目")}");
				return false;
			}
		if(isNaN($("#n").val()) || $("#n").val()<1)
			{
				$.message("warn","${message("服务次数应为大于0的数字")}");
				return false;
			}
		var idx = $("#project_s option:selected").val() + "," + $("#n").val();
		var txt = $("#n").val() + "节 " + $("#project_s option:selected").text()
		$("#project").append("<option value='" + idx + "'>" + txt + "</option>")
		$('#project_s option:selected').remove();
	});
	
	//取消服务项目
	$("#project").dblclick(function(){
		var idx = $("#project option:selected").val().split(",");
		var txt = $("#project option:selected").text().split("节 ");
		$("#project_s").append("<option value='" + idx[0] + "'>" + txt[1] + "</option>")
		$('#project option:selected').remove();
	})
	
	
	//提交
	$('#recoveryPlanSave').click(function(){
		var $myform = $('#myform');
		var recoveryDoctorId = $('#recoveryDoctorId').val();
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		var shortTarget = $('#shortTarget').val();
		var longTarget = $('#longTarget').val();
		
		if(recoveryDoctorId == ""){
	       $.message("warn","${message("请选择康复医师")}");
	       return false;
	    }
		
	    if(startTime == "" || endTime == ""){
	       $.message("warn","${message("请填写康复周期")}");
	       return false;
	    }
	    
	 	if(shortTarget == ""){
	       $.message("warn","${message("请填写短期目标")}");
	       return false;
	    }
	    
	    if(longTarget == ""){
	       $.message("warn","${message("请填写长期目标")}");
	       return false;
	    }
		
		var i = $('#project option').size();
		if(i<1){
		$.message("warn", "至少添加一个康护项目");
		return false;
		}
	
		var tmp_str="";
		for(var j = 0; j < i; j++){
				tmp_str = tmp_str  + $('#project option:eq('+j+')').val() + "&";
		}
		console.log(tmp_str);
		$('#drill').val(tmp_str);
	 	$myform.submit();
	
	});
	
	[@flash_message /]
	
	//提交疗效总结
	$('#summarySave').click(function(){
		var summary = $('#summary').val();
		var recoveryPlanId = $('#recoveryPlanId').val();
		if(summary == ""){
		   $.message("warn","请填写疗效总结");
		   
		   return ;
		}
		
		var $this = $(this);
		
			$.ajax({
			             type: "POST",
			             url: "${base}/mechanism/recoveryPlan/summarySave.jhtml",
			             data: {
				             recoveryPlanId:recoveryPlanId,
				             summary:summary
				             },
			             dataType: "json",
			             success: function(message){
			             	$.message(message);
			             	
			             	
			             	
			             	var html = "";
							html +='								<tr>';
							html +='									<td class="bgffffff pa20" colspan="2">';
							html +='										<table cellpadding="0" cellspacing="0" border="0" width="100%">';
							html +='											<tr>';
							html +='												<td class="bgeef7ff pa20 lh170">';
							html +=summary;
							html +='												</td>';
							html +='											</tr>';
							html +='										</table>';
							html +='									</td>';
							html +='								</tr>';			             	

							$("#addSummary").closest("tbody").append(html);
							$("#addSummary").find("td").eq(1).html("展示详情");
							$("#addSummary").addClass("summary");
							$("#addSummary").removeAttr("onclick");
							$("#addSummary").attr("data_summary",summary);
							$('#visitform')[0].reset();
							disp_hidden_d('visit','','','v_id');
							ifr_height("main_ifr");
							
							return false;
			             }
			             
			             
			      });       
			             
			             
		
	
	});
	
	
	
	})
</script>
<style>
	#flag_1{padding: 0px;left: 130px;top: 0px; position: absolute; display: none;}
</style>
</head>
<body>

<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mb20">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">健康档案</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<!--<a href="javascript:;">导出</a>-->
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					[#include "/mechanism/include/patient_view.ftl"]
				</table>
			</td>
		</tr>
	</table>
	[#list assessReports as assessReport]
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="[#if assessReport_index!=0]mt20 [/#if] z16646464"><!--第二个开始加样式：mt20-->
		<tr>
			<td class="pr">
				<a href="javascript:;" data_id="${assessReport.id}" data_nowExplain="${assessReport.nowExplain}" data_assessResult="${assessReport.assessResult}" data_proposal="${assessReport.proposal}" data_start_date="${assessReport.createDate?string("yyyy-MM-dd HH:mm:ss")}"  [#if assessReport_index+1 == assessReports.size()] data_end_date="${.now?string("yyyy-MM-dd HH:mm:ss")}" [#else] data_end_date="${assessReports[assessReport_index+1].createDate?string("yyyy-MM-dd HH:mm:ss")}" [/#if] class="dpib k_2 pa20 asse">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20444444">评估报告</td>
						<td align="right">评估人：${assessReport.doctor.name}</td>
					</tr>
					<tr>
						<td>${assessReport.diseaseName}</td>
						<td align="right" width="150">${assessReport.createDate?string("yyyy-MM-dd")}</td>
					</tr>
				</table>
				</a>
			</td>
		</tr>
	</table>
	[/#list]
</div>
<div id="assess">
	<div class="assess">
  		<form id="myform" action = "${base}/mechanism/recoveryPlan/save.jhtml" method="POST">
  		<input type="hidden" id="p_id" name="assessReportId" >
  		<input type ="hidden" id = "drill"  name = "drill" />
  		<input type ="hidden" id = "patientMemberId"  name = "patientMemberId" value = "${patientMember.id}" />
  		
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					添加康复计划
  				</td>
   			</tr>
			<tr>
				<td height="50" class="z14323232" align="right">康复医师</td>
				<td align="right" class="pr65">
					<select id="redoctorId" name="redoctorId" class="w150 inputkd9d9d9bgf6f6f6 h30 z14444444">
						[#list doctorMechanismRelations as doctorMechanismRelation]
						    [#if !doctorMechanismRelation.isSystem]
							    <option value = "${doctorMechanismRelation.doctor.id}">${doctorMechanismRelation.doctor.name}</option>
						    [/#if]
						[/#list]
					</select>
  				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					康复周期
   				</td>
   				<td class="plr20">
   					<input type="text" placeholder="开始时间" id = "startTime" name = "startTime" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endTime\')}'});" class="k_3 h30 inputkd9d9d9bgf6f6f6 tac w202">
   					 - 
   					<input type="text" placeholder="结束时间" id = "endTime" name = "endTime" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startTime\')}'});" class="k_3 h30 inputkd9d9d9bgf6f6f6 tac w202">
   				</td>
   			</tr>
  			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					康复项目
   				</td>
   				<td>
   					<table cellpadding="0" cellspacing="0" border="0" class="m1020">
						<tr>
							<td width="160" valign="top">
								<select name="project" size="6" class="w150 h100 inputkd9d9d9bgf6f6f6" id="project">
									
								</select>
								<br>
								<font color="#FF0000">* 双击取消</font>
							</td>
							<td width="25" class="iconfont">&#xe6bf;</td>
							<td width="160" valign="top">
								<select name="project_s" size="6" class="w150 h100 inputkd9d9d9bgf6f6f6" id="project_s">
									[#list serverProjectCategorys as serverProjectCategory]
										[#if !serverProjectCategory.isDeleted]
											<option value="${serverProjectCategory.id}">${serverProjectCategory.name}</option>
										[/#if]
									[/#list]
								</select>
							</td>
							<td align="center" valign="bottom" class="pb20">
								<input name="n" class="w15 tac" type="text" id="n" value="1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">节
						  		<input type="button" name="remove" id="remove" class="button_1 b_d17441_1" value="添加">
							</td>
						</tr>
					</table>
   				</td>
   			</tr>
  			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					短期目标
   				</td>
   				<td>
   					<textarea id = "shortTarget" name = "shortTarget" class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					长期目标
   				</td>
   				<td>
   					<textarea id = "longTarget" name = "longTarget" class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td align="center" colspan="2" height="60">
   					<input type="submit" value="确认" class="button_3" id="recoveryPlanSave">
   					&nbsp;　&nbsp;
					<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#myform')[0].reset();disp_hidden_d('assess','','','p_id');">
  					
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>
<div id="visit">
	<div class="visit">
  		<form id="visitform">
  		<input type="hidden" id="recoveryPlanId" name="recoveryPlanId" >
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4">
   					添加康复总结内容
  				</td>
   			</tr>
   			<tr>
   				<td height="10"></td>
   			</tr>
   			<tr>
   				<td>
   					<table cellpadding="0" cellspacing="0" border="0" width="90%" align="center" class="z14323232">
   						<tr>
   							<td width="90">康复总结</td>
   							<td align="right">
   								<textarea id = "summary" name = "summary" class="z14323232 inputkd9d9d9bgf6f6f6 w450 mtb10 h100"></textarea>
   							</td>
   						</tr>
						<tr>
							<td align="center" colspan="2" height="60">
								<input type="button" value="确认" id="summarySave" class="button_3">
								&nbsp;　&nbsp;
								<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#visitform')[0].reset();disp_hidden_d('visit','','','v_id');">
							</td>
						</tr>
   					</table>
   				</td>
   			</tr>
   		</table>
   		</form>
	</div>
</div>

</body>
</html>