package com.flightsearch.service;


import com.flightsearch.client.ProviderAClient;
import com.flightsearch.client.ProviderBClient;
import com.flightsearch.dto.FlightDto;
import com.flightsearch.dto.FlightKey;
import com.flightsearch.dto.FlightSearchRequest;
import com.flightsearch.dto.FlightSearchResponse;
import com.flightsearch.generated.providera.AvailabilitySearchResponse;
import com.flightsearch.mapper.ProviderAMapper;
import com.flightsearch.mapper.ProviderBMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlightSearchService {

    private final ProviderAClient providerAClient;
    private final ProviderAMapper providerAMapper;
    private final ProviderBMapper providerBMapper;
    private final ProviderBClient providerBClient;
    private final FlightLogService logService;

    public FlightSearchService(
            ProviderAClient providerAClient,
            ProviderAMapper providerAMapper,
            ProviderBMapper providerBMapper,
            ProviderBClient providerBClient,
            FlightLogService logService) {

        this.providerAClient = providerAClient;
        this.providerAMapper = providerAMapper;
        this.providerBClient =providerBClient;
        this.providerBMapper = providerBMapper;
        this.logService = logService;
    }

    public List<FlightDto> getAllFlights(FlightSearchRequest request){
        AvailabilitySearchResponse providerAResponse = providerAClient.search(request);


        com.flightsearch.generated.providerb.AvailabilitySearchResponse providerBResponse =
                providerBClient.search(request);


        List<FlightDto> providerAFlights = providerAMapper.toFlightDtos(providerAResponse);
        List<FlightDto> providerBFlights = providerBMapper.toFlightDtos(providerBResponse);

        List<FlightDto> allFlights = new ArrayList<>();
        allFlights.addAll(providerAFlights);
        allFlights.addAll(providerBFlights);

        return allFlights;

    }

    public FlightSearchResponse search(
            FlightSearchRequest request) {

        FlightSearchResponse response =
                new FlightSearchResponse(
                        false,
                        getAllFlights(request),
                        null
                );

        logService.save(
                "/api/flights/search",
                request,
                response
        );

        return response;
    }

    public FlightSearchResponse cheapestFlight(FlightSearchRequest request){

        Map<FlightKey, FlightDto> cheapestFlights = new HashMap<>();


        for (FlightDto flight:getAllFlights(request)){
            FlightKey key = new FlightKey(
                    flight.flightNumber(),
                    flight.origin(),
                    flight.destination(),
                    flight.departuredatetime(),
                    flight.arrivaldatetime()
            );

            if (!cheapestFlights.containsKey(key)){
                cheapestFlights.put(key,flight);
            }else{
                FlightDto currentFlight = cheapestFlights.get(key);

                if (flight.price().compareTo(currentFlight.price())<0){
                    cheapestFlights.put(key,flight);
                }
            }

        }
        FlightSearchResponse response =new FlightSearchResponse(
                false,
                new ArrayList<>(cheapestFlights.values()),
                null
        );

        logService.save(
                "/api/flights/cheapest",
                request,
                response
        );
        return response;
    }
}
