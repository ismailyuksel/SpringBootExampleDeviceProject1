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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ismailyuksel.model.ErrorModel;
import com.ismailyuksel.model.ErrorType;
import com.ismailyuksel.model.MobileDeviceImportResultModel;
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
	
    @GetMapping("/import/device")
    public @ResponseBody MobileDeviceImportResultModel importMobileDevice()  {
    	
    	String json = null;
		try {
			json = JsonUtil.readJsonFromUrl(deviceUrl);
		} catch (IOException e) {
			ErrorModel error = new ErrorModel(1, ErrorType.IMPORT);
			logger.error(error.toString() + " url: " + deviceUrl, e);
			return getResult(false, error.getErrorCode() ,error.getErrorDescription(), null);
		}
		
		List<MobileDeviceModel> mobileDeviceList = null;
		try {
			TypeToken<List<MobileDeviceModel>> token = new TypeToken<List<MobileDeviceModel>>(){};
			mobileDeviceList = gson.fromJson(json, token.getType());
		} catch (JsonSyntaxException e) {
			ErrorModel error = new ErrorModel(2, ErrorType.IMPORT);
			logger.error(error.toString() + " json: " + json, e);
			return getResult(false, error.getErrorCode() ,error.getErrorDescription(), null);
		}
		
		if(mobileDeviceList == null || mobileDeviceList.isEmpty()) {
			ErrorModel error = new ErrorModel(3, ErrorType.IMPORT);
			logger.error(error.toString());
			return getResult(false, error.getErrorCode() ,error.getErrorDescription(), null);
		}
		
		removeInvalidDeviceInfo(mobileDeviceList);
		
		if(mobileDeviceList.isEmpty()) {
			ErrorModel error = new ErrorModel(4, ErrorType.IMPORT);
			logger.error(error.toString());
			return getResult(false, error.getErrorCode() ,error.getErrorDescription(), null);
		}
		
		List<Integer> IdsList = insertDevices(mobileDeviceList);
        if(IdsList.size() == 0) {
        	ErrorModel error = new ErrorModel(5, ErrorType.IMPORT);
        	logger.warn(error.toString());
        	return getResult(true, null ,error.getErrorDescription(), null);
        }
        return getResult(true, null, IdsList.size() + " mobile device info imported", IdsList);
    }

	private MobileDeviceImportResultModel getResult(boolean success, String errorCode, String result, List<Integer> IdsList) {
		if(IdsList == null) {
			IdsList = new ArrayList<>();
		}
		MobileDeviceImportResultModel resultModel = new MobileDeviceImportResultModel();
		resultModel.setSuccess(success);
		resultModel.setErrorCode(errorCode);
		resultModel.setResult(result);
		resultModel.setIdsList(IdsList);
		return resultModel;
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
						|| (!a.getOs().equals(OsType.ANDROID.toString()) && !a.getOs().equals(OsType.IOS.toString())))
				.collect(Collectors.toList());
		
		if (invalidMobileDeviceList != null && !invalidMobileDeviceList.isEmpty()) {
			logger.warn("importMobileDevice:invalidDeviceList: " + gson.toJson(invalidMobileDeviceList));
			for (MobileDeviceModel invalidMobileDevice : invalidMobileDeviceList) {
				mobileDeviceList.removeIf(a -> a.equals(invalidMobileDevice));
			}
		}
	
	}

}
