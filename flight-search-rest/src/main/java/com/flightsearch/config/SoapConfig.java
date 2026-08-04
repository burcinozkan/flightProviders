package com.flightsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class SoapConfig {

    /*
    * axb2Marshaller
WebServiceTemplate*/

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        //xmle dönüştürülecek java sınıflarının bulundugu paket
        marshaller.setPackagesToScan(
                "com.flightsearch.generated.providera",
                "com.flightsearch.generated.providerb"
        );
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller jaxb2Marshaller){

        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(jaxb2Marshaller);
        template.setUnmarshaller(jaxb2Marshaller);

        return template;
    }
}
