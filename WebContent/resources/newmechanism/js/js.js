
// JavaScript Document
var shenzhou = {
	base: "/shenzhou",
	locale: "zh_CN"
};

function ifr_height(ifr)
{
	if (self.frameElement && self.frameElement.tagName == "IFRAME")
		{	
			var main = $(window.parent.document).find("#" + ifr);
			var thisheight = $(document.body).height() + 100;
			console.log($(window).height());
			if(thisheight<=1000)
			{
//				thisheight = $(window).height();
				thisheight = 1000;
			}
			main.height(thisheight);
		}
}
function get_cookies()
	{
		var html = $.cookie('iframe_nav');
		$("#page_nav").find("tr").append(html);
		//console.log("html");
		//$("#cont").html(html.replace("display: none","display: block"));
		//$.cookie("iframe_nav", "", {path: "/"});
	}
$(window).load(function () {
	get_cookies();
	ifr_height("main_ifr");
	
	$("#page_nav td").click(function(){
		   $("#page_nav td").removeClass("z14ffffff");
		   $("#page_nav td").removeClass("b2292f7");
		   $("#page_nav td").addClass("z14717171"); 
		   $(this).removeClass("z14717171");
		   $(this).addClass("b2292f7"); 
		   $(this).addClass("z14ffffff"); 
		   var trs=$("#page_nav").find("tr").eq(0).html();
		   var tds=$(this).html();
		   $.cookie("iframe_nav", trs, {path:"/"});
		   var url=tds.substring(tds.indexOf("href=\"")+6,tds.indexOf("\">"));
		   $(location).attr("href", url);
		})
})


$(document).keydown(function(event){
    
     //屏蔽 Alt+ 方向键 ← 
     //屏蔽 Alt+ 方向键 →
     if ((event.altKey)&&((event.keyCode==37)||(event.keyCode==39)))  
     { 
        event.returnValue=false; 
        return false;
     }
  
     //屏蔽退格删除键 
     if(event.keyCode==8){
		var doPrevent; 
		var d = event.srcElement || event.target; 
		if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA') 
			{ 
				doPrevent = d.readOnly || d.disabled; 
			} 
		else
			doPrevent = true; 
		if (doPrevent) 
			event.preventDefault();
     }
         
     //屏蔽F5刷新键 
     if(event.keyCode==116){
        return false; 
     }
  
    //屏蔽alt+R 
    if((event.ctrlKey) && (event.keyCode==82)){
       return false; 
    }
 
 });
$(function(){
	if (window.history && window.history.pushState) 
		{
			$(window).on('popstate', function () 
						 {
							window.history.pushState('forward', null, '#');
							window.history.forward(1);
						 });
		}
	window.history.pushState('forward', null, '#'); //在IE中必须得有这两行
	window.history.forward(1);
})

//取消全选	
function unselectall(chkall)
{
	if($("#"+chkall).get(0).checked)
		$("input[name='" + chkall + "']").removeAttr("checked"); 
}

//全选
function CheckAll(id,chkall)
{
	if($("#"+chkall).get(0).checked)
		{
			$("input[name='" + id + "']").attr("checked","true"); 
		}
	else
		{
			$("input[name='" + id + "']").removeAttr("checked"); 
		}
	}

//明细展开
function show_info(sid,flag)
{
	$("#info_"+sid).toggle();
	
	if(flag=="1")
		{
			ifr_height("main_ifr");
		}
	else
		{
		lock_Scroll();
		}
}

////获取图片地址
	function getObjectURL(file) {
		var url = null;
		if(window.createObjectURL != undefined) { // basic
			url = window.createObjectURL(file);
		} else if(window.URL != undefined) { // mozilla(firefox)
			url = window.URL.createObjectURL(file);
		} else if(window.webkitURL != undefined) { // webkit or chrome
			url = window.webkitURL.createObjectURL(file);
		}
		//console.log(url);
		return url;
	}
		
//计算年龄
function getAge(birth)
{
	 // 获得今天的时间
    var date = new Date();
//    var birthday = '${userDetail.birthday}';
//    alert(birthday);
    var startDate = new Date(birth);
    var newDate = date.getTime() - startDate.getTime();
    // 向下取整  例如 10岁 20天 会计算成 10岁
    // 如果要向上取整 计算成11岁，把floor替换成 ceil
    var age = Math.ceil(newDate / 1000 / 60 / 60 / 24 /365);
    if (isNaN(age)){
        age = "";
    }
    return age;
}



//手机号码验证
function checkPhone(str){
	var reg=/^1[34578]\d{9}$/
	if (reg.test(str)){
		return true;
	} else {
		return false;
	}
}

//纯数字验证
function checkNum(str){
	var reg=/^[0-9]{1,20}$/;
	if (reg.test(str)){
		return true;
	} else {
		return false;
	}
}

//获得月日得到日期oTime  
function getMoth(str){  
    var oDate = new Date(str),  
    oMonth = oDate.getMonth()+1,  
    oDay = oDate.getDate(),  
    oTime = getzf(oMonth) +'-'+ getzf(oDay);//最后拼接时间  
    return oTime;  
};  
//获得年月日      得到日期oTime  
function getMyDate(str){  
    var oDate = new Date(str),  
    oYear = oDate.getFullYear(),  
    oMonth = oDate.getMonth()+1,  
    oDay = oDate.getDate(),  
    oHour = oDate.getHours(),  
    oMin = oDate.getMinutes(),  
    oSen = oDate.getSeconds(),  
    oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间  
    return oTime;  
};  
//补0操作  
function getzf(num){  
    if(parseInt(num) < 10){  
        num = '0'+num;  
    }  
    return num;  
}  
