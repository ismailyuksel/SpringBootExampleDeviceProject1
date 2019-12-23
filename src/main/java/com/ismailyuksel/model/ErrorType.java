package com.ismailyuksel.model;

public final class ErrorType {

	public static final ErrorType IMPORT = new ErrorType("IMP");
	public static final ErrorType SEARCH = new ErrorType("SRC");

	private final String value;

	private ErrorType(String value) {
		this.value = value;
	}
	
	public String toString() {
		return this.value;
	}
}
	