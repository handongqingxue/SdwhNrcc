package com.sdwhNrcc.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.v3_1.*;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
//https://blog.csdn.net/young_YangY/article/details/84446559/
//https://www.codenong.com/cs106100031/
import org.springframework.beans.factory.annotation.Autowired;

//这个类用来接收人员位置的推送信息和报警推送信息
@Component
@Controller
@RequestMapping(ServerReceiver.MODULE_NAME)
public class ServerReceiver {
	
	private static final Logger log=LoggerFactory.getLogger(ServerReceiver.class);
	public static final String MODULE_NAME="/serverReceiver";
	//private static final boolean IS_TEST=true;
	private static final boolean IS_TEST=false;
	
	@Autowired
	private PositionService positionService;
	@Autowired
	private KeyWarningService keyWarningService;
	@Autowired
	private ApiLogV3_1Service apiLogService;
	
	/**
	 * 这个接收推送消息的方法只能在和发送消息是同一个项目的情况下使用，现在咱项目接收的是真源项目那边发送的消息，这个方法暂时不用了
	@RabbitHandler
	@RabbitListener(queues="tenant_msg_F4A1D30F_sc22050664")
	public void process(Message message) {
		
		//String msg,Channel channel,Message message
		//String str = new String(message.getBody());
		//JSON.parseObject();
		//System.out.println("str==="+str);
		System.out.println("str===");
	}
	*
	**/

