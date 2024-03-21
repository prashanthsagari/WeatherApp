package com.weather.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/call-service-discovery")
	public String data() {
		return "Getting data from Service discovery.";
	}
}
