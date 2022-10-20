package com.sdwhNrcc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdwhNrcc.entity.*;
import com.sdwhNrcc.service.EntityService;
import com.sdwhNrcc.util.*;

@Controller
@RequestMapping(ApiController.MODULE_NAME)
public class ApiController {

	private static final String ADDRESS_URL="http://"+Constant.REALM_NAME+":"+Constant.PORT;
	public static final String MODULE_NAME="/api";
	public static final int CITY_FLAG=1;
	public static final int SYSTEM_FLAG=1;
	
	@Autowired
	private EntityService entityService;

	@RequestMapping(value="/goTestApi")
	public String goTestApi(HttpServletRequest request) {

		switchCity(CITY_FLAG,request);
		
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
			resultJO = postBody(ADDRESS_URL,bodyParamJO,"/auth/login",request);
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
	public Map<String, Object> dataEmployeeInfo(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			JSONObject bodyParamJO=switchSystem(SYSTEM_FLAG);
			
			JSONArray dataParamJA=new JSONArray();
			
			/*
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
			*/
			
			List<EmployeeInfo> eiList = convertEntityToEmployeeInfo();
			int eiListSize = eiList.size();
			for (int i = 0; i < eiListSize; i++) {
				EmployeeInfo ei=eiList.get(i);
				JSONObject dataParamJO=new JSONObject();
				dataParamJO.put("id", ei.getId());
				dataParamJO.put("post_id", ei.getPost_id());
				dataParamJO.put("post_name", ei.getPost_name());
				dataParamJO.put("depart_id", ei.getDepart_id());
				dataParamJO.put("depart_name", ei.getDepart_name());
				dataParamJO.put("name", ei.getName());
				dataParamJO.put("sex", ei.getSex());
				dataParamJO.put("card_no", ei.getCard_no());
				dataParamJO.put("company_social_code", ei.getCompany_social_code());
				dataParamJO.put("employee_type", ei.getEmployee_type());
				dataParamJA.put(dataParamJO);
			}
			
			bodyParamJO.put("data", dataParamJA);
			
			resultJO = postBody(ADDRESS_URL,bodyParamJO,"/data/employee/info",request);
			String code=resultJO.get("code").toString();
			System.out.println("code==="+code);
			String msg=resultJO.get("msg").toString();
			String data = resultJO.getString("data");
			resultMap.put("code", code);
			resultMap.put("msg", msg);
			resultMap.put("data", data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}

	@RequestMapping(value="/dataEmployeeLocations")
	@ResponseBody
	public Map<String, Object> dataEmployeeLocations(List<EmployeeLocation> elList, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			JSONObject bodyParamJO=switchSystem(SYSTEM_FLAG);
			
			JSONArray dataParamJA=new JSONArray();

			int elListSize = elList.size();
			for (int i = 0; i < elListSize; i++) {
				EmployeeLocation el=elList.get(i);
				JSONObject dataParamJO=new JSONObject();
				dataParamJO.put("company_social_code", el.getCompany_social_code());
				dataParamJO.put("floor_no", el.getFloor_no());
				dataParamJO.put("card_no", el.getCard_no());
				dataParamJO.put("time_stamp", el.getTime_stamp());
				dataParamJO.put("status", el.getStatus());
				dataParamJO.put("longitude", el.getLongitude());
				dataParamJO.put("latitude", el.getLatitude());
				dataParamJA.put(dataParamJO);
			}
			
			bodyParamJO.put("data", dataParamJA);
			
			resultJO = postBody(ADDRESS_URL,bodyParamJO,"/data/employee/locations",request);
			String code=resultJO.get("code").toString();
			System.out.println("code==="+code);
			String msg=resultJO.get("msg").toString();
			JSONArray dataJA = resultJO.getJSONArray("data");
			resultMap.put("code", code);
			resultMap.put("msg", msg);
			resultMap.put("data", dataJA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}

	@RequestMapping(value="/dataEmployeeAlarm")
	@ResponseBody
	public Map<String, Object> dataEmployeeAlarm(List<EmployeeAlarm> eaList, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			JSONObject bodyParamJO=switchSystem(SYSTEM_FLAG);
			
			JSONArray dataParamJA=new JSONArray();

			int eaListSize = eaList.size();
			for (int i = 0; i < eaListSize; i++) {
				EmployeeAlarm ea=eaList.get(i);
				JSONObject dataParamJO=new JSONObject();
				dataParamJO.put("id", ea.getId());
				dataParamJO.put("time", ea.getTime());
				dataParamJO.put("type", ea.getType());
				dataParamJO.put("area_name", ea.getArea_name());
				dataParamJO.put("name", ea.getName());
				dataParamJO.put("card_no", ea.getCard_no());
				dataParamJO.put("handle_time", ea.getHandle_time());
				dataParamJO.put("handle_remark", ea.getHandle_remark());
				dataParamJO.put("company_social_code", ea.getCompany_social_code());
				dataParamJO.put("longitude", ea.getLongitude());
				dataParamJO.put("latitude", ea.getLatitude());
				dataParamJA.put(dataParamJO);
			}
			
			bodyParamJO.put("data", dataParamJA);
			
			resultJO = postBody(ADDRESS_URL,bodyParamJO,"/data/employee/locations",request);
			String code=resultJO.get("code").toString();
			System.out.println("code==="+code);
			String msg=resultJO.get("msg").toString();
			JSONArray dataJA = resultJO.getJSONArray("data");
			resultMap.put("code", code);
			resultMap.put("msg", msg);
			resultMap.put("data", dataJA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
	
	public List<EmployeeInfo> convertEntityToEmployeeInfo() {
		List<EmployeeInfo> eiList=new ArrayList<EmployeeInfo>();
		List<Entity> entityList=entityService.queryEIList();
		for (Entity entity : entityList) {
			EmployeeInfo ei=new EmployeeInfo();
			ei.setId(entity.getId()+"");
			ei.setPost_id("");
			String post = entity.getPost();
			if(StringUtils.isEmpty(post))
				post="未知";
			ei.setPost_name(post);
			ei.setDepart_id(entity.getDepartmentId()+"");
			ei.setDepart_name("未知");
			ei.setName(entity.getName());
			ei.setSex((entity.getSex()==1?0:1)+"");
			ei.setCard_no(entity.getUserId());
			String companySocialCode=null;
			switch (SYSTEM_FLAG) {
			case Constant.WFRZJXHYXGS:
				companySocialCode=Constant.DATA_ID_WFRZJXHYXGS;
				break;
			}
			ei.setCompany_social_code(companySocialCode);
			ei.setEmployee_type(entity.getDutyName());
			eiList.add(ei);
		}
		return eiList;
	}
	
	public void switchCity(int cityFlag,HttpServletRequest request) {
		String username=null;
		String password=null;
		switch (cityFlag) {
		case Constant.WEI_FANG:
			username=Constant.USERNAME_WEI_FANG;
			password=Constant.PASSWORD_WEI_FANG;
			break;
		case Constant.HE_ZE:
			username=Constant.USERNAME_HE_ZE;
			password=Constant.PASSWORD_HE_ZE;
			break;
		}
		request.setAttribute("username", username);
		request.setAttribute("password", password);
	}
	
	public JSONObject switchSystem(int systemFlag) {
		JSONObject bodyParamJO=new JSONObject();
		String systemName=null;
		String areaCode=null;
		String dataId=null;
		switch (systemFlag) {
		case Constant.WFPXHGYXGS:
			systemName=Constant.SYSTEM_NAME_WFPXHGYXGS;
			areaCode=Constant.AREA_CODE_WFPXHGYXGS;
			dataId=Constant.DATA_ID_WFPXHGYXGS;
			break;
		case Constant.WFRZJXHYXGS:
			systemName=Constant.SYSTEM_NAME_WFRZJXHYXGS;
			areaCode=Constant.AREA_CODE_WFRZJXHYXGS;
			dataId=Constant.DATA_ID_WFRZJXHYXGS;
			break;
		case Constant.SDFLXCLKJYXGS:
			systemName=Constant.SYSTEM_NAME_SDFLXCLKJYXGS;
			areaCode=Constant.AREA_CODE_SDFLXCLKJYXGS;
			dataId=Constant.DATA_ID_SDFLXCLKJYXGS;
			break;
		case Constant.ZBXQHGYXGS:
			systemName=Constant.SYSTEM_NAME_ZBXQHGYXGS;
			areaCode=Constant.AREA_CODE_ZBXQHGYXGS;
			dataId=Constant.DATA_ID_ZBXQHGYXGS;
			break;
		case Constant.SDBFXCLYXGS:
			systemName=Constant.SYSTEM_NAME_SDBFXCLYXGS;
			areaCode=Constant.AREA_CODE_SDBFXCLYXGS;
			dataId=Constant.DATA_ID_SDBFXCLYXGS;
			break;
		case Constant.SDLTXDKJYXGS:
			systemName=Constant.SYSTEM_NAME_SDLTXDKJYXGS;
			areaCode=Constant.AREA_CODE_SDLTXDKJYXGS;
			dataId=Constant.DATA_ID_SDLTXDKJYXGS;
			break;
		}
		bodyParamJO.put("systemName", systemName);
		bodyParamJO.put("areaCode", areaCode);
		bodyParamJO.put("dataId", dataId);
		return bodyParamJO;
	}
	
	public JSONObject postBody(String serverURL, JSONObject bodyParamJO, String path, HttpServletRequest request)
			throws IOException {
		StringBuffer sbf = new StringBuffer(); 
		String strRead = null; 
		URL url = new URL(serverURL+path); 
		HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
		
		//connection.setInstanceFollowRedirects(false); 
		
		HttpSession session = request.getSession();
		if(!path.contains("auth/login")) {
			String token = null;
			Object LoginUserObj = session.getAttribute("loginUser");
			//System.out.println("LoginUserObj==="+LoginUserObj);
			if(LoginUserObj!=null) {
				LoginUser loginUser = (LoginUser)LoginUserObj;
				token = loginUser.getToken();
			}
			
			//if(cookie==null)
				//cookie = loginUserService.getCookieByUserId(TEST_USER_Id);
			
			System.out.println("token==="+token);
			if(!StringUtils.isEmpty(token))
				connection.setRequestProperty("Authorization", "Bearer "+token);
			connection.setRequestProperty("Authorization", "Bearer sFHFsfDyHJPziGobVa/XxXigCO4cjKw9mlmwjF4JEVQ=");
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
			
			if(path.contains("auth/login")) {
				if(!checkTokenInSession(session)) {
					JSONObject dataJO = resultJO.getJSONObject("data");
					String token = dataJO.getString("token");
					LoginUser loginUser=new LoginUser();
					loginUser.setToken(token);
					session.setAttribute("loginUser", loginUser);
				}
			}
		}
		return resultJO;
	}
	
	public boolean checkTokenInSession(HttpSession session) {
		Object loginUserObj = session.getAttribute("loginUser");
		if(loginUserObj==null)
			return false;
		else {
			LoginUser loginUser = (LoginUser)loginUserObj;
			if(loginUser==null)
				return false;
			else {
				String token = loginUser.getToken();
				if(StringUtils.isEmpty(token))
					return false;
				else
					return true;
			}
		}
	}
}
