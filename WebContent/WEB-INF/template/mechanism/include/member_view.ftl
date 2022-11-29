<tr>
	<td><img onerror="this.src='${base}/resources/mechanism/images/r.png'" src="${member.logo}" alt="${member.name}" title="${member.name}" class="head_1" /></td>
</tr>
<tr>
	<td height="50" class="b_a_dcdcdc_1"><span class="sp_m_1">${member.name}</span><span class="z_12_999999_1">　${message("Member.Gender."+member.gender)}　${member.nation}</span></td>
</tr>
<tr>
	<td height="35" class="b_a_dcdcdc_1">联系电话：${member.mobile}</td>
</tr>
<tr>
	<td height="35" class="b_a_dcdcdc_1">名下患者：
		[#list member.children as patient]
		    [#if !patient.isDeleted]
				${patient.name}、
			[/#if]
		[/#list]
	</td>
</tr>
<tr>
	<td height="35" class="b_a_dcdcdc_1">出生日期：${member.birth?string("yyyy-MM-dd")}</td>
</tr>