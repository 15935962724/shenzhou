<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script>
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
	})
</script>

<style>
	#flag_1{padding: 0px;left: 130px;top: 0px; position: absolute; display: none;}
</style>

</head>
<body>
   <div class="nav">???????????????
            <a href="javascript:;">??????????????????</a>???
            <a href="javascript:;">????????????</a>???
            <a href="javascript:;">????????????</a>???
            <a href="javascript:;">????????????</a>???
            <a href="javascript:;">????????????</a>???
            <a href="javascript:;">????????????</a>
        </div>
        <div class="seat">
        	<div class="left_z">????????????</div>
            <div class="export">
            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> ??????</a>
            <a href="javascript:;"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> ??????</a>
            <a href="javascript:window.back();"><img src="${base}/resources/mechanism/images/back.png" border="0" class="top_img_b_1" /> ??????</a>
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
            				<td><img onerror = "this.src='${base}/resources/mechanism/images/tmp_2.png'" src="${patientMember.logo}" alt="${patientMember.name}" title="${patientMember.name}" class="head_1" /></td>
            			</tr>
            			<tr>
            				<td height="50" class="b_a_dcdcdc_1"><span class="sp_m_1">${patientMember.name}</span><span class="z_12_999999_1">???${message("Member.Gender."+patientMember.gender)}???${age(patientMember.birth)}?????????${patientMember.nation}</span></td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">???????????????${patientMember.mobile}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">???????????????${visits(patientMember.id)}???<span class="z_12_999999_1">???</span>?????????</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">???????????????${patientMember.birth?string("yyyy-MM-dd")}</td>
            			</tr>
            		</table>
            	</div>
            </td>
            <td valign="top">
                <table width="99%" align="right" border="0" cellspacing="0" cellpadding="0">
                  <tbody>
                    <tr>
                      <td height="40">
    					<div class="peo_tag_1">
    						<ul>
    						  <li style="width:20%;"><div class="sel_div_1 border_radius_l_1"><a href="${base}/mechanism/member/patient_view.jhtml?patientMemberId=${patientMember.id}">????????????</a></div><div class="div_x_1"></div></li>
                              <li style="width:20%;"><div><a href="${base}/mechanism/assessReport/archives.jhtml?patientMemberId=${patientMember.id}">????????????</a></div><div class="div_x_1"></div></li>
                              <li style="width:20%;"><div><a href="${base}/mechanism/member/patient_reserve.jhtml?patientMemberId=${patientMember.id}">????????????</a></div><div class="div_x_1"></div></li>
                              <li style="width:20%;"><div><a href="${base}/mechanism/visitMessage/patient_visitMessage_list.jhtml?patientMemberId=${patientMember.id}&type=patient">????????????</a></div><div class="div_x_1"></div></li>
                              <li style="width:20%;"><div class="border_radius_r_1"><a href="${base}/mechanism/member/patient_doctor.jhtml?patientMemberId=${patientMember.id}">????????????</a></div><div class="div_x_1"></div></li>
    						</ul>												
    					</div>
                   	  </td>
                    </tr>
                    <tr>
                      <td>
    				  	<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cont_list" class="z_14_707070_1" >
    				  		<tr class="fff">
    				  			<td width="50%" height="45">?????????${patientMember.name}  <span class="z_12_707070_1">${patientMember.relationship.title} </span>  <span class="z_12_ff0000_1">${message("Member.HealthType."+patientMember.healthType)}</span></td>
    				  			<td width="50%">?????????${message("Member.Gender."+patientMember.gender)}</td>
    				  		</tr>
    				  		<tr>
    				  			<td height="45">???????????????${patientMember.birth?string("yyyy-MM-dd")}</td>
    				  			<td>???????????????${patientMember.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
    				  		</tr>
    				  		<tr class="fff">
    				  			<td height="45">?????????${patientMember.nation}</td>
    				  			<td>???????????????${patientMember.mobile}</td>
    				  		</tr>
    				  		<tr>
    				  			<td height="45">????????????${patientMember.cardId}</td>
    				  			<td>???????????????${patientMember.medicalInsuranceId}</td>
    				  		</tr>
    				  		<tr class="dcdcdc">
    				  			<td height="45" colspan="2">???????????????</td>
    				  		</tr>
    				  		<tr class="fff">
    				  			<td height="45">?????????${patientMember.parent.name}</td>
    				  			<td>?????????${message("Member.Gender."+patientMember.parent.gender)}</td>
    				  		</tr>
    				  		<tr>
    				  			<td height="45">???????????????${patientMember.parent.birth?string("yyyy-MM-dd")}</td>
    				  			<td>???????????????${patientMember.parent.cardId}</td>
    				  		</tr>
    				  		<tr class="fff">
    				  			<td height="45">?????????${patientMember.parent.nation}</td>
    				  			<td>???????????????${patientMember.parent.mobile}</td>
    				  		</tr>
    				  		<tr>
    				  			<td height="45">QQ???${patientMember.parent.cardQQ}</td>
    				  			<td>?????????${patientMember.parent.cardWX}</td>
    				  		</tr>
    				  		<tr class="fff">
    				  			<td height="45">?????????${patientMember.parent.area.fullName}${patientMember.parent.address}</td>
    				  			<td>???????????????${currency(patientMember.parent.balance,true)}???</td>
    				  		</tr>
    				  		<tr>
    				  			<td height="45">???????????????${patientMember.parent.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
    				  			<td></td>
    				  		</tr>
    				  	</table>
                      </td>
                    </tr>
                  </tbody>
                </table>
            </td>
            </tr>
        </table>
        </div> 

<div class="clear"></div>

</body>
</html>