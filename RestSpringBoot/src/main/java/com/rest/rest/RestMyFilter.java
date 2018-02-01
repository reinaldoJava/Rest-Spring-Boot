package com.rest.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RestMyFilter implements Filter {

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		RequestMapRequestWrapper requestMapRequestWrapper = new RequestMapRequestWrapper(req);
		//HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(req);
		// String remote_addr = request.getRemoteAddr();
		//Enumeration<String> params = requestMapRequestWrapper.getParameterNames();
		requestMapRequestWrapper.getParameter("rg");
		requestMapRequestWrapper.getServletContext().setAttribute("remote_addr", "Test");
		
		//requestWrapper.addHeader("remote_addr", "ursinhos carinhosos");
		chain.doFilter(requestMapRequestWrapper, res);
	}
	

	@Override
	public void init(FilterConfig config) throws ServletException {
	
	}

	@Override
	public void destroy() {

	}

}