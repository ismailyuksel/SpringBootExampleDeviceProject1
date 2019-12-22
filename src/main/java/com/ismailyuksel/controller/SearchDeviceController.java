package com.ismailyuksel.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ismailyuksel.model.MobileDevice;
import com.ismailyuksel.model.MobileDeviceResultModel;
import com.ismailyuksel.service.H2JdbcService;

@RestController
public class SearchDeviceController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private H2JdbcService h2JdbcService;
	
    @GetMapping("/search/device")
    public MobileDeviceResultModel searchMobileDevice()  {
    	MobileDeviceResultModel result = new MobileDeviceResultModel();
    	
    	List<MobileDevice> mobileDeviceList = new ArrayList<>();
		try {
			mobileDeviceList = h2JdbcService.getMobileDevice();
		} catch (Exception e) {
			logger.error("searchMobileDevice:sqlError", e);
			return getErrorResult(result, "Sql exception occured");
		}
		if(mobileDeviceList == null || mobileDeviceList.isEmpty()) {
			logger.warn("searchMobileDevice:emptyResult");
			return getErrorResult(result, "Empty result");
		}
		
    	result.setSuccess(true);
    	result.setMobileDeviceList(mobileDeviceList);
    	
		return result;

    }

	private MobileDeviceResultModel getErrorResult(MobileDeviceResultModel result, String error) {
		result = new MobileDeviceResultModel();
		result.setSuccess(false);
		result.setError(error);
		result.setMobileDeviceList(new ArrayList<MobileDevice>());
		return result;
	}
}