package com.weather.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather.app.model.Location;
import com.weather.app.thirdparty.apis.LatAndLongService;
import com.weather.app.thirdparty.apis.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherServiceController {

	@Autowired
	WeatherService weatherService;
	
	@Autowired LatAndLongService latAndLongService;

	// working url to get lat and long
	// http://api.api-ninjas.com/v1/geocoding?city=Hyderabad&country=India&X-Api-Key=GLixv7ixJXT1GeXfWR0q1Q==jzY0Sxgq7K1VSzaD
	
	// workign url to get weather data by lat and long
//	http://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=2bdff9078622788a87bb6abae03afae3
	
	@Value("${app.weather.secret.key}")
	private String appId;
	
	
	@Value("${app.ninjas.to.name}")
	private String apiKey;

	@GetMapping("/weather-details-by-city-name")
	public ResponseEntity<String> getWeatherData(@RequestParam String city, @RequestParam(required = false) String country) {
		String response = null;
		List<Location> location = null;
		country = country != null ? country : "India"; 
		try {
			location = latAndLongService.getLatandLongbyCountryName(city, country, apiKey);
//			response =  weatherService.getWeatherData(44.34f, 10.99f, appId);
			response =  weatherService.getWeatherData(location.get(0).latitude(), location.get(0).longitude(), appId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/test")
	public ResponseEntity<?> ping() {
		return new ResponseEntity<String>( " WEATHER SERVICE IS UP ", HttpStatus.OK);
	}

}
