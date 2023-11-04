package com.sdwhNrcc.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdwhNrcc.entity.sdwh.*;
import com.sdwhNrcc.entity.sdwh.ApiLog;
import com.sdwhNrcc.entity.v1_3.WarnRecord;
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.sdwh.*;
import com.sdwhNrcc.service.v3_1.*;
import com.sdwhNrcc.util.Constant;
import com.sdwhNrcc.util.DateUtil;
import com.sdwhNrcc.util.StringUtil;

@Controller
@RequestMapping(LzqApiController.MODULE_NAME)
public class LzqApiController {

	private static final String ADDRESS_URL="http://"+Constant.SERVICE_IP_LZQ+":"+Constant.SERVICE_PORT_LZQ+"/data";
	public static final String MODULE_NAME="/lzqApi";

	@Autowired
	private PositionService positionService;
	@Autowired
	private KeyWarningService keyWarningService;
	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private ApiLogServiceSdwh apiLogService;

	@RequestMapping(value="/goPage")
	public String goPage(HttpServletRequest request) {
		//������ƽ̨ͬ����Ա��Ϣҳ��
		//http://localhost:8080/SdwhNrcc/lzqApi/goPage?page=syncDBRun
		//��Ǭ����ͬ����Ա��Ϣҳ��
		//http://localhost:8080/SdwhNrcc/lzqApi/goPage?page=xqhgSyncDBRun

		//������ƽ̨ͬ����Աλ�á�������Ϣҳ��
		//http://localhost:8080/SdwhNrcc/lzqApi/goPage?page=syncDBManager
		//��Ǭ����ͬ����Աλ�á�������Ϣҳ��
		//http://localhost:8080/SdwhNrcc/lzqApi/goPage?page=xqhgSyncDBManager
		String url = null;
		String page = request.getParameter("page");
		if("syncDBRun".equals(page)){

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
		else if("xqhgSyncDBRun".equals(page)){
			url="redirect:goPage?page=syncDBRun&cityFlag="+Constant.ZI_BO+"&systemFlag="+Constant.ZBXQHGYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.LZQ;
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
		else if("xqhgSyncDBManager".equals(page)){
			url="redirect:goPage?page=syncDBManager&cityFlag="+Constant.ZI_BO+"&systemFlag="+Constant.ZBXQHGYXGS+"&epVersion="+Constant.VERSION_3_1+"&apiFlag="+Constant.LZQ;
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
	@RequestMapping(value="/loginDoLogin")
	@ResponseBody
	public Map<String, Object> loginDoLogin(String username, String password, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			JSONObject bodyParamJO=new JSONObject();
			bodyParamJO.put("username", username);
			bodyParamJO.put("password", password);
			resultJO = postBody(bodyParamJO,"/Login/doLogin",request);
			String code=resultJO.get("code").toString();
			System.out.println("==="+code);
			String info=resultJO.get("info").toString();
			
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
			resultMap.put("info", info);
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
	public boolean reLoginDoLogin(HttpServletRequest request) {
		String username = request.getAttribute("username").toString();
		String password = request.getAttribute("password").toString();
		System.out.println("username==="+username);
		System.out.println("password==="+password);
		Map<String, Object> resultMap = loginDoLogin(username,password,request);
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

			String databaseName = request.getAttribute("databaseName").toString();
			int systemFlag = Integer.valueOf(request.getAttribute("systemFlag").toString());
			switch (systemFlag) {
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
					dataParamJO.put("id", ei.getLzqId());
					dataParamJO.put("post_id", ei.getPost_id());
					dataParamJO.put("post_name", ei.getPost_name());
					dataParamJO.put("depart_id", ei.getDepart_id());
					dataParamJO.put("depart_name", ei.getDepart_name());
					dataParamJO.put("name", ei.getName());
					dataParamJO.put("sex", ei.getSex());
					dataParamJO.put("card_no", ei.getCard_no());
					dataParamJO.put("company_social_code", ei.getCompany_social_code());
					dataParamJO.put("employee_type", ei.getEmployee_type());
					dataParamJO.put("deleted", ei.getDeleted());
					dataParamJA.put(dataParamJO);
				}
			
				if(eiListSize>0) {
					JSONObject bodyParamJO=switchSystem(systemFlag);
					bodyParamJO.put("data", dataParamJA);
					System.out.println("bodyParamJOStr="+bodyParamJO.toString());
				
					resultJO = postBody(bodyParamJO,"/employee/info",request);
					String status=resultJO.get("status").toString();
					if("ok".equals(status)) {
						String code=resultJO.get("code").toString();
						String msg=resultJO.get("msg").toString();
						System.out.println("code==="+code);
						System.out.println("msg==="+msg);
						resultMap.put("code", code);
						resultMap.put("msg", msg);
					}
					else {
						boolean success=reLoginDoLogin(request);
						System.out.println("success==="+success);
						if(success) {
							Thread.sleep(1000*60);//����Ƶ������������1���Ӻ���ִ��
							resultMap=dataEmployeeInfo(request);
						}
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
			case Constant.ZBXQHGYXGS:
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
				
					resultJO = postBody(bodyParamJO,"/employee/locations",request);
					String status=resultJO.get("status").toString();
					if("ok".equals(status)) {
						String code=resultJO.get("code").toString();
						System.out.println("code==="+code);
						String msg=resultJO.get("msg").toString();
						
						resultMap.put("code", code);
						resultMap.put("msg", msg);


						addApiLog(createApiLogByParams("dataEmployeeLocations",bodyParamJO.toString(),status,code,msg,elList.get(0).getCompany_social_code()));
					}
					else {
						boolean success=reLoginDoLogin(request);
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
					String lzqId = ea.getLzqId();
					dataParamJO.put("id", lzqId);
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
					//System.out.println("bodyParamJO==="+bodyParamJO.toString());
					
					resultJO = postBody(bodyParamJO,"/employee/alarm",request);
					String status=resultJO.get("status").toString();
					if("ok".equals(status)) {
						String code=resultJO.get("code").toString();
						System.out.println("code==="+code);
						String msg=resultJO.get("msg").toString();
						
						if("200".equals(code)) {
							String syncIds = syncIdsSB.toString().substring(1);
							System.out.println("syncIds==="+syncIds);
							switch (systemFlag) {
							case Constant.ZBXQHGYXGS:
								keyWarningService.syncByIds(syncIds,databaseName);
								break;
							}
						}
						
						resultMap.put("code", code);
						resultMap.put("msg", msg);
					}
					else {
						boolean success=reLoginDoLogin(request);
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
	 * �����־��¼
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
				jsonMap.put("info", "�����־��¼�ɹ�");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "�����־��¼ʧ��");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonMap.put("message", "no");
			jsonMap.put("info", "�����־��¼ʧ��");
		}
		finally {
			return jsonMap;
		}
	}
	
	/**
	 * ���ݲ���������־��¼����
	 * @param action
	 * @param body
	 * @param status
	 * @param code
	 * @param msg
	 * @param company_social_code
	 * @return
	 */
	public ApiLog createApiLogByParams(String action,String body,String status,String code,String msg,String company_social_code){
		
		ApiLog apiLog=new ApiLog();
		apiLog.setAction(action);
		apiLog.setBody(body);
		apiLog.setStatus(status);
		apiLog.setCode(code);
		apiLog.setMsg(msg);
		apiLog.setCompany_social_code(company_social_code);
		
		return apiLog;
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
			ei.setLzqId(staff.getLzqId());
			ei.setPost_id("fbbcd141-1526-42de-ac9e-843e0395671e");
			ei.setPost_name("������");
			ei.setDepart_id(staff.getDeptLzqId());
			ei.setDepart_name(staff.getDeptName());
			ei.setName(staff.getName());
			ei.setSex((staff.getSex()==0?"��":"Ů"));
			ei.setCard_no(staff.getJobNumber());
			String companySocialCode=null;
			switch (systemFlag) {
			case Constant.ZBXQHGYXGS:
				companySocialCode=Constant.DATA_ID_ZBXQHGYXGS;
				break;
			}
			ei.setCompany_social_code(companySocialCode);
			String employeeType=null;
			int type=staff.getType();
			switch (type) {
			case Staff.NEI_BU_YUAN_GONG:
				employeeType=EmployeeInfo.NEI_BU_YUAN_GONG;
				break;
			}
			ei.setEmployee_type(employeeType);
			String deletedStr=null;
			Integer deleted = staff.getDeleted();
			if(deleted==null)
				deletedStr="0";
			ei.setDeleted(deletedStr);
			eiList.add(ei);
		}
		return eiList;
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
		Date date = new Date();
		for (Position position : positionList) {
			EmployeeLocation el=new EmployeeLocation();
			String companySocialCode=null;
			switch (systemFlag) {
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
			ea.setLzqId(kw.getLzqId());
			ea.setTime(kw.getRaiseTimeYMD());
			ea.setType("oneKeyAlarm:alarm");
			ea.setArea_name("map");
			ea.setName(kw.getEntityName());
			ea.setCard_no(kw.getJobNumber());
			
			String companySocialCode=null;
			switch (systemFlag) {
			case Constant.ZBXQHGYXGS:
				companySocialCode=Constant.DATA_ID_ZBXQHGYXGS;
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
		case Constant.ZI_BO:
			int systemFlag = Integer.valueOf(request.getParameter("systemFlag"));
			switch (systemFlag) {
			case Constant.ZBXQHGYXGS:
				username=Constant.AQPT_USERNAME_ZBXQHGYXGS;
				password=Constant.AQPT_PASSWORD_ZBXQHGYXGS;
				break;
			}
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
		String companyCode=null;
		switch (systemFlag) {
		case Constant.ZBXQHGYXGS:
			companyCode=Constant.AREA_CODE_ZBXQHGYXGS;
			break;
		}
		bodyParamJO.put("companyCode", companyCode);
		bodyParamJO.put("dataId", StringUtil.createUUID());
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
		case Constant.ZBXQHGYXGS:
			databaseName=Constant.DATABASE_NAME_ZBXQHGYXGS;
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
			if(!path.contains("Login/doLogin")) {
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
					connection.setRequestProperty("Authorization", token);
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
				resultJO = new JSONObject(result);
				resultJO.put("status", "ok");
				
				if(path.contains("Login/doLogin")) {
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
