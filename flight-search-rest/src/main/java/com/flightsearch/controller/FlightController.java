package com.flightsearch.controller;

import com.flightsearch.dto.FlightSearchRequest;
import com.flightsearch.dto.FlightSearchResponse;
import com.flightsearch.service.FlightSearchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightSearchService flightSearchService;

    public FlightController(FlightSearchService flightSearchService) {
        this.flightSearchService = flightSearchService;
    }

    @PostMapping("/search")
    public FlightSearchResponse search(@RequestBody FlightSearchRequest request){

        return flightSearchService.search(request);
    }

    @PostMapping("/cheapest")
    public FlightSearchResponse cheapestFlight(@RequestBody FlightSearchRequest request){
        return flightSearchService.cheapestFlight(request);
    }
}
