package com.eventrock.service;

import com.eventrock.controller.BookingController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class BookingService {
    private final RestTemplate restTemplate;

    @Autowired
    public BookingService(RestTemplate rest) {
        this.restTemplate = rest;
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public String findBookingsByUsername(String username) {
        URI uri = URI.create("http://localhost:8080/bookings?username=" + username);

        return this.restTemplate.getForObject(uri, String.class);
    }

    public String reliable(String username) {
        return "You're doomed!";
    }
}