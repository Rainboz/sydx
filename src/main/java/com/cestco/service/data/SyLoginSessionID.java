package com.cestco.service.data;

import org.apache.log4j.Logger;

public class SyLoginSessionID {
private SyLoginSessionID () {}

private static Logger logger = Logger.getLogger(SyLoginSessionID.class);
private static SyLoginSessionID instance = new SyLoginSessionID();
public static SyLoginSessionID getInstance() {
	return instance;
}

private String syLoginSessionID;

	public String getSyLoginSessionID() {
		synchronized (syLoginSessionID) {
			return this.syLoginSessionID;
		}
	}
	
	public void moveSyLoginSessionID() {
		synchronized (syLoginSessionID) {
			logger.info("清空SessionID:"+syLoginSessionID);
				this.syLoginSessionID=null;
		}
	}
	
	public void setSyLoginSessionID(String syLoginSessionID) {
		synchronized (syLoginSessionID) {
			logger.info("保存SessionID:"+syLoginSessionID);
			this.syLoginSessionID = syLoginSessionID;
		}
	}
}
