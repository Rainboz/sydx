package com.cestco.service.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cestco.service.data.SyKm;
import com.cestco.service.data.SyMeterData;
import com.cestco.service.data.SyProject;
import com.cestco.service.data.SyStation;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sydxDataController")
public class DataController {
	// 获取所有项目
	@RequestMapping(value = "/projectList", produces = "application/json;charset=UTF-8")
	@CrossOrigin
	public @ResponseBody String projectList() {
		try {
			return JSONArray.fromObject(SyProject.getInstance().getProject()).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	// 获取某个项目下所有控制柜cuid
	@CrossOrigin
	@RequestMapping(value = "/stationCuidList", produces = "application/json;charset=UTF-8")
	public @ResponseBody String stationCuidList(
			@RequestParam(value = "porId", required = true) String porId) {
		try {
			return JSONArray.fromObject(SyStation.getInstance().getStationObj(porId,"cuid")).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	// 获取某个项目下所有控制柜
	@CrossOrigin
	@RequestMapping(value = "/stationList", produces = "application/json;charset=UTF-8")
	public @ResponseBody String stationList(
			@RequestParam(value = "porId", required = true) String porId) {
		try {
			return JSONArray.fromObject(SyStation.getInstance().getStation(porId)).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	// 获取某个项目下某个控制柜下所有开关
	@CrossOrigin
	@RequestMapping(value = "/stationKmList", produces = "application/json;charset=UTF-8")
	public @ResponseBody String stationKmList(
			@RequestParam(value = "porId", required = true) String porId,
			@RequestParam(value = "cuId", required = true) String cuId) {
		try {
			return JSONArray.fromObject(SyKm.getInstance().getStationKm(porId,cuId)).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	// 获取某个项目下所有开关
	@CrossOrigin
	@RequestMapping(value = "/projectKmList", produces = "application/json;charset=UTF-8")
	public @ResponseBody String projectKmList(
			@RequestParam(value = "porId", required = true) String porId) {
		try {
			return JSONObject.fromObject(SyKm.getInstance().getProjectKm(porId)).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	// 获取某个项目下某个控制柜下电表
	@CrossOrigin
	@RequestMapping(value = "/meterList", produces = "application/json;charset=UTF-8")
	public @ResponseBody String stationMeterList(
			@RequestParam(value = "porId", required = true) String porId,
			@RequestParam(value = "cuId", required = true) String cuId) {
		try {
			return JSONArray.fromObject(SyMeterData.getInstance().getStationMeter(porId,cuId)).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	// 获取某个项目下电表
	@CrossOrigin
	@RequestMapping(value = "/projectMeterList", produces = "application/json;charset=UTF-8")
	public @ResponseBody String projectMeterList(
			@RequestParam(value = "porId", required = true) String porId) {
		try {
			return JSONObject.fromObject(SyMeterData.getInstance().getProjectMeter(porId)).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
