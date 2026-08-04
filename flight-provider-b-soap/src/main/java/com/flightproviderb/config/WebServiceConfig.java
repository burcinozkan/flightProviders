package com.flightproviderb.config;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


//Enable Spring Web Services
@EnableWs
@Configuration
public class WebServiceConfig {

    //messagedispatcher

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet>messageDispatcherServlet(ApplicationContext context){
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean
    public XsdSchema flightsSchema(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/providerb.xsd"));
    }

    @Bean(name = "flights")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema flightsSchema){
        DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
        defaultWsdl11Definition.setPortTypeName("FlightsPort");
        defaultWsdl11Definition.setTargetNamespace(
                "http://flightsearch.com/providerb");
        defaultWsdl11Definition.setLocationUri("/ws");
        defaultWsdl11Definition.setSchema(flightsSchema);
        return defaultWsdl11Definition;
    }


}
