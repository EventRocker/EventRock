package com.eventrock.service;

import org.springframework.stereotype.Component;

@Component
public class BookingServiceFallback implements BookingService {
    @Override
    public String findBookingsByUsername(String username) {
        return "none";
    }
}
