package com.ddalkkak.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ClaudeApiConfig {

    @Value("${claude.api.key}")
    private String apiKey;

    @Value("${claude.api.model}")
    private String model;

    @Value("${claude.api.timeout}")
    private int timeout;

    @Value("${claude.api.max-tokens}")
    private int maxTokens;
}
