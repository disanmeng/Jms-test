package com.jms.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class TopicProducer {
	private static final String url="tcp://127.0.0.1:61616";

	private static final String topicName="topicName";
	
	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(url);
		Connection conn=connectionFactory.createConnection();
		conn.start();
		//4.创建会话（第一个参数：是否在事务中处理）
		Session session=conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination des=session.createTopic(topicName);
		
		MessageProducer producer=session.createProducer(des);
		
		for(int i=0;i<300;i++){
			TextMessage text=session.createTextMessage("text"+i);
			producer.send(text);
			System.out.println("发送消息"+text.getText());
		}
		
		conn.close();
	}
}
