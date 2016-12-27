package com.eventrock;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EventrockApplication {

//	@RequestMapping("/")
//	public String home(){
//		return "Hello EventRock!";
//	}

	public static void main(String[] args) {
		SpringApplication.run(EventrockApplication.class, args);
	}

}
