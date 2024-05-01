package com.weather.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather.app.thirdparty.apis.WeatherStackService;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

	@Value("${app.weather.stack}")
	private String access_key;

	@Autowired
	WeatherStackService weatherStackService;

	@GetMapping(value = "/weather-by-city-name", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> weather(
			@RequestParam(required = false, defaultValue = "India", value = "query") String cityName) {
		String response = null;

		try {
			response = weatherStackService.weatherData(access_key, cityName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
