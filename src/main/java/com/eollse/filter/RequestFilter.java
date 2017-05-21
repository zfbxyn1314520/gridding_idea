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

import com.eollse.po.User;


/**
 * Servlet Filter implementation class LoginFilter
 */
public class RequestFilter implements Filter {
	
	private FilterConfig config;
	
    /**
     * Default constructor. 
     */
    public RequestFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String path = req.getServletPath();
		String url = request.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
		String str=config.getInitParameter("excludedUrl");
		String[] params=str.split(",");
		Integer length=params.length;
		boolean result=false;
		for(int i=0;i<length;i++){
			if(path.endsWith(params[i])){
				if(path.endsWith("login.do") || path.endsWith("getPhoneCaptcha.do")){
					if(!(req.getParameter("userName")!=null && req.getParameter("password")!=null)){
						break;
					}
				}
				result=true;
				break;
			}
		}
		if(result){
			chain.doFilter(req, resp);
		}else{
			HttpSession session = req.getSession();
			User user=(User)session.getAttribute("user");
			if(user!=null){
				chain.doFilter(req, resp);
			}else{
				if(path.contains("/app/")){
					resp.sendError(600);
				}else{
					resp.sendRedirect(url);
				}
			}
		}		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
		this.config = fConfig;
	}
}
