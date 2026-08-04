package com.flightsearch.dto;

import java.time.LocalDateTime;

public record FlightSearchRequest(
        String origin,
        String destination,
        LocalDateTime departureDate
) {
}
