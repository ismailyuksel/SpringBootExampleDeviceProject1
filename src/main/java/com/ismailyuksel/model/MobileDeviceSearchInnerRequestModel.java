package com.ismailyuksel.model;

public class MobileDeviceSearchInnerRequestModel {
	private int id;
	private String brand;
	private String model;
	private OsType osType;
	private String osVersion;
	
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public OsType getOsType() {
		return osType;
	}
	public void setOsType(OsType osType) {
		this.osType = osType;
	}
	
	@Override
	public String toString() {
		return "MobileDeviceSearchInnerRequestModel [id=" + id + ", brand=" + brand + ", model=" + model + ", osType="
				+ osType + ", osVersion=" + osVersion + "]";
	}

}
