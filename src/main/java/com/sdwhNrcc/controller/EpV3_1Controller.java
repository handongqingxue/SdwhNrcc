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
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.v3_1.*;
import com.sdwhNrcc.util.Constant;

@Controller
@RequestMapping(EpV3_1Controller.MODULE_NAME)
public class EpV3_1Controller {

	private static final String ADDRESS_URL="http://"+Constant.SERVICE_IP_STR+":"+Constant.SERVICE_PORT_STR+"/positionApi/";
	public static final String MODULE_NAME="/epV3_1";

	@Autowired
	private EpLoginClientService epLoginClientService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private StaffService staffService;

	@RequestMapping(value="/goTestEp")
	public String goTestEp(HttpServletRequest request) {

		//https://blog.csdn.net/m0_57493148/article/details/124030242
		//http://localhost:8080/SdwhNrcc/epV3_1/goTestEp
		//http://localhost:8080/SdwhNrcc/epV3_1/goPage?page=pxhgSync
		//http://localhost:8080/SdwhNrcc/epV3_1/goPage?page=bfxclSync

		return "/testEpV3_1";
	}

	@RequestMapping(value="/goPage")
	public String goPage(HttpServletRequest request) {
		String url = null;
		String page = request.getParameter("page");
		if("testEp".equals(page)){
			String epFlag = request.getParameter("epFlag");
			request.setAttribute("epFlag", epFlag);
			url="/testEpV3_1";
		}
		else if("pxhgSync".equals(page)){
			url="redirect:goPage?page=testEp&epFlag="+Constant.WFPXHGYXGS;
		}
		else if("bfxclSync".equals(page)){
			url="redirect:goPage?page=testEp&epFlag="+Constant.SDBFXCLYXGS;
		}
		return url;
	}
	
