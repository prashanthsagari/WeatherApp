package com.weather.app;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class WebClientDemo {
	
	private final WebClient webClient;

    public WebClientDemo(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    public Mono<String> callAnotherServiceAsync() {
    	return webClient.get()
                .uri("/todos/1")
                .retrieve()
                .bodyToMono(String.class);
    	
    }

	public static void main(String[] args) {
		WebClient.Builder webClientBuilder = WebClient.builder();
		WebClientDemo myWebClient = new WebClientDemo(webClientBuilder);

        Mono<String> responseMono = myWebClient.callAnotherServiceAsync();

        String data1 = responseMono.block();
        System.out.println("Data 1: " + data1);
        
        responseMono.subscribe(
                response -> System.out.println("Response from another service: " + response),
                error -> System.out.println("Error occurred: " + error),
                () -> System.out.println("Request completed successfully.")
        );

	}

}
