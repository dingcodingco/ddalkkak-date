package com.ddalkkak.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Kakao Local API Configuration
 */
@Configuration
public class KakaoApiConfig {

    @Value("${kakao.api.key}")
    private String apiKey;

    @Value("${kakao.api.base-url:https://dapi.kakao.com}")
    private String baseUrl;

    @Bean(name = "kakaoWebClient")
    public WebClient kakaoWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "KakaoAK " + apiKey)
                .build();
    }
}