	/**
	 * 2.1 ��ȡtoken
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
			String params="?client_id="+Constant.CLIENT_ID_STR+"&grant_type=client_credentials&client_secret="+Constant.CLIENT_SECRET_STR;
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
			Object epLoginClientObj = session.getAttribute("epLoginClient"+clientId);
			if(epLoginClientObj!=null) {
				EpLoginClient epLoginClient = (EpLoginClient)epLoginClientObj;
				epLoginClient.setAccess_token(access_token);
				epLoginClient.setClient_id(clientId);
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

	@RequestMapping(value="/apiDeptFormDeptSelect")
	@ResponseBody
	public Map<String, Object> apiDeptFormDeptSelect(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			JSONObject bodyParamJO=new JSONObject();
			
			String apiMethod="api/dept/formDeptSelect/100";
			String params="";
			resultJO = requestApi(apiMethod,params,bodyParamJO,"GET",request);
			resultMap=JSON.parseObject(resultJO.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
	
	@RequestMapping(value="/insertDeptData")
	@ResponseBody
	public Map<String, Object> insertDeptData(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int epFlag = Integer.valueOf(request.getParameter("epFlag"));
			System.out.println("epFlag==="+epFlag);
			request.setAttribute("epFlag", epFlag);
			Map<String, Object> deptListMap = apiDeptFormDeptSelect(request);
			String status = deptListMap.get("status").toString();
			if("ok".equals(status)) {
				Object dataObj = deptListMap.get("data");
				com.alibaba.fastjson.JSONArray dataJA = null;
				if(dataObj!=null) {
					dataJA=(com.alibaba.fastjson.JSONArray)dataObj;
					
				}
				List<Dept> deptList = JSON.parseArray(dataJA.toString(),Dept.class);
				String databaseName = request.getAttribute("databaseName").toString();
				int count=deptService.add(deptList,databaseName);
				if(count==0) {
					resultMap.put("status", "no");
					resultMap.put("message", "��ʼ��������Ϣʧ��");
				}
				else {
					resultMap.put("status", "ok");
					resultMap.put("message", "��ʼ��������Ϣ�ɹ�");
				}
			}
			else {
				boolean success=reOauthToken(request);
				System.out.println("success==="+success);
			}
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
	
	@RequestMapping(value="/insertStaffData")
	@ResponseBody
	public Map<String, Object> insertStaffData(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int epFlag = Integer.valueOf(request.getParameter("epFlag"));
			System.out.println("epFlag==="+epFlag);
			request.setAttribute("epFlag", epFlag);
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
				String databaseName = request.getAttribute("databaseName").toString();
				int count=staffService.add(staffList,databaseName);
				if(count==0) {
					resultMap.put("status", "no");
					resultMap.put("message", "��ʼ��Ա����Ϣʧ��");
				}
				else {
					resultMap.put("status", "ok");
					resultMap.put("message", "��ʼ��Ա����Ϣ�ɹ�");
				}
			}
			else {
				boolean success=reOauthToken(request);
				System.out.println("success==="+success);
				if(success) {
					Thread.sleep(1000*60);//����Ƶ������������60�����ִ��
					resultMap=insertStaffData(request);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
	
	public void switchService(HttpServletRequest request) {
		String serviceIp=null;
		int servicePort=0;
		String tenantId=null;
		String databaseName=null;
		int epFlag=Integer.valueOf(request.getAttribute("epFlag").toString());
		switch (epFlag) {
		case Constant.WFPXHGYXGS:
			serviceIp=Constant.SERVICE_IP_WFPXHGYXGS;
			servicePort=Constant.SERVICE_PORT_WFPXHGYXGS;
			tenantId=Constant.TENANT_ID_WFPXHGYXGS;
			databaseName=Constant.DATABASE_NAME_WFPXHGYXGS;
			break;
		case Constant.SDFLXCLKJYXGS:
			serviceIp=Constant.SERVICE_IP_SDFLXCLKJYXGS;
			servicePort=Constant.SERVICE_PORT_SDFLXCLKJYXGS;
			tenantId=Constant.TENANT_ID_SDFLXCLKJYXGS;
			databaseName=Constant.DATABASE_NAME_SDFLXCLKJYXGS;
			break;
		case Constant.ZBXQHGYXGS:
			serviceIp=Constant.SERVICE_IP_ZBXQHGYXGS;
			servicePort=Constant.SERVICE_PORT_ZBXQHGYXGS;
			tenantId=Constant.TENANT_ID_ZBXQHGYXGS;
			databaseName=Constant.DATABASE_NAME_ZBXQHGYXGS;
			break;
		case Constant.SDBFXCLYXGS:
			serviceIp=Constant.SERVICE_IP_SDBFXCLYXGS;
			servicePort=Constant.SERVICE_PORT_SDBFXCLYXGS;
			tenantId=Constant.TENANT_ID_SDBFXCLYXGS;
			databaseName=Constant.DATABASE_NAME_SDBFXCLYXGS;
			break;
		case Constant.CYSRHSWKJYXGS:
			serviceIp=Constant.SERVICE_IP_CYSRHSWKJYXGS;
			servicePort=Constant.SERVICE_PORT_CYSRHSWKJYXGS;
			tenantId=Constant.TENANT_ID_CYSRHSWKJYXGS;
			databaseName=Constant.DATABASE_NAME_CYSRHSWKJYXGS;
			break;
		}
		request.setAttribute("serviceIp", serviceIp);
		request.setAttribute("servicePort", servicePort);
		request.setAttribute("tenantId", tenantId);
		request.setAttribute("databaseName", databaseName);
	}
	
	public void switchTenant(HttpServletRequest request) {
		String tenantId=null;
		String clientSecret=null;
		int epFlag=Integer.valueOf(request.getAttribute("epFlag").toString());
		switch (epFlag) {
		case Constant.WFPXHGYXGS:
			tenantId=Constant.TENANT_ID_WFPXHGYXGS;
			clientSecret=Constant.CLIENT_SECRET_WFPXHGYXGS;
			break;
		case Constant.SDFLXCLKJYXGS:
			tenantId=Constant.TENANT_ID_SDFLXCLKJYXGS;
			clientSecret=Constant.CLIENT_SECRET_SDFLXCLKJYXGS;
			break;
		case Constant.ZBXQHGYXGS:
			tenantId=Constant.TENANT_ID_ZBXQHGYXGS;
			clientSecret=Constant.CLIENT_SECRET_ZBXQHGYXGS;
			break;
		case Constant.SDBFXCLYXGS:
			tenantId=Constant.TENANT_ID_SDBFXCLYXGS;
			clientSecret=Constant.CLIENT_SECRET_SDBFXCLYXGS;
			break;
		case Constant.CYSRHSWKJYXGS:
			tenantId=Constant.TENANT_ID_CYSRHSWKJYXGS;
			clientSecret=Constant.CLIENT_SECRET_CYSRHSWKJYXGS;
			break;
		}
		request.setAttribute("tenantId", tenantId);
		request.setAttribute("clientSecret", clientSecret);
	}
	
	public JSONObject requestApi(String apiMethod, String params, JSONObject bodyParamJO, String requestMethod, HttpServletRequest request) {
		
		JSONObject resultJO = null;
		try {
			switchService(request);
			
			StringBuffer sbf = new StringBuffer(); 
			String strRead = null; 
			String serverUrl=ADDRESS_URL+apiMethod+params;
			
			String serviceIp = request.getAttribute("serviceIp").toString();
			String servicePort = request.getAttribute("servicePort").toString();
			String clientId = request.getAttribute("tenantId").toString();
			serverUrl=serverUrl.replaceAll(Constant.SERVICE_IP_STR, serviceIp);
			if("443".equals(servicePort)) {
				serverUrl=serverUrl.replaceAll("http","https");
				serverUrl=serverUrl.replaceAll(":"+Constant.SERVICE_PORT_STR, "");
			}
			else
				serverUrl=serverUrl.replaceAll(Constant.SERVICE_PORT_STR, servicePort);
			
			if(apiMethod.contains("oauth/token")) {
				switchTenant(request);
				String clientSecret = request.getAttribute("clientSecret").toString();
				serverUrl=serverUrl.replaceAll(Constant.CLIENT_ID_STR, clientId);
				serverUrl=serverUrl.replaceAll(Constant.CLIENT_SECRET_STR, clientSecret);
			}
			
			System.out.println("serverUrl==="+serverUrl);
			URL url = new URL(serverUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			//connection.setInstanceFollowRedirects(false); 
			
			HttpSession session = request.getSession();
			if(!apiMethod.contains("oauth/token")) {
				String access_token = null;
				Object epLoginClientObj = session.getAttribute("epLoginClient"+clientId);
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
			connection.setRequestMethod(requestMethod);//����ʽ
			connection.setDoInput(true); 
			connection.setDoOutput(true); 
			//header�ڵĵĲ���������set    
			//connection.setRequestProperty("key", "value");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect(); 
			
			//https://blog.csdn.net/zhengaog/article/details/118405244
			if("POST".equals(requestMethod)) {
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8"); 
				//body����������
				String bodyParamStr = bodyParamJO.toString();
				//System.out.println("bodyParamStr==="+bodyParamStr);
				writer.write(bodyParamStr);
				writer.flush();
			}
			
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
					if(!checkTokenInSession(request)) {
						String access_token = resultJO.getString("access_token");
						EpLoginClient elc=new EpLoginClient();
						elc.setAccess_token(access_token);
						elc.setClient_id(clientId);
						session.setAttribute("epLoginClient"+clientId, elc);
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
	
	public boolean checkTokenInSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String clientId=request.getAttribute("tenantId").toString();
		Object epLoginClientObj = session.getAttribute("epLoginClient"+clientId);
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
