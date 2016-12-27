package com.eventrock.repository;

import com.eventrock.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SeatRepository extends JpaRepository<Seat, Long> {
}
