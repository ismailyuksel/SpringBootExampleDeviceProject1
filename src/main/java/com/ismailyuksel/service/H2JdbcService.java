package com.ismailyuksel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ismailyuksel.model.MobileDevice;

@Service
public class H2JdbcService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int addMobileDevice(MobileDevice mobileDevice) {
		return jdbcTemplate.update("insert into mobile_device (brand, model, os, os_version) " + "values(?, ?, ?, ?)",
	            new Object[] {
	            		mobileDevice.getBrand(), mobileDevice.getModel(), mobileDevice.getOs(), mobileDevice.getOsVersion()
	            });
	}
	
	
	
}
