package com.eollse.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;


public class CustomExceptionResolver extends HandlerExceptionResolverComposite {
	
	/**日志log*/
    private static Logger logger = Logger.getLogger(CustomExceptionResolver.class);

    //系统抛出的异常  
    @Override  
    public ModelAndView resolveException(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {  
        //handler就是处理器适配器要执行的Handler对象(只有method)  
        //解析出异常类型。  
    	/*  使用response返回    */  
        response.setStatus(HttpStatus.OK.value()); //设置状态码  
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType  
        response.setCharacterEncoding("UTF-8"); //避免乱码  
        response.setHeader("Cache-Control", "no-cache, must-revalidate");  
        //如果该 异常类型是系统 自定义的异常，直接取出异常信息。  
        System.out.println(1);
        CustomException customException=null;  
        try {
	        if(ex instanceof CustomException){  
	            customException = (CustomException)ex;  
	            //错误信息  
				response.getWriter().write(ResultUtils.error(customException.getCode(),ex.getMessage()).toString());
				System.out.println("==================");
	            logger.error(ex.getMessage());  
	            System.out.println(ex.getMessage());
	            System.out.println("==================");
	        }else
	        	response.getWriter().write(ResultUtils.error(-1,ex.getMessage()).toString());
    	} catch (IOException e) {
            logger.error(e.getMessage(), e);  
			e.printStackTrace();
		}
        ModelAndView modelAndView=new ModelAndView();  
          
        return modelAndView;  
    }  

}
