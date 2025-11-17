package com.br.pdvpostocombustivelbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PDV Posto Combustível API")
                        .version("v1")
                        .description("API de exemplo com CRUD de Pessoas (Spring Boot 3 / Java 17).")
                        .contact(new Contact()
                                .name("Samuel Tavares Portes")
                                .email("samueltportes@gmail.com"))
                        .license(new License()
                                .name("MIT")));
    }
}
