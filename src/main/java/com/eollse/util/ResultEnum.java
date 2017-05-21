package com.eollse.util;

public enum ResultEnum {
	
	UNKONW_ERROR(-1,"未知错误"),
    SUCCESS(0,"成功"),
    TEST_ERRORR(100,"测试异常");

    private Integer code;
    private String msg;
    ResultEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

}
