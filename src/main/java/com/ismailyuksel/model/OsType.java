package com.ismailyuksel.model;

public final class OsType {
	
	public static final OsType UNDEFINED = new OsType(0,"UNDEFINED");
	public static final OsType ANDROID = new OsType(1, "Android");
	public static final OsType IOS = new OsType(2, "ios");
	
	private final int value;
	private final String name;
	
	private OsType(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	private OsType(int value) {
		OsType t = of(value);
		this.name = t.name;
		this.value = t.value;
	}
	private OsType(String name) {
		OsType t = ofName(name);
		this.name = t.name;
		this.value = t.value;
	}

	public static OsType of(int typeId) {
		if(ANDROID.value == typeId) {
			return OsType.ANDROID;			
		} else if(IOS.value == typeId) {
			return OsType.IOS;
		} else {
			return OsType.UNDEFINED;
		}
	}
	public static OsType ofName(String name) {
		if(name.equals(ANDROID.name)) {
			return OsType.ANDROID;			
		} else if(name.equals(IOS.name)) {
			return OsType.IOS;			
		} else {
			return OsType.UNDEFINED;			
		}
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}