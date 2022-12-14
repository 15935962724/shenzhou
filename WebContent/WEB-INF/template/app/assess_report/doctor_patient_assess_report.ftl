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
	.card{border-radius: 0; box-shadow: #ffffff 0px 0rem 0rem;margin: 0px;background: #f5f5f5;margin-top:3.7rem;}
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

	//??????????????????
	$("table").on("click",".assess",function(){
		
		if($(this).closest("tr").next("tr").length>0)
			{
				$(this).closest("tr").next("tr").remove();
			}
		else
			{
					var assessReportId = $(this).attr('data_id');
			
					//????????????
					var nowExplain = $(this).attr('data_nowExplain');
				
					//????????????
					var assessResult = $(this).attr('data_assessResult');
					
					//????????????
					var proposal = $(this).attr('data_proposal');
					
					//????????????
					var start_date = $(this).attr('data_start_date');
					
					//????????????
					var end_date = $(this).attr('data_end_date');
					
					//??????id
					var patientMemberId = ${patientMember.id};
					
					//??????
					var $this = $(this);
			        
			        //????????????json?????? 
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
									html += '											<img src="${base}/resources/app/images/2.png" class="w1875 mw45" align="absmiddle">????????????';
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
									html += '											<img src="${base}/resources/app/images/2.png" class="w1875 mw45" align="absmiddle">????????????';
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
									html += '											<img src="${base}/resources/app/images/2.png" class="w1875 mw45" align="absmiddle">????????????';	
									html += '										</td>';
									html += '									</tr>';
									html += '									<tr>';
									html += '										<td class="z0924666666">'+proposal+'</td>';
									html += '									</tr>';
									html += '								</table>';
									html += '							</div>';
									html += '							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="mt1">';
									html += '								<tr>';
									html += '									<td class="k br05">';
									html += '										<a href="javascript:;" data_assessReportId = "'+assessReportId+'" class="add_plan">';
									html += '										<table cellpadding="0" cellspacing="0" border="0" width="100%">';
									html += '											<tr>';
									html += '												<td class="z1323232 lh1875">';
									html += '													<img src="${base}/resources/app/images/4.png" class="w14 mw35 hauto" align="absmiddle">????????????';	
									html += '												</td>';
									html += '												<td class="z09244d96f3" align="right">';
									html += '													????????????';
									html += '												</td>';
									html += '											</tr>';
									html += '										</table>';
									html += '										</a>';
									html += '									</td>';
									html += '								</tr>';
									html += '							</table>';
									
									var dataObj = data.data.assessReportlist;
									for(var i = 0; i<dataObj.length; i++){
										html += '							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="mt1">';
										html += '								<tr>';
										html += '									<td class="k br05">';
										html += '										<a href="javascript:;" data_id="'+dataObj[i].recoveryPlanId+'" class="list_plan">';
										html += '										<table cellpadding="0" cellspacing="0" border="0" width="100%">';
										html += '											<tr>';
										html += '												<td class="z1323232 lh1875">';
										html += '													<img src="${base}/resources/app/images/4.png" class="w14 mw35 hauto" align="absmiddle">????????????';	
										html += '												</td>';
										html += '												<td class="z09244d96f3" align="right">'+dataObj[i].recoveryPlanStartTime+'???'+dataObj[i].recoveryPlanEndTime+'</td>';
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
					             	$.alert('??????????????????', '??????!');
					             	return false;
					             }
					          }
					      });       
			
			
			
			

			}
	})
	
	
	
	//????????????????????????
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
				html += '											<img src="${base}/resources/app/images/2.png" class="w1875 mw45" align="absmiddle">????????????';
				html += '										</td>';
				html += '										<td class="z09244d96f3" align="right" id="save_plan">';
				html += '											??????';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td colspan="2">';
				html += '											<hr class="banone bb2bd6d6d6">';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td align="center" class="h25">???????????????</td>';
				html += '										<td>';
				html += '											<input type="text" placeholder="????????????" class="inp h2 w75 pl05 pr05 tac" id="startTime" name = "startTime"  data-toggle="date"> - <input type="text" placeholder="????????????" class="inp h2 w75 pl05 pr0 tac" name = "endTime" id="endTime">';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td align="center" class="h25">???????????????</td>';
				html += '										<td>';
				html += '<!--?????????????????????????????????-->';
				html += '											<select class="inp h2 w75 pl05 pr05 tac" name="project_s" id="project_s">';
				html += '												<option>?????????????????????</option>';
									[#list serverProjectCategorys as serverProjectCategory]
										[#if !serverProjectCategory.isDeleted]
				html += '												<option value="${serverProjectCategory.id}">${serverProjectCategory.name}</option>';
										[/#if]
									[/#list]
				html += '											</select>';
				html += '<!--??????????????????????????????-->';
				html += '											<input name="n" class="inp h2 pl05 pr05 tac w2 ml053" type="text" id="n" value="1" onkeyup="this.value=this.value.replace(/\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\D/g,\'\')"> ???';
				html += '											<input type="button" name="remove" id="remove" class="bg3f9fff h2 w35 z1167ffffff ml053 banone" value="??????">';
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
				html += '												<input type="hidden" id="redoctorId" name="redoctorId" value="${doctor.id}">';	
				html += '												<input type="hidden" id="patientMemberId" name="patientMemberId" value="${patientMember.id}">';							
				html += '											</div>';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td align="center" class="pt05">???????????????</td>';
				html += '										<td>';
				html += '											<textarea name = "shortTarget" id = "shortTarget" class="inp pl05 pr05 pb05 pt05 mt05 w158 h5"></textarea>';
				html += '										</td>';
				html += '									</tr>';
				html += '									<tr>';
				html += '										<td align="center" class="pt05">???????????????</td>';
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
				
				//?????????????????????
				$("#startTime").calendar({
					monthNames		:['??????', '??????', '??????', '??????', '??????', '??????', '??????', '??????', '??????', '??????', '?????????', '?????????'],
					dayNamesShort	:['??????','??????','??????','??????','??????','??????','??????'],
				});
				$("#endTime").calendar({
					monthNames		:['??????', '??????', '??????', '??????', '??????', '??????', '??????', '??????', '??????', '??????', '?????????', '?????????'],
					dayNamesShort	:['??????','??????','??????','??????','??????','??????','??????'],
				});
				$.init();
			}
	})
	
	//??????????????????
	$("table").on("click","#remove",function(){
		var html="";
		if(!$("#project_s option").is(":selected"))
			{			
				alert("?????????????????????????????????");
				return false;
			}
		if(isNaN($("#n").val()) || $("#n").val()<1)
			{
				alert("????????????????????????0?????????");
				return false;
			}
		if($("#project_s option:selected").text()!="?????????????????????")
			{
				html +='													<tr>';
				html +='														<td class="tdwb w68" data_id="' + $("#project_s option:selected").val() + '">' + $("#project_s option:selected").text() + '</td>';
				html +='														<td class="w55">??????:' + $("#n").val() + '???</td>';
				html +='														<td class="w25" align="right">';
				html +='															<input type="button" value="??????" class="delitem">';
				html +='														</td>';
				html +='													</tr>';
				$(".items").append(html);
			}
		else
			{
				alert("?????????????????????????????????");
				return false;
			}
	});
	
	//??????????????????
	$("table").on("click",".delitem",function(){
		$(this).closest("tr").remove();
	})
	
	
	//??????????????????
	$("table").on("click","#save_plan",function(){
		var item="",$this = $(this).closest("table").find(".items"), item_td="";
		$this.find("tr").each(function(){
			item_td = $(this).find("td");
			if(item=="")
				{
					item = item_td.eq(0).attr("data_id") + "," + item_td.eq(1).html().replace("??????:","").replace("???","");
				}
			else
				{
					item = item + "#" + item_td.eq(0).attr("data_id") + "," + item_td.eq(1).html().replace("??????:","").replace("???","");
				}
		}); 
		$("#drill").val(item);
	    var	startTime = $('#startTime').val();
	    var	endTime = $('#endTime').val();
	    if(startTime=="" || endTime=="" ){
	    	$.alert('?????????????????????');
	    	return;
	    }else{
		     startTime=new Date(startTime.replace("-", "/"));  
		     endTime=new Date(endTime.replace("-", "/"));  
		    if(endTime<startTime){  
		    	$.alert('????????????????????????????????????????????????');
				return ;  
		    }  
	    }
	    
	    var	drill = $('#drill').val();
	    if(drill==""){
	    	$.alert('??????????????????????????????');
	    	return;
	    }
	    var	shortTarget = $('#shortTarget').val();
	    if(shortTarget==""){
	    	$.alert('?????????????????????');
	    	return;
	    }
	    var	longTarget = $('#longTarget').val();
		if(longTarget==""){
	    	$.alert('?????????????????????');
	    	return;
	    }
	    
		$('#plan').submit();
		//????????????????????????
	})
	
	
	//??????????????????
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
										html += '											<img src="${base}/resources/app/images/2.png" class="w1875 mw45" align="absmiddle">????????????';
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
										html += '										<td align="center" class="h25" valign="top">???????????????</td>';
										html += '										<td valign="top">'+dataObj.recoveryDoctorName+'</td>';
										html += '									</tr>';
										html += '									<tr>';
										html += '										<td align="center" class="h25" valign="top">???????????????</td>';
										html += '										<td valign="top">'+dataObj.recoveryPlanStartTime+'???'+dataObj.recoveryPlanEndTime+'</td>';
										html += '									</tr>';
										html += '									<tr>';
										html += '										<td align="center" class="h25" valign="top">???????????????</td>';
										html += '										<td valign="top">';
										for(var i = 0; i<dataObj.recoveryPlanDrillContents.length;i++){
										html += dataObj.recoveryPlanDrillContents[i].serverProjectCategoryName + dataObj.recoveryPlanDrillContents[i].time+'<br>';
										}
										html += '										</td>';
										html += '									</tr>';
										html += '									<tr>';
										html += '										<td align="center" class="pt05" valign="top">???????????????</td>';
										html += '										<td valign="top" class="pt05">';
										html += dataObj.recoveryPlanShortTarget;
										html += '										</td>';
										html += '									</tr>';
										html += '									<tr>';
										html += '										<td align="center" class="pt05" valign="top">???????????????</td>';
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
										html += '											<img src="${base}/resources/app/images/6.png" class="w1875 mw45" align="absmiddle">????????????';
										html += '										</td>';
										html += '										<td class="z09244d96f3" align="right">';
										html += '											<a href="javascript:;" data_patientMemberId="${patientMember.id}" data_start_date="'+dataObj.recoveryPlanStartTime+'" data_end_date = "'+dataObj.recoveryPlanEndTime+'" class="list_record"><span class="z1393ff">??????</span></a>';
										html += '										</td>';
										html += '									</tr>';
										html += '								</table>';
										html += '								</div>';
										html += '								<div class="k z0924323232">';
										html += '<!--???????????????????????????????????????????????????????????????????????????????????????????????????-->';
										if(!dataObj.recoveryPlanSummary){
											html += '<!--??????????????????-->';
											html += '								<table cellpadding="0" cellspacing="0" border="0" width="100%">';
											html += '									<tr>';
											html += '										<td class="z1323232 lh1875 w68">';
											html += '											<img src="${base}/resources/app/images/6.png" class="w1875 mw45" align="absmiddle">????????????';
											html += '										</td>';
											html += '										<td class="z09244d96f3" align="right">';
											html += '											<a href="javascript:;" data_recoveryPlanId = "'+id+'" class="add_summary"><span class="z1393ff">????????????</span></a>';
											html += '											<a href="javascript:;" data_recoveryPlanId = "'+id+'" class="save_summary dpn"><span class="z1393ff">??????</span></a>';
											html += '										</td>';
											html += '									</tr>';
											html += '								</table>';
											html += '<!--??????????????????-->';
										}else{
											html += '<!--??????????????????-->';
											html += '								<table cellpadding="0" cellspacing="0" border="0" width="100%">';
											html += '									<tr>';
											html += '										<td class="z1323232 lh1875 w68">';
											html += '											<img src="${base}/resources/app/images/6.png" class="w1875 mw45" align="absmiddle">????????????';
											html += '										</td>';
											html += '										<td class="z09244d96f3" align="right">';
											html += '											<a href="javascript:;" data_summary="'+dataObj.recoveryPlanSummary+'" class="list_summary"><span class="z1393ff">??????</span></a>';
											html += '										</td>';
											html += '									</tr>';
											html += '								</table>';
											html += '<!--??????????????????-->';
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
	
	
	//????????????????????????
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
	
	
	//????????????????????????
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
							//????????????????????????
							
							//??????????????????
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
							
							
							html = '											<a href="javascript:;" data_summary="'+summary+'" class="list_summary"><span class="z1393ff">??????</span></a>';
							$this.closest("td").html(html);
												
							return false;
			             }
			      });    
	
		
	})	
	
	
	//??????????????????
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
	
	
	//????????????????????????
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
									html += '<!--????????????????????????-->';
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
									html += '														<span class="z646464"><!--???????????????<br>?????????-->';
									html += '															<b>???????????????</b><br>';
									html += '														</span>'+dataObj[i].recoveryRecordContent+'<br><br>';
									html += '														<span class="z646464">';
									html += '															<b>???????????????</b><br>';
									html += '														</span>'+dataObj[i].recoveryRecordEffect+'</td>';
									html += '												</tr>';
									html += '												<tr>';
									html += '													<td class="h1"></td>';
									html += '												</tr>';
									}
									
									html += '<!--????????????????????????-->';
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
<!-- page ?????? -->
<div class="page" id="page-infinite-scroll">
	<!-- ????????? -->
	<header class="bar bar-nav bgffffff" id="header" style="height: 6.167rem;">
			<!--
				<table cellpadding="0" cellspacing="0" border="0" width="98%" align="center" class="m0auto" style="border-bottom: 1px solid #f0f1f0;">
					<tr>
						<td align="center" class="wz w2" style="height: 3.125rem"><a href="javascript:;" onclick="javascript:history.back(-1);"><img src="${base}/resources/app/images/j_2.png" class="imgw0667 hauto"></a></td>
						<td align="center" class="pr2 z125323232">????????????</td>
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
								${patientMember.name}<span class="z0833444444">(${age(patientMember.birth)}??????)</span> [#if patientMember.gender == "male"] <span class="iconfont z1393ff" style="font-size: 1rem;">&#xe661;</span> [#else] <span class="iconfont zff0000" style="font-size: 1rem;">&#xe6e3;</span> [/#if]
								<br>
								<span class="z0833444444">???????????????${firstOrderDate(patientMember.id)}</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>  	
	</header>
	<!-- ???????????????????????? -->
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
												<td class="tdwb"><span class="z1167444444">???????????? </span></td>
												<td align="right" class="w11 tdwb z10674d96f3">?????????:${assessReport.doctor.name}</td>
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