package com.sdwhNrcc.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sdwhNrcc.entity.udp.*;
import com.sdwhNrcc.service.udp.*;

@Controller
@RequestMapping(UDPReceiver.MODULE_NAME)
public class UDPReceiver {

	public static final String MODULE_NAME="/udpReceiver";
	
	@Autowired
	private LocationUDPService locationUDPService;

	@RequestMapping(value="/receiveData")
	@ResponseBody
	public Map<String, Object> receiveData() {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
        	//创建接收端socket
			DatagramSocket socket = new DatagramSocket(10003);
	        while(true) {
		        System.out.println("待接收数据...");
		        //接收数据报
		        byte[] buffer = new byte[1024];
		        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
		        //将接收到的数据转换成String类型
				String message = new String(packet.getData(), 0, packet.getLength());
		        //输出接收到的数据
		        System.out.println("Received message: " + message);
		        
		        int methodStartLoc = message.indexOf(",{\"method\"");
		        int methodEndLoc = message.lastIndexOf(",");
		        String content = message.substring(methodStartLoc+1, methodEndLoc);
	    		System.out.println("content="+content);
		        com.alibaba.fastjson.JSONObject bodyJO = JSON.parseObject(content);
	    		String method = bodyJO.getString("method");
	    		System.out.println("method="+method);
	    		System.out.println("true="+"Location".equals(method));
	    		if("Location".equals(method)) {
	    			com.alibaba.fastjson.JSONObject paramsJO = bodyJO.getJSONObject("params");
	    			insertLocationData(paramsJO,Constant.DATABASE_NAME_SDXJYJXHXPYXGS);
	    		}
	        }
	        //关闭socket
	        //socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
	
	@RequestMapping(value="/insertLocationData")
	@ResponseBody
	public Map<String, Object> insertLocationData(com.alibaba.fastjson.JSONObject paramsJO,String databaseName) {

		System.out.println("insertLocationData..........");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			String uid=paramsJO.getString("uid");
			String userId=paramsJO.getString("userId");
			String tenantId=paramsJO.getString("tenantId");
			Integer floor = paramsJO.getInteger("floor");
			Double speed = paramsJO.getDouble("speed");
			Double latitude = paramsJO.getDouble("latitude");
			if(latitude==null)
				latitude=(double)0;
			Double longitude = paramsJO.getDouble("longitude");
			if(longitude==null)
				longitude=(double)0;
			
			LocationUDP locationUDP = new LocationUDP();
			locationUDP.setUid(uid);
			locationUDP.setUserId(userId);
			locationUDP.setFloor(floor);
			locationUDP.setSpeed(speed);
			locationUDP.setLatitude(latitude);
			locationUDP.setLongitude(longitude);
			locationUDP.setTenantId(tenantId);
			
			//if("BTT34058043".equals(tagId))
				//System.out.println("longitude="+longitude+",latitude="+latitude);

			locationUDPService.add(locationUDP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultMap;
		}
	}
}
