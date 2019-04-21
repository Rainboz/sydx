package com.cestco.service.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyMeterData {
private SyMeterData () {}

//private static Logger logger = Logger.getLogger(SyKm.class);
private static SyMeterData instance = new SyMeterData();
public static SyMeterData getInstance() {
	return instance;
}

//private List<String> list = new LinkedList<String>();
private Map<String, Map<String,List<Map<String, String>>>> metereMap = new HashMap<String, Map<String,List<Map<String, String>>>>();

	public void setMeter(String cuid, Map<String,List<Map<String, String>>> kmMap) {
		synchronized (kmMap) {
			this.metereMap.put(cuid, kmMap);
		}
	}
	
	public Map<String,List<Map<String, String>>> getProjectMeter(String pid) {
		return this.metereMap.get(pid);
	}
	
	public Map<String, String> getStationMeter(String pid, String cuid) {
		return this.metereMap.get(pid).get(cuid).get(0);
	}
}