	@RequestMapping(value="/receiveMessage")
	@ResponseBody
	public Map<String, Object> receiveMessage(HttpServletRequest request) {

		//https://blog.csdn.net/Bb15070047748/article/details/112184411
		//https://blog.csdn.net/sinat_31583645/article/details/116766214
		//https://blog.csdn.net/lovekjl/article/details/108616353
		//浏览器里查看推送情况的链接:http://localhost:15672/#/queues/%2F/tenant_msg_F4A1D30F_sc22050664
		System.out.println("receiveMessage。。。");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
        	
        	switchSystem(request);

			String databaseName = request.getAttribute("databaseName").toString();
        	if(IS_TEST) {//测试部分，在本地调用模拟接收推送消息
        		//String bodyJOStr = "{\"method\":\"position\",\"params\":{\"absolute\":true,\"altitude\":1.0,\"areaId\":10023,\"beacons\":\"BTI2501FEA6(15000)\",\"entityType\":\"staff\",\"floor\":1,\"inDoor\":1662096250425,\"latitude\":37.041073098658146,\"locationTime\":1666764909608,\"longitude\":119.57507922005624,\"out\":false,\"rootAreaId\":1,\"silent\":false,\"speed\":0.0,\"stateTime\":1666764894643,\"tagId\":\"BTT38206876\",\"volt\":4100,\"voltUnit\":\"mV\",\"x\":81.184,\"y\":176.867,\"z\":0.0}}";
        		String bodyJOStr = "{\"method\":\"keyWarning\",\"params\":{\"tagId\":\"BTT34089197\",\"entityId\":1791,\"areaId\":10023,\"raiseTime\":1666764894643,\"x\":81.184,\"y\":176.867,\"z\":0.0,\"floor\":1}}";
        		com.alibaba.fastjson.JSONObject bodyJO = JSON.parseObject(bodyJOStr);
        		String method = bodyJO.getString("method");
        		if("position".equals(method)) {
        			JSONObject paramsJO = bodyJO.getJSONObject("params");
        			insertPositionData(paramsJO,databaseName);
        		}
				else if("keyWarning".equals(method)) {
					JSONObject paramsJO = bodyJO.getJSONObject("params");
					insertKeyWarningData(paramsJO,databaseName);
				}
        	}
        	else {//发布服务器后根据队列接收推送消息
        		System.out.println("获取推送信息。。。");
				ConnectionFactory factory = new ConnectionFactory();
				
				String connFactoryUsername = request.getAttribute("connFactoryUsername").toString();
				String connFactoryPassword = request.getAttribute("connFactoryPassword").toString();
				//factory.setHost("222.173.86.130");
				factory.setHost(Constant.CONN_FACTORY_HOST);
				factory.setPort(Constant.CONN_FACTORY_PORT);
				factory.setUsername(connFactoryUsername);
				factory.setPassword(connFactoryPassword);
	      
	
				// 2.创建连接
				Connection connection = factory.newConnection();
	
				// 3.创建频道
				Channel channel = connection.createChannel();
	
				// 4.创建队列(企业服务器上已经创建队列了，这里就没必要创建，否则会报错)
				//channel.queueDeclare("tenant_msg_F4A1D30F_sc22050664", true, false, false, null);
	
				// 5. 接收消息
				//tenant_msg_F4A1D30F_sc22050664
				String clientSecret = request.getAttribute("clientSecret").toString();
				String tenantId = request.getAttribute("tenantId").toString();
				System.out.println("接收推送消息的队列===tenant_msg_"+clientSecret+"_"+tenantId);
				channel.basicConsume("tenant_msg_"+clientSecret+"_"+tenantId, true, new DefaultConsumer(channel) {
	
				        // 回调方法,当收到消息之后,会自动执行该方法
				    public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties,byte[] body) throws IOException {
					    //body:{"method":"position","params":{"absolute":true,"altitude":1.0,"areaId":10023,"beacons":"BTI2501FEA6(15000)","entityType":"staff","floor":1,"inDoor":1662096250425,"latitude":37.041073098658146,"locationTime":1666764909608,"longitude":119.57507922005624,"out":false,"rootAreaId":1,"silent":false,"speed":0.0,"stateTime":1666764894643,"tagId":"BTT38206876","volt":4100,"voltUnit":"mV","x":81.184,"y":176.867,"z":0.0}}
					    String bodyJOStr = new String(body);
					    //System.out.println("handleDelivery...");
					    //System.out.println("bodyJOStr===" + bodyJOStr);
						com.alibaba.fastjson.JSONObject bodyJO = JSON.parseObject(bodyJOStr);
						String method = bodyJO.getString("method");
						if("position".equals(method)) {
							JSONObject paramsJO = bodyJO.getJSONObject("params");
							insertPositionData(paramsJO,databaseName);
							addApiLog(createApiLogByParams(method,bodyJOStr,"ok"),databaseName);
						}
						else if("keyWarning".equals(method)) {
							JSONObject paramsJO = bodyJO.getJSONObject("params");
							insertKeyWarningData(paramsJO,databaseName);
							addApiLog(createApiLogByParams(method,bodyJOStr,"ok"),databaseName);
						}
				    }
				});
				
				// 不释放资源,让rabbitmq一直监听
				//如果通道不关闭会一直监听我们的队列
		        //channel.close();
		        //connection.close();
        	}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
        	return resultMap;
        }
    }

	@RequestMapping(value="/insertPositionData")
	@ResponseBody
	public Map<String, Object> insertPositionData(JSONObject paramsJO,String databaseName) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			Boolean absolute = paramsJO.getBoolean("absolute");
			Float altitude = paramsJO.getFloat("altitude");
			Integer areaId = paramsJO.getInteger("areaId");
			String beacons = paramsJO.getString("beacons");
			String entityType = paramsJO.getString("entityType");
			Integer floor = paramsJO.getInteger("floor");
			Long inDoor = paramsJO.getLong("inDoor");
			Double latitude = paramsJO.getDouble("latitude");
			if(latitude==null)
				latitude=(double)0;
			Integer locationTime = paramsJO.getInteger("locationTime");
			Double longitude = paramsJO.getDouble("longitude");
			if(longitude==null)
				longitude=(double)0;
			Boolean out = paramsJO.getBoolean("out");
			Integer rootAreaId = paramsJO.getInteger("rootAreaId");
			Boolean silent = paramsJO.getBoolean("silent");
			Float speed = paramsJO.getFloat("speed");
			Integer stateTime = paramsJO.getInteger("stateTime");
			String tagId = paramsJO.getString("tagId");
			Integer volt = paramsJO.getInteger("volt");
			String voltUnit = paramsJO.getString("voltUnit");
			Float x = paramsJO.getFloat("x");
			Float y = paramsJO.getFloat("y");
			Float z = paramsJO.getFloat("z");

			Position position = new Position();
			position.setAbsolute(absolute);
			position.setAltitude(altitude);
			position.setAreaId(areaId);
			position.setBeacons(beacons);
			position.setEntityType(entityType);
			position.setFloor(floor);
			position.setInDoor(inDoor);
			position.setLatitude(latitude);
			position.setLocationTime(locationTime);
			position.setLongitude(longitude);
			position.setOut(out);
			position.setRootAreaId(rootAreaId);
			position.setSilent(silent);
			position.setSpeed(speed);
			position.setStateTime(stateTime);
			position.setTagId(tagId);
			position.setVolt(volt);
			position.setVoltUnit(voltUnit);
			position.setX(x);
			position.setY(y);
			position.setZ(z);
			
			//if("BTT34058043".equals(tagId))
				//System.out.println("longitude="+longitude+",latitude="+latitude);

			positionService.add(position,databaseName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}

	@RequestMapping(value="/insertKeyWarningData")
	@ResponseBody
	public Map<String, Object> insertKeyWarningData(JSONObject paramsJO,String databaseName) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			String lzqId = null;
			switch (databaseName) {
			case Constant.DATABASE_NAME_ZBXQHGYXGS:
				lzqId = StringUtil.createUUID();
				break;
			}
			String tagId = paramsJO.getString("tagId");
			Integer entityId = paramsJO.getInteger("entityId");
			Integer areaId = paramsJO.getInteger("areaId");
			Long raiseTime = paramsJO.getLong("raiseTime");
			Float x = paramsJO.getFloat("x");
			Float y = paramsJO.getFloat("y");
			Float z = paramsJO.getFloat("z");
			Integer floor = paramsJO.getInteger("floor");
			
			KeyWarning keyWarning=new KeyWarning();
			keyWarning.setLzqId(lzqId);
			keyWarning.setTagId(tagId);
			keyWarning.setEntityId(entityId);
			keyWarning.setAreaId(areaId);
			keyWarning.setRaiseTime(raiseTime);
			keyWarning.setX(x);
			keyWarning.setY(y);
			keyWarning.setZ(z);
			keyWarning.setFloor(floor);
			
			keyWarningService.add(keyWarning,databaseName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
	
	/**
	 * 添加日志记录
	 * @param al
	 * @return
	 */
	@RequestMapping(value="/addApiLog")
	@ResponseBody
	public Map<String, Object> addApiLog(ApiLog al,String databaseName) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=apiLogService.add(al,databaseName);
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
	 * @return
	 */
	public ApiLog createApiLogByParams(String action,String body,String status){
		
		ApiLog apiLog=new ApiLog();
		apiLog.setAction(action);
		apiLog.setBody(body);
		apiLog.setStatus(status);
		
		return apiLog;
	}
	
	public void switchSystem(HttpServletRequest request) {
		String connFactoryUsername=null;
		String connFactoryPassword=null;
		String tenantId=null;
		String clientSecret=null;
		String databaseName=null;
		int systemFlag=Integer.valueOf(request.getParameter("systemFlag"));
		switch (systemFlag) {
		case Constant.WFPXHGYXGS:
		case Constant.SDFLXCLKJYXGS:
			connFactoryUsername=Constant.CONN_FACTORY_USERNAME2;
			connFactoryPassword=Constant.CONN_FACTORY_PASSWORD2;
			break;
		default:
			connFactoryUsername=Constant.CONN_FACTORY_USERNAME1;
			connFactoryPassword=Constant.CONN_FACTORY_PASSWORD1;
			break;
		}
		request.setAttribute("connFactoryUsername", connFactoryUsername);
		request.setAttribute("connFactoryPassword", connFactoryPassword);
		
		switch (systemFlag) {
		case Constant.WFRZJXHYXGS:
			tenantId=Constant.TENANT_ID_WFRZJXHYXGS;
			clientSecret=Constant.CLIENT_SECRET_WFRZJXHYXGS;
			databaseName=Constant.DATABASE_NAME_WFRZJXHYXGS;
			break;
		case Constant.WFPXHGYXGS:
			tenantId=Constant.TENANT_ID_WFPXHGYXGS;
			clientSecret=Constant.CLIENT_SECRET_WFPXHGYXGS;
			databaseName=Constant.DATABASE_NAME_WFPXHGYXGS;
			break;
		case Constant.SDFLXCLKJYXGS:
			tenantId=Constant.TENANT_ID_SDFLXCLKJYXGS;
			clientSecret=Constant.CLIENT_SECRET_SDFLXCLKJYXGS;
			databaseName=Constant.DATABASE_NAME_SDFLXCLKJYXGS;
			break;
		case Constant.ZBXQHGYXGS:
			tenantId=Constant.TENANT_ID_ZBXQHGYXGS;
			clientSecret=Constant.CLIENT_SECRET_ZBXQHGYXGS;
			databaseName=Constant.DATABASE_NAME_ZBXQHGYXGS;
			break;
		case Constant.SDBFXCLYXGS:
			tenantId=Constant.TENANT_ID_SDBFXCLYXGS;
			clientSecret=Constant.CLIENT_SECRET_SDBFXCLYXGS;
			databaseName=Constant.DATABASE_NAME_SDBFXCLYXGS;
			break;
		case Constant.CYSRHSWKJYXGS:
			tenantId=Constant.TENANT_ID_CYSRHSWKJYXGS;
			clientSecret=Constant.CLIENT_SECRET_CYSRHSWKJYXGS;
			databaseName=Constant.DATABASE_NAME_CYSRHSWKJYXGS;
			break;
		case Constant.SDLTXDKJYXGS:
			tenantId=Constant.TENANT_ID_SDLTXDKJYXGS;
			clientSecret=Constant.CLIENT_SECRET_SDLTXDKJYXGS;
			databaseName=Constant.DATABASE_NAME_SDLTXDKJYXGS;
			break;
		}
		request.setAttribute("tenantId", tenantId);
		request.setAttribute("clientSecret", clientSecret);
		request.setAttribute("databaseName", databaseName);
	}
}
