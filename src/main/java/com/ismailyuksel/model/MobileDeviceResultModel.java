package com.ismailyuksel.model;

import java.util.List;

public class MobileDeviceResultModel {

	private boolean success;
	private String error;
	List<MobileDeviceModel> mobileDeviceList;
	
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
	public List<MobileDeviceModel> getMobileDeviceList() {
		return mobileDeviceList;
	}
	public void setMobileDeviceList(List<MobileDeviceModel> mobileDeviceList) {
		this.mobileDeviceList = mobileDeviceList;
	}
	
}
