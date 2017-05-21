package com.eollse.util;

public class CustomException extends Exception {

	private Integer code;

	public CustomException() {
	}

	public CustomException(ResultEnum resultEnum) {
		super((resultEnum.getMsg()));
		this.code = resultEnum.getCode();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
