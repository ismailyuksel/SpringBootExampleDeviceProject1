package com.ismailyuksel.model;

import java.util.List;

public class MobileDeviceResultModel {

	private boolean success;
	private String error;
	List<MobileDevice> mobileDeviceList;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<MobileDevice> getMobileDeviceList() {
		return mobileDeviceList;
	}
	public void setMobileDeviceList(List<MobileDevice> mobileDeviceList) {
		this.mobileDeviceList = mobileDeviceList;
	}
	
}
