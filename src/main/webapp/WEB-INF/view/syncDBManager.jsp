<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"
		+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=basePath %>resource/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
var path='<%=basePath %>';
var apiPath=path+"api/";
var epV1_3Path=path+"epV1_3/";
var interval="60000";
var cityFlag='${requestScope.cityFlag}';
var systemFlag='${requestScope.systemFlag}';
var epFlag='${requestScope.systemFlag}';
var epVersion='${requestScope.epVersion}';
var version_1_3='${requestScope.version_1_3}';
var version_3_1='${requestScope.version_3_1}';
$(function(){
	console.log("cityFlag="+cityFlag+",systemFlag="+systemFlag+",epVersion="+epVersion);
	makeSync();
	if(epVersion==version_1_3){
		setInterval(function(){
			insertLocationData();//因为是老版平台,就得每隔一段时间调用一次平台接口,把位置和报警数据先暂存到数据库里，再同步到省平台上
			insertWarnRecordData();
			dataEmployeeLocations();
		},interval);
	}
	else if(epVersion==version_3_1){
		receiveMessage();//只有新版真源平台才需要对接这个推送，只要数据有变化就会收到推送消息
		setInterval(function(){//每隔一段时间把人员位置和报警信息同步到省平台一次
			dataEmployeeLocations();
			//dataEmployeeAlarm();
		},interval);
	}
});

/*
function insertDutyData(){
	$.post(epV1_3Path+"insertDutyData",
		{epFlag:epFlag},
		function(data){
			var status=data.status;
			if(status=="ok")
				console.log(data.message);
		}
	,"json");
}
*/

function makeSync(){
	$.post(apiPath+"syncDBManager/makeSync",
		{systemFlag:systemFlag},
		function(){
		
		}
	,"json");
}

function dataEmployeeLocations(){
	$.post(apiPath+"dataEmployeeLocations",
		{cityFlag:cityFlag,systemFlag:systemFlag},
		function(result){
			if(result.code=="200")
				dataEmployeeAlarm();
		}
	,"json");
}

function dataEmployeeAlarm(){
	$.post(apiPath+"dataEmployeeAlarm",
		{cityFlag:cityFlag,systemFlag:systemFlag},
		function(){
		
		}
	,"json");
}

function insertLocationData(){
	$.post(epV1_3Path+"insertLocationData",
		{epFlag:epFlag},
		function(data){
			var status=data.status;
			if(status=="ok"){
				console.log(data.message);
			}
		}
	,"json");
}

function insertWarnRecordData(){
	$.post(epV1_3Path+"insertWarnRecordData",
		{epFlag:epFlag},
		function(data){
			var status=data.status;
			if(status=="ok")
				console.log(data.message);
		}
	,"json");
}

function receiveMessage(){
	$.post(apiPath+"serverReceiver/receiveMessage",
		{systemFlag:systemFlag},
		function(){
		
		}
	,"json");
}
</script>
<title>Insert title here</title>
</head>
<body>

</body>
</html>