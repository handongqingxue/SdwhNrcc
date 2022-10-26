package com.sdwhNrcc.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
//https://blog.csdn.net/young_YangY/article/details/84446559/
//https://www.codenong.com/cs106100031/

@Component
public class ServerReceiver {
	
	static {
		System.out.println("3333333333");
		try {
			test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final Logger log=LoggerFactory.getLogger(ServerReceiver.class);
	
	@RabbitHandler
	@RabbitListener(queues="tenant_msg_F4A1D30F_sc22050664")
	public void process(Message message) {
		
		//String msg,Channel channel,Message message
		//http://localhost:15672/#/queues/%2F/tenant_msg_F4A1D30F_sc22050664
		//String str = new String(message.getBody());
		//JSON.parseObject();
		//System.out.println("str==="+str);
		System.out.println("str===");
	}
	
	public static void test() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        //factory.setHost("222.173.86.130");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
       

        // 2.创建连接
        Connection connection = factory.newConnection();

        // 3.创建频道
        Channel channel = connection.createChannel();

        // 4.创建队列
        //channel.queueDeclare("tenant_msg_F4A1D30F_sc22050664", true, false, false, null);

        // 5. 接收消息
        channel.basicConsume("tenant_msg_F4A1D30F_sc22050664", true, new DefaultConsumer(channel) {

                // 回调方法,当收到消息之后,会自动执行该方法
            public void handleDelivery(String consumerTag, Envelope envelope,
                                        AMQP.BasicProperties properties,byte[] body) 
                                        throws IOException {

            System.out.println("body：" + new String(body));
            }
        });
        // 不释放资源,让rabbitmq一直监听
        
    }
}
