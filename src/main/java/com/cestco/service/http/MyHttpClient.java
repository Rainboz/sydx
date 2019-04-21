package com.cestco.service.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.cestco.service.data.SyKm;
import com.cestco.service.data.SyLoginSessionID;
import com.cestco.service.data.SyMeterData;
import com.cestco.service.data.SyProject;
import com.cestco.service.data.SyStation;
import com.cestco.service.util.HttpIO;

public class MyHttpClient {
	public static boolean runSign = false;
	
	public static void toGetData () {
		runSign = true;
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
		 executor.scheduleAtFixedRate(new Runnable() {
				@Override
	            public void run() {
	            	if (runSign)
	            		getPageData();
	            	else
	            		executor.shutdown();
	            }
		 }, 0, 5, TimeUnit.SECONDS);
	}
	
	/**
	 * 数据获取
	 */
	private static void getPageData() {
		
		//项目信息获取
		SyProject.getInstance().setProject(HttpIO.getData(
				"http://112.16.113.5:8800/api/json?cmd=project&ctrl=list&version=1&sid="+SyLoginSessionID.getInstance().getSyLoginSessionID(),
				"{\"wheres\":[{\"k\":\"enabled\",\"o\":\"=\",\"v\":true}],\"orders\":[{\"k\":\"name\",\"v\":\"ASC\"}]}"));
		//获取项目ID
		List<String> projectObjList = SyProject.getInstance().getProjectObj("projectId");
		
		//控制柜信息获取
		for (int i0=0,count0=projectObjList.size(); i0<count0; i0++) {
			SyStation.getInstance().setStation(projectObjList.get(i0),
				HttpIO.getData("http://112.16.113.5:8800/api/json?cmd=station&ctrl=list&version=1&sid="+SyLoginSessionID.getInstance().getSyLoginSessionID(),
					"{\"wheres\":[{\"k\":\"projectId\",\"o\":\"=\",\"v\":\""+projectObjList.get(i0)+"\"}],\"orders\":[{\"k\":\"dirId\",\"v\":\"ASC\"},{\"k\":\"name\",\"v\":\"ASC\"}]}"));
			//获取控制柜ID
			List<String> statioonObjList = SyStation.getInstance().getStationObj(projectObjList.get(i0),"cuid");
			
			Map<String,List<Map<String, String>>> kmMap = new HashMap<String,List<Map<String, String>>>();
			Map<String,List<Map<String, String>>> meterMap = new HashMap<String,List<Map<String, String>>>();
			for (int i1=0,count1=statioonObjList.size(); i1<count1; i1++) {
				//开关信息获取
				kmMap.put(statioonObjList.get(i1),
						HttpIO.getData("http://112.16.113.5:8800/api/json?cmd=data-km&ctrl=list&version=1&sid="+SyLoginSessionID.getInstance().getSyLoginSessionID()+"&pid="+projectObjList.get(i0),
								"{\"wheres\":[{\"k\":\"cuid\",\"o\":\"=\",\"v\":\""+statioonObjList.get(i1)+"\"}],\"orders\":[]}"));

				//电表信息获取
				meterMap.put(statioonObjList.get(i1),
						HttpIO.getData("http://112.16.113.5:8800/api/json?cmd=data-meter&ctrl=list&version=1&sid="+SyLoginSessionID.getInstance().getSyLoginSessionID()+"&pid="+projectObjList.get(i0),
								"{\"wheres\":[{\"k\":\"cuid\",\"o\":\"=\",\"v\":\""+statioonObjList.get(i1)+"\"}],\"orders\":[]}"));
			}
			SyKm.getInstance().setKm(projectObjList.get(i0),kmMap);
			SyMeterData.getInstance().setMeter(projectObjList.get(i0),meterMap);
		}
	}
}
