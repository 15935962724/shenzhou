<tr>
	<td><img onerror = "this.src='${base}/resources/mechanism/images/tmp_2.png'" src="${patientMember.logo}" alt="${patientMember.name}" title="${patientMember.name}" class="head_1" /></td>
</tr>
<tr>
	<td height="50" class="b_a_dcdcdc_1"><span class="sp_m_1">${patientMember.name}</span><span class="z_12_999999_1">　${message("Member.Gender."+patientMember.gender)}　${age(patientMember.birth)}周岁　${patientMember.nation}</span></td>
</tr>
<tr>
	<td height="35" class="b_a_dcdcdc_1">联系电话：${patientMember.mobile}</td>
</tr>
<tr>
	<td height="35" class="b_a_dcdcdc_1">就诊次数：${visits(patientMember.id)}次<span class="z_12_999999_1">　</span>在康护</td>
</tr>
<tr>
	<td height="35" class="b_a_dcdcdc_1">出生日期：${patientMember.birth?string("yyyy-MM-dd")}</td>
</tr>