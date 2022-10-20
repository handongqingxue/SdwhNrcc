package com.sdwhNrcc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdwhNrcc.service.*;
import com.alibaba.fastjson.JSON;
import com.sdwhNrcc.util.*;
import com.sdwhNrcc.entity.*;

@Controller
@RequestMapping(EpController.MODULE_NAME)
public class EpController {

	private static final String PUBLIC_URL="http://"+Constant.SERVICE_IP+":8081/position/public/embeded.smd";
	private static final String SERVICE_URL="http://"+Constant.SERVICE_IP+":8081/position/service/embeded.smd";
	public static final String MODULE_NAME="/ep";
	public static final String TEST_USER_Id="test";
	public static final int EP_FLAG=1;
	
	@Autowired
	private EntityService entityService;
	@Autowired
	private EpLoginUserService epLoginUserService;
	@Autowired
	private DutyService dutyService;
	@Autowired
	private TagService tagService;

	@RequestMapping(value="/goTestEp")
	public String goTestEp(HttpServletRequest request) {

		//https://blog.csdn.net/m0_57493148/article/details/124030242
		switchEnterprise(EP_FLAG,request);

		return "/testEp";
	}
	
	public void switchEnterprise(int epFlag,HttpServletRequest request) {
		String tenantId=null;
		String userId=null;
		String password=null;
		switch (epFlag) {
		case Constant.WFRZJXHYXGS:
			tenantId=Constant.TENANT_ID;
			userId=Constant.USER_ID;
			password=Constant.PASSWORD;
			break;
		}
		request.setAttribute("tenantId", tenantId);
		request.setAttribute("userId", userId);
		request.setAttribute("password", password);
	}

	/**
	 * 2.1.1 获取验证码
	 * @param tenantId
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getCode")
	@ResponseBody
	public Map<String, Object> getCode(String tenantId, String userId,HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			/*
			if(LOCAL_Server_NAME.equals(request.getServerName())) {
				JSONObject paramJO=new JSONObject();
				//paramJO.put("tenantId", "ts00000006");
				paramJO.put("tenantId", tenantId);
				//paramJO.put("userId", "test001");
				paramJO.put("userId", userId);
				resultJO = getRespJson("getCode", paramJO);
			}
			else {
			*/
				JSONObject bodyParamJO=new JSONObject();
				bodyParamJO.put("jsonrpc", "2.0");
				JSONObject paramJO=new JSONObject();
				//paramJO.put("tenantId", "ts00000006");
				paramJO.put("tenantId", tenantId);
				//paramJO.put("userId", "test001");
				paramJO.put("userId", userId);
				bodyParamJO.put("params", paramJO);
				bodyParamJO.put("method", "getCode");
				bodyParamJO.put("id", 1);
				resultJO = postBody(PUBLIC_URL,bodyParamJO,"getCode",request);
			//}
			String result=resultJO.get("result").toString();
			System.out.println("==="+result);
			resultMap.put("result", result);
			//results==={"result":"d9c137a48f074cc9a2d799dfc480be2c","id":1,"jsonrpc":"2.0"}
			//results==={"id":1,"jsonrpc":"2.0","error":{"code":-2,"message":null}}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
		
		/*
		Map<String, Object> resultMap = null;
		String url="http://139.196.143.225:8081/position/public/embeded.smd";
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(0, new BasicNameValuePair("jsonrpc", "2.0"));
		params.add(1, new BasicNameValuePair("params", "{\"tenantId\":\"sc19070007\",\"userId\":\"yyc\"}"));
		params.add(2, new BasicNameValuePair("method", "getCode"));
		params.add(3, new BasicNameValuePair("id", "1"));
			
		try {
			resultMap=getRespJson(url, params);
			JSONObject resultJO = new JSONObject(resultMap.get("result").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	/**
	 * 2.1.2 登录
	 * @param tenantId
	 * @param userId
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Map<String, Object> login(String tenantId, String userId, String password,HttpServletRequest request){
		System.out.println("login...........");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject resultJO = null;
			/*
			if(LOCAL_Server_NAME.equals(request.getServerName())) {
				JSONObject paramJO=new JSONObject();
				//paramJO.put("tenantId", "ts00000006");
				paramJO.put("tenantId", tenantId);
				//paramJO.put("userId", "test001");
				paramJO.put("userId", userId);
				//paramJO.put("password", "test001");
				paramJO.put("password", password);
				resultJO = getRespJson("login", paramJO);
			}
			else {
			*/
				JSONObject bodyParamJO=new JSONObject();
				bodyParamJO.put("jsonrpc", "2.0");
				JSONObject paramJO=new JSONObject();
				//paramJO.put("tenantId", "ts00000006");
				paramJO.put("tenantId", tenantId);
				//paramJO.put("userId", "test001");
				paramJO.put("userId", userId);
				//paramJO.put("key", "415c9486b11c55592bfb20082e5b55184c11d3661e46f37efff7c118ab64bdda");
				String vsCode = getCode(tenantId,userId,request).get("result").toString();
				paramJO.put("key", SHA256Util.getSHA256(tenantId+userId+password+vsCode));
				bodyParamJO.put("params", paramJO);
				bodyParamJO.put("method", "login");
				bodyParamJO.put("id", 1);
				resultJO = postBody(PUBLIC_URL,bodyParamJO,"login",request);
			//}
			//bodyParamStr==={"method":"login","id":1,"jsonrpc":"2.0","params":{"tenantId":"ts00000006","userId":"test001","key":"415c9486b11c55592bfb20082e5b55184c11d3661e46f37efff7c118ab64bdda"}}
			//result==={"result":{"role":1,"staffId":null},"id":1,"jsonrpc":"2.0"}
			//result==={"id":1,"jsonrpc":"2.0","error":{"code":-2,"message":"ts00000006: code miss"}}
			String resultStr = resultJO.toString();
			System.out.println("resultJO==="+resultStr);
			resultMap=JSON.parseObject(resultStr, Map.class);
			if("ok".equals(resultJO.get("status").toString())) {
				Map<String, Object> resultJOMap = (Map<String, Object>)resultMap.get("result");
				
				HttpSession session = request.getSession();
				EpLoginUser epLoginUser=(EpLoginUser)session.getAttribute("epLoginUser");
				epLoginUser.setUserId(userId);
				epLoginUser.setRole(Integer.valueOf(resultJOMap.get("role").toString()));
				resultMap.put("cookie", epLoginUser.getCookie());
			}
			else {
				resultMap.put("message", "用户不存在");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}

	@RequestMapping(value="/exit")
	public String exit(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		
		return MODULE_NAME+"/login";
	}

	@RequestMapping(value="/initEntityList")
	@ResponseBody
	public Map<String, Object> initEntityList() {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Entity> list=entityService.queryList();
		
		if(list.size()==0) {
			resultMap.put("status","no");
			resultMap.put("message", "暂无数据");
		}
		else {
			resultMap.put("status","ok");
			resultMap.put("list", list);
		}
			
		return resultMap;
	}
	
	@RequestMapping(value="/insertEntityData")
	@ResponseBody
	public Map<String, Object> insertEntityData(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> erMap = getEntities("staff", request);
		List<Entity> entityList = JSON.parseArray(erMap.get("result").toString(),Entity.class);
		int count=entityService.add(entityList);
		if(count==0) {
			resultMap.put("status", "no");
			resultMap.put("message", "初始化实体信息失败");
		}
		else {
			resultMap.put("status", "ok");
			resultMap.put("message", "初始化实体信息成功");
		}
		return resultMap;
	}

