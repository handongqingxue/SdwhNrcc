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
var epPath=path+"epV1_3/";
var tenantId='${requestScope.tenantId}';
var userId='${requestScope.userId}';
var password='${requestScope.password}';
$(function(){
	//login();
	//getEntities();
	insertEntityData();
	//insertDutyData();
	//insertTagData();
	//insertWarnRecordData();
	//insertWarnTriggerData();
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
			var status=data.status;
			if(status=="ok")
				console.log(data.result);
		}
	,"json");
}

function insertEntityData(){
	$.post(epPath+"insertEntityData",
		function(data){
			var status=data.status;
			if(status=="ok")
				console.log(data.message);
		}
	,"json");
}

function insertDutyData(){
	$.post(epPath+"insertDutyData",
		function(data){
			var status=data.status;
			if(status=="ok")
				console.log(data.message);
		}
	,"json");
}

function insertTagData(){
	$.post(epPath+"insertTagData",
		function(data){
			var status=data.status;
			if(status=="ok")
				console.log(data.message);
		}
	,"json");
}

function insertWarnRecordData(){
	$.post(epPath+"insertWarnRecordData",
		function(data){
			var status=data.status;
			if(status=="ok")
				console.log(data.message);
		}
	,"json");
}

function insertWarnTriggerData(){
	$.post(epPath+"insertWarnTriggerData",
		function(data){
			var status=data.status;
			if(status=="ok")
				console.log(data.message);
		}
	,"json");
}
</script>
<title>Insert title here</title>
</head>
<body>

</body>
</html>