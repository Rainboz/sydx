package com.cestco.service.websocket;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
import org.java_websocket.WebSocket.READYSTATE;

import com.cestco.service.data.SyLoginSessionID;

public class MyWebSocketClient {
	private MyWebSocketClient () {}
	
	private static Logger logger = Logger.getLogger(MyWebSocketClient.class);
	private static MyWebSocketClient instance = new MyWebSocketClient();
	public static MyWebSocketClient getInstance() {
		return instance;
	}
	public static MyWebSocket webSocketclient;
	
	// 0代表链接断开或者异常 1代表链接中.2代表正在连接
	public int heartbeat = 0;
	
	public void connect(String address, String port) {
		try {
			webSocketclient = new MyWebSocket(new URI("ws://"+ address + ":" + port + "/api/ws?sid="+SyLoginSessionID.getInstance().getSyLoginSessionID()));
			logger.info("webSocketclient:"+webSocketclient);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webSocketclient.connect();
		heartbeat=2;
	}

	public void sendHeartBeat() {
		if (webSocketclient.getReadyState().equals(READYSTATE.OPEN))
			webSocketclient.send("0|login|heatr-beat|1|12|1||zh_CN||0|");
	}
	
//	public void sendMSG() {
//		if (webSocketclient.getReadyState().equals(READYSTATE.OPEN))
//			webSocketclient.send("0|<CMD>|count|1|{\"page\":\"1\"}");
//	}
}
