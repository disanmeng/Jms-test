package com.jms.queue;

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


public class AppConsumer {

	private static final String url="url://127.0.0.1:61616";
	private static final String queueName="queue-test";
	
	public static void main(String[] args) throws JMSException {
		
		//1.创建连接工厂
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(url);
		//2.创建数据库连接
		Connection conn=connectionFactory.createConnection();
		
		//3.启动连接
		conn.start();
		//4.创建会话（第一个参数：是否在事务中处理）
		Session session=conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.创建一个目标
		Destination destination=session.createQueue(queueName);
		
		//6.创建一个消费者
		MessageConsumer consumer=session.createConsumer(destination);
		
		//7.创建一个监听器
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage text=(TextMessage) message;
				
				try {
					System.out.println("接收到的消息："+text.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//8.关闭连接
//		conn.close();
	}
}
