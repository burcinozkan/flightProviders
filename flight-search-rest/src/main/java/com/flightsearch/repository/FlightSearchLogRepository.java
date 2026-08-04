package com.flightsearch.repository;

import com.flightsearch.entity.FlightSearchLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightSearchLogRepository extends JpaRepository<FlightSearchLog, Long> {

    //save()
    //findById()
    //findAll()
    //deleteById()
}