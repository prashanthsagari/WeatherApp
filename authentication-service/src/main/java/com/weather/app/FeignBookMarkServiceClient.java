package com.weather.app;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bookmark-service")
public interface FeignBookMarkServiceClient {

	@DeleteMapping("/bookmark/delete-user-bookmark")
	public void deleteBookMark(@RequestParam String username);
}
