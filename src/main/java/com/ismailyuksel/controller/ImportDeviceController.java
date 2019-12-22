package com.ismailyuksel.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ismailyuksel.model.MobileDeviceModel;
import com.ismailyuksel.model.OsType;
import com.ismailyuksel.service.H2JdbcService;
import com.ismailyuksel.util.JsonUtil;

@RestController
public class ImportDeviceController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${device.json.url}")
	private String deviceUrl;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private H2JdbcService h2JdbcService;
	
	private static final String ERROR_RESULT = "Mobile device info could not imported";
	
    @GetMapping("/import/device")
    public String importMobileDevice()  {
    	
    	String json = null;
		try {
			json = JsonUtil.readJsonFromUrl(deviceUrl);
		} catch (IOException e) {
			logger.error("importMobileDevice:error url: " + deviceUrl, e);
			return ERROR_RESULT;
		}
		
		List<MobileDeviceModel> mobileDeviceList = null;
		try {
			TypeToken<List<MobileDeviceModel>> token = new TypeToken<List<MobileDeviceModel>>(){};
			mobileDeviceList = gson.fromJson(json, token.getType());
		} catch (JsonSyntaxException e) {
			logger.error("importMobileDevice:jsonParseError json: " + json, e);
			return ERROR_RESULT;
		}
		
		if(mobileDeviceList == null || mobileDeviceList.isEmpty()) {
			logger.error("importMobileDevice:nullOrEmptyError mobileDeviceList");
			return ERROR_RESULT;
		}
		
		removeInvalidDeviceInfo(mobileDeviceList);
		
		if(mobileDeviceList.isEmpty()) {
			logger.error("importMobileDevice:mobileDeviceList is invalid");
			return ERROR_RESULT;
		}
		
		List<Integer> IdsList = insertDevices(mobileDeviceList);
        if(IdsList.size() == 0) {
        	return "Mobile devices already imported";
        }
		return IdsList.size() + " mobile device info imported. Imported Ids: " + gson.toJson(IdsList);
    }

	private List<Integer> insertDevices(List<MobileDeviceModel> mobileDeviceList) {
		List<Integer> IdsList = new ArrayList<>();
		for(MobileDeviceModel mobileDevice: mobileDeviceList) {
			int id = 0;
			try {
				id = h2JdbcService.addMobileDevice(mobileDevice);
			} catch (Exception e) {
				if (e.getMessage().contains("Unique index or primary key violation")) {
					logger.info("importMobileDevice:alreadyExists mobileDevice: " + gson.toJson(mobileDevice));
				} else {
					logger.error("importMobileDevice:insertError mobileDevice: " + gson.toJson(mobileDevice), e);					
				}
			}
				
			if (id != 0) {
				IdsList.add(id);
				logger.info("id: " + id + " importedDevice: " + gson.toJson(mobileDevice));				
			}
		}
		return IdsList;
	}

	private void removeInvalidDeviceInfo(List<MobileDeviceModel> mobileDeviceList) {
		
		List<MobileDeviceModel> invalidMobileDeviceList = mobileDeviceList.stream()
				.filter(a -> StringUtils.isNullOrEmpty(a.getBrand()) || StringUtils.isNullOrEmpty(a.getModel())
						|| StringUtils.isNullOrEmpty(a.getOs()) || StringUtils.isNullOrEmpty(a.getOsVersion())
						|| (!a.getOs().equals(OsType.ANDROID.getValue()) && !a.getOs().equals(OsType.IOS.getValue())))
				.collect(Collectors.toList());
		
		if (invalidMobileDeviceList != null && !invalidMobileDeviceList.isEmpty()) {
			logger.warn("importMobileDevice:invalidDeviceList: " + gson.toJson(invalidMobileDeviceList));
			for (MobileDeviceModel invalidMobileDevice : invalidMobileDeviceList) {
				mobileDeviceList.removeIf(a -> a.equals(invalidMobileDevice));
			}
		}
	
	}


}
