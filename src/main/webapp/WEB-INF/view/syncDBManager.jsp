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
var sdwhApiPath=path+"sdwhApi/";
var lzqApiPath=path+"lzqApi/";
var serverReceiverPath=path+"serverReceiver/";
var syncDBManagerPath=path+"syncDBManager/";
var epV1_3Path=path+"epV1_3/";
var epV3_1Path=path+"epV3_1/";
var interval;
var cityFlag='${requestScope.cityFlag}';
var systemFlag='${requestScope.systemFlag}';
var wfrzjxhyxgs='${requestScope.wfrzjxhyxgs}';
var sdxjyjxhxpyxgs='${requestScope.sdxjyjxhxpyxgs}';
var epFlag='${requestScope.systemFlag}';
var epVersion='${requestScope.epVersion}';
var apiFlag='${requestScope.apiFlag}';
var version_1_3='${requestScope.version_1_3}';
var version_3_1='${requestScope.version_3_1}';
var sdwhFlag='${requestScope.sdwhFlag}';
var lzqFlag='${requestScope.lzqFlag}';
$(function(){
	console.log("cityFlag="+cityFlag+",systemFlag="+systemFlag+",epVersion="+epVersion+",apiFlag="+apiFlag);
	makeSync();
	if(epVersion==version_1_3){
		if(systemFlag==wfrzjxhyxgs)
			interval="35000";//润中的定位信息每隔35s同步省平台一次
		else
			interval="60000";//其他企业的定位信息每隔60s同步省平台一次
		setInterval(function(){
			insertLocationData();//因为是老版平台,就得每隔一段时间调用一次平台接口,把位置和报警数据先暂存到数据库里，再同步到省平台上
			insertWarnRecordData();
			dataEmployeeLocations();
		},interval);
	}
	else if(epVersion==version_3_1){
		interval="60000";
		receiveMessage();//只有新版真源平台才需要对接这个推送，只要数据有变化就会收到推送消息
		setInterval(function(){//每隔一段时间把人员位置和报警信息同步到省平台一次
			dataEmployeeLocations();
			//dataEmployeeAlarm();
		},interval);
	}
	//receiveMessage();
	//insertDeptData();
});

function insertDeptData(){
	$.post(epV3_1Path+"insertDeptData",
		{epFlag:epFlag},
		function(data){
			var status=data.status;
			if(status=="ok")
				console.log(data.message);
		}
	,"json");
}

function makeSync(){
	$.post(syncDBManagerPath+"makeSync",
		{systemFlag:systemFlag},
		function(){
		
		}
	,"json");
}

function dataEmployeeLocations(){
	var path;
	if(apiFlag==sdwhFlag)
		path=sdwhApiPath;
	else if(apiFlag==lzqFlag)
		path=lzqApiPath;
	$.post(path+"dataEmployeeLocations",
		{cityFlag:cityFlag,systemFlag:systemFlag},
		function(result){
			if(result.code=="200")
				dataEmployeeAlarm();
		}
	,"json");
}

function dataEmployeeAlarm(){
	var path;
	if(apiFlag==sdwhFlag)
		path=sdwhApiPath;
	else if(apiFlag==lzqFlag)
		path=lzqApiPath;
	$.post(path+"dataEmployeeAlarm",
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
	if(systemFlag==sdxjyjxhxpyxgs){
		var url=epV3_1Path+"receiveUDPData";
		setInterval(function(){
			$.post(url,
				{systemFlag:systemFlag},
				function(){
				
				}
			,"json");
		},"10000");
	}
	else{
		var url=serverReceiverPath+"receiveMessage";
		$.post(url,
			{systemFlag:systemFlag},
			function(){
			
			}
		,"json");
	}
}
</script>
<title>Insert title here</title>
</head>
<body>

</body>
</html>