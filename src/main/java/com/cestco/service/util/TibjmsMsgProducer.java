package com.cestco.service.util;
/*
 * =================================================================
 * Copyright (c) 2001-2003 TIBCO Software Inc.
 * All rights reserved.
 * For more information, please contact:
 * TIBCO Software Inc., Palo Alto, California, USA
 *
 * $RCSfile: tibjmsMsgProducer.java,v $
 * $Revision: 1.9 $
 * $Date: 2004/02/06 00:05:02 $
 * =================================================================
 */

/*
 * This is a simple sample of a basic tibjmsMsgProducer.
 *
 * This sample publishes specified message(s) on a specified
 * destination and quits.
 *
 * Notice that the specified destination should exist in your configuration
 * or your topics/queues configuration file should allow
 * creation of the specified topic or queue. Sample configuration supplied with
 * the TIBCO Enterprise Message Service distribution allows creation of any
 * destination.
 *
 * If this sample is used to publish messages into
 * tibjmsMsgConsumer sample, the tibjmsMsgConsumer
 * sample must be started first.
 *
 * If -topic is not specified this sample will use a topic named
 * "topic.sample".
 *
 * Usage:  java tibjmsMsgProducer  [options]
 *                               <message-text1>
 *                               ...
 *                               <message-textN>
 *
 *  where options are:
 *
 *   -server    <server-url>  Server URL.
 *                            If not specified this sample assumes a
 *                            serverUrl of "tcp://localhost:7222"
 *   -user      <user-name>   User name. Default is null.
 *   -password  <password>    User password. Default is null.
 *   -topic     <topic-name>  Topic name. Default value is "topic.sample"
 *   -queue     <queue-name>  Queue name. No default
 *
 */

import java.util.*;

import javax.jms.*;

public class TibjmsMsgProducer{
    /*-----------------------------------------------------------------------
     * Parameters
     *----------------------------------------------------------------------*/

    String          serverUrl    = "tcp://192.169.110.201:7222";
    String          userName     = "admin";
    String          password     = "admin";
    String          name         = "topic.shdx";
    @SuppressWarnings("rawtypes")
	Vector          data         = new Vector();
    boolean         useTopic     = true;

    /*-----------------------------------------------------------------------
     * Variables
     *----------------------------------------------------------------------*/
    Connection      connection   = null;
    Session         session      = null;
    MessageProducer msgProducer  = null;
    Destination     destination  = null;

    public TibjmsMsgProducer (String[] args) {
        try {
            TextMessage msg;

            System.err.println("Publishing to destination '"+name+"'\n");

            ConnectionFactory factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);
            System.out.println("factory= ssdfs="+factory);
            connection = factory.createConnection(userName,password);
            System.out.println("factory= ssdfs="+connection);
            /* create the session */
            session = connection.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);
            System.out.println("factory= ssdfs="+connection);
            /* create the destination */
            if(useTopic)
                destination = session.createTopic(name);
            else
                destination = session.createQueue(name);

            /* create the producer */
            msgProducer = session.createProducer(null);

