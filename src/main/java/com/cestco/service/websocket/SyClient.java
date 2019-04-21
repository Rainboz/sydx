package com.cestco.service.websocket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import com.cestco.service.data.SyLoginSessionID;
import com.cestco.service.http.MyHttpClient;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
@Component
class SyClient implements DisposableBean, Runnable {
private static Logger logger = Logger.getLogger(SyClient.class);
private Thread thread;

SyClient(){
	this.thread = new Thread(this);
	this.thread.start();
}

	@Override
	public void run() {
		logger.info("running.........");
		if(syClient("http://112.16.113.5:8800/api/json?cmd=login&ctrl=user&version=1&lang=zh_CN",
				"admin",
				"188a2d43c6abca68f35ca8c4fe6be161")){
			
			ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
	        executor.scheduleAtFixedRate(new Runnable() {
	        	
	            @Override
	            public void run() {
					if (MyWebSocketClient.getInstance().heartbeat ==1 ) {
						MyWebSocketClient.getInstance().sendHeartBeat();
						logger.info("心跳检测:连接中");
						if (!MyHttpClient.runSign) {
							MyHttpClient.toGetData();
						}
					}
					if (MyWebSocketClient.getInstance().heartbeat ==2 ) {
						logger.info("心跳检测:正在连接");
					}
					if (MyWebSocketClient.getInstance().heartbeat ==0 ) {
						logger.info("未连接");
						SyClient.syClient("http://112.16.113.5:8800/api/json?cmd=login&ctrl=user&version=1&lang=zh_CN","admin","188a2d43c6abca68f35ca8c4fe6be161");
					}
	            }
	            
	        }, 10, 60, TimeUnit.SECONDS);
		}
		logger.info("end.........");
	}

	public static boolean syClient(String url,String user,String password) {
		SyLoginSessionID.getInstance().setSyLoginSessionID(login(url,user,password));
		if(SyLoginSessionID.getInstance().getSyLoginSessionID()==null){
			logger.info("SID获取失败...");
			return false;
		}
		
		MyWebSocketClient.getInstance().connect("112.16.113.5", "8800");
		return true;
	}
	
	/**
	 * 用户登录获取SID
	 * @param url "http://sccs web server:port/api/json?cmd=login&ctrl=user&version=1&lang=zh_CN"
	 * @param user 
	 * @param password  
	 * @return
	 */
	@SuppressWarnings({ "resource" })
	private static String login(String url,String user,String password){
		// 系统分配SID
		String axWebSID = "";
		// Create the HttpClient object and context
		HttpContext context = new BasicHttpContext();
		HttpClient httpclient = new DefaultHttpClient();
		// Set then cookie
		CookieStore cookieStore = new BasicCookieStore();
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = null;
		try{
			String data = "{\"langKey\": \"zh_CN\",\"password\": \""+password+"\",\"pkey\": null,\"pkeyMode\": false,\"remember\": false,\"user\": \""+user+"\"}";
			// set the http request entity
			StringEntity entity = new StringEntity(data);
			httpPost.setEntity(entity);
			httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0;Windows NT 5.1; SV1; .NET CLR 2.0.50727; CIBA)");
			httpPost.addHeader("Content-Type", "text/plain");
			// Set the http request post
			httpPost.addHeader("X_REQUESTED_WITH", "XMLHttpRequest");
			//  set the cookie and context
			context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			//httpclient.execute(httpPost);
			response = httpclient.execute(httpPost, context);
		} catch (IOException e1) {
			// TODO print the exception
			e1.printStackTrace();
			logger.info("SID获取接口连接异常...");
			return null;
		}
		// get the HTTP response entity
		HttpEntity resEntity = response.getEntity();
		try {
			System.out.println(EntityUtils.toString(resEntity));
			// Get session from cookie ,other way is get session from response entity
			List<Cookie> cookies = cookieStore.getCookies();
			for(int i=0;i<cookies.size();i++){
				Cookie cookie =cookies.get(i);
				System.out.println("===="+cookie.getName() + "=" + cookie.getValue());
				if("AXWEBSID".equalsIgnoreCase(cookie.getName()) ){
					axWebSID = cookie.getValue();
					if(url.indexOf("?")>0)
						url += "&AXWEBSID="+axWebSID;
					url+="?SID" + axWebSID;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			logger.info("SID获取接口反馈数据解析异常...");
			return null;
		} catch (IOException e) {  
			e.printStackTrace();
			logger.info("SID获取接口反馈数据解析异常...");
			return null;
		}
		logger.info("SID："+axWebSID);
		return axWebSID;
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}
}