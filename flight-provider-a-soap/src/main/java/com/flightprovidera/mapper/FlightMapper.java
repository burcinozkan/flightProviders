package com.flightprovidera.mapper;

import com.flightprovidera.generated.AvailabilitySearchRequest;
import com.flightprovidera.generated.AvailabilitySearchResponse;
import com.flightprovidera.generated.Flight;
import com.flightprovidera.model.SearchRequest;
import com.flightprovidera.model.SearchResult;
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

    public SearchRequest toBusinessRequest(
            AvailabilitySearchRequest request) {

        if (request == null) {
            return null;
        }
        SearchRequest businessRequest = new SearchRequest();

        businessRequest.setOrigin(request.getOrigin());
        businessRequest.setDestination(request.getDestination());

        if (request.getDepartureDate() !=null){
            businessRequest.setDepartureDate(
                    request.getDepartureDate()
                            .toGregorianCalendar()
                            .toZonedDateTime()
                            .toLocalDateTime()
            );
        }

        return businessRequest;
    }

    public AvailabilitySearchResponse toSoapResponse(
            SearchResult result) {

        AvailabilitySearchResponse response =
                new AvailabilitySearchResponse();

        if (result == null) {
            response.setHasError(true);
            response.setErrorMessage("Search result is null");
            return response;
        }

        response.setHasError(result.isHasError());
        response.setErrorMessage(result.getErrorMessage());

        if (result.getFlightOptions() == null) {
            return response;
        }

        for (var flight : result.getFlightOptions()) {

            if (flight == null) {
                continue;
            }

            var soapFlight = new Flight();

            soapFlight.setFlightNo(flight.getFlightNo());
            soapFlight.setOrigin(flight.getOrigin());
            soapFlight.setDestination(flight.getDestination());
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

    private XMLGregorianCalendar toXmlDate(LocalDateTime date) {

        if (date == null) {
            return null;
        }
        try {
            GregorianCalendar calendar = GregorianCalendar.from(
                    date.atZone(APPLICATION_ZONE)
            );

            return DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(calendar);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Date can not be converted", e
            );
        }
    }
}