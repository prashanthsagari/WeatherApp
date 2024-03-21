//package com.weather.app.config.filter;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import jakarta.servlet.Filter;
//
//@Configuration
//public class FilterConfig {
//
//	@Bean
//	public FilterRegistrationBean<Filter> jwtFilter() {
//
//		FilterRegistrationBean filter = new FilterRegistrationBean();
//		filter.setFilter(new JwtFilter());
//		filter.addUrlPatterns("/api/**");
//		return filter;
//
//	}
//
//}
