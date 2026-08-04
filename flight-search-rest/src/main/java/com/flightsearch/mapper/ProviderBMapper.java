package com.flightsearch.mapper;

import com.flightsearch.dto.FlightDto;
import com.flightsearch.dto.FlightSearchRequest;
import com.flightsearch.generated.providerb.AvailabilitySearchResponse;
import com.flightsearch.generated.providerb.AvailabilitySearchRequest;
import com.flightsearch.generated.providerb.Flight;
import org.springframework.stereotype.Component;


import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class ProviderBMapper {

    public AvailabilitySearchRequest toSoapRequest(
            FlightSearchRequest request
    ){
        AvailabilitySearchRequest soapRequest = new AvailabilitySearchRequest();

        soapRequest.setArrival(request.destination());
        soapRequest.setDeparture(request.origin());

        GregorianCalendar calendar = GregorianCalendar.from(
                request.departureDate()
                        .atZone(ZoneId.systemDefault())
        );


        try{
            XMLGregorianCalendar xmlDate=
                DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(calendar);

            soapRequest.setDepartureDate(xmlDate);
        }catch (DatatypeConfigurationException E){
            throw  new RuntimeException(E);
        }
        return soapRequest;
    }


    public List<FlightDto> toFlightDtos(
            AvailabilitySearchResponse response
    ){
        List<FlightDto> flights = new ArrayList<>();

        for (Flight flight:response.getFlightOptions()){
            flights.add(new FlightDto(
                    flight.getFlightNumber(),
                    flight.getDeparture(),
                    flight.getArrival(),
                    flight.getDeparturedatetime().toGregorianCalendar().toZonedDateTime().toLocalDateTime(),
                    flight.getArrivaldatetime().toGregorianCalendar().toZonedDateTime().toLocalDateTime(),
                    flight.getPrice()
            ));
        }
        return flights;
    }
}
