package com.moreira.smartstock.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.OAUTH2, scheme = "bearer",
        flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8080/oauth2/token")),
        description = "Use junto com username e password o 'myclientid' e 'myclientsecret' para obter o token")
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SmartStock-API")
                        .version("1.0.0")
                        .description("API RESTful para gerenciamento de estoque")
                        .contact(new Contact()
                                .name("Suporte Técnico")
                                .email("nathangc7@gmail.com")));
    }
}