	@RequestMapping(value="/insertDutyData")
	@ResponseBody
	public Map<String, Object> insertDutyData(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> drMap = getDutys(request);
		List<Duty> dutyList = JSON.parseArray(drMap.get("result").toString(),Duty.class);
		int count=dutyService.add(dutyList);
		if(count==0) {
			resultMap.put("status", "no");
			resultMap.put("message", "初始化员工职务列表失败");
		}
		else {
			resultMap.put("status", "ok");
			resultMap.put("message", "初始化员工职务列表成功");
		}
		
		return resultMap;
	}

	@RequestMapping(value="/insertTagData")
	@ResponseBody
	public Map<String, Object> insertTagData(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> trMap = getTags(request);
		List<Tag> tagList = JSON.parseArray(trMap.get("result").toString(),Tag.class);
		int count=tagService.add(tagList);
		if(count==0) {
			resultMap.put("status", "no");
			resultMap.put("message", "初始化定位标签列表失败");
		}
		else {
			resultMap.put("status", "ok");
			resultMap.put("message", "初始化定位标签列表成功");
		}
		
		return resultMap;
	}

	/**
	 * 2.2.5获取定位标签列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getTags")
	@ResponseBody
	public Map<String, Object> getTags(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject bodyParamJO=new JSONObject();
			bodyParamJO.put("jsonrpc", "2.0");
			bodyParamJO.put("method", "getTags");
			JSONObject paramJO=new JSONObject();
			//paramJO.put("entityType", "");
			//paramJO.put("tagType", "");
			bodyParamJO.put("params", paramJO);
			bodyParamJO.put("id", 1);
			JSONObject resultJO = postBody(SERVICE_URL,bodyParamJO,"getTags",request);
			System.out.println("getTags:resultJO==="+resultJO.toString());
			/*
			 {"result":[
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004761","userId":"4761","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003760","userId":"3760","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003625","userId":"3625","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTTFCC6E902","userId":"6E902"},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003853","userId":"3853","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004903","userId":"4903","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003912","userId":"3912","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004762","userId":"4762","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004816","userId":"4816","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004695","userId":"4695","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004829","userId":"4829","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004896","userId":"4896","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003914","userId":"3914","engineMask":1},
			 {"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039771","userId":"9771","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT19103554","userId":"3554"},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004765","userId":"4765","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004759","userId":"4759","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003756","userId":"3756","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT1E7D5EB9","userId":"5EB9"},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004739","userId":"4739","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003901","userId":"3901","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004744","userId":"4744","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004773","userId":"4773","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003632","userId":"3632","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT20800186","userId":"186"},
			 {"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004669","userId":"4669"},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003823","userId":"3823","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT823DDE32","userId":"3200"},
			 {"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004662","userId":"4662"},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004778","userId":"4778","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004864","userId":"4864","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004753","userId":"4753","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004857","userId":"4857","engineMask":1},
			 {"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004813","userId":"4813","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003840","userId":"3840","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004885","userId":"4885","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003699","userId":"3699","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004758","userId":"4758","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039759","userId":"9759","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039755","userId":"9755","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004921","userId":"4921"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003639","userId":"3639","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004771","userId":"4771","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004763","userId":"4763","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003629","userId":"3629","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004681","userId":"4681"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003637","userId":"3637","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004670","userId":"4670"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004769","userId":"4769","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004776","userId":"4776","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004779","userId":"4779","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003811","userId":"3811","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003929","userId":"3929","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT36003627","userId":"36003627"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT19122156","userId":"2156"},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT32003627","userId":"32003627","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004790","userId":"4790","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003697","userId":"3697","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004835","userId":"4835","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003650","userId":"3650","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003906","userId":"3906","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003723","userId":"3723","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003761","userId":"3761","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004772","userId":"4772","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003687","userId":"3687","engineMask":1},{"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004660","userId":"4660"},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004688","userId":"4688"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003715","userId":"3715","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039762","userId":"9762","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004802","userId":"4802","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039754","userId":"9754","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003882","userId":"3882","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004807","userId":"4807","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003766","userId":"3766","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003892","userId":"3892","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003671","userId":"3671","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003724","userId":"3724","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003741","userId":"3741","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003708","userId":"3708","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004831","userId":"4831","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003729","userId":"3729","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003670","userId":"3670","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003624","userId":"3624","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003673","userId":"3673","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003872","userId":"3872","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003716","userId":"3716","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004691","userId":"4691","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004674","userId":"4674"},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004656","userId":"4656"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004793","userId":"4793","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003759","userId":"3759","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004791","userId":"4791","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003726","userId":"3726","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004808","userId":"4808","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004810","userId":"4810","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003690","userId":"3690","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003739","userId":"3739","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004803","userId":"4803","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003751","userId":"3751","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004817","userId":"4817","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004811","userId":"4811","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004851","userId":"4851","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003736","userId":"3736","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003744","userId":"3744","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003702","userId":"3702","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003870","userId":"3870","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003694","userId":"3694","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003612","userId":"3612","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004833","userId":"4833","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004814","userId":"4814","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003743","userId":"3743","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004848","userId":"4848","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT32004677","userId":"4677"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003727","userId":"3727","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003781","userId":"3781","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003686","userId":"3686","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004800","userId":"4800","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004751","userId":"4751","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003609","userId":"3609","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003731","userId":"3731","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004824","userId":"4824","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003710","userId":"3710","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004768","userId":"4768","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004840","userId":"4840","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004780","userId":"4780","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003819","userId":"3819","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000016","userId":"0016","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003676","userId":"3676","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003821","userId":"3821","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003651","userId":"3651","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003746","userId":"3746","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003869","userId":"3869","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004743","userId":"4743","engineMask":1},{"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004678","userId":"4678"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004692","userId":"4692","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004747","userId":"4747","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004855","userId":"4855","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004679","userId":"4679"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003907","userId":"3907","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004887","userId":"4887","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004854","userId":"4854","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003678","userId":"3678","engineMask":1},{"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004663","userId":"4663"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004877","userId":"4877","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003807","userId":"3807","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004730","userId":"4730","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003665","userId":"3665","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004777","userId":"4777","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004694","userId":"4694","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039774","userId":"9774","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004716","userId":"4716","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003685","userId":"3685","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004910","userId":"4910","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004874","userId":"4874","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004880","userId":"4880","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004789","userId":"4789","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004702","userId":"4702","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004757","userId":"4757","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004863","userId":"4863","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004745","userId":"4745","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004668","userId":"4668"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004886","userId":"4886","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003730","userId":"3730","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004893","userId":"4893","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003622","userId":"3622","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004736","userId":"4736","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004908","userId":"4908","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004909","userId":"4909","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003703","userId":"3703","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003844","userId":"3844","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003899","userId":"3899","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003769","userId":"3769","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004693","userId":"4693","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003696","userId":"3696","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004754","userId":"4754","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004861","userId":"4861","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004822","userId":"4822","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004849","userId":"4849","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004731","userId":"4731","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004667","userId":"4667"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004746","userId":"4746","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004687","userId":"4687"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003603","userId":"3603","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004719","userId":"4719","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003860","userId":"3860","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039792","userId":"9792","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004795","userId":"4795","engineMask":1},{"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004682","userId":"4682"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003645","userId":"3645","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039772","userId":"9772","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004870","userId":"4870","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003654","userId":"3654","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004659","userId":"4659"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003605","userId":"3605","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003662","userId":"3662","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003704","userId":"3704","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004704","userId":"4704","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004871","userId":"4871","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003846","userId":"3846","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003925","userId":"3925","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003852","userId":"3852","engineMask":1},{"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004683","userId":"4683"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004740","userId":"4740","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003763","userId":"3763","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003941","userId":"3941","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003610","userId":"3610","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003709","userId":"3709","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003631","userId":"3631","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004901","userId":"4901","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004684","userId":"4684"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004830","userId":"4830","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004828","userId":"4828","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004708","userId":"4708","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003913","userId":"3913","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004726","userId":"4726","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004725","userId":"4725","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003613","userId":"3613","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003664","userId":"3664","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004862","userId":"4862","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004713","userId":"4713","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003802","userId":"3802","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003717","userId":"3717","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004676","userId":"4676"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004792","userId":"4792","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004685","userId":"4685"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003752","userId":"3752","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004732","userId":"4732","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039769","userId":"9769","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003636","userId":"3636","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003866","userId":"3866","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004767","userId":"4767","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003722","userId":"3722","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039766","userId":"9766","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003828","userId":"3828","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003787","userId":"3787","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004847","userId":"4847","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004797","userId":"4797","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004774","userId":"4774","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003682","userId":"3682","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004839","userId":"4839","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003816","userId":"3816","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003728","userId":"3728","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004895","userId":"4895","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003649","userId":"3649","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003754","userId":"3754","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003652","userId":"3652","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003920","userId":"3920","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003655","userId":"3655","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003758","userId":"3758","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003923","userId":"3923","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004876","userId":"4876","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003864","userId":"3864","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003930","userId":"3930","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003894","userId":"3894","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003755","userId":"3755","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003659","userId":"3659","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003784","userId":"3784","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003734","userId":"3734","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003667","userId":"3667","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003711","userId":"3711","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004841","userId":"4841","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003712","userId":"3712","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004755","userId":"4755","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003738","userId":"3738","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003648","userId":"3648","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003656","userId":"3656","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003797","userId":"3797","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003850","userId":"3850","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003745","userId":"3745","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004782","userId":"4782","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004867","userId":"4867","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003862","userId":"3862","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003719","userId":"3719","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004749","userId":"4749","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000017","userId":"0017","engineMask":1},{"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004672","userId":"4672"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003858","userId":"3858","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004752","userId":"4752","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004658","userId":"4658"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004783","userId":"4783","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004733","userId":"4733","engineMask":1},{"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004661","userId":"4661"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003748","userId":"3748","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004712","userId":"4712","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003641","userId":"3641","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004722","userId":"4722","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003776","userId":"3776","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004865","userId":"4865","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004869","userId":"4869","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003642","userId":"3642","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004748","userId":"4748","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003871","userId":"3871","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003742","userId":"3742","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003845","userId":"3845","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003749","userId":"3749","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003668","userId":"3668","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003928","userId":"3928","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004699","userId":"4699","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003782","userId":"3782","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003643","userId":"3643","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003691","userId":"3691","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003607","userId":"3607","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004703","userId":"4703","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004853","userId":"4853","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004721","userId":"4721","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003767","userId":"3767","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004836","userId":"4836","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004832","userId":"4832","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003900","userId":"3900","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004785","userId":"4785","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004890","userId":"4890","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003943","userId":"3943","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004717","userId":"4717","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003897","userId":"3897","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003783","userId":"3783","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003945","userId":"3945","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003835","userId":"3835","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004796","userId":"4796","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004698","userId":"4698","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004728","userId":"4728","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003772","userId":"3772","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003706","userId":"3706","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003660","userId":"3660","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003672","userId":"3672","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003679","userId":"3679","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004821","userId":"4821","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003937","userId":"3937","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003681","userId":"3681","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004823","userId":"4823","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004905","userId":"4905","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004819","userId":"4819","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003841","userId":"3841","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003770","userId":"3770","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003611","userId":"3611","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004826","userId":"4826","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004697","userId":"4697","engineMask":1},{"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004690","userId":"4690"},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004673","userId":"4673"},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000013","userId":"0013","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003774","userId":"3774","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003893","userId":"3893","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004715","userId":"4715","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003778","userId":"3778","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003775","userId":"3775","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003873","userId":"3873","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003747","userId":"3747","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003606","userId":"3606","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003688","userId":"3688","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003805","userId":"3805","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003617","userId":"3617","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003904","userId":"3904","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004881","userId":"4881","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003813","userId":"3813","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004720","userId":"4720","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003837","userId":"3837","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003809","userId":"3809","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004664","userId":"4664"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004711","userId":"4711","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003640","userId":"3640","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003733","userId":"3733","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004801","userId":"4801","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004845","userId":"4845","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004741","userId":"4741","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004883","userId":"4883","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003623","userId":"3623","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004651","userId":"4651"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003926","userId":"3926","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004900","userId":"4900","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003868","userId":"3868","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004675","userId":"4675"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004787","userId":"4787","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004718","userId":"4718","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004859","userId":"4859","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003942","userId":"3942","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003887","userId":"3887","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003938","userId":"3938","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003886","userId":"3886","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004873","userId":"4873","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003859","userId":"3859","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003889","userId":"3889","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003933","userId":"3933","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003922","userId":"3922","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004750","userId":"4750","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004879","userId":"4879","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003851","userId":"3851","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004844","userId":"4844","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039773","userId":"9773","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003684","userId":"3684","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004897","userId":"4897","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004899","userId":"4899","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003750","userId":"3750","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003856","userId":"3856","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003950","userId":"3950","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004724","userId":"4724","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039760","userId":"9760","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004838","userId":"4838","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003786","userId":"3786","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003822","userId":"3822","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004680","userId":"4680"},{"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004686","userId":"4686"},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039756","userId":"9756","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004723","userId":"4723","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003647","userId":"3647","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003946","userId":"3946","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003804","userId":"3804","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004891","userId":"4891","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004875","userId":"4875","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003621","userId":"3621","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003601","userId":"3601","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003614","userId":"3614","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004812","userId":"4812","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003857","userId":"3857","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003808","userId":"3808","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004834","userId":"4834","engineMask":1},{"temporary":true,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004665","userId":"4665"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003680","userId":"3680","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003689","userId":"3689","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003794","userId":"3794","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003935","userId":"3935","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003817","userId":"3817","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003604","userId":"3604","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003630","userId":"3630","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004760","userId":"4760","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003833","userId":"3833","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003881","userId":"3881","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003877","userId":"3877","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004666","userId":"4666"},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039781","userId":"9781","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003764","userId":"3764","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003790","userId":"3790","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003777","userId":"3777","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003628","userId":"3628","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003884","userId":"3884","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003898","userId":"3898","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003939","userId":"3939","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003698","userId":"3698","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004709","userId":"4709","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004727","userId":"4727","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003919","userId":"3919","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004671","userId":"4671"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003740","userId":"3740","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003771","userId":"3771","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003799","userId":"3799","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003615","userId":"3615","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003855","userId":"3855","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004652","userId":"4652"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004805","userId":"4805","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003880","userId":"3880","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003874","userId":"3874","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003814","userId":"3814","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003633","userId":"3633","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003927","userId":"3927","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003768","userId":"3768","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003827","userId":"3827","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003773","userId":"3773","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003888","userId":"3888","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003677","userId":"3677","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003725","userId":"3725","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003849","userId":"3849","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003902","userId":"3902","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003635","userId":"3635","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003793","userId":"3793","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003917","userId":"3917","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004892","userId":"4892","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004657","userId":"4657"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003944","userId":"3944","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003720","userId":"3720","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003915","userId":"3915","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004884","userId":"4884","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003842","userId":"3842","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003638","userId":"3638","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003663","userId":"3663","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003824","userId":"3824","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004770","userId":"4770","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003701","userId":"3701","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003918","userId":"3918","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003646","userId":"3646","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003879","userId":"3879","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003891","userId":"3891","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003947","userId":"3947","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039793","userId":"9793","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003932","userId":"3932","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003620","userId":"3620","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000022","userId":"0022","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003798","userId":"3798","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003810","userId":"3810","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003779","userId":"3779","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003801","userId":"3801","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003803","userId":"3803","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000007","userId":"0007"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003885","userId":"3885","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004894","userId":"4894","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003666","userId":"3666","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003812","userId":"3812","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003829","userId":"3829","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003839","userId":"3839","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004784","userId":"4784","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003815","userId":"3815","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003675","userId":"3675","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004837","userId":"4837","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004889","userId":"4889","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003602","userId":"3602","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003644","userId":"3644","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003626","userId":"3626","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004689","userId":"4689"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004809","userId":"4809","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003876","userId":"3876","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003820","userId":"3820","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039765","userId":"9765","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003838","userId":"3838","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003890","userId":"3890","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003619","userId":"3619","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003692","userId":"3692","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004888","userId":"4888","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003714","userId":"3714","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003826","userId":"3826","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003737","userId":"3737","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003674","userId":"3674","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004878","userId":"4878","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003867","userId":"3867","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003936","userId":"3936","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003791","userId":"3791","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004654","userId":"4654"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003800","userId":"3800","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004806","userId":"4806","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003707","userId":"3707","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003834","userId":"3834","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003878","userId":"3878","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003718","userId":"3718","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003875","userId":"3875","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003861","userId":"3861","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003910","userId":"3910","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003789","userId":"3789","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004655","userId":"4655"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003721","userId":"3721","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003896","userId":"3896","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003785","userId":"3785","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003705","userId":"3705","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003661","userId":"3661","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003795","userId":"3795","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003657","userId":"3657","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004818","userId":"4818","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003905","userId":"3905","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003806","userId":"3806","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003916","userId":"3916","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003832","userId":"3832","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003843","userId":"3843","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003713","userId":"3713","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003792","userId":"3792","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003818","userId":"3818","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003700","userId":"3700","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003949","userId":"3949","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003934","userId":"3934","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003762","userId":"3762","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003683","userId":"3683","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003616","userId":"3616","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003765","userId":"3765","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004868","userId":"4868","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004858","userId":"4858","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004701","userId":"4701","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004820","userId":"4820","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004742","userId":"4742","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004756","userId":"4756","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003854","userId":"3854","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004804","userId":"4804","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004705","userId":"4705","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004882","userId":"4882","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004866","userId":"4866","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004825","userId":"4825","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039767","userId":"9767","engineMask":1},{"temporary":true,"entityType":"car","tagStyle":"BTT02","id":"BTT32004653","userId":"4653"},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000012","userId":"0012","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003909","userId":"3909","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004907","userId":"4907","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004904","userId":"4904","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003863","userId":"3863","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004788","userId":"4788","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004902","userId":"4902","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039784","userId":"9784","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039761","userId":"9761","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004850","userId":"4850","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004815","userId":"4815","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004764","userId":"4764","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003788","userId":"3788","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004799","userId":"4799","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000015","userId":"0015","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003847","userId":"3847","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039782","userId":"9782","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004827","userId":"4827","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004714","userId":"4714","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003903","userId":"3903","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000018","userId":"0018","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039763","userId":"9763","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000010","userId":"0010","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039775","userId":"9775","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039776","userId":"9776","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000011","userId":"0011","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039788","userId":"9788","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003895","userId":"3895","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039758","userId":"9758","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003865","userId":"3865","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003732","userId":"3732","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004735","userId":"4735","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004898","userId":"4898","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039783","userId":"9783","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000019","userId":"0019","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039757","userId":"9757","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003695","userId":"3695","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039768","userId":"9768","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004842","userId":"4842","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039778","userId":"9778","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004798","userId":"4798","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004734","userId":"4734","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004781","userId":"4781","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039780","userId":"9780","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004852","userId":"4852","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000023","userId":"0023","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039787","userId":"9787","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039764","userId":"9764","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004775","userId":"4775","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039786","userId":"9786","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039770","userId":"9770","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT32003940","userId":"3940"},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039789","userId":"9789","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003780","userId":"3780","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000020","userId":"0020","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004856","userId":"4856","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004860","userId":"4860","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003669","userId":"3669","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039791","userId":"9791","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000014","userId":"0014","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004737","userId":"4737","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003608","userId":"3608","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000006","userId":"0006"},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004766","userId":"4766","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003653","userId":"3653","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003693","userId":"3693","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003735","userId":"3735","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003796","userId":"3796","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000021","userId":"0021","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003825","userId":"3825","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003924","userId":"3924","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003931","userId":"3931","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003753","userId":"3753","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004700","userId":"4700","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004794","userId":"4794","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039790","userId":"9790","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004696","userId":"4696","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004872","userId":"4872","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003948","userId":"3948","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004710","userId":"4710","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003618","userId":"3618","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039777","userId":"9777","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003883","userId":"3883","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003848","userId":"3848","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004707","userId":"4707","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003911","userId":"3911","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003634","userId":"3634","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003908","userId":"3908","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004786","userId":"4786","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004843","userId":"4843","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003658","userId":"3658","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT36000024","userId":"0024","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004906","userId":"4906","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004706","userId":"4706","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003831","userId":"3831","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039785","userId":"9785","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004846","userId":"4846","engineMask":1},{"temporary":false,"entityType":"car","tagStyle":"BTT02","id":"BTT34039779","userId":"9779","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003921","userId":"3921","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003830","userId":"3830","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003836","userId":"3836","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32003757","userId":"3757","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004729","userId":"4729","engineMask":1},{"temporary":false,"entityType":"staff","tagStyle":"BTT01","id":"BTT32004738","userId":"4738","engineMask":1}],"id":1,"jsonrpc":"2.0"}
			 */
			resultMap=JSON.parseObject(resultJO.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}

