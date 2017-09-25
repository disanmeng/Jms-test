package com.jms.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicConsumer {
	
	private static final String url="tcp://127.0.0.1:61616";
	private static final String topicName="topicName";
	
	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(url);
		Connection conn=connectionFactory.createConnection();
		conn.start();
		//4.创建会话（第一个参数：是否在事务中处理）
		Session session=conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination des=session.createTopic(topicName);
		
		MessageConsumer consumer= session.createConsumer(des);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage text=(TextMessage) message;
				try {
					System.out.println("接收到的主题下的消息"+text.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
}
