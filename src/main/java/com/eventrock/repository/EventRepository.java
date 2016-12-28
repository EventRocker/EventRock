package com.eventrock.repository;

import com.eventrock.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findOneById(Long id);
}
