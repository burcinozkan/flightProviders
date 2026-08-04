package com.flightsearch.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightDto(
        String flightNumber,
        String origin,
        String destination,
        LocalDateTime departuredatetime,
        LocalDateTime arrivaldatetime,
        BigDecimal price
) {

}
