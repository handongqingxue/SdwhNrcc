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
