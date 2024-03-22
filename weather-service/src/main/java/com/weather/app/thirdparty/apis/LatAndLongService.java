package com.weather.app.thirdparty.apis;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weather.app.model.Location;

@FeignClient(name = "lat-long-service", url = "https://api.api-ninjas.com/")
public interface LatAndLongService {

	@GetMapping("v1/geocoding")
	public List<Location> getLatandLongbyCountryName(@RequestParam String city, @RequestParam String country, @RequestParam("X-Api-Key") String apiKey);
}
