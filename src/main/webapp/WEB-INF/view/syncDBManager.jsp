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
alert(systemFlag);
$(function(){
	makeSync();
	receiveMessage();
	setInterval(function(){
		console.log("11111111");
		dataEmployeeLocations();
		//insertWarnRecordData();
		dataEmployeeAlarm();
	},interval);
});

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
		function(){
		
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

function insertWarnRecordData(){
	$.post(epV1_3Path+"insertWarnRecordData",
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