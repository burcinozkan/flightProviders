package com.flightsearch.client;

import com.flightsearch.dto.FlightSearchRequest;
import com.flightsearch.generated.providera.AvailabilitySearchRequest;
import com.flightsearch.generated.providera.AvailabilitySearchResponse;
import com.flightsearch.mapper.ProviderAMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;



@Component
public class ProviderAClient {


    private final WebServiceTemplate webServiceTemplate;
    private final String endpoint;
    private final ProviderAMapper mapper;

    public ProviderAClient(WebServiceTemplate webServiceTemplate,
                           @Value("${soap.provider-a.url}") String endpoint,
                           ProviderAMapper mapper) {
        this.webServiceTemplate = webServiceTemplate;
        this.endpoint = endpoint;
        this.mapper = mapper;
    }

    public AvailabilitySearchResponse search(FlightSearchRequest request){
        // rest dto - > soap req

       AvailabilitySearchRequest soapRequest = mapper.toSoapRequest(request);

       AvailabilitySearchResponse response = (AvailabilitySearchResponse)
               webServiceTemplate.marshalSendAndReceive(endpoint,soapRequest);

       return response;
    }
}
