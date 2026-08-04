package com.flightproviderb.mapper;


import com.flightproviderb.generated.AvailabilitySearchRequest;
import com.flightproviderb.generated.AvailabilitySearchResponse;
import com.flightproviderb.generated.Flight;
import com.flightproviderb.model.SearchRequest;
import com.flightproviderb.model.SearchResult;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@Component
public class FlightMapper {

    private static final ZoneId APPLICATION_ZONE =
            ZoneId.of("Europe/Istanbul");

    //toBusinessRequest()

    public SearchRequest toBusinessRequest(AvailabilitySearchRequest request){

        if (request==null) return null;

        SearchRequest businessRequest = new SearchRequest();

        businessRequest.setDeparture(request.getDeparture());
        businessRequest.setArrival(request.getArrival());


        if (request.getDepartureDate()!= null){
            businessRequest.setDepartureDate(request.getDepartureDate()
                    .toGregorianCalendar()
                    .toZonedDateTime()
                    .toLocalDateTime()
            );
        }
        return businessRequest;
    }

    //toSoapResponse()

    public AvailabilitySearchResponse toSoapResponse(SearchResult result){

        AvailabilitySearchResponse response = new AvailabilitySearchResponse();

        if (result == null){
            response.setHasError(true);
            response.setErrorMessage("Search result is null");
            return response;
        }
        response.setHasError(result.isHasError());
        response.setErrorMessage(result.getErrorMessage());

        if (result.getFlightOptions() == null) return response;

        for (var flight:result.getFlightOptions()){
            if (flight == null) continue;

            var soapFlight = new Flight();

            soapFlight.setFlightNumber(flight.getFlightNumber());
            soapFlight.setArrival(flight.getArrival());
            soapFlight.setDeparture(flight.getDeparture());
            soapFlight.setDeparturedatetime(
                    toXmlDate(flight.getDeparturedatetime())
            );
            soapFlight.setArrivaldatetime(
                    toXmlDate(flight.getArrivaldatetime())
            );
            soapFlight.setPrice(flight.getPrice());

            response.getFlightOptions().add(soapFlight);
        }

        return response;

    }

    //toXmlDate()
    private XMLGregorianCalendar toXmlDate(LocalDateTime date){
        if (date == null) return null;

        try {
            GregorianCalendar calendar = GregorianCalendar.from(
                    date.atZone(APPLICATION_ZONE)
            );
            return DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(calendar);
        }catch (Exception e){
            throw new IllegalStateException(
                    "Date can not be converted", e
            );
        }
    }
}
