$(function(){
	$("#content").css("min-height" , window.screen.height + "px");  //补齐白背景
});
//获取经纬度 longitude-经度，latitude-纬度
if(navigator.geolocation)
	{
		navigator.geolocation.getCurrentPosition(function (position)
			{
				var longitude = position.coords.longitude;
				var latitude = position.coords.latitude;
				//此处ajax调用其他代码进行计算
			});
	}



function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
//		var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
//				+ " " + date.getHours() + seperator2 + date.getMinutes()
//				+ seperator2 + date.getSeconds();
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	return currentdate;
}