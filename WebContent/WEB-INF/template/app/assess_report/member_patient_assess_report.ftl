<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/app/css/light7.min.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/app/css/light7-swiper.min.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/app/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/app/css/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/app/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/app/js/light7.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/app/js/light7-swiper.min.js" charset="utf-8"></script>

<style>
	.pull-to-refresh-layer{margin-top: 4.075rem;}	
	.bar{padding-right: 0px;padding-left: 0px;border-bottom:none;}
	.page{background: #f0f1f0;}
	.card{border-radius: 0; box-shadow: #ffffff 0px 0rem 0rem;margin: 0px;background: #f5f5f5;/*margin-top:3.7rem;*/margin-top:0.5rem;}
	.bl24c98f6{border-left: 2px solid #4c98f6;}
	.w05{width: 0.5rem;}
	.assess{ display: inline-block;background: #ffffff;border-radius: 0.5rem; box-shadow: 0 0 15px 1px rgba(30,86,137,0.18); padding: 1rem 0.7rem;position: relative;padding-left: 2rem;}
	.assess img{position: absolute;top: -0.25rem;left: 0.3rem;}
	.card-content-inner{padding: 0.75rem 0.75rem 0.1rem 0.75rem;}
</style>

<script>
$(function () {		
	
	$.init();
	
	$(".card-content").css("min-height" , window.screen.height + "px");  

	//展开评估报告
	$("table").on("click",".assess",function(){
		
		if($(this).closest("tr").next("tr").length>0)
			{
				$(this).closest("tr").next("tr").remove();
			}
		else
			{
					var assessReportId = $(this).attr('data_id');
			
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
			        
			        //手动封装json参数 
			        var file = '{"patientMemberId":'+patientMemberId+',"createDate":"'+start_date+'","endDate":"'+end_date+'"}';
			         
					 $.ajax({
			             type: "GET",
			             url: "recoveryPlanList.jhtml",
			             data: {
				             file:file
				             },
			             dataType: "json",
			             success: function(data){
					             if(data.status=="200"){
					             	var html = "";
		             				html += '					<tr>';
									html += '						<td>';
									html += '							<div class="k">';
									html += '								<table cellpadding="0" cellspacing="0" border="0" width="100%">';
									html += '									<tr>';
									html += '										<td class="z1323232 lh1875">';
									html += '											<img src="${base}/resources/app/images/2.png" class="w1875 mw45" align="absmiddle">现状说明';
									html += '										</td>';
									html += '									</tr>';
									html += '									<tr>';
									html += '										<td class="z0924666666">'+nowExplain+'</td>';
									html += '									</tr>';
									html += '									<tr>';
									html += '										<td height="10">';
									html += '											<hr class="banone bb2bd6d6d6">';
									html += '										</td>';
									html += '									</tr>';
									html += '									<tr>';
									html += '										<td class="z1323232 lh1875">';
									html += '											<img src="${base}/resources/app/images/2.png" class="w1875 mw45" align="absmiddle">诊评结果';
									html += '										</td>';
									html += '									</tr>';
									html += '									<tr>';
									html += '										<td class="z0924666666">'+assessResult+'</td>';
									html += '									</tr>';
									html += '									<tr>';
									html += '										<td height="10">';
									html += '											<hr class="banone bb2bd6d6d6">';
									html += '										</td>';
									html += '									</tr>';
									html += '									<tr>';
									html += '										<td class="z1323232 lh1875">';
									html += '											<img src="${base}/resources/app/images/2.png" class="w1875 mw45" align="absmiddle">康复建议';	
									html += '										</td>';
									html += '									</tr>';
									html += '									<tr>';
									html += '										<td class="z0924666666">'+proposal+'</td>';
									html += '									</tr>';
									html += '								</table>';
									html += '							</div>';
									
									var dataObj = data.data.assessReportlist;
									for(var i = 0; i<dataObj.length; i++){
										html += '							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="mt1">';
										html += '								<tr>';
										html += '									<td class="k br05">';
										html += '										<a href="javascript:;" data_id="'+dataObj[i].recoveryPlanId+'" class="list_plan">';
										html += '										<table cellpadding="0" cellspacing="0" border="0" width="100%">';
										html += '											<tr>';
										html += '												<td class="z1323232 lh1875">';
										html += '													<img src="${base}/resources/app/images/4.png" class="w14 mw35 hauto" align="absmiddle">健康计划';	
										html += '												</td>';
										html += '												<td class="z09244d96f3" align="right">'+dataObj[i].recoveryPlanStartTime+'至'+dataObj[i].recoveryPlanEndTime+'</td>';
										html += '											</tr>';
										html += '										</table>';
										html += '										</a>';
										html += '									</td>';
										html += '								</tr>';
										html += '							</table>';
									}
									html += '						</td>';
									html += '					</tr>';
									$(".assess").closest("tr").next("tr").remove();
									$this.closest("tbody").append(html);
									
					             }else{
					             	$.alert('数据加载错误', '错误!');
					             	return false;
					             }
					          }
					      });       
			
			
			
			

			}
	})
	
	
	
	//显示添加计划表单
	$("table").on("click",".add_plan",function(){
	var assessReportId =  $(this).attr('data_assessReportId');
		var html="";
		if($(this).closest("tr").next("tr").length>0)
			{
				$(this).closest("tr").next("tr").remove();
				$(this).find("img").attr("src","${base}/resources/app/images/4.png");
			}
		else
			{
				html += '						<tr>';
				html += '							<td>';
				html += '								<div class="k z0924323232">';
				html += '								<form id="plan" action = "save.jhtml" method = "POSt">';
				
				html += '								<table cellpadding="0" cellspacing="0" border="0" width="100%">';
				html += '									<tr>';
				html += '										<td class="z1323232 lh1875 w68">';
				html += '											<img src="${base}/resources/app/images/2.png" class="w1875 mw45" align="absmiddle">计划内容';
				html += '										</td>';
				html += '										<td class="z09244d96f3" align="right" id="save_plan">';
				html += '											保存';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td colspan="2">';
				html += '											<hr class="banone bb2bd6d6d6">';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td align="center" class="h25">计划周期：</td>';
				html += '										<td>';
				html += '											<input type="text" placeholder="开始时间" class="inp h2 w75 pl05 pr05 tac" id="startTime" name = "startTime"  data-toggle="date"> - <input type="text" placeholder="结束时间" class="inp h2 w75 pl05 pr0 tac" name = "endTime" id="endTime">';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td align="center" class="h25">康复项目：</td>';
				html += '										<td>';
				html += '<!--此处调用机构的服务项目-->';
				html += '											<select class="inp h2 w75 pl05 pr05 tac" name="project_s" id="project_s">';
				html += '												<option>请选择康复项目</option>';
									[#list serverProjectCategorys as serverProjectCategory]
										[#if !serverProjectCategory.isDeleted]
				html += '												<option value="${serverProjectCategory.id}">${serverProjectCategory.name}</option>';
										[/#if]
									[/#list]
				html += '											</select>';
				html += '<!--调用机构服务项目结束-->';
				html += '											<input name="n" class="inp h2 pl05 pr05 tac w2 ml053" type="text" id="n" value="1" onkeyup="this.value=this.value.replace(/\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\D/g,\'\')"> 次';
				html += '											<input type="button" name="remove" id="remove" class="bg3f9fff h2 w35 z1167ffffff ml053 banone" value="添加">';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td></td>';
				html += '										<td>';
				html += '											<div class="bge6f4ff w158 h6 pa05" style="overflow-y: auto;overflow-x: hidden;">';
				html += '												<table cellpadding="0" cellspacing="0" border="0" class="w148 tlf items">';
				html += '												</table>';
				html += '												<input type="hidden" id="drill" name="drill" value="">';
				html += '												<input type="hidden" id="assessReportId" name="assessReportId" value="'+assessReportId+'">';
				html += '												<input type="hidden" id="patientMemberId" name="patientMemberId" value="${patientMember.id}">';							
				html += '											</div>';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td align="center" class="pt05">短期目标：</td>';
				html += '										<td>';
				html += '											<textarea name = "shortTarget" id = "shortTarget" class="inp pl05 pr05 pb05 pt05 mt05 w158 h5"></textarea>';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td align="center" class="pt05">长期目标：</td>';
				html += '										<td>';
				html += '											<textarea name = "longTarget" id = "longTarget" class="inp pl05 pr05 pb05 pt05 mt05 w158 h5"></textarea>';
				html += '										</td>';
				html += '									</tr>';
				html += '								</table>';
				html += '								</form>';
				html += '								</div>';
				html += '							</td>';
				html += '						</tr>';
				$(this).closest("tbody").append(html);
				$(this).find("img").attr("src","${base}/resources/app/images/3.png");
				
				//初始化日历组件
				$("#startTime").calendar({
					monthNames		:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
					dayNamesShort	:['周日','周一','周二','周三','周四','周五','周六'],
				});
				$("#endTime").calendar({
					monthNames		:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
					dayNamesShort	:['周日','周一','周二','周三','周四','周五','周六'],
				});
				$.init();
			}
	})
	
	//添加服务项目
	$("table").on("click","#remove",function(){
		var html="";
		if(!$("#project_s option").is(":selected"))
			{			
				alert("请选择须添加的康复项目");
				return false;
			}
		if(isNaN($("#n").val()) || $("#n").val()<1)
			{
				alert("服务次数应为大于0的数字");
				return false;
			}
		if($("#project_s option:selected").text()!="请选择康复项目")
			{
				html +='													<tr>';
				html +='														<td class="tdwb w68" data_id="' + $("#project_s option:selected").val() + '">' + $("#project_s option:selected").text() + '</td>';
				html +='														<td class="w55">课节:' + $("#n").val() + '节</td>';
				html +='														<td class="w25" align="right">';
				html +='															<input type="button" value="删除" class="delitem">';
				html +='														</td>';
				html +='													</tr>';
				$(".items").append(html);
			}
		else
			{
				alert("请选择须添加的康复项目");
				return false;
			}
	});
	
	//取消服务项目
	$("table").on("click",".delitem",function(){
		$(this).closest("tr").remove();
	})
	
	
	//添加计划保存
	$("table").on("click","#save_plan",function(){
		var item="",$this = $(this).closest("table").find(".items"), item_td="";
		$this.find("tr").each(function(){
			item_td = $(this).find("td");
			if(item=="")
				{
					item = item_td.eq(0).attr("data_id") + "," + item_td.eq(1).html().replace("课节:","").replace("节","");
				}
			else
				{
					item = item + "#" + item_td.eq(0).attr("data_id") + "," + item_td.eq(1).html().replace("课节:","").replace("节","");
				}
		}); 
		$("#drill").val(item);
	    var	startTime = $('#startTime').val();
	    var	endTime = $('#endTime').val();
	    if(startTime=="" || endTime=="" ){
	    	$.alert('请填写康复周期');
	    	return;
	    }else{
		     startTime=new Date(startTime.replace("-", "/"));  
		     endTime=new Date(endTime.replace("-", "/"));  
		    if(endTime<startTime){  
		    	$.alert('周期结束时间不能大于周期开始时间');
				return ;  
		    }  
	    }
	    
	    var	drill = $('#drill').val();
	    if(drill==""){
	    	$.alert('至少添加一个康护项目');
	    	return;
	    }
	    var	shortTarget = $('#shortTarget').val();
	    if(shortTarget==""){
	    	$.alert('请填写短期目标');
	    	return;
	    }
	    var	longTarget = $('#longTarget').val();
		if(longTarget==""){
	    	$.alert('请填写长期目标');
	    	return;
	    }
	    
		$('#plan').submit();
		//这里进行表单提交
	})
	
	
	//显示计划内容
	$("table").on("click",".list_plan",function(){
		
		if($(this).closest("tr").next("tr").length>0)
			{
				$(this).closest("tr").next("tr").remove();
				$(this).find("img").attr("src","${base}/resources/app/images/4.png");
			}
		else
			{
			
				var id = $(this).attr("data_id");
				var $this = $(this);	
				console.log($this.html());
				
				$.ajax({
			             type: "POST",
			             url: "${base}/app/recoveryPlan/view.jhtml",
			             data: {
				             id:id
				             },
			             dataType: "json",
			             success: function(data){
						             if(data.status=="200"){
						             
						             	console.log($this.html());	
						             	console.log($this.closest("table").html());
						                var dataObj = data.data;
										var html = "";
										html += '						<tr>';
										html += '							<td>';
										html += '								<div class="k z0924323232">';
										html += '								<table cellpadding="0" cellspacing="0" border="0" width="100%">';
										html += '									<tr>';
										html += '										<td class="z1323232 lh1875 w68">';
										html += '											<img src="${base}/resources/app/images/2.png" class="w1875 mw45" align="absmiddle">计划内容';
										html += '										</td>';
										html += '										<td class="z09244d96f3" align="right">';
										html += '											';
										html += '										</td>';
										html += '									</tr>';
										html += '									<tr>';
										html += '										<td colspan="2">';
										html += '											<hr class="banone bb2bd6d6d6">';
										html += '										</td>';
										html += '									</tr>';
										html += '									<tr>';
										html += '										<td align="center" class="h25" valign="top">康护医师：</td>';
										html += '										<td valign="top">'+dataObj.recoveryDoctorName+'</td>';
										html += '									</tr>';
										html += '									<tr>';
										html += '										<td align="center" class="h25" valign="top">计划周期：</td>';
										html += '										<td valign="top">'+dataObj.recoveryPlanStartTime+'至'+dataObj.recoveryPlanEndTime+'</td>';
										html += '									</tr>';
										html += '									<tr>';
										html += '										<td align="center" class="h25" valign="top">训练内容：</td>';
										html += '										<td valign="top">';
										for(var i = 0; i<dataObj.recoveryPlanDrillContents.length;i++){
										html += dataObj.recoveryPlanDrillContents[i].serverProjectCategoryName + dataObj.recoveryPlanDrillContents[i].time+'<br>';
										}
										html += '										</td>';
										html += '									</tr>';
										html += '									<tr>';
										html += '										<td align="center" class="pt05" valign="top">短期目标：</td>';
										html += '										<td valign="top" class="pt05">';
										html += dataObj.recoveryPlanShortTarget;
										html += '										</td>';
										html += '									</tr>';
										html += '									<tr>';
										html += '										<td align="center" class="pt05" valign="top">长期目标：</td>';
										html += '										<td valign="top" class="pt05">';
										html += dataObj.recoveryPlanLongTarget;
										html += '										</td>';
										html += '									</tr>';
										html += '								</table>';
										html += '								</div>';
										html += '								<div class="k z0924323232">';
										html += '								<table cellpadding="0" cellspacing="0" border="0" width="100%">';
										html += '									<tr>';
										html += '										<td class="z1323232 lh1875 w68 pr">';
										html += '											<img src="${base}/resources/app/images/6.png" class="w1875 mw45" align="absmiddle">康复记录';
										html += '										</td>';
										html += '										<td class="z09244d96f3" align="right">';
										html += '											<a href="javascript:;" data_patientMemberId="${patientMember.id}" data_start_date="'+dataObj.recoveryPlanStartTime+'" data_end_date = "'+dataObj.recoveryPlanEndTime+'" class="list_record"><span class="z1393ff">明细</span></a>';
										html += '										</td>';
										html += '									</tr>';
										html += '								</table>';
										html += '								</div>';
										html += '								<div class="k z0924323232">';
										html += '<!--当该条计划无总结时，显示添加总结，当该条计划有总结时，显示明细内容-->';
										if(!dataObj.recoveryPlanSummary){
											html += '<!--添加总结开始-->';
											html += '								<table cellpadding="0" cellspacing="0" border="0" width="100%">';
											html += '									<tr>';
											html += '										<td class="z1323232 lh1875 w68">';
											html += '											<img src="${base}/resources/app/images/6.png" class="w1875 mw45" align="absmiddle">疗效总结';
											html += '										</td>';
											html += '										<td class="z09244d96f3" align="right">';
											html += '											<a href="javascript:;" data_recoveryPlanId = "'+id+'" class="add_summary"><span class="z1393ff">添加总结</span></a>';
											html += '											<a href="javascript:;" data_recoveryPlanId = "'+id+'" class="save_summary dpn"><span class="z1393ff">保存</span></a>';
											html += '										</td>';
											html += '									</tr>';
											html += '								</table>';
											html += '<!--添加总结结束-->';
										}else{
											html += '<!--显示明细开始-->';
											html += '								<table cellpadding="0" cellspacing="0" border="0" width="100%">';
											html += '									<tr>';
											html += '										<td class="z1323232 lh1875 w68">';
											html += '											<img src="${base}/resources/app/images/6.png" class="w1875 mw45" align="absmiddle">疗效总结';
											html += '										</td>';
											html += '										<td class="z09244d96f3" align="right">';
											html += '											<a href="javascript:;" data_summary="'+dataObj.recoveryPlanSummary+'" class="list_summary"><span class="z1393ff">明细</span></a>';
											html += '										</td>';
											html += '									</tr>';
											html += '								</table>';
											html += '<!--显示明细结束-->';
										}
										html += '								</div>';
										html += '							</td>';
										html += '						</tr>';
										$(".list_plan").closest("tr").next("tr").remove();
										$(".list_plan").find("img").attr("src","${base}/resources/app/images/4.png");	
										$this.closest("tbody").append(html);
										$this.find("img").attr("src","${base}/resources/app/images/3.png");		
						             }
						        }
						  });           
						             
				
			
						
			}
	})	
	
	
	//显示添加总结表单
	$("table").on("click",".add_summary",function(){
		var html="";
		if($(this).closest("tr").next("tr").length>0)
			{
				$(this).closest("tr").next("tr").remove();
				$(this).closest("tr").find("img").attr("src","${base}/resources/app/images/6.png");
			}
		else
			{
			    var recoveryPlanId = $(this).attr('data_recoveryPlanId');
				html += '									<tr>';
				html += '										<td colspan="2">';	
				
				html += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
				html += '												<tr>';
				html += '													<td>';
				html += '														<hr class="banone bb2bd6d6d6">';
				html += '													</td>';
				html += '												</tr>';
				html += '												<tr>';
				html += '													<td>';
				html += '														<textarea name="summary" id="summary" class="inp pl05 pr05 pb05 pt05 mt05 w23 h5"></textarea>';
				html += '													</td>';
				html += '												</tr>';
				html += '												<tr>';
				html += '													<td class="h1"></td>';
				html += '												</tr>';
				html += '											</table>';
				
				html += '										</td>';	
				html += '									</tr>';
				
				   
				
				
				$(".add_summary").closest("tr").next("tr").remove();
				$(".add_summary").closest("tr").find("img").attr("src","${base}/resources/app/images/6.png");	
				$(this).closest("tbody").append(html);
				$(this).closest("tr").find("img").attr("src","${base}/resources/app/images/5.png");	
				$(".add_summary").hide();
				$(".save_summary").show();
				
			}
	})	
	
	
	//显示添加总结表单
	$("table").on("click",".save_summary",function(){
	var recoveryPlanId = $(this).attr("data_recoveryPlanId");
	var summary = $('#summary').val();
	var $this = $(this);
				$.ajax({
			             type: "POST",
			             url: "summarySave.jhtml",
			             data: {
				             recoveryPlanId:recoveryPlanId,
				             summary:summary
				             },
			             dataType: "json",
			             success: function(message){
			             	var html="";
							//这里进行提交表单
							
							//处理展示内容
							$this.closest("tr").next("tr").remove();
							html = '									<tr>';
							html += '										<td colspan="2">';	
							html += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
							html += '												<tr>';
							html += '													<td>';
							html += '														<hr class="banone bb2bd6d6d6">';
							html += '													</td>';
							html += '												</tr>';
							html += '												<tr>';
							html += '													<td class="zjx">';
							html += summary;
							html += '													</td>';
							html += '												</tr>';
							html += '												<tr>';
							html += '													<td class="h1"></td>';
							html += '												</tr>';
							html += '											</table>';
							html += '										</td>';	
							html += '									</tr>';
							$this.closest("tbody").append(html);
							
							
							html = '											<a href="javascript:;" data_summary="'+summary+'" class="list_summary"><span class="z1393ff">明细</span></a>';
							$this.closest("td").html(html);
												
							return false;
			             }
			      });    
	
		
	})	
	
	
	//显示总结明细
	$("table").on("click",".list_summary",function(){
		var html="";
		if($(this).closest("tr").next("tr").length>0)
			{
				$(this).closest("tr").next("tr").remove();
				$(this).closest("tr").find("img").attr("src","${base}/resources/app/images/6.png");
			}
		else
			{
				html += '									<tr>';
				html += '										<td colspan="2">';	
				html += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
				html += '												<tr>';
				html += '													<td>';
				html += '														<hr class="banone bb2bd6d6d6">';
				html += '													</td>';
				html += '												</tr>';
				html += '												<tr>';
				html += '													<td class="zjx">';
				html += $(this).attr('data_summary');
				html += '													</td>';
				html += '												</tr>';
				html += '												<tr>';
				html += '													<td class="h1"></td>';
				html += '												</tr>';
				html += '											</table>';
				html += '										</td>';	
				html += '									</tr>';
				$(".add_summary").closest("tr").next("tr").remove();
				$(".add_summary").closest("tr").find("img").attr("src","${base}/resources/app/images/6.png");	
				$(this).closest("tbody").append(html);
				$(this).closest("tr").find("img").attr("src","${base}/resources/app/images/5.png");	
			}
	})	
	
	
	//显示康复记录明细
	$("table").on("click",".list_record",function(){
		
		if($(this).closest("tr").next("tr").length>0)
			{
				$(this).closest("tr").next("tr").remove();
				$(this).closest("tr").find("img").attr("src","${base}/resources/app/images/6.png");
			}
		else
			{
			var $this = $(this);
			
			var patientMemberId = $this.attr('data_patientMemberId');
			var start_date = $this.attr('data_start_date');
			var end_date = $this.attr('data_end_date');
			
			var file = '{"patientMemberId":'+patientMemberId+',"createDate":"'+start_date+'","endDate":"'+end_date+'"}';
			
			$.ajax({
			             type: "GET",
			             url: "${base}/app/assessReport/recoveryRecordList.jhtml",
			             data: {
				             file:file
				             },
			             dataType: "json",
			             success: function(data){
					             if(data.status=="200"){
					             
					            	var dataObj = data.data.recoveryRecordlist;
					            	var html="";
									html += '									<tr>';
									html += '										<td colspan="2">';	
									html += '											<table cellpadding="0" cellspacing="0" border="0" width="100%">';
									html += '<!--康复记录循环开始-->';
									//alert(dataObj.recoveryRecordlist.length);
									for(var i = 0; i < dataObj.length;i++){
									html += '												<tr>';
									html += '													<td>';
									html += '														<hr class="banone bb2bd6d6d6">';
									html += '													</td>';
									html += '												</tr>';
									html += '												<tr>';
									html += '													<td align="center" class="h21" valign="top">';
									html += '														<span class="jlk">'+dataObj[i].recoveryRecordDate+'<img src="${base}/resources/app/images/9.png" class="jljt">';
									html += '														</span>';
									html += '													</td>';
									html += '												</tr>';
									html += '												<tr>';
									html += '													<td class="jlx">';
									html += '														<span class="z646464"><!--以下存在的<br>要保留-->';
									html += '															<b>康复内容：</b><br>';
									html += '														</span>'+dataObj[i].recoveryRecordContent+'<br><br>';
									html += '														<span class="z646464">';
									html += '															<b>疗效总结：</b><br>';
									html += '														</span>'+dataObj[i].recoveryRecordEffect+'</td>';
									html += '												</tr>';
									html += '												<tr>';
									html += '													<td class="h1"></td>';
									html += '												</tr>';
									}
									
									html += '<!--康复记录循环结束-->';
									html += '											</table>';
									html += '										</td>';	
									html += '									</tr>';
									$(".list_record").closest("tr").next("tr").remove();
									$(".list_record").closest("tr").find("img").attr("src","${base}/resources/app/images/6.png");	
									$this.closest("tbody").append(html);
									$this.closest("tr").find("img").attr("src","${base}/resources/app/images/5.png");	
					             
					             }
					        }
				  });	             
			}
	})	
});

</script>
</head>
<body style="background:#f5f5f5">
<!-- page 容器 -->
<div class="page" id="page-infinite-scroll">
	<!-- 标题栏 -->
	<!--header class="bar bar-nav bgffffff" id="header" style="height: 6.167rem;">
			<!--
				<table cellpadding="0" cellspacing="0" border="0" width="98%" align="center" class="m0auto" style="border-bottom: 1px solid #f0f1f0;">
					<tr>
						<td align="center" class="wz w2" style="height: 3.125rem"><a href="javascript:;" onclick="javascript:history.back(-1);"><img src="${base}/resources/app/images/j_2.png" class="imgw0667 hauto"></a></td>
						<td align="center" class="pr2 z125323232">健康档案</td>
					</tr>
				</table>
			-->
		<table cellpadding="0" cellspacing="0" border="0" width="100%" align="center" class="m0auto" style="margin-top: 0.5rem;">
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0" border="0" width="95%" class="m0auto">
						<tr>
							<td valign="top" align="left" style="width: 6rem;"><img onerror = "this.src='${base}/resources/app/images/tmp_2.png'" src="${patientMember.logo}" style="width:5rem; max-width: 120px;" class="br5"></td>
							<td class="z1167444444 pt05" style="line-height: 1.3rem;" valign="top">
								${patientMember.name}<span class="z0833444444">(${age(patientMember.birth)}周岁)</span> [#if patientMember.gender == "male"] <span class="iconfont z1393ff" style="font-size: 1rem;">&#xe661;</span> [#else] <span class="iconfont zff0000" style="font-size: 1rem;">&#xe6e3;</span> [/#if]
								<br>
								<span class="z0833444444">建档时间：${firstOrderDate(patientMember.id)}</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>  	
	</header-->
	<!-- 这里是页面内容区 -->
	<div class="content infinite-scroll" data-ptr-distance="55" data-distance="100">
		<div class="card">
			<div class="card-content">
				[#list assessReports as assessReport]
					<div class="card-content-inner">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td>
									<a href="javascript:;" class="assess" data_id="${assessReport.id}" data_nowExplain="${assessReport.nowExplain}" data_assessResult="${assessReport.assessResult}" data_proposal="${assessReport.proposal}" data_start_date="${assessReport.createDate?string("yyyy-MM-dd HH:mm:ss")}"  [#if assessReport_index+1 == assessReports.size()] data_end_date="${.now?string("yyyy-MM-dd HH:mm:ss")}" [#else] data_end_date="${assessReports[assessReport_index+1].createDate?string("yyyy-MM-dd HH:mm:ss")}" [/#if] >
										<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto tlf">
											<tr>
												<td class="tdwb"><span class="z1167444444">诊评报告 </span></td>
												<td align="right" class="w11 tdwb z10674d96f3">诊评人:${assessReport.doctor.name}</td>
											</tr>
											<tr>
												<td colspan="2">
													<table cellpadding="0" cellspacing="0" border="0" width="100%">
														<tr>
															<td class="z1067999999 tdwb">${assessReport.diseaseName}</td>
															<td align="right" class="z0924999999 tdwb w6">${assessReport.createDate?string("yyyy-MM-dd")}</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
										<img src="${base}/resources/app/images/1.png" class="imgw1667 hauto">
									</a>
								</td>
							</tr>
						</table>
					</div>
				[/#list]	
			</div>
		</div>
	</div>	
</div>


</body>
</html>