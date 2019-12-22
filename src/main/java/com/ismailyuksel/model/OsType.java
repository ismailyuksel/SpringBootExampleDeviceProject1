package com.ismailyuksel.model;

public final class OsType {
	
	public static final OsType ANDROID = new OsType("Android");
	public static final OsType IOS = new OsType("ios");
    
	private final String value;

	private OsType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}