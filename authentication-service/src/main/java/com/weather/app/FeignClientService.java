package com.weather.app;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "service-discovery")
public interface FeignClientService {
	@GetMapping("/discovery/call-service-discovery")
	public String abcd();
}
