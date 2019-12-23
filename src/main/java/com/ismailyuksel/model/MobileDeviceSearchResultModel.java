package com.ismailyuksel.model;

import java.util.List;

public class MobileDeviceSearchResultModel {

	private boolean success;
	private String errorCode;
	private String result;
	List<MobileDeviceModel> mobileDeviceList;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<MobileDeviceModel> getMobileDeviceList() {
		return mobileDeviceList;
	}
	public void setMobileDeviceList(List<MobileDeviceModel> mobileDeviceList) {
		this.mobileDeviceList = mobileDeviceList;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
