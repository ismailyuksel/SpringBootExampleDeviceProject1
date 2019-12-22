package com.ismailyuksel.model;

public class MobileDeviceModel {
	private int id;
	private String brand;
	private String model;
	private String os;
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
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "MobileDevice [id=" + id + ", brand=" + brand + ", model=" + model + ", os=" + os + ", osVersion="
				+ osVersion + "]";
	}
	
}
