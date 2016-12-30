package com.eventrock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@EnableCircuitBreaker
public class EventrockApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventrockApplication.class, args);
	}

	@Bean
	public RestTemplate rest(RestTemplateBuilder builder) {
		return builder.build();
	}

}
