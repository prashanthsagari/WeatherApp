package com.weather.app.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Configured this filter to only allow from api gateway
//@Component
public class RequestFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String message = "API gateway can access this service ";
		String url = "https://localhost:9090" + httpRequest.getServletPath();
		String hyperlink = "<a href=\"" + url + "\" style=\"color: red;\">Click here</a>";
		String finalMessage = "<html><body>" + hyperlink + "</body></html>";
		message = message + finalMessage;

		if (httpRequest.getServerPort() != 9090) {
			httpResponse.sendError(401, message);
		}

		chain.doFilter(httpRequest, response);

	}

}
