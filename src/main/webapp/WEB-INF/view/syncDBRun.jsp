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
<title>Insert title here</title>
<script type="text/javascript">
var path='<%=basePath %>';
var sdwhApiPath=path+"sdwhApi/";
var lzqApiPath=path+"lzqApi/";
var epV1_3Path=path+"epV1_3/";
var epV3_1Path=path+"epV3_1/";
var cityFlag='${requestScope.cityFlag}';
var systemFlag='${requestScope.systemFlag}';
var epFlag='${requestScope.systemFlag}';
var epVersion='${requestScope.epVersion}';
var apiFlag='${requestScope.apiFlag}';
var version_1_3='${requestScope.version_1_3}';
var version_3_1='${requestScope.version_3_1}';
var sdwhFlag='${requestScope.sdwhFlag}';
var lzqFlag='${requestScope.lzqFlag}';
$(function(){
	console.log("cityFlag="+cityFlag+",systemFlag="+systemFlag+",epVersion="+epVersion);
	if(epVersion==version_1_3){
		dataEmployeeInfo();
	}
	else if(epVersion==version_3_1){
		insertStaffData();
		//dataEmployeeInfo();
	}
});

function insertStaffData(){
	//alert(epVersion)
	$.post(epV3_1Path+"insertStaffData",
		{epFlag:epFlag},
		function(data){
			console.log(data);
			if(data.status=="ok")
				dataEmployeeInfo();
		}
	,"json");
}

function dataEmployeeInfo(){
	//alert(apiFlag)
	var path;
	if(apiFlag==sdwhFlag)
		path=sdwhApiPath;
	else if(apiFlag==lzqFlag)
		path=lzqApiPath;
	$.post(path+"dataEmployeeInfo",
		{cityFlag:cityFlag,systemFlag:systemFlag},
		function(){
		
		}
	,"json");
	
	//window.opener=null;
	//window.open(' ', '_self', ' ');
	window.close();
}
</script>
</head>
<body>

</body>
</html>