package com.sdwhNrcc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sdwhNrcc.entity.sdwh.LoginUser;
import com.sdwhNrcc.entity.v1_3.Entity;
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
	@Autowired
	private StaffService staffService;

	@RequestMapping(value="/goTestEp")
	public String goTestEp(HttpServletRequest request) {

		//https://blog.csdn.net/m0_57493148/article/details/124030242
		//http://localhost:8080/SdwhNrcc/epV3_1/goTestEp

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
			String params="?client_id="+Constant.EP_CLIENT_ID_STR+"&grant_type=client_credentials&client_secret="+Constant.EP_CLIENT_SECRET_STR;
			resultJO = requestApi(apiMethod,params,bodyParamJO,"GET",request);
			String resultStr = resultJO.toString();
			System.out.println("resultJO==="+resultStr);
				
			String status=resultJO.get("status").toString();
			String access_token=resultJO.get("access_token").toString();
			System.out.println("status==="+status);
			System.out.println("access_token==="+access_token);

			String clientId = request.getAttribute("tenantId").toString();
			EpLoginClient elc=new EpLoginClient(access_token,clientId);
			epLoginClientService.add(elc);
			
			HttpSession session = request.getSession();
			Object epLoginClientObj = session.getAttribute("epLoginClient");
			if(epLoginClientObj!=null) {
				EpLoginClient epLoginClient = (EpLoginClient)epLoginClientObj;
				epLoginClient.setAccess_token(access_token);
			}

			resultMap=JSON.parseObject(resultStr, Map.class);
			//resultMap.put("access_token", access_token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
	
	public boolean reOauthToken(HttpServletRequest request) {
		//switchEnterprise(EP_FLAG,request);
		Map<String, Object> resultMap = oauthToken(request);
		String status = resultMap.get("status").toString();
		if("ok".equals(status))
			return true;
		else
			return false;
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
	
	@RequestMapping(value="/insertStaffData")
	@ResponseBody
	public Map<String, Object> insertStaffData(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> staffListMap = apiStaffDataList(request);
			String status = staffListMap.get("status").toString();
			if("ok".equals(status)) {
				Object dataObj = staffListMap.get("data");
				com.alibaba.fastjson.JSONObject dataJO = null;
				com.alibaba.fastjson.JSONArray recordJA = null;
				if(dataObj!=null) {
					dataJO=(com.alibaba.fastjson.JSONObject)dataObj;
					recordJA = dataJO.getJSONArray("records");
					
				}
				List<Staff> staffList = JSON.parseArray(recordJA.toString(),Staff.class);
				int count=staffService.add(staffList);
				if(count==0) {
					resultMap.put("status", "no");
					resultMap.put("message", "初始化员工信息失败");
				}
				else {
					resultMap.put("status", "ok");
					resultMap.put("message", "初始化员工信息成功");
				}
			}
			else {
				boolean success=reOauthToken(request);
				System.out.println("success==="+success);
				/*
				if(success) {
					Thread.sleep(1000*10);//避免频繁操作，休眠10秒后再执行
					insertStaffData(request);
				}
				*/
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
	
	public void switchService(int epFlag,HttpServletRequest request) {
		String serviceIp=null;
		int servicePort=0;
		String tenantId=null;
		switch (epFlag) {
		case Constant.WFPXHGYXGS:
			serviceIp=Constant.SERVICE_IP_PX;
			servicePort=Constant.SERVICE_PORT_PX;
			tenantId=Constant.TENANT_ID_WFPXHGYXGS;
			break;
		}
		request.setAttribute("serviceIp", serviceIp);
		request.setAttribute("servicePort", servicePort);
		request.setAttribute("tenantId", tenantId);
	}
	
	public void switchTenant(int epFlag,HttpServletRequest request) {
		String tenantId=null;
		String clientSecret=null;
		switch (epFlag) {
		case Constant.WFPXHGYXGS:
			tenantId=Constant.TENANT_ID_WFPXHGYXGS;
			clientSecret=Constant.CLIENT_SECRET_WFPXHGYXGS;
			break;
		}
		request.setAttribute("tenantId", tenantId);
		request.setAttribute("clientSecret", clientSecret);
	}
	
	public JSONObject requestApi(String apiMethod, String params, JSONObject bodyParamJO, String requestMethod, HttpServletRequest request) {
		
		JSONObject resultJO = null;
		try {
			switchService(EP_FLAG,request);
			
			StringBuffer sbf = new StringBuffer(); 
			String strRead = null; 
			String serverUrl=ADDRESS_URL+apiMethod+params;
			
			String serviceIp = request.getAttribute("serviceIp").toString();
			String servicePort = request.getAttribute("servicePort").toString();
			serverUrl=serverUrl.replaceAll(Constant.EP_SERVICE_IP_STR, serviceIp);
			serverUrl=serverUrl.replaceAll(Constant.EP_SERVICE_PORT_STR, servicePort);
			if(apiMethod.contains("oauth/token")) {
				switchTenant(EP_FLAG,request);
				String clientId = request.getAttribute("tenantId").toString();
				String clientSecret = request.getAttribute("clientSecret").toString();
				serverUrl=serverUrl.replaceAll(Constant.EP_CLIENT_ID_STR, clientId);
				serverUrl=serverUrl.replaceAll(Constant.EP_CLIENT_SECRET_STR, clientSecret);
			}
			
			URL url = new URL(serverUrl);
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
					Object client_idObj = request.getAttribute("tenantId");
					if(client_idObj!=null) {
						String client_id = client_idObj.toString();
						if(client_id!=null)
							access_token = epLoginClientService.getTokenByClientId(client_id);
					}
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception.....");
			resultJO = new JSONObject();
			resultJO.put("status", "no");
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
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
