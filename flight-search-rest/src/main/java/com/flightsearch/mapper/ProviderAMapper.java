package com.flightsearch.mapper;

import com.flightsearch.dto.FlightDto;
import com.flightsearch.dto.FlightSearchRequest;
import com.flightsearch.generated.providera.AvailabilitySearchRequest;
import com.flightsearch.generated.providera.AvailabilitySearchResponse;
import com.flightsearch.generated.providera.Flight;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class ProviderAMapper {
    public AvailabilitySearchRequest toSoapRequest(
            FlightSearchRequest request) {

        AvailabilitySearchRequest soapRequest =
                new AvailabilitySearchRequest();

        soapRequest.setOrigin(request.origin());
        soapRequest.setDestination(request.destination());

        GregorianCalendar calendar =
                GregorianCalendar.from(
                        request.departureDate()
                                .atZone(ZoneId.systemDefault())
                );

        try {

            XMLGregorianCalendar xmlDate =
                    DatatypeFactory.newInstance()
                            .newXMLGregorianCalendar(calendar);

            soapRequest.setDepartureDate(xmlDate);

        } catch (DatatypeConfigurationException e) {

            throw new RuntimeException(e);

        }

        return soapRequest;

    }

    public List<FlightDto> toFlightDtos(
            AvailabilitySearchResponse response){

        List<FlightDto> flights = new ArrayList<>();

        for (Flight flight:response.getFlightOptions()){
            flights.add(new FlightDto(
                    flight.getFlightNo(),
                    flight.getOrigin(),
                    flight.getDestination(),
                    flight.getDeparturedatetime().toGregorianCalendar()
                            .toZonedDateTime().toLocalDateTime(),
                    flight.getArrivaldatetime().toGregorianCalendar()
                            .toZonedDateTime().toLocalDateTime(),
                    flight.getPrice()
            ));
        }

        return flights;
    }

}
