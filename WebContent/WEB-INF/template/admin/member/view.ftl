<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.member.view")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>


<script type="text/javascript">
$().ready(function() {
	
	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	$('.visitMessageDelete').click(function(){
		var id = $(this).attr("data_id");
		
			if(confirm("确定要删除吗？")){
				$.ajax({ 
				url: "delete_visit_message.jhtml",
				type: "POST",
				data: {
					"id":id
				},
				datatype:"text",
				cache: false,
				success: function(data){
				 $.message("删除成功!");
				window.location.reload();
		    	},
		    	error:function(message){
		    	alert(message);
		    	}
		    });
		}
		
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			modifyBalance: {
				min: -${member.balance},
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			}
		},
	});
	
	
	
});

</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.member.view")}
	</div>
	<ul id="tab" class="tab">
		<li>
			<input type="button" value="基本信息" />
		</li>
		<li>
			<input type="button" value="患者信息" />
		</li>
		<li>
			<input type="button" value="预约信息" />
		</li>
		<li>
			<input type="button" value="回访信息" />
		</li>
		<li>
			<input type="button" value="账户充值" />
		</li>
				<li>
			<input type="button" value="医生信息" />
		</li>
	</ul>
	<!--基本信息start-->
	<table class="input tabContent">
		<tr>
			<th>
				姓名:
			</th>
			<td>
				${member.name}
			</td>
		</tr>
		<tr>
			<th>
				性别:
			</th>
			<td>
				${message("Member.Gender."+member.gender)}
			</td>
		</tr>
		<tr>
			<th>
				出生日期:
			</th>
			<td>
				${member.birth?string("yyyy-MM-dd")}
			</td>
		</tr>
		<tr>
			<th>
				身份证:
			</th>
			<td>
				${member.cardId}
			</td>
		</tr>
		<tr>
			<th>
				民族:
			</th>
			<td>
				${member.nation}
			</td>
		</tr>
		<tr>
			<th>
				联系电话:
			</th>
			<td>
				${member.mobile}
			</td>
		</tr>
		<tr>
			<th>
				户籍地址:
			</th>
			<td>
				${member.area.fullName}${member.areaAddress}
			</td>
		</tr>
		<tr>
			<th>
				常居地址:
			</th>
			<td>
				[#if member.nowArea??]${member.nowArea.fullName}[/#if]${member.address}
			</td>
		</tr>
		<tr>
			<th>
				注册时间:
			</th>
			<td>
				${member.createDate?string("yyyy-MM-dd HH:mm:ss")}
			</td>
		</tr>
		<tr>
			<th>
				账户余额:
			</th>
			<td>
				${currency(member.balance, true)}
				<a href="../deposit/list.jhtml?memberId=${member.id}">[查看预存款]</a>
			</td>
		</tr>
		<tr>
			<th>
				平台充值记录:
			</th>
			<td>
				${currency(member.balance, true)}
				<a href="../platformRechargeLog/list.jhtml?memberId=${member.id}">[查看记录]</a>
			</td>
		</tr>
		<tr>
			<th>
				账户积分:
			</th>
			<td>
				${member.point}
				<a href="../memberPointLog/list.jhtml?memberId=${member.id}">[查看积分]</a>
			</td>
		</tr>
	</table>
	<!--基本信息end-->
	<!--患者信息start-->
	<table class="list tabContent">
			<tr>
				<th>
					患者姓名:
				</th>
				<th>
					患者性别:
				</th>
				<th>
					患者状态:
				</th>
				<th>
					与监护人关系:
				</th>
				<th>
					联系电话:
				</th>
				<th>
					建档时间:
				</th>
				<th>
					操作:
				</th>
			</tr>
			[#list member.children as patient]
			<tr>
				<td>
					${patient.name}
				</td>
				<td>
					${message("Member.Gender."+patient.gender)}
				</td>
				<td>
					${message("Member.HealthType."+patient.healthType)}
				</td>
				<td>
					${patient.relationship.title}
				</td>
				<td>
					${patient.mobile}
				</td>
				<td>
					${patient.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<td>
				    <a href = "">[查看]</a>
				</td>
			</tr>
			[/#list]
			
	</table>
	<!--患者信息end-->
	<!--预约信息start-->
	<table class="list tabContent">
			<tr>
				<th>
					&nbsp;
				</th>
				[#list dateLists as dateDay]
				<th>
					${dateDay.week} ${dateDay.workDate?string("yyyy-MM-dd")}
				</th>
                [/#list]
			</tr>
			[#list workDates as workDate]
			<tr>
				<td>
					${workDate.workDateTime}:00
				</td>
				[#list dateLists as dateDay]
				<td>
							[#assign  count = 0]
							[#list orders as order]
						     [#if (dateDay.workDate?string("yyyyMMdd")) == (order.orderWorkDay?string("yyyyMMdd")) && (order.orderStartTime == workDate.workDateTime) ]
						            [#assign  count = count + 1]
						     [/#if]
						    [/#list]
						    [#if count > 0] <input type="button" name="button3" id="button3" value="已约" class="button_3 b_4c98f6_1" > [/#if]
				</td>
                [/#list]
			</tr>
			[/#list]
			
	</table>
	<!--预约信息end-->
	<!--回访信息start-->
	<table class="tabContent list">
		<tr>
			<th>
				回访人
			</th>
			<th>
				被访人
			</th>
			<th>
				回访时间
			</th>
			<th>
				建立时间
			</th>
			<th>
				回访反馈
			</th>
			<th>
				操作
			</th>
		</tr>
		[#list visitMessagePage.content as visitMessage ]
			<tr>
				<td>
				${visitMessage.doctor.name}
				</td>
				<td>
				${visitMessage.member.name}
				</td>
				<td>
				${visitMessage.visitDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<td>
				${visitMessage.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<td>
				${visitMessage.message}
				</td>
				<td>
				<a href="javascript:;" data_id="${visitMessage.id}" class="visitMessageDelete" >[删除]</a>
				</td>
			</tr>
		[/#list]
	</table>
	<!--回访信息end-->
	<!--账户充值start-->
	<form id="inputForm" action="updateBalance.jhtml" method="post">
	<input type="hidden" name="id" value="${member.id}" />
	<table class="input tabContent">
			<tr>
				<th>
					姓名:
				</th>
				<td>
					${member.name}
				</td>
			</tr>
			<tr>
				<th>
					当前余额:
				</th>
				<td>
					${currency(member.balance, true)}
				</td>
			</tr>
			<tr>
				<th>
					余额调整（充值/扣除）:
				</th>
				<td>
					<input type="text" name="modifyBalance" class="text" maxlength="16" title="${message("admin.member.modifyBalanceTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					备注:
				</th>
				<td>
					<input type="text" name="depositMemo" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="history.go(-1)" />
				</td>
			</tr>
	</table>
	</form>
	<!--账户充值end-->
	<!--医生信息start-->
	<table class="tabContent list">
		<tr>
			<th>
				医生姓名
			</th>
			<th>
				性别
			</th>
			<th>
				级别
			</th>
			<th>
				身份证
			</th>
			<th>
				加入时间
			</th>
			<th>
				操作
			</th>
		</tr>
		 [#list member.doctors as doctor]
			<tr>
				<td>
				${doctor.name}
				</td>
				<td>
				${message("Member.Gender."+doctor.gender)}
				</td>
				<td>
				${doctor.doctorCategory.name}
				</td>
				<td>
				${doctor.entityCode}
				</td>
				<td>
				${doctor.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<td>
				<a href="javascript:;" data_id="${doctor.id}">[暂无]</a>
				</td>
			</tr>
		[/#list]
	</table>
	<!--医生信息end-->
</body>
</html>