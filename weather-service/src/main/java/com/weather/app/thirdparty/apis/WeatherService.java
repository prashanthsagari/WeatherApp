package com.weather.app.thirdparty.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "third-party-weather-service", url = "http://api.openweathermap.org/data/2.5")
public interface WeatherService {

	@GetMapping("/weather")
	public String getWeatherData(@RequestParam float lat, @RequestParam float lon, @RequestParam String appid);
}
