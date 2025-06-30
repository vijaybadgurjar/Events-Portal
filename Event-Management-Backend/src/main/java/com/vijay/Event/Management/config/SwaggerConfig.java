package com.vijay.Event.Management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EventsPortal API")
                        .version("0.0.1-SNAPSHOT")
                        .description("API documentation for EventsPortal")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .email("iritikkumar7@gmail.com")
                                .name("Ritik Kumar"))
                );
    }
}