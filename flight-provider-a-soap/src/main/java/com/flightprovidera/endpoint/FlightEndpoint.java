package com.flightprovidera.endpoint;

import com.flightprovidera.generated.AvailabilitySearchRequest;
import com.flightprovidera.generated.AvailabilitySearchResponse;
import com.flightprovidera.mapper.FlightMapper;
import com.flightprovidera.model.SearchRequest;
import com.flightprovidera.model.SearchResult;
import com.flightprovidera.service.SearchService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class FlightEndpoint {

    private static final String NAMESPACE_URI =
            "http://flightsearch.com/providera";

    private final SearchService searchService;
    private final FlightMapper flightMapper;

    public FlightEndpoint(
            SearchService searchService,
            FlightMapper flightMapper) {

        this.searchService = searchService;
        this.flightMapper = flightMapper;
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "AvailabilitySearchRequest"
    )
    @ResponsePayload
    public AvailabilitySearchResponse availabilitySearch(
            @RequestPayload AvailabilitySearchRequest request) {

        SearchRequest businessRequest =
                flightMapper.toBusinessRequest(request);

        SearchResult result =
                searchService.availabilitySearch(businessRequest);

        return flightMapper.toSoapResponse(result);
    }
}