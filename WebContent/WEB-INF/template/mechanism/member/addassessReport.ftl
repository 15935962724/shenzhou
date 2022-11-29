<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.add")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/lhgcalendar.min.js"></script>

<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>

<style type="text/css">
	.specificationSelect {
		height: 100px;
		padding: 5px;
		overflow-y: scroll;
		border: 1px solid #cccccc;
	}
	
	.specificationSelect li {
		float: left;
		min-width: 150px;
		_width: 200px;
	}
	
	.w_350{width:350px; margin:5px;}
	.w_100{width:150px; margin:5px;}
	.z_1{ font-size:12px; line-height:170%;}
	
</style>

<script type="text/javascript">
$().ready(function() {

 	$('#date_1').calendar();
    $('#date_2').calendar();

var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
	
		rules: {
			diseaseName: "required",
			nowExplain: "required",
			assessResult: "required",
			proposal: "required",
			shortTarget: "required",
			longTarget: "required",
			startTime: "required",
			endTime: "required",
			summary: "required"
			
		},
		
		submitHandler: function(form) {
		var i = $('#project option').size();
		console.log($('#project option').text());
		console.log(i);
		if(i<1){
		$.message("warn", "至少添加一个康护项目");
		return false;
		}
		
		var tmp_str="";
		for(var j = 0; j < i; j++){
				tmp_str = tmp_str  + $('#project option:eq('+j+')').val() + "&";
		}
		console.log(tmp_str);
		$('#drillContent').val(tmp_str);
		
		 form.submit();
		}
	});


});
</script>	

<script type="text/javascript">
//动态删除select中的某一项option：
function delOneOption(indx,id){
      document.getElementById(id).options.remove(indx);
}

// 动态添加select中的项option:
function addOneOption(text,val,n,t_id){
      //document.getElementById("user_dm").options.add(new Option(2,"mytest"));
	if (text == '' || val == '' )
		{
			$.message("warn", "请选择服务项目");
			return false;
		}
	
	if(isNaN(n) || n<1)
		{
			$.message("warn", "服务次数应为大于0的数字");
			return false;
		}
		
	text = text + ' ' + n + '次';
	val  = val + ',' + n;
	var selectObj=document.getElementById(t_id);
	selectObj.options[selectObj.length] = new Option(text, val);
}

function CheckForm(o)
	{
		sCantBlank = ",name,describe,result,result,target,date_1,plan,project,shortterm,longterm,";
		with(o)
			{
			for(var i=0;i<elements.length;++i)
				{
					if(sCantBlank.indexOf(elements[i].name)>0&&elements[i].value=="")
						{
							alert("请提交完整信息。");
							elements[i].focus();
							return false;
						}
				}
			}
		return true;
	}

        function AddRecord()   
         {   
        
          var row = activeTable.insertRow(activeTable.rows.length);//id=recordTable     
          var col = row.insertCell(0);   
          var i = row.rowIndex;   
          col.innerHTML = '<input type="file"  name="assessReportImages[' + i + '].file"  id="imgfile_'+i+'" />'
          col   =   row.insertCell(1);   
          col.innerHTML = '<input type="button" name="button" id="button" value="删除本行" onclick="DelRecord();" />'
          document.getElementById("picurl_n").value=i; 
         }  
        function DelRecord()
         {
          document.all.activeTable.deleteRow(window.event.srcElement.parentElement.parentElement.rowIndex);   
         }          
	
	
	

</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.product.add")}
	</div>
	<form id="inputForm" action="saveassessreport.jhtml" method="post" enctype="multipart/form-data">
	<input type="hidden" name="patientMemberId" value="${patientMember.id}" />
	<input type="hidden" id = "drillContent" name="drill" value="" />
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="诊评报告" />
			</li>
			<li>
				<input type="button" value="康复计划" />
			</li>
		</ul>
		<table class="input tabContent">
				<!--<tr>
			      <td height="30" colspan="2" align="center" bgcolor="#ECECEC">诊评报告</td>
			    </tr>-->
			    <tr>
			      <td width="31%" height="30" align="right">病患名称：</td>
			      <td width="69%"><input name="diseaseName" type="text" class="w_350" id="diseaseName" /></td>
			    </tr>
			    <tr>
			      <td height="30" align="right">病情描述：</td>
			      <td><textarea name="nowExplain" id="nowExplain" rows="5" class="w_350"></textarea></td>
			    </tr>
			    <tr>
			      <td height="30" align="right">诊评结果：</td>
			      <td><textarea name="assessResult" id="assessResult" rows="5" class="w_350"></textarea></td>
			    </tr>
			    <tr>
			      <td height="30" align="right">康复建议：</td>
			      <td><textarea name="proposal" id="proposal" rows="5" class="w_350"></textarea></td>
			    </tr>
			    <tr>
			      <td height="30" align="right" valign="top">图片上传：</td>
			      <td valign="top">
				      <table width="350" border="0" cellpadding="0" cellspacing="0" id="activeTable">
				          <tr>
				            <td width="272"><input name="picurl_n" type="hidden" id="picurl_n" value="0" >
				            <input type="button" name="button" id="button" value=" 添加图片 " onClick="AddRecord();"/>
				            </td>
				            <td width="78">&nbsp;</td>
				            </tr>
				      </table>
			      </td>
			    </tr>
		</table>
		<table class="input tabContent">
		    <!--<tr>
		      <td height="30" colspan="2" align="center" bgcolor="#EFEFEF">康复计划：</td>
		    </tr>-->
			<tr>
		      <td height="30" align="right">短期目标：</td>
		      <td><textarea name="shortTarget" id="shortTarget" rows="5" class="w_350"></textarea></td>
		    </tr>
		    <tr>
		      <td height="30" align="right">长期目标：</td>
		      <td><textarea name="longTarget" id="longTarget" rows="5" class="w_350"></textarea></td>
		    </tr>
		    <tr>
		      <td height="30" align="right">康复周期：</td>
		      <td>
		          <input type="text" id="startTime" name="startTime" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'endTime\')}'});" />
		          <!--<input type="text" name="date_1" id="date_1" class="w_100" />-->
			       - 
			       <input type="text" id="endTime" name="endTime" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'startTime\')}'});" />
			       <!--<input type="text" name="date_2" id="date_2" class="w_100" />-->
		       </td>
		    </tr>
		    <tr>
		      <td height="30" align="right">康护项目：</td>
		      <td><select name="project" size="6" class="w_100" id="project" ondblclick="delOneOption($('#project').find('option:selected').text(),'project');">
		      </select>
		        &lt;--加入
		        <select name="project_s" size="6" class="w_100" id="project_s">
			        [#list serverProjectCategorys as serverProjectCategory]
								<option value="${serverProjectCategory.id}">
									${serverProjectCategory.name}
								</option>
					[/#list]
		        </select>
		        次数：
		        <label for="n"></label>
		        <input name="n" type="text" id="n" value="1" size="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
		        次
		      <input type="button" name="button2" id="button2" value="添加" onclick="addOneOption($('#project_s').find('option:selected').text(),$('#project_s').val(),$('#n').val(),'project');" />
		      <br />
		      <font color="#FF0000">* 双击取消</font></td>
		    </tr>
	
		    <tr>
		      <td height="30" align="right">康复总结：</td>
		      <td><textarea name="summary" id="summary" rows="5" class="w_350"></textarea></td>
		    </tr>
		</table>
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
		</form>
</body>
</html>