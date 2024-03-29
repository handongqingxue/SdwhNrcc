package com.sdwhNrcc.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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

import com.sdwhNrcc.entity.sdwh.*;
import com.sdwhNrcc.entity.sdwh.ApiLog;
import com.sdwhNrcc.entity.udp.*;
import com.sdwhNrcc.entity.v1_3.*;
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.sdwh.*;
import com.sdwhNrcc.service.udp.*;
import com.sdwhNrcc.service.v1_3.*;
import com.sdwhNrcc.service.v3_1.*;
import com.sdwhNrcc.util.*;

@Controller
@RequestMapping(SdwhApiController.MODULE_NAME)
public class SdwhApiController {

	private static final String ADDRESS_URL="http://"+Constant.SERVICE_IP_SDWH+":"+Constant.SERVICE_PORT_SDWH;
	public static final String MODULE_NAME="/sdwhApi";
	
	@Autowired
	private EntityService entityService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private LocationUDPService locationUDPService;
	@Autowired
	private WarnRecordService warnRecordService;
	@Autowired
	private KeyWarningService keyWarningService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private ApiLogServiceSdwh apiLogService;
	private List<Map<String, Object>> tagPreLLList=new ArrayList<Map<String, Object>>();

	@RequestMapping(value="/goPage")
	public String goPage(HttpServletRequest request) {
		//https://blog.csdn.net/lxyoucan/article/details/124490682
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=testApi
		
		//省平台同步人员信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=syncDBRun
		//普鑫化工同步人员信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=pxhgSyncDBRun
		//福林新材料同步人员信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=flxclSyncDBRun
		//新家园新材料同步人员信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=xjyjxhxpSyncDBRun
		//宝沣新材料同步人员信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=bfxclSyncDBRun
		//润中精细化工同步人员信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=rzjxhSyncDBRun
		//瑞海生物同步人员信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=rhswSyncDBRun
		//蓝天消毒同步人员信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=ltxdSyncDBRun
		//鑫乾化工同步人员信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=xqhgSyncDBRun
		
		//省平台同步人员位置、报警信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=syncDBManager
		//普鑫化工同步人员位置、报警信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=pxhgSyncDBManager
		//福林新材料同步人员位置、报警信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=flxclSyncDBManager
		//新家园新材料同步人员位置、报警信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=xjyjxhxpSyncDBManager
		//宝沣新材料同步人员位置、报警信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=bfxclSyncDBManager
		//润中精细化工同步人员位置、报警信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=rzjxhSyncDBManager
		//瑞海生物同步人员位置、报警信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=rhswSyncDBManager
		//蓝天消毒同步人员位置、报警信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=ltxdSyncDBManager
		//鑫乾化工同步人员位置、报警信息页面
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=xqhgSyncDBManager
		String url = null;
		String page = request.getParameter("page");
		if("testApi".equals(page)){
			url="/testApi";
		}
		else if("syncDBRun".equals(page)){
			
			String cityFlag = request.getParameter("cityFlag");//获取城市标识
			String systemFlag = request.getParameter("systemFlag");//获取企业系统标识
			String epVersion = request.getParameter("epVersion");//获取企业接口版本标识
			String apiFlag = request.getParameter("apiFlag");//获取平台标识
			
			//将不同标识设置到request里去，以便后面从中获取调用
			request.setAttribute("cityFlag", cityFlag);
			request.setAttribute("systemFlag", systemFlag);
			request.setAttribute("epVersion", epVersion);
			request.setAttribute("apiFlag", apiFlag);
			
			request.setAttribute("version_1_3", Constant.VERSION_1_3);
			request.setAttribute("version_3_1", Constant.VERSION_3_1);
			
			request.setAttribute("sdwhFlag", Constant.SDWH);
			request.setAttribute("lzqFlag", Constant.LZQ);
			
			url="/syncDBRun";
		}
		else if("pxhgSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.WFPXHGYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("flxclSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.HE_ZE+"&systemFlag="+Constant.SDFLXCLKJYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("xjyjxhxpSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.SDXJYJXHXPYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("bfxclSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.TAI_AN+"&systemFlag="+Constant.SDBFXCLYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("rzjxhSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.WFRZJXHYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("rhswSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.CYSRHSWKJYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("ltxdSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.SDLTXDKJYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("xqhgSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.ZI_BO+"&systemFlag="+Constant.ZBXQHGYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("syncDBManager".equals(page)){
			
			String cityFlag = request.getParameter("cityFlag");
			String systemFlag = request.getParameter("systemFlag");
			String epVersion = request.getParameter("epVersion");
			String apiFlag = request.getParameter("apiFlag");
			
			request.setAttribute("cityFlag", cityFlag);
			request.setAttribute("systemFlag", systemFlag);
			request.setAttribute("wfrzjxhyxgs", Constant.WFRZJXHYXGS);//润中的定位信息每隔35s同步省平台一次
			request.setAttribute("sdxjyjxhxpyxgs", Constant.SDXJYJXHXPYXGS);
			request.setAttribute("epVersion", epVersion);
			request.setAttribute("apiFlag", apiFlag);
			
			request.setAttribute("version_1_3", Constant.VERSION_1_3);
			request.setAttribute("version_3_1", Constant.VERSION_3_1);
			
			request.setAttribute("sdwhFlag", Constant.SDWH);
			request.setAttribute("lzqFlag", Constant.LZQ);
			
			url="/syncDBManager";//不管企业接口和平台接口调用的是哪个版本，都往这个页面里跳转，在页面里做一下判断就行
		}
		else if("pxhgSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.WFPXHGYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("flxclSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.HE_ZE+"&systemFlag="+Constant.SDFLXCLKJYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("xjyjxhxpSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.SDXJYJXHXPYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("bfxclSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.TAI_AN+"&systemFlag="+Constant.SDBFXCLYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("rzjxhSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.WFRZJXHYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("rhswSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.CYSRHSWKJYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("ltxdSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.SDLTXDKJYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("xqhgSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.ZI_BO+"&systemFlag="+Constant.ZBXQHGYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		return url;
	}
	
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
			LoginUser lu=new LoginUser(token,username);
			loginUserService.add(lu);
			
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
	 * 重新登录
	 * @param request
	 * @return
	 */
	public boolean reAuthLogin(HttpServletRequest request) {
		String username = request.getAttribute("username").toString();
		String password = request.getAttribute("password").toString();
		System.out.println("username==="+username);
		System.out.println("password==="+password);
		Map<String, Object> resultMap = authLogin(username,password,request);
		String code = resultMap.get("code").toString();
		if("200".equals(code))
			return true;
		else
			return false;
	}

	/**
	 * 同步人员信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/dataEmployeeInfo")
	@ResponseBody
	public Map<String, Object> dataEmployeeInfo(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			setFlagInRequest(request);
			JSONObject resultJO = null;
			switchDatabase(request);
			
			JSONArray dataParamJA=new JSONArray();
			List<EmployeeInfo> eiList = null;
			
			
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

			String databaseName = request.getAttribute("databaseName").toString();
			int systemFlag = Integer.valueOf(request.getAttribute("systemFlag").toString());
			switch (systemFlag) {
			/*
			case Constant.WFRZJXHYXGS:
				eiList = convertEntityToEmployeeInfo(systemFlag,databaseName);
				break;
			*/
			case Constant.WFRZJXHYXGS:
			case Constant.WFPXHGYXGS:
			case Constant.SDFLXCLKJYXGS:
			case Constant.SDXJYJXHXPYXGS:
			case Constant.SDBFXCLYXGS:
			case Constant.CYSRHSWKJYXGS:
			case Constant.SDLTXDKJYXGS:
			case Constant.ZBXQHGYXGS:
				eiList = convertStaffToEmployeeInfo(systemFlag,databaseName);
				break;
			}
			
			if(eiList!=null) {
				int eiListSize = eiList.size();
				for (int i = 0; i < eiListSize; i++) {
					//if(i==1)
						//break;
					EmployeeInfo ei=eiList.get(i);
					JSONObject dataParamJO=new JSONObject();
					//dataParamJO.put("id", ei.getId());
					dataParamJO.put("id", UUID.randomUUID());//避免省平台那边出现主键冲突,每次上传人员信息id都不能相同
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
			
				if(eiListSize>0) {
					JSONObject bodyParamJO=switchSystem(systemFlag);
					bodyParamJO.put("data", dataParamJA);
					String bodyParamJOStr = bodyParamJO.toString();
					
					resultJO = postBody(bodyParamJO,"/data/employee/info",request);
					String status=resultJO.get("status").toString();
					if("ok".equals(status)) {
						String code=resultJO.get("code").toString();
						String msg=resultJO.get("msg").toString();
						String data = resultJO.getString("data");
						System.out.println("code==="+code);
						System.out.println("msg==="+msg);
						System.out.println("data==="+data);
						
						resultMap.put("code", code);
						resultMap.put("msg", msg);
						resultMap.put("data", data);

						addApiLog(createApiLogByParams("dataEmployeeInfo",bodyParamJOStr,status,code,msg,data,eiList.get(0).getCompany_social_code()));
					}
					else if("no".equals(status)) {//若登录信息失效，需重新登录
						boolean success=reAuthLogin(request);
						System.out.println("success==="+success);
						
						String code="-1";
						String msg="请求失败";
						String data="登录信息已过期，重新登录!";
						
						resultMap.put("code", code);
						resultMap.put("msg", msg);
						resultMap.put("data", data);
						
						addApiLog(createApiLogByParams("dataEmployeeInfo",bodyParamJOStr,status,code,msg,data,eiList.get(0).getCompany_social_code()));
					}
					else if("error".equals(status)) {//操作频繁
						String code="-1";
						String msg="请求失败";
						String data=resultJO.get("data").toString();
						
						resultMap.put("code", code);
						resultMap.put("msg", msg);
						resultMap.put("data", data);
						
						addApiLog(createApiLogByParams("dataEmployeeInfo",bodyParamJOStr,status,code,msg,data,eiList.get(0).getCompany_social_code()));
					}
				}
				else {
					String code="-1";
					String data="暂无人员信息可上传省平台";
					
					resultMap.put("code", code);
					resultMap.put("data", data);
					System.out.println(data);
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

	/**
	 * 同步人员位置
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/dataEmployeeLocations")
	@ResponseBody
	public Map<String, Object> dataEmployeeLocations(HttpServletRequest request) {
		System.out.println("dataEmployeeLocations...");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			setFlagInRequest(request);
			JSONObject resultJO = null;
			switchDatabase(request);
			
			JSONArray dataParamJA=new JSONArray();

			List<EmployeeLocation> elList = null;

			String databaseName = request.getAttribute("databaseName").toString();
			int systemFlag = Integer.valueOf(request.getAttribute("systemFlag").toString());
			switch (systemFlag) {
			/*
			case Constant.WFRZJXHYXGS:
				elList = convertLocationToEmployeeLocation(systemFlag,databaseName);
				break;
				*/
			case Constant.WFRZJXHYXGS:
			case Constant.WFPXHGYXGS:
			case Constant.SDFLXCLKJYXGS:
			case Constant.SDBFXCLYXGS:
			case Constant.CYSRHSWKJYXGS:
			case Constant.SDLTXDKJYXGS:
			case Constant.ZBXQHGYXGS:
				elList = convertPositionToEmployeeLocation(systemFlag,databaseName);
				break;
			case Constant.SDXJYJXHXPYXGS:
				elList = convertLocationUDPToEmployeeLocation(systemFlag);
				break;
			}
			
			if(elList!=null) {
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
				
				if(elListSize>0) {
					JSONObject bodyParamJO=switchSystem(systemFlag);
					bodyParamJO.put("data", dataParamJA);
					String bodyParamJOStr = bodyParamJO.toString();
					System.out.println("bodyParamJOStr==="+bodyParamJOStr);
				
					resultJO = postBody(bodyParamJO,"/data/employee/locations",request);
					String status=resultJO.get("status").toString();
					if("ok".equals(status)) {
						String code=resultJO.get("code").toString();
						System.out.println("code==="+code);
						String msg=resultJO.get("msg").toString();
						String data = resultJO.getString("data");
						System.out.println("data==="+data);
						
						resultMap.put("code", code);
						resultMap.put("msg", msg);
						resultMap.put("data", data);
						
						for (EmployeeLocation el : elList) {
							//addTagPreLLInList(Double.valueOf(el.getLongitude()), Double.valueOf(el.getLatitude()), el.getTagId());
						}

						addApiLog(createApiLogByParams("dataEmployeeLocations",bodyParamJOStr,status,code,msg,data,elList.get(0).getCompany_social_code()));
					}
					else if("no".equals(status)) {//若登录信息失效，需重新登录
						boolean success=reAuthLogin(request);
						System.out.println("success==="+success);
						
						String code="-1";
						String msg="请求失败";
						String data="登录信息已过期，重新登录!";
						
						resultMap.put("code", code);
						resultMap.put("msg", msg);
						resultMap.put("data", data);
						
						addApiLog(createApiLogByParams("dataEmployeeLocations",bodyParamJOStr,status,code,msg,data,elList.get(0).getCompany_social_code()));
					}
					else if("error".equals(status)) {//操作频繁
						String code="-1";
						String msg="请求失败";
						String data=resultJO.get("data").toString();
						
						resultMap.put("code", code);
						resultMap.put("msg", msg);
						resultMap.put("data", data);
						
						addApiLog(createApiLogByParams("dataEmployeeLocations",bodyParamJOStr,status,code,msg,data,elList.get(0).getCompany_social_code()));
					}
				}
				else {
					String code="-1";
					String data="暂无人员位置信息可上传省平台";
					
					resultMap.put("code", code);
					resultMap.put("data", data);
					System.out.println(data);
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

	/**
	 * 同步报警信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/dataEmployeeAlarm")
	@ResponseBody
	public Map<String, Object> dataEmployeeAlarm(HttpServletRequest request) {
		System.out.println("dataEmployeeAlarm...");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			setFlagInRequest(request);
			switchDatabase(request);
			JSONObject resultJO = null;
			
			JSONArray dataParamJA=new JSONArray();

			StringBuilder syncIdsSB=new StringBuilder();
			List<EmployeeAlarm> eaList = null;
			String databaseName = request.getAttribute("databaseName").toString();
			int systemFlag = Integer.valueOf(request.getAttribute("systemFlag").toString());
			switch (systemFlag) {
			/*
			case Constant.WFRZJXHYXGS://这是润中老版系统，往后不用了，改用新版的
				eaList = convertWarnRecordToEmployeeAlarm(systemFlag,databaseName);
				break;
				*/
			case Constant.WFRZJXHYXGS:
			case Constant.WFPXHGYXGS:
			case Constant.SDFLXCLKJYXGS:
			case Constant.SDBFXCLYXGS:
			case Constant.CYSRHSWKJYXGS:
			case Constant.SDLTXDKJYXGS:
			case Constant.ZBXQHGYXGS:
				eaList = convertKeyWarningToEmployeeAlarm(systemFlag,databaseName);
				break;
			}
			
			if(eaList!=null) {
				int eaListSize = eaList.size();
				for (int i = 0; i < eaListSize; i++) {
					//if(i==5)
						//break;
					EmployeeAlarm ea=eaList.get(i);
					JSONObject dataParamJO=new JSONObject();
					String id = ea.getId();
					syncIdsSB.append(",");
					syncIdsSB.append(id);
					dataParamJO.put("id", id);
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
	
				if(eaListSize>0) {
					JSONObject bodyParamJO=switchSystem(systemFlag);
					bodyParamJO.put("data", dataParamJA);
					String bodyParamJOStr = bodyParamJO.toString();
					
					resultJO = postBody(bodyParamJO,"/data/employee/alarm",request);
					String status=resultJO.get("status").toString();
					if("ok".equals(status)) {
						String code=resultJO.get("code").toString();
						System.out.println("code==="+code);
						String msg=resultJO.get("msg").toString();
						String data = resultJO.getString("data");
						System.out.println("data==="+data);
						
						if("200".equals(code)) {
							String syncIds = syncIdsSB.toString().substring(1);
							System.out.println("syncIds==="+syncIds);
							switch (systemFlag) {//根据系统标识判断是新版还是旧版系统，更改报警记录里的同步标识为已同步
							case Constant.WFRZJXHYXGS:
								warnRecordService.syncByIds(syncIds,databaseName);
								break;
							case Constant.WFPXHGYXGS:
							case Constant.SDFLXCLKJYXGS:
							case Constant.SDBFXCLYXGS:
								keyWarningService.syncByIds(syncIds,databaseName);
								break;
							}
						}
						
						resultMap.put("code", code);
						resultMap.put("msg", msg);
						resultMap.put("data", data);

						addApiLog(createApiLogByParams("dataEmployeeAlarm",bodyParamJOStr,status,code,msg,data,eaList.get(0).getCompany_social_code()));
					}
					else if("no".equals(status)) {//若登录信息失效，需重新登录
						boolean success=reAuthLogin(request);
						System.out.println("success==="+success);

						String code="-1";
						String msg="请求失败";
						String data="登录信息已过期，重新登录!";
						
						addApiLog(createApiLogByParams("dataEmployeeAlarm",bodyParamJOStr,status,code,msg,data,eaList.get(0).getCompany_social_code()));
					}
					else if("error".equals(status)) {//操作频繁
						String code="-1";
						String msg="请求失败";
						String data=resultJO.get("data").toString();
						
						resultMap.put("code", code);
						resultMap.put("msg", msg);
						resultMap.put("data", data);
						
						addApiLog(createApiLogByParams("dataEmployeeAlarm",bodyParamJOStr,status,code,msg,data,eaList.get(0).getCompany_social_code()));
					}
				}
				else {
					String code="-1";
					String data="暂无报警信息可上传省平台";
					
					resultMap.put("code", code);
					resultMap.put("data", data);
					
					System.out.println(data);
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
	
	/**
	 * 把v1.3接口获取的人员信息转换为省平台的人员信息
	 * @param systemFlag
	 * @param databaseName
	 * @return
	 */
	public List<EmployeeInfo> convertEntityToEmployeeInfo(int systemFlag,String databaseName) {
		List<EmployeeInfo> eiList=new ArrayList<EmployeeInfo>();
		List<Entity> entityList=entityService.queryEIList(databaseName);
		for (Entity entity : entityList) {
			EmployeeInfo ei=new EmployeeInfo();
			ei.setId(entity.getId()+"");
			ei.setPost_id(entity.getDutyId()+"");
			ei.setPost_name(entity.getDutyName());
			ei.setDepart_id(entity.getDepartmentId()+"");
			ei.setDepart_name("未知");
			ei.setName(entity.getName());
			ei.setSex((entity.getSex()==1?0:1)+"");
			ei.setCard_no(entity.getUserId());
			String companySocialCode=null;
			switch (systemFlag) {
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
	
	/**
	 * 把v3.1接口获取的人员信息转换为省平台的人员信息
	 * @param systemFlag
	 * @param databaseName
	 * @return
	 */
	public List<EmployeeInfo> convertStaffToEmployeeInfo(int systemFlag,String databaseName) {
		List<EmployeeInfo> eiList=new ArrayList<EmployeeInfo>();
		List<Staff> staffList=staffService.queryEIList(databaseName);
		for (Staff staff : staffList) {
			EmployeeInfo ei=new EmployeeInfo();
			ei.setId(staff.getId()+"");
			ei.setPost_id("");
			String post = staff.getPost();
			if(StringUtils.isEmpty(post))
				post="未知";
			ei.setPost_name(post);
			ei.setDepart_id(staff.getDeptId()+"");
			ei.setDepart_name("未知");
			ei.setName(staff.getName());
			Integer sex = staff.getSex();
			if(sex==null)
				sex=1;
			ei.setSex((sex==1?0:1)+"");
			ei.setCard_no(staff.getJobNumber());
			String companySocialCode=null;
			switch (systemFlag) {
			case Constant.WFPXHGYXGS:
				companySocialCode=Constant.DATA_ID_WFPXHGYXGS;
				break;
			case Constant.SDFLXCLKJYXGS:
				companySocialCode=Constant.DATA_ID_SDFLXCLKJYXGS;
				break;
			case Constant.SDXJYJXHXPYXGS:
				companySocialCode=Constant.DATA_ID_SDXJYJXHXPYXGS;
				break;
			case Constant.SDBFXCLYXGS:
				companySocialCode=Constant.DATA_ID_SDBFXCLYXGS;
				break;
			case Constant.CYSRHSWKJYXGS:
				companySocialCode=Constant.DATA_ID_CYSRHSWKJYXGS;
				break;
			case Constant.SDLTXDKJYXGS:
				companySocialCode=Constant.DATA_ID_SDLTXDKJYXGS;
				break;
			case Constant.ZBXQHGYXGS:
				companySocialCode=Constant.DATA_ID_ZBXQHGYXGS;
				break;
			}
			ei.setCompany_social_code(companySocialCode);
			String employeeType=null;
			int type=staff.getType();
			switch (type) {
			case Staff.NEI_BU_YUAN_GONG:
				employeeType="内部员工";
				break;
			}
			ei.setEmployee_type(employeeType);
			eiList.add(ei);
		}
		return eiList;
	}
	
	public List<EmployeeLocation> convertLocationUDPToEmployeeLocation(int systemFlag) {
		List<EmployeeLocation> elList=new ArrayList<EmployeeLocation>();
		List<LocationUDP> locationUDPList=locationUDPService.queryELList();
		Date date = new Date();
		for (LocationUDP locationUDP : locationUDPList) {
			EmployeeLocation el=new EmployeeLocation();
			String companySocialCode=null;
			switch (systemFlag) {
			case Constant.SDXJYJXHXPYXGS:
				companySocialCode=Constant.DATA_ID_SDXJYJXHXPYXGS;
				break;
			}
			el.setCompany_social_code(companySocialCode);
			el.setFloor_no(locationUDP.getFloor()+"");
			el.setCard_no(locationUDP.getJobNumber());
			el.setTime_stamp(DateUtil.getDate(date,null));
			
			Double speed = locationUDP.getSpeed();
			String status=null;
			if(speed==0)
				status=EmployeeLocation.SLEEP;
			else
				status=EmployeeLocation.SPORT;
			el.setStatus(status);
			
			el.setLongitude(locationUDP.getLongitude()+"");
			el.setLatitude(locationUDP.getLatitude()+"");
			elList.add(el);
		}
		return elList;
	}
	
	/**
	 * 把v1.3接口获取的人员位置信息转换为省平台的人员位置信息
	 * @param systemFlag
	 * @param databaseName
	 * @return
	 */
	public List<EmployeeLocation> convertLocationToEmployeeLocation(int systemFlag,String databaseName) {
		List<EmployeeLocation> elList=new ArrayList<EmployeeLocation>();
		List<Location> locationList=locationService.queryELList(databaseName);
		Date date = new Date();
		for (Location location : locationList) {
			EmployeeLocation el=new EmployeeLocation();
			String companySocialCode=null;
			switch (systemFlag) {
			case Constant.WFRZJXHYXGS:
				companySocialCode=Constant.DATA_ID_WFRZJXHYXGS;
				break;
			}
			el.setCompany_social_code(companySocialCode);
			el.setFloor_no(location.getFloor()+"");
			el.setCard_no(location.getUserId());
			el.setTime_stamp(DateUtil.getDate(date,null));
			
			Float speed = location.getSpeed();
			String status=null;
			if(speed==0)
				status=EmployeeLocation.SLEEP;
			else
				status=EmployeeLocation.SPORT;
			el.setStatus(status);
			
			el.setLongitude(location.getLongitude()+"");
			el.setLatitude(location.getLatitude()+"");
			elList.add(el);
		}
		return elList;
	}
	
	/**
	 * 把v3.1接口获取的人员位置信息转换为省平台的人员位置信息
	 * @param systemFlag
	 * @param databaseName
	 * @return
	 */
	public List<EmployeeLocation> convertPositionToEmployeeLocation(int systemFlag,String databaseName) {
		List<EmployeeLocation> elList=new ArrayList<EmployeeLocation>();
		List<Position> unCompPosList=positionService.queryELList(databaseName);
		//List<Position> positionList=compareWithTagPreLL(unCompPosList);
		List<Position> positionList=unCompPosList;
		System.out.println("positionListSize="+positionList.size());
		
		Date date = new Date();
		for (Position position : positionList) {
			EmployeeLocation el=new EmployeeLocation();
			String companySocialCode=null;
			switch (systemFlag) {
			case Constant.WFRZJXHYXGS:
				companySocialCode=Constant.DATA_ID_WFRZJXHYXGS;
				break;
			case Constant.WFPXHGYXGS:
				companySocialCode=Constant.DATA_ID_WFPXHGYXGS;
				break;
			case Constant.SDFLXCLKJYXGS:
				companySocialCode=Constant.DATA_ID_SDFLXCLKJYXGS;
				break;
			case Constant.SDBFXCLYXGS:
				companySocialCode=Constant.DATA_ID_SDBFXCLYXGS;
				break;
			case Constant.CYSRHSWKJYXGS:
				companySocialCode=Constant.DATA_ID_CYSRHSWKJYXGS;
				break;
			case Constant.SDLTXDKJYXGS:
				companySocialCode=Constant.DATA_ID_SDLTXDKJYXGS;
				break;
			case Constant.ZBXQHGYXGS:
				companySocialCode=Constant.DATA_ID_ZBXQHGYXGS;
				break;
			}
			el.setCompany_social_code(companySocialCode);
			el.setFloor_no(position.getFloor()+"");
			el.setCard_no(position.getJobNumber());
			el.setTime_stamp(DateUtil.getDate(date,null));
			
			Float speed = position.getSpeed();
			String status=null;
			if(speed!=null) {
				if(speed==0)
					status=EmployeeLocation.SLEEP;
				else
					status=EmployeeLocation.SPORT;
			}
			else {
				status=EmployeeLocation.SLEEP;
			}
			el.setStatus(status);
			
			double longitude = position.getLongitude();
			double latitude = position.getLatitude();
			el.setLongitude(longitude+"");
			el.setLatitude(latitude+"");
			el.setTagId(position.getTagId());
			
			elList.add(el);
		}
		return elList;
	}
	
	/**
	 * 把v1.3接口获取的报警信息转换为省平台的报警信息
	 * @param systemFlag
	 * @param databaseName
	 * @return
	 */
	public List<EmployeeAlarm> convertWarnRecordToEmployeeAlarm(int systemFlag,String databaseName) {
		List<EmployeeAlarm> eaList=new ArrayList<EmployeeAlarm>();
		List<WarnRecord> wrList=warnRecordService.queryEAList(WarnRecord.UNSYNC,databaseName);
		for (WarnRecord wr : wrList) {
			EmployeeAlarm ea=new EmployeeAlarm();
			ea.setId(wr.getId()+"");
			ea.setTime(DateUtil.convertLongToString(wr.getStartTime()));
			ea.setType("oneKeyAlarm:alarm");
			ea.setArea_name("map");
			ea.setName(wr.getEntityName());
			ea.setCard_no(wr.getUserId());
			
			String companySocialCode=null;
			switch (systemFlag) {
			case Constant.WFRZJXHYXGS:
				companySocialCode=Constant.DATA_ID_WFRZJXHYXGS;
				break;
			}
			ea.setCompany_social_code(companySocialCode);
			
			ea.setLongitude(wr.getX()+"");
			ea.setLatitude(wr.getY()+"");
			eaList.add(ea);
		}
		return eaList;
	}
	
	/**
	 * 把v3.1接口获取的报警信息转换为省平台的报警信息
	 * @param systemFlag
	 * @param databaseName
	 * @return
	 */
	public List<EmployeeAlarm> convertKeyWarningToEmployeeAlarm(int systemFlag,String databaseName) {
		List<EmployeeAlarm> eaList=new ArrayList<EmployeeAlarm>();
		List<KeyWarning> kwList=keyWarningService.queryEAList(WarnRecord.UNSYNC,databaseName);
		for (KeyWarning kw : kwList) {
			EmployeeAlarm ea=new EmployeeAlarm();
			ea.setId(kw.getId()+"");
			ea.setTime(kw.getRaiseTimeYMD());
			ea.setType("oneKeyAlarm:alarm");
			ea.setArea_name("map");
			ea.setName(kw.getEntityName());
			ea.setCard_no(kw.getJobNumber());
			
			String companySocialCode=null;
			switch (systemFlag) {
			case Constant.WFRZJXHYXGS://润中定位系统也用新版了
				companySocialCode=Constant.DATA_ID_WFRZJXHYXGS;
				break;
			case Constant.WFPXHGYXGS:
				companySocialCode=Constant.DATA_ID_WFPXHGYXGS;
				break;
			case Constant.SDFLXCLKJYXGS:
				companySocialCode=Constant.DATA_ID_SDFLXCLKJYXGS;
				break;
			case Constant.SDBFXCLYXGS:
				companySocialCode=Constant.DATA_ID_SDBFXCLYXGS;
				break;
			case Constant.CYSRHSWKJYXGS:
				companySocialCode=Constant.DATA_ID_CYSRHSWKJYXGS;
				break;
			case Constant.SDLTXDKJYXGS:
				companySocialCode=Constant.DATA_ID_SDLTXDKJYXGS;
				break;
			}
			ea.setCompany_social_code(companySocialCode);
			
			ea.setLongitude(kw.getX()+"");
			ea.setLatitude(kw.getY()+"");
			eaList.add(ea);
		}
		return eaList;
	}
	
	/**
	 * 获取各个标识存到request里，以便后面调用
	 * @param request
	 */
	public void setFlagInRequest(HttpServletRequest request) {
		int cityFlag = Integer.valueOf(request.getParameter("cityFlag"));
		int systemFlag = Integer.valueOf(request.getParameter("systemFlag"));
		
		System.out.println("cityFlag==="+cityFlag);
		System.out.println("systemFlag==="+systemFlag);
		
		request.setAttribute("cityFlag", cityFlag);
		request.setAttribute("systemFlag", systemFlag);
	}
	
	/**
	 * 根据城市标识选择账户、密码
	 * @param request
	 */
	public void switchCity(HttpServletRequest request) {
		String username=null;
		String password=null;
		int cityFlag = Integer.valueOf(request.getAttribute("cityFlag").toString());
		switch (cityFlag) {
		case Constant.WEI_FANG:
			username=Constant.USERNAME_WEI_FANG;
			password=Constant.PASSWORD_WEI_FANG;
			break;
		case Constant.HE_ZE:
			username=Constant.USERNAME_HE_ZE;
			password=Constant.PASSWORD_HE_ZE;
			break;
		case Constant.TAI_AN:
			username=Constant.USERNAME_TAI_AN;
			password=Constant.PASSWORD_TAI_AN;
			break;
		case Constant.ZI_BO:
			username=Constant.USERNAME_ZI_BO;
			password=Constant.PASSWORD_ZI_BO;
			break;
		}
		request.setAttribute("username", username);
		request.setAttribute("password", password);
	}
	
	/**
	 * 根据企业标识选择接口所需的企业信息
	 * @param systemFlag
	 * @return
	 */
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
		case Constant.SDXJYJXHXPYXGS:
			systemName=Constant.SYSTEM_NAME_SDXJYJXHXPYXGS;
			areaCode=Constant.AREA_CODE_SDXJYJXHXPYXGS;
			dataId=Constant.DATA_ID_SDXJYJXHXPYXGS;
			break;
		case Constant.SDBFXCLYXGS:
			systemName=Constant.SYSTEM_NAME_SDBFXCLYXGS;
			areaCode=Constant.AREA_CODE_SDBFXCLYXGS;
			dataId=Constant.DATA_ID_SDBFXCLYXGS;
			break;
		case Constant.CYSRHSWKJYXGS:
			systemName=Constant.SYSTEM_NAME_CYSRHSWKJYXGS;
			areaCode=Constant.AREA_CODE_CYSRHSWKJYXGS;
			dataId=Constant.DATA_ID_CYSRHSWKJYXGS;
			break;
		case Constant.SDLTXDKJYXGS:
			systemName=Constant.SYSTEM_NAME_SDLTXDKJYXGS;
			areaCode=Constant.AREA_CODE_SDLTXDKJYXGS;
			dataId=Constant.DATA_ID_SDLTXDKJYXGS;
			break;
		case Constant.ZBXQHGYXGS:
			systemName=Constant.SYSTEM_NAME_ZBXQHGYXGS;
			areaCode=Constant.AREA_CODE_ZBXQHGYXGS;
			dataId=Constant.DATA_ID_ZBXQHGYXGS;
			break;
		}
		bodyParamJO.put("systemName", systemName);
		bodyParamJO.put("areaCode", areaCode);
		bodyParamJO.put("dataId", dataId);
		return bodyParamJO;
	}
	
	/**
	 * 根据企业标识选择数据库
	 * @param request
	 */
	public void switchDatabase(HttpServletRequest request) {
		String databaseName=null;
		int systemFlag = Integer.valueOf(request.getAttribute("systemFlag").toString());
		switch (systemFlag) {
		case Constant.WFPXHGYXGS:
			databaseName=Constant.DATABASE_NAME_WFPXHGYXGS;
			break;
		case Constant.SDFLXCLKJYXGS:
			databaseName=Constant.DATABASE_NAME_SDFLXCLKJYXGS;
			break;
		case Constant.SDXJYJXHXPYXGS:
			databaseName=Constant.DATABASE_NAME_SDXJYJXHXPYXGS;
			break;
		case Constant.SDBFXCLYXGS:
			databaseName=Constant.DATABASE_NAME_SDBFXCLYXGS;
			break;
		case Constant.WFRZJXHYXGS:
			databaseName=Constant.DATABASE_NAME_WFRZJXHYXGS;
			break;
		case Constant.CYSRHSWKJYXGS:
			databaseName=Constant.DATABASE_NAME_CYSRHSWKJYXGS;
			break;
		case Constant.SDLTXDKJYXGS:
			databaseName=Constant.DATABASE_NAME_SDLTXDKJYXGS;
			break;
		case Constant.ZBXQHGYXGS:
			databaseName=Constant.DATABASE_NAME_ZBXQHGYXGS;
			break;
		}
		request.setAttribute("databaseName", databaseName);
	}
	
	/**
	 * 添加日志记录
	 * @param al
	 * @return
	 */
	@RequestMapping(value="/addApiLog")
	@ResponseBody
	public Map<String, Object> addApiLog(ApiLog al) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=apiLogService.add(al);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加日志记录成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加日志记录失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonMap.put("message", "no");
			jsonMap.put("info", "添加日志记录失败");
		}
		finally {
			return jsonMap;
		}
	}
	
	/**
	 * 根据参数创建日志记录对象
	 * @param action
	 * @param body
	 * @param status
	 * @param code
	 * @param msg
	 * @param data
	 * @param company_social_code
	 * @return
	 */
	public ApiLog createApiLogByParams(String action,String body,String status,String code,String msg,String data,String company_social_code){
		
		ApiLog apiLog=new ApiLog();
		apiLog.setAction(action);
		apiLog.setBody(body);
		apiLog.setStatus(status);
		apiLog.setCode(code);
		apiLog.setMsg(msg);
		apiLog.setData(data);
		apiLog.setCompany_social_code(company_social_code);
		
		return apiLog;
	}
	
	/**
	 * 添加上次标签经纬度到集合里
	 * @param longitude
	 * @param latitude
	 * @param tagId
	 */
	private void addTagPreLLInList(double longitude, double latitude, String tagId) {
		//System.out.println("addTagPreLLInList...."+tagId);
		boolean exist=checkTagIfExistInPreLLList(tagId);
		//System.out.println("exist==="+exist);
		if(exist) {
			for (Map<String, Object> tagPreLLMap : tagPreLLList) {
				String preTagId = tagPreLLMap.get("tagId").toString();
				if(tagId.equals(preTagId)) {
					//System.out.println("longitude="+longitude+",latitude="+latitude);
					tagPreLLMap.put("longitude", longitude);
					tagPreLLMap.put("latitude", latitude);
				}
			}
		}
		else {
			Map<String,Object> preLLMap=new HashMap<>();
			preLLMap.put("tagId", tagId);
			preLLMap.put("longitude", longitude);
			preLLMap.put("latitude", latitude);
			
			tagPreLLList.add(preLLMap);
		}
	}
	
	/**
	 * 与标签上一个经纬度做对比
	 * @param unCompPosList
	 * @return
	 */
	private List<Position> compareWithTagPreLL(List<Position> unCompPosList) {
		List<Position> positionList=new ArrayList<Position>();
		for (Position unCompPos : unCompPosList) {
			String staffName = unCompPos.getStaffName();
			String tagId = unCompPos.getTagId();
			double longitude = unCompPos.getLongitude();
			double latitude = unCompPos.getLatitude();
			String preLL = getTagPreLLByTagId(tagId);
			String[] preLLArr = preLL.split(",");
			double preLongitude = Double.valueOf(preLLArr[0]);
			double preLatitude = Double.valueOf(preLLArr[1]);
			boolean changed=longitude!=preLongitude||latitude!=preLatitude;
			if(changed) {
				System.out.println("staffName="+staffName+",tagId="+tagId+",changed="+changed+",longitude="+longitude+",preLongitude="+preLongitude+",latitude="+latitude+",preLatitude="+preLatitude);
				positionList.add(unCompPos);
			}
		}
		return positionList;
	}
	
	/**
	 * 根据标签id获取标签上一次经纬度
	 * @param tagId
	 * @return
	 */
	private String getTagPreLLByTagId(String tagId) {
		double longitude = 0;
		double latitude = 0;
		for (int i = 0; i < tagPreLLList.size(); i++) {
			Map<String, Object> tagPreLLMap = tagPreLLList.get(i);
			String preTagId = tagPreLLMap.get("tagId").toString();
			double preLongitude = Double.valueOf(tagPreLLMap.get("longitude").toString());
			double preLatitude = Double.valueOf(tagPreLLMap.get("latitude").toString());
			if(tagId.equals(preTagId)) {
				longitude=preLongitude;
				latitude=preLatitude;
				break;
			}
		}
		//System.out.println("preLongitude="+longitude+",preLatitude="+latitude);
		return longitude+","+latitude;
	}
	
	/**
	 * 检查标签是否存在于标签上一次经纬度的集合里
	 * @param tagId
	 * @return
	 */
	private boolean checkTagIfExistInPreLLList(String tagId) {
		boolean exist=false;
		for (Map<String, Object> tagPreLLMap : tagPreLLList) {
			Object tagIdObj = tagPreLLMap.get("tagId");
			if(tagIdObj!=null) {
				String preTagId = tagIdObj.toString();
				if(tagId.equals(preTagId)) {
					exist=true;
					break;
				}
			}
		}
		return exist;
	}
	
	public JSONObject postBody(JSONObject bodyParamJO, String path, HttpServletRequest request) {
		JSONObject resultJO = null;
		try {
			switchCity(request);
			
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
					token = loginUserService.getTokenByUsername(username);
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
