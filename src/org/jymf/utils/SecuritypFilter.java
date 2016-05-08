package org.jymf.utils;

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

public class SecuritypFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, 
			             ServletResponse response, 
			             FilterChain chain) throws IOException,
			ServletException {

		if (!(req instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {  
	        throw new ServletException("OncePerRequestFilter just supports HTTP requests");  
	    }  
	    HttpServletRequest httpRequest = (HttpServletRequest) req;  
	    HttpServletResponse httpResponse = (HttpServletResponse) response;  
	    HttpSession session = httpRequest.getSession(true);  
	  
	    String url = httpRequest.getRequestURI();  
	    String ctx_path = httpRequest.getContextPath(); 
	    
	    if (url.substring(ctx_path.length()).startsWith("/m/product")||
	    	url.substring(ctx_path.length()).startsWith("/m/company")){
	    	Object object = session.getAttribute("proInfo");  
	  	    if (object == null) {  
	  	    	httpResponse.sendRedirect(ctx_path+"/check"); 
		    	return; 
	  	    }  
	    }

		chain.doFilter(httpRequest, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
