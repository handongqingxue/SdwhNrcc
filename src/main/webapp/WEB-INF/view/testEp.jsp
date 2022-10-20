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
var epPath=path+"ep/";
var tenantId='${requestScope.tenantId}';
var userId='${requestScope.userId}';
var password='${requestScope.password}';
$(function(){
	//login();
	getEntities();
});

function login(){
	$.post(epPath+"login",
		{tenantId:tenantId,userId:userId,password:password},
		function(){
		
		}
	,"json");
}

function getEntities(){
	$.post(epPath+"getEntities",
		{entityType:"staff"},
		function(data){
			console.log(data);
		}
	,"json");
}
</script>
<title>Insert title here</title>
</head>
<body>

</body>
</html>