package com.ismailyuksel.model;

public class ErrorModel {
    
    private int errorId;
    private ErrorType errorType;
    private String errorCode;
    private String errorDescription;

    public int getErrorId() {
		return errorId;
	}
	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}
	public ErrorType getErrorType() {
		return errorType;
	}
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	public ErrorModel(int errorId, ErrorType errorType) {
    	setErrorId(errorId);
    	setErrorType(errorType);
    	
    	if(errorId <= 0) {
    		return;
    	}
    	
		String code = getCode(errorId, errorType.toString());
		
		setErrorCode(code);
		
		if(ErrorType.IMPORT.toString().equals(errorType.toString())) {

			if(errorId == 1) {
				setErrorDescription("Mobile device info access error");
			} else if(errorId == 2) {
				setErrorDescription("Mobile device info parse error");
			} else if(errorId == 3) {
				setErrorDescription("Empty mobile device info");
			} else if(errorId == 4) {
				setErrorDescription("Mobile device info validation error");
			} else if (errorId == 5) {
				setErrorDescription("Mobile devices already imported");
			}
			
		} else if (ErrorType.SEARCH.toString().equals(errorType.toString())) {
			if(errorId == 1) {
				setErrorDescription("Database error");
			} else if(errorId == 2) {
				setErrorDescription("Empty result");
			}
		}
    }
	
	private String getCode(int errorId, String type) {
		String code = String.valueOf(errorId);
		int errLen = code.length();
		if(errLen < 3) {
			for(int i = 0; i < 3 - errLen; i++) {
				code = "0" + code;
			}
		}
		return type + code;
	}
	
	@Override
	public String toString() {
		return "ErrorModel [errorCode=" + errorCode + ", errorDescription=" + errorDescription + "]";
	}
    
}