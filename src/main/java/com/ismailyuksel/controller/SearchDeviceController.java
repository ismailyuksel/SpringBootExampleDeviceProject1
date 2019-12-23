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

import com.ismailyuksel.model.MobileDeviceSearchInnerRequestModel;
import com.ismailyuksel.model.ErrorModel;
import com.ismailyuksel.model.ErrorType;
import com.ismailyuksel.model.MobileDeviceModel;
import com.ismailyuksel.model.MobileDeviceSearchResultModel;
import com.ismailyuksel.service.H2JdbcService;

@RestController
public class SearchDeviceController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private H2JdbcService h2JdbcService;

	@GetMapping("/search/device")
	public @ResponseBody MobileDeviceSearchResultModel searchMobileDevice(
			 @RequestParam(value = "id", required = false, defaultValue = "0") int id,
			 @RequestParam(value = "brand", required = false) String brand,
			 @RequestParam(value = "model", required = false) String model,
			 @RequestParam(value = "os", required = false) String os,
			 @RequestParam(value = "osVersion", required = false) String osVersion)  {
		
    	MobileDeviceSearchInnerRequestModel mobileDeviceInnerRequest = getMobileDeviceInnerRequest(id, brand, model, os, osVersion);
    	
    	List<MobileDeviceModel> mobileDeviceList = new ArrayList<>();
		try {
			mobileDeviceList = h2JdbcService.getMobileDevice(mobileDeviceInnerRequest);
		} catch (Exception e) {
			ErrorModel error = new ErrorModel(1, ErrorType.SEARCH);
			logger.error(error.toString() + " requestParameters: " + mobileDeviceInnerRequest.toString(), e);
			return getResult(false, error.getErrorCode(), error.getErrorDescription(), null);
		}
		if(mobileDeviceList == null || mobileDeviceList.isEmpty()) {
			ErrorModel error = new ErrorModel(2, ErrorType.SEARCH);
			logger.warn(error.toString() + " requestParameters: " + mobileDeviceInnerRequest.toString());
			return getResult(false, error.getErrorCode(), error.getErrorDescription(), null);
		}
		return getResult(true, null, mobileDeviceList.size() + " mobile device found.", mobileDeviceList);
    }

	private MobileDeviceSearchInnerRequestModel getMobileDeviceInnerRequest(int id, String brand, String model, String os,
			String osVersion) {
		MobileDeviceSearchInnerRequestModel mobileDeviceInnerRequest = new MobileDeviceSearchInnerRequestModel();
    	mobileDeviceInnerRequest.setId(id);
    	mobileDeviceInnerRequest.setBrand(brand);
    	mobileDeviceInnerRequest.setModel(model);
    	mobileDeviceInnerRequest.setOs(os);
    	mobileDeviceInnerRequest.setOsVersion(osVersion);
		return mobileDeviceInnerRequest;
	}

	private MobileDeviceSearchResultModel getResult(boolean success, String errorCode, String result, List<MobileDeviceModel> mobileDeviceList) {
		if(mobileDeviceList == null) {
			mobileDeviceList = new ArrayList<>();
		}
		MobileDeviceSearchResultModel searchResult = new MobileDeviceSearchResultModel();
		searchResult.setSuccess(success);
		searchResult.setErrorCode(errorCode);
		searchResult.setResult(result);
		searchResult.setMobileDeviceList(mobileDeviceList);
		return searchResult;
	}
}