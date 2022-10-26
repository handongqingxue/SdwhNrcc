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
var apiPath=path+"api/";
$(function(){
	dataEmployeeInfo();
});

function dataEmployeeInfo(){
	$.post(apiPath+"dataEmployeeInfo",
		function(){
		
		}
	,"json");
	
	//window.opener=null;
	//window.open(' ', '_self', ' ');
	//window.close();
}
/*
$.post(phonePath+"insertWarnRecordData",
	function(){
		
	}
,"json");
*/
</script>
</head>
<body>

</body>
</html>