            /* publish messages */
      //      for (i = 0; i<data.size(); i++)
            {
                /* create text message */
                msg = session.createTextMessage();
//                BytesMessage bm = session.createBytesMessage();
//                bm.writeBytes(value);
                /* set message text */
      //          String text = "ALL@3@2@480@300@control/alarmui";
  //              String text="spjk#192.168.1.108#37777#admin#admin#11";
                //String text= "ALL@3@1@480@400@common/jtmonitor/huojing.jsp?comCode=xfxt&devCode=1-13-19&infCode=1" ;
//                String text = "spjk#192.168.2.31#8000#admin#12345#11";
              //  String text = "spjk#192.168.2.31#37777#admin#admin#2";
       //      text="192.168.2.11:1";
//                text="ALL@1@4@480@400@http://192.168.1.2:6060/ibms/common/export/exportToLocal.jsp?name=制冷站变电站-制冷站变电站变压器1T1&url=d:\\export\\history-20151117114553.xls";
                
//                text="127.0.0.1#1#E:/新建文件夹/dqhz.exe com3 127.0.0.1 queue.data";
//                text="127.0.0.1#0#c#dqhz.exe";
//                
//                text="127.0.0.1#1#java -classpath E:/workspace/aaa/bin;E:/OFBIZ2.0/CATALINA/shared/lib/jms.jar;E:/OFBIZ2.0/CATALINA/shared/lib/tibjms.jar;E:/OFBIZ2.0/CATALINA/shared/lib/ibms.jar tibjmsMsgProducer2 ";
//                text="127.0.0.1#1#e:/aa.bat";
                String text = "sydx#test#13#2018-08-08 09:30:00#123";
//                
                System.out.println(text);
//                msg.setText((String)data.elementAt(i));

                msg.setText(String.valueOf(text));
                /* publish message */
    //     while (true){
                msgProducer.send(destination, msg);
//                try
//                {
//                  Thread.sleep(10000);
//                }
//                catch (InterruptedException e)
//                {
//                  // TODO Auto-generated catch block
//                  e.printStackTrace();
//                }
      //   }
//                System.out.println("finish");
          //      System.err.println("Published message: "+data.elementAt(i));
            }

            /* close the connection */
//            connection.close();
        } 
        catch (JMSException e) 
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /*-----------------------------------------------------------------------
    * usage
    *----------------------------------------------------------------------*/
    private void usage()
    {
        System.err.println("\nUsage: java tibjmsMsgProducer [options] [ssl options]");
        System.err.println("                                <message-text-1>");
        System.err.println("                                [<message-text-2>] ...");
        System.err.println("\n");
        System.err.println("   where options are:");
        System.err.println("");
        System.err.println("   -server   <server URL>  - EMS server URL, default is local server");
        System.err.println("   -user     <user name>   - user name, default is null");
        System.err.println("   -password <password>    - password, default is null");
        System.err.println("   -topic    <topic-name>  - topic name, default is \"topic.sample\"");
        System.err.println("   -queue    <queue-name>  - queue name, no default");
        System.err.println("   -help-ssl                 - help on ssl parameters");
        System.exit(0);
    }

    /*-----------------------------------------------------------------------
     * parseArgs
     *----------------------------------------------------------------------*/
    @SuppressWarnings("unchecked")
	void parseArgs(String[] args)
    {
        int i=0;

        while(i < args.length)
        {
            if (args[i].compareTo("-server")==0)
            {
                if ((i+1) >= args.length) usage();
                serverUrl = args[i+1];
                i += 2;
            }
            else
            if (args[i].compareTo("-topic")==0)
            {
                if ((i+1) >= args.length) usage();
                name = args[i+1];
                i += 2;
            }
            else
            if (args[i].compareTo("-queue")==0)
            {
                if ((i+1) >= args.length) usage();
                name = args[i+1];
                i += 2;
                useTopic = false;
            }
            else
            if (args[i].compareTo("-user")==0)
            {
                if ((i+1) >= args.length) usage();
                userName = args[i+1];
                i += 2;
            }
            else
            if (args[i].compareTo("-password")==0)
            {
                if ((i+1) >= args.length) usage();
                password = args[i+1];
                i += 2;
            }
            else
            if (args[i].compareTo("-help")==0)
            {
                usage();
            }
            else
            if (args[i].compareTo("-help-ssl")==0)
            {
//                tibjmsUtilities.sslUsage();
            }
            else
            if(args[i].startsWith("-ssl"))
            {
                i += 2;
            }
            else
            {
                data.addElement(args[i]);
                i++;
            }
        }
    }
    
    /*-----------------------------------------------------------------------
     * main
     *----------------------------------------------------------------------*/
    public static void main(String[] args)
    {
   
      
    	TibjmsMsgProducer t = new TibjmsMsgProducer(args);
        
        System.out.println("out");
    }
}

