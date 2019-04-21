package com.cestco.service.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SyStation {
private SyStation () {}

private static SyStation instance = new SyStation();
public static SyStation getInstance() {
	return instance;
}

private List<String> list = new LinkedList<String>();
private Map<String, List<Map<String, String>>> stationMap = new HashMap<String,List<Map<String,String>>>();

	public boolean setStation(String pid, List<Map<String,String>> stationMap) {
		synchronized (this.stationMap) {
			try {
				this.stationMap.put(pid, stationMap);
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public List<Map<String, String>> getStation(String pid) {
		return this.stationMap.get(pid);
	}
	
	public List<String> getStationObj(String pid, String objName) {
		this.list.clear();
		for (int i=0,count=this.stationMap.get(pid).size(); i<count; i++) {
			this.list.add(stationMap.get(pid).get(i).get(objName));
		}
		return this.list;
	}
}
