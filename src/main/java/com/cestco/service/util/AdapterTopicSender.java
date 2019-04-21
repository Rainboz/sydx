package com.cestco.service.util;

import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import com.tibco.tibjms.TibjmsTopicConnectionFactory;

public class AdapterTopicSender {
	private String serverUrl = "tcp://192.169.110.201:7222";
    private String userName = "admin";
    private String password  = "admin";
    private String topicName = "query.sydx";

    private TopicConnection connection;
    private TopicSession session ;
    private Topic topic ;
    private TopicPublisher msgProducer;
    
    private static AdapterTopicSender sender = new AdapterTopicSender();
    
    public AdapterTopicSender(){
    }
    
    public static AdapterTopicSender getInstance(){
      return sender;
    }
    
    
    public void createTopic(){
   
      try
      {
        TopicConnectionFactory factory = new TibjmsTopicConnectionFactory(serverUrl);
        connection = factory.createTopicConnection(userName,password);
        /* create the session */
        session = connection.createTopicSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);
   
        topic = session.createTopic(topicName);
        /* create the producer */
        msgProducer = session.createPublisher(topic);
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
    }
  
     public void sendMessage(String message)
    {
      try
      {
  
        if (connection == null || session == null || topic == null)
        {
          createTopic();
        }
  
        if (topic != null)
        {
   
//            com.cesecsh.util.Qmgrc qmgrc = new com.cesecsh.util.Qmgrc();
//            message =  getBASE64(message); 
//            byte[] aa = qmgrc.setQueueMsg(message);
//            
//            javax.jms.BytesMessage message1 = session.createBytesMessage();
//            
//            message1.writeBytes(aa);
//            msgProducer.publish(message1);
            
            TextMessage   msg = session.createTextMessage();
            msg.setText(message);
           /* publish message */
            msgProducer.publish(msg);
          System.out.println("factory= send="+msgProducer);
        }
      }
      catch (Exception e)
      {
        try
        {
          if (connection != null)
            connection.close();
          if (session == null)
            session.close();
          if (topic != null)
            topic = null;
        }
        catch (Exception ex)
        {
        }
      }
    }

    public void setServerUrl(String serverUrl)
    {
      this.serverUrl = serverUrl;
    }

    public void setUserName(String userName)
    {
      this.userName = userName;
    }

    public void setPassword(String password)
    {
      this.password = password;
    }

    public void setTopicName(String topicName)
    {
      this.topicName = topicName;
    }
    
    public static void main(String[] args){
    	
    	String text = "sydx#test#13#2018-08-08 09:30:00#123";
    	AdapterTopicSender t = new AdapterTopicSender();
    	t.sendMessage(text);
        System.out.println("out");
    }
}
