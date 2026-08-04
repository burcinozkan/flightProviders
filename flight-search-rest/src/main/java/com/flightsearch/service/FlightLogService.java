package com.flightsearch.service;


import tools.jackson.databind.ObjectMapper;

import com.flightsearch.entity.FlightSearchLog;
import com.flightsearch.repository.FlightSearchLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        save(endpoint, request, response, true, null);
    }

    public void saveFailure(String endpoint, Object request, Object response, String errorMessage) {
        save(endpoint, request, response, false, errorMessage);
    }

    private void save(String endpoint, Object request, Object response, boolean success, String errorMessage) {

        FlightSearchLog log = new FlightSearchLog();
        log.setEndpoint(endpoint);
        log.setRequestPayload(objectMapper.writeValueAsString(request));
        log.setResponsePayload(objectMapper.writeValueAsString(response));
        log.setSuccess(success);
        log.setErrorMessage(errorMessage);
        log.setCreatedAt(LocalDateTime.now());

        repository.save(log);


    }
}
