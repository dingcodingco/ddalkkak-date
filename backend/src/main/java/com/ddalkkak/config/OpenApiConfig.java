package com.ddalkkak.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("딸깍데이트 API")
                        .description("딸깍데이트 백엔드 REST API 문서")
                        .version("v0.0.1")
                        .contact(new Contact()
                                .name("딸깍데이트 팀")
                                .email("contact@ddalkkak.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.ddalkkak.com")
                                .description("Production Server")
                ));
    }
}
