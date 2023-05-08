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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.sdwhNrcc.entity.v1_3.*;
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.sdwh.*;
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
	private WarnRecordService warnRecordService;
	@Autowired
	private KeyWarningService keyWarningService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private LoginUserService loginUserService;

	@RequestMapping(value="/goPage")
	public String goPage(HttpServletRequest request) {
		//https://blog.csdn.net/lxyoucan/article/details/124490682
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=testApi
		
		//ʡƽ̨ͬ����Ա��Ϣҳ��
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=syncDBRun
		//���λ���ͬ����Ա��Ϣҳ��
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=pxhgSyncDBRun
		//�����²���ͬ����Ա��Ϣҳ��
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=flxclSyncDBRun
		//�����²���ͬ����Ա��Ϣҳ��
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=bfxclSyncDBRun
		//���о�ϸ����ͬ����Ա��Ϣҳ��
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=rzjxhSyncDBRun
		
		//ʡƽ̨ͬ����Աλ�á�������Ϣҳ��
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=syncDBManager
		//���λ���ͬ����Աλ�á�������Ϣҳ��
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=pxhgSyncDBManager
		//�����²���ͬ����Աλ�á�������Ϣҳ��
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=flxclSyncDBManager
		//�����²���ͬ����Աλ�á�������Ϣҳ��
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=bfxclSyncDBManager
		//���о�ϸ����ͬ����Աλ�á�������Ϣҳ��
		//http://localhost:8080/SdwhNrcc/sdwhApi/goPage?page=rzjxhSyncDBManager
		String url = null;
		String page = request.getParameter("page");
		if("testApi".equals(page)){
			url="/testApi";
		}
		else if("syncDBRun".equals(page)){
			
			String cityFlag = request.getParameter("cityFlag");//��ȡ���б�ʶ
			String systemFlag = request.getParameter("systemFlag");//��ȡ��ҵϵͳ��ʶ
			String epVersion = request.getParameter("epVersion");//��ȡ��ҵ�ӿڰ汾��ʶ
			String apiFlag = request.getParameter("apiFlag");//��ȡƽ̨��ʶ
			
			//����ͬ��ʶ���õ�request��ȥ���Ա������л�ȡ����
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
		else if("bfxclSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.TAI_AN+"&systemFlag="+Constant.SDBFXCLYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("rzjxhSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.WFRZJXHYXGS+"&epVersion="+Constant.VERSION_1_3+"&apiFlag="+Constant.SDWH;
		}
		else if("syncDBManager".equals(page)){
			
			String cityFlag = request.getParameter("cityFlag");
			String systemFlag = request.getParameter("systemFlag");
			String epVersion = request.getParameter("epVersion");
			String apiFlag = request.getParameter("apiFlag");
			
			request.setAttribute("cityFlag", cityFlag);
			request.setAttribute("systemFlag", systemFlag);
			request.setAttribute("epVersion", epVersion);
			request.setAttribute("apiFlag", apiFlag);
			
			request.setAttribute("version_1_3", Constant.VERSION_1_3);
			request.setAttribute("version_3_1", Constant.VERSION_3_1);
			
			request.setAttribute("sdwhFlag", Constant.SDWH);
			request.setAttribute("lzqFlag", Constant.LZQ);
			
			url="/syncDBManager";//������ҵ�ӿں�ƽ̨�ӿڵ��õ����ĸ��汾���������ҳ������ת����ҳ������һ���жϾ���
		}
		else if("pxhgSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.WFPXHGYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("flxclSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.HE_ZE+"&systemFlag="+Constant.SDFLXCLKJYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("bfxclSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.TAI_AN+"&systemFlag="+Constant.SDBFXCLYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.SDWH;
		}
		else if("rzjxhSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.WEI_FANG+"&systemFlag="+Constant.WFRZJXHYXGS+"&epVersion="+Constant.VERSION_1_3+"&apiFlag="+Constant.SDWH;
		}
		return url;
	}
	
	/**
	 * ��¼
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
	 * ���µ�¼
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
	 * ͬ����Ա��Ϣ
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
			dataParamJO.put("post_name", "Ա��");
			dataParamJO.put("depart_id", "1");
			dataParamJO.put("depart_name", "һ����");
			dataParamJO.put("name", "������");
			dataParamJO.put("sex", "0");
			dataParamJO.put("card_no", "001");
			dataParamJO.put("company_social_code", "91371700MA3P1UEYX9");
			dataParamJO.put("employee_type", "01");
			dataParamJA.put(dataParamJO);
			*/

			String databaseName = request.getAttribute("databaseName").toString();
			int systemFlag = Integer.valueOf(request.getAttribute("systemFlag").toString());
			switch (systemFlag) {
			case Constant.WFRZJXHYXGS:
				eiList = convertEntityToEmployeeInfo(systemFlag,databaseName);
				break;
			case Constant.WFPXHGYXGS:
			case Constant.SDFLXCLKJYXGS:
			case Constant.SDBFXCLYXGS:
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
			
				if(eiListSize>0) {
					JSONObject bodyParamJO=switchSystem(systemFlag);
					bodyParamJO.put("data", dataParamJA);
				
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
					}
					else if("no".equals(status)) {//����¼��ϢʧЧ�������µ�¼
						boolean success=reAuthLogin(request);
						System.out.println("success==="+success);
					}
				}
				else {
					System.out.println("������Աλ����Ϣ���ϴ�ʡƽ̨");
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
	 * ͬ����Աλ��
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
			case Constant.WFRZJXHYXGS:
				elList = convertLocationToEmployeeLocation(systemFlag,databaseName);
				break;
			case Constant.WFPXHGYXGS:
			case Constant.SDFLXCLKJYXGS:
			case Constant.SDBFXCLYXGS:
				elList = convertPositionToEmployeeLocation(systemFlag,databaseName);
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
					}
					else {
						boolean success=reAuthLogin(request);
						System.out.println("success==="+success);
					}
				}
				else {
					System.out.println("������Աλ����Ϣ���ϴ�ʡƽ̨");
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
	 * ͬ��������Ϣ
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
			case Constant.WFRZJXHYXGS:
				eaList = convertWarnRecordToEmployeeAlarm(systemFlag,databaseName);
				break;
			case Constant.WFPXHGYXGS:
			case Constant.SDFLXCLKJYXGS:
			case Constant.SDBFXCLYXGS:
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
							switch (systemFlag) {//����ϵͳ��ʶ�ж����°滹�Ǿɰ�ϵͳ�����ı�����¼���ͬ����ʶΪ��ͬ��
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
					}
					else {
						boolean success=reAuthLogin(request);
						System.out.println("success==="+success);
					}
				}
				else {
					System.out.println("���ޱ�����Ϣ���ϴ�ʡƽ̨");
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
	 * ��v1.3�ӿڻ�ȡ����Ա��Ϣת��Ϊʡƽ̨����Ա��Ϣ
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
			ei.setDepart_name("δ֪");
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
	 * ��v3.1�ӿڻ�ȡ����Ա��Ϣת��Ϊʡƽ̨����Ա��Ϣ
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
				post="δ֪";
			ei.setPost_name(post);
			ei.setDepart_id(staff.getDeptId()+"");
			ei.setDepart_name("δ֪");
			ei.setName(staff.getName());
			ei.setSex((staff.getSex()==1?0:1)+"");
			ei.setCard_no(staff.getJobNumber());
			String companySocialCode=null;
			switch (systemFlag) {
			case Constant.WFPXHGYXGS:
				companySocialCode=Constant.DATA_ID_WFPXHGYXGS;
				break;
			case Constant.SDFLXCLKJYXGS:
				companySocialCode=Constant.DATA_ID_SDFLXCLKJYXGS;
				break;
			case Constant.SDBFXCLYXGS:
				companySocialCode=Constant.DATA_ID_SDBFXCLYXGS;
				break;
			}
			ei.setCompany_social_code(companySocialCode);
			String employeeType=null;
			int type=staff.getType();
			switch (type) {
			case Staff.NEI_BU_YUAN_GONG:
				employeeType="�ڲ�Ա��";
				break;
			}
			ei.setEmployee_type(employeeType);
			eiList.add(ei);
		}
		return eiList;
	}
	
	/**
	 * ��v1.3�ӿڻ�ȡ����Աλ����Ϣת��Ϊʡƽ̨����Աλ����Ϣ
	 * @param systemFlag
	 * @param databaseName
	 * @return
	 */
	public List<EmployeeLocation> convertLocationToEmployeeLocation(int systemFlag,String databaseName) {
		List<EmployeeLocation> elList=new ArrayList<EmployeeLocation>();
		List<Location> locationList=locationService.queryELList(databaseName);
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
			el.setTime_stamp(DateUtil.convertLongToString(location.getLocationTime()));
			
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
	 * ��v3.1�ӿڻ�ȡ����Աλ����Ϣת��Ϊʡƽ̨����Աλ����Ϣ
	 * @param systemFlag
	 * @param databaseName
	 * @return
	 */
	public List<EmployeeLocation> convertPositionToEmployeeLocation(int systemFlag,String databaseName) {
		List<EmployeeLocation> elList=new ArrayList<EmployeeLocation>();
		List<Position> positionList=positionService.queryELList(databaseName);
		for (Position position : positionList) {
			EmployeeLocation el=new EmployeeLocation();
			String companySocialCode=null;
			switch (systemFlag) {
			case Constant.WFPXHGYXGS:
				companySocialCode=Constant.DATA_ID_WFPXHGYXGS;
				break;
			case Constant.SDFLXCLKJYXGS:
				companySocialCode=Constant.DATA_ID_SDFLXCLKJYXGS;
				break;
			case Constant.SDBFXCLYXGS:
				companySocialCode=Constant.DATA_ID_SDBFXCLYXGS;
				break;
			}
			el.setCompany_social_code(companySocialCode);
			el.setFloor_no(position.getFloor()+"");
			el.setCard_no(position.getJobNumber());
			el.setTime_stamp(DateUtil.convertLongToString(position.getLocationTime()));
			
			Float speed = position.getSpeed();
			String status=null;
			if(speed==0)
				status=EmployeeLocation.SLEEP;
			else
				status=EmployeeLocation.SPORT;
			el.setStatus(status);
			
			el.setLongitude(position.getLongitude()+"");
			el.setLatitude(position.getLatitude()+"");
			elList.add(el);
		}
		return elList;
	}
	
	/**
	 * ��v1.3�ӿڻ�ȡ�ı�����Ϣת��Ϊʡƽ̨�ı�����Ϣ
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
	 * ��v3.1�ӿڻ�ȡ�ı�����Ϣת��Ϊʡƽ̨�ı�����Ϣ
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
			case Constant.WFPXHGYXGS:
				companySocialCode=Constant.DATA_ID_WFPXHGYXGS;
				break;
			case Constant.SDFLXCLKJYXGS:
				companySocialCode=Constant.DATA_ID_SDFLXCLKJYXGS;
				break;
			case Constant.SDBFXCLYXGS:
				companySocialCode=Constant.DATA_ID_SDBFXCLYXGS;
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
	 * ��ȡ������ʶ�浽request��Ա�������
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
	 * ���ݳ��б�ʶѡ���˻�������
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
		}
		request.setAttribute("username", username);
		request.setAttribute("password", password);
	}
	
	/**
	 * ������ҵ��ʶѡ��ӿ��������ҵ��Ϣ
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
	
	/**
	 * ������ҵ��ʶѡ�����ݿ�
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
		case Constant.SDBFXCLYXGS:
			databaseName=Constant.DATABASE_NAME_SDBFXCLYXGS;
			break;
		case Constant.WFRZJXHYXGS:
			databaseName=Constant.DATABASE_NAME_WFRZJXHYXGS;
			break;
		}
		request.setAttribute("databaseName", databaseName);
	}
	
	public JSONObject postBody(JSONObject bodyParamJO, String path, HttpServletRequest request) {
		JSONObject resultJO = null;
		try {
			switchCity(request);
			
			StringBuffer sbf = new StringBuffer(); 
			String strRead = null; 
			String serverUrl=ADDRESS_URL+path;
			
			URL url = new URL(serverUrl); 
			HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
			
			//connection.setInstanceFollowRedirects(false); 

			HttpSession session = request.getSession();
			if(!path.contains("auth/login")) {
				String username=request.getAttribute("username").toString();
				
				String token = null;
				Object LoginUserObj = session.getAttribute("loginUser"+username);
				//System.out.println("LoginUserObj==="+LoginUserObj);
				if(LoginUserObj!=null) {//�ȴ�session���ȡ��¼�û���Ϣ��token
					LoginUser loginUser = (LoginUser)LoginUserObj;
					token = loginUser.getToken();
				}
				
				if(token==null) {//��session��û���û����ٴ����ݿ����ȡtoken(����������tokenδ��������µ���)
					token = loginUserService.getTokenByUsername(username);
				}

				System.out.println("token==="+token);
				if(!StringUtils.isEmpty(token))
					connection.setRequestProperty("Authorization", "Bearer "+token);
				//connection.setRequestProperty("Authorization", "Bearer UhSHfbnUBDxAlTnk7jFGovvxAGKG/XxETyGYhUgpxQ0=");
			}
			connection.setRequestMethod("POST");//����post��ʽ
			connection.setDoInput(true); 
			connection.setDoOutput(true); 
			//header�ڵĵĲ���������set    
			//connection.setRequestProperty("key", "value");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect(); 
			
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8"); 
			//body����������
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
				if(!result.contains("����Ƶ��")) {
					resultJO = new JSONObject(result);
					resultJO.put("status", "ok");
					
					if(path.contains("auth/login")) {
						if(!checkTokenInSession(request)) {//��session�ﲻ���ڸ��û�����Ҫ���û��浽session��
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
				else {//����Ƶ�����Ժ��ٵ���
					resultJO = new JSONObject();
					resultJO.put("status", "error");
					resultJO.put("msg", result);
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
	 * ��֤session�����޸õ�¼�û�
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
