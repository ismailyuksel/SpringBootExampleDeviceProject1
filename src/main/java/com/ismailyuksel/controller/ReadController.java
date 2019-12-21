package com.ismailyuksel.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismailyuksel.model.MobileDevice;
import com.ismailyuksel.service.H2JdbcService;
import com.ismailyuksel.util.JsonUtil;

@RestController
public class ReadController {

	@Value("${device.json.url}")
	private String deviceUrl;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private H2JdbcService h2JdbcService;
	
    @GetMapping("/import/device")
    public boolean getAllContact()  {
    	String json = null;
		try {
			json = JsonUtil.readJsonFromUrl(deviceUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TypeToken<List<MobileDevice>> token = new TypeToken<List<MobileDevice>>(){};
		List<MobileDevice> personList = gson.fromJson(json, token.getType());
		System.out.println(personList);
        
		MobileDevice mobileDevice = new MobileDevice();
		mobileDevice.setBrand("test_brand");
		mobileDevice.setModel("test_model");
		mobileDevice.setOs("test_os");
		mobileDevice.setOsVersion("test_osVersion");
		
		int id = h2JdbcService.addMobileDevice(mobileDevice);
		System.out.println("id: " + String.valueOf(id));
		
		return true;
    }


}
