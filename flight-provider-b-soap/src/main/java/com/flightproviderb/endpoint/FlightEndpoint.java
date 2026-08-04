package com.flightproviderb.endpoint;

import com.flightproviderb.generated.AvailabilitySearchRequest;
import com.flightproviderb.generated.AvailabilitySearchResponse;
import com.flightproviderb.mapper.FlightMapper;
import com.flightproviderb.model.SearchRequest;
import com.flightproviderb.model.SearchResult;
import com.flightproviderb.service.SearchService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@Endpoint
public class FlightEndpoint {

    private static final String NAMESPACE_URI =
            "http://flightsearch.com/providerb";

    private final SearchService searchService;
    private final FlightMapper mapper;

    public FlightEndpoint(SearchService searchService,
                          FlightMapper mapper) {
        this.searchService = searchService;
        this.mapper = mapper;
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "AvailabilitySearchRequest")
    @ResponsePayload
    public AvailabilitySearchResponse availabilitySearch(
            @RequestPayload AvailabilitySearchRequest request) {

        // SOAP Request -> Business Request

        SearchRequest businessRequest = mapper.toBusinessRequest(request);



        SearchResult businessResponse = searchService.availabilitySearch(businessRequest);// Business Response -> SOAP Response

        // Business Response -> SOAP Response
        return mapper.toSoapResponse(businessResponse);
    }

}