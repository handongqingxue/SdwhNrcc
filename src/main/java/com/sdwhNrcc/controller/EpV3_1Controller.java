package com.sdwhNrcc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sdwhNrcc.entity.sdwh.LoginUser;
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.v3_1.*;
import com.sdwhNrcc.util.Constant;

@Controller
@RequestMapping(EpV3_1Controller.MODULE_NAME)
public class EpV3_1Controller {

	private static final String ADDRESS_URL="http://"+Constant.EP_SERVICE_IP_STR+":"+Constant.EP_SERVICE_PORT_STR+"/positionApi/";
	public static final String MODULE_NAME="/epV3_1";
	public static final int EP_FLAG=3;
	
	@Autowired
	private EpLoginClientService epLoginClientService;

	@RequestMapping(value="/goTestEp")
	public String goTestEp(HttpServletRequest request) {

		//https://blog.csdn.net/m0_57493148/article/details/124030242
		switchEnterprise(EP_FLAG,request);

		return "/testEpV3_1";
	}
	
	/**
	 * 2.1 获取token
	 * @param serviceIp
	 * @param servicePort
	 * @param clientId
	 * @param clientSecret
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/oauthToken")
	@ResponseBody
	public Map<String, Object> oauthToken(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			JSONObject bodyParamJO=new JSONObject();
			
			String apiMethod="oauth/token";
			String clientId = request.getParameter("clientId");
			String clientSecret = request.getParameter("clientSecret");
			String params="?client_id="+clientId+"&grant_type=client_credentials&client_secret="+clientSecret;
			resultJO = requestApi(apiMethod,params,bodyParamJO,"GET",request);
				
			String status=resultJO.get("status").toString();
			String access_token=resultJO.get("access_token").toString();
			System.out.println("status==="+status);
			System.out.println("access_token==="+access_token);
			
			EpLoginClient elc=new EpLoginClient(access_token,clientId);
			epLoginClientService.add(elc);
			
			resultMap.put("access_token", access_token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}

	@RequestMapping(value="/apiStaffDataList")
	@ResponseBody
	public Map<String, Object> apiStaffDataList(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			JSONObject bodyParamJO=new JSONObject();
			bodyParamJO.put("type", 1);
			bodyParamJO.put("orgId", 100);
			
			String apiMethod="api/staff/dataList";
			String params="";
			resultJO = requestApi(apiMethod,params,bodyParamJO,"POST",request);
			resultMap=JSON.parseObject(resultJO.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
	
	public void switchEnterprise(int epFlag,HttpServletRequest request) {
		String serviceIp=null;
		int servicePort=0;
		String tenantId=null;
		String userId=null;
		String password=null;
		String clientSecret=null;
		switch (epFlag) {
		case Constant.WFPXHGYXGS:
			serviceIp=Constant.SERVICE_IP_WFPXHGYXGS;
			servicePort=Constant.SERVICE_PORT_WFPXHGYXGS;
			tenantId=Constant.TENANT_ID_WFPXHGYXGS;
			userId=Constant.USER_ID_WFPXHGYXGS;
			password=Constant.PASSWORD_WFPXHGYXGS;
			clientSecret=Constant.CLIENT_SECRET_WFPXHGYXGS;
			break;
		}
		request.setAttribute("serviceIp", serviceIp);
		request.setAttribute("servicePort", servicePort);
		request.setAttribute("tenantId", tenantId);
		request.setAttribute("userId", userId);
		request.setAttribute("password", password);
		request.setAttribute("clientSecret", clientSecret);
	}
	
	public JSONObject requestApi(String apiMethod, String params, JSONObject bodyParamJO, String requestMethod, HttpServletRequest request)
			throws IOException {
		StringBuffer sbf = new StringBuffer(); 
		String strRead = null; 
		String serverUrl=ADDRESS_URL+apiMethod+params;
		String serviceIp = request.getParameter("serviceIp");
		String servicePort = request.getParameter("servicePort");
		URL url = new URL(serverUrl.replaceAll(Constant.EP_SERVICE_IP_STR, serviceIp).replaceAll(Constant.EP_SERVICE_PORT_STR, servicePort));
		HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
		
		//connection.setInstanceFollowRedirects(false); 
		
		HttpSession session = request.getSession();
		if(!apiMethod.contains("oauth/token")) {
			String access_token = null;
			Object epLoginClientObj = session.getAttribute("epLoginClient");
			if(epLoginClientObj!=null) {
				EpLoginClient epLoginClient = (EpLoginClient)epLoginClientObj;
				access_token = epLoginClient.getAccess_token();
			}
			
			if(access_token==null) {
				String client_id = request.getParameter("tenantId");
				if(client_id!=null)
					access_token = epLoginClientService.getTokenByClientId(client_id);
			}
				
			if(!StringUtils.isEmpty(access_token))
				connection.setRequestProperty("Authorization", "Bearer "+access_token);
		}
		connection.setRequestMethod(requestMethod);//请求方式
		connection.setDoInput(true); 
		connection.setDoOutput(true); 
		//header内的的参数在这里set    
		//connection.setRequestProperty("key", "value");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect(); 
		
		
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8"); 
		//body参数放这里
		String bodyParamStr = bodyParamJO.toString();
		//System.out.println("bodyParamStr==="+bodyParamStr);
		writer.write(bodyParamStr);
		//writer.write("{ \"jsonrpc\": \"2.0\", \"params\":{\"tenantId\":\"ts000000061\",\"userId\":\"test001\"}, \"method\":\"getCode\", \"id\":1 }"); 
		writer.flush();
		InputStream is = connection.getInputStream(); 
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
		while ((strRead = reader.readLine()) != null) { 
			sbf.append(strRead); 
			sbf.append("\r\n"); 
		}
		reader.close(); 
		
		connection.disconnect();
		String result = sbf.toString();
		System.out.println("result==="+result);
		JSONObject resultJO = null;
		if(result.contains("DOCTYPE")) {
			resultJO = new JSONObject();
			resultJO.put("status", "no");
		}
		else if(result.contains("error")) {
			resultJO = new JSONObject(result);
			resultJO.put("status", "no");
		}
		else {
			resultJO = new JSONObject(result);
			resultJO.put("status", "ok");
			
			if(apiMethod.contains("oauth/token")) {
				if(!checkTokenInSession(session)) {
					String access_token = resultJO.getString("access_token");
					EpLoginClient elc=new EpLoginClient();
					elc.setAccess_token(access_token);
					session.setAttribute("epLoginClient", elc);
				}
			}
		}
		return resultJO;
	}
	
	public boolean checkTokenInSession(HttpSession session) {
		Object epLoginClientObj = session.getAttribute("epLoginClient");
		if(epLoginClientObj==null)
			return false;
		else {
			EpLoginClient epLoginClient = (EpLoginClient)epLoginClientObj;
			if(epLoginClient==null)
				return false;
			else {
				String access_token = epLoginClient.getAccess_token();
				if(StringUtils.isEmpty(access_token))
					return false;
				else
					return true;
			}
		}
	}
}
