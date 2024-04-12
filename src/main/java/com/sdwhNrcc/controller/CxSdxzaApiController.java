package com.sdwhNrcc.controller;

import java.io.BufferedReader;
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

import com.sdwhNrcc.entity.sdwh.*;
import com.sdwhNrcc.service.sdwh.*;
import com.sdwhNrcc.util.*;

@Controller
@RequestMapping(CxSdxzaApiController.MODULE_NAME)
public class CxSdxzaApiController {

	private static final String ADDRESS_URL="https://"+Constant.SERVICE_IP_CX_SDXZA;
	public static final String MODULE_NAME="/cxSdxzaApi";

	@Autowired
	private LoginUserCxSdService loginUserCxSdService;
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
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
			resultJO = postBody(bodyParamJO,"/auth/login",request);
			String code=resultJO.get("code").toString();
			System.out.println("==="+code);
			String msg=resultJO.get("msg").toString();
			
			JSONObject data = resultJO.getJSONObject("data");
			String token = data.getString("token");
			LoginUserCxSd lucs=new LoginUserCxSd(token,username);
			loginUserCxSdService.add(lucs);
			
			HttpSession session = request.getSession();
			Object loginUserObj = session.getAttribute("loginUser"+username);
			if(loginUserObj!=null) {
				LoginUser loginUser = (LoginUser)loginUserObj;
				loginUser.setToken(token);
				loginUser.setUsername(username);
			}
			
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
	
	/**
	 * 根据企业系统标识选择账户、密码
	 * @param request
	 */
	public void switchLoginInfo(HttpServletRequest request) {
		String username=null;
		String password=null;
		//int systemFlag = Integer.valueOf(request.getAttribute("systemFlag").toString());
		int systemFlag = 1;
		switch (systemFlag) {
		case Constant.FU_LIN:
			username=Constant.USERNAME_FU_LIN_SDXZA;
			password=Constant.PASSWORD_FU_LIN_SDXZA;
			break;
		}
		request.setAttribute("username", username);
		request.setAttribute("password", password);
	}
	
	public JSONObject postBody(JSONObject bodyParamJO, String path, HttpServletRequest request) {
		JSONObject resultJO = null;
		try {
			switchLoginInfo(request);
			
			StringBuffer sbf = new StringBuffer(); 
			String strRead = null; 
			String serverUrl=ADDRESS_URL+path;
			
			System.out.println("serverUrl="+serverUrl);
			URL url = new URL(serverUrl); 
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			//connection.setInstanceFollowRedirects(false); 

			HttpSession session = request.getSession();
			if(!path.contains("auth/login")) {
				String username=request.getAttribute("username").toString();
				
				String token = null;
				Object LoginUserObj = session.getAttribute("loginUser"+username);
				//System.out.println("LoginUserObj==="+LoginUserObj);
				if(LoginUserObj!=null) {//先从session里获取登录用户信息的token
					LoginUser loginUser = (LoginUser)LoginUserObj;
					token = loginUser.getToken();
				}
				
				if(token==null) {//若session里没有用户，再从数据库里获取token(在重启服务token未过期情况下调用)
					token = loginUserCxSdService.getTokenByUsername(username);
				}

				System.out.println("token==="+token);
				if(!StringUtils.isEmpty(token))
					connection.setRequestProperty("Authorization", "Bearer "+token);
				//connection.setRequestProperty("Authorization", "Bearer UhSHfbnUBDxAlTnk7jFGovvxAGKG/XxETyGYhUgpxQ0=");
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
				if(!result.contains("操作频繁")) {
					resultJO = new JSONObject(result);
					resultJO.put("status", "ok");
					
					if(path.contains("auth/login")) {
						if(!checkTokenInSession(request)) {//若session里不存在该用户，需要把用户存到session里
							JSONObject dataJO = resultJO.getJSONObject("data");
							String token = dataJO.getString("token");
							String username=request.getAttribute("username").toString();
							LoginUser loginUser=new LoginUser();
							loginUser.setToken(token);
							loginUser.setUsername(username);
							session.setAttribute("loginUser"+username, loginUser);
						}
					}
				}
				else {//操作频繁，稍后再调用
					resultJO = new JSONObject();
					resultJO.put("status", "error");
					resultJO.put("data", result);
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
	
	/**
	 * 验证session里有无该登录用户
	 * @param request
	 * @return
	 */
	public boolean checkTokenInSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username=request.getAttribute("username").toString();
		Object loginUserObj = session.getAttribute("loginUser"+username);
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
