package com.flightsearch.client;

import com.flightsearch.dto.FlightSearchRequest;
import com.flightsearch.generated.providerb.AvailabilitySearchRequest;
import com.flightsearch.generated.providerb.AvailabilitySearchResponse;
import com.flightsearch.mapper.ProviderBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class ProviderBClient {

    private final WebServiceTemplate webServiceTemplate;
    private final String endpoint;
    private final ProviderBMapper mapper;

    public ProviderBClient(WebServiceTemplate webServiceTemplate, @Value("${soap.provider-b.url}") String endpoint, ProviderBMapper mapper) {
        this.webServiceTemplate = webServiceTemplate;
        this.endpoint = endpoint;
        this.mapper = mapper;
    }

    public AvailabilitySearchResponse search(FlightSearchRequest request){

        AvailabilitySearchRequest soapRequest = mapper.toSoapRequest(request);

        AvailabilitySearchResponse response = (AvailabilitySearchResponse) webServiceTemplate.marshalSendAndReceive(endpoint,soapRequest);

        return response;
    }
}
