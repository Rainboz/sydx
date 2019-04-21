package com.cestco.service.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyKm {
private SyKm () {}

//private static Logger logger = Logger.getLogger(SyKm.class);
private static SyKm instance = new SyKm();
public static SyKm getInstance() {
	return instance;
}

//private List<String> list = new LinkedList<String>();
private Map<String, Map<String,List<Map<String, String>>>> kmMap = new HashMap<String, Map<String,List<Map<String, String>>>>();

	public void setKm(String cuid, Map<String,List<Map<String, String>>> kmMap) {
		synchronized (kmMap) {
			this.kmMap.put(cuid, kmMap);
		}
	}
	
	public Map<String,List<Map<String, String>>> getProjectKm(String pid) {
		return this.kmMap.get(pid);
	}
	
	public List<Map<String, String>> getStationKm(String pid, String cuid) {
		return this.kmMap.get(pid).get(cuid);
	}
}
