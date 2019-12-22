package com.ismailyuksel.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ismailyuksel.model.MobileDeviceInnerRequestModel;
import com.ismailyuksel.model.MobileDeviceModel;
import com.ismailyuksel.model.MobileDeviceResultModel;
import com.ismailyuksel.service.H2JdbcService;

@RestController
public class SearchDeviceController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private H2JdbcService h2JdbcService;

	@GetMapping("/search/device")
	public @ResponseBody MobileDeviceResultModel searchMobileDevice(
			 @RequestParam(value = "id", required = false, defaultValue = "0") int id,
			 @RequestParam(value = "brand", required = false) String brand,
			 @RequestParam(value = "model", required = false) String model,
			 @RequestParam(value = "os", required = false) String os,
			 @RequestParam(value = "osVersion", required = false) String osVersion)  {
		
    	MobileDeviceResultModel result = new MobileDeviceResultModel();
    	
    	MobileDeviceInnerRequestModel mobileDeviceInnerRequest = getMobileDeviceInnerRequest(id, brand, model, os, osVersion);
    	
    	List<MobileDeviceModel> mobileDeviceList = new ArrayList<>();
		try {
			mobileDeviceList = h2JdbcService.getMobileDevice(mobileDeviceInnerRequest);
		} catch (Exception e) {
			logger.error("searchMobileDevice:sqlError requestParameters: " + mobileDeviceInnerRequest.toString(), e);
			return getErrorResult(result, "Sql exception occured");
		}
		if(mobileDeviceList == null || mobileDeviceList.isEmpty()) {
			logger.warn("searchMobileDevice:emptyResult requestParameters: " + mobileDeviceInnerRequest.toString());
			return getErrorResult(result, "Empty result");
		}
		
    	result.setSuccess(true);
    	result.setMobileDeviceList(mobileDeviceList);
    	
		return result;

    }

	private MobileDeviceInnerRequestModel getMobileDeviceInnerRequest(int id, String brand, String model, String os,
			String osVersion) {
		MobileDeviceInnerRequestModel mobileDeviceInnerRequest = new MobileDeviceInnerRequestModel();
    	mobileDeviceInnerRequest.setId(id);
    	mobileDeviceInnerRequest.setBrand(brand);
    	mobileDeviceInnerRequest.setModel(model);
    	mobileDeviceInnerRequest.setOs(os);
    	mobileDeviceInnerRequest.setOsVersion(osVersion);
		return mobileDeviceInnerRequest;
	}

	private MobileDeviceResultModel getErrorResult(MobileDeviceResultModel result, String error) {
		result = new MobileDeviceResultModel();
		result.setSuccess(false);
		result.setError(error);
		result.setMobileDeviceList(new ArrayList<MobileDeviceModel>());
		return result;
	}
}