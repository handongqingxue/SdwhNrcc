package com.sdwhNrcc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
//https://blog.csdn.net/young_YangY/article/details/84446559/
//https://www.codenong.com/cs106100031/

@Component
public class ServerReceiver {

	private static final Logger log=LoggerFactory.getLogger(ServerReceiver.class);
	
	@RabbitHandler
	@RabbitListener(queues="tenant_msg_F4A1D30F_sc22050664")
	public void process(Message message) {
		
		String str = new String(message.getBody());
		//JSON.parseObject();
		System.out.println("str==="+str);
	}
}