	/**
	 * 2.6.2获取员工职务列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getDutys")
	@ResponseBody
	public Map<String, Object> getDutys(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject bodyParamJO=new JSONObject();
			bodyParamJO.put("jsonrpc", "2.0");
			bodyParamJO.put("method", "getDutys");
			bodyParamJO.put("id", 1);
			JSONObject resultJO = postBody(SERVICE_URL,bodyParamJO,"getDutys",request);
			System.out.println("getDutys:resultJO==="+resultJO.toString());
			resultMap=JSON.parseObject(resultJO.toString(), Map.class);
			/*
			 {"result":[
				 {"entityType":"staff","onlineIcon":"/sc20080092/duty/onlineIcon-1.png?t=1605840646476","name":"员工","offlineIcon":"/sc20080092/duty/offlineIcon-1.png?t=1605840646476","id":1,"cnEntityType":"人员","key":1},
				 {"entityType":"car","onlineIcon":"/sc20080092/duty/onlineIcon-2.png?t=1605841283591","name":"内部车辆","offlineIcon":"/sc20080092/duty/offlineIcon-2.png?t=1605841283591","id":2,"cnEntityType":"车辆","onlineColor":"","key":2,"offlineColor":""},
				 {"entityType":"staff","onlineIcon":"/sc20080092/duty/onlineIcon--992.png?t=1605841880539","name":"危险作业人员","offlineIcon":"/sc20080092/duty/offlineIcon--992.png?t=1605841880539","id":-992,"cnEntityType":"人员","key":-992},
				 {"entityType":"staff","onlineIcon":"/sc20080092/duty/onlineIcon-5.png?t=1605857821060","name":"仪电","offlineIcon":"/sc20080092/duty/offlineIcon-5.png?t=1605857598742","id":5,"cnEntityType":"人员","onlineColor":"","key":5,"offlineColor":""},
				 {"entityType":"staff","onlineIcon":"/sc20080092/duty/onlineIcon-3.png?t=1605857827419","name":"管理人员","offlineIcon":"/sc20080092/duty/offlineIcon-3.png?t=1605840746705","id":3,"cnEntityType":"人员","onlineColor":"","key":3,"offlineColor":""},
				 {"entityType":"staff","onlineIcon":"/sc20080092/duty/onlineIcon-4.png?t=1605858310995","name":"安环","offlineIcon":"/sc20080092/duty/offlineIcon-4.png?t=1605840775158","id":4,"cnEntityType":"人员","onlineColor":"","key":4,"offlineColor":""},
				 {"entityType":"staff","onlineIcon":"/sc20080092/duty/onlineIcon-6.png?t=1605858330564","name":"EOG","offlineIcon":"/sc20080092/duty/offlineIcon-6.png?t=1605858330564","id":6,"onlineColor":"","offlineColor":""},
				 {"entityType":"car","onlineIcon":"/sc20080092/duty/onlineIcon--991.png?t=1607394653543","name":"外来车辆","offlineIcon":"/sc20080092/duty/offlineIcon--991.png?t=1607394653543","id":-991,"cnEntityType":"车辆","key":-991},
				 {"entityType":"staff","onlineIcon":"/sc20080092/duty/onlineIcon--990.png?t=1607568753403","name":"外来人员","offlineIcon":"/sc20080092/duty/offlineIcon--990.png?t=1607568753403","id":-990,"cnEntityType":"人员","key":-990}
			 ],
			 "id":1,"jsonrpc":"2.0"}
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}

	/**
	 * 2.6.9 获取实体列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getEntities")
	@ResponseBody
	public Map<String, Object> getEntities(String entityType, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject bodyParamJO=new JSONObject();
			bodyParamJO.put("jsonrpc", "2.0");
			bodyParamJO.put("method", "getEntities");
			JSONObject paramJO=new JSONObject();
			paramJO.put("entityType", entityType);
			//paramJO.put("pageIndex", "0");
			//paramJO.put("maxCount", "100");
			bodyParamJO.put("params", paramJO);
			bodyParamJO.put("id", 1);
			JSONObject resultJO = postBody(SERVICE_URL,bodyParamJO,"getEntities",request);
			System.out.println("getEntities:resultJO==="+resultJO.toString());
			/*
			 {"result":[
			 {"tagId":"BTT1E7D5EB9","entityType":"staff","sex":1,"departmentId":0,"photo":null,"pid":"phone001","userId":"5EB9","post":"","phone":"1","name":"陈锡棋测试手机","dutyId":1,"id":128813,"age":"1"},
			 {"post":"员工","phone":"3316","tagId":"BTT32003607","entityType":"staff","sex":1,"departmentId":4,"name":"王勇-test","photo":null,"dutyId":1,"pid":"FUPY00000","id":24092,"userId":"3607"},
			 {"security":"","post":"","phone":"3222","tagId":"BTT32003729","entityType":"staff","departmentId":33,"name":"范丽龙","photo":"","dutyId":1,"pid":"FUPYJX008","id":28041,"userId":"3729"},
			 {"post":"员工","phone":"123","tagId":"BTT32003618","entityType":"staff","departmentId":13,"name":"郭玉明","photo":null,"dutyId":4,"pid":"FUPY20013","id":24103,"userId":"3618"},
			 {"post":"员工","phone":"123","tagId":"BTT32003628","entityType":"staff","departmentId":13,"name":"李星","photo":null,"dutyId":4,"pid":"FUPY19008","id":24111,"userId":"3628"},
			 {"post":"员工","phone":"123","tagId":"BTT32003660","entityType":"staff","departmentId":13,"name":"丁杰","photo":null,"dutyId":4,"pid":"FUPY20007","id":24132,"userId":"3660"},
			 {"post":"员工","phone":"123","tagId":"BTT32003668","entityType":"staff","departmentId":13,"name":"李京","photo":null,"dutyId":4,"pid":"FUPY13042","id":24140,"userId":"3668"},
			 {"post":"员工","phone":"123","tagId":"BTT32003605","entityType":"staff","departmentId":5,"name":"秦莹莹","photo":null,"dutyId":3,"pid":"FUPY12006","id":24090,"userId":"3605"},
			 {"post":"员工","phone":"123","tagId":"BTT32003611","entityType":"staff","departmentId":6,"name":"陆志文","photo":null,"dutyId":3,"pid":"FUPY14064","id":24096,"userId":"3611"},
			 {"post":"员工","phone":"123","tagId":"BTT32003624","entityType":"staff","departmentId":5,"name":"周汉栋","photo":null,"dutyId":3,"pid":"FUPY12001","id":24109,"userId":"3624"},
			 {"post":"员工","phone":"123","tagId":"BTT32003658","entityType":"staff","departmentId":6,"name":"黄尉辉","photo":null,"dutyId":3,"pid":"FUPY17025","id":24130,"userId":"3658"},
			 {"post":"员工","phone":"123","tagId":"BTT32003688","entityType":"staff","departmentId":6,"name":"陈燕","photo":null,"dutyId":3,"pid":"FUPY13038","id":24154,"userId":"3688"},
			 {"post":"员工","phone":"123","tagId":"BTT32003828","entityType":"staff","departmentId":22,"name":"傅元婷","photo":null,"dutyId":3,"pid":"FUPYYC15011","id":24137,"userId":"3828"},{"post":"员工","tagId":"BTT32003603","entityType":"staff","sex":1,"departmentId":3,"name":"刘建华","photo":null,"dutyId":1,"pid":"FUPY20018","id":24088,"userId":"3603"},{"post":"员工","tagId":"BTT32003602","entityType":"staff","sex":1,"departmentId":2,"name":"谢松","photo":null,"dutyId":1,"pid":"FUPY20017","id":24087,"userId":"3602"},{"post":"员工","tagId":"BTT32003604","entityType":"staff","sex":1,"departmentId":4,"name":"张元","photo":null,"dutyId":1,"pid":"FUPY20019","id":24089,"userId":"3604"},{"post":"员工","tagId":"BTT32003608","entityType":"staff","sex":1,"departmentId":4,"name":"王春华","photo":null,"dutyId":1,"pid":"FUPY17017","id":24093,"userId":"3608"},{"post":"员工","tagId":"BTT32003609","entityType":"staff","sex":1,"departmentId":17,"name":"张清山","photo":null,"dutyId":1,"pid":"FUPY88888","id":24094,"userId":"3609"},{"post":"员工","tagId":"BTT32003612","entityType":"staff","sex":1,"departmentId":7,"name":"梁胜琴","photo":null,"dutyId":1,"pid":"FUPY12020","id":24097,"userId":"3612"},{"post":"员工","tagId":"BTT32003613","entityType":"staff","sex":1,"departmentId":8,"name":"郭延山","photo":null,"dutyId":1,"pid":"FUPY20091","id":24098,"userId":"3613"},{"post":"员工","tagId":"BTT32003614","entityType":"staff","sex":1,"departmentId":9,"name":"李春琴","photo":null,"dutyId":1,"pid":"FUPY13065","id":24099,"userId":"3614"},{"post":"员工","tagId":"BTT32003616","entityType":"staff","sex":1,"departmentId":11,"name":"曹荆","photo":null,"dutyId":1,"pid":"YC10055","id":24101,"userId":"3616"},{"post":"员工","tagId":"BTT32003617","entityType":"staff","sex":1,"departmentId":12,"name":"王雪","photo":null,"dutyId":1,"pid":"FUPY17002","id":24102,"userId":"3617"},{"post":"员工","tagId":"BTT32003619","entityType":"staff","sex":1,"departmentId":4,"name":"尹国香","photo":null,"dutyId":1,"pid":"FUPY19003","id":24104,"userId":"3619"},{"post":"员工","tagId":"BTT32003620","entityType":"staff","sex":1,"departmentId":14,"name":"孙黎","photo":null,"dutyId":1,"pid":"FUPY13058","id":24105,"userId":"3620"},{"post":"员工","tagId":"BTT32003621","entityType":"staff","sex":1,"departmentId":15,"name":"洪生林","photo":null,"dutyId":1,"pid":"FEITYZ0007","id":24106,"userId":"3621"},{"post":"员工","tagId":"BTT32003622","entityType":"staff","sex":1,"departmentId":4,"name":"杜恒峰","photo":null,"dutyId":1,"pid":"YC18006","id":24107,"userId":"3622"},{"post":"员工","tagId":"BTT32003623","entityType":"staff","sex":1,"departmentId":9,"name":"张森","photo":null,"dutyId":1,"pid":"FUPY15037","id":24108,"userId":"3623"},{"post":"员工","tagId":"BTT32003626","entityType":"staff","sex":1,"departmentId":16,"name":"宗慧","photo":null,"dutyId":1,"pid":"FUPY13060","id":24110,"userId":"3626"},{"post":"员工","tagId":"BTT32003631","entityType":"staff","sex":1,"departmentId":4,"name":"王金金","photo":null,"dutyId":1,"pid":"FUPY17024","id":24113,"userId":"3631"},{"post":"员工","tagId":"BTT32003633","entityType":"staff","sex":1,"departmentId":17,"name":"李福贵","photo":null,"dutyId":1,"pid":"FUPY13012","id":24114,"userId":"3633"},{"post":"员工","tagId":"BTT32003634","entityType":"staff","sex":1,"departmentId":4,"name":"王招稳","photo":null,"dutyId":1,"pid":"FUPY14083","id":24115,"userId":"3634"},{"post":"员工","tagId":"BTT32003636","entityType":"staff","sex":1,"departmentId":9,"name":"王莹","photo":null,"dutyId":1,"pid":"FUPY13046","id":24116,"userId":"3636"},{"post":"员工","tagId":"BTT32003638","entityType":"staff","sex":1,"departmentId":14,"name":"吴敏","photo":null,"dutyId":1,"pid":"FUPY15041","id":24117,"userId":"3638"},{"post":"员工","tagId":"BTT32003639","entityType":"staff","sex":1,"departmentId":7,"name":"李冬丽","photo":null,"dutyId":1,"pid":"FUPY13076","id":24118,"userId":"3639"},{"post":"员工","tagId":"BTT32003642","entityType":"staff","sex":1,"departmentId":14,"name":"钟耀翰","photo":null,"dutyId":1,"pid":"FUPY13055","id":24119,"userId":"3642"},{"post":"员工","tagId":"BTT32003643","entityType":"staff","sex":1,"departmentId":7,"name":"罗瑞文","photo":null,"dutyId":1,"pid":"FUPY19001","id":24120,"userId":"3643"},{"post":"员工","tagId":"BTT32003644","entityType":"staff","sex":1,"departmentId":4,"name":"强乃超","photo":null,"dutyId":1,"pid":"FUPY17001","id":24121,"userId":"3644"},{"post":"员工","tagId":"BTT32003645","entityType":"staff","sex":1,"departmentId":9,"name":"郑金花","photo":null,"dutyId":1,"pid":"FUPY15030","id":24122,"userId":"3645"},{"post":"员工","tagId":"BTT32003646","entityType":"staff","sex":1,"departmentId":4,"name":"周萍","photo":null,"dutyId":1,"pid":"FUPY19006","id":24123,"userId":"3646"},{"post":"员工","tagId":"BTT32003650","entityType":"staff","sex":1,"departmentId":16,"name":"钱长露","photo":null,"dutyId":1,"pid":"FUPY17004","id":24124,"userId":"3650"},{"post":"员工","tagId":"BTT32003653","entityType":"staff","sex":1,"departmentId":18,"name":"周宝平","photo":null,"dutyId":1,"pid":"YC12002","id":24125,"userId":"3653"},{"post":"员工","tagId":"BTT32003654","entityType":"staff","sex":1,"departmentId":7,"name":"戴亚南","photo":null,"dutyId":1,"pid":"FUPY18022","id":24126,"userId":"3654"},{"post":"员工","tagId":"BTT32003655","entityType":"staff","sex":1,"departmentId":9,"name":"冯辉棋","photo":null,"dutyId":1,"pid":"FUPY13030","id":24127,"userId":"3655"},{"post":"员工","tagId":"BTT32003656","entityType":"staff","sex":1,"departmentId":16,"name":"胡杨","photo":null,"dutyId":1,"pid":"FUPY18005","id":24128,"userId":"3656"},{"post":"员工","tagId":"BTT32003657","entityType":"staff","sex":1,"departmentId":4,"name":"陶萍萍","photo":null,"dutyId":1,"pid":"FUPY17016","id":24129,"userId":"3657"},{"post":"员工","tagId":"BTT32003659","entityType":"staff","sex":1,"departmentId":19,"name":"王洁丽","photo":null,"dutyId":1,"pid":"FEITYZ0034","id":24131,"userId":"3659"},{"post":"员工","tagId":"BTT32003661","entityType":"staff","sex":1,"departmentId":12,"name":"陈弯弯","photo":null,"dutyId":1,"pid":"FUPY14172","id":24133,"userId":"3661"},{"post":"员工","tagId":"BTT32003662","entityType":"staff","sex":1,"departmentId":20,"name":"张亦廷","photo":null,"dutyId":1,"pid":"YCK99001","id":24134,"userId":"3662"},{"post":"员工","tagId":"BTT32003663","entityType":"staff","sex":1,"departmentId":21,"name":"孙爱霞","photo":null,"dutyId":1,"pid":"FUPY14056","id":24135,"userId":"3663"},{"post":"员工","tagId":"BTT32003666","entityType":"staff","sex":1,"departmentId":9,"name":"陶念慈","photo":null,"dutyId":1,"pid":"FUPY12015","id":24138,"userId":"3666"},{"post":"员工","tagId":"BTT32003667","entityType":"staff","sex":1,"departmentId":16,"name":"高秋菊","photo":null,"dutyId":1,"pid":"FUPY13061","id":24139,"userId":"3667"},{"post":"员工","tagId":"BTT32003669","entityType":"staff","sex":1,"departmentId":8,"name":"宋莹","photo":null,"dutyId":1,"pid":"FUPY12012","id":24141,"userId":"3669"},{"post":"员工","tagId":"BTT32003671","entityType":"staff","sex":1,"departmentId":15,"name":"焦俊","photo":null,"dutyId":1,"pid":"FEITYZ0005","id":24142,"userId":"3671"},{"post":"员工","tagId":"BTT32003672","entityType":"staff","sex":1,"departmentId":16,"name":"赵静","photo":null,"dutyId":1,"pid":"FUPY15001","id":24143,"userId":"3672"},{"post":"员工","tagId":"BTT32003673","entityType":"staff","sex":1,"departmentId":16,"name":"曹桂明","photo":null,"dutyId":1,"pid":"FUPY16003","id":24144,"userId":"3673"},{"post":"员工","tagId":"BTT32003676","entityType":"staff","sex":1,"departmentId":16,"name":"刘婷婷","photo":null,"dutyId":1,"pid":"FUPY14052","id":24145,"userId":"3676"},{"post":"员工","tagId":"BTT32003677","entityType":"staff","sex":1,"departmentId":4,"name":"王登平","photo":null,"dutyId":1,"pid":"FUPY20005","id":24146,"userId":"3677"},{"post":"员工","tagId":"BTT32003678","entityType":"staff","sex":1,"departmentId":4,"name":"陈兵","photo":null,"dutyId":1,"pid":"FUPY14072","id":24147,"userId":"3678"},{"post":"员工","tagId":"BTT32003679","entityType":"staff","sex":1,"departmentId":21,"name":"俞春莲","photo":null,"dutyId":1,"pid":"FUPY17010","id":24148,"userId":"3679"},{"post":"员工","tagId":"BTT32003680","entityType":"staff","sex":1,"departmentId":11,"name":"潘菲菲","photo":null,"dutyId":1,"pid":"YC10051","id":24149,"userId":"3680"},{"post":"员工","tagId":"BTT32003681","entityType":"staff","sex":1,"departmentId":23,"name":"朱杰","photo":null,"dutyId":1,"pid":"FUPY19014","id":24150,"userId":"3681"},{"post":"员工","tagId":"BTT32003682","entityType":"staff","sex":1,"departmentId":4,"name":"强晨晨","photo":null,"dutyId":1,"pid":"FUPY14087","id":24151,"userId":"3682"},{"post":"员工","tagId":"BTT32003683","entityType":"staff","sex":1,"departmentId":4,"name":"豆彪","photo":null,"dutyId":1,"pid":"FUPY14060","id":24152,"userId":"3683"},{"post":"员工","tagId":"BTT32003685","entityType":"staff","sex":1,"departmentId":15,"name":"江启明","photo":null,"dutyId":1,"pid":"FEITYZ0002","id":24153,"userId":"3685"},{"post":"员工","tagId":"BTT32003689","entityType":"staff","sex":1,"departmentId":24,"name":"陈静","photo":null,"dutyId":1,"pid":"FUPY15024","id":24155,"userId":"3689"},{"post":"员工","tagId":"BTT32003693","entityType":"staff","sex":1,"departmentId":16,"name":"顾玉婷","photo":null,"dutyId":1,"pid":"FUPY13062","id":24156,"userId":"3693"},{"post":"员工","tagId":"BTT32003695","entityType":"staff","sex":1,"departmentId":4,"name":"李明","photo":null,"dutyId":1,"pid":"FUPY15038","id":24157,"userId":"3695"},{"post":"员工","tagId":"BTT32003696","entityType":"staff","sex":1,"departmentId":18,"name":"蔡雪芳","photo":null,"dutyId":1,"pid":"FUPYYC12005","id":24158,"userId":"3696"},{"post":"员工","phone":"123","tagId":"BTT32003615","entityType":"staff","departmentId":10,"name":"黄成军","photo":null,"dutyId":4,"pid":"FUPY12035","id":24100,"userId":"3615"},{"tagId":"BTTFCC6E902","entityType":"staff","sex":1,"departmentId":15,"photo":"","pid":"FEITYZ1000","userId":"6E902","security":"","post":"","phone":"2515","name":"开发测试手机","dutyId":1,"id":38466},{"tagId":"BTT32003709","entityType":"staff","sex":2,"departmentId":15,"photo":"","pid":"feityz1000","userId":"3709","security":"","post":"","phone":"2515","name":"行政楼测试","dutyId":1,"id":129978},{"post":"员工","phone":"123","tagId":"BTT32003741","entityType":"staff","departmentId":26,"name":"姚凤华","photo":null,"dutyId":3,"pid":"FUPY14055","id":24187,"userId":"3741"},{"post":"员工","phone":"123","tagId":"BTT32003762","entityType":"staff","departmentId":32,"name":"丁小芳","photo":null,"dutyId":3,"pid":"FUPY20009","id":24199,"userId":"3762"},{"post":"员工","phone":"123","tagId":"BTT32003763","entityType":"staff","departmentId":22,"name":"黄仕国","photo":null,"dutyId":3,"pid":"FUPY13004","id":24200,"userId":"3763"},{"post":"员工","phone":"123","tagId":"BTT32003774","entityType":"staff","departmentId":6,"name":"郑晓平","photo":null,"dutyId":3,"pid":"FUPY13108","id":24205,"userId":"3774"},{"post":"员工","phone":"123","tagId":"BTT32003777","entityType":"staff","departmentId":6,"name":"熊用恩","photo":null,"dutyId":3,"pid":"FUPY12033","id":24207,"userId":"3777"},{"post":"员工","phone":"123","tagId":"BTT32003785","entityType":"staff","departmentId":6,"name":"金玉康","photo":null,"dutyId":3,"pid":"FUPY20010","id":24213,"userId":"3785"},{"post":"员工","phone":"123","tagId":"BTT32003765","entityType":"staff","departmentId":30,"name":"陈义林","photo":null,"dutyId":6,"pid":"FUPY13005","id":24202,"userId":"3765"},{"post":"员工","phone":"123","tagId":"BTT32003791","entityType":"staff","departmentId":30,"name":"王伟","photo":null,"dutyId":6,"pid":"FUPY18021","id":24217,"userId":"3791"},{"post":"员工","phone":"123","tagId":"BTT32003794","entityType":"staff","departmentId":30,"name":"刘国季","photo":null,"dutyId":6,"pid":"FUPY14034","id":24220,"userId":"3794"},{"post":"员工","phone":"123","tagId":"BTT32003795","entityType":"staff","departmentId":30,"name":"陈凯","photo":null,"dutyId":6,"pid":"FUPY14002","id":24221,"userId":"3795"},{"post":"员工","phone":"123","tagId":"BTT32003800","entityType":"staff","departmentId":30,"name":"陈旭东","photo":null,"dutyId":6,"pid":"FUPY17006","id":24226,"userId":"3800"},{"post":"员工","phone":"123","tagId":"BTT32003802","entityType":"staff","departmentId":30,"name":"姚航","photo":null,"dutyId":6,"pid":"FUPY18027","id":24228,"userId":"3802"},{"post":"员工","tagId":"BTT32003697","entityType":"staff","sex":1,"departmentId":15,"name":"董玲玲","photo":null,"dutyId":1,"pid":"FEITYZ0036","id":24159,"userId":"3697"},{"post":"员工","phone":"123","tagId":"BTT32003714","entityType":"staff","departmentId":6,"name":"米荣刚","photo":null,"dutyId":3,"pid":"FUPY13083","id":24169,"userId":"3714"},{"post":"员工","tagId":"BTT32003698","entityType":"staff","sex":1,"departmentId":4,"name":"唐宇","photo":null,"dutyId":1,"pid":"FUPY14098","id":24160,"userId":"3698"},{"post":"员工","tagId":"BTT32003699","entityType":"staff","sex":1,"departmentId":16,"name":"许青青","photo":null,"dutyId":1,"pid":"FUPY19017","id":24161,"userId":"3699"},{"post":"员工","tagId":"BTT32003700","entityType":"staff","sex":1,"departmentId":4,"name":"陈疆","photo":null,"dutyId":1,"pid":"FUPY14110","id":24162,"userId":"3700"},{"post":"员工","tagId":"BTT32003707","entityType":"staff","sex":1,"departmentId":4,"name":"曹蓉","photo":null,"dutyId":1,"pid":"FUPY18002","id":24163,"userId":"3707"},{"post":"员工","tagId":"BTT32003708","entityType":"staff","sex":1,"departmentId":4,"name":"赵杰","photo":null,"dutyId":1,"pid":"FUPY14097","id":24164,"userId":"3708"},{"post":"员工","tagId":"BTT32003711","entityType":"staff","sex":1,"departmentId":8,"name":"惠慧","photo":null,"dutyId":1,"pid":"FUPY20008","id":24166,"userId":"3711"},{"post":"员工","tagId":"BTT32003712","entityType":"staff","sex":1,"departmentId":16,"name":"李娟","photo":null,"dutyId":1,"pid":"FUPY14189","id":24167,"userId":"3712"},{"post":"员工","tagId":"BTT32003713","entityType":"staff","sex":1,"departmentId":4,"name":"张云亮","photo":null,"dutyId":1,"pid":"FUPY14078","id":24168,"userId":"3713"},{"post":"员工","tagId":"BTT32003716","entityType":"staff","sex":1,"departmentId":18,"name":"申春","photo":null,"dutyId":1,"pid":"YC14001","id":24170,"userId":"3716"},{"post":"员工","tagId":"BTT32003717","entityType":"staff","sex":1,"departmentId":11,"name":"任园园","photo":null,"dutyId":1,"pid":"FUPY18006","id":24171,"userId":"3717"},{"post":"员工","tagId":"BTT32003718","entityType":"staff","sex":1,"departmentId":25,"name":"万珍珍","photo":null,"dutyId":1,"pid":"FUPY18010","id":24172,"userId":"3718"},{"post":"员工","tagId":"BTT32003720","entityType":"staff","sex":1,"departmentId":20,"name":"张灵","photo":null,"dutyId":1,"pid":"YC17003","id":24173,"userId":"3720"},{"post":"员工","tagId":"BTT32003721","entityType":"staff","sex":1,"departmentId":4,"name":"周志东","photo":null,"dutyId":1,"pid":"FUPY20015","id":24174,"userId":"3721"},{"post":"员工","tagId":"BTT32003722","entityType":"staff","sex":1,"departmentId":14,"name":"魏江南","photo":null,"dutyId":1,"pid":"FUPY13057","id":24175,"userId":"3722"}],"id":1,"jsonrpc":"2.0"}
			 */
			resultMap=JSON.parseObject(resultJO.toString(), Map.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
	public JSONObject postBody(String serverURL, JSONObject bodyParamJO, String method, HttpServletRequest request)
			throws IOException {
		StringBuffer sbf = new StringBuffer(); 
		String strRead = null; 
		URL url = new URL(serverURL); 
		HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
		
		//connection.setInstanceFollowRedirects(false); 
		
		HttpSession session = request.getSession();
		if(serverURL.contains("service")) {
			//connection.setRequestProperty("Cookie", "JSESSIONID=849CB322A20324C2F7E11AD0A7A9899E;Path=/position; Domain=139.196.143.225; HttpOnly;");
			//connection.setRequestProperty("Cookie", "JSESSIONID=E1CD97E8E9AA306810805BFF21D7FD7D; Path=/position; HttpOnly");
			String cookie = null;
			Object epLoginUserObj = session.getAttribute("epLoginUser");
			//System.out.println("LoginUserObj==="+LoginUserObj);
			if(epLoginUserObj!=null) {
				EpLoginUser epLoginUser = (EpLoginUser)epLoginUserObj;
				cookie = epLoginUser.getCookie();
			}
			
			if(cookie==null)
				cookie = epLoginUserService.getCookieByUserId(TEST_USER_Id);
				
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
		
		if(serverURL.contains("public")&&"login".equals(method)) {
			if(!checkCookieInSession(session)) {
				getCookieFromHeader(connection,session);
			}
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
	
	/**
	 * 验证登录信息是否有效
	 * @param request
	 * @return
	 */
	/*
	 * 
	@RequestMapping(value="/checkCookieValid")
	@ResponseBody
	public Map<String, Object> checkCookieValid(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> soerMap = summaryOnlineEntity(request);
		if("no".equals(soerMap.get("status").toString())) {
			resultMap.put("status", "no");
			resultMap.put("message", "登录信息已过期，请重新登录");
		}
		else {
			resultMap.put("status", "ok");
		}
		return resultMap;
	}
	*/
	
	public boolean checkCookieInSession(HttpSession session) {
		Object epLoginUserObj = session.getAttribute("epLoginUser");
		if(epLoginUserObj==null)
			return false;
		else {
			EpLoginUser epLoginUser = (EpLoginUser)epLoginUserObj;
			if(epLoginUser==null)
				return false;
			else {
				String cookie = epLoginUser.getCookie();
				if(StringUtils.isEmpty(cookie))
					return false;
				else
					return true;
			}
		}
	}
	
	public String getCookieFromHeader(HttpURLConnection connection,HttpSession session) {
		Map<String,List<String>> map = connection.getHeaderFields();
		for (String key : map.keySet()) {
			String value = map.get(key).get(0);
			if(value.contains("JSESSIONID=")) {
				 System.out.println("key==="+value);
				 EpLoginUser epLoginUser=new EpLoginUser();
				 epLoginUser.setCookie(value);
				 session.setAttribute("epLoginUser", epLoginUser);
			}
		}
		return "";
	}
}
