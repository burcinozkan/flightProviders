package com.flightsearch.dto;

import java.util.List;

public record FlightSearchResponse (
        boolean hasError,
        List<FlightDto> flightOptions,
        String errorMessage
){
}
