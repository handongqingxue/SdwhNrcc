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
$(function(){
	login();
});

function login(){
	/*
	var params="{'username':'weifang_report_data','password':'Um5oNWFqSXdNakFo'}";
	$.ajax({
		url:apiPath+"login",
		type:"post",
		contentType:"application/json;charset-utf-8",
		data:JSON.stringify(params),
		dataType:"json",
		async:false,
		success:function(data){
			alert(data);
		}
	});
	*/
	
	$.post(apiPath+"dataEmployeeInfo",
		{username:"weifang_report_data",password:"Rnh5ajIwMjAh"},
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