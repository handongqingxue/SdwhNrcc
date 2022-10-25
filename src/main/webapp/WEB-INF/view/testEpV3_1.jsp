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
var epPath=path+"epV3_1/";
var serviceIp='${requestScope.serviceIp}';
var servicePort='${requestScope.servicePort}';
var tenantId='${requestScope.tenantId}';
var userId='${requestScope.userId}';
var password='${requestScope.password}';
var clientSecret='${requestScope.clientSecret}';
$(function(){
	oauthToken();
});

function oauthToken(){
	$.post(epPath+"oauthToken",
		{serviceIp:serviceIp,servicePort:servicePort,clientId:tenantId,clientSecret:clientSecret},
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