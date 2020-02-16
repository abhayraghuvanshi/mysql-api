package com.mysql.jpa.operation.model;


public class CustomErrorType {

  
    private String id;
    private String code;
    private String errorMessage;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public CustomErrorType(String id,String code,String errorMessage){
        this.errorMessage = errorMessage;
        this.id = id;
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

