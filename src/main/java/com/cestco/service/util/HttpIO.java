package com.cestco.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

import net.sf.json.JSONArray;

@SuppressWarnings({ "deprecation", "unused" })
public class HttpIO {
private static Logger logger = Logger.getLogger(HttpIO.class);
	/**
	 * 数据请求
	 */
	@SuppressWarnings({ "resource", "unchecked" })
	public static List<Map<String, String>> getData(String url, String data) {
		logger.info("url:"+url+" data:"+data);
		// Create the HttpClient object and context
		HttpContext context = new BasicHttpContext();
		// Set then cookie
		CookieStore cookieStore = new BasicCookieStore();
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = null;
		try{
			// set the http request entity
			httpPost.setEntity(new StringEntity(data));
			httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0;Windows NT 5.1; SV1; .NET CLR 2.0.50727; CIBA)");
			httpPost.addHeader("Content-Type", "text/plain");
			// Set the http request post
			httpPost.addHeader("X_REQUESTED_WITH", "XMLHttpRequest");
			//  set the cookie and context
			context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			//httpclient.execute(httpPost);
			response = new DefaultHttpClient().execute(httpPost, context);
		} catch (IOException e1) {
			// TODO print the exception
			e1.printStackTrace();
		}

		// get the HTTP response entity
		HttpEntity resEntity = response.getEntity();
		try {
			String str = EntityUtils.toString(resEntity);
			List<Map<String,String>> map = new LinkedList<Map<String,String>>();
			map = (List<Map<String, String>>) JSONArray.toCollection(JSONArray.fromObject(str.substring(str.indexOf("["))), Map.class);
			return map;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * POST连接请求
	 */
	public static HttpURLConnection postHttpClient(String url, String msg){
		HttpURLConnection httpUrlConnection = null;
		try {
			
			httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
			httpUrlConnection.setConnectTimeout(4000);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setAllowUserInteraction(true);
			httpUrlConnection.setInstanceFollowRedirects(true);
			httpUrlConnection.setRequestProperty("Content-Type", "text/plain");
			httpUrlConnection.setRequestProperty("Charset", "UTF-8");
			httpUrlConnection.connect();
			
			if (msg != null)
				System.out.println(sendMsg(httpUrlConnection, msg));
			if(200==httpUrlConnection.getResponseCode())
				return httpUrlConnection;
			System.out.println(httpUrlConnection.getResponseCode());
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (httpUrlConnection != null)
				httpUrlConnection.disconnect();
		}
	}
	
	/**
	 * 数据发送
	 */
	public static boolean sendMsg(HttpURLConnection servletConnection, String msg) {
		OutputStream outputStream = null;
		
		try {
			outputStream = servletConnection.getOutputStream();
			outputStream.write(msg.getBytes());
			outputStream.flush();
			outputStream.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 数据接收
	 */
	public static String receiveMsg(HttpURLConnection servletConnection) {
		InputStream inputStream = null;
		StringBuffer strBuf = new StringBuffer();
		
		try {
			inputStream = servletConnection.getInputStream();
			
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(inputStream, "UTF-8");
			
			while (scanner.hasNextLine()){
				strBuf.append(scanner.nextLine());
			}
			
			inputStream.close();
			return strBuf.toString();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
