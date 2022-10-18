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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdwhNrcc.entity.*;

@Controller
@RequestMapping(ApiController.MODULE_NAME)
public class ApiController {

	private static final String PUBLIC_URL="http://sdwh.nrcc.com.cn:8102";
	public static final String MODULE_NAME="/api";

	@RequestMapping(value="/goTestApi")
	public String goTestApi() {

		return "/testApi";
	}
	
	@RequestMapping(value="/authLogin")
	@ResponseBody
	public Map<String, Object> authLogin(String username, String password, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			JSONObject bodyParamJO=new JSONObject();
			//bodyParamJO.put("username", "weifang_report_data");
			bodyParamJO.put("username", username);
			//bodyParamJO.put("password", "Um5oNWFqSXdNakFo");
			bodyParamJO.put("password", password);
			resultJO = postBody(PUBLIC_URL,bodyParamJO,"/auth/login",request);
			String code=resultJO.get("code").toString();
			System.out.println("==="+code);
			String msg=resultJO.get("msg").toString();
			JSONObject data = resultJO.getJSONObject("data");
			resultMap.put("code", code);
			resultMap.put("msg", msg);
			resultMap.put("data", data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}

	@RequestMapping(value="/dataEmployeeInfo")
	@ResponseBody
	public Map<String, Object> dataEmployeeInfo(EmployeeInfo ei, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			JSONObject bodyParamJO=new JSONObject();
			bodyParamJO.put("systemName", "信息化建设及人员定位系统");
			bodyParamJO.put("areaCode", "371710271");
			bodyParamJO.put("dataId", "91371700MA3P1UEYX9");
			
			JSONArray dataParamJA=new JSONArray();
			JSONObject dataParamJO=new JSONObject();
			dataParamJO.put("id", "1");
			dataParamJO.put("post_id", "1");
			dataParamJO.put("post_name", "员工");
			dataParamJO.put("depart_id", "1");
			dataParamJO.put("depart_name", "一车间");
			dataParamJO.put("name", "李铁玉");
			dataParamJO.put("sex", "0");
			dataParamJO.put("card_no", "001");
			dataParamJO.put("company_social_code", "91371700MA3P1UEYX9");
			dataParamJO.put("employee_type", "01");
			dataParamJA.put(dataParamJO);
			bodyParamJO.put("data", dataParamJA);
			
			resultJO = postBody(PUBLIC_URL,bodyParamJO,"/data/employee/info",request);
			String code=resultJO.get("code").toString();
			System.out.println("==="+code);
			String msg=resultJO.get("msg").toString();
			JSONObject data = resultJO.getJSONObject("data");
			resultMap.put("code", code);
			resultMap.put("msg", msg);
			resultMap.put("data", data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
	
	public JSONObject postBody(String serverURL, JSONObject bodyParamJO, String path, HttpServletRequest request)
			throws IOException {
		StringBuffer sbf = new StringBuffer(); 
		String strRead = null; 
		URL url = new URL(serverURL+path); 
		HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
		
		//connection.setInstanceFollowRedirects(false); 
		
		HttpSession session = request.getSession();
		connection.setRequestProperty("Authorization", "Bearer HOMP+MNAv68ngENJH8xVE7sgPPfVbLiKn300Ic9mPnI=");
		if(serverURL.contains("service")) {
			//connection.setRequestProperty("Cookie", "JSESSIONID=849CB322A20324C2F7E11AD0A7A9899E;Path=/position; Domain=139.196.143.225; HttpOnly;");
			//connection.setRequestProperty("Cookie", "JSESSIONID=E1CD97E8E9AA306810805BFF21D7FD7D; Path=/position; HttpOnly");
			String cookie = null;
			Object LoginUserObj = session.getAttribute("loginUser");
			//System.out.println("LoginUserObj==="+LoginUserObj);
			if(LoginUserObj!=null) {
				//LoginUser loginUser = (LoginUser)LoginUserObj;
				//cookie = loginUser.getCookie();
			}
			
			//if(cookie==null)
				//cookie = loginUserService.getCookieByUserId(TEST_USER_Id);
				
			if(!StringUtils.isEmpty(cookie))
				connection.setRequestProperty("Cookie", cookie);
		}
		connection.setRequestMethod("POST");//请求post方式
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
		
		if(serverURL.contains("public")&&"login".equals(path)) {
			//if(!checkCookieInSession(session)) {
				//getCookieFromHeader(connection,session);
			//}
		}
		
		connection.disconnect();
		String result = sbf.toString();
		//System.out.println("result==="+result);
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
		}
		return resultJO;
	}
}
