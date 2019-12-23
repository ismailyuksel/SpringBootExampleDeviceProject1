package com.ismailyuksel.model;

import java.util.List;

public class MobileDeviceImportResultModel {

	private boolean success;
	private String errorCode;
	private String result;
	List<Integer> IdsList;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<Integer> getIdsList() {
		return IdsList;
	}
	public void setIdsList(List<Integer> idsList) {
		IdsList = idsList;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
