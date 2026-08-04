package com.flightsearch.service;


import tools.jackson.databind.ObjectMapper;

import com.flightsearch.entity.FlightSearchLog;
import com.flightsearch.repository.FlightSearchLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FlightLogService {

    private final ObjectMapper objectMapper;
    private final FlightSearchLogRepository repository;

    public FlightLogService(ObjectMapper objectMapper, FlightSearchLogRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    //save
    public void save(
            String endpoint,
            Object request,
            Object response
    ){

        FlightSearchLog log = new FlightSearchLog();
        log.setEndpoint(endpoint);
        log.setRequestPayload(objectMapper.writeValueAsString(request));
        log.setResponsePayload(objectMapper.writeValueAsString(response));
        log.setSuccess(true);
        log.setCreatedAt(LocalDateTime.now());

        repository.save(log);


    }
}