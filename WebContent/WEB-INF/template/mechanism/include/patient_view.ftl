<tr>
	<td colspan="2" class="bgf5fafe plr10 ptb10">
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="100">
					<img onerror = "this.src='${base}/resources/mechanism/images/tmp_2.png'" src="${patientMember.logo}" class="w80 h80 br40">
				</td>
				<td>
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td class="z16444444">${patientMember.name}</td>
						</tr>
						<tr>
							<td class="z14717171 pr" height="30">
								${message("Member.Gender."+patientMember.gender)} ${age(patientMember.birth)}周岁 <span id="rehstate">${message("PatientMechanism.HealthType."+patientMechanism.healthType)}</span> 
								<span class="iconfont ">
									<a href="javascript:;" onClick="$('#flag_1').css('display','block');">&#xe738;</a>
								</span>
								<table cellpadding="0" cellspacing="0" border="0" width="200" id="flag_1">
									<tr>
										<td class="k_4 pr" width="200" align="center">状态修改<div class="iconfont z12ffffff pa" style="right: 10px; top: 14px;cursor:pointer;" onClick="$('#flag_1').css('display','none');">&#xe611;</div></td>
									</tr>
									<tr>
										<td height="10"></td>
									</tr>
									[#list healthTypes as healthType]
									<tr>
										<td height="40" class="pl55">
											<input type="radio" name="flag${patientMember.id}" data_id = "${patientMember.id}" [#if patientMechanism.healthType == healthType]checked[/#if] value="${healthType}"> ${message("PatientMechanism.HealthType."+healthType)}
										</td>
									</tr>
									[/#list]
									<tr>
										<td height="10"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="ptb10">
								<table cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td align="center" width="65" height="25" class="[#if colour == "1"]z12ffffff bg279fff [#else]z12575757 bgffffff [/#if]bae1e1e1" id="tag_1" onMouseOver="switch_tag(1,1,'over');" onMouseOut="switch_tag(1,1,'out');"><a href="${base}/mechanism/member/patient_view.jhtml?patientMemberId=${patientMember.id}">明细资料</a></td>
										<td width="10"></td>
										<td align="center" width="65" height="25" class="[#if colour == "2"]z12ffffff bg279fff [#else]z12575757 bgffffff [/#if]bae1e1e1" id="tag_2" onMouseOver="switch_tag(2,1,'over');" onMouseOut="switch_tag(2,1,'out');"><a href="${base}/mechanism/order/patient_order.jhtml?patientMemberId=${patientMember.id}">康复记录</a></td>
										<td width="10"></td>
										<td align="center" width="65" height="25" class="[#if colour == "3"]z12ffffff bg279fff [#else]z12575757 bgffffff [/#if]bae1e1e1" id="tag_3" onMouseOver="switch_tag(3,1,'over');" onMouseOut="switch_tag(3,1,'out');"><a href="${base}/mechanism/assessReport/list.jhtml?patientMemberId=${patientMember.id}">健康档案</a></td>
										<td width="10"></td>
										<td align="center" width="65" height="25" class="[#if colour == "4"]z12ffffff bg279fff [#else]z12575757 bgffffff [/#if]bae1e1e1" id="tag_4" onMouseOver="switch_tag(4,1,'over');" onMouseOut="switch_tag(4,1,'out');"><a href="${base}/mechanism/visitMessage/patient_visitMessage_list.jhtml?patientMemberId=${patientMember.id}&type=patient">回访记录</a></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>


<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

	 $(":radio").click(function(){
	   //alert("您是状态是..." + $(this).val());
	   //alert("您的id是..." + $(this).attr("data_id"));
	   var healthType = $(this).val();
	   var patientId = $(this).attr("data_id");
	    $.ajax( {     
                "type": "POST",  
                "url": '${base}/mechanism/member/updateHealthType.jhtml',  
                "contentType": "application/x-www-form-urlencoded; charset=utf-8",   
                "traditional": true,  
                "async": false,
                "data": {  
                    healthType:healthType,
                    patientId:patientId
                },  
                success: function(message) {   
                	$.message(message);
                    location.reload(true);
					return false;
                }     
        }); 
	   
	   
	  });



});
</script>



