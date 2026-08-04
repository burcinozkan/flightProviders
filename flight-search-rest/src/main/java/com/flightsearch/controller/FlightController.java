package com.flightsearch.controller;

import com.flightsearch.dto.FlightSearchRequest;
import com.flightsearch.dto.FlightSearchResponse;
import com.flightsearch.service.FlightLogService;
import com.flightsearch.service.FlightSearchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightSearchService flightSearchService;
    private final FlightLogService logService;

    public FlightController(FlightSearchService flightSearchService, FlightLogService logService) {
        this.flightSearchService = flightSearchService;
        this.logService = logService;
    }

    @PostMapping("/search")
    public FlightSearchResponse search(@RequestBody FlightSearchRequest request){
        try {
            return flightSearchService.search(request);
        } catch (Exception e) {
            return logFailure("/api/flights/search", request, e);
        }
    }

    @PostMapping("/cheapest")
    public FlightSearchResponse cheapestFlight(@RequestBody FlightSearchRequest request){
        try {
            return flightSearchService.cheapestFlight(request);
        } catch (Exception e) {
            return logFailure("/api/flights/cheapest", request, e);
        }
    }

    private FlightSearchResponse logFailure(String endpoint, FlightSearchRequest request, Exception e) {
        FlightSearchResponse response = new FlightSearchResponse(true, java.util.List.of(), e.getMessage());
        logService.saveFailure(endpoint, request, response, e.getMessage());
        return response;
    }
}
