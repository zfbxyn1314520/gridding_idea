package com.eollse.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.eollse.po.User;

public class Log4jFilter implements Filter{

	private FilterConfig config;
	
	private final static Integer DEFAULT_USERID = 1;
	
	private final static String DEFAULT_LOGID = "127.0.0.0";
    
    @Override
    public void destroy() {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

    	HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
        String path=req.getServletPath();
		String str=config.getInitParameter("excludedUrl");
		String[] params=str.split(",");
		Integer length=params.length;
		boolean result=true;
		for(int i=0;i<length;i++){
			if(path.endsWith(params[i])){
				result=false;
				break;
			}
		}
		if(result){
			HttpSession session = req.getSession();
			User user=(User)session.getAttribute("user");
	        if (user == null){  
	            MDC.put("userId",DEFAULT_USERID);  
	            MDC.put("logIP",DEFAULT_LOGID);  
	        }else{  
	            MDC.put("userId", user.getUserId());  
	            MDC.put("logIP", user.getUser_login_ip()); 
	        }
		}
		chain.doFilter(req, resp); 
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    	
    	this.config = arg0;
    }
	

}
