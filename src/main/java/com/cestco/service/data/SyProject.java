package com.cestco.service.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class SyProject {
private SyProject () {}

private static Logger logger = Logger.getLogger(SyProject.class);
private static SyProject instance = new SyProject();
public static SyProject getInstance() {
	return instance;
}

private List<String> list = new LinkedList<String>();
private List<Map<String,String>> projectMap = new LinkedList<Map<String,String>>();

	public void setProject(List<Map<String,String>> projectMap) {
		synchronized (projectMap) {
			this.projectMap = projectMap;
		}
	}
	
	public List<Map<String, String>> getProject() {
		return this.projectMap;
	}
	
	public List<String> getProjectObj(String objName) {
		this.list.clear();
		for (int i=0,count=projectMap.size(); i<count; i++) {
			logger.info(projectMap.get(i).get(objName));
			list.add(projectMap.get(i).get(objName));
		}
		return this.list;
	}
}
