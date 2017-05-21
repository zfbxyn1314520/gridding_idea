package com.eollse.util;

public class ResultUtils {
	
	private static final String SUCCESS_MSG = "成功";
    /**
     * http回调成功
     * @param object
     * @return
     */
    public  static Result success(Object object){
        Result result = new Result();
        result.setCode(1);
        result.setMsg(SUCCESS_MSG);
        result.setData(object);
        return result;
    }
    /**
     * 无object返回
     * @return
     */
    public  static Result success(){
        return success(null);
    }
    /**
     * http回调错误
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return  result;
    }

}
