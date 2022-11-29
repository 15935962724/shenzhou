<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看医生 - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>


<script type="text/javascript">
$().ready(function() {
	
	[@flash_message /]
	
	$('.delete').click(function(){
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
	
	
		$('.evaluateDelete').click(function(){
		var id = $(this).attr("data_id");
		
			if(confirm("确定要删除吗？")){
				$.ajax({ 
				url: "delete_doctor_evalueate.jhtml",
				type: "POST",
				data: {
					ids:id
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
	
	
			$('#status').bind("change",function(){
			
			var status = $(this).val();
			var id = $(this).attr("data_id");
			var statusExplain = $('#statusExplain').val();
			
				$.ajax({ 
				url: "updateStatus.jhtml",
				type: "POST",
				data: {
					id:id,
					status:status,
					statusExplain:statusExplain
				},
				datatype:"text",
				cache: false,
				success: function(message){
				 $.message(message);
				location.reload();
		    	},
		    	error:function(message){
		    	$.message("warn","操作失败");
		    	}
		    });
	});
	
	// 审核
		$('.status').click(function() {
		var index = $(this).attr('data_index');
			$.dialog({
				title: "审核",
				[@compress single_line = true]
					content: '
					<form id="doctorForm" action="updateDoctorImage.jhtml" method="post">
						<input type="hidden" name="token" value="${token}" \/>
						<input type="hidden" name="doctorId" value="${doctor.id}" \/>
						<input type="hidden" name="index" value="'+index+'" \/>
						<table class="input">
							<tr>
								<th>
									状态:
								<\/th>
								<td>
									<select name="status">
										[#list status as sta]
											<option value="${sta}">${message("Doctor.Status." + sta)}<\/option>
										[/#list]
									<\/select>
								<\/td>
								<th>
									审核说明:
								<\/th>
								<td>
									<input type="text" name="statusExplain" class="text" maxlength="200" \/>
								<\/td>
							<\/tr>
						<\/table>
					<\/form>',
				[/@compress]
				width: 500,
				modal: true,
				ok: "${message("admin.dialog.ok")}",
				cancel: "${message("admin.dialog.cancel")}",
				onOk: function() {
					$("#doctorForm").submit();
					return false;
				}
			});
		});
	
	
	
	
});

</script>

</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 查看医生
	</div>
	<ul id="tab" class="tab">
		<li>
			<input type="button" value="基本信息" />
		</li>
		<li>
			<input type="button" value="服务项目" />
		</li>
		<li>
			<input type="button" value="排班信息" />
		</li>
		<li>
			<input type="button" value="预约管理" />
		</li>
		<li>
			<input type="button" value="回访信息" />
		</li>
		<li>
			<input type="button" value="评价信息" />
		</li>
		<li>
			<input type="button" value="实名认证" />
		</li>
		<li>
			<input type="button" value="资质审核" />
		</li>
	</ul>
	<!--基本信息start-->
	<table class="input tabContent">
		<tr>
			<th>
				姓名:
			</th>
			<td>
				${doctor.name}
			</td>
		</tr>
		<tr>
			<th>
				性别:
			</th>
			<td>
				${message("Member.Gender."+doctor.gender)}
			</td>
		</tr>
		<tr>
			<th>
				岗位级别:
			</th>
			<td>
				[#if doctor.doctorCategory??]${doctor.doctorCategory.name}[/#if]
			</td>
		</tr>
		<tr>
			<th>
				出生日期:
			</th>
			<td>
				[#if doctor.birth??]${doctor.birth?string("yyyy-MM-dd")}[/#if]
			</td>
		</tr>
		<tr>
			<th>
				服务机构:
			</th>
			<td>
				[#list doctor.doctorMechanismRelation as doctorMechanism]
			  			 [#if doctorMechanism.audit == "succeed"]
			  			      ${doctorMechanism.mechanism.name}、
			  			 [/#if]
				[/#list]
			</td>
		</tr>
		<tr>
			<th>
				服务项目:
			</th>
			<td>
				[#if doctor.projects.size()>0]
					[#list doctor.projects as project]
						${project.name}、
					[/#list]
				[#else]
					暂无项目   
				[/#if]
			</td>
		</tr>
		<tr>
			<th>
				个人简介：
			</th>
			<td>
				${doctor.introduce}
			</td>
		</tr>
	</table>
	<!--基本信息end-->
	<!--服务项目start-->
	<table class="tabContent list">
			<tr>
				
				<th>
					<a href="javascript:;" class="sort" name="name">项目名称</a>
				</th>
				<th>
					<span>项目分类</span>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="price">价格(元/分钟)</a>
				</th>
				<th>
					<span>服务医生</span>
				</th>
				<th>
					<span>审核状态</span>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">创建时间</a>
				</th>
			</tr>
			[#list  doctor.projects as project]
				<tr>
					
					<td>
						${project.name}
					</td>
					<td>
						${project.serverProjectCategory.name}
					</td>
					<td>
						${currency(project.price)}/${project.time}
					</td>
					<td>
						${doctor.name}
					</td>
					<td>
						${message("Project.Audit." + project.audit)}
					</td>
					<td>
						<span title="${doctor.createDate?string("yyyy-MM-dd HH:mm:ss")}">${doctor.createDate}</span>
					</td>
				</tr>
			[/#list]
	</table>
	<!--服务项目end-->
	<!--排班信息start-->
	<table class="tabContent list">
			<tr>
				<th>
					<span>姓名</span>
				</th>
				[#list dateDays as dateDay]
				<th>
					${dateDay.week} ${dateDay.dateDay?string("yyyy-MM-dd")}
				</th>
                [/#list]
			</tr>
			<tr>
				<td>
					${doctor.name}
				</td>
				[#list dateDays as dateDay]
				<td>
					[#assign count = 0]
	               	[#list doctor.workDays as workDay]
		               	   [#if  (workDay.workDayDate)?string("yyyyMMdd") == (dateDay.dateDay)?string("yyyyMMdd") && (workDay.isArrange) ]
			               	     	<input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" />
			               	     	[#assign count = count+1 ]
		               	   [/#if]
	               	[/#list]
					[#if count==0]<input type="button" name="button3" id="button3" value="未排" class="button_3" />[/#if]
				</td>
                [/#list]
			</tr>
	</table>
	<!--排班信息end-->
	<!--预约管理start-->
		<table class="tabContent list">
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
	<!--预约管理end-->
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
		[#list page.content as visitMessage ]
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
				<a href="javascript:;" data_id="${visitMessage.id}" class="delete" >[删除]</a>
				</td>
			</tr>
		[/#list]
	</table>
	<!--回访信息end-->
	<!--评价信息start-->
	<table class="tabContent list">
		<tr>
			<th>
				项目名称
			</th>
			<th>
				评论人
			</th>
			<th>
				评论内容
			</th>
			<th>
				综合得分
			</th>
			<th>
				技能评分
			</th>
			<th>
				服务评分
			</th>
			<th>
				沟通评分
			</th>
			<th>
				评论时间
			</th>
			<th>
				操作
			</th>
		</tr>
		[#list evaluatePage.content as evaluate ]
			<tr>
				<td>
				${evaluate.project.name}
				</td>
				<td>
				${evaluate.member.name}
				</td>
				<td>
				${evaluate.content}
				</td>
				<td>
				${evaluate.scoreSort}
				</td>
				<td>
				${evaluate.skillSort}
				</td>
				<td>
				${evaluate.serverSort}
				</td>
				<td>
				${evaluate.communicateSort}
				</td>
				<td>
				${evaluate.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<td>
				<a href="javascript:;" data_id="${evaluate.id}" class="evaluateDelete" >[删除]</a>
				</td>
			</tr>
		[/#list]
	</table>
	<!--评价信息end-->	
	<!--实名认证start-->
	<table class="input tabContent">
			<tr>
				<th>
					姓名:
				</th>
				<td>
					${doctor.name}
				</td>
			</tr>
			<tr>
				<th>
					性别:
				</th>
				<td>
					${message("Member.Gender."+doctor.gender)}
				</td>
			</tr>
			<tr>
				<th>
					审核:
				</th>
				<td>
					<select data_id = "${doctor.id}" id = "status" name="status">
						[#list status as statu]
							<option value="${statu}" [#if statu == doctor.status] selected="selected"[/#if]>${message("Doctor.Status." + statu)}</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					身份证正面:
				</th>
				<td>
					<a href = "${doctor.idCardFrontImg}" target = "_blank"><img width="500" src ="${doctor.idCardFrontImg}" /></a>
				</td>
			</tr>
			<tr>
				<th>
					身份证反面:
				</th>
				<td>
					<a href = "${doctor.idCardReverseImg}" target = "_blank"><img  width="500" src ="${doctor.idCardReverseImg}" /></a>
				</td>
			</tr>
			<tr>
				<th>
					备注：
				</th>
				<td>
					<textarea id = "statusExplain" name="statusExplain" class="" style="width:30%;height: 130px;">${doctor.statusExplain}</textarea>
				</td>
			</tr>
	</table>
	<!--实名认证end-->
	<!--资质审核start-->
	<table class="input tabContent">
		<tr class="title">
			<th>
				证书名称
			</th>
			<th>
				审核状态
			</th>
			<th>
				操作
			</th>
		</tr>
		[#list doctor.doctorImages as doctorImage]
		<tr>
			<td>
				${doctorImage.title}
			</td>
			<td>
				${message("Doctor.Status."+doctorImage.status)}
			</td>
			<td>
				<a href = "${doctorImage.source}" target = "_blank">[证书查看]</a> <a href="javaScript:;" data_index = "${doctorImage_index}" class="status">[审核]</a>
			</td>
		</tr>
		[/#list]
		
	</table>
	<!--资质审核end-->
</body>
</html>