package com.flightsearch.dto;

import java.time.LocalDateTime;

public record FlightKey(
        String flightNo,
        String origin,
        String destination,
        LocalDateTime departureDateTime,
        LocalDateTime arrivalDateTime
) {
}