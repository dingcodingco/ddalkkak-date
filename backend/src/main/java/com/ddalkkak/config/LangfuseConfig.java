package com.ddalkkak.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class LangfuseConfig {

    @Value("${langfuse.api.public-key}")
    private String publicKey;

    @Value("${langfuse.api.secret-key}")
    private String secretKey;

    @Value("${langfuse.api.base-url}")
    private String baseUrl;

    public boolean isEnabled() {
        return publicKey != null && !publicKey.isEmpty()
            && secretKey != null && !secretKey.isEmpty();
    }
}
