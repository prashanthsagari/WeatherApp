package com.weather.app.thirdparty.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "third-party-weather-stack", url = "http://api.weatherstack.com/current")
public interface WeatherStackService {

	@GetMapping
	public String weatherData(@RequestParam String access_key, @RequestParam(required = false, defaultValue = "India", value = "query") String cityName);
	
	// ?access_key=&query=India
}
