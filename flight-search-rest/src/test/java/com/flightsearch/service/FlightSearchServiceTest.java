package com.flightsearch.service;

import com.flightsearch.client.ProviderAClient;
import com.flightsearch.client.ProviderBClient;
import com.flightsearch.dto.FlightDto;
import com.flightsearch.dto.FlightSearchRequest;
import com.flightsearch.dto.FlightSearchResponse;
import com.flightsearch.mapper.ProviderAMapper;
import com.flightsearch.mapper.ProviderBMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlightSearchServiceTest {

    @Test
    void cheapest(){
        ProviderAClient providerAClient = mock(ProviderAClient.class);
        ProviderBClient providerBClient = mock(ProviderBClient.class);
        ProviderAMapper providerAMapper = mock(ProviderAMapper.class);
        ProviderBMapper providerBMapper = mock(ProviderBMapper.class);
        FlightLogService logService = mock(FlightLogService.class);

        FlightSearchService service = new FlightSearchService(
                providerAClient,
                providerAMapper,
                providerBMapper,
                providerBClient,
                logService
        );
        LocalDateTime departure =
                LocalDateTime.of(2026, 9, 10, 9, 0);

        LocalDateTime arrival =
                LocalDateTime.of(2026, 9, 10, 11, 30);

        FlightSearchRequest request =
                new FlightSearchRequest("IST", "COV", departure);

        var providerAResponse =
                new com.flightsearch.generated.providera
                        .AvailabilitySearchResponse();

        var providerBResponse =
                new com.flightsearch.generated.providerb
                        .AvailabilitySearchResponse();

        FlightDto expensiveFlight = new FlightDto(
                "TK1001",
                "IST",
                "COV",
                departure,
                arrival,
                new BigDecimal("250.00")
        );

        FlightDto cheapFlight = new FlightDto(
                "TK1001",
                "IST",
                "COV",
                departure,
                arrival,
                new BigDecimal("210.00")
        );

        when(providerAClient.search(request))
                .thenReturn(providerAResponse);

        when(providerBClient.search(request))
                .thenReturn(providerBResponse);

        when(providerAMapper.toFlightDtos(providerAResponse))
                .thenReturn(List.of(expensiveFlight));

        when(providerBMapper.toFlightDtos(providerBResponse))
                .thenReturn(List.of(cheapFlight));

        FlightSearchResponse response =
                service.cheapestFlight(request);

        assertEquals(1, response.flightOptions().size());
        assertEquals(
                new BigDecimal("210.00"),
                response.flightOptions().get(0).price()
        );
    }

}
