package com.jms.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class AppProducer {
	
	private static final String url="tcp://127.0.0.1:61616";
	private static final String queueName="queue-test";
	
	public static void main(String[] args) throws JMSException {
		//1.创建ConnectionFactory
			ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(url);
		//2.创建连接
			Connection connection=connectionFactory.createConnection();
		//3.启动连接
			connection.start();
		//4.创建会话（第一个参数：是否在事务中处理）
			Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.创建一个目标
			Destination destination=session.createQueue(queueName);
		//6.创建一个生成者
			MessageProducer producer=session.createProducer(destination);
		
			for(int i=0;i<500;i++){
				//7.创建消息
				TextMessage text=session.createTextMessage("text"+i);
				//8.发布消息
				producer.send(text);
				System.out.println("发送消息"+text.getText());
			}
			
			//9.关闭连接
			connection.close();
	}
}
