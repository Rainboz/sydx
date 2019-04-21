package com.cestco.service.websocket;

import java.net.URI;

import org.apache.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import com.cestco.service.data.SyLoginSessionID;

public class MyWebSocket extends WebSocketClient {
private static Logger logger = Logger.getLogger(MyWebSocket.class);
	
	public MyWebSocket(URI serverUri) {
		super(serverUri,new Draft_6455());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		// TODO Auto-generated method stub
		logger.info("链接已关闭");
		MyWebSocketClient.getInstance().heartbeat = 0;
		SyLoginSessionID.getInstance().moveSyLoginSessionID();
	}

	@Override
	public void onError(Exception arg0) {
		// TODO Auto-generated method stub
		logger.info("发生错误已关闭");
		MyWebSocketClient.getInstance().heartbeat = 0;
		SyLoginSessionID.getInstance().moveSyLoginSessionID();
	}

	@Override
	public void onMessage(String arg0) {
		// TODO Auto-generated method stub
		if(arg0!=null)
			logger.info("收到消息"+arg0);
	}

	@Override
	public void onOpen(ServerHandshake arg0) {
		// TODO Auto-generated method stub
		logger.info("打开链接");
		MyWebSocketClient.getInstance().heartbeat = 1;
	}
}
